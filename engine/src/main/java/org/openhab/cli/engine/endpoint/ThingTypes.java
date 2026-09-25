package org.openhab.cli.engine.endpoint;

import java.util.LinkedHashMap;
import java.util.Set;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.client.ApiException;
import org.openhab.cli.client.api.ThingTypesApi;
import org.openhab.cli.client.model.StrippedThingType;
import org.openhab.cli.client.model.ThingType;
import org.openhab.cli.engine.rest.ApiClient;

/**
 * Synchronous access to the openHAB operations provided by {@link ThingTypesApi}.
 */
@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public final class ThingTypes implements Endpoint {
    private final ThingTypesApi api;

    /**
     * Creates an endpoint using the supplied REST client.
     *
     * @param apiClient the client providing connection and authentication settings
     */
    public ThingTypes(ApiClient apiClient) {
        this(new ThingTypesApi(apiClient.toNative()));
    }

    /**
     * Gets thing type by UID.
     *
     * @param thingTypeUID thingTypeUID (required)
     * @param acceptLanguage language (optional)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public ThingType thingTypeById(String thingTypeUID, String acceptLanguage) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("thingTypeUID", thingTypeUID);
            params.put("acceptLanguage", acceptLanguage);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: ThingTypes.getThingTypeByIdWithHttpInfo({})", params);
            }
            var response = api.getThingTypeByIdWithHttpInfo(thingTypeUID, acceptLanguage);
            var data = response.getData();
            if (debugEnabled) {
                log.debug(
                        "RES: ThingTypes.getThingTypeByIdWithHttpInfo({}): status_code: {}, data: {}",
                        params,
                        response.getStatusCode(),
                        data);
            }
            return data;
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("thingTypeUID", thingTypeUID);
                params.put("acceptLanguage", acceptLanguage);
            }
            throw new EndpointException(this.getClass(), "getThingTypeByIdWithHttpInfo", params, e);
        }
    }

    /**
     * Gets thing type by UID.
     *
     * <p>Omitted optional arguments are passed as {@code null}, leaving their defaults to the API.
     *
     * @param thingTypeUID thingTypeUID (required)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public ThingType thingTypeById(String thingTypeUID) {
        return thingTypeById(thingTypeUID, null);
    }

    /**
     * Gets all available thing types without config description, channels and properties.
     *
     * @param acceptLanguage language (optional)
     * @param bindingId filter by binding Id (optional)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public Set<StrippedThingType> thingTypes(String acceptLanguage, String bindingId) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("acceptLanguage", acceptLanguage);
            params.put("bindingId", bindingId);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: ThingTypes.getThingTypesWithHttpInfo({})", params);
            }
            var response = api.getThingTypesWithHttpInfo(acceptLanguage, bindingId);
            var data = response.getData();
            if (debugEnabled) {
                log.debug(
                        "RES: ThingTypes.getThingTypesWithHttpInfo({}): status_code: {}, data: {}",
                        params,
                        response.getStatusCode(),
                        data);
            }
            return data;
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("acceptLanguage", acceptLanguage);
                params.put("bindingId", bindingId);
            }
            throw new EndpointException(this.getClass(), "getThingTypesWithHttpInfo", params, e);
        }
    }

    /**
     * Gets all available thing types without config description, channels and properties.
     *
     * <p>Omitted optional arguments are passed as {@code null}, leaving their defaults to the API.
     *
     * @param bindingId filter by binding Id (optional)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public Set<StrippedThingType> thingTypes(String bindingId) {
        return thingTypes(null, bindingId);
    }

    /**
     * Gets all available thing types without config description, channels and properties.
     *
     * <p>Omitted optional arguments are passed as {@code null}, leaving their defaults to the API.
     *
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public Set<StrippedThingType> thingTypes() {
        return thingTypes(null, null);
    }
}
