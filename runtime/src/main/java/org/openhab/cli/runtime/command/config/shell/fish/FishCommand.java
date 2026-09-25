package org.openhab.cli.runtime.command.config.shell.fish;

import picocli.CommandLine;

/** Groups Fish shell integration commands. */
@CommandLine.Command(
        name = "fish",
        description = "Configure Fish shell integration.",
        mixinStandardHelpOptions = true,
        subcommands = {FishCompletionCommand.class})
public class FishCommand {}
