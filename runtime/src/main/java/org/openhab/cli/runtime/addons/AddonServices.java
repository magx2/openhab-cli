package org.openhab.cli.runtime.addons;

import java.util.concurrent.Callable;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.engine.endpoint.Addons;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Get add-on services. */
@Slf4j
@CommandLine.Command(name = "addonServices", description = "Get add-on services.", mixinStandardHelpOptions = true)
public class AddonServices implements Callable<Integer> {
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

    /** Creates the command with injected output and REST client services. */
    @Inject
    AddonServices(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link Addons#addonServices} and returns zero on success. */
    @Override
    public Integer call() {
        log.debug("Command: Addons.addonServices");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Addons(apiClient);
        var result = endpoint.addonServices(acceptLanguage, serviceId);
        console.writeJson(result, options.isPrettyPrint());
        return 0;
    }
}
