package org.openhab.cli.runtime.services;

import com.google.gson.reflect.TypeToken;
import java.util.concurrent.Callable;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.engine.endpoint.Services;
import org.openhab.cli.runtime.JsonArguments;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Updates a service configuration for given service ID and returns the old configuration. */
@Slf4j
@CommandLine.Command(
        name = "updateServiceConfig",
        description = "Updates a service configuration for given service ID and returns the old configuration.",
        mixinStandardHelpOptions = true)
public class UpdateServiceConfig implements Callable<Integer> {
    @CommandLine.Mixin
    private Options options;

    @CommandLine.Parameters(index = "0", arity = "1", paramLabel = "<serviceId>", description = "service ID (required)")
    private String serviceId;

    @CommandLine.Option(
            names = "--accept-language",
            paramLabel = "<acceptLanguage>",
            description = "language (optional)",
            arity = "1")
    private String acceptLanguage;

    @CommandLine.Option(
            names = "--body",
            paramLabel = "<body>",
            description = "(optional) Supply a JSON value.",
            arity = "1")
    private String body;

    private final Console console;
    private final ApiClientBuilder apiClientBuilder;

    /** Creates the command with injected output and REST client services. */
    @Inject
    UpdateServiceConfig(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link Services#updateServiceConfig} and returns zero on success. */
    @Override
    public Integer call() {
        log.debug("Command: Services.updateServiceConfig");
        Object bodyValue = JsonArguments.parse(body, new TypeToken<Object>() {}.getType(), "body");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Services(apiClient);
        var result = endpoint.updateServiceConfig(serviceId, acceptLanguage, bodyValue);
        console.writeJson(result, options.isPrettyPrint());
        return 0;
    }
}
