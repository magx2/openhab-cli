package org.openhab.cli.runtime.command.links;

import java.util.concurrent.Callable;
import javax.inject.Inject;
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
public class UnlinkItemFromChannel implements Callable<Integer> {
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

    /** Creates the command with injected output and REST client services. */
    @Inject
    UnlinkItemFromChannel(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link Links#unlinkItemFromChannel} and returns zero on success. */
    @Override
    public Integer call() {
        log.debug("Command: Links.unlinkItemFromChannel");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Links(apiClient);
        endpoint.unlinkItemFromChannel(itemName, channelUID);
        return 0;
    }
}
