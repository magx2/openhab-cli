package org.openhab.cli.runtime.command.rules;

import com.google.gson.reflect.TypeToken;
import javax.inject.Inject;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.client.model.Rule;
import org.openhab.cli.engine.endpoint.Rules;
import org.openhab.cli.runtime.JsonArguments;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Creates a rule. */
@Slf4j
@CommandLine.Command(name = "createRule", description = "Creates a rule.", mixinStandardHelpOptions = true)
@RequiredArgsConstructor(access = AccessLevel.PACKAGE, onConstructor_ = @Inject)
public class CreateRule implements Runnable {
    @CommandLine.Mixin
    private Options options;

    @CommandLine.Parameters(
            index = "0",
            arity = "1",
            paramLabel = "<rule>",
            description = "rule data (required) Supply a JSON value.")
    private String rule;

    private final Console console;
    private final ApiClientBuilder apiClientBuilder;

    /** Executes {@link Rules#createRule}. */
    @Override
    public void run() {
        log.debug("Command: Rules.createRule");
        Rule ruleValue = JsonArguments.parse(rule, new TypeToken<Rule>() {}.getType(), "rule");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Rules(apiClient);
        endpoint.createRule(ruleValue);
    }
}
