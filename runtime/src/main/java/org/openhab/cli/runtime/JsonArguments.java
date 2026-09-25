package org.openhab.cli.runtime;

import java.lang.reflect.Type;
import org.openhab.cli.client.JSON;
import org.openhab.cli.client.model.CanSerializeRulesRequest;

/** Decodes structured command arguments using the API client's JSON adapters. */
public final class JsonArguments {
    static {
        // Keep the correction in the shared serializer so request bodies use the same representation.
        JSON.setGson(JSON.getGson()
                .newBuilder()
                .registerTypeAdapter(CanSerializeRulesRequest.class, new RuleSerializationRequestAdapter())
                .create());
    }

    private JsonArguments() {}

    /** Returns null for an omitted argument, otherwise decodes its JSON value. */
    public static <T> T parse(String value, Type type, String name) {
        if (value == null) {
            return null;
        }
        try {
            return JSON.getGson().fromJson(value, type);
        } catch (RuntimeException e) {
            throw new IllegalArgumentException("Invalid JSON for argument " + name, e);
        }
    }
}
