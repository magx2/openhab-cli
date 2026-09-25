package org.openhab.cli.runtime.command.persistence;

import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.engine.endpoint.Persistence;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Gets a persistence service configuration. */
@Slf4j
@CommandLine.Command(
        name = "persistenceServiceConfiguration",
        description = "Gets a persistence service configuration.",
        mixinStandardHelpOptions = true)
public class PersistenceServiceConfiguration implements Runnable {
    @CommandLine.Mixin
    private Options options;

    @CommandLine.Parameters(
            index = "0",
            arity = "1",
            paramLabel = "<serviceId>",
            description = "Id of the persistence service. (required)")
    private String serviceId;

    private final Console console;
    private final ApiClientBuilder apiClientBuilder;

    /** Creates the command with injected output and REST client services. */
    @Inject
    PersistenceServiceConfiguration(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link Persistence#persistenceServiceConfiguration}. */
    @Override
    public void run() {
        log.debug("Command: Persistence.persistenceServiceConfiguration");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Persistence(apiClient);
        var result = endpoint.persistenceServiceConfiguration(serviceId);
        console.writeJson(result, options.isPrettyPrint());
    }
}
