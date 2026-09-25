package org.openhab.cli.runtime.command.templates;

import picocli.CommandLine.Command;

/** Picocli command group for Templates operations. */
@Command(
        name = "templates",
        description = "Commands for Templates.",
        mixinStandardHelpOptions = true,
        subcommands = {TemplateById.class, Templates.class})
public class TemplatesCommand {}
