package org.openhab.cli.runtime.service;

import static org.openhab.cli.runtime.ExitCodeMapper.IO_EXCEPTION_EXIT_CODE;

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
    private final Console console;
    /** Creates the service that loads CLI properties from disk. */
    @Inject
    public PropertiesReader(Console console) {
        this.console = console;
    }

    public static final String PROPERTIES_FILE_NAME = "oh-cli.properties";

    /**
     * Reads stored properties and converts them to CLI settings with defaults for unspecified values.
     *
     * @param stringPath file to read, or null to use the default file in the working directory
     * @return CLI settings, using defaults when the file does not exist
     * @throws UncheckedIOException if the existing file cannot be read
     */
    public Properties read(String stringPath) {
        var javaProps = readProperties(stringPath);
        var oAuthToken = javaProps.getProperty("auth.oAuthToken");
        var username = javaProps.getProperty("auth.username");
        var password = javaProps.getProperty("auth.password");
        var prettyPrint = Boolean.parseBoolean(javaProps.getProperty("config.prettyPrint", "true"));
        var certPath = javaProps.getProperty("config.ssl.sslCaCertPath");
        return new Properties(
                javaProps.getProperty("config.rest.baseUrl"),
                javaProps.getProperty("config.rest.basePath"),
                oAuthToken,
                username,
                password,
                prettyPrint,
                Boolean.parseBoolean(javaProps.getProperty("config.ssl.verifyingSsl", "true")),
                Boolean.parseBoolean(javaProps.getProperty("config.rest.apiClientDebugging", "false")),
                javaProps.getProperty("config.ssl.sslCaCert"),
                certPath == null ? null : Path.of(certPath),
                javaProps.getProperty("config.ssl.tlsServerName"),
                Integer.parseInt(javaProps.getProperty(
                        "config.timeout.connectTimeout", Integer.toString(Properties.DEFAULT_CONNECT_TIMEOUT))),
                Integer.parseInt(javaProps.getProperty(
                        "config.timeout.readTimeout", Integer.toString(Properties.DEFAULT_READ_TIMEOUT))),
                Integer.parseInt(javaProps.getProperty(
                        "config.timeout.writeTimeout", Integer.toString(Properties.DEFAULT_WRITE_TIMEOUT))));
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

    /**
     * Adds or replaces a property in an existing file, preserving all other property values.
     *
     * @param propertiesFile file to update, or null to use the default file in the working directory
     * @param key property name
     * @param value property value, or null to remove the property
     * @return zero on success, or the I/O exit code if the file cannot be read or written
     */
    public int set(String propertiesFile, String key, String value) {
        var path = buildPath(propertiesFile);
        if (Files.notExists(path)) {
            console.writeError("Properties file `%s` does not exist", path);
            return IO_EXCEPTION_EXIT_CODE;
        }
        var javaProps = new java.util.Properties();
        try {
            try (var stream = Files.newInputStream(path)) {
                javaProps.load(stream);
            }
            if (value == null) {
                javaProps.remove(key);
            } else {
                javaProps.setProperty(key, value);
            }
            try (var stream = Files.newOutputStream(path)) {
                javaProps.store(stream, null);
            }
            return 0;
        } catch (IOException e) {
            console.writeError("Cannot update properties file `%s`: %s", path, e.getMessage());
            return IO_EXCEPTION_EXIT_CODE;
        }
    }

    /**
     * Clears a property while preserving all other property values.
     *
     * @param propertiesFile file to update, or null to use the default file in the working directory
     * @param key property name
     * @return zero on success, or the I/O exit code if the file cannot be read or written
     */
    public int clear(String propertiesFile, String key) {
        return set(propertiesFile, key, null);
    }

    /**
     * Reads a single stored property without applying connection-setting defaults.
     *
     * @param propertiesFile file to read, or null to use the default file in the working directory
     * @param key property name
     * @return the stored value, or null if the file or property does not exist
     * @throws UncheckedIOException if the existing file cannot be read
     */
    public String get(String propertiesFile, String key) {
        return readProperties(propertiesFile).getProperty(key);
    }

    /**
     * Loads stored Java properties without applying CLI defaults or converting values.
     *
     * @param propertiesFile file to read, or null to use the default file in the working directory
     * @return stored properties, or an empty set if the file does not exist
     * @throws UncheckedIOException if the existing file cannot be read
     */
    public java.util.Properties readProperties(String propertiesFile) {
        var path = buildPath(propertiesFile);
        var javaProps = new java.util.Properties();
        if (Files.notExists(path)) {
            log.warn("Path `{}` does not exist", path);
            return javaProps;
        }
        try (var stream = Files.newInputStream(path)) {
            javaProps.load(stream);
            return javaProps;
        } catch (IOException e) {
            throw new UncheckedIOException("Cannot read properties from file %s.".formatted(path), e);
        }
    }
}
