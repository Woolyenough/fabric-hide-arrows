package wtf.wooly.hide_arrows.mixin.client;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.layers.ArrowLayer;
import net.minecraft.client.renderer.entity.layers.StuckInBodyLayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import wtf.wooly.hide_arrows.config.HideArrowsConfig;

@Mixin(StuckInBodyLayer.class)
public class StuckInBodyLayerMixin {
    // The render loop walks every stuck object (index 0 = oldest .. count-1 = newest), advancing a
    // per-entity RNG so each object keeps a stable body position. We let the loop run in full so the
    // RNG state is preserved, but skip the actual draw for everything older than the newest `max`.
    @WrapWithCondition(
            method = "submit(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;ILnet/minecraft/client/renderer/entity/state/AvatarRenderState;FF)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/entity/layers/StuckInBodyLayer;submitStuckItem(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;IFFFI)V"
            )
    )
    private boolean hidearrows$capStuck(StuckInBodyLayer<?, ?> self, PoseStack pose, SubmitNodeCollector collector,
                                        int light, float x, float y, float z, int color,
                                        @Local(ordinal = 1) int count, @Local(ordinal = 2) int index) {
        HideArrowsConfig config = HideArrowsConfig.get();
        int max = (self instanceof ArrowLayer) ? config.maxArrows : config.maxStingers;
        if (max < 0) {
            return true; // unlimited: draw everything
        }
        if (max == 0) {
            return false; // hidden (also short-circuited in numStuck, but guard anyway)
        }
        return index >= count - max;
    }
}
