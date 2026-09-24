package org.openhab.cli.engine.endpoint;

import java.util.LinkedHashMap;
import java.util.Set;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.client.ApiException;
import org.openhab.cli.client.api.DiscoveryApi;
import org.openhab.cli.client.model.DiscoveryInfo;
import org.openhab.cli.engine.rest.ApiClient;

/**
 * Synchronous access to the openHAB operations provided by {@link DiscoveryApi}.
 */
@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public final class Discovery implements Endpoint {
    private final DiscoveryApi api;

    /**
     * Creates an endpoint using the supplied REST client.
     *
     * @param apiClient the client providing connection and authentication settings
     */
    public Discovery(ApiClient apiClient) {
        this(new DiscoveryApi(apiClient.toNative()));
    }

    /**
     * Gets all bindings that support discovery.
     *
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public Set<String> bindingsWithDiscoverySupport() {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Discovery.getBindingsWithDiscoverySupportWithHttpInfo({})", params);
            }
            var response = api.getBindingsWithDiscoverySupportWithHttpInfo();
            var data = response.getData();
            if (debugEnabled) {
                log.debug(
                        "RES: Discovery.getBindingsWithDiscoverySupportWithHttpInfo({}): status_code: {}, data: {}",
                        params,
                        response.getStatusCode(),
                        data);
            }
            return data;
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
            }
            throw new EndpointException(this.getClass(), "getBindingsWithDiscoverySupportWithHttpInfo", params, e);
        }
    }

    /**
     * Gets information about the discovery services for a binding.
     *
     * @param bindingId binding Id (required)
     * @param acceptLanguage language (optional)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public DiscoveryInfo discoveryServicesInfo(String bindingId, String acceptLanguage) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("bindingId", bindingId);
            params.put("acceptLanguage", acceptLanguage);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Discovery.getDiscoveryServicesInfoWithHttpInfo({})", params);
            }
            var response = api.getDiscoveryServicesInfoWithHttpInfo(bindingId, acceptLanguage);
            var data = response.getData();
            if (debugEnabled) {
                log.debug(
                        "RES: Discovery.getDiscoveryServicesInfoWithHttpInfo({}): status_code: {}, data: {}",
                        params,
                        response.getStatusCode(),
                        data);
            }
            return data;
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("bindingId", bindingId);
                params.put("acceptLanguage", acceptLanguage);
            }
            throw new EndpointException(this.getClass(), "getDiscoveryServicesInfoWithHttpInfo", params, e);
        }
    }

    /**
     * Gets information about the discovery services for a binding.
     *
     * <p>Omitted optional arguments are passed as {@code null}, leaving their defaults to the API.
     *
     * @param bindingId binding Id (required)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public DiscoveryInfo discoveryServicesInfo(String bindingId) {
        return discoveryServicesInfo(bindingId, null);
    }

    /**
     * Starts asynchronous discovery process for a binding and returns the timeout in seconds of the discovery operation.
     *
     * @param bindingId binding Id (required)
     * @param input input parameter to start the discovery (optional)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public Integer scan(String bindingId, String input) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("bindingId", bindingId);
            params.put("input", input);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Discovery.scanWithHttpInfo({})", params);
            }
            var response = api.scanWithHttpInfo(bindingId, input);
            var data = response.getData();
            if (debugEnabled) {
                log.debug(
                        "RES: Discovery.scanWithHttpInfo({}): status_code: {}, data: {}",
                        params,
                        response.getStatusCode(),
                        data);
            }
            return data;
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("bindingId", bindingId);
                params.put("input", input);
            }
            throw new EndpointException(this.getClass(), "scanWithHttpInfo", params, e);
        }
    }

    /**
     * Starts asynchronous discovery process for a binding and returns the timeout in seconds of the discovery operation.
     *
     * <p>Omitted optional arguments are passed as {@code null}, leaving their defaults to the API.
     *
     * @param bindingId binding Id (required)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public Integer scan(String bindingId) {
        return scan(bindingId, null);
    }
}
