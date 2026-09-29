package org.openhab.cli.runtime;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.slf4j.LoggerFactory;

class LoggingTest {
    @TempDir
    Path home;

    @Test
    void writesInfoLogsToFileAndRollsOnEachStartupWithoutConsoleOutput() throws Exception {
        runProcess("first");
        var log = home.resolve(".oh/oh.log");
        var first = Files.readString(log);
        assertLog(first, "first");

        // Startup rollover compares filesystem and JVM timestamps, which may have coarse precision on WSL.
        Thread.sleep(1100);
        runProcess("second");
        var second = Files.readString(log);
        assertLog(second, "second");
        assertFalse(second.contains("first"));
        try (var files = Files.list(log.getParent())) {
            var archives = files.filter(path -> !path.equals(log)).toList();
            assertEquals(1, archives.size());
            assertEquals(first, Files.readString(archives.getFirst()));
        }
    }

    @Test
    void retainsOnlyFiveNewestArchivesAndPreservesOtherFiles() throws Exception {
        runProcess("first");
        var directory = home.resolve(".oh");
        var oldTime = java.time.Instant.now().minusSeconds(3600);
        for (int i = 0; i < 7; i++) {
            var archive = directory.resolve("oh-2020-01-01_00-00-00-000-" + i + ".log");
            Files.writeString(archive, "archive-" + i);
            Files.setLastModifiedTime(archive, java.nio.file.attribute.FileTime.from(oldTime.plusSeconds(i)));
        }
        var properties = directory.resolve("oh-cli.properties");
        Files.writeString(properties, "custom=keep");
        var unrelated = directory.resolve("other.log");
        Files.writeString(unrelated, "keep");
        Thread.sleep(1100);
        runProcess("second");
        try (var files = Files.list(directory)) {
            var archives = files.filter(path -> path.getFileName().toString().startsWith("oh-"))
                    .filter(path -> path.toString().endsWith(".log"))
                    .toList();
            assertEquals(5, archives.size());
        }
        for (int i = 0; i < 7; i++) {
            assertEquals(i >= 3, Files.exists(directory.resolve("oh-2020-01-01_00-00-00-000-" + i + ".log")));
        }
        assertLog(Files.readString(directory.resolve("oh.log")), "second");
        assertEquals("custom=keep", Files.readString(properties));
        assertEquals("keep", Files.readString(unrelated));
    }

    @Test
    void userConfigurationOverridesBundledConfiguration() throws Exception {
        var directory = Files.createDirectories(home.resolve(".oh"));
        Files.writeString(directory.resolve("log4j2.xml"), """
                <Configuration>
                    <Appenders>
                        <File name="Custom" fileName="${sys:user.home}/.oh/custom.log">
                            <PatternLayout pattern="CUSTOM %msg%n"/>
                        </File>
                    </Appenders>
                    <Loggers>
                        <Root level="debug"><AppenderRef ref="Custom"/></Root>
                    </Loggers>
                </Configuration>
                """);
        runProcess("custom");
        var output = Files.readString(directory.resolve("custom.log"));
        assertTrue(output.contains("CUSTOM debug-custom"), output);
        assertTrue(output.contains("CUSTOM jul-custom"), output);
        assertFalse(Files.exists(directory.resolve("oh.log")));
    }

    private void runProcess(String marker) throws Exception {
        var javaExecutable =
                Path.of(System.getProperty("java.home"), "bin", "java").toString();
        var process = new ProcessBuilder(
                        javaExecutable,
                        "-Duser.home=" + home,
                        "-cp",
                        System.getProperty("loggingTestClasspath"),
                        Probe.class.getName(),
                        marker)
                .start();
        try {
            assertTrue(process.waitFor(30, TimeUnit.SECONDS), "Logging probe timed out");
            assertEquals(0, process.exitValue());
            assertEquals(
                    "", new String(process.getInputStream().readAllBytes(), java.nio.charset.StandardCharsets.UTF_8));
            assertEquals(
                    "", new String(process.getErrorStream().readAllBytes(), java.nio.charset.StandardCharsets.UTF_8));
        } finally {
            process.destroyForcibly();
        }
    }

    private void assertLog(String log, String marker) {
        assertTrue(log.contains("slf4j-" + marker), log);
        assertTrue(log.contains("jul-" + marker), log);
        assertFalse(log.contains("debug-"));
        assertFalse(log.contains("hidden-thread"));
        assertEquals(log.indexOf("jul-" + marker), log.lastIndexOf("jul-" + marker));
        assertTrue(log.matches("(?s)\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}\\.\\d{3} INFO .*"), log);
    }

    public static class Probe {
        public static void main(String[] args) throws Exception {
            Class.forName(Cli.class.getName());
            Thread.currentThread().setName("hidden-thread");
            Logging.configure();
            Logging.configure();
            var logger = LoggerFactory.getLogger("logging-test");
            logger.info("slf4j-{}", args[0]);
            logger.debug("debug-{}", args[0]);
            Logger.getLogger("logging-test.jul").info("jul-" + args[0]);
        }
    }
}
