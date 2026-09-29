package org.openhab.cli.runtime.command.config.properties;

import java.nio.file.Files;
import javax.inject.Inject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.runtime.PropertiesFileOptions;
import org.openhab.cli.runtime.service.Console;
import org.openhab.cli.runtime.service.PropertiesReader;
import picocli.CommandLine;

/** Shows the properties file and a stored key=value entry, or explains why the property is absent. */
@Slf4j
@CommandLine.Command(
        name = "get",
        description = "Show the properties file and a stored key=value entry. Report missing files or keys.",
        mixinStandardHelpOptions = true)
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class GetCommand implements Runnable {
    @CommandLine.Mixin
    private PropertiesFileOptions options;

    @CommandLine.Parameters(index = "0", arity = "1", paramLabel = "<key>", description = "property key (required)")
    private String key;

    private final PropertiesReader propertiesReader;
    private final Console console;

    /** Prints the source path and requested key=value entry, or a descriptive missing-file/key message. */
    @Override
    public void run() {
        log.info("Getting property value {}", key);
        var path = PropertiesReader.resolvePath(options.getPropertiesFile());
        var value = propertiesReader.get(path.toString(), key);
        console.write("Properties file: " + path);
        if (Files.notExists(path)) {
            console.write("Cannot get property '" + key + "': the file does not exist.");
        } else if (value == null) {
            console.write("Property '" + key + "' is not set in this file.");
        } else {
            console.write(key + "=" + value);
        }
    }
}
