package org.openhab.cli.runtime.command.profiletypes;

import picocli.CommandLine.Command;

/** Picocli command group for ProfileTypes operations. */
@Command(
        name = "profiletypes",
        description = "Commands for ProfileTypes.",
        mixinStandardHelpOptions = true,
        subcommands = {ProfileTypes.class})
public class ProfileTypesCommand {}
