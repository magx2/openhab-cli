package org.openhab.cli.runtime.command.tags;

import java.util.concurrent.Callable;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.engine.endpoint.Tags;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Removes a semantic tag and its sub tags from the registry. */
@Slf4j
@CommandLine.Command(
        name = "removeSemanticTag",
        description = "Removes a semantic tag and its sub tags from the registry.",
        mixinStandardHelpOptions = true)
public class RemoveSemanticTag implements Callable<Integer> {
    @CommandLine.Mixin
    private Options options;

    @CommandLine.Parameters(index = "0", arity = "1", paramLabel = "<tagId>", description = "tag id (required)")
    private String tagId;

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
    RemoveSemanticTag(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link Tags#removeSemanticTag} and returns zero on success. */
    @Override
    public Integer call() {
        log.debug("Command: Tags.removeSemanticTag");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Tags(apiClient);
        endpoint.removeSemanticTag(tagId, acceptLanguage);
        return 0;
    }
}
