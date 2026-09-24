package org.openhab.cli.engine.endpoint;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.client.ApiException;
import org.openhab.cli.client.api.ThingsApi;
import org.openhab.cli.client.model.ConfigStatusMessage;
import org.openhab.cli.client.model.EnrichedThing;
import org.openhab.cli.client.model.Firmware;
import org.openhab.cli.client.model.FirmwareStatus;
import org.openhab.cli.client.model.Thing;
import org.openhab.cli.client.model.ThingStatusInfo;
import org.openhab.cli.engine.rest.ApiClient;

/**
 * Synchronous access to the openHAB operations provided by {@link ThingsApi}.
 */
@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public final class Things implements Endpoint {
    private final ThingsApi api;

    /**
     * Creates an endpoint using the supplied REST client.
     *
     * @param apiClient the client providing connection and authentication settings
     */
    public Things(ApiClient apiClient) {
        this(new ThingsApi(apiClient.toNative()));
    }

    /**
     * Creates a new thing and adds it to the registry.
     *
     * @param thing thing data (required)
     * @param acceptLanguage language (optional)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public EnrichedThing createThingInRegistry(Thing thing, String acceptLanguage) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("thing", thing);
            params.put("acceptLanguage", acceptLanguage);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Things.createThingInRegistryWithHttpInfo({})", params);
            }
            var response = api.createThingInRegistryWithHttpInfo(thing, acceptLanguage);
            var data = response.getData();
            if (debugEnabled) {
                log.debug(
                        "RES: Things.createThingInRegistryWithHttpInfo({}): status_code: {}, data: {}",
                        params,
                        response.getStatusCode(),
                        data);
            }
            return data;
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("thing", thing);
                params.put("acceptLanguage", acceptLanguage);
            }
            throw new EndpointException(this.getClass(), "createThingInRegistryWithHttpInfo", params, e);
        }
    }

    /**
     * Creates a new thing and adds it to the registry.
     *
     * <p>Omitted optional arguments are passed as {@code null}, leaving their defaults to the API.
     *
     * @param thing thing data (required)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public EnrichedThing createThingInRegistry(Thing thing) {
        return createThingInRegistry(thing, null);
    }

    /**
     * Sets the thing enabled status.
     *
     * @param thingUID thing (required)
     * @param acceptLanguage language (optional)
     * @param body enabled (optional)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public EnrichedThing enableThing(String thingUID, String acceptLanguage, String body) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("thingUID", thingUID);
            params.put("acceptLanguage", acceptLanguage);
            params.put("body", body);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Things.enableThingWithHttpInfo({})", params);
            }
            var response = api.enableThingWithHttpInfo(thingUID, acceptLanguage, body);
            var data = response.getData();
            if (debugEnabled) {
                log.debug(
                        "RES: Things.enableThingWithHttpInfo({}): status_code: {}, data: {}",
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
                params.put("body", body);
            }
            throw new EndpointException(this.getClass(), "enableThingWithHttpInfo", params, e);
        }
    }

    /**
     * Sets the thing enabled status.
     *
     * <p>Omitted optional arguments are passed as {@code null}, leaving their defaults to the API.
     *
     * @param thingUID thing (required)
     * @param body enabled (optional)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public EnrichedThing enableThing(String thingUID, String body) {
        return enableThing(thingUID, null, body);
    }

    /**
     * Sets the thing enabled status.
     *
     * <p>Omitted optional arguments are passed as {@code null}, leaving their defaults to the API.
     *
     * @param thingUID thing (required)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public EnrichedThing enableThing(String thingUID) {
        return enableThing(thingUID, null, null);
    }

    /**
     * Get all available firmwares for provided thing UID
     *
     * @param thingUID thingUID (required)
     * @param acceptLanguage language (optional)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public Set<Firmware> availableFirmwaresForThing(String thingUID, String acceptLanguage) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("thingUID", thingUID);
            params.put("acceptLanguage", acceptLanguage);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Things.getAvailableFirmwaresForThingWithHttpInfo({})", params);
            }
            var response = api.getAvailableFirmwaresForThingWithHttpInfo(thingUID, acceptLanguage);
            var data = response.getData();
            if (debugEnabled) {
                log.debug(
                        "RES: Things.getAvailableFirmwaresForThingWithHttpInfo({}): status_code: {}, data: {}",
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
            throw new EndpointException(this.getClass(), "getAvailableFirmwaresForThingWithHttpInfo", params, e);
        }
    }

    /**
     * Get all available firmwares for provided thing UID
     *
     * <p>Omitted optional arguments are passed as {@code null}, leaving their defaults to the API.
     *
     * @param thingUID thingUID (required)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public Set<Firmware> availableFirmwaresForThing(String thingUID) {
        return availableFirmwaresForThing(thingUID, null);
    }

    /**
     * Gets thing by UID.
     *
     * @param thingUID thingUID (required)
     * @param acceptLanguage language (optional)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public EnrichedThing thingById(String thingUID, String acceptLanguage) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("thingUID", thingUID);
            params.put("acceptLanguage", acceptLanguage);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Things.getThingByIdWithHttpInfo({})", params);
            }
            var response = api.getThingByIdWithHttpInfo(thingUID, acceptLanguage);
            var data = response.getData();
            if (debugEnabled) {
                log.debug(
                        "RES: Things.getThingByIdWithHttpInfo({}): status_code: {}, data: {}",
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
            throw new EndpointException(this.getClass(), "getThingByIdWithHttpInfo", params, e);
        }
    }

    /**
     * Gets thing by UID.
     *
     * <p>Omitted optional arguments are passed as {@code null}, leaving their defaults to the API.
     *
     * @param thingUID thingUID (required)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public EnrichedThing thingById(String thingUID) {
        return thingById(thingUID, null);
    }

    /**
     * Gets thing config status.
     *
     * @param thingUID thing (required)
     * @param acceptLanguage language (optional)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public List<ConfigStatusMessage> thingConfigStatus(String thingUID, String acceptLanguage) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("thingUID", thingUID);
            params.put("acceptLanguage", acceptLanguage);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Things.getThingConfigStatusWithHttpInfo({})", params);
            }
            var response = api.getThingConfigStatusWithHttpInfo(thingUID, acceptLanguage);
            var data = response.getData();
            if (debugEnabled) {
                log.debug(
                        "RES: Things.getThingConfigStatusWithHttpInfo({}): status_code: {}, data: {}",
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
            throw new EndpointException(this.getClass(), "getThingConfigStatusWithHttpInfo", params, e);
        }
    }

    /**
     * Gets thing config status.
     *
     * <p>Omitted optional arguments are passed as {@code null}, leaving their defaults to the API.
     *
     * @param thingUID thing (required)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public List<ConfigStatusMessage> thingConfigStatus(String thingUID) {
        return thingConfigStatus(thingUID, null);
    }

    /**
     * Gets thing&#39;s firmware status.
     *
     * @param thingUID thing (required)
     * @param acceptLanguage language (optional)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public FirmwareStatus thingFirmwareStatus(String thingUID, String acceptLanguage) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("thingUID", thingUID);
            params.put("acceptLanguage", acceptLanguage);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Things.getThingFirmwareStatusWithHttpInfo({})", params);
            }
            var response = api.getThingFirmwareStatusWithHttpInfo(thingUID, acceptLanguage);
            var data = response.getData();
            if (debugEnabled) {
                log.debug(
                        "RES: Things.getThingFirmwareStatusWithHttpInfo({}): status_code: {}, data: {}",
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
            throw new EndpointException(this.getClass(), "getThingFirmwareStatusWithHttpInfo", params, e);
        }
    }

    /**
     * Gets thing&#39;s firmware status.
     *
     * <p>Omitted optional arguments are passed as {@code null}, leaving their defaults to the API.
     *
     * @param thingUID thing (required)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public FirmwareStatus thingFirmwareStatus(String thingUID) {
        return thingFirmwareStatus(thingUID, null);
    }

    /**
     * Gets thing status.
     *
     * @param thingUID thing (required)
     * @param acceptLanguage language (optional)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public ThingStatusInfo thingStatus(String thingUID, String acceptLanguage) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("thingUID", thingUID);
            params.put("acceptLanguage", acceptLanguage);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Things.getThingStatusWithHttpInfo({})", params);
            }
            var response = api.getThingStatusWithHttpInfo(thingUID, acceptLanguage);
            var data = response.getData();
            if (debugEnabled) {
                log.debug(
                        "RES: Things.getThingStatusWithHttpInfo({}): status_code: {}, data: {}",
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
            throw new EndpointException(this.getClass(), "getThingStatusWithHttpInfo", params, e);
        }
    }

    /**
     * Gets thing status.
     *
     * <p>Omitted optional arguments are passed as {@code null}, leaving their defaults to the API.
     *
     * @param thingUID thing (required)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public ThingStatusInfo thingStatus(String thingUID) {
        return thingStatus(thingUID, null);
    }

    /**
     * Get all available things.
     *
     * @param acceptLanguage language (optional)
     * @param summary summary fields only (optional)
     * @param staticDataOnly provides a cacheable list of values not expected to change regularly and checks the If-Modified-Since header (optional, default to false)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public Set<EnrichedThing> things(String acceptLanguage, Boolean summary, Boolean staticDataOnly) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("acceptLanguage", acceptLanguage);
            params.put("summary", summary);
            params.put("staticDataOnly", staticDataOnly);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Things.getThingsWithHttpInfo({})", params);
            }
            var response = api.getThingsWithHttpInfo(acceptLanguage, summary, staticDataOnly);
            var data = response.getData();
            if (debugEnabled) {
                log.debug(
                        "RES: Things.getThingsWithHttpInfo({}): status_code: {}, data: {}",
                        params,
                        response.getStatusCode(),
                        data);
            }
            return data;
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("acceptLanguage", acceptLanguage);
                params.put("summary", summary);
                params.put("staticDataOnly", staticDataOnly);
            }
            throw new EndpointException(this.getClass(), "getThingsWithHttpInfo", params, e);
        }
    }

    /**
     * Get all available things.
     *
     * <p>Omitted optional arguments are passed as {@code null}, leaving their defaults to the API.
     *
     * @param summary summary fields only (optional)
     * @param staticDataOnly provides a cacheable list of values not expected to change regularly and checks the If-Modified-Since header (optional, default to false)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public Set<EnrichedThing> things(Boolean summary, Boolean staticDataOnly) {
        return things(null, summary, staticDataOnly);
    }

    /**
     * Get all available things.
     *
     * <p>Omitted optional arguments are passed as {@code null}, leaving their defaults to the API.
     *
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public Set<EnrichedThing> things() {
        return things(null, null, null);
    }

    /**
     * Removes a thing from the registry. Set &#39;force&#39; to __true__ if you want the thing to be removed immediately.
     *
     * @param thingUID thingUID (required)
     * @param acceptLanguage language (optional)
     * @param force force (optional, default to false)
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public void removeThingById(String thingUID, String acceptLanguage, Boolean force) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("thingUID", thingUID);
            params.put("acceptLanguage", acceptLanguage);
            params.put("force", force);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Things.removeThingByIdWithHttpInfo({})", params);
            }
            var response = api.removeThingByIdWithHttpInfo(thingUID, acceptLanguage, force);
            if (debugEnabled) {
                log.debug(
                        "RES: Things.removeThingByIdWithHttpInfo({}): status_code: {}",
                        params,
                        response.getStatusCode());
            }
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("thingUID", thingUID);
                params.put("acceptLanguage", acceptLanguage);
                params.put("force", force);
            }
            throw new EndpointException(this.getClass(), "removeThingByIdWithHttpInfo", params, e);
        }
    }

    /**
     * Removes a thing from the registry. Set &#39;force&#39; to __true__ if you want the thing to be removed immediately.
     *
     * <p>Omitted optional arguments are passed as {@code null}, leaving their defaults to the API.
     *
     * @param thingUID thingUID (required)
     * @param force force (optional, default to false)
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public void removeThingById(String thingUID, Boolean force) {
        removeThingById(thingUID, null, force);
    }

    /**
     * Removes a thing from the registry. Set &#39;force&#39; to __true__ if you want the thing to be removed immediately.
     *
     * <p>Omitted optional arguments are passed as {@code null}, leaving their defaults to the API.
     *
     * @param thingUID thingUID (required)
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public void removeThingById(String thingUID) {
        removeThingById(thingUID, null, null);
    }

    /**
     * Updates a thing.
     *
     * @param thingUID thingUID (required)
     * @param thing thing (required)
     * @param acceptLanguage language (optional)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public EnrichedThing updateThing(String thingUID, Thing thing, String acceptLanguage) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("thingUID", thingUID);
            params.put("thing", thing);
            params.put("acceptLanguage", acceptLanguage);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Things.updateThingWithHttpInfo({})", params);
            }
            var response = api.updateThingWithHttpInfo(thingUID, thing, acceptLanguage);
            var data = response.getData();
            if (debugEnabled) {
                log.debug(
                        "RES: Things.updateThingWithHttpInfo({}): status_code: {}, data: {}",
                        params,
                        response.getStatusCode(),
                        data);
            }
            return data;
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("thingUID", thingUID);
                params.put("thing", thing);
                params.put("acceptLanguage", acceptLanguage);
            }
            throw new EndpointException(this.getClass(), "updateThingWithHttpInfo", params, e);
        }
    }

    /**
     * Updates a thing.
     *
     * <p>Omitted optional arguments are passed as {@code null}, leaving their defaults to the API.
     *
     * @param thingUID thingUID (required)
     * @param thing thing (required)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public EnrichedThing updateThing(String thingUID, Thing thing) {
        return updateThing(thingUID, thing, null);
    }

    /**
     * Updates thing&#39;s configuration.
     *
     * @param thingUID thing (required)
     * @param acceptLanguage language (optional)
     * @param requestBody configuration parameters (optional)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public EnrichedThing updateThingConfig(String thingUID, String acceptLanguage, Map<String, Object> requestBody) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("thingUID", thingUID);
            params.put("acceptLanguage", acceptLanguage);
            params.put("requestBody", requestBody);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Things.updateThingConfigWithHttpInfo({})", params);
            }
            var response = api.updateThingConfigWithHttpInfo(thingUID, acceptLanguage, requestBody);
            var data = response.getData();
            if (debugEnabled) {
                log.debug(
                        "RES: Things.updateThingConfigWithHttpInfo({}): status_code: {}, data: {}",
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
                params.put("requestBody", requestBody);
            }
            throw new EndpointException(this.getClass(), "updateThingConfigWithHttpInfo", params, e);
        }
    }

    /**
     * Updates thing&#39;s configuration.
     *
     * <p>Omitted optional arguments are passed as {@code null}, leaving their defaults to the API.
     *
     * @param thingUID thing (required)
     * @param requestBody configuration parameters (optional)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public EnrichedThing updateThingConfig(String thingUID, Map<String, Object> requestBody) {
        return updateThingConfig(thingUID, null, requestBody);
    }

    /**
     * Updates thing&#39;s configuration.
     *
     * <p>Omitted optional arguments are passed as {@code null}, leaving their defaults to the API.
     *
     * @param thingUID thing (required)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public EnrichedThing updateThingConfig(String thingUID) {
        return updateThingConfig(thingUID, null, null);
    }

    /**
     * Update thing firmware.
     *
     * @param thingUID thing (required)
     * @param firmwareVersion version (required)
     * @param acceptLanguage language (optional)
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public void updateThingFirmware(String thingUID, String firmwareVersion, String acceptLanguage) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("thingUID", thingUID);
            params.put("firmwareVersion", firmwareVersion);
            params.put("acceptLanguage", acceptLanguage);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Things.updateThingFirmwareWithHttpInfo({})", params);
            }
            var response = api.updateThingFirmwareWithHttpInfo(thingUID, firmwareVersion, acceptLanguage);
            if (debugEnabled) {
                log.debug(
                        "RES: Things.updateThingFirmwareWithHttpInfo({}): status_code: {}",
                        params,
                        response.getStatusCode());
            }
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("thingUID", thingUID);
                params.put("firmwareVersion", firmwareVersion);
                params.put("acceptLanguage", acceptLanguage);
            }
            throw new EndpointException(this.getClass(), "updateThingFirmwareWithHttpInfo", params, e);
        }
    }

    /**
     * Update thing firmware.
     *
     * <p>Omitted optional arguments are passed as {@code null}, leaving their defaults to the API.
     *
     * @param thingUID thing (required)
     * @param firmwareVersion version (required)
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public void updateThingFirmware(String thingUID, String firmwareVersion) {
        updateThingFirmware(thingUID, firmwareVersion, null);
    }
}
