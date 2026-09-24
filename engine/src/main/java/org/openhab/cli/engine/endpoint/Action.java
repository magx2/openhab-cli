package org.openhab.cli.engine.endpoint;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.client.ApiException;
import org.openhab.cli.client.api.ActionsApi;
import org.openhab.cli.client.model.ThingAction;
import org.openhab.cli.engine.rest.ApiClient;

/**
 * Synchronous access to the openHAB operations provided by {@link ActionsApi}.
 */
@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public final class Action implements Endpoint {
    private final ActionsApi api;

    /**
     * Creates an endpoint using the supplied REST client.
     *
     * @param apiClient the client providing connection and authentication settings
     */
    public Action(ApiClient apiClient) {
        this(new ActionsApi(apiClient.toNative()));
    }

    /**
     * Executes a thing action.
     *
     * @param thingUID thingUID (required)
     * @param actionUid action type UID (including scope, separated by &#39;.&#39;) (required)
     * @param acceptLanguage language (optional)
     * @param requestBody action inputs as map (parameter name as key / argument as value) (optional)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public String executeThingAction(
            String thingUID, String actionUid, String acceptLanguage, Map<String, Object> requestBody) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("thingUID", thingUID);
            params.put("actionUid", actionUid);
            params.put("acceptLanguage", acceptLanguage);
            params.put("requestBody", requestBody);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Action.executeThingActionWithHttpInfo({})", params);
            }
            var response = api.executeThingActionWithHttpInfo(thingUID, actionUid, acceptLanguage, requestBody);
            var data = response.getData();
            if (debugEnabled) {
                log.debug(
                        "RES: Action.executeThingActionWithHttpInfo({}): status_code: {}, data: {}",
                        params,
                        response.getStatusCode(),
                        data);
            }
            return data;
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("thingUID", thingUID);
                params.put("actionUid", actionUid);
                params.put("acceptLanguage", acceptLanguage);
                params.put("requestBody", requestBody);
            }
            throw new EndpointException(this.getClass(), "executeThingActionWithHttpInfo", params, e);
        }
    }

    /**
     * Executes a thing action.
     *
     * <p>Omitted optional arguments are passed as {@code null}, leaving their defaults to the API.
     *
     * @param thingUID thingUID (required)
     * @param actionUid action type UID (including scope, separated by &#39;.&#39;) (required)
     * @param requestBody action inputs as map (parameter name as key / argument as value) (optional)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public String executeThingAction(String thingUID, String actionUid, Map<String, Object> requestBody) {
        return executeThingAction(thingUID, actionUid, null, requestBody);
    }

    /**
     * Executes a thing action.
     *
     * <p>Omitted optional arguments are passed as {@code null}, leaving their defaults to the API.
     *
     * @param thingUID thingUID (required)
     * @param actionUid action type UID (including scope, separated by &#39;.&#39;) (required)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public String executeThingAction(String thingUID, String actionUid) {
        return executeThingAction(thingUID, actionUid, null, null);
    }

    /**
     * Get all available actions for provided thing UID
     *
     * @param thingUID thingUID (required)
     * @param acceptLanguage language (optional)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public Set<ThingAction> availableActionsForThing(String thingUID, String acceptLanguage) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("thingUID", thingUID);
            params.put("acceptLanguage", acceptLanguage);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Action.getAvailableActionsForThingWithHttpInfo({})", params);
            }
            var response = api.getAvailableActionsForThingWithHttpInfo(thingUID, acceptLanguage);
            var data = response.getData();
            if (debugEnabled) {
                log.debug(
                        "RES: Action.getAvailableActionsForThingWithHttpInfo({}): status_code: {}, data: {}",
                        params,
                        response.getStatusCode(),
                        data);
            }
            return data;
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("thingUID", thingUID);
                params.put("acceptLanguage", acceptLanguage);
            }
            throw new EndpointException(this.getClass(), "getAvailableActionsForThingWithHttpInfo", params, e);
        }
    }

    /**
     * Get all available actions for provided thing UID
     *
     * <p>Omitted optional arguments are passed as {@code null}, leaving their defaults to the API.
     *
     * @param thingUID thingUID (required)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public Set<ThingAction> availableActionsForThing(String thingUID) {
        return availableActionsForThing(thingUID, null);
    }
}
