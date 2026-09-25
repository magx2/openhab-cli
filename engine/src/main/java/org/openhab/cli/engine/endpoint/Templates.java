package org.openhab.cli.engine.endpoint;

import java.util.LinkedHashMap;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.client.ApiException;
import org.openhab.cli.client.api.TemplatesApi;
import org.openhab.cli.client.model.RuleTemplateDTO;
import org.openhab.cli.engine.rest.ApiClient;

/**
 * Synchronous access to the openHAB operations provided by {@link TemplatesApi}.
 */
@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public final class Templates {
    private final TemplatesApi api;

    /**
     * Creates an endpoint using the supplied REST client.
     *
     * @param apiClient the client providing connection and authentication settings
     */
    public Templates(ApiClient apiClient) {
        this(new TemplatesApi(apiClient.toNative()));
    }

    /**
     * Gets a template corresponding to the given UID.
     *
     * @param templateUID templateUID (required)
     * @param acceptLanguage language (optional)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public RuleTemplateDTO templateById(String templateUID, String acceptLanguage) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("templateUID", templateUID);
            params.put("acceptLanguage", acceptLanguage);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Templates.getTemplateByIdWithHttpInfo({})", params);
            }
            var response = api.getTemplateByIdWithHttpInfo(templateUID, acceptLanguage);
            var data = response.getData();
            if (debugEnabled) {
                log.debug(
                        "RES: Templates.getTemplateByIdWithHttpInfo({}): status_code: {}, data: {}",
                        params,
                        response.getStatusCode(),
                        data);
            }
            return data;
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("templateUID", templateUID);
                params.put("acceptLanguage", acceptLanguage);
            }
            throw new EndpointException(this.getClass(), "getTemplateByIdWithHttpInfo", params, e);
        }
    }

    /**
     * Gets a template corresponding to the given UID.
     *
     * <p>Omitted optional arguments are passed as {@code null}, leaving their defaults to the API.
     *
     * @param templateUID templateUID (required)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public RuleTemplateDTO templateById(String templateUID) {
        return templateById(templateUID, null);
    }

    /**
     * Get all available templates.
     *
     * @param acceptLanguage language (optional)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public List<RuleTemplateDTO> templates(String acceptLanguage) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("acceptLanguage", acceptLanguage);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Templates.getTemplatesWithHttpInfo({})", params);
            }
            var response = api.getTemplatesWithHttpInfo(acceptLanguage);
            var data = response.getData();
            if (debugEnabled) {
                log.debug(
                        "RES: Templates.getTemplatesWithHttpInfo({}): status_code: {}, data: {}",
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
            throw new EndpointException(this.getClass(), "getTemplatesWithHttpInfo", params, e);
        }
    }

    /**
     * Get all available templates.
     *
     * <p>Omitted optional arguments are passed as {@code null}, leaving their defaults to the API.
     *
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public List<RuleTemplateDTO> templates() {
        return templates(null);
    }
}
