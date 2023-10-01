package lotr.client.render.item;

import lotr.client.model.LOTRModelBanner;
import lotr.client.render.entity.LOTRRenderBanner;
import lotr.common.item.LOTRItemBanner;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

public class LOTRRenderBannerItem implements IItemRenderer {
	public static LOTRModelBanner model = new LOTRModelBanner();

	@Override
	public boolean handleRenderType(ItemStack itemstack, IItemRenderer.ItemRenderType type) {
		return type == IItemRenderer.ItemRenderType.EQUIPPED || type == IItemRenderer.ItemRenderType.EQUIPPED_FIRST_PERSON;
	}

	@Override
	public void renderItem(IItemRenderer.ItemRenderType type, ItemStack itemstack, Object... data) {
		GL11.glDisable(2884);
		Entity holder = (Entity) data[1];
		boolean isFirstPerson = holder == Minecraft.getMinecraft().thePlayer && Minecraft.getMinecraft().gameSettings.thirdPersonView == 0;
		boolean renderStand = false;
		TextureManager textureManager = Minecraft.getMinecraft().getTextureManager();
		if (isFirstPerson) {
			GL11.glTranslatef(1.0f, 1.0f, 0.0f);
			GL11.glScalef(-1.0f, 1.0f, 1.0f);
		} else {
			GL11.glTranslatef(-1.5f, 0.85f, -0.1f);
			GL11.glRotatef(75.0f, 0.0f, 0.0f, 1.0f);
		}
		GL11.glScalef(1.0f, -1.0f, 1.0f);
		LOTRItemBanner.BannerType bannerType = LOTRItemBanner.getBannerType(itemstack);
		textureManager.bindTexture(LOTRRenderBanner.getStandTexture(bannerType));
		model.renderPost(0.0625f);
		model.renderLowerPost(0.0625f);
		textureManager.bindTexture(LOTRRenderBanner.getBannerTexture(bannerType));
		model.renderBanner(0.0625f);
	}

	@Override
	public boolean shouldUseRenderHelper(IItemRenderer.ItemRenderType type, ItemStack itemstack, IItemRenderer.ItemRendererHelper helper) {
		return false;
	}
}
