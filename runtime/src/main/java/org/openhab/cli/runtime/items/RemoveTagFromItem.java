package org.openhab.cli.runtime.items;

import java.util.concurrent.Callable;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.engine.endpoint.Items;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Removes a tag from an item. */
@Slf4j
@CommandLine.Command(
        name = "removeTagFromItem",
        description = "Removes a tag from an item.",
        mixinStandardHelpOptions = true)
public class RemoveTagFromItem implements Callable<Integer> {
    @CommandLine.Mixin
    private Options options;

    @CommandLine.Parameters(index = "0", arity = "1", paramLabel = "<itemName>", description = "item name (required)")
    private String itemName;

    @CommandLine.Parameters(index = "1", arity = "1", paramLabel = "<tag>", description = "tag (required)")
    private String tag;

    private final Console console;
    private final ApiClientBuilder apiClientBuilder;

    /** Creates the command with injected output and REST client services. */
    @Inject
    RemoveTagFromItem(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link Items#removeTagFromItem} and returns zero on success. */
    @Override
    public Integer call() {
        log.debug("Command: Items.removeTagFromItem");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Items(apiClient);
        endpoint.removeTagFromItem(itemName, tag);
        return 0;
    }
}
