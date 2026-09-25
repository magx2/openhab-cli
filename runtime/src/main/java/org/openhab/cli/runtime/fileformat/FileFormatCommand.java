package org.openhab.cli.runtime.fileformat;

import picocli.CommandLine.Command;

/** Picocli command group for FileFormat operations. */
@Command(
        name = "fileformat",
        description = "Commands for FileFormat.",
        mixinStandardHelpOptions = true,
        subcommands = {
            CanSerializeRules.class,
            Create.class,
            CreateFileFormatForItems.class,
            CreateFileFormatForRuleTemplates.class,
            CreateFileFormatForRules.class,
            CreateFileFormatForSemanticTags.class,
            CreateFileFormatForSitemaps.class,
            CreateFileFormatForThings.class,
            Parse.class
        })
public class FileFormatCommand {}
