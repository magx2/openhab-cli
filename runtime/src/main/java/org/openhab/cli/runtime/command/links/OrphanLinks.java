package org.openhab.cli.runtime.command.links;

import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.engine.endpoint.Links;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Get orphan links between items and broken/non-existent thing channels */
@Slf4j
@CommandLine.Command(
        name = "orphanLinks",
        description = "Get orphan links between items and broken/non-existent thing channels",
        mixinStandardHelpOptions = true)
public class OrphanLinks implements Runnable {
    @CommandLine.Mixin
    private Options options;

    private final Console console;
    private final ApiClientBuilder apiClientBuilder;

    /** Creates the command with injected output and REST client services. */
    @Inject
    OrphanLinks(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link Links#orphanLinks}. */
    @Override
    public void run() {
        log.debug("Command: Links.orphanLinks");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Links(apiClient);
        var result = endpoint.orphanLinks();
        console.writeJson(result, options.isPrettyPrint());
    }
}
