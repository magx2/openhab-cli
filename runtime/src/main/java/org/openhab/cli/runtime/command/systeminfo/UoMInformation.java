package org.openhab.cli.runtime.command.systeminfo;

import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.engine.endpoint.SystemInfo;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Get all supported dimensions and their system units. */
@Slf4j
@CommandLine.Command(
        name = "uoMInformation",
        description = "Get all supported dimensions and their system units.",
        mixinStandardHelpOptions = true)
public class UoMInformation implements Runnable {
    @CommandLine.Mixin
    private Options options;

    private final Console console;
    private final ApiClientBuilder apiClientBuilder;

    /** Creates the command with injected output and REST client services. */
    @Inject
    UoMInformation(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link SystemInfo#uoMInformation}. */
    @Override
    public void run() {
        log.debug("Command: SystemInfo.uoMInformation");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new SystemInfo(apiClient);
        var result = endpoint.uoMInformation();
        console.writeJson(result, options.isPrettyPrint());
    }
}
