package org.openhab.cli.runtime.transformations;

import java.util.concurrent.Callable;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.engine.endpoint.Transformations;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Get all transformation services */
@Slf4j
@CommandLine.Command(
        name = "transformationServices",
        description = "Get all transformation services",
        mixinStandardHelpOptions = true)
public class TransformationServices implements Callable<Integer> {
    @CommandLine.Mixin
    private Options options;

    private final Console console;
    private final ApiClientBuilder apiClientBuilder;

    /** Creates the command with injected output and REST client services. */
    @Inject
    TransformationServices(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link Transformations#transformationServices} and returns zero on success. */
    @Override
    public Integer call() {
        log.debug("Command: Transformations.transformationServices");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Transformations(apiClient);
        var result = endpoint.transformationServices();
        console.writeJson(result, options.isPrettyPrint());
        return 0;
    }
}
