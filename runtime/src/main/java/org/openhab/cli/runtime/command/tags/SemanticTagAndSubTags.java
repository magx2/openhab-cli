package org.openhab.cli.runtime.command.tags;

import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.engine.endpoint.Tags;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Gets a semantic tag and its sub tags. */
@Slf4j
@CommandLine.Command(
        name = "semanticTagAndSubTags",
        description = "Gets a semantic tag and its sub tags.",
        mixinStandardHelpOptions = true)
public class SemanticTagAndSubTags implements Runnable {
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
    SemanticTagAndSubTags(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link Tags#semanticTagAndSubTags}. */
    @Override
    public void run() {
        log.debug("Command: Tags.semanticTagAndSubTags");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Tags(apiClient);
        var result = endpoint.semanticTagAndSubTags(tagId, acceptLanguage);
        console.writeJson(result, options.isPrettyPrint());
    }
}
