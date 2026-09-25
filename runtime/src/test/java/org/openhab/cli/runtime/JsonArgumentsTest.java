package org.openhab.cli.runtime;

import static org.junit.jupiter.api.Assertions.*;

import com.google.gson.reflect.TypeToken;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.openhab.cli.client.model.GroupItem;

class JsonArgumentsTest {
    @Test
    void decodesModelCollectionsAndNestedMaps() {
        List<GroupItem> items = JsonArguments.parse(
                "[{\"name\":\"Lamp\",\"type\":\"Switch\"}]", new TypeToken<List<GroupItem>>() {}.getType(), "items");
        assertEquals("Lamp", items.getFirst().getName());
        Map<String, Object> values = JsonArguments.parse(
                "{\"values\":[1,true,\"text\"]}", new TypeToken<Map<String, Object>>() {}.getType(), "requestBody");
        assertEquals(List.of(1.0, true, "text"), values.get("values"));
    }

    @Test
    void roundTripsBothRuleSerializationRequestVariants() {
        for (var json : List.of("[\"rule:test\"]", "{\"rules\":[]}", "{}")) {
            var value = JsonArguments.parse(json, org.openhab.cli.client.model.CanSerializeRulesRequest.class, "body");
            var serialized = com.google.gson.JsonParser.parseString(org.openhab.cli.client.JSON.serialize(value));
            if (json.startsWith("[")) {
                assertEquals(com.google.gson.JsonParser.parseString(json), serialized);
            } else {
                assertTrue(serialized.isJsonObject());
                assertEquals(
                        0, serialized.getAsJsonObject().getAsJsonArray("rules").size());
            }
        }
        assertThrows(
                IllegalArgumentException.class,
                () -> JsonArguments.parse("[42]", org.openhab.cli.client.model.CanSerializeRulesRequest.class, "body"));
    }

    @Test
    void rejectsMalformedJsonAndPreservesOmittedValues() {
        assertNull(JsonArguments.parse(null, GroupItem.class, "item"));
        var exception =
                assertThrows(IllegalArgumentException.class, () -> JsonArguments.parse("{", GroupItem.class, "item"));
        assertEquals("Invalid JSON for argument item", exception.getMessage());
    }
}
