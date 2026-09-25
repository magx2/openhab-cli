package org.openhab.cli.runtime;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.openhab.cli.engine.properties.Properties;
import picocli.CommandLine;

class OptionsTest {
    private Options parse(String... args) {
        var options = new Options();
        new CommandLine(options).parseArgs(args);
        return options;
    }

    @Test
    void omittedFlagsPreserveAllConfiguredValues() {
        var configured = new Properties(
                null,
                "http://host/rest",
                null,
                "user",
                "pass",
                false,
                false,
                true,
                "certificate",
                null,
                "host",
                123,
                456,
                789);
        assertEquals(configured, parse().overrideProps(configured));
        assertEquals(Properties.DEFAULT, parse().overrideProps(Properties.DEFAULT));
    }

    @Test
    void groupedFlagsOverrideEverySetting() {
        var options = parse(
                "--base-path=http://host/rest",
                "--username=user",
                "--password=pass",
                "--no-pretty-print",
                "--no-verifying-ssl",
                "--api-client-debugging",
                "--ssl-ca-cert=certificate",
                "--tls-server-name=host",
                "--connect-timeout=123",
                "--read-timeout=0",
                "--write-timeout=789");
        assertEquals(
                new Properties(
                        null,
                        "http://host/rest",
                        null,
                        "user",
                        "pass",
                        false,
                        false,
                        true,
                        "certificate",
                        null,
                        "host",
                        123,
                        0,
                        789),
                options.overrideProps(Properties.DEFAULT));
    }

    @Test
    void oauthCannotBeCombinedWithBasicAuthenticationFromFile() {
        var configured =
                new Properties(null, null, null, "user", "pass", true, true, false, null, null, null, 10, 20, 30);
        assertThrows(IllegalArgumentException.class, () -> parse("--oauth-token=token")
                .overrideProps(configured));
        assertEquals(
                "token",
                parse("--oauth-token=token").overrideProps(Properties.DEFAULT).oAuthToken());
    }

    @Test
    void certificateFlagReplacesAlternativeFromFile() {
        var configured = new Properties(
                null, null, null, null, null, true, true, false, "old certificate", null, null, 10, 20, 30);
        var merged = parse("--ssl-ca-cert-path=ca.pem").overrideProps(configured);
        assertNull(merged.sslCaCert());
        assertEquals(Path.of("ca.pem"), merged.sslCaCertPath());
        assertThrows(
                CommandLine.ParameterException.class,
                () -> parse("--ssl-ca-cert=certificate", "--ssl-ca-cert-path=ca.pem"));
    }

    @Test
    void booleanFlagsCanExplicitlyEnableOrDisableFileValues() {
        var configured = new Properties(null, null, null, null, null, false, false, true, null, null, null, 10, 20, 30);
        var merged = parse("--pretty-print", "--verifying-ssl", "--no-api-client-debugging")
                .overrideProps(configured);
        assertTrue(merged.prettyPrint());
        assertTrue(merged.verifyingSsl());
        assertFalse(merged.apiClientDebugging());
    }
}
