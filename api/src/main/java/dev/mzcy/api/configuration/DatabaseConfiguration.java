package dev.mzcy.api.configuration;

import dev.mzcy.api.database.DatabaseManager;
import org.jetbrains.annotations.ApiStatus;

/**
 * Interface representing the configuration for the database.
 * Provides methods to get the MongoDB connection URI, database name, and collection prefix.
 * You should never use this interface directly, but rather use the {@link DatabaseManager} to access the database.
 * This interface is used internally only.
 */
@ApiStatus.Internal
public interface DatabaseConfiguration {

    /**
     * Gets the MongoDB connection URI.
     *
     * @return the MongoDB connection URI
     */
    String getMongoConnectionUri();

    /**
     * Gets the name of the MongoDB database.
     *
     * @return the name of the MongoDB database
     */
    String getMongoDatabase();

    /**
     * Gets the prefix for MongoDB collections.
     *
     * @return the prefix for MongoDB collections
     */
    String getMongoCollectionPrefix();

}