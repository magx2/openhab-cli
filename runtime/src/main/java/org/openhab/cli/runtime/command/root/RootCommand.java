package org.openhab.cli.runtime.command.root;

import picocli.CommandLine.Command;

/** Picocli command group for Root operations. */
@Command(
        name = "root",
        description = "Commands for Root.",
        mixinStandardHelpOptions = true,
        subcommands = {Root.class})
public class RootCommand {}
