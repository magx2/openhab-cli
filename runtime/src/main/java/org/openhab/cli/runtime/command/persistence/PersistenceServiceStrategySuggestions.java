package org.openhab.cli.runtime.command.persistence;

import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.engine.endpoint.Persistence;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Gets a persistence service suggested strategies. */
@Slf4j
@CommandLine.Command(
        name = "persistenceServiceStrategySuggestions",
        description = "Gets a persistence service suggested strategies.",
        mixinStandardHelpOptions = true)
public class PersistenceServiceStrategySuggestions implements Runnable {
    @CommandLine.Mixin
    private Options options;

    @CommandLine.Option(
            names = "--service-id",
            paramLabel = "<serviceId>",
            description = "Id of the persistence service. (optional)",
            arity = "1")
    private String serviceId;

    private final Console console;
    private final ApiClientBuilder apiClientBuilder;

    /** Creates the command with injected output and REST client services. */
    @Inject
    PersistenceServiceStrategySuggestions(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link Persistence#persistenceServiceStrategySuggestions}. */
    @Override
    public void run() {
        log.debug("Command: Persistence.persistenceServiceStrategySuggestions");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Persistence(apiClient);
        var result = endpoint.persistenceServiceStrategySuggestions(serviceId);
        console.writeJson(result, options.isPrettyPrint());
    }
}
