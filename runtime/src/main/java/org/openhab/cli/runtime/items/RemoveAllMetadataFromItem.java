package org.openhab.cli.runtime.items;

import java.util.concurrent.Callable;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.engine.endpoint.Items;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Removes all managed metadata from an item. */
@Slf4j
@CommandLine.Command(
        name = "removeAllMetadataFromItem",
        description = "Removes all managed metadata from an item.",
        mixinStandardHelpOptions = true)
public class RemoveAllMetadataFromItem implements Callable<Integer> {
    @CommandLine.Mixin
    private Options options;

    @CommandLine.Parameters(index = "0", arity = "1", paramLabel = "<itemName>", description = "item name (required)")
    private String itemName;

    private final Console console;
    private final ApiClientBuilder apiClientBuilder;

    /** Creates the command with injected output and REST client services. */
    @Inject
    RemoveAllMetadataFromItem(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link Items#removeAllMetadataFromItem} and returns zero on success. */
    @Override
    public Integer call() {
        log.debug("Command: Items.removeAllMetadataFromItem");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Items(apiClient);
        endpoint.removeAllMetadataFromItem(itemName);
        return 0;
    }
}
