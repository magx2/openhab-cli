package org.openhab.cli.runtime;

import dagger.Component;

/** Provides the application dependencies used to construct CLI commands. */
@Component
interface RuntimeComponent {
    CommandFactory commandFactory();

    ExitCodeMapper exitCodeMapper();
}
