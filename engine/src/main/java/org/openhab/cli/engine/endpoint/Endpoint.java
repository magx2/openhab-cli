package org.openhab.cli.engine.endpoint;

import java.util.Locale;

/** A group of openHAB operations exposed by the CLI engine. */
public sealed interface Endpoint
        permits Action,
                Addons,
                Audio,
                Auth,
                ChannelTypes,
                ConfigDescriptions,
                Discovery,
                EngineInternal,
                Events,
                FileFormat,
                Iconsets,
                Inbox,
                Items,
                Links,
                Logging,
                ModuleTypes,
                Persistence,
                ProfileTypes,
                Root,
                Rules,
                Services,
                Sitemaps,
                SystemInfo,
                Tags,
                Templates,
                ThingTypes,
                Things,
                Transformations,
                Ui,
                Uuid,
                Voice {
    /**
     * Returns the keyword identifying this endpoint group.
     *
     * @return the endpoint class name in lowercase, unless overridden
     */
    default String keyword() {
        return this.getClass().getSimpleName().toLowerCase(Locale.ROOT);
    }
}
