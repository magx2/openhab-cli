package org.openhab.cli.runtime.command.config.properties;

import java.util.concurrent.Callable;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.PropertiesReader;
import picocli.CommandLine;

/** Adds or replaces a stored configuration property while preserving other properties. */
@Slf4j
@CommandLine.Command(
        name = "set",
        description =
                "Add or replace a property, creating the configuration file if needed. Other properties are preserved.",
        mixinStandardHelpOptions = true)
public class SetCommand implements Callable<Integer> {
    @CommandLine.Mixin
    private Options options;

    @CommandLine.Parameters(index = "0", arity = "1", paramLabel = "<key>", description = "property key (required)")
    private String key;

    @CommandLine.Parameters(index = "1", arity = "1", paramLabel = "<value>", description = "property value (required)")
    private String value;

    private final PropertiesReader propertiesReader;

    /** Creates the command with the service used to update the properties file. */
    @Inject
    public SetCommand(PropertiesReader propertiesReader) {
        this.propertiesReader = propertiesReader;
    }

    /** Stores the requested property and returns zero on success or the I/O error exit code. */
    @Override
    public Integer call() throws Exception {
        log.info("Settting property value {}={}", key, value);
        return propertiesReader.set(options.getPropertiesFile(), key, value);
    }
}
