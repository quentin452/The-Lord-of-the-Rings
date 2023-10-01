package lotr.client.render.tileentity;

import lotr.client.LOTRTickHandlerClient;
import lotr.common.entity.LOTREntities;
import lotr.common.tileentity.LOTRTileEntityMobSpawner;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

import java.util.HashMap;

public class LOTRTileEntityMobSpawnerRenderer extends TileEntitySpecialRenderer {
	public static double itemYaw;
	public static double prevItemYaw;
	public int tempID;
	public HashMap initialisedItemEntities = new HashMap();

	public static void onClientTick() {
		prevItemYaw = itemYaw = MathHelper.wrapAngleTo180_double(itemYaw);
		itemYaw += 1.5;
	}

	@Override
	public void func_147496_a(World world) {
		loadEntities(world);
	}

	public void loadEntities(World world) {
		unloadEntities();
		if (world != null) {
			for (LOTREntities.SpawnEggInfo info : LOTREntities.spawnEggs.values()) {
				String entityName = LOTREntities.getStringFromID(info.spawnedID);
				Entity entity = EntityList.createEntityByName(entityName, world);
				if (entity instanceof EntityLiving) {
					((EntityLiving) entity).onSpawnWithEgg(null);
				}
				initialisedItemEntities.put(info.spawnedID, entity);
			}
		}
	}

	public void renderInvMobSpawner(int i) {
		if (Minecraft.getMinecraft().currentScreen instanceof GuiIngameMenu) {
			return;
		}
		GL11.glPushMatrix();
		GL11.glTranslatef(-0.5f, -0.5f, -0.5f);
		tempID = i;
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		GL11.glPushAttrib(1048575);
		renderTileEntityAt(null, 0.0, 0.0, 0.0, LOTRTickHandlerClient.renderTick);
		GL11.glPopAttrib();
		tempID = 0;
		GL11.glPopMatrix();
	}

	@Override
	public void renderTileEntityAt(TileEntity tileentity, double d, double d1, double d2, float f) {
		WorldClient world = Minecraft.getMinecraft().theWorld;
		LOTRTileEntityMobSpawner mobSpawner = (LOTRTileEntityMobSpawner) tileentity;
		if (mobSpawner != null && !mobSpawner.isActive()) {
			return;
		}
		GL11.glPushMatrix();
		GL11.glTranslatef((float) d + 0.5f, (float) d1, (float) d2 + 0.5f);
		Entity entity;
		double yaw;
		double prevYaw;
		if (mobSpawner != null) {
			entity = mobSpawner.getMobEntity(world);
			yaw = mobSpawner.yaw;
			prevYaw = mobSpawner.prevYaw;
		} else {
			entity = (Entity) initialisedItemEntities.get(tempID);
			yaw = itemYaw;
			prevYaw = prevItemYaw;
		}
		if (entity != null) {
			float f1 = 0.4375f;
			GL11.glTranslatef(0.0f, 0.4f, 0.0f);
			GL11.glRotatef((float) (prevYaw + (yaw - prevYaw) * f) * 10.0f, 0.0f, 1.0f, 0.0f);
			GL11.glRotatef(-30.0f, 1.0f, 0.0f, 0.0f);
			GL11.glTranslatef(0.0f, -0.4f, 0.0f);
			GL11.glScalef(f1, f1, f1);
			entity.setLocationAndAngles(d, d1, d2, 0.0f, 0.0f);
			RenderManager.instance.renderEntityWithPosYaw(entity, 0.0, 0.0, 0.0, 0.0f, f);
		}
		GL11.glPopMatrix();
	}

	public void unloadEntities() {
		initialisedItemEntities.clear();
	}
}
