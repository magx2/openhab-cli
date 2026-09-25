package org.openhab.cli.runtime.command.things;

import com.google.gson.reflect.TypeToken;
import java.util.Map;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.engine.endpoint.Things;
import org.openhab.cli.runtime.JsonArguments;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Updates thing's configuration. */
@Slf4j
@CommandLine.Command(
        name = "updateThingConfig",
        description = "Updates thing's configuration.",
        mixinStandardHelpOptions = true)
public class UpdateThingConfig implements Runnable {
    @CommandLine.Mixin
    private Options options;

    @CommandLine.Parameters(index = "0", arity = "1", paramLabel = "<thingUID>", description = "thing (required)")
    private String thingUID;

    @CommandLine.Option(
            names = "--accept-language",
            paramLabel = "<acceptLanguage>",
            description = "language (optional)",
            arity = "1")
    private String acceptLanguage;

    @CommandLine.Option(
            names = "--request-body",
            paramLabel = "<requestBody>",
            description = "configuration parameters (optional) Supply a JSON value.",
            arity = "1")
    private String requestBody;

    private final Console console;
    private final ApiClientBuilder apiClientBuilder;

    /** Creates the command with injected output and REST client services. */
    @Inject
    UpdateThingConfig(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link Things#updateThingConfig}. */
    @Override
    public void run() {
        log.debug("Command: Things.updateThingConfig");
        Map<String, Object> requestBodyValue =
                JsonArguments.parse(requestBody, new TypeToken<Map<String, Object>>() {}.getType(), "requestBody");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Things(apiClient);
        var result = endpoint.updateThingConfig(thingUID, acceptLanguage, requestBodyValue);
        console.writeJson(result, options.isPrettyPrint());
    }
}
