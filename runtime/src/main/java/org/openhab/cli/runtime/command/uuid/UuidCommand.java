package org.openhab.cli.runtime.command.uuid;

import picocli.CommandLine.Command;

/** Picocli command group for Uuid operations. */
@Command(
        name = "uuid",
        description = "Commands for Uuid.",
        mixinStandardHelpOptions = true,
        subcommands = {Uuid.class})
public class UuidCommand {}
