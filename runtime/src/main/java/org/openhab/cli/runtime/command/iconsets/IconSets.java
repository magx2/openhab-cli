package org.openhab.cli.runtime.command.iconsets;

import javax.inject.Inject;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.engine.endpoint.Iconsets;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Gets all icon sets. */
@Slf4j
@CommandLine.Command(name = "iconSets", description = "Gets all icon sets.", mixinStandardHelpOptions = true)
@RequiredArgsConstructor(access = AccessLevel.PACKAGE, onConstructor_ = @Inject)
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
