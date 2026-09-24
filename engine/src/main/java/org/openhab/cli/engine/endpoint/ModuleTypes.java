package org.openhab.cli.engine.endpoint;

import java.util.LinkedHashMap;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.client.ApiException;
import org.openhab.cli.client.api.ModuleTypesApi;
import org.openhab.cli.client.model.ModuleType;
import org.openhab.cli.engine.rest.ApiClient;

/**
 * Synchronous access to the openHAB operations provided by {@link ModuleTypesApi}.
 */
@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public final class ModuleTypes implements Endpoint {
    private final ModuleTypesApi api;

    /**
     * Creates an endpoint using the supplied REST client.
     *
     * @param apiClient the client providing connection and authentication settings
     */
    public ModuleTypes(ApiClient apiClient) {
        this(new ModuleTypesApi(apiClient.toNative()));
    }

    /**
     * Gets a module type corresponding to the given UID.
     *
     * @param moduleTypeUID moduleTypeUID (required)
     * @param acceptLanguage language (optional)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public ModuleType moduleTypeById(String moduleTypeUID, String acceptLanguage) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("moduleTypeUID", moduleTypeUID);
            params.put("acceptLanguage", acceptLanguage);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: ModuleTypes.getModuleTypeByIdWithHttpInfo({})", params);
            }
            var response = api.getModuleTypeByIdWithHttpInfo(moduleTypeUID, acceptLanguage);
            var data = response.getData();
            if (debugEnabled) {
                log.debug(
                        "RES: ModuleTypes.getModuleTypeByIdWithHttpInfo({}): status_code: {}, data: {}",
                        params,
                        response.getStatusCode(),
                        data);
            }
            return data;
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("moduleTypeUID", moduleTypeUID);
                params.put("acceptLanguage", acceptLanguage);
            }
            throw new EndpointException(this.getClass(), "getModuleTypeByIdWithHttpInfo", params, e);
        }
    }

    /**
     * Gets a module type corresponding to the given UID.
     *
     * <p>Omitted optional arguments are passed as {@code null}, leaving their defaults to the API.
     *
     * @param moduleTypeUID moduleTypeUID (required)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public ModuleType moduleTypeById(String moduleTypeUID) {
        return moduleTypeById(moduleTypeUID, null);
    }

    /**
     * Get all available module types.
     *
     * @param acceptLanguage language (optional)
     * @param tags tags for filtering (optional)
     * @param type filtering by action, condition or trigger (optional)
     * @param asMap returns an object of arrays by type instead of a mixed array (optional)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public List<ModuleType> moduleTypes(String acceptLanguage, String tags, String type, Boolean asMap) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("acceptLanguage", acceptLanguage);
            params.put("tags", tags);
            params.put("type", type);
            params.put("asMap", asMap);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: ModuleTypes.getModuleTypesWithHttpInfo({})", params);
            }
            var response = api.getModuleTypesWithHttpInfo(acceptLanguage, tags, type, asMap);
            var data = response.getData();
            if (debugEnabled) {
                log.debug(
                        "RES: ModuleTypes.getModuleTypesWithHttpInfo({}): status_code: {}, data: {}",
                        params,
                        response.getStatusCode(),
                        data);
            }
            return data;
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("acceptLanguage", acceptLanguage);
                params.put("tags", tags);
                params.put("type", type);
                params.put("asMap", asMap);
            }
            throw new EndpointException(this.getClass(), "getModuleTypesWithHttpInfo", params, e);
        }
    }

    /**
     * Get all available module types.
     *
     * <p>Omitted optional arguments are passed as {@code null}, leaving their defaults to the API.
     *
     * @param tags tags for filtering (optional)
     * @param type filtering by action, condition or trigger (optional)
     * @param asMap returns an object of arrays by type instead of a mixed array (optional)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public List<ModuleType> moduleTypes(String tags, String type, Boolean asMap) {
        return moduleTypes(null, tags, type, asMap);
    }

    /**
     * Get all available module types.
     *
     * <p>Omitted optional arguments are passed as {@code null}, leaving their defaults to the API.
     *
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public List<ModuleType> moduleTypes() {
        return moduleTypes(null, null, null, null);
    }
}
