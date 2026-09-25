package org.openhab.cli.runtime.inbox;

import picocli.CommandLine.Command;

/** Picocli command group for Inbox operations. */
@Command(
        name = "inbox",
        description = "Commands for Inbox.",
        mixinStandardHelpOptions = true,
        subcommands = {
            ApproveInboxItemById.class,
            FlagInboxItemAsIgnored.class,
            DiscoveredInboxItems.class,
            RemoveIgnoreFlagOnInboxItem.class,
            RemoveItemFromInbox.class
        })
public class InboxCommand {}
