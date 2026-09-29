package org.openhab.cli.runtime.service;

import com.google.gson.JsonParser;
import lombok.experimental.UtilityClass;
import org.openhab.cli.client.JSON;
import org.openhab.cli.engine.endpoint.EndpointException;

/** Creates a safe command-line error message from an openHAB endpoint failure. */
@UtilityClass
public class EndpointErrorMessage {
    /**
     * Returns the API response as formatted JSON when possible, otherwise a concise endpoint message.
     *
     * @param exception endpoint failure
     * @param prettyPrint whether JSON should be indented
     * @return message suitable for {@link Console#writeError(String, Throwable, Object...)}
     */
    public String from(EndpointException exception, boolean prettyPrint) {
        var responseBody = exception.responseBody();
        if (responseBody == null || responseBody.isBlank()) {
            return exception.getMessage();
        }
        try {
            var json = JsonParser.parseString(responseBody);
            return prettyPrint
                    ? JSON.getGson().newBuilder().setPrettyPrinting().create().toJson(json)
                    : JSON.getGson().toJson(json);
        } catch (RuntimeException ignored) {
            return responseBody;
        }
    }
}
