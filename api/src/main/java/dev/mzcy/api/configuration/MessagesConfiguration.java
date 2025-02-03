package dev.mzcy.api.configuration;

public interface MessagesConfiguration {

    String getPrefix();

    String getNoPermission();

    String get(String key, Object... args);

    String get(String key);

}
