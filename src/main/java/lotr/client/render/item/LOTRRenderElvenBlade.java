package lotr.client.render.item;

import lotr.client.LOTRClientProxy;
import lotr.common.LOTRConfig;
import lotr.common.entity.npc.LOTREntityOrc;
import lotr.common.item.LOTRItemSword;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

import java.util.List;

public class LOTRRenderElvenBlade implements IItemRenderer {
	public double distance;
	public LOTRRenderLargeItem largeItemRenderer;
	public LOTRRenderLargeItem.ExtraLargeIconToken tokenGlowing;

	public LOTRRenderElvenBlade(double d, LOTRRenderLargeItem large) {
		distance = d;
		largeItemRenderer = large;
		if (largeItemRenderer != null) {
			tokenGlowing = largeItemRenderer.extraIcon("glowing");
		}
	}

	@Override
	public boolean handleRenderType(ItemStack itemstack, IItemRenderer.ItemRenderType type) {
		return type == IItemRenderer.ItemRenderType.EQUIPPED || type == IItemRenderer.ItemRenderType.EQUIPPED_FIRST_PERSON;
	}

	@Override
	public void renderItem(IItemRenderer.ItemRenderType type, ItemStack itemstack, Object... data) {
		EntityLivingBase entity = (EntityLivingBase) data[1];
		Item item = itemstack.getItem();
		entity.worldObj.theProfiler.startSection("elvenBlade");
		boolean glows = false;
		List orcs = entity.worldObj.getEntitiesWithinAABB(LOTREntityOrc.class, entity.boundingBox.expand(distance, distance, distance));
		if (!orcs.isEmpty()) {
			glows = true;
		}
		if (glows) {
			GL11.glDisable(2896);
		}
		if (largeItemRenderer != null) {
			if (glows) {
				largeItemRenderer.renderLargeItem(tokenGlowing);
			} else {
				largeItemRenderer.renderLargeItem();
			}
		} else {
			IIcon icon = ((EntityLivingBase) data[1]).getItemIcon(itemstack, 0);
			if (glows) {
				icon = ((LOTRItemSword) item).glowingIcon;
			}
			icon = RenderBlocks.getInstance().getIconSafe(icon);
			float minU = icon.getMinU();
			float maxU = icon.getMaxU();
			float minV = icon.getMinV();
			float maxV = icon.getMaxV();
			int width = icon.getIconWidth();
			int height = icon.getIconWidth();
			Tessellator tessellator = Tessellator.instance;
			ItemRenderer.renderItemIn2D(tessellator, maxU, minV, minU, maxV, width, height, 0.0625f);
		}
		if (itemstack.hasEffect(0)) {
			LOTRClientProxy.renderEnchantmentEffect();
		}
		if (glows) {
			GL11.glEnable(2896);
			if (LOTRConfig.elvenBladeGlow) {
				for (int i = 0; i < 4; ++i) {
					LOTRClientProxy.renderEnchantmentEffect();
				}
			}
		}
		GL11.glDisable(32826);
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		entity.worldObj.theProfiler.endSection();
	}

	@Override
	public boolean shouldUseRenderHelper(IItemRenderer.ItemRenderType type, ItemStack itemstack, IItemRenderer.ItemRendererHelper helper) {
		return false;
	}
}
