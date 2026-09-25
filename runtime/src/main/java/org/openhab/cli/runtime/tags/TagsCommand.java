package org.openhab.cli.runtime.tags;

import picocli.CommandLine.Command;

/** Picocli command group for Tags operations. */
@Command(
        name = "tags",
        description = "Commands for Tags.",
        mixinStandardHelpOptions = true,
        subcommands = {
            CreateSemanticTag.class,
            SemanticTagAndSubTags.class,
            SemanticTags.class,
            RemoveSemanticTag.class,
            UpdateSemanticTag.class
        })
public class TagsCommand {}
