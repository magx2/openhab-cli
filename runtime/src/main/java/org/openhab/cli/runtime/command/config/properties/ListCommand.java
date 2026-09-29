package org.openhab.cli.runtime.command.config.properties;

import java.nio.file.Files;
import javax.inject.Inject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.Console;
import org.openhab.cli.runtime.service.PropertiesReader;
import picocli.CommandLine;

/** Shows the properties file and its stored key=value entries, or explains why no entries are available. */
@Slf4j
@CommandLine.Command(
        name = "list",
        description =
                "Show the properties file and stored key=value entries, ordered by key. Report missing or empty files; omit CLI defaults.",
        mixinStandardHelpOptions = true)
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class ListCommand implements Runnable {
    @CommandLine.Mixin
    private Options options;

    private final PropertiesReader propertiesReader;
    private final Console console;

    /**
     * Reads the selected file and prints its path and each key=value entry, or a missing/empty-file message.
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
            return;
        }
        if (properties.isEmpty()) {
            console.write("No properties are stored in this file.");
            return;
        }
        console.write("Stored properties (" + properties.size() + "):");
        properties.stringPropertyNames().stream()
                .sorted()
                .forEach(key -> console.write(key + "=" + properties.getProperty(key)));
    }
}
