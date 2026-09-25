package org.openhab.cli.runtime.command.links;

import com.google.gson.reflect.TypeToken;
import java.util.concurrent.Callable;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.client.model.ItemChannelLink;
import org.openhab.cli.engine.endpoint.Links;
import org.openhab.cli.runtime.JsonArguments;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Links an item to a channel. */
@Slf4j
@CommandLine.Command(
        name = "linkItemToChannel",
        description = "Links an item to a channel.",
        mixinStandardHelpOptions = true)
public class LinkItemToChannel implements Callable<Integer> {
    @CommandLine.Mixin
    private Options options;

    @CommandLine.Parameters(index = "0", arity = "1", paramLabel = "<itemName>", description = "itemName (required)")
    private String itemName;

    @CommandLine.Parameters(
            index = "1",
            arity = "1",
            paramLabel = "<channelUID>",
            description = "channelUID (required)")
    private String channelUID;

    @CommandLine.Option(
            names = "--item-channel-link",
            paramLabel = "<itemChannelLink>",
            description = "link data (optional) Supply a JSON value.",
            arity = "1")
    private String itemChannelLink;

    private final Console console;
    private final ApiClientBuilder apiClientBuilder;

    /** Creates the command with injected output and REST client services. */
    @Inject
    LinkItemToChannel(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link Links#linkItemToChannel} and returns zero on success. */
    @Override
    public Integer call() {
        log.debug("Command: Links.linkItemToChannel");
        ItemChannelLink itemChannelLinkValue =
                JsonArguments.parse(itemChannelLink, new TypeToken<ItemChannelLink>() {}.getType(), "itemChannelLink");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Links(apiClient);
        endpoint.linkItemToChannel(itemName, channelUID, itemChannelLinkValue);
        return 0;
    }
}
