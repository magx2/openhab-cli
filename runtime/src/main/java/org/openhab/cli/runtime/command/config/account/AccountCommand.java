package org.openhab.cli.runtime.command.config.account;

import picocli.CommandLine;

/** Groups commands for managing credentials saved in the CLI properties file. */
@CommandLine.Command(
        name = "account",
        description = "Manage saved login credentials (no server authentication check is performed).",
        mixinStandardHelpOptions = true,
        subcommands = {StatusCommand.class, LoginCommand.class, LogoutCommand.class})
public class AccountCommand {}
