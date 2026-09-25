package org.openhab.cli.runtime.command.inbox;

import java.util.concurrent.Callable;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.engine.endpoint.Inbox;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Approves the discovery result by adding the thing to the registry. */
@Slf4j
@CommandLine.Command(
        name = "approveInboxItemById",
        description = "Approves the discovery result by adding the thing to the registry.",
        mixinStandardHelpOptions = true)
public class ApproveInboxItemById implements Callable<Integer> {
    @CommandLine.Mixin
    private Options options;

    @CommandLine.Parameters(index = "0", arity = "1", paramLabel = "<thingUID>", description = "thingUID (required)")
    private String thingUID;

    @CommandLine.Option(
            names = "--accept-language",
            paramLabel = "<acceptLanguage>",
            description = "language (optional)",
            arity = "1")
    private String acceptLanguage;

    @CommandLine.Option(
            names = "--new-thing-id",
            paramLabel = "<newThingId>",
            description = "new thing ID (optional)",
            arity = "1")
    private String newThingId;

    @CommandLine.Option(names = "--body", paramLabel = "<body>", description = "thing label (optional)", arity = "1")
    private String body;

    private final Console console;
    private final ApiClientBuilder apiClientBuilder;

    /** Creates the command with injected output and REST client services. */
    @Inject
    ApproveInboxItemById(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link Inbox#approveInboxItemById} and returns zero on success. */
    @Override
    public Integer call() {
        log.debug("Command: Inbox.approveInboxItemById");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Inbox(apiClient);
        endpoint.approveInboxItemById(thingUID, acceptLanguage, newThingId, body);
        return 0;
    }
}
