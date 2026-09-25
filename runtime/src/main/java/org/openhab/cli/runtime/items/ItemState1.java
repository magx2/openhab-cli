package org.openhab.cli.runtime.items;

import java.util.concurrent.Callable;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.engine.endpoint.Items;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Gets the state of an item. */
@Slf4j
@CommandLine.Command(name = "itemState1", description = "Gets the state of an item.", mixinStandardHelpOptions = true)
public class ItemState1 implements Callable<Integer> {
    @CommandLine.Mixin
    private Options options;

    @CommandLine.Parameters(index = "0", arity = "1", paramLabel = "<itemName>", description = "item name (required)")
    private String itemName;

    private final Console console;
    private final ApiClientBuilder apiClientBuilder;

    /** Creates the command with injected output and REST client services. */
    @Inject
    ItemState1(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link Items#itemState1} and returns zero on success. */
    @Override
    public Integer call() {
        log.debug("Command: Items.itemState1");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Items(apiClient);
        var result = endpoint.itemState1(itemName);
        console.writeJson(result, options.isPrettyPrint());
        return 0;
    }
}
