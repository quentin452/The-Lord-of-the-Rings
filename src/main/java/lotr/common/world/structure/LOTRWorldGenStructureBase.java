package lotr.common.world.structure;

import lotr.common.LOTRFoods;
import lotr.common.LOTRMod;
import lotr.common.block.LOTRBlockFlowerPot;
import lotr.common.block.LOTRBlockMug;
import lotr.common.entity.LOTREntities;
import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.item.LOTREntityBanner;
import lotr.common.entity.item.LOTREntityBannerWall;
import lotr.common.item.LOTRItemBanner;
import lotr.common.item.LOTRItemMug;
import lotr.common.recipe.LOTRBrewingRecipes;
import lotr.common.tileentity.*;
import lotr.common.world.structure2.LOTRStructureTimelapse;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public abstract class LOTRWorldGenStructureBase extends WorldGenerator {
	public boolean restrictions = true;
	public EntityPlayer usingPlayer;
	public boolean notifyChanges;
	public LOTRStructureTimelapse.ThreadTimelapse threadTimelapse;

	protected LOTRWorldGenStructureBase(boolean flag) {
		super(flag);
		notifyChanges = flag;
	}

	public void placeArmorStand(World world, int i, int j, int k, int direction, ItemStack[] armor) {
		setBlockAndNotifyAdequately(world, i, j, k, LOTRMod.armorStand, direction);
		setBlockAndNotifyAdequately(world, i, j + 1, k, LOTRMod.armorStand, direction | 4);
		TileEntity tileentity = world.getTileEntity(i, j, k);
		if (tileentity instanceof LOTRTileEntityArmorStand) {
			IInventory armorStand = (IInventory) tileentity;
			for (int l = 0; l < armor.length; ++l) {
				ItemStack armorPart = armor[l];
				if (armorPart == null) {
					armorStand.setInventorySlotContents(l, null);
					continue;
				}
				armorStand.setInventorySlotContents(l, armor[l].copy());
			}
		}
	}

	public void placeBanner(World world, int i, int j, int k, int direction, LOTRItemBanner.BannerType type) {
		LOTREntityBanner banner = new LOTREntityBanner(world);
		banner.setLocationAndAngles(i + 0.5, j, k + 0.5, direction * 90.0f, 0.0f);
		banner.setBannerType(type);
		world.spawnEntityInWorld(banner);
	}

	public void placeBarrel(World world, Random random, int i, int j, int k, int meta, ItemStack drink) {
		setBlockAndNotifyAdequately(world, i, j, k, LOTRMod.barrel, meta);
		TileEntity tileentity = world.getTileEntity(i, j, k);
		if (tileentity instanceof LOTRTileEntityBarrel) {
			LOTRTileEntityBarrel barrel = (LOTRTileEntityBarrel) tileentity;
			barrel.barrelMode = 2;
			drink = drink.copy();
			LOTRItemMug.setStrengthMeta(drink, MathHelper.getRandomIntegerInRange(random, 1, 3));
			LOTRItemMug.setVessel(drink, LOTRItemMug.Vessel.MUG, true);
			drink.stackSize = MathHelper.getRandomIntegerInRange(random, LOTRBrewingRecipes.BARREL_CAPACITY / 2, LOTRBrewingRecipes.BARREL_CAPACITY);
			barrel.setInventorySlotContents(9, drink);
		}
	}

	public void placeBarrel(World world, Random random, int i, int j, int k, int meta, LOTRFoods foodList) {
		placeBarrel(world, random, i, j, k, meta, foodList.getRandomBrewableDrink(random));
	}

	public void placeFlowerPot(World world, int i, int j, int k, ItemStack itemstack) {
		setBlockAndNotifyAdequately(world, i, j, k, LOTRMod.flowerPot, 0);
		LOTRBlockFlowerPot.setPlant(world, i, j, k, itemstack);
	}

	public void placeMobSpawner(World world, int i, int j, int k, Class entityClass) {
		setBlockAndNotifyAdequately(world, i, j, k, LOTRMod.mobSpawner, 0);
		TileEntity tileentity = world.getTileEntity(i, j, k);
		if (tileentity instanceof LOTRTileEntityMobSpawner) {
			((LOTRTileEntityMobSpawner) tileentity).setEntityClassID(LOTREntities.getEntityIDFromClass(entityClass));
		}
	}

	public void placeMug(World world, Random random, int i, int j, int k, int meta, ItemStack drink, LOTRFoods foodList) {
		placeMug(world, random, i, j, k, meta, drink, foodList.getPlaceableDrinkVessels());
	}

	public void placeMug(World world, Random random, int i, int j, int k, int meta, ItemStack drink, LOTRItemMug.Vessel[] vesselTypes) {
		LOTRItemMug.Vessel vessel = vesselTypes[random.nextInt(vesselTypes.length)];
		setBlockAndNotifyAdequately(world, i, j, k, vessel.getBlock(), meta);
		if (random.nextInt(3) != 0) {
			drink = drink.copy();
			drink.stackSize = 1;
			if (drink.getItem() instanceof LOTRItemMug && ((LOTRItemMug) drink.getItem()).isBrewable) {
				LOTRItemMug.setStrengthMeta(drink, MathHelper.getRandomIntegerInRange(random, 1, 3));
			}
			LOTRItemMug.setVessel(drink, vessel, true);
			LOTRBlockMug.setMugItem(world, i, j, k, drink, vessel);
		}
	}

	public void placeMug(World world, Random random, int i, int j, int k, int meta, LOTRFoods foodList) {
		placeMug(world, random, i, j, k, meta, foodList.getRandomPlaceableDrink(random), foodList);
	}

	public void placeNPCRespawner(LOTREntityNPCRespawner entity, World world, int i, int j, int k) {
		entity.setLocationAndAngles(i + 0.5, j, k + 0.5, 0.0f, 0.0f);
		world.spawnEntityInWorld(entity);
	}

	public void placeOrcTorch(World world, int i, int j, int k) {
		setBlockAndNotifyAdequately(world, i, j, k, LOTRMod.orcTorch, 0);
		setBlockAndNotifyAdequately(world, i, j + 1, k, LOTRMod.orcTorch, 1);
	}

	public void placePlate(World world, Random random, int i, int j, int k, Block plateBlock, LOTRFoods foodList) {
		placePlate_do(world, random, i, j, k, plateBlock, foodList, false);
	}

	public void placePlate_do(World world, Random random, int i, int j, int k, Block plateBlock, LOTRFoods foodList, boolean certain) {
		TileEntity tileentity;
		if (!certain && random.nextBoolean()) {
			return;
		}
		setBlockAndNotifyAdequately(world, i, j, k, plateBlock, 0);
		if ((certain || random.nextBoolean()) && (tileentity = world.getTileEntity(i, j, k)) != null && tileentity instanceof LOTRTileEntityPlate) {
			LOTRTileEntityPlate plate = (LOTRTileEntityPlate) tileentity;
			ItemStack food = foodList.getRandomFoodForPlate(random);
			if (random.nextInt(4) == 0) {
				food.stackSize += 1 + random.nextInt(3);
			}
			plate.setFoodItem(food);
		}
	}

	public void placePlateWithCertainty(World world, Random random, int i, int j, int k, Block plateBlock, LOTRFoods foodList) {
		placePlate_do(world, random, i, j, k, plateBlock, foodList, true);
	}

	public void placeSkull(World world, Random random, int i, int j, int k) {
		setBlockAndNotifyAdequately(world, i, j, k, Blocks.skull, 1);
		TileEntity tileentity = world.getTileEntity(i, j, k);
		if (tileentity instanceof TileEntitySkull) {
			TileEntitySkull skull = (TileEntitySkull) tileentity;
			skull.func_145903_a(random.nextInt(16));
		}
	}

	public void placeSpawnerChest(World world, int i, int j, int k, Block block, int meta, Class entityClass) {
		setBlockAndNotifyAdequately(world, i, j, k, block, 0);
		setBlockMetadata(world, i, j, k, meta);
		TileEntity tileentity = world.getTileEntity(i, j, k);
		if (tileentity instanceof LOTRTileEntitySpawnerChest) {
			((LOTRTileEntitySpawnerChest) tileentity).setMobID(entityClass);
		}
	}

	public void placeWallBanner(World world, int i, int j, int k, int direction, LOTRItemBanner.BannerType type) {
		LOTREntityBannerWall banner = new LOTREntityBannerWall(world, i, j, k, direction);
		banner.setBannerType(type);
		world.spawnEntityInWorld(banner);
	}

	public void setAir(World world, int i, int j, int k) {
		setBlockAndNotifyAdequately(world, i, j, k, Blocks.air, 0);
	}

	@Override
	public void setBlockAndNotifyAdequately(World world, int i, int j, int k, Block block, int meta) {
		super.setBlockAndNotifyAdequately(world, i, j, k, block, meta);
		if (block != Blocks.air && threadTimelapse != null) {
			threadTimelapse.onBlockSet();
		}
	}

	public void setBlockMetadata(World world, int i, int j, int k, int meta) {
		world.setBlockMetadataWithNotify(i, j, k, meta, notifyChanges ? 3 : 2);
	}

	public void setGrassToDirt(World world, int i, int j, int k) {
		world.getBlock(i, j, k).onPlantGrow(world, i, j, k, i, j, k);
	}

	public void spawnItemFrame(World world, int i, int j, int k, int direction, ItemStack itemstack) {
		EntityItemFrame frame = new EntityItemFrame(world, i, j, k, direction);
		frame.setDisplayedItem(itemstack);
		world.spawnEntityInWorld(frame);
	}

	public int usingPlayerRotation() {
		return MathHelper.floor_double(usingPlayer.rotationYaw * 4.0f / 360.0f + 0.5) & 3;
	}
}
