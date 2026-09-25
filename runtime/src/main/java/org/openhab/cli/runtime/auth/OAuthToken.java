package org.openhab.cli.runtime.auth;

import java.util.concurrent.Callable;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.engine.endpoint.Auth;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Get access and refresh tokens. */
@Slf4j
@CommandLine.Command(
        name = "oAuthToken",
        description = "Get access and refresh tokens.",
        mixinStandardHelpOptions = true)
public class OAuthToken implements Callable<Integer> {
    @CommandLine.Mixin
    private Options options;

    @CommandLine.Option(
            names = "--use-cookie",
            paramLabel = "<useCookie>",
            description = "(optional)",
            arity = "0..1",
            fallbackValue = "true")
    private Boolean useCookie;

    @CommandLine.Option(names = "--grant-type", paramLabel = "<grantType>", description = "(optional)", arity = "1")
    private String grantType;

    @CommandLine.Option(names = "--code", paramLabel = "<code>", description = "(optional)", arity = "1")
    private String code;

    @CommandLine.Option(names = "--redirect-uri", paramLabel = "<redirectUri>", description = "(optional)", arity = "1")
    private String redirectUri;

    @CommandLine.Option(names = "--client-id", paramLabel = "<clientId>", description = "(optional)", arity = "1")
    private String clientId;

    @CommandLine.Option(
            names = "--refresh-token",
            paramLabel = "<refreshToken>",
            description = "(optional)",
            arity = "1")
    private String refreshToken;

    @CommandLine.Option(
            names = "--code-verifier",
            paramLabel = "<codeVerifier>",
            description = "(optional)",
            arity = "1")
    private String codeVerifier;

    private final Console console;
    private final ApiClientBuilder apiClientBuilder;

    /** Creates the command with injected output and REST client services. */
    @Inject
    OAuthToken(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link Auth#oAuthToken} and returns zero on success. */
    @Override
    public Integer call() {
        log.debug("Command: Auth.oAuthToken");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Auth(apiClient);
        var result = endpoint.oAuthToken(useCookie, grantType, code, redirectUri, clientId, refreshToken, codeVerifier);
        console.writeJson(result, options.isPrettyPrint());
        return 0;
    }
}
