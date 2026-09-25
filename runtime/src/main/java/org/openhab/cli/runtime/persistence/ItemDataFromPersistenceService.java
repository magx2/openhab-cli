package org.openhab.cli.runtime.persistence;

import java.util.concurrent.Callable;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.engine.endpoint.Persistence;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Gets Item persistence data from the persistence service. */
@Slf4j
@CommandLine.Command(
        name = "itemDataFromPersistenceService",
        description = "Gets Item persistence data from the persistence service.",
        mixinStandardHelpOptions = true)
public class ItemDataFromPersistenceService implements Callable<Integer> {
    @CommandLine.Mixin
    private Options options;

    @CommandLine.Parameters(
            index = "0",
            arity = "1",
            paramLabel = "<itemName>",
            description = "The Item name (required)")
    private String itemName;

    @CommandLine.Option(
            names = "--accept-language",
            paramLabel = "<acceptLanguage>",
            description = "language (optional)",
            arity = "1")
    private String acceptLanguage;

    @CommandLine.Option(
            names = "--service-id",
            paramLabel = "<serviceId>",
            description = "Id of the persistence service. If not provided the default service will be used (optional)",
            arity = "1")
    private String serviceId;

    @CommandLine.Option(
            names = "--starttime",
            paramLabel = "<starttime>",
            description =
                    "Start time of the data to return. Will default to 1 day before endtime. [yyyy-MM-dd'T'HH:mm:ss.SSSZ] (optional)",
            arity = "1")
    private String starttime;

    @CommandLine.Option(
            names = "--endtime",
            paramLabel = "<endtime>",
            description =
                    "End time of the data to return. Will default to current time. [yyyy-MM-dd'T'HH:mm:ss.SSSZ] (optional)",
            arity = "1")
    private String endtime;

    @CommandLine.Option(
            names = "--page",
            paramLabel = "<page>",
            description = "Page number of data to return. This parameter will enable paging. (optional)",
            arity = "1")
    private Integer page;

    @CommandLine.Option(
            names = "--pagelength",
            paramLabel = "<pagelength>",
            description = "The length of each page. (optional)",
            arity = "1")
    private Integer pagelength;

    @CommandLine.Option(
            names = "--boundary",
            paramLabel = "<boundary>",
            description = "Gets one value before and after the requested period. (optional)",
            arity = "0..1",
            fallbackValue = "true")
    private Boolean boundary;

    @CommandLine.Option(
            names = "--item-state",
            paramLabel = "<itemState>",
            description =
                    "Adds the current Item state into the requested period (the Item state will be before or at the endtime) (optional)",
            arity = "0..1",
            fallbackValue = "true")
    private Boolean itemState;

    @CommandLine.Option(
            names = "--display-state",
            paramLabel = "<displayState>",
            description =
                    "If set to true, formatting from the state description is applied to the values. For QuantityType states, the value in the display unit as defined by the pattern, is returned. (optional)",
            arity = "0..1",
            fallbackValue = "true")
    private Boolean displayState;

    private final Console console;
    private final ApiClientBuilder apiClientBuilder;

    /** Creates the command with injected output and REST client services. */
    @Inject
    ItemDataFromPersistenceService(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link Persistence#itemDataFromPersistenceService} and returns zero on success. */
    @Override
    public Integer call() {
        log.debug("Command: Persistence.itemDataFromPersistenceService");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Persistence(apiClient);
        var result = endpoint.itemDataFromPersistenceService(
                itemName,
                acceptLanguage,
                serviceId,
                starttime,
                endtime,
                page,
                pagelength,
                boundary,
                itemState,
                displayState);
        console.writeJson(result, options.isPrettyPrint());
        return 0;
    }
}
