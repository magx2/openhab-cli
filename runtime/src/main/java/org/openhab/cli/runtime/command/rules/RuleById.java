package org.openhab.cli.runtime.command.rules;

import javax.inject.Inject;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.engine.endpoint.Rules;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Gets the rule corresponding to the given UID. */
@Slf4j
@CommandLine.Command(
        name = "ruleById",
        description = "Gets the rule corresponding to the given UID.",
        mixinStandardHelpOptions = true)
@RequiredArgsConstructor(access = AccessLevel.PACKAGE, onConstructor_ = @Inject)
public class RuleById implements Runnable {
    @CommandLine.Mixin
    private Options options;

    @CommandLine.Parameters(index = "0", arity = "1", paramLabel = "<ruleUID>", description = "ruleUID (required)")
    private String ruleUID;

    private final Console console;
    private final ApiClientBuilder apiClientBuilder;

    /** Executes {@link Rules#ruleById}. */
    @Override
    public void run() {
        log.debug("Command: Rules.ruleById");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Rules(apiClient);
        var result = endpoint.ruleById(ruleUID);
        console.writeJson(result, options.isPrettyPrint());
    }
}
