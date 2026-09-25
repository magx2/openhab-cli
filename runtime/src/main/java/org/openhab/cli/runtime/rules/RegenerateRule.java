package org.openhab.cli.runtime.rules;

import java.util.concurrent.Callable;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.engine.endpoint.Rules;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Regenerates the rule from its template. */
@Slf4j
@CommandLine.Command(
        name = "regenerateRule",
        description = "Regenerates the rule from its template.",
        mixinStandardHelpOptions = true)
public class RegenerateRule implements Callable<Integer> {
    @CommandLine.Mixin
    private Options options;

    @CommandLine.Parameters(index = "0", arity = "1", paramLabel = "<ruleUID>", description = "ruleUID (required)")
    private String ruleUID;

    private final Console console;
    private final ApiClientBuilder apiClientBuilder;

    /** Creates the command with injected output and REST client services. */
    @Inject
    RegenerateRule(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link Rules#regenerateRule} and returns zero on success. */
    @Override
    public Integer call() {
        log.debug("Command: Rules.regenerateRule");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Rules(apiClient);
        endpoint.regenerateRule(ruleUID);
        return 0;
    }
}
