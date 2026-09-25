package org.openhab.cli.runtime.command.sitemaps;

import com.google.gson.reflect.TypeToken;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.client.model.SitemapDefinition;
import org.openhab.cli.engine.endpoint.Sitemaps;
import org.openhab.cli.runtime.JsonArguments;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Adds a new sitemap to the registry or updates the existing sitemap. */
@Slf4j
@CommandLine.Command(
        name = "addOrUpdateSitemapInRegistry",
        description = "Adds a new sitemap to the registry or updates the existing sitemap.",
        mixinStandardHelpOptions = true)
public class AddOrUpdateSitemapInRegistry implements Runnable {
    @CommandLine.Mixin
    private Options options;

    @CommandLine.Parameters(
            index = "0",
            arity = "1",
            paramLabel = "<sitemapname>",
            description = "sitemap name (required)")
    private String sitemapname;

    @CommandLine.Parameters(
            index = "1",
            arity = "1",
            paramLabel = "<sitemapDefinition>",
            description = "sitemap data (required) Supply a JSON value.")
    private String sitemapDefinition;

    private final Console console;
    private final ApiClientBuilder apiClientBuilder;

    /** Creates the command with injected output and REST client services. */
    @Inject
    AddOrUpdateSitemapInRegistry(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link Sitemaps#addOrUpdateSitemapInRegistry}. */
    @Override
    public void run() {
        log.debug("Command: Sitemaps.addOrUpdateSitemapInRegistry");
        SitemapDefinition sitemapDefinitionValue = JsonArguments.parse(
                sitemapDefinition, new TypeToken<SitemapDefinition>() {}.getType(), "sitemapDefinition");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Sitemaps(apiClient);
        var result = endpoint.addOrUpdateSitemapInRegistry(sitemapname, sitemapDefinitionValue);
        console.writeJson(result, options.isPrettyPrint());
    }
}
