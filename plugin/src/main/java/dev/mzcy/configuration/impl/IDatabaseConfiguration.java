package dev.mzcy.configuration.impl;

import dev.mzcy.api.configuration.DatabaseConfiguration;
import dev.mzcy.configuration.Configuration;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class IDatabaseConfiguration extends Configuration implements DatabaseConfiguration {

    String mongoConnectionUri = "mongodb://localhost:27017";
    String mongoDatabase = "lootboxes";
    String mongoCollectionPrefix = "lb_";

}
