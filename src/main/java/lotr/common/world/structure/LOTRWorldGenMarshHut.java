package lotr.common.world.structure;

import java.util.Locale;
import java.util.Random;

import lotr.common.LOTRMod;
import lotr.common.entity.animal.LOTREntityHorse;
import lotr.common.entity.npc.LOTREntityTroll;
import lotr.common.world.feature.LOTRWorldGenFangornTrees;
import lotr.common.world.map.LOTRFixedStructures;
import net.minecraft.block.Block;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.*;
import net.minecraft.world.World;

public class LOTRWorldGenMarshHut extends LOTRWorldGenStructureBase {
	public LOTRWorldGenMarshHut() {
		super(false);
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k) {
		--j;
		int radius = 8;
		int radiusPlusOne = radius + 1;
		int wallThresholdMin = radius * radius;
		int wallThresholdMax = radiusPlusOne * radiusPlusOne;
		Block plankBlock = LOTRMod.planks2;
		int plankMeta = 9;
		Block doorBlock = LOTRMod.doorWillow;
		for (int i1 = i - radiusPlusOne; i1 <= i + radiusPlusOne; ++i1) {
			for (int k1 = k - radiusPlusOne; k1 <= k + radiusPlusOne; ++k1) {
				int j1;
				int i2 = i1 - i;
				int k2 = k1 - k;
				int distSq = i2 * i2 + k2 * k2;
				if (distSq >= wallThresholdMax) {
					continue;
				}
				for (j1 = j; (j1 == j || !LOTRMod.isOpaque(world, i1, j1, k1)) && j1 >= 0; --j1) {
					setBlockAndNotifyAdequately(world, i1, j1, k1, Blocks.dirt, 1);
					setGrassToDirt(world, i1, j1 - 1, k1);
				}
				for (j1 = j + 1; j1 <= j + 6; ++j1) {
					if (distSq >= wallThresholdMin) {
						setBlockAndNotifyAdequately(world, i1, j1, k1, plankBlock, plankMeta);
						continue;
					}
					setBlockAndNotifyAdequately(world, i1, j1, k1, Blocks.air, 0);
				}
			}
		}
		for (int i1 = i - radiusPlusOne - 2; i1 <= i + radiusPlusOne + 2; ++i1) {
			for (int k1 = k - radiusPlusOne - 2; k1 <= k + radiusPlusOne + 2; ++k1) {
				for (int j1 = j + 6; j1 <= j + 10; ++j1) {
					int i2 = i1 - i;
					int k2 = k1 - k;
					int j2 = j1 - (j + 4);
					int distSq = i2 * i2 + k2 * k2 + j2 * j2;
					if (distSq + j2 * j2 >= wallThresholdMax) {
						continue;
					}
					boolean grass = !LOTRMod.isOpaque(world, i1, j1 + 1, k1);
					setBlockAndNotifyAdequately(world, i1, j1, k1, grass ? Blocks.grass : Blocks.dirt, 0);
					setGrassToDirt(world, i1, j1 - 1, k1);
				}
			}
		}
		setBlockAndNotifyAdequately(world, i - (radius - 1), j + 3, k, Blocks.torch, 1);
		setBlockAndNotifyAdequately(world, i + radius - 1, j + 3, k, Blocks.torch, 2);
		setBlockAndNotifyAdequately(world, i, j + 3, k - (radius - 1), Blocks.torch, 3);
		setBlockAndNotifyAdequately(world, i, j + 3, k + radius - 1, Blocks.torch, 4);
		setBlockAndNotifyAdequately(world, i, j + 1, k - radius, doorBlock, 1);
		setBlockAndNotifyAdequately(world, i, j + 2, k - radius, doorBlock, 8);
		new LOTRWorldGenFangornTrees(false, Blocks.log, 0, Blocks.leaves, 0).disableRestrictions().generate(world, random, i, j + 11, k);
		LOTREntityTroll troll = new LOTREntityTroll(world);
		troll.setLocationAndAngles(i + 0.5, j + 1, k + 0.5, 0.0f, 0.0f);
		troll.isNPCPersistent = true;
		troll.onSpawnWithEgg(null);
		troll.trollImmuneToSun = true;
		troll.isPassive = true;
		troll.familyInfo.setName("" + 'S' + 'h' + 'r' + 'e' + 'k');
		troll.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(1.0E8);
		troll.setHealth(troll.getMaxHealth());
		world.spawnEntityInWorld(troll);
		LOTREntityTroll troll2 = new LOTREntityTroll(world);
		troll2.setLocationAndAngles(i + 0.5, j + 1, k + 0.5, 0.0f, 0.0f);
		troll2.isNPCPersistent = true;
		troll2.onSpawnWithEgg(null);
		troll2.trollImmuneToSun = true;
		troll2.isPassive = true;
		troll2.familyInfo.setName("" + 'D' + 'r' + 'e' + 'k');
		troll2.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(1.0E8);
		troll2.setHealth(troll2.getMaxHealth());
		world.spawnEntityInWorld(troll2);
		LOTREntityHorse horse = new LOTREntityHorse(world);
		horse.setLocationAndAngles(i + 0.5, j + 1, k + 0.5, 0.0f, 0.0f);
		horse.setHorseType(1);
		horse.setMountable(false);
		horse.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(1.0E8);
		horse.setHealth(horse.getMaxHealth());
		world.spawnEntityInWorld(horse);
		EntityOcelot cat = new EntityOcelot(world);
		cat.setLocationAndAngles(i + 0.5, j + 1, k + 0.5, 0.0f, 0.0f);
		cat.setTamed(true);
		cat.setTameSkin(2);
		cat.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(1.0E8);
		cat.setHealth(cat.getMaxHealth());
		world.spawnEntityInWorld(cat);
		setBlockAndNotifyAdequately(world, i, j + 2, k + radius - 1, Blocks.wall_sign, 2);
		TileEntity tileentity = world.getTileEntity(i, j + 2, k + radius - 1);
		if (tileentity instanceof TileEntitySign) {
			TileEntitySign sign = (TileEntitySign) tileentity;
			sign.signText[0] = "Check yourself";
			sign.signText[1] = "before you";
			sign.signText[2] = troll.familyInfo.getName() + " yourself";
		}
		setBlockAndNotifyAdequately(world, i, j + 1, k + radius - 1, Blocks.wall_sign, 2);
		tileentity = world.getTileEntity(i, j + 1, k + radius - 1);
		if (tileentity instanceof TileEntitySign) {
			TileEntitySign sign = (TileEntitySign) tileentity;
			sign.signText[0] = troll.familyInfo.getName().toUpperCase(Locale.ROOT);
			sign.signText[1] = "IS";
			sign.signText[2] = troll2.familyInfo.getName().toUpperCase(Locale.ROOT);
		}
		return true;
	}

	public static boolean generatesAt(World world, int i, int k) {
		return LOTRFixedStructures.generatesAtMapImageCoords(i, k, 1419, 1134);
	}
}
