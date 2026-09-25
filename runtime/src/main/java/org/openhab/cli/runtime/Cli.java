package org.openhab.cli.runtime;

import static java.lang.String.join;

import java.util.concurrent.Callable;
import org.openhab.cli.engine.Version;
import org.slf4j.LoggerFactory;
import picocli.CommandLine;
import picocli.CommandLine.Command;

@Command(
        subcommands = {
            org.openhab.cli.runtime.action.ActionCommand.class,
            org.openhab.cli.runtime.addons.AddonsCommand.class,
            org.openhab.cli.runtime.audio.AudioCommand.class,
            org.openhab.cli.runtime.auth.AuthCommand.class,
            org.openhab.cli.runtime.channeltypes.ChannelTypesCommand.class,
            org.openhab.cli.runtime.configdescriptions.ConfigDescriptionsCommand.class,
            org.openhab.cli.runtime.discovery.DiscoveryCommand.class,
            org.openhab.cli.runtime.engineinternal.EngineInternalCommand.class,
            org.openhab.cli.runtime.events.EventsCommand.class,
            org.openhab.cli.runtime.fileformat.FileFormatCommand.class,
            org.openhab.cli.runtime.iconsets.IconsetsCommand.class,
            org.openhab.cli.runtime.inbox.InboxCommand.class,
            org.openhab.cli.runtime.items.ItemsCommand.class,
            org.openhab.cli.runtime.links.LinksCommand.class,
            org.openhab.cli.runtime.logging.LoggingCommand.class,
            org.openhab.cli.runtime.moduletypes.ModuleTypesCommand.class,
            org.openhab.cli.runtime.persistence.PersistenceCommand.class,
            org.openhab.cli.runtime.profiletypes.ProfileTypesCommand.class,
            org.openhab.cli.runtime.root.RootCommand.class,
            org.openhab.cli.runtime.rules.RulesCommand.class,
            org.openhab.cli.runtime.services.ServicesCommand.class,
            org.openhab.cli.runtime.sitemaps.SitemapsCommand.class,
            org.openhab.cli.runtime.systeminfo.SystemInfoCommand.class,
            org.openhab.cli.runtime.tags.TagsCommand.class,
            org.openhab.cli.runtime.templates.TemplatesCommand.class,
            org.openhab.cli.runtime.thingtypes.ThingTypesCommand.class,
            org.openhab.cli.runtime.things.ThingsCommand.class,
            org.openhab.cli.runtime.transformations.TransformationsCommand.class,
            org.openhab.cli.runtime.ui.UiCommand.class,
            org.openhab.cli.runtime.uuid.UuidCommand.class,
            org.openhab.cli.runtime.voice.VoiceCommand.class
        },
        mixinStandardHelpOptions = true,
        versionProvider = Cli.VersionProvider.class,
        exitCodeListHeading = "Exit Codes:%n",
        exitCodeList = { //
            " 0: Successful program execution", //
            " 1: Command execution failed", //
            "98: I/O operation failed (for example, reading the properties file)", //
            "99: openHAB API request failed" //
        })
public class Cli implements Callable<Integer> {
    /** Supplies the application version embedded by the engine build. */
    public static class VersionProvider implements CommandLine.IVersionProvider {
        @Override
        public String[] getVersion() {
            return new String[] {Version.VERSION};
        }
    }

    public static void main(String[] args) {
        Logging.configure();
        var log = LoggerFactory.getLogger(Cli.class);
        if (log.isDebugEnabled()) {
            log.debug("oh {}", join(" ", args));
        }
        int exitCode = commandLine().execute(args);
        System.exit(exitCode);
    }

    /** Creates a parser with Dagger-backed commands and application exit-code handling. */
    static CommandLine commandLine() {
        var component = DaggerRuntimeComponent.create();
        return new CommandLine(new Cli(), component.commandFactory())
                .setExitCodeExceptionMapper(component.exitCodeMapper());
    }

    @Override
    public Integer call() throws Exception {
        System.out.println("run");
        return 1;
    }
}
