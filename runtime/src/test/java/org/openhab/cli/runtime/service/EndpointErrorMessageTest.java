package org.openhab.cli.runtime.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;
import org.junit.jupiter.api.Test;
import org.openhab.cli.client.ApiException;
import org.openhab.cli.engine.endpoint.EndpointException;
import org.openhab.cli.engine.endpoint.Items;

class EndpointErrorMessageTest {
    @Test
    void formatsJsonResponseBodiesWithoutHttpHeaders() {
        var exception = endpointException("{\"error\":{\"message\":\"Item x does not exist!\",\"http-code\":404}}");

        assertEquals("""
                {
                  "error": {
                    "message": "Item x does not exist!",
                    "http-code": 404
                  }
                }""", EndpointErrorMessage.from(exception, true));
        assertEquals(
                "{\"error\":{\"message\":\"Item x does not exist!\",\"http-code\":404}}",
                EndpointErrorMessage.from(exception, false));
    }

    @Test
    void fallsBackToResponseTextOrConciseEndpointMessage() {
        assertEquals("gateway unavailable", EndpointErrorMessage.from(endpointException("gateway unavailable"), true));
        assertEquals(
                "Failed: Items.getItemByNameWithHttpInfo(itemName=x) with HTTP status 404",
                EndpointErrorMessage.from(endpointException(null), true));
    }

    private EndpointException endpointException(String responseBody) {
        var apiException = new ApiException(
                "generated client message", 404, Map.of("private-header", java.util.List.of("secret")), responseBody);
        return new EndpointException(Items.class, "getItemByNameWithHttpInfo", Map.of("itemName", "x"), apiException);
    }
}
