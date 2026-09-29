package org.openhab.cli.runtime.command.config.account;

import java.util.HashMap;
import java.util.concurrent.Callable;
import javax.inject.Inject;
import lombok.RequiredArgsConstructor;
import org.openhab.cli.runtime.PropertiesFileOptions;
import org.openhab.cli.runtime.service.Console;
import org.openhab.cli.runtime.service.PropertiesReader;
import picocli.CommandLine;

/** Removes selected authentication credentials while preserving other configuration. */
@CommandLine.Command(
        name = "logout",
        description = "Remove saved login credentials. Omit the method to remove all credentials.",
        mixinStandardHelpOptions = true)
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class LogoutCommand implements Callable<Integer> {
    @CommandLine.Mixin
    private PropertiesFileOptions options;

    @CommandLine.Parameters(
            index = "0",
            arity = "0..1",
            paramLabel = "[method]",
            converter = MethodConverter.class,
            description =
                    "Authentication method: ${COMPLETION-CANDIDATES}. basic also accepts username/password or username/pass; omitted means all.")
    private Method method;

    private final PropertiesReader propertiesReader;
    private final Console console;

    /** Supported authentication methods; an omitted method selects both. */
    public enum Method {
        oauth,
        basic
    }

    /** Accepts case-insensitive method names and username/password aliases. */
    public static class MethodConverter implements CommandLine.ITypeConverter<Method> {
        /** Converts the logout selector without depending on the host locale. */
        @Override
        public Method convert(String value) {
            return switch (value.toLowerCase(java.util.Locale.ROOT)) {
                case "oauth" -> Method.oauth;
                case "basic", "username/password", "username/pass" -> Method.basic;
                default ->
                    throw new CommandLine.TypeConversionException("Expected oauth or username/password (basic).");
            };
        }
    }

    /** Removes selected credentials and confirms their absence from the selected properties file. */
    @Override
    public Integer call() {
        var changes = new HashMap<String, String>();
        if (method == null || method == Method.oauth) {
            changes.put("auth.oAuthToken", null);
        }
        if (method == null || method == Method.basic) {
            changes.put("auth.username", null);
            changes.put("auth.password", null);
        }
        var result = propertiesReader.update(options.getPropertiesFile(), changes);
        if (result == 0) {
            var credentials = method == null
                    ? "All login credentials"
                    : method == Method.oauth ? "OAuth credentials" : "Username/password credentials";
            console.write("Properties file: " + PropertiesReader.resolvePath(options.getPropertiesFile()));
            console.write(credentials + " cleared (not saved in this file). Server tokens have not been revoked.");
        }
        return result;
    }
}
