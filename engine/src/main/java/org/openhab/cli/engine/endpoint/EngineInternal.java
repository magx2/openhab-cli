package org.openhab.cli.engine.endpoint;

/** Identifies engine-internal commands, which have no corresponding openHAB REST API. */
public final class EngineInternal implements Endpoint {
    /** Creates the endpoint identifying engine-internal commands. */
    public EngineInternal() {}

    @Override
    public String keyword() {
        return "#";
    }
}
