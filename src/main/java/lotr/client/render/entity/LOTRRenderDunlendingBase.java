package lotr.client.render.entity;

import org.lwjgl.opengl.GL11;

import lotr.client.model.LOTRModelHuman;
import lotr.common.LOTRMod;
import lotr.common.entity.item.LOTREntityOrcBomb;
import lotr.common.entity.npc.*;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderDunlendingBase extends LOTRRenderBiped {
	public static LOTRRandomSkins dunlendingSkinsMale;
	public static LOTRRandomSkins dunlendingSkinsFemale;
	public static LOTRRandomSkins dunlendingSkinsBerserker;

	public LOTRRenderDunlendingBase() {
		super(new LOTRModelHuman(), 0.5f);
		dunlendingSkinsMale = LOTRRandomSkins.loadSkinsList("lotr:mob/dunland/dunlending_male");
		dunlendingSkinsFemale = LOTRRandomSkins.loadSkinsList("lotr:mob/dunland/dunlending_female");
		dunlendingSkinsBerserker = LOTRRandomSkins.loadSkinsList("lotr:mob/dunland/berserker");
	}

	@Override
	public void doRender(EntityLiving entity, double d, double d1, double d2, float f, float f1) {
		LOTREntityDunlending dunlending = (LOTREntityDunlending) entity;
		ItemStack helmet = dunlending.getEquipmentInSlot(4);
		if (helmet != null && helmet.getItem() == Item.getItemFromBlock(LOTRMod.orcBomb)) {
			GL11.glEnable(32826);
			GL11.glPushMatrix();
			GL11.glTranslatef((float) d, (float) (d1 += 0.5) + 2.5f, (float) d2);
			GL11.glRotatef(-f, 0.0f, 1.0f, 0.0f);
			int i = entity.getBrightnessForRender(f1);
			int j = i % 65536;
			int k = i / 65536;
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, j, k);
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			LOTRRenderOrcBomb bombRenderer = (LOTRRenderOrcBomb) RenderManager.instance.getEntityClassRenderObject(LOTREntityOrcBomb.class);
			bombRenderer.renderBomb(entity, 0.0, 0.0, 0.0, f1, 5, 0, 0.75f, 1.0f);
			GL11.glPopMatrix();
			GL11.glDisable(32826);
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		}
		super.doRender(entity, d, d1, d2, f, f1);
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		LOTREntityDunlending dunlending = (LOTREntityDunlending) entity;
		if (dunlending.familyInfo.isMale()) {
			if (dunlending instanceof LOTREntityDunlendingBerserker) {
				return dunlendingSkinsBerserker.getRandomSkin(dunlending);
			}
			return dunlendingSkinsMale.getRandomSkin(dunlending);
		}
		return dunlendingSkinsFemale.getRandomSkin(dunlending);
	}
}
