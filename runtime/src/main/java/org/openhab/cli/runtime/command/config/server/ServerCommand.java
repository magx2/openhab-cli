package org.openhab.cli.runtime.command.config.server;

import picocli.CommandLine;

/** Groups commands for configuring the stored openHAB server URL. */
@CommandLine.Command(
        name = "server",
        description = "Set or clear the saved openHAB server URL.",
        mixinStandardHelpOptions = true,
        subcommands = {SetServerCommand.class, ClearServerCommand.class})
public class ServerCommand {}
