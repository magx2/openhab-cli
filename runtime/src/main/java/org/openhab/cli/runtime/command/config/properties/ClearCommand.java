package org.openhab.cli.runtime.command.config.properties;

import java.util.concurrent.Callable;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.PropertiesReader;
import picocli.CommandLine;

/** Removes a stored configuration property while preserving other properties. */
@Slf4j
@CommandLine.Command(
        name = "clear",
        description = "Remove a property from an existing configuration file. Other properties are preserved.",
        mixinStandardHelpOptions = true)
public class ClearCommand implements Callable<Integer> {
    @CommandLine.Mixin
    private Options options;

    @CommandLine.Parameters(index = "0", arity = "1", paramLabel = "<key>", description = "property key (required)")
    private String key;

    private final PropertiesReader propertiesReader;

    /** Creates the command with the service used to update the properties file. */
    @Inject
    public ClearCommand(PropertiesReader propertiesReader) {
        this.propertiesReader = propertiesReader;
    }

    /** Clears the requested property and returns zero on success or the I/O error exit code. */
    @Override
    public Integer call() throws Exception {
        log.info("Clearing property {}", key);
        return propertiesReader.clear(options.getPropertiesFile(), key);
    }
}
