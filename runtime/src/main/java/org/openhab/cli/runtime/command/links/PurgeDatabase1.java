package org.openhab.cli.runtime.command.links;

import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.engine.endpoint.Links;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Remove unused/orphaned links. */
@Slf4j
@CommandLine.Command(
        name = "purgeDatabase1",
        description = "Remove unused/orphaned links.",
        mixinStandardHelpOptions = true)
public class PurgeDatabase1 implements Runnable {
    @CommandLine.Mixin
    private Options options;

    private final Console console;
    private final ApiClientBuilder apiClientBuilder;

    /** Creates the command with injected output and REST client services. */
    @Inject
    PurgeDatabase1(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link Links#purgeDatabase1}. */
    @Override
    public void run() {
        log.debug("Command: Links.purgeDatabase1");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Links(apiClient);
        endpoint.purgeDatabase1();
    }
}
