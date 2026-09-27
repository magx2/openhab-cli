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

/** Gets a list of persistence services. */
@Slf4j
@CommandLine.Command(
        name = "persistenceServices",
        description = "Gets a list of persistence services.",
        mixinStandardHelpOptions = true)
@RequiredArgsConstructor(access = AccessLevel.PACKAGE, onConstructor_ = @Inject)
public class PersistenceServices implements Runnable {
    @CommandLine.Mixin
    private Options options;

    @CommandLine.Option(
            names = "--accept-language",
            paramLabel = "<acceptLanguage>",
            description = "language (optional)",
            arity = "1")
    private String acceptLanguage;

    private final Console console;
    private final ApiClientBuilder apiClientBuilder;

    /** Executes {@link Persistence#persistenceServices}. */
    @Override
    public void run() {
        log.debug("Command: Persistence.persistenceServices");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Persistence(apiClient);
        var result = endpoint.persistenceServices(acceptLanguage);
        console.writeJson(result, options.isPrettyPrint());
    }
}
