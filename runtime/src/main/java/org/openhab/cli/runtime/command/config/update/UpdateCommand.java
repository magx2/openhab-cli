package org.openhab.cli.runtime.command.config.update;

import static org.openhab.cli.runtime.ExitCodeMapper.IO_EXCEPTION_EXIT_CODE;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.concurrent.Callable;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Checks for or installs an openHAB CLI release matching the running installation. */
@Slf4j
@CommandLine.Command(
        name = "update",
        description = "Check for or install an openHAB CLI update from GitHub Releases.",
        mixinStandardHelpOptions = true)
public class UpdateCommand implements Callable<Integer> {
    @CommandLine.Parameters(
            index = "0",
            arity = "0..1",
            defaultValue = "check",
            paramLabel = "<action>",
            description =
                    "check reports updates; run installs them. Valid values: ${COMPLETION-CANDIDATES} (case-insensitive).")
    private Action action;

    @CommandLine.Option(
            names = "--release",
            paramLabel = "<version>",
            description = "Select a release such as 0.1.0 or v0.1.0; defaults to the latest stable release.")
    private String release;

    @CommandLine.Option(
            names = "--force",
            description = "Reinstall the selected release even if this version or a newer version is installed.")
    private boolean force;

    private final SelfUpdater updater;
    private final Console console;

    /** Creates the command with the release update service and console output. */
    @Inject
    public UpdateCommand(SelfUpdater updater, Console console) {
        this.updater = updater;
        this.console = console;
    }

    /** Available update actions; checking never downloads or replaces the installation. */
    public enum Action {
        check,
        run
    }

    /**
     * Executes the selected action and logs failures before reporting them to the user.
     *
     * @return zero on success, 98 for I/O failures, or 1 for interruption and other failures
     */
    @Override
    public Integer call() {
        try {
            updater.execute(action == Action.check, release, force);
            return 0;
        } catch (IOException | UncheckedIOException e) {
            return failure("Cannot update CLI", e, IO_EXCEPTION_EXIT_CODE);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return failure("CLI update interrupted", e, 1);
        } catch (RuntimeException e) {
            return failure("Cannot update CLI", e, 1);
        }
    }

    private int failure(String message, Exception exception, int exitCode) {
        log.error(message, exception);
        console.writeError("%s: %s", exception, message, exception.getMessage());
        return exitCode;
    }
}
