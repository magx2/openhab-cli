package org.openhab.cli.runtime;

import java.io.UncheckedIOException;
import javax.inject.Inject;
import org.openhab.cli.engine.endpoint.EndpointException;
import picocli.CommandLine;

public class ExitCodeMapper implements CommandLine.IExitCodeExceptionMapper {
    public static final int ENDPOINT_EXCEPTION_EXIT_CODE = 99;
    public static final int IO_EXCEPTION_EXIT_CODE = 98;
    public static final int ILLEGAL_STATE_EXCEPTION_EXIT_CODE = 97;
    public static final int ILLEGAL_ARGUMENT_EXCEPTION_EXIT_CODE = 96;

    /** Creates the mapper used for command execution failures. */
    @Inject
    public ExitCodeMapper() {}

    @Override
    public int getExitCode(Throwable throwable) {
        if (throwable instanceof EndpointException) {
            return ENDPOINT_EXCEPTION_EXIT_CODE;
        }
        if (throwable instanceof UncheckedIOException) {
            return IO_EXCEPTION_EXIT_CODE;
        }
        if (throwable instanceof IllegalStateException) {
            return ILLEGAL_STATE_EXCEPTION_EXIT_CODE;
        }
        if (throwable instanceof IllegalArgumentException) {
            return ILLEGAL_ARGUMENT_EXCEPTION_EXIT_CODE;
        }
        return 1;
    }
}
