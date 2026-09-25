package org.openhab.cli.runtime.command.things;

import java.util.concurrent.Callable;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.engine.endpoint.Things;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Gets thing by UID. */
@Slf4j
@CommandLine.Command(name = "thingById", description = "Gets thing by UID.", mixinStandardHelpOptions = true)
public class ThingById implements Callable<Integer> {
    @CommandLine.Mixin
    private Options options;

    @CommandLine.Parameters(index = "0", arity = "1", paramLabel = "<thingUID>", description = "thingUID (required)")
    private String thingUID;

    @CommandLine.Option(
            names = "--accept-language",
            paramLabel = "<acceptLanguage>",
            description = "language (optional)",
            arity = "1")
    private String acceptLanguage;

    private final Console console;
    private final ApiClientBuilder apiClientBuilder;

    /** Creates the command with injected output and REST client services. */
    @Inject
    ThingById(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link Things#thingById} and returns zero on success. */
    @Override
    public Integer call() {
        log.debug("Command: Things.thingById");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Things(apiClient);
        var result = endpoint.thingById(thingUID, acceptLanguage);
        console.writeJson(result, options.isPrettyPrint());
        return 0;
    }
}
