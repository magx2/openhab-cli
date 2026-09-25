package org.openhab.cli.runtime.audio;

import java.util.concurrent.Callable;
import javax.inject.Inject;
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
public class AudioSources implements Callable<Integer> {
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
    AudioSources(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link Audio#audioSources} and returns zero on success. */
    @Override
    public Integer call() {
        log.debug("Command: Audio.audioSources");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Audio(apiClient);
        var result = endpoint.audioSources(acceptLanguage);
        console.writeJson(result, options.isPrettyPrint());
        return 0;
    }
}
