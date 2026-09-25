package org.openhab.cli.runtime.command.sitemaps;

import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.engine.endpoint.Sitemaps;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Get sitemap events for a whole sitemap. Not recommended due to potentially high traffic. */
@Slf4j
@CommandLine.Command(
        name = "sitemapEvents",
        description = "Get sitemap events for a whole sitemap. Not recommended due to potentially high traffic.",
        mixinStandardHelpOptions = true)
public class SitemapEvents implements Runnable {
    @CommandLine.Mixin
    private Options options;

    @CommandLine.Parameters(
            index = "0",
            arity = "1",
            paramLabel = "<subscriptionid>",
            description = "subscription id (required)")
    private String subscriptionid;

    @CommandLine.Option(
            names = "--sitemap",
            paramLabel = "<sitemap>",
            description = "sitemap name (optional)",
            arity = "1")
    private String sitemap;

    private final Console console;
    private final ApiClientBuilder apiClientBuilder;

    /** Creates the command with injected output and REST client services. */
    @Inject
    SitemapEvents(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link Sitemaps#sitemapEvents}. */
    @Override
    public void run() {
        log.debug("Command: Sitemaps.sitemapEvents");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Sitemaps(apiClient);
        endpoint.sitemapEvents(subscriptionid, sitemap);
    }
}
