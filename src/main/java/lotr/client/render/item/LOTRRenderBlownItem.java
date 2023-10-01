package lotr.client.render.item;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

public class LOTRRenderBlownItem implements IItemRenderer {
	@Override
	public boolean handleRenderType(ItemStack itemstack, IItemRenderer.ItemRenderType type) {
		return type == IItemRenderer.ItemRenderType.EQUIPPED;
	}

	@Override
	public void renderItem(IItemRenderer.ItemRenderType type, ItemStack itemstack, Object... data) {
		EntityLivingBase equipper = (EntityLivingBase) data[1];
		Item item = itemstack.getItem();
		Tessellator tessellator = Tessellator.instance;
		if (Minecraft.getMinecraft().gameSettings.thirdPersonView != 0 || equipper != Minecraft.getMinecraft().thePlayer) {
			GL11.glScalef(-1.0f, 1.0f, 1.0f);
			GL11.glTranslatef(-1.35f, 0.0f, 0.0f);
			GL11.glRotatef(-45.0f, 0.0f, 0.0f, 1.0f);
			GL11.glTranslatef(0.0f, 0.3f, 0.0f);
			GL11.glTranslatef(0.0f, 0.0f, 0.15f);
		}
		int passes = item.getRenderPasses(itemstack.getItemDamage());
		for (int pass = 0; pass < passes; ++pass) {
			int color = item.getColorFromItemStack(itemstack, pass);
			float r = (color >> 16 & 0xFF) / 255.0f;
			float g = (color >> 8 & 0xFF) / 255.0f;
			float b = (color & 0xFF) / 255.0f;
			GL11.glColor3f(r, g, b);
			IIcon icon = equipper.getItemIcon(itemstack, pass);
			float f = icon.getMinU();
			float f1 = icon.getMaxU();
			float f2 = icon.getMinV();
			float f3 = icon.getMaxV();
			ItemRenderer.renderItemIn2D(tessellator, f1, f2, f, f3, icon.getIconWidth(), icon.getIconHeight(), 0.0625f);
		}
		GL11.glColor3f(1.0f, 1.0f, 1.0f);
		GL11.glDisable(32826);
	}

	@Override
	public boolean shouldUseRenderHelper(IItemRenderer.ItemRenderType type, ItemStack itemstack, IItemRenderer.ItemRendererHelper helper) {
		return false;
	}
}
