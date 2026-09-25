package org.openhab.cli.runtime.channeltypes;

import java.util.concurrent.Callable;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.engine.endpoint.ChannelTypes;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Gets channel type by UID. */
@Slf4j
@CommandLine.Command(
        name = "channelTypeByUID",
        description = "Gets channel type by UID.",
        mixinStandardHelpOptions = true)
public class ChannelTypeByUID implements Callable<Integer> {
    @CommandLine.Mixin
    private Options options;

    @CommandLine.Parameters(
            index = "0",
            arity = "1",
            paramLabel = "<channelTypeUID>",
            description = "channelTypeUID (required)")
    private String channelTypeUID;

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
    ChannelTypeByUID(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link ChannelTypes#channelTypeByUID} and returns zero on success. */
    @Override
    public Integer call() {
        log.debug("Command: ChannelTypes.channelTypeByUID");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new ChannelTypes(apiClient);
        var result = endpoint.channelTypeByUID(channelTypeUID, acceptLanguage);
        console.writeJson(result, options.isPrettyPrint());
        return 0;
    }
}
