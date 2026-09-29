package org.openhab.cli.runtime.command.config.properties;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.ArgumentCaptor;
import org.openhab.cli.runtime.service.Console;
import org.openhab.cli.runtime.service.PropertiesReader;
import picocli.CommandLine;

class ListCommandTest {
    @TempDir
    Path directory;

    private String list(Path file) {
        var console = mock(Console.class);
        assertEquals(
                0,
                new CommandLine(new ListCommand(new PropertiesReader(console), console))
                        .execute("-p", file.toString()));
        var lines = ArgumentCaptor.forClass(String.class);
        verify(console, atLeastOnce()).write(lines.capture());
        return String.join("\n", lines.getAllValues());
    }

    @Test
    void missingAndEmptyFilesStillListSupportedKeysAndDescriptions() throws Exception {
        var file = directory.resolve("client.properties");
        for (boolean exists : new boolean[] {false, true}) {
            if (exists) Files.writeString(file, "# empty\n");
            var text = list(file);
            assertTrue(text.contains(file.toString()));
            assertTrue(text.contains(exists ? "No properties are stored" : "does not exist"));
            assertTrue(text.matches("(?s).*\\| Property +\\| Value +\\| Description +\\|.*"));
            assertTrue(text.matches("(?s).*\\| auth.password +\\| null +\\| Basic authentication password.*"));
            assertTrue(text.matches("(?s).*\\| config.prettyPrint +\\| null +\\| .*default: true.*"));
            assertTrue(text.contains("config.rest.baseUrl"));
            assertTrue(text.contains("config.timeout.writeTimeout"));
        }
    }

    @Test
    void showsStoredAndCustomValuesAndEscapesMarkdownWithoutChangingFile() throws Exception {
        var file = directory.resolve("custom.properties");
        var contents = "config.rest.baseUrl=http://localhost:8080\nauth.username=\ncustom=a|b\\n<c> & *value*\n";
        Files.writeString(file, contents);
        var text = list(file);
        assertTrue(text.contains("http://localhost:8080"));
        assertTrue(text.matches("(?s).*\\| auth.username +\\| \"\" +\\|.*"));
        assertTrue(text.contains("a&#124;b<br>&lt;c&gt; &amp; &#42;value&#42;"));
        assertTrue(text.contains("Custom property; no built-in description."));
        assertEquals(contents, Files.readString(file));
    }
}
