package org.openhab.cli.runtime.command.items;

import com.google.gson.reflect.TypeToken;
import java.util.concurrent.Callable;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.client.model.GroupItem;
import org.openhab.cli.engine.endpoint.Items;
import org.openhab.cli.runtime.JsonArguments;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Adds a new item to the registry or updates the existing item. */
@Slf4j
@CommandLine.Command(
        name = "addOrUpdateItemInRegistry",
        description = "Adds a new item to the registry or updates the existing item.",
        mixinStandardHelpOptions = true)
public class AddOrUpdateItemInRegistry implements Callable<Integer> {
    @CommandLine.Mixin
    private Options options;

    @CommandLine.Parameters(index = "0", arity = "1", paramLabel = "<itemName>", description = "item name (required)")
    private String itemName;

    @CommandLine.Parameters(
            index = "1",
            arity = "1",
            paramLabel = "<groupItem>",
            description = "item data (required) Supply a JSON value.")
    private String groupItem;

    @CommandLine.Option(
            names = "--accept-language",
            paramLabel = "<acceptLanguage>",
            description = "language (optional)",
            arity = "1")
    private String acceptLanguage;

    private final Console console;
    private final ApiClientBuilder apiClientBuilder;

    /** Creates the command with injected output and REST client services. */
    @Inject
    AddOrUpdateItemInRegistry(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link Items#addOrUpdateItemInRegistry} and returns zero on success. */
    @Override
    public Integer call() {
        log.debug("Command: Items.addOrUpdateItemInRegistry");
        GroupItem groupItemValue = JsonArguments.parse(groupItem, new TypeToken<GroupItem>() {}.getType(), "groupItem");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Items(apiClient);
        var result = endpoint.addOrUpdateItemInRegistry(itemName, groupItemValue, acceptLanguage);
        console.writeJson(result, options.isPrettyPrint());
        return 0;
    }
}
