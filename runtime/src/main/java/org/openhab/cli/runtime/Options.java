package org.openhab.cli.runtime;

import lombok.Getter;
import lombok.ToString;
import org.openhab.cli.engine.properties.Properties;
import picocli.CommandLine.Option;

@ToString
@Getter
public class Options {
    @SuppressWarnings("FieldMayBeFinal")
    @Option(
            names = {"-pp", "--pretty-print"},
            description = {"Pretty print output JSON"},
            negatable = true)
    private boolean prettyPrint = true;

    @Option(
            names = {"-t", "--oauth-token"},
            description = {"oAuth token (overrides properties)"},
            arity = "0..1",
            interactive = true)
    private String oAuthToken;

    @Option(
            names = {"-p", "--properties-file"},
            description = {"oAuth token (overrides properties)"},
            arity = "0..1")
    private String propertiesFile;

    public Properties overrideProps(Properties props) {
        return new Properties(
                firstNonNullOrNull(oAuthToken, props.oAuthToken()),
                firstNonNullOrNull(null, props.username()), // todo
                firstNonNullOrNull(null, props.password()), // todo
                prettyPrint || props.prettyPrint());
    }

    @SafeVarargs
    private <T> T firstNonNullOrNull(T... params) {
        for (T param : params) {
            if (param != null) {
                return param;
            }
        }
        return null;
    }
}
