package org.openhab.cli.runtime.configdescriptions;

import picocli.CommandLine.Command;

/** Picocli command group for ConfigDescriptions operations. */
@Command(
        name = "configdescriptions",
        description = "Commands for ConfigDescriptions.",
        mixinStandardHelpOptions = true,
        subcommands = {ConfigDescriptionByURI.class, ConfigDescriptions.class})
public class ConfigDescriptionsCommand {}
