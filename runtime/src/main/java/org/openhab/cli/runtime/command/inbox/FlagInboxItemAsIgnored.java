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

/** Flags a discovery result as ignored for further processing. */
@Slf4j
@CommandLine.Command(
        name = "flagInboxItemAsIgnored",
        description = "Flags a discovery result as ignored for further processing.",
        mixinStandardHelpOptions = true)
@RequiredArgsConstructor(access = AccessLevel.PACKAGE, onConstructor_ = @Inject)
public class FlagInboxItemAsIgnored implements Runnable {
    @CommandLine.Mixin
    private Options options;

    @CommandLine.Parameters(index = "0", arity = "1", paramLabel = "<thingUID>", description = "thingUID (required)")
    private String thingUID;

    private final Console console;
    private final ApiClientBuilder apiClientBuilder;

    /** Executes {@link Inbox#flagInboxItemAsIgnored}. */
    @Override
    public void run() {
        log.debug("Command: Inbox.flagInboxItemAsIgnored");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Inbox(apiClient);
        endpoint.flagInboxItemAsIgnored(thingUID);
    }
}
