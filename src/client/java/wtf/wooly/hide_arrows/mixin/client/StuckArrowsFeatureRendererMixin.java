package wtf.wooly.hide_arrows.mixin.client;

import net.minecraft.client.render.entity.feature.StuckArrowsFeatureRenderer;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(StuckArrowsFeatureRenderer.class)
public class StuckArrowsFeatureRendererMixin {
	@Inject(method = "getObjectCount", at = @At("HEAD"), cancellable = true)
	private void getObjectCount(PlayerEntityRenderState playerRenderState, CallbackInfoReturnable<Integer> cir) {
		cir.setReturnValue(0);
	}
}