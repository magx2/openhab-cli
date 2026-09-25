package org.openhab.cli.runtime;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.PrintWriter;
import java.io.StringWriter;
import org.junit.jupiter.api.Test;
import org.openhab.cli.engine.Version;

class CliVersionTest {
    @Test
    void versionFlagsPrintEngineVersionAndExitSuccessfully() {
        for (var flag : new String[] {"--version", "-V"}) {
            var output = new StringWriter();
            var command = Cli.commandLine().setOut(new PrintWriter(output));

            assertEquals(0, command.execute(flag));
            assertEquals(Version.VERSION + System.lineSeparator(), output.toString());
        }
    }
}
