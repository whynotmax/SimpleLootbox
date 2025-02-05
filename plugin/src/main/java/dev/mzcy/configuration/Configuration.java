package dev.mzcy.configuration;

import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.representer.Representer;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

@Getter
@Accessors(fluent = true)
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public abstract class Configuration {

    static Yaml yaml;

    static {
        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        options.setPrettyFlow(true);

        LoaderOptions loaderOptions = new LoaderOptions();
        loaderOptions.setAllowDuplicateKeys(true);
        loaderOptions.setMaxAliasesForCollections(50); // Adjust for large YAML files
        loaderOptions.setTagInspector(tag -> true); // ✅ Allow all global tags

        Representer representer = new Representer(options);
        representer.getPropertyUtils().setSkipMissingProperties(true);

        yaml = new Yaml(new org.yaml.snakeyaml.constructor.Constructor(loaderOptions), representer, options);
    }

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
                throw new RuntimeException(e);
            }
        }
        try (FileReader reader = new FileReader(filePath)) {
            return yaml.loadAs(reader, clazz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void save(String filePath) throws IOException {
        try (FileWriter writer = new FileWriter(filePath)) {
            yaml.dump(this, writer);
        }
    }
}