package org.openhab.cli.runtime.command.rules;

import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.engine.endpoint.Rules;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Sets the module's configuration parameter value. */
@Slf4j
@CommandLine.Command(
        name = "setRuleModuleConfigParameter",
        description = "Sets the module's configuration parameter value.",
        mixinStandardHelpOptions = true)
public class SetRuleModuleConfigParameter implements Runnable {
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

    @CommandLine.Parameters(index = "4", arity = "1", paramLabel = "<body>", description = "value (required)")
    private String body;

    private final Console console;
    private final ApiClientBuilder apiClientBuilder;

    /** Creates the command with injected output and REST client services. */
    @Inject
    SetRuleModuleConfigParameter(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link Rules#setRuleModuleConfigParameter}. */
    @Override
    public void run() {
        log.debug("Command: Rules.setRuleModuleConfigParameter");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Rules(apiClient);
        endpoint.setRuleModuleConfigParameter(ruleUID, moduleCategory, id, param, body);
    }
}
