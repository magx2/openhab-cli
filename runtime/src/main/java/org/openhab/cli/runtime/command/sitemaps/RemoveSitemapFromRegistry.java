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

/** Removes a sitemap from the registry. */
@Slf4j
@CommandLine.Command(
        name = "removeSitemapFromRegistry",
        description = "Removes a sitemap from the registry.",
        mixinStandardHelpOptions = true)
@RequiredArgsConstructor(access = AccessLevel.PACKAGE, onConstructor_ = @Inject)
public class RemoveSitemapFromRegistry implements Runnable {
    @CommandLine.Mixin
    private Options options;

    @CommandLine.Parameters(
            index = "0",
            arity = "1",
            paramLabel = "<sitemapname>",
            description = "sitemap name (required)")
    private String sitemapname;

    private final Console console;
    private final ApiClientBuilder apiClientBuilder;

    /** Executes {@link Sitemaps#removeSitemapFromRegistry}. */
    @Override
    public void run() {
        log.debug("Command: Sitemaps.removeSitemapFromRegistry");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Sitemaps(apiClient);
        endpoint.removeSitemapFromRegistry(sitemapname);
    }
}
