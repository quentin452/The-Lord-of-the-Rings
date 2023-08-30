package lotr.client;

import java.lang.reflect.Field;
import java.util.*;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import lotr.client.render.item.*;
import lotr.client.render.tileentity.LOTRRenderAnimalJar;
import lotr.common.LOTRMod;
import lotr.common.item.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.*;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.MinecraftForge;

public class LOTRItemRendererManager implements IResourceManagerReloadListener {
	public static LOTRItemRendererManager INSTANCE;
	public static List<LOTRRenderLargeItem> largeItemRenderers = new ArrayList<>();

	@Override
	public void onResourceManagerReload(IResourceManager resourceManager) {
		largeItemRenderers.clear();
		try {
			for (Field field : LOTRMod.class.getFields()) {
				boolean isLarge;
				if (!(field.get(null) instanceof Item)) {
					continue;
				}
				Item item = (Item) field.get(null);
				MinecraftForgeClient.registerItemRenderer(item, null);
				LOTRRenderLargeItem largeItemRenderer = LOTRRenderLargeItem.getRendererIfLarge(item);
				isLarge = largeItemRenderer != null;
				if (item instanceof LOTRItemCrossbow) {
					MinecraftForgeClient.registerItemRenderer(item, new LOTRRenderCrossbow());
				} else if (item instanceof LOTRItemBow) {
					MinecraftForgeClient.registerItemRenderer(item, new LOTRRenderBow(largeItemRenderer));
				} else if (item instanceof LOTRItemSword && ((LOTRItemSword) item).isElvenBlade()) {
					double d = 24.0;
					if (item == LOTRMod.sting) {
						d = 40.0;
					}
					MinecraftForgeClient.registerItemRenderer(item, new LOTRRenderElvenBlade(d, largeItemRenderer));
				} else if (isLarge) {
					MinecraftForgeClient.registerItemRenderer(item, largeItemRenderer);
				}
				if (largeItemRenderer == null) {
					continue;
				}
				largeItemRenderers.add(largeItemRenderer);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(LOTRMod.commandTable), new LOTRRenderInvTableCommand());
		MinecraftForgeClient.registerItemRenderer(LOTRMod.hobbitPipe, new LOTRRenderBlownItem());
		MinecraftForgeClient.registerItemRenderer(LOTRMod.commandHorn, new LOTRRenderBlownItem());
		MinecraftForgeClient.registerItemRenderer(LOTRMod.conquestHorn, new LOTRRenderBlownItem());
		MinecraftForgeClient.registerItemRenderer(LOTRMod.banner, new LOTRRenderBannerItem());
		MinecraftForgeClient.registerItemRenderer(LOTRMod.orcSkullStaff, new LOTRRenderSkullStaff());
		for (Object o : Item.itemRegistry) {
			Item item = (Item) o;
			if (item instanceof LOTRItemAnimalJar) {
				MinecraftForgeClient.registerItemRenderer(item, new LOTRRenderAnimalJar());
			}
		}
	}

	@SubscribeEvent
	public void preTextureStitch(TextureStitchEvent.Pre event) {
		TextureMap map = event.map;
		if (map.getTextureType() == 1) {
			for (LOTRRenderLargeItem largeRenderer : largeItemRenderers) {
				largeRenderer.registerIcons(map);
			}
		}
	}

	public static void load() {
		Minecraft mc = Minecraft.getMinecraft();
		IResourceManager resMgr = mc.getResourceManager();
		INSTANCE = new LOTRItemRendererManager();
		INSTANCE.onResourceManagerReload(resMgr);
		((IReloadableResourceManager) resMgr).registerReloadListener(INSTANCE);
		MinecraftForge.EVENT_BUS.register(INSTANCE);
	}
}
