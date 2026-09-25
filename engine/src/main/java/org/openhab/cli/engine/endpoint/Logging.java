package org.openhab.cli.engine.endpoint;

import java.util.LinkedHashMap;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.client.ApiException;
import org.openhab.cli.client.api.LoggingApi;
import org.openhab.cli.client.model.LoggerBean;
import org.openhab.cli.client.model.LoggerInfo;
import org.openhab.cli.engine.rest.ApiClient;

/**
 * Synchronous access to the openHAB operations provided by {@link LoggingApi}.
 */
@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public final class Logging {
    private final LoggingApi api;

    /**
     * Creates an endpoint using the supplied REST client.
     *
     * @param apiClient the client providing connection and authentication settings
     */
    public Logging(ApiClient apiClient) {
        this(new LoggingApi(apiClient.toNative()));
    }

    /**
     * Get a single logger.
     *
     * @param loggerName logger name (required)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public LoggerInfo logger(String loggerName) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("loggerName", loggerName);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Logging.getLoggerWithHttpInfo({})", params);
            }
            var response = api.getLoggerWithHttpInfo(loggerName);
            var data = response.getData();
            if (debugEnabled) {
                log.debug(
                        "RES: Logging.getLoggerWithHttpInfo({}): status_code: {}, data: {}",
                        params,
                        response.getStatusCode(),
                        data);
            }
            return data;
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("loggerName", loggerName);
            }
            throw new EndpointException(this.getClass(), "getLoggerWithHttpInfo", params, e);
        }
    }

    /**
     * Get all loggers
     *
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public LoggerBean logger1() {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Logging.getLogger1WithHttpInfo({})", params);
            }
            var response = api.getLogger1WithHttpInfo();
            var data = response.getData();
            if (debugEnabled) {
                log.debug(
                        "RES: Logging.getLogger1WithHttpInfo({}): status_code: {}, data: {}",
                        params,
                        response.getStatusCode(),
                        data);
            }
            return data;
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
            }
            throw new EndpointException(this.getClass(), "getLogger1WithHttpInfo", params, e);
        }
    }

    /**
     * Modify or add logger
     *
     * @param loggerName logger name (required)
     * @param loggerInfo logger (required)
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public void putLogger(String loggerName, LoggerInfo loggerInfo) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("loggerName", loggerName);
            params.put("loggerInfo", loggerInfo);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Logging.putLoggerWithHttpInfo({})", params);
            }
            var response = api.putLoggerWithHttpInfo(loggerName, loggerInfo);
            if (debugEnabled) {
                log.debug("RES: Logging.putLoggerWithHttpInfo({}): status_code: {}", params, response.getStatusCode());
            }
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("loggerName", loggerName);
                params.put("loggerInfo", loggerInfo);
            }
            throw new EndpointException(this.getClass(), "putLoggerWithHttpInfo", params, e);
        }
    }

    /**
     * Remove a single logger.
     *
     * @param loggerName logger name (required)
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public void removeLogger(String loggerName) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("loggerName", loggerName);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Logging.removeLoggerWithHttpInfo({})", params);
            }
            var response = api.removeLoggerWithHttpInfo(loggerName);
            if (debugEnabled) {
                log.debug(
                        "RES: Logging.removeLoggerWithHttpInfo({}): status_code: {}", params, response.getStatusCode());
            }
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("loggerName", loggerName);
            }
            throw new EndpointException(this.getClass(), "removeLoggerWithHttpInfo", params, e);
        }
    }
}
