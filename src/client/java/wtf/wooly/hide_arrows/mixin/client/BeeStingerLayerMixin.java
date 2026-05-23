package wtf.wooly.hide_arrows.mixin.client;

import net.minecraft.client.renderer.entity.layers.BeeStingerLayer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import wtf.wooly.hide_arrows.config.HideArrowsConfig;

@Mixin(BeeStingerLayer.class)
public class BeeStingerLayerMixin {
    @Inject(method = "numStuck", at = @At("HEAD"), cancellable = true)
    private void hidearrows$hideStingers(AvatarRenderState state, CallbackInfoReturnable<Integer> cir) {
        // 0 = hide all; the newest-n cap (max > 0) is applied later in StuckInBodyLayerMixin.
        if (HideArrowsConfig.get().maxStingers == 0) {
            cir.setReturnValue(0);
        }
    }
}
