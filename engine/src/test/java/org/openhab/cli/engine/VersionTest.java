package org.openhab.cli.engine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class VersionTest {
    @Test
    void returnsVersionEmbeddedByGradle() {
        var expected = System.getProperty("expectedProjectVersion");
        assertNotNull(expected);
        var expectedApiVersion = System.getProperty("expectedApiVersion");
        assertNotNull(expectedApiVersion);
        assertEquals(expectedApiVersion + "." + expected, Version.VERSION);
    }
}
