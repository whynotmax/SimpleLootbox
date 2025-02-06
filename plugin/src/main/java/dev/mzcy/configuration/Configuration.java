package dev.mzcy.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import java.io.File;
import java.io.IOException;

@Getter
@Accessors(fluent = true)
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public abstract class Configuration {

    private static final ObjectMapper YAML_MAPPER = new ObjectMapper(new YAMLFactory());

    public static <T extends Configuration> T load(String filePath, Class<T> clazz) {
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                File folder = file.getParentFile();
                if (!folder.exists()) folder.mkdirs();
                file.createNewFile();
                T instance = clazz.getDeclaredConstructor().newInstance();
                instance.save(filePath);
                return instance;
            } catch (Exception e) {
                throw new RuntimeException("Failed to create configuration file: " + filePath, e);
            }
        }
        try {
            return YAML_MAPPER.readValue(file, clazz);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load configuration from: " + filePath, e);
        }
    }

    public void save(String filePath) throws IOException {
        YAML_MAPPER.writeValue(new File(filePath), this);
    }
}
