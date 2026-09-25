package org.openhab.cli.runtime.systeminfo;

import picocli.CommandLine.Command;

/** Picocli command group for SystemInfo operations. */
@Command(
        name = "systeminfo",
        description = "Commands for SystemInfo.",
        mixinStandardHelpOptions = true,
        subcommands = {SystemInformation.class, UoMInformation.class})
public class SystemInfoCommand {}
