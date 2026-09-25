package org.openhab.cli.runtime.command.rules;

import java.util.concurrent.Callable;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.engine.endpoint.Rules;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Gets the rule triggers. */
@Slf4j
@CommandLine.Command(name = "ruleTriggers", description = "Gets the rule triggers.", mixinStandardHelpOptions = true)
public class RuleTriggers implements Callable<Integer> {
    @CommandLine.Mixin
    private Options options;

    @CommandLine.Parameters(index = "0", arity = "1", paramLabel = "<ruleUID>", description = "ruleUID (required)")
    private String ruleUID;

    private final Console console;
    private final ApiClientBuilder apiClientBuilder;

    /** Creates the command with injected output and REST client services. */
    @Inject
    RuleTriggers(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link Rules#ruleTriggers} and returns zero on success. */
    @Override
    public Integer call() {
        log.debug("Command: Rules.ruleTriggers");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Rules(apiClient);
        var result = endpoint.ruleTriggers(ruleUID);
        console.writeJson(result, options.isPrettyPrint());
        return 0;
    }
}
