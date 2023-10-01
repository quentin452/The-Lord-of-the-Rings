package lotr.common.entity.ai;

import com.mojang.authlib.GameProfile;
import lotr.common.LOTRMod;
import lotr.common.LOTRReflection;
import lotr.common.block.LOTRBlockCorn;
import lotr.common.block.LOTRBlockGrapevine;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.entity.npc.LOTRFarmhand;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.BlockStem;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class LOTREntityAIFarm extends EntityAIBase {
	public static int DEPOSIT_THRESHOLD = 16;

	public static int COLLECT_THRESHOLD = 16;

	public static int MIN_CHEST_RANGE = 24;

	public LOTREntityNPC theEntity;

	public LOTRFarmhand theEntityFarmer;

	public World theWorld;

	public double moveSpeed;

	public float farmingEfficiency;

	public Action action;

	public ChunkCoordinates actionTarget;

	public ChunkCoordinates pathTarget;

	public int pathingTick;

	public int rePathDelay;

	public boolean harvestingSolidBlock;

	public FakePlayer fakePlayer;

	public LOTREntityAIFarm(LOTRFarmhand npc, double d, float f) {
		theEntity = (LOTREntityNPC) npc;
		theEntityFarmer = npc;
		theWorld = theEntity.worldObj;
		moveSpeed = d;
		setMutexBits(1);
		if (theWorld instanceof WorldServer) {
			fakePlayer = FakePlayerFactory.get((WorldServer) theWorld, new GameProfile(null, "LOTRFarming"));
		}
		farmingEfficiency = f;
	}

	public boolean canDoBonemealing() {
		if (theEntity.hiredNPCInfo.isActive) {
			ItemStack invBmeal = getInventoryBonemeal();
			return invBmeal != null;
		}
		return false;
	}

	public boolean canDoCollecting() {
		if (theEntity.hiredNPCInfo.isActive) {
			ItemStack seeds = getInventorySeeds();
			if (seeds != null && seeds.stackSize <= 16) {
				return true;
			}
			ItemStack bonemeal = getInventoryBonemeal();
			return bonemeal == null || bonemeal.stackSize <= 16;
		}
		return false;
	}

	public boolean canDoDepositing() {
		if (theEntity.hiredNPCInfo.isActive) {
			for (int l = 1; l <= 2; l++) {
				ItemStack itemstack = theEntity.hiredNPCInfo.getHiredInventory().getStackInSlot(l);
				if (itemstack != null && itemstack.stackSize >= 16) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean canDoHarvesting() {
		if (theEntity.hiredNPCInfo.isActive) {
			return getInventorySeeds() != null && hasSpaceForCrops() && getCropForSeed(getSeedsToPlant()) != null;
		}
		return false;
	}

	public boolean canDoHoeing() {
		return true;
	}

	public boolean canDoPlanting() {
		if (theEntity.hiredNPCInfo.isActive) {
			ItemStack invSeeds = getInventorySeeds();
			return invSeeds != null && invSeeds.stackSize > 1;
		}
		return true;
	}

	@Override
	public boolean continueExecuting() {
		if (theEntity.hiredNPCInfo.isActive && !theEntity.hiredNPCInfo.isGuardMode() || theEntity.getNavigator().noPath()) {
			return false;
		}
		if (pathingTick < 200) {
			if (action == Action.HOEING) {
				return canDoHoeing() && isSuitableForHoeing(actionTarget);
			}
			if (action == Action.PLANTING) {
				return canDoPlanting() && isSuitableForPlanting(actionTarget);
			}
			if (action == Action.HARVESTING) {
				return canDoHarvesting() && isSuitableForHarvesting(actionTarget);
			}
			if (action == Action.DEPOSITING) {
				return canDoDepositing() && isSuitableForDepositing(actionTarget);
			}
			if (action == Action.BONEMEALING) {
				return canDoBonemealing() && isSuitableForBonemealing(actionTarget);
			}
			if (action == Action.COLLECTING) {
				return canDoCollecting() && isSuitableForCollecting(actionTarget);
			}
		}
		return false;
	}

	public TargetPair findTarget(Action targetAction) {
		setAppropriateHomeRange(targetAction);
		Random rand = theEntity.getRNG();
		boolean isChestAction = targetAction == Action.DEPOSITING || targetAction == Action.COLLECTING;
		List<TileEntityChest> chests = new ArrayList<>();
		if (isChestAction) {
			chests = gatherNearbyChests();
		}
		for (int l = 0; l < 32; l++) {
			int i = 0;
			int j = 0;
			int k = 0;
			boolean suitable = false;
			if (isChestAction) {
				if (chests.isEmpty()) {
					suitable = false;
				} else {
					TileEntityChest chest = chests.get(rand.nextInt(chests.size()));
					i = chest.xCoord;
					j = chest.yCoord;
					k = chest.zCoord;
					if (targetAction == Action.DEPOSITING) {
						suitable = isSuitableForDepositing(i, j, k);
					} else {
						suitable = isSuitableForCollecting(i, j, k);
					}
				}
			} else {
				i = MathHelper.floor_double(theEntity.posX) + MathHelper.getRandomIntegerInRange(rand, -8, 8);
				j = MathHelper.floor_double(theEntity.boundingBox.minY) + MathHelper.getRandomIntegerInRange(rand, -4, 4);
				k = MathHelper.floor_double(theEntity.posZ) + MathHelper.getRandomIntegerInRange(rand, -8, 8);
				if (targetAction == Action.HOEING) {
					suitable = isSuitableForHoeing(i, j, k);
				} else if (targetAction == Action.PLANTING) {
					suitable = isSuitableForPlanting(i, j, k);
				} else if (targetAction == Action.HARVESTING) {
					suitable = isSuitableForHarvesting(i, j, k);
				} else if (targetAction == Action.BONEMEALING) {
					suitable = isSuitableForBonemealing(i, j, k);
				}
			}
			if (suitable && theEntity.isWithinHomeDistance(i, j, k)) {
				ChunkCoordinates target = new ChunkCoordinates(i, j, k);
				ChunkCoordinates path = getPathTarget(i, j, k, targetAction);
				PathEntity pathCheck = theEntity.getNavigator().getPathToXYZ(path.posX, path.posY, path.posZ);
				if (pathCheck != null) {
					return new TargetPair(target, path);
				}
			}
		}
		return null;
	}

	public List<TileEntityChest> gatherNearbyChests() {
		int x = MathHelper.floor_double(theEntity.posX);
		int z = MathHelper.floor_double(theEntity.posZ);
		int searchRange = (int) theEntity.func_110174_bM();
		int chunkX = x >> 4;
		int chunkZ = z >> 4;
		int chunkRange = (searchRange >> 4) + 1;
		List<TileEntityChest> nearbyChests = new ArrayList<>();
		for (int i = -chunkRange; i <= chunkRange; i++) {
			for (int k = -chunkRange; k <= chunkRange; k++) {
				int nearChunkX = chunkX + i;
				int nearChunkZ = chunkZ + k;
				if (theWorld.getChunkProvider().chunkExists(nearChunkX, nearChunkZ)) {
					Chunk chunk = theWorld.getChunkFromChunkCoords(nearChunkX, nearChunkZ);
					for (Object obj : chunk.chunkTileEntityMap.values()) {
						TileEntity te = (TileEntity) obj;
						if (te instanceof TileEntityChest && !te.isInvalid()) {
							TileEntityChest chest = (TileEntityChest) te;
							if (theEntity.isWithinHomeDistance(chest.xCoord, chest.yCoord, chest.zCoord)) {
								nearbyChests.add(chest);
							}
						}
					}
				}
			}
		}
		return nearbyChests;
	}

	public ChunkCoordinates getAdjacentSolidOpenWalkTarget(int i, int j, int k) {
		List<ChunkCoordinates> possibleCoords = new ArrayList<>();
		for (int i1 = -1; i1 <= 1; i1++) {
			for (int k1 = -1; k1 <= 1; k1++) {
				int i2 = i + i1;
				int k2 = k + k1;
				for (int j1 = 1; j1 >= -1; j1--) {
					int j2 = j + j1;
					if (isSolidOpenWalkTarget(i2, j2, k2)) {
						possibleCoords.add(new ChunkCoordinates(i2, j2, k2));
					}
				}
			}
		}
		if (!possibleCoords.isEmpty()) {
			return possibleCoords.get(0);
		}
		return new ChunkCoordinates(i, j, k);
	}

	public ItemStack getCropForSeed(IPlantable seed) {
		Block block = seed.getPlant(theWorld, -1, -1, -1);
		if (block instanceof BlockCrops) {
			return new ItemStack(LOTRReflection.getCropItem((BlockCrops) block));
		}
		if (block instanceof BlockStem) {
			return new ItemStack(LOTRReflection.getStemFruitBlock((BlockStem) block).getItemDropped(0, theWorld.rand, 0), 1, 0);
		}
		if (block instanceof LOTRBlockCorn) {
			return new ItemStack(LOTRMod.corn);
		}
		if (block instanceof LOTRBlockGrapevine) {
			return new ItemStack(((LOTRBlockGrapevine) block).getGrapeItem());
		}
		return null;
	}

	public ItemStack getInventoryBonemeal() {
		if (theEntity.hiredNPCInfo.getHiredInventory() == null) {
			return null;
		}
		ItemStack itemstack = theEntity.hiredNPCInfo.getHiredInventory().getStackInSlot(3);
		if (itemstack != null && itemstack.getItem() == Items.dye && itemstack.getItemDamage() == 15) {
			return itemstack;
		}
		return null;
	}

	public ItemStack getInventorySeeds() {
		if (theEntity.hiredNPCInfo.getHiredInventory() == null) {
			return null;
		}
		ItemStack itemstack = theEntity.hiredNPCInfo.getHiredInventory().getStackInSlot(0);
		if (itemstack != null) {
			Item item = itemstack.getItem();
			if (item instanceof IPlantable) {
				IPlantable iplantable = (IPlantable) item;
				if (iplantable.getPlantType(theWorld, -1, -1, -1) == EnumPlantType.Crop) {
					return itemstack;
				}
			}
		}
		return null;
	}

	public ChunkCoordinates getPathTarget(int i, int j, int k, Action targetAction) {
		if (targetAction == Action.HOEING) {
			if (isReplaceable(i, j + 1, k)) {
				return new ChunkCoordinates(i, j + 1, k);
			}
			return getAdjacentSolidOpenWalkTarget(i, j + 1, k);
		}
		if (targetAction == Action.PLANTING || targetAction == Action.HARVESTING || targetAction == Action.BONEMEALING) {
			if (harvestingSolidBlock) {
				return new ChunkCoordinates(i, j + 1, k);
			}
			if (isFarmingGrapes()) {
				int groundY = j;
				for (int j1 = 1; j1 <= 2; j1++) {
					if (!(theWorld.getBlock(i, j - j1 - 1, k) instanceof LOTRBlockGrapevine)) {
						groundY = j - j1 - 1;
						break;
					}
				}
				return getAdjacentSolidOpenWalkTarget(i, groundY + 1, k);
			}
			return new ChunkCoordinates(i, j, k);
		}
		if (targetAction == Action.DEPOSITING || targetAction == Action.COLLECTING) {
			return getAdjacentSolidOpenWalkTarget(i, j, k);
		}
		return new ChunkCoordinates(i, j, k);
	}

	public IPlantable getSeedsToPlant() {
		if (theEntity.hiredNPCInfo.isActive) {
			ItemStack invSeeds = getInventorySeeds();
			if (invSeeds != null) {
				return (IPlantable) invSeeds.getItem();
			}
		}
		return theEntityFarmer.getUnhiredSeeds();
	}

	public TileEntityChest getSuitableChest(int i, int j, int k) {
		Block block = theWorld.getBlock(i, j, k);
		int meta = theWorld.getBlockMetadata(i, j, k);
		TileEntityChest suitableChest = null;
		if (block.hasTileEntity(meta)) {
			TileEntity te = theWorld.getTileEntity(i, j, k);
			if (te instanceof TileEntityChest) {
				TileEntityChest chest = (TileEntityChest) te;
				boolean flag = false;
				if (isFarmhandMarked(chest) || chest.adjacentChestXNeg != null && isFarmhandMarked(chest.adjacentChestXNeg)) {
					flag = true;
				} else if (chest.adjacentChestXPos != null && isFarmhandMarked(chest.adjacentChestXPos) || chest.adjacentChestZNeg != null && isFarmhandMarked(chest.adjacentChestZNeg)) {
					flag = true;
				} else if (chest.adjacentChestZPos != null && isFarmhandMarked(chest.adjacentChestZPos)) {
					flag = true;
				}
				if (flag) {
					suitableChest = chest;
				}
			}
		}
		return suitableChest;
	}

	public boolean hasSpaceForCrops() {
		if (theEntity.hiredNPCInfo.getHiredInventory() == null) {
			return false;
		}
		for (int l = 1; l <= 2; l++) {
			ItemStack itemstack = theEntity.hiredNPCInfo.getHiredInventory().getStackInSlot(l);
			if (itemstack == null || itemstack.stackSize < itemstack.getMaxStackSize() && itemstack.isItemEqual(getCropForSeed(getSeedsToPlant()))) {
				return true;
			}
		}
		return false;
	}

	public boolean isFarmhandMarked(TileEntityChest chest) {
		int i = chest.xCoord;
		int j = chest.yCoord;
		int k = chest.zCoord;
		AxisAlignedBB chestBB = AxisAlignedBB.getBoundingBox(i, j, k, i + 1, j + 1, k + 1);
		List entities = theWorld.getEntitiesWithinAABB(EntityItemFrame.class, chestBB.expand(2.0D, 2.0D, 2.0D));
		for (Object obj : entities) {
			EntityItemFrame frame = (EntityItemFrame) obj;
			if (frame.field_146063_b == i && frame.field_146064_c == j && frame.field_146062_d == k) {
				ItemStack frameItem = frame.getDisplayedItem();
				if (frameItem != null && frameItem.getItem() instanceof net.minecraft.item.ItemHoe) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean isFarmingGrapes() {
		IPlantable seed = getSeedsToPlant();
		return seed.getPlant(theWorld, -1, -1, -1) instanceof LOTRBlockGrapevine;
	}

	public boolean isReplaceable(int i, int j, int k) {
		Block block = theWorld.getBlock(i, j, k);
		return !block.getMaterial().isLiquid() && block.isReplaceable(theWorld, i, j, k);
	}

	public boolean isSolidOpenWalkTarget(int i, int j, int k) {
		Block below = theWorld.getBlock(i, j - 1, k);
		if (below.isOpaqueCube() || below.canSustainPlant(theWorld, i, j - 1, k, ForgeDirection.UP, (IPlantable) Blocks.wheat)) {
			List bounds = new ArrayList();
			AxisAlignedBB aabb = AxisAlignedBB.getBoundingBox(i, j, k, i + 1, j + 2, k + 1);
			for (int j1 = j; j1 <= j + 1; j1++) {
				theWorld.getBlock(i, j1, k).addCollisionBoxesToList(theWorld, i, j1, k, aabb, bounds, theEntity);
			}
			if (bounds.isEmpty()) {
				return theEntity.getNavigator().getPathToXYZ(i, j, k) != null;
			}
		}
		return false;
	}

	public boolean isSuitableForBonemealing(ChunkCoordinates pos) {
		return isSuitableForBonemealing(pos.posX, pos.posY, pos.posZ);
	}

	public boolean isSuitableForBonemealing(int i, int j, int k) {
		harvestingSolidBlock = false;
		IPlantable seed = getSeedsToPlant();
		Block plantBlock = seed.getPlant(theWorld, i, j, k);
		if (plantBlock instanceof IGrowable && theWorld.getBlock(i, j, k) == plantBlock) {
			IGrowable growableBlock = (IGrowable) plantBlock;
			if (growableBlock.func_149851_a(theWorld, i, j, k, theWorld.isRemote)) {
				harvestingSolidBlock = plantBlock.isOpaqueCube();
				return true;
			}
		}
		return false;
	}

	public boolean isSuitableForCollecting(ChunkCoordinates pos) {
		return isSuitableForCollecting(pos.posX, pos.posY, pos.posZ);
	}

	public boolean isSuitableForCollecting(int i, int j, int k) {
		harvestingSolidBlock = false;
		TileEntityChest chest = getSuitableChest(i, j, k);
		if (chest != null) {
			int[] invSlots = {0, 3};
			for (int l : invSlots) {
				ItemStack collectMatch = theEntity.hiredNPCInfo.getHiredInventory().getStackInSlot(l);
				if (collectMatch == null && l == 3) {
					collectMatch = new ItemStack(Items.dye, 0, 15);
				}
				if (collectMatch != null && collectMatch.stackSize <= 16) {
					for (int slot = 0; slot < chest.getSizeInventory(); slot++) {
						ItemStack chestItem = chest.getStackInSlot(slot);
						if (chestItem != null && chestItem.isItemEqual(collectMatch) && ItemStack.areItemStackTagsEqual(chestItem, collectMatch) && chestItem.stackSize > 0) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	public boolean isSuitableForDepositing(ChunkCoordinates pos) {
		return isSuitableForDepositing(pos.posX, pos.posY, pos.posZ);
	}

	public boolean isSuitableForDepositing(int i, int j, int k) {
		harvestingSolidBlock = false;
		TileEntityChest chest = getSuitableChest(i, j, k);
		if (chest != null) {
			for (int l = 1; l <= 2; l++) {
				ItemStack depositItem = theEntity.hiredNPCInfo.getHiredInventory().getStackInSlot(l);
				if (depositItem != null) {
					for (int slot = 0; slot < chest.getSizeInventory(); slot++) {
						ItemStack chestItem = chest.getStackInSlot(slot);
						if (chestItem == null || chestItem.isItemEqual(depositItem) && ItemStack.areItemStackTagsEqual(chestItem, depositItem) && chestItem.stackSize < chestItem.getMaxStackSize()) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	public boolean isSuitableForHarvesting(ChunkCoordinates pos) {
		return isSuitableForHarvesting(pos.posX, pos.posY, pos.posZ);
	}

	public boolean isSuitableForHarvesting(int i, int j, int k) {
		harvestingSolidBlock = false;
		IPlantable seed = getSeedsToPlant();
		Block plantBlock = seed.getPlant(theWorld, i, j, k);
		if (plantBlock instanceof BlockCrops) {
			harvestingSolidBlock = false;
			return theWorld.getBlock(i, j, k) == plantBlock && theWorld.getBlockMetadata(i, j, k) >= 7;
		}
		if (plantBlock instanceof BlockStem) {
			harvestingSolidBlock = true;
			return theWorld.getBlock(i, j, k) == LOTRReflection.getStemFruitBlock((BlockStem) plantBlock);
		}
		if (plantBlock instanceof LOTRBlockCorn) {
			harvestingSolidBlock = false;
			if (theWorld.getBlock(i, j, k) == plantBlock) {
				for (int j1 = 0; j1 <= LOTRBlockCorn.MAX_GROW_HEIGHT - 1; j1++) {
					int j2 = j + j1;
					if (theWorld.getBlock(i, j2, k) == plantBlock && LOTRBlockCorn.hasCorn(theWorld, i, j2, k)) {
						return true;
					}
				}
			}
		} else if (plantBlock instanceof LOTRBlockGrapevine) {
			harvestingSolidBlock = false;
			return theWorld.getBlock(i, j, k) == seed.getPlant(theWorld, i, j, k) && theWorld.getBlockMetadata(i, j, k) >= 7;
		}
		return false;
	}

	public boolean isSuitableForHoeing(ChunkCoordinates pos) {
		return isSuitableForHoeing(pos.posX, pos.posY, pos.posZ);
	}

	public boolean isSuitableForHoeing(int i, int j, int k) {
		harvestingSolidBlock = false;
		Block block = theWorld.getBlock(i, j, k);
		boolean isGrassDirt = block.canSustainPlant(theWorld, i, j, k, ForgeDirection.UP, Blocks.tallgrass);
		boolean isFarmland = block.canSustainPlant(theWorld, i, j, k, ForgeDirection.UP, (IPlantable) Blocks.wheat);
		if (isGrassDirt && !isFarmland && (isReplaceable(i, j + 1, k) || theWorld.getBlock(i, j + 1, k) == LOTRMod.grapevine)) {
			Block below = theWorld.getBlock(i, j - 1, k);
			if (below == Blocks.sand) {
				return false;
			}
			boolean waterNearby = false;
			int range = 4;
			int i1;
			label24:
			for (i1 = i - range; i1 <= i + range; i1++) {
				for (int k1 = k - range; k1 <= k + range; k1++) {
					if (theWorld.getBlock(i1, j, k1).getMaterial() == Material.water) {
						waterNearby = true;
						break label24;
					}
				}
			}
			return waterNearby;
		}
		return false;
	}

	public boolean isSuitableForPlanting(ChunkCoordinates pos) {
		return isSuitableForPlanting(pos.posX, pos.posY, pos.posZ);
	}

	public boolean isSuitableForPlanting(int i, int j, int k) {
		harvestingSolidBlock = false;
		if (isFarmingGrapes()) {
			return theWorld.getBlock(i, j, k) == LOTRMod.grapevine && LOTRBlockGrapevine.canPlantGrapesAt(theWorld, i, j, k, getSeedsToPlant());
		}
		return theWorld.getBlock(i, j - 1, k).isFertile(theWorld, i, j - 1, k) && isReplaceable(i, j, k);
	}

	@Override
	public void resetTask() {
		action = null;
		setAppropriateHomeRange(null);
		actionTarget = null;
		pathTarget = null;
		pathingTick = 0;
		rePathDelay = 0;
		harvestingSolidBlock = false;
	}

	public void setAppropriateHomeRange(Action targetAction) {
		if (theEntity.hiredNPCInfo.isActive) {
			int hRange = theEntity.hiredNPCInfo.getGuardRange();
			ChunkCoordinates home = theEntity.getHomePosition();
			if ((targetAction == Action.DEPOSITING || targetAction == Action.COLLECTING) && hRange < 24) {
				hRange = 24;
			}
			theEntity.setHomeArea(home.posX, home.posY, home.posZ, hRange);
		}
	}

	@Override
	public boolean shouldExecute() {
		return shouldFarmhandExecute();
	}

	public boolean shouldFarmhandExecute() {
		if (theEntity.hiredNPCInfo.isActive && !theEntity.hiredNPCInfo.isGuardMode()) {
			return false;
		}
		setAppropriateHomeRange(null);
		if (theEntity.hasHome() && !theEntity.isWithinHomeDistanceCurrentPosition()) {
			return false;
		}
		if (theEntity.getRNG().nextFloat() < farmingEfficiency * 0.1F) {
			if (canDoDepositing()) {
				TargetPair depositTarget = findTarget(Action.DEPOSITING);
				if (depositTarget != null) {
					actionTarget = depositTarget.actionTarget;
					pathTarget = depositTarget.pathTarget;
					action = Action.DEPOSITING;
					return true;
				}
			}
			if (canDoHoeing()) {
				TargetPair hoeTarget = findTarget(Action.HOEING);
				if (hoeTarget != null) {
					actionTarget = hoeTarget.actionTarget;
					pathTarget = hoeTarget.pathTarget;
					action = Action.HOEING;
					return true;
				}
			}
			if (canDoPlanting()) {
				TargetPair plantTarget = findTarget(Action.PLANTING);
				if (plantTarget != null) {
					actionTarget = plantTarget.actionTarget;
					pathTarget = plantTarget.pathTarget;
					action = Action.PLANTING;
					return true;
				}
			}
			if (canDoHarvesting()) {
				TargetPair harvestTarget = findTarget(Action.HARVESTING);
				if (harvestTarget != null) {
					actionTarget = harvestTarget.actionTarget;
					pathTarget = harvestTarget.pathTarget;
					action = Action.HARVESTING;
					return true;
				}
			}
			if (canDoBonemealing()) {
				TargetPair bonemealTarget = findTarget(Action.BONEMEALING);
				if (bonemealTarget != null) {
					actionTarget = bonemealTarget.actionTarget;
					pathTarget = bonemealTarget.pathTarget;
					action = Action.BONEMEALING;
					return true;
				}
			}
			if (canDoCollecting()) {
				TargetPair collectTarget = findTarget(Action.COLLECTING);
				if (collectTarget != null) {
					actionTarget = collectTarget.actionTarget;
					pathTarget = collectTarget.pathTarget;
					action = Action.COLLECTING;
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void startExecuting() {
		super.startExecuting();
		setAppropriateHomeRange(action);
	}

	@Override
	public void updateTask() {
		boolean canDoAction;
		double distSq = theEntity.getDistanceSq(pathTarget.posX + 0.5D, pathTarget.posY, pathTarget.posZ + 0.5D);
		if (action == Action.HOEING || action == Action.PLANTING) {
			int i = MathHelper.floor_double(theEntity.posX);
			int j = MathHelper.floor_double(theEntity.boundingBox.minY);
			int k = MathHelper.floor_double(theEntity.posZ);
			canDoAction = i == pathTarget.posX && j == pathTarget.posY && k == pathTarget.posZ;
		} else {
			canDoAction = distSq < 9.0D;
		}
		if (!canDoAction) {
			theEntity.getLookHelper().setLookPosition(actionTarget.posX + 0.5D, actionTarget.posY + 0.5D, actionTarget.posZ + 0.5D, 10.0F, theEntity.getVerticalFaceSpeed());
			rePathDelay--;
			if (rePathDelay <= 0) {
				rePathDelay = 10;
				theEntity.getNavigator().tryMoveToXYZ(pathTarget.posX + 0.5D, pathTarget.posY, pathTarget.posZ + 0.5D, moveSpeed);
			}
			pathingTick++;
		} else if (action == Action.HOEING) {
			boolean canHoe = isSuitableForHoeing(actionTarget);
			if (canHoe) {
				theEntity.swingItem();
				ItemStack proxyHoe = new ItemStack(Items.iron_hoe);
				int hoeRange = 1;
				for (int i1 = -hoeRange; i1 <= hoeRange; i1++) {
					for (int k1 = -hoeRange; k1 <= hoeRange; k1++) {
						if (Math.abs(i1) + Math.abs(k1) <= hoeRange) {
							int x = actionTarget.posX + i1;
							int z = actionTarget.posZ + k1;
							int y = actionTarget.posY;
							boolean alreadyChecked = i1 == 0 && k1 == 0;
							if (alreadyChecked || isSuitableForHoeing(x, y, z)) {
								if (isReplaceable(x, y + 1, z)) {
									theWorld.setBlockToAir(x, y + 1, z);
								}
								proxyHoe.tryPlaceItemIntoWorld(fakePlayer, theWorld, x, y, z, 1, 0.5F, 0.5F, 0.5F);
							}
						}
					}
				}
			}
		} else if (action == Action.PLANTING) {
			boolean canPlant = isSuitableForPlanting(actionTarget.posX, actionTarget.posY, actionTarget.posZ);
			if (canPlant) {
				theEntity.swingItem();
				IPlantable seed = getSeedsToPlant();
				Block plant = seed.getPlant(theWorld, actionTarget.posX, actionTarget.posY, actionTarget.posZ);
				int meta = seed.getPlantMetadata(theWorld, actionTarget.posX, actionTarget.posY, actionTarget.posZ);
				theWorld.setBlock(actionTarget.posX, actionTarget.posY, actionTarget.posZ, plant, meta, 3);
				if (theEntity.hiredNPCInfo.isActive) {
					theEntity.hiredNPCInfo.getHiredInventory().decrStackSize(0, 1);
				}
			}
		} else if (action == Action.HARVESTING) {
			boolean canHarvest = isSuitableForHarvesting(actionTarget.posX, actionTarget.posY, actionTarget.posZ);
			if (canHarvest) {
				theEntity.swingItem();
				Block block = theWorld.getBlock(actionTarget.posX, actionTarget.posY, actionTarget.posZ);
				Collection drops = new ArrayList();
				if (block instanceof LOTRBlockCorn) {
					int x = actionTarget.posX;
					int z = actionTarget.posZ;
					for (int j1 = 0; j1 <= LOTRBlockCorn.MAX_GROW_HEIGHT - 1; j1++) {
						int y = actionTarget.posY + j1;
						if (theWorld.getBlock(x, y, z) == block && LOTRBlockCorn.hasCorn(theWorld, x, y, z)) {
							int meta = theWorld.getBlockMetadata(x, y, z);
							drops.addAll(((LOTRBlockCorn) block).getCornDrops(theWorld, x, y, z, meta));
							LOTRBlockCorn.setHasCorn(theWorld, x, y, z, false);
						}
					}
				} else if (block instanceof LOTRBlockGrapevine) {
					int meta = theWorld.getBlockMetadata(actionTarget.posX, actionTarget.posY, actionTarget.posZ);
					drops.addAll(block.getDrops(theWorld, actionTarget.posX, actionTarget.posY, actionTarget.posZ, meta, 0));
					block.removedByPlayer(theWorld, fakePlayer, actionTarget.posX, actionTarget.posY, actionTarget.posZ, true);
				} else {
					int meta = theWorld.getBlockMetadata(actionTarget.posX, actionTarget.posY, actionTarget.posZ);
					drops.addAll(block.getDrops(theWorld, actionTarget.posX, actionTarget.posY, actionTarget.posZ, meta, 0));
					theWorld.setBlockToAir(actionTarget.posX, actionTarget.posY, actionTarget.posZ);
				}
				Block.SoundType cropSound = block.stepSound;
				theWorld.playSoundEffect(actionTarget.posX + 0.5D, actionTarget.posY + 0.5D, actionTarget.posZ + 0.5D, cropSound.getBreakSound(), (cropSound.getVolume() + 1.0F) / 2.0F, cropSound.getPitch() * 0.8F);
				ItemStack seedItem = theEntity.hiredNPCInfo.getHiredInventory().getStackInSlot(0);
				ItemStack cropItem = getCropForSeed(getSeedsToPlant());
				boolean addedOneCropSeed = false;
				for (Object obj : drops) {
					ItemStack drop = (ItemStack) obj;
					if (drop.isItemEqual(cropItem)) {
						if (drop.isItemEqual(seedItem) && !addedOneCropSeed) {
							addedOneCropSeed = true;
							ItemStack itemstack = theEntity.hiredNPCInfo.getHiredInventory().getStackInSlot(0);
							if (itemstack.stackSize + drop.stackSize <= itemstack.getMaxStackSize()) {
								itemstack.stackSize++;
								theEntity.hiredNPCInfo.getHiredInventory().setInventorySlotContents(0, itemstack);
								continue;
							}
						}
						for (int l = 1; l <= 2; l++) {
							ItemStack itemstack = theEntity.hiredNPCInfo.getHiredInventory().getStackInSlot(l);
							if (itemstack == null) {
								theEntity.hiredNPCInfo.getHiredInventory().setInventorySlotContents(l, drop);
								break;
							}
							if (itemstack.stackSize + drop.stackSize <= itemstack.getMaxStackSize() && itemstack.isItemEqual(cropItem)) {
								itemstack.stackSize++;
								theEntity.hiredNPCInfo.getHiredInventory().setInventorySlotContents(l, itemstack);
								break;
							}
						}
						continue;
					}
					if (drop.isItemEqual(seedItem)) {
						ItemStack itemstack = theEntity.hiredNPCInfo.getHiredInventory().getStackInSlot(0);
						if (itemstack.stackSize + drop.stackSize <= itemstack.getMaxStackSize()) {
							itemstack.stackSize++;
							theEntity.hiredNPCInfo.getHiredInventory().setInventorySlotContents(0, itemstack);
						}
					}
				}
			}
		} else if (action == Action.DEPOSITING) {
			boolean canDeposit = isSuitableForDepositing(actionTarget.posX, actionTarget.posY, actionTarget.posZ);
			if (canDeposit) {
				theEntity.swingItem();
				TileEntity te = theWorld.getTileEntity(actionTarget.posX, actionTarget.posY, actionTarget.posZ);
				if (te instanceof TileEntityChest) {
					IInventory chest = (IInventory) te;
					for (int l = 1; l <= 2; l++) {
						ItemStack itemstack = theEntity.hiredNPCInfo.getHiredInventory().getStackInSlot(l);
						if (itemstack != null) {
							for (int slot = 0; slot < chest.getSizeInventory(); slot++) {
								ItemStack chestItem = chest.getStackInSlot(slot);
								if (chestItem == null || chestItem.isItemEqual(itemstack) && ItemStack.areItemStackTagsEqual(chestItem, itemstack) && chestItem.stackSize < chestItem.getMaxStackSize()) {
									if (chestItem == null) {
										chestItem = itemstack.copy();
										chestItem.stackSize = 0;
									}
									while (itemstack.stackSize > 0 && chestItem.stackSize < chestItem.getMaxStackSize()) {
										chestItem.stackSize++;
										itemstack.stackSize--;
									}
									chest.setInventorySlotContents(slot, chestItem);
									if (itemstack.stackSize <= 0) {
										theEntity.hiredNPCInfo.getHiredInventory().setInventorySlotContents(l, null);
										break;
									}
								}
							}
						}
					}
					theWorld.playSoundEffect(actionTarget.posX + 0.5D, actionTarget.posY + 0.5D, actionTarget.posZ + 0.5D, "random.chestclosed", 0.5F, theWorld.rand.nextFloat() * 0.1F + 0.9F);
				}
			}
		} else if (action == Action.BONEMEALING) {
			boolean canBonemeal = isSuitableForBonemealing(actionTarget.posX, actionTarget.posY, actionTarget.posZ);
			if (canBonemeal) {
				theEntity.swingItem();
				ItemStack bonemeal = getInventoryBonemeal();
				if (ItemDye.applyBonemeal(getInventoryBonemeal(), theWorld, actionTarget.posX, actionTarget.posY, actionTarget.posZ, fakePlayer)) {
					theWorld.playAuxSFX(2005, actionTarget.posX, actionTarget.posY, actionTarget.posZ, 0);
				}
				if (bonemeal.stackSize <= 0) {
					bonemeal = null;
				}
				theEntity.hiredNPCInfo.getHiredInventory().setInventorySlotContents(3, bonemeal);
			}
		} else if (action == Action.COLLECTING) {
			boolean canCollect = isSuitableForCollecting(actionTarget.posX, actionTarget.posY, actionTarget.posZ);
			if (canCollect) {
				theEntity.swingItem();
				TileEntity te = theWorld.getTileEntity(actionTarget.posX, actionTarget.posY, actionTarget.posZ);
				if (te instanceof TileEntityChest) {
					IInventory chest = (IInventory) te;
					int[] invSlots = {0, 3};
					for (int l : invSlots) {
						ItemStack itemstack = theEntity.hiredNPCInfo.getHiredInventory().getStackInSlot(l);
						if (itemstack == null && l == 3) {
							itemstack = new ItemStack(Items.dye, 0, 15);
						}
						if (itemstack != null) {
							for (int slot = 0; slot < chest.getSizeInventory(); slot++) {
								ItemStack chestItem = chest.getStackInSlot(slot);
								if (chestItem != null && chestItem.isItemEqual(itemstack) && ItemStack.areItemStackTagsEqual(chestItem, itemstack) && chestItem.stackSize > 0) {
									while (itemstack.stackSize < itemstack.getMaxStackSize() && chestItem.stackSize > 0) {
										chestItem.stackSize--;
										itemstack.stackSize++;
									}
									if (itemstack.stackSize <= 0) {
										itemstack = null;
									}
									if (chestItem.stackSize <= 0) {
										chestItem = null;
									}
									theEntity.hiredNPCInfo.getHiredInventory().setInventorySlotContents(l, itemstack);
									chest.setInventorySlotContents(slot, chestItem);
									if (itemstack.stackSize >= itemstack.getMaxStackSize()) {
										break;
									}
								}
							}
						}
					}
					theWorld.playSoundEffect(actionTarget.posX + 0.5D, actionTarget.posY + 0.5D, actionTarget.posZ + 0.5D, "random.chestopen", 0.5F, theWorld.rand.nextFloat() * 0.1F + 0.9F);
				}
			}
		}
	}

	public enum Action {
		HOEING, PLANTING, HARVESTING, DEPOSITING, BONEMEALING, COLLECTING
	}

	public static class TargetPair {
		public ChunkCoordinates actionTarget;

		public ChunkCoordinates pathTarget;

		public TargetPair(ChunkCoordinates action, ChunkCoordinates path) {
			actionTarget = action;
			pathTarget = path;
		}
	}
}