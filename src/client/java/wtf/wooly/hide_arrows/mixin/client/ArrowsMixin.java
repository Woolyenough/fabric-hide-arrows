package wtf.wooly.hide_arrows.mixin.client;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.ArrowEntityRenderer;
import net.minecraft.client.render.entity.feature.StuckObjectsFeatureRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.lang.reflect.Field;

@Mixin(StuckObjectsFeatureRenderer.class)
public class ArrowsMixin {

	@Unique
	private static final Logger LOGGER = LogManager.getLogger();

	@Inject(method = "renderObject", at = @At("HEAD"), cancellable = true)
	private void cancelRendering(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, float f, float directionX, float directionY, CallbackInfo ci) {
		try {
			Field textureField = StuckObjectsFeatureRenderer.class.getDeclaredField("texture");
			textureField.setAccessible(true);
			Identifier texture = (Identifier) textureField.get(this);

			if(texture == ArrowEntityRenderer.TEXTURE){
				ci.cancel();
			}
		} catch (NoSuchFieldException | IllegalAccessException e) {
			LOGGER.error("Failed to access 'texture' field in StuckObjectsFeatureRenderer", e);
		}
	}
}