package org.openhab.cli.runtime.command.links;

import javax.inject.Inject;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.engine.endpoint.Links;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Unlinks an item from a channel. */
@Slf4j
@CommandLine.Command(
        name = "unlinkItemFromChannel",
        description = "Unlinks an item from a channel.",
        mixinStandardHelpOptions = true)
@RequiredArgsConstructor(access = AccessLevel.PACKAGE, onConstructor_ = @Inject)
public class UnlinkItemFromChannel implements Runnable {
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

    private final Console console;
    private final ApiClientBuilder apiClientBuilder;

    /** Executes {@link Links#unlinkItemFromChannel}. */
    @Override
    public void run() {
        log.debug("Command: Links.unlinkItemFromChannel");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Links(apiClient);
        endpoint.unlinkItemFromChannel(itemName, channelUID);
    }
}
