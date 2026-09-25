package org.openhab.cli.runtime;

import javax.inject.Inject;
import org.openhab.cli.engine.rest.ApiClient;

public class ApiClientBuilder {
    private final PropertiesReader propertiesReader;

    @Inject
    public ApiClientBuilder(PropertiesReader propertiesReader) {
        this.propertiesReader = propertiesReader;
    }

    public ApiClient build(Options options) {
        var properties = propertiesReader.read(options.getPropertiesFile());
        properties = options.overrideProps(properties);
        return new org.openhab.cli.engine.rest.ApiClient(properties);
    }
}
