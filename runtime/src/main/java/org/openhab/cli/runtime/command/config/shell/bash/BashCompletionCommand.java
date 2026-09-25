package org.openhab.cli.runtime.command.config.shell.bash;

import static org.openhab.cli.runtime.ExitCodeMapper.IO_EXCEPTION_EXIT_CODE;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.Callable;
import javax.inject.Inject;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Prints or installs Bash completions for the complete oh command tree. */
@CommandLine.Command(
        name = "completion",
        description = "Print Bash completions or install them in the user's Bash completion directory.",
        mixinStandardHelpOptions = true)
public class BashCompletionCommand implements Callable<Integer> {
    @CommandLine.Parameters(
            index = "0",
            arity = "0..1",
            defaultValue = "show",
            paramLabel = "<action>",
            description =
                    "Valid values: ${COMPLETION-CANDIDATES} (case-insensitive). show prints to stdout (default); install writes oh.bash to the bash-completion user directory ($BASH_COMPLETION_USER_DIR, $XDG_DATA_HOME/bash-completion, or ~/.local/share/bash-completion).")
    private Action action;

    private final Console console;

    @CommandLine.Spec
    private CommandLine.Model.CommandSpec spec;

    /** Creates the command with its output service. */
    @Inject
    public BashCompletionCommand(Console console) {
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
        return BashCompletions.generate(spec.root());
    }

    private int show() {
        console.write(buildCompletions());
        return 0;
    }

    private int install() {
        var completions = buildCompletions();
        var home = System.getenv("HOME");
        if (home == null || home.isBlank()) home = System.getProperty("user.home");
        var target = completionFile(System.getenv("BASH_COMPLETION_USER_DIR"), System.getenv("XDG_DATA_HOME"), home);
        try {
            writeCompletions(target, completions);
        } catch (IOException e) {
            console.writeError("Cannot install Bash completions: %s", e, e.getMessage());
            return IO_EXCEPTION_EXIT_CODE;
        }
        console.write("Installed Bash completions in " + target
                + ". Enable bash-completion or source this file to load them.");
        return 0;
    }

    static Path completionFile(String completionHome, String dataHome, String userHome) {
        if (completionHome != null) {
            for (var entry : completionHome.split(java.util.regex.Pattern.quote(java.io.File.pathSeparator))) {
                if (!entry.isBlank() && Path.of(entry).isAbsolute()) {
                    return Path.of(entry).resolve("completions/oh.bash");
                }
            }
        }
        var base = dataHome == null || dataHome.isBlank() ? null : Path.of(dataHome);
        if (base == null || !base.isAbsolute()) base = Path.of(userHome, ".local", "share");
        return base.resolve("bash-completion/completions/oh.bash");
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
