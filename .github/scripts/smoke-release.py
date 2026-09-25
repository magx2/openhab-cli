"""Exercise a packaged CLI, including reflection, HTTP and JSON, without openHAB."""

from http.server import BaseHTTPRequestHandler, ThreadingHTTPServer
import json
from pathlib import Path
import re
import subprocess
import sys
from threading import Thread


class OpenHabHandler(BaseHTTPRequestHandler):
    """Serve one representative response containing generated API models."""

    def do_GET(self):
        if self.path != "/rest/addons/services":
            self.send_error(404)
            return
        payload = json.dumps([{"id": "binding", "label": "Bindings"}]).encode()
        self.send_response(200)
        self.send_header("Content-Type", "application/json")
        self.send_header("Content-Length", str(len(payload)))
        self.end_headers()
        self.wfile.write(payload)

    def log_message(self, *_):
        pass


def run(*arguments):
    """Fail on a CLI error, timeout or unexpected logging to stderr."""
    result = subprocess.run(sys.argv[1:] + list(arguments), capture_output=True,
                            text=True, timeout=60)
    if result.returncode:
        raise AssertionError(f"CLI exited {result.returncode}: {result.stdout}\n{result.stderr}")
    if result.stderr:
        raise AssertionError(result.stderr)
    return result.stdout


if __name__ == "__main__":
    if len(sys.argv) < 2:
        sys.exit("Usage: smoke-release.py <executable> [launcher arguments...]")
    version = re.search(r"^version=(.+)$", Path("gradle.properties").read_text(encoding="utf-8"), re.MULTILINE).group(1)
    spec = json.loads(Path("openapi/src/main/open-hab/spec.json").read_text(encoding="utf-8"))
    assert run("--version").strip() == f"{spec['info']['version']}.{version}"
    assert "availableActionsForThing" in run("action", "--help")
    assert "--release" in run("_config", "update", "--help")
    server = ThreadingHTTPServer(("127.0.0.1", 0), OpenHabHandler)
    thread = Thread(target=server.serve_forever, daemon=True)
    thread.start()
    try:
        output = run("addons", "addonTypes", f"--base-url=http://127.0.0.1:{server.server_port}")
        assert json.loads(output) == [{"id": "binding", "label": "Bindings"}], output
    finally:
        server.shutdown()
        server.server_close()
        thread.join()
    print("Version, command help, HTTP and JSON smoke tests passed")
