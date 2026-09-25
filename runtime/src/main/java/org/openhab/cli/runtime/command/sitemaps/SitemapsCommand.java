package org.openhab.cli.runtime.command.sitemaps;

import picocli.CommandLine.Command;

/** Picocli command group for Sitemaps operations. */
@Command(
        name = "sitemaps",
        description = "Commands for Sitemaps.",
        mixinStandardHelpOptions = true,
        subcommands = {
            AddOrUpdateSitemapInRegistry.class,
            CreateSitemapEventSubscription.class,
            SitemapByName.class,
            SitemapDefinitionByName.class,
            SitemapDefinitions.class,
            SitemapEvents.class,
            SitemapEvents1.class,
            Sitemaps.class,
            PollDataForPage.class,
            PollDataForSitemap.class,
            RemoveSitemapFromRegistry.class
        })
public class SitemapsCommand {}
