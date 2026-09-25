package org.openhab.cli.engine.endpoint;

import java.util.LinkedHashMap;
import java.util.Set;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.client.ApiException;
import org.openhab.cli.client.api.EventsApi;
import org.openhab.cli.engine.rest.ApiClient;

/**
 * Synchronous access to the openHAB operations provided by {@link EventsApi}.
 */
@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public final class Events {
    private final EventsApi api;

    /**
     * Creates an endpoint using the supplied REST client.
     *
     * @param apiClient the client providing connection and authentication settings
     */
    public Events(ApiClient apiClient) {
        this(new EventsApi(apiClient.toNative()));
    }

    /**
     * Get all events.
     *
     * @param topics topics (optional)
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public void events(String topics) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("topics", topics);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Events.getEventsWithHttpInfo({})", params);
            }
            var response = api.getEventsWithHttpInfo(topics);
            if (debugEnabled) {
                log.debug("RES: Events.getEventsWithHttpInfo({}): status_code: {}", params, response.getStatusCode());
            }
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("topics", topics);
            }
            throw new EndpointException(this.getClass(), "getEventsWithHttpInfo", params, e);
        }
    }

    /**
     * Get all events.
     *
     * <p>Omitted optional arguments are passed as {@code null}, leaving their defaults to the API.
     *
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public void events() {
        events(null);
    }

    /**
     * Initiates a new item state tracker connection
     *
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public void initNewStateTacker() {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Events.initNewStateTackerWithHttpInfo({})", params);
            }
            var response = api.initNewStateTackerWithHttpInfo();
            if (debugEnabled) {
                log.debug(
                        "RES: Events.initNewStateTackerWithHttpInfo({}): status_code: {}",
                        params,
                        response.getStatusCode());
            }
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
            }
            throw new EndpointException(this.getClass(), "initNewStateTackerWithHttpInfo", params, e);
        }
    }

    /**
     * Changes the list of items a SSE connection will receive state updates to.
     *
     * @param connectionId  (required)
     * @param requestBody items (optional)
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public void updateItemListForStateUpdates(String connectionId, Set<String> requestBody) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("connectionId", connectionId);
            params.put("requestBody", requestBody);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Events.updateItemListForStateUpdatesWithHttpInfo({})", params);
            }
            var response = api.updateItemListForStateUpdatesWithHttpInfo(connectionId, requestBody);
            if (debugEnabled) {
                log.debug(
                        "RES: Events.updateItemListForStateUpdatesWithHttpInfo({}): status_code: {}",
                        params,
                        response.getStatusCode());
            }
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("connectionId", connectionId);
                params.put("requestBody", requestBody);
            }
            throw new EndpointException(this.getClass(), "updateItemListForStateUpdatesWithHttpInfo", params, e);
        }
    }

    /**
     * Changes the list of items a SSE connection will receive state updates to.
     *
     * <p>Omitted optional arguments are passed as {@code null}, leaving their defaults to the API.
     *
     * @param connectionId  (required)
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public void updateItemListForStateUpdates(String connectionId) {
        updateItemListForStateUpdates(connectionId, null);
    }
}
