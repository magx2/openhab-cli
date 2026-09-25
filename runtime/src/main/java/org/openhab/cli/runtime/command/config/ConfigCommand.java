package org.openhab.cli.runtime.command.config;

import org.openhab.cli.runtime.command.config.properties.PropertiesCommand;
import picocli.CommandLine;

/** Groups commands for managing local openHAB CLI configuration. */
@CommandLine.Command(
        name = "_config",
        description = "Manage local openHAB CLI configuration.",
        mixinStandardHelpOptions = true,
        subcommands = {PropertiesCommand.class})
public class ConfigCommand {}
