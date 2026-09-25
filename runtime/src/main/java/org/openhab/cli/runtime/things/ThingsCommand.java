package org.openhab.cli.runtime.things;

import picocli.CommandLine.Command;

/** Picocli command group for Things operations. */
@Command(
        name = "things",
        description = "Commands for Things.",
        mixinStandardHelpOptions = true,
        subcommands = {
            CreateThingInRegistry.class,
            EnableThing.class,
            AvailableFirmwaresForThing.class,
            ThingById.class,
            ThingConfigStatus.class,
            ThingFirmwareStatus.class,
            ThingStatus.class,
            Things.class,
            RemoveThingById.class,
            UpdateThing.class,
            UpdateThingConfig.class,
            UpdateThingFirmware.class
        })
public class ThingsCommand {}
