package org.openhab.cli.runtime.command.sitemaps;

import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.engine.endpoint.Sitemaps;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Polls the data for a whole sitemap. Not recommended due to potentially high traffic. */
@Slf4j
@CommandLine.Command(
        name = "pollDataForSitemap",
        description = "Polls the data for a whole sitemap. Not recommended due to potentially high traffic.",
        mixinStandardHelpOptions = true)
public class PollDataForSitemap implements Runnable {
    @CommandLine.Mixin
    private Options options;

    @CommandLine.Parameters(
            index = "0",
            arity = "1",
            paramLabel = "<sitemapname>",
            description = "sitemap name (required)")
    private String sitemapname;

    @CommandLine.Option(
            names = "--accept-language",
            paramLabel = "<acceptLanguage>",
            description = "language (optional)",
            arity = "1")
    private String acceptLanguage;

    @CommandLine.Option(
            names = "--subscriptionid",
            paramLabel = "<subscriptionid>",
            description = "subscriptionid (optional)",
            arity = "1")
    private String subscriptionid;

    @CommandLine.Option(
            names = "--include-hidden",
            paramLabel = "<includeHidden>",
            description = "include hidden widgets (optional)",
            arity = "0..1",
            fallbackValue = "true")
    private Boolean includeHidden;

    private final Console console;
    private final ApiClientBuilder apiClientBuilder;

    /** Creates the command with injected output and REST client services. */
    @Inject
    PollDataForSitemap(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link Sitemaps#pollDataForSitemap}. */
    @Override
    public void run() {
        log.debug("Command: Sitemaps.pollDataForSitemap");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Sitemaps(apiClient);
        var result = endpoint.pollDataForSitemap(sitemapname, acceptLanguage, subscriptionid, includeHidden);
        console.writeJson(result, options.isPrettyPrint());
    }
}
