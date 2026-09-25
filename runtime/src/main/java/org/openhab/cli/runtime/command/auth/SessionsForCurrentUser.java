package org.openhab.cli.runtime.command.auth;

import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.engine.endpoint.Auth;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** List the sessions associated to the authenticated user. */
@Slf4j
@CommandLine.Command(
        name = "sessionsForCurrentUser",
        description = "List the sessions associated to the authenticated user.",
        mixinStandardHelpOptions = true)
public class SessionsForCurrentUser implements Runnable {
    @CommandLine.Mixin
    private Options options;

    private final Console console;
    private final ApiClientBuilder apiClientBuilder;

    /** Creates the command with injected output and REST client services. */
    @Inject
    SessionsForCurrentUser(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link Auth#sessionsForCurrentUser}. */
    @Override
    public void run() {
        log.debug("Command: Auth.sessionsForCurrentUser");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Auth(apiClient);
        var result = endpoint.sessionsForCurrentUser();
        console.writeJson(result, options.isPrettyPrint());
    }
}
