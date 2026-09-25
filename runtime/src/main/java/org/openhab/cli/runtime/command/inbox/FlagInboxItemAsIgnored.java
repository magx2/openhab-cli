package org.openhab.cli.runtime.command.inbox;

import java.util.concurrent.Callable;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.engine.endpoint.Inbox;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Flags a discovery result as ignored for further processing. */
@Slf4j
@CommandLine.Command(
        name = "flagInboxItemAsIgnored",
        description = "Flags a discovery result as ignored for further processing.",
        mixinStandardHelpOptions = true)
public class FlagInboxItemAsIgnored implements Callable<Integer> {
    @CommandLine.Mixin
    private Options options;

    @CommandLine.Parameters(index = "0", arity = "1", paramLabel = "<thingUID>", description = "thingUID (required)")
    private String thingUID;

    private final Console console;
    private final ApiClientBuilder apiClientBuilder;

    /** Creates the command with injected output and REST client services. */
    @Inject
    FlagInboxItemAsIgnored(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link Inbox#flagInboxItemAsIgnored} and returns zero on success. */
    @Override
    public Integer call() {
        log.debug("Command: Inbox.flagInboxItemAsIgnored");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Inbox(apiClient);
        endpoint.flagInboxItemAsIgnored(thingUID);
        return 0;
    }
}
