package org.openhab.cli.runtime.command.templates;

import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.engine.endpoint.Templates;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Gets a template corresponding to the given UID. */
@Slf4j
@CommandLine.Command(
        name = "templateById",
        description = "Gets a template corresponding to the given UID.",
        mixinStandardHelpOptions = true)
public class TemplateById implements Runnable {
    @CommandLine.Mixin
    private Options options;

    @CommandLine.Parameters(
            index = "0",
            arity = "1",
            paramLabel = "<templateUID>",
            description = "templateUID (required)")
    private String templateUID;

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
    TemplateById(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link Templates#templateById}. */
    @Override
    public void run() {
        log.debug("Command: Templates.templateById");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Templates(apiClient);
        var result = endpoint.templateById(templateUID, acceptLanguage);
        console.writeJson(result, options.isPrettyPrint());
    }
}
