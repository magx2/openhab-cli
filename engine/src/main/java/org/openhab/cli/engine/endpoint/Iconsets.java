package org.openhab.cli.engine.endpoint;

import java.util.LinkedHashMap;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.client.ApiException;
import org.openhab.cli.client.api.IconsetsApi;
import org.openhab.cli.client.model.IconSet;
import org.openhab.cli.engine.rest.ApiClient;

/**
 * Synchronous access to the openHAB operations provided by {@link IconsetsApi}.
 */
@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public final class Iconsets {
    private final IconsetsApi api;

    /**
     * Creates an endpoint using the supplied REST client.
     *
     * @param apiClient the client providing connection and authentication settings
     */
    public Iconsets(ApiClient apiClient) {
        this(new IconsetsApi(apiClient.toNative()));
    }

    /**
     * Gets all icon sets.
     *
     * @param acceptLanguage language (optional)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public List<IconSet> iconSets(String acceptLanguage) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("acceptLanguage", acceptLanguage);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Iconsets.getIconSetsWithHttpInfo({})", params);
            }
            var response = api.getIconSetsWithHttpInfo(acceptLanguage);
            var data = response.getData();
            if (debugEnabled) {
                log.debug(
                        "RES: Iconsets.getIconSetsWithHttpInfo({}): status_code: {}, data: {}",
                        params,
                        response.getStatusCode(),
                        data);
            }
            return data;
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("acceptLanguage", acceptLanguage);
            }
            throw new EndpointException(this.getClass(), "getIconSetsWithHttpInfo", params, e);
        }
    }

    /**
     * Gets all icon sets.
     *
     * <p>Omitted optional arguments are passed as {@code null}, leaving their defaults to the API.
     *
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public List<IconSet> iconSets() {
        return iconSets(null);
    }
}
