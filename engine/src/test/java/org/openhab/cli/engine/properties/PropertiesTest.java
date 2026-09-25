package org.openhab.cli.engine.properties;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PropertiesTest {
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
