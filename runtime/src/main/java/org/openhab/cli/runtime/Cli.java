package org.openhab.cli.runtime;

import static java.lang.String.join;

import java.util.concurrent.Callable;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.engine.Version;
import org.openhab.cli.runtime.action.ActionCommand;
import picocli.CommandLine;
import picocli.CommandLine.Command;

@Command(
        subcommands = {
            ActionCommand.class,
        },
        mixinStandardHelpOptions = true,
        versionProvider = Cli.VersionProvider.class,
        exitCodeListHeading = "Exit Codes:%n",
        exitCodeList = { //
            " 0: Successful program execution", //
            " 1: Command execution failed", //
            "98: I/O operation failed (for example, reading the properties file)", //
            "99: openHAB API request failed" //
        })
@Slf4j
public class Cli implements Callable<Integer> {
    /** Supplies the application version embedded by the engine build. */
    public static class VersionProvider implements CommandLine.IVersionProvider {
        @Override
        public String[] getVersion() {
            return new String[] {Version.VERSION};
        }
    }

    public static void main(String[] args) {
        if (log.isDebugEnabled()) {
            log.debug("oh {}", join(" ", args));
        }
        System.out.println("oh " + join(" ", args));
        int exitCode = commandLine().execute(args);
        System.exit(exitCode);
    }

    /** Creates a parser with Dagger-backed commands and application exit-code handling. */
    static CommandLine commandLine() {
        var component = DaggerRuntimeComponent.create();
        return new CommandLine(new Cli(), component.commandFactory())
                .setExitCodeExceptionMapper(component.exitCodeMapper());
    }

    @Override
    public Integer call() throws Exception {
        System.out.println("run");
        return 1;
    }
}
