package org.openhab.cli.runtime.command.config.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.Callable;
import javax.inject.Inject;
import lombok.RequiredArgsConstructor;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.Console;
import org.openhab.cli.runtime.service.PropertiesReader;
import picocli.CommandLine;

/** Stores a server URL supplied on the command line or entered at a prompt. */
@CommandLine.Command(
        name = "set",
        description = "Save the openHAB server URL. Supply --base-url or enter it at the prompt.",
        mixinStandardHelpOptions = true)
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class SetServerCommand implements Callable<Integer> {
    @CommandLine.Mixin
    private Options options;

    @CommandLine.Spec
    private CommandLine.Model.CommandSpec spec;

    private final PropertiesReader propertiesReader;
    private final Console console;

    /** Validates the entered HTTP(S) URL and saves it while preserving other properties. */
    @Override
    public Integer call() throws Exception {
        var baseUrl = options.getBaseUrl();
        if (baseUrl == null) {
            var terminal = System.console();
            if (terminal != null) {
                baseUrl = terminal.readLine("Server base URL: ");
            } else {
                spec.commandLine().getOut().print("Server base URL: ");
                spec.commandLine().getOut().flush();
                baseUrl = new BufferedReader(new InputStreamReader(System.in)).readLine();
            }
        }
        if (baseUrl == null || baseUrl.isBlank()) {
            throw new IllegalArgumentException("Server base URL is required.");
        }
        var validationOptions = new Options();
        new CommandLine(validationOptions).parseArgs("--base-url=" + baseUrl);
        validationOptions
                .overrideProps(org.openhab.cli.engine.properties.Properties.DEFAULT)
                .apiBaseUrl();
        var path = PropertiesReader.resolvePath(options.getPropertiesFile());
        var result = propertiesReader.set(path.toString(), "config.rest.baseUrl", baseUrl);
        if (result == 0) {
            console.write("Properties file: " + path);
            console.write("Saved server base URL (config.rest.baseUrl).");
        }
        return result;
    }
}
