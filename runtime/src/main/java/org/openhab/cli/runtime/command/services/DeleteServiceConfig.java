package org.openhab.cli.runtime.command.services;

import java.util.concurrent.Callable;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.engine.endpoint.Services;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Deletes a service configuration for given service ID and returns the old configuration. */
@Slf4j
@CommandLine.Command(
        name = "deleteServiceConfig",
        description = "Deletes a service configuration for given service ID and returns the old configuration.",
        mixinStandardHelpOptions = true)
public class DeleteServiceConfig implements Callable<Integer> {
    @CommandLine.Mixin
    private Options options;

    @CommandLine.Parameters(index = "0", arity = "1", paramLabel = "<serviceId>", description = "service ID (required)")
    private String serviceId;

    private final Console console;
    private final ApiClientBuilder apiClientBuilder;

    /** Creates the command with injected output and REST client services. */
    @Inject
    DeleteServiceConfig(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link Services#deleteServiceConfig} and returns zero on success. */
    @Override
    public Integer call() {
        log.debug("Command: Services.deleteServiceConfig");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Services(apiClient);
        var result = endpoint.deleteServiceConfig(serviceId);
        console.writeJson(result, options.isPrettyPrint());
        return 0;
    }
}
