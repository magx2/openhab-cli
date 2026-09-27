package org.openhab.cli.runtime.command.sitemaps;

import javax.inject.Inject;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Get all available sitemaps. */
@Slf4j
@CommandLine.Command(name = "sitemaps", description = "Get all available sitemaps.", mixinStandardHelpOptions = true)
@RequiredArgsConstructor(access = AccessLevel.PACKAGE, onConstructor_ = @Inject)
public class Sitemaps implements Runnable {
    @CommandLine.Mixin
    private Options options;

    private final Console console;
    private final ApiClientBuilder apiClientBuilder;

    /** Executes {@link org.openhab.cli.engine.endpoint.Sitemaps#sitemaps}. */
    @Override
    public void run() {
        log.debug("Command: Sitemaps.sitemaps");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new org.openhab.cli.engine.endpoint.Sitemaps(apiClient);
        var result = endpoint.sitemaps();
        console.writeJson(result, options.isPrettyPrint());
    }
}
