package org.openhab.cli.runtime.command.rules;

import java.util.concurrent.Callable;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.engine.endpoint.Rules;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Gets the rule actions. */
@Slf4j
@CommandLine.Command(name = "ruleActions", description = "Gets the rule actions.", mixinStandardHelpOptions = true)
public class RuleActions implements Callable<Integer> {
    @CommandLine.Mixin
    private Options options;

    @CommandLine.Parameters(index = "0", arity = "1", paramLabel = "<ruleUID>", description = "ruleUID (required)")
    private String ruleUID;

    private final Console console;
    private final ApiClientBuilder apiClientBuilder;

    /** Creates the command with injected output and REST client services. */
    @Inject
    RuleActions(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link Rules#ruleActions} and returns zero on success. */
    @Override
    public Integer call() {
        log.debug("Command: Rules.ruleActions");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Rules(apiClient);
        var result = endpoint.ruleActions(ruleUID);
        console.writeJson(result, options.isPrettyPrint());
        return 0;
    }
}
