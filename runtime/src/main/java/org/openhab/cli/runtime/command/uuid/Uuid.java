package org.openhab.cli.runtime.command.uuid;

import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** A unified unique id. */
@Slf4j
@CommandLine.Command(name = "uuid", description = "A unified unique id.", mixinStandardHelpOptions = true)
public class Uuid implements Runnable {
    @CommandLine.Mixin
    private Options options;

    private final Console console;
    private final ApiClientBuilder apiClientBuilder;

    /** Creates the command with injected output and REST client services. */
    @Inject
    Uuid(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link org.openhab.cli.engine.endpoint.Uuid#uuid}. */
    @Override
    public void run() {
        log.debug("Command: Uuid.uuid");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new org.openhab.cli.engine.endpoint.Uuid(apiClient);
        var result = endpoint.uuid();
        console.writeJson(result, options.isPrettyPrint());
    }
}
