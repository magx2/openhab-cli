package org.openhab.cli.runtime.command.auth;

import java.util.concurrent.Callable;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.engine.endpoint.Auth;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** List the API tokens associated to the authenticated user. */
@Slf4j
@CommandLine.Command(
        name = "apiTokens",
        description = "List the API tokens associated to the authenticated user.",
        mixinStandardHelpOptions = true)
public class ApiTokens implements Callable<Integer> {
    @CommandLine.Mixin
    private Options options;

    private final Console console;
    private final ApiClientBuilder apiClientBuilder;

    /** Creates the command with injected output and REST client services. */
    @Inject
    ApiTokens(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link Auth#apiTokens} and returns zero on success. */
    @Override
    public Integer call() {
        log.debug("Command: Auth.apiTokens");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Auth(apiClient);
        var result = endpoint.apiTokens();
        console.writeJson(result, options.isPrettyPrint());
        return 0;
    }
}
