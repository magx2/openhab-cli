package org.openhab.cli.runtime.addons;

import java.util.concurrent.Callable;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.engine.endpoint.Addons;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Installs the add-on with the given ID. */
@Slf4j
@CommandLine.Command(
        name = "installAddonById",
        description = "Installs the add-on with the given ID.",
        mixinStandardHelpOptions = true)
public class InstallAddonById implements Callable<Integer> {
    @CommandLine.Mixin
    private Options options;

    @CommandLine.Parameters(index = "0", arity = "1", paramLabel = "<addonId>", description = "addon ID (required)")
    private String addonId;

    @CommandLine.Option(
            names = "--service-id",
            paramLabel = "<serviceId>",
            description = "service ID (optional)",
            arity = "1")
    private String serviceId;

    private final Console console;
    private final ApiClientBuilder apiClientBuilder;

    /** Creates the command with injected output and REST client services. */
    @Inject
    InstallAddonById(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link Addons#installAddonById} and returns zero on success. */
    @Override
    public Integer call() {
        log.debug("Command: Addons.installAddonById");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Addons(apiClient);
        endpoint.installAddonById(addonId, serviceId);
        return 0;
    }
}
