package org.openhab.cli.runtime.things;

import java.util.concurrent.Callable;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Get all available things. */
@Slf4j
@CommandLine.Command(name = "things", description = "Get all available things.", mixinStandardHelpOptions = true)
public class Things implements Callable<Integer> {
    @CommandLine.Mixin
    private Options options;

    @CommandLine.Option(
            names = "--accept-language",
            paramLabel = "<acceptLanguage>",
            description = "language (optional)",
            arity = "1")
    private String acceptLanguage;

    @CommandLine.Option(
            names = "--summary",
            paramLabel = "<summary>",
            description = "summary fields only (optional)",
            arity = "0..1",
            fallbackValue = "true")
    private Boolean summary;

    @CommandLine.Parameters(
            index = "0",
            arity = "1",
            paramLabel = "<staticDataOnly>",
            description =
                    "provides a cacheable list of values not expected to change regularly and checks the If-Modified-Since header (optional, default to false)")
    private Boolean staticDataOnly;

    private final Console console;
    private final ApiClientBuilder apiClientBuilder;

    /** Creates the command with injected output and REST client services. */
    @Inject
    Things(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link org.openhab.cli.engine.endpoint.Things#things} and returns zero on success. */
    @Override
    public Integer call() {
        log.debug("Command: Things.things");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new org.openhab.cli.engine.endpoint.Things(apiClient);
        var result = endpoint.things(acceptLanguage, summary, staticDataOnly);
        console.writeJson(result, options.isPrettyPrint());
        return 0;
    }
}
