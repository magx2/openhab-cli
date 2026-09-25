package org.openhab.cli.runtime.command.rules;

import com.google.gson.reflect.TypeToken;
import java.util.List;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.runtime.JsonArguments;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Get available rules, optionally filtered by tags and/or prefix. */
@Slf4j
@CommandLine.Command(
        name = "rules",
        description = "Get available rules, optionally filtered by tags and/or prefix.",
        mixinStandardHelpOptions = true)
public class Rules implements Runnable {
    @CommandLine.Mixin
    private Options options;

    @CommandLine.Option(names = "--prefix", paramLabel = "<prefix>", description = "(optional)", arity = "1")
    private String prefix;

    @CommandLine.Option(
            names = "--tags",
            paramLabel = "<tags>",
            description = "(optional) Supply a JSON value.",
            arity = "1")
    private String tags;

    @CommandLine.Option(
            names = "--summary",
            paramLabel = "<summary>",
            description = "summary fields only (optional)",
            arity = "0..1",
            fallbackValue = "true")
    private Boolean summary;

    @CommandLine.Parameters(
            index = "0",
            arity = "1",
            paramLabel = "<staticDataOnly>",
            description =
                    "provides a cacheable list of values not expected to change regularly and honors the If-Modified-Since header, all other parameters are ignored (optional, default to false)")
    private Boolean staticDataOnly;

    private final Console console;
    private final ApiClientBuilder apiClientBuilder;

    /** Creates the command with injected output and REST client services. */
    @Inject
    Rules(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link org.openhab.cli.engine.endpoint.Rules#rules}. */
    @Override
    public void run() {
        log.debug("Command: Rules.rules");
        List<String> tagsValue = JsonArguments.parse(tags, new TypeToken<List<String>>() {}.getType(), "tags");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new org.openhab.cli.engine.endpoint.Rules(apiClient);
        var result = endpoint.rules(prefix, tagsValue, summary, staticDataOnly);
        console.writeJson(result, options.isPrettyPrint());
    }
}
