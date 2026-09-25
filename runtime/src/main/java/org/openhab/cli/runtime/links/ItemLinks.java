package org.openhab.cli.runtime.links;

import java.util.concurrent.Callable;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.engine.endpoint.Links;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Gets all available links. */
@Slf4j
@CommandLine.Command(name = "itemLinks", description = "Gets all available links.", mixinStandardHelpOptions = true)
public class ItemLinks implements Callable<Integer> {
    @CommandLine.Mixin
    private Options options;

    @CommandLine.Option(
            names = "--channel-uid",
            paramLabel = "<channelUID>",
            description = "filter by channel UID (optional)",
            arity = "1")
    private String channelUID;

    @CommandLine.Option(
            names = "--item-name",
            paramLabel = "<itemName>",
            description = "filter by item name (optional)",
            arity = "1")
    private String itemName;

    private final Console console;
    private final ApiClientBuilder apiClientBuilder;

    /** Creates the command with injected output and REST client services. */
    @Inject
    ItemLinks(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link Links#itemLinks} and returns zero on success. */
    @Override
    public Integer call() {
        log.debug("Command: Links.itemLinks");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Links(apiClient);
        var result = endpoint.itemLinks(channelUID, itemName);
        console.writeJson(result, options.isPrettyPrint());
        return 0;
    }
}
