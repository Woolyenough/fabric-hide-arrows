package wtf.wooly.hide_arrows.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;
import wtf.wooly.hide_arrows.HideArrows;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;

public class HideArrowsConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path PATH = FabricLoader.getInstance().getConfigDir().resolve("hide-arrows.json");
    private static HideArrowsConfig instance;

    /** Sentinel for "show everything" - replicates not having the mod (for that type). */
    public static final int UNLIMITED = -1;

    // Max objects of each type rendered per entity. UNLIMITED (-1) = show all (vanilla behaviour),
    // 0 = hidden, n > 0 = keep the newest n. Defaults hide both, matching the mod's purpose.
    public int maxArrows = 0;
    public int maxStingers = 0;

    public static HideArrowsConfig get() {
        if (instance == null) {
            instance = load();
        }
        return instance;
    }

    private static HideArrowsConfig load() {
        if (Files.exists(PATH)) {
            try (Reader reader = Files.newBufferedReader(PATH)) {
                HideArrowsConfig loaded = GSON.fromJson(reader, HideArrowsConfig.class);
                if (loaded != null) {
                    return loaded;
                }
            } catch (Exception e) {
                HideArrows.LOGGER.warn("Failed to read config, falling back to defaults", e);
            }
        }
        HideArrowsConfig fresh = new HideArrowsConfig();
        fresh.save();
        return fresh;
    }

    public void save() {
        try {
            Files.createDirectories(PATH.getParent());
            try (Writer writer = Files.newBufferedWriter(PATH)) {
                GSON.toJson(this, writer);
            }
        } catch (IOException e) {
            HideArrows.LOGGER.warn("Failed to save config", e);
        }
    }

    /** Clamps a real count to a cap: UNLIMITED keeps everything, 0 hides, n caps to the newest n. */
    public static int clamp(int count, int max) {
        return max < 0 ? count : Math.min(count, max);
    }
}
