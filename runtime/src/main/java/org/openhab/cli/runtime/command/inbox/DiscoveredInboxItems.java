package org.openhab.cli.runtime.command.inbox;

import javax.inject.Inject;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor(access = AccessLevel.PACKAGE, onConstructor_ = @Inject)
public class DiscoveredInboxItems implements Runnable {
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

    /** Executes {@link Inbox#discoveredInboxItems}. */
    @Override
    public void run() {
        log.debug("Command: Inbox.discoveredInboxItems");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Inbox(apiClient);
        var result = endpoint.discoveredInboxItems(includeIgnored);
        console.writeJson(result, options.isPrettyPrint());
    }
}
