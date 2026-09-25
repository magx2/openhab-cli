package org.openhab.cli.runtime.command.fileformat;

import com.google.gson.reflect.TypeToken;
import java.util.concurrent.Callable;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.engine.endpoint.FileFormat;
import org.openhab.cli.runtime.JsonArguments;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Create file format. */
@Slf4j
@CommandLine.Command(name = "create", description = "Create file format.", mixinStandardHelpOptions = true)
public class Create implements Callable<Integer> {
    @CommandLine.Mixin
    private Options options;

    @CommandLine.Parameters(
            index = "0",
            arity = "1",
            paramLabel = "<fileFormat>",
            description = "JSON data (required) Supply a JSON value.")
    private String fileFormat;

    @CommandLine.Parameters(
            index = "1",
            arity = "1",
            paramLabel = "<hideDefaultParameters>",
            description =
                    "if true, exclude the configuration parameters having the default value from the result. (optional, default to false)")
    private Boolean hideDefaultParameters;

    @CommandLine.Parameters(
            index = "2",
            arity = "1",
            paramLabel = "<hideDefaultChannels>",
            description =
                    "if true, exclude the non extensible channels having a default configuration from the result. (optional, default to false)")
    private Boolean hideDefaultChannels;

    @CommandLine.Parameters(
            index = "3",
            arity = "1",
            paramLabel = "<hideChannelLinksAndMetadata>",
            description =
                    "if true, exclude the channel links and metadata for items from the result. (optional, default to false)")
    private Boolean hideChannelLinksAndMetadata;

    @CommandLine.Parameters(
            index = "4",
            arity = "1",
            paramLabel = "<ruleSerializationOption>",
            description =
                    "Decides what to include in serialized rules and rule templates (optional, default to Normal)")
    private String ruleSerializationOption;

    private final Console console;
    private final ApiClientBuilder apiClientBuilder;

    /** Creates the command with injected output and REST client services. */
    @Inject
    Create(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link FileFormat#create} and returns zero on success. */
    @Override
    public Integer call() {
        log.debug("Command: FileFormat.create");
        org.openhab.cli.client.model.FileFormat fileFormatValue = JsonArguments.parse(
                fileFormat, new TypeToken<org.openhab.cli.client.model.FileFormat>() {}.getType(), "fileFormat");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new FileFormat(apiClient);
        var result = endpoint.create(
                fileFormatValue,
                hideDefaultParameters,
                hideDefaultChannels,
                hideChannelLinksAndMetadata,
                ruleSerializationOption);
        console.writeJson(result, options.isPrettyPrint());
        return 0;
    }
}
