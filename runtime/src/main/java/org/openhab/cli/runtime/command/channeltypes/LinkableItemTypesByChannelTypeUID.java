package org.openhab.cli.runtime.command.channeltypes;

import java.util.concurrent.Callable;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.engine.endpoint.ChannelTypes;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Gets the item types the given trigger channel type UID can be linked to. */
@Slf4j
@CommandLine.Command(
        name = "linkableItemTypesByChannelTypeUID",
        description = "Gets the item types the given trigger channel type UID can be linked to.",
        mixinStandardHelpOptions = true)
public class LinkableItemTypesByChannelTypeUID implements Callable<Integer> {
    @CommandLine.Mixin
    private Options options;

    @CommandLine.Parameters(
            index = "0",
            arity = "1",
            paramLabel = "<channelTypeUID>",
            description = "channelTypeUID (required)")
    private String channelTypeUID;

    private final Console console;
    private final ApiClientBuilder apiClientBuilder;

    /** Creates the command with injected output and REST client services. */
    @Inject
    LinkableItemTypesByChannelTypeUID(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link ChannelTypes#linkableItemTypesByChannelTypeUID} and returns zero on success. */
    @Override
    public Integer call() {
        log.debug("Command: ChannelTypes.linkableItemTypesByChannelTypeUID");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new ChannelTypes(apiClient);
        var result = endpoint.linkableItemTypesByChannelTypeUID(channelTypeUID);
        console.writeJson(result, options.isPrettyPrint());
        return 0;
    }
}
