package org.openhab.cli.runtime.command.config.server;

import java.util.Collections;
import java.util.concurrent.Callable;
import javax.inject.Inject;
import lombok.RequiredArgsConstructor;
import org.openhab.cli.runtime.PropertiesFileOptions;
import org.openhab.cli.runtime.service.Console;
import org.openhab.cli.runtime.service.PropertiesReader;
import picocli.CommandLine;

/** Removes the saved server URL while retaining authentication and other settings. */
@CommandLine.Command(
        name = "clear",
        description = "Remove the saved server URL, preserving all other properties.",
        mixinStandardHelpOptions = true)
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class ClearServerCommand implements Callable<Integer> {
    @CommandLine.Mixin
    private PropertiesFileOptions options;

    private final PropertiesReader propertiesReader;
    private final Console console;

    /** Clears the URL; clearing an absent file or key succeeds without creating a file. */
    @Override
    public Integer call() {
        var path = PropertiesReader.resolvePath(options.getPropertiesFile());
        var result = propertiesReader.update(path.toString(), Collections.singletonMap("config.rest.baseUrl", null));
        if (result == 0) {
            console.write("Properties file: " + path);
            console.write("Server base URL is not set (config.rest.baseUrl cleared).");
        }
        return result;
    }
}
