package org.openhab.cli.engine.endpoint;

import java.io.Serial;
import java.util.Map;
import java.util.stream.Collectors;
import org.openhab.cli.client.ApiException;

/** An API failure with endpoint, operation, and parameter context, retaining the original cause. */
public class EndpointException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Wraps a failure from a generated API operation.
     *
     * @param endpointClass the endpoint that made the request
     * @param name the generated API operation name
     * @param params request parameters for diagnostics; values may be null or redacted
     * @param apiEx the original API exception
     */
    public EndpointException(
            Class<? extends Endpoint> endpointClass, String name, Map<String, ?> params, ApiException apiEx) {
        super(buildMessage(endpointClass, name, params, apiEx), apiEx);
    }

    private static String buildMessage(
            Class<? extends Endpoint> endpointClass, String name, Map<String, ?> params, ApiException apiEx) {
        var paramsString = params.entrySet().stream()
                .map(e -> "%s=%s".formatted(e.getKey(), e.getValue()))
                .collect(Collectors.joining(", "));
        return "Failed: %s.%s(%s) with code %s:%s"
                .formatted(endpointClass.getSimpleName(), name, paramsString, apiEx.getCode(), apiEx.getMessage());
    }
}
