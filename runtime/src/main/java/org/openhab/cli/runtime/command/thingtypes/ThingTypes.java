package org.openhab.cli.runtime.command.thingtypes;

import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Gets all available thing types without config description, channels and properties. */
@Slf4j
@CommandLine.Command(
        name = "thingTypes",
        description = "Gets all available thing types without config description, channels and properties.",
        mixinStandardHelpOptions = true)
public class ThingTypes implements Runnable {
    @CommandLine.Mixin
    private Options options;

    @CommandLine.Option(
            names = "--accept-language",
            paramLabel = "<acceptLanguage>",
            description = "language (optional)",
            arity = "1")
    private String acceptLanguage;

    @CommandLine.Option(
            names = "--binding-id",
            paramLabel = "<bindingId>",
            description = "filter by binding Id (optional)",
            arity = "1")
    private String bindingId;

    private final Console console;
    private final ApiClientBuilder apiClientBuilder;

    /** Creates the command with injected output and REST client services. */
    @Inject
    ThingTypes(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link org.openhab.cli.engine.endpoint.ThingTypes#thingTypes}. */
    @Override
    public void run() {
        log.debug("Command: ThingTypes.thingTypes");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new org.openhab.cli.engine.endpoint.ThingTypes(apiClient);
        var result = endpoint.thingTypes(acceptLanguage, bindingId);
        console.writeJson(result, options.isPrettyPrint());
    }
}
