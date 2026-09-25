package org.openhab.cli.engine.endpoint;

import java.util.LinkedHashMap;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.client.ApiException;
import org.openhab.cli.client.api.SysteminfoApi;
import org.openhab.cli.client.model.SystemInfoBean;
import org.openhab.cli.client.model.UoMInfoBean;
import org.openhab.cli.engine.rest.ApiClient;

/**
 * Synchronous access to the openHAB operations provided by {@link SysteminfoApi}.
 */
@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public final class SystemInfo {
    private final SysteminfoApi api;

    /**
     * Creates an endpoint using the supplied REST client.
     *
     * @param apiClient the client providing connection and authentication settings
     */
    public SystemInfo(ApiClient apiClient) {
        this(new SysteminfoApi(apiClient.toNative()));
    }

    /**
     * Gets information about the system.
     *
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public SystemInfoBean systemInformation() {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: SystemInfo.getSystemInformationWithHttpInfo({})", params);
            }
            var response = api.getSystemInformationWithHttpInfo();
            var data = response.getData();
            if (debugEnabled) {
                log.debug(
                        "RES: SystemInfo.getSystemInformationWithHttpInfo({}): status_code: {}, data: {}",
                        params,
                        response.getStatusCode(),
                        data);
            }
            return data;
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
            }
            throw new EndpointException(this.getClass(), "getSystemInformationWithHttpInfo", params, e);
        }
    }

    /**
     * Get all supported dimensions and their system units.
     *
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public UoMInfoBean uoMInformation() {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: SystemInfo.getUoMInformationWithHttpInfo({})", params);
            }
            var response = api.getUoMInformationWithHttpInfo();
            var data = response.getData();
            if (debugEnabled) {
                log.debug(
                        "RES: SystemInfo.getUoMInformationWithHttpInfo({}): status_code: {}, data: {}",
                        params,
                        response.getStatusCode(),
                        data);
            }
            return data;
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
            }
            throw new EndpointException(this.getClass(), "getUoMInformationWithHttpInfo", params, e);
        }
    }
}
