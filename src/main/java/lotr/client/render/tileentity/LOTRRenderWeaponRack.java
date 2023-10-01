package lotr.client.render.tileentity;

import lotr.client.model.LOTRModelWeaponRack;
import lotr.client.render.item.LOTRRenderBow;
import lotr.common.tileentity.LOTRTileEntityWeaponRack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

public class LOTRRenderWeaponRack extends TileEntitySpecialRenderer {
	public static ResourceLocation rackTexture = new ResourceLocation("lotr:item/weaponRack.png");
	public static LOTRModelWeaponRack rackModel = new LOTRModelWeaponRack();

	@Override
	public void renderTileEntityAt(TileEntity tileentity, double d, double d1, double d2, float f) {
		LOTRTileEntityWeaponRack weaponRack = (LOTRTileEntityWeaponRack) tileentity;
		GL11.glPushMatrix();
		GL11.glDisable(2884);
		GL11.glEnable(32826);
		GL11.glEnable(3008);
		GL11.glTranslatef((float) d + 0.5f, (float) d1 + 1.5f, (float) d2 + 0.5f);
		int meta = weaponRack.getBlockMetadata();
		int dir = meta & 3;
		boolean wall = (meta & 4) != 0;
		switch (dir) {
			case 0: {
				GL11.glRotatef(0.0f, 0.0f, 1.0f, 0.0f);
				break;
			}
			case 1: {
				GL11.glRotatef(270.0f, 0.0f, 1.0f, 0.0f);
				break;
			}
			case 2: {
				GL11.glRotatef(180.0f, 0.0f, 1.0f, 0.0f);
				break;
			}
			case 3: {
				GL11.glRotatef(90.0f, 0.0f, 1.0f, 0.0f);
			}
		}
		if (wall) {
			GL11.glTranslatef(0.0f, 0.375f, -0.5f);
		}
		GL11.glScalef(-1.0f, -1.0f, 1.0f);
		float scale = 0.0625f;
		bindTexture(rackTexture);
		rackModel.onWall = wall;
		rackModel.render(null, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, scale);
		ItemStack weaponItem = weaponRack.getWeaponItem();
		if (weaponItem != null) {
			float weaponScale = 0.625f;
			GL11.glScalef(weaponScale, weaponScale, weaponScale);
			GL11.glScalef(-1.0f, 1.0f, 1.0f);
			GL11.glTranslatef(0.0f, 0.52f, 0.0f);
			if (wall) {
				GL11.glTranslatef(0.0f, 1.1f, 0.51f);
			}
			GL11.glRotatef(45.0f, 0.0f, 0.0f, 1.0f);
			GL11.glTranslatef(0.9375f, 0.0625f, 0.0f);
			GL11.glRotatef(-335.0f, 0.0f, 0.0f, 1.0f);
			GL11.glRotatef(-50.0f, 0.0f, 1.0f, 0.0f);
			GL11.glScalef(0.6666667f, 0.6666667f, 0.6666667f);
			GL11.glTranslatef(0.0f, 0.3f, 0.0f);
			RenderManager renderManager = RenderManager.instance;
			int passes = 1;
			if (weaponItem.getItem().requiresMultipleRenderPasses()) {
				passes = weaponItem.getItem().getRenderPasses(weaponItem.getItemDamage());
			}
			LOTRRenderBow.renderingWeaponRack = true;
			for (int pass = 0; pass < passes; ++pass) {
				int color = weaponItem.getItem().getColorFromItemStack(weaponItem, pass);
				float r = (color >> 16 & 0xFF) / 255.0f;
				float g = (color >> 8 & 0xFF) / 255.0f;
				float b = (color & 0xFF) / 255.0f;
				GL11.glColor4f(r, g, b, 1.0f);
				renderManager.itemRenderer.renderItem(weaponRack.getEntityForRender(), weaponItem, 0, IItemRenderer.ItemRenderType.EQUIPPED);
			}
			LOTRRenderBow.renderingWeaponRack = false;
		}
		GL11.glEnable(2884);
		GL11.glDisable(32826);
		GL11.glPopMatrix();
		renderWeaponName(weaponRack, d + 0.5, d1 + 0.75, d2 + 0.5);
	}

	public void renderWeaponName(LOTRTileEntityWeaponRack rack, double d, double d1, double d2) {
		MovingObjectPosition mop = Minecraft.getMinecraft().objectMouseOver;
		if (mop != null && mop.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && mop.blockX == rack.xCoord && mop.blockY == rack.yCoord && mop.blockZ == rack.zCoord) {
			ItemStack weaponItem = rack.getWeaponItem();
			if (Minecraft.isGuiEnabled() && weaponItem != null && weaponItem.hasDisplayName()) {
				float f2;
				RenderManager renderManager = RenderManager.instance;
				FontRenderer fontRenderer = func_147498_b();
				float f = 1.6f;
				float f1 = 0.016666668f * f;
				double dSq = renderManager.livingPlayer.getDistanceSq(rack.xCoord + 0.5, rack.yCoord + 0.5, rack.zCoord);
				f2 = 64.0f;
				if (dSq < f2 * f2) {
					String name = weaponItem.getDisplayName();
					GL11.glPushMatrix();
					GL11.glTranslatef((float) d, (float) d1 + 0.5f, (float) d2);
					GL11.glNormal3f(0.0f, 1.0f, 0.0f);
					GL11.glRotatef(-renderManager.playerViewY, 0.0f, 1.0f, 0.0f);
					GL11.glRotatef(renderManager.playerViewX, 1.0f, 0.0f, 0.0f);
					GL11.glScalef(-f1, -f1, f1);
					GL11.glDisable(2896);
					GL11.glDepthMask(false);
					GL11.glDisable(2929);
					GL11.glEnable(3042);
					OpenGlHelper.glBlendFunc(770, 771, 1, 0);
					Tessellator tessellator = Tessellator.instance;
					int b0 = 0;
					GL11.glDisable(3553);
					tessellator.startDrawingQuads();
					int j = fontRenderer.getStringWidth(name) / 2;
					tessellator.setColorRGBA_F(0.0f, 0.0f, 0.0f, 0.25f);
					tessellator.addVertex(-j - 1, -1 + b0, 0.0);
					tessellator.addVertex(-j - 1, 8 + b0, 0.0);
					tessellator.addVertex(j + 1, 8 + b0, 0.0);
					tessellator.addVertex(j + 1, -1 + b0, 0.0);
					tessellator.draw();
					GL11.glEnable(3553);
					fontRenderer.drawString(name, -fontRenderer.getStringWidth(name) / 2, b0, 553648127);
					GL11.glEnable(2929);
					GL11.glDepthMask(true);
					fontRenderer.drawString(name, -fontRenderer.getStringWidth(name) / 2, b0, -1);
					GL11.glEnable(2896);
					GL11.glDisable(3042);
					GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
					GL11.glPopMatrix();
				}
			}
		}
	}
}
