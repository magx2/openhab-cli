package org.openhab.cli.runtime.command.voice;

import java.util.concurrent.Callable;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.engine.endpoint.Voice;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Gets a single interpreter. */
@Slf4j
@CommandLine.Command(
        name = "voiceInterpreterByUID",
        description = "Gets a single interpreter.",
        mixinStandardHelpOptions = true)
public class VoiceInterpreterByUID implements Callable<Integer> {
    @CommandLine.Mixin
    private Options options;

    @CommandLine.Parameters(index = "0", arity = "1", paramLabel = "<id>", description = "interpreter id (required)")
    private String id;

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
    VoiceInterpreterByUID(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link Voice#voiceInterpreterByUID} and returns zero on success. */
    @Override
    public Integer call() {
        log.debug("Command: Voice.voiceInterpreterByUID");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Voice(apiClient);
        var result = endpoint.voiceInterpreterByUID(id, acceptLanguage);
        console.writeJson(result, options.isPrettyPrint());
        return 0;
    }
}
