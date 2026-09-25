package org.openhab.cli.runtime.command.addons;

import picocli.CommandLine.Command;

/** Picocli command group for Addons operations. */
@Command(
        name = "addons",
        description = "Commands for Addons.",
        mixinStandardHelpOptions = true,
        subcommands = {
            AddonById.class,
            AddonConfiguration.class,
            AddonServices.class,
            AddonTypes.class,
            Addons.class,
            SuggestedAddons.class,
            InstallAddonById.class,
            InstallAddonFromURL.class,
            UninstallAddon.class,
            UpdateAddonConfiguration.class
        })
public class AddonsCommand {}
