package org.openhab.cli.engine.rest;

import java.util.Objects;

/** Provides the configured generated REST client shared by endpoint instances. */
public class ApiClient {
    private final org.openhab.cli.client.ApiClient delegate;

    /** Creates a REST client with the generated client's default settings. */
    public ApiClient() {
        this(new org.openhab.cli.client.ApiClient());
    }

    /**
     * Wraps an existing REST client, preserving its connection and authentication settings.
     *
     * @param delegate the configured generated client
     * @throws NullPointerException if the client is null
     */
    public ApiClient(org.openhab.cli.client.ApiClient delegate) {
        this.delegate = Objects.requireNonNull(delegate, "delegate");
    }

    /**
     * Returns the shared generated REST client.
     *
     * @return the configured generated client
     */
    public org.openhab.cli.client.ApiClient toNative() {
        return delegate;
    }
}
