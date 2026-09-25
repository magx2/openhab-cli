package org.openhab.cli.runtime.command.voice;

import java.util.concurrent.Callable;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.engine.endpoint.Voice;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Gets the default voice. */
@Slf4j
@CommandLine.Command(name = "defaultVoice", description = "Gets the default voice.", mixinStandardHelpOptions = true)
public class DefaultVoice implements Callable<Integer> {
    @CommandLine.Mixin
    private Options options;

    private final Console console;
    private final ApiClientBuilder apiClientBuilder;

    /** Creates the command with injected output and REST client services. */
    @Inject
    DefaultVoice(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link Voice#defaultVoice} and returns zero on success. */
    @Override
    public Integer call() {
        log.debug("Command: Voice.defaultVoice");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Voice(apiClient);
        var result = endpoint.defaultVoice();
        console.writeJson(result, options.isPrettyPrint());
        return 0;
    }
}
