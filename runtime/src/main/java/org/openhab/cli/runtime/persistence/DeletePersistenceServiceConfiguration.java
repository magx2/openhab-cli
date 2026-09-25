package org.openhab.cli.runtime.persistence;

import java.util.concurrent.Callable;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.engine.endpoint.Persistence;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Deletes a persistence service configuration. */
@Slf4j
@CommandLine.Command(
        name = "deletePersistenceServiceConfiguration",
        description = "Deletes a persistence service configuration.",
        mixinStandardHelpOptions = true)
public class DeletePersistenceServiceConfiguration implements Callable<Integer> {
    @CommandLine.Mixin
    private Options options;

    @CommandLine.Parameters(
            index = "0",
            arity = "1",
            paramLabel = "<serviceId>",
            description = "Id of the persistence service. (required)")
    private String serviceId;

    private final Console console;
    private final ApiClientBuilder apiClientBuilder;

    /** Creates the command with injected output and REST client services. */
    @Inject
    DeletePersistenceServiceConfiguration(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link Persistence#deletePersistenceServiceConfiguration} and returns zero on success. */
    @Override
    public Integer call() {
        log.debug("Command: Persistence.deletePersistenceServiceConfiguration");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Persistence(apiClient);
        endpoint.deletePersistenceServiceConfiguration(serviceId);
        return 0;
    }
}
