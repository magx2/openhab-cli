package org.openhab.cli.runtime.command.action;

import com.google.gson.reflect.TypeToken;
import java.util.Map;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.engine.endpoint.Action;
import org.openhab.cli.runtime.JsonArguments;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Executes a thing action. */
@Slf4j
@CommandLine.Command(
        name = "executeThingAction",
        description = "Executes a thing action.",
        mixinStandardHelpOptions = true)
public class ExecuteThingAction implements Runnable {
    @CommandLine.Mixin
    private Options options;

    @CommandLine.Parameters(index = "0", arity = "1", paramLabel = "<thingUID>", description = "thingUID (required)")
    private String thingUID;

    @CommandLine.Parameters(
            index = "1",
            arity = "1",
            paramLabel = "<actionUid>",
            description = "action type UID (including scope, separated by '.') (required)")
    private String actionUid;

    @CommandLine.Option(
            names = "--accept-language",
            paramLabel = "<acceptLanguage>",
            description = "language (optional)",
            arity = "1")
    private String acceptLanguage;

    @CommandLine.Option(
            names = "--request-body",
            paramLabel = "<requestBody>",
            description =
                    "action inputs as map (parameter name as key / argument as value) (optional) Supply a JSON value.",
            arity = "1")
    private String requestBody;

    private final Console console;
    private final ApiClientBuilder apiClientBuilder;

    /** Creates the command with injected output and REST client services. */
    @Inject
    ExecuteThingAction(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link Action#executeThingAction}. */
    @Override
    public void run() {
        log.debug("Command: Action.executeThingAction");
        Map<String, Object> requestBodyValue =
                JsonArguments.parse(requestBody, new TypeToken<Map<String, Object>>() {}.getType(), "requestBody");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Action(apiClient);
        var result = endpoint.executeThingAction(thingUID, actionUid, acceptLanguage, requestBodyValue);
        console.writeJson(result, options.isPrettyPrint());
    }
}
