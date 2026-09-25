package org.openhab.cli.runtime.command.config.properties;

import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.Console;
import org.openhab.cli.runtime.service.PropertiesReader;
import picocli.CommandLine;

/** Prints stored Java properties as one key=value entry per line, ordered by key. */
@Slf4j
@CommandLine.Command(
        name = "list",
        description = "List stored Java properties as key=value, one per line ordered by key, without CLI defaults.",
        mixinStandardHelpOptions = true)
public class ListCommand implements Runnable {
    @CommandLine.Mixin
    private Options options;

    private final PropertiesReader propertiesReader;
    private final Console console;

    /** Creates the command with services for reading stored properties and printing text. */
    @Inject
    public ListCommand(PropertiesReader propertiesReader, Console console) {
        this.propertiesReader = propertiesReader;
        this.console = console;
    }

    /**
     * Reads the selected properties file and prints each stored entry as key=value on its own line.
     *
     * @throws java.io.UncheckedIOException if the existing properties file cannot be read
     */
    @Override
    public void run() {
        log.info("List properties");
        var properties = propertiesReader.readProperties(options.getPropertiesFile());
        properties.stringPropertyNames().stream()
                .sorted()
                .forEach(key -> console.write(key + "=" + properties.getProperty(key)));
    }
}
