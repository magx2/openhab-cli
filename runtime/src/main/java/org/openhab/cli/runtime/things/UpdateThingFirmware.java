package org.openhab.cli.runtime.things;

import java.util.concurrent.Callable;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.engine.endpoint.Things;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Update thing firmware. */
@Slf4j
@CommandLine.Command(
        name = "updateThingFirmware",
        description = "Update thing firmware.",
        mixinStandardHelpOptions = true)
public class UpdateThingFirmware implements Callable<Integer> {
    @CommandLine.Mixin
    private Options options;

    @CommandLine.Parameters(index = "0", arity = "1", paramLabel = "<thingUID>", description = "thing (required)")
    private String thingUID;

    @CommandLine.Parameters(
            index = "1",
            arity = "1",
            paramLabel = "<firmwareVersion>",
            description = "version (required)")
    private String firmwareVersion;

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
    UpdateThingFirmware(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link Things#updateThingFirmware} and returns zero on success. */
    @Override
    public Integer call() {
        log.debug("Command: Things.updateThingFirmware");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Things(apiClient);
        endpoint.updateThingFirmware(thingUID, firmwareVersion, acceptLanguage);
        return 0;
    }
}
