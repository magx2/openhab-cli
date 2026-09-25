package org.openhab.cli.runtime.command.services;

import picocli.CommandLine.Command;

/** Picocli command group for Services operations. */
@Command(
        name = "services",
        description = "Commands for Services.",
        mixinStandardHelpOptions = true,
        subcommands = {
            DeleteServiceConfig.class,
            ServiceConfig.class,
            ServiceContext.class,
            Services.class,
            ServicesById.class,
            UpdateServiceConfig.class
        })
public class ServicesCommand {}
