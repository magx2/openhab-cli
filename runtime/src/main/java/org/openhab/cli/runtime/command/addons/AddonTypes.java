package org.openhab.cli.runtime.command.addons;

import javax.inject.Inject;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.engine.endpoint.Addons;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Get all add-on types. */
@Slf4j
@CommandLine.Command(name = "addonTypes", description = "Get all add-on types.", mixinStandardHelpOptions = true)
@RequiredArgsConstructor(access = AccessLevel.PACKAGE, onConstructor_ = @Inject)
public class AddonTypes implements Runnable {
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

    /** Executes {@link Addons#addonTypes}. */
    @Override
    public void run() {
        log.debug("Command: Addons.addonTypes");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Addons(apiClient);
        var result = endpoint.addonTypes(acceptLanguage);
        console.writeJson(result, options.isPrettyPrint());
    }
}
