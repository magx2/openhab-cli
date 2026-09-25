package org.openhab.cli.runtime.items;

import java.util.concurrent.Callable;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.engine.endpoint.Items;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Removes metadata in a specific namespace from an item. */
@Slf4j
@CommandLine.Command(
        name = "removeMetadataFromItem",
        description = "Removes metadata in a specific namespace from an item.",
        mixinStandardHelpOptions = true)
public class RemoveMetadataFromItem implements Callable<Integer> {
    @CommandLine.Mixin
    private Options options;

    @CommandLine.Parameters(index = "0", arity = "1", paramLabel = "<itemName>", description = "item name (required)")
    private String itemName;

    @CommandLine.Parameters(index = "1", arity = "1", paramLabel = "<namespace>", description = "namespace (required)")
    private String namespace;

    private final Console console;
    private final ApiClientBuilder apiClientBuilder;

    /** Creates the command with injected output and REST client services. */
    @Inject
    RemoveMetadataFromItem(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link Items#removeMetadataFromItem} and returns zero on success. */
    @Override
    public Integer call() {
        log.debug("Command: Items.removeMetadataFromItem");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Items(apiClient);
        endpoint.removeMetadataFromItem(itemName, namespace);
        return 0;
    }
}
