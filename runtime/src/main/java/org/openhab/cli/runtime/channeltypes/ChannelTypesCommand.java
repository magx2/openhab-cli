package org.openhab.cli.runtime.channeltypes;

import picocli.CommandLine.Command;

/** Picocli command group for ChannelTypes operations. */
@Command(
        name = "channeltypes",
        description = "Commands for ChannelTypes.",
        mixinStandardHelpOptions = true,
        subcommands = {ChannelTypeByUID.class, ChannelTypes.class, LinkableItemTypesByChannelTypeUID.class})
public class ChannelTypesCommand {}
