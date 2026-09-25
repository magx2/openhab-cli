package org.openhab.cli.runtime.command.addons;

import java.util.concurrent.Callable;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.engine.endpoint.Addons;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Installs the add-on from the given URL. */
@Slf4j
@CommandLine.Command(
        name = "installAddonFromURL",
        description = "Installs the add-on from the given URL.",
        mixinStandardHelpOptions = true)
public class InstallAddonFromURL implements Callable<Integer> {
    @CommandLine.Mixin
    private Options options;

    @CommandLine.Parameters(
            index = "0",
            arity = "1",
            paramLabel = "<url>",
            description = "addon install URL (required)")
    private String url;

    private final Console console;
    private final ApiClientBuilder apiClientBuilder;

    /** Creates the command with injected output and REST client services. */
    @Inject
    InstallAddonFromURL(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link Addons#installAddonFromURL} and returns zero on success. */
    @Override
    public Integer call() {
        log.debug("Command: Addons.installAddonFromURL");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Addons(apiClient);
        endpoint.installAddonFromURL(url);
        return 0;
    }
}
