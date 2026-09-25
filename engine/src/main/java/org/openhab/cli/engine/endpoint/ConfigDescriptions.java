package org.openhab.cli.engine.endpoint;

import java.util.LinkedHashMap;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.client.ApiException;
import org.openhab.cli.client.api.ConfigDescriptionsApi;
import org.openhab.cli.client.model.EnrichedConfigDescription;
import org.openhab.cli.engine.rest.ApiClient;

/**
 * Synchronous access to the openHAB operations provided by {@link ConfigDescriptionsApi}.
 */
@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public final class ConfigDescriptions implements Endpoint {
    private final ConfigDescriptionsApi api;

    /**
     * Creates an endpoint using the supplied REST client.
     *
     * @param apiClient the client providing connection and authentication settings
     */
    public ConfigDescriptions(ApiClient apiClient) {
        this(new ConfigDescriptionsApi(apiClient.toNative()));
    }

    /**
     * Gets a config description by URI.
     *
     * @param uri uri (required)
     * @param acceptLanguage language (optional)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public EnrichedConfigDescription configDescriptionByURI(String uri, String acceptLanguage) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("uri", uri);
            params.put("acceptLanguage", acceptLanguage);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: ConfigDescriptions.getConfigDescriptionByURIWithHttpInfo({})", params);
            }
            var response = api.getConfigDescriptionByURIWithHttpInfo(uri, acceptLanguage);
            var data = response.getData();
            if (debugEnabled) {
                log.debug(
                        "RES: ConfigDescriptions.getConfigDescriptionByURIWithHttpInfo({}): status_code: {}, data: {}",
                        params,
                        response.getStatusCode(),
                        data);
            }
            return data;
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("uri", uri);
                params.put("acceptLanguage", acceptLanguage);
            }
            throw new EndpointException(this.getClass(), "getConfigDescriptionByURIWithHttpInfo", params, e);
        }
    }

    /**
     * Gets a config description by URI.
     *
     * <p>Omitted optional arguments are passed as {@code null}, leaving their defaults to the API.
     *
     * @param uri uri (required)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public EnrichedConfigDescription configDescriptionByURI(String uri) {
        return configDescriptionByURI(uri, null);
    }

    /**
     * Gets all available config descriptions.
     *
     * @param acceptLanguage language (optional)
     * @param scheme scheme filter (optional)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public List<EnrichedConfigDescription> configDescriptions(String acceptLanguage, String scheme) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("acceptLanguage", acceptLanguage);
            params.put("scheme", scheme);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: ConfigDescriptions.getConfigDescriptionsWithHttpInfo({})", params);
            }
            var response = api.getConfigDescriptionsWithHttpInfo(acceptLanguage, scheme);
            var data = response.getData();
            if (debugEnabled) {
                log.debug(
                        "RES: ConfigDescriptions.getConfigDescriptionsWithHttpInfo({}): status_code: {}, data: {}",
                        params,
                        response.getStatusCode(),
                        data);
            }
            return data;
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("acceptLanguage", acceptLanguage);
                params.put("scheme", scheme);
            }
            throw new EndpointException(this.getClass(), "getConfigDescriptionsWithHttpInfo", params, e);
        }
    }

    /**
     * Gets all available config descriptions.
     *
     * <p>Omitted optional arguments are passed as {@code null}, leaving their defaults to the API.
     *
     * @param scheme scheme filter (optional)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public List<EnrichedConfigDescription> configDescriptions(String scheme) {
        return configDescriptions(null, scheme);
    }

    /**
     * Gets all available config descriptions.
     *
     * <p>Omitted optional arguments are passed as {@code null}, leaving their defaults to the API.
     *
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public List<EnrichedConfigDescription> configDescriptions() {
        return configDescriptions(null, null);
    }
}
