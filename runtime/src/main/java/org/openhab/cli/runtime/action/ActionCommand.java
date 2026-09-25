package org.openhab.cli.runtime.action;

import picocli.CommandLine.Command;

@Command(
        name = "action",
        subcommands = {
            AvailableActionsForThing.class,
        })
public class ActionCommand {}
