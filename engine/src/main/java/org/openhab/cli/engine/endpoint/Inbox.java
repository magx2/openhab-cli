package org.openhab.cli.engine.endpoint;

import java.util.LinkedHashMap;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.client.ApiException;
import org.openhab.cli.client.api.InboxApi;
import org.openhab.cli.client.model.DiscoveryResult;
import org.openhab.cli.engine.rest.ApiClient;

/**
 * Synchronous access to the openHAB operations provided by {@link InboxApi}.
 */
@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public final class Inbox {
    private final InboxApi api;

    /**
     * Creates an endpoint using the supplied REST client.
     *
     * @param apiClient the client providing connection and authentication settings
     */
    public Inbox(ApiClient apiClient) {
        this(new InboxApi(apiClient.toNative()));
    }

    /**
     * Approves the discovery result by adding the thing to the registry.
     *
     * @param thingUID thingUID (required)
     * @param acceptLanguage language (optional)
     * @param newThingId new thing ID (optional)
     * @param body thing label (optional)
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public void approveInboxItemById(String thingUID, String acceptLanguage, String newThingId, String body) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("thingUID", thingUID);
            params.put("acceptLanguage", acceptLanguage);
            params.put("newThingId", newThingId);
            params.put("body", body);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Inbox.approveInboxItemByIdWithHttpInfo({})", params);
            }
            var response = api.approveInboxItemByIdWithHttpInfo(thingUID, acceptLanguage, newThingId, body);
            if (debugEnabled) {
                log.debug(
                        "RES: Inbox.approveInboxItemByIdWithHttpInfo({}): status_code: {}",
                        params,
                        response.getStatusCode());
            }
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("thingUID", thingUID);
                params.put("acceptLanguage", acceptLanguage);
                params.put("newThingId", newThingId);
                params.put("body", body);
            }
            throw new EndpointException(this.getClass(), "approveInboxItemByIdWithHttpInfo", params, e);
        }
    }

    /**
     * Approves the discovery result by adding the thing to the registry.
     *
     * <p>Omitted optional arguments are passed as {@code null}, leaving their defaults to the API.
     *
     * @param thingUID thingUID (required)
     * @param newThingId new thing ID (optional)
     * @param body thing label (optional)
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public void approveInboxItemById(String thingUID, String newThingId, String body) {
        approveInboxItemById(thingUID, null, newThingId, body);
    }

    /**
     * Approves the discovery result by adding the thing to the registry.
     *
     * <p>Omitted optional arguments are passed as {@code null}, leaving their defaults to the API.
     *
     * @param thingUID thingUID (required)
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public void approveInboxItemById(String thingUID) {
        approveInboxItemById(thingUID, null, null, null);
    }

    /**
     * Flags a discovery result as ignored for further processing.
     *
     * @param thingUID thingUID (required)
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public void flagInboxItemAsIgnored(String thingUID) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("thingUID", thingUID);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Inbox.flagInboxItemAsIgnoredWithHttpInfo({})", params);
            }
            var response = api.flagInboxItemAsIgnoredWithHttpInfo(thingUID);
            if (debugEnabled) {
                log.debug(
                        "RES: Inbox.flagInboxItemAsIgnoredWithHttpInfo({}): status_code: {}",
                        params,
                        response.getStatusCode());
            }
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("thingUID", thingUID);
            }
            throw new EndpointException(this.getClass(), "flagInboxItemAsIgnoredWithHttpInfo", params, e);
        }
    }

    /**
     * Get all discovered things.
     *
     * @param includeIgnored If true, include ignored inbox entries. Defaults to true (optional, default to true)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public List<DiscoveryResult> discoveredInboxItems(Boolean includeIgnored) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("includeIgnored", includeIgnored);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Inbox.getDiscoveredInboxItemsWithHttpInfo({})", params);
            }
            var response = api.getDiscoveredInboxItemsWithHttpInfo(includeIgnored);
            var data = response.getData();
            if (debugEnabled) {
                log.debug(
                        "RES: Inbox.getDiscoveredInboxItemsWithHttpInfo({}): status_code: {}, data: {}",
                        params,
                        response.getStatusCode(),
                        data);
            }
            return data;
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("includeIgnored", includeIgnored);
            }
            throw new EndpointException(this.getClass(), "getDiscoveredInboxItemsWithHttpInfo", params, e);
        }
    }

    /**
     * Get all discovered things.
     *
     * <p>Omitted optional arguments are passed as {@code null}, leaving their defaults to the API.
     *
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public List<DiscoveryResult> discoveredInboxItems() {
        return discoveredInboxItems(null);
    }

    /**
     * Removes ignore flag from a discovery result.
     *
     * @param thingUID thingUID (required)
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public void removeIgnoreFlagOnInboxItem(String thingUID) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("thingUID", thingUID);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Inbox.removeIgnoreFlagOnInboxItemWithHttpInfo({})", params);
            }
            var response = api.removeIgnoreFlagOnInboxItemWithHttpInfo(thingUID);
            if (debugEnabled) {
                log.debug(
                        "RES: Inbox.removeIgnoreFlagOnInboxItemWithHttpInfo({}): status_code: {}",
                        params,
                        response.getStatusCode());
            }
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("thingUID", thingUID);
            }
            throw new EndpointException(this.getClass(), "removeIgnoreFlagOnInboxItemWithHttpInfo", params, e);
        }
    }

    /**
     * Removes the discovery result from the inbox.
     *
     * @param thingUID thingUID (required)
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public void removeItemFromInbox(String thingUID) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("thingUID", thingUID);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Inbox.removeItemFromInboxWithHttpInfo({})", params);
            }
            var response = api.removeItemFromInboxWithHttpInfo(thingUID);
            if (debugEnabled) {
                log.debug(
                        "RES: Inbox.removeItemFromInboxWithHttpInfo({}): status_code: {}",
                        params,
                        response.getStatusCode());
            }
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("thingUID", thingUID);
            }
            throw new EndpointException(this.getClass(), "removeItemFromInboxWithHttpInfo", params, e);
        }
    }
}
