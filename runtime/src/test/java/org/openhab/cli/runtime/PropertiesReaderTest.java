package org.openhab.cli.runtime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.openhab.cli.runtime.service.Console;
import org.openhab.cli.runtime.service.PropertiesReader;

class PropertiesReaderTest {
    @TempDir
    Path directory;

    @Test
    void clearsPropertiesAndPreservesOtherValues() throws IOException {
        var file = directory.resolve("client.properties");
        Files.writeString(file, "auth.password=secret\ncustom.setting=keep\\nthis\n");
        var reader = new PropertiesReader(new Console());

        assertEquals(0, reader.clear(file.toString(), "auth.password"));
        assertNull(reader.get(file.toString(), "auth.password"));
        assertEquals("keep\nthis", reader.get(file.toString(), "custom.setting"));
        assertEquals(0, reader.clear(file.toString(), "missing"));
        assertEquals(0, reader.set(file.toString(), "custom.setting", null));
        assertNull(reader.get(file.toString(), "custom.setting"));
    }

    @Test
    void readsSingleValuesWithoutChangingTheFile() throws IOException {
        var file = directory.resolve("client.properties");
        var contents = "custom.setting=za\\u017c\\u00f3\\u0142\\u0107\\nline\nempty=\n";
        Files.writeString(file, contents);
        var reader = new PropertiesReader(new Console());

        assertEquals("zażółć\nline", reader.get(file.toString(), "custom.setting"));
        assertEquals("", reader.get(file.toString(), "empty"));
        assertNull(reader.get(file.toString(), "missing"));
        assertEquals(contents, Files.readString(file));
    }

    @Test
    void handlesMissingFilesAndReadFailuresForSingleProperties() {
        var file = directory.resolve("missing.properties");
        var reader = new PropertiesReader(mock(Console.class));

        assertNull(reader.get(file.toString(), "key"));
        assertEquals(ExitCodeMapper.IO_EXCEPTION_EXIT_CODE, reader.clear(file.toString(), "key"));
        assertFalse(Files.exists(file));
        assertThrows(UncheckedIOException.class, () -> reader.get(directory.toString(), "key"));
    }

    @Test
    void addsAndReplacesPropertiesWithoutLosingOtherValues() throws IOException {
        var file = directory.resolve("client.properties");
        Files.writeString(file, "config.rest.baseUrl=http://localhost:8080\ncustom.setting=keep\\nthis\n");
        var reader = new PropertiesReader(new Console());

        assertEquals(0, reader.set(file.toString(), "config.rest.baseUrl", "https://openhab.example"));
        assertEquals(0, reader.set(file.toString(), "auth.password", " leading = : \\ newline\nzażółć"));

        var saved = new java.util.Properties();
        try (var input = Files.newInputStream(file)) {
            saved.load(input);
        }
        assertEquals(3, saved.size());
        assertEquals("https://openhab.example", saved.getProperty("config.rest.baseUrl"));
        assertEquals("keep\nthis", saved.getProperty("custom.setting"));
        assertEquals(" leading = : \\ newline\nzażółć", saved.getProperty("auth.password"));
    }

    @Test
    void updatesDefaultFileInWorkingDirectory() throws IOException {
        var file = directory.resolve(PropertiesReader.PROPERTIES_FILE_NAME);
        Files.writeString(file, "config.prettyPrint=true\n");
        var previousDirectory = System.getProperty("user.dir");
        try {
            System.setProperty("user.dir", directory.toString());
            var reader = new PropertiesReader(new Console());
            assertEquals(0, reader.set(null, "config.prettyPrint", "false"));
            assertFalse(reader.read(null).prettyPrint());
            assertEquals("false", reader.get(null, "config.prettyPrint"));
            assertEquals(0, reader.clear(null, "config.prettyPrint"));
            assertNull(reader.get(null, "config.prettyPrint"));
        } finally {
            System.setProperty("user.dir", previousDirectory);
        }
    }

    @Test
    void reportsMissingFileWithoutCreatingIt() {
        var file = directory.resolve("missing.properties");
        var console = mock(Console.class);

        assertEquals(
                ExitCodeMapper.IO_EXCEPTION_EXIT_CODE,
                new PropertiesReader(console).set(file.toString(), "key", "value"));

        assertFalse(Files.exists(file));
        verify(console).writeError("Properties file `%s` does not exist", file);
    }

    @Test
    void reportsReadFailureWithoutReplacingThePath() {
        var console = mock(Console.class);

        assertEquals(
                ExitCodeMapper.IO_EXCEPTION_EXIT_CODE,
                new PropertiesReader(console).set(directory.toString(), "key", "value"));

        assertTrue(Files.isDirectory(directory));
        verify(console).writeError(eq("Cannot update properties file `%s`: %s"), eq(directory), anyString());
    }

    @Test
    void readsGroupedConnectionSettings() throws IOException {
        var file = directory.resolve("client.properties");
        Files.writeString(file, """
                config.rest.baseUrl=https://openhab.example
                config.rest.basePath=/api
                config.rest.apiClientDebugging=true
                config.ssl.verifyingSsl=false
                config.ssl.sslCaCertPath=ca.pem
                config.ssl.tlsServerName=openhab.example
                config.timeout.connectTimeout=123
                config.timeout.readTimeout=456
                config.timeout.writeTimeout=789
                config.prettyPrint=false
                auth.oAuthToken=token
                """);
        var properties = new PropertiesReader(new Console()).read(file.toString());
        assertEquals("https://openhab.example/api", properties.apiBaseUrl());
        assertTrue(properties.apiClientDebugging());
        assertFalse(properties.verifyingSsl());
        assertEquals(Path.of("ca.pem"), properties.sslCaCertPath());
        assertEquals("openhab.example", properties.tlsServerName());
        assertEquals(123, properties.connectTimeout());
        assertEquals(456, properties.readTimeout());
        assertEquals(789, properties.writeTimeout());
        assertFalse(properties.prettyPrint());
        assertEquals("token", properties.oAuthToken());
    }

    @Test
    void readsInlineCertificateFromSslGroup() throws IOException {
        var file = directory.resolve("client.properties");
        Files.writeString(file, "config.ssl.sslCaCert=certificate\n");
        assertEquals(
                "certificate",
                new PropertiesReader(new Console()).read(file.toString()).sslCaCert());
    }
}
