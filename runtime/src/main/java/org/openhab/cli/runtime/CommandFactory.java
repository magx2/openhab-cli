package org.openhab.cli.runtime;

import java.util.Map;
import java.util.concurrent.Callable;
import javax.inject.Inject;
import javax.inject.Provider;
import picocli.CommandLine;

/** Creates injected commands with Dagger and delegates other Picocli objects to its default factory. */
public final class CommandFactory implements CommandLine.IFactory {
    private final Map<Class<?>, Provider<Callable<Integer>>> commands;
    private final Map<Class<?>, Provider<Runnable>> runnableCommands;
    private final CommandLine.IFactory fallback = CommandLine.defaultFactory();

    @Inject
    CommandFactory(
            Map<Class<?>, Provider<Callable<Integer>>> commands, Map<Class<?>, Provider<Runnable>> runnableCommands) {
        this.commands = commands;
        this.runnableCommands = runnableCommands;
    }

    @Override
    public <K> K create(Class<K> type) throws Exception {
        Provider<?> provider = runnableCommands.get(type);
        if (provider == null) provider = commands.get(type);
        return provider == null ? fallback.create(type) : type.cast(provider.get());
    }
}
