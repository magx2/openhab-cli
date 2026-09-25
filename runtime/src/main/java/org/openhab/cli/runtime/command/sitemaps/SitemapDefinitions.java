package org.openhab.cli.runtime.command.sitemaps;

import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.engine.endpoint.Sitemaps;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Get all available sitemap definitions. */
@Slf4j
@CommandLine.Command(
        name = "sitemapDefinitions",
        description = "Get all available sitemap definitions.",
        mixinStandardHelpOptions = true)
public class SitemapDefinitions implements Runnable {
    @CommandLine.Mixin
    private Options options;

    private final Console console;
    private final ApiClientBuilder apiClientBuilder;

    /** Creates the command with injected output and REST client services. */
    @Inject
    SitemapDefinitions(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link Sitemaps#sitemapDefinitions}. */
    @Override
    public void run() {
        log.debug("Command: Sitemaps.sitemapDefinitions");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Sitemaps(apiClient);
        var result = endpoint.sitemapDefinitions();
        console.writeJson(result, options.isPrettyPrint());
    }
}
