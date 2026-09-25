package org.openhab.cli.runtime.command.things;

import java.util.concurrent.Callable;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.engine.endpoint.Things;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Get all available firmwares for provided thing UID */
@Slf4j
@CommandLine.Command(
        name = "availableFirmwaresForThing",
        description = "Get all available firmwares for provided thing UID",
        mixinStandardHelpOptions = true)
public class AvailableFirmwaresForThing implements Callable<Integer> {
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
    AvailableFirmwaresForThing(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link Things#availableFirmwaresForThing} and returns zero on success. */
    @Override
    public Integer call() {
        log.debug("Command: Things.availableFirmwaresForThing");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Things(apiClient);
        var result = endpoint.availableFirmwaresForThing(thingUID, acceptLanguage);
        console.writeJson(result, options.isPrettyPrint());
        return 0;
    }
}
