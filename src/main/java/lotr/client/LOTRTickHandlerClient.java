package lotr.client;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import lotr.client.fx.LOTREntityDeadMarshFace;
import lotr.client.gui.LOTRGuiMap;
import lotr.client.gui.LOTRGuiMenu;
import lotr.client.gui.LOTRGuiMiniquestTracker;
import lotr.client.gui.LOTRGuiNotificationDisplay;
import lotr.client.model.LOTRModelCompass;
import lotr.client.render.LOTRCloudRenderer;
import lotr.client.render.LOTRRenderNorthernLights;
import lotr.client.render.entity.LOTRNPCRendering;
import lotr.client.render.tileentity.LOTRTileEntityMobSpawnerRenderer;
import lotr.client.sound.LOTRAmbience;
import lotr.client.sound.LOTRMusicTicker;
import lotr.client.sound.LOTRMusicTrack;
import lotr.common.*;
import lotr.common.block.LOTRBlockLeavesBase;
import lotr.common.enchant.LOTREnchantment;
import lotr.common.enchant.LOTREnchantmentHelper;
import lotr.common.entity.LOTRInvasionStatus;
import lotr.common.entity.LOTRMountFunctions;
import lotr.common.entity.item.LOTREntityPortal;
import lotr.common.entity.npc.LOTREntityBalrog;
import lotr.common.entity.npc.LOTREntityBarrowWight;
import lotr.common.entity.npc.LOTREntityScrapTrader;
import lotr.common.entity.npc.LOTREntitySpiderBase;
import lotr.common.fac.*;
import lotr.common.fellowship.LOTRFellowshipData;
import lotr.common.item.*;
import lotr.common.quest.IPickpocketable;
import lotr.common.util.LOTRColorUtil;
import lotr.common.util.LOTRFunctions;
import lotr.common.util.LOTRLog;
import lotr.common.util.LOTRVersionChecker;
import lotr.common.world.LOTRWorldChunkManager;
import lotr.common.world.LOTRWorldProvider;
import lotr.common.world.biome.LOTRBiome;
import lotr.common.world.biome.LOTRBiomeGenUtumno;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.map.LOTRConquestGrid;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.*;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.*;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.client.GuiIngameForge;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.world.WorldEvent;
import org.lwjgl.opengl.GL11;

import java.util.*;

public class LOTRTickHandlerClient {
	public static ResourceLocation portalOverlay = new ResourceLocation("lotr:misc/portal_overlay.png");
	public static ResourceLocation elvenPortalOverlay = new ResourceLocation("lotr:misc/elvenportal_overlay.png");
	public static ResourceLocation morgulPortalOverlay = new ResourceLocation("lotr:misc/morgulportal_overlay.png");
	public static ResourceLocation mistOverlay = new ResourceLocation("lotr:misc/mist_overlay.png");
	public static ResourceLocation frostOverlay = new ResourceLocation("lotr:misc/frost_overlay.png");
	public static float[] frostRGBMiddle = {0.4F, 0.46F, 0.74F};
	public static float[] frostRGBEdge = {1.0F, 1.0F, 1.0F};
	public static ResourceLocation burnOverlay = new ResourceLocation("lotr:misc/burn_overlay.png");
	public static ResourceLocation wightOverlay = new ResourceLocation("lotr:misc/wight.png");
	public static HashMap<Object, Object> playersInPortals = new HashMap<>();
	public static HashMap playersInElvenPortals = new HashMap<>();
	public static HashMap playersInMorgulPortals = new HashMap<>();
	public static int clientTick;
	public static float renderTick;
	public static int alignDrainTick;
	public static int alignDrainTickMax = 200;
	public static int alignDrainNum;
	public static LOTRInvasionStatus watchedInvasion = new LOTRInvasionStatus();
	public static LOTRGuiNotificationDisplay notificationDisplay;
	public static LOTRGuiMiniquestTracker miniquestTracker;
	public static boolean anyWightsViewed;
	public static int scrapTraderMisbehaveTick;
	public LOTRAmbience ambienceTicker;
	public GuiScreen lastGuiOpen;
	public int mistTick;
	public int prevMistTick;
	public float mistFactor;
	public float sunGlare;
	public float prevSunGlare;
	public float rainFactor;
	public float prevRainFactor;
	public int alignmentXBase;
	public int alignmentYBase;
	public int alignmentXCurrent;
	public int alignmentYCurrent;
	public int alignmentXPrev;
	public int alignmentYPrev;
	public boolean firstAlignmentRender = true;
	public boolean wasShowingBannerRepossessMessage;
	public int bannerRepossessDisplayTick;
	public int frostTick;
	public int burnTick;
	public int drunkennessDirection = 1;
	public int newDate;
	public float utumnoCamRoll;
	public boolean inUtumnoReturnPortal;
	public int utumnoReturnX;
	public int utumnoReturnZ;
	public double lastUtumnoReturnY = -1.0D;
	public int prevWightLookTick;
	public int wightLookTick;
	public int prevWightNearTick;
	public int wightNearTick;
	public int prevBalrogNearTick;
	public int balrogNearTick;
	public float balrogFactor;
	public float[] storedLightTable;
	public int storedScrapID;
	public boolean addedClientPoisonEffect;
	public LOTRMusicTrack lastTrack;
	public int musicTrackTick;
	public boolean cancelItemHighlight;
	public ItemStack lastHighlightedItemstack;
	public String highlightedItemstackName;

	public LOTRTickHandlerClient() {
		FMLCommonHandler.instance().bus().register(this);
		MinecraftForge.EVENT_BUS.register(this);
		ambienceTicker = new LOTRAmbience();
		notificationDisplay = new LOTRGuiNotificationDisplay();
		miniquestTracker = new LOTRGuiMiniquestTracker();
	}

	@SubscribeEvent
	public void getItemTooltip(ItemTooltipEvent event) {
		ItemStack itemstack = event.itemStack;
		List<String> tooltip = event.toolTip;
		EntityPlayer entityplayer = event.entityPlayer;
		FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
		List<LOTREnchantment> enchantments = LOTREnchantmentHelper.getEnchantList(itemstack);
		if (!itemstack.hasDisplayName() && !enchantments.isEmpty()) {
			String name = tooltip.get(0);
			name = LOTREnchantmentHelper.getFullEnchantedName(itemstack, name);
			tooltip.set(0, name);
		}
		if (itemstack.getItem() instanceof LOTRSquadrons.SquadronItem) {
			String squadron = LOTRSquadrons.getSquadron(itemstack);
			if (!StringUtils.isNullOrEmpty(squadron)) {
				Collection<String> newTooltip = new ArrayList<>();
				newTooltip.add(tooltip.get(0));
				newTooltip.add(StatCollector.translateToLocalFormatted("item.lotr.generic.squadron", squadron));
				for (int i = 1; i < tooltip.size(); i++) {
					newTooltip.add(tooltip.get(i));
				}
				tooltip.clear();
				tooltip.addAll(newTooltip);
			}
		}
		if (LOTRWeaponStats.isMeleeWeapon(itemstack)) {
			int dmgIndex = -1;
			for (int i = 0; i < tooltip.size(); i++) {
				String s = tooltip.get(i);
				if (s.startsWith(EnumChatFormatting.BLUE.toString())) {
					dmgIndex = i;
					break;
				}
			}
			if (dmgIndex >= 0) {
				Collection<String> newTooltip = new ArrayList<>();
				for (int j = 0; j <= dmgIndex - 1; j++) {
					newTooltip.add(tooltip.get(j));
				}
				float meleeDamage = LOTRWeaponStats.getMeleeDamageBonus(itemstack);
				newTooltip.add(EnumChatFormatting.BLUE + StatCollector.translateToLocalFormatted("lotr.weaponstat.meleeDamage", meleeDamage));
				float meleeSpeed = LOTRWeaponStats.getMeleeSpeed(itemstack);
				int pcSpeed = Math.round(meleeSpeed * 100.0F);
				newTooltip.add(EnumChatFormatting.BLUE + StatCollector.translateToLocalFormatted("lotr.weaponstat.meleeSpeed", pcSpeed));
				float reach = LOTRWeaponStats.getMeleeReachFactor(itemstack);
				int pcReach = Math.round(reach * 100.0F);
				newTooltip.add(EnumChatFormatting.BLUE + StatCollector.translateToLocalFormatted("lotr.weaponstat.reach", pcReach));
				int kb = LOTRWeaponStats.getTotalKnockback(itemstack);
				if (kb > 0) {
					newTooltip.add(EnumChatFormatting.BLUE + StatCollector.translateToLocalFormatted("lotr.weaponstat.kb", kb));
				}
				for (int k = dmgIndex + 1; k < tooltip.size(); k++) {
					newTooltip.add(tooltip.get(k));
				}
				tooltip.clear();
				tooltip.addAll(newTooltip);
			}
		}
		if (LOTRWeaponStats.isRangedWeapon(itemstack)) {
			tooltip.add("");
			float drawSpeed = LOTRWeaponStats.getRangedSpeed(itemstack);
			if (drawSpeed > 0.0F) {
				int pcSpeed = Math.round(drawSpeed * 100.0F);
				tooltip.add(EnumChatFormatting.DARK_GREEN + StatCollector.translateToLocalFormatted("lotr.weaponstat.rangedSpeed", pcSpeed));
			}
			float damage = LOTRWeaponStats.getRangedDamageFactor(itemstack, false);
			if (damage > 0.0F) {
				int pcDamage = Math.round(damage * 100.0F);
				tooltip.add(EnumChatFormatting.DARK_GREEN + StatCollector.translateToLocalFormatted("lotr.weaponstat.rangedDamage", pcDamage));
				if (itemstack.getItem() instanceof net.minecraft.item.ItemBow || itemstack.getItem() instanceof LOTRItemCrossbow) {
					float range = LOTRWeaponStats.getRangedDamageFactor(itemstack, true);
					int pcRange = Math.round(range * 100.0F);
					tooltip.add(EnumChatFormatting.DARK_GREEN + StatCollector.translateToLocalFormatted("lotr.weaponstat.range", pcRange));
				}
			}
			int kb = LOTRWeaponStats.getRangedKnockback(itemstack);
			if (kb > 0) {
				tooltip.add(EnumChatFormatting.DARK_GREEN + StatCollector.translateToLocalFormatted("lotr.weaponstat.kb", kb));
			}
		}
		if (LOTRWeaponStats.isPoisoned(itemstack)) {
			tooltip.add(EnumChatFormatting.DARK_GREEN + StatCollector.translateToLocalFormatted("lotr.weaponstat.poison"));
		}
		int armorProtect = LOTRWeaponStats.getArmorProtection(itemstack);
		if (armorProtect > 0) {
			tooltip.add("");
			int pcProtection = Math.round(armorProtect / 25.0F * 100.0F);
			tooltip.add(EnumChatFormatting.BLUE + StatCollector.translateToLocalFormatted("lotr.weaponstat.protection", armorProtect, pcProtection));
		}
		if (!enchantments.isEmpty()) {
			tooltip.add("");
			Collection<String> enchGood = new ArrayList<>();
			Collection<String> enchBad = new ArrayList<>();
			for (LOTREnchantment ench : enchantments) {
				String enchDesc = ench.getNamedFormattedDescription(itemstack);
				if (ench.isBeneficial()) {
					enchGood.add(enchDesc);
					continue;
				}
				enchBad.add(enchDesc);
			}
			tooltip.addAll(enchGood);
			tooltip.addAll(enchBad);
		}
		if (LOTRPoisonedDrinks.isDrinkPoisoned(itemstack) && LOTRPoisonedDrinks.canPlayerSeePoisoned(itemstack, entityplayer)) {
			tooltip.add(EnumChatFormatting.DARK_GREEN + StatCollector.translateToLocal("item.lotr.drink.poison"));
		}
		String currentOwner = LOTRItemOwnership.getCurrentOwner(itemstack);
		if (currentOwner != null) {
			tooltip.add("");
			String ownerFormatted = StatCollector.translateToLocalFormatted("item.lotr.generic.currentOwner", currentOwner);
			List<String> ownerLines = fontRenderer.listFormattedStringToWidth(ownerFormatted, 150);
			for (int i = 0; i < ownerLines.size(); i++) {
				String line = ownerLines.get(i);
				if (i > 0) {
					line = "  " + line;
				}
				tooltip.add(line);
			}
		}
		List<String> previousOwners = LOTRItemOwnership.getPreviousOwners(itemstack);
		if (!previousOwners.isEmpty()) {
			tooltip.add("");
			List<String> ownerLines = new ArrayList<>();
			if (previousOwners.size() == 1) {
				String ownerFormatted = EnumChatFormatting.ITALIC + StatCollector.translateToLocalFormatted("item.lotr.generic.previousOwner", previousOwners.get(0));
				ownerLines.addAll(fontRenderer.listFormattedStringToWidth(ownerFormatted, 150));
			} else {
				String beginList = EnumChatFormatting.ITALIC + StatCollector.translateToLocal("item.lotr.generic.previousOwnerList");
				ownerLines.add(beginList);
				for (String previousOwner : previousOwners) {
					previousOwner = EnumChatFormatting.ITALIC + previousOwner;
					ownerLines.addAll(fontRenderer.listFormattedStringToWidth(previousOwner, 150));
				}
			}
			for (int i = 0; i < ownerLines.size(); i++) {
				String line = ownerLines.get(i);
				if (i > 0) {
					line = "  " + line;
				}
				tooltip.add(line);
			}
		}
		if (IPickpocketable.Helper.isPickpocketed(itemstack)) {
			tooltip.add("");
			String owner = IPickpocketable.Helper.getOwner(itemstack);
			owner = StatCollector.translateToLocalFormatted("item.lotr.generic.stolen", owner);
			String wanter = IPickpocketable.Helper.getWanter(itemstack);
			wanter = StatCollector.translateToLocalFormatted("item.lotr.generic.stolenWanted", wanter);
			List<String> robbedLines = new ArrayList<>(fontRenderer.listFormattedStringToWidth(owner, 200));
			robbedLines.addAll(fontRenderer.listFormattedStringToWidth(wanter, 200));
			for (int i = 0; i < robbedLines.size(); i++) {
				String line = robbedLines.get(i);
				if (i > 0) {
					line = "  " + line;
				}
				tooltip.add(line);
			}
		}
		if (itemstack.getItem() == Item.getItemFromBlock(Blocks.monster_egg)) {
			tooltip.set(0, EnumChatFormatting.RED + tooltip.get(0));
		}
		if (LOTRMod.isAprilFools()) {
			String name = tooltip.get(0);
			name = name.replace("kebab", "gyros");
			name = name.replace("Kebab", "Gyros");
			tooltip.set(0, name);
		}
	}

	public float getWightLookFactor() {
		float f = prevWightLookTick + (wightLookTick - prevWightLookTick) * renderTick;
		f /= 100.0F;
		return f;
	}

	public boolean isGamePaused(Minecraft mc) {
		return mc.isSingleplayer() && mc.currentScreen != null && mc.currentScreen.doesGuiPauseGame() && !mc.getIntegratedServer().getPublic();
	}

	public void onBurnDamage() {
		burnTick = 40;
	}

	@SubscribeEvent
	public void onClientTick(TickEvent.ClientTickEvent event) {
		Minecraft minecraft = Minecraft.getMinecraft();
		EntityClientPlayerMP entityplayer = minecraft.thePlayer;
		WorldClient world = minecraft.theWorld;
		if (event.phase == TickEvent.Phase.START) {
			clientTick++;
			if (LOTRConfig.fixRenderDistance && !FMLClientHandler.instance().hasOptifine()) {
				GameSettings gs = Minecraft.getMinecraft().gameSettings;
				int renderDistance = gs.renderDistanceChunks;
				if (renderDistance > 16) {
					renderDistance = 16;
					gs.renderDistanceChunks = renderDistance;
					gs.saveOptions();
					LOTRLog.logger.info("LOTR: Render distance was above 16 - set to 16 to prevent a vanilla crash");
				}
			}
			if (minecraft.entityRenderer != null && !(minecraft.entityRenderer instanceof LOTREntityRenderer)) {
				minecraft.entityRenderer = new LOTREntityRenderer(minecraft, minecraft.getResourceManager());
				((IReloadableResourceManager) minecraft.getResourceManager()).registerReloadListener(minecraft.entityRenderer);
				FMLLog.info("LOTR: Successfully replaced entityrenderer");
			}
		}
		if (event.phase == TickEvent.Phase.END) {
			LOTRTileEntityMobSpawnerRenderer.onClientTick();
			if (minecraft.currentScreen == null) {
				lastGuiOpen = null;
			}
			if (FMLClientHandler.instance().hasOptifine()) {
				int optifineSetting = 0;
				try {
					Object field = GameSettings.class.getField("ofTrees").get(minecraft.gameSettings);
					if (field instanceof Integer) {
						optifineSetting = (Integer) field;
					}
				} catch (Exception exception) {
				}
				boolean fancyGraphics = optifineSetting == 0 ? minecraft.gameSettings.fancyGraphics : optifineSetting == 2;
				LOTRBlockLeavesBase.setAllGraphicsLevels(fancyGraphics);
			} else {
				LOTRBlockLeavesBase.setAllGraphicsLevels(minecraft.gameSettings.fancyGraphics);
			}
			if (entityplayer != null && world != null) {
				if (LOTRConfig.checkUpdates) {
					LOTRVersionChecker.checkForUpdates();
				}
				if (!isGamePaused(minecraft)) {
					miniquestTracker.update(minecraft, entityplayer);
					LOTRAlignmentTicker.updateAll(entityplayer, false);
					if (alignDrainTick > 0) {
						alignDrainTick--;
						if (alignDrainTick <= 0) {
							alignDrainNum = 0;
						}
					}
					watchedInvasion.tick();
					if (LOTRItemBanner.hasChoiceToKeepOriginalOwner(entityplayer)) {
						boolean showBannerRespossessMessage = LOTRItemBanner.isHoldingBannerWithExistingProtection(entityplayer);
						if (showBannerRespossessMessage && !wasShowingBannerRepossessMessage) {
							bannerRepossessDisplayTick = 60;
						} else {
							bannerRepossessDisplayTick--;
						}
						wasShowingBannerRepossessMessage = showBannerRespossessMessage;
					} else {
						bannerRepossessDisplayTick = 0;
						wasShowingBannerRepossessMessage = false;
					}
					EntityLivingBase viewer = minecraft.renderViewEntity;
					int i = MathHelper.floor_double(viewer.posX);
					int j = MathHelper.floor_double(viewer.boundingBox.minY);
					int k = MathHelper.floor_double(viewer.posZ);
					BiomeGenBase biome = world.getBiomeGenForCoords(i, k);
					LOTRBiome.updateWaterColor(i, j, k);
					LOTRBiomeGenUtumno.updateFogColor(i, j, k);
					LOTRCloudRenderer.updateClouds(world);
					if (LOTRConfig.aurora) {
						LOTRRenderNorthernLights.update(viewer);
					}
					LOTRSpeechClient.update();
					LOTRKeyHandler.update();
					LOTRAttackTiming.update();
					prevMistTick = mistTick;
					if (viewer.posY >= 72.0D && biome instanceof lotr.common.world.biome.LOTRBiomeGenMistyMountains && biome != LOTRBiome.mistyMountainsFoothills && world.canBlockSeeTheSky(i, j, k) && world.getSavedLightValue(EnumSkyBlock.Block, i, j, k) < 7) {
						if (mistTick < 80) {
							mistTick++;
						}
					} else if (mistTick > 0) {
						mistTick--;
					}
					if (frostTick > 0) {
						frostTick--;
					}
					if (burnTick > 0) {
						burnTick--;
					}
					prevWightLookTick = wightLookTick;
					if (anyWightsViewed) {
						if (wightLookTick < 100) {
							wightLookTick++;
						}
					} else if (wightLookTick > 0) {
						wightLookTick--;
					}
					prevWightNearTick = wightNearTick;
					double wightRange = 32.0D;
					List nearbyWights = world.getEntitiesWithinAABB(LOTREntityBarrowWight.class, viewer.boundingBox.expand(wightRange, wightRange, wightRange));
					if (!nearbyWights.isEmpty()) {
						if (wightNearTick < 100) {
							wightNearTick++;
						}
					} else if (wightNearTick > 0) {
						wightNearTick--;
					}
					prevBalrogNearTick = balrogNearTick;
					double balrogRange = 24.0D;
					List nearbyBalrogs = world.getEntitiesWithinAABB(LOTREntityBalrog.class, viewer.boundingBox.expand(balrogRange, balrogRange, balrogRange));
					if (!nearbyBalrogs.isEmpty()) {
						if (balrogNearTick < 100) {
							balrogNearTick++;
						}
					} else if (balrogNearTick > 0) {
						balrogNearTick--;
					}
					if (LOTRConfig.enableSunFlare && world.provider instanceof LOTRWorldProvider && !world.provider.hasNoSky) {
						prevSunGlare = sunGlare;
						MovingObjectPosition look = viewer.rayTrace(10000.0D, renderTick);
						boolean lookingAtSky = look == null || look.typeOfHit == MovingObjectPosition.MovingObjectType.MISS;
						boolean biomeHasSun = true;
						if (biome instanceof LOTRBiome) {
							biomeHasSun = ((LOTRBiome) biome).hasSky();
						}
						float sunPitch = world.getCelestialAngle(renderTick) * 360.0F - 90.0F;
						float sunYaw = 90.0F;
						float yc = MathHelper.cos((float) Math.toRadians((-sunYaw - 180.0F)));
						float ys = MathHelper.sin((float) Math.toRadians((-sunYaw - 180.0F)));
						float pc = -MathHelper.cos((float) Math.toRadians(-sunPitch));
						float ps = MathHelper.sin((float) Math.toRadians(-sunPitch));
						Vec3 sunVec = Vec3.createVectorHelper((ys * pc), ps, (yc * pc));
						Vec3 lookVec = viewer.getLook(renderTick);
						double cos = lookVec.dotProduct(sunVec) / lookVec.lengthVector() * sunVec.lengthVector();
						float cosThreshold = 0.95F;
						float cQ = ((float) cos - cosThreshold) / (1.0F - cosThreshold);
						cQ = Math.max(cQ, 0.0F);
						float brightness = world.getSunBrightness(renderTick);
						float brightnessThreshold = 0.7F;
						float bQ = (brightness - brightnessThreshold) / (1.0F - brightnessThreshold);
						bQ = Math.max(bQ, 0.0F);
						float maxGlare = cQ * bQ;
						if (maxGlare > 0.0F && lookingAtSky && !world.isRaining() && biomeHasSun) {
							if (sunGlare < maxGlare) {
								sunGlare += 0.1F * maxGlare;
								sunGlare = Math.min(sunGlare, maxGlare);
							} else if (sunGlare > maxGlare) {
								sunGlare -= 0.02F;
								sunGlare = Math.max(sunGlare, maxGlare);
							}
						} else {
							if (sunGlare > 0.0F) {
								sunGlare -= 0.02F;
							}
							sunGlare = Math.max(sunGlare, 0.0F);
						}
					} else {
						prevSunGlare = sunGlare = 0.0F;
					}
					if (LOTRConfig.newWeather) {
						prevRainFactor = rainFactor;
						if (world.isRaining()) {
							if (rainFactor < 1.0F) {
								rainFactor += 0.008333334F;
								rainFactor = Math.min(rainFactor, 1.0F);
							}
						} else if (rainFactor > 0.0F) {
							rainFactor -= 0.0016666667F;
							rainFactor = Math.max(rainFactor, 0.0F);
						}
					} else {
						prevRainFactor = rainFactor = 0.0F;
					}
					if (minecraft.gameSettings.particleSetting < 2) {
						spawnEnvironmentFX(entityplayer, world);
					}
					LOTRClientProxy.customEffectRenderer.updateEffects();
					if (minecraft.renderViewEntity.isPotionActive(Potion.confusion.id)) {
						float drunkenness = minecraft.renderViewEntity.getActivePotionEffect(Potion.confusion).getDuration();
						drunkenness /= 20.0F;
						if (drunkenness > 100.0F) {
							drunkenness = 100.0F;
						}
						minecraft.renderViewEntity.rotationYaw += drunkennessDirection * drunkenness / 20.0F;
						minecraft.renderViewEntity.rotationPitch += MathHelper.cos(minecraft.renderViewEntity.ticksExisted / 10.0F) * drunkenness / 20.0F;
						if (world.rand.nextInt(100) == 0) {
							drunkennessDirection *= -1;
						}
					}
					if (LOTRDimension.getCurrentDimension(world) == LOTRDimension.UTUMNO) {
						if (inUtumnoReturnPortal) {
							if (utumnoCamRoll < 180.0F) {
								utumnoCamRoll += 5.0F;
								utumnoCamRoll = Math.min(utumnoCamRoll, 180.0F);
								LOTRReflectionClient.setCameraRoll(minecraft.entityRenderer, utumnoCamRoll);
							}
						} else if (utumnoCamRoll > 0.0F) {
							utumnoCamRoll -= 5.0F;
							utumnoCamRoll = Math.max(utumnoCamRoll, 0.0F);
							LOTRReflectionClient.setCameraRoll(minecraft.entityRenderer, utumnoCamRoll);
						}
					} else if (utumnoCamRoll != 0.0F) {
						utumnoCamRoll = 0.0F;
						LOTRReflectionClient.setCameraRoll(minecraft.entityRenderer, utumnoCamRoll);
					}
					if (newDate > 0) {
						newDate--;
					}
					ambienceTicker.updateAmbience(world, entityplayer);
					if (scrapTraderMisbehaveTick > 0) {
						scrapTraderMisbehaveTick--;
						if (scrapTraderMisbehaveTick <= 0) {
							world.provider.lightBrightnessTable = Arrays.copyOf(storedLightTable, storedLightTable.length);
							Entity scrap = world.getEntityByID(storedScrapID);
							if (scrap != null) {
								scrap.ignoreFrustumCheck = false;
							}
						}
					} else {
						MovingObjectPosition target = minecraft.objectMouseOver;
						if (target != null && target.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY && target.entityHit instanceof LOTREntityScrapTrader) {
							LOTREntityScrapTrader scrap = (LOTREntityScrapTrader) target.entityHit;
							if (minecraft.currentScreen == null && world.rand.nextInt(50000) == 0) {
								scrapTraderMisbehaveTick = 400;
								scrap.ignoreFrustumCheck = true;
								storedScrapID = scrap.getEntityId();
								float[] lightTable = world.provider.lightBrightnessTable;
								storedLightTable = Arrays.copyOf(lightTable, lightTable.length);
								Arrays.fill(lightTable, 1.0E-7F);
							}
						}
					}
				}
				if ((entityplayer.dimension == 0 || entityplayer.dimension == LOTRDimension.MIDDLE_EARTH.dimensionID) && playersInPortals.containsKey(entityplayer)) {
					List<LOTREntityPortal> portals = world.getEntitiesWithinAABB(LOTREntityPortal.class, entityplayer.boundingBox.expand(8.0D, 8.0D, 8.0D));
					boolean inPortal = false;
					int i;
					for (i = 0; i < portals.size(); i++) {
						LOTREntityPortal portal = portals.get(i);
						if (portal.boundingBox.intersectsWith(entityplayer.boundingBox)) {
							inPortal = true;
							break;
						}
					}
					if (inPortal) {
						i = (Integer) playersInPortals.get(entityplayer);
						i++;
						playersInPortals.put(entityplayer, i);
						if (i >= 100) {
							minecraft.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("portal.trigger"), world.rand.nextFloat() * 0.4F + 0.8F));
							playersInPortals.remove(entityplayer);
						}
					} else {
						playersInPortals.remove(entityplayer);
					}
				}
				updatePlayerInPortal(entityplayer, playersInElvenPortals, LOTRMod.elvenPortal);
				updatePlayerInPortal(entityplayer, playersInMorgulPortals, LOTRMod.morgulPortal);
				if (inUtumnoReturnPortal) {
					entityplayer.setPosition(utumnoReturnX + 0.5D, entityplayer.posY, utumnoReturnZ + 0.5D);
					if (lastUtumnoReturnY >= 0.0D && entityplayer.posY < lastUtumnoReturnY) {
						entityplayer.setPosition(entityplayer.posX, lastUtumnoReturnY, entityplayer.posZ);
					}
					lastUtumnoReturnY = entityplayer.posY;
				} else {
					lastUtumnoReturnY = -1.0D;
				}
				inUtumnoReturnPortal = false;
			}
			LOTRClientProxy.musicHandler.update();
			if (LOTRConfig.displayMusicTrack) {
				LOTRMusicTrack nowPlaying = LOTRMusicTicker.currentTrack;
				if (nowPlaying != lastTrack) {
					lastTrack = nowPlaying;
					musicTrackTick = 200;
				}
				if (lastTrack != null && musicTrackTick > 0) {
					musicTrackTick--;
				}
			}
			GuiScreen guiscreen = minecraft.currentScreen;
			if (guiscreen != null) {
				if (guiscreen instanceof net.minecraft.client.gui.GuiMainMenu && !(lastGuiOpen instanceof net.minecraft.client.gui.GuiMainMenu)) {
					LOTRLevelData.needsLoad = true;
					LOTRTime.needsLoad = true;
					LOTRFellowshipData.needsLoad = true;
					LOTRFactionBounties.needsLoad = true;
					LOTRFactionRelations.needsLoad = true;
					LOTRDate.resetWorldTimeInMenu();
					LOTRConquestGrid.needsLoad = true;
					LOTRSpeechClient.clearAll();
					LOTRAttackTiming.reset();
					LOTRGuiMenu.resetLastMenuScreen();
					LOTRGuiMap.clearPlayerLocations();
					LOTRCloudRenderer.resetClouds();
					firstAlignmentRender = true;
					watchedInvasion.clear();
				}
				lastGuiOpen = guiscreen;
			}
			anyWightsViewed = false;
		}
	}

	@SubscribeEvent
	public void onFogColors(EntityViewRenderEvent.FogColors event) {
		Minecraft mc = Minecraft.getMinecraft();
		WorldClient worldClient = mc.theWorld;
		WorldProvider provider = worldClient.provider;
		if (provider instanceof LOTRWorldProvider) {
			float[] rgb = {event.red, event.green, event.blue};
			rgb = ((LOTRWorldProvider) provider).handleFinalFogColors(event.entity, event.renderPartialTicks, rgb);
			event.red = rgb[0];
			event.green = rgb[1];
			event.blue = rgb[2];
		}
		if (balrogFactor > 0.0F) {
			int shadowColor = 1114112;
			float[] rgb = {event.red, event.green, event.blue};
			rgb = LOTRColorUtil.lerpColors(rgb, shadowColor, balrogFactor);
			event.red = rgb[0];
			event.green = rgb[1];
			event.blue = rgb[2];
		}
	}

	@SubscribeEvent
	public void onFOVUpdate(FOVUpdateEvent event) {
		EntityPlayerSP entityplayer = event.entity;
		float fov = event.newfov;
		ItemStack itemstack = entityplayer.getHeldItem();
		Item item = itemstack == null ? null : itemstack.getItem();
		float usage = -1.0F;
		if (entityplayer.isUsingItem()) {
			float maxDrawTime = 0.0F;
			if (item instanceof LOTRItemBow) {
				maxDrawTime = ((LOTRItemBow) item).getMaxDrawTime();
			} else if (item instanceof LOTRItemCrossbow) {
				maxDrawTime = ((LOTRItemCrossbow) item).getMaxDrawTime();
			} else if (item instanceof LOTRItemSpear) {
				maxDrawTime = ((LOTRItemSpear) item).getMaxDrawTime();
			} else if (item instanceof LOTRItemBlowgun) {
				maxDrawTime = ((LOTRItemBlowgun) item).getMaxDrawTime();
			}
			if (maxDrawTime > 0.0F) {
				int i = entityplayer.getItemInUseDuration();
				usage = i / maxDrawTime;
				if (usage > 1.0F) {
					usage = 1.0F;
				} else {
					usage *= usage;
				}
			}
		}
		if (LOTRItemCrossbow.isLoaded(itemstack)) {
			usage = 1.0F;
		}
		if (usage >= 0.0F) {
			fov *= 1.0F - usage * 0.15F;
		}
		event.newfov = fov;
	}

	public void onFrostDamage() {
		frostTick = 80;
	}

	@SubscribeEvent
	public void onPlayerTick(TickEvent.PlayerTickEvent event) {
		EntityPlayer player = event.player;
		if (event.phase == TickEvent.Phase.END && player instanceof EntityClientPlayerMP) {
			EntityClientPlayerMP clientPlayer = (EntityClientPlayerMP) player;
			if (clientPlayer.isRiding()) {
				LOTRMountFunctions.sendControlToServer(clientPlayer);
			}
		}
	}

	@SubscribeEvent
	public void onPostRenderGameOverlay(RenderGameOverlayEvent.Post event) {
		Minecraft mc = Minecraft.getMinecraft();
		WorldClient worldClient = mc.theWorld;
		EntityClientPlayerMP entityClientPlayerMP = mc.thePlayer;
		GuiIngame guiIngame = mc.ingameGUI;
		if (worldClient != null && entityClientPlayerMP != null) {
			if (event.type == RenderGameOverlayEvent.ElementType.ALL && lastHighlightedItemstack != null) {
				if (highlightedItemstackName != null) {
					lastHighlightedItemstack.setStackDisplayName(highlightedItemstackName);
				} else {
					lastHighlightedItemstack.func_135074_t();
				}
				lastHighlightedItemstack = null;
				highlightedItemstackName = null;
			}
			if (event.type == RenderGameOverlayEvent.ElementType.BOSSHEALTH && watchedInvasion.isActive()) {
				GL11.glEnable(3042);
				FontRenderer fr = mc.fontRenderer;
				ScaledResolution scaledresolution = event.resolution;
				int width = scaledresolution.getScaledWidth();
				int barWidth = 182;
				int remainingWidth = (int) (watchedInvasion.getHealth() * (barWidth - 2));
				int barHeight = 5;
				int barX = width / 2 - barWidth / 2;
				int barY = 12;
				if (isBossActive()) {
					barY += 20;
				}
				mc.getTextureManager().bindTexture(LOTRClientProxy.alignmentTexture);
				guiIngame.drawTexturedModalRect(barX, barY, 64, 64, barWidth, barHeight);
				if (remainingWidth > 0) {
					float[] rgb = watchedInvasion.getRGB();
					GL11.glColor4f(rgb[0], rgb[1], rgb[2], 1.0F);
					guiIngame.drawTexturedModalRect(barX + 1, barY + 1, 65, 70, remainingWidth, barHeight - 2);
				}
				String s = watchedInvasion.getTitle();
				fr.drawStringWithShadow(s, width / 2 - fr.getStringWidth(s) / 2, barY - 10, 16777215);
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				mc.getTextureManager().bindTexture(Gui.icons);
				GL11.glDisable(3042);
			}
			if (event.type == RenderGameOverlayEvent.ElementType.HEALTH && addedClientPoisonEffect) {
				entityClientPlayerMP.removePotionEffectClient(Potion.poison.id);
				addedClientPoisonEffect = false;
			}
			if (event.type == RenderGameOverlayEvent.ElementType.TEXT && bannerRepossessDisplayTick > 0) {
				String text = StatCollector.translateToLocalFormatted("item.lotr.banner.toggleRepossess", GameSettings.getKeyDisplayString(mc.gameSettings.keyBindSneak.getKeyCode()));
				int fadeAtTick = 10;
				int opacity = (int) (bannerRepossessDisplayTick * 255.0F / fadeAtTick);
				opacity = Math.min(opacity, 255);
				if (opacity > 0) {
					ScaledResolution scaledresolution = event.resolution;
					int width = scaledresolution.getScaledWidth();
					int height = scaledresolution.getScaledHeight();
					int y = height - 59;
					y -= 12;
					if (!mc.playerController.shouldDrawHUD()) {
						y += 14;
					}
					GL11.glPushMatrix();
					GL11.glEnable(3042);
					OpenGlHelper.glBlendFunc(770, 771, 1, 0);
					FontRenderer fr = mc.fontRenderer;
					int x = (width - fr.getStringWidth(text)) / 2;
					fr.drawString(text, x, y, 0xFFFFFF | opacity << 24);
					GL11.glDisable(3042);
					GL11.glPopMatrix();
				}
			}
		}
	}

	@SubscribeEvent
	public void onPreRenderGameOverlay(RenderGameOverlayEvent.Pre event) {
		Minecraft mc = Minecraft.getMinecraft();
		WorldClient worldClient = mc.theWorld;
		EntityClientPlayerMP entityClientPlayerMP = mc.thePlayer;
		float partialTicks = event.partialTicks;
		GuiIngame guiIngame = mc.ingameGUI;
		if (worldClient != null && entityClientPlayerMP != null) {
			if (event.type == RenderGameOverlayEvent.ElementType.ALL) {
				mc.theWorld.theProfiler.startSection("lotr_fixHighlightedItemName");
				ItemStack itemstack = LOTRReflectionClient.getHighlightedItemStack(guiIngame);
				if (itemstack != null && !itemstack.hasDisplayName()) {
					List<LOTREnchantment> enchants = LOTREnchantmentHelper.getEnchantList(itemstack);
					if (!enchants.isEmpty()) {
						lastHighlightedItemstack = itemstack;
						highlightedItemstackName = itemstack.hasDisplayName() ? itemstack.getDisplayName() : null;
						itemstack.setStackDisplayName(LOTREnchantmentHelper.getFullEnchantedName(itemstack, itemstack.getDisplayName()));
					}
				}
				mc.theWorld.theProfiler.endSection();
			}
			if (event.type == RenderGameOverlayEvent.ElementType.HELMET) {
				if (sunGlare > 0.0F && mc.gameSettings.thirdPersonView == 0) {
					float brightness = prevSunGlare + (sunGlare - prevSunGlare) * partialTicks;
					brightness *= 1.0F;
					renderOverlay(null, brightness, mc, null);
				}
				if (playersInPortals.containsKey(entityClientPlayerMP)) {
					int i = (Integer) playersInPortals.get(entityClientPlayerMP);
					if (i > 0) {
						renderOverlay(null, 0.1F + i / 100.0F * 0.6F, mc, portalOverlay);
					}
				}
				if (playersInElvenPortals.containsKey(entityClientPlayerMP)) {
					int i = (Integer) playersInElvenPortals.get(entityClientPlayerMP);
					if (i > 0) {
						renderOverlay(null, 0.1F + (float) i / entityClientPlayerMP.getMaxInPortalTime() * 0.6F, mc, elvenPortalOverlay);
					}
				}
				if (playersInMorgulPortals.containsKey(entityClientPlayerMP)) {
					int i = (Integer) playersInMorgulPortals.get(entityClientPlayerMP);
					if (i > 0) {
						renderOverlay(null, 0.1F + (float) i / entityClientPlayerMP.getMaxInPortalTime() * 0.6F, mc, morgulPortalOverlay);
					}
				}
				if (LOTRConfig.enableMistyMountainsMist) {
					float mistTickF = prevMistTick + (mistTick - prevMistTick) * partialTicks;
					mistTickF /= 80.0F;
					float mistFactorY = (float) entityClientPlayerMP.posY / 256.0F;
					mistFactor = mistTickF * mistFactorY;
					if (mistFactor > 0.0F) {
						renderOverlay(null, mistFactor * 0.75F, mc, mistOverlay);
					}
				} else {
					mistFactor = 0.0F;
				}
				if (frostTick > 0) {
					float frostAlpha = frostTick / 80.0F;
					frostAlpha *= 0.9F;
					float frostAlphaEdge = (float) Math.sqrt(frostAlpha);
					renderOverlayWithVerticalGradients(frostRGBEdge, frostRGBMiddle, frostAlphaEdge, frostAlpha, mc);
					renderOverlay(null, frostAlpha * 0.6F, mc, frostOverlay);
				}
				if (burnTick > 0) {
					renderOverlay(null, burnTick / 40.0F * 0.6F, mc, burnOverlay);
				}
				if (wightLookTick > 0) {
					renderOverlay(null, wightLookTick / 100.0F * 0.95F, mc, wightOverlay);
				}
			}
			if (event.type == RenderGameOverlayEvent.ElementType.HOTBAR) {
				if (LOTRConfig.meleeAttackMeter) {
					LOTRAttackTiming.renderAttackMeter(event.resolution, partialTicks);
				}
				if (entityClientPlayerMP.ridingEntity instanceof LOTREntitySpiderBase) {
					LOTREntitySpiderBase spider = (LOTREntitySpiderBase) entityClientPlayerMP.ridingEntity;
					if (spider.shouldRenderClimbingMeter()) {
						mc.getTextureManager().bindTexture(Gui.icons);
						GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
						GL11.glDisable(3042);
						mc.mcProfiler.startSection("spiderClimb");
						ScaledResolution resolution = event.resolution;
						int width = resolution.getScaledWidth();
						int height = resolution.getScaledHeight();
						float charge = spider.getClimbFractionRemaining();
						int x = width / 2 - 91;
						int filled = (int) (charge * 183.0F);
						int top = height - 32 + 3;
						guiIngame.drawTexturedModalRect(x, top, 0, 84, 182, 5);
						if (filled > 0) {
							guiIngame.drawTexturedModalRect(x, top, 0, 89, filled, 5);
						}
						GL11.glEnable(3042);
						mc.mcProfiler.endSection();
						GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
					}
				}
			}
			if (event.type == RenderGameOverlayEvent.ElementType.HEALTH && entityClientPlayerMP.isPotionActive(LOTRPoisonedDrinks.killingPoison) && !entityClientPlayerMP.isPotionActive(Potion.poison)) {
				entityClientPlayerMP.addPotionEffect(new PotionEffect(Potion.poison.id, 20));
				addedClientPoisonEffect = true;
			}
			boolean enchantingDisabled = !LOTRLevelData.clientside_thisServer_enchanting && worldClient.provider instanceof LOTRWorldProvider;
			if (event.type == RenderGameOverlayEvent.ElementType.EXPERIENCE && enchantingDisabled) {
				event.setCanceled(true);
				return;
			}
			if (event.type == RenderGameOverlayEvent.ElementType.ALL && enchantingDisabled && entityClientPlayerMP.ridingEntity == null) {
				GuiIngameForge.left_height -= 6;
				GuiIngameForge.right_height -= 6;
			}
			if (event.type == RenderGameOverlayEvent.ElementType.ARMOR) {
				event.setCanceled(true);
				ScaledResolution resolution = event.resolution;
				int width = resolution.getScaledWidth();
				int height = resolution.getScaledHeight();
				mc.mcProfiler.startSection("armor");
				GL11.glEnable(3042);
				int left = width / 2 - 91;
				int top = height - GuiIngameForge.left_height;
				int level = LOTRWeaponStats.getTotalArmorValue(mc.thePlayer);
				if (level > 0) {
					for (int i = 1; i < 20; i += 2) {
						if (i < level) {
							guiIngame.drawTexturedModalRect(left, top, 34, 9, 9, 9);
						} else if (i == level) {
							guiIngame.drawTexturedModalRect(left, top, 25, 9, 9, 9);
						} else {
							guiIngame.drawTexturedModalRect(left, top, 16, 9, 9, 9);
						}
						left += 8;
					}
				}
				GuiIngameForge.left_height += 10;
				GL11.glDisable(3042);
				mc.mcProfiler.endSection();
			}
		}
	}

	@SubscribeEvent
	public void onRenderDebugText(RenderGameOverlayEvent.Text event) {
		Minecraft mc = Minecraft.getMinecraft();
		if (mc.gameSettings.showDebugInfo && mc.theWorld != null && mc.thePlayer != null && mc.theWorld.getWorldChunkManager() instanceof LOTRWorldChunkManager) {
			mc.theWorld.theProfiler.startSection("lotrBiomeDisplay");
			LOTRWorldChunkManager chunkManager = (LOTRWorldChunkManager) mc.theWorld.getWorldChunkManager();
			int i = MathHelper.floor_double(mc.thePlayer.posX);
			int j = MathHelper.floor_double(mc.thePlayer.boundingBox.minY);
			int k = MathHelper.floor_double(mc.thePlayer.posZ);
			LOTRBiome biome = (LOTRBiome) mc.theWorld.getBiomeGenForCoords(i, k);
			LOTRBiomeVariant variant = chunkManager.getBiomeVariantAt(i, k);
			event.left.add(null);
			biome.addBiomeF3Info(event.left, mc.theWorld, variant, i, j, k);
			mc.theWorld.theProfiler.endSection();
		}
	}

	@SubscribeEvent
	public void onRenderFog(EntityViewRenderEvent.RenderFogEvent event) {
		Minecraft mc = Minecraft.getMinecraft();
		EntityLivingBase viewer = event.entity;
		WorldClient worldClient = mc.theWorld;
		WorldProvider provider = worldClient.provider;
		int i = MathHelper.floor_double(viewer.posX);
		int j = MathHelper.floor_double(viewer.boundingBox.minY);
		int k = MathHelper.floor_double(viewer.posZ);
		BiomeGenBase biome = worldClient.getBiomeGenForCoords(i, k);
		float farPlane = event.farPlaneDistance;
		int fogMode = event.fogMode;
		if (provider instanceof LOTRWorldProvider) {
			LOTRBiome lotrbiome = (LOTRBiome) biome;
			float[] fogStartEnd = ((LOTRWorldProvider) provider).modifyFogIntensity(farPlane, fogMode);
			float fogStart = fogStartEnd[0];
			float fogEnd = fogStartEnd[1];
			if (LOTRConfig.newWeather && (lotrbiome.getEnableRain() || lotrbiome.getEnableSnow())) {
				float rain = prevRainFactor + (rainFactor - prevRainFactor) * renderTick;
				if (rain > 0.0F) {
					float rainOpacityStart = 0.95F;
					float rainOpacityEnd = 0.2F;
					fogStart -= fogStart * rain * rainOpacityStart;
					fogEnd -= fogEnd * rain * rainOpacityEnd;
				}
			}
			if (mistFactor > 0.0F) {
				float mistOpacityStart = 0.95F;
				float mistOpacityEnd = 0.7F;
				fogStart -= fogStart * mistFactor * mistOpacityStart;
				fogEnd -= fogEnd * mistFactor * mistOpacityEnd;
			}
			float wightFactor = prevWightNearTick + (wightNearTick - prevWightNearTick) * renderTick;
			wightFactor /= 100.0F;
			if (wightFactor > 0.0F) {
				float wightOpacityStart = 0.97F;
				float wightOpacityEnd = 0.75F;
				fogStart -= fogStart * wightFactor * wightOpacityStart;
				fogEnd -= fogEnd * wightFactor * wightOpacityEnd;
			}
			if (lotrbiome instanceof lotr.common.world.biome.LOTRBiomeGenBarrowDowns) {
				if (wightFactor > 0.0F) {
					int sky0 = lotrbiome.getBaseSkyColorByTemp(i, j, k);
					int sky1 = 9674385;
					int clouds0 = 16777215;
					int clouds1 = 11842740;
					int fog0 = 16777215;
					int fog1 = 10197915;
					lotrbiome.biomeColors.setSky(LOTRColorUtil.lerpColors_I(sky0, sky1, wightFactor));
					lotrbiome.biomeColors.setClouds(LOTRColorUtil.lerpColors_I(clouds0, clouds1, wightFactor));
					lotrbiome.biomeColors.setFog(LOTRColorUtil.lerpColors_I(fog0, fog1, wightFactor));
				} else {
					lotrbiome.biomeColors.resetSky();
					lotrbiome.biomeColors.resetClouds();
					lotrbiome.biomeColors.resetFog();
				}
			}
			balrogFactor = prevBalrogNearTick + (balrogNearTick - prevBalrogNearTick) * renderTick;
			balrogFactor /= 100.0F;
			if (balrogFactor > 0.0F) {
				float balrogOpacityStart = 0.98F;
				float balrogOpacityEnd = 0.75F;
				fogStart -= fogStart * balrogFactor * balrogOpacityStart;
				fogEnd -= fogEnd * balrogFactor * balrogOpacityEnd;
			}
			GL11.glFogf(2915, fogStart);
			GL11.glFogf(2916, fogEnd);
		}
	}

	@SubscribeEvent
	public void onRenderTick(TickEvent.RenderTickEvent event) {
		Minecraft minecraft = Minecraft.getMinecraft();
		EntityClientPlayerMP entityplayer = minecraft.thePlayer;
		WorldClient worldClient = minecraft.theWorld;
		if (event.phase == TickEvent.Phase.START) {
			renderTick = event.renderTickTime;
			if (cancelItemHighlight) {
				GuiIngame guiIngame = minecraft.ingameGUI;
				int highlightTicks = LOTRReflectionClient.getHighlightedItemTicks(guiIngame);
				if (highlightTicks > 0) {
					LOTRReflectionClient.setHighlightedItemTicks(guiIngame, 0);
					cancelItemHighlight = false;
				}
			}
		}
		if (event.phase == TickEvent.Phase.END) {
			if (entityplayer != null && worldClient != null) {
				if ((worldClient.provider instanceof LOTRWorldProvider || LOTRConfig.alwaysShowAlignment) && Minecraft.isGuiEnabled()) {
					alignmentXPrev = alignmentXCurrent;
					alignmentYPrev = alignmentYCurrent;
					alignmentXCurrent = alignmentXBase;
					int yMove = (int) ((alignmentYBase + 20) / 10.0F);
					boolean alignmentOnscreen = (minecraft.currentScreen == null || minecraft.currentScreen instanceof lotr.client.gui.LOTRGuiMessage) && !minecraft.gameSettings.keyBindPlayerList.getIsKeyPressed() && !minecraft.gameSettings.showDebugInfo;
					if (alignmentOnscreen) {
						alignmentYCurrent = Math.min(alignmentYCurrent + yMove, alignmentYBase);
					} else {
						alignmentYCurrent = Math.max(alignmentYCurrent - yMove, -20);
					}
					renderAlignment(minecraft, renderTick);
					if (LOTRConfig.enableOnscreenCompass && minecraft.currentScreen == null && !minecraft.gameSettings.showDebugInfo) {
						GL11.glPushMatrix();
						ScaledResolution resolution = new ScaledResolution(minecraft, minecraft.displayWidth, minecraft.displayHeight);
						int i = resolution.getScaledWidth();
						int compassX = i - 60;
						int compassY = 40;
						GL11.glTranslatef(compassX, compassY, 0.0F);
						float rotation = entityplayer.prevRotationYaw + (entityplayer.rotationYaw - entityplayer.prevRotationYaw) * event.renderTickTime;
						rotation = 180.0F - rotation;
						LOTRModelCompass.compassModel.render(1.0F, rotation);
						GL11.glPopMatrix();
						if (LOTRConfig.compassExtraInfo) {
							GL11.glPushMatrix();
							GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
							float scale = 0.5F;
							float invScale = 1.0F / scale;
							compassX = (int) (compassX * invScale);
							compassY = (int) (compassY * invScale);
							GL11.glScalef(scale, scale, scale);
							String coords = MathHelper.floor_double(entityplayer.posX) + ", " + MathHelper.floor_double(entityplayer.boundingBox.minY) + ", " + MathHelper.floor_double(entityplayer.posZ);
							FontRenderer fontRenderer = minecraft.fontRenderer;
							fontRenderer.drawString(coords, compassX - fontRenderer.getStringWidth(coords) / 2, compassY + 70, 16777215);
							int playerX = MathHelper.floor_double(entityplayer.posX);
							int playerZ = MathHelper.floor_double(entityplayer.posZ);
							if (LOTRClientProxy.doesClientChunkExist(worldClient, playerX, playerZ)) {
								BiomeGenBase biome = worldClient.getBiomeGenForCoords(playerX, playerZ);
								if (biome instanceof LOTRBiome) {
									String biomeName = ((LOTRBiome) biome).getBiomeDisplayName();
									fontRenderer.drawString(biomeName, compassX - fontRenderer.getStringWidth(biomeName) / 2, compassY - 70, 16777215);
								}
							}
							GL11.glPopMatrix();
						}
					}
				}
				if (entityplayer.dimension == LOTRDimension.MIDDLE_EARTH.dimensionID && minecraft.currentScreen == null && newDate > 0) {
					int halfMaxDate = 100;
					float alpha;
					if (newDate > halfMaxDate) {
						alpha = (float) (200 - newDate) / halfMaxDate;
					} else {
						alpha = (float) newDate / halfMaxDate;
					}
					String date = LOTRDate.ShireReckoning.getShireDate().getDateName(true);
					ScaledResolution resolution = new ScaledResolution(minecraft, minecraft.displayWidth, minecraft.displayHeight);
					int i = resolution.getScaledWidth();
					int j = resolution.getScaledHeight();
					float scale = 1.5F;
					float invScale = 1.0F / scale;
					i = (int) (i * invScale);
					j = (int) (j * invScale);
					int x = (i - minecraft.fontRenderer.getStringWidth(date)) / 2;
					int y = (j - minecraft.fontRenderer.FONT_HEIGHT) * 2 / 5;
					GL11.glScalef(scale, scale, scale);
					GL11.glEnable(3042);
					OpenGlHelper.glBlendFunc(770, 771, 1, 0);
					minecraft.fontRenderer.drawString(date, x, y, 16777215 + (LOTRClientProxy.getAlphaInt(alpha) << 24));
					GL11.glDisable(3042);
					GL11.glScalef(invScale, invScale, invScale);
				}
				if (LOTRConfig.displayMusicTrack && minecraft.currentScreen == null && lastTrack != null && musicTrackTick > 0) {
					Collection<String> lines = new ArrayList<>();
					lines.add(StatCollector.translateToLocal("lotr.music.nowPlaying"));
					String title = lastTrack.getTitle();
					lines.add(title);
					if (!lastTrack.getAuthors().isEmpty()) {
						StringBuilder authors = new StringBuilder("(");
						int a = 0;
						for (String auth : lastTrack.getAuthors()) {
							authors.append(auth);
							if (a < lastTrack.getAuthors().size() - 1) {
								authors.append(", ");
							}
							a++;
						}
						authors.append(")");
						lines.add(authors.toString());
					}
					ScaledResolution resolution = new ScaledResolution(minecraft, minecraft.displayWidth, minecraft.displayHeight);
					int w = resolution.getScaledWidth();
					int h = resolution.getScaledHeight();
					int border = 20;
					int x;
					int y = h - border - lines.size() * minecraft.fontRenderer.FONT_HEIGHT;
					float alpha = 1.0F;
					if (musicTrackTick >= 140) {
						alpha = (200 - musicTrackTick) / 60.0F;
					} else if (musicTrackTick <= 60) {
						alpha = musicTrackTick / 60.0F;
					}
					for (String line : lines) {
						x = w - border - minecraft.fontRenderer.getStringWidth(line);
						minecraft.fontRenderer.drawString(line, x, y, 16777215 + (LOTRClientProxy.getAlphaInt(alpha) << 24));
						y += minecraft.fontRenderer.FONT_HEIGHT;
					}
				}
			}
			notificationDisplay.updateWindow();
			if (LOTRConfig.enableQuestTracker && minecraft.currentScreen == null && !minecraft.gameSettings.showDebugInfo) {
				miniquestTracker.drawTracker(minecraft, entityplayer);
			}
		}
	}

	@SubscribeEvent
	public void onRenderWorldLast(RenderWorldLastEvent event) {
		Minecraft mc = Minecraft.getMinecraft();
		float f = event.partialTicks;
		if (LOTRConfig.aurora && LOTRDimension.getCurrentDimension(mc.theWorld) == LOTRDimension.MIDDLE_EARTH) {
			LOTRRenderNorthernLights.render(mc, mc.theWorld, f);
		}
		mc.entityRenderer.enableLightmap(f);
		RenderHelper.disableStandardItemLighting();
		LOTRClientProxy.customEffectRenderer.renderParticles(mc.renderViewEntity, f);
		mc.entityRenderer.disableLightmap(f);
		if (Minecraft.isGuiEnabled() && mc.entityRenderer.debugViewDirection == 0) {
			mc.mcProfiler.startSection("lotrSpeech");
			LOTRNPCRendering.renderAllNPCSpeeches(mc, mc.theWorld, f);
			mc.mcProfiler.endSection();
		}
	}

	@SubscribeEvent
	public void onWorldLoad(WorldEvent.Load event) {
		if (event.world instanceof WorldClient) {
			LOTRClientProxy.customEffectRenderer.clearEffectsAndSetWorld(event.world);
		}
	}

	public void renderAlignment(Minecraft mc, float f) {
		EntityClientPlayerMP entityClientPlayerMP = mc.thePlayer;
		LOTRPlayerData pd = LOTRLevelData.getData(entityClientPlayerMP);
		LOTRFaction viewingFac = pd.getViewingFaction();
		ScaledResolution resolution = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
		int width = resolution.getScaledWidth();
		alignmentXBase = width / 2 + LOTRConfig.alignmentXOffset;
		alignmentYBase = 4 + LOTRConfig.alignmentYOffset;
		if (isBossActive()) {
			alignmentYBase += 20;
		}
		if (watchedInvasion.isActive()) {
			alignmentYBase += 20;
		}
		if (firstAlignmentRender) {
			LOTRAlignmentTicker.updateAll(entityClientPlayerMP, true);
			alignmentXPrev = alignmentXCurrent = alignmentXBase;
			alignmentYPrev = alignmentYCurrent = -20;
			firstAlignmentRender = false;
		}
		float alignmentXF = alignmentXPrev + (alignmentXCurrent - alignmentXPrev) * f;
		float alignmentYF = alignmentYPrev + (alignmentYCurrent - alignmentYPrev) * f;
		boolean text = alignmentYCurrent == alignmentYBase;
		float alignment = LOTRAlignmentTicker.forFaction(viewingFac).getInterpolatedAlignment(f);
		renderAlignmentBar(alignment, false, viewingFac, alignmentXF, alignmentYF, text, text, text, false);
		if (alignDrainTick > 0 && text) {
			float alpha = 1.0F;
			int fadeTick = 20;
			if (alignDrainTick < fadeTick) {
				alpha = 0;
			}
			renderAlignmentDrain(mc, (int) alignmentXF - 155, (int) alignmentYF + 2, alignDrainNum, alpha);
		}
	}

	public void renderOverlay(float[] rgb, float alpha, Minecraft mc, ResourceLocation texture) {
		ScaledResolution resolution = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
		int width = resolution.getScaledWidth();
		int height = resolution.getScaledHeight();
		GL11.glEnable(3042);
		GL11.glBlendFunc(770, 771);
		GL11.glDisable(2929);
		GL11.glDepthMask(false);
		if (rgb != null) {
			GL11.glColor4f(rgb[0], rgb[1], rgb[2], alpha);
		} else {
			GL11.glColor4f(1.0F, 1.0F, 1.0F, alpha);
		}
		GL11.glDisable(3008);
		if (texture != null) {
			mc.getTextureManager().bindTexture(texture);
		} else {
			GL11.glDisable(3553);
		}
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(0.0D, height, -90.0D, 0.0D, 1.0D);
		tessellator.addVertexWithUV(width, height, -90.0D, 1.0D, 1.0D);
		tessellator.addVertexWithUV(width, 0.0D, -90.0D, 1.0D, 0.0D);
		tessellator.addVertexWithUV(0.0D, 0.0D, -90.0D, 0.0D, 0.0D);
		tessellator.draw();
		if (texture == null) {
			GL11.glEnable(3553);
		}
		GL11.glDepthMask(true);
		GL11.glEnable(2929);
		GL11.glEnable(3008);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}

	public void renderOverlayWithVerticalGradients(float[] rgbEdge, float[] rgbCentre, float alphaEdge, float alphaCentre, Minecraft mc) {
		ScaledResolution resolution = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
		int width = resolution.getScaledWidth();
		int height = resolution.getScaledHeight();
		int heightThird = height / 3;
		int heightTwoThirds = height * 2 / 3;
		GL11.glEnable(3042);
		GL11.glBlendFunc(770, 771);
		GL11.glDisable(2929);
		GL11.glDepthMask(false);
		GL11.glDisable(3008);
		GL11.glDisable(3553);
		GL11.glShadeModel(7425);
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.setColorRGBA_F(rgbCentre[0], rgbCentre[1], rgbCentre[2], alphaCentre);
		tessellator.addVertex(0.0D, heightThird, -90.0D);
		tessellator.addVertex(width, heightThird, -90.0D);
		tessellator.setColorRGBA_F(rgbEdge[0], rgbEdge[1], rgbEdge[2], alphaEdge);
		tessellator.addVertex(width, 0.0D, -90.0D);
		tessellator.addVertex(0.0D, 0.0D, -90.0D);
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setColorRGBA_F(rgbCentre[0], rgbCentre[1], rgbCentre[2], alphaCentre);
		tessellator.addVertex(0.0D, heightTwoThirds, -90.0D);
		tessellator.addVertex(width, heightTwoThirds, -90.0D);
		tessellator.setColorRGBA_F(rgbCentre[0], rgbCentre[1], rgbCentre[2], alphaCentre);
		tessellator.addVertex(width, heightThird, -90.0D);
		tessellator.addVertex(0.0D, heightThird, -90.0D);
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setColorRGBA_F(rgbEdge[0], rgbEdge[1], rgbEdge[2], alphaEdge);
		tessellator.addVertex(0.0D, height, -90.0D);
		tessellator.addVertex(width, height, -90.0D);
		tessellator.setColorRGBA_F(rgbCentre[0], rgbCentre[1], rgbCentre[2], alphaCentre);
		tessellator.addVertex(width, heightTwoThirds, -90.0D);
		tessellator.addVertex(0.0D, heightTwoThirds, -90.0D);
		tessellator.draw();
		GL11.glShadeModel(7424);
		GL11.glEnable(3553);
		GL11.glDepthMask(true);
		GL11.glEnable(2929);
		GL11.glEnable(3008);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}

	public void spawnEnvironmentFX(EntityPlayer entityplayer, World world) {
		world.theProfiler.startSection("lotrEnvironmentFX");
		int i = MathHelper.floor_double(entityplayer.posX);
		int j = MathHelper.floor_double(entityplayer.boundingBox.minY);
		int k = MathHelper.floor_double(entityplayer.posZ);
		byte range = 16;
		for (int l = 0; l < 1000; l++) {
			int i1 = i + world.rand.nextInt(range) - world.rand.nextInt(range);
			int j1 = j + world.rand.nextInt(range) - world.rand.nextInt(range);
			int k1 = k + world.rand.nextInt(range) - world.rand.nextInt(range);
			Block block = world.getBlock(i1, j1, k1);
			int meta = world.getBlockMetadata(i1, j1, k1);
			if (block.getMaterial() == Material.water) {
				BiomeGenBase biome = world.getBiomeGenForCoords(i1, k1);
				if (biome instanceof lotr.common.world.biome.LOTRBiomeGenMirkwoodCorrupted && world.rand.nextInt(20) == 0) {
					LOTRMod.proxy.spawnParticle("mirkwoodWater", i1 + world.rand.nextFloat(), j1 + 0.75D, k1 + world.rand.nextFloat(), 0.0D, 0.05D, 0.0D);
				}
				if (biome instanceof lotr.common.world.biome.LOTRBiomeGenMorgulVale && world.rand.nextInt(40) == 0) {
					LOTRMod.proxy.spawnParticle("morgulWater", i1 + world.rand.nextFloat(), j1 + 0.75D, k1 + world.rand.nextFloat(), 0.0D, 0.05D, 0.0D);
				}
				if (biome instanceof lotr.common.world.biome.LOTRBiomeGenDeadMarshes && world.rand.nextInt(800) == 0) {
					world.spawnEntityInWorld(new LOTREntityDeadMarshFace(world, i1 + world.rand.nextFloat(), j1 + 0.25D - world.rand.nextFloat(), k1 + world.rand.nextFloat()));
				}
			}
			if (block.getMaterial() == Material.water && meta != 0) {
				Block below = world.getBlock(i1, j1 - 1, k1);
				if (below.getMaterial() == Material.water) {
					for (int i2 = i1 - 1; i2 <= i1 + 1; i2++) {
						for (int k2 = k1 - 1; k2 <= k1 + 1; k2++) {
							Block adjBlock = world.getBlock(i2, j1 - 1, k2);
							int adjMeta = world.getBlockMetadata(i2, j1 - 1, k2);
							if (adjBlock.getMaterial() == Material.water && adjMeta == 0 && world.isAirBlock(i2, j1, k2)) {
								for (int l1 = 0; l1 < 2; l1++) {
									double d = i1 + 0.5D + (i2 - i1) * world.rand.nextFloat();
									double d1 = j1 + world.rand.nextFloat() * 0.2F;
									double d2 = k1 + 0.5D + (k2 - k1) * world.rand.nextFloat();
									world.spawnParticle("explode", d, d1, d2, 0.0D, 0.0D, 0.0D);
								}
							}
						}
					}
				}
			}
		}
		world.theProfiler.endSection();
	}

	public void updateDate() {
		newDate = 200;
	}

	public void updatePlayerInPortal(EntityPlayer entityplayer, HashMap<EntityPlayer, Integer> players, Block portalBlock) {
		if ((entityplayer.dimension == 0 || entityplayer.dimension == LOTRDimension.MIDDLE_EARTH.dimensionID) && players.containsKey(entityplayer)) {
			boolean inPortal = entityplayer.worldObj.getBlock(MathHelper.floor_double(entityplayer.posX), MathHelper.floor_double(entityplayer.boundingBox.minY), MathHelper.floor_double(entityplayer.posZ)) == portalBlock;
			if (inPortal) {
				int i = players.get(entityplayer);
				i++;
				players.put(entityplayer, i);
				if (i >= entityplayer.getMaxInPortalTime()) {
					Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("portal.trigger"), entityplayer.worldObj.rand.nextFloat() * 0.4F + 0.8F));
					players.remove(entityplayer);
				}
			} else {
				players.remove(entityplayer);
			}
		}
	}

	public static void drawAlignmentText(FontRenderer f, int x, int y, String s, float alphaF) {
		drawBorderedText(f, x, y, s, 16772620, alphaF);
	}

	public static void drawBorderedText(FontRenderer f, int x, int y, String s, int color, float alphaF) {
		int alpha = (int) (alphaF * 255.0F);
		alpha = MathHelper.clamp_int(alpha, 4, 255);
		alpha <<= 24;
		f.drawString(s, x - 1, y - 1, alpha);
		f.drawString(s, x, y - 1, alpha);
		f.drawString(s, x + 1, y - 1, alpha);
		f.drawString(s, x + 1, y, alpha);
		f.drawString(s, x + 1, y + 1, alpha);
		f.drawString(s, x, y + 1, alpha);
		f.drawString(s, x - 1, y + 1, alpha);
		f.drawString(s, x - 1, y, alpha);
		f.drawString(s, x, y, color | alpha);
	}

	public static void drawConquestText(FontRenderer f, int x, int y, String s, boolean cleanse, float alphaF) {
		drawBorderedText(f, x, y, s, cleanse ? 16773846 : 14833677, alphaF);
	}

	public static void drawTexturedModalRect(double x, double y, int u, int v, int width, int height) {
		float f = 0.00390625F;
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(x + 0.0D, y + height, 0.0D, (u) * f, (v + height) * f);
		tessellator.addVertexWithUV(x + width, y + height, 0.0D, (u + width) * f, (v + height) * f);
		tessellator.addVertexWithUV(x + width, y + 0.0D, 0.0D, (u + width) * f, (v) * f);
		tessellator.addVertexWithUV(x + 0.0D, y + 0.0D, 0.0D, (u) * f, (v) * f);
		tessellator.draw();
	}

	public static boolean isBossActive() {
		return BossStatus.bossName != null && BossStatus.statusBarTime > 0;
	}

	public static void renderAlignmentBar(float alignment, boolean isOtherPlayer, LOTRFaction faction, float x, float y, boolean renderFacName, boolean renderValue, boolean renderLimits, boolean renderLimitValues) {
		Minecraft mc = Minecraft.getMinecraft();
		EntityClientPlayerMP entityClientPlayerMP = mc.thePlayer;
		LOTRPlayerData clientPD = LOTRLevelData.getData(entityClientPlayerMP);
		LOTRFactionRank rank = faction.getRank(alignment);
		boolean pledged = clientPD.isPledgedTo(faction);
		LOTRAlignmentTicker ticker = LOTRAlignmentTicker.forFaction(faction);
		float alignMin;
		float alignMax;
		LOTRFactionRank rankMin;
		LOTRFactionRank rankMax;
		if (rank.isDummyRank()) {
			float firstRankAlign;
			LOTRFactionRank firstRank = faction.getFirstRank();
			if (firstRank != null && !firstRank.isDummyRank()) {
				firstRankAlign = firstRank.alignment;
			} else {
				firstRankAlign = 10.0F;
			}
			if (Math.abs(alignment) < firstRankAlign) {
				alignMin = -firstRankAlign;
				alignMax = firstRankAlign;
				rankMin = LOTRFactionRank.RANK_ENEMY;
				rankMax = firstRank != null && !firstRank.isDummyRank() ? firstRank : LOTRFactionRank.RANK_NEUTRAL;
			} else if (alignment < 0.0F) {
				alignMax = -firstRankAlign;
				alignMin = alignMax * 10.0F;
				rankMin = rankMax = LOTRFactionRank.RANK_ENEMY;
				while (alignment <= alignMin) {
					alignMax *= 10.0F;
					alignMin = alignMax * 10.0F;
				}
			} else {
				alignMin = firstRankAlign;
				alignMax = alignMin * 10.0F;
				rankMin = rankMax = LOTRFactionRank.RANK_NEUTRAL;
				while (alignment >= alignMax) {
					alignMin = alignMax;
					alignMax = alignMin * 10.0F;
				}
			}
		} else {
			alignMin = rank.alignment;
			rankMin = rank;
			LOTRFactionRank nextRank = faction.getRankAbove(rank);
			if (nextRank != null && !nextRank.isDummyRank() && nextRank != rank) {
				alignMax = nextRank.alignment;
				rankMax = nextRank;
			} else {
				alignMax = rank.alignment * 10.0F;
				rankMax = rank;
				while (alignment >= alignMax) {
					alignMin = alignMax;
					alignMax = alignMin * 10.0F;
				}
			}
		}
		float ringProgress = (alignment - alignMin) / (alignMax - alignMin);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.getTextureManager().bindTexture(LOTRClientProxy.alignmentTexture);
		int barWidth = 232;
		int barHeight = 14;
		int activeBarWidth = 220;
		float[] factionColors = faction.getFactionRGB();
		GL11.glColor4f(factionColors[0], factionColors[1], factionColors[2], 1.0F);
		drawTexturedModalRect(x - (double) barWidth / 2, y, 0, 14, barWidth, barHeight);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		drawTexturedModalRect(x - (double) barWidth / 2, y, 0, 0, barWidth, barHeight);
		float ringProgressAdj = (ringProgress - 0.5F) * 2.0F;
		int ringSize = 16;
		float ringX = x - (float) ringSize / 2 + ringProgressAdj * activeBarWidth / 2.0F;
		float ringY = y + (float) barHeight / 2 - (float) ringSize / 2;
		int flashTick = ticker.flashTick;
		if (pledged) {
			drawTexturedModalRect(ringX, ringY, 16 * Math.round((float) flashTick / 3), 212, ringSize, ringSize);
		} else {
			drawTexturedModalRect(ringX, ringY, 16 * Math.round((float) flashTick / 3), 36, ringSize, ringSize);
		}
		if (faction.isPlayableAlignmentFaction()) {
			float alpha;
			boolean definedZone;
			if (faction.inControlZone(entityClientPlayerMP)) {
				alpha = 1.0F;
				definedZone = faction.inDefinedControlZone(entityClientPlayerMP);
			} else {
				alpha = faction.getControlZoneAlignmentMultiplier(entityClientPlayerMP);
				definedZone = true;
			}
			if (alpha > 0.0F) {
				int arrowSize = 14;
				int y0 = definedZone ? 60 : 88;
				int y1 = definedZone ? 74 : 102;
				GL11.glEnable(3042);
				OpenGlHelper.glBlendFunc(770, 771, 1, 0);
				GL11.glColor4f(factionColors[0], factionColors[1], factionColors[2], alpha);
				drawTexturedModalRect(x - (double) barWidth / 2 - arrowSize, y, 0, y1, arrowSize, arrowSize);
				drawTexturedModalRect(x + (double) barWidth / 2, y, arrowSize, y1, arrowSize, arrowSize);
				GL11.glColor4f(1.0F, 1.0F, 1.0F, alpha);
				drawTexturedModalRect(x - (double) barWidth / 2 - arrowSize, y, 0, y0, arrowSize, arrowSize);
				drawTexturedModalRect(x + (double) barWidth / 2, y, arrowSize, y0, arrowSize, arrowSize);
				GL11.glDisable(3042);
			}
		}
		FontRenderer fr = mc.fontRenderer;
		int textX = Math.round(x);
		int textY = Math.round(y + barHeight + 4.0F);
		if (renderLimits) {
			String sMin = rankMin.getShortNameWithGender(clientPD);
			String sMax = rankMax.getShortNameWithGender(clientPD);
			if (renderLimitValues) {
				sMin = StatCollector.translateToLocalFormatted("lotr.gui.factions.alignment.limits", sMin, LOTRAlignmentValues.formatAlignForDisplay(alignMin));
				sMax = StatCollector.translateToLocalFormatted("lotr.gui.factions.alignment.limits", sMax, LOTRAlignmentValues.formatAlignForDisplay(alignMax));
			}
			int limitsX = barWidth / 2 - 6;
			int xMin = Math.round(x - limitsX);
			int xMax = Math.round(x + limitsX);
			GL11.glPushMatrix();
			GL11.glScalef(0.5F, 0.5F, 0.5F);
			drawAlignmentText(fr, xMin * 2 - fr.getStringWidth(sMin) / 2, textY * 2, sMin, 1.0F);
			drawAlignmentText(fr, xMax * 2 - fr.getStringWidth(sMax) / 2, textY * 2, sMax, 1.0F);
			GL11.glPopMatrix();
		}
		if (renderFacName) {
			String name = faction.factionName();
			drawAlignmentText(fr, textX - fr.getStringWidth(name) / 2, textY, name, 1.0F);
		}
		if (renderValue) {
			String alignS;
			float alignAlpha;
			int numericalTick = ticker.numericalTick;
			if (numericalTick > 0) {
				alignS = LOTRAlignmentValues.formatAlignForDisplay(alignment);
				alignAlpha = LOTRFunctions.triangleWave(numericalTick, 0.7F, 1.0F, 30.0F);
				int fadeTick = 15;
				if (numericalTick < fadeTick) {
					alignAlpha *= 0;
				}
			} else {
				alignS = rank.getShortNameWithGender(clientPD);
				alignAlpha = 1.0F;
			}
			GL11.glEnable(3042);
			OpenGlHelper.glBlendFunc(770, 771, 1, 0);
			drawAlignmentText(fr, textX - fr.getStringWidth(alignS) / 2, textY + fr.FONT_HEIGHT + 3, alignS, alignAlpha);
			GL11.glDisable(3042);
		}
	}

	public static void renderAlignmentDrain(Minecraft mc, int x, int y, int numFactions) {
		renderAlignmentDrain(mc, x, y, numFactions, 1.0F);
	}

	public static void renderAlignmentDrain(Minecraft mc, int x, int y, int numFactions, float alpha) {
		GL11.glEnable(3042);
		OpenGlHelper.glBlendFunc(770, 771, 1, 0);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, alpha);
		mc.getTextureManager().bindTexture(LOTRClientProxy.alignmentTexture);
		drawTexturedModalRect(x, y, 0, 128, 16, 16);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		String s = "-" + numFactions;
		FontRenderer fr = mc.fontRenderer;
		drawBorderedText(fr, x + 8 - fr.getStringWidth(s) / 2, y + 8 - fr.FONT_HEIGHT / 2, s, 16777215, alpha);
		GL11.glDisable(3042);
	}
}
