package org.openhab.cli.runtime.logging;

import picocli.CommandLine.Command;

/** Picocli command group for Logging operations. */
@Command(
        name = "logging",
        description = "Commands for Logging.",
        mixinStandardHelpOptions = true,
        subcommands = {Logger.class, Logger1.class, PutLogger.class, RemoveLogger.class})
public class LoggingCommand {}
