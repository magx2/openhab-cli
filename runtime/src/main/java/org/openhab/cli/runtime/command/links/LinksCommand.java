package org.openhab.cli.runtime.command.links;

import picocli.CommandLine.Command;

/** Picocli command group for Links operations. */
@Command(
        name = "links",
        description = "Commands for Links.",
        mixinStandardHelpOptions = true,
        subcommands = {
            ItemLink.class,
            ItemLinks.class,
            OrphanLinks.class,
            LinkItemToChannel.class,
            PurgeDatabase1.class,
            RemoveAllLinksForObject.class,
            UnlinkItemFromChannel.class
        })
public class LinksCommand {}
