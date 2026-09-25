package org.openhab.cli.runtime.thingtypes;

import picocli.CommandLine.Command;

/** Picocli command group for ThingTypes operations. */
@Command(
        name = "thingtypes",
        description = "Commands for ThingTypes.",
        mixinStandardHelpOptions = true,
        subcommands = {ThingTypeById.class, ThingTypes.class})
public class ThingTypesCommand {}
