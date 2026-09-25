package org.openhab.cli.runtime.persistence;

import java.util.concurrent.Callable;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.engine.endpoint.Persistence;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Gets a list of stored Items available via a specific persistence service with their stored name. */
@Slf4j
@CommandLine.Command(
        name = "itemsForPersistenceService",
        description =
                "Gets a list of stored Items available via a specific persistence service with their stored name.",
        mixinStandardHelpOptions = true)
public class ItemsForPersistenceService implements Callable<Integer> {
    @CommandLine.Mixin
    private Options options;

    @CommandLine.Option(
            names = "--service-id",
            paramLabel = "<serviceId>",
            description = "Id of the persistence service. If not provided the default service will be used (optional)",
            arity = "1")
    private String serviceId;

    @CommandLine.Option(
            names = "--item-name",
            paramLabel = "<itemName>",
            description = "An Item name, if provided response will only contain information for this Item (optional)",
            arity = "1")
    private String itemName;

    private final Console console;
    private final ApiClientBuilder apiClientBuilder;

    /** Creates the command with injected output and REST client services. */
    @Inject
    ItemsForPersistenceService(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link Persistence#itemsForPersistenceService} and returns zero on success. */
    @Override
    public Integer call() {
        log.debug("Command: Persistence.itemsForPersistenceService");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Persistence(apiClient);
        var result = endpoint.itemsForPersistenceService(serviceId, itemName);
        console.writeJson(result, options.isPrettyPrint());
        return 0;
    }
}
