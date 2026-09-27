package org.openhab.cli.runtime.service;

import javax.inject.Inject;
import lombok.RequiredArgsConstructor;
import org.openhab.cli.engine.rest.ApiClient;
import org.openhab.cli.runtime.Options;

@RequiredArgsConstructor(onConstructor_ = @Inject)
public class ApiClientBuilder {
    private final PropertiesReader propertiesReader;

    public ApiClient build(Options options) {
        var properties = propertiesReader.read(options.getPropertiesFile());
        properties = options.overrideProps(properties);
        return new org.openhab.cli.engine.rest.ApiClient(properties);
    }
}
