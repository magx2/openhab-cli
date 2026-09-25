package org.openhab.cli.runtime.command.systeminfo;

import java.util.concurrent.Callable;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.engine.endpoint.SystemInfo;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Gets information about the system. */
@Slf4j
@CommandLine.Command(
        name = "systemInformation",
        description = "Gets information about the system.",
        mixinStandardHelpOptions = true)
public class SystemInformation implements Callable<Integer> {
    @CommandLine.Mixin
    private Options options;

    private final Console console;
    private final ApiClientBuilder apiClientBuilder;

    /** Creates the command with injected output and REST client services. */
    @Inject
    SystemInformation(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link SystemInfo#systemInformation} and returns zero on success. */
    @Override
    public Integer call() {
        log.debug("Command: SystemInfo.systemInformation");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new SystemInfo(apiClient);
        var result = endpoint.systemInformation();
        console.writeJson(result, options.isPrettyPrint());
        return 0;
    }
}
