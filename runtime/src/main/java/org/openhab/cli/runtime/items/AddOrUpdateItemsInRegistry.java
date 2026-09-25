package org.openhab.cli.runtime.items;

import com.google.gson.reflect.TypeToken;
import java.util.List;
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

/** Adds a list of items to the registry or updates the existing items. */
@Slf4j
@CommandLine.Command(
        name = "addOrUpdateItemsInRegistry",
        description = "Adds a list of items to the registry or updates the existing items.",
        mixinStandardHelpOptions = true)
public class AddOrUpdateItemsInRegistry implements Callable<Integer> {
    @CommandLine.Mixin
    private Options options;

    @CommandLine.Parameters(
            index = "0",
            arity = "1",
            paramLabel = "<groupItem>",
            description = "array of item data (required) Supply a JSON value.")
    private String groupItem;

    private final Console console;
    private final ApiClientBuilder apiClientBuilder;

    /** Creates the command with injected output and REST client services. */
    @Inject
    AddOrUpdateItemsInRegistry(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link Items#addOrUpdateItemsInRegistry} and returns zero on success. */
    @Override
    public Integer call() {
        log.debug("Command: Items.addOrUpdateItemsInRegistry");
        List<GroupItem> groupItemValue =
                JsonArguments.parse(groupItem, new TypeToken<List<GroupItem>>() {}.getType(), "groupItem");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Items(apiClient);
        var result = endpoint.addOrUpdateItemsInRegistry(groupItemValue);
        console.writeJson(result, options.isPrettyPrint());
        return 0;
    }
}
