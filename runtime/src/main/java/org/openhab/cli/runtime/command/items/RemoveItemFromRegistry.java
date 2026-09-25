package org.openhab.cli.runtime.command.items;

import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.engine.endpoint.Items;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Removes an item from the registry. */
@Slf4j
@CommandLine.Command(
        name = "removeItemFromRegistry",
        description = "Removes an item from the registry.",
        mixinStandardHelpOptions = true)
public class RemoveItemFromRegistry implements Runnable {
    @CommandLine.Mixin
    private Options options;

    @CommandLine.Parameters(index = "0", arity = "1", paramLabel = "<itemName>", description = "item name (required)")
    private String itemName;

    private final Console console;
    private final ApiClientBuilder apiClientBuilder;

    /** Creates the command with injected output and REST client services. */
    @Inject
    RemoveItemFromRegistry(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link Items#removeItemFromRegistry}. */
    @Override
    public void run() {
        log.debug("Command: Items.removeItemFromRegistry");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Items(apiClient);
        endpoint.removeItemFromRegistry(itemName);
    }
}
