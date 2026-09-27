package org.openhab.cli.runtime;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.openhab.cli.runtime.service.Console;
import org.openhab.cli.runtime.service.PropertiesReader;

class PropertyLoggingTest {
    @TempDir
    Path directory;

    @Test
    void settingPropertiesDoesNotLogTheirValues() {
        var file = directory.resolve("client.properties");
        var bytes = new ByteArrayOutputStream();
        var original = System.out;
        try (var output = new PrintStream(bytes, true, StandardCharsets.UTF_8)) {
            System.setOut(output);
            for (var key : new String[] {"auth.oAuthToken", "auth.username", "auth.password", "custom.credential"}) {
                assertEquals(
                        0,
                        Cli.commandLine()
                                .execute("_config", "properties", "set", "-p", file.toString(), key, "private-value"));
            }
        } finally {
            System.setOut(original);
        }
        var diagnostic = bytes.toString(StandardCharsets.UTF_8);
        assertTrue(diagnostic.contains("Setting property auth.oAuthToken"), diagnostic);
        assertFalse(diagnostic.contains("private-value"), diagnostic);
        assertEquals("private-value", new PropertiesReader(new Console()).get(file.toString(), "auth.password"));
    }
}
