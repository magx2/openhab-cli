package org.openhab.cli.runtime.command.fileformat;

import com.google.gson.reflect.TypeToken;
import java.util.List;
import javax.inject.Inject;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor(access = AccessLevel.PACKAGE, onConstructor_ = @Inject)
public class CreateFileFormatForSemanticTags implements Runnable {
    @CommandLine.Mixin
    private Options options;

    @CommandLine.Parameters(
            index = "0",
            arity = "0..1",
            paramLabel = "<hideNonEditableTags>",
            description =
                    "if true, exclude the non editable semantic tags from the result. (optional, default to false)")
    private Boolean hideNonEditableTags;

    @CommandLine.Parameters(
            index = "1",
            arity = "0..1",
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

    /** Executes {@link FileFormat#createFileFormatForSemanticTags}. */
    @Override
    public void run() {
        log.debug("Command: FileFormat.createFileFormatForSemanticTags");
        List<String> requestBodyValue =
                JsonArguments.parse(requestBody, new TypeToken<List<String>>() {}.getType(), "requestBody");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new FileFormat(apiClient);
        var result = endpoint.createFileFormatForSemanticTags(hideNonEditableTags, hideDefaultTags, requestBodyValue);
        console.writeJson(result, options.isPrettyPrint());
    }
}
