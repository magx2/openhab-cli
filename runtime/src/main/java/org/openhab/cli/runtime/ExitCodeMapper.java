package org.openhab.cli.runtime;

import java.io.UncheckedIOException;
import javax.inject.Inject;
import lombok.RequiredArgsConstructor;
import org.openhab.cli.engine.endpoint.EndpointException;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

@RequiredArgsConstructor(onConstructor_ = @Inject)
public class ExitCodeMapper implements CommandLine.IExitCodeExceptionMapper {
    public static final int ENDPOINT_EXCEPTION_EXIT_CODE = 99;
    public static final int IO_EXCEPTION_EXIT_CODE = 98;
    public static final int ILLEGAL_STATE_EXCEPTION_EXIT_CODE = 97;
    public static final int ILLEGAL_ARGUMENT_EXCEPTION_EXIT_CODE = 96;

    private final Console console;

    @Override
    public int getExitCode(Throwable throwable) {
        writeError(throwable);
        return switch (throwable) {
            case EndpointException endpointException -> ENDPOINT_EXCEPTION_EXIT_CODE;
            case UncheckedIOException uncheckedIOException -> IO_EXCEPTION_EXIT_CODE;
            case IllegalStateException illegalStateException -> ILLEGAL_STATE_EXCEPTION_EXIT_CODE;
            case IllegalArgumentException illegalArgumentException -> ILLEGAL_ARGUMENT_EXCEPTION_EXIT_CODE;
            default -> 1;
        };
    }

    private void writeError(Throwable throwable) {
        var message = throwable.getMessage();
        if (message == null || message.isBlank()) {
            message = throwable.getClass().getName();
        }
        console.writeError("%s", throwable, message);
    }
}
