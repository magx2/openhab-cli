package org.openhab.cli.runtime.command.discovery;

import java.util.concurrent.Callable;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.engine.endpoint.Discovery;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Starts asynchronous discovery process for a binding and returns the timeout in seconds of the discovery operation. */
@Slf4j
@CommandLine.Command(
        name = "scan",
        description =
                "Starts asynchronous discovery process for a binding and returns the timeout in seconds of the discovery operation.",
        mixinStandardHelpOptions = true)
public class Scan implements Callable<Integer> {
    @CommandLine.Mixin
    private Options options;

    @CommandLine.Parameters(index = "0", arity = "1", paramLabel = "<bindingId>", description = "binding Id (required)")
    private String bindingId;

    @CommandLine.Option(
            names = "--input",
            paramLabel = "<input>",
            description = "input parameter to start the discovery (optional)",
            arity = "1")
    private String input;

    private final Console console;
    private final ApiClientBuilder apiClientBuilder;

    /** Creates the command with injected output and REST client services. */
    @Inject
    Scan(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link Discovery#scan} and returns zero on success. */
    @Override
    public Integer call() {
        log.debug("Command: Discovery.scan");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Discovery(apiClient);
        var result = endpoint.scan(bindingId, input);
        console.writeJson(result, options.isPrettyPrint());
        return 0;
    }
}
