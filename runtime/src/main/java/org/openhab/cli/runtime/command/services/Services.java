package org.openhab.cli.runtime.command.services;

import javax.inject.Inject;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Get all configurable services. */
@Slf4j
@CommandLine.Command(name = "services", description = "Get all configurable services.", mixinStandardHelpOptions = true)
@RequiredArgsConstructor(access = AccessLevel.PACKAGE, onConstructor_ = @Inject)
public class Services implements Runnable {
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

    /** Executes {@link org.openhab.cli.engine.endpoint.Services#services}. */
    @Override
    public void run() {
        log.debug("Command: Services.services");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new org.openhab.cli.engine.endpoint.Services(apiClient);
        var result = endpoint.services(acceptLanguage);
        console.writeJson(result, options.isPrettyPrint());
    }
}
