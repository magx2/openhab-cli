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

/** Gets configuration problems with persistence services. */
@Slf4j
@CommandLine.Command(
        name = "persistenceHealth",
        description = "Gets configuration problems with persistence services.",
        mixinStandardHelpOptions = true)
@RequiredArgsConstructor(access = AccessLevel.PACKAGE, onConstructor_ = @Inject)
public class PersistenceHealth implements Runnable {
    @CommandLine.Mixin
    private Options options;

    private final Console console;
    private final ApiClientBuilder apiClientBuilder;

    /** Executes {@link Persistence#persistenceHealth}. */
    @Override
    public void run() {
        log.debug("Command: Persistence.persistenceHealth");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Persistence(apiClient);
        var result = endpoint.persistenceHealth();
        console.writeJson(result, options.isPrettyPrint());
    }
}
