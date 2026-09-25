package org.openhab.cli.runtime.persistence;

import picocli.CommandLine.Command;

/** Picocli command group for Persistence operations. */
@Command(
        name = "persistence",
        description = "Commands for Persistence.",
        mixinStandardHelpOptions = true,
        subcommands = {
            DeleteItemFromPersistenceService.class,
            DeletePersistenceServiceConfiguration.class,
            ItemDataFromPersistenceService.class,
            ItemsForPersistenceService.class,
            PersistenceHealth.class,
            PersistenceServiceConfiguration.class,
            PersistenceServiceStrategySuggestions.class,
            PersistenceServices.class,
            PutPersistenceServiceConfiguration.class,
            StoreItemDataInPersistenceService.class
        })
public class PersistenceCommand {}
