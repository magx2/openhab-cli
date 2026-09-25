package org.openhab.cli.runtime.command.sitemaps;

import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.engine.endpoint.Sitemaps;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Get sitemap events. */
@Slf4j
@CommandLine.Command(name = "sitemapEvents1", description = "Get sitemap events.", mixinStandardHelpOptions = true)
public class SitemapEvents1 implements Runnable {
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

    @CommandLine.Option(names = "--pageid", paramLabel = "<pageid>", description = "page id (optional)", arity = "1")
    private String pageid;

    private final Console console;
    private final ApiClientBuilder apiClientBuilder;

    /** Creates the command with injected output and REST client services. */
    @Inject
    SitemapEvents1(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link Sitemaps#sitemapEvents1}. */
    @Override
    public void run() {
        log.debug("Command: Sitemaps.sitemapEvents1");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Sitemaps(apiClient);
        endpoint.sitemapEvents1(subscriptionid, sitemap, pageid);
    }
}
