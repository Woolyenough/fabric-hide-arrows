package wtf.wooly.hide_arrows;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import wtf.wooly.hide_arrows.config.HideArrowsConfig;

public class HideArrows implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("hide-arrows");
    @Override
    public void onInitialize() {
        // Loads (and writes defaults for) config/hide-arrows.json so the server-side
        // LivingEntityMixin can clamp synced arrow/stinger counts. On a dedicated server
        // this is the only config entry point; on the client HideArrowsClient also calls it.
        HideArrowsConfig.get();
        LOGGER.info("Hide Arrows has been initialised.");
    }
}