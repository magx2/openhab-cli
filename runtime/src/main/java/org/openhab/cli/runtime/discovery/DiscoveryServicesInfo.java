package org.openhab.cli.runtime.discovery;

import java.util.concurrent.Callable;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.engine.endpoint.Discovery;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Gets information about the discovery services for a binding. */
@Slf4j
@CommandLine.Command(
        name = "discoveryServicesInfo",
        description = "Gets information about the discovery services for a binding.",
        mixinStandardHelpOptions = true)
public class DiscoveryServicesInfo implements Callable<Integer> {
    @CommandLine.Mixin
    private Options options;

    @CommandLine.Parameters(index = "0", arity = "1", paramLabel = "<bindingId>", description = "binding Id (required)")
    private String bindingId;

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
    DiscoveryServicesInfo(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link Discovery#discoveryServicesInfo} and returns zero on success. */
    @Override
    public Integer call() {
        log.debug("Command: Discovery.discoveryServicesInfo");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Discovery(apiClient);
        var result = endpoint.discoveryServicesInfo(bindingId, acceptLanguage);
        console.writeJson(result, options.isPrettyPrint());
        return 0;
    }
}
