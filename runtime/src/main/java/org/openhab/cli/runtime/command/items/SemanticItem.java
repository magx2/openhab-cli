package org.openhab.cli.runtime.command.items;

import java.util.concurrent.Callable;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.engine.endpoint.Items;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Gets the item which defines the requested semantics of an item. */
@Slf4j
@CommandLine.Command(
        name = "semanticItem",
        description = "Gets the item which defines the requested semantics of an item.",
        mixinStandardHelpOptions = true)
public class SemanticItem implements Callable<Integer> {
    @CommandLine.Mixin
    private Options options;

    @CommandLine.Parameters(index = "0", arity = "1", paramLabel = "<itemName>", description = "item name (required)")
    private String itemName;

    @CommandLine.Parameters(
            index = "1",
            arity = "1",
            paramLabel = "<semanticClass>",
            description = "semantic class (required)")
    private String semanticClass;

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
    SemanticItem(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link Items#semanticItem} and returns zero on success. */
    @Override
    public Integer call() {
        log.debug("Command: Items.semanticItem");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Items(apiClient);
        var result = endpoint.semanticItem(itemName, semanticClass, acceptLanguage);
        console.writeJson(result, options.isPrettyPrint());
        return 0;
    }
}
