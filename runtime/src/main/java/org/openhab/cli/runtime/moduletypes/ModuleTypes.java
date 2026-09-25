package org.openhab.cli.runtime.moduletypes;

import java.util.concurrent.Callable;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Get all available module types. */
@Slf4j
@CommandLine.Command(
        name = "moduleTypes",
        description = "Get all available module types.",
        mixinStandardHelpOptions = true)
public class ModuleTypes implements Callable<Integer> {
    @CommandLine.Mixin
    private Options options;

    @CommandLine.Option(
            names = "--accept-language",
            paramLabel = "<acceptLanguage>",
            description = "language (optional)",
            arity = "1")
    private String acceptLanguage;

    @CommandLine.Option(
            names = "--tags",
            paramLabel = "<tags>",
            description = "tags for filtering (optional)",
            arity = "1")
    private String tags;

    @CommandLine.Option(
            names = "--type",
            paramLabel = "<type>",
            description = "filtering by action, condition or trigger (optional)",
            arity = "1")
    private String type;

    @CommandLine.Option(
            names = "--as-map",
            paramLabel = "<asMap>",
            description = "returns an object of arrays by type instead of a mixed array (optional)",
            arity = "0..1",
            fallbackValue = "true")
    private Boolean asMap;

    private final Console console;
    private final ApiClientBuilder apiClientBuilder;

    /** Creates the command with injected output and REST client services. */
    @Inject
    ModuleTypes(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link org.openhab.cli.engine.endpoint.ModuleTypes#moduleTypes} and returns zero on success. */
    @Override
    public Integer call() {
        log.debug("Command: ModuleTypes.moduleTypes");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new org.openhab.cli.engine.endpoint.ModuleTypes(apiClient);
        var result = endpoint.moduleTypes(acceptLanguage, tags, type, asMap);
        console.writeJson(result, options.isPrettyPrint());
        return 0;
    }
}
