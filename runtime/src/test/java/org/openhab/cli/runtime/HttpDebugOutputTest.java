package org.openhab.cli.runtime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicReference;
import okhttp3.Credentials;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.openhab.cli.engine.endpoint.Addons;
import org.openhab.cli.engine.properties.Properties;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import org.openhab.cli.runtime.service.PropertiesReader;
import picocli.CommandLine;

class HttpDebugOutputTest {
    @Test
    void builderSendsUsernameAndPasswordAsBasicAuthentication() throws Exception {
        var server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0);
        var authorization = new AtomicReference<String>();
        server.createContext("/rest/addons/services", exchange -> {
            authorization.set(exchange.getRequestHeaders().getFirst("Authorization"));
            var body = "[]".getBytes(StandardCharsets.UTF_8);
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(200, body.length);
            try (var output = exchange.getResponseBody()) {
                output.write(body);
            }
        });
        server.start();
        try {
            var console = mock(Console.class);
            var reader = mock(PropertiesReader.class);
            when(reader.read(null)).thenReturn(Properties.DEFAULT);
            var options = new Options();
            new CommandLine(options)
                    .parseArgs(
                            "--base-url=http://127.0.0.1:" + server.getAddress().getPort(),
                            "--username=test-user",
                            "--password=test-password");

            assertTrue(new Addons(new ApiClientBuilder(reader, console).build(options))
                    .addonTypes()
                    .isEmpty());
            assertEquals(Credentials.basic("test-user", "test-password"), authorization.get());
        } finally {
            server.stop(0);
        }
    }

    @Test
    void builderRoutesRedactedHttpLogsToConsoleOnlyWhenEnabled() throws Exception {
        var server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0);
        var authorization = new AtomicReference<String>();
        server.createContext("/rest/addons/services", exchange -> {
            authorization.set(exchange.getRequestHeaders().getFirst("Authorization"));
            var body = "[]".getBytes(StandardCharsets.UTF_8);
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.getResponseHeaders().set("X-Progress", "100%");
            exchange.sendResponseHeaders(200, body.length);
            try (var output = exchange.getResponseBody()) {
                output.write(body);
            }
        });
        server.start();
        try {
            for (boolean debug : new boolean[] {false, true}) {
                var console = mock(Console.class);
                var reader = mock(PropertiesReader.class);
                when(reader.read(null)).thenReturn(Properties.DEFAULT);
                var options = new Options();
                new CommandLine(options)
                        .parseArgs(
                                "--base-url=http://127.0.0.1:"
                                        + server.getAddress().getPort(),
                                "--oauth-token=test-secret-token",
                                "--api-client-debugging=" + debug);
                assertTrue(new Addons(new ApiClientBuilder(reader, console).build(options))
                        .addonTypes()
                        .isEmpty());
                assertEquals("Bearer test-secret-token", authorization.get());
                if (debug) {
                    var lines = ArgumentCaptor.forClass(String.class);
                    verify(console, atLeastOnce()).writeDebug(eq("%s"), lines.capture());
                    var output = String.join("\n", lines.getAllValues());
                    assertTrue(output.contains("100%"), output);
                    assertTrue(output.contains("[]"), output);
                    assertTrue(output.contains("Authorization"), output);
                    assertFalse(output.contains("test-secret-token"), output);
                } else {
                    verifyNoInteractions(console);
                }
            }
        } finally {
            server.stop(0);
        }
    }
}
