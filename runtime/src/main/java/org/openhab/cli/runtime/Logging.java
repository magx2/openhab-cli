package org.openhab.cli.runtime;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.slf4j.bridge.SLF4JBridgeHandler;

/** Routes JDK logging to the same SLF4J backend used by the application. */
final class Logging {
    private static boolean configured;

    private Logging() {}

    /** Selects the user configuration before logging starts, then installs the root JUL bridge. */
    static synchronized void configure() {
        if (configured) {
            return;
        }
        var configuration = Path.of(System.getProperty("user.home"), "oh", "log4j2.xml");
        if (Files.isRegularFile(configuration)) {
            System.setProperty("log4j.configurationFile", configuration.toUri().toString());
        }
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
        Logger.getLogger("").setLevel(Level.ALL);
        configured = true;
    }
}
