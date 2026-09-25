package org.openhab.cli.runtime.command.config.properties;

import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.Console;
import org.openhab.cli.runtime.service.PropertiesReader;
import picocli.CommandLine;

/** Prints a stored configuration property in key=value format. */
@Slf4j
@CommandLine.Command(
        name = "get",
        description = "Print a property from the CLI configuration file as key=value.",
        mixinStandardHelpOptions = true)
public class GetCommand implements Runnable {
    @CommandLine.Mixin
    private Options options;

    @CommandLine.Parameters(index = "0", arity = "1", paramLabel = "<key>", description = "property key (required)")
    private String key;

    private final PropertiesReader propertiesReader;
    private final Console console;

    /** Creates the command with services for reading and printing a property. */
    @Inject
    public GetCommand(PropertiesReader propertiesReader, Console console) {
        this.propertiesReader = propertiesReader;
        this.console = console;
    }

    /** Prints the requested key and its stored value, using null when the property is absent. */
    @Override
    public void run() {
        log.info("Getting property value {}", key);
        var value = propertiesReader.get(options.getPropertiesFile(), key);
        console.write(key + "=" + value);
    }
}
