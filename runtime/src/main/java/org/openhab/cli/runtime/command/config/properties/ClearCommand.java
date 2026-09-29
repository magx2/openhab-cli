package org.openhab.cli.runtime.command.config.properties;

import java.util.concurrent.Callable;
import javax.inject.Inject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.runtime.PropertiesFileOptions;
import org.openhab.cli.runtime.service.Console;
import org.openhab.cli.runtime.service.PropertiesReader;
import picocli.CommandLine;

/** Removes a stored configuration property while preserving other properties. */
@Slf4j
@CommandLine.Command(
        name = "clear",
        description =
                "Remove a property from an existing configuration file and confirm its key and file path. Other properties are preserved.",
        mixinStandardHelpOptions = true)
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class ClearCommand implements Callable<Integer> {
    @CommandLine.Mixin
    private PropertiesFileOptions options;

    @CommandLine.Parameters(index = "0", arity = "1", paramLabel = "<key>", description = "property key (required)")
    private String key;

    private final PropertiesReader propertiesReader;
    private final Console console;

    /** Clears the requested property, reports success with its file path, and returns the exit code. */
    @Override
    public Integer call() throws Exception {
        log.info("Clearing property {}", key);
        var path = PropertiesReader.resolvePath(options.getPropertiesFile());
        var exitCode = propertiesReader.clear(path.toString(), key);
        if (exitCode == 0) {
            console.write("Properties file: " + path);
            console.write("Cleared property '" + key + "' (not set in this file).");
        }
        return exitCode;
    }
}
