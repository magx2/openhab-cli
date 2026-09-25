package org.openhab.cli.runtime;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.openhab.cli.engine.properties.Properties;

@Slf4j
public class PropertiesReader {
    /** Creates the service that loads CLI properties from disk. */
    @Inject
    public PropertiesReader() {}

    public static final String PROPERTIES_FILE_NAME = "oh-cli.properties";

    public Properties read(String stringPath) {
        var path = buildPath(stringPath);
        if (Files.notExists(path)) {
            log.warn("Path `{}` does not exists using default props", path);
            return Properties.DEFAULT;
        }
        try (var stream = new FileInputStream(path.toFile())) {
            var javaProps = new java.util.Properties();
            javaProps.load(stream);
            var oAuthToken = javaProps.getProperty("auth.oAuthToken");
            var username = javaProps.getProperty("auth.username");
            var password = javaProps.getProperty("auth.password");
            var prettyPrint = Boolean.parseBoolean(javaProps.getProperty("config.prettyPrint", "true"));
            return new Properties(oAuthToken, username, password, prettyPrint);
        } catch (IOException e) {
            throw new UncheckedIOException("Cannot read properties from file %s.".formatted(path.toString()), e);
        }
    }

    private static @NonNull Path buildPath(String stringPath) {
        Path path;
        if (stringPath == null) {
            var userDir = System.getProperty("user.dir");
            log.debug("Using user.dir=`{}` to read props", userDir);
            path = Paths.get(userDir).resolve(PROPERTIES_FILE_NAME);
        } else {
            path = Paths.get(stringPath);
        }
        return path;
    }
}
