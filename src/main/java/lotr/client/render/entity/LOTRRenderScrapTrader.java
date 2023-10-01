package lotr.client.render.entity;

import lotr.client.LOTRTickHandlerClient;
import lotr.client.model.LOTRModelHuman;
import lotr.common.entity.LOTRRandomSkinEntity;
import lotr.common.entity.npc.LOTREntityScrapTrader;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class LOTRRenderScrapTrader extends LOTRRenderBiped {
	public static LOTRRandomSkins traderSkins;

	public LOTRRenderScrapTrader() {
		super(new LOTRModelHuman(), 0.5f);
		traderSkins = LOTRRandomSkins.loadSkinsList("lotr:mob/scrapTrader");
	}

	@Override
	public void doRender(EntityLiving entity, double d, double d1, double d2, float f, float f1) {
		if (Keyboard.isKeyDown(Minecraft.getMinecraft().gameSettings.keyBindScreenshot.getKeyCode())) {
			return;
		}
		if (LOTRTickHandlerClient.scrapTraderMisbehaveTick > 0) {
			int r = 3;
			for (int i = -r; i <= r; ++i) {
				for (int k = -r; k <= r; ++k) {
					if (Math.abs(i) + Math.abs(k) <= 2) {
						continue;
					}
					GL11.glPushMatrix();
					GL11.glScalef(1.0f, 3.0f, 1.0f);
					double g = 6.0;
					super.doRender(entity, i * g, 0.0, k * g, f, f1);
					GL11.glPopMatrix();
				}
			}
			GL11.glPushMatrix();
			float s = 6.0f;
			GL11.glScalef(1.0f, s, 1.0f);
			GL11.glColor3f(0.0f, 0.0f, 0.0f);
			super.doRender(entity, d, d1 / s, d2, f, f1);
			GL11.glPopMatrix();
			return;
		}
		super.doRender(entity, d, d1, d2, f, f1);
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		return traderSkins.getRandomSkin((LOTRRandomSkinEntity) entity);
	}

	@Override
	public void preRenderCallback(EntityLivingBase entity, float f) {
		super.preRenderCallback(entity, f);
		float fadeout = ((LOTREntityScrapTrader) entity).getFadeoutProgress(f);
		if (fadeout > 0.0f) {
			GL11.glEnable(3042);
			GL11.glBlendFunc(770, 771);
			GL11.glEnable(3008);
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f - fadeout);
		}
	}
}
