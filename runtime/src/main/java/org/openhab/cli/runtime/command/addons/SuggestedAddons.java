package org.openhab.cli.runtime.command.addons;

import java.util.concurrent.Callable;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.engine.endpoint.Addons;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Get suggested add-ons to be installed. */
@Slf4j
@CommandLine.Command(
        name = "suggestedAddons",
        description = "Get suggested add-ons to be installed.",
        mixinStandardHelpOptions = true)
public class SuggestedAddons implements Callable<Integer> {
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
    SuggestedAddons(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link Addons#suggestedAddons} and returns zero on success. */
    @Override
    public Integer call() {
        log.debug("Command: Addons.suggestedAddons");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Addons(apiClient);
        var result = endpoint.suggestedAddons(acceptLanguage);
        console.writeJson(result, options.isPrettyPrint());
        return 0;
    }
}
