package org.openhab.cli.engine.endpoint;

import java.util.LinkedHashMap;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.client.ApiException;
import org.openhab.cli.client.api.VoiceApi;
import org.openhab.cli.client.model.Conversation;
import org.openhab.cli.client.model.ConversationInfo;
import org.openhab.cli.client.model.HumanLanguageInterpreter;
import org.openhab.cli.client.model.LLMTool;
import org.openhab.cli.engine.rest.ApiClient;

/**
 * Synchronous access to the openHAB operations provided by {@link VoiceApi}.
 */
@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public final class Voice implements Endpoint {
    private final VoiceApi api;

    /**
     * Creates an endpoint using the supplied REST client.
     *
     * @param apiClient the client providing connection and authentication settings
     */
    public Voice(ApiClient apiClient) {
        this(new VoiceApi(apiClient.toNative()));
    }

    /**
     * Deletes a full conversation or its messages since a given message id.
     *
     * @param id conversation id (required)
     * @param messageId Optional message ID (optional)
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public void deleteConversationById(String id, Integer messageId) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("id", id);
            params.put("messageId", messageId);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Voice.deleteConversationByIdWithHttpInfo({})", params);
            }
            var response = api.deleteConversationByIdWithHttpInfo(id, messageId);
            if (debugEnabled) {
                log.debug(
                        "RES: Voice.deleteConversationByIdWithHttpInfo({}): status_code: {}",
                        params,
                        response.getStatusCode());
            }
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("id", id);
                params.put("messageId", messageId);
            }
            throw new EndpointException(this.getClass(), "deleteConversationByIdWithHttpInfo", params, e);
        }
    }

    /**
     * Deletes a full conversation or its messages since a given message id.
     *
     * <p>Omitted optional arguments are passed as {@code null}, leaving their defaults to the API.
     *
     * @param id conversation id (required)
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public void deleteConversationById(String id) {
        deleteConversationById(id, null);
    }

    /**
     * Get a conversation.
     *
     * @param id conversation id (required)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public Conversation conversationById(String id) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("id", id);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Voice.getConversationByIdWithHttpInfo({})", params);
            }
            var response = api.getConversationByIdWithHttpInfo(id);
            var data = response.getData();
            if (debugEnabled) {
                log.debug(
                        "RES: Voice.getConversationByIdWithHttpInfo({}): status_code: {}, data: {}",
                        params,
                        response.getStatusCode(),
                        data);
            }
            return data;
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("id", id);
            }
            throw new EndpointException(this.getClass(), "getConversationByIdWithHttpInfo", params, e);
        }
    }

    /**
     * Gets the default voice.
     *
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public org.openhab.cli.client.model.Voice defaultVoice() {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Voice.getDefaultVoiceWithHttpInfo({})", params);
            }
            var response = api.getDefaultVoiceWithHttpInfo();
            var data = response.getData();
            if (debugEnabled) {
                log.debug(
                        "RES: Voice.getDefaultVoiceWithHttpInfo({}): status_code: {}, data: {}",
                        params,
                        response.getStatusCode(),
                        data);
            }
            return data;
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
            }
            throw new EndpointException(this.getClass(), "getDefaultVoiceWithHttpInfo", params, e);
        }
    }

    /**
     * Get the list of all LLM tools.
     *
     * @param acceptLanguage language (optional)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public List<LLMTool> llmTools(String acceptLanguage) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("acceptLanguage", acceptLanguage);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Voice.getLLMToolsWithHttpInfo({})", params);
            }
            var response = api.getLLMToolsWithHttpInfo(acceptLanguage);
            var data = response.getData();
            if (debugEnabled) {
                log.debug(
                        "RES: Voice.getLLMToolsWithHttpInfo({}): status_code: {}, data: {}",
                        params,
                        response.getStatusCode(),
                        data);
            }
            return data;
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("acceptLanguage", acceptLanguage);
            }
            throw new EndpointException(this.getClass(), "getLLMToolsWithHttpInfo", params, e);
        }
    }

    /**
     * Get the list of all LLM tools.
     *
     * <p>Omitted optional arguments are passed as {@code null}, leaving their defaults to the API.
     *
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public List<LLMTool> llmTools() {
        return llmTools(null);
    }

    /**
     * Gets a single interpreter.
     *
     * @param id interpreter id (required)
     * @param acceptLanguage language (optional)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public List<HumanLanguageInterpreter> voiceInterpreterByUID(String id, String acceptLanguage) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("id", id);
            params.put("acceptLanguage", acceptLanguage);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Voice.getVoiceInterpreterByUIDWithHttpInfo({})", params);
            }
            var response = api.getVoiceInterpreterByUIDWithHttpInfo(id, acceptLanguage);
            var data = response.getData();
            if (debugEnabled) {
                log.debug(
                        "RES: Voice.getVoiceInterpreterByUIDWithHttpInfo({}): status_code: {}, data: {}",
                        params,
                        response.getStatusCode(),
                        data);
            }
            return data;
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("id", id);
                params.put("acceptLanguage", acceptLanguage);
            }
            throw new EndpointException(this.getClass(), "getVoiceInterpreterByUIDWithHttpInfo", params, e);
        }
    }

    /**
     * Gets a single interpreter.
     *
     * <p>Omitted optional arguments are passed as {@code null}, leaving their defaults to the API.
     *
     * @param id interpreter id (required)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public List<HumanLanguageInterpreter> voiceInterpreterByUID(String id) {
        return voiceInterpreterByUID(id, null);
    }

    /**
     * Get the list of all interpreters.
     *
     * @param acceptLanguage language (optional)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public List<HumanLanguageInterpreter> voiceInterpreters(String acceptLanguage) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("acceptLanguage", acceptLanguage);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Voice.getVoiceInterpretersWithHttpInfo({})", params);
            }
            var response = api.getVoiceInterpretersWithHttpInfo(acceptLanguage);
            var data = response.getData();
            if (debugEnabled) {
                log.debug(
                        "RES: Voice.getVoiceInterpretersWithHttpInfo({}): status_code: {}, data: {}",
                        params,
                        response.getStatusCode(),
                        data);
            }
            return data;
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("acceptLanguage", acceptLanguage);
            }
            throw new EndpointException(this.getClass(), "getVoiceInterpretersWithHttpInfo", params, e);
        }
    }

    /**
     * Get the list of all interpreters.
     *
     * <p>Omitted optional arguments are passed as {@code null}, leaving their defaults to the API.
     *
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public List<HumanLanguageInterpreter> voiceInterpreters() {
        return voiceInterpreters(null);
    }

    /**
     * Get the list of all voices.
     *
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public List<org.openhab.cli.client.model.Voice> voices() {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Voice.getVoicesWithHttpInfo({})", params);
            }
            var response = api.getVoicesWithHttpInfo();
            var data = response.getData();
            if (debugEnabled) {
                log.debug(
                        "RES: Voice.getVoicesWithHttpInfo({}): status_code: {}, data: {}",
                        params,
                        response.getStatusCode(),
                        data);
            }
            return data;
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
            }
            throw new EndpointException(this.getClass(), "getVoicesWithHttpInfo", params, e);
        }
    }

    /**
     * Sends a text to a given human language interpreter(s).
     *
     * @param ids comma separated list of interpreter ids (required)
     * @param body text to interpret (required)
     * @param acceptLanguage language (optional)
     * @param conversation Conversation id (optional)
     * @param llmTools Comma separated list of llm-tool ids or * wildcard (optional)
     * @param locationItem Location item id to contextualize the command (optional)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public String interpretText(
            List<String> ids,
            String body,
            String acceptLanguage,
            String conversation,
            List<String> llmTools,
            String locationItem) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("ids", ids);
            params.put("body", body);
            params.put("acceptLanguage", acceptLanguage);
            params.put("conversation", conversation);
            params.put("llmTools", llmTools);
            params.put("locationItem", locationItem);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Voice.interpretTextWithHttpInfo({})", params);
            }
            var response =
                    api.interpretTextWithHttpInfo(ids, body, acceptLanguage, conversation, llmTools, locationItem);
            var data = response.getData();
            if (debugEnabled) {
                log.debug(
                        "RES: Voice.interpretTextWithHttpInfo({}): status_code: {}, data: {}",
                        params,
                        response.getStatusCode(),
                        data);
            }
            return data;
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("ids", ids);
                params.put("body", body);
                params.put("acceptLanguage", acceptLanguage);
                params.put("conversation", conversation);
                params.put("llmTools", llmTools);
                params.put("locationItem", locationItem);
            }
            throw new EndpointException(this.getClass(), "interpretTextWithHttpInfo", params, e);
        }
    }

    /**
     * Sends a text to a given human language interpreter(s).
     *
     * <p>Omitted optional arguments are passed as {@code null}, leaving their defaults to the API.
     *
     * @param ids comma separated list of interpreter ids (required)
     * @param body text to interpret (required)
     * @param conversation Conversation id (optional)
     * @param llmTools Comma separated list of llm-tool ids or * wildcard (optional)
     * @param locationItem Location item id to contextualize the command (optional)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public String interpretText(
            List<String> ids, String body, String conversation, List<String> llmTools, String locationItem) {
        return interpretText(ids, body, null, conversation, llmTools, locationItem);
    }

    /**
     * Sends a text to a given human language interpreter(s).
     *
     * <p>Omitted optional arguments are passed as {@code null}, leaving their defaults to the API.
     *
     * @param ids comma separated list of interpreter ids (required)
     * @param body text to interpret (required)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public String interpretText(List<String> ids, String body) {
        return interpretText(ids, body, null, null, null, null);
    }

    /**
     * Sends a text to the default human language interpreter.
     *
     * @param body text to interpret (required)
     * @param acceptLanguage language (optional)
     * @param conversation Conversation id (optional)
     * @param llmTools Comma separated list of llm-tool ids or * wildcard (optional)
     * @param locationItem Location item id to contextualize the command (optional)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public String interpretTextByDefaultInterpreter(
            String body, String acceptLanguage, String conversation, List<String> llmTools, String locationItem) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("body", body);
            params.put("acceptLanguage", acceptLanguage);
            params.put("conversation", conversation);
            params.put("llmTools", llmTools);
            params.put("locationItem", locationItem);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Voice.interpretTextByDefaultInterpreterWithHttpInfo({})", params);
            }
            var response = api.interpretTextByDefaultInterpreterWithHttpInfo(
                    body, acceptLanguage, conversation, llmTools, locationItem);
            var data = response.getData();
            if (debugEnabled) {
                log.debug(
                        "RES: Voice.interpretTextByDefaultInterpreterWithHttpInfo({}): status_code: {}, data: {}",
                        params,
                        response.getStatusCode(),
                        data);
            }
            return data;
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("body", body);
                params.put("acceptLanguage", acceptLanguage);
                params.put("conversation", conversation);
                params.put("llmTools", llmTools);
                params.put("locationItem", locationItem);
            }
            throw new EndpointException(this.getClass(), "interpretTextByDefaultInterpreterWithHttpInfo", params, e);
        }
    }

    /**
     * Sends a text to the default human language interpreter.
     *
     * <p>Omitted optional arguments are passed as {@code null}, leaving their defaults to the API.
     *
     * @param body text to interpret (required)
     * @param conversation Conversation id (optional)
     * @param llmTools Comma separated list of llm-tool ids or * wildcard (optional)
     * @param locationItem Location item id to contextualize the command (optional)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public String interpretTextByDefaultInterpreter(
            String body, String conversation, List<String> llmTools, String locationItem) {
        return interpretTextByDefaultInterpreter(body, null, conversation, llmTools, locationItem);
    }

    /**
     * Sends a text to the default human language interpreter.
     *
     * <p>Omitted optional arguments are passed as {@code null}, leaving their defaults to the API.
     *
     * @param body text to interpret (required)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public String interpretTextByDefaultInterpreter(String body) {
        return interpretTextByDefaultInterpreter(body, null, null, null, null);
    }

    /**
     * Get the metadata of all conversations.
     *
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public List<ConversationInfo> listConversations() {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Voice.listConversationsWithHttpInfo({})", params);
            }
            var response = api.listConversationsWithHttpInfo();
            var data = response.getData();
            if (debugEnabled) {
                log.debug(
                        "RES: Voice.listConversationsWithHttpInfo({}): status_code: {}, data: {}",
                        params,
                        response.getStatusCode(),
                        data);
            }
            return data;
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
            }
            throw new EndpointException(this.getClass(), "listConversationsWithHttpInfo", params, e);
        }
    }

    /**
     * Executes a simple dialog sequence without keyword spotting for a given audio source.
     *
     * @param acceptLanguage language (optional)
     * @param sourceId source ID (optional)
     * @param sttId Speech-to-Text ID (optional)
     * @param ttsId Text-to-Speech ID (optional)
     * @param voiceId voice ID (optional)
     * @param hliIds interpreter IDs (optional)
     * @param sinkId audio sink ID (optional)
     * @param listeningItem listening item (optional)
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public void listenAndAnswer(
            String acceptLanguage,
            String sourceId,
            String sttId,
            String ttsId,
            String voiceId,
            List<String> hliIds,
            String sinkId,
            String listeningItem) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("acceptLanguage", acceptLanguage);
            params.put("sourceId", sourceId);
            params.put("sttId", sttId);
            params.put("ttsId", ttsId);
            params.put("voiceId", voiceId);
            params.put("hliIds", hliIds);
            params.put("sinkId", sinkId);
            params.put("listeningItem", listeningItem);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Voice.listenAndAnswerWithHttpInfo({})", params);
            }
            var response = api.listenAndAnswerWithHttpInfo(
                    acceptLanguage, sourceId, sttId, ttsId, voiceId, hliIds, sinkId, listeningItem);
            if (debugEnabled) {
                log.debug(
                        "RES: Voice.listenAndAnswerWithHttpInfo({}): status_code: {}",
                        params,
                        response.getStatusCode());
            }
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("acceptLanguage", acceptLanguage);
                params.put("sourceId", sourceId);
                params.put("sttId", sttId);
                params.put("ttsId", ttsId);
                params.put("voiceId", voiceId);
                params.put("hliIds", hliIds);
                params.put("sinkId", sinkId);
                params.put("listeningItem", listeningItem);
            }
            throw new EndpointException(this.getClass(), "listenAndAnswerWithHttpInfo", params, e);
        }
    }

    /**
     * Executes a simple dialog sequence without keyword spotting for a given audio source.
     *
     * <p>Omitted optional arguments are passed as {@code null}, leaving their defaults to the API.
     *
     * @param sourceId source ID (optional)
     * @param sttId Speech-to-Text ID (optional)
     * @param ttsId Text-to-Speech ID (optional)
     * @param voiceId voice ID (optional)
     * @param hliIds interpreter IDs (optional)
     * @param sinkId audio sink ID (optional)
     * @param listeningItem listening item (optional)
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public void listenAndAnswer(
            String sourceId,
            String sttId,
            String ttsId,
            String voiceId,
            List<String> hliIds,
            String sinkId,
            String listeningItem) {
        listenAndAnswer(null, sourceId, sttId, ttsId, voiceId, hliIds, sinkId, listeningItem);
    }

    /**
     * Executes a simple dialog sequence without keyword spotting for a given audio source.
     *
     * <p>Omitted optional arguments are passed as {@code null}, leaving their defaults to the API.
     *
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public void listenAndAnswer() {
        listenAndAnswer(null, null, null, null, null, null, null, null);
    }

    /**
     * Start dialog processing for a given audio source.
     *
     * @param acceptLanguage language (optional)
     * @param sourceId source ID (optional)
     * @param ksId keywork spotter ID (optional)
     * @param sttId Speech-to-Text ID (optional)
     * @param ttsId Text-to-Speech ID (optional)
     * @param voiceId voice ID (optional)
     * @param hliIds comma separated list of interpreter IDs (optional)
     * @param sinkId audio sink ID (optional)
     * @param keyword keyword (optional)
     * @param listeningItem listening item (optional)
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public void startDialog(
            String acceptLanguage,
            String sourceId,
            String ksId,
            String sttId,
            String ttsId,
            String voiceId,
            String hliIds,
            String sinkId,
            String keyword,
            String listeningItem) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("acceptLanguage", acceptLanguage);
            params.put("sourceId", sourceId);
            params.put("ksId", ksId);
            params.put("sttId", sttId);
            params.put("ttsId", ttsId);
            params.put("voiceId", voiceId);
            params.put("hliIds", hliIds);
            params.put("sinkId", sinkId);
            params.put("keyword", keyword);
            params.put("listeningItem", listeningItem);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Voice.startDialogWithHttpInfo({})", params);
            }
            var response = api.startDialogWithHttpInfo(
                    acceptLanguage, sourceId, ksId, sttId, ttsId, voiceId, hliIds, sinkId, keyword, listeningItem);
            if (debugEnabled) {
                log.debug("RES: Voice.startDialogWithHttpInfo({}): status_code: {}", params, response.getStatusCode());
            }
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("acceptLanguage", acceptLanguage);
                params.put("sourceId", sourceId);
                params.put("ksId", ksId);
                params.put("sttId", sttId);
                params.put("ttsId", ttsId);
                params.put("voiceId", voiceId);
                params.put("hliIds", hliIds);
                params.put("sinkId", sinkId);
                params.put("keyword", keyword);
                params.put("listeningItem", listeningItem);
            }
            throw new EndpointException(this.getClass(), "startDialogWithHttpInfo", params, e);
        }
    }

    /**
     * Start dialog processing for a given audio source.
     *
     * <p>Omitted optional arguments are passed as {@code null}, leaving their defaults to the API.
     *
     * @param sourceId source ID (optional)
     * @param ksId keywork spotter ID (optional)
     * @param sttId Speech-to-Text ID (optional)
     * @param ttsId Text-to-Speech ID (optional)
     * @param voiceId voice ID (optional)
     * @param hliIds comma separated list of interpreter IDs (optional)
     * @param sinkId audio sink ID (optional)
     * @param keyword keyword (optional)
     * @param listeningItem listening item (optional)
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public void startDialog(
            String sourceId,
            String ksId,
            String sttId,
            String ttsId,
            String voiceId,
            String hliIds,
            String sinkId,
            String keyword,
            String listeningItem) {
        startDialog(null, sourceId, ksId, sttId, ttsId, voiceId, hliIds, sinkId, keyword, listeningItem);
    }

    /**
     * Start dialog processing for a given audio source.
     *
     * <p>Omitted optional arguments are passed as {@code null}, leaving their defaults to the API.
     *
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public void startDialog() {
        startDialog(null, null, null, null, null, null, null, null, null, null);
    }

    /**
     * Stop dialog processing for a given audio source.
     *
     * @param sourceId source ID (optional)
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public void stopDialog(String sourceId) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("sourceId", sourceId);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Voice.stopDialogWithHttpInfo({})", params);
            }
            var response = api.stopDialogWithHttpInfo(sourceId);
            if (debugEnabled) {
                log.debug("RES: Voice.stopDialogWithHttpInfo({}): status_code: {}", params, response.getStatusCode());
            }
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("sourceId", sourceId);
            }
            throw new EndpointException(this.getClass(), "stopDialogWithHttpInfo", params, e);
        }
    }

    /**
     * Stop dialog processing for a given audio source.
     *
     * <p>Omitted optional arguments are passed as {@code null}, leaving their defaults to the API.
     *
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public void stopDialog() {
        stopDialog(null);
    }

    /**
     * Speaks a given text with a given voice through the given audio sink.
     *
     * @param body text to speak (required)
     * @param voiceid voice id (optional)
     * @param sinkid audio sink id (optional)
     * @param volume volume level (optional)
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public void textToSpeech(String body, String voiceid, String sinkid, String volume) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("body", body);
            params.put("voiceid", voiceid);
            params.put("sinkid", sinkid);
            params.put("volume", volume);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Voice.textToSpeechWithHttpInfo({})", params);
            }
            var response = api.textToSpeechWithHttpInfo(body, voiceid, sinkid, volume);
            if (debugEnabled) {
                log.debug("RES: Voice.textToSpeechWithHttpInfo({}): status_code: {}", params, response.getStatusCode());
            }
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("body", body);
                params.put("voiceid", voiceid);
                params.put("sinkid", sinkid);
                params.put("volume", volume);
            }
            throw new EndpointException(this.getClass(), "textToSpeechWithHttpInfo", params, e);
        }
    }

    /**
     * Speaks a given text with a given voice through the given audio sink.
     *
     * <p>Omitted optional arguments are passed as {@code null}, leaving their defaults to the API.
     *
     * @param body text to speak (required)
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public void textToSpeech(String body) {
        textToSpeech(body, null, null, null);
    }
}
