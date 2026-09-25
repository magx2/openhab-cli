package org.openhab.cli.engine.endpoint;

import java.util.LinkedHashMap;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.client.ApiException;
import org.openhab.cli.client.api.FileFormatApi;
import org.openhab.cli.client.model.CanSerializeRulesRequest;
import org.openhab.cli.client.model.ExtendedFileFormat;
import org.openhab.cli.client.model.SerializabilityResults;
import org.openhab.cli.engine.rest.ApiClient;

/**
 * Synchronous access to the openHAB operations provided by {@link FileFormatApi}.
 */
@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public final class FileFormat implements Endpoint {
    private final FileFormatApi api;

    /**
     * Creates an endpoint using the supplied REST client.
     *
     * @param apiClient the client providing connection and authentication settings
     */
    public FileFormat(ApiClient apiClient) {
        this(new FileFormatApi(apiClient.toNative()));
    }

    /**
     * Checks if the specified rule(s) can be serialized to the target format.
     *
     * @param targetFormat Target format (optional, default to application/yaml)
     * @param canSerializeRulesRequest JSON rule data (optional)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public SerializabilityResults canSerializeRules(
            String targetFormat, CanSerializeRulesRequest canSerializeRulesRequest) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("targetFormat", targetFormat);
            params.put("canSerializeRulesRequest", canSerializeRulesRequest);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: FileFormat.canSerializeRulesWithHttpInfo({})", params);
            }
            var response = api.canSerializeRulesWithHttpInfo(targetFormat, canSerializeRulesRequest);
            var data = response.getData();
            if (debugEnabled) {
                log.debug(
                        "RES: FileFormat.canSerializeRulesWithHttpInfo({}): status_code: {}, data: {}",
                        params,
                        response.getStatusCode(),
                        data);
            }
            return data;
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("targetFormat", targetFormat);
                params.put("canSerializeRulesRequest", canSerializeRulesRequest);
            }
            throw new EndpointException(this.getClass(), "canSerializeRulesWithHttpInfo", params, e);
        }
    }

    /**
     * Checks if the specified rule(s) can be serialized to the target format.
     *
     * <p>Omitted optional arguments are passed as {@code null}, leaving their defaults to the API.
     *
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public SerializabilityResults canSerializeRules() {
        return canSerializeRules(null, null);
    }

    /**
     * Create file format.
     *
     * @param fileFormat JSON data (required)
     * @param hideDefaultParameters if true, exclude the configuration parameters having the default value from the result. (optional, default to false)
     * @param hideDefaultChannels if true, exclude the non extensible channels having a default configuration from the result. (optional, default to false)
     * @param hideChannelLinksAndMetadata if true, exclude the channel links and metadata for items from the result. (optional, default to false)
     * @param ruleSerializationOption Decides what to include in serialized rules and rule templates (optional, default to Normal)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public String create(
            org.openhab.cli.client.model.FileFormat fileFormat,
            Boolean hideDefaultParameters,
            Boolean hideDefaultChannels,
            Boolean hideChannelLinksAndMetadata,
            String ruleSerializationOption) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("fileFormat", fileFormat);
            params.put("hideDefaultParameters", hideDefaultParameters);
            params.put("hideDefaultChannels", hideDefaultChannels);
            params.put("hideChannelLinksAndMetadata", hideChannelLinksAndMetadata);
            params.put("ruleSerializationOption", ruleSerializationOption);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: FileFormat.createWithHttpInfo({})", params);
            }
            var response = api.createWithHttpInfo(
                    fileFormat,
                    hideDefaultParameters,
                    hideDefaultChannels,
                    hideChannelLinksAndMetadata,
                    ruleSerializationOption);
            var data = response.getData();
            if (debugEnabled) {
                log.debug(
                        "RES: FileFormat.createWithHttpInfo({}): status_code: {}, data: {}",
                        params,
                        response.getStatusCode(),
                        data);
            }
            return data;
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("fileFormat", fileFormat);
                params.put("hideDefaultParameters", hideDefaultParameters);
                params.put("hideDefaultChannels", hideDefaultChannels);
                params.put("hideChannelLinksAndMetadata", hideChannelLinksAndMetadata);
                params.put("ruleSerializationOption", ruleSerializationOption);
            }
            throw new EndpointException(this.getClass(), "createWithHttpInfo", params, e);
        }
    }

    /**
     * Create file format.
     *
     * <p>Omitted optional arguments are passed as {@code null}, leaving their defaults to the API.
     *
     * @param fileFormat JSON data (required)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public String create(org.openhab.cli.client.model.FileFormat fileFormat) {
        return create(fileFormat, null, null, null, null);
    }

    /**
     * Create file format for a list of items in registry.
     *
     * @param hideDefaultParameters if true, exclude the configuration parameters having the default value from the result. (optional, default to true)
     * @param requestBody Array of item names. If empty or omitted, return all Items. (optional)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public String createFileFormatForItems(Boolean hideDefaultParameters, List<String> requestBody) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("hideDefaultParameters", hideDefaultParameters);
            params.put("requestBody", requestBody);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: FileFormat.createFileFormatForItemsWithHttpInfo({})", params);
            }
            var response = api.createFileFormatForItemsWithHttpInfo(hideDefaultParameters, requestBody);
            var data = response.getData();
            if (debugEnabled) {
                log.debug(
                        "RES: FileFormat.createFileFormatForItemsWithHttpInfo({}): status_code: {}, data: {}",
                        params,
                        response.getStatusCode(),
                        data);
            }
            return data;
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("hideDefaultParameters", hideDefaultParameters);
                params.put("requestBody", requestBody);
            }
            throw new EndpointException(this.getClass(), "createFileFormatForItemsWithHttpInfo", params, e);
        }
    }

    /**
     * Create file format for a list of items in registry.
     *
     * <p>Omitted optional arguments are passed as {@code null}, leaving their defaults to the API.
     *
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public String createFileFormatForItems() {
        return createFileFormatForItems(null, null);
    }

    /**
     * Create file format for a list of rule templates in the registry.
     *
     * @param serializationOption Decides what to include in serialized rule templates (optional, default to Normal)
     * @param requestBody Array of rule template UIDs. If empty or omitted, return all rule templates. (optional)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public String createFileFormatForRuleTemplates(String serializationOption, List<String> requestBody) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("serializationOption", serializationOption);
            params.put("requestBody", requestBody);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: FileFormat.createFileFormatForRuleTemplatesWithHttpInfo({})", params);
            }
            var response = api.createFileFormatForRuleTemplatesWithHttpInfo(serializationOption, requestBody);
            var data = response.getData();
            if (debugEnabled) {
                log.debug(
                        "RES: FileFormat.createFileFormatForRuleTemplatesWithHttpInfo({}): status_code: {}, data: {}",
                        params,
                        response.getStatusCode(),
                        data);
            }
            return data;
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("serializationOption", serializationOption);
                params.put("requestBody", requestBody);
            }
            throw new EndpointException(this.getClass(), "createFileFormatForRuleTemplatesWithHttpInfo", params, e);
        }
    }

    /**
     * Create file format for a list of rule templates in the registry.
     *
     * <p>Omitted optional arguments are passed as {@code null}, leaving their defaults to the API.
     *
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public String createFileFormatForRuleTemplates() {
        return createFileFormatForRuleTemplates(null, null);
    }

    /**
     * Create file format for a list of rules in the registry.
     *
     * @param serializationOption Decides what to include in serialized rules (optional, default to Normal)
     * @param requestBody Array of rule UIDs. If empty or omitted, return all rules. (optional)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public String createFileFormatForRules(String serializationOption, List<String> requestBody) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("serializationOption", serializationOption);
            params.put("requestBody", requestBody);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: FileFormat.createFileFormatForRulesWithHttpInfo({})", params);
            }
            var response = api.createFileFormatForRulesWithHttpInfo(serializationOption, requestBody);
            var data = response.getData();
            if (debugEnabled) {
                log.debug(
                        "RES: FileFormat.createFileFormatForRulesWithHttpInfo({}): status_code: {}, data: {}",
                        params,
                        response.getStatusCode(),
                        data);
            }
            return data;
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("serializationOption", serializationOption);
                params.put("requestBody", requestBody);
            }
            throw new EndpointException(this.getClass(), "createFileFormatForRulesWithHttpInfo", params, e);
        }
    }

    /**
     * Create file format for a list of rules in the registry.
     *
     * <p>Omitted optional arguments are passed as {@code null}, leaving their defaults to the API.
     *
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public String createFileFormatForRules() {
        return createFileFormatForRules(null, null);
    }

    /**
     * Create file format for a list of semantic tags in registry.
     *
     * @param hideNonEditableTags if true, exclude the non editable semantic tags from the result. (optional, default to false)
     * @param hideDefaultTags if true, exclude the default semantic tags from the result. (optional, default to false)
     * @param requestBody Array of semantic tag UIDs. If empty or omitted, return all custom semantic tags from the Registry. (optional)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public String createFileFormatForSemanticTags(
            Boolean hideNonEditableTags, Boolean hideDefaultTags, List<String> requestBody) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("hideNonEditableTags", hideNonEditableTags);
            params.put("hideDefaultTags", hideDefaultTags);
            params.put("requestBody", requestBody);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: FileFormat.createFileFormatForSemanticTagsWithHttpInfo({})", params);
            }
            var response =
                    api.createFileFormatForSemanticTagsWithHttpInfo(hideNonEditableTags, hideDefaultTags, requestBody);
            var data = response.getData();
            if (debugEnabled) {
                log.debug(
                        "RES: FileFormat.createFileFormatForSemanticTagsWithHttpInfo({}): status_code: {}, data: {}",
                        params,
                        response.getStatusCode(),
                        data);
            }
            return data;
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("hideNonEditableTags", hideNonEditableTags);
                params.put("hideDefaultTags", hideDefaultTags);
                params.put("requestBody", requestBody);
            }
            throw new EndpointException(this.getClass(), "createFileFormatForSemanticTagsWithHttpInfo", params, e);
        }
    }

    /**
     * Create file format for a list of semantic tags in registry.
     *
     * <p>Omitted optional arguments are passed as {@code null}, leaving their defaults to the API.
     *
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public String createFileFormatForSemanticTags() {
        return createFileFormatForSemanticTags(null, null, null);
    }

    /**
     * Create file format for a list of sitemaps in registry.
     *
     * @param requestBody Array of Sitemap names. If empty or omitted, return all Sitemaps from the Registry. (optional)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public String createFileFormatForSitemaps(List<String> requestBody) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("requestBody", requestBody);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: FileFormat.createFileFormatForSitemapsWithHttpInfo({})", params);
            }
            var response = api.createFileFormatForSitemapsWithHttpInfo(requestBody);
            var data = response.getData();
            if (debugEnabled) {
                log.debug(
                        "RES: FileFormat.createFileFormatForSitemapsWithHttpInfo({}): status_code: {}, data: {}",
                        params,
                        response.getStatusCode(),
                        data);
            }
            return data;
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("requestBody", requestBody);
            }
            throw new EndpointException(this.getClass(), "createFileFormatForSitemapsWithHttpInfo", params, e);
        }
    }

    /**
     * Create file format for a list of sitemaps in registry.
     *
     * <p>Omitted optional arguments are passed as {@code null}, leaving their defaults to the API.
     *
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public String createFileFormatForSitemaps() {
        return createFileFormatForSitemaps(null);
    }

    /**
     * Create file format for a list of things in things or discovery registry.
     *
     * @param hideDefaultParameters if true, exclude the configuration parameters having the default value from the result. (optional, default to true)
     * @param requestBody Array of Thing UIDs. If empty or omitted, return all Things from the Registry. (optional)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public String createFileFormatForThings(Boolean hideDefaultParameters, List<String> requestBody) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("hideDefaultParameters", hideDefaultParameters);
            params.put("requestBody", requestBody);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: FileFormat.createFileFormatForThingsWithHttpInfo({})", params);
            }
            var response = api.createFileFormatForThingsWithHttpInfo(hideDefaultParameters, requestBody);
            var data = response.getData();
            if (debugEnabled) {
                log.debug(
                        "RES: FileFormat.createFileFormatForThingsWithHttpInfo({}): status_code: {}, data: {}",
                        params,
                        response.getStatusCode(),
                        data);
            }
            return data;
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("hideDefaultParameters", hideDefaultParameters);
                params.put("requestBody", requestBody);
            }
            throw new EndpointException(this.getClass(), "createFileFormatForThingsWithHttpInfo", params, e);
        }
    }

    /**
     * Create file format for a list of things in things or discovery registry.
     *
     * <p>Omitted optional arguments are passed as {@code null}, leaving their defaults to the API.
     *
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public String createFileFormatForThings() {
        return createFileFormatForThings(null, null);
    }

    /**
     * Parse file format.
     *
     * @param body file format syntax (required)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public ExtendedFileFormat parse(String body) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("body", body);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: FileFormat.parseWithHttpInfo({})", params);
            }
            var response = api.parseWithHttpInfo(body);
            var data = response.getData();
            if (debugEnabled) {
                log.debug(
                        "RES: FileFormat.parseWithHttpInfo({}): status_code: {}, data: {}",
                        params,
                        response.getStatusCode(),
                        data);
            }
            return data;
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("body", body);
            }
            throw new EndpointException(this.getClass(), "parseWithHttpInfo", params, e);
        }
    }
}
