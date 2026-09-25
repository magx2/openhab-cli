package org.openhab.cli.runtime;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.openhab.cli.runtime.service.PropertiesReader;

class PropertiesReaderTest {
    @TempDir
    Path directory;

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
        var properties = new PropertiesReader().read(file.toString());
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
        assertEquals("certificate", new PropertiesReader().read(file.toString()).sslCaCert());
    }
}
