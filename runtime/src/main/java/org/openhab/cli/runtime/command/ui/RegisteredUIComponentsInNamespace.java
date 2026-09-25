package org.openhab.cli.runtime.command.ui;

import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.engine.endpoint.Ui;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Get all registered UI components in the specified namespace. */
@Slf4j
@CommandLine.Command(
        name = "registeredUIComponentsInNamespace",
        description = "Get all registered UI components in the specified namespace.",
        mixinStandardHelpOptions = true)
public class RegisteredUIComponentsInNamespace implements Runnable {
    @CommandLine.Mixin
    private Options options;

    @CommandLine.Parameters(index = "0", arity = "1", paramLabel = "<namespace>", description = "(required)")
    private String namespace;

    @CommandLine.Option(
            names = "--summary",
            paramLabel = "<summary>",
            description = "summary fields only (optional)",
            arity = "0..1",
            fallbackValue = "true")
    private Boolean summary;

    private final Console console;
    private final ApiClientBuilder apiClientBuilder;

    /** Creates the command with injected output and REST client services. */
    @Inject
    RegisteredUIComponentsInNamespace(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link Ui#registeredUIComponentsInNamespace}. */
    @Override
    public void run() {
        log.debug("Command: Ui.registeredUIComponentsInNamespace");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Ui(apiClient);
        var result = endpoint.registeredUIComponentsInNamespace(namespace, summary);
        console.writeJson(result, options.isPrettyPrint());
    }
}
