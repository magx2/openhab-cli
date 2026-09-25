package org.openhab.cli.runtime.ui;

import java.util.concurrent.Callable;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.engine.endpoint.Ui;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Get all registered UI tiles. */
@Slf4j
@CommandLine.Command(name = "uiTiles", description = "Get all registered UI tiles.", mixinStandardHelpOptions = true)
public class UiTiles implements Callable<Integer> {
    @CommandLine.Mixin
    private Options options;

    private final Console console;
    private final ApiClientBuilder apiClientBuilder;

    /** Creates the command with injected output and REST client services. */
    @Inject
    UiTiles(Console console, ApiClientBuilder apiClientBuilder) {
        this.console = console;
        this.apiClientBuilder = apiClientBuilder;
    }

    /** Executes {@link Ui#uiTiles} and returns zero on success. */
    @Override
    public Integer call() {
        log.debug("Command: Ui.uiTiles");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Ui(apiClient);
        var result = endpoint.uiTiles();
        console.writeJson(result, options.isPrettyPrint());
        return 0;
    }
}
