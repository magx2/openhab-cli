package org.openhab.cli.runtime.ui;

import picocli.CommandLine.Command;

/** Picocli command group for Ui operations. */
@Command(
        name = "ui",
        description = "Commands for Ui.",
        mixinStandardHelpOptions = true,
        subcommands = {
            AddUIComponentToNamespace.class,
            RegisteredUIComponentsInNamespace.class,
            UiComponentInNamespace.class,
            UiTiles.class,
            RemoveUIComponentFromNamespace.class,
            UpdateUIComponentInNamespace.class
        })
public class UiCommand {}
