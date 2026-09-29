package org.openhab.cli.runtime.command.config.account;

import java.util.HashMap;
import java.util.concurrent.Callable;
import javax.inject.Inject;
import lombok.RequiredArgsConstructor;
import org.openhab.cli.runtime.PropertiesFileOptions;
import org.openhab.cli.runtime.service.Console;
import org.openhab.cli.runtime.service.PropertiesReader;
import picocli.CommandLine;

/** Saves one authentication method, removing credentials for the other method. */
@CommandLine.Command(
        name = "login",
        description =
                "Save an OAuth token or username/password in the properties file. Replaces the previous login; does not verify credentials with the server.",
        mixinStandardHelpOptions = true)
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class LoginCommand implements Callable<Integer> {
    @CommandLine.Mixin
    private PropertiesFileOptions options;

    @CommandLine.Option(
            names = {"-t", "--oauth-token"},
            description = "OAuth token to save (prompts if no value is supplied)",
            arity = "0..1",
            interactive = true)
    private String token;

    @CommandLine.Option(
            names = "--username",
            description = "Basic authentication username to save (prompts if no value is supplied)",
            arity = "0..1",
            interactive = true)
    private String username;

    @CommandLine.Option(
            names = "--password",
            description = "Basic authentication password to save (prompts if no value is supplied)",
            arity = "0..1",
            interactive = true)
    private String password;

    @CommandLine.Spec
    private CommandLine.Model.CommandSpec spec;

    private final PropertiesReader propertiesReader;
    private final Console console;

    /** Validates and stores credentials, then confirms the saved method without revealing secrets. */
    @Override
    public Integer call() {
        if (token != null) {
            if (token.isBlank() || username != null || password != null) {
                throw new CommandLine.ParameterException(
                        spec.commandLine(), "Supply a nonblank OAuth token without username or password.");
            }
        } else if (username == null || username.isBlank() || password == null || password.isEmpty()) {
            throw new CommandLine.ParameterException(
                    spec.commandLine(), "Supply --oauth-token or both --username and --password.");
        }
        var changes = new HashMap<String, String>();
        changes.put("auth.oAuthToken", token);
        changes.put("auth.username", username);
        changes.put("auth.password", password);
        var result = propertiesReader.update(options.getPropertiesFile(), changes);
        if (result == 0) {
            var method = token != null ? "OAuth token" : "username/password (basic authentication)";
            console.write("Properties file: " + PropertiesReader.resolvePath(options.getPropertiesFile()));
            console.write("Saved login: " + method + ". Credentials have not been verified with the server.");
        }
        return result;
    }
}
