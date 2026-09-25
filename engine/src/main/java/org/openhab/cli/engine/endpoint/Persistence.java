package org.openhab.cli.engine.endpoint;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.client.ApiException;
import org.openhab.cli.client.api.PersistenceApi;
import org.openhab.cli.client.model.ItemHistory;
import org.openhab.cli.client.model.PersistenceItemInfo;
import org.openhab.cli.client.model.PersistenceService;
import org.openhab.cli.client.model.PersistenceServiceConfiguration;
import org.openhab.cli.client.model.PersistenceServiceProblem;
import org.openhab.cli.engine.rest.ApiClient;

/**
 * Synchronous access to the openHAB operations provided by {@link PersistenceApi}.
 */
@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public final class Persistence implements Endpoint {
    private final PersistenceApi api;

    /**
     * Creates an endpoint using the supplied REST client.
     *
     * @param apiClient the client providing connection and authentication settings
     */
    public Persistence(ApiClient apiClient) {
        this(new PersistenceApi(apiClient.toNative()));
    }

    /**
     * Deletes Item persistence data from a specific persistence service in a given time range.
     *
     * @param serviceId Id of the persistence service. (required)
     * @param itemName The Item name. (required)
     * @param starttime Start of the time range to be deleted. [yyyy-MM-dd&#39;T&#39;HH:mm:ss.SSSZ] (required)
     * @param endtime End of the time range to be deleted. [yyyy-MM-dd&#39;T&#39;HH:mm:ss.SSSZ] (required)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public List<String> deleteItemFromPersistenceService(
            String serviceId, String itemName, String starttime, String endtime) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("serviceId", serviceId);
            params.put("itemName", itemName);
            params.put("starttime", starttime);
            params.put("endtime", endtime);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Persistence.deleteItemFromPersistenceServiceWithHttpInfo({})", params);
            }
            var response = api.deleteItemFromPersistenceServiceWithHttpInfo(serviceId, itemName, starttime, endtime);
            var data = response.getData();
            if (debugEnabled) {
                log.debug(
                        "RES: Persistence.deleteItemFromPersistenceServiceWithHttpInfo({}): status_code: {}, data: {}",
                        params,
                        response.getStatusCode(),
                        data);
            }
            return data;
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("serviceId", serviceId);
                params.put("itemName", itemName);
                params.put("starttime", starttime);
                params.put("endtime", endtime);
            }
            throw new EndpointException(this.getClass(), "deleteItemFromPersistenceServiceWithHttpInfo", params, e);
        }
    }

    /**
     * Deletes a persistence service configuration.
     *
     * @param serviceId Id of the persistence service. (required)
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public void deletePersistenceServiceConfiguration(String serviceId) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("serviceId", serviceId);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Persistence.deletePersistenceServiceConfigurationWithHttpInfo({})", params);
            }
            var response = api.deletePersistenceServiceConfigurationWithHttpInfo(serviceId);
            if (debugEnabled) {
                log.debug(
                        "RES: Persistence.deletePersistenceServiceConfigurationWithHttpInfo({}): status_code: {}",
                        params,
                        response.getStatusCode());
            }
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("serviceId", serviceId);
            }
            throw new EndpointException(
                    this.getClass(), "deletePersistenceServiceConfigurationWithHttpInfo", params, e);
        }
    }

    /**
     * Gets Item persistence data from the persistence service.
     *
     * @param itemName The Item name (required)
     * @param acceptLanguage language (optional)
     * @param serviceId Id of the persistence service. If not provided the default service will be used (optional)
     * @param starttime Start time of the data to return. Will default to 1 day before endtime. [yyyy-MM-dd&#39;T&#39;HH:mm:ss.SSSZ] (optional)
     * @param endtime End time of the data to return. Will default to current time. [yyyy-MM-dd&#39;T&#39;HH:mm:ss.SSSZ] (optional)
     * @param page Page number of data to return. This parameter will enable paging. (optional)
     * @param pagelength The length of each page. (optional)
     * @param boundary Gets one value before and after the requested period. (optional)
     * @param itemState Adds the current Item state into the requested period (the Item state will be before or at the endtime) (optional)
     * @param displayState If set to true, formatting from the state description is applied to the values. For QuantityType states, the value in the display unit as defined by the pattern, is returned. (optional)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public ItemHistory itemDataFromPersistenceService(
            String itemName,
            String acceptLanguage,
            String serviceId,
            String starttime,
            String endtime,
            Integer page,
            Integer pagelength,
            Boolean boundary,
            Boolean itemState,
            Boolean displayState) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("itemName", itemName);
            params.put("acceptLanguage", acceptLanguage);
            params.put("serviceId", serviceId);
            params.put("starttime", starttime);
            params.put("endtime", endtime);
            params.put("page", page);
            params.put("pagelength", pagelength);
            params.put("boundary", boundary);
            params.put("itemState", itemState);
            params.put("displayState", displayState);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Persistence.getItemDataFromPersistenceServiceWithHttpInfo({})", params);
            }
            var response = api.getItemDataFromPersistenceServiceWithHttpInfo(
                    itemName,
                    acceptLanguage,
                    serviceId,
                    starttime,
                    endtime,
                    page,
                    pagelength,
                    boundary,
                    itemState,
                    displayState);
            var data = response.getData();
            if (debugEnabled) {
                log.debug(
                        "RES: Persistence.getItemDataFromPersistenceServiceWithHttpInfo({}): status_code: {}, data: {}",
                        params,
                        response.getStatusCode(),
                        data);
            }
            return data;
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("itemName", itemName);
                params.put("acceptLanguage", acceptLanguage);
                params.put("serviceId", serviceId);
                params.put("starttime", starttime);
                params.put("endtime", endtime);
                params.put("page", page);
                params.put("pagelength", pagelength);
                params.put("boundary", boundary);
                params.put("itemState", itemState);
                params.put("displayState", displayState);
            }
            throw new EndpointException(this.getClass(), "getItemDataFromPersistenceServiceWithHttpInfo", params, e);
        }
    }

    /**
     * Gets Item persistence data from the persistence service.
     *
     * <p>Omitted optional arguments are passed as {@code null}, leaving their defaults to the API.
     *
     * @param itemName The Item name (required)
     * @param serviceId Id of the persistence service. If not provided the default service will be used (optional)
     * @param starttime Start time of the data to return. Will default to 1 day before endtime. [yyyy-MM-dd&#39;T&#39;HH:mm:ss.SSSZ] (optional)
     * @param endtime End time of the data to return. Will default to current time. [yyyy-MM-dd&#39;T&#39;HH:mm:ss.SSSZ] (optional)
     * @param page Page number of data to return. This parameter will enable paging. (optional)
     * @param pagelength The length of each page. (optional)
     * @param boundary Gets one value before and after the requested period. (optional)
     * @param itemState Adds the current Item state into the requested period (the Item state will be before or at the endtime) (optional)
     * @param displayState If set to true, formatting from the state description is applied to the values. For QuantityType states, the value in the display unit as defined by the pattern, is returned. (optional)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public ItemHistory itemDataFromPersistenceService(
            String itemName,
            String serviceId,
            String starttime,
            String endtime,
            Integer page,
            Integer pagelength,
            Boolean boundary,
            Boolean itemState,
            Boolean displayState) {
        return itemDataFromPersistenceService(
                itemName, null, serviceId, starttime, endtime, page, pagelength, boundary, itemState, displayState);
    }

    /**
     * Gets Item persistence data from the persistence service.
     *
     * <p>Omitted optional arguments are passed as {@code null}, leaving their defaults to the API.
     *
     * @param itemName The Item name (required)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public ItemHistory itemDataFromPersistenceService(String itemName) {
        return itemDataFromPersistenceService(itemName, null, null, null, null, null, null, null, null, null);
    }

    /**
     * Gets a list of stored Items available via a specific persistence service with their stored name.
     *
     * @param serviceId Id of the persistence service. If not provided the default service will be used (optional)
     * @param itemName An Item name, if provided response will only contain information for this Item (optional)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public Set<PersistenceItemInfo> itemsForPersistenceService(String serviceId, String itemName) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("serviceId", serviceId);
            params.put("itemName", itemName);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Persistence.getItemsForPersistenceServiceWithHttpInfo({})", params);
            }
            var response = api.getItemsForPersistenceServiceWithHttpInfo(serviceId, itemName);
            var data = response.getData();
            if (debugEnabled) {
                log.debug(
                        "RES: Persistence.getItemsForPersistenceServiceWithHttpInfo({}): status_code: {}, data: {}",
                        params,
                        response.getStatusCode(),
                        data);
            }
            return data;
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("serviceId", serviceId);
                params.put("itemName", itemName);
            }
            throw new EndpointException(this.getClass(), "getItemsForPersistenceServiceWithHttpInfo", params, e);
        }
    }

    /**
     * Gets a list of stored Items available via a specific persistence service with their stored name.
     *
     * <p>Omitted optional arguments are passed as {@code null}, leaving their defaults to the API.
     *
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public Set<PersistenceItemInfo> itemsForPersistenceService() {
        return itemsForPersistenceService(null, null);
    }

    /**
     * Gets configuration problems with persistence services.
     *
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public Set<PersistenceServiceProblem> persistenceHealth() {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Persistence.getPersistenceHealthWithHttpInfo({})", params);
            }
            var response = api.getPersistenceHealthWithHttpInfo();
            var data = response.getData();
            if (debugEnabled) {
                log.debug(
                        "RES: Persistence.getPersistenceHealthWithHttpInfo({}): status_code: {}, data: {}",
                        params,
                        response.getStatusCode(),
                        data);
            }
            return data;
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
            }
            throw new EndpointException(this.getClass(), "getPersistenceHealthWithHttpInfo", params, e);
        }
    }

    /**
     * Gets a persistence service configuration.
     *
     * @param serviceId Id of the persistence service. (required)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public PersistenceServiceConfiguration persistenceServiceConfiguration(String serviceId) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("serviceId", serviceId);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Persistence.getPersistenceServiceConfigurationWithHttpInfo({})", params);
            }
            var response = api.getPersistenceServiceConfigurationWithHttpInfo(serviceId);
            var data = response.getData();
            if (debugEnabled) {
                log.debug(
                        "RES: Persistence.getPersistenceServiceConfigurationWithHttpInfo({}): status_code: {}, data: {}",
                        params,
                        response.getStatusCode(),
                        data);
            }
            return data;
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("serviceId", serviceId);
            }
            throw new EndpointException(this.getClass(), "getPersistenceServiceConfigurationWithHttpInfo", params, e);
        }
    }

    /**
     * Gets a persistence service suggested strategies.
     *
     * @param serviceId Id of the persistence service. (optional)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public Set<String> persistenceServiceStrategySuggestions(String serviceId) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("serviceId", serviceId);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Persistence.getPersistenceServiceStrategySuggestionsWithHttpInfo({})", params);
            }
            var response = api.getPersistenceServiceStrategySuggestionsWithHttpInfo(serviceId);
            var data = response.getData();
            if (debugEnabled) {
                log.debug(
                        "RES: Persistence.getPersistenceServiceStrategySuggestionsWithHttpInfo({}): status_code: {}, data: {}",
                        params,
                        response.getStatusCode(),
                        data);
            }
            return data;
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("serviceId", serviceId);
            }
            throw new EndpointException(
                    this.getClass(), "getPersistenceServiceStrategySuggestionsWithHttpInfo", params, e);
        }
    }

    /**
     * Gets a persistence service suggested strategies.
     *
     * <p>Omitted optional arguments are passed as {@code null}, leaving their defaults to the API.
     *
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public Set<String> persistenceServiceStrategySuggestions() {
        return persistenceServiceStrategySuggestions(null);
    }

    /**
     * Gets a list of persistence services.
     *
     * @param acceptLanguage language (optional)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public List<PersistenceService> persistenceServices(String acceptLanguage) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("acceptLanguage", acceptLanguage);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Persistence.getPersistenceServicesWithHttpInfo({})", params);
            }
            var response = api.getPersistenceServicesWithHttpInfo(acceptLanguage);
            var data = response.getData();
            if (debugEnabled) {
                log.debug(
                        "RES: Persistence.getPersistenceServicesWithHttpInfo({}): status_code: {}, data: {}",
                        params,
                        response.getStatusCode(),
                        data);
            }
            return data;
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("acceptLanguage", acceptLanguage);
            }
            throw new EndpointException(this.getClass(), "getPersistenceServicesWithHttpInfo", params, e);
        }
    }

    /**
     * Gets a list of persistence services.
     *
     * <p>Omitted optional arguments are passed as {@code null}, leaving their defaults to the API.
     *
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public List<PersistenceService> persistenceServices() {
        return persistenceServices(null);
    }

    /**
     * Sets a persistence service configuration.
     *
     * @param serviceId Id of the persistence service. (required)
     * @param persistenceServiceConfiguration service configuration (required)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public PersistenceServiceConfiguration putPersistenceServiceConfiguration(
            String serviceId, PersistenceServiceConfiguration persistenceServiceConfiguration) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("serviceId", serviceId);
            params.put("persistenceServiceConfiguration", persistenceServiceConfiguration);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Persistence.putPersistenceServiceConfigurationWithHttpInfo({})", params);
            }
            var response =
                    api.putPersistenceServiceConfigurationWithHttpInfo(serviceId, persistenceServiceConfiguration);
            var data = response.getData();
            if (debugEnabled) {
                log.debug(
                        "RES: Persistence.putPersistenceServiceConfigurationWithHttpInfo({}): status_code: {}, data: {}",
                        params,
                        response.getStatusCode(),
                        data);
            }
            return data;
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("serviceId", serviceId);
                params.put("persistenceServiceConfiguration", persistenceServiceConfiguration);
            }
            throw new EndpointException(this.getClass(), "putPersistenceServiceConfigurationWithHttpInfo", params, e);
        }
    }

    /**
     * Stores Item persistence data into the persistence service.
     *
     * @param itemName The Item name. (required)
     * @param time Time of the data to be stored. Will default to current time. [yyyy-MM-dd&#39;T&#39;HH:mm:ss.SSSZ] (required)
     * @param state The state to store. (required)
     * @param serviceId Id of the persistence service. If not provided the default service will be used (optional)
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public void storeItemDataInPersistenceService(String itemName, String time, String state, String serviceId) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("itemName", itemName);
            params.put("time", time);
            params.put("state", state);
            params.put("serviceId", serviceId);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Persistence.storeItemDataInPersistenceServiceWithHttpInfo({})", params);
            }
            var response = api.storeItemDataInPersistenceServiceWithHttpInfo(itemName, time, state, serviceId);
            if (debugEnabled) {
                log.debug(
                        "RES: Persistence.storeItemDataInPersistenceServiceWithHttpInfo({}): status_code: {}",
                        params,
                        response.getStatusCode());
            }
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("itemName", itemName);
                params.put("time", time);
                params.put("state", state);
                params.put("serviceId", serviceId);
            }
            throw new EndpointException(this.getClass(), "storeItemDataInPersistenceServiceWithHttpInfo", params, e);
        }
    }

    /**
     * Stores Item persistence data into the persistence service.
     *
     * <p>Omitted optional arguments are passed as {@code null}, leaving their defaults to the API.
     *
     * @param itemName The Item name. (required)
     * @param time Time of the data to be stored. Will default to current time. [yyyy-MM-dd&#39;T&#39;HH:mm:ss.SSSZ] (required)
     * @param state The state to store. (required)
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public void storeItemDataInPersistenceService(String itemName, String time, String state) {
        storeItemDataInPersistenceService(itemName, time, state, null);
    }
}
