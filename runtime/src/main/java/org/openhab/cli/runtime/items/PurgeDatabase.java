package org.openhab.cli.runtime.items;

import java.util.concurrent.Callable;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.engine.endpoint.Items;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Remove unused/orphaned metadata. */
@Slf4j
@CommandLine.Command(
        name = "purgeDatabase",
        description = "Remove unused/orphaned metadata.",
        mixinStandardHelpOptions = true)
public class PurgeDatabase implements Callable<Integer> {
    @CommandLine.Mixin
    private Options options;

    private final Console console;
    private final ApiClientBuilder apiClientBuilder;

    /** Creates the command with injected output and REST client services. */
    @Inject
    PurgeDatabase(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link Items#purgeDatabase} and returns zero on success. */
    @Override
    public Integer call() {
        log.debug("Command: Items.purgeDatabase");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Items(apiClient);
        endpoint.purgeDatabase();
        return 0;
    }
}
