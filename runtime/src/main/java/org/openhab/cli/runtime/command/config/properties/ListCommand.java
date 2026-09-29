package org.openhab.cli.runtime.command.config.properties;

import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeSet;
import javax.inject.Inject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.runtime.PropertiesFileOptions;
import org.openhab.cli.runtime.service.Console;
import org.openhab.cli.runtime.service.PropertiesReader;
import picocli.CommandLine;

/** Shows all supported and custom properties in a Markdown table, including unset values. */
@Slf4j
@CommandLine.Command(
        name = "list",
        description =
                "Show all supported and stored custom properties with values and descriptions in a Markdown table. Unset values are null.",
        mixinStandardHelpOptions = true)
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class ListCommand implements Runnable {
    private static final Map<String, String> DESCRIPTIONS = Map.ofEntries(
            Map.entry("auth.oAuthToken", "OAuth access token; use instead of username/password."),
            Map.entry("auth.username", "Basic authentication username; use with auth.password."),
            Map.entry("auth.password", "Basic authentication password; use with auth.username."),
            Map.entry("config.rest.baseUrl", "openHAB server URL (HTTP or HTTPS); required for API requests."),
            Map.entry("config.rest.basePath", "REST API path appended to the server URL (default: /rest)."),
            Map.entry("config.prettyPrint", "Pretty-print JSON output (default: true)."),
            Map.entry("config.rest.apiClientDebugging", "Enable HTTP request/response debug output (default: false)."),
            Map.entry("config.ssl.verifyingSsl", "Verify server TLS certificates (default: true)."),
            Map.entry("config.ssl.sslCaCert", "Trusted CA certificate in PEM format; alternative to sslCaCertPath."),
            Map.entry("config.ssl.sslCaCertPath", "Path to a trusted CA certificate; alternative to sslCaCert."),
            Map.entry("config.ssl.tlsServerName", "TLS server name override."),
            Map.entry(
                    "config.timeout.connectTimeout",
                    "Connection timeout in milliseconds (default: 10000; 0: unlimited)."),
            Map.entry("config.timeout.readTimeout", "Read timeout in milliseconds (default: 10000; 0: unlimited)."),
            Map.entry("config.timeout.writeTimeout", "Write timeout in milliseconds (default: 10000; 0: unlimited)."));

    @CommandLine.Mixin
    private PropertiesFileOptions options;

    private final PropertiesReader propertiesReader;
    private final Console console;

    /**
     * Prints the file path and a table of stored values without applying runtime defaults.
     *
     * @throws java.io.UncheckedIOException if the existing properties file cannot be read
     */
    @Override
    public void run() {
        log.info("List properties");
        var path = PropertiesReader.resolvePath(options.getPropertiesFile());
        var properties = propertiesReader.readProperties(path.toString());
        console.write("Properties file: " + path);
        if (Files.notExists(path)) {
            console.write("No properties found: the file does not exist.");
            console.write(
                    "Use '_config properties set <key> <value>' to create it; add '-p <file>' for a custom file.");
        } else if (properties.isEmpty()) {
            console.write("No properties are stored in this file.");
        }
        console.write("Values are read from the file; null means not set. Defaults are described below.");
        console.write("");
        var keys = new TreeSet<>(DESCRIPTIONS.keySet());
        keys.addAll(properties.stringPropertyNames());
        var rows = new ArrayList<String[]>();
        rows.add(new String[] {"Property", "Value", "Description"});
        for (var key : keys) {
            var value = properties.getProperty(key);
            rows.add(new String[] {
                cell(key),
                cell(value == null ? "null" : value.isEmpty() ? "\"\"" : value),
                cell(DESCRIPTIONS.getOrDefault(key, "Custom property; no built-in description."))
            });
        }
        var widths = new int[] {3, 3, 3};
        for (var row : rows) {
            for (int i = 0; i < widths.length; i++) widths[i] = Math.max(widths[i], row[i].length());
        }
        var format = "| %-" + widths[0] + "s | %-" + widths[1] + "s | %-" + widths[2] + "s |";
        console.write(format.formatted((Object[]) rows.getFirst()));
        console.write(
                "| " + "-".repeat(widths[0]) + " | " + "-".repeat(widths[1]) + " | " + "-".repeat(widths[2]) + " |");
        rows.stream().skip(1).forEach(row -> console.write(format.formatted((Object[]) row)));
    }

    private static String cell(String value) {
        return value.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\\", "&#92;")
                .replace("|", "&#124;")
                .replace("`", "&#96;")
                .replace("*", "&#42;")
                .replace("_", "&#95;")
                .replace("[", "&#91;")
                .replace("]", "&#93;")
                .replace("\r\n", "\n")
                .replace("\r", "\n")
                .replace("\n", "<br>");
    }
}
