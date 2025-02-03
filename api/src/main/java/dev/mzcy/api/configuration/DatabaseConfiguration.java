package dev.mzcy.api.configuration;

public interface DatabaseConfiguration {

    String getMongoConnectionUri();

    String getMongoDatabase();

    String getMongoCollectionPrefix();

}
