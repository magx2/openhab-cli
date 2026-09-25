package org.openhab.cli.runtime.command.ui;

import com.google.gson.reflect.TypeToken;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.client.model.RootUIComponent;
import org.openhab.cli.engine.endpoint.Ui;
import org.openhab.cli.runtime.JsonArguments;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Add a UI component in the specified namespace. */
@Slf4j
@CommandLine.Command(
        name = "addUIComponentToNamespace",
        description = "Add a UI component in the specified namespace.",
        mixinStandardHelpOptions = true)
public class AddUIComponentToNamespace implements Runnable {
    @CommandLine.Mixin
    private Options options;

    @CommandLine.Parameters(index = "0", arity = "1", paramLabel = "<namespace>", description = "(required)")
    private String namespace;

    @CommandLine.Option(
            names = "--root-uicomponent",
            paramLabel = "<rootUIComponent>",
            description = "(optional) Supply a JSON value.",
            arity = "1")
    private String rootUIComponent;

    private final Console console;
    private final ApiClientBuilder apiClientBuilder;

    /** Creates the command with injected output and REST client services. */
    @Inject
    AddUIComponentToNamespace(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link Ui#addUIComponentToNamespace}. */
    @Override
    public void run() {
        log.debug("Command: Ui.addUIComponentToNamespace");
        RootUIComponent rootUIComponentValue =
                JsonArguments.parse(rootUIComponent, new TypeToken<RootUIComponent>() {}.getType(), "rootUIComponent");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Ui(apiClient);
        var result = endpoint.addUIComponentToNamespace(namespace, rootUIComponentValue);
        console.writeJson(result, options.isPrettyPrint());
    }
}
