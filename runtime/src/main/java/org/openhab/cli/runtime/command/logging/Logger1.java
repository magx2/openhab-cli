package org.openhab.cli.runtime.command.logging;

import java.util.concurrent.Callable;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.engine.endpoint.Logging;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Get all loggers */
@Slf4j
@CommandLine.Command(name = "logger1", description = "Get all loggers", mixinStandardHelpOptions = true)
public class Logger1 implements Callable<Integer> {
    @CommandLine.Mixin
    private Options options;

    private final Console console;
    private final ApiClientBuilder apiClientBuilder;

    /** Creates the command with injected output and REST client services. */
    @Inject
    Logger1(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link Logging#logger1} and returns zero on success. */
    @Override
    public Integer call() {
        log.debug("Command: Logging.logger1");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Logging(apiClient);
        var result = endpoint.logger1();
        console.writeJson(result, options.isPrettyPrint());
        return 0;
    }
}
