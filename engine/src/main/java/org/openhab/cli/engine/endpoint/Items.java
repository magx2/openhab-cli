package org.openhab.cli.engine.endpoint;

import java.util.LinkedHashMap;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openhab.cli.client.ApiException;
import org.openhab.cli.client.api.ItemsApi;
import org.openhab.cli.client.model.EnrichedItem;
import org.openhab.cli.client.model.GetItemByName200Response;
import org.openhab.cli.client.model.GroupItem;
import org.openhab.cli.client.model.ItemSemanticsProblem;
import org.openhab.cli.client.model.Metadata;
import org.openhab.cli.engine.rest.ApiClient;

/**
 * Synchronous access to the openHAB operations provided by {@link ItemsApi}.
 */
@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public final class Items implements Endpoint {
    private final ItemsApi api;

    /**
     * Creates an endpoint using the supplied REST client.
     *
     * @param apiClient the client providing connection and authentication settings
     */
    public Items(ApiClient apiClient) {
        this(new ItemsApi(apiClient.toNative()));
    }

    /**
     * Adds a new member to a group item.
     *
     * @param itemName item name (required)
     * @param memberItemName member item name (required)
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public void addMemberToGroupItem(String itemName, String memberItemName) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("itemName", itemName);
            params.put("memberItemName", memberItemName);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Items.addMemberToGroupItemWithHttpInfo({})", params);
            }
            var response = api.addMemberToGroupItemWithHttpInfo(itemName, memberItemName);
            if (debugEnabled) {
                log.debug(
                        "RES: Items.addMemberToGroupItemWithHttpInfo({}): status_code: {}",
                        params,
                        response.getStatusCode());
            }
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("itemName", itemName);
                params.put("memberItemName", memberItemName);
            }
            throw new EndpointException(this.getClass(), "addMemberToGroupItemWithHttpInfo", params, e);
        }
    }

    /**
     * Adds metadata to an item.
     *
     * @param itemName item name (required)
     * @param namespace namespace (required)
     * @param metadata metadata (required)
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public void addMetadataToItem(String itemName, String namespace, Metadata metadata) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("itemName", itemName);
            params.put("namespace", namespace);
            params.put("metadata", metadata);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Items.addMetadataToItemWithHttpInfo({})", params);
            }
            var response = api.addMetadataToItemWithHttpInfo(itemName, namespace, metadata);
            if (debugEnabled) {
                log.debug(
                        "RES: Items.addMetadataToItemWithHttpInfo({}): status_code: {}",
                        params,
                        response.getStatusCode());
            }
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("itemName", itemName);
                params.put("namespace", namespace);
                params.put("metadata", metadata);
            }
            throw new EndpointException(this.getClass(), "addMetadataToItemWithHttpInfo", params, e);
        }
    }

    /**
     * Adds a new item to the registry or updates the existing item.
     *
     * @param itemName item name (required)
     * @param groupItem item data (required)
     * @param acceptLanguage language (optional)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public EnrichedItem addOrUpdateItemInRegistry(String itemName, GroupItem groupItem, String acceptLanguage) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("itemName", itemName);
            params.put("groupItem", groupItem);
            params.put("acceptLanguage", acceptLanguage);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Items.addOrUpdateItemInRegistryWithHttpInfo({})", params);
            }
            var response = api.addOrUpdateItemInRegistryWithHttpInfo(itemName, groupItem, acceptLanguage);
            var data = response.getData();
            if (debugEnabled) {
                log.debug(
                        "RES: Items.addOrUpdateItemInRegistryWithHttpInfo({}): status_code: {}, data: {}",
                        params,
                        response.getStatusCode(),
                        data);
            }
            return data;
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("itemName", itemName);
                params.put("groupItem", groupItem);
                params.put("acceptLanguage", acceptLanguage);
            }
            throw new EndpointException(this.getClass(), "addOrUpdateItemInRegistryWithHttpInfo", params, e);
        }
    }

    /**
     * Adds a new item to the registry or updates the existing item.
     *
     * <p>Omitted optional arguments are passed as {@code null}, leaving their defaults to the API.
     *
     * @param itemName item name (required)
     * @param groupItem item data (required)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public EnrichedItem addOrUpdateItemInRegistry(String itemName, GroupItem groupItem) {
        return addOrUpdateItemInRegistry(itemName, groupItem, null);
    }

    /**
     * Adds a list of items to the registry or updates the existing items.
     *
     * @param groupItem array of item data (required)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public String addOrUpdateItemsInRegistry(List<GroupItem> groupItem) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("groupItem", groupItem);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Items.addOrUpdateItemsInRegistryWithHttpInfo({})", params);
            }
            var response = api.addOrUpdateItemsInRegistryWithHttpInfo(groupItem);
            var data = response.getData();
            if (debugEnabled) {
                log.debug(
                        "RES: Items.addOrUpdateItemsInRegistryWithHttpInfo({}): status_code: {}, data: {}",
                        params,
                        response.getStatusCode(),
                        data);
            }
            return data;
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("groupItem", groupItem);
            }
            throw new EndpointException(this.getClass(), "addOrUpdateItemsInRegistryWithHttpInfo", params, e);
        }
    }

    /**
     * Adds a tag to an item.
     *
     * @param itemName item name (required)
     * @param tag tag (required)
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public void addTagToItem(String itemName, String tag) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("itemName", itemName);
            params.put("tag", tag);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Items.addTagToItemWithHttpInfo({})", params);
            }
            var response = api.addTagToItemWithHttpInfo(itemName, tag);
            if (debugEnabled) {
                log.debug("RES: Items.addTagToItemWithHttpInfo({}): status_code: {}", params, response.getStatusCode());
            }
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("itemName", itemName);
                params.put("tag", tag);
            }
            throw new EndpointException(this.getClass(), "addTagToItemWithHttpInfo", params, e);
        }
    }

    /**
     * Gets a single item.
     *
     * @param itemName item name (required)
     * @param acceptLanguage language (optional)
     * @param metadata metadata selector - a comma separated list or a regular expression (returns all if no value given) (optional, default to .*)
     * @param recursive get member items if the item is a group item (optional, default to true)
     * @param parents get parent group items recursively (optional, default to false)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public GetItemByName200Response itemByName(
            String itemName, String acceptLanguage, String metadata, Boolean recursive, Boolean parents) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("itemName", itemName);
            params.put("acceptLanguage", acceptLanguage);
            params.put("metadata", metadata);
            params.put("recursive", recursive);
            params.put("parents", parents);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Items.getItemByNameWithHttpInfo({})", params);
            }
            var response = api.getItemByNameWithHttpInfo(itemName, acceptLanguage, metadata, recursive, parents);
            var data = response.getData();
            if (debugEnabled) {
                log.debug(
                        "RES: Items.getItemByNameWithHttpInfo({}): status_code: {}, data: {}",
                        params,
                        response.getStatusCode(),
                        data);
            }
            return data;
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("itemName", itemName);
                params.put("acceptLanguage", acceptLanguage);
                params.put("metadata", metadata);
                params.put("recursive", recursive);
                params.put("parents", parents);
            }
            throw new EndpointException(this.getClass(), "getItemByNameWithHttpInfo", params, e);
        }
    }

    /**
     * Gets a single item.
     *
     * <p>Omitted optional arguments are passed as {@code null}, leaving their defaults to the API.
     *
     * @param itemName item name (required)
     * @param metadata metadata selector - a comma separated list or a regular expression (returns all if no value given) (optional, default to .*)
     * @param recursive get member items if the item is a group item (optional, default to true)
     * @param parents get parent group items recursively (optional, default to false)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public GetItemByName200Response itemByName(String itemName, String metadata, Boolean recursive, Boolean parents) {
        return itemByName(itemName, null, metadata, recursive, parents);
    }

    /**
     * Gets a single item.
     *
     * <p>Omitted optional arguments are passed as {@code null}, leaving their defaults to the API.
     *
     * @param itemName item name (required)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public GetItemByName200Response itemByName(String itemName) {
        return itemByName(itemName, null, null, null, null);
    }

    /**
     * Gets the namespace of an item.
     *
     * @param itemName item name (required)
     * @param acceptLanguage language (optional)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public String itemNamespaces(String itemName, String acceptLanguage) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("itemName", itemName);
            params.put("acceptLanguage", acceptLanguage);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Items.getItemNamespacesWithHttpInfo({})", params);
            }
            var response = api.getItemNamespacesWithHttpInfo(itemName, acceptLanguage);
            var data = response.getData();
            if (debugEnabled) {
                log.debug(
                        "RES: Items.getItemNamespacesWithHttpInfo({}): status_code: {}, data: {}",
                        params,
                        response.getStatusCode(),
                        data);
            }
            return data;
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("itemName", itemName);
                params.put("acceptLanguage", acceptLanguage);
            }
            throw new EndpointException(this.getClass(), "getItemNamespacesWithHttpInfo", params, e);
        }
    }

    /**
     * Gets the namespace of an item.
     *
     * <p>Omitted optional arguments are passed as {@code null}, leaving their defaults to the API.
     *
     * @param itemName item name (required)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public String itemNamespaces(String itemName) {
        return itemNamespaces(itemName, null);
    }

    /**
     * Gets the state of an item.
     *
     * @param itemName item name (required)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public String itemState1(String itemName) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("itemName", itemName);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Items.getItemState1WithHttpInfo({})", params);
            }
            var response = api.getItemState1WithHttpInfo(itemName);
            var data = response.getData();
            if (debugEnabled) {
                log.debug(
                        "RES: Items.getItemState1WithHttpInfo({}): status_code: {}, data: {}",
                        params,
                        response.getStatusCode(),
                        data);
            }
            return data;
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("itemName", itemName);
            }
            throw new EndpointException(this.getClass(), "getItemState1WithHttpInfo", params, e);
        }
    }

    /**
     * Get all available items.
     *
     * @param acceptLanguage language (optional)
     * @param type item type filter (optional)
     * @param tags item tag filter (optional)
     * @param metadata metadata selector - a comma separated list or a regular expression (returns all if no value given) (optional, default to .*)
     * @param recursive get member items recursively (optional, default to false)
     * @param parents get parent group items recursively (optional, default to false)
     * @param fields limit output to the given fields (comma separated) (optional)
     * @param staticDataOnly provides a cacheable list of values not expected to change regularly and checks the If-Modified-Since header, all other parameters are ignored except \&quot;metadata\&quot; (optional, default to false)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public List<String> items(
            String acceptLanguage,
            String type,
            String tags,
            String metadata,
            Boolean recursive,
            Boolean parents,
            String fields,
            Boolean staticDataOnly) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("acceptLanguage", acceptLanguage);
            params.put("type", type);
            params.put("tags", tags);
            params.put("metadata", metadata);
            params.put("recursive", recursive);
            params.put("parents", parents);
            params.put("fields", fields);
            params.put("staticDataOnly", staticDataOnly);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Items.getItemsWithHttpInfo({})", params);
            }
            var response = api.getItemsWithHttpInfo(
                    acceptLanguage, type, tags, metadata, recursive, parents, fields, staticDataOnly);
            var data = response.getData();
            if (debugEnabled) {
                log.debug(
                        "RES: Items.getItemsWithHttpInfo({}): status_code: {}, data: {}",
                        params,
                        response.getStatusCode(),
                        data);
            }
            return data;
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("acceptLanguage", acceptLanguage);
                params.put("type", type);
                params.put("tags", tags);
                params.put("metadata", metadata);
                params.put("recursive", recursive);
                params.put("parents", parents);
                params.put("fields", fields);
                params.put("staticDataOnly", staticDataOnly);
            }
            throw new EndpointException(this.getClass(), "getItemsWithHttpInfo", params, e);
        }
    }

    /**
     * Get all available items.
     *
     * <p>Omitted optional arguments are passed as {@code null}, leaving their defaults to the API.
     *
     * @param type item type filter (optional)
     * @param tags item tag filter (optional)
     * @param metadata metadata selector - a comma separated list or a regular expression (returns all if no value given) (optional, default to .*)
     * @param recursive get member items recursively (optional, default to false)
     * @param parents get parent group items recursively (optional, default to false)
     * @param fields limit output to the given fields (comma separated) (optional)
     * @param staticDataOnly provides a cacheable list of values not expected to change regularly and checks the If-Modified-Since header, all other parameters are ignored except \&quot;metadata\&quot; (optional, default to false)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public List<String> items(
            String type,
            String tags,
            String metadata,
            Boolean recursive,
            Boolean parents,
            String fields,
            Boolean staticDataOnly) {
        return items(null, type, tags, metadata, recursive, parents, fields, staticDataOnly);
    }

    /**
     * Get all available items.
     *
     * <p>Omitted optional arguments are passed as {@code null}, leaving their defaults to the API.
     *
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public List<String> items() {
        return items(null, null, null, null, null, null, null, null);
    }

    /**
     * Gets the item which defines the requested semantics of an item.
     *
     * @param itemName item name (required)
     * @param semanticClass semantic class (required)
     * @param acceptLanguage language (optional)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public EnrichedItem semanticItem(String itemName, String semanticClass, String acceptLanguage) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("itemName", itemName);
            params.put("semanticClass", semanticClass);
            params.put("acceptLanguage", acceptLanguage);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Items.getSemanticItemWithHttpInfo({})", params);
            }
            var response = api.getSemanticItemWithHttpInfo(itemName, semanticClass, acceptLanguage);
            var data = response.getData();
            if (debugEnabled) {
                log.debug(
                        "RES: Items.getSemanticItemWithHttpInfo({}): status_code: {}, data: {}",
                        params,
                        response.getStatusCode(),
                        data);
            }
            return data;
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("itemName", itemName);
                params.put("semanticClass", semanticClass);
                params.put("acceptLanguage", acceptLanguage);
            }
            throw new EndpointException(this.getClass(), "getSemanticItemWithHttpInfo", params, e);
        }
    }

    /**
     * Gets the item which defines the requested semantics of an item.
     *
     * <p>Omitted optional arguments are passed as {@code null}, leaving their defaults to the API.
     *
     * @param itemName item name (required)
     * @param semanticClass semantic class (required)
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public EnrichedItem semanticItem(String itemName, String semanticClass) {
        return semanticItem(itemName, semanticClass, null);
    }

    /**
     * Gets configuration problems with item semantics.
     *
     * @return the response data, or {@code null} when the response has no body
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public List<ItemSemanticsProblem> semanticsHealth() {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Items.getSemanticsHealthWithHttpInfo({})", params);
            }
            var response = api.getSemanticsHealthWithHttpInfo();
            var data = response.getData();
            if (debugEnabled) {
                log.debug(
                        "RES: Items.getSemanticsHealthWithHttpInfo({}): status_code: {}, data: {}",
                        params,
                        response.getStatusCode(),
                        data);
            }
            return data;
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
            }
            throw new EndpointException(this.getClass(), "getSemanticsHealthWithHttpInfo", params, e);
        }
    }

    /**
     * Remove unused/orphaned metadata.
     *
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public void purgeDatabase() {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Items.purgeDatabaseWithHttpInfo({})", params);
            }
            var response = api.purgeDatabaseWithHttpInfo();
            if (debugEnabled) {
                log.debug(
                        "RES: Items.purgeDatabaseWithHttpInfo({}): status_code: {}", params, response.getStatusCode());
            }
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
            }
            throw new EndpointException(this.getClass(), "purgeDatabaseWithHttpInfo", params, e);
        }
    }

    /**
     * Removes all managed metadata from an item.
     *
     * @param itemName item name (required)
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public void removeAllMetadataFromItem(String itemName) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("itemName", itemName);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Items.removeAllMetadataFromItemWithHttpInfo({})", params);
            }
            var response = api.removeAllMetadataFromItemWithHttpInfo(itemName);
            if (debugEnabled) {
                log.debug(
                        "RES: Items.removeAllMetadataFromItemWithHttpInfo({}): status_code: {}",
                        params,
                        response.getStatusCode());
            }
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("itemName", itemName);
            }
            throw new EndpointException(this.getClass(), "removeAllMetadataFromItemWithHttpInfo", params, e);
        }
    }

    /**
     * Removes an item from the registry.
     *
     * @param itemName item name (required)
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public void removeItemFromRegistry(String itemName) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("itemName", itemName);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Items.removeItemFromRegistryWithHttpInfo({})", params);
            }
            var response = api.removeItemFromRegistryWithHttpInfo(itemName);
            if (debugEnabled) {
                log.debug(
                        "RES: Items.removeItemFromRegistryWithHttpInfo({}): status_code: {}",
                        params,
                        response.getStatusCode());
            }
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("itemName", itemName);
            }
            throw new EndpointException(this.getClass(), "removeItemFromRegistryWithHttpInfo", params, e);
        }
    }

    /**
     * Removes an existing member from a group item.
     *
     * @param itemName item name (required)
     * @param memberItemName member item name (required)
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public void removeMemberFromGroupItem(String itemName, String memberItemName) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("itemName", itemName);
            params.put("memberItemName", memberItemName);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Items.removeMemberFromGroupItemWithHttpInfo({})", params);
            }
            var response = api.removeMemberFromGroupItemWithHttpInfo(itemName, memberItemName);
            if (debugEnabled) {
                log.debug(
                        "RES: Items.removeMemberFromGroupItemWithHttpInfo({}): status_code: {}",
                        params,
                        response.getStatusCode());
            }
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("itemName", itemName);
                params.put("memberItemName", memberItemName);
            }
            throw new EndpointException(this.getClass(), "removeMemberFromGroupItemWithHttpInfo", params, e);
        }
    }

    /**
     * Removes metadata in a specific namespace from an item.
     *
     * @param itemName item name (required)
     * @param namespace namespace (required)
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public void removeMetadataFromItem(String itemName, String namespace) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("itemName", itemName);
            params.put("namespace", namespace);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Items.removeMetadataFromItemWithHttpInfo({})", params);
            }
            var response = api.removeMetadataFromItemWithHttpInfo(itemName, namespace);
            if (debugEnabled) {
                log.debug(
                        "RES: Items.removeMetadataFromItemWithHttpInfo({}): status_code: {}",
                        params,
                        response.getStatusCode());
            }
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("itemName", itemName);
                params.put("namespace", namespace);
            }
            throw new EndpointException(this.getClass(), "removeMetadataFromItemWithHttpInfo", params, e);
        }
    }

    /**
     * Removes a tag from an item.
     *
     * @param itemName item name (required)
     * @param tag tag (required)
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public void removeTagFromItem(String itemName, String tag) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("itemName", itemName);
            params.put("tag", tag);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Items.removeTagFromItemWithHttpInfo({})", params);
            }
            var response = api.removeTagFromItemWithHttpInfo(itemName, tag);
            if (debugEnabled) {
                log.debug(
                        "RES: Items.removeTagFromItemWithHttpInfo({}): status_code: {}",
                        params,
                        response.getStatusCode());
            }
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("itemName", itemName);
                params.put("tag", tag);
            }
            throw new EndpointException(this.getClass(), "removeTagFromItemWithHttpInfo", params, e);
        }
    }

    /**
     * Sends a command to an item.
     *
     * @param itemName item name (required)
     * @param body Valid item command (e.g., ON, OFF) either as plain text or JSON (required)
     * @param xOpenHABSource the source of the command; takes priority over the query parameter or JSON body if multiple are set (optional)
     * @param source the source of the command (optional)
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public void sendItemCommand(String itemName, String body, String xOpenHABSource, String source) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("itemName", itemName);
            params.put("body", body);
            params.put("xOpenHABSource", xOpenHABSource);
            params.put("source", source);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Items.sendItemCommandWithHttpInfo({})", params);
            }
            var response = api.sendItemCommandWithHttpInfo(itemName, body, xOpenHABSource, source);
            if (debugEnabled) {
                log.debug(
                        "RES: Items.sendItemCommandWithHttpInfo({}): status_code: {}",
                        params,
                        response.getStatusCode());
            }
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("itemName", itemName);
                params.put("body", body);
                params.put("xOpenHABSource", xOpenHABSource);
                params.put("source", source);
            }
            throw new EndpointException(this.getClass(), "sendItemCommandWithHttpInfo", params, e);
        }
    }

    /**
     * Sends a command to an item.
     *
     * <p>Omitted optional arguments are passed as {@code null}, leaving their defaults to the API.
     *
     * @param itemName item name (required)
     * @param body Valid item command (e.g., ON, OFF) either as plain text or JSON (required)
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public void sendItemCommand(String itemName, String body) {
        sendItemCommand(itemName, body, null, null);
    }

    /**
     * Updates the state of an item.
     *
     * @param itemName item name (required)
     * @param body Valid item state (e.g., ON, OFF) either as plain text or JSON (required)
     * @param acceptLanguage language (optional)
     * @param xOpenHABSource the source of the event; takes priority over the query parameter or JSON body if multiple are set (optional)
     * @param source the source of the event (optional)
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public void updateItemState(
            String itemName, String body, String acceptLanguage, String xOpenHABSource, String source) {
        var debugEnabled = log.isDebugEnabled();
        LinkedHashMap<String, Object> params = null;
        if (debugEnabled) {
            params = new LinkedHashMap<>();
            params.put("itemName", itemName);
            params.put("body", body);
            params.put("acceptLanguage", acceptLanguage);
            params.put("xOpenHABSource", xOpenHABSource);
            params.put("source", source);
        }
        try {
            if (debugEnabled) {
                log.debug("REQ: Items.updateItemStateWithHttpInfo({})", params);
            }
            var response = api.updateItemStateWithHttpInfo(itemName, body, acceptLanguage, xOpenHABSource, source);
            if (debugEnabled) {
                log.debug(
                        "RES: Items.updateItemStateWithHttpInfo({}): status_code: {}",
                        params,
                        response.getStatusCode());
            }
        } catch (ApiException e) {
            if (params == null) {
                params = new LinkedHashMap<>();
                params.put("itemName", itemName);
                params.put("body", body);
                params.put("acceptLanguage", acceptLanguage);
                params.put("xOpenHABSource", xOpenHABSource);
                params.put("source", source);
            }
            throw new EndpointException(this.getClass(), "updateItemStateWithHttpInfo", params, e);
        }
    }

    /**
     * Updates the state of an item.
     *
     * <p>Omitted optional arguments are passed as {@code null}, leaving their defaults to the API.
     *
     * @param itemName item name (required)
     * @param body Valid item state (e.g., ON, OFF) either as plain text or JSON (required)
     * @param xOpenHABSource the source of the event; takes priority over the query parameter or JSON body if multiple are set (optional)
     * @param source the source of the event (optional)
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public void updateItemState(String itemName, String body, String xOpenHABSource, String source) {
        updateItemState(itemName, body, null, xOpenHABSource, source);
    }

    /**
     * Updates the state of an item.
     *
     * <p>Omitted optional arguments are passed as {@code null}, leaving their defaults to the API.
     *
     * @param itemName item name (required)
     * @param body Valid item state (e.g., ON, OFF) either as plain text or JSON (required)
     * @throws EndpointException if the request fails, including parameter validation or response decoding
     */
    public void updateItemState(String itemName, String body) {
        updateItemState(itemName, body, null, null, null);
    }
}
