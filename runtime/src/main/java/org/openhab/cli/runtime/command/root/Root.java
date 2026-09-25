package org.openhab.cli.runtime.command.root;

import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Gets information about the runtime, the API version and links to resources. */
@Slf4j
@CommandLine.Command(
        name = "root",
        description = "Gets information about the runtime, the API version and links to resources.",
        mixinStandardHelpOptions = true)
public class Root implements Runnable {
    @CommandLine.Mixin
    private Options options;

    private final Console console;
    private final ApiClientBuilder apiClientBuilder;

    /** Creates the command with injected output and REST client services. */
    @Inject
    Root(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link org.openhab.cli.engine.endpoint.Root#root}. */
    @Override
    public void run() {
        log.debug("Command: Root.root");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new org.openhab.cli.engine.endpoint.Root(apiClient);
        var result = endpoint.root();
        console.writeJson(result, options.isPrettyPrint());
    }
}
