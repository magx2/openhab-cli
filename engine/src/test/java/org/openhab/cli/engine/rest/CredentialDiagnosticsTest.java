package org.openhab.cli.engine.rest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.junit.jupiter.api.Test;
import org.openhab.cli.client.model.TokenResponse;
import org.openhab.cli.client.model.User;
import org.openhab.cli.client.model.UserSession;
import org.openhab.cli.engine.properties.Properties;

class CredentialDiagnosticsTest {
    @Test
    void generatedModelsMaskSecretsButKeepGettersAndJsonUnchanged() {
        var user = new User().name("private-username");
        var token = new TokenResponse()
                .accessToken("private-access-token")
                .refreshToken("private-refresh-token")
                .user(user);
        var session = new UserSession().sessionId("private-session");
        assertFalse(token.toString().contains("private-"));
        assertFalse(user.toString().contains("private-"));
        assertFalse(session.toString().contains("private-"));
        assertEquals("private-access-token", token.getAccessToken());
        assertEquals("private-refresh-token", token.getRefreshToken());
        assertEquals("private-username", user.getName());
        assertEquals("private-session", session.getSessionId());
        assertTrue(token.toJson().contains("private-access-token"));
        assertTrue(token.toJson().contains("private-refresh-token"));
        assertTrue(token.toJson().contains("private-username"));
        assertTrue(session.toJson().contains("private-session"));
    }

    @Test
    void httpDebuggingLogsBodiesButRedactsCredentialHeadersWithoutChangingRequests() throws Exception {
        var props = new Properties(
                "http://localhost",
                null,
                "private-token",
                null,
                null,
                true,
                true,
                true,
                null,
                null,
                null,
                10000,
                10000,
                10000);
        var lines = new ArrayList<String>();
        var client = new ApiClient(props, lines::add).toNative();
        var interceptors = client.getHttpClient().interceptors();
        assertEquals(1, interceptors.size());
        var request = new Request.Builder()
                .url("http://localhost/rest/test")
                .post(RequestBody.create("request 100% payload", MediaType.get("text/plain")))
                .header("Content-Type", "text/plain")
                .header("X-Trace", "visible-trace")
                .header("aUtHoRiZaTiOn", "Bearer private-token")
                .header("Proxy-Authorization", "Basic private-proxy")
                .header("Cookie", "private-cookie")
                .build();
        var response = new Response.Builder()
                .request(request)
                .protocol(Protocol.HTTP_1_1)
                .code(200)
                .message("OK")
                .body(ResponseBody.create("response payload", MediaType.get("text/plain")))
                .header("AUTHORIZATION", "private-response-auth")
                .header("Set-Cookie", "private-response-cookie")
                .build();
        var chain = mock(Interceptor.Chain.class);
        when(chain.request()).thenReturn(request);
        when(chain.proceed(request)).thenReturn(response);
        assertSame(response, interceptors.getFirst().intercept(chain));
        verify(chain).proceed(request);
        var diagnostic = String.join("\n", lines);
        assertTrue(diagnostic.contains("--> POST http://localhost/rest/test"), diagnostic);
        assertTrue(diagnostic.contains("<-- 200 OK"), diagnostic);
        assertTrue(diagnostic.contains("visible-trace"), diagnostic);
        assertTrue(diagnostic.contains("request 100% payload"), diagnostic);
        assertTrue(diagnostic.contains("response payload"), diagnostic);
        assertTrue(diagnostic.contains("Authorization"), diagnostic);
        assertFalse(diagnostic.contains("private-"), diagnostic);
        assertEquals("Bearer private-token", request.header("Authorization"));
        assertEquals("response payload", response.body().string());
    }
}
