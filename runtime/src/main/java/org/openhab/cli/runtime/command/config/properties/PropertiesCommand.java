package org.openhab.cli.runtime.command.config.properties;

import picocli.CommandLine;

/** Groups commands for reading, setting and clearing properties in the CLI configuration file. */
@CommandLine.Command(
        name = "properties",
        description = "Read, set or clear properties in the CLI configuration file.",
        mixinStandardHelpOptions = true,
        subcommands = {SetCommand.class, GetCommand.class, ClearCommand.class, ListCommand.class})
public class PropertiesCommand {}
