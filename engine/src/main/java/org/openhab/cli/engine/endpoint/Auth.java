package org.openhab.cli.engine.endpoint;

import java.util.LinkedHashMap;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.client.ApiException;
import org.openhab.cli.client.api.AuthApi;
import org.openhab.cli.client.model.TokenResponse;
import org.openhab.cli.client.model.UserApiToken;
import org.openhab.cli.client.model.UserSession;
import org.openhab.cli.engine.rest.ApiClient;

/**
 * Synchronous access to the openHAB operations provided by {@link AuthApi}.
 */
@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public final class Auth implements Endpoint {
    private final AuthApi api;

    /**
     * Creates an endpoint using the supplied REST client.
     *
     * @param apiClient the client providing connection and authentication settings
     */
    public Auth(ApiClient apiClient) {
        this(new AuthApi(apiClient.toNative()));
    }

    /**
     * Delete the session associated with a refresh token.
     *
     * @param refreshToken  (optional)
     * @param id  (optional)
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public void deleteSession(String refreshToken, String id) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("refreshToken", "[REDACTED]");
            params.put("id", id);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Auth.deleteSessionWithHttpInfo({})", params);
            }
            var response = api.deleteSessionWithHttpInfo(refreshToken, id);
            if (debugEnabled) {
                log.debug("RES: Auth.deleteSessionWithHttpInfo({}): status_code: {}", params, response.getStatusCode());
            }
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("refreshToken", "[REDACTED]");
                params.put("id", id);
            }
            throw new EndpointException(this.getClass(), "deleteSessionWithHttpInfo", params, e);
        }
    }

    /**
     * Delete the session associated with a refresh token.
     *
     * <p>Omitted optional arguments are passed as {@code null}, leaving their defaults to the API.
     *
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public void deleteSession() {
        deleteSession(null, null);
    }

    /**
     * List the API tokens associated to the authenticated user.
     *
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public List<UserApiToken> apiTokens() {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Auth.getApiTokensWithHttpInfo({})", params);
            }
            var response = api.getApiTokensWithHttpInfo();
            var data = response.getData();
            if (debugEnabled) {
                log.debug(
                        "RES: Auth.getApiTokensWithHttpInfo({}): status_code: {}, data: {}",
                        params,
                        response.getStatusCode(),
                        "[REDACTED]");
            }
            return data;
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
            }
            throw new EndpointException(this.getClass(), "getApiTokensWithHttpInfo", params, e);
        }
    }

    /**
     * Get access and refresh tokens.
     *
     * @param useCookie  (optional)
     * @param grantType  (optional)
     * @param code  (optional)
     * @param redirectUri  (optional)
     * @param clientId  (optional)
     * @param refreshToken  (optional)
     * @param codeVerifier  (optional)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public TokenResponse oAuthToken(
            Boolean useCookie,
            String grantType,
            String code,
            String redirectUri,
            String clientId,
            String refreshToken,
            String codeVerifier) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("useCookie", useCookie);
            params.put("grantType", grantType);
            params.put("code", "[REDACTED]");
            params.put("redirectUri", redirectUri);
            params.put("clientId", clientId);
            params.put("refreshToken", "[REDACTED]");
            params.put("codeVerifier", "[REDACTED]");
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Auth.getOAuthTokenWithHttpInfo({})", params);
            }
            var response = api.getOAuthTokenWithHttpInfo(
                    useCookie, grantType, code, redirectUri, clientId, refreshToken, codeVerifier);
            var data = response.getData();
            if (debugEnabled) {
                log.debug(
                        "RES: Auth.getOAuthTokenWithHttpInfo({}): status_code: {}, data: {}",
                        params,
                        response.getStatusCode(),
                        "[REDACTED]");
            }
            return data;
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("useCookie", useCookie);
                params.put("grantType", grantType);
                params.put("code", "[REDACTED]");
                params.put("redirectUri", redirectUri);
                params.put("clientId", clientId);
                params.put("refreshToken", "[REDACTED]");
                params.put("codeVerifier", "[REDACTED]");
            }
            throw new EndpointException(this.getClass(), "getOAuthTokenWithHttpInfo", params, e);
        }
    }

    /**
     * Get access and refresh tokens.
     *
     * <p>Omitted optional arguments are passed as {@code null}, leaving their defaults to the API.
     *
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public TokenResponse oAuthToken() {
        return oAuthToken(null, null, null, null, null, null, null);
    }

    /**
     * List the sessions associated to the authenticated user.
     *
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public List<UserSession> sessionsForCurrentUser() {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Auth.getSessionsForCurrentUserWithHttpInfo({})", params);
            }
            var response = api.getSessionsForCurrentUserWithHttpInfo();
            var data = response.getData();
            if (debugEnabled) {
                log.debug(
                        "RES: Auth.getSessionsForCurrentUserWithHttpInfo({}): status_code: {}, data: {}",
                        params,
                        response.getStatusCode(),
                        "[REDACTED]");
            }
            return data;
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
            }
            throw new EndpointException(this.getClass(), "getSessionsForCurrentUserWithHttpInfo", params, e);
        }
    }

    /**
     * Revoke a specified API token associated to the authenticated user.
     *
     * @param name  (required)
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public void removeApiToken(String name) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("name", name);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Auth.removeApiTokenWithHttpInfo({})", params);
            }
            var response = api.removeApiTokenWithHttpInfo(name);
            if (debugEnabled) {
                log.debug(
                        "RES: Auth.removeApiTokenWithHttpInfo({}): status_code: {}", params, response.getStatusCode());
            }
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("name", name);
            }
            throw new EndpointException(this.getClass(), "removeApiTokenWithHttpInfo", params, e);
        }
    }
}
