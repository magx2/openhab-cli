package org.openhab.cli.runtime.items;

import picocli.CommandLine.Command;

/** Picocli command group for Items operations. */
@Command(
        name = "items",
        description = "Commands for Items.",
        mixinStandardHelpOptions = true,
        subcommands = {
            AddMemberToGroupItem.class,
            AddMetadataToItem.class,
            AddOrUpdateItemInRegistry.class,
            AddOrUpdateItemsInRegistry.class,
            AddTagToItem.class,
            ItemByName.class,
            ItemNamespaces.class,
            ItemState1.class,
            Items.class,
            SemanticItem.class,
            SemanticsHealth.class,
            PurgeDatabase.class,
            RemoveAllMetadataFromItem.class,
            RemoveItemFromRegistry.class,
            RemoveMemberFromGroupItem.class,
            RemoveMetadataFromItem.class,
            RemoveTagFromItem.class,
            SendItemCommand.class,
            UpdateItemState.class
        })
public class ItemsCommand {}
