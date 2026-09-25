package org.openhab.cli.runtime.command.iconsets;

import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.engine.endpoint.Iconsets;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Gets all icon sets. */
@Slf4j
@CommandLine.Command(name = "iconSets", description = "Gets all icon sets.", mixinStandardHelpOptions = true)
public class IconSets implements Runnable {
    @CommandLine.Mixin
    private Options options;

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
    IconSets(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link Iconsets#iconSets}. */
    @Override
    public void run() {
        log.debug("Command: Iconsets.iconSets");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Iconsets(apiClient);
        var result = endpoint.iconSets(acceptLanguage);
        console.writeJson(result, options.isPrettyPrint());
    }
}
