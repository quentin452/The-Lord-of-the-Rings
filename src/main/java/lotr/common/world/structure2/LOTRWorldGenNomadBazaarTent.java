package lotr.common.world.structure2;

import lotr.common.LOTRFoods;
import lotr.common.LOTRMod;
import lotr.common.entity.animal.LOTREntityBird;
import lotr.common.entity.animal.LOTREntityButterfly;
import lotr.common.entity.npc.LOTREntityNomadArmourer;
import lotr.common.entity.npc.LOTREntityNomadBrewer;
import lotr.common.entity.npc.LOTREntityNomadMason;
import lotr.common.entity.npc.LOTREntityNomadMiner;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class LOTRWorldGenNomadBazaarTent extends LOTRWorldGenNomadStructure {
	public static Class[] stalls = {Mason.class, Brewer.class, Miner.class, Armourer.class};

	public LOTRWorldGenNomadBazaarTent(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
		int j1;
		setOriginAndRotation(world, i, j, k, rotation, 7);
		setupRandomBlocks(random);
		if (restrictions) {
			int minHeight = 0;
			int maxHeight = 0;
			for (int i1 = -14; i1 <= 14; ++i1) {
				for (int k1 = -6; k1 <= 8; ++k1) {
					j1 = getTopBlock(world, i1, k1) - 1;
					if (!isSurface(world, i1, j1, k1)) {
						return false;
					}
					if (j1 < minHeight) {
						minHeight = j1;
					}
					if (j1 > maxHeight) {
						maxHeight = j1;
					}
					if (maxHeight - minHeight <= 8) {
						continue;
					}
					return false;
				}
			}
		}
		for (int i1 = -14; i1 <= 14; ++i1) {
			for (int k1 = -6; k1 <= 8; ++k1) {
				if (!isSurface(world, i1, 0, k1)) {
					laySandBase(world, i1, 0, k1);
				}
				for (j1 = 1; j1 <= 8; ++j1) {
					setAir(world, i1, j1, k1);
				}
			}
		}
		loadStrScan("nomad_bazaar");
		associateBlockMetaAlias("PLANK", plankBlock, plankMeta);
		associateBlockMetaAlias("PLANK_SLAB", plankSlabBlock, plankSlabMeta);
		associateBlockMetaAlias("PLANK_SLAB_INV", plankSlabBlock, plankSlabMeta | 8);
		associateBlockAlias("PLANK_STAIR", plankStairBlock);
		associateBlockMetaAlias("FENCE", fenceBlock, fenceMeta);
		associateBlockMetaAlias("BEAM", beamBlock, beamMeta);
		associateBlockMetaAlias("TENT", tentBlock, tentMeta);
		associateBlockMetaAlias("TENT2", tent2Block, tent2Meta);
		associateBlockMetaAlias("CARPET", carpetBlock, carpetMeta);
		associateBlockMetaAlias("CARPET2", carpet2Block, carpet2Meta);
		generateStrScan(world, random, 0, 1, 0);
		placeSkull(world, random, -8, 2, -4);
		placeBarrel(world, random, 7, 2, -4, 3, LOTRFoods.NOMAD_DRINK);
		placeBarrel(world, random, 8, 2, -4, 3, LOTRFoods.NOMAD_DRINK);
		placeAnimalJar(world, -7, 2, -4, LOTRMod.butterflyJar, 0, new LOTREntityButterfly(world));
		placeAnimalJar(world, 9, 1, 5, LOTRMod.birdCageWood, 0, null);
		placeAnimalJar(world, 4, 3, 2, LOTRMod.birdCageWood, 0, new LOTREntityBird(world));
		placeAnimalJar(world, -4, 4, 5, LOTRMod.birdCage, 2, new LOTREntityBird(world));
		placeAnimalJar(world, -4, 5, -1, LOTRMod.birdCage, 0, new LOTREntityBird(world));
		placeAnimalJar(world, 0, 5, 5, LOTRMod.birdCageWood, 0, new LOTREntityBird(world));
		List<Class> stallClasses = new ArrayList<>(Arrays.asList(stalls));
		while (stallClasses.size() > 3) {
			stallClasses.remove(random.nextInt(stallClasses.size()));
		}
		try {
			LOTRWorldGenStructureBase2 stall0 = (LOTRWorldGenStructureBase2) stallClasses.get(0).getConstructor(Boolean.TYPE).newInstance(notifyChanges);
			LOTRWorldGenStructureBase2 stall1 = (LOTRWorldGenStructureBase2) stallClasses.get(1).getConstructor(Boolean.TYPE).newInstance(notifyChanges);
			LOTRWorldGenStructureBase2 stall2 = (LOTRWorldGenStructureBase2) stallClasses.get(2).getConstructor(Boolean.TYPE).newInstance(notifyChanges);
			generateSubstructure(stall0, world, random, -4, 1, 6, 0);
			generateSubstructure(stall1, world, random, 0, 1, 6, 0);
			generateSubstructure(stall2, world, random, 4, 1, 6, 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	public static class Armourer extends LOTRWorldGenStructureBase2 {
		public Armourer(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
			setOriginAndRotation(world, i, j, k, rotation, 0);
			setBlockAndMetadata(world, 1, 1, 1, Blocks.anvil, 1);
			placeArmorStand(world, 0, 1, 1, 0, new ItemStack[]{new ItemStack(LOTRMod.helmetMoredainLion), new ItemStack(LOTRMod.bodyHarnedor), new ItemStack(LOTRMod.legsNomad), new ItemStack(LOTRMod.bootsNomad)});
			placeWeaponRack(world, -1, 2, -2, 2, new LOTRWorldGenNomadBazaarTent(false).getRandomNomadWeapon(random));
			LOTREntityNomadArmourer trader = new LOTREntityNomadArmourer(world);
			spawnNPCAndSetHome(trader, world, 0, 1, 0, 4);
			return true;
		}
	}

	public static class Brewer extends LOTRWorldGenStructureBase2 {
		public Brewer(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
			setOriginAndRotation(world, i, j, k, rotation, 0);
			setBlockAndMetadata(world, -1, 1, 1, LOTRMod.stairsCedar, 6);
			setBlockAndMetadata(world, -1, 2, 1, LOTRMod.barrel, 2);
			setBlockAndMetadata(world, 0, 1, 1, Blocks.cauldron, 3);
			setBlockAndMetadata(world, 1, 1, 1, LOTRMod.stairsCedar, 6);
			setBlockAndMetadata(world, 1, 2, 1, LOTRMod.barrel, 2);
			placeMug(world, random, -1, 2, -2, 0, LOTRFoods.NOMAD_DRINK);
			placeMug(world, random, 1, 2, -2, 0, LOTRFoods.NOMAD_DRINK);
			LOTREntityNomadBrewer trader = new LOTREntityNomadBrewer(world);
			spawnNPCAndSetHome(trader, world, 0, 1, 0, 4);
			return true;
		}
	}

	public static class Mason extends LOTRWorldGenStructureBase2 {
		public Mason(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
			setOriginAndRotation(world, i, j, k, rotation, 0);
			setBlockAndMetadata(world, -1, 1, 1, LOTRMod.redSandstone, 0);
			setBlockAndMetadata(world, -1, 2, 1, LOTRMod.redSandstone, 0);
			setBlockAndMetadata(world, -1, 3, 1, LOTRMod.redSandstone, 0);
			setBlockAndMetadata(world, -1, 1, 0, Blocks.sandstone, 0);
			setBlockAndMetadata(world, -1, 2, 0, Blocks.sandstone, 0);
			setBlockAndMetadata(world, 0, 1, 1, LOTRMod.brick, 15);
			setBlockAndMetadata(world, 0, 2, 1, LOTRMod.slabSingle4, 0);
			setBlockAndMetadata(world, 1, 1, 1, LOTRMod.brick, 15);
			setBlockAndMetadata(world, 1, 2, 1, LOTRMod.slabSingle4, 0);
			placeWeaponRack(world, 1, 3, 1, 6, new ItemStack(LOTRMod.pickaxeBronze));
			LOTREntityNomadMason trader = new LOTREntityNomadMason(world);
			spawnNPCAndSetHome(trader, world, 0, 1, 0, 4);
			return true;
		}
	}

	public static class Miner extends LOTRWorldGenStructureBase2 {
		public Miner(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
			setOriginAndRotation(world, i, j, k, rotation, 0);
			setBlockAndMetadata(world, -1, 1, 1, LOTRMod.oreCopper, 0);
			setBlockAndMetadata(world, -1, 2, 1, LOTRMod.oreTin, 0);
			setBlockAndMetadata(world, 0, 1, 1, LOTRMod.oreCopper, 0);
			setBlockAndMetadata(world, 1, 1, 1, LOTRMod.oreTin, 0);
			setBlockAndMetadata(world, 1, 2, 1, Blocks.lapis_ore, 0);
			setBlockAndMetadata(world, 1, 1, 0, Blocks.lapis_ore, 0);
			placeWeaponRack(world, 0, 2, 1, 6, new ItemStack(LOTRMod.pickaxeBronze));
			LOTREntityNomadMiner trader = new LOTREntityNomadMiner(world);
			spawnNPCAndSetHome(trader, world, 0, 1, 0, 4);
			return true;
		}
	}

}
