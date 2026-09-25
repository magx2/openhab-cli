package org.openhab.cli.runtime.command.fileformat;

import com.google.gson.reflect.TypeToken;
import java.util.List;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.engine.endpoint.FileFormat;
import org.openhab.cli.runtime.JsonArguments;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Create file format for a list of things in things or discovery registry. */
@Slf4j
@CommandLine.Command(
        name = "createFileFormatForThings",
        description = "Create file format for a list of things in things or discovery registry.",
        mixinStandardHelpOptions = true)
public class CreateFileFormatForThings implements Runnable {
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
            description =
                    "Array of Thing UIDs. If empty or omitted, return all Things from the Registry. (optional) Supply a JSON value.",
            arity = "1")
    private String requestBody;

    private final Console console;
    private final ApiClientBuilder apiClientBuilder;

    /** Creates the command with injected output and REST client services. */
    @Inject
    CreateFileFormatForThings(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link FileFormat#createFileFormatForThings}. */
    @Override
    public void run() {
        log.debug("Command: FileFormat.createFileFormatForThings");
        List<String> requestBodyValue =
                JsonArguments.parse(requestBody, new TypeToken<List<String>>() {}.getType(), "requestBody");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new FileFormat(apiClient);
        var result = endpoint.createFileFormatForThings(hideDefaultParameters, requestBodyValue);
        console.writeJson(result, options.isPrettyPrint());
    }
}
