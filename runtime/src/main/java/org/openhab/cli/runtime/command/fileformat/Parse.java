package org.openhab.cli.runtime.command.fileformat;

import javax.inject.Inject;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.engine.endpoint.FileFormat;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Parse file format. */
@Slf4j
@CommandLine.Command(name = "parse", description = "Parse file format.", mixinStandardHelpOptions = true)
@RequiredArgsConstructor(access = AccessLevel.PACKAGE, onConstructor_ = @Inject)
public class Parse implements Runnable {
    @CommandLine.Mixin
    private Options options;

    @CommandLine.Parameters(
            index = "0",
            arity = "1",
            paramLabel = "<body>",
            description = "file format syntax (required)")
    private String body;

    private final Console console;
    private final ApiClientBuilder apiClientBuilder;

    /** Executes {@link FileFormat#parse}. */
    @Override
    public void run() {
        log.debug("Command: FileFormat.parse");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new FileFormat(apiClient);
        var result = endpoint.parse(body);
        console.writeJson(result, options.isPrettyPrint());
    }
}
