package org.openhab.cli.runtime.command.sitemaps;

import javax.inject.Inject;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.engine.endpoint.Sitemaps;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Polls the data for one page of a sitemap. */
@Slf4j
@CommandLine.Command(
        name = "pollDataForPage",
        description = "Polls the data for one page of a sitemap.",
        mixinStandardHelpOptions = true)
@RequiredArgsConstructor(access = AccessLevel.PACKAGE, onConstructor_ = @Inject)
public class PollDataForPage implements Runnable {
    @CommandLine.Mixin
    private Options options;

    @CommandLine.Parameters(
            index = "0",
            arity = "1",
            paramLabel = "<sitemapname>",
            description = "sitemap name (required)")
    private String sitemapname;

    @CommandLine.Parameters(index = "1", arity = "1", paramLabel = "<pageid>", description = "page id (required)")
    private String pageid;

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

    /** Executes {@link Sitemaps#pollDataForPage}. */
    @Override
    public void run() {
        log.debug("Command: Sitemaps.pollDataForPage");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Sitemaps(apiClient);
        var result = endpoint.pollDataForPage(sitemapname, pageid, acceptLanguage, subscriptionid, includeHidden);
        console.writeJson(result, options.isPrettyPrint());
    }
}
