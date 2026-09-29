package org.openhab.cli.runtime;

import org.openhab.cli.engine.Version;
import org.openhab.cli.runtime.command.action.ActionCommand;
import org.openhab.cli.runtime.command.addons.AddonsCommand;
import org.openhab.cli.runtime.command.audio.AudioCommand;
import org.openhab.cli.runtime.command.auth.AuthCommand;
import org.openhab.cli.runtime.command.channeltypes.ChannelTypesCommand;
import org.openhab.cli.runtime.command.config.ConfigCommand;
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
            ConfigCommand.class,
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
            "96: Invalid argument value", //
            "97: Invalid application state", //
            "98: I/O operation failed (for example, reading the properties file)", //
            "99: openHAB API request failed" //
        })
public class Cli implements Runnable {
    @CommandLine.Spec
    private CommandLine.Model.CommandSpec spec;

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
            log.debug("oh invoked with {} arguments", args.length);
        }
        int exitCode = commandLine().execute(args);
        System.exit(exitCode);
    }

    /** Creates a parser with Dagger-backed commands and application exit-code handling. */
    static CommandLine commandLine() {
        var component = DaggerRuntimeComponent.create();
        var exitCodeMapper = component.exitCodeMapper();
        return new CommandLine(new Cli(), component.commandFactory())
                .setCaseInsensitiveEnumValuesAllowed(true)
                .setExitCodeExceptionMapper(exitCodeMapper)
                .setExecutionExceptionHandler(executionExceptionHandler(exitCodeMapper));
    }

    /** Routes command failures to the mapper without Picocli printing an additional stack trace. */
    static CommandLine.IExecutionExceptionHandler executionExceptionHandler(ExitCodeMapper exitCodeMapper) {
        return (exception, commandLine, parseResult) -> exitCodeMapper.getExitCode(exception, prettyPrint(parseResult));
    }

    private static boolean prettyPrint(CommandLine.ParseResult parseResult) {
        var commandParseResult = parseResult;
        while (commandParseResult.subcommand() != null) {
            commandParseResult = commandParseResult.subcommand();
        }
        return commandParseResult.matchedOptionValue("--pretty-print", true);
    }

    /** Prints usage when the CLI is invoked without a subcommand. */
    @Override
    public void run() {
        spec.commandLine().usage(spec.commandLine().getOut());
    }
}
