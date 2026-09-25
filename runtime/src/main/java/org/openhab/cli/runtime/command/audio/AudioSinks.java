package org.openhab.cli.runtime.command.audio;

import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.engine.endpoint.Audio;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Get the list of all sinks. */
@Slf4j
@CommandLine.Command(name = "audioSinks", description = "Get the list of all sinks.", mixinStandardHelpOptions = true)
public class AudioSinks implements Runnable {
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
    AudioSinks(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link Audio#audioSinks}. */
    @Override
    public void run() {
        log.debug("Command: Audio.audioSinks");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Audio(apiClient);
        var result = endpoint.audioSinks(acceptLanguage);
        console.writeJson(result, options.isPrettyPrint());
    }
}
