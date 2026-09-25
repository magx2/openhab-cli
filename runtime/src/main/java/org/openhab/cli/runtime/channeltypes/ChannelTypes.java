package org.openhab.cli.runtime.channeltypes;

import java.util.concurrent.Callable;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Gets all available channel types. */
@Slf4j
@CommandLine.Command(
        name = "channelTypes",
        description = "Gets all available channel types.",
        mixinStandardHelpOptions = true)
public class ChannelTypes implements Callable<Integer> {
    @CommandLine.Mixin
    private Options options;

    @CommandLine.Option(
            names = "--accept-language",
            paramLabel = "<acceptLanguage>",
            description = "language (optional)",
            arity = "1")
    private String acceptLanguage;

    @CommandLine.Option(
            names = "--prefixes",
            paramLabel = "<prefixes>",
            description =
                    "filter UIDs by prefix (multiple comma-separated prefixes allowed, for example: 'system,mqtt') (optional)",
            arity = "1")
    private String prefixes;

    private final Console console;
    private final ApiClientBuilder apiClientBuilder;

    /** Creates the command with injected output and REST client services. */
    @Inject
    ChannelTypes(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link org.openhab.cli.engine.endpoint.ChannelTypes#channelTypes} and returns zero on success. */
    @Override
    public Integer call() {
        log.debug("Command: ChannelTypes.channelTypes");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new org.openhab.cli.engine.endpoint.ChannelTypes(apiClient);
        var result = endpoint.channelTypes(acceptLanguage, prefixes);
        console.writeJson(result, options.isPrettyPrint());
        return 0;
    }
}
