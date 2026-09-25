package org.openhab.cli.runtime.command.action;

import java.util.concurrent.Callable;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.engine.endpoint.Action;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Get all available actions for provided thing UID */
@Slf4j
@CommandLine.Command(
        name = "availableActionsForThing",
        description = "Get all available actions for provided thing UID",
        mixinStandardHelpOptions = true)
public class AvailableActionsForThing implements Callable<Integer> {
    @CommandLine.Mixin
    private Options options;

    @CommandLine.Parameters(index = "0", arity = "1", paramLabel = "<thingUID>", description = "thingUID (required)")
    private String thingUID;

    @CommandLine.Parameters(
            index = "1",
            arity = "0..1",
            paramLabel = "<acceptLanguage>",
            description = "language (optional)")
    private String acceptLanguage;

    private final Console console;
    private final ApiClientBuilder apiClientBuilder;

    /** Creates the command with injected output and REST client services. */
    @Inject
    AvailableActionsForThing(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link Action#availableActionsForThing} and returns zero on success. */
    @Override
    public Integer call() {
        log.debug("Command: Action.availableActionsForThing");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Action(apiClient);
        var result = endpoint.availableActionsForThing(thingUID, acceptLanguage);
        console.writeJson(result, options.isPrettyPrint());
        return 0;
    }
}
