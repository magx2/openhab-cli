package org.openhab.cli.runtime.command.rules;

import com.google.gson.reflect.TypeToken;
import java.util.concurrent.Callable;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.client.model.Rule;
import org.openhab.cli.engine.endpoint.Rules;
import org.openhab.cli.runtime.JsonArguments;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Updates an existing rule corresponding to the given UID. */
@Slf4j
@CommandLine.Command(
        name = "updateRule",
        description = "Updates an existing rule corresponding to the given UID.",
        mixinStandardHelpOptions = true)
public class UpdateRule implements Callable<Integer> {
    @CommandLine.Mixin
    private Options options;

    @CommandLine.Parameters(index = "0", arity = "1", paramLabel = "<ruleUID>", description = "ruleUID (required)")
    private String ruleUID;

    @CommandLine.Parameters(
            index = "1",
            arity = "1",
            paramLabel = "<rule>",
            description = "rule data (required) Supply a JSON value.")
    private String rule;

    private final Console console;
    private final ApiClientBuilder apiClientBuilder;

    /** Creates the command with injected output and REST client services. */
    @Inject
    UpdateRule(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link Rules#updateRule} and returns zero on success. */
    @Override
    public Integer call() {
        log.debug("Command: Rules.updateRule");
        Rule ruleValue = JsonArguments.parse(rule, new TypeToken<Rule>() {}.getType(), "rule");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Rules(apiClient);
        endpoint.updateRule(ruleUID, ruleValue);
        return 0;
    }
}
