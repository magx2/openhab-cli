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
import org.openhab.cli.runtime.command.config.properties.ListCommand;
import org.openhab.cli.runtime.command.config.properties.SetCommand;
import org.openhab.cli.runtime.command.config.shell.bash.BashCompletionCommand;
import org.openhab.cli.runtime.command.config.shell.fish.FishCompletionCommand;
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
    Runnable actionExecuteThingAction(ExecuteThingAction command);

    @Binds
    @IntoMap
    @ClassKey(AvailableActionsForThing.class)
    Runnable actionAvailableActionsForThing(AvailableActionsForThing command);

    @Binds
    @IntoMap
    @ClassKey(AddonById.class)
    Runnable addonsAddonById(AddonById command);

    @Binds
    @IntoMap
    @ClassKey(AddonConfiguration.class)
    Runnable addonsAddonConfiguration(AddonConfiguration command);

    @Binds
    @IntoMap
    @ClassKey(AddonServices.class)
    Runnable addonsAddonServices(AddonServices command);

    @Binds
    @IntoMap
    @ClassKey(AddonTypes.class)
    Runnable addonsAddonTypes(AddonTypes command);

    @Binds
    @IntoMap
    @ClassKey(Addons.class)
    Runnable addonsAddons(Addons command);

    @Binds
    @IntoMap
    @ClassKey(SuggestedAddons.class)
    Runnable addonsSuggestedAddons(SuggestedAddons command);

    @Binds
    @IntoMap
    @ClassKey(InstallAddonById.class)
    Runnable addonsInstallAddonById(InstallAddonById command);

    @Binds
    @IntoMap
    @ClassKey(InstallAddonFromURL.class)
    Runnable addonsInstallAddonFromURL(InstallAddonFromURL command);

    @Binds
    @IntoMap
    @ClassKey(UninstallAddon.class)
    Runnable addonsUninstallAddon(UninstallAddon command);

    @Binds
    @IntoMap
    @ClassKey(UpdateAddonConfiguration.class)
    Runnable addonsUpdateAddonConfiguration(UpdateAddonConfiguration command);

    @Binds
    @IntoMap
    @ClassKey(AudioDefaultSink.class)
    Runnable audioAudioDefaultSink(AudioDefaultSink command);

    @Binds
    @IntoMap
    @ClassKey(AudioDefaultSource.class)
    Runnable audioAudioDefaultSource(AudioDefaultSource command);

    @Binds
    @IntoMap
    @ClassKey(AudioSinks.class)
    Runnable audioAudioSinks(AudioSinks command);

    @Binds
    @IntoMap
    @ClassKey(AudioSources.class)
    Runnable audioAudioSources(AudioSources command);

    @Binds
    @IntoMap
    @ClassKey(DeleteSession.class)
    Runnable authDeleteSession(DeleteSession command);

    @Binds
    @IntoMap
    @ClassKey(ApiTokens.class)
    Runnable authApiTokens(ApiTokens command);

    @Binds
    @IntoMap
    @ClassKey(OAuthToken.class)
    Runnable authOAuthToken(OAuthToken command);

    @Binds
    @IntoMap
    @ClassKey(SessionsForCurrentUser.class)
    Runnable authSessionsForCurrentUser(SessionsForCurrentUser command);

    @Binds
    @IntoMap
    @ClassKey(RemoveApiToken.class)
    Runnable authRemoveApiToken(RemoveApiToken command);

    @Binds
    @IntoMap
    @ClassKey(ChannelTypeByUID.class)
    Runnable channelTypesChannelTypeByUID(ChannelTypeByUID command);

    @Binds
    @IntoMap
    @ClassKey(ChannelTypes.class)
    Runnable channelTypesChannelTypes(ChannelTypes command);

    @Binds
    @IntoMap
    @ClassKey(LinkableItemTypesByChannelTypeUID.class)
    Runnable channelTypesLinkableItemTypesByChannelTypeUID(LinkableItemTypesByChannelTypeUID command);

    @Binds
    @IntoMap
    @ClassKey(ConfigDescriptionByURI.class)
    Runnable configDescriptionsConfigDescriptionByURI(ConfigDescriptionByURI command);

    @Binds
    @IntoMap
    @ClassKey(ConfigDescriptions.class)
    Runnable configDescriptionsConfigDescriptions(ConfigDescriptions command);

    @Binds
    @IntoMap
    @ClassKey(BindingsWithDiscoverySupport.class)
    Runnable discoveryBindingsWithDiscoverySupport(BindingsWithDiscoverySupport command);

    @Binds
    @IntoMap
    @ClassKey(DiscoveryServicesInfo.class)
    Runnable discoveryDiscoveryServicesInfo(DiscoveryServicesInfo command);

    @Binds
    @IntoMap
    @ClassKey(Scan.class)
    Runnable discoveryScan(Scan command);

    @Binds
    @IntoMap
    @ClassKey(Events.class)
    Runnable eventsEvents(Events command);

    @Binds
    @IntoMap
    @ClassKey(InitNewStateTacker.class)
    Runnable eventsInitNewStateTacker(InitNewStateTacker command);

    @Binds
    @IntoMap
    @ClassKey(UpdateItemListForStateUpdates.class)
    Runnable eventsUpdateItemListForStateUpdates(UpdateItemListForStateUpdates command);

    @Binds
    @IntoMap
    @ClassKey(CanSerializeRules.class)
    Runnable fileFormatCanSerializeRules(CanSerializeRules command);

    @Binds
    @IntoMap
    @ClassKey(Create.class)
    Runnable fileFormatCreate(Create command);

    @Binds
    @IntoMap
    @ClassKey(CreateFileFormatForItems.class)
    Runnable fileFormatCreateFileFormatForItems(CreateFileFormatForItems command);

    @Binds
    @IntoMap
    @ClassKey(CreateFileFormatForRuleTemplates.class)
    Runnable fileFormatCreateFileFormatForRuleTemplates(CreateFileFormatForRuleTemplates command);

    @Binds
    @IntoMap
    @ClassKey(CreateFileFormatForRules.class)
    Runnable fileFormatCreateFileFormatForRules(CreateFileFormatForRules command);

    @Binds
    @IntoMap
    @ClassKey(CreateFileFormatForSemanticTags.class)
    Runnable fileFormatCreateFileFormatForSemanticTags(CreateFileFormatForSemanticTags command);

    @Binds
    @IntoMap
    @ClassKey(CreateFileFormatForSitemaps.class)
    Runnable fileFormatCreateFileFormatForSitemaps(CreateFileFormatForSitemaps command);

    @Binds
    @IntoMap
    @ClassKey(CreateFileFormatForThings.class)
    Runnable fileFormatCreateFileFormatForThings(CreateFileFormatForThings command);

    @Binds
    @IntoMap
    @ClassKey(Parse.class)
    Runnable fileFormatParse(Parse command);

    @Binds
    @IntoMap
    @ClassKey(IconSets.class)
    Runnable iconsetsIconSets(IconSets command);

    @Binds
    @IntoMap
    @ClassKey(ApproveInboxItemById.class)
    Runnable inboxApproveInboxItemById(ApproveInboxItemById command);

    @Binds
    @IntoMap
    @ClassKey(FlagInboxItemAsIgnored.class)
    Runnable inboxFlagInboxItemAsIgnored(FlagInboxItemAsIgnored command);

    @Binds
    @IntoMap
    @ClassKey(DiscoveredInboxItems.class)
    Runnable inboxDiscoveredInboxItems(DiscoveredInboxItems command);

    @Binds
    @IntoMap
    @ClassKey(RemoveIgnoreFlagOnInboxItem.class)
    Runnable inboxRemoveIgnoreFlagOnInboxItem(RemoveIgnoreFlagOnInboxItem command);

    @Binds
    @IntoMap
    @ClassKey(RemoveItemFromInbox.class)
    Runnable inboxRemoveItemFromInbox(RemoveItemFromInbox command);

    @Binds
    @IntoMap
    @ClassKey(AddMemberToGroupItem.class)
    Runnable itemsAddMemberToGroupItem(AddMemberToGroupItem command);

    @Binds
    @IntoMap
    @ClassKey(AddMetadataToItem.class)
    Runnable itemsAddMetadataToItem(AddMetadataToItem command);

    @Binds
    @IntoMap
    @ClassKey(AddOrUpdateItemInRegistry.class)
    Runnable itemsAddOrUpdateItemInRegistry(AddOrUpdateItemInRegistry command);

    @Binds
    @IntoMap
    @ClassKey(AddOrUpdateItemsInRegistry.class)
    Runnable itemsAddOrUpdateItemsInRegistry(AddOrUpdateItemsInRegistry command);

    @Binds
    @IntoMap
    @ClassKey(AddTagToItem.class)
    Runnable itemsAddTagToItem(AddTagToItem command);

    @Binds
    @IntoMap
    @ClassKey(ItemByName.class)
    Runnable itemsItemByName(ItemByName command);

    @Binds
    @IntoMap
    @ClassKey(ItemNamespaces.class)
    Runnable itemsItemNamespaces(ItemNamespaces command);

    @Binds
    @IntoMap
    @ClassKey(ItemState1.class)
    Runnable itemsItemState1(ItemState1 command);

    @Binds
    @IntoMap
    @ClassKey(Items.class)
    Runnable itemsItems(Items command);

    @Binds
    @IntoMap
    @ClassKey(SemanticItem.class)
    Runnable itemsSemanticItem(SemanticItem command);

    @Binds
    @IntoMap
    @ClassKey(SemanticsHealth.class)
    Runnable itemsSemanticsHealth(SemanticsHealth command);

    @Binds
    @IntoMap
    @ClassKey(PurgeDatabase.class)
    Runnable itemsPurgeDatabase(PurgeDatabase command);

    @Binds
    @IntoMap
    @ClassKey(RemoveAllMetadataFromItem.class)
    Runnable itemsRemoveAllMetadataFromItem(RemoveAllMetadataFromItem command);

    @Binds
    @IntoMap
    @ClassKey(RemoveItemFromRegistry.class)
    Runnable itemsRemoveItemFromRegistry(RemoveItemFromRegistry command);

    @Binds
    @IntoMap
    @ClassKey(RemoveMemberFromGroupItem.class)
    Runnable itemsRemoveMemberFromGroupItem(RemoveMemberFromGroupItem command);

    @Binds
    @IntoMap
    @ClassKey(RemoveMetadataFromItem.class)
    Runnable itemsRemoveMetadataFromItem(RemoveMetadataFromItem command);

    @Binds
    @IntoMap
    @ClassKey(RemoveTagFromItem.class)
    Runnable itemsRemoveTagFromItem(RemoveTagFromItem command);

    @Binds
    @IntoMap
    @ClassKey(SendItemCommand.class)
    Runnable itemsSendItemCommand(SendItemCommand command);

    @Binds
    @IntoMap
    @ClassKey(UpdateItemState.class)
    Runnable itemsUpdateItemState(UpdateItemState command);

    @Binds
    @IntoMap
    @ClassKey(ItemLink.class)
    Runnable linksItemLink(ItemLink command);

    @Binds
    @IntoMap
    @ClassKey(ItemLinks.class)
    Runnable linksItemLinks(ItemLinks command);

    @Binds
    @IntoMap
    @ClassKey(OrphanLinks.class)
    Runnable linksOrphanLinks(OrphanLinks command);

    @Binds
    @IntoMap
    @ClassKey(LinkItemToChannel.class)
    Runnable linksLinkItemToChannel(LinkItemToChannel command);

    @Binds
    @IntoMap
    @ClassKey(PurgeDatabase1.class)
    Runnable linksPurgeDatabase1(PurgeDatabase1 command);

    @Binds
    @IntoMap
    @ClassKey(RemoveAllLinksForObject.class)
    Runnable linksRemoveAllLinksForObject(RemoveAllLinksForObject command);

    @Binds
    @IntoMap
    @ClassKey(UnlinkItemFromChannel.class)
    Runnable linksUnlinkItemFromChannel(UnlinkItemFromChannel command);

    @Binds
    @IntoMap
    @ClassKey(Logger.class)
    Runnable loggingLogger(Logger command);

    @Binds
    @IntoMap
    @ClassKey(Logger1.class)
    Runnable loggingLogger1(Logger1 command);

    @Binds
    @IntoMap
    @ClassKey(PutLogger.class)
    Runnable loggingPutLogger(PutLogger command);

    @Binds
    @IntoMap
    @ClassKey(RemoveLogger.class)
    Runnable loggingRemoveLogger(RemoveLogger command);

    @Binds
    @IntoMap
    @ClassKey(ModuleTypeById.class)
    Runnable moduleTypesModuleTypeById(ModuleTypeById command);

    @Binds
    @IntoMap
    @ClassKey(ModuleTypes.class)
    Runnable moduleTypesModuleTypes(ModuleTypes command);

    @Binds
    @IntoMap
    @ClassKey(DeleteItemFromPersistenceService.class)
    Runnable persistenceDeleteItemFromPersistenceService(DeleteItemFromPersistenceService command);

    @Binds
    @IntoMap
    @ClassKey(DeletePersistenceServiceConfiguration.class)
    Runnable persistenceDeletePersistenceServiceConfiguration(DeletePersistenceServiceConfiguration command);

    @Binds
    @IntoMap
    @ClassKey(ItemDataFromPersistenceService.class)
    Runnable persistenceItemDataFromPersistenceService(ItemDataFromPersistenceService command);

    @Binds
    @IntoMap
    @ClassKey(ItemsForPersistenceService.class)
    Runnable persistenceItemsForPersistenceService(ItemsForPersistenceService command);

    @Binds
    @IntoMap
    @ClassKey(PersistenceHealth.class)
    Runnable persistencePersistenceHealth(PersistenceHealth command);

    @Binds
    @IntoMap
    @ClassKey(PersistenceServiceConfiguration.class)
    Runnable persistencePersistenceServiceConfiguration(PersistenceServiceConfiguration command);

    @Binds
    @IntoMap
    @ClassKey(PersistenceServiceStrategySuggestions.class)
    Runnable persistencePersistenceServiceStrategySuggestions(PersistenceServiceStrategySuggestions command);

    @Binds
    @IntoMap
    @ClassKey(PersistenceServices.class)
    Runnable persistencePersistenceServices(PersistenceServices command);

    @Binds
    @IntoMap
    @ClassKey(PutPersistenceServiceConfiguration.class)
    Runnable persistencePutPersistenceServiceConfiguration(PutPersistenceServiceConfiguration command);

    @Binds
    @IntoMap
    @ClassKey(StoreItemDataInPersistenceService.class)
    Runnable persistenceStoreItemDataInPersistenceService(StoreItemDataInPersistenceService command);

    @Binds
    @IntoMap
    @ClassKey(ProfileTypes.class)
    Runnable profileTypesProfileTypes(ProfileTypes command);

    @Binds
    @IntoMap
    @ClassKey(Root.class)
    Runnable rootRoot(Root command);

    @Binds
    @IntoMap
    @ClassKey(CreateRule.class)
    Runnable rulesCreateRule(CreateRule command);

    @Binds
    @IntoMap
    @ClassKey(DeleteRule.class)
    Runnable rulesDeleteRule(DeleteRule command);

    @Binds
    @IntoMap
    @ClassKey(EnableRule.class)
    Runnable rulesEnableRule(EnableRule command);

    @Binds
    @IntoMap
    @ClassKey(RuleActions.class)
    Runnable rulesRuleActions(RuleActions command);

    @Binds
    @IntoMap
    @ClassKey(RuleById.class)
    Runnable rulesRuleById(RuleById command);

    @Binds
    @IntoMap
    @ClassKey(RuleConditions.class)
    Runnable rulesRuleConditions(RuleConditions command);

    @Binds
    @IntoMap
    @ClassKey(RuleConfiguration.class)
    Runnable rulesRuleConfiguration(RuleConfiguration command);

    @Binds
    @IntoMap
    @ClassKey(RuleModuleById.class)
    Runnable rulesRuleModuleById(RuleModuleById command);

    @Binds
    @IntoMap
    @ClassKey(RuleModuleConfig.class)
    Runnable rulesRuleModuleConfig(RuleModuleConfig command);

    @Binds
    @IntoMap
    @ClassKey(RuleModuleConfigParameter.class)
    Runnable rulesRuleModuleConfigParameter(RuleModuleConfigParameter command);

    @Binds
    @IntoMap
    @ClassKey(RuleTriggers.class)
    Runnable rulesRuleTriggers(RuleTriggers command);

    @Binds
    @IntoMap
    @ClassKey(Rules.class)
    Runnable rulesRules(Rules command);

    @Binds
    @IntoMap
    @ClassKey(ScheduleRuleSimulations.class)
    Runnable rulesScheduleRuleSimulations(ScheduleRuleSimulations command);

    @Binds
    @IntoMap
    @ClassKey(RegenerateRule.class)
    Runnable rulesRegenerateRule(RegenerateRule command);

    @Binds
    @IntoMap
    @ClassKey(RunRuleNow1.class)
    Runnable rulesRunRuleNow1(RunRuleNow1 command);

    @Binds
    @IntoMap
    @ClassKey(SetRuleModuleConfigParameter.class)
    Runnable rulesSetRuleModuleConfigParameter(SetRuleModuleConfigParameter command);

    @Binds
    @IntoMap
    @ClassKey(UpdateRule.class)
    Runnable rulesUpdateRule(UpdateRule command);

    @Binds
    @IntoMap
    @ClassKey(UpdateRuleConfiguration.class)
    Runnable rulesUpdateRuleConfiguration(UpdateRuleConfiguration command);

    @Binds
    @IntoMap
    @ClassKey(DeleteServiceConfig.class)
    Runnable servicesDeleteServiceConfig(DeleteServiceConfig command);

    @Binds
    @IntoMap
    @ClassKey(ServiceConfig.class)
    Runnable servicesServiceConfig(ServiceConfig command);

    @Binds
    @IntoMap
    @ClassKey(ServiceContext.class)
    Runnable servicesServiceContext(ServiceContext command);

    @Binds
    @IntoMap
    @ClassKey(Services.class)
    Runnable servicesServices(Services command);

    @Binds
    @IntoMap
    @ClassKey(ServicesById.class)
    Runnable servicesServicesById(ServicesById command);

    @Binds
    @IntoMap
    @ClassKey(UpdateServiceConfig.class)
    Runnable servicesUpdateServiceConfig(UpdateServiceConfig command);

    @Binds
    @IntoMap
    @ClassKey(AddOrUpdateSitemapInRegistry.class)
    Runnable sitemapsAddOrUpdateSitemapInRegistry(AddOrUpdateSitemapInRegistry command);

    @Binds
    @IntoMap
    @ClassKey(CreateSitemapEventSubscription.class)
    Runnable sitemapsCreateSitemapEventSubscription(CreateSitemapEventSubscription command);

    @Binds
    @IntoMap
    @ClassKey(SitemapByName.class)
    Runnable sitemapsSitemapByName(SitemapByName command);

    @Binds
    @IntoMap
    @ClassKey(SitemapDefinitionByName.class)
    Runnable sitemapsSitemapDefinitionByName(SitemapDefinitionByName command);

    @Binds
    @IntoMap
    @ClassKey(SitemapDefinitions.class)
    Runnable sitemapsSitemapDefinitions(SitemapDefinitions command);

    @Binds
    @IntoMap
    @ClassKey(SitemapEvents.class)
    Runnable sitemapsSitemapEvents(SitemapEvents command);

    @Binds
    @IntoMap
    @ClassKey(SitemapEvents1.class)
    Runnable sitemapsSitemapEvents1(SitemapEvents1 command);

    @Binds
    @IntoMap
    @ClassKey(Sitemaps.class)
    Runnable sitemapsSitemaps(Sitemaps command);

    @Binds
    @IntoMap
    @ClassKey(PollDataForPage.class)
    Runnable sitemapsPollDataForPage(PollDataForPage command);

    @Binds
    @IntoMap
    @ClassKey(PollDataForSitemap.class)
    Runnable sitemapsPollDataForSitemap(PollDataForSitemap command);

    @Binds
    @IntoMap
    @ClassKey(RemoveSitemapFromRegistry.class)
    Runnable sitemapsRemoveSitemapFromRegistry(RemoveSitemapFromRegistry command);

    @Binds
    @IntoMap
    @ClassKey(SystemInformation.class)
    Runnable systemInfoSystemInformation(SystemInformation command);

    @Binds
    @IntoMap
    @ClassKey(UoMInformation.class)
    Runnable systemInfoUoMInformation(UoMInformation command);

    @Binds
    @IntoMap
    @ClassKey(CreateSemanticTag.class)
    Runnable tagsCreateSemanticTag(CreateSemanticTag command);

    @Binds
    @IntoMap
    @ClassKey(SemanticTagAndSubTags.class)
    Runnable tagsSemanticTagAndSubTags(SemanticTagAndSubTags command);

    @Binds
    @IntoMap
    @ClassKey(SemanticTags.class)
    Runnable tagsSemanticTags(SemanticTags command);

    @Binds
    @IntoMap
    @ClassKey(RemoveSemanticTag.class)
    Runnable tagsRemoveSemanticTag(RemoveSemanticTag command);

    @Binds
    @IntoMap
    @ClassKey(UpdateSemanticTag.class)
    Runnable tagsUpdateSemanticTag(UpdateSemanticTag command);

    @Binds
    @IntoMap
    @ClassKey(TemplateById.class)
    Runnable templatesTemplateById(TemplateById command);

    @Binds
    @IntoMap
    @ClassKey(Templates.class)
    Runnable templatesTemplates(Templates command);

    @Binds
    @IntoMap
    @ClassKey(ThingTypeById.class)
    Runnable thingTypesThingTypeById(ThingTypeById command);

    @Binds
    @IntoMap
    @ClassKey(ThingTypes.class)
    Runnable thingTypesThingTypes(ThingTypes command);

    @Binds
    @IntoMap
    @ClassKey(CreateThingInRegistry.class)
    Runnable thingsCreateThingInRegistry(CreateThingInRegistry command);

    @Binds
    @IntoMap
    @ClassKey(EnableThing.class)
    Runnable thingsEnableThing(EnableThing command);

    @Binds
    @IntoMap
    @ClassKey(AvailableFirmwaresForThing.class)
    Runnable thingsAvailableFirmwaresForThing(AvailableFirmwaresForThing command);

    @Binds
    @IntoMap
    @ClassKey(ThingById.class)
    Runnable thingsThingById(ThingById command);

    @Binds
    @IntoMap
    @ClassKey(ThingConfigStatus.class)
    Runnable thingsThingConfigStatus(ThingConfigStatus command);

    @Binds
    @IntoMap
    @ClassKey(ThingFirmwareStatus.class)
    Runnable thingsThingFirmwareStatus(ThingFirmwareStatus command);

    @Binds
    @IntoMap
    @ClassKey(ThingStatus.class)
    Runnable thingsThingStatus(ThingStatus command);

    @Binds
    @IntoMap
    @ClassKey(Things.class)
    Runnable thingsThings(Things command);

    @Binds
    @IntoMap
    @ClassKey(RemoveThingById.class)
    Runnable thingsRemoveThingById(RemoveThingById command);

    @Binds
    @IntoMap
    @ClassKey(UpdateThing.class)
    Runnable thingsUpdateThing(UpdateThing command);

    @Binds
    @IntoMap
    @ClassKey(UpdateThingConfig.class)
    Runnable thingsUpdateThingConfig(UpdateThingConfig command);

    @Binds
    @IntoMap
    @ClassKey(UpdateThingFirmware.class)
    Runnable thingsUpdateThingFirmware(UpdateThingFirmware command);

    @Binds
    @IntoMap
    @ClassKey(DeleteTransformation.class)
    Runnable transformationsDeleteTransformation(DeleteTransformation command);

    @Binds
    @IntoMap
    @ClassKey(Transformation.class)
    Runnable transformationsTransformation(Transformation command);

    @Binds
    @IntoMap
    @ClassKey(TransformationServices.class)
    Runnable transformationsTransformationServices(TransformationServices command);

    @Binds
    @IntoMap
    @ClassKey(Transformations.class)
    Runnable transformationsTransformations(Transformations command);

    @Binds
    @IntoMap
    @ClassKey(PutTransformation.class)
    Runnable transformationsPutTransformation(PutTransformation command);

    @Binds
    @IntoMap
    @ClassKey(AddUIComponentToNamespace.class)
    Runnable uiAddUIComponentToNamespace(AddUIComponentToNamespace command);

    @Binds
    @IntoMap
    @ClassKey(RegisteredUIComponentsInNamespace.class)
    Runnable uiRegisteredUIComponentsInNamespace(RegisteredUIComponentsInNamespace command);

    @Binds
    @IntoMap
    @ClassKey(UiComponentInNamespace.class)
    Runnable uiUiComponentInNamespace(UiComponentInNamespace command);

    @Binds
    @IntoMap
    @ClassKey(UiTiles.class)
    Runnable uiUiTiles(UiTiles command);

    @Binds
    @IntoMap
    @ClassKey(RemoveUIComponentFromNamespace.class)
    Runnable uiRemoveUIComponentFromNamespace(RemoveUIComponentFromNamespace command);

    @Binds
    @IntoMap
    @ClassKey(UpdateUIComponentInNamespace.class)
    Runnable uiUpdateUIComponentInNamespace(UpdateUIComponentInNamespace command);

    @Binds
    @IntoMap
    @ClassKey(Uuid.class)
    Runnable uuidUuid(Uuid command);

    @Binds
    @IntoMap
    @ClassKey(DeleteConversationById.class)
    Runnable voiceDeleteConversationById(DeleteConversationById command);

    @Binds
    @IntoMap
    @ClassKey(ConversationById.class)
    Runnable voiceConversationById(ConversationById command);

    @Binds
    @IntoMap
    @ClassKey(DefaultVoice.class)
    Runnable voiceDefaultVoice(DefaultVoice command);

    @Binds
    @IntoMap
    @ClassKey(LlmTools.class)
    Runnable voiceLlmTools(LlmTools command);

    @Binds
    @IntoMap
    @ClassKey(VoiceInterpreterByUID.class)
    Runnable voiceVoiceInterpreterByUID(VoiceInterpreterByUID command);

    @Binds
    @IntoMap
    @ClassKey(VoiceInterpreters.class)
    Runnable voiceVoiceInterpreters(VoiceInterpreters command);

    @Binds
    @IntoMap
    @ClassKey(Voices.class)
    Runnable voiceVoices(Voices command);

    @Binds
    @IntoMap
    @ClassKey(InterpretText.class)
    Runnable voiceInterpretText(InterpretText command);

    @Binds
    @IntoMap
    @ClassKey(InterpretTextByDefaultInterpreter.class)
    Runnable voiceInterpretTextByDefaultInterpreter(InterpretTextByDefaultInterpreter command);

    @Binds
    @IntoMap
    @ClassKey(ListConversations.class)
    Runnable voiceListConversations(ListConversations command);

    @Binds
    @IntoMap
    @ClassKey(ListenAndAnswer.class)
    Runnable voiceListenAndAnswer(ListenAndAnswer command);

    @Binds
    @IntoMap
    @ClassKey(StartDialog.class)
    Runnable voiceStartDialog(StartDialog command);

    @Binds
    @IntoMap
    @ClassKey(StopDialog.class)
    Runnable voiceStopDialog(StopDialog command);

    @Binds
    @IntoMap
    @ClassKey(TextToSpeech.class)
    Runnable voiceTextToSpeech(TextToSpeech command);

    @Binds
    @IntoMap
    @ClassKey(SetCommand.class)
    Callable<Integer> setProperty(SetCommand command);

    @Binds
    @IntoMap
    @ClassKey(GetCommand.class)
    Runnable getProperty(GetCommand command);

    @Binds
    @IntoMap
    @ClassKey(ClearCommand.class)
    Callable<Integer> clearProperty(ClearCommand command);

    @Binds
    @IntoMap
    @ClassKey(ListCommand.class)
    Runnable listProperties(ListCommand command);

    @Binds
    @IntoMap
    @ClassKey(FishCompletionCommand.class)
    Callable<Integer> fishCompletions(FishCompletionCommand command);

    @Binds
    @IntoMap
    @ClassKey(BashCompletionCommand.class)
    Callable<Integer> bashCompletions(BashCompletionCommand command);
}
