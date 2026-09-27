package org.openhab.cli.runtime.command.audio;

import javax.inject.Inject;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.engine.endpoint.Audio;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Get the list of all sources. */
@Slf4j
@CommandLine.Command(
        name = "audioSources",
        description = "Get the list of all sources.",
        mixinStandardHelpOptions = true)
@RequiredArgsConstructor(access = AccessLevel.PACKAGE, onConstructor_ = @Inject)
public class AudioSources implements Runnable {
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

    /** Executes {@link Audio#audioSources}. */
    @Override
    public void run() {
        log.debug("Command: Audio.audioSources");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Audio(apiClient);
        var result = endpoint.audioSources(acceptLanguage);
        console.writeJson(result, options.isPrettyPrint());
    }
}
