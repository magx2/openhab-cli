package org.openhab.cli.runtime.command.config.properties;

import java.util.concurrent.Callable;
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
public class ListCommand implements Callable<Integer> {
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
     * @return zero on success
     * @throws java.io.UncheckedIOException if the existing properties file cannot be read
     */
    @Override
    public Integer call() throws Exception {
        log.info("List properties");
        var properties = propertiesReader.read(options.getPropertiesFile());
        console.writeJson(properties, properties.prettyPrint());
        return 0;
    }
}
