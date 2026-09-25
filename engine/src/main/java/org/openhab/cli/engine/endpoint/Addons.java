package org.openhab.cli.engine.endpoint;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.client.ApiException;
import org.openhab.cli.client.api.AddonsApi;
import org.openhab.cli.client.model.Addon;
import org.openhab.cli.client.model.AddonType;
import org.openhab.cli.engine.rest.ApiClient;

/**
 * Synchronous access to the openHAB operations provided by {@link AddonsApi}.
 */
@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public final class Addons {
    private final AddonsApi api;

    /**
     * Creates an endpoint using the supplied REST client.
     *
     * @param apiClient the client providing connection and authentication settings
     */
    public Addons(ApiClient apiClient) {
        this(new AddonsApi(apiClient.toNative()));
    }

    /**
     * Get add-on with given ID.
     *
     * @param addonId addon ID (required)
     * @param acceptLanguage language (optional)
     * @param serviceId service ID (optional)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public Addon addonById(String addonId, String acceptLanguage, String serviceId) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("addonId", addonId);
            params.put("acceptLanguage", acceptLanguage);
            params.put("serviceId", serviceId);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Addons.getAddonByIdWithHttpInfo({})", params);
            }
            var response = api.getAddonByIdWithHttpInfo(addonId, acceptLanguage, serviceId);
            var data = response.getData();
            if (debugEnabled) {
                log.debug(
                        "RES: Addons.getAddonByIdWithHttpInfo({}): status_code: {}, data: {}",
                        params,
                        response.getStatusCode(),
                        data);
            }
            return data;
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("addonId", addonId);
                params.put("acceptLanguage", acceptLanguage);
                params.put("serviceId", serviceId);
            }
            throw new EndpointException(this.getClass(), "getAddonByIdWithHttpInfo", params, e);
        }
    }

    /**
     * Get add-on with given ID.
     *
     * <p>Omitted optional arguments are passed as {@code null}, leaving their defaults to the API.
     *
     * @param addonId addon ID (required)
     * @param serviceId service ID (optional)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public Addon addonById(String addonId, String serviceId) {
        return addonById(addonId, null, serviceId);
    }

    /**
     * Get add-on with given ID.
     *
     * <p>Omitted optional arguments are passed as {@code null}, leaving their defaults to the API.
     *
     * @param addonId addon ID (required)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public Addon addonById(String addonId) {
        return addonById(addonId, null, null);
    }

    /**
     * Get add-on configuration for given add-on ID.
     *
     * @param addonId addon ID (required)
     * @param serviceId service ID (optional)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public String addonConfiguration(String addonId, String serviceId) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("addonId", addonId);
            params.put("serviceId", serviceId);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Addons.getAddonConfigurationWithHttpInfo({})", params);
            }
            var response = api.getAddonConfigurationWithHttpInfo(addonId, serviceId);
            var data = response.getData();
            if (debugEnabled) {
                log.debug(
                        "RES: Addons.getAddonConfigurationWithHttpInfo({}): status_code: {}, data: {}",
                        params,
                        response.getStatusCode(),
                        data);
            }
            return data;
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("addonId", addonId);
                params.put("serviceId", serviceId);
            }
            throw new EndpointException(this.getClass(), "getAddonConfigurationWithHttpInfo", params, e);
        }
    }

    /**
     * Get add-on configuration for given add-on ID.
     *
     * <p>Omitted optional arguments are passed as {@code null}, leaving their defaults to the API.
     *
     * @param addonId addon ID (required)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public String addonConfiguration(String addonId) {
        return addonConfiguration(addonId, null);
    }

    /**
     * Get add-on services.
     *
     * @param acceptLanguage language (optional)
     * @param serviceId service ID (optional)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public List<AddonType> addonServices(String acceptLanguage, String serviceId) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("acceptLanguage", acceptLanguage);
            params.put("serviceId", serviceId);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Addons.getAddonServicesWithHttpInfo({})", params);
            }
            var response = api.getAddonServicesWithHttpInfo(acceptLanguage, serviceId);
            var data = response.getData();
            if (debugEnabled) {
                log.debug(
                        "RES: Addons.getAddonServicesWithHttpInfo({}): status_code: {}, data: {}",
                        params,
                        response.getStatusCode(),
                        data);
            }
            return data;
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("acceptLanguage", acceptLanguage);
                params.put("serviceId", serviceId);
            }
            throw new EndpointException(this.getClass(), "getAddonServicesWithHttpInfo", params, e);
        }
    }

    /**
     * Get add-on services.
     *
     * <p>Omitted optional arguments are passed as {@code null}, leaving their defaults to the API.
     *
     * @param serviceId service ID (optional)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public List<AddonType> addonServices(String serviceId) {
        return addonServices(null, serviceId);
    }

    /**
     * Get add-on services.
     *
     * <p>Omitted optional arguments are passed as {@code null}, leaving their defaults to the API.
     *
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public List<AddonType> addonServices() {
        return addonServices(null, null);
    }

    /**
     * Get all add-on types.
     *
     * @param acceptLanguage language (optional)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public List<AddonType> addonTypes(String acceptLanguage) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("acceptLanguage", acceptLanguage);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Addons.getAddonTypesWithHttpInfo({})", params);
            }
            var response = api.getAddonTypesWithHttpInfo(acceptLanguage);
            var data = response.getData();
            if (debugEnabled) {
                log.debug(
                        "RES: Addons.getAddonTypesWithHttpInfo({}): status_code: {}, data: {}",
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
            throw new EndpointException(this.getClass(), "getAddonTypesWithHttpInfo", params, e);
        }
    }

    /**
     * Get all add-on types.
     *
     * <p>Omitted optional arguments are passed as {@code null}, leaving their defaults to the API.
     *
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public List<AddonType> addonTypes() {
        return addonTypes(null);
    }

    /**
     * Get all add-ons.
     *
     * @param acceptLanguage language (optional)
     * @param serviceId service ID (optional)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public List<Addon> addons(String acceptLanguage, String serviceId) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("acceptLanguage", acceptLanguage);
            params.put("serviceId", serviceId);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Addons.getAddonsWithHttpInfo({})", params);
            }
            var response = api.getAddonsWithHttpInfo(acceptLanguage, serviceId);
            var data = response.getData();
            if (debugEnabled) {
                log.debug(
                        "RES: Addons.getAddonsWithHttpInfo({}): status_code: {}, data: {}",
                        params,
                        response.getStatusCode(),
                        data);
            }
            return data;
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("acceptLanguage", acceptLanguage);
                params.put("serviceId", serviceId);
            }
            throw new EndpointException(this.getClass(), "getAddonsWithHttpInfo", params, e);
        }
    }

    /**
     * Get all add-ons.
     *
     * <p>Omitted optional arguments are passed as {@code null}, leaving their defaults to the API.
     *
     * @param serviceId service ID (optional)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public List<Addon> addons(String serviceId) {
        return addons(null, serviceId);
    }

    /**
     * Get all add-ons.
     *
     * <p>Omitted optional arguments are passed as {@code null}, leaving their defaults to the API.
     *
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public List<Addon> addons() {
        return addons(null, null);
    }

    /**
     * Get suggested add-ons to be installed.
     *
     * @param acceptLanguage language (optional)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public List<Addon> suggestedAddons(String acceptLanguage) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("acceptLanguage", acceptLanguage);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Addons.getSuggestedAddonsWithHttpInfo({})", params);
            }
            var response = api.getSuggestedAddonsWithHttpInfo(acceptLanguage);
            var data = response.getData();
            if (debugEnabled) {
                log.debug(
                        "RES: Addons.getSuggestedAddonsWithHttpInfo({}): status_code: {}, data: {}",
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
            throw new EndpointException(this.getClass(), "getSuggestedAddonsWithHttpInfo", params, e);
        }
    }

    /**
     * Get suggested add-ons to be installed.
     *
     * <p>Omitted optional arguments are passed as {@code null}, leaving their defaults to the API.
     *
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public List<Addon> suggestedAddons() {
        return suggestedAddons(null);
    }

    /**
     * Installs the add-on with the given ID.
     *
     * @param addonId addon ID (required)
     * @param serviceId service ID (optional)
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public void installAddonById(String addonId, String serviceId) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("addonId", addonId);
            params.put("serviceId", serviceId);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Addons.installAddonByIdWithHttpInfo({})", params);
            }
            var response = api.installAddonByIdWithHttpInfo(addonId, serviceId);
            if (debugEnabled) {
                log.debug(
                        "RES: Addons.installAddonByIdWithHttpInfo({}): status_code: {}",
                        params,
                        response.getStatusCode());
            }
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("addonId", addonId);
                params.put("serviceId", serviceId);
            }
            throw new EndpointException(this.getClass(), "installAddonByIdWithHttpInfo", params, e);
        }
    }

    /**
     * Installs the add-on with the given ID.
     *
     * <p>Omitted optional arguments are passed as {@code null}, leaving their defaults to the API.
     *
     * @param addonId addon ID (required)
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public void installAddonById(String addonId) {
        installAddonById(addonId, null);
    }

    /**
     * Installs the add-on from the given URL.
     *
     * @param url addon install URL (required)
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public void installAddonFromURL(String url) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("url", url);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Addons.installAddonFromURLWithHttpInfo({})", params);
            }
            var response = api.installAddonFromURLWithHttpInfo(url);
            if (debugEnabled) {
                log.debug(
                        "RES: Addons.installAddonFromURLWithHttpInfo({}): status_code: {}",
                        params,
                        response.getStatusCode());
            }
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("url", url);
            }
            throw new EndpointException(this.getClass(), "installAddonFromURLWithHttpInfo", params, e);
        }
    }

    /**
     * Uninstalls the add-on with the given ID.
     *
     * @param addonId addon ID (required)
     * @param serviceId service ID (optional)
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public void uninstallAddon(String addonId, String serviceId) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("addonId", addonId);
            params.put("serviceId", serviceId);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Addons.uninstallAddonWithHttpInfo({})", params);
            }
            var response = api.uninstallAddonWithHttpInfo(addonId, serviceId);
            if (debugEnabled) {
                log.debug(
                        "RES: Addons.uninstallAddonWithHttpInfo({}): status_code: {}",
                        params,
                        response.getStatusCode());
            }
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("addonId", addonId);
                params.put("serviceId", serviceId);
            }
            throw new EndpointException(this.getClass(), "uninstallAddonWithHttpInfo", params, e);
        }
    }

    /**
     * Uninstalls the add-on with the given ID.
     *
     * <p>Omitted optional arguments are passed as {@code null}, leaving their defaults to the API.
     *
     * @param addonId addon ID (required)
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public void uninstallAddon(String addonId) {
        uninstallAddon(addonId, null);
    }

    /**
     * Updates an add-on configuration for given ID and returns the old configuration.
     *
     * @param addonId Add-on id (required)
     * @param serviceId service ID (optional)
     * @param requestBody  (optional)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public String updateAddonConfiguration(String addonId, String serviceId, Map<String, Object> requestBody) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("addonId", addonId);
            params.put("serviceId", serviceId);
            params.put("requestBody", requestBody);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Addons.updateAddonConfigurationWithHttpInfo({})", params);
            }
            var response = api.updateAddonConfigurationWithHttpInfo(addonId, serviceId, requestBody);
            var data = response.getData();
            if (debugEnabled) {
                log.debug(
                        "RES: Addons.updateAddonConfigurationWithHttpInfo({}): status_code: {}, data: {}",
                        params,
                        response.getStatusCode(),
                        data);
            }
            return data;
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("addonId", addonId);
                params.put("serviceId", serviceId);
                params.put("requestBody", requestBody);
            }
            throw new EndpointException(this.getClass(), "updateAddonConfigurationWithHttpInfo", params, e);
        }
    }

    /**
     * Updates an add-on configuration for given ID and returns the old configuration.
     *
     * <p>Omitted optional arguments are passed as {@code null}, leaving their defaults to the API.
     *
     * @param addonId Add-on id (required)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public String updateAddonConfiguration(String addonId) {
        return updateAddonConfiguration(addonId, null, null);
    }
}
