package org.openhab.cli.runtime.command.config.properties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.openhab.cli.runtime.service.Console;
import org.openhab.cli.runtime.service.PropertiesReader;
import picocli.CommandLine;

class GetCommandTest {
    @Test
    void printsStoredEmptyAndMissingValues(@TempDir Path directory) throws IOException {
        var file = directory.resolve("client.properties");
        Files.writeString(file, "custom.key=a=b c\nempty=\n");
        var console = mock(Console.class);
        var reader = new PropertiesReader(console);
        for (var key : new String[] {"custom.key", "empty", "missing"}) {
            assertEquals(0, new CommandLine(new GetCommand(reader, console)).execute("--properties-file=" + file, key));
        }
        verify(console).write("custom.key=a=b c");
        verify(console).write("empty=");
        verify(console).write("missing=null");
        verifyNoMoreInteractions(console);
    }
}
