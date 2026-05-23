package wtf.wooly.hide_arrows.mixin;

import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import wtf.wooly.hide_arrows.config.HideArrowsConfig;

/**
 * Server-side counterpart to the client render mixins. The client mixins only change what the
 * local player draws; this clamps the stuck-object counts before they are stored in the entity's
 * synced data, so every connected client (including vanilla, mod-less ones) renders fewer objects.
 * Loads in every environment, so in singleplayer it also runs on the integrated server.
 */
@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    @ModifyVariable(method = "setArrowCount", at = @At("HEAD"), argsOnly = true)
    private int hidearrows$clampArrows(int count) {
        return HideArrowsConfig.clamp(count, HideArrowsConfig.get().maxArrows);
    }

    @ModifyVariable(method = "setStingerCount", at = @At("HEAD"), argsOnly = true)
    private int hidearrows$clampStingers(int count) {
        return HideArrowsConfig.clamp(count, HideArrowsConfig.get().maxStingers);
    }
}
