package org.openhab.cli.engine.rest;

import lombok.RequiredArgsConstructor;
import org.openhab.cli.engine.properties.Properties;

/** Provides the configured generated REST client shared by endpoint instances. */
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
        // todo configure
        return apiClient;
    }
}
