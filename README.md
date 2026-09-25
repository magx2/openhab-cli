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
