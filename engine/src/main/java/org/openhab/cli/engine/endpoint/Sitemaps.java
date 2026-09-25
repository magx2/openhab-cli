package org.openhab.cli.engine.endpoint;

import java.util.LinkedHashMap;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.client.ApiException;
import org.openhab.cli.client.api.SitemapsApi;
import org.openhab.cli.client.model.EnrichedSitemapDefinition;
import org.openhab.cli.client.model.Sitemap;
import org.openhab.cli.client.model.SitemapDefinition;
import org.openhab.cli.client.model.SitemapPage;
import org.openhab.cli.engine.rest.ApiClient;

/**
 * Synchronous access to the openHAB operations provided by {@link SitemapsApi}.
 */
@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public final class Sitemaps {
    private final SitemapsApi api;

    /**
     * Creates an endpoint using the supplied REST client.
     *
     * @param apiClient the client providing connection and authentication settings
     */
    public Sitemaps(ApiClient apiClient) {
        this(new SitemapsApi(apiClient.toNative()));
    }

    /**
     * Adds a new sitemap to the registry or updates the existing sitemap.
     *
     * @param sitemapname sitemap name (required)
     * @param sitemapDefinition sitemap data (required)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public SitemapDefinition addOrUpdateSitemapInRegistry(String sitemapname, SitemapDefinition sitemapDefinition) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("sitemapname", sitemapname);
            params.put("sitemapDefinition", sitemapDefinition);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Sitemaps.addOrUpdateSitemapInRegistryWithHttpInfo({})", params);
            }
            var response = api.addOrUpdateSitemapInRegistryWithHttpInfo(sitemapname, sitemapDefinition);
            var data = response.getData();
            if (debugEnabled) {
                log.debug(
                        "RES: Sitemaps.addOrUpdateSitemapInRegistryWithHttpInfo({}): status_code: {}, data: {}",
                        params,
                        response.getStatusCode(),
                        data);
            }
            return data;
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("sitemapname", sitemapname);
                params.put("sitemapDefinition", sitemapDefinition);
            }
            throw new EndpointException(this.getClass(), "addOrUpdateSitemapInRegistryWithHttpInfo", params, e);
        }
    }

    /**
     * Creates a sitemap event subscription.
     *
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public void createSitemapEventSubscription() {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Sitemaps.createSitemapEventSubscriptionWithHttpInfo({})", params);
            }
            var response = api.createSitemapEventSubscriptionWithHttpInfo();
            if (debugEnabled) {
                log.debug(
                        "RES: Sitemaps.createSitemapEventSubscriptionWithHttpInfo({}): status_code: {}",
                        params,
                        response.getStatusCode());
            }
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
            }
            throw new EndpointException(this.getClass(), "createSitemapEventSubscriptionWithHttpInfo", params, e);
        }
    }

    /**
     * Get sitemap by name.
     *
     * @param sitemapname sitemap name (required)
     * @param acceptLanguage language (optional)
     * @param includeHidden include hidden widgets (optional)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public Sitemap sitemapByName(String sitemapname, String acceptLanguage, Boolean includeHidden) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("sitemapname", sitemapname);
            params.put("acceptLanguage", acceptLanguage);
            params.put("includeHidden", includeHidden);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Sitemaps.getSitemapByNameWithHttpInfo({})", params);
            }
            var response = api.getSitemapByNameWithHttpInfo(sitemapname, acceptLanguage, includeHidden);
            var data = response.getData();
            if (debugEnabled) {
                log.debug(
                        "RES: Sitemaps.getSitemapByNameWithHttpInfo({}): status_code: {}, data: {}",
                        params,
                        response.getStatusCode(),
                        data);
            }
            return data;
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("sitemapname", sitemapname);
                params.put("acceptLanguage", acceptLanguage);
                params.put("includeHidden", includeHidden);
            }
            throw new EndpointException(this.getClass(), "getSitemapByNameWithHttpInfo", params, e);
        }
    }

    /**
     * Get sitemap by name.
     *
     * <p>Omitted optional arguments are passed as {@code null}, leaving their defaults to the API.
     *
     * @param sitemapname sitemap name (required)
     * @param includeHidden include hidden widgets (optional)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public Sitemap sitemapByName(String sitemapname, Boolean includeHidden) {
        return sitemapByName(sitemapname, null, includeHidden);
    }

    /**
     * Get sitemap by name.
     *
     * <p>Omitted optional arguments are passed as {@code null}, leaving their defaults to the API.
     *
     * @param sitemapname sitemap name (required)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public Sitemap sitemapByName(String sitemapname) {
        return sitemapByName(sitemapname, null, null);
    }

    /**
     * Get sitemap definition by name.
     *
     * @param sitemapname sitemap name (required)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public EnrichedSitemapDefinition sitemapDefinitionByName(String sitemapname) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("sitemapname", sitemapname);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Sitemaps.getSitemapDefinitionByNameWithHttpInfo({})", params);
            }
            var response = api.getSitemapDefinitionByNameWithHttpInfo(sitemapname);
            var data = response.getData();
            if (debugEnabled) {
                log.debug(
                        "RES: Sitemaps.getSitemapDefinitionByNameWithHttpInfo({}): status_code: {}, data: {}",
                        params,
                        response.getStatusCode(),
                        data);
            }
            return data;
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("sitemapname", sitemapname);
            }
            throw new EndpointException(this.getClass(), "getSitemapDefinitionByNameWithHttpInfo", params, e);
        }
    }

    /**
     * Get all available sitemap definitions.
     *
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public List<EnrichedSitemapDefinition> sitemapDefinitions() {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Sitemaps.getSitemapDefinitionsWithHttpInfo({})", params);
            }
            var response = api.getSitemapDefinitionsWithHttpInfo();
            var data = response.getData();
            if (debugEnabled) {
                log.debug(
                        "RES: Sitemaps.getSitemapDefinitionsWithHttpInfo({}): status_code: {}, data: {}",
                        params,
                        response.getStatusCode(),
                        data);
            }
            return data;
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
            }
            throw new EndpointException(this.getClass(), "getSitemapDefinitionsWithHttpInfo", params, e);
        }
    }

    /**
     * Get sitemap events for a whole sitemap. Not recommended due to potentially high traffic.
     *
     * @param subscriptionid subscription id (required)
     * @param sitemap sitemap name (optional)
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public void sitemapEvents(String subscriptionid, String sitemap) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("subscriptionid", subscriptionid);
            params.put("sitemap", sitemap);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Sitemaps.getSitemapEventsWithHttpInfo({})", params);
            }
            var response = api.getSitemapEventsWithHttpInfo(subscriptionid, sitemap);
            if (debugEnabled) {
                log.debug(
                        "RES: Sitemaps.getSitemapEventsWithHttpInfo({}): status_code: {}",
                        params,
                        response.getStatusCode());
            }
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("subscriptionid", subscriptionid);
                params.put("sitemap", sitemap);
            }
            throw new EndpointException(this.getClass(), "getSitemapEventsWithHttpInfo", params, e);
        }
    }

    /**
     * Get sitemap events for a whole sitemap. Not recommended due to potentially high traffic.
     *
     * <p>Omitted optional arguments are passed as {@code null}, leaving their defaults to the API.
     *
     * @param subscriptionid subscription id (required)
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public void sitemapEvents(String subscriptionid) {
        sitemapEvents(subscriptionid, null);
    }

    /**
     * Get sitemap events.
     *
     * @param subscriptionid subscription id (required)
     * @param sitemap sitemap name (optional)
     * @param pageid page id (optional)
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public void sitemapEvents1(String subscriptionid, String sitemap, String pageid) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("subscriptionid", subscriptionid);
            params.put("sitemap", sitemap);
            params.put("pageid", pageid);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Sitemaps.getSitemapEvents1WithHttpInfo({})", params);
            }
            var response = api.getSitemapEvents1WithHttpInfo(subscriptionid, sitemap, pageid);
            if (debugEnabled) {
                log.debug(
                        "RES: Sitemaps.getSitemapEvents1WithHttpInfo({}): status_code: {}",
                        params,
                        response.getStatusCode());
            }
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("subscriptionid", subscriptionid);
                params.put("sitemap", sitemap);
                params.put("pageid", pageid);
            }
            throw new EndpointException(this.getClass(), "getSitemapEvents1WithHttpInfo", params, e);
        }
    }

    /**
     * Get sitemap events.
     *
     * <p>Omitted optional arguments are passed as {@code null}, leaving their defaults to the API.
     *
     * @param subscriptionid subscription id (required)
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public void sitemapEvents1(String subscriptionid) {
        sitemapEvents1(subscriptionid, null, null);
    }

    /**
     * Get all available sitemaps.
     *
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public List<Sitemap> sitemaps() {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Sitemaps.getSitemapsWithHttpInfo({})", params);
            }
            var response = api.getSitemapsWithHttpInfo();
            var data = response.getData();
            if (debugEnabled) {
                log.debug(
                        "RES: Sitemaps.getSitemapsWithHttpInfo({}): status_code: {}, data: {}",
                        params,
                        response.getStatusCode(),
                        data);
            }
            return data;
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
            }
            throw new EndpointException(this.getClass(), "getSitemapsWithHttpInfo", params, e);
        }
    }

    /**
     * Polls the data for one page of a sitemap.
     *
     * @param sitemapname sitemap name (required)
     * @param pageid page id (required)
     * @param acceptLanguage language (optional)
     * @param subscriptionid subscriptionid (optional)
     * @param includeHidden include hidden widgets (optional)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public SitemapPage pollDataForPage(
            String sitemapname, String pageid, String acceptLanguage, String subscriptionid, Boolean includeHidden) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("sitemapname", sitemapname);
            params.put("pageid", pageid);
            params.put("acceptLanguage", acceptLanguage);
            params.put("subscriptionid", subscriptionid);
            params.put("includeHidden", includeHidden);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Sitemaps.pollDataForPageWithHttpInfo({})", params);
            }
            var response =
                    api.pollDataForPageWithHttpInfo(sitemapname, pageid, acceptLanguage, subscriptionid, includeHidden);
            var data = response.getData();
            if (debugEnabled) {
                log.debug(
                        "RES: Sitemaps.pollDataForPageWithHttpInfo({}): status_code: {}, data: {}",
                        params,
                        response.getStatusCode(),
                        data);
            }
            return data;
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("sitemapname", sitemapname);
                params.put("pageid", pageid);
                params.put("acceptLanguage", acceptLanguage);
                params.put("subscriptionid", subscriptionid);
                params.put("includeHidden", includeHidden);
            }
            throw new EndpointException(this.getClass(), "pollDataForPageWithHttpInfo", params, e);
        }
    }

    /**
     * Polls the data for one page of a sitemap.
     *
     * <p>Omitted optional arguments are passed as {@code null}, leaving their defaults to the API.
     *
     * @param sitemapname sitemap name (required)
     * @param pageid page id (required)
     * @param subscriptionid subscriptionid (optional)
     * @param includeHidden include hidden widgets (optional)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public SitemapPage pollDataForPage(
            String sitemapname, String pageid, String subscriptionid, Boolean includeHidden) {
        return pollDataForPage(sitemapname, pageid, null, subscriptionid, includeHidden);
    }

    /**
     * Polls the data for one page of a sitemap.
     *
     * <p>Omitted optional arguments are passed as {@code null}, leaving their defaults to the API.
     *
     * @param sitemapname sitemap name (required)
     * @param pageid page id (required)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public SitemapPage pollDataForPage(String sitemapname, String pageid) {
        return pollDataForPage(sitemapname, pageid, null, null, null);
    }

    /**
     * Polls the data for a whole sitemap. Not recommended due to potentially high traffic.
     *
     * @param sitemapname sitemap name (required)
     * @param acceptLanguage language (optional)
     * @param subscriptionid subscriptionid (optional)
     * @param includeHidden include hidden widgets (optional)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public Sitemap pollDataForSitemap(
            String sitemapname, String acceptLanguage, String subscriptionid, Boolean includeHidden) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("sitemapname", sitemapname);
            params.put("acceptLanguage", acceptLanguage);
            params.put("subscriptionid", subscriptionid);
            params.put("includeHidden", includeHidden);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Sitemaps.pollDataForSitemapWithHttpInfo({})", params);
            }
            var response =
                    api.pollDataForSitemapWithHttpInfo(sitemapname, acceptLanguage, subscriptionid, includeHidden);
            var data = response.getData();
            if (debugEnabled) {
                log.debug(
                        "RES: Sitemaps.pollDataForSitemapWithHttpInfo({}): status_code: {}, data: {}",
                        params,
                        response.getStatusCode(),
                        data);
            }
            return data;
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("sitemapname", sitemapname);
                params.put("acceptLanguage", acceptLanguage);
                params.put("subscriptionid", subscriptionid);
                params.put("includeHidden", includeHidden);
            }
            throw new EndpointException(this.getClass(), "pollDataForSitemapWithHttpInfo", params, e);
        }
    }

    /**
     * Polls the data for a whole sitemap. Not recommended due to potentially high traffic.
     *
     * <p>Omitted optional arguments are passed as {@code null}, leaving their defaults to the API.
     *
     * @param sitemapname sitemap name (required)
     * @param subscriptionid subscriptionid (optional)
     * @param includeHidden include hidden widgets (optional)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public Sitemap pollDataForSitemap(String sitemapname, String subscriptionid, Boolean includeHidden) {
        return pollDataForSitemap(sitemapname, null, subscriptionid, includeHidden);
    }

    /**
     * Polls the data for a whole sitemap. Not recommended due to potentially high traffic.
     *
     * <p>Omitted optional arguments are passed as {@code null}, leaving their defaults to the API.
     *
     * @param sitemapname sitemap name (required)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public Sitemap pollDataForSitemap(String sitemapname) {
        return pollDataForSitemap(sitemapname, null, null, null);
    }

    /**
     * Removes a sitemap from the registry.
     *
     * @param sitemapname sitemap name (required)
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public void removeSitemapFromRegistry(String sitemapname) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("sitemapname", sitemapname);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Sitemaps.removeSitemapFromRegistryWithHttpInfo({})", params);
            }
            var response = api.removeSitemapFromRegistryWithHttpInfo(sitemapname);
            if (debugEnabled) {
                log.debug(
                        "RES: Sitemaps.removeSitemapFromRegistryWithHttpInfo({}): status_code: {}",
                        params,
                        response.getStatusCode());
            }
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("sitemapname", sitemapname);
            }
            throw new EndpointException(this.getClass(), "removeSitemapFromRegistryWithHttpInfo", params, e);
        }
    }
}
