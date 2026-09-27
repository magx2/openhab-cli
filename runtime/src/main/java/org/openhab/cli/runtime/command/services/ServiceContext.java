package org.openhab.cli.runtime.command.services;

import javax.inject.Inject;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.engine.endpoint.Services;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Get existing multiple context service configurations for the given factory PID. */
@Slf4j
@CommandLine.Command(
        name = "serviceContext",
        description = "Get existing multiple context service configurations for the given factory PID.",
        mixinStandardHelpOptions = true)
@RequiredArgsConstructor(access = AccessLevel.PACKAGE, onConstructor_ = @Inject)
public class ServiceContext implements Runnable {
    @CommandLine.Mixin
    private Options options;

    @CommandLine.Parameters(index = "0", arity = "1", paramLabel = "<serviceId>", description = "service ID (required)")
    private String serviceId;

    @CommandLine.Option(
            names = "--accept-language",
            paramLabel = "<acceptLanguage>",
            description = "language (optional)",
            arity = "1")
    private String acceptLanguage;

    private final Console console;
    private final ApiClientBuilder apiClientBuilder;

    /** Executes {@link Services#serviceContext}. */
    @Override
    public void run() {
        log.debug("Command: Services.serviceContext");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Services(apiClient);
        var result = endpoint.serviceContext(serviceId, acceptLanguage);
        console.writeJson(result, options.isPrettyPrint());
    }
}
