package lotr.common;

import com.mojang.authlib.GameProfile;
import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.IFuelHandler;
import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.*;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.registry.GameRegistry;
import lotr.common.block.*;
import lotr.common.enchant.LOTREnchantment;
import lotr.common.enchant.LOTREnchantmentHelper;
import lotr.common.enchant.LOTREnchantmentWeaponSpecial;
import lotr.common.entity.LOTRBannerProtectable;
import lotr.common.entity.LOTREntityRegistry;
import lotr.common.entity.LOTRPlateFallingInfo;
import lotr.common.entity.LOTRRandomSkinEntity;
import lotr.common.entity.ai.LOTREntityAINearestAttackableTargetBasic;
import lotr.common.entity.animal.LOTREntityButterfly;
import lotr.common.entity.animal.LOTREntityHorse;
import lotr.common.entity.animal.LOTREntityZebra;
import lotr.common.entity.item.LOTREntityArrowPoisoned;
import lotr.common.entity.item.LOTREntityBanner;
import lotr.common.entity.npc.*;
import lotr.common.entity.projectile.*;
import lotr.common.fac.LOTRAlignmentValues;
import lotr.common.fac.LOTRFaction;
import lotr.common.fac.LOTRFactionBounties;
import lotr.common.fac.LOTRFactionRelations;
import lotr.common.inventory.LOTRContainerCraftingTable;
import lotr.common.item.*;
import lotr.common.network.*;
import lotr.common.quest.LOTRMiniQuest;
import lotr.common.recipe.LOTRRecipePoisonWeapon;
import lotr.common.tileentity.LOTRTileEntityPlate;
import lotr.common.world.*;
import lotr.common.world.biome.*;
import lotr.common.world.biome.variant.LOTRBiomeVariantStorage;
import lotr.common.world.structure2.LOTRStructureTimelapse;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.item.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.UsernameCache;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.minecart.MinecartInteractEvent;
import net.minecraftforge.event.entity.minecart.MinecartUpdateEvent;
import net.minecraftforge.event.entity.player.*;
import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;
import net.minecraftforge.event.entity.player.PlayerEvent.HarvestCheck;
import net.minecraftforge.event.entity.player.PlayerEvent.StartTracking;
import net.minecraftforge.event.terraingen.SaplingGrowTreeEvent;
import net.minecraftforge.event.world.*;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

public class LOTREventHandler implements IFuelHandler {
	public LOTRItemBow proxyBowItemServer;
	public LOTRItemBow proxyBowItemClient;

	public LOTREventHandler() {
        FMLCommonHandler.instance().bus().register(this);
		MinecraftForge.EVENT_BUS.register(this);
		MinecraftForge.TERRAIN_GEN_BUS.register(this);
		GameRegistry.registerFuelHandler(this);
		new LOTRStructureTimelapse();
    }

	public static void dechant(ItemStack itemstack, EntityPlayer entityplayer) {
		if (!entityplayer.capabilities.isCreativeMode && itemstack != null && itemstack.isItemEnchanted() && !(itemstack.getItem() instanceof ItemFishingRod)) {
			itemstack.getTagCompound().removeTag("ench");
		}
	}

	public static String getUsernameWithoutWebservice(UUID player) {
		GameProfile profile = MinecraftServer.getServer().func_152358_ax().func_152652_a(player);
		if (profile != null && !StringUtils.isBlank(profile.getName())) {
			return profile.getName();
		}
		String cachedName = UsernameCache.getLastKnownUsername(player);
		if (cachedName != null && !StringUtils.isBlank(cachedName)) {
			return cachedName;
		}
		return player.toString();
	}

	public void cancelAttackEvent(LivingAttackEvent event) {
		event.setCanceled(true);
		DamageSource source = event.source;
		if (source instanceof EntityDamageSourceIndirect) {
			source.getSourceOfDamage();
		}
	}

	@SubscribeEvent
	public void getBlockDrops(BlockEvent.HarvestDropsEvent event) {
		ItemStack itemstack;
		EntityPlayer entityplayer = event.harvester;
		Block block = event.block;
		if (entityplayer != null && (itemstack = entityplayer.getCurrentEquippedItem()) != null && block instanceof BlockWeb && itemstack.getItem() instanceof ItemShears) {
			int meta = 0;
			Item item = Item.getItemFromBlock(block);
			if (item != null && item.getHasSubtypes()) {
				meta = event.blockMetadata;
			}
			ItemStack silkDrop = new ItemStack(item, 1, meta);
			event.drops.clear();
			event.drops.add(silkDrop);
		}
	}

	@Override
	public int getBurnTime(ItemStack itemstack) {
		Item item = itemstack.getItem();
		if (item instanceof ItemBlock && ((ItemBlock) item).field_150939_a instanceof LOTRBlockSaplingBase) {
			return 100;
		}
		if (item == LOTRMod.nauriteGem) {
			return 600;
		}
		if (item == Item.getItemFromBlock(LOTRMod.blockOreStorage) && itemstack.getItemDamage() == 10) {
			return 6000;
		}
		if (item == LOTRMod.mallornStick) {
			return 100;
		}
		if (item instanceof ItemTool && ((ItemTool) item).func_150913_i() == LOTRMaterial.MALLORN.toToolMaterial() || item instanceof ItemSword && ((ItemSword) item).getToolMaterialName().equals(LOTRMaterial.MALLORN.toToolMaterial().toString())) {
			return 200;
		}
		if (item instanceof ItemHoe && ((ItemHoe) item).getToolMaterialName().equals(LOTRMaterial.MALLORN.toToolMaterial().toString())) {
			return 200;
		}
		if (item == Items.reeds || item == Item.getItemFromBlock(LOTRMod.reeds) || item == Item.getItemFromBlock(LOTRMod.driedReeds) || item == Item.getItemFromBlock(LOTRMod.cornStalk)) {
			return 100;
		}
		return 0;
	}

	@SubscribeEvent
	public void onAnvilUpdate(AnvilUpdateEvent event) {
		if (!LOTRConfig.enchantingVanilla && (event.left != null && event.left.getItem() instanceof ItemEnchantedBook || event.right != null && event.right.getItem() instanceof ItemEnchantedBook)) {
			event.setCanceled(true);
		}
	}

	@SubscribeEvent
	public void onArrowNock(ArrowNockEvent event) {
		EntityPlayer entityplayer = event.entityPlayer;
		World world = entityplayer.worldObj;
		ItemStack itemstack = event.result;
		if (itemstack != null && itemstack.getItem() instanceof ItemBow && !(itemstack.getItem() instanceof LOTRItemBow) && !(itemstack.getItem() instanceof LOTRItemCrossbow)) {
			if (!world.isRemote) {
				if (proxyBowItemServer == null) {
					proxyBowItemServer = new LOTRItemBow(Item.ToolMaterial.WOOD);
					event.result = proxyBowItemServer.onItemRightClick(itemstack, world, entityplayer);
					proxyBowItemServer = null;
					event.setCanceled(true);
				}
			} else if (proxyBowItemClient == null) {
				proxyBowItemClient = new LOTRItemBow(Item.ToolMaterial.WOOD);
				event.result = proxyBowItemClient.onItemRightClick(itemstack, world, entityplayer);
				proxyBowItemClient = null;
				event.setCanceled(true);
			}
		}
	}

	@SubscribeEvent
	public void onBlockBreak(BlockEvent.BreakEvent event) {
		List trees;
		EntityPlayer entityplayer = event.getPlayer();
		Block block = event.block;
		int meta = event.blockMetadata;
		World world = event.world;
		int i = event.x;
		int j = event.y;
		int k = event.z;
		if (!world.isRemote && LOTRBannerProtection.isProtected(world, i, j, k, LOTRBannerProtection.forPlayer(entityplayer, LOTRBannerProtection.Permission.FULL), true)) {
			event.setCanceled(true);
			return;
		}
		if (!world.isRemote && entityplayer != null && !entityplayer.capabilities.isCreativeMode && block.isWood(world, i, j, k) && !LOTRBlockRottenLog.isRottenWood(block) && !(trees = world.getEntitiesWithinAABB(LOTREntityTree.class, AxisAlignedBB.getBoundingBox(i, j, k, i + 1, j + 1, k + 1).expand(16.0, 16.0, 16.0))).isEmpty()) {
			boolean sentMessage = false;
			boolean penalty = false;
			for (Object tree2 : trees) {
				LOTREntityTree tree = (LOTREntityTree) tree2;
				if (tree.hiredNPCInfo.isActive && tree.hiredNPCInfo.getHiringPlayer() == entityplayer) {
					continue;
				}
				tree.setAttackTarget(entityplayer);
				if (tree instanceof LOTREntityEnt && !sentMessage) {
					tree.sendSpeechBank(entityplayer, "ent/ent/defendTrees");
					sentMessage = true;
				}
				if (!(world.getBiomeGenForCoords(i, k) instanceof LOTRBiomeGenFangorn) || penalty) {
					continue;
				}
				LOTRLevelData.getData(entityplayer).addAlignment(entityplayer, LOTRAlignmentValues.FANGORN_TREE_PENALTY, LOTRFaction.FANGORN, i + 0.5, j + 0.5, k + 0.5);
				penalty = true;
			}
		}
		if (!world.isRemote && entityplayer != null) {
			if (LOTRBlockGrapevine.isFullGrownGrapes(block, meta)) {
				LOTREntityDorwinionGuard.defendGrapevines(entityplayer, world, i, j, k);
			} else {
				boolean grapesAbove = false;
				for (int j1 = 1; j1 <= 3; ++j1) {
					int j2 = j + j1;
					Block above = world.getBlock(i, j2, k);
					if (!LOTRBlockGrapevine.isFullGrownGrapes(above, world.getBlockMetadata(i, j2, k))) {
						continue;
					}
					grapesAbove = true;
				}
				if (grapesAbove) {
					LOTREntityDorwinionGuard.defendGrapevines(entityplayer, world, i, j + 1, k);
				}
			}
		}
	}

	@SubscribeEvent
	public void onBlockBreaking(BreakSpeed event) {
		EntityPlayer entityplayer = event.entityPlayer;
		World world = entityplayer.worldObj;
		Block block = event.block;
		int j = event.y;
		if (block instanceof LOTRWorldProviderUtumno.UtumnoBlock && LOTRDimension.getCurrentDimensionWithFallback(world) == LOTRDimension.UTUMNO) {
			boolean canMine = false;
			ItemStack itemstack = entityplayer.getCurrentEquippedItem();
			if (itemstack != null && itemstack.getItem() == LOTRMod.utumnoPickaxe) {
				canMine = true;
				int levelFuzz = 2;
				for (int l = 0; l < LOTRUtumnoLevel.values().length - 1; ++l) {
					LOTRUtumnoLevel levelUpper = LOTRUtumnoLevel.values()[l];
					LOTRUtumnoLevel levelLower = LOTRUtumnoLevel.values()[l + 1];
					if (j < levelLower.getHighestCorridorRoof() + levelFuzz || j > levelUpper.getLowestCorridorFloor() - levelFuzz) {
						continue;
					}
					canMine = false;
				}
			}
			if (!canMine) {
				event.setCanceled(true);
			}
		}
	}

	@SubscribeEvent
	public void onBlockInteract(PlayerInteractEvent event) {
		Block block;
		int meta;
		EntityPlayer entityplayer = event.entityPlayer;
		World world = entityplayer.worldObj;
		ItemStack itemstack = entityplayer.inventory.getCurrentItem();
		int i = event.x;
		int j = event.y;
		int k = event.z;
		int side = event.face;
		if (event.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) {
			boolean mightBeAbleToAlterWorld;
			ItemStack plateItem;
			int cauldronMeta;
			LOTRTileEntityPlate plate;
			int logFacing;
			TileEntity tileentity;
			block = world.getBlock(i, j, k);
			meta = world.getBlockMetadata(i, j, k);
			LOTRBannerProtection.Permission perm = LOTRBannerProtection.Permission.FULL;
			mightBeAbleToAlterWorld = entityplayer.isSneaking() && itemstack != null;
			if (!mightBeAbleToAlterWorld) {
				if (block instanceof BlockDoor || block instanceof BlockTrapDoor || block instanceof BlockFenceGate || block instanceof LOTRBlockGate) {
					perm = LOTRBannerProtection.Permission.DOORS;
				} else if (block instanceof BlockWorkbench || block instanceof LOTRBlockCraftingTable || block instanceof BlockAnvil || block instanceof LOTRBlockCommandTable) {
					perm = LOTRBannerProtection.Permission.TABLES;
				} else if (world.getTileEntity(i, j, k) instanceof IInventory) {
					perm = block instanceof LOTRBlockBarrel || block instanceof LOTRBlockKebabStand ? LOTRBannerProtection.Permission.FOOD : LOTRBannerProtection.Permission.CONTAINERS;
				} else if (block instanceof LOTRBlockArmorStand || block instanceof LOTRBlockWeaponRack || block == Blocks.bookshelf || block instanceof BlockJukebox) {
					perm = LOTRBannerProtection.Permission.CONTAINERS;
				} else if (block instanceof BlockEnderChest) {
					perm = LOTRBannerProtection.Permission.PERSONAL_CONTAINERS;
				} else if (block instanceof LOTRBlockPlate || block instanceof BlockCake || block instanceof LOTRBlockPlaceableFood || block instanceof LOTRBlockMug || block instanceof LOTRBlockEntJar) {
					perm = LOTRBannerProtection.Permission.FOOD;
				} else if (block instanceof BlockBed) {
					perm = LOTRBannerProtection.Permission.BEDS;
				} else if (block instanceof BlockButton || block instanceof BlockLever) {
					perm = LOTRBannerProtection.Permission.SWITCHES;
				}
			}
			if (!world.isRemote && LOTRBannerProtection.isProtected(world, i, j, k, LOTRBannerProtection.forPlayer(entityplayer, perm), true)) {
				event.setCanceled(true);
				if (block instanceof BlockDoor) {
					world.markBlockForUpdate(i, j - 1, k);
					world.markBlockForUpdate(i, j, k);
					world.markBlockForUpdate(i, j + 1, k);
				} else if (block instanceof LOTRBlockPlate && LOTRBlockPlate.getFoodItem(world, i, j, k) != null) {
					world.markBlockForUpdate(i, j, k);
				}
				return;
			}
			if (block == Blocks.flower_pot && meta == 0 && itemstack != null && LOTRBlockFlowerPot.canAcceptPlant(itemstack)) {
				LOTRMod.proxy.placeFlowerInPot(world, i, j, k, side, itemstack);
				if (!entityplayer.capabilities.isCreativeMode) {
					--itemstack.stackSize;
				}
				event.setCanceled(true);
				return;
			}
			if (itemstack != null && block == Blocks.cauldron && meta > 0) {
				LOTRItemMug.Vessel drinkVessel = null;
				for (LOTRItemMug.Vessel v : LOTRItemMug.Vessel.values()) {
					if (v.getEmptyVesselItem() != itemstack.getItem()) {
						continue;
					}
					drinkVessel = v;
					break;
				}
				if (drinkVessel != null) {
					LOTRMod.proxy.fillMugFromCauldron(world, i, j, k, side, itemstack);
					--itemstack.stackSize;
					ItemStack mugFill = new ItemStack(LOTRMod.mugWater);
					LOTRItemMug.setVessel(mugFill, drinkVessel, true);
					if (itemstack.stackSize <= 0) {
						entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, mugFill);
					} else if (!entityplayer.inventory.addItemStackToInventory(mugFill)) {
						entityplayer.dropPlayerItemWithRandomChoice(mugFill, false);
					}
					event.setCanceled(true);
					return;
				}
			}
			if (!world.isRemote && block instanceof LOTRBlockPlate && entityplayer.isSneaking() && (tileentity = world.getTileEntity(i, j, k)) instanceof LOTRTileEntityPlate && (plateItem = (plate = (LOTRTileEntityPlate) tileentity).getFoodItem()) != null) {
				((LOTRBlockPlate) block).dropOnePlateItem(plate);
				--plateItem.stackSize;
				plate.setFoodItem(plateItem);
				event.setCanceled(true);
				return;
			}
			if (!world.isRemote && block instanceof LOTRBlockMechanisedRail && entityplayer.isSneaking() && ((LOTRBlockMechanisedRail) block).onShiftClickActivateFirst(world, i, j, k, entityplayer, side)) {
				event.setCanceled(true);
				return;
			}
			if (!world.isRemote && block instanceof BlockCauldron && itemstack != null && (cauldronMeta = BlockCauldron.func_150027_b(meta)) > 0) {
				boolean undyed = false;
				Item item = itemstack.getItem();
				if (LOTRRecipePoisonWeapon.poisonedToInput.containsKey(item)) {
					Item inputWeapon = LOTRRecipePoisonWeapon.poisonedToInput.get(item);
					itemstack.func_150996_a(inputWeapon);
					undyed = true;
				} else if (item instanceof LOTRItemPouch && LOTRItemPouch.isPouchDyed(itemstack)) {
					LOTRItemPouch.removePouchDye(itemstack);
					undyed = true;
				} else if (item instanceof LOTRItemHobbitPipe && LOTRItemHobbitPipe.isPipeDyed(itemstack)) {
					LOTRItemHobbitPipe.removePipeDye(itemstack);
					undyed = true;
				} else if (item instanceof LOTRItemLeatherHat && (LOTRItemLeatherHat.isHatDyed(itemstack) || LOTRItemLeatherHat.isFeatherDyed(itemstack))) {
					LOTRItemLeatherHat.removeHatAndFeatherDye(itemstack);
					undyed = true;
				} else if (item instanceof LOTRItemFeatherDyed && LOTRItemFeatherDyed.isFeatherDyed(itemstack)) {
					LOTRItemFeatherDyed.removeFeatherDye(itemstack);
					undyed = true;
				} else if (item instanceof LOTRItemHaradRobes && LOTRItemHaradRobes.areRobesDyed(itemstack)) {
					LOTRItemHaradRobes.removeRobeDye(itemstack);
					undyed = true;
				} else if (item instanceof LOTRItemPartyHat && LOTRItemPartyHat.isHatDyed(itemstack)) {
					LOTRItemPartyHat.removeHatDye(itemstack);
					undyed = true;
				}
				if (undyed) {
					((BlockCauldron) block).func_150024_a(world, i, j, k, cauldronMeta - 1);
					event.setCanceled(true);
					return;
				}
			}
			if (!world.isRemote && itemstack != null && itemstack.getItem() == Items.dye && itemstack.getItemDamage() == 15 && block instanceof BlockLog && (logFacing = meta & 0xC) != 12) {
				boolean onInnerFace = false;
				switch (logFacing) {
					case 0:
						onInnerFace = side == 0 || side == 1;
						break;
					case 4:
						onInnerFace = side == 4 || side == 5;
						break;
					case 8: {
						onInnerFace = side == 2 || side == 3;
						break;
					}
					default:
						break;
				}
				if (onInnerFace) {
					world.setBlockMetadataWithNotify(i, j, k, meta | 0xC, 3);
					world.playAuxSFX(2005, i, j, k, 0);
					if (!entityplayer.capabilities.isCreativeMode) {
						--itemstack.stackSize;
					}
					event.setCanceled(true);
					return;
				}
			}
			if (!world.isRemote && LOTRBlockGrapevine.isFullGrownGrapes(block, meta)) {
				LOTREntityDorwinionGuard.defendGrapevines(entityplayer, world, i, j, k);
			}
			if (block == Blocks.bookshelf && !entityplayer.isSneaking() && LOTRBlockBookshelfStorage.canOpenBookshelf(world, i, j, k, entityplayer) && !world.isRemote) {
				world.setBlock(i, j, k, LOTRMod.bookshelfStorage, 0, 3);
				boolean flag = LOTRMod.bookshelfStorage.onBlockActivated(world, i, j, k, entityplayer, side, 0.5f, 0.5f, 0.5f);
				if (!flag) {
					world.setBlock(i, j, k, Blocks.bookshelf, 0, 3);
				}
				event.setCanceled(true);
				return;
			}
			if (block == Blocks.enchanting_table && !LOTRConfig.isEnchantingEnabled(world) && !world.isRemote) {
				LOTRLevelData.getData(entityplayer).sendMessageIfNotReceived(LOTRGuiMessageTypes.ENCHANTING);
				event.setCanceled(true);
				return;
			}
			if (block == Blocks.brewing_stand && !LOTRConfig.enablePotionBrewing) {
				event.setCanceled(true);
				return;
			}
			if (block == Blocks.ender_chest && LOTRDimension.getCurrentDimensionWithFallback(world) == LOTRDimension.UTUMNO && LOTRConfig.disableEnderChestsUtumno) {
				event.setCanceled(true);
				return;
			}
			if (block == Blocks.anvil && (LOTRConfig.isLOTREnchantingEnabled(world) || !LOTRConfig.isEnchantingEnabled(world)) && !world.isRemote) {
				entityplayer.openGui(LOTRMod.instance, 53, world, i, j, k);
				event.setCanceled(true);
				return;
			}
		}
		if (event.action == PlayerInteractEvent.Action.LEFT_CLICK_BLOCK) {
			ForgeDirection dir = ForgeDirection.getOrientation(side);
			int i1 = i + dir.offsetX;
			int j1 = j + dir.offsetY;
			int k1 = k + dir.offsetZ;
			Block block1 = world.getBlock(i1, j1, k1);
			if (!world.isRemote && LOTRBannerProtection.isProtected(world, i1, j1, k1, LOTRBannerProtection.forPlayer(entityplayer, LOTRBannerProtection.Permission.FULL), true) && block1 instanceof BlockFire) {
				event.setCanceled(true);
			}
		}
	}

	@SubscribeEvent
	public void onBlockPlace(BlockEvent.PlaceEvent event) {
		Block block = event.block;
		World world = event.world;
		int i = event.x;
		int j = event.y;
		int k = event.z;
		if (world.provider instanceof LOTRWorldProviderUtumno && LOTRUtumnoLevel.forY(j) == LOTRUtumnoLevel.FIRE && block == Blocks.ice) {
			world.setBlock(i, j, k, Blocks.air, 0, 3);
			LOTRWorldProviderUtumno.doEvaporateFX(world, i, j, k);
		}
	}

	@SubscribeEvent
	public void onBreakingSpeed(BreakSpeed event) {
		EntityPlayer entityplayer = event.entityPlayer;
		Block block = event.block;
		int meta = event.metadata;
		float speed = event.newSpeed;
		ItemStack itemstack = entityplayer.getCurrentEquippedItem();
		if (itemstack != null && itemstack.getItem().getDigSpeed(itemstack, block, meta) > 1.0f) {
			speed *= LOTREnchantmentHelper.calcToolEfficiency(itemstack);
		}
		event.newSpeed = speed;
	}

	@SubscribeEvent
	public void onChunkDataLoad(ChunkDataEvent.Load event) {
		World world = event.world;
		Chunk chunk = event.getChunk();
		NBTTagCompound data = event.getData();
		if (!world.isRemote && world.provider instanceof LOTRWorldProvider) {
			LOTRBiomeVariantStorage.loadChunkVariants(world, chunk, data);
		}
	}

	@SubscribeEvent
	public void onChunkDataSave(ChunkDataEvent.Save event) {
		World world = event.world;
		Chunk chunk = event.getChunk();
		NBTTagCompound data = event.getData();
		if (!world.isRemote && world.provider instanceof LOTRWorldProvider) {
			LOTRBiomeVariantStorage.saveChunkVariants(world, chunk, data);
		}
	}

	@SubscribeEvent
	public void onChunkStartWatching(ChunkWatchEvent.Watch event) {
		EntityPlayerMP entityplayer = event.player;
		World world = entityplayer.worldObj;
		ChunkCoordIntPair chunkCoords = event.chunk;
		Chunk chunk = world.getChunkFromChunkCoords(chunkCoords.chunkXPos, chunkCoords.chunkZPos);
		if (!world.isRemote && world.provider instanceof LOTRWorldProvider) {
			LOTRBiomeVariantStorage.sendChunkVariantsToPlayer(world, chunk, entityplayer);
		}
	}

	@SubscribeEvent
	public void onChunkStopWatching(ChunkWatchEvent.UnWatch event) {
		EntityPlayerMP entityplayer = event.player;
		World world = entityplayer.worldObj;
		ChunkCoordIntPair chunkCoords = event.chunk;
		Chunk chunk = world.getChunkFromChunkCoords(chunkCoords.chunkXPos, chunkCoords.chunkZPos);
		if (!world.isRemote && world.provider instanceof LOTRWorldProvider) {
			LOTRBiomeVariantStorage.sendUnwatchToPlayer(world, chunk, entityplayer);
		}
	}

	@SubscribeEvent
	public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
		if ("lotr".equals(event.modID)) {
			LOTRConfig.load();
		}
	}

	@SubscribeEvent
	public void onCrafting(ItemCraftedEvent event) {
		EntityPlayer entityplayer = event.player;
		ItemStack itemstack = event.crafting;
		if (!entityplayer.worldObj.isRemote) {
			if (entityplayer.openContainer instanceof LOTRContainerCraftingTable.Elven) {
				LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.useElvenTable);
			}
			if (entityplayer.openContainer instanceof LOTRContainerCraftingTable.Uruk) {
				LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.useUrukTable);
			}
			if (entityplayer.openContainer instanceof LOTRContainerCraftingTable.Rohirric) {
				LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.useRohirricTable);
			}
			if (entityplayer.openContainer instanceof LOTRContainerCraftingTable.Gondorian) {
				LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.useGondorianTable);
			}
			if (entityplayer.openContainer instanceof LOTRContainerCraftingTable.WoodElven) {
				LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.useWoodElvenTable);
			}
			if (entityplayer.openContainer instanceof LOTRContainerCraftingTable.Dwarven) {
				LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.useDwarvenTable);
			}
			if (entityplayer.openContainer instanceof LOTRContainerCraftingTable.Morgul) {
				LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.useMorgulTable);
			}
			if (entityplayer.openContainer instanceof LOTRContainerCraftingTable.Dunlending) {
				LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.useDunlendingTable);
			}
			if (entityplayer.openContainer instanceof LOTRContainerCraftingTable.Angmar) {
				LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.useAngmarTable);
			}
			if (entityplayer.openContainer instanceof LOTRContainerCraftingTable.NearHarad) {
				LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.useNearHaradTable);
			}
			if (entityplayer.openContainer instanceof LOTRContainerCraftingTable.HighElven) {
				LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.useHighElvenTable);
			}
			if (entityplayer.openContainer instanceof LOTRContainerCraftingTable.BlueDwarven) {
				LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.useBlueDwarvenTable);
			}
			if (entityplayer.openContainer instanceof LOTRContainerCraftingTable.Ranger) {
				LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.useRangerTable);
			}
			if (entityplayer.openContainer instanceof LOTRContainerCraftingTable.DolGuldur) {
				LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.useDolGuldurTable);
			}
			if (entityplayer.openContainer instanceof LOTRContainerCraftingTable.Gundabad) {
				LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.useGundabadTable);
			}
			if (entityplayer.openContainer instanceof LOTRContainerCraftingTable.HalfTroll) {
				LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.useHalfTrollTable);
			}
			if (entityplayer.openContainer instanceof LOTRContainerCraftingTable.DolAmroth) {
				LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.useDolAmrothTable);
			}
			if (entityplayer.openContainer instanceof LOTRContainerCraftingTable.Moredain) {
				LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.useMoredainTable);
			}
			if (entityplayer.openContainer instanceof LOTRContainerCraftingTable.Tauredain) {
				LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.useTauredainTable);
			}
			if (entityplayer.openContainer instanceof LOTRContainerCraftingTable.Dale) {
				LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.useDaleTable);
			}
			if (entityplayer.openContainer instanceof LOTRContainerCraftingTable.Dorwinion) {
				LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.useDorwinionTable);
			}
			if (entityplayer.openContainer instanceof LOTRContainerCraftingTable.Hobbit) {
				LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.useHobbitTable);
			}
			if (entityplayer.openContainer instanceof LOTRContainerCraftingTable.Rhun) {
				LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.useRhunTable);
			}
			if (entityplayer.openContainer instanceof LOTRContainerCraftingTable.Rivendell) {
				LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.useRivendellTable);
			}
			if (entityplayer.openContainer instanceof LOTRContainerCraftingTable.Umbar) {
				LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.useUmbarTable);
			}
			if (entityplayer.openContainer instanceof LOTRContainerCraftingTable.Gulf) {
				LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.useGulfTable);
			}
			if (entityplayer.openContainer instanceof LOTRContainerCraftingTable.Bree) {
				LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.useBreeTable);
			}
			if (itemstack.getItem() == Items.saddle) {
				LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.craftSaddle);
			}
			if (itemstack.getItem() == LOTRMod.bronze) {
				LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.craftBronze);
			}
			if (itemstack.getItem() == LOTRMod.appleCrumbleItem) {
				LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.craftAppleCrumble);
			}
			if (itemstack.getItem() == LOTRMod.rabbitStew) {
				LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.craftRabbitStew);
			}
			if (itemstack.getItem() == LOTRMod.saltedFlesh) {
				LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.craftSaltedFlesh);
			}
			if (itemstack.getItem() == Item.getItemFromBlock(LOTRMod.brick) && itemstack.getItemDamage() == 10) {
				LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.craftMithrilDwarvenBrick);
			}
			if (itemstack.getItem() == Item.getItemFromBlock(LOTRMod.orcBomb)) {
				LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.craftOrcBomb);
			}
			if (itemstack.getItem() == LOTRMod.utumnoKey) {
				LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.craftUtumnoKey);
			}
		}
	}

	@SubscribeEvent
	public void onEntityAttackedByPlayer(AttackEntityEvent event) {
		Entity entity = event.target;
		World world = entity.worldObj;
		EntityPlayer entityplayer = event.entityPlayer;
		if (!world.isRemote && (entity instanceof EntityHanging || entity instanceof LOTRBannerProtectable) && LOTRBannerProtection.isProtected(world, entity, LOTRBannerProtection.forPlayer(entityplayer, LOTRBannerProtection.Permission.FULL), true)) {
			event.setCanceled(true);
		}
	}

	@SubscribeEvent
	public void onEntityInteract(EntityInteractEvent event) {
		String playerName;
		UUID brandingPlayer;
		EntityPlayer entityplayer = event.entityPlayer;
		World world = entityplayer.worldObj;
		ItemStack itemstack = entityplayer.inventory.getCurrentItem();
		Entity entity = event.target;
		if (!world.isRemote && (entity instanceof EntityHanging || entity instanceof LOTRBannerProtectable) && LOTRBannerProtection.isProtected(world, entity, LOTRBannerProtection.forPlayer(entityplayer, LOTRBannerProtection.Permission.FULL), true)) {
			event.setCanceled(true);
			return;
		}
		if ((entity instanceof EntityCow || entity instanceof LOTREntityZebra) && LOTRItemMug.isItemEmptyDrink(itemstack)) {
			LOTRItemMug.Vessel vessel = LOTRItemMug.getVessel(itemstack);
			ItemStack milkItem = new ItemStack(LOTRMod.mugMilk);
			LOTRItemMug.setVessel(milkItem, vessel, true);
			if (!entityplayer.capabilities.isCreativeMode) {
				--itemstack.stackSize;
			}
			if (itemstack.stackSize <= 0 || entityplayer.capabilities.isCreativeMode) {
				entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, milkItem);
			} else if (!entityplayer.inventory.addItemStackToInventory(milkItem)) {
				entityplayer.dropPlayerItemWithRandomChoice(milkItem, false);
			}
			event.setCanceled(true);
			return;
		}
		if (entity instanceof EntityWolf) {
			int dyeType;
			EntityWolf wolf = (EntityWolf) entity;
			if (itemstack != null && LOTRMod.isOreNameEqual(itemstack, "bone") && itemstack.getItem() != Items.bone) {
				Item prevItem = itemstack.getItem();
				itemstack.func_150996_a(Items.bone);
				boolean flag = wolf.interact(entityplayer);
				itemstack.func_150996_a(prevItem);
				if (flag) {
					event.setCanceled(true);
					return;
				}
			}
			if (itemstack != null && (dyeType = LOTRItemDye.isItemDye(itemstack)) >= 0 && itemstack.getItem() != Items.dye) {
				Item prevItem = itemstack.getItem();
				int prevMeta = itemstack.getItemDamage();
				itemstack.func_150996_a(Items.dye);
				itemstack.setItemDamage(dyeType);
				boolean flag = wolf.interact(entityplayer);
				itemstack.func_150996_a(prevItem);
				itemstack.setItemDamage(prevMeta);
				if (flag) {
					event.setCanceled(true);
					return;
				}
			}
		}
		if (entity instanceof LOTRTradeable && ((LOTRTradeable) entity).canTradeWith(entityplayer)) {
			if (entity instanceof LOTRUnitTradeable) {
				entityplayer.openGui(LOTRMod.instance, 24, world, entity.getEntityId(), 0, 0);
			} else {
				entityplayer.openGui(LOTRMod.instance, 19, world, entity.getEntityId(), 0, 0);
			}
			event.setCanceled(true);
			return;
		}
		if (entity instanceof LOTRUnitTradeable && ((LOTRHireableBase) entity).canTradeWith(entityplayer)) {
			entityplayer.openGui(LOTRMod.instance, 20, world, entity.getEntityId(), 0, 0);
			event.setCanceled(true);
			return;
		}
		if (entity instanceof LOTRMercenary && ((LOTRHireableBase) entity).canTradeWith(entityplayer) && ((LOTREntityNPC) entity).hiredNPCInfo.getHiringPlayerUUID() == null) {
			entityplayer.openGui(LOTRMod.instance, 58, world, entity.getEntityId(), 0, 0);
			event.setCanceled(true);
			return;
		}
		if (entity instanceof LOTREntityNPC) {
			LOTREntityNPC npc = (LOTREntityNPC) entity;
			if (npc.hiredNPCInfo.getHiringPlayer() == entityplayer) {
				entityplayer.openGui(LOTRMod.instance, 21, world, entity.getEntityId(), 0, 0);
				event.setCanceled(true);
				return;
			}
			if (npc.hiredNPCInfo.isActive && entityplayer.capabilities.isCreativeMode && itemstack != null && itemstack.getItem() == Items.clock) {
				UUID hiringUUID;
				String playerName2;
				if (!world.isRemote && MinecraftServer.getServer().getConfigurationManager().func_152596_g(entityplayer.getGameProfile()) && (hiringUUID = npc.hiredNPCInfo.getHiringPlayerUUID()) != null && (playerName2 = getUsernameWithoutWebservice(hiringUUID)) != null) {
					IChatComponent msg = new ChatComponentText("Hired unit belongs to " + playerName2);
					msg.getChatStyle().setColor(EnumChatFormatting.GREEN);
					entityplayer.addChatMessage(msg);
				}
				event.setCanceled(true);
				return;
			}
		}
		if (!world.isRemote && entityplayer.capabilities.isCreativeMode && MinecraftServer.getServer().getConfigurationManager().func_152596_g(entityplayer.getGameProfile()) && itemstack != null && itemstack.getItem() == Items.clock && entity instanceof EntityLiving && (brandingPlayer = LOTRItemBrandingIron.getBrandingPlayer(entity)) != null && (playerName = getUsernameWithoutWebservice(brandingPlayer)) != null) {
			IChatComponent msg = new ChatComponentText("Entity was branded by " + playerName);
			msg.getChatStyle().setColor(EnumChatFormatting.GREEN);
			entityplayer.addChatMessage(msg);
			event.setCanceled(true);
			return;
		}
		if (entity instanceof EntityVillager && !LOTRConfig.enableVillagerTrading) {
			event.setCanceled(true);
		}
	}

	@SubscribeEvent
	public void onEntityJoinWorld(EntityJoinWorldEvent event) {
		EntityCreature entitycreature;
		Object obj;
		Entity entity = event.entity;
		World world = entity.worldObj;
		if (!world.isRemote && entity instanceof EntityXPOrb && !LOTRConfig.enchantingVanilla && world.provider instanceof LOTRWorldProvider) {
			event.setCanceled(true);
			return;
		}
		if (!world.isRemote && entity instanceof EntityCreature && (obj = LOTREntityRegistry.registeredNPCs.get(EntityList.getEntityString(entitycreature = (EntityCreature) entity))) != null) {
			LOTREntityRegistry.RegistryInfo info = (LOTREntityRegistry.RegistryInfo) obj;
			if (info.shouldTargetEnemies) {
				LOTREntityNPC.addTargetTasks(entitycreature, 100, LOTREntityAINearestAttackableTargetBasic.class);
			}
		}
		if (!world.isRemote && entity.getClass() == EntityFishHook.class && world.provider instanceof LOTRWorldProvider) {
			EntityFishHook oldFish = (EntityFishHook) entity;
			NBTTagCompound fishData = new NBTTagCompound();
			oldFish.writeToNBT(fishData);
			oldFish.setDead();
			LOTREntityFishHook newFish = new LOTREntityFishHook(world);
			newFish.readFromNBT(fishData);
			newFish.field_146042_b = oldFish.field_146042_b;
			if (newFish.field_146042_b != null) {
				newFish.field_146042_b.fishEntity = newFish;
				newFish.setPlayerID(newFish.field_146042_b.getEntityId());
			}
			world.spawnEntityInWorld(newFish);
			event.setCanceled(true);
		}
	}

	@SubscribeEvent
	public void onEntitySpawnAttempt(LivingSpawnEvent.CheckSpawn event) {
		EntityLivingBase entity = event.entityLiving;
		World world = entity.worldObj;
		if (!world.isRemote && entity instanceof EntityMob && LOTRBannerProtection.isProtected(world, entity, LOTRBannerProtection.anyBanner(), false)) {
			event.setResult(Event.Result.DENY);
		}
	}

	@SubscribeEvent
	public void onExplosionDetonate(ExplosionEvent.Detonate event) {
		Explosion expl = event.explosion;
		World world = event.world;
		Entity exploder = expl.exploder;
		if (!world.isRemote && exploder != null) {
			LOTRBannerProtection.IFilter protectFilter = null;
			if (exploder instanceof LOTREntityNPC || exploder instanceof EntityMob) {
				protectFilter = LOTRBannerProtection.anyBanner();
			} else if (exploder instanceof EntityThrowable) {
				protectFilter = LOTRBannerProtection.forThrown((EntityThrowable) exploder);
			} else if (exploder instanceof EntityTNTPrimed) {
				protectFilter = LOTRBannerProtection.forTNT((EntityTNTPrimed) exploder);
			} else if (exploder instanceof EntityMinecartTNT) {
				protectFilter = LOTRBannerProtection.forTNTMinecart((EntityMinecartTNT) exploder);
			}
			if (protectFilter != null) {
				List<ChunkPosition> blockList = expl.affectedBlockPositions;
				Collection<ChunkPosition> removes = new ArrayList<>();
				for (ChunkPosition blockPos : blockList) {
					int i = blockPos.chunkPosX;
					int j = blockPos.chunkPosY;
					int k = blockPos.chunkPosZ;
					if (!LOTRBannerProtection.isProtected(world, i, j, k, protectFilter, false)) {
						continue;
					}
					removes.add(blockPos);
				}
				blockList.removeAll(removes);
			}
		}
	}

	@SubscribeEvent
	public void onFillBucket(FillBucketEvent event) {
		EntityPlayer entityplayer = event.entityPlayer;
		ItemStack itemstack = event.current;
		World world = event.world;
		MovingObjectPosition target = event.target;
		if (target.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
			int i = target.blockX;
			int j = target.blockY;
			int k = target.blockZ;
			if (!world.isRemote && LOTRBannerProtection.isProtected(world, i, j, k, LOTRBannerProtection.forPlayer(entityplayer, LOTRBannerProtection.Permission.FULL), true)) {
				event.setCanceled(true);
				return;
			}
			if (world.provider instanceof LOTRWorldProviderUtumno && LOTRUtumnoLevel.forY(j) == LOTRUtumnoLevel.FIRE && itemstack != null && itemstack.getItem() == Items.water_bucket) {
				LOTRWorldProviderUtumno.doEvaporateFX(world, i, j, k);
				event.result = new ItemStack(Items.bucket);
				event.setResult(Event.Result.ALLOW);
			}
		}
	}

	@SubscribeEvent
	public void onHarvestCheck(HarvestCheck event) {
		EntityPlayer entityplayer = event.entityPlayer;
		Block block = event.block;
		ItemStack itemstack = entityplayer.getCurrentEquippedItem();
		if (itemstack != null && block instanceof BlockWeb && itemstack.getItem() instanceof ItemShears) {
			event.success = true;
		}
	}

	@SubscribeEvent
	public void onItemPickup(EntityItemPickupEvent event) {
		EntityPlayer entityplayer = event.entityPlayer;
		ItemStack itemstack = event.item.getEntityItem();
		if (!entityplayer.worldObj.isRemote) {
			if (itemstack.stackSize > 0) {
				for (int i = 0; i < entityplayer.inventory.getSizeInventory(); ++i) {
					ItemStack itemInSlot = entityplayer.inventory.getStackInSlot(i);
					if (itemInSlot == null || itemInSlot.getItem() != LOTRMod.pouch) {
						continue;
					}
					LOTRItemPouch.tryAddItemToPouch(itemInSlot, itemstack, true);
					if (itemstack.stackSize <= 0) {
						break;
					}
				}
				if (itemstack.stackSize <= 0) {
					event.setResult(Event.Result.ALLOW);
				}
			}
			if (itemstack.getItem() == Item.getItemFromBlock(LOTRMod.athelas)) {
				LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.findAthelas);
			}
			if (itemstack.getItem() == Item.getItemFromBlock(LOTRMod.clover) && itemstack.getItemDamage() == 1) {
				LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.findFourLeafClover);
			}
			if (itemstack.getItem() == LOTRMod.kineArawHorn) {
				LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.getKineArawHorn);
			}
			if (LOTRConfig.enchantingAutoRemoveVanilla) {
				dechant(itemstack, entityplayer);
			}
		}
	}

	@SubscribeEvent
	public void onItemUseFinish(PlayerUseItemEvent.Finish event) {
		EntityPlayer entityplayer = event.entityPlayer;
		World world = entityplayer.worldObj;
		ItemStack itemstack = event.item;
		if (!world.isRemote && LOTRPoisonedDrinks.isDrinkPoisoned(itemstack)) {
			LOTRPoisonedDrinks.addPoisonEffect(entityplayer, itemstack);
		}
	}

	@SubscribeEvent
	public void onItemUseStop(PlayerUseItemEvent.Stop event) {
		EntityPlayer entityplayer = event.entityPlayer;
		World world = entityplayer.worldObj;
		ItemStack itemstack = event.item;
		int usingTick = event.duration;
		if (itemstack != null && itemstack.getItem() instanceof ItemBow && !(itemstack.getItem() instanceof LOTRItemBow) && !(itemstack.getItem() instanceof LOTRItemCrossbow)) {
			if (!world.isRemote) {
				if (proxyBowItemServer == null) {
					proxyBowItemServer = new LOTRItemBow(Item.ToolMaterial.WOOD);
				}
				proxyBowItemServer.onPlayerStoppedUsing(itemstack, world, entityplayer, usingTick);
				proxyBowItemServer = null;
				event.setCanceled(true);
				return;
			}
			if (proxyBowItemClient == null) {
				proxyBowItemClient = new LOTRItemBow(Item.ToolMaterial.WOOD);
			}
			proxyBowItemClient.onPlayerStoppedUsing(itemstack, world, entityplayer, usingTick);
			proxyBowItemClient = null;
			event.setCanceled(true);
		}
	}

	@SubscribeEvent
	public void onLivingAttacked(LivingAttackEvent event) {
		EntityLivingBase entity = event.entityLiving;
		EntityLivingBase attacker = event.source.getEntity() instanceof EntityLivingBase ? (EntityLivingBase) event.source.getEntity() : null;
		World world = entity.worldObj;
		if (entity instanceof LOTRNPCMount && entity.riddenByEntity != null && attacker == entity.riddenByEntity) {
			cancelAttackEvent(event);
		}
		if (attacker instanceof EntityPlayer && !LOTRMod.canPlayerAttackEntity((EntityPlayer) attacker, entity, true)) {
			cancelAttackEvent(event);
		}
		if (attacker instanceof EntityCreature && !LOTRMod.canNPCAttackEntity((EntityCreature) attacker, entity, false)) {
			cancelAttackEvent(event);
		}
		if (event.source instanceof EntityDamageSourceIndirect) {
			ItemStack chestplate;
			Entity projectile = event.source.getSourceOfDamage();
			if (projectile instanceof EntityArrow || projectile instanceof LOTREntityCrossbowBolt || projectile instanceof LOTREntityDart) {
				boolean wearingAllGalvorn = true;
				for (int i = 0; i < 4; ++i) {
					ItemStack armour = entity.getEquipmentInSlot(i + 1);
					if (armour != null && armour.getItem() instanceof ItemArmor && ((ItemArmor) armour.getItem()).getArmorMaterial() == LOTRMaterial.GALVORN.toArmorMaterial()) {
						continue;
					}
					wearingAllGalvorn = false;
					break;
				}
				if (wearingAllGalvorn) {
					if (!world.isRemote && entity instanceof EntityPlayer) {
						((EntityPlayer) entity).inventory.damageArmor(event.ammount);
					}
					cancelAttackEvent(event);
				}
			}
			if (!world.isRemote && entity instanceof EntityPlayer && attacker instanceof LOTREntityOrc && projectile instanceof LOTREntitySpear && (chestplate = entity.getEquipmentInSlot(3)) != null && chestplate.getItem() == LOTRMod.bodyMithril) {
				LOTRLevelData.getData((EntityPlayer) entity).addAchievement(LOTRAchievement.hitByOrcSpear);
			}
		}
	}

	@SuppressWarnings("Convert2Lambda")
	@SubscribeEvent
	public void onLivingDeath(LivingDeathEvent event) {
		int i;
		EntityPlayer entityplayer;
		int k;
		Entity attacker;
		int j;
		EntityLivingBase entity = event.entityLiving;
		World world = entity.worldObj;
		DamageSource source = event.source;
		if (!world.isRemote && entity instanceof EntityPlayer) {
			entityplayer = (EntityPlayer) entity;
			i = MathHelper.floor_double(entityplayer.posX);
			j = MathHelper.floor_double(entityplayer.posY);
			k = MathHelper.floor_double(entityplayer.posZ);
			LOTRLevelData.getData(entityplayer).setDeathPoint(i, j, k);
			LOTRLevelData.getData(entityplayer).setDeathDimension(entityplayer.dimension);
		}
		if (!world.isRemote) {
			entityplayer = null;
			boolean creditHiredUnit = false;
			boolean byNearbyUnit = false;
			if (source.getEntity() instanceof EntityPlayer) {
				entityplayer = (EntityPlayer) source.getEntity();
			} else if (entity.func_94060_bK() instanceof EntityPlayer) {
				entityplayer = (EntityPlayer) entity.func_94060_bK();
			} else if (source.getEntity() instanceof LOTREntityNPC) {
				LOTREntityNPC npc = (LOTREntityNPC) source.getEntity();
				if (npc.hiredNPCInfo.isActive && npc.hiredNPCInfo.getHiringPlayer() != null) {
					entityplayer = npc.hiredNPCInfo.getHiringPlayer();
					creditHiredUnit = true;
					double nearbyDist = 64.0;
					byNearbyUnit = npc.getDistanceSqToEntity(entityplayer) <= nearbyDist * nearbyDist;
				}
			}
			if (entityplayer != null) {
				LOTRPlayerData playerData = LOTRLevelData.getData(entityplayer);
				LOTRFaction entityFaction = LOTRMod.getNPCFaction(entity);
				float prevAlignment = playerData.getAlignment(entityFaction);
				List<LOTRFaction> forcedBonusFactions = null;
				if (entity instanceof LOTREntityNPC) {
					forcedBonusFactions = ((LOTREntityNPC) entity).killBonusFactions;
				}
				boolean wasSelfDefenceAgainstAlliedUnit = false;
				if (!creditHiredUnit && prevAlignment > 0.0f && entity instanceof LOTREntityNPC) {
					LOTREntityNPC npc = (LOTREntityNPC) entity;
					if (npc.hiredNPCInfo.isActive && npc.hiredNPCInfo.wasAttackCommanded) {
						wasSelfDefenceAgainstAlliedUnit = true;
					}
				}
				LOTRAlignmentValues.AlignmentBonus alignmentBonus = null;
				if (!wasSelfDefenceAgainstAlliedUnit) {
					if (entity instanceof LOTREntityNPC) {
						LOTREntityNPC npc = (LOTREntityNPC) entity;
						alignmentBonus = new LOTRAlignmentValues.AlignmentBonus(npc.getAlignmentBonus(), npc.getEntityClassName());
						alignmentBonus.needsTranslation = true;
						alignmentBonus.isCivilianKill = npc.isCivilianNPC();
					} else {
						String s = EntityList.getEntityString(entity);
						Object obj = LOTREntityRegistry.registeredNPCs.get(s);
						if (obj != null) {
							LOTREntityRegistry.RegistryInfo info = (LOTREntityRegistry.RegistryInfo) obj;
							alignmentBonus = info.alignmentBonus;
							alignmentBonus.isCivilianKill = false;
						}
					}
				}
				// empty if block
				if (alignmentBonus != null && alignmentBonus.bonus != 0.0f && (!creditHiredUnit || byNearbyUnit)) {
					alignmentBonus.isKill = true;
					if (creditHiredUnit) {
						alignmentBonus.killByHiredUnit = true;
					}
					playerData.addAlignment(entityplayer, alignmentBonus, entityFaction, forcedBonusFactions, entity);
				}
				if (!creditHiredUnit) {
					if (entityFaction.allowPlayer) {
						LOTRFaction pledgeFac;
						playerData.getFactionData(entityFaction).addNPCKill();
						List<LOTRFaction> killBonuses = entityFaction.getBonusesForKilling();
						for (LOTRFaction enemy : killBonuses) {
							playerData.getFactionData(enemy).addEnemyKill();
						}
						if (!entityplayer.capabilities.isCreativeMode && entityFaction.inDefinedControlZone(entityplayer, Math.max(entityFaction.getControlZoneReducedRange(), 50))) {
							LOTRFactionBounties.forFaction(entityFaction).forPlayer(entityplayer).recordNewKill();
						}
						pledgeFac = playerData.getPledgeFaction();
						if (pledgeFac != null && (pledgeFac == entityFaction || pledgeFac.isAlly(entityFaction))) {
							playerData.onPledgeKill(entityplayer);
						}
					}
					float newAlignment = playerData.getAlignment(entityFaction);
					if (!wasSelfDefenceAgainstAlliedUnit && !entityplayer.capabilities.isCreativeMode && entityFaction != LOTRFaction.UNALIGNED) {
						int sentSpeeches = 0;
						int maxSpeeches = 5;
						double range = 8.0;
						List nearbyAlliedNPCs = world.selectEntitiesWithinAABB(EntityLiving.class, entity.boundingBox.expand(range, range, range), new IEntitySelector() {

							@Override
							public boolean isEntityApplicable(Entity entitySelect) {
								if (entitySelect.isEntityAlive()) {
									LOTRFaction fac = LOTRMod.getNPCFaction(entitySelect);
									return fac.isGoodRelation(entityFaction);
								}
								return false;
							}
						});
						for (Object nearbyAlliedNPC : nearbyAlliedNPCs) {
							String speech;
							LOTREntityNPC lotrnpc;
							EntityLiving npc = (EntityLiving) nearbyAlliedNPC;
							if (npc instanceof LOTREntityNPC) {
								LOTREntityNPC lotrNPC = (LOTREntityNPC) npc;
								if (lotrNPC.hiredNPCInfo.isActive && newAlignment > 0.0f || lotrNPC.hiredNPCInfo.isActive && lotrNPC.hiredNPCInfo.getHiringPlayer() == entityplayer) {
									continue;
								}
							}
							if (npc.getAttackTarget() != null) {
								continue;
							}
							npc.setAttackTarget(entityplayer);
							if (!(npc instanceof LOTREntityNPC) || sentSpeeches >= maxSpeeches || (speech = (lotrnpc = (LOTREntityNPC) npc).getSpeechBank(entityplayer)) == null || lotrnpc.getDistanceSqToEntity(entityplayer) >= range) {
								continue;
							}
							lotrnpc.sendSpeechBank(entityplayer, speech);
							++sentSpeeches;
						}
					}
					if (!playerData.isSiegeActive()) {
						List<LOTRMiniQuest> miniquests = playerData.getMiniQuests();
						for (LOTRMiniQuest quest : miniquests) {
							quest.onKill(entityplayer, entity);
						}
						if (entity instanceof EntityPlayer) {
							EntityPlayer slainPlayer = (EntityPlayer) entity;
							List<LOTRMiniQuest> slainMiniquests = LOTRLevelData.getData(slainPlayer).getMiniQuests();
							for (LOTRMiniQuest quest : slainMiniquests) {
								quest.onKilledByPlayer(slainPlayer, entityplayer);
							}
						}
					}
				}
			}
		}
		if (!world.isRemote && source.getEntity() instanceof EntityPlayer) {
			entityplayer = (EntityPlayer) source.getEntity();
			LOTREnchantmentHelper.onKillEntity(entityplayer, entity, source);
		}
		if (!world.isRemote && source.getEntity() instanceof EntityPlayer && source.getSourceOfDamage() != null && source.getSourceOfDamage().getClass() == LOTREntitySpear.class && entity != (entityplayer = (EntityPlayer) source.getEntity()) && entityplayer.getDistanceSqToEntity(entity) >= 2500.0) {
			LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.useSpearFromFar);
		}
		if (!world.isRemote && entity instanceof LOTREntityButterfly && source.getEntity() instanceof EntityPlayer) {
			entityplayer = (EntityPlayer) source.getEntity();
			LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.killButterfly);
		}
		if (!world.isRemote && entity.getClass() == LOTREntityHorse.class && source.getEntity() instanceof EntityPlayer) {
			List rohirrimList;
			entityplayer = (EntityPlayer) source.getEntity();
			if (!entityplayer.capabilities.isCreativeMode && !(rohirrimList = world.getEntitiesWithinAABB(LOTREntityRohanMan.class, entityplayer.boundingBox.expand(16.0, 16.0, 16.0))).isEmpty()) {
				boolean sentMessage = false;
				boolean penalty = false;
				for (Object element : rohirrimList) {
					LOTREntityRohanMan rohirrim = (LOTREntityRohanMan) element;
					if (rohirrim.hiredNPCInfo.isActive && rohirrim.hiredNPCInfo.getHiringPlayer() == entityplayer) {
						continue;
					}
					rohirrim.setAttackTarget(entityplayer);
					if (!sentMessage) {
						rohirrim.sendSpeechBank(entityplayer, "rohan/warrior/avengeHorse");
						sentMessage = true;
					}
					if (penalty) {
						continue;
					}
					LOTRLevelData.getData(entityplayer).addAlignment(entityplayer, LOTRAlignmentValues.ROHAN_HORSE_PENALTY, LOTRFaction.ROHAN, entity);
					penalty = true;
				}
			}
		}
		if (!world.isRemote) {
			EntityPlayer attackingPlayer = null;
			LOTREntityNPC attackingHiredUnit = null;
			if (source.getEntity() instanceof EntityPlayer) {
				attackingPlayer = (EntityPlayer) source.getEntity();
			} else if (source.getEntity() instanceof LOTREntityNPC) {
				LOTREntityNPC npc = (LOTREntityNPC) source.getEntity();
				if (npc.hiredNPCInfo.isActive && npc.hiredNPCInfo.getHiringPlayer() != null) {
					attackingPlayer = npc.hiredNPCInfo.getHiringPlayer();
					attackingHiredUnit = npc;
				}
			}
			if (attackingPlayer != null) {
				boolean isFoe;
				isFoe = LOTRLevelData.getData(attackingPlayer).getAlignment(LOTRMod.getNPCFaction(entity)) < 0.0f;
				if (isFoe) {
					if (attackingHiredUnit != null) {
						if (attackingHiredUnit instanceof LOTREntityWargBombardier) {
							LOTRLevelData.getData(attackingPlayer).addAchievement(LOTRAchievement.hireWargBombardier);
						}
						if (attackingHiredUnit instanceof LOTREntityOlogHai) {
							LOTRLevelData.getData(attackingPlayer).addAchievement(LOTRAchievement.hireOlogHai);
						}
					} else {
						LOTREntityOrc orc;
						if (attackingPlayer.isPotionActive(Potion.confusion.id)) {
							LOTRLevelData.getData(attackingPlayer).addAchievement(LOTRAchievement.killWhileDrunk);
						}
						if (entity instanceof LOTREntityOrc && (orc = (LOTREntityOrc) entity).isOrcBombardier() && orc.npcItemsInv.getBomb() != null) {
							LOTRLevelData.getData(attackingPlayer).addAchievement(LOTRAchievement.killBombardier);
						}
						if (source.getSourceOfDamage() instanceof LOTREntityCrossbowBolt) {
							LOTRLevelData.getData(attackingPlayer).addAchievement(LOTRAchievement.useCrossbow);
						}
						if (source.getSourceOfDamage() instanceof LOTREntityThrowingAxe && ((LOTREntityProjectileBase) source.getSourceOfDamage()).getProjectileItem().getItem() == LOTRMod.throwingAxeDwarven) {
							LOTRLevelData.getData(attackingPlayer).addAchievement(LOTRAchievement.useDwarvenThrowingAxe);
						}
					}
				}
			}
		}
		if (!world.isRemote && LOTRMod.getNPCFaction(entity) == LOTRFaction.UTUMNO && LOTRDimension.getCurrentDimensionWithFallback(world) == LOTRDimension.UTUMNO && (attacker = source.getEntity()) instanceof EntityPlayer) {
			i = MathHelper.floor_double(entity.posX);
			j = MathHelper.floor_double(entity.boundingBox.minY);
			k = MathHelper.floor_double(entity.posZ);
			int range = LOTRBlockUtumnoReturnPortalBase.RANGE;
			for (int i1 = -range; i1 <= range; ++i1) {
				for (int j1 = -range; j1 <= range; ++j1) {
					for (int k1 = -range; k1 <= range; ++k1) {
						int i2 = i + i1;
						int j2 = j + j1;
						int k2 = k + k1;
						if (world.getBlock(i2, j2, k2) != LOTRMod.utumnoReturnPortalBase) {
							continue;
						}
						int meta = world.getBlockMetadata(i2, j2, k2);
						if (++meta >= LOTRBlockUtumnoReturnPortalBase.MAX_SACRIFICE) {
							world.createExplosion(attacker, i2 + 0.5, j2 + 0.5, k2 + 0.5, 0.0f, false);
							world.setBlock(i2, j2, k2, LOTRMod.utumnoReturnPortal, 0, 3);
						} else {
							world.setBlockMetadataWithNotify(i2, j2, k2, meta, 3);
						}
						IMessage packet = new LOTRPacketUtumnoKill(entity.getEntityId(), i2, j2, k2);
						LOTRPacketHandler.networkWrapper.sendToAllAround(packet, new NetworkRegistry.TargetPoint(entity.dimension, i2 + 0.5, j2 + 0.5, k2 + 0.5, 32.0));
					}
				}
			}
		}
		if (!world.isRemote && entity instanceof EntityPlayer) {
			entityplayer = (EntityPlayer) entity;
			if (LOTREnchantmentHelper.hasMeleeOrRangedEnchant(source, LOTREnchantment.headhunting)) {
				ItemStack playerHead = new ItemStack(Items.skull, 1, 3);
				GameProfile profile = entityplayer.getGameProfile();
				NBTTagCompound profileData = new NBTTagCompound();
				NBTUtil.func_152460_a(profileData, profile);
				playerHead.setTagInfo("SkullOwner", profileData);
				entityplayer.entityDropItem(playerHead, 0.0f);
			}
		}
	}

	@SubscribeEvent
	public void onLivingDrops(LivingDropsEvent event) {
		EntityLivingBase entity = event.entityLiving;
		Random rand = entity.getRNG();
		int i = event.lootingLevel;
		if (entity instanceof EntitySheep && LOTRConfig.dropMutton) {
			int meat = rand.nextInt(3) + rand.nextInt(1 + i);
			for (int l = 0; l < meat; ++l) {
				if (entity.isBurning()) {
					entity.dropItem(LOTRMod.muttonCooked, 1);
					continue;
				}
				entity.dropItem(LOTRMod.muttonRaw, 1);
			}
		}
	}

	@SubscribeEvent
	public void onLivingHurt(LivingHurtEvent event) {
		EntityLivingBase entity = event.entityLiving;
		EntityLivingBase attacker = event.source.getEntity() instanceof EntityLivingBase ? (EntityLivingBase) event.source.getEntity() : null;
		World world = entity.worldObj;
		if (entity instanceof EntityPlayerMP && event.source == LOTRDamage.frost) {
			LOTRDamage.doFrostDamage((EntityPlayerMP) entity);
		}
		if (!world.isRemote) {
			ItemStack weapon;
			int preMaxHurtResTime = entity.maxHurtResistantTime;
			int maxHurtResTime = 20;
			if (attacker != null && LOTRWeaponStats.isMeleeWeapon(weapon = attacker.getHeldItem())) {
				maxHurtResTime = LOTRWeaponStats.getAttackTimeWithBase(weapon, 20);
			}
			entity.maxHurtResistantTime = maxHurtResTime = Math.min(maxHurtResTime, 20);
			if (entity.hurtResistantTime == preMaxHurtResTime) {
				entity.hurtResistantTime = maxHurtResTime;
			}
		}
		if (attacker != null && event.source.getSourceOfDamage() == attacker) {
			ItemStack usingItem;
			EntityPlayerMP entityplayer;
			ItemStack weapon = attacker.getHeldItem();
			if (!world.isRemote && entity instanceof EntityPlayerMP && (entityplayer = (EntityPlayerMP) entity).isUsingItem() && (usingItem = entityplayer.getHeldItem()) != null && LOTRWeaponStats.isRangedWeapon(usingItem)) {
				entityplayer.clearItemInUse();
				IMessage packet = new LOTRPacketStopItemUse();
				LOTRPacketHandler.networkWrapper.sendTo(packet, entityplayer);
			}
			boolean wearingAllMorgul = true;
			for (int i = 0; i < 4; ++i) {
				ItemStack armour = entity.getEquipmentInSlot(i + 1);
				if (armour != null && armour.getItem() instanceof ItemArmor && ((ItemArmor) armour.getItem()).getArmorMaterial() == LOTRMaterial.MORGUL.toArmorMaterial()) {
					continue;
				}
				wearingAllMorgul = false;
				break;
			}
			if (wearingAllMorgul && !world.isRemote && weapon != null && weapon.isItemStackDamageable()) {
				int damage = weapon.getItemDamage();
				int maxDamage = weapon.getMaxDamage();
				float durability = 1.0f - (float) damage / maxDamage;
				int newDamage = Math.round((1.0f - durability * 0.9f) * maxDamage);
				newDamage = Math.min(newDamage, maxDamage);
				weapon.damageItem(newDamage - damage, attacker);
			}
			if (weapon != null) {
				Item.ToolMaterial material = null;
				if (weapon.getItem() instanceof ItemTool) {
					material = ((ItemTool) weapon.getItem()).func_150913_i();
				} else if (weapon.getItem() instanceof ItemSword) {
					material = LOTRMaterial.getToolMaterialByName(((ItemSword) weapon.getItem()).getToolMaterialName());
				}
				if (material != null && material == LOTRMaterial.MORGUL.toToolMaterial() && !world.isRemote) {
					entity.addPotionEffect(new PotionEffect(Potion.wither.id, 160));
				}
			}
		}
		if (event.source.getSourceOfDamage() instanceof LOTREntityArrowPoisoned && !world.isRemote) {
			LOTRItemDagger.applyStandardPoison(entity);
		}
		if (!world.isRemote) {
			if (LOTREnchantmentHelper.hasMeleeOrRangedEnchant(event.source, LOTREnchantment.fire)) {
				IMessage packet = new LOTRPacketWeaponFX(LOTRPacketWeaponFX.Type.INFERNAL, entity);
				LOTRPacketHandler.networkWrapper.sendToAllAround(packet, LOTRPacketHandler.nearEntity(entity, 64.0));
			}
			if (LOTREnchantmentHelper.hasMeleeOrRangedEnchant(event.source, LOTREnchantment.chill)) {
				LOTREnchantmentWeaponSpecial.doChillAttack(entity);
			}
		}
	}

	@SubscribeEvent
	public void onLivingSetAttackTarget(LivingSetAttackTargetEvent event) {
		boolean sneaking = event.target instanceof LOTREntityRanger && ((LOTREntityRanger) event.target).isRangerSneaking();
		if (event.target instanceof LOTREntityGaladhrimWarden && ((LOTREntityGaladhrimWarden) event.target).isElfSneaking()) {
			sneaking = true;
		}
		if (event.target instanceof LOTREntityHuornBase && !((LOTREntityHuornBase) event.target).isHuornActive()) {
			sneaking = true;
		}
		if (event.target instanceof EntityPlayer) {
			boolean cloak;
			EntityPlayer entityplayer = (EntityPlayer) event.target;
			cloak = LOTRLevelData.getData((EntityPlayer) event.target).isPlayerWearingFull(entityplayer) == LOTRMaterial.HITHLAIN;
			if (cloak && entityplayer.getLastAttacker() != event.entityLiving && entityplayer.getDistanceSqToEntity(event.entityLiving) >= 64.0) {
				sneaking = true;
			}
		}
		if (event.entityLiving instanceof EntityLiving && sneaking) {
			((EntityLiving) event.entityLiving).setAttackTarget(null);
		}
	}

    @SubscribeEvent
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        EntityLivingBase entity = event.entityLiving;
        World world = entity.worldObj;

        if (!world.isRemote) {
            LOTREnchantmentHelper.onEntityUpdate(entity);
        }
        handleEnchantmentRemoval(world, entity);

        if (shouldSpawnWraith(entity, world)) {
            spawnWraith(entity, world);
        }

        handleConditionsAffectingMob(entity, world);
        handleSpecialEffects(entity, world);

        if (world.isRemote) {
            handleRemoteInfo(entity);
        }
    }

    private void handleEnchantmentRemoval(World world, EntityLivingBase entity) {
        if (LOTRConfig.enchantingAutoRemoveVanilla && !world.isRemote && entity instanceof EntityPlayer && entity.ticksExisted % 60 == 0) {
            EntityPlayer entityplayer = (EntityPlayer) entity;
            for (int l2 = 0; l2 < entityplayer.inventory.getSizeInventory(); ++l2) {
                ItemStack itemstack = entityplayer.inventory.getStackInSlot(l2);
                if (itemstack == null) {
                    continue;
                }
                dechant(itemstack, entityplayer);
            }
        }
    }

    private boolean shouldSpawnWraith(EntityLivingBase entity, World world) {
        if (canSpawnWraith(entity, world)) {
            if (isDeadMarshes(entity, world)) {
                if (!hasNearbyWraiths(entity, world)) {
                    spawnWraith(entity, world);
                    return true;
                }
            }
        }
        return false;
    }
    private boolean inWater2;
    private boolean canSpawnWraith(EntityLivingBase entity, World world) {
        inWater2 = entity.isInWater();
        boolean isPlayerCreative = entity instanceof EntityPlayer && ((EntityPlayer) entity).capabilities.isCreativeMode;
        return !world.isRemote && LOTRMod.canSpawnMobs(world) && entity.isEntityAlive()
            && inWater2 && entity.ridingEntity == null
            && !(entity instanceof EntityWaterMob) && !(entity instanceof LOTREntityMarshWraith) && !isPlayerCreative;
    }

    private boolean isDeadMarshes(EntityLivingBase entity, World world) {
        int posX = MathHelper.floor_double(entity.posX);
        int posZ = MathHelper.floor_double(entity.posZ);

        int j2 = world.getTopSolidOrLiquidBlock(posX, posZ);
        while (world.getBlock(posX, j2 + 1, posZ).getMaterial().isLiquid() || world.getBlock(posX, j2 + 1, posZ).getMaterial().isSolid()) {
            ++j2;
        }

        return j2 - entity.boundingBox.minY < 2.0 && world.getBlock(posX, j2, posZ).getMaterial() == Material.water
            && world.getBiomeGenForCoords(posX, posZ) instanceof LOTRBiomeGenDeadMarshes;
    }

    private boolean hasNearbyWraiths(EntityLivingBase entity, World world) {
        List<LOTREntityMarshWraith> nearbyWraiths = world.getEntitiesWithinAABB(LOTREntityMarshWraith.class, entity.boundingBox.expand(15.0, 15.0, 15.0));
        return nearbyWraiths.stream()
            .anyMatch(wraith -> wraith.getAttackTarget() == entity && wraith.getDeathFadeTime() == 0);
    }

    private void spawnWraith(EntityLivingBase entity, World world) {
        int posX = MathHelper.floor_double(entity.posX);
        int posZ = MathHelper.floor_double(entity.posZ);
        LOTREntityMarshWraith wraith = new LOTREntityMarshWraith(world);
        int i1 = posX + MathHelper.getRandomIntegerInRange(world.rand, -3, 3);
        int k1 = posZ + MathHelper.getRandomIntegerInRange(world.rand, -3, 3);
        int j1 = world.getTopSolidOrLiquidBlock(i1, k1);
        wraith.setLocationAndAngles(i1 + 0.5, j1, k1 + 0.5, world.rand.nextFloat() * 360.0f, 0.0f);

        double distanceSq = wraith.getDistanceSqToEntity(entity);
        if (distanceSq <= 144.0) {
            world.spawnEntityInWorld(wraith);
            wraith.setAttackTarget(entity);
            wraith.attackTargetUUID = entity.getUniqueID();
            world.playSoundAtEntity(wraith, "lotr:wraith.spawn", 1.0f, 0.7f + world.rand.nextFloat() * 0.6f);
        }
    }

    private void handleConditionsAffectingMob(EntityLivingBase entity, World world) {
        inWater2 = entity.isInWater();
        if (!world.isRemote && LOTRMod.canSpawnMobs(world) && entity.isEntityAlive() && world.isDaytime()) {
            float f = 0.0f;
            int bounders = 0;

            LOTRFaction entityFaction = LOTRMod.getNPCFaction(entity);
            if (LOTRFaction.HOBBIT.isBadRelation(entityFaction)) {
                float health = entity.getMaxHealth() + entity.getTotalArmorValue();
                f = health * 2.5f;
                int healthDivided = (int) (health / 15.0f);
                bounders = 2 + world.rand.nextInt(healthDivided + 1);
            } else if (entity instanceof EntityPlayer) {
                EntityPlayer entityplayer = (EntityPlayer) entity;
                float alignment = LOTRLevelData.getData(entityplayer).getAlignment(LOTRFaction.HOBBIT);
                if (!entityplayer.capabilities.isCreativeMode && alignment < 0.0f) {
                    f = -alignment;
                    int alignmentDivided = (int) (f / 50.0f);
                    bounders = 2 + world.rand.nextInt(alignmentDivided + 1);
                }
            }

            if (f > 0.0f) {
                f = Math.min(f, 2000.0f);
                int chance = (int) (2000000.0f / f);
                bounders = Math.min(bounders, 5);

                int i3 = MathHelper.floor_double(entity.posX);
                int k3 = MathHelper.floor_double(entity.posZ);
                BiomeGenBase biome = world.getBiomeGenForCoords(i3, k3);

                if (world.rand.nextInt(chance) == 0 && biome instanceof LOTRBiomeGenShire && world.getEntitiesWithinAABB(LOTREntityHobbitBounder.class, entity.boundingBox.expand(12.0, 6.0, 12.0)).isEmpty()) {
                    boolean sentMessage = false;
                    boolean playedHorn = false;

                    for (int l4 = 0; l4 < bounders; ++l4) {
                        LOTREntityHobbitBounder bounder = new LOTREntityHobbitBounder(world);

                        for (int l1 = 0; l1 < 32; ++l1) {
                            int i1 = i3 - world.rand.nextInt(12) + world.rand.nextInt(12);
                            int j1 = world.getTopSolidOrLiquidBlock(i1, k3 - world.rand.nextInt(12) + world.rand.nextInt(12));
                            int k1 = k3 - world.rand.nextInt(12) + world.rand.nextInt(12);

                            if (world.getBlock(i1, j1 - 1, k1).isSideSolid(world, i1, j1 - 1, k1, ForgeDirection.UP) && !world.getBlock(i1, j1, k1).isNormalCube() && !world.getBlock(i1, j1 + 1, k1).isNormalCube()) {
                                bounder.setLocationAndAngles(i1 + 0.5, j1, k1 + 0.5, 0.0f, 0.0f);

                                if (bounder.getCanSpawnHere() && entity.getDistanceToEntity(bounder) > 6.0) {
                                    bounder.onSpawnWithEgg(null);
                                    world.spawnEntityInWorld(bounder);
                                    bounder.setAttackTarget(entity);

                                    if (!sentMessage && entity instanceof EntityPlayer) {
                                        bounder.sendSpeechBank((EntityPlayer) entity, bounder.getSpeechBank((EntityPlayer) entity));
                                        sentMessage = true;
                                    }

                                    if (!playedHorn) {
                                        world.playSoundAtEntity(bounder, "lotr:item.horn", 2.0f, 2.0f);
                                        playedHorn = true;
                                    }

                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
        if (!world.isRemote && entity.isEntityAlive() && inWater2 && entity.ridingEntity == null && entity.ticksExisted % 10 == 0) {
            boolean flag = !(entity instanceof EntityPlayer) || !((EntityPlayer) entity).capabilities.isCreativeMode;
            if (entity instanceof LOTREntityMirkwoodSpider) {
                flag = false;
            }
            if (flag && world.getBiomeGenForCoords(MathHelper.floor_double(entity.posX), MathHelper.floor_double(entity.posZ)) instanceof LOTRBiomeGenMirkwoodCorrupted) {
                entity.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 600, 1));
                entity.addPotionEffect(new PotionEffect(Potion.digSlowdown.id, 600, 1));
                entity.addPotionEffect(new PotionEffect(Potion.weakness.id, 600));
                entity.addPotionEffect(new PotionEffect(Potion.blindness.id, 600));
            }
        }
    }

    private void handleSpecialEffects(EntityLivingBase entity, World world) {
        int i;
        inWater2 = entity.isInWater();
        if (!world.isRemote && entity.isEntityAlive() && inWater2 && entity.ridingEntity == null && entity.ticksExisted % 10 == 0) {
            boolean applyEffects = true;

            if (entity instanceof EntityPlayer) {
                EntityPlayer entityplayer = (EntityPlayer) entity;
                if (entityplayer.capabilities.isCreativeMode) {
                    applyEffects = false;
                } else {
                    float alignment = LOTRLevelData.getData(entityplayer).getAlignment(LOTRFaction.MORDOR);
                    float level = 100.0f;

                    if (alignment > level || world.rand.nextInt((int) Math.max(level, 1)) < alignment) {
                        applyEffects = false;
                    }
                }
            }

            if (LOTRMod.getNPCFaction(entity).isGoodRelation(LOTRFaction.MORDOR)) {
                applyEffects = false;
            }

            if (applyEffects && world.getBiomeGenForCoords(MathHelper.floor_double(entity.posX), MathHelper.floor_double(entity.posZ)) instanceof LOTRBiomeGenMorgulVale) {
                entity.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 600, 1));
                entity.addPotionEffect(new PotionEffect(Potion.digSlowdown.id, 600, 1));
                entity.addPotionEffect(new PotionEffect(Potion.weakness.id, 600));
                entity.addPotionEffect(new PotionEffect(Potion.poison.id, 100));
            }
        }
        if (!world.isRemote && entity.isEntityAlive()) {
            IAttributeInstance speedAttribute = entity.getEntityAttribute(SharedMonsterAttributes.movementSpeed);

            // Wood Elven Scout armor speed boost
            if (entity.ticksExisted % 10 == 0) {
                boolean wearingAllWoodElvenScout = true;
                for (i = 0; i < 4; ++i) {
                    ItemStack armour = entity.getEquipmentInSlot(i + 1);
                    if (armour != null && armour.getItem() instanceof ItemArmor && ((ItemArmor) armour.getItem()).getArmorMaterial() != LOTRMaterial.WOOD_ELVEN_SCOUT.toArmorMaterial()) {
                        wearingAllWoodElvenScout = false;
                        break;
                    }
                }

                if (speedAttribute.getModifier(LOTREntityWoodElfScout.scoutArmorSpeedBoost.getID()) != null) {
                    speedAttribute.removeModifier(LOTREntityWoodElfScout.scoutArmorSpeedBoost);
                }

                if (wearingAllWoodElvenScout) {
                    speedAttribute.applyModifier(LOTREntityWoodElfScout.scoutArmorSpeedBoost);
                }
            }

            // Lance speed boost
            ItemStack weapon = entity.getHeldItem();
            boolean lanceOnFoot = weapon != null && weapon.getItem() instanceof LOTRItemLance && entity.ridingEntity == null;

            if (speedAttribute.getModifier(LOTRItemLance.lanceSpeedBoost_id) != null) {
                speedAttribute.removeModifier(LOTRItemLance.lanceSpeedBoost);
            }

            if (lanceOnFoot) {
                boolean notCreative = !(entity instanceof EntityPlayer) || !((EntityPlayer) entity).capabilities.isCreativeMode;
                if (notCreative) {
                    speedAttribute.applyModifier(LOTRItemLance.lanceSpeedBoost);
                }
            }
        }
        if (!world.isRemote && entity.isEntityAlive() && entity.ticksExisted % 20 == 0) {
            boolean shouldApplyFrost = !(entity instanceof LOTREntityNPC) || !((LOTREntityNPC) entity).isImmuneToFrost;

            if (entity instanceof EntityPlayer) {
                shouldApplyFrost = !((EntityPlayer) entity).capabilities.isCreativeMode;
            }

            if (shouldApplyFrost) {
                int posX = MathHelper.floor_double(entity.posX);
                int posY = MathHelper.floor_double(entity.boundingBox.minY);
                int posZ = MathHelper.floor_double(entity.posZ);

                BiomeGenBase biome = world.getBiomeGenForCoords(posX, posZ);
                boolean isForodwaithBiome = biome instanceof LOTRBiomeGenForodwaith;
                boolean canSeeSkyOrInWater = world.canBlockSeeTheSky(posX, posY, posZ) || inWater2;
                boolean isLightLow = world.getSavedLightValue(EnumSkyBlock.Block, posX, posY, posZ) < 10;

                if (isForodwaithBiome && canSeeSkyOrInWater && isLightLow) {
                    int frostChance = 50;
                    int frostProtection = 0;

                    for (int slot = 0; slot < 4; ++slot) {
                        ItemStack armor = entity.getEquipmentInSlot(slot + 1);
                        if (armor == null || !(armor.getItem() instanceof ItemArmor)) {
                            continue;
                        }

                        ItemArmor.ArmorMaterial material = ((ItemArmor) armor.getItem()).getArmorMaterial();
                        Item materialItem = material.func_151685_b();

                        if (materialItem == Items.leather) {
                            frostProtection += 50;
                        } else if (materialItem == LOTRMod.fur) {
                            frostProtection += 100;
                        }
                    }

                    frostChance += frostProtection;

                    if (world.isRaining()) {
                        frostChance /= 3;
                    }
                    if (inWater2) {
                        frostChance /= 20;
                    }

                    if (world.rand.nextInt(Math.max(frostChance, 1)) == 0) {
                        entity.attackEntityFrom(LOTRDamage.frost, 1.0f);
                    }
                }
            }
        }
        if (!world.isRemote && entity.isEntityAlive() && entity.ticksExisted % 20 == 0 && (!(entity instanceof EntityPlayer) || !((EntityPlayer) entity).capabilities.isCreativeMode)) {
            int posX = MathHelper.floor_double(entity.posX);
            int posY = MathHelper.floor_double(entity.boundingBox.minY);
            int posZ = MathHelper.floor_double(entity.posZ);

            BiomeGenBase biome = world.getBiomeGenForCoords(posX, posZ);
            boolean isNearHarad = biome instanceof LOTRBiomeGenNearHarad;
            boolean isDaytime = world.isDaytime();
            boolean canSeeSky = world.canBlockSeeTheSky(posX, posY, posZ);
            boolean isNotInWater = !inWater2;

            if (isNearHarad && isNotInWater && canSeeSky && isDaytime) {
                int burnChance = 50;
                int burnProtection = 0;

                for (int slot = 0; slot < 4; ++slot) {
                    ItemStack armour = entity.getEquipmentInSlot(slot + 1);
                    if (armour == null || !(armour.getItem() instanceof ItemArmor)) {
                        continue;
                    }
                    ItemArmor.ArmorMaterial material = ((ItemArmor) armour.getItem()).getArmorMaterial();

                    if (material.customCraftingMaterial == Items.leather) {
                        burnProtection += 50;
                    } else if (material == LOTRMaterial.HARAD_ROBES.toArmorMaterial()) {
                        burnProtection += 400;
                    } else if (material == LOTRMaterial.HARAD_NOMAD.toArmorMaterial()) {
                        burnProtection += 200;
                    }
                }

                burnChance += burnProtection;

                if (world.rand.nextInt(Math.max(burnChance, 1)) == 0 && entity.attackEntityFrom(DamageSource.onFire, 1.0f) && entity instanceof EntityPlayerMP) {
                    LOTRDamage.doBurnDamage((EntityPlayerMP) entity);
                }
            }

        }

    }

    private void handleRemoteInfo(EntityLivingBase entity) {
        World world = entity.worldObj;
        if (world.isRemote) {
            LOTRPlateFallingInfo.getOrCreateFor(entity, true).update();
        }
    }

	@SubscribeEvent
	public void onMinecartInteract(MinecartInteractEvent event) {
		EntityPlayer entityplayer = event.player;
		World world = entityplayer.worldObj;
		ItemStack itemstack = entityplayer.inventory.getCurrentItem();
		EntityMinecart minecart = event.minecart;
		if (minecart instanceof EntityMinecartChest && itemstack != null && itemstack.getItem() instanceof LOTRItemPouch) {
			if (!world.isRemote) {
				int pouchSlot = entityplayer.inventory.currentItem;
				entityplayer.openGui(LOTRMod.instance, LOTRCommonProxy.packGuiIDWithSlot(64, pouchSlot), world, minecart.getEntityId(), 0, 0);
			}
			event.setCanceled(true);
		}
	}

	@SubscribeEvent
	@SuppressWarnings("all")
	public void onMinecartUpdate(MinecartUpdateEvent event) {
	}

	@SubscribeEvent
	public void onPlayerChangedDimension(PlayerChangedDimensionEvent event) {
		EntityPlayer entityplayer = event.player;
		if (!entityplayer.worldObj.isRemote) {
			LOTRLevelData.sendAlignmentToAllPlayersInWorld(entityplayer, entityplayer.worldObj);
			LOTRLevelData.sendAllAlignmentsInWorldToPlayer(entityplayer, entityplayer.worldObj);
			LOTRLevelData.sendShieldToAllPlayersInWorld(entityplayer, entityplayer.worldObj);
			LOTRLevelData.sendAllShieldsInWorldToPlayer(entityplayer, entityplayer.worldObj);
		}
	}

	@SubscribeEvent
	public void onPlayerLogin(PlayerLoggedInEvent event) {
		EntityPlayer entityplayer = event.player;
		World world = entityplayer.worldObj;
		if (!world.isRemote) {
			EntityPlayerMP entityplayermp = (EntityPlayerMP) entityplayer;
			if (world.provider.terrainType instanceof LOTRWorldTypeMiddleEarth && entityplayermp.dimension == 0 && !LOTRLevelData.getData(entityplayermp).getTeleportedME()) {
				int dimension = LOTRDimension.MIDDLE_EARTH.dimensionID;
				LOTRTeleporter teleporter = new LOTRTeleporter(DimensionManager.getWorld(dimension), false);
				MinecraftServer.getServer().getConfigurationManager().transferPlayerToDimension(entityplayermp, dimension, teleporter);
				LOTRLevelData.getData(entityplayermp).setTeleportedME(true);
			}
			LOTRLevelData.sendLoginPacket(entityplayermp);
			LOTRLevelData.sendPlayerData(entityplayermp);
			LOTRLevelData.sendAlignmentToAllPlayersInWorld(entityplayer, world);
			LOTRLevelData.sendAllAlignmentsInWorldToPlayer(entityplayer, world);
			LOTRLevelData.sendShieldToAllPlayersInWorld(entityplayermp, world);
			LOTRLevelData.sendAllShieldsInWorldToPlayer(entityplayermp, world);
			LOTRDate.sendUpdatePacket(entityplayermp, false);
			LOTRFactionRelations.sendAllRelationsTo(entityplayermp);
			LOTRPlayerData pd = LOTRLevelData.getData(entityplayermp);
			pd.send35AlignmentChoice(entityplayermp, world);
			pd.updateFastTravelClockFromLastOnlineTime(entityplayermp, world);
		}
	}

	@SubscribeEvent
	public void onPlayerRespawn(PlayerRespawnEvent event) {
		EntityPlayer entityplayer = event.player;
		World world = entityplayer.worldObj;
		if (!world.isRemote && entityplayer instanceof EntityPlayerMP && world instanceof WorldServer) {
			EntityPlayerMP entityplayermp = (EntityPlayerMP) entityplayer;
			WorldServer worldserver = (WorldServer) world;
			ChunkCoordinates deathPoint = LOTRLevelData.getData(entityplayermp).getDeathPoint();
			int deathDimension = LOTRLevelData.getData(entityplayermp).getDeathDimension();
			if (deathDimension == LOTRDimension.MIDDLE_EARTH.dimensionID) {
				if (LOTRConfig.middleEarthRespawning) {
					double respawnThreshold;
					boolean hasBed;
					ChunkCoordinates bedLocation = entityplayermp.getBedLocation(entityplayermp.dimension);
					hasBed = bedLocation != null;
					if (hasBed) {
						hasBed = EntityPlayer.verifyRespawnCoordinates(worldserver, bedLocation, entityplayermp.isSpawnForced(entityplayermp.dimension)) != null;
					}
					ChunkCoordinates spawnLocation = hasBed ? bedLocation : worldserver.getSpawnPoint();
					respawnThreshold = hasBed ? LOTRConfig.MERBedRespawnThreshold : LOTRConfig.MERWorldRespawnThreshold;
					if (deathPoint != null) {
						boolean flag;
						flag = deathPoint.getDistanceSquaredToChunkCoordinates(spawnLocation) > respawnThreshold * respawnThreshold;
						if (flag) {
							double randomDistance = MathHelper.getRandomIntegerInRange(worldserver.rand, LOTRConfig.MERMinRespawn, LOTRConfig.MERMaxRespawn);
							float angle = worldserver.rand.nextFloat() * 3.1415927f * 2.0f;
							int i = deathPoint.posX + (int) (randomDistance * MathHelper.sin(angle));
							int k = deathPoint.posZ + (int) (randomDistance * MathHelper.cos(angle));
							int j = LOTRMod.getTrueTopBlock(worldserver, i, k);
							entityplayermp.setLocationAndAngles(i + 0.5, j, k + 0.5, entityplayermp.rotationYaw, entityplayermp.rotationPitch);
							entityplayermp.playerNetServerHandler.setPlayerLocation(i + 0.5, j, k + 0.5, entityplayermp.rotationYaw, entityplayermp.rotationPitch);
						}
					}
				}
			} else if (deathDimension == LOTRDimension.UTUMNO.dimensionID) {
				LOTRTeleporterUtumno.newTeleporter(LOTRDimension.MIDDLE_EARTH.dimensionID).placeInPortal(entityplayermp, 0.0, 0.0, 0.0, 0.0f);
				entityplayermp.playerNetServerHandler.setPlayerLocation(entityplayermp.posX, entityplayermp.posY, entityplayermp.posZ, entityplayermp.rotationYaw, entityplayermp.rotationPitch);
			}
		}
	}

	@SubscribeEvent
	public void onSaplingGrow(SaplingGrowTreeEvent event) {
		World world = event.world;
		int i = event.x;
		int j = event.y;
		int k = event.z;
		Block block = world.getBlock(i, j, k);
		if (block == Blocks.sapling) {
			LOTRVanillaSaplings.growTree(world, i, j, k, event.rand);
			event.setResult(Event.Result.DENY);
		}
	}

	@SubscribeEvent
	public void onServerChat(ServerChatEvent event) {
		PotionEffect nausea;
		LOTRTitle.PlayerTitle playerTitle;
		EntityPlayerMP entityplayer = event.player;
		String message = event.message;
		String username = event.username;
		ChatComponentTranslation chatComponent = event.component;
		if (LOTRConfig.drunkMessages && (nausea = entityplayer.getActivePotionEffect(Potion.confusion)) != null) {
			int duration = nausea.getDuration();
			float chance = duration / 4800.0f;
			chance = Math.min(chance, 1.0f);
			chance *= 0.4f;
			String key = chatComponent.getKey();
			Object[] formatArgs = chatComponent.getFormatArgs();
			for (int a = 0; a < formatArgs.length; ++a) {
				Object arg = formatArgs[a];
				String chatText = null;
				if (arg instanceof ChatComponentText) {
					IChatComponent componentText = (IChatComponent) arg;
					chatText = componentText.getUnformattedText();
				} else if (arg instanceof String) {
					chatText = (String) arg;
				}
				if (chatText == null || !chatText.equals(message)) {
					continue;
				}
				String newText = LOTRDrunkenSpeech.getDrunkenSpeech(chatText, chance);
				if (arg instanceof String) {
					formatArgs[a] = newText;
					continue;
				}
				formatArgs[a] = new ChatComponentText(newText);
			}
			chatComponent = new ChatComponentTranslation(key, formatArgs);
		}
		if (LOTRConfig.enableTitles && (playerTitle = LOTRLevelData.getData(entityplayer).getPlayerTitle()) != null) {
			Collection<Object> newFormatArgs = new ArrayList<>();
			for (Object arg : chatComponent.getFormatArgs()) {
				if (arg instanceof ChatComponentText) {
					IChatComponent componentText = (IChatComponent) arg;
					if (componentText.getUnformattedText().contains(username)) {
						IChatComponent titleComponent = playerTitle.getFullTitleComponent(entityplayer);
						IChatComponent fullUsernameComponent = new ChatComponentText("").appendSibling(titleComponent).appendSibling(componentText);
						newFormatArgs.add(fullUsernameComponent);
						continue;
					}
					newFormatArgs.add(componentText);
					continue;
				}
				newFormatArgs.add(arg);
			}
			ChatComponentTranslation newChatComponent = new ChatComponentTranslation(chatComponent.getKey(), newFormatArgs.toArray());
			newChatComponent.setChatStyle(chatComponent.getChatStyle().createShallowCopy());
			for (Object sibling : chatComponent.getSiblings()) {
				newChatComponent.appendSibling((IChatComponent) sibling);
			}
			chatComponent = newChatComponent;
		}
		event.component = chatComponent;
	}

	@SubscribeEvent
	public void onSmelting(ItemSmeltedEvent event) {
		EntityPlayer entityplayer = event.player;
		ItemStack itemstack = event.smelting;
		if (!entityplayer.worldObj.isRemote) {
			if (itemstack.getItem() == LOTRMod.bronze) {
				LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.alloyBronze);
			}
			if (itemstack.getItem() == LOTRMod.deerCooked) {
				LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.cookDeer);
			}
			if (itemstack.getItem() == LOTRMod.blueDwarfSteel) {
				LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.smeltBlueDwarfSteel);
			}
			if (itemstack.getItem() == LOTRMod.elfSteel) {
				LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.smeltElfSteel);
			}
			if (itemstack.getItem() == LOTRMod.dwarfSteel) {
				LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.smeltDwarfSteel);
			}
			if (itemstack.getItem() == LOTRMod.urukSteel) {
				LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.smeltUrukSteel);
			}
			if (itemstack.getItem() == LOTRMod.orcSteel) {
				LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.smeltOrcSteel);
			}
			if (itemstack.getItem() == LOTRMod.blackUrukSteel) {
				LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.smeltBlackUrukSteel);
			}
		}
	}

	@SubscribeEvent
	public void onStartTrackingEntity(StartTracking event) {
		Entity entity = event.target;
		EntityPlayer entityplayer = event.entityPlayer;
		if (!entity.worldObj.isRemote && entity instanceof LOTREntityNPC) {
			EntityPlayerMP entityplayermp = (EntityPlayerMP) entityplayer;
			LOTREntityNPC npc = (LOTREntityNPC) entity;
			npc.onPlayerStartTracking(entityplayermp);
		}
		if (!entity.worldObj.isRemote && entity instanceof LOTRRandomSkinEntity) {
			IMessage packet = new LOTRPacketEntityUUID(entity.getEntityId(), entity.getUniqueID());
			LOTRPacketHandler.networkWrapper.sendTo(packet, (EntityPlayerMP) entityplayer);
		}
		if (!entity.worldObj.isRemote && entity instanceof LOTREntityBanner) {
			((LOTREntityBanner) entity).sendBannerToPlayer(entityplayer, false, false);
		}
	}

	@SubscribeEvent
	public void onUseBonemeal(BonemealEvent event) {
		EntityPlayer entityplayer = event.entityPlayer;
		World world = event.world;
		Random rand = world.rand;
		int i = event.x;
		int j = event.y;
		int k = event.z;
		if (!world.isRemote) {
			BiomeGenBase biomegenbase;
			if (event.block instanceof LOTRBlockSaplingBase) {
				LOTRBlockSaplingBase sapling = (LOTRBlockSaplingBase) event.block;
				int meta = world.getBlockMetadata(i, j, k);
				if (rand.nextFloat() < 0.45) {
					sapling.incrementGrowth(world, i, j, k, rand);
				}
				if (sapling == LOTRMod.sapling4 && (meta & 7) == 1 && world.getBlock(i, j, k) == LOTRMod.wood4 && world.getBlockMetadata(i, j, k) == 1) {
					LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.growBaobab);
				}
				event.setResult(Event.Result.ALLOW);
				return;
			}
			if (event.block.canSustainPlant(world, i, j, k, ForgeDirection.UP, Blocks.tallgrass) && event.block instanceof IGrowable && (biomegenbase = world.getBiomeGenForCoords(i, k)) instanceof LOTRBiome) {
				LOTRBiome biome = (LOTRBiome) biomegenbase;
				block0:
				for (int attempts = 0; attempts < 128; ++attempts) {
					int i1 = i;
					int j1 = j + 1;
					int k1 = k;
					for (int subAttempts = 0; subAttempts < attempts / 16; ++subAttempts) {
						Block below = world.getBlock(i1 += rand.nextInt(3) - 1, (j1 += (rand.nextInt(3) - 1) * rand.nextInt(3) / 2) - 1, k1 += rand.nextInt(3) - 1);
						if (!(below instanceof IGrowable) || !below.canSustainPlant(world, i1, j1 - 1, k1, ForgeDirection.UP, Blocks.tallgrass) || world.getBlock(i1, j1, k1).isNormalCube()) {
							continue block0;
						}
					}
					if (world.getBlock(i1, j1, k1).getMaterial() != Material.air) {
						continue;
					}
					if (rand.nextInt(8) > 0) {
						LOTRBiome.GrassBlockAndMeta obj = biome.getRandomGrass(rand);
						Block block = obj.block;
						int meta = obj.meta;
						if (!block.canBlockStay(world, i1, j1, k1)) {
							continue;
						}
						world.setBlock(i1, j1, k1, block, meta, 3);
						continue;
					}
					biome.plantFlower(world, rand, i1, j1, k1);
				}
				event.setResult(Event.Result.ALLOW);
			}
		}
	}

	@SubscribeEvent
	public void onUseHoe(UseHoeEvent event) {
		World world = event.world;
		int i = event.x;
		int j = event.y;
		int k = event.z;
		Block block = world.getBlock(i, j, k);
		LOTRBlockGrapevine.hoeing = true;
		if (world.getBlock(i, j + 1, k).isAir(world, i, j + 1, k) && (block == LOTRMod.mudGrass || block == LOTRMod.mud)) {
			Block tilled = LOTRMod.mudFarmland;
			world.playSoundEffect(i + 0.5f, j + 0.5f, k + 0.5f, tilled.stepSound.getStepResourcePath(), (tilled.stepSound.getVolume() + 1.0f) / 2.0f, tilled.stepSound.getPitch() * 0.8f);
			if (!world.isRemote) {
				world.setBlock(i, j, k, tilled);
			}
			event.setResult(Event.Result.ALLOW);
			return;
		}
		LOTRBlockGrapevine.hoeing = true;
	}

	@SubscribeEvent
	public void onWorldSave(WorldEvent.Save event) {
		World world = event.world;
		if (!world.isRemote && world.provider.dimensionId == 0) {
			LOTRTime.save();
		}
	}

	@SubscribeEvent
	public void onWorldUnload(WorldEvent.Unload event) {
		World world = event.world;
		if (world.provider instanceof LOTRWorldProvider) {
			LOTRBiomeVariantStorage.clearAllVariants(world);
		}
	}

}
