package org.openhab.cli.runtime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.openhab.cli.client.JSON;
import org.openhab.cli.engine.rest.ApiClient;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

class EndpointCommandsTest {
    private record Operation(Class<?> endpoint, Class<?> command, Method method) {}

    private static Stream<Operation> operations() {
        return Cli.commandLine().getSubcommands().values().stream()
                .filter(group -> !(group.getCommand() instanceof org.openhab.cli.runtime.command.config.ConfigCommand))
                .flatMap(group -> {
                    var groupType = group.getCommand().getClass();
                    try {
                        var endpoint = Class.forName("org.openhab.cli.engine.endpoint."
                                + groupType.getSimpleName().replace("Command", ""));
                        return group.getSubcommands().entrySet().stream().map(entry -> {
                            var method = Arrays.stream(endpoint.getDeclaredMethods())
                                    .filter(candidate -> candidate.getName().equals(entry.getKey()))
                                    .max(Comparator.comparingInt(Method::getParameterCount))
                                    .orElseThrow();
                            return new Operation(
                                    endpoint, entry.getValue().getCommand().getClass(), method);
                        });
                    } catch (ClassNotFoundException e) {
                        throw new AssertionError(e);
                    }
                });
    }

    @Test
    void registersEveryEngineOperationAndInjectsEveryCommand() throws Exception {
        var operations = operations().toList();
        assertEquals(175, operations.size());
        assertEquals(30, operations.stream().map(Operation::endpoint).distinct().count());
        var factory = DaggerRuntimeComponent.create().commandFactory();
        for (var operation : operations) {
            assertNotSame(factory.create(operation.command()), factory.create(operation.command()));
            for (var method : operation.endpoint().getDeclaredMethods()) {
                if (java.lang.reflect.Modifier.isPublic(method.getModifiers())) {
                    assertTrue(
                            operations.stream()
                                    .anyMatch(candidate -> candidate.endpoint() == operation.endpoint()
                                            && candidate.method().getName().equals(method.getName())),
                            method.toString());
                }
            }
        }
    }

    @TestFactory
    Stream<DynamicTest> delegatesEveryOperationWithAllArguments() {
        return operations()
                .map(operation -> DynamicTest.dynamicTest(
                        operation.endpoint().getSimpleName() + "."
                                + operation.method().getName(),
                        () -> verifyDelegation(operation, false)));
    }

    @TestFactory
    Stream<DynamicTest> passesNullForOmittedOptionalArguments() {
        return operations()
                .map(operation -> DynamicTest.dynamicTest(
                        operation.endpoint().getSimpleName() + "."
                                + operation.method().getName(),
                        () -> verifyDelegation(operation, true)));
    }

    private void verifyDelegation(Operation operation, boolean omitOptional) throws Exception {
        var console = mock(Console.class);
        var builder = mock(ApiClientBuilder.class);
        var apiClient = mock(ApiClient.class);
        when(builder.build(any())).thenReturn(apiClient);
        var constructor = operation.command().getDeclaredConstructor(Console.class, ApiClientBuilder.class);
        constructor.setAccessible(true);
        var command = (Callable<?>) constructor.newInstance(console, builder);
        var fields = Arrays.stream(operation.command().getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(CommandLine.Parameters.class)
                        || field.isAnnotationPresent(CommandLine.Option.class))
                .toList();
        assertEquals(operation.method().getParameterCount(), fields.size());
        var flags = new ArrayList<String>();
        var positionals = new ArrayList<String>();
        var expected = new Object[fields.size()];
        var types = operation.method().getGenericParameterTypes();
        for (int i = 0; i < fields.size(); i++) {
            Field field = fields.get(i);
            var positional = field.getAnnotation(CommandLine.Parameters.class);
            boolean optional = positional == null || positional.arity().equals("0..1");
            if (omitOptional && optional) {
                continue;
            }
            String argument = argument(types[i], i);
            if (positional == null) {
                flags.add(field.getAnnotation(CommandLine.Option.class).names()[0] + "=" + argument);
            } else {
                positionals.add(argument);
            }
            expected[i] = types[i] == String.class ? argument : JSON.getGson().fromJson(argument, types[i]);
        }
        flags.add("--");
        flags.addAll(positionals);
        new CommandLine(command).parseArgs(flags.toArray(String[]::new));
        var returnType = operation.method().getReturnType();
        Object response = returnType == void.class
                ? null
                : returnType == String.class
                        ? "result"
                        : returnType == Integer.class
                                ? 42
                                : returnType == List.class
                                        ? List.of("result")
                                        : returnType == Set.class
                                                ? Set.of("result")
                                                : returnType == Object.class
                                                        ? Map.of("result", true)
                                                        : mock(returnType);
        var invoked = new AtomicReference<Method>();
        var arguments = new AtomicReference<Object[]>();
        try (var mocked = mockConstruction(operation.endpoint(), withSettings().defaultAnswer(invocation -> {
            invoked.set(invocation.getMethod());
            arguments.set(invocation.getArguments());
            return response;
        }))) {
            assertEquals(0, command.call());
            assertEquals(1, mocked.constructed().size());
            assertEquals(operation.method(), invoked.get());
            for (int i = 0; i < expected.length; i++) {
                assertEquals(
                        JSON.getGson().toJsonTree(expected[i], types[i]),
                        JSON.getGson().toJsonTree(arguments.get()[i], types[i]),
                        fields.get(i).getName());
            }
            if (operation.method().getReturnType() == void.class) {
                verifyNoInteractions(console);
            } else {
                verify(console).writeJson(same(response), eq(true));
            }
        }
        verify(builder).build(any(Options.class));
    }

    private static String argument(Type type, int index) {
        if (type == String.class) return "value-" + index;
        if (type == Integer.class) return "7";
        if (type == Boolean.class) return "true";
        if (type instanceof ParameterizedType parameterized) {
            if (parameterized.getRawType() == Map.class) return "{\"key\":\"value\"}";
            var element = parameterized.getActualTypeArguments()[0];
            return "[" + (element == String.class ? "\"element\"" : argument(element, index)) + "]";
        }
        return switch (((Class<?>) type).getSimpleName()) {
            case "CanSerializeRulesRequest" -> "{\"rules\":[]}";
            case "GroupItem" -> "{\"name\":\"sample\",\"type\":\"Switch\"}";
            case "Metadata" -> "{\"editable\":true,\"value\":\"sample\"}";
            case "Thing" ->
                "{\"UID\":\"sample\",\"channels\":[],\"configuration\":{},\"properties\":{},\"thingTypeUID\":\"sample\"}";
            case "Rule" ->
                "{\"actions\":[],\"conditions\":[],\"configDescriptions\":[],\"configuration\":{},\"tags\":[],\"templateState\":\"sample\",\"triggers\":[],\"uid\":\"sample\",\"visibility\":\"VISIBLE\"}";
            default -> "{}";
        };
    }
}
