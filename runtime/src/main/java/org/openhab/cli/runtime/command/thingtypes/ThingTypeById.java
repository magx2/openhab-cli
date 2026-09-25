package org.openhab.cli.runtime.command.thingtypes;

import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.engine.endpoint.ThingTypes;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Gets thing type by UID. */
@Slf4j
@CommandLine.Command(name = "thingTypeById", description = "Gets thing type by UID.", mixinStandardHelpOptions = true)
public class ThingTypeById implements Runnable {
    @CommandLine.Mixin
    private Options options;

    @CommandLine.Parameters(
            index = "0",
            arity = "1",
            paramLabel = "<thingTypeUID>",
            description = "thingTypeUID (required)")
    private String thingTypeUID;

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
    ThingTypeById(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link ThingTypes#thingTypeById}. */
    @Override
    public void run() {
        log.debug("Command: ThingTypes.thingTypeById");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new ThingTypes(apiClient);
        var result = endpoint.thingTypeById(thingTypeUID, acceptLanguage);
        console.writeJson(result, options.isPrettyPrint());
    }
}
