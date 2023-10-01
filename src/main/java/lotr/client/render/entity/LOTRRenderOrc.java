package lotr.client.render.entity;

import lotr.client.LOTRTextures;
import lotr.client.model.LOTRModelOrc;
import lotr.common.entity.LOTRRandomSkinEntity;
import lotr.common.entity.npc.LOTREntityBlackUruk;
import lotr.common.entity.npc.LOTREntityOrc;
import lotr.common.entity.npc.LOTREntityUrukHai;
import lotr.common.entity.npc.LOTREntityUrukHaiBerserker;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class LOTRRenderOrc extends LOTRRenderBiped {
	public static LOTRRandomSkins orcSkins;
	public static LOTRRandomSkins urukSkins;
	public static LOTRRandomSkins blackUrukSkins;
	public LOTRGlowingEyes.Model eyesModel = new LOTRModelOrc(0.05f);

	public LOTRRenderOrc() {
		super(new LOTRModelOrc(), 0.5f);
		orcSkins = LOTRRandomSkins.loadSkinsList("lotr:mob/orc/orc");
		urukSkins = LOTRRandomSkins.loadSkinsList("lotr:mob/orc/urukHai");
		blackUrukSkins = LOTRRandomSkins.loadSkinsList("lotr:mob/orc/blackUruk");
	}

	@Override
	public void func_82421_b() {
		field_82423_g = new LOTRModelOrc(1.0f);
		field_82425_h = new LOTRModelOrc(0.5f);
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		LOTRRandomSkinEntity orc = (LOTRRandomSkinEntity) entity;
		if (orc instanceof LOTREntityUrukHai) {
			return urukSkins.getRandomSkin(orc);
		}
		if (orc instanceof LOTREntityBlackUruk) {
			return blackUrukSkins.getRandomSkin(orc);
		}
		return orcSkins.getRandomSkin(orc);
	}

	@Override
	public void preRenderCallback(EntityLivingBase entity, float f) {
		super.preRenderCallback(entity, f);
		LOTREntityOrc orc = (LOTREntityOrc) entity;
		if (orc.isWeakOrc) {
			GL11.glScalef(0.85f, 0.85f, 0.85f);
		} else if (orc instanceof LOTREntityUrukHaiBerserker) {
			float scale = LOTREntityUrukHaiBerserker.BERSERKER_SCALE;
			GL11.glScalef(scale, scale, scale);
		}
	}

	@Override
	public void renderModel(EntityLivingBase entity, float f, float f1, float f2, float f3, float f4, float f5) {
		super.renderModel(entity, f, f1, f2, f3, f4, f5);
		ResourceLocation eyes = LOTRTextures.getEyesTexture(getEntityTexture(entity), new int[][]{{9, 11}, {13, 11}}, 2, 1);
		LOTRGlowingEyes.renderGlowingEyes(entity, eyes, eyesModel, f, f1, f2, f3, f4, f5);
	}
}
