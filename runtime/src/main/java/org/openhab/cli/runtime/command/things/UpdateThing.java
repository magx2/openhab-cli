package org.openhab.cli.runtime.command.things;

import com.google.gson.reflect.TypeToken;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.client.model.Thing;
import org.openhab.cli.engine.endpoint.Things;
import org.openhab.cli.runtime.JsonArguments;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Updates a thing. */
@Slf4j
@CommandLine.Command(name = "updateThing", description = "Updates a thing.", mixinStandardHelpOptions = true)
public class UpdateThing implements Runnable {
    @CommandLine.Mixin
    private Options options;

    @CommandLine.Parameters(index = "0", arity = "1", paramLabel = "<thingUID>", description = "thingUID (required)")
    private String thingUID;

    @CommandLine.Parameters(
            index = "1",
            arity = "1",
            paramLabel = "<thing>",
            description = "thing (required) Supply a JSON value.")
    private String thing;

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
    UpdateThing(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link Things#updateThing}. */
    @Override
    public void run() {
        log.debug("Command: Things.updateThing");
        Thing thingValue = JsonArguments.parse(thing, new TypeToken<Thing>() {}.getType(), "thing");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Things(apiClient);
        var result = endpoint.updateThing(thingUID, thingValue, acceptLanguage);
        console.writeJson(result, options.isPrettyPrint());
    }
}
