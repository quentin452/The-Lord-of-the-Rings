package lotr.client.render.entity;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;

import javax.imageio.ImageIO;

import cpw.mods.fml.common.FMLLog;
import lotr.client.LOTRTextures;
import lotr.common.entity.LOTRRandomSkinEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.resources.*;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class LOTRRandomSkins implements IResourceManagerReloadListener {
	public static Random rand = new Random();
	public static Minecraft mc = Minecraft.getMinecraft();
	public static Map<String, LOTRRandomSkins> allRandomSkins = new HashMap<>();
	public String skinPath;
	public List<ResourceLocation> skins;

	public LOTRRandomSkins(String path, boolean register, Object... args) {
		skinPath = path;
		handleExtraArgs(args);
		if (register) {
			((IReloadableResourceManager) mc.getResourceManager()).registerReloadListener(this);
		} else {
			loadAllRandomSkins();
		}
	}

	public List<ResourceLocation> getAllSkins() {
		return skins;
	}

	public ResourceLocation getRandomSkin() {
		int i = rand.nextInt(skins.size());
		return skins.get(i);
	}

	public ResourceLocation getRandomSkin(LOTRRandomSkinEntity rsEntity) {
		if (skins == null || skins.isEmpty()) {
			return LOTRTextures.missingTexture;
		}
		Entity entity = (Entity) rsEntity;
		long l = entity.getUniqueID().getLeastSignificantBits();
		long hash = skinPath.hashCode();
		l = l * 39603773L ^ l * 6583690632L ^ hash;
		l = l * hash * 2906920L + l * 65936063L;
		rand.setSeed(l);
		int i = rand.nextInt(skins.size());
		return skins.get(i);
	}

	public void handleExtraArgs(Object... args) {
	}

	public void loadAllRandomSkins() {
		skins = new ArrayList<>();
		int skinCount = 0;
		int skips = 0;
		int maxSkips = 10;
		boolean foundAfterSkip = false;
		do {
			ResourceLocation skin = new ResourceLocation(skinPath + "/" + skinCount + ".png");
			boolean noFile = false;
			try {
				if (mc.getResourceManager().getResource(skin) == null) {
					noFile = true;
				}
			} catch (Exception e) {
				noFile = true;
			}
			if (noFile) {
				skips++;
				if (skips >= maxSkips) {
					break;
				}
				++skinCount;
				continue;
			}
			skins.add(skin);
			++skinCount;
			if (skips <= 0) {
				continue;
			}
			foundAfterSkip = true;
		} while (true);
		if (skins.isEmpty()) {
			FMLLog.warning("LOTR: No random skins for %s", skinPath);
		}
		if (foundAfterSkip) {
			FMLLog.warning("LOTR: Random skins %s skipped a number. This is not good - please number your skins from 0 and upwards, with no gaps!", skinPath);
		}
	}

	@Override
	public void onResourceManagerReload(IResourceManager resourcemanager) {
		loadAllRandomSkins();
	}

	public static LOTRRandomSkins getCombinatorialSkins(String path, String... layers) {
		StringBuilder combinedPath = new StringBuilder(path);
		for (String s : layers) {
			combinedPath.append("_").append(s);
		}
		LOTRRandomSkins skins = allRandomSkins.get(combinedPath.toString());
		if (skins == null) {
			skins = new LOTRRandomSkinsCombinatorial(path, layers);
			allRandomSkins.put(combinedPath.toString(), skins);
		}
		return skins;
	}

	public static LOTRRandomSkins loadSkinsList(String path) {
		LOTRRandomSkins skins = allRandomSkins.get(path);
		if (skins == null) {
			skins = new LOTRRandomSkins(path, true);
			allRandomSkins.put(path, skins);
		}
		return skins;
	}

	public static int nextInt(LOTRRandomSkinEntity rsEntity, int n) {
		Entity entity = (Entity) rsEntity;
		long l = entity.getUniqueID().getLeastSignificantBits();
		l = l * 29506206L * (l ^ 0x6429C58L) + 25859L;
		l = l * l * 426430295004L + 25925025L * l;
		rand.setSeed(l);
		return rand.nextInt(n);
	}

	public static class LOTRRandomSkinsCombinatorial extends LOTRRandomSkins {
		public String[] skinLayers;

		public LOTRRandomSkinsCombinatorial(String path, String... layers) {
			super(path, true, (Object[]) layers);
		}

		@Override
		public void handleExtraArgs(Object... args) {
			skinLayers = (String[]) args;
		}

		@Override
		public void loadAllRandomSkins() {
			skins = new ArrayList<>();
			Collection<BufferedImage> layeredImages = new ArrayList<>();
			Collection<BufferedImage> tempLayered = new ArrayList<>();
			block2:
			for (String layer : skinLayers) {
				String layerPath = skinPath + "/" + layer;
				LOTRRandomSkins layerSkins = new LOTRRandomSkins(layerPath, false);
				tempLayered.clear();
				for (ResourceLocation overlay : layerSkins.getAllSkins()) {
					try {
						BufferedImage overlayImage = ImageIO.read(mc.getResourceManager().getResource(overlay).getInputStream());
						if (layeredImages.isEmpty()) {
							tempLayered.add(overlayImage);
							continue;
						}
						for (BufferedImage baseImage : layeredImages) {
							int skinWidth = baseImage.getWidth();
							int skinHeight = baseImage.getHeight();
							BufferedImage newImage = new BufferedImage(skinWidth, skinHeight, 2);
							for (int i = 0; i < skinWidth; ++i) {
								for (int j = 0; j < skinHeight; ++j) {
									int opaqueTest;
									int baseRGB = baseImage.getRGB(i, j);
									int overlayRGB = overlayImage.getRGB(i, j);
									opaqueTest = -16777216;
									if ((overlayRGB & opaqueTest) == opaqueTest) {
										newImage.setRGB(i, j, overlayRGB);
										continue;
									}
									newImage.setRGB(i, j, baseRGB);
								}
							}
							tempLayered.add(newImage);
						}
					} catch (IOException e) {
						FMLLog.severe("LOTR: Error combining skins " + skinPath + " on layer " + layer + "!");
						e.printStackTrace();
						break block2;
					}
				}
				layeredImages.clear();
				layeredImages.addAll(tempLayered);
			}
			int skinCount = 0;
			for (BufferedImage image : layeredImages) {
				ResourceLocation skin = mc.renderEngine.getDynamicTextureLocation(skinPath + "_layered/" + skinCount + ".png", new DynamicTexture(image));
				skins.add(skin);
				++skinCount;
			}
			FMLLog.info("LOTR: Assembled combinatorial skins for " + skinPath + ": " + skins.size() + " skins");
		}
	}

}
