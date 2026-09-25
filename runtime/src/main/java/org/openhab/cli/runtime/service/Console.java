package org.openhab.cli.runtime.service;

import javax.inject.Inject;
import org.openhab.cli.client.JSON;

public class Console {
    @Inject
    public Console() {}

    /** Prints JSON followed by a newline, using the API client's configured Gson adapters. */
    public void writeJson(Object object, boolean prettyPrint) {
        var json = prettyPrint
                ? JSON.getGson().newBuilder().setPrettyPrinting().create().toJson(object)
                : JSON.serialize(object);
        System.out.println(json);
    }

    public void writeError(String msg) {
        //        System.err.println("ERR: " + msg);
        System.out.println("ERR: " + msg);
    }
}
