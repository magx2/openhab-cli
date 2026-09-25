package org.openhab.cli.runtime.command.config.shell.fish;

import static org.openhab.cli.runtime.ExitCodeMapper.IO_EXCEPTION_EXIT_CODE;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.Callable;
import javax.inject.Inject;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Prints or installs Fish completions for the complete oh command tree. */
@CommandLine.Command(
        name = "completion",
        description = "Print Fish completions or install them in the user's Fish completion directory.",
        mixinStandardHelpOptions = true)
public class FishCompletionCommand implements Callable<Integer> {
    @CommandLine.Parameters(
            index = "0",
            arity = "0..1",
            defaultValue = "show",
            paramLabel = "<action>",
            description =
                    "Valid values: ${COMPLETION-CANDIDATES} (case-insensitive). show prints to stdout (default); install writes oh.fish to $XDG_CONFIG_HOME/fish/completions or ~/.config/fish/completions.")
    private Action action;

    private final Console console;

    @CommandLine.Spec
    private CommandLine.Model.CommandSpec spec;

    /** Creates the command with its output service. */
    @Inject
    public FishCompletionCommand(Console console) {
        this.console = console;
    }

    /** Prints or installs completions, returning the I/O exit code if installation fails. */
    @Override
    public Integer call() throws Exception {
        return switch (action) {
            case show -> show();
            case install -> install();
        };
    }

    private String buildCompletions() {
        return FishCompletions.generate(spec.root());
    }

    private int show() {
        console.write(buildCompletions());
        return 0;
    }

    private int install() {
        var completions = buildCompletions();
        var home = System.getenv("HOME");
        if (home == null || home.isBlank()) home = System.getProperty("user.home");
        var target = completionFile(System.getenv("XDG_CONFIG_HOME"), home);
        try {
            writeCompletions(target, completions);
        } catch (IOException e) {
            console.writeError("Cannot install Fish completions: %s", e, e.getMessage());
            return IO_EXCEPTION_EXIT_CODE;
        }
        console.write("Installed Fish completions in " + target);
        return 0;
    }

    static Path completionFile(String configHome, String userHome) {
        var base = configHome == null || configHome.isBlank() ? null : Path.of(configHome);
        if (base == null || !base.isAbsolute()) base = Path.of(userHome, ".config");
        return base.resolve("fish/completions/oh.fish");
    }

    static void writeCompletions(Path target, String completions) throws IOException {
        Files.createDirectories(target.getParent());
        Files.writeString(target, completions, StandardCharsets.UTF_8);
    }

    /** Supported completion output destinations. */
    public enum Action {
        show,
        install
    }
}
