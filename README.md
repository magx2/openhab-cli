# openhab-cli

[![Gradle check](https://github.com/magx2/openhab-cli/actions/workflows/check.yml/badge.svg)](https://github.com/magx2/openhab-cli/actions/workflows/check.yml)
[![Latest release](https://img.shields.io/github/v/release/magx2/openhab-cli)](https://github.com/magx2/openhab-cli/releases/latest)

`openhab-cli` is a command-line client for the [openHAB REST API](https://www.openhab.org/docs/configuration/restdocs). It provides the `oh` command for inspecting and managing an openHAB installation from a terminal, a script, or an AI coding agent.

The command tree follows the openHAB API. It covers items, things, rules, add-ons, persistence, services, sitemaps, voice, and the other endpoints exposed by the bundled OpenAPI specification.

## Installation

Download the latest build from [GitHub Releases](https://github.com/magx2/openhab-cli/releases/latest). Each release contains:

- a Linux x86-64 executable;
- a Windows x86-64 executable; and
- a runnable JAR for other platforms. The JAR requires Java 21 or newer.

Rename the native executable to `oh` (`oh.exe` on Windows) and place it in a directory on your `PATH`. On Linux, make it executable first:

```bash
chmod +x oh-<version>-linux-x86_64
sudo mv oh-<version>-linux-x86_64 /usr/local/bin/oh
```

Run the JAR directly with:

```bash
java -jar oh-<version>.jar
```

## Initial setup

Save the URL of your openHAB server:

```bash
oh _config server set https://openhab.example.com
```

Next, save an OAuth token. Omitting its value opens an interactive prompt, which keeps the token out of your shell history:

```bash
oh _config account login --oauth-token
```

Username and password authentication is also supported:

```bash
oh _config account login --username --password
```

Token authentication is recommended. To use a username and password, enable Basic Authentication in **Main UI → Settings → API Security** on the openHAB server.

The default configuration file is `~/.oh/oh-cli.properties`. Pass `--properties-file` to a configuration command to use another file.

## Usage

Run `oh` to list the available command groups:

```bash
oh
```

Append `--help` at any level of the command tree to inspect its commands and options:

```bash
oh items --help
oh items itemByName --help
```

For example, list every item:

```bash
oh items items
```

Retrieve one item by name:

```bash
oh items itemByName KitchenLight
```

Enum values are case-insensitive. JSON output is pretty-printed by default; use `--no-pretty-print` when compact output is more convenient for a script.

## Shell completion

Install completion definitions for Bash or Fish:

```bash
oh _config shell bash completion install
oh _config shell fish completion install
```

Use `show` instead of `install` to print the completion script to standard output.

## Updating

Check whether a newer release is available:

```bash
oh _config update check
```

Install the latest release:

```bash
oh _config update run
```

The updater supports the Linux and Windows native executables as well as the runnable JAR. On Linux, it requests `sudo` only when replacing an installation that requires elevated permissions.

## Building from source

The project uses the Gradle wrapper and Java 21:

```bash
./gradlew clean build
```

The runnable JAR is written to `runtime/build/libs/`.
