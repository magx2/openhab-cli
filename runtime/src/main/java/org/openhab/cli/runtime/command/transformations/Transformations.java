package org.openhab.cli.runtime.command.transformations;

import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Get a list of all transformations */
@Slf4j
@CommandLine.Command(
        name = "transformations",
        description = "Get a list of all transformations",
        mixinStandardHelpOptions = true)
public class Transformations implements Runnable {
    @CommandLine.Mixin
    private Options options;

    private final Console console;
    private final ApiClientBuilder apiClientBuilder;

    /** Creates the command with injected output and REST client services. */
    @Inject
    Transformations(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link org.openhab.cli.engine.endpoint.Transformations#transformations}. */
    @Override
    public void run() {
        log.debug("Command: Transformations.transformations");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new org.openhab.cli.engine.endpoint.Transformations(apiClient);
        var result = endpoint.transformations();
        console.writeJson(result, options.isPrettyPrint());
    }
}
