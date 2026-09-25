package org.openhab.cli.runtime.command.voice;

import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.engine.endpoint.Voice;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Get the list of all voices. */
@Slf4j
@CommandLine.Command(name = "voices", description = "Get the list of all voices.", mixinStandardHelpOptions = true)
public class Voices implements Runnable {
    @CommandLine.Mixin
    private Options options;

    private final Console console;
    private final ApiClientBuilder apiClientBuilder;

    /** Creates the command with injected output and REST client services. */
    @Inject
    Voices(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link Voice#voices}. */
    @Override
    public void run() {
        log.debug("Command: Voice.voices");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Voice(apiClient);
        var result = endpoint.voices();
        console.writeJson(result, options.isPrettyPrint());
    }
}
