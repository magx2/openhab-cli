package org.openhab.cli.runtime.voice;

import java.util.concurrent.Callable;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.engine.endpoint.Voice;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Get a conversation. */
@Slf4j
@CommandLine.Command(name = "conversationById", description = "Get a conversation.", mixinStandardHelpOptions = true)
public class ConversationById implements Callable<Integer> {
    @CommandLine.Mixin
    private Options options;

    @CommandLine.Parameters(index = "0", arity = "1", paramLabel = "<id>", description = "conversation id (required)")
    private String id;

    private final Console console;
    private final ApiClientBuilder apiClientBuilder;

    /** Creates the command with injected output and REST client services. */
    @Inject
    ConversationById(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link Voice#conversationById} and returns zero on success. */
    @Override
    public Integer call() {
        log.debug("Command: Voice.conversationById");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Voice(apiClient);
        var result = endpoint.conversationById(id);
        console.writeJson(result, options.isPrettyPrint());
        return 0;
    }
}
