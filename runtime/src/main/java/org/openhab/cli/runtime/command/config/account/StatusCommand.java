package org.openhab.cli.runtime.command.config.account;

import javax.inject.Inject;
import lombok.RequiredArgsConstructor;
import org.openhab.cli.runtime.PropertiesFileOptions;
import org.openhab.cli.runtime.service.Console;
import org.openhab.cli.runtime.service.PropertiesReader;
import picocli.CommandLine;

/** Reports the saved authentication method without disclosing credentials. */
@CommandLine.Command(
        name = "status",
        description = "Show the saved login method, without displaying credentials or verifying them with the server.",
        mixinStandardHelpOptions = true)
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class StatusCommand implements Runnable {
    @CommandLine.Mixin
    private PropertiesFileOptions options;

    private final PropertiesReader propertiesReader;
    private final Console console;

    /** Reads the selected properties file and reports OAuth, username/password, or no login. */
    @Override
    public void run() {
        var props = propertiesReader.readProperties(options.getPropertiesFile());
        var token = !props.getProperty("auth.oAuthToken", "").isBlank();
        var username = !props.getProperty("auth.username", "").isBlank();
        var password = !props.getProperty("auth.password", "").isEmpty();
        if (token && (username || password)) {
            console.write(
                    "Conflicting saved login credentials: OAuth and username/password. Run account login to select one method.");
        } else if (token) {
            console.write("Saved login: OAuth token (not verified).");
        } else if (username && password) {
            console.write("Saved login: username/password (not verified).");
        } else if (username || password) {
            console.write("Not logged in: saved username/password credentials are incomplete.");
        } else {
            console.write("Not logged in: no saved credentials.");
        }
    }
}
