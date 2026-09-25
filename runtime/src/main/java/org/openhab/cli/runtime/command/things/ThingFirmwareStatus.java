package org.openhab.cli.runtime.command.things;

import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.engine.endpoint.Things;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Gets thing's firmware status. */
@Slf4j
@CommandLine.Command(
        name = "thingFirmwareStatus",
        description = "Gets thing's firmware status.",
        mixinStandardHelpOptions = true)
public class ThingFirmwareStatus implements Runnable {
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

    private final Console console;
    private final ApiClientBuilder apiClientBuilder;

    /** Creates the command with injected output and REST client services. */
    @Inject
    ThingFirmwareStatus(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link Things#thingFirmwareStatus}. */
    @Override
    public void run() {
        log.debug("Command: Things.thingFirmwareStatus");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Things(apiClient);
        var result = endpoint.thingFirmwareStatus(thingUID, acceptLanguage);
        console.writeJson(result, options.isPrettyPrint());
    }
}
