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

/** Get all loggers */
@Slf4j
@CommandLine.Command(name = "logger1", description = "Get all loggers", mixinStandardHelpOptions = true)
@RequiredArgsConstructor(access = AccessLevel.PACKAGE, onConstructor_ = @Inject)
public class Logger1 implements Runnable {
    @CommandLine.Mixin
    private Options options;

    private final Console console;
    private final ApiClientBuilder apiClientBuilder;

    /** Executes {@link Logging#logger1}. */
    @Override
    public void run() {
        log.debug("Command: Logging.logger1");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Logging(apiClient);
        var result = endpoint.logger1();
        console.writeJson(result, options.isPrettyPrint());
    }
}
