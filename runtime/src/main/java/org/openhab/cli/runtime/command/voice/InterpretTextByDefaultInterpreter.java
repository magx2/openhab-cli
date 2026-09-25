package org.openhab.cli.runtime.command.voice;

import com.google.gson.reflect.TypeToken;
import java.util.List;
import java.util.concurrent.Callable;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.engine.endpoint.Voice;
import org.openhab.cli.runtime.JsonArguments;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Sends a text to the default human language interpreter. */
@Slf4j
@CommandLine.Command(
        name = "interpretTextByDefaultInterpreter",
        description = "Sends a text to the default human language interpreter.",
        mixinStandardHelpOptions = true)
public class InterpretTextByDefaultInterpreter implements Callable<Integer> {
    @CommandLine.Mixin
    private Options options;

    @CommandLine.Parameters(
            index = "0",
            arity = "1",
            paramLabel = "<body>",
            description = "text to interpret (required)")
    private String body;

    @CommandLine.Option(
            names = "--accept-language",
            paramLabel = "<acceptLanguage>",
            description = "language (optional)",
            arity = "1")
    private String acceptLanguage;

    @CommandLine.Option(
            names = "--conversation",
            paramLabel = "<conversation>",
            description = "Conversation id (optional)",
            arity = "1")
    private String conversation;

    @CommandLine.Option(
            names = "--llm-tools",
            paramLabel = "<llmTools>",
            description = "Comma separated list of llm-tool ids or * wildcard (optional) Supply a JSON value.",
            arity = "1")
    private String llmTools;

    @CommandLine.Option(
            names = "--location-item",
            paramLabel = "<locationItem>",
            description = "Location item id to contextualize the command (optional)",
            arity = "1")
    private String locationItem;

    private final Console console;
    private final ApiClientBuilder apiClientBuilder;

    /** Creates the command with injected output and REST client services. */
    @Inject
    InterpretTextByDefaultInterpreter(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link Voice#interpretTextByDefaultInterpreter} and returns zero on success. */
    @Override
    public Integer call() {
        log.debug("Command: Voice.interpretTextByDefaultInterpreter");
        List<String> llmToolsValue =
                JsonArguments.parse(llmTools, new TypeToken<List<String>>() {}.getType(), "llmTools");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Voice(apiClient);
        var result = endpoint.interpretTextByDefaultInterpreter(
                body, acceptLanguage, conversation, llmToolsValue, locationItem);
        console.writeJson(result, options.isPrettyPrint());
        return 0;
    }
}
