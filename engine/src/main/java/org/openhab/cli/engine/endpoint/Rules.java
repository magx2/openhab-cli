package org.openhab.cli.engine.endpoint;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.client.ApiException;
import org.openhab.cli.client.api.RulesApi;
import org.openhab.cli.client.model.Action;
import org.openhab.cli.client.model.Condition;
import org.openhab.cli.client.model.EnrichedRule;
import org.openhab.cli.client.model.Module;
import org.openhab.cli.client.model.Rule;
import org.openhab.cli.client.model.RuleExecution;
import org.openhab.cli.client.model.Trigger;
import org.openhab.cli.engine.rest.ApiClient;

/**
 * Synchronous access to the openHAB operations provided by {@link RulesApi}.
 */
@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public final class Rules {
    private final RulesApi api;

    /**
     * Creates an endpoint using the supplied REST client.
     *
     * @param apiClient the client providing connection and authentication settings
     */
    public Rules(ApiClient apiClient) {
        this(new RulesApi(apiClient.toNative()));
    }

    /**
     * Creates a rule.
     *
     * @param rule rule data (required)
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public void createRule(Rule rule) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("rule", rule);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Rules.createRuleWithHttpInfo({})", params);
            }
            var response = api.createRuleWithHttpInfo(rule);
            if (debugEnabled) {
                log.debug("RES: Rules.createRuleWithHttpInfo({}): status_code: {}", params, response.getStatusCode());
            }
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("rule", rule);
            }
            throw new EndpointException(this.getClass(), "createRuleWithHttpInfo", params, e);
        }
    }

    /**
     * Removes an existing rule corresponding to the given UID.
     *
     * @param ruleUID ruleUID (required)
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public void deleteRule(String ruleUID) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("ruleUID", ruleUID);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Rules.deleteRuleWithHttpInfo({})", params);
            }
            var response = api.deleteRuleWithHttpInfo(ruleUID);
            if (debugEnabled) {
                log.debug("RES: Rules.deleteRuleWithHttpInfo({}): status_code: {}", params, response.getStatusCode());
            }
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("ruleUID", ruleUID);
            }
            throw new EndpointException(this.getClass(), "deleteRuleWithHttpInfo", params, e);
        }
    }

    /**
     * Sets the rule enabled status.
     *
     * @param ruleUID ruleUID (required)
     * @param body enable (required)
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public void enableRule(String ruleUID, String body) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("ruleUID", ruleUID);
            params.put("body", body);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Rules.enableRuleWithHttpInfo({})", params);
            }
            var response = api.enableRuleWithHttpInfo(ruleUID, body);
            if (debugEnabled) {
                log.debug("RES: Rules.enableRuleWithHttpInfo({}): status_code: {}", params, response.getStatusCode());
            }
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("ruleUID", ruleUID);
                params.put("body", body);
            }
            throw new EndpointException(this.getClass(), "enableRuleWithHttpInfo", params, e);
        }
    }

    /**
     * Gets the rule actions.
     *
     * @param ruleUID ruleUID (required)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public List<Action> ruleActions(String ruleUID) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("ruleUID", ruleUID);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Rules.getRuleActionsWithHttpInfo({})", params);
            }
            var response = api.getRuleActionsWithHttpInfo(ruleUID);
            var data = response.getData();
            if (debugEnabled) {
                log.debug(
                        "RES: Rules.getRuleActionsWithHttpInfo({}): status_code: {}, data: {}",
                        params,
                        response.getStatusCode(),
                        data);
            }
            return data;
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("ruleUID", ruleUID);
            }
            throw new EndpointException(this.getClass(), "getRuleActionsWithHttpInfo", params, e);
        }
    }

    /**
     * Gets the rule corresponding to the given UID.
     *
     * @param ruleUID ruleUID (required)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public EnrichedRule ruleById(String ruleUID) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("ruleUID", ruleUID);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Rules.getRuleByIdWithHttpInfo({})", params);
            }
            var response = api.getRuleByIdWithHttpInfo(ruleUID);
            var data = response.getData();
            if (debugEnabled) {
                log.debug(
                        "RES: Rules.getRuleByIdWithHttpInfo({}): status_code: {}, data: {}",
                        params,
                        response.getStatusCode(),
                        data);
            }
            return data;
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("ruleUID", ruleUID);
            }
            throw new EndpointException(this.getClass(), "getRuleByIdWithHttpInfo", params, e);
        }
    }

    /**
     * Gets the rule conditions.
     *
     * @param ruleUID ruleUID (required)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public List<Condition> ruleConditions(String ruleUID) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("ruleUID", ruleUID);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Rules.getRuleConditionsWithHttpInfo({})", params);
            }
            var response = api.getRuleConditionsWithHttpInfo(ruleUID);
            var data = response.getData();
            if (debugEnabled) {
                log.debug(
                        "RES: Rules.getRuleConditionsWithHttpInfo({}): status_code: {}, data: {}",
                        params,
                        response.getStatusCode(),
                        data);
            }
            return data;
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("ruleUID", ruleUID);
            }
            throw new EndpointException(this.getClass(), "getRuleConditionsWithHttpInfo", params, e);
        }
    }

    /**
     * Gets the rule configuration values.
     *
     * @param ruleUID ruleUID (required)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public String ruleConfiguration(String ruleUID) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("ruleUID", ruleUID);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Rules.getRuleConfigurationWithHttpInfo({})", params);
            }
            var response = api.getRuleConfigurationWithHttpInfo(ruleUID);
            var data = response.getData();
            if (debugEnabled) {
                log.debug(
                        "RES: Rules.getRuleConfigurationWithHttpInfo({}): status_code: {}, data: {}",
                        params,
                        response.getStatusCode(),
                        data);
            }
            return data;
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("ruleUID", ruleUID);
            }
            throw new EndpointException(this.getClass(), "getRuleConfigurationWithHttpInfo", params, e);
        }
    }

    /**
     * Gets the rule&#39;s module corresponding to the given Category and ID.
     *
     * @param ruleUID ruleUID (required)
     * @param moduleCategory moduleCategory (required)
     * @param id id (required)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public Module ruleModuleById(String ruleUID, String moduleCategory, String id) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("ruleUID", ruleUID);
            params.put("moduleCategory", moduleCategory);
            params.put("id", id);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Rules.getRuleModuleByIdWithHttpInfo({})", params);
            }
            var response = api.getRuleModuleByIdWithHttpInfo(ruleUID, moduleCategory, id);
            var data = response.getData();
            if (debugEnabled) {
                log.debug(
                        "RES: Rules.getRuleModuleByIdWithHttpInfo({}): status_code: {}, data: {}",
                        params,
                        response.getStatusCode(),
                        data);
            }
            return data;
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("ruleUID", ruleUID);
                params.put("moduleCategory", moduleCategory);
                params.put("id", id);
            }
            throw new EndpointException(this.getClass(), "getRuleModuleByIdWithHttpInfo", params, e);
        }
    }

    /**
     * Gets the module&#39;s configuration.
     *
     * @param ruleUID ruleUID (required)
     * @param moduleCategory moduleCategory (required)
     * @param id id (required)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public String ruleModuleConfig(String ruleUID, String moduleCategory, String id) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("ruleUID", ruleUID);
            params.put("moduleCategory", moduleCategory);
            params.put("id", id);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Rules.getRuleModuleConfigWithHttpInfo({})", params);
            }
            var response = api.getRuleModuleConfigWithHttpInfo(ruleUID, moduleCategory, id);
            var data = response.getData();
            if (debugEnabled) {
                log.debug(
                        "RES: Rules.getRuleModuleConfigWithHttpInfo({}): status_code: {}, data: {}",
                        params,
                        response.getStatusCode(),
                        data);
            }
            return data;
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("ruleUID", ruleUID);
                params.put("moduleCategory", moduleCategory);
                params.put("id", id);
            }
            throw new EndpointException(this.getClass(), "getRuleModuleConfigWithHttpInfo", params, e);
        }
    }

    /**
     * Gets the module&#39;s configuration parameter.
     *
     * @param ruleUID ruleUID (required)
     * @param moduleCategory moduleCategory (required)
     * @param id id (required)
     * @param param param (required)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public String ruleModuleConfigParameter(String ruleUID, String moduleCategory, String id, String param) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("ruleUID", ruleUID);
            params.put("moduleCategory", moduleCategory);
            params.put("id", id);
            params.put("param", param);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Rules.getRuleModuleConfigParameterWithHttpInfo({})", params);
            }
            var response = api.getRuleModuleConfigParameterWithHttpInfo(ruleUID, moduleCategory, id, param);
            var data = response.getData();
            if (debugEnabled) {
                log.debug(
                        "RES: Rules.getRuleModuleConfigParameterWithHttpInfo({}): status_code: {}, data: {}",
                        params,
                        response.getStatusCode(),
                        data);
            }
            return data;
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("ruleUID", ruleUID);
                params.put("moduleCategory", moduleCategory);
                params.put("id", id);
                params.put("param", param);
            }
            throw new EndpointException(this.getClass(), "getRuleModuleConfigParameterWithHttpInfo", params, e);
        }
    }

    /**
     * Gets the rule triggers.
     *
     * @param ruleUID ruleUID (required)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public List<Trigger> ruleTriggers(String ruleUID) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("ruleUID", ruleUID);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Rules.getRuleTriggersWithHttpInfo({})", params);
            }
            var response = api.getRuleTriggersWithHttpInfo(ruleUID);
            var data = response.getData();
            if (debugEnabled) {
                log.debug(
                        "RES: Rules.getRuleTriggersWithHttpInfo({}): status_code: {}, data: {}",
                        params,
                        response.getStatusCode(),
                        data);
            }
            return data;
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("ruleUID", ruleUID);
            }
            throw new EndpointException(this.getClass(), "getRuleTriggersWithHttpInfo", params, e);
        }
    }

    /**
     * Get available rules, optionally filtered by tags and/or prefix.
     *
     * @param prefix  (optional)
     * @param tags  (optional)
     * @param summary summary fields only (optional)
     * @param staticDataOnly provides a cacheable list of values not expected to change regularly and honors the If-Modified-Since header, all other parameters are ignored (optional, default to false)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public List<EnrichedRule> rules(String prefix, List<String> tags, Boolean summary, Boolean staticDataOnly) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("prefix", prefix);
            params.put("tags", tags);
            params.put("summary", summary);
            params.put("staticDataOnly", staticDataOnly);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Rules.getRulesWithHttpInfo({})", params);
            }
            var response = api.getRulesWithHttpInfo(prefix, tags, summary, staticDataOnly);
            var data = response.getData();
            if (debugEnabled) {
                log.debug(
                        "RES: Rules.getRulesWithHttpInfo({}): status_code: {}, data: {}",
                        params,
                        response.getStatusCode(),
                        data);
            }
            return data;
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("prefix", prefix);
                params.put("tags", tags);
                params.put("summary", summary);
                params.put("staticDataOnly", staticDataOnly);
            }
            throw new EndpointException(this.getClass(), "getRulesWithHttpInfo", params, e);
        }
    }

    /**
     * Get available rules, optionally filtered by tags and/or prefix.
     *
     * <p>Omitted optional arguments are passed as {@code null}, leaving their defaults to the API.
     *
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public List<EnrichedRule> rules() {
        return rules(null, null, null, null);
    }

    /**
     * Simulates the executions of rules filtered by tag &#39;Schedule&#39; within the given times.
     *
     * @param from Start time of the simulated rule executions. Will default to the current time. [yyyy-MM-dd&#39;T&#39;HH:mm:ss.SSSZ] (optional)
     * @param until End time of the simulated rule executions. Will default to 30 days after the start time. Must be less than 180 days after the given start time. [yyyy-MM-dd&#39;T&#39;HH:mm:ss.SSSZ] (optional)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public List<RuleExecution> scheduleRuleSimulations(String from, String until) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("from", from);
            params.put("until", until);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Rules.getScheduleRuleSimulationsWithHttpInfo({})", params);
            }
            var response = api.getScheduleRuleSimulationsWithHttpInfo(from, until);
            var data = response.getData();
            if (debugEnabled) {
                log.debug(
                        "RES: Rules.getScheduleRuleSimulationsWithHttpInfo({}): status_code: {}, data: {}",
                        params,
                        response.getStatusCode(),
                        data);
            }
            return data;
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("from", from);
                params.put("until", until);
            }
            throw new EndpointException(this.getClass(), "getScheduleRuleSimulationsWithHttpInfo", params, e);
        }
    }

    /**
     * Simulates the executions of rules filtered by tag &#39;Schedule&#39; within the given times.
     *
     * <p>Omitted optional arguments are passed as {@code null}, leaving their defaults to the API.
     *
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public List<RuleExecution> scheduleRuleSimulations() {
        return scheduleRuleSimulations(null, null);
    }

    /**
     * Regenerates the rule from its template.
     *
     * @param ruleUID ruleUID (required)
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public void regenerateRule(String ruleUID) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("ruleUID", ruleUID);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Rules.regenerateRuleWithHttpInfo({})", params);
            }
            var response = api.regenerateRuleWithHttpInfo(ruleUID);
            if (debugEnabled) {
                log.debug(
                        "RES: Rules.regenerateRuleWithHttpInfo({}): status_code: {}", params, response.getStatusCode());
            }
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("ruleUID", ruleUID);
            }
            throw new EndpointException(this.getClass(), "regenerateRuleWithHttpInfo", params, e);
        }
    }

    /**
     * Executes actions of the rule.
     *
     * @param ruleUID ruleUID (required)
     * @param requestBody the context for running this rule (optional)
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public void runRuleNow1(String ruleUID, Map<String, Object> requestBody) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("ruleUID", ruleUID);
            params.put("requestBody", requestBody);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Rules.runRuleNow1WithHttpInfo({})", params);
            }
            var response = api.runRuleNow1WithHttpInfo(ruleUID, requestBody);
            if (debugEnabled) {
                log.debug("RES: Rules.runRuleNow1WithHttpInfo({}): status_code: {}", params, response.getStatusCode());
            }
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("ruleUID", ruleUID);
                params.put("requestBody", requestBody);
            }
            throw new EndpointException(this.getClass(), "runRuleNow1WithHttpInfo", params, e);
        }
    }

    /**
     * Executes actions of the rule.
     *
     * <p>Omitted optional arguments are passed as {@code null}, leaving their defaults to the API.
     *
     * @param ruleUID ruleUID (required)
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public void runRuleNow1(String ruleUID) {
        runRuleNow1(ruleUID, null);
    }

    /**
     * Sets the module&#39;s configuration parameter value.
     *
     * @param ruleUID ruleUID (required)
     * @param moduleCategory moduleCategory (required)
     * @param id id (required)
     * @param param param (required)
     * @param body value (required)
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public void setRuleModuleConfigParameter(
            String ruleUID, String moduleCategory, String id, String param, String body) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("ruleUID", ruleUID);
            params.put("moduleCategory", moduleCategory);
            params.put("id", id);
            params.put("param", param);
            params.put("body", body);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Rules.setRuleModuleConfigParameterWithHttpInfo({})", params);
            }
            var response = api.setRuleModuleConfigParameterWithHttpInfo(ruleUID, moduleCategory, id, param, body);
            if (debugEnabled) {
                log.debug(
                        "RES: Rules.setRuleModuleConfigParameterWithHttpInfo({}): status_code: {}",
                        params,
                        response.getStatusCode());
            }
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("ruleUID", ruleUID);
                params.put("moduleCategory", moduleCategory);
                params.put("id", id);
                params.put("param", param);
                params.put("body", body);
            }
            throw new EndpointException(this.getClass(), "setRuleModuleConfigParameterWithHttpInfo", params, e);
        }
    }

    /**
     * Updates an existing rule corresponding to the given UID.
     *
     * @param ruleUID ruleUID (required)
     * @param rule rule data (required)
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public void updateRule(String ruleUID, Rule rule) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("ruleUID", ruleUID);
            params.put("rule", rule);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Rules.updateRuleWithHttpInfo({})", params);
            }
            var response = api.updateRuleWithHttpInfo(ruleUID, rule);
            if (debugEnabled) {
                log.debug("RES: Rules.updateRuleWithHttpInfo({}): status_code: {}", params, response.getStatusCode());
            }
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("ruleUID", ruleUID);
                params.put("rule", rule);
            }
            throw new EndpointException(this.getClass(), "updateRuleWithHttpInfo", params, e);
        }
    }

    /**
     * Sets the rule configuration values.
     *
     * @param ruleUID ruleUID (required)
     * @param requestBody config (optional)
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public void updateRuleConfiguration(String ruleUID, Map<String, Object> requestBody) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("ruleUID", ruleUID);
            params.put("requestBody", requestBody);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Rules.updateRuleConfigurationWithHttpInfo({})", params);
            }
            var response = api.updateRuleConfigurationWithHttpInfo(ruleUID, requestBody);
            if (debugEnabled) {
                log.debug(
                        "RES: Rules.updateRuleConfigurationWithHttpInfo({}): status_code: {}",
                        params,
                        response.getStatusCode());
            }
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("ruleUID", ruleUID);
                params.put("requestBody", requestBody);
            }
            throw new EndpointException(this.getClass(), "updateRuleConfigurationWithHttpInfo", params, e);
        }
    }

    /**
     * Sets the rule configuration values.
     *
     * <p>Omitted optional arguments are passed as {@code null}, leaving their defaults to the API.
     *
     * @param ruleUID ruleUID (required)
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public void updateRuleConfiguration(String ruleUID) {
        updateRuleConfiguration(ruleUID, null);
    }
}
