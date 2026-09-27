package org.openhab.cli.runtime.command.items;

import javax.inject.Inject;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.engine.endpoint.Items;
import org.openhab.cli.runtime.Options;
import org.openhab.cli.runtime.service.ApiClientBuilder;
import org.openhab.cli.runtime.service.Console;
import picocli.CommandLine;

/** Adds a new member to a group item. */
@Slf4j
@CommandLine.Command(
        name = "addMemberToGroupItem",
        description = "Adds a new member to a group item.",
        mixinStandardHelpOptions = true)
@RequiredArgsConstructor(access = AccessLevel.PACKAGE, onConstructor_ = @Inject)
public class AddMemberToGroupItem implements Runnable {
    @CommandLine.Mixin
    private Options options;

    @CommandLine.Parameters(index = "0", arity = "1", paramLabel = "<itemName>", description = "item name (required)")
    private String itemName;

    @CommandLine.Parameters(
            index = "1",
            arity = "1",
            paramLabel = "<memberItemName>",
            description = "member item name (required)")
    private String memberItemName;

    private final Console console;
    private final ApiClientBuilder apiClientBuilder;

    /** Executes {@link Items#addMemberToGroupItem}. */
    @Override
    public void run() {
        log.debug("Command: Items.addMemberToGroupItem");
        var apiClient = apiClientBuilder.build(options);
        var endpoint = new Items(apiClient);
        endpoint.addMemberToGroupItem(itemName, memberItemName);
    }
}
