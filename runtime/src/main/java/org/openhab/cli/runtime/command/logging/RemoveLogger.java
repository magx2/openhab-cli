package org.openhab.cli.runtime.command.logging;

import javax.inject.Inject;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.engine.endpoint.Logging;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Remove a single logger. */
@Slf4j
@CommandLine.Command(name = "removeLogger", description = "Remove a single logger.", mixinStandardHelpOptions = true)
@RequiredArgsConstructor(access = AccessLevel.PACKAGE, onConstructor_ = @Inject)
public class RemoveLogger implements Runnable {
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

    /** Executes {@link Logging#removeLogger}. */
    @Override
    public void run() {
        log.debug("Command: Logging.removeLogger");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Logging(apiClient);
        endpoint.removeLogger(loggerName);
    }
}
