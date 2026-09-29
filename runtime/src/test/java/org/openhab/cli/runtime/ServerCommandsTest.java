package org.openhab.cli.runtime;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.openhab.cli.runtime.service.Console;
import org.openhab.cli.runtime.service.PropertiesReader;

class ServerCommandsTest {
    @TempDir
    Path directory;

    @Test
    void acceptsPositionalUrlAndRejectsAmbiguousInput() throws Exception {
        var file = directory.resolve("server.properties");
        var url = "https://oh.pixel.grzeslowski.pl";
        assertEquals(0, Cli.commandLine().execute("_config", "server", "set", url, "-p", file.toString()));
        assertEquals(url, new PropertiesReader(new Console()).get(file.toString(), "config.rest.baseUrl"));
        var contents = Files.readString(file);
        assertEquals(
                96,
                Cli.commandLine()
                        .execute(
                                "_config", "server", "set", url, "--base-url=http://localhost", "-p", file.toString()));
        assertEquals(contents, Files.readString(file));
        assertEquals(96, Cli.commandLine().execute("_config", "server", "set", "relative", "-p", file.toString()));
        assertEquals(contents, Files.readString(file));
    }

    @Test
    void savesAndClearsUrlWhilePreservingOtherProperties() throws Exception {
        var file = directory.resolve("custom.properties");
        Files.writeString(file, "auth.oAuthToken=keep-token\ncustom=keep-value\n");
        assertEquals(
                0,
                Cli.commandLine()
                        .execute(
                                "_config",
                                "server",
                                "set",
                                "-p",
                                file.toString(),
                                "--base-url=https://openhab.example/openhab"));
        var reader = new PropertiesReader(new Console());
        assertEquals("https://openhab.example/openhab", reader.get(file.toString(), "config.rest.baseUrl"));
        assertEquals(0, Cli.commandLine().execute("_config", "server", "clear", "-p", file.toString()));
        assertNull(reader.get(file.toString(), "config.rest.baseUrl"));
        assertEquals("keep-token", reader.get(file.toString(), "auth.oAuthToken"));
        assertEquals("keep-value", reader.get(file.toString(), "custom"));
    }

    @Test
    void promptsForUrlAndCreatesDefaultFile() throws Exception {
        var previousDirectory = System.getProperty("user.home");
        var previousInput = System.in;
        try {
            System.setProperty("user.home", directory.toString());
            System.setIn(new ByteArrayInputStream("http://localhost:8080\n".getBytes(StandardCharsets.UTF_8)));
            assertEquals(0, Cli.commandLine().execute("_config", "server", "set"));
            assertEquals("http://localhost:8080", new PropertiesReader(new Console()).get(null, "config.rest.baseUrl"));
        } finally {
            System.setProperty("user.home", previousDirectory);
            System.setIn(previousInput);
        }
    }

    @Test
    void rejectsInvalidUrlsWithoutChangingFile() throws Exception {
        var file = directory.resolve("custom.properties");
        var contents = "config.rest.baseUrl=http://localhost\n";
        Files.writeString(file, contents);
        for (var url : new String[] {"", "relative", "ftp://host", "http://host?query=value", "http://host#fragment"}) {
            assertEquals(
                    96,
                    Cli.commandLine().execute("_config", "server", "set", "-p", file.toString(), "--base-url=" + url));
            assertEquals(contents, Files.readString(file));
        }
    }

    @Test
    void clearMissingFileIsANoopAndWriteErrorsAreReported() {
        var missing = directory.resolve("missing.properties");
        assertEquals(0, Cli.commandLine().execute("_config", "server", "clear", "-p", missing.toString()));
        assertFalse(Files.exists(missing));
        assertEquals(
                98,
                Cli.commandLine()
                        .execute(
                                "_config", "server", "set", "-p", directory.toString(), "--base-url=http://localhost"));
    }
}
