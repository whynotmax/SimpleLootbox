package dev.mzcy.api.configuration;

import java.util.List;

/**
 * Interface representing the configuration for messages.
 * Provides methods to get the prefix, no permission message, and other messages by key.
 */
public interface MessagesConfiguration {

    /**
     * Gets the prefix for messages.
     *
     * @return the prefix for messages
     */
    String getPrefix();

    /**
     * Gets the message for no permission.
     *
     * @return the no permission message
     */
    String getNoPermission();

    /**
     * Gets a message by its key with optional arguments.
     *
     * @param key  the key of the message
     * @param args the optional arguments for the message
     * @return the message corresponding to the key
     */
    String get(String key, Object... args);

    /**
     * Gets a message by its key.
     *
     * @param key the key of the message
     * @return the message corresponding to the key
     */
    String get(String key);

    /**
     * Gets the list of messages for lootbox all broadcasts.
     *
     * @return the list of messages for lootbox all broadcasts
     */
    List<String> getLootboxAllBroadcast();

}