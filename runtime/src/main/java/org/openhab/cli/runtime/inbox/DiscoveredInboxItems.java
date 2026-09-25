package org.openhab.cli.runtime.inbox;

import java.util.concurrent.Callable;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.engine.endpoint.Inbox;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Get all discovered things. */
@Slf4j
@CommandLine.Command(
        name = "discoveredInboxItems",
        description = "Get all discovered things.",
        mixinStandardHelpOptions = true)
public class DiscoveredInboxItems implements Callable<Integer> {
    @CommandLine.Mixin
    private Options options;

    @CommandLine.Parameters(
            index = "0",
            arity = "1",
            paramLabel = "<includeIgnored>",
            description = "If true, include ignored inbox entries. Defaults to true (optional, default to true)")
    private Boolean includeIgnored;

    private final Console console;
    private final ApiClientBuilder apiClientBuilder;

    /** Creates the command with injected output and REST client services. */
    @Inject
    DiscoveredInboxItems(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link Inbox#discoveredInboxItems} and returns zero on success. */
    @Override
    public Integer call() {
        log.debug("Command: Inbox.discoveredInboxItems");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Inbox(apiClient);
        var result = endpoint.discoveredInboxItems(includeIgnored);
        console.writeJson(result, options.isPrettyPrint());
        return 0;
    }
}
