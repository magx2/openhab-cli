package org.openhab.cli.runtime;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.ClassKey;
import dagger.multibindings.IntoMap;
import java.util.concurrent.Callable;
import org.openhab.cli.runtime.command.action.AvailableActionsForThing;
import org.openhab.cli.runtime.command.action.ExecuteThingAction;
import org.openhab.cli.runtime.command.addons.*;
import org.openhab.cli.runtime.command.audio.AudioDefaultSink;
import org.openhab.cli.runtime.command.audio.AudioDefaultSource;
import org.openhab.cli.runtime.command.audio.AudioSinks;
import org.openhab.cli.runtime.command.audio.AudioSources;
import org.openhab.cli.runtime.command.auth.*;
import org.openhab.cli.runtime.command.channeltypes.ChannelTypeByUID;
import org.openhab.cli.runtime.command.channeltypes.ChannelTypes;
import org.openhab.cli.runtime.command.channeltypes.LinkableItemTypesByChannelTypeUID;
import org.openhab.cli.runtime.command.config.properties.ClearCommand;
import org.openhab.cli.runtime.command.config.properties.GetCommand;
import org.openhab.cli.runtime.command.config.properties.SetCommand;
import org.openhab.cli.runtime.command.configdescriptions.ConfigDescriptionByURI;
import org.openhab.cli.runtime.command.configdescriptions.ConfigDescriptions;
import org.openhab.cli.runtime.command.discovery.BindingsWithDiscoverySupport;
import org.openhab.cli.runtime.command.discovery.DiscoveryServicesInfo;
import org.openhab.cli.runtime.command.discovery.Scan;
import org.openhab.cli.runtime.command.events.Events;
import org.openhab.cli.runtime.command.events.InitNewStateTacker;
import org.openhab.cli.runtime.command.events.UpdateItemListForStateUpdates;
import org.openhab.cli.runtime.command.fileformat.*;
import org.openhab.cli.runtime.command.iconsets.IconSets;
import org.openhab.cli.runtime.command.inbox.*;
import org.openhab.cli.runtime.command.items.*;
import org.openhab.cli.runtime.command.links.*;
import org.openhab.cli.runtime.command.logging.Logger;
import org.openhab.cli.runtime.command.logging.Logger1;
import org.openhab.cli.runtime.command.logging.PutLogger;
import org.openhab.cli.runtime.command.logging.RemoveLogger;
import org.openhab.cli.runtime.command.moduletypes.ModuleTypeById;
import org.openhab.cli.runtime.command.moduletypes.ModuleTypes;
import org.openhab.cli.runtime.command.persistence.*;
import org.openhab.cli.runtime.command.profiletypes.ProfileTypes;
import org.openhab.cli.runtime.command.root.Root;
import org.openhab.cli.runtime.command.rules.*;
import org.openhab.cli.runtime.command.services.*;
import org.openhab.cli.runtime.command.sitemaps.*;
import org.openhab.cli.runtime.command.systeminfo.SystemInformation;
import org.openhab.cli.runtime.command.systeminfo.UoMInformation;
import org.openhab.cli.runtime.command.tags.*;
import org.openhab.cli.runtime.command.templates.TemplateById;
import org.openhab.cli.runtime.command.templates.Templates;
import org.openhab.cli.runtime.command.things.*;
import org.openhab.cli.runtime.command.thingtypes.ThingTypeById;
import org.openhab.cli.runtime.command.thingtypes.ThingTypes;
import org.openhab.cli.runtime.command.transformations.*;
import org.openhab.cli.runtime.command.ui.*;
import org.openhab.cli.runtime.command.uuid.Uuid;
import org.openhab.cli.runtime.command.voice.*;

/** Registers each operation command with the Dagger-backed Picocli factory. */
@Module
interface CommandModule {
    @Binds
    @IntoMap
    @ClassKey(ExecuteThingAction.class)
    Callable<Integer> actionExecuteThingAction(ExecuteThingAction command);

    @Binds
    @IntoMap
    @ClassKey(AvailableActionsForThing.class)
    Callable<Integer> actionAvailableActionsForThing(AvailableActionsForThing command);

    @Binds
    @IntoMap
    @ClassKey(AddonById.class)
    Callable<Integer> addonsAddonById(AddonById command);

    @Binds
    @IntoMap
    @ClassKey(AddonConfiguration.class)
    Callable<Integer> addonsAddonConfiguration(AddonConfiguration command);

    @Binds
    @IntoMap
    @ClassKey(AddonServices.class)
    Callable<Integer> addonsAddonServices(AddonServices command);

    @Binds
    @IntoMap
    @ClassKey(AddonTypes.class)
    Callable<Integer> addonsAddonTypes(AddonTypes command);

    @Binds
    @IntoMap
    @ClassKey(Addons.class)
    Callable<Integer> addonsAddons(Addons command);

    @Binds
    @IntoMap
    @ClassKey(SuggestedAddons.class)
    Callable<Integer> addonsSuggestedAddons(SuggestedAddons command);

    @Binds
    @IntoMap
    @ClassKey(InstallAddonById.class)
    Callable<Integer> addonsInstallAddonById(InstallAddonById command);

    @Binds
    @IntoMap
    @ClassKey(InstallAddonFromURL.class)
    Callable<Integer> addonsInstallAddonFromURL(InstallAddonFromURL command);

    @Binds
    @IntoMap
    @ClassKey(UninstallAddon.class)
    Callable<Integer> addonsUninstallAddon(UninstallAddon command);

    @Binds
    @IntoMap
    @ClassKey(UpdateAddonConfiguration.class)
    Callable<Integer> addonsUpdateAddonConfiguration(UpdateAddonConfiguration command);

    @Binds
    @IntoMap
    @ClassKey(AudioDefaultSink.class)
    Callable<Integer> audioAudioDefaultSink(AudioDefaultSink command);

    @Binds
    @IntoMap
    @ClassKey(AudioDefaultSource.class)
    Callable<Integer> audioAudioDefaultSource(AudioDefaultSource command);

    @Binds
    @IntoMap
    @ClassKey(AudioSinks.class)
    Callable<Integer> audioAudioSinks(AudioSinks command);

    @Binds
    @IntoMap
    @ClassKey(AudioSources.class)
    Callable<Integer> audioAudioSources(AudioSources command);

    @Binds
    @IntoMap
    @ClassKey(DeleteSession.class)
    Callable<Integer> authDeleteSession(DeleteSession command);

    @Binds
    @IntoMap
    @ClassKey(ApiTokens.class)
    Callable<Integer> authApiTokens(ApiTokens command);

    @Binds
    @IntoMap
    @ClassKey(OAuthToken.class)
    Callable<Integer> authOAuthToken(OAuthToken command);

    @Binds
    @IntoMap
    @ClassKey(SessionsForCurrentUser.class)
    Callable<Integer> authSessionsForCurrentUser(SessionsForCurrentUser command);

    @Binds
    @IntoMap
    @ClassKey(RemoveApiToken.class)
    Callable<Integer> authRemoveApiToken(RemoveApiToken command);

    @Binds
    @IntoMap
    @ClassKey(ChannelTypeByUID.class)
    Callable<Integer> channelTypesChannelTypeByUID(ChannelTypeByUID command);

    @Binds
    @IntoMap
    @ClassKey(ChannelTypes.class)
    Callable<Integer> channelTypesChannelTypes(ChannelTypes command);

    @Binds
    @IntoMap
    @ClassKey(LinkableItemTypesByChannelTypeUID.class)
    Callable<Integer> channelTypesLinkableItemTypesByChannelTypeUID(LinkableItemTypesByChannelTypeUID command);

    @Binds
    @IntoMap
    @ClassKey(ConfigDescriptionByURI.class)
    Callable<Integer> configDescriptionsConfigDescriptionByURI(ConfigDescriptionByURI command);

    @Binds
    @IntoMap
    @ClassKey(ConfigDescriptions.class)
    Callable<Integer> configDescriptionsConfigDescriptions(ConfigDescriptions command);

    @Binds
    @IntoMap
    @ClassKey(BindingsWithDiscoverySupport.class)
    Callable<Integer> discoveryBindingsWithDiscoverySupport(BindingsWithDiscoverySupport command);

    @Binds
    @IntoMap
    @ClassKey(DiscoveryServicesInfo.class)
    Callable<Integer> discoveryDiscoveryServicesInfo(DiscoveryServicesInfo command);

    @Binds
    @IntoMap
    @ClassKey(Scan.class)
    Callable<Integer> discoveryScan(Scan command);

    @Binds
    @IntoMap
    @ClassKey(Events.class)
    Callable<Integer> eventsEvents(Events command);

    @Binds
    @IntoMap
    @ClassKey(InitNewStateTacker.class)
    Callable<Integer> eventsInitNewStateTacker(InitNewStateTacker command);

    @Binds
    @IntoMap
    @ClassKey(UpdateItemListForStateUpdates.class)
    Callable<Integer> eventsUpdateItemListForStateUpdates(UpdateItemListForStateUpdates command);

    @Binds
    @IntoMap
    @ClassKey(CanSerializeRules.class)
    Callable<Integer> fileFormatCanSerializeRules(CanSerializeRules command);

    @Binds
    @IntoMap
    @ClassKey(Create.class)
    Callable<Integer> fileFormatCreate(Create command);

    @Binds
    @IntoMap
    @ClassKey(CreateFileFormatForItems.class)
    Callable<Integer> fileFormatCreateFileFormatForItems(CreateFileFormatForItems command);

    @Binds
    @IntoMap
    @ClassKey(CreateFileFormatForRuleTemplates.class)
    Callable<Integer> fileFormatCreateFileFormatForRuleTemplates(CreateFileFormatForRuleTemplates command);

    @Binds
    @IntoMap
    @ClassKey(CreateFileFormatForRules.class)
    Callable<Integer> fileFormatCreateFileFormatForRules(CreateFileFormatForRules command);

    @Binds
    @IntoMap
    @ClassKey(CreateFileFormatForSemanticTags.class)
    Callable<Integer> fileFormatCreateFileFormatForSemanticTags(CreateFileFormatForSemanticTags command);

    @Binds
    @IntoMap
    @ClassKey(CreateFileFormatForSitemaps.class)
    Callable<Integer> fileFormatCreateFileFormatForSitemaps(CreateFileFormatForSitemaps command);

    @Binds
    @IntoMap
    @ClassKey(CreateFileFormatForThings.class)
    Callable<Integer> fileFormatCreateFileFormatForThings(CreateFileFormatForThings command);

    @Binds
    @IntoMap
    @ClassKey(Parse.class)
    Callable<Integer> fileFormatParse(Parse command);

    @Binds
    @IntoMap
    @ClassKey(IconSets.class)
    Callable<Integer> iconsetsIconSets(IconSets command);

    @Binds
    @IntoMap
    @ClassKey(ApproveInboxItemById.class)
    Callable<Integer> inboxApproveInboxItemById(ApproveInboxItemById command);

    @Binds
    @IntoMap
    @ClassKey(FlagInboxItemAsIgnored.class)
    Callable<Integer> inboxFlagInboxItemAsIgnored(FlagInboxItemAsIgnored command);

    @Binds
    @IntoMap
    @ClassKey(DiscoveredInboxItems.class)
    Callable<Integer> inboxDiscoveredInboxItems(DiscoveredInboxItems command);

    @Binds
    @IntoMap
    @ClassKey(RemoveIgnoreFlagOnInboxItem.class)
    Callable<Integer> inboxRemoveIgnoreFlagOnInboxItem(RemoveIgnoreFlagOnInboxItem command);

    @Binds
    @IntoMap
    @ClassKey(RemoveItemFromInbox.class)
    Callable<Integer> inboxRemoveItemFromInbox(RemoveItemFromInbox command);

    @Binds
    @IntoMap
    @ClassKey(AddMemberToGroupItem.class)
    Callable<Integer> itemsAddMemberToGroupItem(AddMemberToGroupItem command);

    @Binds
    @IntoMap
    @ClassKey(AddMetadataToItem.class)
    Callable<Integer> itemsAddMetadataToItem(AddMetadataToItem command);

    @Binds
    @IntoMap
    @ClassKey(AddOrUpdateItemInRegistry.class)
    Callable<Integer> itemsAddOrUpdateItemInRegistry(AddOrUpdateItemInRegistry command);

    @Binds
    @IntoMap
    @ClassKey(AddOrUpdateItemsInRegistry.class)
    Callable<Integer> itemsAddOrUpdateItemsInRegistry(AddOrUpdateItemsInRegistry command);

    @Binds
    @IntoMap
    @ClassKey(AddTagToItem.class)
    Callable<Integer> itemsAddTagToItem(AddTagToItem command);

    @Binds
    @IntoMap
    @ClassKey(ItemByName.class)
    Callable<Integer> itemsItemByName(ItemByName command);

    @Binds
    @IntoMap
    @ClassKey(ItemNamespaces.class)
    Callable<Integer> itemsItemNamespaces(ItemNamespaces command);

    @Binds
    @IntoMap
    @ClassKey(ItemState1.class)
    Callable<Integer> itemsItemState1(ItemState1 command);

    @Binds
    @IntoMap
    @ClassKey(Items.class)
    Callable<Integer> itemsItems(Items command);

    @Binds
    @IntoMap
    @ClassKey(SemanticItem.class)
    Callable<Integer> itemsSemanticItem(SemanticItem command);

    @Binds
    @IntoMap
    @ClassKey(SemanticsHealth.class)
    Callable<Integer> itemsSemanticsHealth(SemanticsHealth command);

    @Binds
    @IntoMap
    @ClassKey(PurgeDatabase.class)
    Callable<Integer> itemsPurgeDatabase(PurgeDatabase command);

    @Binds
    @IntoMap
    @ClassKey(RemoveAllMetadataFromItem.class)
    Callable<Integer> itemsRemoveAllMetadataFromItem(RemoveAllMetadataFromItem command);

    @Binds
    @IntoMap
    @ClassKey(RemoveItemFromRegistry.class)
    Callable<Integer> itemsRemoveItemFromRegistry(RemoveItemFromRegistry command);

    @Binds
    @IntoMap
    @ClassKey(RemoveMemberFromGroupItem.class)
    Callable<Integer> itemsRemoveMemberFromGroupItem(RemoveMemberFromGroupItem command);

    @Binds
    @IntoMap
    @ClassKey(RemoveMetadataFromItem.class)
    Callable<Integer> itemsRemoveMetadataFromItem(RemoveMetadataFromItem command);

    @Binds
    @IntoMap
    @ClassKey(RemoveTagFromItem.class)
    Callable<Integer> itemsRemoveTagFromItem(RemoveTagFromItem command);

    @Binds
    @IntoMap
    @ClassKey(SendItemCommand.class)
    Callable<Integer> itemsSendItemCommand(SendItemCommand command);

    @Binds
    @IntoMap
    @ClassKey(UpdateItemState.class)
    Callable<Integer> itemsUpdateItemState(UpdateItemState command);

    @Binds
    @IntoMap
    @ClassKey(ItemLink.class)
    Callable<Integer> linksItemLink(ItemLink command);

    @Binds
    @IntoMap
    @ClassKey(ItemLinks.class)
    Callable<Integer> linksItemLinks(ItemLinks command);

    @Binds
    @IntoMap
    @ClassKey(OrphanLinks.class)
    Callable<Integer> linksOrphanLinks(OrphanLinks command);

    @Binds
    @IntoMap
    @ClassKey(LinkItemToChannel.class)
    Callable<Integer> linksLinkItemToChannel(LinkItemToChannel command);

    @Binds
    @IntoMap
    @ClassKey(PurgeDatabase1.class)
    Callable<Integer> linksPurgeDatabase1(PurgeDatabase1 command);

    @Binds
    @IntoMap
    @ClassKey(RemoveAllLinksForObject.class)
    Callable<Integer> linksRemoveAllLinksForObject(RemoveAllLinksForObject command);

    @Binds
    @IntoMap
    @ClassKey(UnlinkItemFromChannel.class)
    Callable<Integer> linksUnlinkItemFromChannel(UnlinkItemFromChannel command);

    @Binds
    @IntoMap
    @ClassKey(Logger.class)
    Callable<Integer> loggingLogger(Logger command);

    @Binds
    @IntoMap
    @ClassKey(Logger1.class)
    Callable<Integer> loggingLogger1(Logger1 command);

    @Binds
    @IntoMap
    @ClassKey(PutLogger.class)
    Callable<Integer> loggingPutLogger(PutLogger command);

    @Binds
    @IntoMap
    @ClassKey(RemoveLogger.class)
    Callable<Integer> loggingRemoveLogger(RemoveLogger command);

    @Binds
    @IntoMap
    @ClassKey(ModuleTypeById.class)
    Callable<Integer> moduleTypesModuleTypeById(ModuleTypeById command);

    @Binds
    @IntoMap
    @ClassKey(ModuleTypes.class)
    Callable<Integer> moduleTypesModuleTypes(ModuleTypes command);

    @Binds
    @IntoMap
    @ClassKey(DeleteItemFromPersistenceService.class)
    Callable<Integer> persistenceDeleteItemFromPersistenceService(DeleteItemFromPersistenceService command);

    @Binds
    @IntoMap
    @ClassKey(DeletePersistenceServiceConfiguration.class)
    Callable<Integer> persistenceDeletePersistenceServiceConfiguration(DeletePersistenceServiceConfiguration command);

    @Binds
    @IntoMap
    @ClassKey(ItemDataFromPersistenceService.class)
    Callable<Integer> persistenceItemDataFromPersistenceService(ItemDataFromPersistenceService command);

    @Binds
    @IntoMap
    @ClassKey(ItemsForPersistenceService.class)
    Callable<Integer> persistenceItemsForPersistenceService(ItemsForPersistenceService command);

    @Binds
    @IntoMap
    @ClassKey(PersistenceHealth.class)
    Callable<Integer> persistencePersistenceHealth(PersistenceHealth command);

    @Binds
    @IntoMap
    @ClassKey(PersistenceServiceConfiguration.class)
    Callable<Integer> persistencePersistenceServiceConfiguration(PersistenceServiceConfiguration command);

    @Binds
    @IntoMap
    @ClassKey(PersistenceServiceStrategySuggestions.class)
    Callable<Integer> persistencePersistenceServiceStrategySuggestions(PersistenceServiceStrategySuggestions command);

    @Binds
    @IntoMap
    @ClassKey(PersistenceServices.class)
    Callable<Integer> persistencePersistenceServices(PersistenceServices command);

    @Binds
    @IntoMap
    @ClassKey(PutPersistenceServiceConfiguration.class)
    Callable<Integer> persistencePutPersistenceServiceConfiguration(PutPersistenceServiceConfiguration command);

    @Binds
    @IntoMap
    @ClassKey(StoreItemDataInPersistenceService.class)
    Callable<Integer> persistenceStoreItemDataInPersistenceService(StoreItemDataInPersistenceService command);

    @Binds
    @IntoMap
    @ClassKey(ProfileTypes.class)
    Callable<Integer> profileTypesProfileTypes(ProfileTypes command);

    @Binds
    @IntoMap
    @ClassKey(Root.class)
    Callable<Integer> rootRoot(Root command);

    @Binds
    @IntoMap
    @ClassKey(CreateRule.class)
    Callable<Integer> rulesCreateRule(CreateRule command);

    @Binds
    @IntoMap
    @ClassKey(DeleteRule.class)
    Callable<Integer> rulesDeleteRule(DeleteRule command);

    @Binds
    @IntoMap
    @ClassKey(EnableRule.class)
    Callable<Integer> rulesEnableRule(EnableRule command);

    @Binds
    @IntoMap
    @ClassKey(RuleActions.class)
    Callable<Integer> rulesRuleActions(RuleActions command);

    @Binds
    @IntoMap
    @ClassKey(RuleById.class)
    Callable<Integer> rulesRuleById(RuleById command);

    @Binds
    @IntoMap
    @ClassKey(RuleConditions.class)
    Callable<Integer> rulesRuleConditions(RuleConditions command);

    @Binds
    @IntoMap
    @ClassKey(RuleConfiguration.class)
    Callable<Integer> rulesRuleConfiguration(RuleConfiguration command);

    @Binds
    @IntoMap
    @ClassKey(RuleModuleById.class)
    Callable<Integer> rulesRuleModuleById(RuleModuleById command);

    @Binds
    @IntoMap
    @ClassKey(RuleModuleConfig.class)
    Callable<Integer> rulesRuleModuleConfig(RuleModuleConfig command);

    @Binds
    @IntoMap
    @ClassKey(RuleModuleConfigParameter.class)
    Callable<Integer> rulesRuleModuleConfigParameter(RuleModuleConfigParameter command);

    @Binds
    @IntoMap
    @ClassKey(RuleTriggers.class)
    Callable<Integer> rulesRuleTriggers(RuleTriggers command);

    @Binds
    @IntoMap
    @ClassKey(Rules.class)
    Callable<Integer> rulesRules(Rules command);

    @Binds
    @IntoMap
    @ClassKey(ScheduleRuleSimulations.class)
    Callable<Integer> rulesScheduleRuleSimulations(ScheduleRuleSimulations command);

    @Binds
    @IntoMap
    @ClassKey(RegenerateRule.class)
    Callable<Integer> rulesRegenerateRule(RegenerateRule command);

    @Binds
    @IntoMap
    @ClassKey(RunRuleNow1.class)
    Callable<Integer> rulesRunRuleNow1(RunRuleNow1 command);

    @Binds
    @IntoMap
    @ClassKey(SetRuleModuleConfigParameter.class)
    Callable<Integer> rulesSetRuleModuleConfigParameter(SetRuleModuleConfigParameter command);

    @Binds
    @IntoMap
    @ClassKey(UpdateRule.class)
    Callable<Integer> rulesUpdateRule(UpdateRule command);

    @Binds
    @IntoMap
    @ClassKey(UpdateRuleConfiguration.class)
    Callable<Integer> rulesUpdateRuleConfiguration(UpdateRuleConfiguration command);

    @Binds
    @IntoMap
    @ClassKey(DeleteServiceConfig.class)
    Callable<Integer> servicesDeleteServiceConfig(DeleteServiceConfig command);

    @Binds
    @IntoMap
    @ClassKey(ServiceConfig.class)
    Callable<Integer> servicesServiceConfig(ServiceConfig command);

    @Binds
    @IntoMap
    @ClassKey(ServiceContext.class)
    Callable<Integer> servicesServiceContext(ServiceContext command);

    @Binds
    @IntoMap
    @ClassKey(Services.class)
    Callable<Integer> servicesServices(Services command);

    @Binds
    @IntoMap
    @ClassKey(ServicesById.class)
    Callable<Integer> servicesServicesById(ServicesById command);

    @Binds
    @IntoMap
    @ClassKey(UpdateServiceConfig.class)
    Callable<Integer> servicesUpdateServiceConfig(UpdateServiceConfig command);

    @Binds
    @IntoMap
    @ClassKey(AddOrUpdateSitemapInRegistry.class)
    Callable<Integer> sitemapsAddOrUpdateSitemapInRegistry(AddOrUpdateSitemapInRegistry command);

    @Binds
    @IntoMap
    @ClassKey(CreateSitemapEventSubscription.class)
    Callable<Integer> sitemapsCreateSitemapEventSubscription(CreateSitemapEventSubscription command);

    @Binds
    @IntoMap
    @ClassKey(SitemapByName.class)
    Callable<Integer> sitemapsSitemapByName(SitemapByName command);

    @Binds
    @IntoMap
    @ClassKey(SitemapDefinitionByName.class)
    Callable<Integer> sitemapsSitemapDefinitionByName(SitemapDefinitionByName command);

    @Binds
    @IntoMap
    @ClassKey(SitemapDefinitions.class)
    Callable<Integer> sitemapsSitemapDefinitions(SitemapDefinitions command);

    @Binds
    @IntoMap
    @ClassKey(SitemapEvents.class)
    Callable<Integer> sitemapsSitemapEvents(SitemapEvents command);

    @Binds
    @IntoMap
    @ClassKey(SitemapEvents1.class)
    Callable<Integer> sitemapsSitemapEvents1(SitemapEvents1 command);

    @Binds
    @IntoMap
    @ClassKey(Sitemaps.class)
    Callable<Integer> sitemapsSitemaps(Sitemaps command);

    @Binds
    @IntoMap
    @ClassKey(PollDataForPage.class)
    Callable<Integer> sitemapsPollDataForPage(PollDataForPage command);

    @Binds
    @IntoMap
    @ClassKey(PollDataForSitemap.class)
    Callable<Integer> sitemapsPollDataForSitemap(PollDataForSitemap command);

    @Binds
    @IntoMap
    @ClassKey(RemoveSitemapFromRegistry.class)
    Callable<Integer> sitemapsRemoveSitemapFromRegistry(RemoveSitemapFromRegistry command);

    @Binds
    @IntoMap
    @ClassKey(SystemInformation.class)
    Callable<Integer> systemInfoSystemInformation(SystemInformation command);

    @Binds
    @IntoMap
    @ClassKey(UoMInformation.class)
    Callable<Integer> systemInfoUoMInformation(UoMInformation command);

    @Binds
    @IntoMap
    @ClassKey(CreateSemanticTag.class)
    Callable<Integer> tagsCreateSemanticTag(CreateSemanticTag command);

    @Binds
    @IntoMap
    @ClassKey(SemanticTagAndSubTags.class)
    Callable<Integer> tagsSemanticTagAndSubTags(SemanticTagAndSubTags command);

    @Binds
    @IntoMap
    @ClassKey(SemanticTags.class)
    Callable<Integer> tagsSemanticTags(SemanticTags command);

    @Binds
    @IntoMap
    @ClassKey(RemoveSemanticTag.class)
    Callable<Integer> tagsRemoveSemanticTag(RemoveSemanticTag command);

    @Binds
    @IntoMap
    @ClassKey(UpdateSemanticTag.class)
    Callable<Integer> tagsUpdateSemanticTag(UpdateSemanticTag command);

    @Binds
    @IntoMap
    @ClassKey(TemplateById.class)
    Callable<Integer> templatesTemplateById(TemplateById command);

    @Binds
    @IntoMap
    @ClassKey(Templates.class)
    Callable<Integer> templatesTemplates(Templates command);

    @Binds
    @IntoMap
    @ClassKey(ThingTypeById.class)
    Callable<Integer> thingTypesThingTypeById(ThingTypeById command);

    @Binds
    @IntoMap
    @ClassKey(ThingTypes.class)
    Callable<Integer> thingTypesThingTypes(ThingTypes command);

    @Binds
    @IntoMap
    @ClassKey(CreateThingInRegistry.class)
    Callable<Integer> thingsCreateThingInRegistry(CreateThingInRegistry command);

    @Binds
    @IntoMap
    @ClassKey(EnableThing.class)
    Callable<Integer> thingsEnableThing(EnableThing command);

    @Binds
    @IntoMap
    @ClassKey(AvailableFirmwaresForThing.class)
    Callable<Integer> thingsAvailableFirmwaresForThing(AvailableFirmwaresForThing command);

    @Binds
    @IntoMap
    @ClassKey(ThingById.class)
    Callable<Integer> thingsThingById(ThingById command);

    @Binds
    @IntoMap
    @ClassKey(ThingConfigStatus.class)
    Callable<Integer> thingsThingConfigStatus(ThingConfigStatus command);

    @Binds
    @IntoMap
    @ClassKey(ThingFirmwareStatus.class)
    Callable<Integer> thingsThingFirmwareStatus(ThingFirmwareStatus command);

    @Binds
    @IntoMap
    @ClassKey(ThingStatus.class)
    Callable<Integer> thingsThingStatus(ThingStatus command);

    @Binds
    @IntoMap
    @ClassKey(Things.class)
    Callable<Integer> thingsThings(Things command);

    @Binds
    @IntoMap
    @ClassKey(RemoveThingById.class)
    Callable<Integer> thingsRemoveThingById(RemoveThingById command);

    @Binds
    @IntoMap
    @ClassKey(UpdateThing.class)
    Callable<Integer> thingsUpdateThing(UpdateThing command);

    @Binds
    @IntoMap
    @ClassKey(UpdateThingConfig.class)
    Callable<Integer> thingsUpdateThingConfig(UpdateThingConfig command);

    @Binds
    @IntoMap
    @ClassKey(UpdateThingFirmware.class)
    Callable<Integer> thingsUpdateThingFirmware(UpdateThingFirmware command);

    @Binds
    @IntoMap
    @ClassKey(DeleteTransformation.class)
    Callable<Integer> transformationsDeleteTransformation(DeleteTransformation command);

    @Binds
    @IntoMap
    @ClassKey(Transformation.class)
    Callable<Integer> transformationsTransformation(Transformation command);

    @Binds
    @IntoMap
    @ClassKey(TransformationServices.class)
    Callable<Integer> transformationsTransformationServices(TransformationServices command);

    @Binds
    @IntoMap
    @ClassKey(Transformations.class)
    Callable<Integer> transformationsTransformations(Transformations command);

    @Binds
    @IntoMap
    @ClassKey(PutTransformation.class)
    Callable<Integer> transformationsPutTransformation(PutTransformation command);

    @Binds
    @IntoMap
    @ClassKey(AddUIComponentToNamespace.class)
    Callable<Integer> uiAddUIComponentToNamespace(AddUIComponentToNamespace command);

    @Binds
    @IntoMap
    @ClassKey(RegisteredUIComponentsInNamespace.class)
    Callable<Integer> uiRegisteredUIComponentsInNamespace(RegisteredUIComponentsInNamespace command);

    @Binds
    @IntoMap
    @ClassKey(UiComponentInNamespace.class)
    Callable<Integer> uiUiComponentInNamespace(UiComponentInNamespace command);

    @Binds
    @IntoMap
    @ClassKey(UiTiles.class)
    Callable<Integer> uiUiTiles(UiTiles command);

    @Binds
    @IntoMap
    @ClassKey(RemoveUIComponentFromNamespace.class)
    Callable<Integer> uiRemoveUIComponentFromNamespace(RemoveUIComponentFromNamespace command);

    @Binds
    @IntoMap
    @ClassKey(UpdateUIComponentInNamespace.class)
    Callable<Integer> uiUpdateUIComponentInNamespace(UpdateUIComponentInNamespace command);

    @Binds
    @IntoMap
    @ClassKey(Uuid.class)
    Callable<Integer> uuidUuid(Uuid command);

    @Binds
    @IntoMap
    @ClassKey(DeleteConversationById.class)
    Callable<Integer> voiceDeleteConversationById(DeleteConversationById command);

    @Binds
    @IntoMap
    @ClassKey(ConversationById.class)
    Callable<Integer> voiceConversationById(ConversationById command);

    @Binds
    @IntoMap
    @ClassKey(DefaultVoice.class)
    Callable<Integer> voiceDefaultVoice(DefaultVoice command);

    @Binds
    @IntoMap
    @ClassKey(LlmTools.class)
    Callable<Integer> voiceLlmTools(LlmTools command);

    @Binds
    @IntoMap
    @ClassKey(VoiceInterpreterByUID.class)
    Callable<Integer> voiceVoiceInterpreterByUID(VoiceInterpreterByUID command);

    @Binds
    @IntoMap
    @ClassKey(VoiceInterpreters.class)
    Callable<Integer> voiceVoiceInterpreters(VoiceInterpreters command);

    @Binds
    @IntoMap
    @ClassKey(Voices.class)
    Callable<Integer> voiceVoices(Voices command);

    @Binds
    @IntoMap
    @ClassKey(InterpretText.class)
    Callable<Integer> voiceInterpretText(InterpretText command);

    @Binds
    @IntoMap
    @ClassKey(InterpretTextByDefaultInterpreter.class)
    Callable<Integer> voiceInterpretTextByDefaultInterpreter(InterpretTextByDefaultInterpreter command);

    @Binds
    @IntoMap
    @ClassKey(ListConversations.class)
    Callable<Integer> voiceListConversations(ListConversations command);

    @Binds
    @IntoMap
    @ClassKey(ListenAndAnswer.class)
    Callable<Integer> voiceListenAndAnswer(ListenAndAnswer command);

    @Binds
    @IntoMap
    @ClassKey(StartDialog.class)
    Callable<Integer> voiceStartDialog(StartDialog command);

    @Binds
    @IntoMap
    @ClassKey(StopDialog.class)
    Callable<Integer> voiceStopDialog(StopDialog command);

    @Binds
    @IntoMap
    @ClassKey(TextToSpeech.class)
    Callable<Integer> voiceTextToSpeech(TextToSpeech command);

    @Binds
    @IntoMap
    @ClassKey(SetCommand.class)
    Callable<Integer> setProperty(SetCommand command);

    @Binds
    @IntoMap
    @ClassKey(GetCommand.class)
    Callable<Integer> getProperty(GetCommand command);

    @Binds
    @IntoMap
    @ClassKey(ClearCommand.class)
    Callable<Integer> clearProperty(ClearCommand command);
}
