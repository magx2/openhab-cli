package org.openhab.cli.runtime.ui;

import com.google.gson.reflect.TypeToken;
import java.util.concurrent.Callable;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.client.model.RootUIComponent;
import org.openhab.cli.engine.endpoint.Ui;
import org.openhab.cli.runtime.JsonArguments;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Update a specific UI component in the specified namespace. */
@Slf4j
@CommandLine.Command(
        name = "updateUIComponentInNamespace",
        description = "Update a specific UI component in the specified namespace.",
        mixinStandardHelpOptions = true)
public class UpdateUIComponentInNamespace implements Callable<Integer> {
    @CommandLine.Mixin
    private Options options;

    @CommandLine.Parameters(index = "0", arity = "1", paramLabel = "<namespace>", description = "(required)")
    private String namespace;

    @CommandLine.Parameters(index = "1", arity = "1", paramLabel = "<componentUID>", description = "(required)")
    private String componentUID;

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
    UpdateUIComponentInNamespace(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link Ui#updateUIComponentInNamespace} and returns zero on success. */
    @Override
    public Integer call() {
        log.debug("Command: Ui.updateUIComponentInNamespace");
        RootUIComponent rootUIComponentValue =
                JsonArguments.parse(rootUIComponent, new TypeToken<RootUIComponent>() {}.getType(), "rootUIComponent");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Ui(apiClient);
        var result = endpoint.updateUIComponentInNamespace(namespace, componentUID, rootUIComponentValue);
        console.writeJson(result, options.isPrettyPrint());
        return 0;
    }
}
