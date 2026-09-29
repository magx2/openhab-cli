package org.openhab.cli.runtime.command.config.properties;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.openhab.cli.runtime.service.Console;
import org.openhab.cli.runtime.service.PropertiesReader;
import picocli.CommandLine;

class ClearCommandTest {
    @Test
    void confirmsSuccessfulRemovalWithoutPrintingOldValue(@TempDir Path directory) throws Exception {
        var file = directory.resolve("client.properties");
        Files.writeString(file, "auth.password=secret-password\ncustom=keep\n");
        var console = mock(Console.class);
        var reader = new PropertiesReader(console);
        assertEquals(
                0, new CommandLine(new ClearCommand(reader, console)).execute("-p", file.toString(), "auth.password"));
        verify(console).write("Properties file: " + file);
        verify(console).write("Cleared property 'auth.password' (not set in this file).");
        verifyNoMoreInteractions(console);
        assertNull(reader.get(file.toString(), "auth.password"));
        assertEquals("keep", reader.get(file.toString(), "custom"));
    }

    @Test
    void failedClearDoesNotPrintSuccess(@TempDir Path directory) {
        var file = directory.resolve("missing.properties");
        var console = mock(Console.class);
        assertEquals(
                98,
                new CommandLine(new ClearCommand(new PropertiesReader(console), console))
                        .execute("-p", file.toString(), "auth.password"));
        verify(console, never()).write(anyString());
        assertFalse(Files.exists(file));
    }
}
