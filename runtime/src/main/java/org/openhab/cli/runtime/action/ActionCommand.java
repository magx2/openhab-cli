package org.openhab.cli.runtime.action;

import picocli.CommandLine.Command;

/** Picocli command group for Action operations. */
@Command(
        name = "action",
        description = "Commands for Action.",
        mixinStandardHelpOptions = true,
        subcommands = {ExecuteThingAction.class, AvailableActionsForThing.class})
public class ActionCommand {}
