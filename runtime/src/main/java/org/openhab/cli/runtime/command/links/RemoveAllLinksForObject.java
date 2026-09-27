package org.openhab.cli.runtime.command.links;

import javax.inject.Inject;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.engine.endpoint.Links;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Delete all links that refer to an item or thing. */
@Slf4j
@CommandLine.Command(
        name = "removeAllLinksForObject",
        description = "Delete all links that refer to an item or thing.",
        mixinStandardHelpOptions = true)
@RequiredArgsConstructor(access = AccessLevel.PACKAGE, onConstructor_ = @Inject)
public class RemoveAllLinksForObject implements Runnable {
    @CommandLine.Mixin
    private Options options;

    @CommandLine.Parameters(
            index = "0",
            arity = "1",
            paramLabel = "<_object>",
            description = "item name or thing UID (required)")
    private String _object;

    private final Console console;
    private final ApiClientBuilder apiClientBuilder;

    /** Executes {@link Links#removeAllLinksForObject}. */
    @Override
    public void run() {
        log.debug("Command: Links.removeAllLinksForObject");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Links(apiClient);
        endpoint.removeAllLinksForObject(_object);
    }
}
