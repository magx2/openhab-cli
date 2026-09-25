package org.openhab.cli.runtime.command.tags;

import com.google.gson.reflect.TypeToken;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.client.model.EnrichedSemanticTag;
import org.openhab.cli.engine.endpoint.Tags;
import org.openhab.cli.runtime.JsonArguments;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Updates a semantic tag. */
@Slf4j
@CommandLine.Command(
        name = "updateSemanticTag",
        description = "Updates a semantic tag.",
        mixinStandardHelpOptions = true)
public class UpdateSemanticTag implements Runnable {
    @CommandLine.Mixin
    private Options options;

    @CommandLine.Parameters(index = "0", arity = "1", paramLabel = "<tagId>", description = "tag id (required)")
    private String tagId;

    @CommandLine.Parameters(
            index = "1",
            arity = "1",
            paramLabel = "<enrichedSemanticTag>",
            description = "tag data (required) Supply a JSON value.")
    private String enrichedSemanticTag;

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
    UpdateSemanticTag(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link Tags#updateSemanticTag}. */
    @Override
    public void run() {
        log.debug("Command: Tags.updateSemanticTag");
        EnrichedSemanticTag enrichedSemanticTagValue = JsonArguments.parse(
                enrichedSemanticTag, new TypeToken<EnrichedSemanticTag>() {}.getType(), "enrichedSemanticTag");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Tags(apiClient);
        var result = endpoint.updateSemanticTag(tagId, enrichedSemanticTagValue, acceptLanguage);
        console.writeJson(result, options.isPrettyPrint());
    }
}
