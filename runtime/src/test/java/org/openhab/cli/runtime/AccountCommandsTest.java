package org.openhab.cli.runtime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.openhab.cli.runtime.command.config.account.StatusCommand;
import org.openhab.cli.runtime.service.Console;
import org.openhab.cli.runtime.service.PropertiesReader;
import picocli.CommandLine;

class AccountCommandsTest {
    @TempDir
    Path directory;

    private final PropertiesReader reader = new PropertiesReader(new Console());

    private int execute(Path file, String action, String... args) {
        var arguments = new ArrayList<>(Arrays.asList("_config", "account", action));
        if (file != null) {
            arguments.addAll(Arrays.asList("-p", file.toString()));
        }
        arguments.addAll(Arrays.asList(args));
        var cli = Cli.commandLine();
        cli.setErr(new PrintWriter(new StringWriter()));
        return cli.execute(arguments.toArray(String[]::new));
    }

    @Test
    void loginCreatesFileAndSwitchesMethodsWithoutLosingSettings() throws Exception {
        var file = directory.resolve("account.properties");
        assertEquals(0, execute(file, "login", "--oauth-token=test-token"));
        assertEquals("test-token", reader.get(file.toString(), "auth.oAuthToken"));
        assertEquals(0, reader.set(file.toString(), "custom", "keep"));
        assertEquals(0, execute(file, "login", "--username=test-user", "--password=test-password"));
        var props = reader.read(file.toString());
        assertNull(props.oAuthToken());
        assertEquals("test-user", props.username());
        assertEquals("test-password", props.password());
        assertEquals(0, execute(file, "login", "-t=another-token"));
        props = reader.read(file.toString());
        assertEquals("another-token", props.oAuthToken());
        assertNull(props.username());
        assertNull(props.password());
        assertEquals("keep", reader.get(file.toString(), "custom"));
    }

    @Test
    void invalidLoginNeverChangesExistingCredentials() throws Exception {
        var file = directory.resolve("account.properties");
        Files.writeString(file, "auth.oAuthToken=existing\n");
        for (var args : new String[][] {
            {},
            {"--username=user"},
            {"--password=secret"},
            {"--oauth-token="},
            {"--username=user", "--password="},
            {"--oauth-token=secret", "--username=user", "--password=secret"}
        }) {
            assertEquals(1, execute(file, "login", args));
            assertEquals("auth.oAuthToken=existing\n", Files.readString(file));
        }
    }

    @Test
    void logoutSelectsOnlyRequestedCredentialsAndPreservesOtherSettings() throws Exception {
        var file = directory.resolve("account.properties");
        for (var selector : new String[] {"oauth", "OAUTH", "basic", "USERNAME/PASSWORD", "username/pass"}) {
            Files.writeString(file, "auth.oAuthToken=token\nauth.username=user\nauth.password=secret\ncustom=keep\n");
            assertEquals(0, execute(file, "logout", selector));
            var props = reader.readProperties(file.toString());
            boolean oauth = selector.equalsIgnoreCase("oauth");
            assertEquals(!oauth, props.containsKey("auth.oAuthToken"));
            assertEquals(oauth, props.containsKey("auth.username"));
            assertEquals(oauth, props.containsKey("auth.password"));
            assertEquals("keep", props.getProperty("custom"));
        }
        assertEquals(1, execute(file, "logout", "invalid"));
        assertEquals(0, execute(file, "logout"));
        assertEquals(java.util.Map.of("custom", "keep"), reader.readProperties(file.toString()));
        assertEquals(0, execute(file, "logout"));
    }

    @Test
    void usesDefaultFileForLoginStatusAndLogout() throws Exception {
        var previous = System.getProperty("user.home");
        System.setProperty("user.home", directory.toString());
        try {
            assertEquals(0, execute(null, "login", "-t=secret"));
            var file = directory.resolve(".oh").resolve(PropertiesReader.PROPERTIES_FILE_NAME);
            assertTrue(Files.exists(file));
            assertEquals("secret", reader.get(null, "auth.oAuthToken"));
            assertEquals(0, execute(null, "status"));
            assertEquals(0, execute(null, "logout"));
            assertTrue(reader.readProperties(null).isEmpty());
        } finally {
            System.setProperty("user.home", previous);
        }
    }

    @Test
    void missingFileMeansLoggedOutAndLogoutDoesNotCreateIt() {
        var file = directory.resolve("missing.properties");
        assertEquals(0, execute(file, "status"));
        assertEquals(0, execute(file, "logout"));
        assertFalse(Files.exists(file));
    }

    @Test
    void statusReportsStoredMethodWithoutSecretsOrConnectionRequirements() throws Exception {
        var file = directory.resolve("account.properties");
        var contents = new String[] {
            "",
            "auth.oAuthToken=secret",
            "auth.username=secret\nauth.password=secret",
            "auth.username=secret",
            "auth.oAuthToken=secret\nauth.password=secret"
        };
        var messages = new String[] {"Not logged in", "OAuth token", "username/password", "incomplete", "Conflicting"};
        for (int i = 0; i < contents.length; i++) {
            Files.writeString(file, contents[i]);
            var console = mock(Console.class);
            var cli = new CommandLine(new StatusCommand(reader, console));
            assertEquals(0, cli.execute("-p", file.toString()));
            var captured = org.mockito.ArgumentCaptor.forClass(String.class);
            verify(console).write(captured.capture());
            assertTrue(captured.getValue().contains(messages[i]));
            assertFalse(captured.getValue().contains("secret"));
            assertEquals(contents[i], Files.readString(file));
        }
    }

    @Test
    void ioFailuresReturnIoExitCode() {
        assertEquals(98, execute(directory, "login", "--oauth-token=secret"));
        assertEquals(98, execute(directory, "logout"));
        assertEquals(98, execute(directory, "status"));
        assertEquals(98, execute(directory.resolve("missing/props"), "login", "--oauth-token=secret"));
    }
}
