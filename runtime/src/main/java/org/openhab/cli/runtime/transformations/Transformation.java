package org.openhab.cli.runtime.transformations;

import java.util.concurrent.Callable;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.engine.endpoint.Transformations;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Get a single transformation */
@Slf4j
@CommandLine.Command(
        name = "transformation",
        description = "Get a single transformation",
        mixinStandardHelpOptions = true)
public class Transformation implements Callable<Integer> {
    @CommandLine.Mixin
    private Options options;

    @CommandLine.Parameters(
            index = "0",
            arity = "1",
            paramLabel = "<uid>",
            description = "Transformation UID (required)")
    private String uid;

    private final Console console;
    private final ApiClientBuilder apiClientBuilder;

    /** Creates the command with injected output and REST client services. */
    @Inject
    Transformation(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link Transformations#transformation} and returns zero on success. */
    @Override
    public Integer call() {
        log.debug("Command: Transformations.transformation");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Transformations(apiClient);
        var result = endpoint.transformation(uid);
        console.writeJson(result, options.isPrettyPrint());
        return 0;
    }
}
