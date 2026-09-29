package org.openhab.cli.runtime;

import lombok.Getter;
import picocli.CommandLine.Option;

/** Selects a properties file without introducing connection or authentication options. */
public class PropertiesFileOptions {
    @Getter
    @Option(
            names = {"-p", "--properties-file"},
            description = "Properties file (default: ~/.oh/oh-cli.properties)")
    private String propertiesFile;
}
