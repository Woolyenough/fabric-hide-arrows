package wtf.wooly.hide_arrows;

import net.fabricmc.api.ClientModInitializer;

import wtf.wooly.hide_arrows.config.HideArrowsConfig;

public class HideArrowsClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		HideArrowsConfig.get();
	}
}