package org.openhab.cli.runtime.command.persistence;

import java.util.concurrent.Callable;
import javax.inject.Inject;
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
public class PersistenceServices implements Callable<Integer> {
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

    /** Creates the command with injected output and REST client services. */
    @Inject
    PersistenceServices(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link Persistence#persistenceServices} and returns zero on success. */
    @Override
    public Integer call() {
        log.debug("Command: Persistence.persistenceServices");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Persistence(apiClient);
        var result = endpoint.persistenceServices(acceptLanguage);
        console.writeJson(result, options.isPrettyPrint());
        return 0;
    }
}
