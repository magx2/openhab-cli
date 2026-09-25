package org.openhab.cli.runtime.command.config.properties;

import java.util.concurrent.Callable;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.PropertiesReader;
import picocli.CommandLine;

/** Reads a stored configuration property through the properties service. */
@Slf4j
@CommandLine.Command(
        name = "get",
        description = "Read a property from the CLI configuration file.",
        mixinStandardHelpOptions = true)
public class GetCommand implements Callable<Integer> {
    @CommandLine.Mixin
    private Options options;

    @CommandLine.Parameters(index = "0", arity = "1", paramLabel = "<key>", description = "property key (required)")
    private String key;

    private final PropertiesReader propertiesReader;

    /** Creates the command with the service used to read the properties file. */
    @Inject
    public GetCommand(PropertiesReader propertiesReader) {
        this.propertiesReader = propertiesReader;
    }

    /** Reads the requested property and returns zero on success. */
    @Override
    public Integer call() throws Exception {
        log.info("Getting property value {}", key);
        propertiesReader.get(options.getPropertiesFile(), key);
        return 0;
    }
}
