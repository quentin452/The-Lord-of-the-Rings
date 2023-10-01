package lotr.client.render.item;

import lotr.client.LOTRClientProxy;
import lotr.common.item.LOTRItemLance;
import lotr.common.item.LOTRItemPike;
import lotr.common.item.LOTRItemSpear;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.IResource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class LOTRRenderLargeItem implements IItemRenderer {
	public static Map<String, Float> sizeFolders = new HashMap<>();

	static {
		sizeFolders.put("large", 2.0f);
		sizeFolders.put("large2", 3.0f);
	}

	public Item theItem;
	public String folderName;
	public float largeIconScale;
	public IIcon largeIcon;

	public Collection<ExtraLargeIconToken> extraTokens = new ArrayList<>();

	public LOTRRenderLargeItem(Item item, String dir, float f) {
		theItem = item;
		folderName = dir;
		largeIconScale = f;
	}

	public static ResourceLocation getLargeTexturePath(Item item, String folder) {
		String prefix = "lotr:";
		String itemName = item.getUnlocalizedName();
		itemName = itemName.substring(itemName.indexOf(prefix) + prefix.length());
		String s = prefix + "textures/items/" + folder + "/" + itemName +
				".png";
		return new ResourceLocation(s);
	}

	public static LOTRRenderLargeItem getRendererIfLarge(Item item) {
		for (Map.Entry<String, Float> entry : sizeFolders.entrySet()) {
			String folder = entry.getKey();
			float iconScale = entry.getValue();
			try {
				ResourceLocation resLoc = getLargeTexturePath(item, folder);
				IResource res = Minecraft.getMinecraft().getResourceManager().getResource(resLoc);
				if (res == null) {
					continue;
				}
				return new LOTRRenderLargeItem(item, folder, iconScale);
			} catch (IOException resLoc) {
			}
		}
		return null;
	}

	public void doTransformations() {
		GL11.glTranslatef(-(largeIconScale - 1.0f) / 2.0f, -(largeIconScale - 1.0f) / 2.0f, 0.0f);
		GL11.glScalef(largeIconScale, largeIconScale, 1.0f);
	}

	public ExtraLargeIconToken extraIcon(String name) {
		ExtraLargeIconToken token = new ExtraLargeIconToken(name);
		extraTokens.add(token);
		return token;
	}

	@Override
	public boolean handleRenderType(ItemStack itemstack, IItemRenderer.ItemRenderType type) {
		return type == IItemRenderer.ItemRenderType.EQUIPPED || type == IItemRenderer.ItemRenderType.EQUIPPED_FIRST_PERSON;
	}

	public void registerIcons(IIconRegister register) {
		largeIcon = registerLargeIcon(register, null);
		for (ExtraLargeIconToken token : extraTokens) {
			token.icon = registerLargeIcon(register, token.name);
		}
	}

	public IIcon registerLargeIcon(IIconRegister register, String extra) {
		String prefix = "lotr:";
		String itemName = theItem.getUnlocalizedName();
		itemName = itemName.substring(itemName.indexOf(prefix) + prefix.length());
		StringBuilder path = new StringBuilder().append(prefix).append(folderName).append("/").append(itemName);
		if (!StringUtils.isNullOrEmpty(extra)) {
			path.append("_").append(extra);
		}
		return register.registerIcon(path.toString());
	}

	@Override
	public void renderItem(IItemRenderer.ItemRenderType type, ItemStack itemstack, Object... data) {
		EntityLivingBase entityliving;
		GL11.glPushMatrix();
		Entity holder = (Entity) data[1];
		boolean isFirstPerson = holder == Minecraft.getMinecraft().thePlayer && Minecraft.getMinecraft().gameSettings.thirdPersonView == 0;
		Item item = itemstack.getItem();
		if (item instanceof LOTRItemSpear && holder instanceof EntityPlayer && ((EntityPlayer) holder).getItemInUse() == itemstack) {
			GL11.glRotatef(260.0f, 0.0f, 0.0f, 1.0f);
			GL11.glTranslatef(-1.0f, 0.0f, 0.0f);
		}
		if (item instanceof LOTRItemPike && holder instanceof EntityLivingBase && (entityliving = (EntityLivingBase) holder).getHeldItem() == itemstack && entityliving.swingProgress <= 0.0f) {
			if (entityliving.isSneaking()) {
				if (isFirstPerson) {
					GL11.glRotatef(270.0f, 0.0f, 0.0f, 1.0f);
					GL11.glTranslatef(-1.0f, 0.0f, 0.0f);
				} else {
					GL11.glTranslatef(0.0f, -0.1f, 0.0f);
					GL11.glRotatef(20.0f, 0.0f, 0.0f, 1.0f);
				}
			} else if (!isFirstPerson) {
				GL11.glTranslatef(0.0f, -0.3f, 0.0f);
				GL11.glRotatef(40.0f, 0.0f, 0.0f, 1.0f);
			}
		}
		if (item instanceof LOTRItemLance && holder instanceof EntityLivingBase && ((EntityLivingBase) holder).getHeldItem() == itemstack) {
			if (isFirstPerson) {
				GL11.glRotatef(260.0f, 0.0f, 0.0f, 1.0f);
			} else {
				GL11.glTranslatef(0.7f, 0.0f, 0.0f);
				GL11.glRotatef(-30.0f, 0.0f, 0.0f, 1.0f);
			}
			GL11.glTranslatef(-1.0f, 0.0f, 0.0f);
		}
		renderLargeItem();
		if (itemstack.hasEffect(0)) {
			LOTRClientProxy.renderEnchantmentEffect();
		}
		GL11.glPopMatrix();
	}

	public void renderLargeItem() {
		renderLargeItem(largeIcon);
	}

	public void renderLargeItem(ExtraLargeIconToken token) {
		renderLargeItem(token.icon);
	}

	public void renderLargeItem(IIcon icon) {
		doTransformations();
		Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.locationItemsTexture);
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		Tessellator tess = Tessellator.instance;
		ItemRenderer.renderItemIn2D(tess, icon.getMaxU(), icon.getMinV(), icon.getMinU(), icon.getMaxV(), icon.getIconWidth(), icon.getIconHeight(), 0.0625f);
	}

	@Override
	public boolean shouldUseRenderHelper(IItemRenderer.ItemRenderType type, ItemStack itemstack, IItemRenderer.ItemRendererHelper helper) {
		return false;
	}

	public static class ExtraLargeIconToken {
		public String name;
		public IIcon icon;

		public ExtraLargeIconToken(String s) {
			name = s;
		}
	}

}
