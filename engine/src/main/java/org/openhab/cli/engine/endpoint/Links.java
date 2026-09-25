package org.openhab.cli.engine.endpoint;

import java.util.LinkedHashMap;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.client.ApiException;
import org.openhab.cli.client.api.LinksApi;
import org.openhab.cli.client.model.BrokenItemChannelLink;
import org.openhab.cli.client.model.EnrichedItemChannelLink;
import org.openhab.cli.client.model.ItemChannelLink;
import org.openhab.cli.engine.rest.ApiClient;

/**
 * Synchronous access to the openHAB operations provided by {@link LinksApi}.
 */
@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public final class Links implements Endpoint {
    private final LinksApi api;

    /**
     * Creates an endpoint using the supplied REST client.
     *
     * @param apiClient the client providing connection and authentication settings
     */
    public Links(ApiClient apiClient) {
        this(new LinksApi(apiClient.toNative()));
    }

    /**
     * Retrieves an individual link.
     *
     * @param itemName item name (required)
     * @param channelUID channel UID (required)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public EnrichedItemChannelLink itemLink(String itemName, String channelUID) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("itemName", itemName);
            params.put("channelUID", channelUID);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Links.getItemLinkWithHttpInfo({})", params);
            }
            var response = api.getItemLinkWithHttpInfo(itemName, channelUID);
            var data = response.getData();
            if (debugEnabled) {
                log.debug(
                        "RES: Links.getItemLinkWithHttpInfo({}): status_code: {}, data: {}",
                        params,
                        response.getStatusCode(),
                        data);
            }
            return data;
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("itemName", itemName);
                params.put("channelUID", channelUID);
            }
            throw new EndpointException(this.getClass(), "getItemLinkWithHttpInfo", params, e);
        }
    }

    /**
     * Gets all available links.
     *
     * @param channelUID filter by channel UID (optional)
     * @param itemName filter by item name (optional)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public List<EnrichedItemChannelLink> itemLinks(String channelUID, String itemName) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("channelUID", channelUID);
            params.put("itemName", itemName);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Links.getItemLinksWithHttpInfo({})", params);
            }
            var response = api.getItemLinksWithHttpInfo(channelUID, itemName);
            var data = response.getData();
            if (debugEnabled) {
                log.debug(
                        "RES: Links.getItemLinksWithHttpInfo({}): status_code: {}, data: {}",
                        params,
                        response.getStatusCode(),
                        data);
            }
            return data;
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("channelUID", channelUID);
                params.put("itemName", itemName);
            }
            throw new EndpointException(this.getClass(), "getItemLinksWithHttpInfo", params, e);
        }
    }

    /**
     * Gets all available links.
     *
     * <p>Omitted optional arguments are passed as {@code null}, leaving their defaults to the API.
     *
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public List<EnrichedItemChannelLink> itemLinks() {
        return itemLinks(null, null);
    }

    /**
     * Get orphan links between items and broken/non-existent thing channels
     *
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public List<BrokenItemChannelLink> orphanLinks() {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Links.getOrphanLinksWithHttpInfo({})", params);
            }
            var response = api.getOrphanLinksWithHttpInfo();
            var data = response.getData();
            if (debugEnabled) {
                log.debug(
                        "RES: Links.getOrphanLinksWithHttpInfo({}): status_code: {}, data: {}",
                        params,
                        response.getStatusCode(),
                        data);
            }
            return data;
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
            }
            throw new EndpointException(this.getClass(), "getOrphanLinksWithHttpInfo", params, e);
        }
    }

    /**
     * Links an item to a channel.
     *
     * @param itemName itemName (required)
     * @param channelUID channelUID (required)
     * @param itemChannelLink link data (optional)
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public void linkItemToChannel(String itemName, String channelUID, ItemChannelLink itemChannelLink) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("itemName", itemName);
            params.put("channelUID", channelUID);
            params.put("itemChannelLink", itemChannelLink);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Links.linkItemToChannelWithHttpInfo({})", params);
            }
            var response = api.linkItemToChannelWithHttpInfo(itemName, channelUID, itemChannelLink);
            if (debugEnabled) {
                log.debug(
                        "RES: Links.linkItemToChannelWithHttpInfo({}): status_code: {}",
                        params,
                        response.getStatusCode());
            }
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("itemName", itemName);
                params.put("channelUID", channelUID);
                params.put("itemChannelLink", itemChannelLink);
            }
            throw new EndpointException(this.getClass(), "linkItemToChannelWithHttpInfo", params, e);
        }
    }

    /**
     * Links an item to a channel.
     *
     * <p>Omitted optional arguments are passed as {@code null}, leaving their defaults to the API.
     *
     * @param itemName itemName (required)
     * @param channelUID channelUID (required)
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public void linkItemToChannel(String itemName, String channelUID) {
        linkItemToChannel(itemName, channelUID, null);
    }

    /**
     * Remove unused/orphaned links.
     *
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public void purgeDatabase1() {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Links.purgeDatabase1WithHttpInfo({})", params);
            }
            var response = api.purgeDatabase1WithHttpInfo();
            if (debugEnabled) {
                log.debug(
                        "RES: Links.purgeDatabase1WithHttpInfo({}): status_code: {}", params, response.getStatusCode());
            }
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
            }
            throw new EndpointException(this.getClass(), "purgeDatabase1WithHttpInfo", params, e);
        }
    }

    /**
     * Delete all links that refer to an item or thing.
     *
     * @param _object item name or thing UID (required)
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public void removeAllLinksForObject(String _object) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("_object", _object);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Links.removeAllLinksForObjectWithHttpInfo({})", params);
            }
            var response = api.removeAllLinksForObjectWithHttpInfo(_object);
            if (debugEnabled) {
                log.debug(
                        "RES: Links.removeAllLinksForObjectWithHttpInfo({}): status_code: {}",
                        params,
                        response.getStatusCode());
            }
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("_object", _object);
            }
            throw new EndpointException(this.getClass(), "removeAllLinksForObjectWithHttpInfo", params, e);
        }
    }

    /**
     * Unlinks an item from a channel.
     *
     * @param itemName itemName (required)
     * @param channelUID channelUID (required)
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public void unlinkItemFromChannel(String itemName, String channelUID) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("itemName", itemName);
            params.put("channelUID", channelUID);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Links.unlinkItemFromChannelWithHttpInfo({})", params);
            }
            var response = api.unlinkItemFromChannelWithHttpInfo(itemName, channelUID);
            if (debugEnabled) {
                log.debug(
                        "RES: Links.unlinkItemFromChannelWithHttpInfo({}): status_code: {}",
                        params,
                        response.getStatusCode());
            }
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("itemName", itemName);
                params.put("channelUID", channelUID);
            }
            throw new EndpointException(this.getClass(), "unlinkItemFromChannelWithHttpInfo", params, e);
        }
    }
}
