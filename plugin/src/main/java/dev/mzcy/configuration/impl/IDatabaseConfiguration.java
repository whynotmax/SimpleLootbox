package dev.mzcy.configuration.impl;

import dev.mzcy.api.configuration.DatabaseConfiguration;
import dev.mzcy.configuration.Configuration;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class IDatabaseConfiguration extends Configuration implements DatabaseConfiguration {

    final String mongoConnectionUri = "mongodb://localhost:27017";
    final String mongoDatabase = "lootboxes";
    final String mongoCollectionPrefix = "lb_";

}
