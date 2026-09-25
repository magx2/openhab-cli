package org.openhab.cli.runtime.command.audio;

import picocli.CommandLine.Command;

/** Picocli command group for Audio operations. */
@Command(
        name = "audio",
        description = "Commands for Audio.",
        mixinStandardHelpOptions = true,
        subcommands = {AudioDefaultSink.class, AudioDefaultSource.class, AudioSinks.class, AudioSources.class})
public class AudioCommand {}
