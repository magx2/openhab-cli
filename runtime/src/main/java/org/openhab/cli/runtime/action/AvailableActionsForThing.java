package org.openhab.cli.runtime.action;

import java.util.concurrent.Callable;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.engine.endpoint.Action;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;
import picocli.CommandLine.Parameters;

@Slf4j
@CommandLine.Command(name = "availableActionsForThing")
public class AvailableActionsForThing implements Callable<Integer> {
    @CommandLine.Mixin
    private Options options;

    @Parameters(index = "0")
    private String thingUID;

    @Parameters(index = "1", arity = "0..1")
    private String acceptLanguage;

    private final Console console;
    private final ApiClientBuilder apiClientBuilder;

    @Inject
    AvailableActionsForThing(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    @Override
    public Integer call() {
        log.debug(
                "Command: {}, thingUID={}, acceptLanguage={}",
                this.getClass().getSimpleName(),
                thingUID,
                acceptLanguage);

        var apiClient = apiClientBuilder.build(options);
        var action = new Action(apiClient);
        var thingActions = action.availableActionsForThing(thingUID, acceptLanguage);
        console.writeJson(thingActions, options.isPrettyPrint());

        return 0;
    }
}
