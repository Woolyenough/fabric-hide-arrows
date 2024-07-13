package wtf.wooly.hide_arrows.mixin;


import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    @Inject(method = "setStuckArrowCount", at = @At("HEAD"), cancellable = true)
    private void setStuckArrowCountMixin(int stuckArrowCount, CallbackInfo ci) {
        ci.cancel();
    }
}