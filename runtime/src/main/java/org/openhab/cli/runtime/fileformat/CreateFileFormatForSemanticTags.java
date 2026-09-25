package org.openhab.cli.runtime.fileformat;

import com.google.gson.reflect.TypeToken;
import java.util.List;
import java.util.concurrent.Callable;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.engine.endpoint.FileFormat;
import org.openhab.cli.runtime.JsonArguments;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Create file format for a list of semantic tags in registry. */
@Slf4j
@CommandLine.Command(
        name = "createFileFormatForSemanticTags",
        description = "Create file format for a list of semantic tags in registry.",
        mixinStandardHelpOptions = true)
public class CreateFileFormatForSemanticTags implements Callable<Integer> {
    @CommandLine.Mixin
    private Options options;

    @CommandLine.Parameters(
            index = "0",
            arity = "1",
            paramLabel = "<hideNonEditableTags>",
            description =
                    "if true, exclude the non editable semantic tags from the result. (optional, default to false)")
    private Boolean hideNonEditableTags;

    @CommandLine.Parameters(
            index = "1",
            arity = "1",
            paramLabel = "<hideDefaultTags>",
            description = "if true, exclude the default semantic tags from the result. (optional, default to false)")
    private Boolean hideDefaultTags;

    @CommandLine.Option(
            names = "--request-body",
            paramLabel = "<requestBody>",
            description =
                    "Array of semantic tag UIDs. If empty or omitted, return all custom semantic tags from the Registry. (optional) Supply a JSON value.",
            arity = "1")
    private String requestBody;

    private final Console console;
    private final ApiClientBuilder apiClientBuilder;

    /** Creates the command with injected output and REST client services. */
    @Inject
    CreateFileFormatForSemanticTags(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link FileFormat#createFileFormatForSemanticTags} and returns zero on success. */
    @Override
    public Integer call() {
        log.debug("Command: FileFormat.createFileFormatForSemanticTags");
        List<String> requestBodyValue =
                JsonArguments.parse(requestBody, new TypeToken<List<String>>() {}.getType(), "requestBody");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new FileFormat(apiClient);
        var result = endpoint.createFileFormatForSemanticTags(hideNonEditableTags, hideDefaultTags, requestBodyValue);
        console.writeJson(result, options.isPrettyPrint());
        return 0;
    }
}
