package org.openhab.cli.runtime.command.rules;

import java.util.concurrent.Callable;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.engine.endpoint.Rules;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Gets the module's configuration parameter. */
@Slf4j
@CommandLine.Command(
        name = "ruleModuleConfigParameter",
        description = "Gets the module's configuration parameter.",
        mixinStandardHelpOptions = true)
public class RuleModuleConfigParameter implements Callable<Integer> {
    @CommandLine.Mixin
    private Options options;

    @CommandLine.Parameters(index = "0", arity = "1", paramLabel = "<ruleUID>", description = "ruleUID (required)")
    private String ruleUID;

    @CommandLine.Parameters(
            index = "1",
            arity = "1",
            paramLabel = "<moduleCategory>",
            description = "moduleCategory (required)")
    private String moduleCategory;

    @CommandLine.Parameters(index = "2", arity = "1", paramLabel = "<id>", description = "id (required)")
    private String id;

    @CommandLine.Parameters(index = "3", arity = "1", paramLabel = "<param>", description = "param (required)")
    private String param;

    private final Console console;
    private final ApiClientBuilder apiClientBuilder;

    /** Creates the command with injected output and REST client services. */
    @Inject
    RuleModuleConfigParameter(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link Rules#ruleModuleConfigParameter} and returns zero on success. */
    @Override
    public Integer call() {
        log.debug("Command: Rules.ruleModuleConfigParameter");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Rules(apiClient);
        var result = endpoint.ruleModuleConfigParameter(ruleUID, moduleCategory, id, param);
        console.writeJson(result, options.isPrettyPrint());
        return 0;
    }
}
