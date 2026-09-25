package org.openhab.cli.runtime.command.config.update;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import javax.inject.Inject;

/** Reads public release metadata and downloads assets without requiring the GitHub CLI. */
public class ReleaseClient {
    private final URI releases;

    /** Creates a client for the official openHAB CLI repository. */
    @Inject
    public ReleaseClient() {
        this(URI.create("https://api.github.com/repos/magx2/openhab-cli/releases/"));
    }

    ReleaseClient(URI releases) {
        this.releases = releases;
    }

    /** Loads the latest stable release, or an explicitly selected vMAJOR.MINOR.PATCH tag. */
    public JsonObject release(String version) throws IOException {
        var endpoint = version == null ? "latest" : "tags/v" + SelfUpdater.normalizeVersion(version);
        try (var input = open(releases.resolve(endpoint));
                var reader = new InputStreamReader(input, StandardCharsets.UTF_8)) {
            var release = JsonParser.parseReader(reader).getAsJsonObject();
            if (release.get("draft").getAsBoolean() || release.get("prerelease").getAsBoolean()) {
                throw new IOException("Only published stable releases can be installed");
            }
            SelfUpdater.normalizeVersion(release.get("tag_name").getAsString());
            return release;
        } catch (IllegalArgumentException | IllegalStateException | NullPointerException | JsonParseException e) {
            throw new IOException("Invalid GitHub release metadata", e);
        }
    }

    /** Streams an asset to disk and verifies its size and GitHub SHA-256 digest when supplied. */
    public void download(JsonObject asset, Path destination) throws IOException {
        var uri = URI.create(asset.get("browser_download_url").getAsString());
        if ("https".equals(releases.getScheme())
                && (!"https".equals(uri.getScheme())
                        || !"github.com".equals(uri.getHost())
                        || !uri.getPath().startsWith("/magx2/openhab-cli/releases/download/"))) {
            throw new IOException("Unexpected release download URL");
        }
        var digest = sha256();
        long size = 0;
        try (var input = open(uri);
                var output = Files.newOutputStream(destination)) {
            var buffer = new byte[64 * 1024];
            int count;
            while ((count = input.read(buffer)) != -1) {
                output.write(buffer, 0, count);
                digest.update(buffer, 0, count);
                size += count;
            }
        }
        if (size == 0 || size != asset.get("size").getAsLong()) {
            throw new IOException("Downloaded asset size does not match release metadata");
        }
        var expected = asset.get("digest");
        if (expected != null && !expected.isJsonNull()) {
            var actual = "sha256:" + HexFormat.of().formatHex(digest.digest());
            if (!actual.equalsIgnoreCase(expected.getAsString())) {
                throw new IOException("Downloaded asset SHA-256 checksum does not match release metadata");
            }
        }
    }

    private static MessageDigest sha256() {
        try {
            return MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }
    }

    private static InputStream open(URI uri) throws IOException {
        var connection = (HttpURLConnection) uri.toURL().openConnection();
        connection.setConnectTimeout(15_000);
        connection.setReadTimeout(60_000);
        connection.setRequestProperty("User-Agent", "openhab-cli-updater");
        connection.setRequestProperty("Accept", "application/vnd.github+json");
        if (connection.getResponseCode() != 200) {
            var status = connection.getResponseCode();
            connection.disconnect();
            throw new IOException("GitHub returned HTTP " + status + " for " + uri
                    + (status == 404 ? "; no matching published release was found" : ""));
        }
        return connection.getInputStream();
    }
}
