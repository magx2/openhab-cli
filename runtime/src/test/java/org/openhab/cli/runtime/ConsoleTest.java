package org.openhab.cli.runtime;

import static org.junit.jupiter.api.Assertions.*;

import com.google.gson.JsonParser;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.openhab.cli.runtime.service.Console;

class ConsoleTest {
    @Test
    void printsCompactJsonWithEscapedValues() {
        var output = capture(Map.of("message", "hello\n\"world\""), false);
        assertEquals("{\"message\":\"hello\\n\\\"world\\\"\"}" + System.lineSeparator(), output);
    }

    @Test
    void prettyPrintingPreservesDataWithoutChangingCompactSerialization() {
        var value = Map.of("nested", Map.of("enabled", true));
        var pretty = capture(value, true);
        var compact = capture(value, false);
        assertTrue(pretty.contains("\n  \"nested\""));
        assertEquals(JsonParser.parseString(compact), JsonParser.parseString(pretty));
        assertEquals("{\"nested\":{\"enabled\":true}}" + System.lineSeparator(), compact);
    }

    @Test
    void printsJsonNull() {
        assertEquals("null" + System.lineSeparator(), capture(null, false));
        assertEquals("null" + System.lineSeparator(), capture(null, true));
    }

    private String capture(Object object, boolean prettyPrint) {
        var original = System.out;
        var bytes = new ByteArrayOutputStream();
        try (var output = new PrintStream(bytes, true, StandardCharsets.UTF_8)) {
            System.setOut(output);
            new Console().writeJson(object, prettyPrint);
        } finally {
            System.setOut(original);
        }
        return bytes.toString(StandardCharsets.UTF_8);
    }
}
