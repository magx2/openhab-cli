package org.openhab.cli.runtime.command.rules;

import picocli.CommandLine.Command;

/** Picocli command group for Rules operations. */
@Command(
        name = "rules",
        description = "Commands for Rules.",
        mixinStandardHelpOptions = true,
        subcommands = {
            CreateRule.class,
            DeleteRule.class,
            EnableRule.class,
            RuleActions.class,
            RuleById.class,
            RuleConditions.class,
            RuleConfiguration.class,
            RuleModuleById.class,
            RuleModuleConfig.class,
            RuleModuleConfigParameter.class,
            RuleTriggers.class,
            Rules.class,
            ScheduleRuleSimulations.class,
            RegenerateRule.class,
            RunRuleNow1.class,
            SetRuleModuleConfigParameter.class,
            UpdateRule.class,
            UpdateRuleConfiguration.class
        })
public class RulesCommand {}
