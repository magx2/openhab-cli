package org.openhab.cli.runtime;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import org.openhab.cli.client.model.CanSerializeRulesRequest;
import org.openhab.cli.client.model.FileFormat;
import org.openhab.cli.client.model.StringList;

/** Handles the array/object union whose generated array-alias adapter expects an object. */
final class RuleSerializationRequestAdapter
        implements JsonSerializer<CanSerializeRulesRequest>, JsonDeserializer<CanSerializeRulesRequest> {
    @Override
    public CanSerializeRulesRequest deserialize(JsonElement json, Type type, JsonDeserializationContext context) {
        if (json.isJsonArray()) {
            var ids = new StringList();
            for (var value : json.getAsJsonArray()) {
                if (!value.isJsonPrimitive() || !value.getAsJsonPrimitive().isString()) {
                    throw new JsonParseException("Rule IDs must be JSON strings");
                }
                ids.add(value.getAsString());
            }
            return new CanSerializeRulesRequest(ids);
        }
        if (json.isJsonObject()) {
            return new CanSerializeRulesRequest(context.<FileFormat>deserialize(json, FileFormat.class));
        }
        throw new JsonParseException("Expected a rule-ID array or a file-format object");
    }

    @Override
    public JsonElement serialize(CanSerializeRulesRequest value, Type type, JsonSerializationContext context) {
        if (value.getActualInstance() instanceof StringList ids) {
            var array = new JsonArray();
            ids.forEach(array::add);
            return array;
        }
        return context.serialize(value.getActualInstance(), FileFormat.class);
    }
}
