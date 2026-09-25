package org.openhab.cli.runtime.voice;

import picocli.CommandLine.Command;

/** Picocli command group for Voice operations. */
@Command(
        name = "voice",
        description = "Commands for Voice.",
        mixinStandardHelpOptions = true,
        subcommands = {
            DeleteConversationById.class,
            ConversationById.class,
            DefaultVoice.class,
            LlmTools.class,
            VoiceInterpreterByUID.class,
            VoiceInterpreters.class,
            Voices.class,
            InterpretText.class,
            InterpretTextByDefaultInterpreter.class,
            ListConversations.class,
            ListenAndAnswer.class,
            StartDialog.class,
            StopDialog.class,
            TextToSpeech.class
        })
public class VoiceCommand {}
