package dev.mzcy.configuration.impl;

import dev.mzcy.api.configuration.DatabaseConfiguration;
import dev.mzcy.configuration.Configuration;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class IDatabaseConfiguration extends Configuration implements DatabaseConfiguration {

    final String mongoConnectionUri;
    final String mongoDatabase;
    final String mongoCollectionPrefix;

    public IDatabaseConfiguration() {
        this.mongoConnectionUri = "mongodb://localhost:27017";
        this.mongoDatabase = "lootboxes";
        this.mongoCollectionPrefix = "lb_";
    }

}
