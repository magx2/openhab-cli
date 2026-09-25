package org.openhab.cli.runtime.command.config.update;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.util.HexFormat;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.openhab.cli.runtime.ExitCodeMapper;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

class SelfUpdaterTest {
    @TempDir
    Path directory;

    @Test
    void comparesReleaseNumbersAndSnapshotsWithoutTheRestApiPrefix() {
        assertTrue(SelfUpdater.compareVersions("0.9.0", "0.10.0") < 0);
        assertTrue(SelfUpdater.compareVersions("0.1.0-SNAPSHOT", "0.1.0") < 0);
        assertTrue(SelfUpdater.compareVersions("0.2.0-SNAPSHOT", "0.1.0") > 0);
        assertEquals(0, SelfUpdater.compareVersions("1.0.0", "1.0.0"));
        assertEquals("0.1.0", SelfUpdater.normalizeVersion("v0.1.0"));
        assertThrows(IllegalArgumentException.class, () -> SelfUpdater.normalizeVersion("../../latest"));
    }

    @Test
    void checkSelectsMatchingAssetsWithoutDownloadingOrInstalling() throws Exception {
        for (var kind : UpdateInstaller.Kind.values()) {
            var releases = mock(ReleaseClient.class);
            var installer = mock(UpdateInstaller.class);
            var console = mock(Console.class);
            when(releases.release(null)).thenReturn(release("999.0.0"));
            when(installer.detectTarget())
                    .thenReturn(new UpdateInstaller.Target(
                            directory.resolve("oh"), kind, kind == UpdateInstaller.Kind.windows));
            new SelfUpdater(console, releases, installer).execute(true, null, false);
            verify(releases, never()).download(any(), any());
            verify(installer, never()).install(any(), any());
            verify(console).write(contains("oh-999.0.0" + kind.suffix));
        }
    }

    @Test
    void refusesDowngradeUnlessForced() throws Exception {
        var releases = mock(ReleaseClient.class);
        var installer = mock(UpdateInstaller.class);
        var console = mock(Console.class);
        when(releases.release(null)).thenReturn(release("0.0.0"));
        var updater = new SelfUpdater(console, releases, installer);
        updater.execute(false, null, false);
        verifyNoInteractions(installer);
        when(installer.detectTarget())
                .thenReturn(new UpdateInstaller.Target(directory.resolve("oh.jar"), UpdateInstaller.Kind.jar, false));
        updater.execute(true, null, true);
        verify(installer).detectTarget();
    }

    @Test
    void invalidDownloadNeverTouchesTheInstallation() throws Exception {
        var releases = mock(ReleaseClient.class);
        var installer = mock(UpdateInstaller.class);
        when(releases.release(null)).thenReturn(release("999.0.0"));
        when(installer.detectTarget())
                .thenReturn(new UpdateInstaller.Target(directory.resolve("oh.jar"), UpdateInstaller.Kind.jar, false));
        doAnswer(invocation -> {
                    Files.writeString(invocation.getArgument(1), "not a JAR");
                    return null;
                })
                .when(releases)
                .download(any(), any());
        assertThrows(IOException.class, () -> new SelfUpdater(mock(Console.class), releases, installer)
                .execute(false, null, false));
        verify(installer, never()).install(any(), any());
    }

    @Test
    void downloadsAndInstallsJarThroughLocalHttpWithChecksumValidation() throws Exception {
        org.junit.jupiter.api.Assumptions.assumeTrue(
                System.getProperty("os.name").equals("Linux"));
        var payload = jar(directory.resolve("new.jar"));
        var bytes = Files.readAllBytes(payload);
        var sha = HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(bytes));
        var server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0);
        var origin = "http://127.0.0.1:" + server.getAddress().getPort();
        var metadata = release("999.0.0");
        var asset = metadata.getAsJsonArray("assets").get(0).getAsJsonObject();
        asset.addProperty("browser_download_url", origin + "/asset");
        asset.addProperty("size", bytes.length);
        asset.addProperty("digest", "sha256:" + sha);
        server.createContext("/latest", exchange -> {
            var response = metadata.toString().getBytes(java.nio.charset.StandardCharsets.UTF_8);
            exchange.sendResponseHeaders(200, response.length);
            try (var output = exchange.getResponseBody()) {
                output.write(response);
            }
        });
        server.createContext("/asset", exchange -> {
            exchange.sendResponseHeaders(200, bytes.length);
            try (var output = exchange.getResponseBody()) {
                output.write(bytes);
            }
        });
        server.start();
        try {
            var client = new ReleaseClient(URI.create(origin + "/"));
            var installer = spy(new UpdateInstaller());
            var target = directory.resolve("installed.jar");
            Files.writeString(target, "old installation");
            doReturn(new UpdateInstaller.Target(target, UpdateInstaller.Kind.jar, false))
                    .when(installer)
                    .detectTarget();
            new SelfUpdater(mock(Console.class), client, installer).execute(false, null, false);
            assertArrayEquals(bytes, Files.readAllBytes(target));
            asset.addProperty("digest", "sha256:" + "0".repeat(64));
            assertThrows(IOException.class, () -> client.download(asset, directory.resolve("bad.jar")));
            asset.addProperty("digest", "sha256:" + sha);
            asset.addProperty("size", bytes.length + 1);
            assertThrows(IOException.class, () -> client.download(asset, directory.resolve("short.jar")));
        } finally {
            server.stop(0);
        }
    }

    @Test
    void parsesActionsAndMapsFailures() throws Exception {
        var updater = mock(SelfUpdater.class);
        var command = new CommandLine(new UpdateCommand(updater, mock(Console.class)))
                .setCaseInsensitiveEnumValuesAllowed(true)
                .setExitCodeExceptionMapper(new ExitCodeMapper());
        assertEquals(0, command.execute());
        verify(updater).execute(true, null, false);
        assertEquals(0, command.execute("RUN", "--release=v1.0.0", "--force"));
        verify(updater).execute(false, "v1.0.0", true);
        doThrow(new IOException("download failed")).when(updater).execute(true, null, false);
        assertEquals(98, command.execute("check"));
    }

    @Test
    void commandReportsFailuresThroughConsoleAndPreservesInterruption() throws Exception {
        var updater = mock(SelfUpdater.class);
        var console = mock(Console.class);
        var command = new CommandLine(new UpdateCommand(updater, console));
        var invalid = new IllegalArgumentException("invalid release");
        doThrow(invalid).when(updater).execute(true, null, false);
        assertEquals(1, command.execute());
        verify(console).writeError("%s: %s", invalid, "Cannot update CLI", "invalid release");
        var interrupted = new InterruptedException("cancelled");
        doThrow(interrupted).when(updater).execute(true, null, false);
        try {
            assertEquals(1, command.execute());
            assertTrue(Thread.currentThread().isInterrupted());
            verify(console).writeError("%s: %s", interrupted, "CLI update interrupted", "cancelled");
        } finally {
            Thread.interrupted();
        }
    }

    static Path jar(Path path) throws IOException {
        var manifest = new Manifest();
        manifest.getMainAttributes().put(Attributes.Name.MANIFEST_VERSION, "1.0");
        manifest.getMainAttributes().put(Attributes.Name.MAIN_CLASS, "org.openhab.cli.runtime.Cli");
        try (var output = new JarOutputStream(Files.newOutputStream(path), manifest)) {
            output.putNextEntry(new JarEntry("org/openhab/cli/runtime/Cli.class"));
            output.write(new byte[] {0});
            output.closeEntry();
        }
        return path;
    }

    private static JsonObject release(String version) {
        return JsonParser.parseString("""
                {"tag_name":"v%s","draft":false,"prerelease":false,"assets":[
                {"name":"oh-%s.jar"},{"name":"oh-%s-linux-x86_64"},{"name":"oh-%s-windows-x86_64.exe"}]}
                """.formatted(version, version, version, version))
                .getAsJsonObject();
    }
}
