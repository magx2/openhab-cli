package org.openhab.cli.runtime;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UncheckedIOException;
import java.nio.file.Path;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.openhab.cli.client.ApiException;
import org.openhab.cli.engine.endpoint.Action;
import org.openhab.cli.engine.endpoint.EndpointException;
import org.openhab.cli.runtime.action.AvailableActionsForThing;

class CommandFactoryTest {
    @Test
    void injectedCommandAcceptsPicocliMixinAndPositionalArguments() {
        var command = Cli.commandLine();

        var parsed = command.parseArgs(
                        "action",
                        "availableActionsForThing",
                        "-t=test-token",
                        "--pretty-print=false",
                        "--",
                        "thing:test",
                        "en")
                .subcommand()
                .subcommand();

        assertInstanceOf(AvailableActionsForThing.class, parsed.commandSpec().userObject());
        assertEquals("thing:test", parsed.matchedPositionalValue(0, ""));
        assertEquals("en", parsed.matchedPositionalValue(1, ""));
        var options = (Options) parsed.commandSpec().mixins().get("options").userObject();
        assertEquals("test-token", options.getOAuthToken());
        assertFalse(options.isPrettyPrint());
    }

    @Test
    void picocliCanInitializeMixinDefaultsWithoutAnyOptionArguments() {
        var command = Cli.commandLine();
        var parsed = command.parseArgs("action", "availableActionsForThing", "thing:test")
                .subcommand()
                .subcommand();

        var options = (Options) parsed.commandSpec().mixins().get("options").userObject();
        assertNull(options.getOAuthToken());
        assertTrue(options.isPrettyPrint());
        assertEquals("thing:test", parsed.matchedPositionalValue(0, ""));
    }

    @Test
    void factoryCreatesFreshCommandsAndFallsBackForUnmanagedMixins() throws Exception {
        var factory = DaggerRuntimeComponent.create().commandFactory();
        assertNotSame(factory.create(AvailableActionsForThing.class), factory.create(AvailableActionsForThing.class));
        assertNotSame(factory.create(Options.class), factory.create(Options.class));
    }

    @Test
    void injectedServicesMapPropertiesReadFailuresToExitCode98(@TempDir Path directory) {
        var command = Cli.commandLine();
        var errors = new StringWriter();
        command.setErr(new PrintWriter(errors));

        // A directory cannot be loaded as a properties file; this fails before any HTTP call.
        int exitCode = command.execute(
                "action", "availableActionsForThing", "--properties-file=" + directory, "--", "thing:test");

        assertEquals(98, exitCode);
        assertTrue(errors.toString().contains("Cannot read properties"));
    }

    @Test
    void injectedExitCodeMapperPreservesApplicationErrorCodes() {
        var mapper = Cli.commandLine().getExitCodeExceptionMapper();
        assertEquals(
                99,
                mapper.getExitCode(
                        new EndpointException(Action.class, "test", Map.of(), new ApiException(503, "unavailable"))));
        assertEquals(98, mapper.getExitCode(new UncheckedIOException(new IOException("unreadable"))));
        assertEquals(1, mapper.getExitCode(new IllegalStateException("unexpected")));
    }
}
