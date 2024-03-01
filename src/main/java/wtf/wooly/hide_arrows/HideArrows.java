package wtf.wooly.hide_arrows;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HideArrows implements ModInitializer {

    public static final Logger LOGGER = LoggerFactory.getLogger("hide-arrows");

    @Override
    public void onInitialize() {
        LOGGER.info("Hide Arrows has been initialised.");
    }
}