package org.openhab.cli.runtime.command.items;

import com.google.gson.reflect.TypeToken;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.client.model.Metadata;
import org.openhab.cli.engine.endpoint.Items;
import org.openhab.cli.runtime.JsonArguments;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Adds metadata to an item. */
@Slf4j
@CommandLine.Command(
        name = "addMetadataToItem",
        description = "Adds metadata to an item.",
        mixinStandardHelpOptions = true)
public class AddMetadataToItem implements Runnable {
    @CommandLine.Mixin
    private Options options;

    @CommandLine.Parameters(index = "0", arity = "1", paramLabel = "<itemName>", description = "item name (required)")
    private String itemName;

    @CommandLine.Parameters(index = "1", arity = "1", paramLabel = "<namespace>", description = "namespace (required)")
    private String namespace;

    @CommandLine.Parameters(
            index = "2",
            arity = "1",
            paramLabel = "<metadata>",
            description = "metadata (required) Supply a JSON value.")
    private String metadata;

    private final Console console;
    private final ApiClientBuilder apiClientBuilder;

    /** Creates the command with injected output and REST client services. */
    @Inject
    AddMetadataToItem(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link Items#addMetadataToItem}. */
    @Override
    public void run() {
        log.debug("Command: Items.addMetadataToItem");
        Metadata metadataValue = JsonArguments.parse(metadata, new TypeToken<Metadata>() {}.getType(), "metadata");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Items(apiClient);
        endpoint.addMetadataToItem(itemName, namespace, metadataValue);
    }
}
