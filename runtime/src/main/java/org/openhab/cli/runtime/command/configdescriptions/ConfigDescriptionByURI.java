package org.openhab.cli.runtime.command.configdescriptions;

import java.util.concurrent.Callable;
import javax.inject.Inject;
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
public class ConfigDescriptionByURI implements Callable<Integer> {
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

    /** Creates the command with injected output and REST client services. */
    @Inject
    ConfigDescriptionByURI(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link ConfigDescriptions#configDescriptionByURI} and returns zero on success. */
    @Override
    public Integer call() {
        log.debug("Command: ConfigDescriptions.configDescriptionByURI");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new ConfigDescriptions(apiClient);
        var result = endpoint.configDescriptionByURI(uri, acceptLanguage);
        console.writeJson(result, options.isPrettyPrint());
        return 0;
    }
}
