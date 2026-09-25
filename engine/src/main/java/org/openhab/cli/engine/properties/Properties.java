package org.openhab.cli.engine.properties;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.file.Files.exists;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public record Properties(
        String baseUrl,
        String basePath,
        String oAuthToken,
        String username,
        String password,
        boolean prettyPrint,
        boolean verifyingSsl,
        boolean apiClientDebugging,
        String sslCaCert,
        Path sslCaCertPath,
        String tlsServerName,
        int connectTimeout,
        int readTimeout,
        int writeTimeout) {
    public static final String DEFAULT_BASE_PATH = "/rest";
    public static final int DEFAULT_CONNECT_TIMEOUT = 10_000;
    public static final int DEFAULT_READ_TIMEOUT = 10_000;
    public static final int DEFAULT_WRITE_TIMEOUT = 10_000;
    public static final Properties DEFAULT = new Properties(
            null,
            null,
            null,
            null,
            null,
            true,
            true,
            false,
            null,
            null,
            null,
            DEFAULT_CONNECT_TIMEOUT,
            DEFAULT_READ_TIMEOUT,
            DEFAULT_WRITE_TIMEOUT);

    /**
     * Creates client settings.
     *
     * @throws IllegalArgumentException if OAuth and basic authentication credentials are both supplied
     */
    public Properties {
        if (oAuthToken != null
                && !oAuthToken.isEmpty()
                && ((username != null && !username.isEmpty()) || (password != null && !password.isEmpty()))) {
            throw new IllegalArgumentException("OAuth token cannot be combined with username or password");
        }
        if (basePath == null || basePath.isEmpty()) basePath = DEFAULT_BASE_PATH;
        if (sslCaCert != null && sslCaCertPath != null) {
            throw new IllegalArgumentException("You can't set both `sslCaCert` and `sslCaCertPath`");
        }
        if (connectTimeout < 0) connectTimeout = DEFAULT_CONNECT_TIMEOUT;
        if (readTimeout < 0) readTimeout = DEFAULT_READ_TIMEOUT;
        if (writeTimeout < 0) writeTimeout = DEFAULT_WRITE_TIMEOUT;
    }

    /**
     * Resolves the REST URL after file and CLI settings have been merged.
     *
     * @return the server URL followed by the configured REST path
     * @throws IllegalArgumentException if the required base URL is missing or is not an HTTP(S) server URL
     */
    public String apiBaseUrl() {
        if (baseUrl == null || baseUrl.isBlank()) {
            throw new IllegalArgumentException(
                    "baseUrl is required: set --base-url or config.baseUrl in the properties file");
        }
        var uri = java.net.URI.create(baseUrl);
        if ((!"http".equalsIgnoreCase(uri.getScheme()) && !"https".equalsIgnoreCase(uri.getScheme()))
                || uri.getHost() == null
                || uri.getRawQuery() != null
                || uri.getRawFragment() != null) {
            throw new IllegalArgumentException("baseUrl must be an absolute HTTP(S) URL without a query or fragment");
        }
        return baseUrl.replaceAll("/+$", "") + "/" + basePath.replaceAll("^/+", "");
    }

    public boolean hasOAuthToken() {
        return oAuthToken != null && !oAuthToken.isEmpty();
    }

    public boolean hasUsername() {
        return username != null && !username.isEmpty();
    }

    public boolean hasPassword() {
        return password != null && !password.isEmpty();
    }

    public boolean hasTlsServerName() {
        return tlsServerName != null && !tlsServerName.isEmpty();
    }

    public boolean hasSslCaCert() {
        return sslCaCert != null || (sslCaCertPath != null && exists(sslCaCertPath));
    }

    public InputStream getSslCaCertInputStream() {
        if (sslCaCert != null) {
            return new ByteArrayInputStream(sslCaCert.getBytes(UTF_8));
        }
        try {
            return Files.newInputStream(sslCaCertPath, StandardOpenOption.READ);
        } catch (IOException e) {
            throw new UncheckedIOException("Cannot read SSL CA Cert from `%s`".formatted(sslCaCertPath), e);
        }
    }
}
