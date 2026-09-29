package org.openhab.cli.runtime.command.config;

import org.openhab.cli.runtime.command.config.account.AccountCommand;
import org.openhab.cli.runtime.command.config.properties.PropertiesCommand;
import org.openhab.cli.runtime.command.config.server.ServerCommand;
import org.openhab.cli.runtime.command.config.shell.ShellCommand;
import org.openhab.cli.runtime.command.config.update.UpdateCommand;
import picocli.CommandLine;

/** Groups commands for managing local openHAB CLI configuration. */
@CommandLine.Command(
        name = "_config",
        description = "Manage local openHAB CLI configuration.",
        mixinStandardHelpOptions = true,
        subcommands = {
            PropertiesCommand.class,
            ShellCommand.class,
            UpdateCommand.class,
            AccountCommand.class,
            ServerCommand.class
        })
public class ConfigCommand {}
