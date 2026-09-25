package org.openhab.cli.runtime.events;

import com.google.gson.reflect.TypeToken;
import java.util.Set;
import java.util.concurrent.Callable;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.engine.endpoint.Events;
import org.openhab.cli.runtime.JsonArguments;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Changes the list of items a SSE connection will receive state updates to. */
@Slf4j
@CommandLine.Command(
        name = "updateItemListForStateUpdates",
        description = "Changes the list of items a SSE connection will receive state updates to.",
        mixinStandardHelpOptions = true)
public class UpdateItemListForStateUpdates implements Callable<Integer> {
    @CommandLine.Mixin
    private Options options;

    @CommandLine.Parameters(index = "0", arity = "1", paramLabel = "<connectionId>", description = "(required)")
    private String connectionId;

    @CommandLine.Option(
            names = "--request-body",
            paramLabel = "<requestBody>",
            description = "items (optional) Supply a JSON value.",
            arity = "1")
    private String requestBody;

    private final Console console;
    private final ApiClientBuilder apiClientBuilder;

    /** Creates the command with injected output and REST client services. */
    @Inject
    UpdateItemListForStateUpdates(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link Events#updateItemListForStateUpdates} and returns zero on success. */
    @Override
    public Integer call() {
        log.debug("Command: Events.updateItemListForStateUpdates");
        Set<String> requestBodyValue =
                JsonArguments.parse(requestBody, new TypeToken<Set<String>>() {}.getType(), "requestBody");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Events(apiClient);
        endpoint.updateItemListForStateUpdates(connectionId, requestBodyValue);
        return 0;
    }
}
