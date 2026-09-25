"""Update the release version without changing other Gradle properties."""

import pathlib
import re
import sys


def update_version(text, phase):
    """Return updated properties and the release/next development versions."""
    suffix = r"-SNAPSHOT" if phase == "release" else ""
    matches = list(re.finditer(
        rf"^version=((?:0|[1-9][0-9]*)\.(?:0|[1-9][0-9]*)\.(?:0|[1-9][0-9]*)){suffix}$",
        text, re.MULTILINE,
    ))
    if len(matches) != 1:
        raise ValueError(f"Expected exactly one version=MAJOR.MINOR.PATCH{suffix} property")
    match = matches[0]
    version = match.group(1)
    major, minor, _ = map(int, version.split("."))
    next_version = f"{major}.{minor + 1}.0-SNAPSHOT"
    replacement = version if phase == "release" else next_version
    return text[:match.start()] + f"version={replacement}" + text[match.end():], version, next_version


if __name__ == "__main__":
    if len(sys.argv) != 2 or sys.argv[1] not in {"release", "snapshot"}:
        sys.exit("Usage: release-version.py release|snapshot")
    properties = pathlib.Path("gradle.properties")
    updated, version, next_version = update_version(properties.read_text(encoding="utf-8"), sys.argv[1])
    properties.write_text(updated, encoding="utf-8", newline="\n")
    print(f"version={version}")
    print(f"next_version={next_version}")
    print(f"tag=v{version}")
