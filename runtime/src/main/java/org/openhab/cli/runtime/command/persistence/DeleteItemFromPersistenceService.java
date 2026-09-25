package org.openhab.cli.runtime.command.persistence;

import java.util.concurrent.Callable;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.engine.endpoint.Persistence;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Deletes Item persistence data from a specific persistence service in a given time range. */
@Slf4j
@CommandLine.Command(
        name = "deleteItemFromPersistenceService",
        description = "Deletes Item persistence data from a specific persistence service in a given time range.",
        mixinStandardHelpOptions = true)
public class DeleteItemFromPersistenceService implements Callable<Integer> {
    @CommandLine.Mixin
    private Options options;

    @CommandLine.Parameters(
            index = "0",
            arity = "1",
            paramLabel = "<serviceId>",
            description = "Id of the persistence service. (required)")
    private String serviceId;

    @CommandLine.Parameters(
            index = "1",
            arity = "1",
            paramLabel = "<itemName>",
            description = "The Item name. (required)")
    private String itemName;

    @CommandLine.Parameters(
            index = "2",
            arity = "1",
            paramLabel = "<starttime>",
            description = "Start of the time range to be deleted. [yyyy-MM-dd'T'HH:mm:ss.SSSZ] (required)")
    private String starttime;

    @CommandLine.Parameters(
            index = "3",
            arity = "1",
            paramLabel = "<endtime>",
            description = "End of the time range to be deleted. [yyyy-MM-dd'T'HH:mm:ss.SSSZ] (required)")
    private String endtime;

    private final Console console;
    private final ApiClientBuilder apiClientBuilder;

    /** Creates the command with injected output and REST client services. */
    @Inject
    DeleteItemFromPersistenceService(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link Persistence#deleteItemFromPersistenceService} and returns zero on success. */
    @Override
    public Integer call() {
        log.debug("Command: Persistence.deleteItemFromPersistenceService");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Persistence(apiClient);
        var result = endpoint.deleteItemFromPersistenceService(serviceId, itemName, starttime, endtime);
        console.writeJson(result, options.isPrettyPrint());
        return 0;
    }
}
