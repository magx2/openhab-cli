package org.openhab.cli.runtime.command.fileformat;

import com.google.gson.reflect.TypeToken;
import javax.inject.Inject;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.client.model.CanSerializeRulesRequest;
import org.openhab.cli.engine.endpoint.FileFormat;
import org.openhab.cli.runtime.JsonArguments;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Checks if the specified rule(s) can be serialized to the target format. */
@Slf4j
@CommandLine.Command(
        name = "canSerializeRules",
        description = "Checks if the specified rule(s) can be serialized to the target format.",
        mixinStandardHelpOptions = true)
@RequiredArgsConstructor(access = AccessLevel.PACKAGE, onConstructor_ = @Inject)
public class CanSerializeRules implements Runnable {
    @CommandLine.Mixin
    private Options options;

    @CommandLine.Parameters(
            index = "0",
            arity = "0..1",
            paramLabel = "<targetFormat>",
            description = "Target format (optional, default to application/yaml)")
    private String targetFormat;

    @CommandLine.Option(
            names = "--can-serialize-rules-request",
            paramLabel = "<canSerializeRulesRequest>",
            description = "JSON rule data (optional) Supply a JSON value.",
            arity = "1")
    private String canSerializeRulesRequest;

    private final Console console;
    private final ApiClientBuilder apiClientBuilder;

    /** Executes {@link FileFormat#canSerializeRules}. */
    @Override
    public void run() {
        log.debug("Command: FileFormat.canSerializeRules");
        CanSerializeRulesRequest canSerializeRulesRequestValue = JsonArguments.parse(
                canSerializeRulesRequest,
                new TypeToken<CanSerializeRulesRequest>() {}.getType(),
                "canSerializeRulesRequest");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new FileFormat(apiClient);
        var result = endpoint.canSerializeRules(targetFormat, canSerializeRulesRequestValue);
        console.writeJson(result, options.isPrettyPrint());
    }
}
