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

/** Get the list of all LLM tools. */
@Slf4j
@CommandLine.Command(name = "llmTools", description = "Get the list of all LLM tools.", mixinStandardHelpOptions = true)
@RequiredArgsConstructor(access = AccessLevel.PACKAGE, onConstructor_ = @Inject)
public class LlmTools implements Runnable {
    @CommandLine.Mixin
    private Options options;

    @CommandLine.Option(
            names = "--accept-language",
            paramLabel = "<acceptLanguage>",
            description = "language (optional)",
            arity = "1")
    private String acceptLanguage;

    private final Console console;
    private final ApiClientBuilder apiClientBuilder;

    /** Executes {@link Voice#llmTools}. */
    @Override
    public void run() {
        log.debug("Command: Voice.llmTools");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Voice(apiClient);
        var result = endpoint.llmTools(acceptLanguage);
        console.writeJson(result, options.isPrettyPrint());
    }
}
