package org.openhab.cli.runtime.command.config.update;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.PosixFilePermission;
import java.time.Duration;
import java.util.Base64;
import java.util.Locale;
import java.util.Set;
import javax.inject.Inject;

/** Detects the running installation and replaces it without overwriting a running executable in place. */
public class UpdateInstaller {
    /** Release asset formats produced by the release workflow. */
    enum Kind {
        jar(".jar"),
        linux("-linux-x86_64"),
        windows("-windows-x86_64.exe");

        final String suffix;

        Kind(String suffix) {
            this.suffix = suffix;
        }
    }

    /** Resolved installation path, asset format and host platform. */
    record Target(Path path, Kind kind, boolean windows) {}

    /** Creates an installer for the current process. */
    @Inject
    public UpdateInstaller() {}

    /** Locates a released standalone JAR or GraalVM executable, resolving installation symlinks. */
    Target detectTarget() throws IOException {
        var nativeImage = "runtime".equals(System.getProperty("org.graalvm.nativeimage.imagecode"));
        Path location;
        if (nativeImage) {
            location = ProcessHandle.current()
                    .info()
                    .command()
                    .map(Path::of)
                    .orElseThrow(() -> new IOException("Cannot locate the running native executable"));
        } else {
            try {
                location = Path.of(SelfUpdater.class
                        .getProtectionDomain()
                        .getCodeSource()
                        .getLocation()
                        .toURI());
            } catch (Exception e) {
                throw new IOException("Cannot locate the running JAR", e);
            }
        }
        return detectTarget(location, nativeImage, System.getProperty("os.name"), System.getProperty("os.arch"));
    }

    static Target detectTarget(Path location, boolean nativeImage, String osName, String architecture)
            throws IOException {
        var os = osName.toLowerCase(Locale.ROOT);
        boolean windows = os.startsWith("windows");
        if (!windows && !os.startsWith("linux")) {
            throw new IOException("Self-update supports Linux and Windows only");
        }
        if (!Files.isRegularFile(location)) {
            throw new IOException(
                    "Self-update requires a released standalone JAR or native executable, not a development classpath");
        }
        if (nativeImage) {
            if (!Set.of("amd64", "x86_64").contains(architecture.toLowerCase(Locale.ROOT))) {
                throw new IOException("Native releases are available for x86_64 only");
            }
            return new Target(location.toRealPath(), windows ? Kind.windows : Kind.linux, windows);
        }
        if (!location.getFileName().toString().toLowerCase(Locale.ROOT).endsWith(".jar")) {
            throw new IOException("Self-update requires a standalone JAR");
        }
        SelfUpdater.validate(location, Kind.jar);
        return new Target(location.toRealPath(), Kind.jar, windows);
    }

    /** Installs on Linux immediately, or prepares a Windows helper that finishes after this process exits. */
    boolean install(Target target, Path source) throws IOException, InterruptedException {
        if (target.windows()) {
            launchWindows(target.path(), source);
            return true;
        }
        replaceLinux(target.path(), source);
        return false;
    }

    private void replaceLinux(Path target, Path source) throws IOException, InterruptedException {
        var permissions = Files.getPosixFilePermissions(target);
        Path staged = null;
        try {
            staged = Files.createTempFile(target.getParent(), ".oh-update-", ".tmp");
            Files.copy(source, staged, StandardCopyOption.REPLACE_EXISTING);
            Files.setPosixFilePermissions(staged, permissions);
            Files.move(staged, target, StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING);
        } catch (AccessDeniedException e) {
            // Only the final installation is elevated; sudo owns the password prompt and terminal input.
            runElevated(target, source, permissions);
        } finally {
            if (staged != null) Files.deleteIfExists(staged);
        }
    }

    void runElevated(Path target, Path source, Set<PosixFilePermission> permissions)
            throws IOException, InterruptedException {
        var process = new ProcessBuilder(
                        "sudo",
                        "--",
                        "sh",
                        "-c",
                        linuxScript(),
                        "oh-update",
                        target.toString(),
                        source.toString(),
                        permissionMode(permissions))
                .inheritIO()
                .start();
        if (process.waitFor() != 0) {
            throw new IOException("sudo could not install the update; the existing installation was preserved");
        }
    }

    static String permissionMode(Set<PosixFilePermission> permissions) {
        int mode = 0;
        for (var permission : permissions) mode |= 1 << (8 - permission.ordinal());
        return Integer.toOctalString(mode);
    }

    static String linuxScript() {
        return """
                set -eu
                target=$1
                source=$2
                mode=$3
                staged=$(mktemp "${target}.update.XXXXXX")
                trap 'rm -f -- "$staged"' EXIT HUP INT TERM
                install -m "$mode" -- "$source" "$staged"
                mv -f -- "$staged" "$target"
                """;
    }

    private void launchWindows(Path target, Path source) throws IOException, InterruptedException {
        var status = source.getParent().resolve("status.txt");
        var script = windowsScript(
                target.toString(),
                source.toString(),
                status.toString(),
                ProcessHandle.current().pid(),
                true);
        var process = new ProcessBuilder(
                        "powershell.exe",
                        "-NoProfile",
                        "-NonInteractive",
                        "-ExecutionPolicy",
                        "Bypass",
                        "-EncodedCommand",
                        encodeCommand(script))
                .redirectOutput(source.getParent().resolve("helper.log").toFile())
                .redirectErrorStream(true)
                .start();
        var deadline = System.nanoTime() + Duration.ofMinutes(2).toNanos();
        try {
            while (System.nanoTime() < deadline) {
                if (Files.exists(status)) {
                    var result =
                            Files.readString(status, StandardCharsets.UTF_8).strip();
                    if (result.startsWith("READY")) return;
                    if (result.startsWith("ERROR")) throw new IOException(result);
                }
                // The initial helper may exit after launching an elevated child; the status file is authoritative.
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            Files.writeString(status, "CANCELLED");
            process.destroy();
            throw e;
        }
        Files.writeString(status, "CANCELLED");
        process.destroy();
        throw new IOException("Windows update helper did not become ready. See "
                + source.getParent().resolve("helper.log"));
    }

    static String encodeCommand(String script) {
        return Base64.getEncoder().encodeToString(script.getBytes(StandardCharsets.UTF_16LE));
    }

    private static String encodedPath(String path) {
        return "[Text.Encoding]::UTF8.GetString([Convert]::FromBase64String('"
                + Base64.getEncoder().encodeToString(path.getBytes(StandardCharsets.UTF_8)) + "'))";
    }

    /** Builds a helper with literal paths encoded as data, including spaces, quotes and shell metacharacters. */
    static String windowsScript(String target, String source, String status, long parentPid, boolean allowElevation) {
        var elevated = allowElevation ? encodeCommand(windowsScript(target, source, status, parentPid, false)) : "";
        return """
                $ErrorActionPreference = 'Stop'
                $ProgressPreference = 'SilentlyContinue'
                $target = %s
                $source = %s
                $status = %s
                $staged = $target + '.update.' + [Guid]::NewGuid().ToString('N')
                function Status([string]$text) {
                    [IO.File]::WriteAllText($status, $text, (New-Object Text.UTF8Encoding $false))
                }
                try {
                    try {
                        [IO.File]::Copy($source, $staged, $false)
                    } catch [UnauthorizedAccessException] {
                        if (%s) {
                            Start-Process -FilePath "$PSHOME\\powershell.exe" -Verb RunAs -ArgumentList '-NoProfile -NonInteractive -ExecutionPolicy Bypass -EncodedCommand %s' | Out-Null
                            exit 0
                        }
                        throw
                    }
                    if ([IO.File]::Exists($status) -and [IO.File]::ReadAllText($status) -eq 'CANCELLED') { exit 1 }
                    Status 'READY'
                    Wait-Process -Id %d -ErrorAction SilentlyContinue
                    if ([IO.File]::ReadAllText($status) -ne 'READY') { exit 1 }
                    $deadline = [DateTime]::UtcNow.AddMinutes(2)
                    while ($true) {
                        try {
                            [IO.File]::Replace($staged, $target, [NullString]::Value)
                            break
                        } catch [IO.IOException] {
                            if ([DateTime]::UtcNow -ge $deadline) { throw }
                            Start-Sleep -Milliseconds 200
                        }
                    }
                    [IO.File]::Delete($source)
                    Status 'DONE'
                } catch {
                    Status ('ERROR: ' + $_.Exception.Message)
                    exit 1
                } finally {
                    if ([IO.File]::Exists($staged)) { [IO.File]::Delete($staged) }
                }
                """.formatted(
                        encodedPath(target),
                        encodedPath(source),
                        encodedPath(status),
                        allowElevation ? "$true" : "$false",
                        elevated,
                        parentPid);
    }
}
