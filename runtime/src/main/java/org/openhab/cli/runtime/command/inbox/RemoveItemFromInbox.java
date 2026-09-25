package org.openhab.cli.runtime.command.inbox;

import java.util.concurrent.Callable;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.engine.endpoint.Inbox;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Removes the discovery result from the inbox. */
@Slf4j
@CommandLine.Command(
        name = "removeItemFromInbox",
        description = "Removes the discovery result from the inbox.",
        mixinStandardHelpOptions = true)
public class RemoveItemFromInbox implements Callable<Integer> {
    @CommandLine.Mixin
    private Options options;

    @CommandLine.Parameters(index = "0", arity = "1", paramLabel = "<thingUID>", description = "thingUID (required)")
    private String thingUID;

    private final Console console;
    private final ApiClientBuilder apiClientBuilder;

    /** Creates the command with injected output and REST client services. */
    @Inject
    RemoveItemFromInbox(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link Inbox#removeItemFromInbox} and returns zero on success. */
    @Override
    public Integer call() {
        log.debug("Command: Inbox.removeItemFromInbox");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Inbox(apiClient);
        endpoint.removeItemFromInbox(thingUID);
        return 0;
    }
}
