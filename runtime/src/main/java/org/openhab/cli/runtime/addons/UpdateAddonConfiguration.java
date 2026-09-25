package org.openhab.cli.runtime.addons;

import com.google.gson.reflect.TypeToken;
import java.util.Map;
import java.util.concurrent.Callable;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.engine.endpoint.Addons;
import org.openhab.cli.runtime.JsonArguments;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Updates an add-on configuration for given ID and returns the old configuration. */
@Slf4j
@CommandLine.Command(
        name = "updateAddonConfiguration",
        description = "Updates an add-on configuration for given ID and returns the old configuration.",
        mixinStandardHelpOptions = true)
public class UpdateAddonConfiguration implements Callable<Integer> {
    @CommandLine.Mixin
    private Options options;

    @CommandLine.Parameters(index = "0", arity = "1", paramLabel = "<addonId>", description = "Add-on id (required)")
    private String addonId;

    @CommandLine.Option(
            names = "--service-id",
            paramLabel = "<serviceId>",
            description = "service ID (optional)",
            arity = "1")
    private String serviceId;

    @CommandLine.Option(
            names = "--request-body",
            paramLabel = "<requestBody>",
            description = "(optional) Supply a JSON value.",
            arity = "1")
    private String requestBody;

    private final Console console;
    private final ApiClientBuilder apiClientBuilder;

    /** Creates the command with injected output and REST client services. */
    @Inject
    UpdateAddonConfiguration(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link Addons#updateAddonConfiguration} and returns zero on success. */
    @Override
    public Integer call() {
        log.debug("Command: Addons.updateAddonConfiguration");
        Map<String, Object> requestBodyValue =
                JsonArguments.parse(requestBody, new TypeToken<Map<String, Object>>() {}.getType(), "requestBody");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Addons(apiClient);
        var result = endpoint.updateAddonConfiguration(addonId, serviceId, requestBodyValue);
        console.writeJson(result, options.isPrettyPrint());
        return 0;
    }
}
