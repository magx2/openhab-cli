package org.openhab.cli.runtime.command.logging;

import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.engine.endpoint.Logging;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Get a single logger. */
@Slf4j
@CommandLine.Command(name = "logger", description = "Get a single logger.", mixinStandardHelpOptions = true)
public class Logger implements Runnable {
    @CommandLine.Mixin
    private Options options;

    @CommandLine.Parameters(
            index = "0",
            arity = "1",
            paramLabel = "<loggerName>",
            description = "logger name (required)")
    private String loggerName;

    private final Console console;
    private final ApiClientBuilder apiClientBuilder;

    /** Creates the command with injected output and REST client services. */
    @Inject
    Logger(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link Logging#logger}. */
    @Override
    public void run() {
        log.debug("Command: Logging.logger");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Logging(apiClient);
        var result = endpoint.logger(loggerName);
        console.writeJson(result, options.isPrettyPrint());
    }
}
