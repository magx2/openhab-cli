package org.openhab.cli.runtime.command.audio;

import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.engine.endpoint.Audio;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Get the default source if defined or the first available source. */
@Slf4j
@CommandLine.Command(
        name = "audioDefaultSource",
        description = "Get the default source if defined or the first available source.",
        mixinStandardHelpOptions = true)
public class AudioDefaultSource implements Runnable {
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

    /** Creates the command with injected output and REST client services. */
    @Inject
    AudioDefaultSource(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link Audio#audioDefaultSource}. */
    @Override
    public void run() {
        log.debug("Command: Audio.audioDefaultSource");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Audio(apiClient);
        var result = endpoint.audioDefaultSource(acceptLanguage);
        console.writeJson(result, options.isPrettyPrint());
    }
}
