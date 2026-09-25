package org.openhab.cli.runtime;

import static java.lang.String.join;

import java.util.concurrent.Callable;
import org.openhab.cli.engine.Version;
import org.openhab.cli.runtime.command.action.ActionCommand;
import org.openhab.cli.runtime.command.addons.AddonsCommand;
import org.openhab.cli.runtime.command.audio.AudioCommand;
import org.openhab.cli.runtime.command.auth.AuthCommand;
import org.openhab.cli.runtime.command.channeltypes.ChannelTypesCommand;
import org.openhab.cli.runtime.command.configdescriptions.ConfigDescriptionsCommand;
import org.openhab.cli.runtime.command.discovery.DiscoveryCommand;
import org.openhab.cli.runtime.command.engineinternal.EngineInternalCommand;
import org.openhab.cli.runtime.command.events.EventsCommand;
import org.openhab.cli.runtime.command.fileformat.FileFormatCommand;
import org.openhab.cli.runtime.command.iconsets.IconsetsCommand;
import org.openhab.cli.runtime.command.inbox.InboxCommand;
import org.openhab.cli.runtime.command.items.ItemsCommand;
import org.openhab.cli.runtime.command.links.LinksCommand;
import org.openhab.cli.runtime.command.logging.LoggingCommand;
import org.openhab.cli.runtime.command.moduletypes.ModuleTypesCommand;
import org.openhab.cli.runtime.command.persistence.PersistenceCommand;
import org.openhab.cli.runtime.command.profiletypes.ProfileTypesCommand;
import org.openhab.cli.runtime.command.root.RootCommand;
import org.openhab.cli.runtime.command.rules.RulesCommand;
import org.openhab.cli.runtime.command.services.ServicesCommand;
import org.openhab.cli.runtime.command.sitemaps.SitemapsCommand;
import org.openhab.cli.runtime.command.systeminfo.SystemInfoCommand;
import org.openhab.cli.runtime.command.tags.TagsCommand;
import org.openhab.cli.runtime.command.templates.TemplatesCommand;
import org.openhab.cli.runtime.command.things.ThingsCommand;
import org.openhab.cli.runtime.command.thingtypes.ThingTypesCommand;
import org.openhab.cli.runtime.command.transformations.TransformationsCommand;
import org.openhab.cli.runtime.command.ui.UiCommand;
import org.openhab.cli.runtime.command.uuid.UuidCommand;
import org.openhab.cli.runtime.command.voice.VoiceCommand;
import org.slf4j.LoggerFactory;
import picocli.CommandLine;
import picocli.CommandLine.Command;

@Command(
        subcommands = {
            ActionCommand.class,
            AddonsCommand.class,
            AudioCommand.class,
            AuthCommand.class,
            ChannelTypesCommand.class,
            ConfigDescriptionsCommand.class,
            DiscoveryCommand.class,
            EngineInternalCommand.class,
            EventsCommand.class,
            FileFormatCommand.class,
            IconsetsCommand.class,
            InboxCommand.class,
            ItemsCommand.class,
            LinksCommand.class,
            LoggingCommand.class,
            ModuleTypesCommand.class,
            PersistenceCommand.class,
            ProfileTypesCommand.class,
            RootCommand.class,
            RulesCommand.class,
            ServicesCommand.class,
            SitemapsCommand.class,
            SystemInfoCommand.class,
            TagsCommand.class,
            TemplatesCommand.class,
            ThingTypesCommand.class,
            ThingsCommand.class,
            TransformationsCommand.class,
            UiCommand.class,
            UuidCommand.class,
            VoiceCommand.class
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
