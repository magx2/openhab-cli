package org.openhab.cli.runtime.command.fileformat;

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

/** Create file format for a list of rule templates in the registry. */
@Slf4j
@CommandLine.Command(
        name = "createFileFormatForRuleTemplates",
        description = "Create file format for a list of rule templates in the registry.",
        mixinStandardHelpOptions = true)
public class CreateFileFormatForRuleTemplates implements Callable<Integer> {
    @CommandLine.Mixin
    private Options options;

    @CommandLine.Parameters(
            index = "0",
            arity = "1",
            paramLabel = "<serializationOption>",
            description = "Decides what to include in serialized rule templates (optional, default to Normal)")
    private String serializationOption;

    @CommandLine.Option(
            names = "--request-body",
            paramLabel = "<requestBody>",
            description =
                    "Array of rule template UIDs. If empty or omitted, return all rule templates. (optional) Supply a JSON value.",
            arity = "1")
    private String requestBody;

    private final Console console;
    private final ApiClientBuilder apiClientBuilder;

    /** Creates the command with injected output and REST client services. */
    @Inject
    CreateFileFormatForRuleTemplates(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link FileFormat#createFileFormatForRuleTemplates} and returns zero on success. */
    @Override
    public Integer call() {
        log.debug("Command: FileFormat.createFileFormatForRuleTemplates");
        List<String> requestBodyValue =
                JsonArguments.parse(requestBody, new TypeToken<List<String>>() {}.getType(), "requestBody");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new FileFormat(apiClient);
        var result = endpoint.createFileFormatForRuleTemplates(serializationOption, requestBodyValue);
        console.writeJson(result, options.isPrettyPrint());
        return 0;
    }
}
