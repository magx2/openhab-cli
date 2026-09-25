package org.openhab.cli.runtime.items;

import java.util.concurrent.Callable;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.engine.endpoint.Items;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Sends a command to an item. */
@Slf4j
@CommandLine.Command(
        name = "sendItemCommand",
        description = "Sends a command to an item.",
        mixinStandardHelpOptions = true)
public class SendItemCommand implements Callable<Integer> {
    @CommandLine.Mixin
    private Options options;

    @CommandLine.Parameters(index = "0", arity = "1", paramLabel = "<itemName>", description = "item name (required)")
    private String itemName;

    @CommandLine.Parameters(
            index = "1",
            arity = "1",
            paramLabel = "<body>",
            description = "Valid item command (e.g., ON, OFF) either as plain text or JSON (required)")
    private String body;

    @CommandLine.Option(
            names = "--x-open-habsource",
            paramLabel = "<xOpenHABSource>",
            description =
                    "the source of the command; takes priority over the query parameter or JSON body if multiple are set (optional)",
            arity = "1")
    private String xOpenHABSource;

    @CommandLine.Option(
            names = "--source",
            paramLabel = "<source>",
            description = "the source of the command (optional)",
            arity = "1")
    private String source;

    private final Console console;
    private final ApiClientBuilder apiClientBuilder;

    /** Creates the command with injected output and REST client services. */
    @Inject
    SendItemCommand(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link Items#sendItemCommand} and returns zero on success. */
    @Override
    public Integer call() {
        log.debug("Command: Items.sendItemCommand");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Items(apiClient);
        endpoint.sendItemCommand(itemName, body, xOpenHABSource, source);
        return 0;
    }
}
