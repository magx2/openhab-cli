package org.openhab.cli.runtime.command.ui;

import java.util.concurrent.Callable;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.engine.endpoint.Ui;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Remove a specific UI component in the specified namespace. */
@Slf4j
@CommandLine.Command(
        name = "removeUIComponentFromNamespace",
        description = "Remove a specific UI component in the specified namespace.",
        mixinStandardHelpOptions = true)
public class RemoveUIComponentFromNamespace implements Callable<Integer> {
    @CommandLine.Mixin
    private Options options;

    @CommandLine.Parameters(index = "0", arity = "1", paramLabel = "<namespace>", description = "(required)")
    private String namespace;

    @CommandLine.Parameters(index = "1", arity = "1", paramLabel = "<componentUID>", description = "(required)")
    private String componentUID;

    private final Console console;
    private final ApiClientBuilder apiClientBuilder;

    /** Creates the command with injected output and REST client services. */
    @Inject
    RemoveUIComponentFromNamespace(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link Ui#removeUIComponentFromNamespace} and returns zero on success. */
    @Override
    public Integer call() {
        log.debug("Command: Ui.removeUIComponentFromNamespace");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Ui(apiClient);
        endpoint.removeUIComponentFromNamespace(namespace, componentUID);
        return 0;
    }
}
