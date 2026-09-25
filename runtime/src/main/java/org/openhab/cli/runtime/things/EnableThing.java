package org.openhab.cli.runtime.things;

import java.util.concurrent.Callable;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.engine.endpoint.Things;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Sets the thing enabled status. */
@Slf4j
@CommandLine.Command(
        name = "enableThing",
        description = "Sets the thing enabled status.",
        mixinStandardHelpOptions = true)
public class EnableThing implements Callable<Integer> {
    @CommandLine.Mixin
    private Options options;

    @CommandLine.Parameters(index = "0", arity = "1", paramLabel = "<thingUID>", description = "thing (required)")
    private String thingUID;

    @CommandLine.Option(
            names = "--accept-language",
            paramLabel = "<acceptLanguage>",
            description = "language (optional)",
            arity = "1")
    private String acceptLanguage;

    @CommandLine.Option(names = "--body", paramLabel = "<body>", description = "enabled (optional)", arity = "1")
    private String body;

    private final Console console;
    private final ApiClientBuilder apiClientBuilder;

    /** Creates the command with injected output and REST client services. */
    @Inject
    EnableThing(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link Things#enableThing} and returns zero on success. */
    @Override
    public Integer call() {
        log.debug("Command: Things.enableThing");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Things(apiClient);
        var result = endpoint.enableThing(thingUID, acceptLanguage, body);
        console.writeJson(result, options.isPrettyPrint());
        return 0;
    }
}
