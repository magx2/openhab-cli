package org.openhab.cli.runtime.command.config.properties;

import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.Console;
import org.openhab.cli.runtime.service.PropertiesReader;
import picocli.CommandLine;

/** Prints CLI configuration properties as JSON, including defaults for unspecified settings. */
@Slf4j
@CommandLine.Command(
        name = "list",
        description = "List CLI configuration properties as JSON, including defaults for unspecified settings.",
        mixinStandardHelpOptions = true)
public class ListCommand implements Runnable {
    @CommandLine.Mixin
    private Options options;

    private final PropertiesReader propertiesReader;
    private final Console console;

    /** Creates the command with services for reading configuration properties and printing JSON. */
    @Inject
    public ListCommand(PropertiesReader propertiesReader, Console console) {
        this.propertiesReader = propertiesReader;
        this.console = console;
    }

    /**
     * Reads the selected properties file and prints its configuration using the stored pretty-print setting.
     *
     * @throws java.io.UncheckedIOException if the existing properties file cannot be read
     */
    @Override
    public void run() {
        log.info("List properties");
        var properties = propertiesReader.read(options.getPropertiesFile());
        console.writeJson(properties, properties.prettyPrint());
    }
}
