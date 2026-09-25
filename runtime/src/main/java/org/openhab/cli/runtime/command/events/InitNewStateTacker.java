package org.openhab.cli.runtime.command.events;

import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.engine.endpoint.Events;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Initiates a new item state tracker connection */
@Slf4j
@CommandLine.Command(
        name = "initNewStateTacker",
        description = "Initiates a new item state tracker connection",
        mixinStandardHelpOptions = true)
public class InitNewStateTacker implements Runnable {
    @CommandLine.Mixin
    private Options options;

    private final Console console;
    private final ApiClientBuilder apiClientBuilder;

    /** Creates the command with injected output and REST client services. */
    @Inject
    InitNewStateTacker(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link Events#initNewStateTacker}. */
    @Override
    public void run() {
        log.debug("Command: Events.initNewStateTacker");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Events(apiClient);
        endpoint.initNewStateTacker();
    }
}
