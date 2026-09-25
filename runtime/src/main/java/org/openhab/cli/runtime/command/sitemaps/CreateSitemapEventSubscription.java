package org.openhab.cli.runtime.command.sitemaps;

import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.engine.endpoint.Sitemaps;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Creates a sitemap event subscription. */
@Slf4j
@CommandLine.Command(
        name = "createSitemapEventSubscription",
        description = "Creates a sitemap event subscription.",
        mixinStandardHelpOptions = true)
public class CreateSitemapEventSubscription implements Runnable {
    @CommandLine.Mixin
    private Options options;

    private final Console console;
    private final ApiClientBuilder apiClientBuilder;

    /** Creates the command with injected output and REST client services. */
    @Inject
    CreateSitemapEventSubscription(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link Sitemaps#createSitemapEventSubscription}. */
    @Override
    public void run() {
        log.debug("Command: Sitemaps.createSitemapEventSubscription");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Sitemaps(apiClient);
        endpoint.createSitemapEventSubscription();
    }
}
