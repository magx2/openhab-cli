package org.openhab.cli.engine.endpoint;

import java.util.LinkedHashMap;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.client.ApiException;
import org.openhab.cli.client.api.ServicesApi;
import org.openhab.cli.client.model.ConfigurableService;
import org.openhab.cli.engine.rest.ApiClient;

/**
 * Synchronous access to the openHAB operations provided by {@link ServicesApi}.
 */
@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public final class Services {
    private final ServicesApi api;

    /**
     * Creates an endpoint using the supplied REST client.
     *
     * @param apiClient the client providing connection and authentication settings
     */
    public Services(ApiClient apiClient) {
        this(new ServicesApi(apiClient.toNative()));
    }

    /**
     * Deletes a service configuration for given service ID and returns the old configuration.
     *
     * @param serviceId service ID (required)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public Object deleteServiceConfig(String serviceId) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("serviceId", serviceId);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Services.deleteServiceConfigWithHttpInfo({})", params);
            }
            var response = api.deleteServiceConfigWithHttpInfo(serviceId);
            var data = response.getData();
            if (debugEnabled) {
                log.debug(
                        "RES: Services.deleteServiceConfigWithHttpInfo({}): status_code: {}, data: {}",
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
            throw new EndpointException(this.getClass(), "deleteServiceConfigWithHttpInfo", params, e);
        }
    }

    /**
     * Get service configuration for given service ID.
     *
     * @param serviceId service ID (required)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public Object serviceConfig(String serviceId) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("serviceId", serviceId);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Services.getServiceConfigWithHttpInfo({})", params);
            }
            var response = api.getServiceConfigWithHttpInfo(serviceId);
            var data = response.getData();
            if (debugEnabled) {
                log.debug(
                        "RES: Services.getServiceConfigWithHttpInfo({}): status_code: {}, data: {}",
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
            throw new EndpointException(this.getClass(), "getServiceConfigWithHttpInfo", params, e);
        }
    }

    /**
     * Get existing multiple context service configurations for the given factory PID.
     *
     * @param serviceId service ID (required)
     * @param acceptLanguage language (optional)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public List<ConfigurableService> serviceContext(String serviceId, String acceptLanguage) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("serviceId", serviceId);
            params.put("acceptLanguage", acceptLanguage);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Services.getServiceContextWithHttpInfo({})", params);
            }
            var response = api.getServiceContextWithHttpInfo(serviceId, acceptLanguage);
            var data = response.getData();
            if (debugEnabled) {
                log.debug(
                        "RES: Services.getServiceContextWithHttpInfo({}): status_code: {}, data: {}",
                        params,
                        response.getStatusCode(),
                        data);
            }
            return data;
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("serviceId", serviceId);
                params.put("acceptLanguage", acceptLanguage);
            }
            throw new EndpointException(this.getClass(), "getServiceContextWithHttpInfo", params, e);
        }
    }

    /**
     * Get existing multiple context service configurations for the given factory PID.
     *
     * <p>Omitted optional arguments are passed as {@code null}, leaving their defaults to the API.
     *
     * @param serviceId service ID (required)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public List<ConfigurableService> serviceContext(String serviceId) {
        return serviceContext(serviceId, null);
    }

    /**
     * Get all configurable services.
     *
     * @param acceptLanguage language (optional)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public List<ConfigurableService> services(String acceptLanguage) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("acceptLanguage", acceptLanguage);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Services.getServicesWithHttpInfo({})", params);
            }
            var response = api.getServicesWithHttpInfo(acceptLanguage);
            var data = response.getData();
            if (debugEnabled) {
                log.debug(
                        "RES: Services.getServicesWithHttpInfo({}): status_code: {}, data: {}",
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
            throw new EndpointException(this.getClass(), "getServicesWithHttpInfo", params, e);
        }
    }

    /**
     * Get all configurable services.
     *
     * <p>Omitted optional arguments are passed as {@code null}, leaving their defaults to the API.
     *
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public List<ConfigurableService> services() {
        return services(null);
    }

    /**
     * Get configurable service for given service ID.
     *
     * @param serviceId service ID (required)
     * @param acceptLanguage language (optional)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public ConfigurableService servicesById(String serviceId, String acceptLanguage) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("serviceId", serviceId);
            params.put("acceptLanguage", acceptLanguage);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Services.getServicesByIdWithHttpInfo({})", params);
            }
            var response = api.getServicesByIdWithHttpInfo(serviceId, acceptLanguage);
            var data = response.getData();
            if (debugEnabled) {
                log.debug(
                        "RES: Services.getServicesByIdWithHttpInfo({}): status_code: {}, data: {}",
                        params,
                        response.getStatusCode(),
                        data);
            }
            return data;
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("serviceId", serviceId);
                params.put("acceptLanguage", acceptLanguage);
            }
            throw new EndpointException(this.getClass(), "getServicesByIdWithHttpInfo", params, e);
        }
    }

    /**
     * Get configurable service for given service ID.
     *
     * <p>Omitted optional arguments are passed as {@code null}, leaving their defaults to the API.
     *
     * @param serviceId service ID (required)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public ConfigurableService servicesById(String serviceId) {
        return servicesById(serviceId, null);
    }

    /**
     * Updates a service configuration for given service ID and returns the old configuration.
     *
     * @param serviceId service ID (required)
     * @param acceptLanguage language (optional)
     * @param body  (optional)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public Object updateServiceConfig(String serviceId, String acceptLanguage, Object body) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("serviceId", serviceId);
            params.put("acceptLanguage", acceptLanguage);
            params.put("body", body);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Services.updateServiceConfigWithHttpInfo({})", params);
            }
            var response = api.updateServiceConfigWithHttpInfo(serviceId, acceptLanguage, body);
            var data = response.getData();
            if (debugEnabled) {
                log.debug(
                        "RES: Services.updateServiceConfigWithHttpInfo({}): status_code: {}, data: {}",
                        params,
                        response.getStatusCode(),
                        data);
            }
            return data;
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("serviceId", serviceId);
                params.put("acceptLanguage", acceptLanguage);
                params.put("body", body);
            }
            throw new EndpointException(this.getClass(), "updateServiceConfigWithHttpInfo", params, e);
        }
    }

    /**
     * Updates a service configuration for given service ID and returns the old configuration.
     *
     * <p>Omitted optional arguments are passed as {@code null}, leaving their defaults to the API.
     *
     * @param serviceId service ID (required)
     * @param body  (optional)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public Object updateServiceConfig(String serviceId, Object body) {
        return updateServiceConfig(serviceId, null, body);
    }

    /**
     * Updates a service configuration for given service ID and returns the old configuration.
     *
     * <p>Omitted optional arguments are passed as {@code null}, leaving their defaults to the API.
     *
     * @param serviceId service ID (required)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public Object updateServiceConfig(String serviceId) {
        return updateServiceConfig(serviceId, null, null);
    }
}
