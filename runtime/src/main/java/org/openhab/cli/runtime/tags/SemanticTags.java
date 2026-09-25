package org.openhab.cli.runtime.tags;

import java.util.concurrent.Callable;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.engine.endpoint.Tags;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Get all available semantic tags. */
@Slf4j
@CommandLine.Command(
        name = "semanticTags",
        description = "Get all available semantic tags.",
        mixinStandardHelpOptions = true)
public class SemanticTags implements Callable<Integer> {
    @CommandLine.Mixin
    private Options options;

    @CommandLine.Option(
            names = "--accept-language",
            paramLabel = "<acceptLanguage>",
            description = "language (optional)",
            arity = "1")
    private String acceptLanguage;

    private final Console console;
    private final ApiClientBuilder apiClientBuilder;

    /** Creates the command with injected output and REST client services. */
    @Inject
    SemanticTags(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link Tags#semanticTags} and returns zero on success. */
    @Override
    public Integer call() {
        log.debug("Command: Tags.semanticTags");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Tags(apiClient);
        var result = endpoint.semanticTags(acceptLanguage);
        console.writeJson(result, options.isPrettyPrint());
        return 0;
    }
}
