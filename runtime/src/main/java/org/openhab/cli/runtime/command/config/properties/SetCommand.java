package org.openhab.cli.runtime.command.config.properties;

import java.nio.file.Files;
import java.util.concurrent.Callable;
import javax.inject.Inject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.Console;
import org.openhab.cli.runtime.service.PropertiesReader;
import picocli.CommandLine;

/** Adds or replaces a stored configuration property while preserving other properties. */
@Slf4j
@CommandLine.Command(
        name = "set",
        description =
                "Add or replace a property, creating the configuration file if needed. Report the destination file on success. Other properties are preserved.",
        mixinStandardHelpOptions = true)
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class SetCommand implements Callable<Integer> {
    @CommandLine.Mixin
    private Options options;

    @CommandLine.Parameters(index = "0", arity = "1", paramLabel = "<key>", description = "property key (required)")
    private String key;

    @CommandLine.Parameters(index = "1", arity = "1", paramLabel = "<value>", description = "property value (required)")
    private String value;

    private final PropertiesReader propertiesReader;
    private final Console console;

    /** Stores the requested property, confirms its destination without echoing its value, and returns the exit code. */
    @Override
    public Integer call() throws Exception {
        log.info("Setting property {}", key);
        var path = PropertiesReader.resolvePath(options.getPropertiesFile());
        var creating = Files.notExists(path);
        var exitCode = propertiesReader.set(path.toString(), key, value);
        if (exitCode == 0) {
            console.write("Properties file: " + path);
            if (creating) console.write("Created the properties file.");
            console.write("Saved property '" + key + "'.");
        }
        return exitCode;
    }
}
