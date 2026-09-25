package org.openhab.cli.runtime.command.config.update;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class UpdateInstallerTest {
    @TempDir
    Path directory;

    @Test
    void detectsJarAndRenamedNativeExecutablesAndRejectsDevelopmentDirectories() throws Exception {
        var jar = SelfUpdaterTest.jar(directory.resolve("my cli.jar"));
        assertEquals(
                UpdateInstaller.Kind.jar,
                UpdateInstaller.detectTarget(jar, false, "Windows 11", "aarch64")
                        .kind());
        assertEquals(
                UpdateInstaller.Kind.linux,
                UpdateInstaller.detectTarget(jar, true, "Linux", "amd64").kind());
        assertEquals(
                UpdateInstaller.Kind.windows,
                UpdateInstaller.detectTarget(jar, true, "Windows 11", "x86_64").kind());
        assertThrows(IOException.class, () -> UpdateInstaller.detectTarget(directory, false, "Linux", "amd64"));
        assertThrows(IOException.class, () -> UpdateInstaller.detectTarget(jar, true, "Linux", "aarch64"));
        assertThrows(IOException.class, () -> UpdateInstaller.detectTarget(jar, true, "Darwin", "amd64"));
    }

    @Test
    void replacesRunningLinuxExecutableThroughSymlinkAndPreservesPermissions() throws Exception {
        assumeTrue(System.getProperty("os.name").equals("Linux"));
        var target = directory.resolve("oh with spaces");
        Files.copy(Path.of("/bin/sleep"), target);
        var permissions = PosixFilePermissions.fromString("rwxr-x---");
        Files.setPosixFilePermissions(target, permissions);
        var link = directory.resolve("oh");
        Files.createSymbolicLink(link, target);
        var source = directory.resolve("replacement");
        Files.copy(Path.of("/bin/true"), source);
        var running = new ProcessBuilder(target.toString(), "30").start();
        try {
            var detected = UpdateInstaller.detectTarget(link, true, "Linux", "amd64");
            assertFalse(new UpdateInstaller().install(detected, source));
            assertTrue(running.isAlive());
            assertTrue(Files.isSymbolicLink(link));
            assertEquals(permissions, Files.getPosixFilePermissions(target));
            assertEquals(0, new ProcessBuilder(link.toString()).start().waitFor());
        } finally {
            running.destroyForcibly().waitFor();
        }
    }

    @Test
    void elevatedLinuxScriptHandlesLiteralPathsAndPreservesOldFileOnFailure() throws Exception {
        assumeTrue(System.getProperty("os.name").equals("Linux"));
        var target = directory.resolve("oh ' $() ; target");
        var source = directory.resolve("source ' ; $value");
        Files.writeString(target, "old");
        Files.writeString(source, "new");
        assertEquals(
                0,
                new ProcessBuilder(
                                "sh",
                                "-c",
                                UpdateInstaller.linuxScript(),
                                "oh-update",
                                target.toString(),
                                source.toString(),
                                "751")
                        .start()
                        .waitFor());
        assertEquals("new", Files.readString(target));
        assertEquals("751", UpdateInstaller.permissionMode(Files.getPosixFilePermissions(target)));
        assertNotEquals(
                0,
                new ProcessBuilder(
                                "sh",
                                "-c",
                                UpdateInstaller.linuxScript(),
                                "oh-update",
                                target.toString(),
                                directory.resolve("missing").toString(),
                                "751")
                        .start()
                        .waitFor());
        assertEquals("new", Files.readString(target));
        try (var files = Files.list(directory)) {
            assertEquals(2, files.count());
        }
    }

    @Test
    void permissionDeniedInvokesOnlyTheElevatedInstaller() throws Exception {
        assumeTrue(System.getProperty("os.name").equals("Linux"));
        var target = directory.resolve("oh");
        var source = Files.createTempFile("oh-test-update-", ".bin");
        Files.writeString(target, "old");
        var original = Files.getPosixFilePermissions(directory);
        var permissions = Files.getPosixFilePermissions(target);
        var installer = spy(new UpdateInstaller());
        doNothing().when(installer).runElevated(target, source, permissions);
        try {
            Files.setPosixFilePermissions(directory, PosixFilePermissions.fromString("r-x------"));
            assumeTrue(!Files.isWritable(directory), "Requires an unprivileged test process");
            installer.install(new UpdateInstaller.Target(target, UpdateInstaller.Kind.linux, false), source);
            verify(installer).runElevated(target, source, permissions);
            assertEquals("old", Files.readString(target));
        } finally {
            Files.setPosixFilePermissions(directory, original);
            Files.deleteIfExists(source);
        }
    }

    @Test
    void windowsHelperReplacesARunningExecutable() throws Exception {
        assumeTrue(System.getProperty("os.name").startsWith("Windows"));
        var system = Path.of(System.getenv("SystemRoot"), "System32");
        var target = directory.resolve("oh custom.exe");
        var source = directory.resolve("replacement.exe");
        var status = directory.resolve("native-status.txt");
        Files.copy(system.resolve("ping.exe"), target);
        Files.copy(system.resolve("where.exe"), source);
        var parent = new ProcessBuilder(target.toString(), "-n", "4", "127.0.0.1").start();
        var helper = new ProcessBuilder(
                        "powershell.exe",
                        "-NoProfile",
                        "-EncodedCommand",
                        UpdateInstaller.encodeCommand(UpdateInstaller.windowsScript(
                                target.toString(), source.toString(), status.toString(), parent.pid(), true)))
                .inheritIO()
                .start();
        try {
            assertTrue(helper.waitFor(30, TimeUnit.SECONDS));
            assertEquals(0, helper.exitValue(), Files.readString(status));
            assertEquals("DONE", Files.readString(status));
            assertArrayEquals(Files.readAllBytes(system.resolve("where.exe")), Files.readAllBytes(target));
            assertEquals(
                    0,
                    new ProcessBuilder(target.toString(), "/?")
                            .redirectOutput(ProcessBuilder.Redirect.DISCARD)
                            .start()
                            .waitFor());
        } finally {
            parent.destroyForcibly();
            helper.destroyForcibly();
        }
    }

    @Test
    void windowsHelperWaitsForExitAndReplacesLockedFile() throws Exception {
        assumeTrue(System.getProperty("os.name").startsWith("Windows"));
        var target = directory.resolve("oh ' special $.jar");
        var source = directory.resolve("new file.jar");
        var status = directory.resolve("status.txt");
        Files.writeString(target, "old");
        Files.writeString(source, "new");
        var lockScript = "$f = [IO.File]::Open('" + target.toString().replace("'", "''")
                + "', 'Open', 'Read', 'None'); Start-Sleep -Seconds 4; $f.Dispose()";
        var parent = new ProcessBuilder(
                        "powershell.exe", "-NoProfile", "-EncodedCommand", UpdateInstaller.encodeCommand(lockScript))
                .start();
        var helper = new ProcessBuilder(
                        "powershell.exe",
                        "-NoProfile",
                        "-EncodedCommand",
                        UpdateInstaller.encodeCommand(UpdateInstaller.windowsScript(
                                target.toString(), source.toString(), status.toString(), parent.pid(), true)))
                .inheritIO()
                .start();
        try {
            assertTrue(helper.waitFor(30, TimeUnit.SECONDS));
            assertEquals(0, helper.exitValue(), () -> {
                try {
                    return Files.readString(status);
                } catch (IOException e) {
                    return e.toString();
                }
            });
            assertEquals("DONE", Files.readString(status));
            assertEquals("new", Files.readString(target));
            assertFalse(Files.exists(source));
        } finally {
            parent.destroyForcibly();
            helper.destroyForcibly();
        }
    }
}
