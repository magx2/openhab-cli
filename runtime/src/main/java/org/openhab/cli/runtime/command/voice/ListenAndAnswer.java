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

/** Executes a simple dialog sequence without keyword spotting for a given audio source. */
@Slf4j
@CommandLine.Command(
        name = "listenAndAnswer",
        description = "Executes a simple dialog sequence without keyword spotting for a given audio source.",
        mixinStandardHelpOptions = true)
public class ListenAndAnswer implements Callable<Integer> {
    @CommandLine.Mixin
    private Options options;

    @CommandLine.Option(
            names = "--accept-language",
            paramLabel = "<acceptLanguage>",
            description = "language (optional)",
            arity = "1")
    private String acceptLanguage;

    @CommandLine.Option(
            names = "--source-id",
            paramLabel = "<sourceId>",
            description = "source ID (optional)",
            arity = "1")
    private String sourceId;

    @CommandLine.Option(
            names = "--stt-id",
            paramLabel = "<sttId>",
            description = "Speech-to-Text ID (optional)",
            arity = "1")
    private String sttId;

    @CommandLine.Option(
            names = "--tts-id",
            paramLabel = "<ttsId>",
            description = "Text-to-Speech ID (optional)",
            arity = "1")
    private String ttsId;

    @CommandLine.Option(
            names = "--voice-id",
            paramLabel = "<voiceId>",
            description = "voice ID (optional)",
            arity = "1")
    private String voiceId;

    @CommandLine.Option(
            names = "--hli-ids",
            paramLabel = "<hliIds>",
            description = "interpreter IDs (optional) Supply a JSON value.",
            arity = "1")
    private String hliIds;

    @CommandLine.Option(
            names = "--sink-id",
            paramLabel = "<sinkId>",
            description = "audio sink ID (optional)",
            arity = "1")
    private String sinkId;

    @CommandLine.Option(
            names = "--listening-item",
            paramLabel = "<listeningItem>",
            description = "listening item (optional)",
            arity = "1")
    private String listeningItem;

    private final Console console;
    private final ApiClientBuilder apiClientBuilder;

    /** Creates the command with injected output and REST client services. */
    @Inject
    ListenAndAnswer(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link Voice#listenAndAnswer} and returns zero on success. */
    @Override
    public Integer call() {
        log.debug("Command: Voice.listenAndAnswer");
        List<String> hliIdsValue = JsonArguments.parse(hliIds, new TypeToken<List<String>>() {}.getType(), "hliIds");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Voice(apiClient);
        endpoint.listenAndAnswer(acceptLanguage, sourceId, sttId, ttsId, voiceId, hliIdsValue, sinkId, listeningItem);
        return 0;
    }
}
