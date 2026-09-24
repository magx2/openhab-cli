package org.openhab.cli.engine.endpoint;

import java.util.LinkedHashMap;
import java.util.Set;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.client.ApiException;
import org.openhab.cli.client.api.ChannelTypesApi;
import org.openhab.cli.client.model.ChannelType;
import org.openhab.cli.engine.rest.ApiClient;

/**
 * Synchronous access to the openHAB operations provided by {@link ChannelTypesApi}.
 */
@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public final class ChannelTypes implements Endpoint {
    private final ChannelTypesApi api;

    /**
     * Creates an endpoint using the supplied REST client.
     *
     * @param apiClient the client providing connection and authentication settings
     */
    public ChannelTypes(ApiClient apiClient) {
        this(new ChannelTypesApi(apiClient.toNative()));
    }

    /**
     * Gets channel type by UID.
     *
     * @param channelTypeUID channelTypeUID (required)
     * @param acceptLanguage language (optional)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public ChannelType channelTypeByUID(String channelTypeUID, String acceptLanguage) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("channelTypeUID", channelTypeUID);
            params.put("acceptLanguage", acceptLanguage);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: ChannelTypes.getChannelTypeByUIDWithHttpInfo({})", params);
            }
            var response = api.getChannelTypeByUIDWithHttpInfo(channelTypeUID, acceptLanguage);
            var data = response.getData();
            if (debugEnabled) {
                log.debug(
                        "RES: ChannelTypes.getChannelTypeByUIDWithHttpInfo({}): status_code: {}, data: {}",
                        params,
                        response.getStatusCode(),
                        data);
            }
            return data;
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("channelTypeUID", channelTypeUID);
                params.put("acceptLanguage", acceptLanguage);
            }
            throw new EndpointException(this.getClass(), "getChannelTypeByUIDWithHttpInfo", params, e);
        }
    }

    /**
     * Gets channel type by UID.
     *
     * <p>Omitted optional arguments are passed as {@code null}, leaving their defaults to the API.
     *
     * @param channelTypeUID channelTypeUID (required)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public ChannelType channelTypeByUID(String channelTypeUID) {
        return channelTypeByUID(channelTypeUID, null);
    }

    /**
     * Gets all available channel types.
     *
     * @param acceptLanguage language (optional)
     * @param prefixes filter UIDs by prefix (multiple comma-separated prefixes allowed, for example: &#39;system,mqtt&#39;) (optional)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public Set<ChannelType> channelTypes(String acceptLanguage, String prefixes) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("acceptLanguage", acceptLanguage);
            params.put("prefixes", prefixes);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: ChannelTypes.getChannelTypesWithHttpInfo({})", params);
            }
            var response = api.getChannelTypesWithHttpInfo(acceptLanguage, prefixes);
            var data = response.getData();
            if (debugEnabled) {
                log.debug(
                        "RES: ChannelTypes.getChannelTypesWithHttpInfo({}): status_code: {}, data: {}",
                        params,
                        response.getStatusCode(),
                        data);
            }
            return data;
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("acceptLanguage", acceptLanguage);
                params.put("prefixes", prefixes);
            }
            throw new EndpointException(this.getClass(), "getChannelTypesWithHttpInfo", params, e);
        }
    }

    /**
     * Gets all available channel types.
     *
     * <p>Omitted optional arguments are passed as {@code null}, leaving their defaults to the API.
     *
     * @param prefixes filter UIDs by prefix (multiple comma-separated prefixes allowed, for example: &#39;system,mqtt&#39;) (optional)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public Set<ChannelType> channelTypes(String prefixes) {
        return channelTypes(null, prefixes);
    }

    /**
     * Gets all available channel types.
     *
     * <p>Omitted optional arguments are passed as {@code null}, leaving their defaults to the API.
     *
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public Set<ChannelType> channelTypes() {
        return channelTypes(null, null);
    }

    /**
     * Gets the item types the given trigger channel type UID can be linked to.
     *
     * @param channelTypeUID channelTypeUID (required)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public Set<String> linkableItemTypesByChannelTypeUID(String channelTypeUID) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("channelTypeUID", channelTypeUID);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: ChannelTypes.getLinkableItemTypesByChannelTypeUIDWithHttpInfo({})", params);
            }
            var response = api.getLinkableItemTypesByChannelTypeUIDWithHttpInfo(channelTypeUID);
            var data = response.getData();
            if (debugEnabled) {
                log.debug(
                        "RES: ChannelTypes.getLinkableItemTypesByChannelTypeUIDWithHttpInfo({}): status_code: {}, data: {}",
                        params,
                        response.getStatusCode(),
                        data);
            }
            return data;
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("channelTypeUID", channelTypeUID);
            }
            throw new EndpointException(this.getClass(), "getLinkableItemTypesByChannelTypeUIDWithHttpInfo", params, e);
        }
    }
}
