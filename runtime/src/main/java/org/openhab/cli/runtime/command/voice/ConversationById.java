package org.openhab.cli.runtime.command.voice;

import javax.inject.Inject;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.engine.endpoint.Voice;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Get a conversation. */
@Slf4j
@CommandLine.Command(name = "conversationById", description = "Get a conversation.", mixinStandardHelpOptions = true)
@RequiredArgsConstructor(access = AccessLevel.PACKAGE, onConstructor_ = @Inject)
public class ConversationById implements Runnable {
    @CommandLine.Mixin
    private Options options;

    @CommandLine.Parameters(index = "0", arity = "1", paramLabel = "<id>", description = "conversation id (required)")
    private String id;

    private final Console console;
    private final ApiClientBuilder apiClientBuilder;

    /** Executes {@link Voice#conversationById}. */
    @Override
    public void run() {
        log.debug("Command: Voice.conversationById");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Voice(apiClient);
        var result = endpoint.conversationById(id);
        console.writeJson(result, options.isPrettyPrint());
    }
}
