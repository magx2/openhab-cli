package org.openhab.cli.runtime.command.addons;

import java.util.concurrent.Callable;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.engine.endpoint.Addons;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Get all add-on types. */
@Slf4j
@CommandLine.Command(name = "addonTypes", description = "Get all add-on types.", mixinStandardHelpOptions = true)
public class AddonTypes implements Callable<Integer> {
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
    AddonTypes(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link Addons#addonTypes} and returns zero on success. */
    @Override
    public Integer call() {
        log.debug("Command: Addons.addonTypes");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Addons(apiClient);
        var result = endpoint.addonTypes(acceptLanguage);
        console.writeJson(result, options.isPrettyPrint());
        return 0;
    }
}
