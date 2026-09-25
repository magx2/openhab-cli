package org.openhab.cli.engine.endpoint;

import java.util.LinkedHashMap;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.client.ApiException;
import org.openhab.cli.client.api.TransformationsApi;
import org.openhab.cli.client.model.Transformation;
import org.openhab.cli.engine.rest.ApiClient;

/**
 * Synchronous access to the openHAB operations provided by {@link TransformationsApi}.
 */
@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public final class Transformations implements Endpoint {
    private final TransformationsApi api;

    /**
     * Creates an endpoint using the supplied REST client.
     *
     * @param apiClient the client providing connection and authentication settings
     */
    public Transformations(ApiClient apiClient) {
        this(new TransformationsApi(apiClient.toNative()));
    }

    /**
     * Get a single transformation
     *
     * @param uid Transformation UID (required)
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public void deleteTransformation(String uid) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("uid", uid);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Transformations.deleteTransformationWithHttpInfo({})", params);
            }
            var response = api.deleteTransformationWithHttpInfo(uid);
            if (debugEnabled) {
                log.debug(
                        "RES: Transformations.deleteTransformationWithHttpInfo({}): status_code: {}",
                        params,
                        response.getStatusCode());
            }
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("uid", uid);
            }
            throw new EndpointException(this.getClass(), "deleteTransformationWithHttpInfo", params, e);
        }
    }

    /**
     * Get a single transformation
     *
     * @param uid Transformation UID (required)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public Transformation transformation(String uid) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("uid", uid);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Transformations.getTransformationWithHttpInfo({})", params);
            }
            var response = api.getTransformationWithHttpInfo(uid);
            var data = response.getData();
            if (debugEnabled) {
                log.debug(
                        "RES: Transformations.getTransformationWithHttpInfo({}): status_code: {}, data: {}",
                        params,
                        response.getStatusCode(),
                        data);
            }
            return data;
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("uid", uid);
            }
            throw new EndpointException(this.getClass(), "getTransformationWithHttpInfo", params, e);
        }
    }

    /**
     * Get all transformation services
     *
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public List<String> transformationServices() {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Transformations.getTransformationServicesWithHttpInfo({})", params);
            }
            var response = api.getTransformationServicesWithHttpInfo();
            var data = response.getData();
            if (debugEnabled) {
                log.debug(
                        "RES: Transformations.getTransformationServicesWithHttpInfo({}): status_code: {}, data: {}",
                        params,
                        response.getStatusCode(),
                        data);
            }
            return data;
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
            }
            throw new EndpointException(this.getClass(), "getTransformationServicesWithHttpInfo", params, e);
        }
    }

    /**
     * Get a list of all transformations
     *
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public List<Transformation> transformations() {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Transformations.getTransformationsWithHttpInfo({})", params);
            }
            var response = api.getTransformationsWithHttpInfo();
            var data = response.getData();
            if (debugEnabled) {
                log.debug(
                        "RES: Transformations.getTransformationsWithHttpInfo({}): status_code: {}, data: {}",
                        params,
                        response.getStatusCode(),
                        data);
            }
            return data;
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
            }
            throw new EndpointException(this.getClass(), "getTransformationsWithHttpInfo", params, e);
        }
    }

    /**
     * Put a single transformation
     *
     * @param uid Transformation UID (required)
     * @param transformation transformation (required)
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public void putTransformation(String uid, Transformation transformation) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("uid", uid);
            params.put("transformation", transformation);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Transformations.putTransformationWithHttpInfo({})", params);
            }
            var response = api.putTransformationWithHttpInfo(uid, transformation);
            if (debugEnabled) {
                log.debug(
                        "RES: Transformations.putTransformationWithHttpInfo({}): status_code: {}",
                        params,
                        response.getStatusCode());
            }
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("uid", uid);
                params.put("transformation", transformation);
            }
            throw new EndpointException(this.getClass(), "putTransformationWithHttpInfo", params, e);
        }
    }
}
