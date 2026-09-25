package org.openhab.cli.runtime.voice;

import java.util.concurrent.Callable;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.engine.endpoint.Voice;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Get the metadata of all conversations. */
@Slf4j
@CommandLine.Command(
        name = "listConversations",
        description = "Get the metadata of all conversations.",
        mixinStandardHelpOptions = true)
public class ListConversations implements Callable<Integer> {
    @CommandLine.Mixin
    private Options options;

    private final Console console;
    private final ApiClientBuilder apiClientBuilder;

    /** Creates the command with injected output and REST client services. */
    @Inject
    ListConversations(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link Voice#listConversations} and returns zero on success. */
    @Override
    public Integer call() {
        log.debug("Command: Voice.listConversations");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Voice(apiClient);
        var result = endpoint.listConversations();
        console.writeJson(result, options.isPrettyPrint());
        return 0;
    }
}
