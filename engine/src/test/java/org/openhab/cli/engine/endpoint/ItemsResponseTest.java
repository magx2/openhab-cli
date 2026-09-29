package org.openhab.cli.engine.endpoint;

import static org.junit.jupiter.api.Assertions.*;

import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import org.junit.jupiter.api.Test;
import org.openhab.cli.client.api.ItemsApi;

class ItemsResponseTest {
    @Test
    void decodesItemsGroupsFilteredObjectsAndEmptyLists() throws Exception {
        var body = new AtomicReference<String>();
        var server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0);
        server.createContext("/items", exchange -> {
            var bytes = body.get().getBytes(StandardCharsets.UTF_8);
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(200, bytes.length);
            try (var output = exchange.getResponseBody()) {
                output.write(bytes);
            }
        });
        server.start();
        try {
            var client = new org.openhab.cli.client.ApiClient();
            client.setBasePath("http://127.0.0.1:" + server.getAddress().getPort());
            var endpoint = new Items(new ItemsApi(client));
            body.set("""
                    [{"name":"Lamp","type":"Switch","state":"ON","editable":true,
                      "metadata":{"room":{"value":"Kitchen"}}},
                     {"name":"Lights","type":"Group","state":"ON","editable":false,
                      "members":[{"name":"Lamp","state":"ON"}],"groupType":"Switch"}]
                    """);
            var items = endpoint.items(null, null, null, null, null, null, null, null);
            assertEquals(2, items.size());
            assertEquals("Lamp", items.getFirst().get("name"));
            assertEquals(
                    Map.of("room", Map.of("value", "Kitchen")), items.getFirst().get("metadata"));
            assertEquals(
                    List.of(Map.of("name", "Lamp", "state", "ON")), items.get(1).get("members"));
            assertEquals("Switch", items.get(1).get("groupType"));
            body.set("[{\"name\":\"Lamp\"}]");
            assertEquals(
                    List.of(Map.of("name", "Lamp")), endpoint.items(null, null, null, null, null, null, "name", null));
            body.set("[]");
            assertTrue(endpoint.items(null, null, null, null, null, null, null, null)
                    .isEmpty());
        } finally {
            server.stop(0);
        }
    }
}
