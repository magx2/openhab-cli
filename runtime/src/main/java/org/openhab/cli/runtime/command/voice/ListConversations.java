package org.openhab.cli.runtime.command.voice;

import javax.inject.Inject;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor(access = AccessLevel.PACKAGE, onConstructor_ = @Inject)
public class ListConversations implements Runnable {
    @CommandLine.Mixin
    private Options options;

    private final Console console;
    private final ApiClientBuilder apiClientBuilder;

    /** Executes {@link Voice#listConversations}. */
    @Override
    public void run() {
        log.debug("Command: Voice.listConversations");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Voice(apiClient);
        var result = endpoint.listConversations();
        console.writeJson(result, options.isPrettyPrint());
    }
}
