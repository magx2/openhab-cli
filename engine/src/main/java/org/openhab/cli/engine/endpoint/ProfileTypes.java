package org.openhab.cli.engine.endpoint;

import java.util.LinkedHashMap;
import java.util.Set;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.client.ApiException;
import org.openhab.cli.client.api.ProfileTypesApi;
import org.openhab.cli.client.model.ProfileType;
import org.openhab.cli.engine.rest.ApiClient;

/**
 * Synchronous access to the openHAB operations provided by {@link ProfileTypesApi}.
 */
@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public final class ProfileTypes implements Endpoint {
    private final ProfileTypesApi api;

    /**
     * Creates an endpoint using the supplied REST client.
     *
     * @param apiClient the client providing connection and authentication settings
     */
    public ProfileTypes(ApiClient apiClient) {
        this(new ProfileTypesApi(apiClient.toNative()));
    }

    /**
     * Gets all available profile types.
     *
     * @param acceptLanguage language (optional)
     * @param channelTypeUID channel type filter (optional)
     * @param itemType item type filter (optional)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public Set<ProfileType> profileTypes(String acceptLanguage, String channelTypeUID, String itemType) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("acceptLanguage", acceptLanguage);
            params.put("channelTypeUID", channelTypeUID);
            params.put("itemType", itemType);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: ProfileTypes.getProfileTypesWithHttpInfo({})", params);
            }
            var response = api.getProfileTypesWithHttpInfo(acceptLanguage, channelTypeUID, itemType);
            var data = response.getData();
            if (debugEnabled) {
                log.debug(
                        "RES: ProfileTypes.getProfileTypesWithHttpInfo({}): status_code: {}, data: {}",
                        params,
                        response.getStatusCode(),
                        data);
            }
            return data;
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("acceptLanguage", acceptLanguage);
                params.put("channelTypeUID", channelTypeUID);
                params.put("itemType", itemType);
            }
            throw new EndpointException(this.getClass(), "getProfileTypesWithHttpInfo", params, e);
        }
    }

    /**
     * Gets all available profile types.
     *
     * <p>Omitted optional arguments are passed as {@code null}, leaving their defaults to the API.
     *
     * @param channelTypeUID channel type filter (optional)
     * @param itemType item type filter (optional)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public Set<ProfileType> profileTypes(String channelTypeUID, String itemType) {
        return profileTypes(null, channelTypeUID, itemType);
    }

    /**
     * Gets all available profile types.
     *
     * <p>Omitted optional arguments are passed as {@code null}, leaving their defaults to the API.
     *
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public Set<ProfileType> profileTypes() {
        return profileTypes(null, null, null);
    }
}
