package org.openhab.cli.runtime.links;

import java.util.concurrent.Callable;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.engine.endpoint.Links;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Retrieves an individual link. */
@Slf4j
@CommandLine.Command(name = "itemLink", description = "Retrieves an individual link.", mixinStandardHelpOptions = true)
public class ItemLink implements Callable<Integer> {
    @CommandLine.Mixin
    private Options options;

    @CommandLine.Parameters(index = "0", arity = "1", paramLabel = "<itemName>", description = "item name (required)")
    private String itemName;

    @CommandLine.Parameters(
            index = "1",
            arity = "1",
            paramLabel = "<channelUID>",
            description = "channel UID (required)")
    private String channelUID;

    private final Console console;
    private final ApiClientBuilder apiClientBuilder;

    /** Creates the command with injected output and REST client services. */
    @Inject
    ItemLink(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link Links#itemLink} and returns zero on success. */
    @Override
    public Integer call() {
        log.debug("Command: Links.itemLink");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Links(apiClient);
        var result = endpoint.itemLink(itemName, channelUID);
        console.writeJson(result, options.isPrettyPrint());
        return 0;
    }
}
