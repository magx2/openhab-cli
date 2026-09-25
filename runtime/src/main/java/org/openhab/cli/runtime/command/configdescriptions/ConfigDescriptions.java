package org.openhab.cli.runtime.command.configdescriptions;

import java.util.concurrent.Callable;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Gets all available config descriptions. */
@Slf4j
@CommandLine.Command(
        name = "configDescriptions",
        description = "Gets all available config descriptions.",
        mixinStandardHelpOptions = true)
public class ConfigDescriptions implements Callable<Integer> {
    @CommandLine.Mixin
    private Options options;

    @CommandLine.Option(
            names = "--accept-language",
            paramLabel = "<acceptLanguage>",
            description = "language (optional)",
            arity = "1")
    private String acceptLanguage;

    @CommandLine.Option(
            names = "--scheme",
            paramLabel = "<scheme>",
            description = "scheme filter (optional)",
            arity = "1")
    private String scheme;

    private final Console console;
    private final ApiClientBuilder apiClientBuilder;

    /** Creates the command with injected output and REST client services. */
    @Inject
    ConfigDescriptions(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link org.openhab.cli.engine.endpoint.ConfigDescriptions#configDescriptions} and returns zero on success. */
    @Override
    public Integer call() {
        log.debug("Command: ConfigDescriptions.configDescriptions");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new org.openhab.cli.engine.endpoint.ConfigDescriptions(apiClient);
        var result = endpoint.configDescriptions(acceptLanguage, scheme);
        console.writeJson(result, options.isPrettyPrint());
        return 0;
    }
}
