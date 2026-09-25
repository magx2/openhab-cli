package org.openhab.cli.runtime.iconsets;

import picocli.CommandLine.Command;

/** Picocli command group for Iconsets operations. */
@Command(
        name = "iconsets",
        description = "Commands for Iconsets.",
        mixinStandardHelpOptions = true,
        subcommands = {IconSets.class})
public class IconsetsCommand {}
