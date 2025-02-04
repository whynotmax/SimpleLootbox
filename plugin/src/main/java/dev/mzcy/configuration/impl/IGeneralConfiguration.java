package dev.mzcy.configuration.impl;

import dev.mzcy.api.configuration.GeneralConfiguration;
import dev.mzcy.configuration.Configuration;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

@Getter
@Accessors(fluent = true)
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class IGeneralConfiguration extends Configuration implements GeneralConfiguration {

    final boolean enableLiveDrops = true;

}
