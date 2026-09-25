package org.openhab.cli.runtime.command.events;

import picocli.CommandLine.Command;

/** Picocli command group for Events operations. */
@Command(
        name = "events",
        description = "Commands for Events.",
        mixinStandardHelpOptions = true,
        subcommands = {Events.class, InitNewStateTacker.class, UpdateItemListForStateUpdates.class})
public class EventsCommand {}
