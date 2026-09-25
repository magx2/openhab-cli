package org.openhab.cli.runtime.command.config.shell;

import org.openhab.cli.runtime.command.config.shell.bash.BashCommand;
import org.openhab.cli.runtime.command.config.shell.fish.FishCommand;
import picocli.CommandLine;

/** Groups commands that integrate oh with interactive shells. */
@CommandLine.Command(
        name = "shell",
        description = "Configure shell integration.",
        mixinStandardHelpOptions = true,
        subcommands = {FishCommand.class, BashCommand.class})
public class ShellCommand {}
