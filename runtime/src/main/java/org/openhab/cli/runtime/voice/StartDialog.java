package org.openhab.cli.runtime.voice;

import java.util.concurrent.Callable;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.engine.endpoint.Voice;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Start dialog processing for a given audio source. */
@Slf4j
@CommandLine.Command(
        name = "startDialog",
        description = "Start dialog processing for a given audio source.",
        mixinStandardHelpOptions = true)
public class StartDialog implements Callable<Integer> {
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
            names = "--ks-id",
            paramLabel = "<ksId>",
            description = "keywork spotter ID (optional)",
            arity = "1")
    private String ksId;

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
            description = "comma separated list of interpreter IDs (optional)",
            arity = "1")
    private String hliIds;

    @CommandLine.Option(
            names = "--sink-id",
            paramLabel = "<sinkId>",
            description = "audio sink ID (optional)",
            arity = "1")
    private String sinkId;

    @CommandLine.Option(names = "--keyword", paramLabel = "<keyword>", description = "keyword (optional)", arity = "1")
    private String keyword;

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
    StartDialog(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link Voice#startDialog} and returns zero on success. */
    @Override
    public Integer call() {
        log.debug("Command: Voice.startDialog");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Voice(apiClient);
        endpoint.startDialog(
                acceptLanguage, sourceId, ksId, sttId, ttsId, voiceId, hliIds, sinkId, keyword, listeningItem);
        return 0;
    }
}
