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

/** Create file format for a list of sitemaps in registry. */
@Slf4j
@CommandLine.Command(
        name = "createFileFormatForSitemaps",
        description = "Create file format for a list of sitemaps in registry.",
        mixinStandardHelpOptions = true)
public class CreateFileFormatForSitemaps implements Callable<Integer> {
    @CommandLine.Mixin
    private Options options;

    @CommandLine.Option(
            names = "--request-body",
            paramLabel = "<requestBody>",
            description =
                    "Array of Sitemap names. If empty or omitted, return all Sitemaps from the Registry. (optional) Supply a JSON value.",
            arity = "1")
    private String requestBody;

    private final Console console;
    private final ApiClientBuilder apiClientBuilder;

    /** Creates the command with injected output and REST client services. */
    @Inject
    CreateFileFormatForSitemaps(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link FileFormat#createFileFormatForSitemaps} and returns zero on success. */
    @Override
    public Integer call() {
        log.debug("Command: FileFormat.createFileFormatForSitemaps");
        List<String> requestBodyValue =
                JsonArguments.parse(requestBody, new TypeToken<List<String>>() {}.getType(), "requestBody");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new FileFormat(apiClient);
        var result = endpoint.createFileFormatForSitemaps(requestBodyValue);
        console.writeJson(result, options.isPrettyPrint());
        return 0;
    }
}
