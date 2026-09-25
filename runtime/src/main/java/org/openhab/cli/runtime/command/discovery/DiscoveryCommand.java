package org.openhab.cli.runtime.command.discovery;

import picocli.CommandLine.Command;

/** Picocli command group for Discovery operations. */
@Command(
        name = "discovery",
        description = "Commands for Discovery.",
        mixinStandardHelpOptions = true,
        subcommands = {BindingsWithDiscoverySupport.class, DiscoveryServicesInfo.class, Scan.class})
public class DiscoveryCommand {}
