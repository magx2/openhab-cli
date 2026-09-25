package org.openhab.cli.runtime.command.config.shell.bash;

import picocli.CommandLine;

/** Groups Bash shell integration commands. */
@CommandLine.Command(
        name = "bash",
        description = "Configure Bash shell integration.",
        mixinStandardHelpOptions = true,
        subcommands = {BashCompletionCommand.class})
public class BashCommand {}
