package org.openhab.cli.runtime.items;

import java.util.concurrent.Callable;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.engine.endpoint.Items;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Gets configuration problems with item semantics. */
@Slf4j
@CommandLine.Command(
        name = "semanticsHealth",
        description = "Gets configuration problems with item semantics.",
        mixinStandardHelpOptions = true)
public class SemanticsHealth implements Callable<Integer> {
    @CommandLine.Mixin
    private Options options;

    private final Console console;
    private final ApiClientBuilder apiClientBuilder;

    /** Creates the command with injected output and REST client services. */
    @Inject
    SemanticsHealth(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link Items#semanticsHealth} and returns zero on success. */
    @Override
    public Integer call() {
        log.debug("Command: Items.semanticsHealth");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Items(apiClient);
        var result = endpoint.semanticsHealth();
        console.writeJson(result, options.isPrettyPrint());
        return 0;
    }
}
