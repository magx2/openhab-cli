package org.openhab.cli.runtime.command.config.shell.bash;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.ArgumentCaptor;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

class BashCompletionCommandTest {
    @TempDir
    Path directory;

    @CommandLine.Command(name = "oh", mixinStandardHelpOptions = true)
    static class Root {}

    @CommandLine.Command(name = "availableActionsForThing", mixinStandardHelpOptions = true)
    static class Leaf {
        @CommandLine.Mixin
        Options options;
    }

    private CommandLine commandLine(Console console) {
        var root = new CommandLine(new Root());
        root.addSubcommand(
                "action",
                new CommandLine(CommandLine.Model.CommandSpec.create().name("action"))
                        .addSubcommand("availableActionsForThing", new Leaf()));
        root.addSubcommand("completion", new BashCompletionCommand(console));
        return root.setCaseInsensitiveEnumValuesAllowed(true);
    }

    @Test
    void printsTheSameScriptForDefaultAndCaseInsensitiveShowActions() {
        var console = mock(Console.class);
        var root = commandLine(console);
        assertEquals(0, root.execute("completion"));
        assertEquals(0, root.execute("completion", "ShOw"));
        var scripts = ArgumentCaptor.forClass(String.class);
        verify(console, times(2)).write(scripts.capture());
        assertEquals(scripts.getAllValues().getFirst(), scripts.getAllValues().getLast());
        assertTrue(scripts.getValue().contains("_complete_oh"));
        assertFalse(scripts.getValue().contains("\r"));
        verifyNoMoreInteractions(console);
    }

    @Test
    void resolvesCompletionDirectoryWithCustomAndXdgOverrides() {
        var home = directory.resolve("home");
        var custom = directory.resolve("custom");
        var xdg = directory.resolve("xdg");
        assertEquals(
                custom.resolve("completions/oh.bash"),
                BashCompletionCommand.completionFile(
                        custom + java.io.File.pathSeparator + xdg, xdg.toString(), home.toString()));
        assertEquals(
                xdg.resolve("bash-completion/completions/oh.bash"),
                BashCompletionCommand.completionFile(null, xdg.toString(), home.toString()));
        var fallback = home.resolve(".local/share/bash-completion/completions/oh.bash");
        assertEquals(fallback, BashCompletionCommand.completionFile(null, null, home.toString()));
        assertEquals(fallback, BashCompletionCommand.completionFile("relative", "relative", home.toString()));
    }

    @Test
    void installsAndReplacesFilesAndReportsUnwritablePaths() throws IOException {
        var file = directory.resolve("new/completions/oh.bash");
        BashCompletionCommand.writeCompletions(file, "# old\n");
        BashCompletionCommand.writeCompletions(file, "# new – script\n");
        assertEquals("# new – script\n", Files.readString(file));
        assertThrows(
                IOException.class, () -> BashCompletionCommand.writeCompletions(file.resolve("oh.bash"), "script"));
    }

    @Test
    void generatedScriptCompletesCommandsOptionsAndLowercaseEnumsInBash() throws Exception {
        try {
            Assumptions.assumeTrue(
                    new ProcessBuilder("bash", "--version").start().waitFor() == 0);
        } catch (IOException e) {
            Assumptions.abort("Bash is unavailable");
        }
        var root = commandLine(mock(Console.class));
        var script = directory.resolve("oh.bash");
        Files.writeString(script, BashCompletions.generate(root.getCommandSpec()));
        assertEquals(Set.of("action", "completion"), complete(script, "oh", ""));
        assertEquals(Set.of("availableActionsForThing"), complete(script, "oh", "action", ""));
        assertEquals(Set.of("show", "install"), complete(script, "oh", "completion", ""));
        assertTrue(complete(script, "oh", "action", "availableActionsForThing", "--base")
                .contains("--base-url"));
        assertTrue(complete(script, "oh", "action", "availableActionsForThing", "--no-")
                .contains("--no-pretty-print"));
        // Generating completions must leave the parser's negated options intact.
        assertDoesNotThrow(() -> root.parseArgs("action", "availableActionsForThing", "--no-pretty-print"));
    }

    private Set<String> complete(Path script, String... words) throws Exception {
        var args = new ArrayList<>(
                java.util.List.of("bash", "--noprofile", "--norc", "-c", """
                source "$1"
                shift
                COMP_WORDS=("$@")
                COMP_CWORD=$((${#COMP_WORDS[@]} - 1))
                COMP_LINE="${COMP_WORDS[*]}"
                COMP_POINT=${#COMP_LINE}
                _complete_oh
                printf '%s\\n' "${COMPREPLY[@]}"
                """, "bash", script.toString()));
        args.addAll(Arrays.asList(words));
        var output = directory.resolve("bash-output.txt");
        var process = new ProcessBuilder(args)
                .redirectErrorStream(true)
                .redirectOutput(output.toFile())
                .start();
        assertTrue(process.waitFor(15, TimeUnit.SECONDS));
        var text = Files.readString(output);
        assertEquals(0, process.exitValue(), text);
        return text.lines().filter(line -> !line.isEmpty()).collect(Collectors.toSet());
    }
}
