package org.openhab.cli.runtime.voice;

import java.util.concurrent.Callable;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.engine.endpoint.Voice;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Stop dialog processing for a given audio source. */
@Slf4j
@CommandLine.Command(
        name = "stopDialog",
        description = "Stop dialog processing for a given audio source.",
        mixinStandardHelpOptions = true)
public class StopDialog implements Callable<Integer> {
    @CommandLine.Mixin
    private Options options;

    @CommandLine.Option(
            names = "--source-id",
            paramLabel = "<sourceId>",
            description = "source ID (optional)",
            arity = "1")
    private String sourceId;

    private final Console console;
    private final ApiClientBuilder apiClientBuilder;

    /** Creates the command with injected output and REST client services. */
    @Inject
    StopDialog(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link Voice#stopDialog} and returns zero on success. */
    @Override
    public Integer call() {
        log.debug("Command: Voice.stopDialog");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Voice(apiClient);
        endpoint.stopDialog(sourceId);
        return 0;
    }
}
