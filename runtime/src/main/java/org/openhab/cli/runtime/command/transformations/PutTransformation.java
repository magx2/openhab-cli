package org.openhab.cli.runtime.command.transformations;

import com.google.gson.reflect.TypeToken;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.client.model.Transformation;
import org.openhab.cli.engine.endpoint.Transformations;
import org.openhab.cli.runtime.JsonArguments;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Put a single transformation */
@Slf4j
@CommandLine.Command(
        name = "putTransformation",
        description = "Put a single transformation",
        mixinStandardHelpOptions = true)
public class PutTransformation implements Runnable {
    @CommandLine.Mixin
    private Options options;

    @CommandLine.Parameters(
            index = "0",
            arity = "1",
            paramLabel = "<uid>",
            description = "Transformation UID (required)")
    private String uid;

    @CommandLine.Parameters(
            index = "1",
            arity = "1",
            paramLabel = "<transformation>",
            description = "transformation (required) Supply a JSON value.")
    private String transformation;

    private final Console console;
    private final ApiClientBuilder apiClientBuilder;

    /** Creates the command with injected output and REST client services. */
    @Inject
    PutTransformation(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link Transformations#putTransformation}. */
    @Override
    public void run() {
        log.debug("Command: Transformations.putTransformation");
        Transformation transformationValue =
                JsonArguments.parse(transformation, new TypeToken<Transformation>() {}.getType(), "transformation");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Transformations(apiClient);
        endpoint.putTransformation(uid, transformationValue);
    }
}
