package org.openhab.cli.runtime.voice;

import java.util.concurrent.Callable;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.engine.endpoint.Voice;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Deletes a full conversation or its messages since a given message id. */
@Slf4j
@CommandLine.Command(
        name = "deleteConversationById",
        description = "Deletes a full conversation or its messages since a given message id.",
        mixinStandardHelpOptions = true)
public class DeleteConversationById implements Callable<Integer> {
    @CommandLine.Mixin
    private Options options;

    @CommandLine.Parameters(index = "0", arity = "1", paramLabel = "<id>", description = "conversation id (required)")
    private String id;

    @CommandLine.Option(
            names = "--message-id",
            paramLabel = "<messageId>",
            description = "Optional message ID (optional)",
            arity = "1")
    private Integer messageId;

    private final Console console;
    private final ApiClientBuilder apiClientBuilder;

    /** Creates the command with injected output and REST client services. */
    @Inject
    DeleteConversationById(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link Voice#deleteConversationById} and returns zero on success. */
    @Override
    public Integer call() {
        log.debug("Command: Voice.deleteConversationById");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Voice(apiClient);
        endpoint.deleteConversationById(id, messageId);
        return 0;
    }
}
