package lotr.client.render.entity;

import lotr.common.entity.LOTRRandomSkinEntity;
import org.lwjgl.opengl.GL11;

import lotr.client.model.LOTRModelTroll;
import lotr.common.entity.npc.*;
import lotr.common.entity.projectile.LOTREntityThrownRock;
import net.minecraft.entity.*;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderMountainTroll extends LOTRRenderTroll {
	public static LOTRRandomSkins mountainTrollSkins;
	public LOTREntityThrownRock heldRock;

	public LOTRRenderMountainTroll() {
		mountainTrollSkins = LOTRRandomSkins.loadSkinsList("lotr:mob/troll/mountainTroll");
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		return mountainTrollSkins.getRandomSkin((LOTRRandomSkinEntity) entity);
	}

	@Override
	public void renderTrollWeapon(EntityLivingBase entity, float f) {
		LOTREntityMountainTroll troll = (LOTREntityMountainTroll) entity;
		if (troll.isThrowingRocks()) {
			if (mainModel.onGround <= 0.0f) {
				if (heldRock == null) {
					heldRock = new LOTREntityThrownRock(troll.worldObj);
				}
				heldRock.setWorld(troll.worldObj);
				heldRock.setPosition(troll.posX, troll.posY, troll.posZ);
				((LOTRModelTroll) mainModel).rightArm.postRender(0.0625f);
				GL11.glTranslatef(0.375f, 1.5f, 0.0f);
				GL11.glRotatef(45.0f, 0.0f, 1.0f, 0.0f);
				scaleTroll(troll, true);
				renderManager.renderEntityWithPosYaw(heldRock, 0.0, 0.0, 0.0, 0.0f, f);
			}
		} else {
			((LOTRModelTroll) mainModel).renderWoodenClubWithSpikes(0.0625f);
		}
	}
}
