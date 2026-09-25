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

Use `<group> --help` to list operations and `<group> <operation> --help` to see
arguments. Every engine API operation has a matching CLI command. Required
operation arguments are positional; optional values use named kebab-case flags.
The existing `action availableActionsForThing <thingUID> [<acceptLanguage>]`
syntax is also preserved. Optional booleans accept `--flag` or `--flag=false`.

Pass models, maps, and collections as a single JSON argument. For example, these
argument lists can be passed to the CLI (examples use Bash quoting):

```sh
action availableActionsForThing --base-url=http://localhost:8080 -- thing:test en
action executeThingAction --base-url=http://localhost:8080 --request-body='{"input":"value"}' -- thing:test action:test
things updateThing --base-url=http://localhost:8080 --accept-language=en -- thing:test '{"UID":"thing:test","thingTypeUID":"binding:type","channels":[],"configuration":{},"properties":{}}'
```

Operations with a response print JSON using the shared output options. Operations
with no response body return successfully without printing JSON. Commands use
the shared authentication, connection, TLS, and timeout settings.

| Command group | Operations |
| --- | ---: |
| `action` | 2 |
| `addons` | 10 |
| `audio` | 4 |
| `auth` | 5 |
| `channeltypes` | 3 |
| `configdescriptions` | 2 |
| `discovery` | 3 |
| `events` | 3 |
| `fileformat` | 9 |
| `iconsets` | 1 |
| `inbox` | 5 |
| `items` | 19 |
| `links` | 7 |
| `logging` | 4 |
| `moduletypes` | 2 |
| `persistence` | 10 |
| `profiletypes` | 1 |
| `root` | 1 |
| `rules` | 18 |
| `services` | 6 |
| `sitemaps` | 11 |
| `systeminfo` | 2 |
| `tags` | 5 |
| `templates` | 2 |
| `things` | 12 |
| `thingtypes` | 2 |
| `transformations` | 5 |
| `ui` | 6 |
| `uuid` | 1 |
| `voice` | 14 |

`engineinternal` is registered as an empty group because the engine class does
not currently expose any operations.

## Fish completions

Print the completion script to stdout (the default action is `show`):

```sh
oh _config shell fish completion show
```

Install it for your user:

```sh
oh _config shell fish completion install
```

This writes `oh.fish` to `$XDG_CONFIG_HOME/fish/completions`, or
`~/.config/fish/completions` when `XDG_CONFIG_HOME` is unset. Fish autoloads the
file for `oh`. To refresh completions in an existing shell, source the installed
file or start a new Fish session. Run `install` again after upgrading the CLI.
Completions include command groups, operations, options and enum values; they do
not query an openHAB server for item or thing identifiers.

## Bash completions

```sh
oh _config shell bash completion show     # Print the script (default action)
oh _config shell bash completion install  # Install it for your user
```

Actions are case-insensitive. Installation writes `completions/oh.bash` under
the first absolute directory in `$BASH_COMPLETION_USER_DIR`, or under
`$XDG_DATA_HOME/bash-completion` (default: `~/.local/share/bash-completion`).
The `bash-completion` package must be enabled in your shell for automatic loading.
Alternatively, load the script directly without that package:

```bash
source <(oh _config shell bash completion show)
```

Start a new Bash session or source the installed file to refresh completions.
Run `install` again after upgrading the CLI. Completion generation uses Picocli's
command model and does not contact an openHAB server.

## Releases

Run **Release** from the GitHub Actions tab, selecting `master`. Other branches
are skipped. The workflow first runs `./gradlew clean check`, then removes
`-SNAPSHOT` from `gradle.properties`, commits the release version and creates an
annotated `vMAJOR.MINOR.PATCH` tag locally. A Git bundle transfers that unpushed
commit to three parallel builds: a runnable JAR, a Linux x86_64 executable and a
Windows x86_64 executable.

After all builds and smoke tests pass, the workflow increments the minor version,
resets the patch to zero and commits the next `-SNAPSHOT` version. For example,
`0.1.0-SNAPSHOT` releases `v0.1.0` and leaves `master` at `0.2.0-SNAPSHOT`.
It creates a draft release and uploads all three assets before atomically pushing
both commits and the tag. It then publishes the draft. Publishing must follow the
push so that the release points to the actual release commit.

The workflow needs `GITHUB_TOKEN` permission to write repository contents, and
repository rules must allow it to push to `master` and create release tags. It
refuses to push if `master` has advanced since the run started. If the final push
fails, the draft remains available for inspection; delete it before retrying from
the current `master`. If only publication fails after a successful push, publish
the existing draft instead of running another release. Build artifacts and failed
test reports are retained for three days.

For local builds, use `./gradlew :runtime:shadowJar` and run the resulting
`runtime/build/libs/oh-<version>-all.jar` with `java -jar` (Java 21 or newer).
With GraalVM for Java 21 installed, `./gradlew :runtime:nativeCompile` creates
`runtime/build/native/nativeCompile/oh` (`oh.exe` on Windows). Native executables
must be built on their target operating system. The release workflow uses the
[GraalVM setup action](https://github.com/graalvm/setup-graalvm) and
[Native Build Tools](https://graalvm.github.io/native-build-tools/latest/gradle-plugin).
