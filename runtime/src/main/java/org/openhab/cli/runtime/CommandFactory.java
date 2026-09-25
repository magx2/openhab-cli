package org.openhab.cli.runtime;

import javax.inject.Inject;
import javax.inject.Provider;
import org.openhab.cli.runtime.action.AvailableActionsForThing;
import picocli.CommandLine;

/** Creates injected commands with Dagger and delegates other Picocli objects to its default factory. */
public final class CommandFactory implements CommandLine.IFactory {
    private final Provider<AvailableActionsForThing> availableActionsForThing;
    private final CommandLine.IFactory fallback = CommandLine.defaultFactory();

    @Inject
    CommandFactory(Provider<AvailableActionsForThing> availableActionsForThing) {
        this.availableActionsForThing = availableActionsForThing;
    }

    @Override
    public <K> K create(Class<K> type) throws Exception {
        if (type == AvailableActionsForThing.class) {
            return type.cast(availableActionsForThing.get());
        }
        return fallback.create(type);
    }
}
