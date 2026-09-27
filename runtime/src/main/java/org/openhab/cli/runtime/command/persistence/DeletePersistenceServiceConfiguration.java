package org.openhab.cli.runtime.command.persistence;

import javax.inject.Inject;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor(access = AccessLevel.PACKAGE, onConstructor_ = @Inject)
public class DeletePersistenceServiceConfiguration implements Runnable {
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

    /** Executes {@link Persistence#deletePersistenceServiceConfiguration}. */
    @Override
    public void run() {
        log.debug("Command: Persistence.deletePersistenceServiceConfiguration");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Persistence(apiClient);
        endpoint.deletePersistenceServiceConfiguration(serviceId);
    }
}
