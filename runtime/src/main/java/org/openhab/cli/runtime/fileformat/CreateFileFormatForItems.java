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

/** Create file format for a list of items in registry. */
@Slf4j
@CommandLine.Command(
        name = "createFileFormatForItems",
        description = "Create file format for a list of items in registry.",
        mixinStandardHelpOptions = true)
public class CreateFileFormatForItems implements Callable<Integer> {
    @CommandLine.Mixin
    private Options options;

    @CommandLine.Parameters(
            index = "0",
            arity = "1",
            paramLabel = "<hideDefaultParameters>",
            description =
                    "if true, exclude the configuration parameters having the default value from the result. (optional, default to true)")
    private Boolean hideDefaultParameters;

    @CommandLine.Option(
            names = "--request-body",
            paramLabel = "<requestBody>",
            description = "Array of item names. If empty or omitted, return all Items. (optional) Supply a JSON value.",
            arity = "1")
    private String requestBody;

    private final Console console;
    private final ApiClientBuilder apiClientBuilder;

    /** Creates the command with injected output and REST client services. */
    @Inject
    CreateFileFormatForItems(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link FileFormat#createFileFormatForItems} and returns zero on success. */
    @Override
    public Integer call() {
        log.debug("Command: FileFormat.createFileFormatForItems");
        List<String> requestBodyValue =
                JsonArguments.parse(requestBody, new TypeToken<List<String>>() {}.getType(), "requestBody");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new FileFormat(apiClient);
        var result = endpoint.createFileFormatForItems(hideDefaultParameters, requestBodyValue);
        console.writeJson(result, options.isPrettyPrint());
        return 0;
    }
}
