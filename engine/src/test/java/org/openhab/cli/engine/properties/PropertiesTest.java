package org.openhab.cli.engine.properties;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PropertiesTest {
    @Test
    void diagnosticsHideCredentialsWithoutChangingAccessors() {
        var oauth = properties("private-token", null, null);
        var basic = properties(null, "private-username", "private-password");
        for (var props : new Properties[] {oauth, basic, Properties.DEFAULT}) {
            var diagnostic = props.toString();
            assertFalse(diagnostic.contains("private-"));
            assertTrue(diagnostic.contains("[REDACTED]"));
            assertTrue(diagnostic.contains("connectTimeout=10000"));
        }
        assertEquals("private-token", oauth.oAuthToken());
        assertEquals("private-username", basic.username());
        assertEquals("private-password", basic.password());
    }

    @Test
    void diagnosticsDoNotExposeCredentialsEmbeddedInFreeFormSettings() {
        var props = new Properties(
                "http://private-user:private-password@localhost",
                "private-path",
                null,
                null,
                null,
                true,
                true,
                false,
                "private-certificate",
                null,
                "private-server",
                1,
                2,
                3);
        assertFalse(props.toString().contains("private-"));
    }

    @Test
    void rejectsOAuthCombinedWithEitherBasicCredential() {
        assertThrows(IllegalArgumentException.class, () -> properties("token", "user", "pass"));
        assertThrows(IllegalArgumentException.class, () -> properties("token", "user", null));
        assertThrows(IllegalArgumentException.class, () -> properties("token", null, "pass"));
    }

    @Test
    void acceptsSeparateAuthenticationMethodsAndEmptyCredentials() {
        assertDoesNotThrow(() -> properties("token", null, null));
        assertDoesNotThrow(() -> properties(null, "user", "pass"));
        assertDoesNotThrow(() -> properties("", "user", "pass"));
        assertDoesNotThrow(() -> properties("token", "", ""));
        assertDoesNotThrow(() -> properties(null, null, null));
    }

    private Properties properties(String token, String username, String password) {
        return new Properties(
                null, null, token, username, password, true, true, false, null, null, null, 10000, 10000, 10000);
    }
}
