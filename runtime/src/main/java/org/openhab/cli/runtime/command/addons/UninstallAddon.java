package org.openhab.cli.runtime.command.addons;

import javax.inject.Inject;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.engine.endpoint.Addons;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Uninstalls the add-on with the given ID. */
@Slf4j
@CommandLine.Command(
        name = "uninstallAddon",
        description = "Uninstalls the add-on with the given ID.",
        mixinStandardHelpOptions = true)
@RequiredArgsConstructor(access = AccessLevel.PACKAGE, onConstructor_ = @Inject)
public class UninstallAddon implements Runnable {
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

    /** Executes {@link Addons#uninstallAddon}. */
    @Override
    public void run() {
        log.debug("Command: Addons.uninstallAddon");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Addons(apiClient);
        endpoint.uninstallAddon(addonId, serviceId);
    }
}
