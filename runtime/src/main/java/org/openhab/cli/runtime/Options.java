package org.openhab.cli.runtime;

import java.nio.file.Path;
import lombok.Getter;
import org.openhab.cli.engine.properties.Properties;
import picocli.CommandLine.ArgGroup;
import picocli.CommandLine.Option;

/** Shared CLI options. Explicit flags override file settings; omitted flags preserve them. */
@SuppressWarnings("FieldMayBeFinal")
public class Options {
    @Getter
    @Option(
            names = {"-p", "--properties-file"},
            description = "Properties file (default: oh-cli.properties)")
    private String propertiesFile;

    @ArgGroup(exclusive = false, heading = "Connection options:%n")
    private Connection connection = new Connection();

    @ArgGroup(exclusive = false, heading = "Authentication options:%n")
    private Authentication authentication = new Authentication();

    @ArgGroup(exclusive = false, heading = "TLS options:%n")
    private Tls tls = new Tls();

    @ArgGroup(exclusive = false, heading = "Timeout options:%n")
    private Timeouts timeouts = new Timeouts();

    @ArgGroup(exclusive = false, heading = "Output options:%n")
    private Output output = new Output();

    /** Connection options. */
    static class Connection {
        @Option(
                names = "--base-url",
                description = "openHAB server URL (required unless config.rest.baseUrl is set in the properties file)")
        private String baseUrl;

        @Option(
                names = {"--base-path"},
                description = "REST API base path (default: /rest)")
        private String basePath;

        @Option(
                names = {"--api-client-debugging"},
                description = "Enable HTTP client debugging (default: false)",
                negatable = true,
                fallbackValue = "true")
        private Boolean apiClientDebugging;
    }

    /** Authentication options. */
    static class Authentication {
        @Option(
                names = {"-t", "--oauth-token"},
                description = "OAuth token (overrides properties)",
                arity = "0..1",
                interactive = true)
        private String oAuthToken;

        @Option(
                names = {"--username"},
                description = "Basic authentication username")
        private String username;

        @Option(
                names = {"--password"},
                description = "Basic authentication password",
                arity = "0..1",
                interactive = true)
        private String password;
    }

    /** TLS options. */
    static class Tls {
        @Option(
                names = {"--verifying-ssl"},
                description = "Verify SSL certificates (default: true)",
                negatable = true,
                fallbackValue = "true")
        private Boolean verifyingSsl;

        @Option(
                names = {"--tls-server-name"},
                description = "TLS server name")
        private String tlsServerName;

        @ArgGroup(exclusive = true)
        private Certificate certificate = new Certificate();
    }

    /** Timeout options. */
    static class Timeouts {
        @Option(
                names = {"--connect-timeout"},
                description = "Connection timeout in milliseconds (default: 10000; 0: unlimited)")
        private Integer connectTimeout;

        @Option(
                names = {"--read-timeout"},
                description = "Read timeout in milliseconds (default: 10000; 0: unlimited)")
        private Integer readTimeout;

        @Option(
                names = {"--write-timeout"},
                description = "Write timeout in milliseconds (default: 10000; 0: unlimited)")
        private Integer writeTimeout;
    }

    /** Output options. */
    static class Output {
        @Option(
                names = {"-pp", "--pretty-print"},
                description = "Pretty print output JSON (default: true)",
                negatable = true,
                fallbackValue = "true")
        private Boolean prettyPrint;
    }

    /** Alternative sources for the trusted CA certificate. */
    static class Certificate {
        @Option(names = "--ssl-ca-cert", description = "Trusted CA certificate in PEM format")
        private String sslCaCert;

        @Option(names = "--ssl-ca-cert-path", description = "Path to a trusted CA certificate")
        private Path sslCaCertPath;
    }

    /** Returns the explicitly supplied OAuth token, if any. */
    public String getOAuthToken() {
        return authentication.oAuthToken;
    }

    /** Returns the output flag, falling back to the application default. */
    public boolean isPrettyPrint() {
        return value(output.prettyPrint, Properties.DEFAULT.prettyPrint());
    }

    /** Merges explicit command-line values with properties loaded from disk. */
    public Properties overrideProps(Properties props) {
        var certificate = tls.certificate;
        boolean replacesCertificate = certificate.sslCaCert != null || certificate.sslCaCertPath != null;
        return new Properties(
                value(connection.baseUrl, props.baseUrl()),
                value(connection.basePath, props.basePath()),
                value(authentication.oAuthToken, props.oAuthToken()),
                value(authentication.username, props.username()),
                value(authentication.password, props.password()),
                value(output.prettyPrint, props.prettyPrint()),
                value(tls.verifyingSsl, props.verifyingSsl()),
                value(connection.apiClientDebugging, props.apiClientDebugging()),
                replacesCertificate ? certificate.sslCaCert : props.sslCaCert(),
                replacesCertificate ? certificate.sslCaCertPath : props.sslCaCertPath(),
                value(tls.tlsServerName, props.tlsServerName()),
                value(timeouts.connectTimeout, props.connectTimeout()),
                value(timeouts.readTimeout, props.readTimeout()),
                value(timeouts.writeTimeout, props.writeTimeout()));
    }

    private static <T> T value(T override, T fallback) {
        return override == null ? fallback : override;
    }
}
