package org.openhab.cli.runtime.command.discovery;

import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.engine.endpoint.Discovery;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Gets all bindings that support discovery. */
@Slf4j
@CommandLine.Command(
        name = "bindingsWithDiscoverySupport",
        description = "Gets all bindings that support discovery.",
        mixinStandardHelpOptions = true)
public class BindingsWithDiscoverySupport implements Runnable {
    @CommandLine.Mixin
    private Options options;

    private final Console console;
    private final ApiClientBuilder apiClientBuilder;

    /** Creates the command with injected output and REST client services. */
    @Inject
    BindingsWithDiscoverySupport(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link Discovery#bindingsWithDiscoverySupport}. */
    @Override
    public void run() {
        log.debug("Command: Discovery.bindingsWithDiscoverySupport");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Discovery(apiClient);
        var result = endpoint.bindingsWithDiscoverySupport();
        console.writeJson(result, options.isPrettyPrint());
    }
}
