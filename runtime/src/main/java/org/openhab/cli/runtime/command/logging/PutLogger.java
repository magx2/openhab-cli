package org.openhab.cli.runtime.command.logging;

import com.google.gson.reflect.TypeToken;
import java.util.concurrent.Callable;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.client.model.LoggerInfo;
import org.openhab.cli.engine.endpoint.Logging;
import org.openhab.cli.runtime.JsonArguments;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Modify or add logger */
@Slf4j
@CommandLine.Command(name = "putLogger", description = "Modify or add logger", mixinStandardHelpOptions = true)
public class PutLogger implements Callable<Integer> {
    @CommandLine.Mixin
    private Options options;

    @CommandLine.Parameters(
            index = "0",
            arity = "1",
            paramLabel = "<loggerName>",
            description = "logger name (required)")
    private String loggerName;

    @CommandLine.Parameters(
            index = "1",
            arity = "1",
            paramLabel = "<loggerInfo>",
            description = "logger (required) Supply a JSON value.")
    private String loggerInfo;

    private final Console console;
    private final ApiClientBuilder apiClientBuilder;

    /** Creates the command with injected output and REST client services. */
    @Inject
    PutLogger(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link Logging#putLogger} and returns zero on success. */
    @Override
    public Integer call() {
        log.debug("Command: Logging.putLogger");
        LoggerInfo loggerInfoValue =
                JsonArguments.parse(loggerInfo, new TypeToken<LoggerInfo>() {}.getType(), "loggerInfo");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Logging(apiClient);
        endpoint.putLogger(loggerName, loggerInfoValue);
        return 0;
    }
}
