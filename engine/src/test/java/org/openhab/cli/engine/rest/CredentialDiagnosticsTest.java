package org.openhab.cli.engine.rest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import okhttp3.Interceptor;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
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
    void httpDebuggingLogsMetadataWithoutCredentialsOrChangingRequests() throws Exception {
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
        var client = new ApiClient(props).toNative();
        var interceptors = client.getHttpClient().interceptors();
        assertEquals(1, interceptors.size());
        var request = new Request.Builder()
                .url("http://localhost/private-path?token=private-query")
                .header("Authorization", "Bearer private-token")
                .header("Cookie", "private-cookie")
                .build();
        var response = new Response.Builder()
                .request(request)
                .protocol(Protocol.HTTP_1_1)
                .code(200)
                .message("private-status-message")
                .header("Set-Cookie", "private-response-cookie")
                .build();
        var chain = mock(Interceptor.Chain.class);
        when(chain.request()).thenReturn(request);
        when(chain.proceed(request)).thenReturn(response);
        var original = System.out;
        var bytes = new ByteArrayOutputStream();
        try (var output = new PrintStream(bytes, true, StandardCharsets.UTF_8)) {
            System.setOut(output);
            assertSame(response, interceptors.getFirst().intercept(chain));
        } finally {
            System.setOut(original);
        }
        verify(chain).proceed(request);
        var diagnostic = bytes.toString(StandardCharsets.UTF_8);
        assertTrue(diagnostic.contains("HTTP request: GET"), diagnostic);
        assertTrue(diagnostic.contains("HTTP response: GET 200"), diagnostic);
        assertFalse(diagnostic.contains("private-"), diagnostic);
    }
}
