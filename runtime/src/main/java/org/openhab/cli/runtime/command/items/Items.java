package org.openhab.cli.runtime.command.items;

import java.util.concurrent.Callable;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Get all available items. */
@Slf4j
@CommandLine.Command(name = "items", description = "Get all available items.", mixinStandardHelpOptions = true)
public class Items implements Callable<Integer> {
    @CommandLine.Mixin
    private Options options;

    @CommandLine.Option(
            names = "--accept-language",
            paramLabel = "<acceptLanguage>",
            description = "language (optional)",
            arity = "1")
    private String acceptLanguage;

    @CommandLine.Option(
            names = "--type",
            paramLabel = "<type>",
            description = "item type filter (optional)",
            arity = "1")
    private String type;

    @CommandLine.Option(
            names = "--tags",
            paramLabel = "<tags>",
            description = "item tag filter (optional)",
            arity = "1")
    private String tags;

    @CommandLine.Parameters(
            index = "0",
            arity = "1",
            paramLabel = "<metadata>",
            description =
                    "metadata selector - a comma separated list or a regular expression (returns all if no value given) (optional, default to .*)")
    private String metadata;

    @CommandLine.Parameters(
            index = "1",
            arity = "1",
            paramLabel = "<recursive>",
            description = "get member items recursively (optional, default to false)")
    private Boolean recursive;

    @CommandLine.Parameters(
            index = "2",
            arity = "1",
            paramLabel = "<parents>",
            description = "get parent group items recursively (optional, default to false)")
    private Boolean parents;

    @CommandLine.Option(
            names = "--fields",
            paramLabel = "<fields>",
            description = "limit output to the given fields (comma separated) (optional)",
            arity = "1")
    private String fields;

    @CommandLine.Parameters(
            index = "3",
            arity = "1",
            paramLabel = "<staticDataOnly>",
            description =
                    "provides a cacheable list of values not expected to change regularly and checks the If-Modified-Since header, all other parameters are ignored except \\\"metadata\\\" (optional, default to false)")
    private Boolean staticDataOnly;

    private final Console console;
    private final ApiClientBuilder apiClientBuilder;

    /** Creates the command with injected output and REST client services. */
    @Inject
    Items(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link org.openhab.cli.engine.endpoint.Items#items} and returns zero on success. */
    @Override
    public Integer call() {
        log.debug("Command: Items.items");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new org.openhab.cli.engine.endpoint.Items(apiClient);
        var result = endpoint.items(acceptLanguage, type, tags, metadata, recursive, parents, fields, staticDataOnly);
        console.writeJson(result, options.isPrettyPrint());
        return 0;
    }
}
