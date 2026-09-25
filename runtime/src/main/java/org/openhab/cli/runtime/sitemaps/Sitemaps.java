package org.openhab.cli.runtime.sitemaps;

import java.util.concurrent.Callable;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Get all available sitemaps. */
@Slf4j
@CommandLine.Command(name = "sitemaps", description = "Get all available sitemaps.", mixinStandardHelpOptions = true)
public class Sitemaps implements Callable<Integer> {
    @CommandLine.Mixin
    private Options options;

    private final Console console;
    private final ApiClientBuilder apiClientBuilder;

    /** Creates the command with injected output and REST client services. */
    @Inject
    Sitemaps(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link org.openhab.cli.engine.endpoint.Sitemaps#sitemaps} and returns zero on success. */
    @Override
    public Integer call() {
        log.debug("Command: Sitemaps.sitemaps");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new org.openhab.cli.engine.endpoint.Sitemaps(apiClient);
        var result = endpoint.sitemaps();
        console.writeJson(result, options.isPrettyPrint());
        return 0;
    }
}
