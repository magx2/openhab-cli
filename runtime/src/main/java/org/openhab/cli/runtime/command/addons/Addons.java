package org.openhab.cli.runtime.command.addons;

import javax.inject.Inject;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Get all add-ons. */
@Slf4j
@CommandLine.Command(name = "addons", description = "Get all add-ons.", mixinStandardHelpOptions = true)
@RequiredArgsConstructor(access = AccessLevel.PACKAGE, onConstructor_ = @Inject)
public class Addons implements Runnable {
    @CommandLine.Mixin
    private Options options;

    @CommandLine.Option(
            names = "--accept-language",
            paramLabel = "<acceptLanguage>",
            description = "language (optional)",
            arity = "1")
    private String acceptLanguage;

    @CommandLine.Option(
            names = "--service-id",
            paramLabel = "<serviceId>",
            description = "service ID (optional)",
            arity = "1")
    private String serviceId;

    private final Console console;
    private final ApiClientBuilder apiClientBuilder;

    /** Executes {@link org.openhab.cli.engine.endpoint.Addons#addons}. */
    @Override
    public void run() {
        log.debug("Command: Addons.addons");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new org.openhab.cli.engine.endpoint.Addons(apiClient);
        var result = endpoint.addons(acceptLanguage, serviceId);
        console.writeJson(result, options.isPrettyPrint());
    }
}
