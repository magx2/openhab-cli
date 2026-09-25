package org.openhab.cli.runtime.command.config.update;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.jar.JarFile;
import javax.inject.Inject;
import org.openhab.cli.engine.Version;
import org.openhab.cli.runtime.service.Console;

/** Selects and validates a release before replacing the installed JAR or native executable. */
public class SelfUpdater {
    private final Console console;
    private final ReleaseClient releases;
    private final UpdateInstaller installer;

    /** Creates an updater with the output, GitHub and platform installation services. */
    @Inject
    public SelfUpdater(Console console, ReleaseClient releases, UpdateInstaller installer) {
        this.console = console;
        this.releases = releases;
        this.installer = installer;
    }

    /** Checks or installs a release; force permits reinstalling or downgrading the selected version. */
    public void execute(boolean checkOnly, String requested, boolean force) throws IOException, InterruptedException {
        var release = releases.release(requested);
        var version = normalizeVersion(release.get("tag_name").getAsString());
        // VERSION begins with the openHAB REST API version, which is not part of release tags.
        var current = Version.VERSION.substring(Version.VERSION.indexOf('.') + 1);
        if (!force && compareVersions(current, version) >= 0) {
            console.write("CLI " + current + " is already current (selected release: " + version + ").");
            return;
        }
        var target = installer.detectTarget();
        var name = "oh-" + version + target.kind().suffix;
        var asset = java.util.stream.StreamSupport.stream(
                        release.getAsJsonArray("assets").spliterator(), false)
                .map(element -> element.getAsJsonObject())
                .filter(element -> name.equals(element.get("name").getAsString()))
                .findFirst()
                .orElseThrow(() -> new IOException("Release v" + version + " does not contain " + name));
        if (checkOnly) {
            console.write("Update available: " + current + " -> " + version + " (" + name
                    + "). Run _config update run to install.");
            return;
        }
        var directory = Files.createTempDirectory("oh-update-");
        var download = directory.resolve(name);
        boolean deferred = false;
        try {
            console.write("Downloading " + name + "...");
            releases.download(asset, download);
            validate(download, target.kind());
            deferred = installer.install(target, download);
            console.write(
                    deferred
                            ? "Update to " + version
                                    + " prepared. Installation finishes after this process exits. Status: "
                                    + directory.resolve("status.txt")
                            : "Updated CLI to " + version + " at " + target.path());
        } finally {
            if (!deferred) {
                // Keep failed Windows helper diagnostics, if any.
                Files.deleteIfExists(download);
                try (var files = Files.list(directory)) {
                    if (files.findAny().isEmpty()) Files.deleteIfExists(directory);
                }
            }
        }
    }

    /** Accepts only stable release versions used by the release workflow. */
    static String normalizeVersion(String value) {
        var normalized = value.startsWith("v") ? value.substring(1) : value;
        if (!normalized.matches("(0|[1-9][0-9]*)\\.(0|[1-9][0-9]*)\\.(0|[1-9][0-9]*)")) {
            throw new IllegalArgumentException(
                    "Expected release version MAJOR.MINOR.PATCH, optionally prefixed with v");
        }
        return normalized;
    }

    /** Compares release versions numerically, placing a snapshot before its matching stable release. */
    static int compareVersions(String current, String target) {
        var snapshot = current.endsWith("-SNAPSHOT");
        var left = normalizeVersion(current.replaceFirst("-SNAPSHOT$", "")).split("\\.");
        var right = normalizeVersion(target).split("\\.");
        for (int i = 0; i < left.length; i++) {
            int comparison = new BigInteger(left[i]).compareTo(new BigInteger(right[i]));
            if (comparison != 0) return comparison;
        }
        return snapshot ? -1 : 0;
    }

    /** Rejects wrong file formats before the installed application is touched. */
    static void validate(Path path, UpdateInstaller.Kind kind) throws IOException {
        if (kind == UpdateInstaller.Kind.jar) {
            try (var jar = new JarFile(path.toFile())) {
                if (jar.getManifest() == null
                        || !"org.openhab.cli.runtime.Cli"
                                .equals(jar.getManifest().getMainAttributes().getValue("Main-Class"))
                        || jar.getEntry("org/openhab/cli/runtime/Cli.class") == null) {
                    throw new IOException("Downloaded JAR is not an executable openHAB CLI JAR");
                }
            }
        } else {
            try (var input = Files.newInputStream(path)) {
                var header = input.readNBytes(64);
                boolean valid = kind == UpdateInstaller.Kind.linux
                        ? header.length == 64
                                && header[0] == 0x7f
                                && header[1] == 'E'
                                && header[2] == 'L'
                                && header[3] == 'F'
                                && header[4] == 2
                                && header[5] == 1
                                && header[18] == 62
                                && header[19] == 0
                        : header.length == 64 && header[0] == 'M' && header[1] == 'Z';
                if (!valid) throw new IOException("Downloaded asset is not a matching native executable");
            }
        }
    }
}
