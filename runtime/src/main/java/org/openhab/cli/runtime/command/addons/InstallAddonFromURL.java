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

/** Installs the add-on from the given URL. */
@Slf4j
@CommandLine.Command(
        name = "installAddonFromURL",
        description = "Installs the add-on from the given URL.",
        mixinStandardHelpOptions = true)
@RequiredArgsConstructor(access = AccessLevel.PACKAGE, onConstructor_ = @Inject)
public class InstallAddonFromURL implements Runnable {
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

    /** Executes {@link Addons#installAddonFromURL}. */
    @Override
    public void run() {
        log.debug("Command: Addons.installAddonFromURL");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Addons(apiClient);
        endpoint.installAddonFromURL(url);
    }
}
