package lotr.client;

import com.mojang.authlib.GameProfile;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.relauncher.Side;
import lotr.client.fx.*;
import lotr.client.gui.*;
import lotr.client.model.LOTRArmorModels;
import lotr.client.render.LOTRRenderBlocks;
import lotr.client.render.LOTRRenderPlayer;
import lotr.client.render.entity.*;
import lotr.client.render.tileentity.*;
import lotr.client.sound.LOTRMusic;
import lotr.common.*;
import lotr.common.entity.LOTREntityFallingFireJar;
import lotr.common.entity.LOTREntityInvasionSpawner;
import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.LOTRInvasionStatus;
import lotr.common.entity.animal.*;
import lotr.common.entity.item.*;
import lotr.common.entity.npc.*;
import lotr.common.entity.projectile.*;
import lotr.common.fac.LOTRAlignmentBonusMap;
import lotr.common.fac.LOTRFaction;
import lotr.common.network.LOTRPacketClientInfo;
import lotr.common.network.LOTRPacketFellowshipAcceptInviteResult;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.quest.LOTRMiniQuest;
import lotr.common.tileentity.*;
import lotr.common.util.LOTRFunctions;
import lotr.common.world.biome.LOTRBiome;
import lotr.common.world.map.LOTRAbstractWaypoint;
import lotr.common.world.map.LOTRConquestZone;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.EmptyChunk;
import org.lwjgl.opengl.GL11;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class LOTRClientProxy extends LOTRCommonProxy {
	public static ResourceLocation enchantmentTexture = new ResourceLocation("textures/misc/enchanted_item_glint.png");
	public static ResourceLocation alignmentTexture = new ResourceLocation("lotr:gui/alignment.png");
	public static ResourceLocation particlesTexture = new ResourceLocation("lotr:misc/particles.png");
	public static ResourceLocation particles2Texture = new ResourceLocation("lotr:misc/particles2.png");
	public static ResourceLocation customPotionsTexture = new ResourceLocation("lotr:gui/effects.png");
	public static int TESSELLATOR_MAX_BRIGHTNESS = 15728880;
	public static int FONTRENDERER_ALPHA_MIN = 4;
	public static LOTREffectRenderer customEffectRenderer;
	public static LOTRRenderPlayer specialPlayerRenderer = new LOTRRenderPlayer();
	public static LOTRSwingHandler swingHandler = new LOTRSwingHandler();
	public static LOTRTickHandlerClient tickHandler = new LOTRTickHandlerClient();
	public static LOTRKeyHandler keyHandler = new LOTRKeyHandler();
	public static LOTRGuiHandler guiHandler = new LOTRGuiHandler();
	public static LOTRMusic musicHandler;

	public int beaconRenderID;
	public int barrelRenderID;
	public int orcBombRenderID;
	public int doubleTorchRenderID;
	public int mobSpawnerRenderID;
	public int plateRenderID;
	public int stalactiteRenderID;
	public int flowerPotRenderID;
	public int cloverRenderID;
	public int entJarRenderID;
	public int trollTotemRenderID;
	public int fenceRenderID;
	public int grassRenderID;
	public int fallenLeavesRenderID;
	public int commandTableRenderID;
	public int butterflyJarRenderID;
	public int unsmelteryRenderID;
	public int chestRenderID;
	public int reedsRenderID;
	public int wasteRenderID;
	public int beamRenderID;
	public int vCauldronRenderID;
	public int grapevineRenderID;
	public int thatchFloorRenderID;
	public int treasureRenderID;
	public int flowerRenderID;
	public int doublePlantRenderID;
	public int birdCageRenderID;
	public int rhunFireJarRenderID;
	public int coralRenderID;
	public int doorRenderID;
	public int ropeRenderID;
	public int orcChainRenderID;
	public int guldurilRenderID;
	public int orcPlatingRenderID;

	public int trapdoorRenderID;

	public static boolean doesClientChunkExist(World world, int i, int k) {
		int chunkX = i >> 4;
		int chunkZ = k >> 4;
		Chunk chunk = world.getChunkProvider().provideChunk(chunkX, chunkZ);
		return !(chunk instanceof EmptyChunk);
	}

	public static int getAlphaInt(float alphaF) {
		int alphaI = (int) (alphaF * 255.0f);
		return MathHelper.clamp_int(alphaI, 4, 255);
	}

	public static void renderEnchantmentEffect() {
		Tessellator tessellator = Tessellator.instance;
		TextureManager texturemanager = Minecraft.getMinecraft().getTextureManager();
		GL11.glDepthFunc(514);
		GL11.glDisable(2896);
		texturemanager.bindTexture(enchantmentTexture);
		GL11.glEnable(3042);
		GL11.glBlendFunc(768, 1);
		float shade = 0.76f;
		GL11.glColor4f(0.5f * shade, 0.25f * shade, 0.8f * shade, 1.0f);
		GL11.glMatrixMode(5890);
		GL11.glPushMatrix();
		float scale = 0.125f;
		GL11.glScalef(scale, scale, scale);
		float randomShift = Minecraft.getSystemTime() % 3000L / 3000.0f * 8.0f;
		GL11.glTranslatef(randomShift, 0.0f, 0.0f);
		GL11.glRotatef(-50.0f, 0.0f, 0.0f, 1.0f);
		ItemRenderer.renderItemIn2D(tessellator, 0.0f, 0.0f, 1.0f, 1.0f, 256, 256, 0.0625f);
		GL11.glPopMatrix();
		GL11.glPushMatrix();
		GL11.glScalef(scale, scale, scale);
		randomShift = Minecraft.getSystemTime() % 4873L / 4873.0f * 8.0f;
		GL11.glTranslatef(-randomShift, 0.0f, 0.0f);
		GL11.glRotatef(10.0f, 0.0f, 0.0f, 1.0f);
		ItemRenderer.renderItemIn2D(tessellator, 0.0f, 0.0f, 1.0f, 1.0f, 256, 256, 0.0625f);
		GL11.glPopMatrix();
		GL11.glMatrixMode(5888);
		GL11.glDisable(3042);
		GL11.glEnable(2896);
		GL11.glDepthFunc(515);
	}

	public static void sendClientInfoPacket(LOTRFaction viewingFaction, Map<LOTRDimension.DimensionRegion, LOTRFaction> changedRegionMap) {
		boolean showWP = LOTRGuiMap.showWP;
		boolean showCWP = LOTRGuiMap.showCWP;
		boolean showHiddenSWP = LOTRGuiMap.showHiddenSWP;
		IMessage packet = new LOTRPacketClientInfo(viewingFaction, changedRegionMap, showWP, showCWP, showHiddenSWP);
		LOTRPacketHandler.networkWrapper.sendToServer(packet);
	}

	@Override
	public void addMapPlayerLocation(GameProfile player, double posX, double posZ) {
		LOTRGuiMap.addPlayerLocationInfo(player, posX, posZ);
	}

	@Override
	public void cancelItemHighlight() {
		tickHandler.cancelItemHighlight = true;
	}

	@Override
	public void clearMapPlayerLocations() {
		LOTRGuiMap.clearPlayerLocations();
	}

	@Override
	public void clientReceiveSpeech(LOTREntityNPC npc, String speech) {
		LOTRSpeechClient.receiveSpeech(npc, speech);
	}

	@Override
	public void displayAlignDrain(int numFactions) {
		LOTRTickHandlerClient.alignDrainTick = 200;
		LOTRTickHandlerClient.alignDrainNum = numFactions;
	}

	@Override
	public void displayAlignmentChoice() {
		Minecraft mc = Minecraft.getMinecraft();
		mc.displayGuiScreen(new LOTRGuiAlignmentChoices());
	}

	@Override
	public void displayAlignmentSee(String username, Map<LOTRFaction, Float> alignments) {
		LOTRGuiFactions gui = new LOTRGuiFactions();
		gui.setOtherPlayer(username, alignments);
		Minecraft mc = Minecraft.getMinecraft();
		mc.displayGuiScreen(gui);
	}

	@Override
	public void displayBannerGui(LOTREntityBanner banner) {
		Minecraft mc = Minecraft.getMinecraft();
		LOTRGuiBanner gui = new LOTRGuiBanner(banner);
		mc.displayGuiScreen(gui);
	}

	@Override
	public void displayFellowshipAcceptInvitationResult(UUID fellowshipID, String name, LOTRPacketFellowshipAcceptInviteResult.AcceptInviteResult result) {
		Minecraft mc = Minecraft.getMinecraft();
		GuiScreen gui = mc.currentScreen;
		if (gui instanceof LOTRGuiFellowships) {
			((LOTRGuiFellowships) gui).displayAcceptInvitationResult(fellowshipID, name, result);
		}
	}

	@Override
	public void displayFTScreen(LOTRAbstractWaypoint waypoint, int startX, int startZ) {
		Minecraft mc = Minecraft.getMinecraft();
		mc.displayGuiScreen(new LOTRGuiFastTravel(waypoint, startX, startZ));
	}

	@Override
	public void displayMessage(LOTRGuiMessageTypes message) {
		Minecraft.getMinecraft().displayGuiScreen(new LOTRGuiMessage(message));
	}

	@Override
	public void displayMiniquestOffer(LOTRMiniQuest quest, LOTREntityNPC npc) {
		Minecraft mc = Minecraft.getMinecraft();
		mc.displayGuiScreen(new LOTRGuiMiniquestOffer(quest, npc));
	}

	@Override
	public void displayNewDate() {
		tickHandler.updateDate();
	}

	@Override
	public void fillMugFromCauldron(World world, int i, int j, int k, int side, ItemStack itemstack) {
		if (world.isRemote) {
			Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(i, j, k, side, itemstack, 0.0f, 0.0f, 0.0f));
		} else {
			super.fillMugFromCauldron(world, i, j, k, side, itemstack);
		}
	}

	@Override
	public int getBarrelRenderID() {
		return barrelRenderID;
	}

	@Override
	public int getBeaconRenderID() {
		return beaconRenderID;
	}

	@Override
	public int getBeamRenderID() {
		return beamRenderID;
	}

	@Override
	public int getBirdCageRenderID() {
		return birdCageRenderID;
	}

	@Override
	public int getButterflyJarRenderID() {
		return butterflyJarRenderID;
	}

	@Override
	public int getChestRenderID() {
		return chestRenderID;
	}

	@Override
	public EntityPlayer getClientPlayer() {
		return Minecraft.getMinecraft().thePlayer;
	}

	@Override
	public World getClientWorld() {
		return Minecraft.getMinecraft().theWorld;
	}

	@Override
	public int getCloverRenderID() {
		return cloverRenderID;
	}

	@Override
	public int getCommandTableRenderID() {
		return commandTableRenderID;
	}

	@Override
	public int getCoralRenderID() {
		return coralRenderID;
	}

	@Override
	public int getDoorRenderID() {
		return doorRenderID;
	}

	@Override
	public int getDoublePlantRenderID() {
		return doublePlantRenderID;
	}

	@Override
	public int getDoubleTorchRenderID() {
		return doubleTorchRenderID;
	}

	@Override
	public int getEntJarRenderID() {
		return entJarRenderID;
	}

	@Override
	public int getFallenLeavesRenderID() {
		return fallenLeavesRenderID;
	}

	@Override
	public int getFenceRenderID() {
		return fenceRenderID;
	}

	@Override
	public int getFlowerPotRenderID() {
		return flowerPotRenderID;
	}

	@Override
	public int getFlowerRenderID() {
		return flowerRenderID;
	}

	@Override
	public int getGrapevineRenderID() {
		return grapevineRenderID;
	}

	@Override
	public int getGrassRenderID() {
		return grassRenderID;
	}

	@Override
	public int getGuldurilRenderID() {
		return guldurilRenderID;
	}

	@Override
	public int getMobSpawnerRenderID() {
		return mobSpawnerRenderID;
	}

	@Override
	public int getOrcBombRenderID() {
		return orcBombRenderID;
	}

	@Override
	public int getOrcChainRenderID() {
		return orcChainRenderID;
	}

	@Override
	public int getOrcPlatingRenderID() {
		return orcPlatingRenderID;
	}

	@Override
	public int getPlateRenderID() {
		return plateRenderID;
	}

	@Override
	public int getReedsRenderID() {
		return reedsRenderID;
	}

	@Override
	public int getRhunFireJarRenderID() {
		return rhunFireJarRenderID;
	}

	@Override
	public int getRopeRenderID() {
		return ropeRenderID;
	}

	@Override
	public int getStalactiteRenderID() {
		return stalactiteRenderID;
	}

	@Override
	public int getThatchFloorRenderID() {
		return thatchFloorRenderID;
	}

	@Override
	public int getTrapdoorRenderID() {
		return trapdoorRenderID;
	}

	@Override
	public int getTreasureRenderID() {
		return treasureRenderID;
	}

	@Override
	public int getTrollTotemRenderID() {
		return trollTotemRenderID;
	}

	@Override
	public int getUnsmelteryRenderID() {
		return unsmelteryRenderID;
	}

	@Override
	public int getVCauldronRenderID() {
		return vCauldronRenderID;
	}

	@Override
	public int getWasteRenderID() {
		return wasteRenderID;
	}

	@Override
	public void handleInvasionWatch(int invasionEntityID, boolean overrideAlreadyWatched) {
		Entity e;
		LOTRInvasionStatus status = LOTRTickHandlerClient.watchedInvasion;
		if ((overrideAlreadyWatched || !status.isActive()) && (e = getClientWorld().getEntityByID(invasionEntityID)) instanceof LOTREntityInvasionSpawner) {
			status.setWatchedInvasion((LOTREntityInvasionSpawner) e);
		}
	}

	@Override
	public boolean isClient() {
		return FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT;
	}

	@Override
	public boolean isPaused() {
		return Minecraft.getMinecraft().isGamePaused();
	}

	@Override
	public boolean isSingleplayer() {
		return Minecraft.getMinecraft().isSingleplayer();
	}

	@Override
	public void onLoad() {
		customEffectRenderer = new LOTREffectRenderer(Minecraft.getMinecraft());
		LOTRTextures.load();
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityPortal.class, new LOTRRenderPortal());
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityHorse.class, new LOTRRenderHorse());
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityHobbit.class, new LOTRRenderHobbit());
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityHobbitBartender.class, new LOTRRenderHobbitTrader("outfit_bartender"));
		RenderingRegistry.registerEntityRenderingHandler(LOTREntitySmokeRing.class, new LOTRRenderSmokeRing());
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityOrc.class, new LOTRRenderOrc());
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityShirePony.class, new LOTRRenderShirePony());
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityOrcBomb.class, new LOTRRenderOrcBomb());
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityWarg.class, new LOTRRenderWarg());
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityGandalfFireball.class, new LOTRRenderGandalfFireball());
		RenderingRegistry.registerEntityRenderingHandler(LOTREntitySpear.class, new LOTRRenderSpear());
		RenderingRegistry.registerEntityRenderingHandler(LOTREntitySauron.class, new LOTRRenderSauron());
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityElf.class, new LOTRRenderElf());
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityPlate.class, new LOTRRenderPlate());
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityWargskinRug.class, new LOTRRenderWargskinRug());
		RenderingRegistry.registerEntityRenderingHandler(LOTREntitySkeletalWraith.class, new LOTRRenderSkeleton());
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityGondorBlacksmith.class, new LOTRRenderGondorTrader("outfit_blacksmith"));
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityGaladhrimTrader.class, new LOTRRenderElvenTrader("galadhrimTrader_cloak"));
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityAlignmentBonus.class, new LOTRRenderAlignmentBonus());
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityDwarf.class, new LOTRRenderDwarf());
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityMarshWraith.class, new LOTRRenderMarshWraith());
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityMarshWraithBall.class, new LOTRRenderWraithBall());
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityDwarfCommander.class, new LOTRRenderDwarfCommander());
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityBlueDwarfCommander.class, new LOTRRenderDwarfCommander());
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityBlueDwarfMerchant.class, new LOTRRenderDwarfCommander());
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityThrowingAxe.class, new LOTRRenderThrowingAxe());
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityCrossbowBolt.class, new LOTRRenderCrossbowBolt());
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityTroll.class, new LOTRRenderTroll());
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityOlogHai.class, new LOTRRenderOlogHai());
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityStoneTroll.class, new LOTRRenderStoneTroll());
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityGollum.class, new LOTRRenderGollum());
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityMirkwoodSpider.class, new LOTRRenderMirkwoodSpider());
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityRohanMan.class, new LOTRRenderRohirrim());
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityPebble.class, new RenderSnowball(LOTRMod.pebble));
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityMysteryWeb.class, new RenderSnowball(LOTRMod.mysteryWeb));
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityRohanBlacksmith.class, new LOTRRenderRohanTrader("outfit_blacksmith"));
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityRanger.class, new LOTRRenderRanger());
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityDunlending.class, new LOTRRenderDunlending());
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityDunlendingWarrior.class, new LOTRRenderDunlendingBase());
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityEnt.class, new LOTRRenderEnt());
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityTraderRespawn.class, new LOTRRenderTraderRespawn());
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityMountainTroll.class, new LOTRRenderMountainTroll());
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityThrownRock.class, new LOTRRenderThrownRock());
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityMountainTrollChieftain.class, new LOTRRenderMountainTrollChieftain());
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityHuornBase.class, new LOTRRenderHuorn());
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityRohanMeadhost.class, new LOTRRenderRohanTrader("outfit_meadhost"));
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityButterfly.class, new LOTRRenderButterfly());
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityBarrel.class, new LOTRRenderEntityBarrel());
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityMidges.class, new LOTRRenderMidges());
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityDeadMarshFace.class, new LOTRRenderDeadMarshFace());
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityNurnSlave.class, new LOTRRenderNurnSlave());
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityRabbit.class, new LOTRRenderRabbit());
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityWildBoar.class, new LOTRRenderWildBoar());
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityMordorSpider.class, new LOTRRenderMordorSpider());
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityBanner.class, new LOTRRenderBanner());
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityBannerWall.class, new LOTRRenderBannerWall());
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityLionBase.class, new LOTRRenderLion());
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityGiraffe.class, new LOTRRenderGiraffe());
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityZebra.class, new LOTRRenderZebra());
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityRhino.class, new LOTRRenderRhino());
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityCrocodile.class, new LOTRRenderCrocodile());
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityNearHaradrimBase.class, new LOTRRenderNearHaradrim());
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityNearHaradrimWarlord.class, new LOTRRenderNearHaradrimWarlord());
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityGemsbok.class, new LOTRRenderGemsbok());
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityFlamingo.class, new LOTRRenderFlamingo());
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityScorpion.class, new LOTRRenderScorpion());
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityBird.class, new LOTRRenderBird());
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityCamel.class, new LOTRRenderCamel());
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityBandit.class, new LOTRRenderBandit("bandit"));
		RenderingRegistry.registerEntityRenderingHandler(LOTREntitySaruman.class, new LOTRRenderSaruman());
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityInvasionSpawner.class, new LOTRRenderInvasionSpawner());
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityElk.class, new LOTRRenderElk());
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityMirkTroll.class, new LOTRRenderMirkTroll());
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityTermite.class, new LOTRRenderTermite());
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityThrownTermite.class, new RenderSnowball(LOTRMod.termite));
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityDikDik.class, new LOTRRenderDikDik());
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityUtumnoIceSpider.class, new LOTRRenderUtumnoIceSpider());
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityConker.class, new RenderSnowball(LOTRMod.chestnut));
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityUtumnoTroll.class, new LOTRRenderUtumnoTroll());
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityBalrog.class, new LOTRRenderBalrog());
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityHalfTroll.class, new LOTRRenderHalfTroll());
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityHalfTrollScavenger.class, new LOTRRenderHalfTrollScavenger());
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityGaladhrimSmith.class, new LOTRRenderElvenSmith("galadhrimSmith_cloak", "galadhrimSmith_cape"));
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityHighElfSmith.class, new LOTRRenderElvenSmith("highElfSmith_cloak", "highElfSmith_cape"));
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityWoodElfSmith.class, new LOTRRenderElvenSmith("woodElfSmith_cloak", "woodElfSmith_cape"));
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityDolAmrothSoldier.class, new LOTRRenderSwanKnight());
		RenderingRegistry.registerEntityRenderingHandler(LOTREntitySwan.class, new LOTRRenderSwan());
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityMoredain.class, new LOTRRenderMoredain());
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityAngmarHillman.class, new LOTRRenderAngmarHillman(true));
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityAngmarHillmanWarrior.class, new LOTRRenderAngmarHillman(false));
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityIronHillsMerchant.class, new LOTRRenderDwarfCommander());
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityBossTrophy.class, new LOTRRenderBossTrophy());
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityMallornEnt.class, new LOTRRenderMallornEnt());
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityMallornLeafBomb.class, new LOTRRenderMallornLeafBomb());
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityScrapTrader.class, new LOTRRenderScrapTrader());
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityTauredain.class, new LOTRRenderTauredain());
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityDart.class, new LOTRRenderDart());
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityBarrowWight.class, new LOTRRenderBarrowWight());
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityTauredainShaman.class, new LOTRRenderTauredainShaman());
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityGaladhrimWarden.class, new LOTRRenderGaladhrimWarden());
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityDwarfSmith.class, new LOTRRenderDwarfSmith());
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityBlueMountainsSmith.class, new LOTRRenderDwarfSmith());
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityBanditHarad.class, new LOTRRenderBandit("harad"));
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityDeer.class, new LOTRRenderDeer());
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityDaleMan.class, new LOTRRenderDaleMan());
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityDaleBlacksmith.class, new LOTRRenderDaleTrader("blacksmith_apron"));
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityNPCRespawner.class, new LOTRRenderNPCRespawner());
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityDorwinionMan.class, new LOTRRenderDorwinionMan());
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityDaleBaker.class, new LOTRRenderDaleTrader("baker_apron"));
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityDorwinionElfVintner.class, new LOTRRenderDorwinionElfVintner());
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityAurochs.class, new LOTRRenderAurochs());
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityKineAraw.class, new LOTRRenderKineAraw());
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityGondorMan.class, new LOTRRenderGondorMan());
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityFallingTreasure.class, new LOTRRenderFallingCoinPile());
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityGondorBartender.class, new LOTRRenderGondorTrader("outfit_bartender"));
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityGondorGreengrocer.class, new LOTRRenderGondorTrader("outfit_greengrocer"));
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityGondorMason.class, new LOTRRenderGondorTrader("outfit_mason"));
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityGondorBrewer.class, new LOTRRenderGondorTrader("outfit_brewer"));
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityGondorFlorist.class, new LOTRRenderGondorTrader("outfit_florist"));
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityGondorButcher.class, new LOTRRenderGondorTrader("outfit_butcher"));
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityGondorBaker.class, new LOTRRenderGondorTrader("outfit_baker"));
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityDunedain.class, new LOTRRenderDunedain());
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityDunedainBlacksmith.class, new LOTRRenderDunedainTrader("outfit_blacksmith"));
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityRohanBuilder.class, new LOTRRenderRohanTrader("outfit_builder"));
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityRohanBrewer.class, new LOTRRenderRohanTrader("outfit_brewer"));
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityRohanButcher.class, new LOTRRenderRohanTrader("outfit_butcher"));
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityRohanBaker.class, new LOTRRenderRohanTrader("outfit_baker"));
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityRohanOrcharder.class, new LOTRRenderRohanTrader("outfit_orcharder"));
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityBear.class, new LOTRRenderBear());
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityEasterling.class, new LOTRRenderEasterling());
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityEasterlingBlacksmith.class, new LOTRRenderEasterlingTrader("outfit_blacksmith"));
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityFallingFireJar.class, new LOTRRenderFallingFireJar());
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityFirePot.class, new RenderSnowball(LOTRMod.rhunFirePot));
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityRivendellSmith.class, new LOTRRenderElvenSmith("rivendellSmith_cloak", "rivendellSmith_cape"));
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityRivendellTrader.class, new LOTRRenderElvenTrader("rivendellTrader_cloak"));
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityFish.class, new LOTRRenderFish());
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityArrowPoisoned.class, new LOTRRenderArrowPoisoned());
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityNearHaradBlacksmith.class, new LOTRRenderNearHaradTrader("outfit_blacksmith"));
		RenderingRegistry.registerEntityRenderingHandler(LOTREntitySnowTroll.class, new LOTRRenderSnowTroll());
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityLionRug.class, new LOTRRenderLionRug());
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityBearRug.class, new LOTRRenderBearRug());
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityGiraffeRug.class, new LOTRRenderGiraffeRug());
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityHaradSlave.class, new LOTRRenderHaradSlave());
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityGondorRenegade.class, new LOTRRenderGondorRenegade());
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityHarnedorBartender.class, new LOTRRenderHaradrimTrader("outfit_bartender"));
		RenderingRegistry.registerEntityRenderingHandler(LOTREntitySouthronBartender.class, new LOTRRenderHaradrimTrader("outfit_bartender"));
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityUmbarBartender.class, new LOTRRenderHaradrimTrader("outfit_bartender"));
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityGulfBartender.class, new LOTRRenderHaradrimTrader("outfit_bartender"));
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityWhiteOryx.class, new LOTRRenderWhiteOryx());
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityGandalf.class, new LOTRRenderGandalf());
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityWickedDwarf.class, new LOTRRenderWickedDwarf());
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityBreeMan.class, new LOTRRenderBreeMan());
		RenderingRegistry.registerEntityRenderingHandler(LOTREntitySwordCommandMarker.class, new LOTRRenderSwordCommandMarker());
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityBreeBlacksmith.class, new LOTRRenderBreeTrader("outfit_blacksmith"));
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityBreeInnkeeper.class, new LOTRRenderBreeTrader("outfit_innkeeper"));
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityBreeRuffian.class, new LOTRRenderBreeRuffian());
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityBreeHobbitInnkeeper.class, new LOTRRenderHobbitTrader("outfit_bartender"));
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityBreeBaker.class, new LOTRRenderBreeTrader("outfit_baker"));
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityBreeButcher.class, new LOTRRenderBreeTrader("outfit_butcher"));
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityBreeBrewer.class, new LOTRRenderBreeTrader("outfit_brewer"));
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityBreeMason.class, new LOTRRenderBreeTrader("outfit_mason"));
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityBreeFlorist.class, new LOTRRenderBreeTrader("outfit_florist"));
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityBreeHobbitBaker.class, new LOTRRenderHobbitTrader("outfit_baker"));
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityBreeHobbitButcher.class, new LOTRRenderHobbitTrader("outfit_butcher"));
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityBreeHobbitBrewer.class, new LOTRRenderHobbitTrader("outfit_brewer"));
		RenderingRegistry.registerEntityRenderingHandler(LOTREntityBreeHobbitFlorist.class, new LOTRRenderHobbitTrader("outfit_florist"));
		RenderingRegistry.registerEntityRenderingHandler(EntityPotion.class, new RenderSnowball(Items.potionitem, 16384));
		beaconRenderID = RenderingRegistry.getNextAvailableRenderId();
		barrelRenderID = RenderingRegistry.getNextAvailableRenderId();
		orcBombRenderID = RenderingRegistry.getNextAvailableRenderId();
		doubleTorchRenderID = RenderingRegistry.getNextAvailableRenderId();
		mobSpawnerRenderID = RenderingRegistry.getNextAvailableRenderId();
		plateRenderID = RenderingRegistry.getNextAvailableRenderId();
		stalactiteRenderID = RenderingRegistry.getNextAvailableRenderId();
		flowerPotRenderID = RenderingRegistry.getNextAvailableRenderId();
		cloverRenderID = RenderingRegistry.getNextAvailableRenderId();
		entJarRenderID = RenderingRegistry.getNextAvailableRenderId();
		trollTotemRenderID = RenderingRegistry.getNextAvailableRenderId();
		fenceRenderID = RenderingRegistry.getNextAvailableRenderId();
		grassRenderID = RenderingRegistry.getNextAvailableRenderId();
		fallenLeavesRenderID = RenderingRegistry.getNextAvailableRenderId();
		commandTableRenderID = RenderingRegistry.getNextAvailableRenderId();
		butterflyJarRenderID = RenderingRegistry.getNextAvailableRenderId();
		unsmelteryRenderID = RenderingRegistry.getNextAvailableRenderId();
		chestRenderID = RenderingRegistry.getNextAvailableRenderId();
		reedsRenderID = RenderingRegistry.getNextAvailableRenderId();
		wasteRenderID = RenderingRegistry.getNextAvailableRenderId();
		beamRenderID = RenderingRegistry.getNextAvailableRenderId();
		vCauldronRenderID = RenderingRegistry.getNextAvailableRenderId();
		grapevineRenderID = RenderingRegistry.getNextAvailableRenderId();
		thatchFloorRenderID = RenderingRegistry.getNextAvailableRenderId();
		treasureRenderID = RenderingRegistry.getNextAvailableRenderId();
		flowerRenderID = RenderingRegistry.getNextAvailableRenderId();
		doublePlantRenderID = RenderingRegistry.getNextAvailableRenderId();
		birdCageRenderID = RenderingRegistry.getNextAvailableRenderId();
		rhunFireJarRenderID = RenderingRegistry.getNextAvailableRenderId();
		coralRenderID = RenderingRegistry.getNextAvailableRenderId();
		doorRenderID = RenderingRegistry.getNextAvailableRenderId();
		ropeRenderID = RenderingRegistry.getNextAvailableRenderId();
		orcChainRenderID = RenderingRegistry.getNextAvailableRenderId();
		guldurilRenderID = RenderingRegistry.getNextAvailableRenderId();
		orcPlatingRenderID = RenderingRegistry.getNextAvailableRenderId();
		trapdoorRenderID = RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(beaconRenderID, new LOTRRenderBlocks(true));
		RenderingRegistry.registerBlockHandler(barrelRenderID, new LOTRRenderBlocks(true));
		RenderingRegistry.registerBlockHandler(orcBombRenderID, new LOTRRenderBlocks(true));
		RenderingRegistry.registerBlockHandler(doubleTorchRenderID, new LOTRRenderBlocks(false));
		RenderingRegistry.registerBlockHandler(mobSpawnerRenderID, new LOTRRenderBlocks(true));
		RenderingRegistry.registerBlockHandler(plateRenderID, new LOTRRenderBlocks(false));
		RenderingRegistry.registerBlockHandler(stalactiteRenderID, new LOTRRenderBlocks(true));
		RenderingRegistry.registerBlockHandler(flowerPotRenderID, new LOTRRenderBlocks(false));
		RenderingRegistry.registerBlockHandler(cloverRenderID, new LOTRRenderBlocks(true));
		RenderingRegistry.registerBlockHandler(entJarRenderID, new LOTRRenderBlocks(true));
		RenderingRegistry.registerBlockHandler(trollTotemRenderID, new LOTRRenderBlocks(true));
		RenderingRegistry.registerBlockHandler(fenceRenderID, new LOTRRenderBlocks(true));
		RenderingRegistry.registerBlockHandler(grassRenderID, new LOTRRenderBlocks(false));
		RenderingRegistry.registerBlockHandler(fallenLeavesRenderID, new LOTRRenderBlocks(false));
		RenderingRegistry.registerBlockHandler(commandTableRenderID, new LOTRRenderBlocks(true));
		RenderingRegistry.registerBlockHandler(butterflyJarRenderID, new LOTRRenderBlocks(true));
		RenderingRegistry.registerBlockHandler(unsmelteryRenderID, new LOTRRenderBlocks(true));
		RenderingRegistry.registerBlockHandler(chestRenderID, new LOTRRenderBlocks(true));
		RenderingRegistry.registerBlockHandler(reedsRenderID, new LOTRRenderBlocks(false));
		RenderingRegistry.registerBlockHandler(wasteRenderID, new LOTRRenderBlocks(true));
		RenderingRegistry.registerBlockHandler(beamRenderID, new LOTRRenderBlocks(true));
		RenderingRegistry.registerBlockHandler(vCauldronRenderID, new LOTRRenderBlocks(false));
		RenderingRegistry.registerBlockHandler(grapevineRenderID, new LOTRRenderBlocks(true));
		RenderingRegistry.registerBlockHandler(thatchFloorRenderID, new LOTRRenderBlocks(false));
		RenderingRegistry.registerBlockHandler(treasureRenderID, new LOTRRenderBlocks(true));
		RenderingRegistry.registerBlockHandler(flowerRenderID, new LOTRRenderBlocks(false));
		RenderingRegistry.registerBlockHandler(doublePlantRenderID, new LOTRRenderBlocks(false));
		RenderingRegistry.registerBlockHandler(birdCageRenderID, new LOTRRenderBlocks(true));
		RenderingRegistry.registerBlockHandler(rhunFireJarRenderID, new LOTRRenderBlocks(true));
		RenderingRegistry.registerBlockHandler(coralRenderID, new LOTRRenderBlocks(true));
		RenderingRegistry.registerBlockHandler(doorRenderID, new LOTRRenderBlocks(false));
		RenderingRegistry.registerBlockHandler(ropeRenderID, new LOTRRenderBlocks(false));
		RenderingRegistry.registerBlockHandler(orcChainRenderID, new LOTRRenderBlocks(false));
		RenderingRegistry.registerBlockHandler(guldurilRenderID, new LOTRRenderBlocks(true));
		RenderingRegistry.registerBlockHandler(orcPlatingRenderID, new LOTRRenderBlocks(true));
		RenderingRegistry.registerBlockHandler(trapdoorRenderID, new LOTRRenderBlocks(true));
		ClientRegistry.bindTileEntitySpecialRenderer(LOTRTileEntityBeacon.class, new LOTRRenderBeacon());
		ClientRegistry.bindTileEntitySpecialRenderer(LOTRTileEntityMobSpawner.class, new LOTRTileEntityMobSpawnerRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(LOTRTileEntityPlate.class, new LOTRRenderPlateFood());
		ClientRegistry.bindTileEntitySpecialRenderer(LOTRTileEntityElvenPortal.class, new LOTRRenderElvenPortal());
		ClientRegistry.bindTileEntitySpecialRenderer(LOTRTileEntitySpawnerChest.class, new LOTRRenderSpawnerChest());
		ClientRegistry.bindTileEntitySpecialRenderer(LOTRTileEntityGulduril.class, new LOTRRenderGuldurilGlow());
		ClientRegistry.bindTileEntitySpecialRenderer(LOTRTileEntityDwarvenDoor.class, new LOTRRenderDwarvenDoor());
		ClientRegistry.bindTileEntitySpecialRenderer(LOTRTileEntityMorgulPortal.class, new LOTRRenderMorgulPortal());
		ClientRegistry.bindTileEntitySpecialRenderer(LOTRTileEntityArmorStand.class, new LOTRRenderArmorStand());
		ClientRegistry.bindTileEntitySpecialRenderer(LOTRTileEntityMug.class, new LOTRRenderMug());
		ClientRegistry.bindTileEntitySpecialRenderer(LOTRTileEntityEntJar.class, new LOTRRenderEntJar());
		ClientRegistry.bindTileEntitySpecialRenderer(LOTRTileEntityTrollTotem.class, new LOTRRenderTrollTotem());
		ClientRegistry.bindTileEntitySpecialRenderer(LOTRTileEntityUtumnoPortal.class, new LOTRRenderUtumnoPortal());
		ClientRegistry.bindTileEntitySpecialRenderer(LOTRTileEntityUtumnoReturnPortal.class, new LOTRRenderUtumnoReturnPortal());
		ClientRegistry.bindTileEntitySpecialRenderer(LOTRTileEntityCommandTable.class, new LOTRRenderCommandTable());
		ClientRegistry.bindTileEntitySpecialRenderer(LOTRTileEntityAnimalJar.class, new LOTRRenderAnimalJar());
		ClientRegistry.bindTileEntitySpecialRenderer(LOTRTileEntityUnsmeltery.class, new LOTRRenderUnsmeltery());
		ClientRegistry.bindTileEntitySpecialRenderer(LOTRTileEntityDartTrap.class, new LOTRRenderDartTrap());
		ClientRegistry.bindTileEntitySpecialRenderer(LOTRTileEntityChest.class, new LOTRRenderChest());
		ClientRegistry.bindTileEntitySpecialRenderer(LOTRTileEntityWeaponRack.class, new LOTRRenderWeaponRack());
		ClientRegistry.bindTileEntitySpecialRenderer(LOTRTileEntityKebabStand.class, new LOTRRenderKebabStand());
		ClientRegistry.bindTileEntitySpecialRenderer(LOTRTileEntitySignCarved.class, new LOTRRenderSignCarved());
		ClientRegistry.bindTileEntitySpecialRenderer(LOTRTileEntitySignCarvedIthildin.class, new LOTRRenderSignCarvedIthildin());
	}

	@Override
	public void onPostload() {
		if (LOTRConfig.updateLangFiles) {
			LOTRLang.runUpdateThread();
		}
		musicHandler = new LOTRMusic();
	}

	@Override
	public void onPreload() {
		System.setProperty("fml.skipFirstTextureLoad", "false");
		LOTRItemRendererManager.load();
		LOTRArmorModels.setupArmorModels();
	}

	@Override
	public void openHiredNPCGui(LOTREntityNPC npc) {
		Minecraft mc = Minecraft.getMinecraft();
		if (npc.hiredNPCInfo.getTask() == LOTRHiredNPCInfo.Task.WARRIOR) {
			mc.displayGuiScreen(new LOTRGuiHiredWarrior(npc));
		} else if (npc.hiredNPCInfo.getTask() == LOTRHiredNPCInfo.Task.FARMER) {
			mc.displayGuiScreen(new LOTRGuiHiredFarmer(npc));
		}
	}

	@Override
	public void placeFlowerInPot(World world, int i, int j, int k, int side, ItemStack itemstack) {
		if (world.isRemote) {
			Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(i, j, k, side, itemstack, 0.0f, 0.0f, 0.0f));
		} else {
			super.placeFlowerInPot(world, i, j, k, side, itemstack);
		}
	}

	@Override
	public void queueAchievement(LOTRAchievement achievement) {
		LOTRTickHandlerClient.notificationDisplay.queueAchievement(achievement);
	}

	@Override
	public void queueConquestNotification(LOTRFaction fac, float conq, boolean isCleansing) {
		LOTRTickHandlerClient.notificationDisplay.queueConquest(fac, conq, isCleansing);
	}

	@Override
	public void queueFellowshipNotification(IChatComponent message) {
		LOTRTickHandlerClient.notificationDisplay.queueFellowshipNotification(message);
	}

	@Override
	public void receiveConquestGrid(LOTRFaction conqFac, List<LOTRConquestZone> allZones) {
		Minecraft mc = Minecraft.getMinecraft();
		GuiScreen gui = mc.currentScreen;
		if (gui instanceof LOTRGuiMap) {
			((LOTRGuiMap) gui).receiveConquestGrid(conqFac, allZones);
		}
	}

	@Override
	public void renderCustomPotionEffect(int x, int y, PotionEffect effect, Minecraft mc) {
		Potion potion = Potion.potionTypes[effect.getPotionID()];
		mc.getTextureManager().bindTexture(customPotionsTexture);
		int l = potion.getStatusIconIndex();
		GuiScreen screen = mc.currentScreen;
		if (screen != null) {
			screen.drawTexturedModalRect(x + 6, y + 7, l % 8 * 18, l / 8 * 18, 18, 18);
		}
	}

	@Override
	public void setClientDifficulty(EnumDifficulty difficulty) {
		Minecraft.getMinecraft().gameSettings.difficulty = difficulty;
	}

	@Override
	public void setInElvenPortal(EntityPlayer entityplayer) {
		if (!LOTRTickHandlerClient.playersInElvenPortals.containsKey(entityplayer)) {
			LOTRTickHandlerClient.playersInElvenPortals.put(entityplayer, 0);
		}
		if (Minecraft.getMinecraft().isSingleplayer() && !LOTRTickHandlerServer.playersInElvenPortals.containsKey(entityplayer)) {
			LOTRTickHandlerServer.playersInElvenPortals.put(entityplayer, 0);
		}
	}

	@Override
	public void setInMorgulPortal(EntityPlayer entityplayer) {
		if (!LOTRTickHandlerClient.playersInMorgulPortals.containsKey(entityplayer)) {
			LOTRTickHandlerClient.playersInMorgulPortals.put(entityplayer, 0);
		}
		if (Minecraft.getMinecraft().isSingleplayer() && !LOTRTickHandlerServer.playersInMorgulPortals.containsKey(entityplayer)) {
			LOTRTickHandlerServer.playersInMorgulPortals.put(entityplayer, 0);
		}
	}

	@Override
	public void setInPortal(EntityPlayer entityplayer) {
		if (!LOTRTickHandlerClient.playersInPortals.containsKey(entityplayer)) {
			LOTRTickHandlerClient.playersInPortals.put(entityplayer, 0);
		}
		if (Minecraft.getMinecraft().isSingleplayer() && !LOTRTickHandlerServer.playersInPortals.containsKey(entityplayer)) {
			LOTRTickHandlerServer.playersInPortals.put(entityplayer, 0);
		}
	}

	@Override
	public void setInUtumnoReturnPortal(EntityPlayer entityplayer) {
		if (entityplayer == Minecraft.getMinecraft().thePlayer) {
			tickHandler.inUtumnoReturnPortal = true;
		}
	}

	@Override
	public void setMapCWPProtectionMessage(IChatComponent message) {
		Minecraft mc = Minecraft.getMinecraft();
		GuiScreen gui = mc.currentScreen;
		if (gui instanceof LOTRGuiMap) {
			((LOTRGuiMap) gui).setCWPProtectionMessage(message);
		}
	}

	@Override
	public void setMapIsOp(boolean isOp) {
		Minecraft mc = Minecraft.getMinecraft();
		GuiScreen gui = mc.currentScreen;
		if (gui instanceof LOTRGuiMap) {
			LOTRGuiMap map = (LOTRGuiMap) gui;
			map.isPlayerOp = isOp;
		}
	}

	@Override
	public void setTrackedQuest(LOTRMiniQuest quest) {
		LOTRTickHandlerClient.miniquestTracker.setTrackedQuest(quest);
	}

	@Override
	public void setUtumnoReturnPortalCoords(EntityPlayer entityplayer, int x, int z) {
		if (entityplayer == Minecraft.getMinecraft().thePlayer) {
			tickHandler.inUtumnoReturnPortal = true;
			tickHandler.utumnoReturnX = x;
			tickHandler.utumnoReturnZ = z;
		}
	}

	@Override
	public void setWaypointModes(boolean showWP, boolean showCWP, boolean showHiddenSWP) {
		LOTRGuiMap.showWP = showWP;
		LOTRGuiMap.showCWP = showCWP;
		LOTRGuiMap.showHiddenSWP = showHiddenSWP;
	}

	@Override
	public void showBurnOverlay() {
		tickHandler.onBurnDamage();
	}

	@Override
	public void showFrostOverlay() {
		tickHandler.onFrostDamage();
	}

	@Override
	public void spawnAlignmentBonus(LOTRFaction faction, float prevMainAlignment, LOTRAlignmentBonusMap factionBonusMap, String name, boolean isKill, boolean isHiredKill, float conquestBonus, double posX, double posY, double posZ) {
		World world = getClientWorld();
		if (world != null) {
			LOTREntityAlignmentBonus entity = new LOTREntityAlignmentBonus(world, posX, posY, posZ, name, faction, prevMainAlignment, factionBonusMap, isKill, isHiredKill, conquestBonus);
			world.spawnEntityInWorld(entity);
		}
	}

	@Override
	public void spawnParticle(String type, double d, double d1, double d2, double d3, double d4, double d5) {
		Minecraft mc = Minecraft.getMinecraft();
		if (mc.renderViewEntity != null && mc.theWorld != null) {
			WorldClient world = mc.theWorld;
			Random rand = world.rand;
			int i = mc.gameSettings.particleSetting;
			if (i == 1 && rand.nextInt(3) == 0) {
				i = 2;
			}
			if (i > 1) {
				return;
			}
			if ("angry".equals(type)) {
				customEffectRenderer.addEffect(new LOTREntityAngryFX(world, d, d1, d2, d3, d4, d5));
			} else if ("blueFlame".equals(type)) {
				customEffectRenderer.addEffect(new LOTREntityBlueFlameFX(world, d, d1, d2, d3, d4, d5));
			} else if ("chill".equals(type)) {
				mc.effectRenderer.addEffect(new LOTREntityChillFX(world, d, d1, d2, d3, d4, d5));
			} else if (type.startsWith("elvenGlow")) {
				LOTREntityElvenGlowFX fx = new LOTREntityElvenGlowFX(world, d, d1, d2, d3, d4, d5);
				int subIndex = type.indexOf('_');
				if (subIndex > -1) {
					String hex = type.substring(subIndex + 1);
					int color = Integer.parseInt(hex, 16);
					fx.setElvenGlowColor(color);
				}
				mc.effectRenderer.addEffect(fx);
			} else if ("gandalfFireball".equals(type)) {
				mc.effectRenderer.addEffect(new LOTREntityGandalfFireballExplodeFX(world, d, d1, d2));
			} else if ("largeStone".equals(type)) {
				mc.effectRenderer.addEffect(new LOTREntityLargeBlockFX(world, d, d1, d2, d3, d4, d5, Blocks.stone, 0));
			} else if (type.startsWith("leaf")) {
				String s = type.substring("leaf".length());
				int[] texIndices = null;
				if (s.startsWith("Gold")) {
					texIndices = rand.nextBoolean() ? LOTRFunctions.intRange(0, 5) : LOTRFunctions.intRange(8, 13);
				} else if (s.startsWith("Red")) {
					texIndices = rand.nextBoolean() ? LOTRFunctions.intRange(16, 21) : LOTRFunctions.intRange(24, 29);
				} else if (s.startsWith("Mirk")) {
					texIndices = rand.nextBoolean() ? LOTRFunctions.intRange(32, 37) : LOTRFunctions.intRange(40, 45);
				} else if (s.startsWith("Green")) {
					texIndices = rand.nextBoolean() ? LOTRFunctions.intRange(48, 53) : LOTRFunctions.intRange(56, 61);
				}
				if (texIndices != null) {
					if (type.contains("_")) {
						int age = Integer.parseInt(type.substring(type.indexOf('_') + 1));
						customEffectRenderer.addEffect(new LOTREntityLeafFX(world, d, d1, d2, d3, d4, d5, texIndices, age));
					} else {
						customEffectRenderer.addEffect(new LOTREntityLeafFX(world, d, d1, d2, d3, d4, d5, texIndices));
					}
				}
			} else if ("marshFlame".equals(type)) {
				mc.effectRenderer.addEffect(new LOTREntityMarshFlameFX(world, d, d1, d2, d3, d4, d5));
			} else if ("marshLight".equals(type)) {
				customEffectRenderer.addEffect(new LOTREntityMarshLightFX(world, d, d1, d2, d3, d4, d5));
			} else if (type.startsWith("mEntHeal")) {
				String[] args = type.split("_", 3);
				Block block = Block.getBlockById(Integer.parseInt(args[1]));
				int meta = Integer.parseInt(args[2]);
				int color = block.getRenderColor(meta);
				mc.effectRenderer.addEffect(new LOTREntityMallornEntHealFX(world, d, d1, d2, d3, d4, d5, block, meta, color));
			} else if ("mEntJumpSmash".equals(type)) {
				mc.effectRenderer.addEffect(new LOTREntityLargeBlockFX(world, d, d1, d2, d3, d4, d5, LOTRMod.wood, 1));
			} else if ("mEntSpawn".equals(type)) {
				Block block;
				int meta;
				if (world.rand.nextBoolean()) {
					block = Blocks.dirt;
					meta = 0;
				} else {
					block = LOTRMod.wood;
					meta = 1;
				}
				mc.effectRenderer.addEffect(new LOTREntityBossSpawnFX(world, d, d1, d2, d3, d4, d5, block, meta));
			} else if (type.startsWith("mEntSummon")) {
				String[] args = type.split("_", 6);
				int summonerID = Integer.parseInt(args[1]);
				int summonedID = Integer.parseInt(args[2]);
				float arcParam = Float.parseFloat(args[3]);
				Block block = Block.getBlockById(Integer.parseInt(args[4]));
				int meta = Integer.parseInt(args[5]);
				int color = block.getRenderColor(meta);
				mc.effectRenderer.addEffect(new LOTREntityMallornEntSummonFX(world, d, d1, d2, d3, d4, d5, summonerID, summonedID, arcParam, block, meta, color));
			} else if ("mirkwoodWater".equals(type)) {
				mc.effectRenderer.addEffect(new LOTREntityRiverWaterFX(world, d, d1, d2, d3, d4, d5, LOTRBiome.mirkwoodCorrupted.getWaterColorMultiplier()));
			} else if ("morgulPortal".equals(type)) {
				mc.effectRenderer.addEffect(new LOTREntityMorgulPortalFX(world, d, d1, d2, d3, d4, d5));
			} else if ("morgulWater".equals(type)) {
				mc.effectRenderer.addEffect(new LOTREntityRiverWaterFX(world, d, d1, d2, d3, d4, d5, LOTRBiome.morgulVale.getWaterColorMultiplier()));
			} else if ("mtcArmor".equals(type)) {
				mc.effectRenderer.addEffect(new LOTREntityLargeBlockFX(world, d, d1, d2, d3, d4, d5, Blocks.iron_block, 0));
			} else if ("mtcHeal".equals(type)) {
				mc.effectRenderer.addEffect(new LOTREntityMTCHealFX(world, d, d1, d2, d3, d4, d5));
			} else if ("mtcSpawn".equals(type)) {
				Block block;
				int meta = 0;
				if (world.rand.nextBoolean()) {
					block = Blocks.stone;
				} else if (world.rand.nextBoolean()) {
					block = Blocks.dirt;
				} else if (world.rand.nextBoolean()) {
					block = Blocks.gravel;
				} else {
					block = Blocks.sand;
				}
				mc.effectRenderer.addEffect(new LOTREntityBossSpawnFX(world, d, d1, d2, d3, d4, d5, block, meta));
			} else if ("music".equals(type)) {
				double pitch = world.rand.nextDouble();
				LOTREntityMusicFX note = new LOTREntityMusicFX(world, d, d1, d2, d3, d4, d5, pitch);
				mc.effectRenderer.addEffect(note);
			} else if ("pickpocket".equals(type)) {
				customEffectRenderer.addEffect(new LOTREntityPickpocketFX(world, d, d1, d2, d3, d4, d5));
			} else if ("pickpocketFail".equals(type)) {
				customEffectRenderer.addEffect(new LOTREntityPickpocketFailFX(world, d, d1, d2, d3, d4, d5));
			} else if ("quenditeSmoke".equals(type)) {
				mc.effectRenderer.addEffect(new LOTREntityQuenditeSmokeFX(world, d, d1, d2, d3, d4, d5));
			} else if ("utumnoKill".equals(type)) {
				customEffectRenderer.addEffect(new LOTREntityUtumnoKillFX(world, d, d1, d2, d3, d4, d5));
			} else if ("wave".equals(type)) {
				mc.effectRenderer.addEffect(new LOTREntityWaveFX(world, d, d1, d2, d3, d4, d5));
			} else if ("whiteSmoke".equals(type)) {
				mc.effectRenderer.addEffect(new LOTREntityWhiteSmokeFX(world, d, d1, d2, d3, d4, d5));
			}
		}
	}

	@Override
	public void testReflection(World world) {
		super.testReflection(world);
		LOTRReflectionClient.testAll(world, Minecraft.getMinecraft());
	}

	@Override
	public void usePouchOnChest(EntityPlayer entityplayer, World world, int i, int j, int k, int side, ItemStack itemstack, int pouchSlot) {
		if (world.isRemote) {
			((EntityClientPlayerMP) entityplayer).sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(i, j, k, side, itemstack, 0.0f, 0.0f, 0.0f));
		} else {
			super.usePouchOnChest(entityplayer, world, i, j, k, side, itemstack, pouchSlot);
		}
	}

	@Override
	public void validateBannerUsername(LOTREntityBanner banner, int slot, String prevText, boolean valid) {
		Minecraft mc = Minecraft.getMinecraft();
		GuiScreen gui = mc.currentScreen;
		if (gui instanceof LOTRGuiBanner) {
			LOTRGuiBanner guiBanner = (LOTRGuiBanner) gui;
			if (guiBanner.theBanner == banner) {
				guiBanner.validateUsername(slot, prevText, valid);
			}
		}
	}
}
