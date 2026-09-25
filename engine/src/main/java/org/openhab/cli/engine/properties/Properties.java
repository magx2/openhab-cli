package org.openhab.cli.engine.properties;

public record Properties(String oAuthToken, String username, String password, boolean prettyPrint) {
    public static final Properties DEFAULT = new Properties(null, null, null, true);
}
