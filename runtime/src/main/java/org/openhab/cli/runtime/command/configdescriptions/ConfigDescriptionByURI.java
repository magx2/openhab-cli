package org.openhab.cli.runtime.command.configdescriptions;

import javax.inject.Inject;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.engine.endpoint.ConfigDescriptions;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Gets a config description by URI. */
@Slf4j
@CommandLine.Command(
        name = "configDescriptionByURI",
        description = "Gets a config description by URI.",
        mixinStandardHelpOptions = true)
@RequiredArgsConstructor(access = AccessLevel.PACKAGE, onConstructor_ = @Inject)
public class ConfigDescriptionByURI implements Runnable {
    @CommandLine.Mixin
    private Options options;

    @CommandLine.Parameters(index = "0", arity = "1", paramLabel = "<uri>", description = "uri (required)")
    private String uri;

    @CommandLine.Option(
            names = "--accept-language",
            paramLabel = "<acceptLanguage>",
            description = "language (optional)",
            arity = "1")
    private String acceptLanguage;

    private final Console console;
    private final ApiClientBuilder apiClientBuilder;

    /** Executes {@link ConfigDescriptions#configDescriptionByURI}. */
    @Override
    public void run() {
        log.debug("Command: ConfigDescriptions.configDescriptionByURI");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new ConfigDescriptions(apiClient);
        var result = endpoint.configDescriptionByURI(uri, acceptLanguage);
        console.writeJson(result, options.isPrettyPrint());
    }
}
