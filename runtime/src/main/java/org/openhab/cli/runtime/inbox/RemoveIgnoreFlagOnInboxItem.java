package org.openhab.cli.runtime.inbox;

import java.util.concurrent.Callable;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.engine.endpoint.Inbox;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Removes ignore flag from a discovery result. */
@Slf4j
@CommandLine.Command(
        name = "removeIgnoreFlagOnInboxItem",
        description = "Removes ignore flag from a discovery result.",
        mixinStandardHelpOptions = true)
public class RemoveIgnoreFlagOnInboxItem implements Callable<Integer> {
    @CommandLine.Mixin
    private Options options;

    @CommandLine.Parameters(index = "0", arity = "1", paramLabel = "<thingUID>", description = "thingUID (required)")
    private String thingUID;

    private final Console console;
    private final ApiClientBuilder apiClientBuilder;

    /** Creates the command with injected output and REST client services. */
    @Inject
    RemoveIgnoreFlagOnInboxItem(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link Inbox#removeIgnoreFlagOnInboxItem} and returns zero on success. */
    @Override
    public Integer call() {
        log.debug("Command: Inbox.removeIgnoreFlagOnInboxItem");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Inbox(apiClient);
        endpoint.removeIgnoreFlagOnInboxItem(thingUID);
        return 0;
    }
}
