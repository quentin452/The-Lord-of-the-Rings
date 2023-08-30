package lotr.common;

import java.util.*;

import com.mojang.authlib.GameProfile;

import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import lotr.client.gui.*;
import lotr.common.block.LOTRBlockFlowerPot;
import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.animal.LOTREntityHorse;
import lotr.common.entity.item.LOTREntityBanner;
import lotr.common.entity.npc.*;
import lotr.common.fac.*;
import lotr.common.inventory.*;
import lotr.common.item.*;
import lotr.common.network.*;
import lotr.common.quest.LOTRMiniQuest;
import lotr.common.tileentity.*;
import lotr.common.world.map.*;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityMinecartContainer;
import net.minecraft.entity.player.*;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.*;

public class LOTRCommonProxy implements IGuiHandler {
	public static int GUI_ID_HOBBIT_OVEN;
	public static int GUI_ID_MORGUL_TABLE = 1;
	public static int GUI_ID_ELVEN_TABLE = 2;
	public static int GUI_ID_TRADE = 3;
	public static int GUI_ID_DWARVEN_TABLE = 4;
	public static int GUI_ID_ALLOY_FORGE = 5;
	public static int GUI_ID_MOB_SPAWNER = 6;
	public static int GUI_ID_UNIT_TRADE = 7;
	public static int GUI_ID_URUK_TABLE = 8;
	public static int GUI_ID_HORN_SELECT = 9;
	public static int GUI_ID_GOLLUM = 10;
	public static int GUI_ID_LOTR = 11;
	public static int GUI_ID_WOOD_ELVEN_TABLE = 12;
	public static int GUI_ID_GONDORIAN_TABLE = 13;
	public static int GUI_ID_ROHIRRIC_TABLE = 14;
	public static int GUI_ID_POUCH = 15;
	public static int GUI_ID_BARREL = 16;
	public static int GUI_ID_ARMOR_STAND = 17;
	public static int GUI_ID_DUNLENDING_TABLE = 18;
	public static int GUI_ID_TRADE_INTERACT = 19;
	public static int GUI_ID_UNIT_TRADE_INTERACT = 20;
	public static int GUI_ID_HIRED_INTERACT = 21;
	public static int GUI_ID_HIRED_FARMER_INVENTORY = 22;
	public static int GUI_ID_ANGMAR_TABLE = 23;
	public static int GUI_ID_TRADE_UNIT_TRADE_INTERACT = 24;
	public static int GUI_ID_NEAR_HARAD_TABLE = 25;
	public static int GUI_ID_HIGH_ELVEN_TABLE = 26;
	public static int GUI_ID_BLUE_DWARVEN_TABLE = 27;
	public static int GUI_ID_RANGER_TABLE = 28;
	public static int GUI_ID_MOUNT_INV = 29;
	public static int GUI_ID_DOL_GULDUR_TABLE = 30;
	public static int GUI_ID_GUNDABAD_TABLE = 31;
	public static int GUI_ID_RED_BOOK = 32;
	public static int GUI_ID_SQUADRON_ITEM = 33;
	public static int GUI_ID_HALF_TROLL_TABLE = 34;
	public static int GUI_ID_COIN_EXCHANGE = 35;
	public static int GUI_ID_DOL_AMROTH_TABLE = 36;
	public static int GUI_ID_MOREDAIN_TABLE = 37;
	public static int GUI_ID_UNSMELTERY = 38;
	public static int GUI_ID_TAUREDAIN_TABLE = 39;
	public static int GUI_ID_DART_TRAP = 40;
	public static int GUI_ID_CHEST = 41;
	public static int GUI_ID_DALE_TABLE = 42;
	public static int GUI_ID_DORWINION_TABLE = 43;
	public static int GUI_ID_HOBBIT_TABLE = 44;
	public static int GUI_ID_RESPAWNER = 45;
	public static int GUI_ID_HIRED_WARRIOR_INVENTORY = 46;
	public static int GUI_ID_EDIT_SIGN = 47;
	public static int GUI_ID_DALE_CRACKER = 48;
	public static int GUI_ID_RHUN_TABLE = 49;
	public static int GUI_ID_BEACON = 50;
	public static int GUI_ID_RIVENDELL_TABLE = 51;
	public static int GUI_ID_MILLSTONE = 52;
	public static int GUI_ID_ANVIL = 53;
	public static int GUI_ID_ANVIL_TRADER = 54;
	public static int GUI_ID_BOOKSHELF = 55;
	public static int GUI_ID_UMBAR_TABLE = 56;
	public static int GUI_ID_GULF_TABLE = 57;
	public static int GUI_ID_MERCENARY_INTERACT = 58;
	public static int GUI_ID_MERCENARY_TRADE = 59;
	public static int GUI_ID_CONQUEST_GRID = 60;
	public static int GUI_ID_BRANDING_IRON = 61;
	public static int GUI_ID_BREE_TABLE = 62;
	public static int GUI_ID_CHEST_WITH_POUCH = 63;
	public static int GUI_ID_MINECART_CHEST_WITH_POUCH = 64;

	public void addMapPlayerLocation(GameProfile player, double posX, double posZ) {
	}

	public void cancelItemHighlight() {
	}

	public void clearMapPlayerLocations() {
	}

	public void clientReceiveSpeech(LOTREntityNPC npc, String speech) {
	}

	public void displayAlignDrain(int numFactions) {
	}

	public void displayAlignmentChoice() {
	}

	public void displayAlignmentSee(String username, Map<LOTRFaction, Float> alignments) {
	}

	public void displayBannerGui(LOTREntityBanner banner) {
	}

	public void displayFellowshipAcceptInvitationResult(UUID fellowshipID, String name, LOTRPacketFellowshipAcceptInviteResult.AcceptInviteResult result) {
	}

	public void displayFTScreen(LOTRAbstractWaypoint waypoint, int startX, int startZ) {
	}

	public void displayMessage(LOTRGuiMessageTypes message) {
	}

	public void displayMiniquestOffer(LOTRMiniQuest quest, LOTREntityNPC npc) {
	}

	public void displayNewDate() {
	}

	public void fillMugFromCauldron(World world, int i, int j, int k, int side, ItemStack itemstack) {
		world.setBlockMetadataWithNotify(i, j, k, world.getBlockMetadata(i, j, k) - 1, 3);
	}

	public int getBarrelRenderID() {
		return 0;
	}

	public int getBeaconRenderID() {
		return 0;
	}

	public int getBeamRenderID() {
		return 0;
	}

	public int getBirdCageRenderID() {
		return 0;
	}

	public int getButterflyJarRenderID() {
		return 0;
	}

	public int getChestRenderID() {
		return 0;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer entityplayer, World world, int i, int j, int k) {
		TileEntity beacon;
		TileEntity stand;
		TileEntity unsmeltery;
		TileEntity forge;
		TileEntity chest;
		LOTREntityNPC npc;
		TileEntity oven;
		Entity entity;
		TileEntity trap;
		TileEntity millstone;
		TileEntity barrel;
		if (ID == 0 && (oven = world.getTileEntity(i, j, k)) instanceof LOTRTileEntityHobbitOven) {
			return new LOTRGuiHobbitOven(entityplayer.inventory, (LOTRTileEntityHobbitOven) oven);
		}
		if (ID == 1) {
			return new LOTRGuiCraftingTable.Morgul(entityplayer.inventory, world, i, j, k);
		}
		if (ID == 2) {
			return new LOTRGuiCraftingTable.Elven(entityplayer.inventory, world, i, j, k);
		}
		if (ID == 3 && (entity = world.getEntityByID(i)) instanceof LOTRTradeable) {
			return new LOTRGuiTrade(entityplayer.inventory, (LOTRTradeable) entity, world);
		}
		if (ID == 4) {
			return new LOTRGuiCraftingTable.Dwarven(entityplayer.inventory, world, i, j, k);
		}
		if (ID == 5 && (forge = world.getTileEntity(i, j, k)) instanceof LOTRTileEntityAlloyForgeBase) {
			return new LOTRGuiAlloyForge(entityplayer.inventory, (LOTRTileEntityAlloyForgeBase) forge);
		}
		if (ID == 6) {
			return new LOTRGuiMobSpawner(world, i, j, k);
		}
		if (ID == 7 && (entity = world.getEntityByID(i)) instanceof LOTRUnitTradeable) {
			return new LOTRGuiUnitTrade(entityplayer, (LOTRUnitTradeable) entity, world);
		}
		if (ID == 8) {
			return new LOTRGuiCraftingTable.Uruk(entityplayer.inventory, world, i, j, k);
		}
		if (ID == 9) {
			return new LOTRGuiHornSelect();
		}
		if (ID == 10 && (entity = world.getEntityByID(i)) instanceof LOTREntityGollum) {
			return new LOTRGuiGollum(entityplayer.inventory, (LOTREntityGollum) entity);
		}
		switch (ID) {
			case 11:
				return LOTRGuiMenu.openMenu(entityplayer);
			case 12:
				return new LOTRGuiCraftingTable.WoodElven(entityplayer.inventory, world, i, j, k);
			case 13:
				return new LOTRGuiCraftingTable.Gondorian(entityplayer.inventory, world, i, j, k);
			case 14:
				return new LOTRGuiCraftingTable.Rohirric(entityplayer.inventory, world, i, j, k);
			case 15:
				return new LOTRGuiPouch(entityplayer, i);
			default:
				break;
		}
		if (ID == 16 && (barrel = world.getTileEntity(i, j, k)) instanceof LOTRTileEntityBarrel) {
			return new LOTRGuiBarrel(entityplayer.inventory, (LOTRTileEntityBarrel) barrel);
		}
		if (ID == 17 && (stand = world.getTileEntity(i, j, k)) instanceof LOTRTileEntityArmorStand) {
			return new LOTRGuiArmorStand(entityplayer.inventory, (LOTRTileEntityArmorStand) stand);
		}
		if (ID == 18) {
			return new LOTRGuiCraftingTable.Dunlending(entityplayer.inventory, world, i, j, k);
		}
		if (ID == 19 && (entity = world.getEntityByID(i)) instanceof LOTRTradeable) {
			return new LOTRGuiTradeInteract((LOTREntityNPC) entity);
		}
		if (ID == 20 && (entity = world.getEntityByID(i)) instanceof LOTRUnitTradeable) {
			return new LOTRGuiUnitTradeInteract((LOTREntityNPC) entity);
		}
		if (ID == 21 && (entity = world.getEntityByID(i)) instanceof LOTREntityNPC) {
			return new LOTRGuiHiredInteract((LOTREntityNPC) entity);
		}
		if (ID == 22 && (entity = world.getEntityByID(i)) instanceof LOTREntityNPC) {
			npc = (LOTREntityNPC) entity;
			if (npc.hiredNPCInfo.isActive && npc.hiredNPCInfo.getHiringPlayer() == entityplayer && npc.hiredNPCInfo.getTask() == LOTRHiredNPCInfo.Task.FARMER) {
				return new LOTRGuiHiredFarmerInventory(entityplayer.inventory, npc);
			}
		}
		if (ID == 23) {
			return new LOTRGuiCraftingTable.Angmar(entityplayer.inventory, world, i, j, k);
		}
		if (ID == 24 && (entity = world.getEntityByID(i)) instanceof LOTRTradeable) {
			return new LOTRGuiTradeUnitTradeInteract((LOTREntityNPC) entity);
		}
		switch (ID) {
			case 25:
				return new LOTRGuiCraftingTable.NearHarad(entityplayer.inventory, world, i, j, k);
			case 26:
				return new LOTRGuiCraftingTable.HighElven(entityplayer.inventory, world, i, j, k);
			case 27:
				return new LOTRGuiCraftingTable.BlueDwarven(entityplayer.inventory, world, i, j, k);
			case 28:
				return new LOTRGuiCraftingTable.Ranger(entityplayer.inventory, world, i, j, k);
			case 29: {
				LOTREntityNPCRideable npc2;
				entity = world.getEntityByID(i);
				if (entity instanceof LOTREntityHorse) {
					LOTREntityHorse horse = (LOTREntityHorse) entity;
					return new LOTRGuiMountInventory(entityplayer.inventory, new AnimalChest(horse.getCommandSenderName(), j), horse);
				}
				if (entity instanceof LOTREntityNPCRideable && (npc2 = (LOTREntityNPCRideable) entity).getMountInventory() != null) {
					return new LOTRGuiNPCMountInventory(entityplayer.inventory, new AnimalChest(npc2.getCommandSenderName(), j), npc2);
				}
				break;
			}
			default:
				break;
		}
		switch (ID) {
			case 30:
				return new LOTRGuiCraftingTable.DolGuldur(entityplayer.inventory, world, i, j, k);
			case 31:
				return new LOTRGuiCraftingTable.Gundabad(entityplayer.inventory, world, i, j, k);
			case 32:
				return new LOTRGuiRedBook();
			case 33:
				return new LOTRGuiSquadronItem();
			case 34:
				return new LOTRGuiCraftingTable.HalfTroll(entityplayer.inventory, world, i, j, k);
			default:
				break;
		}
		if (ID == 35 && (entity = world.getEntityByID(i)) instanceof LOTREntityNPC) {
			npc = (LOTREntityNPC) entity;
			return new LOTRGuiCoinExchange(entityplayer, npc);
		}
		if (ID == 36) {
			return new LOTRGuiCraftingTable.DolAmroth(entityplayer.inventory, world, i, j, k);
		}
		if (ID == 37) {
			return new LOTRGuiCraftingTable.Moredain(entityplayer.inventory, world, i, j, k);
		}
		if (ID == 38 && (unsmeltery = world.getTileEntity(i, j, k)) instanceof LOTRTileEntityUnsmeltery) {
			return new LOTRGuiUnsmeltery(entityplayer.inventory, (LOTRTileEntityUnsmeltery) unsmeltery);
		}
		if (ID == 39) {
			return new LOTRGuiCraftingTable.Tauredain(entityplayer.inventory, world, i, j, k);
		}
		if (ID == 40 && (trap = world.getTileEntity(i, j, k)) instanceof LOTRTileEntityDartTrap) {
			return new GuiDispenser(entityplayer.inventory, (TileEntityDispenser) trap);
		}
		if (ID == 41 && (chest = world.getTileEntity(i, j, k)) instanceof LOTRTileEntityChest) {
			return new GuiChest(entityplayer.inventory, (IInventory) chest);
		}
		switch (ID) {
			case 42:
				return new LOTRGuiCraftingTable.Dale(entityplayer.inventory, world, i, j, k);
			case 43:
				return new LOTRGuiCraftingTable.Dorwinion(entityplayer.inventory, world, i, j, k);
			case 44:
				return new LOTRGuiCraftingTable.Hobbit(entityplayer.inventory, world, i, j, k);
			default:
				break;
		}
		if (ID == 45 && (entity = world.getEntityByID(i)) instanceof LOTREntityNPCRespawner) {
			return new LOTRGuiNPCRespawner((LOTREntityNPCRespawner) entity);
		}
		if (ID == 46 && (entity = world.getEntityByID(i)) instanceof LOTREntityNPC) {
			npc = (LOTREntityNPC) entity;
			if (npc.hiredNPCInfo.isActive && npc.hiredNPCInfo.getHiringPlayer() == entityplayer && npc.hiredNPCInfo.getTask() == LOTRHiredNPCInfo.Task.WARRIOR) {
				return new LOTRGuiHiredWarriorInventory(entityplayer.inventory, npc);
			}
		}
		if (ID == 47) {
			Block block = world.getBlock(i, j, k);
			int meta = world.getBlockMetadata(i, j, k);
			LOTRTileEntitySign fake = (LOTRTileEntitySign) block.createTileEntity(world, meta);
			fake.setWorldObj(world);
			fake.xCoord = i;
			fake.yCoord = j;
			fake.zCoord = k;
			fake.isFakeGuiSign = true;
			return new LOTRGuiEditSign(fake);
		}
		if (ID == 48 && entityplayer.inventory.getCurrentItem() != null && entityplayer.inventory.getCurrentItem().getItem() instanceof LOTRItemDaleCracker) {
			return new LOTRGuiDaleCracker(entityplayer);
		}
		if (ID == 49) {
			return new LOTRGuiCraftingTable.Rhun(entityplayer.inventory, world, i, j, k);
		}
		if (ID == 50 && (beacon = world.getTileEntity(i, j, k)) instanceof LOTRTileEntityBeacon) {
			return new LOTRGuiBeacon((LOTRTileEntityBeacon) beacon);
		}
		if (ID == 51) {
			return new LOTRGuiCraftingTable.Rivendell(entityplayer.inventory, world, i, j, k);
		}
		if (ID == 52 && (millstone = world.getTileEntity(i, j, k)) instanceof LOTRTileEntityMillstone) {
			return new LOTRGuiMillstone(entityplayer.inventory, (LOTRTileEntityMillstone) millstone);
		}
		if (ID == 53) {
			return new LOTRGuiAnvil(entityplayer, i, j, k);
		}
		if (ID == 54 && (entity = world.getEntityByID(i)) instanceof LOTREntityNPC) {
			return new LOTRGuiAnvil(entityplayer, (LOTREntityNPC) entity);
		}
		if (ID == 55) {
			TileEntity bookshelf;
			if (world.getBlock(i, j, k) == Blocks.bookshelf) {
				world.setBlock(i, j, k, LOTRMod.bookshelfStorage, 0, 3);
			}
			if ((bookshelf = world.getTileEntity(i, j, k)) instanceof LOTRTileEntityBookshelf) {
				return new LOTRGuiBookshelf(entityplayer.inventory, (LOTRTileEntityBookshelf) bookshelf);
			}
		}
		if (ID == 56) {
			return new LOTRGuiCraftingTable.Umbar(entityplayer.inventory, world, i, j, k);
		}
		if (ID == 57) {
			return new LOTRGuiCraftingTable.Gulf(entityplayer.inventory, world, i, j, k);
		}
		if (ID == 58 && (entity = world.getEntityByID(i)) instanceof LOTRMercenary) {
			return new LOTRGuiMercenaryInteract((LOTREntityNPC) entity);
		}
		if (ID == 59 && (entity = world.getEntityByID(i)) instanceof LOTRMercenary) {
			return new LOTRGuiMercenaryHire(entityplayer, (LOTRMercenary) entity, world);
		}
		switch (ID) {
			case 60:
				return new LOTRGuiMap().setConquestGrid();
			case 61:
				return new LOTRGuiBrandingIron();
			case 62:
				return new LOTRGuiCraftingTable.Bree(entityplayer.inventory, world, i, j, k);
			default:
				break;
		}
		if (testForSlotPackedGuiID(ID, 63)) {
			int slot = unpackSlot(ID);
			IInventory chest2 = LOTRItemPouch.getChestInvAt(entityplayer, world, i, j, k);
			if (chest2 != null) {
				return new LOTRGuiChestWithPouch(entityplayer, slot, chest2);
			}
		}
		if (testForSlotPackedGuiID(ID, 64)) {
			int slot = unpackSlot(ID);
			Entity minecart = world.getEntityByID(i);
			if (minecart instanceof EntityMinecartContainer) {
				return new LOTRGuiChestWithPouch(entityplayer, slot, (IInventory) minecart);
			}
		}
		return null;
	}

	public EntityPlayer getClientPlayer() {
		return null;
	}

	public World getClientWorld() {
		return null;
	}

	public int getCloverRenderID() {
		return 0;
	}

	public int getCommandTableRenderID() {
		return 0;
	}

	public int getCoralRenderID() {
		return 0;
	}

	public int getDoorRenderID() {
		return 0;
	}

	public int getDoublePlantRenderID() {
		return 0;
	}

	public int getDoubleTorchRenderID() {
		return 0;
	}

	public int getEntJarRenderID() {
		return 0;
	}

	public int getFallenLeavesRenderID() {
		return 0;
	}

	public int getFenceRenderID() {
		return 0;
	}

	public int getFlowerPotRenderID() {
		return 0;
	}

	public int getFlowerRenderID() {
		return 0;
	}

	public int getGrapevineRenderID() {
		return 0;
	}

	public int getGrassRenderID() {
		return 0;
	}

	public int getGuldurilRenderID() {
		return 0;
	}

	public int getMobSpawnerRenderID() {
		return 0;
	}

	public int getOrcBombRenderID() {
		return 0;
	}

	public int getOrcChainRenderID() {
		return 0;
	}

	public int getOrcPlatingRenderID() {
		return 0;
	}

	public int getPlateRenderID() {
		return 0;
	}

	public int getReedsRenderID() {
		return 0;
	}

	public int getRhunFireJarRenderID() {
		return 0;
	}

	public int getRopeRenderID() {
		return 0;
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer entityplayer, World world, int i, int j, int k) {
		TileEntity beacon;
		TileEntity stand;
		TileEntity unsmeltery;
		TileEntity forge;
		TileEntity chest;
		IInventory chest2;
		LOTREntityNPC npc;
		TileEntity oven;
		Entity entity;
		TileEntity trap;
		int slot;
		TileEntity millstone;
		TileEntity barrel;
		TileEntity bookshelf;
		Entity minecart;
		if (ID == 0 && (oven = world.getTileEntity(i, j, k)) instanceof LOTRTileEntityHobbitOven) {
			return new LOTRContainerHobbitOven(entityplayer.inventory, (LOTRTileEntityHobbitOven) oven);
		}
		if (ID == 1) {
			return new LOTRContainerCraftingTable.Morgul(entityplayer.inventory, world, i, j, k);
		}
		if (ID == 2) {
			return new LOTRContainerCraftingTable.Elven(entityplayer.inventory, world, i, j, k);
		}
		if (ID == 3 && (entity = world.getEntityByID(i)) instanceof LOTRTradeable) {
			return new LOTRContainerTrade(entityplayer.inventory, (LOTRTradeable) entity, world);
		}
		if (ID == 4) {
			return new LOTRContainerCraftingTable.Dwarven(entityplayer.inventory, world, i, j, k);
		}
		if (ID == 5 && (forge = world.getTileEntity(i, j, k)) instanceof LOTRTileEntityAlloyForgeBase) {
			return new LOTRContainerAlloyForge(entityplayer.inventory, (LOTRTileEntityAlloyForgeBase) forge);
		}
		if (ID == 7 && (entity = world.getEntityByID(i)) instanceof LOTRUnitTradeable) {
			return new LOTRContainerUnitTrade(entityplayer, (LOTRHireableBase) entity, world);
		}
		if (ID == 8) {
			return new LOTRContainerCraftingTable.Uruk(entityplayer.inventory, world, i, j, k);
		}
		if (ID == 10 && (entity = world.getEntityByID(i)) instanceof LOTREntityGollum) {
			return new LOTRContainerGollum(entityplayer.inventory, (LOTREntityGollum) entity);
		}
		switch (ID) {
			case 12:
				return new LOTRContainerCraftingTable.WoodElven(entityplayer.inventory, world, i, j, k);
			case 13:
				return new LOTRContainerCraftingTable.Gondorian(entityplayer.inventory, world, i, j, k);
			case 14:
				return new LOTRContainerCraftingTable.Rohirric(entityplayer.inventory, world, i, j, k);
			default:
				break;
		}
		if (ID == 15 && LOTRItemPouch.isHoldingPouch(entityplayer, i)) {
			return new LOTRContainerPouch(entityplayer, i);
		}
		if (ID == 16 && (barrel = world.getTileEntity(i, j, k)) instanceof LOTRTileEntityBarrel) {
			return new LOTRContainerBarrel(entityplayer.inventory, (LOTRTileEntityBarrel) barrel);
		}
		if (ID == 17 && (stand = world.getTileEntity(i, j, k)) instanceof LOTRTileEntityArmorStand) {
			return new LOTRContainerArmorStand(entityplayer.inventory, (LOTRTileEntityArmorStand) stand);
		}
		if (ID == 18) {
			return new LOTRContainerCraftingTable.Dunlending(entityplayer.inventory, world, i, j, k);
		}
		if (ID == 22 && (entity = world.getEntityByID(i)) instanceof LOTREntityNPC) {
			npc = (LOTREntityNPC) entity;
			if (npc.hiredNPCInfo.isActive && npc.hiredNPCInfo.getHiringPlayer() == entityplayer && npc.hiredNPCInfo.getTask() == LOTRHiredNPCInfo.Task.FARMER) {
				return new LOTRContainerHiredFarmerInventory(entityplayer.inventory, npc);
			}
		}
		switch (ID) {
			case 23:
				return new LOTRContainerCraftingTable.Angmar(entityplayer.inventory, world, i, j, k);
			case 25:
				return new LOTRContainerCraftingTable.NearHarad(entityplayer.inventory, world, i, j, k);
			case 26:
				return new LOTRContainerCraftingTable.HighElven(entityplayer.inventory, world, i, j, k);
			case 27:
				return new LOTRContainerCraftingTable.BlueDwarven(entityplayer.inventory, world, i, j, k);
			case 28:
				return new LOTRContainerCraftingTable.Ranger(entityplayer.inventory, world, i, j, k);
			case 29:
				entity = world.getEntityByID(i);
				if (entity instanceof LOTREntityHorse) {
					LOTREntityHorse horse = (LOTREntityHorse) entity;
					return new LOTRContainerMountInventory(entityplayer.inventory, LOTRReflection.getHorseInv(horse), horse);
				}
				if (entity instanceof LOTREntityNPCRideable && ((LOTREntityNPCRideable) (npc = (LOTREntityNPC) entity)).getMountInventory() != null) {
					return new LOTRContainerNPCMountInventory(entityplayer.inventory, ((LOTREntityNPCRideable) npc).getMountInventory(), (LOTREntityNPCRideable) npc);
				}
				break;
			default:
				break;
		}
		switch (ID) {
			case 30:
				return new LOTRContainerCraftingTable.DolGuldur(entityplayer.inventory, world, i, j, k);
			case 31:
				return new LOTRContainerCraftingTable.Gundabad(entityplayer.inventory, world, i, j, k);
			case 34:
				return new LOTRContainerCraftingTable.HalfTroll(entityplayer.inventory, world, i, j, k);
			default:
				break;
		}
		if (ID == 35 && (entity = world.getEntityByID(i)) instanceof LOTREntityNPC) {
			npc = (LOTREntityNPC) entity;
			return new LOTRContainerCoinExchange(entityplayer, npc);
		}
		if (ID == 36) {
			return new LOTRContainerCraftingTable.DolAmroth(entityplayer.inventory, world, i, j, k);
		}
		if (ID == 37) {
			return new LOTRContainerCraftingTable.Moredain(entityplayer.inventory, world, i, j, k);
		}
		if (ID == 38 && (unsmeltery = world.getTileEntity(i, j, k)) instanceof LOTRTileEntityUnsmeltery) {
			return new LOTRContainerUnsmeltery(entityplayer.inventory, (LOTRTileEntityUnsmeltery) unsmeltery);
		}
		if (ID == 39) {
			return new LOTRContainerCraftingTable.Tauredain(entityplayer.inventory, world, i, j, k);
		}
		if (ID == 40 && (trap = world.getTileEntity(i, j, k)) instanceof LOTRTileEntityDartTrap) {
			return new ContainerDispenser(entityplayer.inventory, (TileEntityDispenser) trap);
		}
		if (ID == 41 && (chest = world.getTileEntity(i, j, k)) instanceof LOTRTileEntityChest) {
			return new ContainerChest(entityplayer.inventory, (IInventory) chest);
		}
		switch (ID) {
			case 42:
				return new LOTRContainerCraftingTable.Dale(entityplayer.inventory, world, i, j, k);
			case 43:
				return new LOTRContainerCraftingTable.Dorwinion(entityplayer.inventory, world, i, j, k);
			case 44:
				return new LOTRContainerCraftingTable.Hobbit(entityplayer.inventory, world, i, j, k);
			default:
				break;
		}
		if (ID == 46 && (entity = world.getEntityByID(i)) instanceof LOTREntityNPC) {
			npc = (LOTREntityNPC) entity;
			if (npc.hiredNPCInfo.isActive && npc.hiredNPCInfo.getHiringPlayer() == entityplayer && npc.hiredNPCInfo.getTask() == LOTRHiredNPCInfo.Task.WARRIOR) {
				return new LOTRContainerHiredWarriorInventory(entityplayer.inventory, npc);
			}
		}
		if (ID == 48 && entityplayer.inventory.getCurrentItem() != null && entityplayer.inventory.getCurrentItem().getItem() instanceof LOTRItemDaleCracker) {
			return new LOTRContainerDaleCracker(entityplayer);
		}
		if (ID == 49) {
			return new LOTRContainerCraftingTable.Rhun(entityplayer.inventory, world, i, j, k);
		}
		if (ID == 50 && (beacon = world.getTileEntity(i, j, k)) instanceof LOTRTileEntityBeacon) {
			((LOTRTileEntityBeacon) beacon).addEditingPlayer(entityplayer);
		}
		if (ID == 51) {
			return new LOTRContainerCraftingTable.Rivendell(entityplayer.inventory, world, i, j, k);
		}
		if (ID == 52 && (millstone = world.getTileEntity(i, j, k)) instanceof LOTRTileEntityMillstone) {
			return new LOTRContainerMillstone(entityplayer.inventory, (LOTRTileEntityMillstone) millstone);
		}
		if (ID == 53) {
			return new LOTRContainerAnvil(entityplayer, i, j, k);
		}
		if (ID == 54 && (entity = world.getEntityByID(i)) instanceof LOTREntityNPC) {
			return new LOTRContainerAnvil(entityplayer, (LOTREntityNPC) entity);
		}
		if (ID == 55 && (bookshelf = world.getTileEntity(i, j, k)) instanceof LOTRTileEntityBookshelf) {
			return new LOTRContainerBookshelf(entityplayer.inventory, (LOTRTileEntityBookshelf) bookshelf);
		}
		if (ID == 56) {
			return new LOTRContainerCraftingTable.Umbar(entityplayer.inventory, world, i, j, k);
		}
		if (ID == 57) {
			return new LOTRContainerCraftingTable.Gulf(entityplayer.inventory, world, i, j, k);
		}
		if (ID == 59 && (entity = world.getEntityByID(i)) instanceof LOTRMercenary) {
			return new LOTRContainerUnitTrade(entityplayer, (LOTRHireableBase) entity, world);
		}
		if (ID == 62) {
			return new LOTRContainerCraftingTable.Bree(entityplayer.inventory, world, i, j, k);
		}
		if (testForSlotPackedGuiID(ID, 63) && LOTRItemPouch.isHoldingPouch(entityplayer, slot = unpackSlot(ID)) && (chest2 = LOTRItemPouch.getChestInvAt(entityplayer, world, i, j, k)) != null) {
			return new LOTRContainerChestWithPouch(entityplayer, slot, chest2);
		}
		if (testForSlotPackedGuiID(ID, 64) && LOTRItemPouch.isHoldingPouch(entityplayer, slot = unpackSlot(ID)) && (minecart = world.getEntityByID(i)) instanceof EntityMinecartContainer) {
			return new LOTRContainerChestWithPouch(entityplayer, slot, (IInventory) minecart);
		}
		return null;
	}

	public int getStalactiteRenderID() {
		return 0;
	}

	public int getThatchFloorRenderID() {
		return 0;
	}

	public int getTrapdoorRenderID() {
		return 0;
	}

	public int getTreasureRenderID() {
		return 0;
	}

	public int getTrollTotemRenderID() {
		return 0;
	}

	public int getUnsmelteryRenderID() {
		return 0;
	}

	public int getVCauldronRenderID() {
		return 0;
	}

	public int getWasteRenderID() {
		return 0;
	}

	public void handleInvasionWatch(int invasionEntityID, boolean overrideAlreadyWatched) {
	}

	public boolean isClient() {
		return false;
	}

	public boolean isPaused() {
		return false;
	}

	public boolean isSingleplayer() {
		return false;
	}

	public void onLoad() {
	}

	public void onPostload() {
	}

	public void onPreload() {
	}

	public void openHiredNPCGui(LOTREntityNPC npc) {
	}

	public void placeFlowerInPot(World world, int i, int j, int k, int side, ItemStack itemstack) {
		world.setBlock(i, j, k, LOTRMod.flowerPot, 0, 3);
		LOTRBlockFlowerPot.setPlant(world, i, j, k, itemstack);
	}

	public void queueAchievement(LOTRAchievement achievement) {
	}

	public void queueConquestNotification(LOTRFaction fac, float conq, boolean isCleansing) {
	}

	public void queueFellowshipNotification(IChatComponent message) {
	}

	public void receiveConquestGrid(LOTRFaction conqFac, List<LOTRConquestZone> allZones) {
	}

	public void renderCustomPotionEffect(int x, int y, PotionEffect effect, Minecraft mc) {
	}

	public void setClientDifficulty(EnumDifficulty difficulty) {
	}

	public void setInElvenPortal(EntityPlayer entityplayer) {
		if (!LOTRTickHandlerServer.playersInElvenPortals.containsKey(entityplayer)) {
			LOTRTickHandlerServer.playersInElvenPortals.put(entityplayer, 0);
		}
	}

	public void setInMorgulPortal(EntityPlayer entityplayer) {
		if (!LOTRTickHandlerServer.playersInMorgulPortals.containsKey(entityplayer)) {
			LOTRTickHandlerServer.playersInMorgulPortals.put(entityplayer, 0);
		}
	}

	public void setInPortal(EntityPlayer entityplayer) {
		if (!LOTRTickHandlerServer.playersInPortals.containsKey(entityplayer)) {
			LOTRTickHandlerServer.playersInPortals.put(entityplayer, 0);
		}
	}

	public void setInUtumnoReturnPortal(EntityPlayer entityplayer) {
	}

	public void setMapCWPProtectionMessage(IChatComponent message) {
	}

	public void setMapIsOp(boolean isOp) {
	}

	public void setTrackedQuest(LOTRMiniQuest quest) {
	}

	public void setUtumnoReturnPortalCoords(EntityPlayer entityplayer, int x, int z) {
	}

	public void setWaypointModes(boolean showWP, boolean showCWP, boolean showHiddenSWP) {
	}

	public void showBurnOverlay() {
	}

	public void showFrostOverlay() {
	}

	public void spawnAlignmentBonus(LOTRFaction faction, float prevMainAlignment, LOTRAlignmentBonusMap factionBonusMap, String name, boolean isKill, boolean isHiredKill, float conquestBonus, double posX, double posY, double posZ) {
	}

	public void spawnParticle(String type, double d, double d1, double d2, double d3, double d4, double d5) {
	}

	public void testReflection(World world) {
		LOTRReflection.testAll(world);
	}

	public void usePouchOnChest(EntityPlayer entityplayer, World world, int i, int j, int k, int side, ItemStack itemstack, int pouchSlot) {
		entityplayer.openGui(LOTRMod.instance, packGuiIDWithSlot(63, pouchSlot), world, i, j, k);
	}

	public void validateBannerUsername(LOTREntityBanner banner, int slot, String prevText, boolean valid) {
	}

	public static int packGuiIDWithSlot(int guiID, int slotNo) {
		return guiID | slotNo << 16;
	}

	public static void sendClientsideGUI(EntityPlayerMP entityplayer, int guiID, int x, int y, int z) {
		IMessage packet = new LOTRPacketClientsideGUI(guiID, x, y, z);
		LOTRPacketHandler.networkWrapper.sendTo(packet, entityplayer);
	}

	public static boolean testForSlotPackedGuiID(int fullID, int guiID) {
		return (fullID & 0xFFFF) == guiID;
	}

	public static int unpackSlot(int fullID) {
		return fullID >> 16;
	}
}
