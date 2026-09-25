package org.openhab.cli.runtime.profiletypes;

import java.util.concurrent.Callable;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Gets all available profile types. */
@Slf4j
@CommandLine.Command(
        name = "profileTypes",
        description = "Gets all available profile types.",
        mixinStandardHelpOptions = true)
public class ProfileTypes implements Callable<Integer> {
    @CommandLine.Mixin
    private Options options;

    @CommandLine.Option(
            names = "--accept-language",
            paramLabel = "<acceptLanguage>",
            description = "language (optional)",
            arity = "1")
    private String acceptLanguage;

    @CommandLine.Option(
            names = "--channel-type-uid",
            paramLabel = "<channelTypeUID>",
            description = "channel type filter (optional)",
            arity = "1")
    private String channelTypeUID;

    @CommandLine.Option(
            names = "--item-type",
            paramLabel = "<itemType>",
            description = "item type filter (optional)",
            arity = "1")
    private String itemType;

    private final Console console;
    private final ApiClientBuilder apiClientBuilder;

    /** Creates the command with injected output and REST client services. */
    @Inject
    ProfileTypes(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link org.openhab.cli.engine.endpoint.ProfileTypes#profileTypes} and returns zero on success. */
    @Override
    public Integer call() {
        log.debug("Command: ProfileTypes.profileTypes");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new org.openhab.cli.engine.endpoint.ProfileTypes(apiClient);
        var result = endpoint.profileTypes(acceptLanguage, channelTypeUID, itemType);
        console.writeJson(result, options.isPrettyPrint());
        return 0;
    }
}
