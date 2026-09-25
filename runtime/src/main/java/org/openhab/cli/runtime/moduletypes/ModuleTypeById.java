package org.openhab.cli.runtime.moduletypes;

import java.util.concurrent.Callable;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.engine.endpoint.ModuleTypes;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Gets a module type corresponding to the given UID. */
@Slf4j
@CommandLine.Command(
        name = "moduleTypeById",
        description = "Gets a module type corresponding to the given UID.",
        mixinStandardHelpOptions = true)
public class ModuleTypeById implements Callable<Integer> {
    @CommandLine.Mixin
    private Options options;

    @CommandLine.Parameters(
            index = "0",
            arity = "1",
            paramLabel = "<moduleTypeUID>",
            description = "moduleTypeUID (required)")
    private String moduleTypeUID;

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
    ModuleTypeById(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link ModuleTypes#moduleTypeById} and returns zero on success. */
    @Override
    public Integer call() {
        log.debug("Command: ModuleTypes.moduleTypeById");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new ModuleTypes(apiClient);
        var result = endpoint.moduleTypeById(moduleTypeUID, acceptLanguage);
        console.writeJson(result, options.isPrettyPrint());
        return 0;
    }
}
