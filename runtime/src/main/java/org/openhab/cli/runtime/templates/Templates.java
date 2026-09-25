package org.openhab.cli.runtime.templates;

import java.util.concurrent.Callable;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Get all available templates. */
@Slf4j
@CommandLine.Command(name = "templates", description = "Get all available templates.", mixinStandardHelpOptions = true)
public class Templates implements Callable<Integer> {
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
    Templates(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link org.openhab.cli.engine.endpoint.Templates#templates} and returns zero on success. */
    @Override
    public Integer call() {
        log.debug("Command: Templates.templates");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new org.openhab.cli.engine.endpoint.Templates(apiClient);
        var result = endpoint.templates(acceptLanguage);
        console.writeJson(result, options.isPrettyPrint());
        return 0;
    }
}
