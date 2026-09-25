package org.openhab.cli.runtime.tags;

import com.google.gson.reflect.TypeToken;
import java.util.concurrent.Callable;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.client.model.EnrichedSemanticTag;
import org.openhab.cli.engine.endpoint.Tags;
import org.openhab.cli.runtime.JsonArguments;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Creates a new semantic tag and adds it to the registry. */
@Slf4j
@CommandLine.Command(
        name = "createSemanticTag",
        description = "Creates a new semantic tag and adds it to the registry.",
        mixinStandardHelpOptions = true)
public class CreateSemanticTag implements Callable<Integer> {
    @CommandLine.Mixin
    private Options options;

    @CommandLine.Parameters(
            index = "0",
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
    CreateSemanticTag(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link Tags#createSemanticTag} and returns zero on success. */
    @Override
    public Integer call() {
        log.debug("Command: Tags.createSemanticTag");
        EnrichedSemanticTag enrichedSemanticTagValue = JsonArguments.parse(
                enrichedSemanticTag, new TypeToken<EnrichedSemanticTag>() {}.getType(), "enrichedSemanticTag");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Tags(apiClient);
        var result = endpoint.createSemanticTag(enrichedSemanticTagValue, acceptLanguage);
        console.writeJson(result, options.isPrettyPrint());
        return 0;
    }
}
