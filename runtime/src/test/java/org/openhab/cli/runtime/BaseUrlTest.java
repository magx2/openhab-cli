package org.openhab.cli.runtime;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import org.openhab.cli.runtime.service.PropertiesReader;
import picocli.CommandLine;

class BaseUrlTest {
    @TempDir
    Path directory;

    @Test
    void requiresBaseUrlWhenNeitherSourceSuppliesIt() {
        var error = assertThrows(IllegalArgumentException.class, () -> build(""));
        assertTrue(error.getMessage().contains("baseUrl is required"));
    }

    @Test
    void acceptsFileValueAndAppendsDefaultRestPath() throws IOException {
        assertEquals("https://openhab.example/rest", build("config.rest.baseUrl=https://openhab.example/\n"));
    }

    @Test
    void cliOverridesFileAndPreservesServerPathPrefix() throws IOException {
        assertEquals(
                "http://localhost:8080/openhab/api",
                build(
                        "config.rest.baseUrl=https://openhab.example\n",
                        "--base-url=http://localhost:8080/openhab/",
                        "--base-path=/api"));
    }

    @Test
    void cliSuppliesUrlWhenPropertiesFileDoesNotExist() {
        var options = new Options();
        new CommandLine(options)
                .parseArgs("--properties-file=" + directory.resolve("missing"), "--base-url=http://localhost:8080");
        assertEquals(
                "http://localhost:8080/rest",
                new ApiClientBuilder(new PropertiesReader(new Console()))
                        .build(options)
                        .toNative()
                        .getBasePath());
    }

    @Test
    void rejectsBlankRelativeAndNonHttpUrls() {
        for (var url : new String[] {"", " ", "/rest", "ftp://host", "https://host?query=value"}) {
            assertThrows(IllegalArgumentException.class, () -> build("", "--base-url=" + url));
        }
    }

    private String build(String fileContents, String... flags) throws IOException {
        var file = directory.resolve("client.properties");
        Files.writeString(file, fileContents);
        var args = new java.util.ArrayList<String>();
        args.add("--properties-file=" + file);
        args.addAll(java.util.List.of(flags));
        var options = new Options();
        new CommandLine(options).parseArgs(args.toArray(String[]::new));
        return new ApiClientBuilder(new PropertiesReader(new Console()))
                .build(options)
                .toNative()
                .getBasePath();
    }
}
