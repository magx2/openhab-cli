package org.openhab.cli.runtime.command.auth;

import java.util.concurrent.Callable;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.engine.endpoint.Auth;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Revoke a specified API token associated to the authenticated user. */
@Slf4j
@CommandLine.Command(
        name = "removeApiToken",
        description = "Revoke a specified API token associated to the authenticated user.",
        mixinStandardHelpOptions = true)
public class RemoveApiToken implements Callable<Integer> {
    @CommandLine.Mixin
    private Options options;

    @CommandLine.Parameters(index = "0", arity = "1", paramLabel = "<name>", description = "(required)")
    private String name;

    private final Console console;
    private final ApiClientBuilder apiClientBuilder;

    /** Creates the command with injected output and REST client services. */
    @Inject
    RemoveApiToken(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link Auth#removeApiToken} and returns zero on success. */
    @Override
    public Integer call() {
        log.debug("Command: Auth.removeApiToken");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Auth(apiClient);
        endpoint.removeApiToken(name);
        return 0;
    }
}
