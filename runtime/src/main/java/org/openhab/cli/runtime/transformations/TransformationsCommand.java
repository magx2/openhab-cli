package org.openhab.cli.runtime.transformations;

import picocli.CommandLine.Command;

/** Picocli command group for Transformations operations. */
@Command(
        name = "transformations",
        description = "Commands for Transformations.",
        mixinStandardHelpOptions = true,
        subcommands = {
            DeleteTransformation.class,
            Transformation.class,
            TransformationServices.class,
            Transformations.class,
            PutTransformation.class
        })
public class TransformationsCommand {}
