package org.openhab.cli.runtime.command.voice;

import java.util.concurrent.Callable;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.engine.endpoint.Voice;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Speaks a given text with a given voice through the given audio sink. */
@Slf4j
@CommandLine.Command(
        name = "textToSpeech",
        description = "Speaks a given text with a given voice through the given audio sink.",
        mixinStandardHelpOptions = true)
public class TextToSpeech implements Callable<Integer> {
    @CommandLine.Mixin
    private Options options;

    @CommandLine.Parameters(index = "0", arity = "1", paramLabel = "<body>", description = "text to speak (required)")
    private String body;

    @CommandLine.Option(names = "--voiceid", paramLabel = "<voiceid>", description = "voice id (optional)", arity = "1")
    private String voiceid;

    @CommandLine.Option(
            names = "--sinkid",
            paramLabel = "<sinkid>",
            description = "audio sink id (optional)",
            arity = "1")
    private String sinkid;

    @CommandLine.Option(
            names = "--volume",
            paramLabel = "<volume>",
            description = "volume level (optional)",
            arity = "1")
    private String volume;

    private final Console console;
    private final ApiClientBuilder apiClientBuilder;

    /** Creates the command with injected output and REST client services. */
    @Inject
    TextToSpeech(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link Voice#textToSpeech} and returns zero on success. */
    @Override
    public Integer call() {
        log.debug("Command: Voice.textToSpeech");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Voice(apiClient);
        endpoint.textToSpeech(body, voiceid, sinkid, volume);
        return 0;
    }
}
