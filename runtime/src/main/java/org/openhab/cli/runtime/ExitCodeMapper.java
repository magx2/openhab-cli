package org.openhab.cli.runtime;

import java.io.UncheckedIOException;
import javax.inject.Inject;
import org.openhab.cli.engine.endpoint.EndpointException;
import picocli.CommandLine;

public class ExitCodeMapper implements CommandLine.IExitCodeExceptionMapper {
    /** Creates the mapper used for command execution failures. */
    @Inject
    public ExitCodeMapper() {}

    @Override
    public int getExitCode(Throwable throwable) {
        if (throwable instanceof EndpointException) {
            return 99;
        }
        if (throwable instanceof UncheckedIOException) {
            return 98;
        }
        return 1;
    }
}
