package org.openhab.cli.runtime.sitemaps;

import java.util.concurrent.Callable;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.engine.endpoint.Sitemaps;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Get sitemap definition by name. */
@Slf4j
@CommandLine.Command(
        name = "sitemapDefinitionByName",
        description = "Get sitemap definition by name.",
        mixinStandardHelpOptions = true)
public class SitemapDefinitionByName implements Callable<Integer> {
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

    /** Creates the command with injected output and REST client services. */
    @Inject
    SitemapDefinitionByName(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link Sitemaps#sitemapDefinitionByName} and returns zero on success. */
    @Override
    public Integer call() {
        log.debug("Command: Sitemaps.sitemapDefinitionByName");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Sitemaps(apiClient);
        var result = endpoint.sitemapDefinitionByName(sitemapname);
        console.writeJson(result, options.isPrettyPrint());
        return 0;
    }
}
