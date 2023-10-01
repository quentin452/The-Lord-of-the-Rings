package lotr.client.render.tileentity;

import lotr.client.LOTRTickHandlerClient;
import lotr.client.render.entity.LOTRRenderBird;
import lotr.common.item.LOTRItemAnimalJar;
import lotr.common.tileentity.LOTRTileEntityAnimalJar;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

public class LOTRRenderAnimalJar extends TileEntitySpecialRenderer implements IItemRenderer {
	@Override
	public boolean handleRenderType(ItemStack itemstack, IItemRenderer.ItemRenderType type) {
		return true;
	}

	@Override
	public void renderItem(IItemRenderer.ItemRenderType type, ItemStack itemstack, Object... data) {
		if (type == IItemRenderer.ItemRenderType.EQUIPPED || type == IItemRenderer.ItemRenderType.EQUIPPED_FIRST_PERSON) {
			GL11.glTranslatef(0.5f, 0.5f, 0.5f);
		}
		EntityLivingBase viewer = Minecraft.getMinecraft().renderViewEntity;
		Entity jarEntity = LOTRItemAnimalJar.getItemJarEntity(itemstack, viewer.worldObj);
		if (jarEntity != null) {
			jarEntity.setLocationAndAngles(0.0, 0.0, 0.0, 0.0f, 0.0f);
			jarEntity.ticksExisted = viewer.ticksExisted;
			GL11.glPushMatrix();
			GL11.glTranslatef(0.0f, -0.5f, 0.0f);
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			GL11.glPushAttrib(1048575);
			if (type == IItemRenderer.ItemRenderType.ENTITY) {
				LOTRRenderBird.renderStolenItem = false;
			}
			RenderManager.instance.renderEntityWithPosYaw(jarEntity, 0.0, 0.0, 0.0, 0.0f, LOTRTickHandlerClient.renderTick);
			LOTRRenderBird.renderStolenItem = true;
			GL11.glPopAttrib();
			GL11.glPopMatrix();
		}
		GL11.glEnable(3008);
		GL11.glAlphaFunc(516, 0.1f);
		GL11.glEnable(3042);
		OpenGlHelper.glBlendFunc(770, 771, 1, 0);
		RenderBlocks.getInstance().renderBlockAsItem(Block.getBlockFromItem(itemstack.getItem()), itemstack.getItemDamage(), 1.0f);
		GL11.glDisable(3042);
	}

	@Override
	public void renderTileEntityAt(TileEntity tileentity, double d, double d1, double d2, float f) {
		LOTRTileEntityAnimalJar jar = (LOTRTileEntityAnimalJar) tileentity;
		Entity jarEntity = jar.getOrCreateJarEntity();
		if (jarEntity != null) {
			GL11.glPushMatrix();
			GL11.glTranslatef(0.0f, jar.getEntityBobbing(f), 0.0f);
			if (jarEntity instanceof EntityLivingBase) {
				EntityLivingBase jarLiving = (EntityLivingBase) jarEntity;
				EntityLivingBase viewer = RenderManager.instance.livingPlayer;
				if (jar.isEntityWatching()) {
					Vec3 viewerPos = viewer.getPosition(f);
					Vec3 entityPos = jarLiving.getPosition(f);
					double dx = entityPos.xCoord - viewerPos.xCoord;
					double dz = entityPos.zCoord - viewerPos.zCoord;
					float lookYaw = (float) Math.toDegrees(Math.atan2(dz, dx));
					jarLiving.rotationYaw = jarLiving.prevRotationYaw = lookYaw + 90.0f;
				}
				jarLiving.renderYawOffset = jarLiving.rotationYaw;
				jarLiving.prevRenderYawOffset = jarLiving.prevRotationYaw;
			}
			RenderManager.instance.renderEntityStatic(jarEntity, f, false);
			GL11.glPopMatrix();
		}
	}

	@Override
	public boolean shouldUseRenderHelper(IItemRenderer.ItemRenderType type, ItemStack itemstack, IItemRenderer.ItemRendererHelper helper) {
		return true;
	}
}
