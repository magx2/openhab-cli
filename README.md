# openhab-cli

Generate the Java openHAB REST API client and model classes from
`openapi/src/main/open-hab/spec.json`:

```sh
./gradlew :openApiGenerate
```

Generated sources are written to `build/generated/openapi/src/main/java`.

The `openapi` subproject compiles the generated client. The `engine` subproject
depends on it, and compilation runs generation automatically.

Format Java sources with Spotless and Palantir Java Format:

```sh
./gradlew spotlessApply
```

`./gradlew spotlessCheck` checks formatting and also runs as part of `build`.
Generated OpenAPI sources are excluded.

Commands that call openHAB require a server URL. Supply `--base-url=http://localhost:8080`
or set it in `oh-cli.properties` (or the file selected by `--properties-file`):

```properties
config.rest.baseUrl=http://localhost:8080
```

CLI options override file settings. The REST path defaults to `/rest` and is appended
to the server URL; customize it with `--base-path` or `config.rest.basePath`.

Runtime logging uses SLF4J with Log4j 2 and writes INFO-and-higher messages only
to `~/oh/oh.log`. Each JVM startup archives the previous log as
`~/oh/oh-<timestamp>-<index>.log`. Logs include date and time, without thread names.
JDK `java.util.logging` messages are forwarded to the same backend at CLI startup.
All projects share the SLF4J version in `gradle/libs.versions.toml`.

To override the bundled logging configuration, create `~/oh/log4j2.xml`. The CLI
loads this file at startup when present; otherwise it uses the bundled defaults.

The location uses the home directory of the Java process:

- Windows Java: typically `C:\Users\<username>\oh\log4j2.xml`.
- Ubuntu/WSL Java: typically `/home/<username>/oh/log4j2.xml`.

These are separate locations even when Windows and WSL share the same checkout.
Create the `oh` directory and save the following as `log4j2.xml`. This example
keeps file-only logging and startup rollover, while enabling DEBUG messages for
the CLI and retaining INFO for other libraries:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<Configuration>
    <Appenders>
        <RollingFile name="File" fileName="${sys:user.home}/oh/oh.log"
                     filePattern="${sys:user.home}/oh/oh-%d{yyyy-MM-dd_HH-mm-ss-SSS}-%i.log">
            <PatternLayout pattern="%d{yyyy-MM-dd HH:mm:ss.SSS} %-5level %logger - %msg%n"/>
            <OnStartupTriggeringPolicy minSize="0"/>
            <DefaultRolloverStrategy fileIndex="nomax"/>
        </RollingFile>
    </Appenders>
    <Loggers>
        <Logger name="org.openhab.cli" level="debug"/>
        <Root level="info">
            <AppenderRef ref="File"/>
        </Root>
    </Loggers>
</Configuration>
```

To customize this example:

- Change the root `level` to `trace`, `debug`, `info`, `warn`, `error`, or `off`.
- Add a `Logger` with a package or class name to override its level. It inherits
  the root file appender, so it does not need its own `AppenderRef`.
- Change `fileName` and `filePattern` together to choose another log location.
- Change `PatternLayout` to adjust the output. The example includes timestamps
  and omits thread names; add `%t` if you want thread names.

Restart the CLI after editing the file. This file replaces the bundled
configuration completely; remove or rename it to restore the defaults. The
example retains all archived logs, so remove old archives as needed.
