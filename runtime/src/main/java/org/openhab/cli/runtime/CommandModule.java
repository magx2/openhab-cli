package org.openhab.cli.runtime;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.ClassKey;
import dagger.multibindings.IntoMap;
import java.util.concurrent.Callable;

/** Registers each operation command with the Dagger-backed Picocli factory. */
@Module
interface CommandModule {
    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.action.ExecuteThingAction.class)
    Callable<Integer> actionExecuteThingAction(org.openhab.cli.runtime.action.ExecuteThingAction command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.action.AvailableActionsForThing.class)
    Callable<Integer> actionAvailableActionsForThing(org.openhab.cli.runtime.action.AvailableActionsForThing command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.addons.AddonById.class)
    Callable<Integer> addonsAddonById(org.openhab.cli.runtime.addons.AddonById command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.addons.AddonConfiguration.class)
    Callable<Integer> addonsAddonConfiguration(org.openhab.cli.runtime.addons.AddonConfiguration command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.addons.AddonServices.class)
    Callable<Integer> addonsAddonServices(org.openhab.cli.runtime.addons.AddonServices command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.addons.AddonTypes.class)
    Callable<Integer> addonsAddonTypes(org.openhab.cli.runtime.addons.AddonTypes command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.addons.Addons.class)
    Callable<Integer> addonsAddons(org.openhab.cli.runtime.addons.Addons command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.addons.SuggestedAddons.class)
    Callable<Integer> addonsSuggestedAddons(org.openhab.cli.runtime.addons.SuggestedAddons command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.addons.InstallAddonById.class)
    Callable<Integer> addonsInstallAddonById(org.openhab.cli.runtime.addons.InstallAddonById command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.addons.InstallAddonFromURL.class)
    Callable<Integer> addonsInstallAddonFromURL(org.openhab.cli.runtime.addons.InstallAddonFromURL command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.addons.UninstallAddon.class)
    Callable<Integer> addonsUninstallAddon(org.openhab.cli.runtime.addons.UninstallAddon command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.addons.UpdateAddonConfiguration.class)
    Callable<Integer> addonsUpdateAddonConfiguration(org.openhab.cli.runtime.addons.UpdateAddonConfiguration command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.audio.AudioDefaultSink.class)
    Callable<Integer> audioAudioDefaultSink(org.openhab.cli.runtime.audio.AudioDefaultSink command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.audio.AudioDefaultSource.class)
    Callable<Integer> audioAudioDefaultSource(org.openhab.cli.runtime.audio.AudioDefaultSource command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.audio.AudioSinks.class)
    Callable<Integer> audioAudioSinks(org.openhab.cli.runtime.audio.AudioSinks command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.audio.AudioSources.class)
    Callable<Integer> audioAudioSources(org.openhab.cli.runtime.audio.AudioSources command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.auth.DeleteSession.class)
    Callable<Integer> authDeleteSession(org.openhab.cli.runtime.auth.DeleteSession command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.auth.ApiTokens.class)
    Callable<Integer> authApiTokens(org.openhab.cli.runtime.auth.ApiTokens command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.auth.OAuthToken.class)
    Callable<Integer> authOAuthToken(org.openhab.cli.runtime.auth.OAuthToken command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.auth.SessionsForCurrentUser.class)
    Callable<Integer> authSessionsForCurrentUser(org.openhab.cli.runtime.auth.SessionsForCurrentUser command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.auth.RemoveApiToken.class)
    Callable<Integer> authRemoveApiToken(org.openhab.cli.runtime.auth.RemoveApiToken command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.channeltypes.ChannelTypeByUID.class)
    Callable<Integer> channelTypesChannelTypeByUID(org.openhab.cli.runtime.channeltypes.ChannelTypeByUID command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.channeltypes.ChannelTypes.class)
    Callable<Integer> channelTypesChannelTypes(org.openhab.cli.runtime.channeltypes.ChannelTypes command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.channeltypes.LinkableItemTypesByChannelTypeUID.class)
    Callable<Integer> channelTypesLinkableItemTypesByChannelTypeUID(
            org.openhab.cli.runtime.channeltypes.LinkableItemTypesByChannelTypeUID command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.configdescriptions.ConfigDescriptionByURI.class)
    Callable<Integer> configDescriptionsConfigDescriptionByURI(
            org.openhab.cli.runtime.configdescriptions.ConfigDescriptionByURI command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.configdescriptions.ConfigDescriptions.class)
    Callable<Integer> configDescriptionsConfigDescriptions(
            org.openhab.cli.runtime.configdescriptions.ConfigDescriptions command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.discovery.BindingsWithDiscoverySupport.class)
    Callable<Integer> discoveryBindingsWithDiscoverySupport(
            org.openhab.cli.runtime.discovery.BindingsWithDiscoverySupport command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.discovery.DiscoveryServicesInfo.class)
    Callable<Integer> discoveryDiscoveryServicesInfo(org.openhab.cli.runtime.discovery.DiscoveryServicesInfo command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.discovery.Scan.class)
    Callable<Integer> discoveryScan(org.openhab.cli.runtime.discovery.Scan command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.events.Events.class)
    Callable<Integer> eventsEvents(org.openhab.cli.runtime.events.Events command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.events.InitNewStateTacker.class)
    Callable<Integer> eventsInitNewStateTacker(org.openhab.cli.runtime.events.InitNewStateTacker command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.events.UpdateItemListForStateUpdates.class)
    Callable<Integer> eventsUpdateItemListForStateUpdates(
            org.openhab.cli.runtime.events.UpdateItemListForStateUpdates command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.fileformat.CanSerializeRules.class)
    Callable<Integer> fileFormatCanSerializeRules(org.openhab.cli.runtime.fileformat.CanSerializeRules command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.fileformat.Create.class)
    Callable<Integer> fileFormatCreate(org.openhab.cli.runtime.fileformat.Create command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.fileformat.CreateFileFormatForItems.class)
    Callable<Integer> fileFormatCreateFileFormatForItems(
            org.openhab.cli.runtime.fileformat.CreateFileFormatForItems command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.fileformat.CreateFileFormatForRuleTemplates.class)
    Callable<Integer> fileFormatCreateFileFormatForRuleTemplates(
            org.openhab.cli.runtime.fileformat.CreateFileFormatForRuleTemplates command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.fileformat.CreateFileFormatForRules.class)
    Callable<Integer> fileFormatCreateFileFormatForRules(
            org.openhab.cli.runtime.fileformat.CreateFileFormatForRules command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.fileformat.CreateFileFormatForSemanticTags.class)
    Callable<Integer> fileFormatCreateFileFormatForSemanticTags(
            org.openhab.cli.runtime.fileformat.CreateFileFormatForSemanticTags command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.fileformat.CreateFileFormatForSitemaps.class)
    Callable<Integer> fileFormatCreateFileFormatForSitemaps(
            org.openhab.cli.runtime.fileformat.CreateFileFormatForSitemaps command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.fileformat.CreateFileFormatForThings.class)
    Callable<Integer> fileFormatCreateFileFormatForThings(
            org.openhab.cli.runtime.fileformat.CreateFileFormatForThings command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.fileformat.Parse.class)
    Callable<Integer> fileFormatParse(org.openhab.cli.runtime.fileformat.Parse command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.iconsets.IconSets.class)
    Callable<Integer> iconsetsIconSets(org.openhab.cli.runtime.iconsets.IconSets command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.inbox.ApproveInboxItemById.class)
    Callable<Integer> inboxApproveInboxItemById(org.openhab.cli.runtime.inbox.ApproveInboxItemById command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.inbox.FlagInboxItemAsIgnored.class)
    Callable<Integer> inboxFlagInboxItemAsIgnored(org.openhab.cli.runtime.inbox.FlagInboxItemAsIgnored command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.inbox.DiscoveredInboxItems.class)
    Callable<Integer> inboxDiscoveredInboxItems(org.openhab.cli.runtime.inbox.DiscoveredInboxItems command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.inbox.RemoveIgnoreFlagOnInboxItem.class)
    Callable<Integer> inboxRemoveIgnoreFlagOnInboxItem(
            org.openhab.cli.runtime.inbox.RemoveIgnoreFlagOnInboxItem command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.inbox.RemoveItemFromInbox.class)
    Callable<Integer> inboxRemoveItemFromInbox(org.openhab.cli.runtime.inbox.RemoveItemFromInbox command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.items.AddMemberToGroupItem.class)
    Callable<Integer> itemsAddMemberToGroupItem(org.openhab.cli.runtime.items.AddMemberToGroupItem command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.items.AddMetadataToItem.class)
    Callable<Integer> itemsAddMetadataToItem(org.openhab.cli.runtime.items.AddMetadataToItem command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.items.AddOrUpdateItemInRegistry.class)
    Callable<Integer> itemsAddOrUpdateItemInRegistry(org.openhab.cli.runtime.items.AddOrUpdateItemInRegistry command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.items.AddOrUpdateItemsInRegistry.class)
    Callable<Integer> itemsAddOrUpdateItemsInRegistry(org.openhab.cli.runtime.items.AddOrUpdateItemsInRegistry command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.items.AddTagToItem.class)
    Callable<Integer> itemsAddTagToItem(org.openhab.cli.runtime.items.AddTagToItem command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.items.ItemByName.class)
    Callable<Integer> itemsItemByName(org.openhab.cli.runtime.items.ItemByName command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.items.ItemNamespaces.class)
    Callable<Integer> itemsItemNamespaces(org.openhab.cli.runtime.items.ItemNamespaces command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.items.ItemState1.class)
    Callable<Integer> itemsItemState1(org.openhab.cli.runtime.items.ItemState1 command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.items.Items.class)
    Callable<Integer> itemsItems(org.openhab.cli.runtime.items.Items command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.items.SemanticItem.class)
    Callable<Integer> itemsSemanticItem(org.openhab.cli.runtime.items.SemanticItem command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.items.SemanticsHealth.class)
    Callable<Integer> itemsSemanticsHealth(org.openhab.cli.runtime.items.SemanticsHealth command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.items.PurgeDatabase.class)
    Callable<Integer> itemsPurgeDatabase(org.openhab.cli.runtime.items.PurgeDatabase command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.items.RemoveAllMetadataFromItem.class)
    Callable<Integer> itemsRemoveAllMetadataFromItem(org.openhab.cli.runtime.items.RemoveAllMetadataFromItem command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.items.RemoveItemFromRegistry.class)
    Callable<Integer> itemsRemoveItemFromRegistry(org.openhab.cli.runtime.items.RemoveItemFromRegistry command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.items.RemoveMemberFromGroupItem.class)
    Callable<Integer> itemsRemoveMemberFromGroupItem(org.openhab.cli.runtime.items.RemoveMemberFromGroupItem command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.items.RemoveMetadataFromItem.class)
    Callable<Integer> itemsRemoveMetadataFromItem(org.openhab.cli.runtime.items.RemoveMetadataFromItem command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.items.RemoveTagFromItem.class)
    Callable<Integer> itemsRemoveTagFromItem(org.openhab.cli.runtime.items.RemoveTagFromItem command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.items.SendItemCommand.class)
    Callable<Integer> itemsSendItemCommand(org.openhab.cli.runtime.items.SendItemCommand command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.items.UpdateItemState.class)
    Callable<Integer> itemsUpdateItemState(org.openhab.cli.runtime.items.UpdateItemState command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.links.ItemLink.class)
    Callable<Integer> linksItemLink(org.openhab.cli.runtime.links.ItemLink command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.links.ItemLinks.class)
    Callable<Integer> linksItemLinks(org.openhab.cli.runtime.links.ItemLinks command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.links.OrphanLinks.class)
    Callable<Integer> linksOrphanLinks(org.openhab.cli.runtime.links.OrphanLinks command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.links.LinkItemToChannel.class)
    Callable<Integer> linksLinkItemToChannel(org.openhab.cli.runtime.links.LinkItemToChannel command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.links.PurgeDatabase1.class)
    Callable<Integer> linksPurgeDatabase1(org.openhab.cli.runtime.links.PurgeDatabase1 command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.links.RemoveAllLinksForObject.class)
    Callable<Integer> linksRemoveAllLinksForObject(org.openhab.cli.runtime.links.RemoveAllLinksForObject command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.links.UnlinkItemFromChannel.class)
    Callable<Integer> linksUnlinkItemFromChannel(org.openhab.cli.runtime.links.UnlinkItemFromChannel command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.logging.Logger.class)
    Callable<Integer> loggingLogger(org.openhab.cli.runtime.logging.Logger command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.logging.Logger1.class)
    Callable<Integer> loggingLogger1(org.openhab.cli.runtime.logging.Logger1 command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.logging.PutLogger.class)
    Callable<Integer> loggingPutLogger(org.openhab.cli.runtime.logging.PutLogger command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.logging.RemoveLogger.class)
    Callable<Integer> loggingRemoveLogger(org.openhab.cli.runtime.logging.RemoveLogger command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.moduletypes.ModuleTypeById.class)
    Callable<Integer> moduleTypesModuleTypeById(org.openhab.cli.runtime.moduletypes.ModuleTypeById command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.moduletypes.ModuleTypes.class)
    Callable<Integer> moduleTypesModuleTypes(org.openhab.cli.runtime.moduletypes.ModuleTypes command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.persistence.DeleteItemFromPersistenceService.class)
    Callable<Integer> persistenceDeleteItemFromPersistenceService(
            org.openhab.cli.runtime.persistence.DeleteItemFromPersistenceService command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.persistence.DeletePersistenceServiceConfiguration.class)
    Callable<Integer> persistenceDeletePersistenceServiceConfiguration(
            org.openhab.cli.runtime.persistence.DeletePersistenceServiceConfiguration command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.persistence.ItemDataFromPersistenceService.class)
    Callable<Integer> persistenceItemDataFromPersistenceService(
            org.openhab.cli.runtime.persistence.ItemDataFromPersistenceService command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.persistence.ItemsForPersistenceService.class)
    Callable<Integer> persistenceItemsForPersistenceService(
            org.openhab.cli.runtime.persistence.ItemsForPersistenceService command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.persistence.PersistenceHealth.class)
    Callable<Integer> persistencePersistenceHealth(org.openhab.cli.runtime.persistence.PersistenceHealth command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.persistence.PersistenceServiceConfiguration.class)
    Callable<Integer> persistencePersistenceServiceConfiguration(
            org.openhab.cli.runtime.persistence.PersistenceServiceConfiguration command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.persistence.PersistenceServiceStrategySuggestions.class)
    Callable<Integer> persistencePersistenceServiceStrategySuggestions(
            org.openhab.cli.runtime.persistence.PersistenceServiceStrategySuggestions command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.persistence.PersistenceServices.class)
    Callable<Integer> persistencePersistenceServices(org.openhab.cli.runtime.persistence.PersistenceServices command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.persistence.PutPersistenceServiceConfiguration.class)
    Callable<Integer> persistencePutPersistenceServiceConfiguration(
            org.openhab.cli.runtime.persistence.PutPersistenceServiceConfiguration command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.persistence.StoreItemDataInPersistenceService.class)
    Callable<Integer> persistenceStoreItemDataInPersistenceService(
            org.openhab.cli.runtime.persistence.StoreItemDataInPersistenceService command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.profiletypes.ProfileTypes.class)
    Callable<Integer> profileTypesProfileTypes(org.openhab.cli.runtime.profiletypes.ProfileTypes command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.root.Root.class)
    Callable<Integer> rootRoot(org.openhab.cli.runtime.root.Root command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.rules.CreateRule.class)
    Callable<Integer> rulesCreateRule(org.openhab.cli.runtime.rules.CreateRule command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.rules.DeleteRule.class)
    Callable<Integer> rulesDeleteRule(org.openhab.cli.runtime.rules.DeleteRule command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.rules.EnableRule.class)
    Callable<Integer> rulesEnableRule(org.openhab.cli.runtime.rules.EnableRule command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.rules.RuleActions.class)
    Callable<Integer> rulesRuleActions(org.openhab.cli.runtime.rules.RuleActions command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.rules.RuleById.class)
    Callable<Integer> rulesRuleById(org.openhab.cli.runtime.rules.RuleById command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.rules.RuleConditions.class)
    Callable<Integer> rulesRuleConditions(org.openhab.cli.runtime.rules.RuleConditions command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.rules.RuleConfiguration.class)
    Callable<Integer> rulesRuleConfiguration(org.openhab.cli.runtime.rules.RuleConfiguration command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.rules.RuleModuleById.class)
    Callable<Integer> rulesRuleModuleById(org.openhab.cli.runtime.rules.RuleModuleById command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.rules.RuleModuleConfig.class)
    Callable<Integer> rulesRuleModuleConfig(org.openhab.cli.runtime.rules.RuleModuleConfig command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.rules.RuleModuleConfigParameter.class)
    Callable<Integer> rulesRuleModuleConfigParameter(org.openhab.cli.runtime.rules.RuleModuleConfigParameter command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.rules.RuleTriggers.class)
    Callable<Integer> rulesRuleTriggers(org.openhab.cli.runtime.rules.RuleTriggers command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.rules.Rules.class)
    Callable<Integer> rulesRules(org.openhab.cli.runtime.rules.Rules command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.rules.ScheduleRuleSimulations.class)
    Callable<Integer> rulesScheduleRuleSimulations(org.openhab.cli.runtime.rules.ScheduleRuleSimulations command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.rules.RegenerateRule.class)
    Callable<Integer> rulesRegenerateRule(org.openhab.cli.runtime.rules.RegenerateRule command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.rules.RunRuleNow1.class)
    Callable<Integer> rulesRunRuleNow1(org.openhab.cli.runtime.rules.RunRuleNow1 command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.rules.SetRuleModuleConfigParameter.class)
    Callable<Integer> rulesSetRuleModuleConfigParameter(
            org.openhab.cli.runtime.rules.SetRuleModuleConfigParameter command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.rules.UpdateRule.class)
    Callable<Integer> rulesUpdateRule(org.openhab.cli.runtime.rules.UpdateRule command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.rules.UpdateRuleConfiguration.class)
    Callable<Integer> rulesUpdateRuleConfiguration(org.openhab.cli.runtime.rules.UpdateRuleConfiguration command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.services.DeleteServiceConfig.class)
    Callable<Integer> servicesDeleteServiceConfig(org.openhab.cli.runtime.services.DeleteServiceConfig command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.services.ServiceConfig.class)
    Callable<Integer> servicesServiceConfig(org.openhab.cli.runtime.services.ServiceConfig command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.services.ServiceContext.class)
    Callable<Integer> servicesServiceContext(org.openhab.cli.runtime.services.ServiceContext command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.services.Services.class)
    Callable<Integer> servicesServices(org.openhab.cli.runtime.services.Services command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.services.ServicesById.class)
    Callable<Integer> servicesServicesById(org.openhab.cli.runtime.services.ServicesById command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.services.UpdateServiceConfig.class)
    Callable<Integer> servicesUpdateServiceConfig(org.openhab.cli.runtime.services.UpdateServiceConfig command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.sitemaps.AddOrUpdateSitemapInRegistry.class)
    Callable<Integer> sitemapsAddOrUpdateSitemapInRegistry(
            org.openhab.cli.runtime.sitemaps.AddOrUpdateSitemapInRegistry command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.sitemaps.CreateSitemapEventSubscription.class)
    Callable<Integer> sitemapsCreateSitemapEventSubscription(
            org.openhab.cli.runtime.sitemaps.CreateSitemapEventSubscription command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.sitemaps.SitemapByName.class)
    Callable<Integer> sitemapsSitemapByName(org.openhab.cli.runtime.sitemaps.SitemapByName command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.sitemaps.SitemapDefinitionByName.class)
    Callable<Integer> sitemapsSitemapDefinitionByName(org.openhab.cli.runtime.sitemaps.SitemapDefinitionByName command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.sitemaps.SitemapDefinitions.class)
    Callable<Integer> sitemapsSitemapDefinitions(org.openhab.cli.runtime.sitemaps.SitemapDefinitions command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.sitemaps.SitemapEvents.class)
    Callable<Integer> sitemapsSitemapEvents(org.openhab.cli.runtime.sitemaps.SitemapEvents command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.sitemaps.SitemapEvents1.class)
    Callable<Integer> sitemapsSitemapEvents1(org.openhab.cli.runtime.sitemaps.SitemapEvents1 command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.sitemaps.Sitemaps.class)
    Callable<Integer> sitemapsSitemaps(org.openhab.cli.runtime.sitemaps.Sitemaps command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.sitemaps.PollDataForPage.class)
    Callable<Integer> sitemapsPollDataForPage(org.openhab.cli.runtime.sitemaps.PollDataForPage command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.sitemaps.PollDataForSitemap.class)
    Callable<Integer> sitemapsPollDataForSitemap(org.openhab.cli.runtime.sitemaps.PollDataForSitemap command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.sitemaps.RemoveSitemapFromRegistry.class)
    Callable<Integer> sitemapsRemoveSitemapFromRegistry(
            org.openhab.cli.runtime.sitemaps.RemoveSitemapFromRegistry command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.systeminfo.SystemInformation.class)
    Callable<Integer> systemInfoSystemInformation(org.openhab.cli.runtime.systeminfo.SystemInformation command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.systeminfo.UoMInformation.class)
    Callable<Integer> systemInfoUoMInformation(org.openhab.cli.runtime.systeminfo.UoMInformation command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.tags.CreateSemanticTag.class)
    Callable<Integer> tagsCreateSemanticTag(org.openhab.cli.runtime.tags.CreateSemanticTag command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.tags.SemanticTagAndSubTags.class)
    Callable<Integer> tagsSemanticTagAndSubTags(org.openhab.cli.runtime.tags.SemanticTagAndSubTags command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.tags.SemanticTags.class)
    Callable<Integer> tagsSemanticTags(org.openhab.cli.runtime.tags.SemanticTags command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.tags.RemoveSemanticTag.class)
    Callable<Integer> tagsRemoveSemanticTag(org.openhab.cli.runtime.tags.RemoveSemanticTag command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.tags.UpdateSemanticTag.class)
    Callable<Integer> tagsUpdateSemanticTag(org.openhab.cli.runtime.tags.UpdateSemanticTag command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.templates.TemplateById.class)
    Callable<Integer> templatesTemplateById(org.openhab.cli.runtime.templates.TemplateById command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.templates.Templates.class)
    Callable<Integer> templatesTemplates(org.openhab.cli.runtime.templates.Templates command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.thingtypes.ThingTypeById.class)
    Callable<Integer> thingTypesThingTypeById(org.openhab.cli.runtime.thingtypes.ThingTypeById command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.thingtypes.ThingTypes.class)
    Callable<Integer> thingTypesThingTypes(org.openhab.cli.runtime.thingtypes.ThingTypes command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.things.CreateThingInRegistry.class)
    Callable<Integer> thingsCreateThingInRegistry(org.openhab.cli.runtime.things.CreateThingInRegistry command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.things.EnableThing.class)
    Callable<Integer> thingsEnableThing(org.openhab.cli.runtime.things.EnableThing command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.things.AvailableFirmwaresForThing.class)
    Callable<Integer> thingsAvailableFirmwaresForThing(
            org.openhab.cli.runtime.things.AvailableFirmwaresForThing command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.things.ThingById.class)
    Callable<Integer> thingsThingById(org.openhab.cli.runtime.things.ThingById command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.things.ThingConfigStatus.class)
    Callable<Integer> thingsThingConfigStatus(org.openhab.cli.runtime.things.ThingConfigStatus command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.things.ThingFirmwareStatus.class)
    Callable<Integer> thingsThingFirmwareStatus(org.openhab.cli.runtime.things.ThingFirmwareStatus command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.things.ThingStatus.class)
    Callable<Integer> thingsThingStatus(org.openhab.cli.runtime.things.ThingStatus command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.things.Things.class)
    Callable<Integer> thingsThings(org.openhab.cli.runtime.things.Things command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.things.RemoveThingById.class)
    Callable<Integer> thingsRemoveThingById(org.openhab.cli.runtime.things.RemoveThingById command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.things.UpdateThing.class)
    Callable<Integer> thingsUpdateThing(org.openhab.cli.runtime.things.UpdateThing command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.things.UpdateThingConfig.class)
    Callable<Integer> thingsUpdateThingConfig(org.openhab.cli.runtime.things.UpdateThingConfig command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.things.UpdateThingFirmware.class)
    Callable<Integer> thingsUpdateThingFirmware(org.openhab.cli.runtime.things.UpdateThingFirmware command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.transformations.DeleteTransformation.class)
    Callable<Integer> transformationsDeleteTransformation(
            org.openhab.cli.runtime.transformations.DeleteTransformation command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.transformations.Transformation.class)
    Callable<Integer> transformationsTransformation(org.openhab.cli.runtime.transformations.Transformation command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.transformations.TransformationServices.class)
    Callable<Integer> transformationsTransformationServices(
            org.openhab.cli.runtime.transformations.TransformationServices command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.transformations.Transformations.class)
    Callable<Integer> transformationsTransformations(org.openhab.cli.runtime.transformations.Transformations command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.transformations.PutTransformation.class)
    Callable<Integer> transformationsPutTransformation(
            org.openhab.cli.runtime.transformations.PutTransformation command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.ui.AddUIComponentToNamespace.class)
    Callable<Integer> uiAddUIComponentToNamespace(org.openhab.cli.runtime.ui.AddUIComponentToNamespace command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.ui.RegisteredUIComponentsInNamespace.class)
    Callable<Integer> uiRegisteredUIComponentsInNamespace(
            org.openhab.cli.runtime.ui.RegisteredUIComponentsInNamespace command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.ui.UiComponentInNamespace.class)
    Callable<Integer> uiUiComponentInNamespace(org.openhab.cli.runtime.ui.UiComponentInNamespace command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.ui.UiTiles.class)
    Callable<Integer> uiUiTiles(org.openhab.cli.runtime.ui.UiTiles command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.ui.RemoveUIComponentFromNamespace.class)
    Callable<Integer> uiRemoveUIComponentFromNamespace(
            org.openhab.cli.runtime.ui.RemoveUIComponentFromNamespace command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.ui.UpdateUIComponentInNamespace.class)
    Callable<Integer> uiUpdateUIComponentInNamespace(org.openhab.cli.runtime.ui.UpdateUIComponentInNamespace command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.uuid.Uuid.class)
    Callable<Integer> uuidUuid(org.openhab.cli.runtime.uuid.Uuid command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.voice.DeleteConversationById.class)
    Callable<Integer> voiceDeleteConversationById(org.openhab.cli.runtime.voice.DeleteConversationById command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.voice.ConversationById.class)
    Callable<Integer> voiceConversationById(org.openhab.cli.runtime.voice.ConversationById command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.voice.DefaultVoice.class)
    Callable<Integer> voiceDefaultVoice(org.openhab.cli.runtime.voice.DefaultVoice command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.voice.LlmTools.class)
    Callable<Integer> voiceLlmTools(org.openhab.cli.runtime.voice.LlmTools command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.voice.VoiceInterpreterByUID.class)
    Callable<Integer> voiceVoiceInterpreterByUID(org.openhab.cli.runtime.voice.VoiceInterpreterByUID command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.voice.VoiceInterpreters.class)
    Callable<Integer> voiceVoiceInterpreters(org.openhab.cli.runtime.voice.VoiceInterpreters command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.voice.Voices.class)
    Callable<Integer> voiceVoices(org.openhab.cli.runtime.voice.Voices command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.voice.InterpretText.class)
    Callable<Integer> voiceInterpretText(org.openhab.cli.runtime.voice.InterpretText command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.voice.InterpretTextByDefaultInterpreter.class)
    Callable<Integer> voiceInterpretTextByDefaultInterpreter(
            org.openhab.cli.runtime.voice.InterpretTextByDefaultInterpreter command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.voice.ListConversations.class)
    Callable<Integer> voiceListConversations(org.openhab.cli.runtime.voice.ListConversations command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.voice.ListenAndAnswer.class)
    Callable<Integer> voiceListenAndAnswer(org.openhab.cli.runtime.voice.ListenAndAnswer command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.voice.StartDialog.class)
    Callable<Integer> voiceStartDialog(org.openhab.cli.runtime.voice.StartDialog command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.voice.StopDialog.class)
    Callable<Integer> voiceStopDialog(org.openhab.cli.runtime.voice.StopDialog command);

    @Binds
    @IntoMap
    @ClassKey(org.openhab.cli.runtime.voice.TextToSpeech.class)
    Callable<Integer> voiceTextToSpeech(org.openhab.cli.runtime.voice.TextToSpeech command);
}
