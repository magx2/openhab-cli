package org.openhab.cli.runtime.items;

import java.util.concurrent.Callable;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.engine.endpoint.Items;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Updates the state of an item. */
@Slf4j
@CommandLine.Command(
        name = "updateItemState",
        description = "Updates the state of an item.",
        mixinStandardHelpOptions = true)
public class UpdateItemState implements Callable<Integer> {
    @CommandLine.Mixin
    private Options options;

    @CommandLine.Parameters(index = "0", arity = "1", paramLabel = "<itemName>", description = "item name (required)")
    private String itemName;

    @CommandLine.Parameters(
            index = "1",
            arity = "1",
            paramLabel = "<body>",
            description = "Valid item state (e.g., ON, OFF) either as plain text or JSON (required)")
    private String body;

    @CommandLine.Option(
            names = "--accept-language",
            paramLabel = "<acceptLanguage>",
            description = "language (optional)",
            arity = "1")
    private String acceptLanguage;

    @CommandLine.Option(
            names = "--x-open-habsource",
            paramLabel = "<xOpenHABSource>",
            description =
                    "the source of the event; takes priority over the query parameter or JSON body if multiple are set (optional)",
            arity = "1")
    private String xOpenHABSource;

    @CommandLine.Option(
            names = "--source",
            paramLabel = "<source>",
            description = "the source of the event (optional)",
            arity = "1")
    private String source;

    private final Console console;
    private final ApiClientBuilder apiClientBuilder;

    /** Creates the command with injected output and REST client services. */
    @Inject
    UpdateItemState(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link Items#updateItemState} and returns zero on success. */
    @Override
    public Integer call() {
        log.debug("Command: Items.updateItemState");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Items(apiClient);
        endpoint.updateItemState(itemName, body, acceptLanguage, xOpenHABSource, source);
        return 0;
    }
}
