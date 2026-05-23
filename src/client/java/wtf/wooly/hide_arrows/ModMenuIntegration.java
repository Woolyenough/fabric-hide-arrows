package wtf.wooly.hide_arrows;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import wtf.wooly.hide_arrows.gui.HideArrowsConfigScreen;

public class ModMenuIntegration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return HideArrowsConfigScreen::new;
    }
}
