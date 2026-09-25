package org.openhab.cli.runtime.command.rules;

import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.engine.endpoint.Rules;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Sets the rule enabled status. */
@Slf4j
@CommandLine.Command(
        name = "enableRule",
        description = "Sets the rule enabled status.",
        mixinStandardHelpOptions = true)
public class EnableRule implements Runnable {
    @CommandLine.Mixin
    private Options options;

    @CommandLine.Parameters(index = "0", arity = "1", paramLabel = "<ruleUID>", description = "ruleUID (required)")
    private String ruleUID;

    @CommandLine.Parameters(index = "1", arity = "1", paramLabel = "<body>", description = "enable (required)")
    private String body;

    private final Console console;
    private final ApiClientBuilder apiClientBuilder;

    /** Creates the command with injected output and REST client services. */
    @Inject
    EnableRule(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link Rules#enableRule}. */
    @Override
    public void run() {
        log.debug("Command: Rules.enableRule");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Rules(apiClient);
        endpoint.enableRule(ruleUID, body);
    }
}
