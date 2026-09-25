package org.openhab.cli.runtime;

import javax.inject.Inject;

public class Console {
    @Inject
    public Console() {}

    public void writeJson(Object object, boolean prettyPrint) {
        // todo iplement properly
        System.out.println(object);
    }

    public void writeError(String msg) {
        //        System.err.println("ERR: " + msg);
        System.out.println("ERR: " + msg);
    }
}
