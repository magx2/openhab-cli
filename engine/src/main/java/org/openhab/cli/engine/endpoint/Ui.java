package org.openhab.cli.engine.endpoint;

import java.util.LinkedHashMap;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.client.ApiException;
import org.openhab.cli.client.api.UiApi;
import org.openhab.cli.client.model.EnrichedRootUIComponent;
import org.openhab.cli.client.model.RootUIComponent;
import org.openhab.cli.client.model.Tile;
import org.openhab.cli.engine.rest.ApiClient;

/**
 * Synchronous access to the openHAB operations provided by {@link UiApi}.
 */
@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public final class Ui {
    private final UiApi api;

    /**
     * Creates an endpoint using the supplied REST client.
     *
     * @param apiClient the client providing connection and authentication settings
     */
    public Ui(ApiClient apiClient) {
        this(new UiApi(apiClient.toNative()));
    }

    /**
     * Add a UI component in the specified namespace.
     *
     * @param namespace  (required)
     * @param rootUIComponent  (optional)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public EnrichedRootUIComponent addUIComponentToNamespace(String namespace, RootUIComponent rootUIComponent) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("namespace", namespace);
            params.put("rootUIComponent", rootUIComponent);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Ui.addUIComponentToNamespaceWithHttpInfo({})", params);
            }
            var response = api.addUIComponentToNamespaceWithHttpInfo(namespace, rootUIComponent);
            var data = response.getData();
            if (debugEnabled) {
                log.debug(
                        "RES: Ui.addUIComponentToNamespaceWithHttpInfo({}): status_code: {}, data: {}",
                        params,
                        response.getStatusCode(),
                        data);
            }
            return data;
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("namespace", namespace);
                params.put("rootUIComponent", rootUIComponent);
            }
            throw new EndpointException(this.getClass(), "addUIComponentToNamespaceWithHttpInfo", params, e);
        }
    }

    /**
     * Add a UI component in the specified namespace.
     *
     * <p>Omitted optional arguments are passed as {@code null}, leaving their defaults to the API.
     *
     * @param namespace  (required)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public EnrichedRootUIComponent addUIComponentToNamespace(String namespace) {
        return addUIComponentToNamespace(namespace, null);
    }

    /**
     * Get all registered UI components in the specified namespace.
     *
     * @param namespace  (required)
     * @param summary summary fields only (optional)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public List<EnrichedRootUIComponent> registeredUIComponentsInNamespace(String namespace, Boolean summary) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("namespace", namespace);
            params.put("summary", summary);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Ui.getRegisteredUIComponentsInNamespaceWithHttpInfo({})", params);
            }
            var response = api.getRegisteredUIComponentsInNamespaceWithHttpInfo(namespace, summary);
            var data = response.getData();
            if (debugEnabled) {
                log.debug(
                        "RES: Ui.getRegisteredUIComponentsInNamespaceWithHttpInfo({}): status_code: {}, data: {}",
                        params,
                        response.getStatusCode(),
                        data);
            }
            return data;
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("namespace", namespace);
                params.put("summary", summary);
            }
            throw new EndpointException(this.getClass(), "getRegisteredUIComponentsInNamespaceWithHttpInfo", params, e);
        }
    }

    /**
     * Get all registered UI components in the specified namespace.
     *
     * <p>Omitted optional arguments are passed as {@code null}, leaving their defaults to the API.
     *
     * @param namespace  (required)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public List<EnrichedRootUIComponent> registeredUIComponentsInNamespace(String namespace) {
        return registeredUIComponentsInNamespace(namespace, null);
    }

    /**
     * Get a specific UI component in the specified namespace.
     *
     * @param namespace  (required)
     * @param componentUID  (required)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public EnrichedRootUIComponent uiComponentInNamespace(String namespace, String componentUID) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("namespace", namespace);
            params.put("componentUID", componentUID);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Ui.getUIComponentInNamespaceWithHttpInfo({})", params);
            }
            var response = api.getUIComponentInNamespaceWithHttpInfo(namespace, componentUID);
            var data = response.getData();
            if (debugEnabled) {
                log.debug(
                        "RES: Ui.getUIComponentInNamespaceWithHttpInfo({}): status_code: {}, data: {}",
                        params,
                        response.getStatusCode(),
                        data);
            }
            return data;
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("namespace", namespace);
                params.put("componentUID", componentUID);
            }
            throw new EndpointException(this.getClass(), "getUIComponentInNamespaceWithHttpInfo", params, e);
        }
    }

    /**
     * Get all registered UI tiles.
     *
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public List<Tile> uiTiles() {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Ui.getUITilesWithHttpInfo({})", params);
            }
            var response = api.getUITilesWithHttpInfo();
            var data = response.getData();
            if (debugEnabled) {
                log.debug(
                        "RES: Ui.getUITilesWithHttpInfo({}): status_code: {}, data: {}",
                        params,
                        response.getStatusCode(),
                        data);
            }
            return data;
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
            }
            throw new EndpointException(this.getClass(), "getUITilesWithHttpInfo", params, e);
        }
    }

    /**
     * Remove a specific UI component in the specified namespace.
     *
     * @param namespace  (required)
     * @param componentUID  (required)
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public void removeUIComponentFromNamespace(String namespace, String componentUID) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("namespace", namespace);
            params.put("componentUID", componentUID);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Ui.removeUIComponentFromNamespaceWithHttpInfo({})", params);
            }
            var response = api.removeUIComponentFromNamespaceWithHttpInfo(namespace, componentUID);
            if (debugEnabled) {
                log.debug(
                        "RES: Ui.removeUIComponentFromNamespaceWithHttpInfo({}): status_code: {}",
                        params,
                        response.getStatusCode());
            }
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("namespace", namespace);
                params.put("componentUID", componentUID);
            }
            throw new EndpointException(this.getClass(), "removeUIComponentFromNamespaceWithHttpInfo", params, e);
        }
    }

    /**
     * Update a specific UI component in the specified namespace.
     *
     * @param namespace  (required)
     * @param componentUID  (required)
     * @param rootUIComponent  (optional)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public EnrichedRootUIComponent updateUIComponentInNamespace(
            String namespace, String componentUID, RootUIComponent rootUIComponent) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("namespace", namespace);
            params.put("componentUID", componentUID);
            params.put("rootUIComponent", rootUIComponent);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Ui.updateUIComponentInNamespaceWithHttpInfo({})", params);
            }
            var response = api.updateUIComponentInNamespaceWithHttpInfo(namespace, componentUID, rootUIComponent);
            var data = response.getData();
            if (debugEnabled) {
                log.debug(
                        "RES: Ui.updateUIComponentInNamespaceWithHttpInfo({}): status_code: {}, data: {}",
                        params,
                        response.getStatusCode(),
                        data);
            }
            return data;
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("namespace", namespace);
                params.put("componentUID", componentUID);
                params.put("rootUIComponent", rootUIComponent);
            }
            throw new EndpointException(this.getClass(), "updateUIComponentInNamespaceWithHttpInfo", params, e);
        }
    }

    /**
     * Update a specific UI component in the specified namespace.
     *
     * <p>Omitted optional arguments are passed as {@code null}, leaving their defaults to the API.
     *
     * @param namespace  (required)
     * @param componentUID  (required)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public EnrichedRootUIComponent updateUIComponentInNamespace(String namespace, String componentUID) {
        return updateUIComponentInNamespace(namespace, componentUID, null);
    }
}
