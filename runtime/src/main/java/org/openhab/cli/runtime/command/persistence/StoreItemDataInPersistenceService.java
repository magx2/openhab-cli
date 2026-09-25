package org.openhab.cli.runtime.command.persistence;

import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.engine.endpoint.Persistence;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Stores Item persistence data into the persistence service. */
@Slf4j
@CommandLine.Command(
        name = "storeItemDataInPersistenceService",
        description = "Stores Item persistence data into the persistence service.",
        mixinStandardHelpOptions = true)
public class StoreItemDataInPersistenceService implements Runnable {
    @CommandLine.Mixin
    private Options options;

    @CommandLine.Parameters(
            index = "0",
            arity = "1",
            paramLabel = "<itemName>",
            description = "The Item name. (required)")
    private String itemName;

    @CommandLine.Parameters(
            index = "1",
            arity = "1",
            paramLabel = "<time>",
            description =
                    "Time of the data to be stored. Will default to current time. [yyyy-MM-dd'T'HH:mm:ss.SSSZ] (required)")
    private String time;

    @CommandLine.Parameters(
            index = "2",
            arity = "1",
            paramLabel = "<state>",
            description = "The state to store. (required)")
    private String state;

    @CommandLine.Option(
            names = "--service-id",
            paramLabel = "<serviceId>",
            description = "Id of the persistence service. If not provided the default service will be used (optional)",
            arity = "1")
    private String serviceId;

    private final Console console;
    private final ApiClientBuilder apiClientBuilder;

    /** Creates the command with injected output and REST client services. */
    @Inject
    StoreItemDataInPersistenceService(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link Persistence#storeItemDataInPersistenceService}. */
    @Override
    public void run() {
        log.debug("Command: Persistence.storeItemDataInPersistenceService");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Persistence(apiClient);
        endpoint.storeItemDataInPersistenceService(itemName, time, state, serviceId);
    }
}
