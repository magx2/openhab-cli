package org.openhab.cli.runtime.command.rules;

import java.util.concurrent.Callable;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.engine.endpoint.Rules;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Simulates the executions of rules filtered by tag 'Schedule' within the given times. */
@Slf4j
@CommandLine.Command(
        name = "scheduleRuleSimulations",
        description = "Simulates the executions of rules filtered by tag 'Schedule' within the given times.",
        mixinStandardHelpOptions = true)
public class ScheduleRuleSimulations implements Callable<Integer> {
    @CommandLine.Mixin
    private Options options;

    @CommandLine.Option(
            names = "--from",
            paramLabel = "<from>",
            description =
                    "Start time of the simulated rule executions. Will default to the current time. [yyyy-MM-dd'T'HH:mm:ss.SSSZ] (optional)",
            arity = "1")
    private String from;

    @CommandLine.Option(
            names = "--until",
            paramLabel = "<until>",
            description =
                    "End time of the simulated rule executions. Will default to 30 days after the start time. Must be less than 180 days after the given start time. [yyyy-MM-dd'T'HH:mm:ss.SSSZ] (optional)",
            arity = "1")
    private String until;

    private final Console console;
    private final ApiClientBuilder apiClientBuilder;

    /** Creates the command with injected output and REST client services. */
    @Inject
    ScheduleRuleSimulations(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link Rules#scheduleRuleSimulations} and returns zero on success. */
    @Override
    public Integer call() {
        log.debug("Command: Rules.scheduleRuleSimulations");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Rules(apiClient);
        var result = endpoint.scheduleRuleSimulations(from, until);
        console.writeJson(result, options.isPrettyPrint());
        return 0;
    }
}
