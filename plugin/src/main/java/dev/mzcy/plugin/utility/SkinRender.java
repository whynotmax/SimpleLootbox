package dev.mzcy.plugin.utility;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Base64;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class SkinRender {

    private static final Cache<UUID, BufferedImage> skinCache = CacheBuilder.newBuilder()
            .expireAfterWrite(1, TimeUnit.HOURS)
            .build();
    private static final Gson gson = new Gson();
    private UUID uniqueId;
    private boolean useHexColors = false;

    private SkinRender() {
    }

    public static Builder builder() {
        return new Builder();
    }

    private static Optional<BufferedImage> fetchSkinImage(UUID uniqueId) {
        BufferedImage cachedImage = skinCache.getIfPresent(uniqueId);
        if (cachedImage != null) {
            return Optional.of(cachedImage);
        }
        try {
            String profileUrl = "https://sessionserver.mojang.com/session/minecraft/profile/" + uniqueId;
            JsonObject profileJson = readJsonFromUrl(profileUrl);
            String textureBase64 = profileJson.getAsJsonArray("properties").get(0).getAsJsonObject().get("value").getAsString();
            String textureJson = new String(Base64.getDecoder().decode(textureBase64));
            JsonObject textureData = gson.fromJson(textureJson, JsonObject.class);
            String skinUrl = textureData.getAsJsonObject("textures").getAsJsonObject("SKIN").get("url").getAsString();

            BufferedImage originalImage = ImageIO.read(new URI(skinUrl).toURL());
            BufferedImage faceImage = originalImage.getSubimage(8, 8, 8, 8);
            skinCache.put(uniqueId, faceImage);
            return Optional.of(faceImage);

        } catch (Exception e) {
            throw new RuntimeException("Error fetching skin image", e);
        }
    }

    private static JsonObject readJsonFromUrl(String urlString) throws IOException, URISyntaxException {
        URL url = new URI(urlString).toURL();
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        try (InputStreamReader reader = new InputStreamReader(conn.getInputStream())) {
            return gson.fromJson(reader, JsonObject.class);
        }
    }

    public final Component[] render() {
        Optional<BufferedImage> skinImageOptional = fetchSkinImage(uniqueId);
        if (skinImageOptional.isEmpty()) {
            throw new IllegalStateException("Skin image not found");
        }

        BufferedImage image = skinImageOptional.get();
        int width = 8;
        int height = 8;
        Component[] result = new Component[height];
        MiniMessage miniMessage = MiniMessage.miniMessage();

        for (int y = 0; y < height; y++) {
            StringBuilder line = new StringBuilder();
            for (int x = 0; x < width; x++) {
                Color color = new Color(image.getRGB(x, y));
                String hexColor = String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
                line.append("<color:").append(hexColor).append(">█</color>");
            }
            result[y] = miniMessage.deserialize(line.toString());
        }

        return result;
    }

    public static class Builder {
        private final SkinRender skinRender = new SkinRender();

        public Builder() {
        }

        public final Builder fromUniqueId(UUID uniqueId) {
            this.skinRender.uniqueId = uniqueId;
            return this;
        }

        public final Builder useHexColors() {
            this.skinRender.useHexColors = true;
            return this;
        }

        public final SkinRender build() {
            return this.skinRender;
        }
    }
}