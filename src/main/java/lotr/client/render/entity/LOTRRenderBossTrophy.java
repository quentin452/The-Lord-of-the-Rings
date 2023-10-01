package lotr.client.render.entity;

import lotr.client.model.LOTRModelEnt;
import lotr.client.model.LOTRModelTroll;
import lotr.common.entity.item.LOTREntityBossTrophy;
import lotr.common.item.LOTRItemBossTrophy;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.util.EnumMap;
import java.util.Map;

public class LOTRRenderBossTrophy extends Render {
	public static Map<LOTRItemBossTrophy.TrophyType, ResourceLocation> trophyTextures = new EnumMap<>(LOTRItemBossTrophy.TrophyType.class);
	public static LOTRModelTroll trollModel = new LOTRModelTroll();
	public static LOTRModelEnt entModel = new LOTRModelEnt();

	@Override
	public void doRender(Entity entity, double d, double d1, double d2, float f, float f1) {
		LOTREntityBossTrophy trophy = (LOTREntityBossTrophy) entity;
		LOTRItemBossTrophy.TrophyType type = trophy.getTrophyType();
		float modelscale = 0.0625f;
		GL11.glPushMatrix();
		GL11.glDisable(2884);
		GL11.glTranslatef((float) d, (float) d1, (float) d2);
		GL11.glScalef(-1.0f, -1.0f, 1.0f);
		float rotation;
		rotation = trophy.isTrophyHanging() ? 180.0f + trophy.getTrophyFacing() * 90.0f : 180.0f - entity.rotationYaw;
		GL11.glRotatef(rotation, 0.0f, 1.0f, 0.0f);
		bindEntityTexture(entity);
		if (type == LOTRItemBossTrophy.TrophyType.MOUNTAIN_TROLL_CHIEFTAIN) {
			ModelRenderer head = trollModel.head;
			head.setRotationPoint(0.0f, -6.0f, 6.0f);
			GL11.glTranslatef(0.0f, -0.05f, 0.1f);
			GL11.glPushMatrix();
			GL11.glTranslatef(-0.25f, 0.0f, 0.0f);
			GL11.glRotatef(-10.0f, 0.0f, 0.0f, 1.0f);
			GL11.glRotatef(15.0f, 0.0f, 1.0f, 0.0f);
			head.render(modelscale);
			GL11.glPopMatrix();
			GL11.glPushMatrix();
			GL11.glTranslatef(0.25f, 0.0f, 0.0f);
			GL11.glRotatef(10.0f, 0.0f, 0.0f, 1.0f);
			GL11.glRotatef(-15.0f, 0.0f, 1.0f, 0.0f);
			head.render(modelscale);
			GL11.glPopMatrix();
		}
		if (type == LOTRItemBossTrophy.TrophyType.MALLORN_ENT) {
			ModelRenderer trunk = entModel.trunk;
			entModel.rightArm.showModel = false;
			entModel.leftArm.showModel = false;
			entModel.trophyBottomPanel.showModel = true;
			float scale = 0.6f;
			GL11.glTranslatef(0.0f, 34.0f * modelscale * scale, 0.0f);
			if (trophy.isTrophyHanging()) {
				GL11.glTranslatef(0.0f, 0.0f, 3.0f * modelscale / scale);
			}
			GL11.glScalef(scale, scale, scale);
			trunk.render(modelscale);
		}
		GL11.glEnable(2884);
		GL11.glPopMatrix();
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		LOTREntityBossTrophy trophy = (LOTREntityBossTrophy) entity;
		LOTRItemBossTrophy.TrophyType type = trophy.getTrophyType();
		ResourceLocation r = trophyTextures.get(type);
		if (r == null) {
			r = new ResourceLocation("lotr:item/bossTrophy/" + type.trophyName + ".png");
			trophyTextures.put(type, r);
		}
		return r;
	}
}
