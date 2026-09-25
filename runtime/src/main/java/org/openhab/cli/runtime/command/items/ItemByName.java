package org.openhab.cli.runtime.command.items;

import java.util.concurrent.Callable;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.engine.endpoint.Items;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Gets a single item. */
@Slf4j
@CommandLine.Command(name = "itemByName", description = "Gets a single item.", mixinStandardHelpOptions = true)
public class ItemByName implements Callable<Integer> {
    @CommandLine.Mixin
    private Options options;

    @CommandLine.Parameters(index = "0", arity = "1", paramLabel = "<itemName>", description = "item name (required)")
    private String itemName;

    @CommandLine.Option(
            names = "--accept-language",
            paramLabel = "<acceptLanguage>",
            description = "language (optional)",
            arity = "1")
    private String acceptLanguage;

    @CommandLine.Parameters(
            index = "1",
            arity = "1",
            paramLabel = "<metadata>",
            description =
                    "metadata selector - a comma separated list or a regular expression (returns all if no value given) (optional, default to .*)")
    private String metadata;

    @CommandLine.Parameters(
            index = "2",
            arity = "1",
            paramLabel = "<recursive>",
            description = "get member items if the item is a group item (optional, default to true)")
    private Boolean recursive;

    @CommandLine.Parameters(
            index = "3",
            arity = "1",
            paramLabel = "<parents>",
            description = "get parent group items recursively (optional, default to false)")
    private Boolean parents;

    private final Console console;
    private final ApiClientBuilder apiClientBuilder;

    /** Creates the command with injected output and REST client services. */
    @Inject
    ItemByName(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link Items#itemByName} and returns zero on success. */
    @Override
    public Integer call() {
        log.debug("Command: Items.itemByName");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Items(apiClient);
        var result = endpoint.itemByName(itemName, acceptLanguage, metadata, recursive, parents);
        console.writeJson(result, options.isPrettyPrint());
        return 0;
    }
}
