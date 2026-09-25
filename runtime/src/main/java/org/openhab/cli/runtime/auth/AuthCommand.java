package org.openhab.cli.runtime.auth;

import picocli.CommandLine.Command;

/** Picocli command group for Auth operations. */
@Command(
        name = "auth",
        description = "Commands for Auth.",
        mixinStandardHelpOptions = true,
        subcommands = {
            DeleteSession.class,
            ApiTokens.class,
            OAuthToken.class,
            SessionsForCurrentUser.class,
            RemoveApiToken.class
        })
public class AuthCommand {}
