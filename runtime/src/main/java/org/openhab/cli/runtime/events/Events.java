package org.openhab.cli.runtime.events;

import java.util.concurrent.Callable;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Get all events. */
@Slf4j
@CommandLine.Command(name = "events", description = "Get all events.", mixinStandardHelpOptions = true)
public class Events implements Callable<Integer> {
    @CommandLine.Mixin
    private Options options;

    @CommandLine.Option(names = "--topics", paramLabel = "<topics>", description = "topics (optional)", arity = "1")
    private String topics;

    private final Console console;
    private final ApiClientBuilder apiClientBuilder;

    /** Creates the command with injected output and REST client services. */
    @Inject
    Events(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link org.openhab.cli.engine.endpoint.Events#events} and returns zero on success. */
    @Override
    public Integer call() {
        log.debug("Command: Events.events");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new org.openhab.cli.engine.endpoint.Events(apiClient);
        endpoint.events(topics);
        return 0;
    }
}
