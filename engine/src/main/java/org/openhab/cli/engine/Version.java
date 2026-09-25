package org.openhab.cli.engine;

import java.io.IOException;
import java.util.Properties;
import lombok.experimental.UtilityClass;

/** Provides the application version embedded by Gradle during the build. */
@UtilityClass
public class Version {
    public static final String VERSION = loadVersion();

    private static String loadVersion() {
        try (var stream = Version.class.getResourceAsStream("version.properties")) {
            if (stream == null) {
                throw new IllegalStateException("Missing application version resource");
            }
            var properties = new Properties();
            properties.load(stream);
            var version = properties.getProperty("version");
            if (version == null || version.isBlank() || version.equals("${version}")) {
                throw new IllegalStateException("Application version was not populated by the build");
            }
            return version;
        } catch (IOException e) {
            throw new IllegalStateException("Cannot read application version", e);
        }
    }
}
