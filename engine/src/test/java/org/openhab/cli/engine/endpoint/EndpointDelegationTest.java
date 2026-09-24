package org.openhab.cli.engine.endpoint;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.withSettings;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.mockito.MockMakers;
import org.mockito.stubbing.Answer;
import org.openhab.cli.client.ApiException;
import org.openhab.cli.client.ApiResponse;
import org.openhab.cli.client.api.ActionsApi;
import org.openhab.cli.engine.rest.ApiClient;

class EndpointDelegationTest {
    private record Operation(Class<?> endpoint, Class<?> api, Method method) {
        String wrapperName() {
            var name = method.getName().replaceFirst("WithHttpInfo$", "");
            if (!name.startsWith("get")) {
                return name;
            }
            var suffix = name.substring(3);
            var acronym =
                    java.util.regex.Pattern.compile("^[A-Z]+(?=[A-Z][a-z]|$)").matcher(suffix);
            if (acronym.find()) {
                suffix = acronym.group().toLowerCase(Locale.ROOT) + suffix.substring(acronym.end());
            }
            return Character.toLowerCase(suffix.charAt(0)) + suffix.substring(1);
        }

        Method wrapper() throws NoSuchMethodException {
            return endpoint.getMethod(wrapperName(), method.getParameterTypes());
        }

        Object instance(Answer<?> answer) throws ReflectiveOperationException {
            var client = mock(api, withSettings().mockMaker(MockMakers.SUBCLASS).defaultAnswer(answer));
            var constructor = endpoint.getDeclaredConstructor(api);
            constructor.setAccessible(true);
            return constructor.newInstance(client);
        }

        String label() {
            return endpoint.getSimpleName() + "." + wrapperName();
        }
    }

    private static Stream<Operation> operations() {
        return Arrays.stream(Endpoint.class.getPermittedSubclasses())
                .filter(endpoint -> endpoint != EngineInternal.class)
                .flatMap(endpoint -> {
                    try {
                        var api = endpoint.getDeclaredField("api").getType();
                        return Arrays.stream(api.getDeclaredMethods())
                                .filter(method -> method.getName().endsWith("WithHttpInfo"))
                                .map(method -> new Operation(endpoint, api, method));
                    } catch (NoSuchFieldException e) {
                        throw new AssertionError("Missing API client for " + endpoint, e);
                    }
                });
    }

    @Test
    void coversEveryCompiledApiOperation() {
        assertEquals(175, operations().count());
        assertEquals(30, operations().map(Operation::api).distinct().count());
    }

    @TestFactory
    Stream<DynamicTest> forwardsArgumentsAndReturnsData() {
        return operations()
                .map(operation -> DynamicTest.dynamicTest(operation.label(), () -> {
                    var types = operation.method().getGenericParameterTypes();
                    var args = new Object[types.length];
                    for (int i = 0; i < types.length; i++) {
                        args[i] = sample(types[i], i);
                    }
                    var returnType =
                            ((ParameterizedType) operation.method().getGenericReturnType()).getActualTypeArguments()[0];
                    var data = sample(returnType, 100);
                    var calls = new AtomicInteger();
                    var endpoint = operation.instance(invocation -> {
                        assertEquals(operation.method(), invocation.getMethod());
                        assertArrayEquals(args, invocation.getArguments());
                        calls.incrementAndGet();
                        return new ApiResponse<>(200, Map.of(), data);
                    });
                    assertSame(data, operation.wrapper().invoke(endpoint, args));
                    assertEquals(1, calls.get());
                    assertEquals(
                            returnType == Void.class ? void.class : returnType,
                            operation.wrapper().getGenericReturnType());
                }));
    }

    @TestFactory
    Stream<DynamicTest> supportsEmptyResponseBodies() {
        return operations()
                .map(operation -> DynamicTest.dynamicTest(operation.label(), () -> {
                    var endpoint = operation.instance(invocation -> new ApiResponse<>(204, Map.of(), null));
                    assertNull(operation
                            .wrapper()
                            .invoke(endpoint, new Object[operation.method().getParameterCount()]));
                }));
    }

    @TestFactory
    Stream<DynamicTest> preservesApiFailuresWithNullArguments() {
        return operations()
                .map(operation -> DynamicTest.dynamicTest(operation.label(), () -> {
                    var failure = new ApiException(503, "server unavailable");
                    var endpoint = operation.instance(invocation -> {
                        throw failure;
                    });
                    var reflected = assertThrows(InvocationTargetException.class, () -> operation
                            .wrapper()
                            .invoke(endpoint, new Object[operation.method().getParameterCount()]));
                    var wrapped = assertInstanceOf(EndpointException.class, reflected.getCause());
                    assertSame(failure, wrapped.getCause());
                    assertTrue(wrapped.getMessage().contains(operation.method().getName()));
                    assertTrue(
                            wrapped.getMessage().contains(operation.endpoint().getSimpleName()));
                    assertTrue(wrapped.getMessage().contains("503"));
                }));
    }

    @TestFactory
    Stream<DynamicTest> overloadsSupplyNullForOptionalParameters() {
        return operations()
                .filter(operation ->
                        Arrays.stream(operation.method().getParameters()).anyMatch(EndpointDelegationTest::optional))
                .map(operation -> DynamicTest.dynamicTest(operation.label(), () -> {
                    var parameters = operation.method().getParameters();
                    var required = IntStream.range(0, parameters.length)
                            .filter(i -> !optional(parameters[i]))
                            .toArray();
                    var types = Arrays.stream(required)
                            .mapToObj(i -> parameters[i].getType())
                            .toArray(Class<?>[]::new);
                    var overload = operation.endpoint().getMethod(operation.wrapperName(), types);
                    var args = new Object[required.length];
                    var forwarded = new Object[parameters.length];
                    for (int i = 0; i < required.length; i++) {
                        args[i] = sample(parameters[required[i]].getParameterizedType(), i);
                        forwarded[required[i]] = args[i];
                    }
                    var calls = new AtomicInteger();
                    var endpoint = operation.instance(invocation -> {
                        assertEquals(operation.method(), invocation.getMethod());
                        assertArrayEquals(forwarded, invocation.getArguments());
                        calls.incrementAndGet();
                        return new ApiResponse<>(204, Map.of(), null);
                    });
                    overload.invoke(endpoint, args);
                    assertEquals(1, calls.get());
                }));
    }

    @Test
    void publicConstructorsShareConfiguredClient() throws ReflectiveOperationException {
        var nativeClient = new org.openhab.cli.client.ApiClient().setBasePath("http://localhost:12345/rest");
        var client = new ApiClient(nativeClient);
        assertSame(nativeClient, client.toNative());
        for (var type : Endpoint.class.getPermittedSubclasses()) {
            if (type == EngineInternal.class) {
                continue;
            }
            var endpoint = type.getConstructor(ApiClient.class).newInstance(client);
            var field = type.getDeclaredField("api");
            field.setAccessible(true);
            var generated = field.get(endpoint);
            assertSame(
                    nativeClient, generated.getClass().getMethod("getApiClient").invoke(generated));
        }
    }

    @Test
    void actionLanguageOverloadPreservesApiFailure() throws ApiException {
        var failure = new ApiException(404, "thing missing");
        var api = mock(
                ActionsApi.class, withSettings().mockMaker(MockMakers.SUBCLASS).defaultAnswer(invocation -> {
                    assertArrayEquals(new Object[] {"thing:test", null}, invocation.getArguments());
                    throw failure;
                }));
        var wrapped =
                assertThrows(EndpointException.class, () -> new Action(api).availableActionsForThing("thing:test"));
        assertSame(failure, wrapped.getCause());
        assertTrue(wrapped.getMessage().contains("acceptLanguage=null"));
        assertTrue(wrapped.getMessage().contains("thingUID=thing:test"));
    }

    private static boolean optional(Parameter parameter) {
        return Arrays.stream(parameter.getAnnotations())
                .anyMatch(annotation ->
                        annotation.annotationType().getSimpleName().equals("Nullable"));
    }

    private static Object sample(Type type, int index) throws ReflectiveOperationException {
        if (type instanceof ParameterizedType parameterized) {
            var element = sample(parameterized.getActualTypeArguments()[0], index);
            if (parameterized.getRawType() == List.class) return List.of(element);
            if (parameterized.getRawType() == Set.class) return Set.of(element);
            if (parameterized.getRawType() == Map.class) return Map.of("key-" + index, "value-" + index);
            throw new AssertionError("Unhandled generic type " + type);
        }
        var clazz = (Class<?>) type;
        if (clazz == Void.class) return null;
        if (clazz == String.class) return "value-" + index;
        if (clazz == Boolean.class) return index % 2 == 0;
        if (clazz == Integer.class) return index + 1;
        return clazz.getConstructor().newInstance();
    }
}
