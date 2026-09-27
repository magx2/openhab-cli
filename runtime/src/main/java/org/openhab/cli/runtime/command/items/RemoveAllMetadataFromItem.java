package org.openhab.cli.runtime.command.items;

import javax.inject.Inject;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor(access = AccessLevel.PACKAGE, onConstructor_ = @Inject)
public class RemoveAllMetadataFromItem implements Runnable {
    @CommandLine.Mixin
    private Options options;

    @CommandLine.Parameters(index = "0", arity = "1", paramLabel = "<itemName>", description = "item name (required)")
    private String itemName;

    private final Console console;
    private final ApiClientBuilder apiClientBuilder;

    /** Executes {@link Items#removeAllMetadataFromItem}. */
    @Override
    public void run() {
        log.debug("Command: Items.removeAllMetadataFromItem");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Items(apiClient);
        endpoint.removeAllMetadataFromItem(itemName);
    }
}
