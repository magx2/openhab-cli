package org.openhab.cli.engine.rest;

import static org.openhab.cli.engine.Version.VERSION;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.engine.properties.Properties;

/**
 * Provides the configured generated REST client shared by endpoint instances.
 */
@Slf4j
@RequiredArgsConstructor
public class ApiClient {
    private final Properties properties;

    /**
     * Returns the shared generated REST client.
     *
     * @return the configured generated client
     */
    public org.openhab.cli.client.ApiClient toNative() {
        var apiClient = new org.openhab.cli.client.ApiClient();

        // COMMON
        apiClient.setUserAgent("OpenHAB-CLI." + VERSION);
        apiClient.setDebugging(properties.apiClientDebugging());
        apiClient.setBasePath(properties.basePath());

        // TIMEOUTS
        apiClient.setConnectTimeout(properties.connectTimeout());
        apiClient.setReadTimeout(properties.readTimeout());
        apiClient.setWriteTimeout(properties.writeTimeout());

        // AUTH
        if (properties.hasOAuthToken()) {
            apiClient.setAccessToken(properties.oAuthToken());
        } else if (properties.hasUsername() && properties.hasPassword()) {
            var credentials = properties.username() + ":" + properties.password();
            var encoded = Base64.getEncoder().encodeToString(credentials.getBytes(StandardCharsets.ISO_8859_1));
            apiClient.addDefaultHeader("Authorization", "Basic " + encoded);
        }

        // SSL
        apiClient.setVerifyingSsl(properties.verifyingSsl());
        if (properties.hasTlsServerName()) apiClient.setTlsServerName(properties.tlsServerName());
        // setting SSL CA Cert should be the last one
        if (properties.hasSslCaCert()) {
            try (var cert = properties.getSslCaCertInputStream()) {
                apiClient.setSslCaCert(cert);
            } catch (IOException e) {
                log.error(
                        "Error while closing input stream for SSL CA cert! sslCaCert={}, sslCaCertPath={}",
                        properties.sslCaCert(),
                        properties.sslCaCertPath(),
                        e);
            }
        }

        return apiClient;
    }
}
