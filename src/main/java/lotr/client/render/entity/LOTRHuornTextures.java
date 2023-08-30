package lotr.client.render.entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import javax.imageio.ImageIO;

import org.lwjgl.opengl.GL11;

import lotr.common.entity.npc.*;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.resources.*;
import net.minecraft.init.Blocks;
import net.minecraft.util.*;

public class LOTRHuornTextures implements IResourceManagerReloadListener {
	public static LOTRHuornTextures INSTANCE = new LOTRHuornTextures();

	static {
		((IReloadableResourceManager) Minecraft.getMinecraft().getResourceManager()).registerReloadListener(INSTANCE);
	}

	public RenderManager renderManager = RenderManager.instance;
	public HashMap woodTextures = new HashMap();
	public HashMap leafTexturesFast = new HashMap();

	public HashMap leafTexturesFancy = new HashMap();

	public void bindLeafTexture(LOTREntityHuornBase entity) {
		int treeType = entity.getTreeType();
		Block block = LOTREntityTree.LEAF_BLOCKS[treeType];
		int meta = LOTREntityTree.LEAF_META[treeType];
		ResourceLocation texture = (ResourceLocation) leafTexturesFast.get(treeType);
		if (Minecraft.isFancyGraphicsEnabled()) {
			texture = (ResourceLocation) leafTexturesFancy.get(treeType);
		}
		if (texture == null) {
			texture = getDynamicHuornTexture(block, meta);
			if (Minecraft.isFancyGraphicsEnabled()) {
				leafTexturesFancy.put(treeType, texture);
			} else {
				leafTexturesFast.put(treeType, texture);
			}
		}
		renderManager.renderEngine.bindTexture(texture);
		int color = block.getRenderColor(meta);
		if (block == Blocks.leaves && meta == 0) {
			int i = MathHelper.floor_double(entity.posX);
			int j = MathHelper.floor_double(entity.boundingBox.minY);
			int k = MathHelper.floor_double(entity.posZ);
			color = entity.worldObj.getBiomeGenForCoords(i, k).getBiomeFoliageColor(i, j, k);
		}
		float r = (color >> 16 & 0xFF) / 255.0f;
		float g = (color >> 8 & 0xFF) / 255.0f;
		float b = (color & 0xFF) / 255.0f;
		GL11.glColor4f(r, g, b, 1.0f);
	}

	public void bindWoodTexture(LOTREntityHuornBase entity) {
		int treeType = entity.getTreeType();
		Block block = LOTREntityTree.WOOD_BLOCKS[treeType];
		int meta = LOTREntityTree.WOOD_META[treeType];
		ResourceLocation texture = (ResourceLocation) woodTextures.get(treeType);
		if (texture == null) {
			texture = getDynamicHuornTexture(block, meta);
			woodTextures.put(treeType, texture);
		}
		renderManager.renderEngine.bindTexture(texture);
	}

	public ResourceLocation getDynamicHuornTexture(Block block, int meta) {
		try {
			boolean aF = Minecraft.getMinecraft().gameSettings.anisotropicFiltering > 1;
			Minecraft.getMinecraft().getTextureManager().getTexture(TextureMap.locationBlocksTexture);
			BufferedImage[] icons = new BufferedImage[6];
			int width = block.getIcon(0, meta).getIconWidth();
			if (aF) {
				width -= 16;
			}
			for (int i = 0; i < 6; ++i) {
				IIcon icon = block.getIcon(i, meta);
				int iconWidth = icon.getIconWidth();
				int iconHeight = icon.getIconHeight();
				if (aF) {
					iconWidth -= 16;
					iconHeight -= 16;
				}
				if (iconWidth != width || iconHeight != width) {
					throw new RuntimeException("Error registering Huorn textures: all icons for block " + block.getUnlocalizedName() + " must have the same texture dimensions");
				}
				ResourceLocation iconPath = new ResourceLocation(icon.getIconName());
				ResourceLocation r = new ResourceLocation(iconPath.getResourceDomain(), "textures/blocks/" + iconPath.getResourcePath() + ".png");
				BufferedImage iconImage = ImageIO.read(Minecraft.getMinecraft().getResourceManager().getResource(r).getInputStream());
				icons[i] = iconImage.getSubimage(0, 0, width, width);
			}
			BufferedImage image = new BufferedImage(width * 4, width * 2, 2);
			Graphics2D g2d = image.createGraphics();
			g2d.drawImage(icons[1], null, width, 0);
			g2d.drawImage(icons[0], null, width * 2, 0);
			g2d.drawImage(icons[4], null, 0, width);
			g2d.drawImage(icons[2], null, width, width);
			g2d.drawImage(icons[5], null, width * 2, width);
			g2d.drawImage(icons[3], null, width * 3, width);
			g2d.dispose();
			return renderManager.renderEngine.getDynamicTextureLocation("lotr:huorn", new DynamicTexture(image));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void onResourceManagerReload(IResourceManager resourcemanager) {
		woodTextures.clear();
		leafTexturesFast.clear();
		leafTexturesFancy.clear();
	}
}
