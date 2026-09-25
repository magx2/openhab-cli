package org.openhab.cli.runtime.persistence;

import com.google.gson.reflect.TypeToken;
import java.util.concurrent.Callable;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.client.model.PersistenceServiceConfiguration;
import org.openhab.cli.engine.endpoint.Persistence;
import org.openhab.cli.runtime.JsonArguments;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Sets a persistence service configuration. */
@Slf4j
@CommandLine.Command(
        name = "putPersistenceServiceConfiguration",
        description = "Sets a persistence service configuration.",
        mixinStandardHelpOptions = true)
public class PutPersistenceServiceConfiguration implements Callable<Integer> {
    @CommandLine.Mixin
    private Options options;

    @CommandLine.Parameters(
            index = "0",
            arity = "1",
            paramLabel = "<serviceId>",
            description = "Id of the persistence service. (required)")
    private String serviceId;

    @CommandLine.Parameters(
            index = "1",
            arity = "1",
            paramLabel = "<persistenceServiceConfiguration>",
            description = "service configuration (required) Supply a JSON value.")
    private String persistenceServiceConfiguration;

    private final Console console;
    private final ApiClientBuilder apiClientBuilder;

    /** Creates the command with injected output and REST client services. */
    @Inject
    PutPersistenceServiceConfiguration(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link Persistence#putPersistenceServiceConfiguration} and returns zero on success. */
    @Override
    public Integer call() {
        log.debug("Command: Persistence.putPersistenceServiceConfiguration");
        PersistenceServiceConfiguration persistenceServiceConfigurationValue = JsonArguments.parse(
                persistenceServiceConfiguration,
                new TypeToken<PersistenceServiceConfiguration>() {}.getType(),
                "persistenceServiceConfiguration");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Persistence(apiClient);
        var result = endpoint.putPersistenceServiceConfiguration(serviceId, persistenceServiceConfigurationValue);
        console.writeJson(result, options.isPrettyPrint());
        return 0;
    }
}
