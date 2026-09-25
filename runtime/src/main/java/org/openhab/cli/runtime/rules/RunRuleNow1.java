package org.openhab.cli.runtime.rules;

import com.google.gson.reflect.TypeToken;
import java.util.Map;
import java.util.concurrent.Callable;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.engine.endpoint.Rules;
import org.openhab.cli.runtime.JsonArguments;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Executes actions of the rule. */
@Slf4j
@CommandLine.Command(
        name = "runRuleNow1",
        description = "Executes actions of the rule.",
        mixinStandardHelpOptions = true)
public class RunRuleNow1 implements Callable<Integer> {
    @CommandLine.Mixin
    private Options options;

    @CommandLine.Parameters(index = "0", arity = "1", paramLabel = "<ruleUID>", description = "ruleUID (required)")
    private String ruleUID;

    @CommandLine.Option(
            names = "--request-body",
            paramLabel = "<requestBody>",
            description = "the context for running this rule (optional) Supply a JSON value.",
            arity = "1")
    private String requestBody;

    private final Console console;
    private final ApiClientBuilder apiClientBuilder;

    /** Creates the command with injected output and REST client services. */
    @Inject
    RunRuleNow1(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link Rules#runRuleNow1} and returns zero on success. */
    @Override
    public Integer call() {
        log.debug("Command: Rules.runRuleNow1");
        Map<String, Object> requestBodyValue =
                JsonArguments.parse(requestBody, new TypeToken<Map<String, Object>>() {}.getType(), "requestBody");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Rules(apiClient);
        endpoint.runRuleNow1(ruleUID, requestBodyValue);
        return 0;
    }
}
