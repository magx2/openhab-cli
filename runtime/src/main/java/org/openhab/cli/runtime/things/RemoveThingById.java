package org.openhab.cli.runtime.things;

import java.util.concurrent.Callable;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.engine.endpoint.Things;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Removes a thing from the registry. Set 'force' to __true__ if you want the thing to be removed immediately. */
@Slf4j
@CommandLine.Command(
        name = "removeThingById",
        description =
                "Removes a thing from the registry. Set 'force' to __true__ if you want the thing to be removed immediately.",
        mixinStandardHelpOptions = true)
public class RemoveThingById implements Callable<Integer> {
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

    @CommandLine.Parameters(
            index = "1",
            arity = "1",
            paramLabel = "<force>",
            description = "force (optional, default to false)")
    private Boolean force;

    private final Console console;
    private final ApiClientBuilder apiClientBuilder;

    /** Creates the command with injected output and REST client services. */
    @Inject
    RemoveThingById(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link Things#removeThingById} and returns zero on success. */
    @Override
    public Integer call() {
        log.debug("Command: Things.removeThingById");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Things(apiClient);
        endpoint.removeThingById(thingUID, acceptLanguage, force);
        return 0;
    }
}
