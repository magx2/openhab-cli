package org.openhab.cli.runtime.sitemaps;

import java.util.concurrent.Callable;
import javax.inject.Inject;
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
public class PollDataForPage implements Callable<Integer> {
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

    /** Creates the command with injected output and REST client services. */
    @Inject
    PollDataForPage(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link Sitemaps#pollDataForPage} and returns zero on success. */
    @Override
    public Integer call() {
        log.debug("Command: Sitemaps.pollDataForPage");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Sitemaps(apiClient);
        var result = endpoint.pollDataForPage(sitemapname, pageid, acceptLanguage, subscriptionid, includeHidden);
        console.writeJson(result, options.isPrettyPrint());
        return 0;
    }
}
