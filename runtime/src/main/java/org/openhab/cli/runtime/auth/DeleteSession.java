package org.openhab.cli.runtime.auth;

import java.util.concurrent.Callable;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.engine.endpoint.Auth;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Delete the session associated with a refresh token. */
@Slf4j
@CommandLine.Command(
        name = "deleteSession",
        description = "Delete the session associated with a refresh token.",
        mixinStandardHelpOptions = true)
public class DeleteSession implements Callable<Integer> {
    @CommandLine.Mixin
    private Options options;

    @CommandLine.Option(
            names = "--refresh-token",
            paramLabel = "<refreshToken>",
            description = "(optional)",
            arity = "1")
    private String refreshToken;

    @CommandLine.Option(names = "--id", paramLabel = "<id>", description = "(optional)", arity = "1")
    private String id;

    private final Console console;
    private final ApiClientBuilder apiClientBuilder;

    /** Creates the command with injected output and REST client services. */
    @Inject
    DeleteSession(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link Auth#deleteSession} and returns zero on success. */
    @Override
    public Integer call() {
        log.debug("Command: Auth.deleteSession");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Auth(apiClient);
        endpoint.deleteSession(refreshToken, id);
        return 0;
    }
}
