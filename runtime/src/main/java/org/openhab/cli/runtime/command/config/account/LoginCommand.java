package org.openhab.cli.runtime.command.config.account;

import java.util.HashMap;
import java.util.concurrent.Callable;
import javax.inject.Inject;
import lombok.RequiredArgsConstructor;
import org.openhab.cli.runtime.Options;
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
    private Options options;

    @CommandLine.Spec
    private CommandLine.Model.CommandSpec spec;

    private final PropertiesReader propertiesReader;

    /** Validates explicit credentials and stores them together, preserving unrelated properties. */
    @Override
    public Integer call() {
        var token = options.getOAuthToken();
        var username = options.getUsername();
        var password = options.getPassword();
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
        return propertiesReader.update(options.getPropertiesFile(), changes);
    }
}
