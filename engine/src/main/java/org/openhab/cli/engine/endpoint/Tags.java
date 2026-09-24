package org.openhab.cli.engine.endpoint;

import java.util.LinkedHashMap;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.client.ApiException;
import org.openhab.cli.client.api.TagsApi;
import org.openhab.cli.client.model.EnrichedSemanticTag;
import org.openhab.cli.engine.rest.ApiClient;

/**
 * Synchronous access to the openHAB operations provided by {@link TagsApi}.
 */
@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public final class Tags implements Endpoint {
    private final TagsApi api;

    /**
     * Creates an endpoint using the supplied REST client.
     *
     * @param apiClient the client providing connection and authentication settings
     */
    public Tags(ApiClient apiClient) {
        this(new TagsApi(apiClient.toNative()));
    }

    /**
     * Creates a new semantic tag and adds it to the registry.
     *
     * @param enrichedSemanticTag tag data (required)
     * @param acceptLanguage language (optional)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public EnrichedSemanticTag createSemanticTag(EnrichedSemanticTag enrichedSemanticTag, String acceptLanguage) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("enrichedSemanticTag", enrichedSemanticTag);
            params.put("acceptLanguage", acceptLanguage);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Tags.createSemanticTagWithHttpInfo({})", params);
            }
            var response = api.createSemanticTagWithHttpInfo(enrichedSemanticTag, acceptLanguage);
            var data = response.getData();
            if (debugEnabled) {
                log.debug(
                        "RES: Tags.createSemanticTagWithHttpInfo({}): status_code: {}, data: {}",
                        params,
                        response.getStatusCode(),
                        data);
            }
            return data;
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("enrichedSemanticTag", enrichedSemanticTag);
                params.put("acceptLanguage", acceptLanguage);
            }
            throw new EndpointException(this.getClass(), "createSemanticTagWithHttpInfo", params, e);
        }
    }

    /**
     * Creates a new semantic tag and adds it to the registry.
     *
     * <p>Omitted optional arguments are passed as {@code null}, leaving their defaults to the API.
     *
     * @param enrichedSemanticTag tag data (required)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public EnrichedSemanticTag createSemanticTag(EnrichedSemanticTag enrichedSemanticTag) {
        return createSemanticTag(enrichedSemanticTag, null);
    }

    /**
     * Gets a semantic tag and its sub tags.
     *
     * @param tagId tag id (required)
     * @param acceptLanguage language (optional)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public List<EnrichedSemanticTag> semanticTagAndSubTags(String tagId, String acceptLanguage) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("tagId", tagId);
            params.put("acceptLanguage", acceptLanguage);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Tags.getSemanticTagAndSubTagsWithHttpInfo({})", params);
            }
            var response = api.getSemanticTagAndSubTagsWithHttpInfo(tagId, acceptLanguage);
            var data = response.getData();
            if (debugEnabled) {
                log.debug(
                        "RES: Tags.getSemanticTagAndSubTagsWithHttpInfo({}): status_code: {}, data: {}",
                        params,
                        response.getStatusCode(),
                        data);
            }
            return data;
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("tagId", tagId);
                params.put("acceptLanguage", acceptLanguage);
            }
            throw new EndpointException(this.getClass(), "getSemanticTagAndSubTagsWithHttpInfo", params, e);
        }
    }

    /**
     * Gets a semantic tag and its sub tags.
     *
     * <p>Omitted optional arguments are passed as {@code null}, leaving their defaults to the API.
     *
     * @param tagId tag id (required)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public List<EnrichedSemanticTag> semanticTagAndSubTags(String tagId) {
        return semanticTagAndSubTags(tagId, null);
    }

    /**
     * Get all available semantic tags.
     *
     * @param acceptLanguage language (optional)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public List<EnrichedSemanticTag> semanticTags(String acceptLanguage) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("acceptLanguage", acceptLanguage);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Tags.getSemanticTagsWithHttpInfo({})", params);
            }
            var response = api.getSemanticTagsWithHttpInfo(acceptLanguage);
            var data = response.getData();
            if (debugEnabled) {
                log.debug(
                        "RES: Tags.getSemanticTagsWithHttpInfo({}): status_code: {}, data: {}",
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
            throw new EndpointException(this.getClass(), "getSemanticTagsWithHttpInfo", params, e);
        }
    }

    /**
     * Get all available semantic tags.
     *
     * <p>Omitted optional arguments are passed as {@code null}, leaving their defaults to the API.
     *
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public List<EnrichedSemanticTag> semanticTags() {
        return semanticTags(null);
    }

    /**
     * Removes a semantic tag and its sub tags from the registry.
     *
     * @param tagId tag id (required)
     * @param acceptLanguage language (optional)
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public void removeSemanticTag(String tagId, String acceptLanguage) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("tagId", tagId);
            params.put("acceptLanguage", acceptLanguage);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Tags.removeSemanticTagWithHttpInfo({})", params);
            }
            var response = api.removeSemanticTagWithHttpInfo(tagId, acceptLanguage);
            if (debugEnabled) {
                log.debug(
                        "RES: Tags.removeSemanticTagWithHttpInfo({}): status_code: {}",
                        params,
                        response.getStatusCode());
            }
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("tagId", tagId);
                params.put("acceptLanguage", acceptLanguage);
            }
            throw new EndpointException(this.getClass(), "removeSemanticTagWithHttpInfo", params, e);
        }
    }

    /**
     * Removes a semantic tag and its sub tags from the registry.
     *
     * <p>Omitted optional arguments are passed as {@code null}, leaving their defaults to the API.
     *
     * @param tagId tag id (required)
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public void removeSemanticTag(String tagId) {
        removeSemanticTag(tagId, null);
    }

    /**
     * Updates a semantic tag.
     *
     * @param tagId tag id (required)
     * @param enrichedSemanticTag tag data (required)
     * @param acceptLanguage language (optional)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public EnrichedSemanticTag updateSemanticTag(
            String tagId, EnrichedSemanticTag enrichedSemanticTag, String acceptLanguage) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("tagId", tagId);
            params.put("enrichedSemanticTag", enrichedSemanticTag);
            params.put("acceptLanguage", acceptLanguage);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Tags.updateSemanticTagWithHttpInfo({})", params);
            }
            var response = api.updateSemanticTagWithHttpInfo(tagId, enrichedSemanticTag, acceptLanguage);
            var data = response.getData();
            if (debugEnabled) {
                log.debug(
                        "RES: Tags.updateSemanticTagWithHttpInfo({}): status_code: {}, data: {}",
                        params,
                        response.getStatusCode(),
                        data);
            }
            return data;
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("tagId", tagId);
                params.put("enrichedSemanticTag", enrichedSemanticTag);
                params.put("acceptLanguage", acceptLanguage);
            }
            throw new EndpointException(this.getClass(), "updateSemanticTagWithHttpInfo", params, e);
        }
    }

    /**
     * Updates a semantic tag.
     *
     * <p>Omitted optional arguments are passed as {@code null}, leaving their defaults to the API.
     *
     * @param tagId tag id (required)
     * @param enrichedSemanticTag tag data (required)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public EnrichedSemanticTag updateSemanticTag(String tagId, EnrichedSemanticTag enrichedSemanticTag) {
        return updateSemanticTag(tagId, enrichedSemanticTag, null);
    }
}
