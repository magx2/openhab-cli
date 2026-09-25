package org.openhab.cli.runtime.moduletypes;

import picocli.CommandLine.Command;

/** Picocli command group for ModuleTypes operations. */
@Command(
        name = "moduletypes",
        description = "Commands for ModuleTypes.",
        mixinStandardHelpOptions = true,
        subcommands = {ModuleTypeById.class, ModuleTypes.class})
public class ModuleTypesCommand {}
