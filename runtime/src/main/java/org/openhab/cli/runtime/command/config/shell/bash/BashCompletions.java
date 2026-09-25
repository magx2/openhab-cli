package org.openhab.cli.runtime.command.config.shell.bash;

import java.util.ArrayList;
import java.util.Locale;
import picocli.AutoComplete;
import picocli.CommandLine;
import picocli.CommandLine.Model.ArgSpec;
import picocli.CommandLine.Model.CommandSpec;
import picocli.CommandLine.Model.OptionSpec;
import picocli.CommandLine.Model.PositionalParamSpec;

/** Adapts CLI metadata for Picocli's Bash generator without changing the running parser. */
final class BashCompletions {
    private BashCompletions() {}

    static String generate(CommandSpec root) {
        return AutoComplete.bash("oh", new CommandLine(copy(root))).replace("\r\n", "\n");
    }

    private static CommandSpec copy(CommandSpec source) {
        var copy = CommandSpec.create().name(source.name());
        copy.usageMessage().hidden(source.usageMessage().hidden());
        for (var option : source.options()) {
            // Picocli's Bash generator reads option names but does not expand negatable options.
            var builder = OptionSpec.builder(option).negatable(false);
            var candidates = candidates(option);
            if (candidates != null) builder.completionCandidates(candidates);
            copy.addOption(builder.build());
        }
        source.negatedOptionsMap()
                .forEach((name, option) -> copy.addOption(OptionSpec.builder(name)
                        .type(boolean.class)
                        .hidden(option.hidden())
                        .description(option.description())
                        .build()));
        for (var parameter : source.positionalParameters()) {
            var builder = PositionalParamSpec.builder(parameter);
            var candidates = candidates(parameter);
            if (candidates != null) builder.completionCandidates(candidates);
            copy.addPositional(builder.build());
        }
        source.subcommands()
                .forEach((name, command) -> copy.addSubcommand(name, new CommandLine(copy(command.getCommandSpec()))));
        return copy;
    }

    private static Iterable<String> candidates(ArgSpec argument) {
        if (!argument.type().isEnum()) return argument.completionCandidates();
        var result = new ArrayList<String>();
        if (argument.completionCandidates() != null) {
            argument.completionCandidates().forEach(value -> result.add(value.toLowerCase(Locale.ROOT)));
        } else {
            for (var value : argument.type().getEnumConstants()) {
                result.add(((Enum<?>) value).name().toLowerCase(Locale.ROOT));
            }
        }
        return result;
    }
}
