package lotr.common.block;

import java.util.*;

import org.apache.commons.lang3.tuple.Pair;

import lotr.common.*;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.*;
import net.minecraftforge.common.util.ForgeDirection;

public class LOTRBlockRhunFire extends BlockFire {
	public LOTRBlockRhunFire() {
		setLightLevel(1.0f);
	}

	public boolean canCatchFireNotBannered(World world, int i, int j, int k, ForgeDirection face) {
		if (isBannered(world, i, j, k)) {
			return false;
		}
		return canCatchFire(world, i, j, k, face);
	}

	public boolean canNeighborBurn(World world, int i, int j, int k) {
		for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
			if (!canCatchFireNotBannered(world, i + dir.offsetX, j + dir.offsetY, k + dir.offsetZ, dir)) {
				continue;
			}
			return true;
		}
		return false;
	}

	public int getChanceOfNeighborsEncouragingFire(IBlockAccess world, int i, int j, int k) {
		if (!world.isAirBlock(i, j, k)) {
			return 0;
		}
		int chance = 0;
		for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
			chance = getChanceToEncourageFire(world, i + dir.offsetX, j + dir.offsetY, k + dir.offsetZ, chance, dir);
		}
		return chance;
	}

	@Override
	public int getChanceToEncourageFire(IBlockAccess world, int i, int j, int k, int oldChance, ForgeDirection face) {
		int chance = super.getChanceToEncourageFire(world, i, j, k, oldChance, face);
		return (int) (chance * 1.25f);
	}

	public boolean isBannered(World world, int i, int j, int k) {
		return LOTRBannerProtection.isProtected(world, i, j, k, LOTRBannerProtection.anyBanner(), false);
	}

	@Override
	public boolean isBurning(IBlockAccess world, int i, int j, int k) {
		return true;
	}

	public void runBaseFireUpdate(World world, int i, int j, int k, Random random) {
		if (LOTRMod.doFireTick(world)) {
			boolean isFireplace = world.getBlock(i, j - 1, k).isFireSource(world, i, j - 1, k, ForgeDirection.UP);
			if (!canPlaceBlockAt(world, i, j, k)) {
				world.setBlockToAir(i, j, k);
			}
			if (!isFireplace && world.isRaining() && (world.canLightningStrikeAt(i, j, k) || world.canLightningStrikeAt(i - 1, j, k) || world.canLightningStrikeAt(i + 1, j, k) || world.canLightningStrikeAt(i, j, k - 1) || world.canLightningStrikeAt(i, j, k + 1))) {
				world.setBlockToAir(i, j, k);
			} else {
				int meta = world.getBlockMetadata(i, j, k);
				if (meta < 15) {
					world.setBlockMetadataWithNotify(i, j, k, meta + random.nextInt(3) / 2, 4);
				}
				world.scheduleBlockUpdate(i, j, k, this, tickRate(world) + random.nextInt(10));
				if (!isFireplace && !canNeighborBurn(world, i, j, k)) {
					if (!World.doesBlockHaveSolidTopSurface(world, i, j - 1, k) || meta > 3) {
						world.setBlockToAir(i, j, k);
					}
				} else if (!isFireplace && !canCatchFire(world, i, j - 1, k, ForgeDirection.UP) && meta == 15 && random.nextInt(4) == 0) {
					world.setBlockToAir(i, j, k);
				} else {
					int extraChance = 0;
					boolean humid = world.isBlockHighHumidity(i, j, k);
					if (humid) {
						extraChance = -50;
					}
					int hChance = 300 + extraChance;
					int vChance = 250 + extraChance;
					for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
						tryCatchFire(world, i + dir.offsetX, j + dir.offsetY, k + dir.offsetZ, dir.offsetY == 0 ? hChance : vChance, random, meta, dir);
					}
					int xzRange = 1;
					int yMin = -1;
					int yMax = 4;
					for (int i1 = i - xzRange; i1 <= i + xzRange; ++i1) {
						for (int k1 = k - xzRange; k1 <= k + xzRange; ++k1) {
							for (int j1 = j + yMin; j1 <= j + yMax; ++j1) {
								int encourage;
								if (i1 == i && j1 == j && k1 == k || isBannered(world, i1, j1, k1)) {
									continue;
								}
								int totalChance = 100;
								if (j1 > j + 1) {
									totalChance += (j1 - (j + 1)) * 100;
								}
								encourage = getChanceOfNeighborsEncouragingFire(world, i1, j1, k1);
								if (encourage <= 0) {
									continue;
								}
								int chance = (encourage + 40 + world.difficultySetting.getDifficultyId() * 7) / (meta + 30);
								if (humid) {
									chance /= 2;
								}
								if (chance <= 0 || random.nextInt(totalChance) > chance || world.isRaining() && world.canLightningStrikeAt(i1, j1, k1) || world.canLightningStrikeAt(i1 - 1, j1, k) || world.canLightningStrikeAt(i1 + 1, j1, k1) || world.canLightningStrikeAt(i1, j1, k1 - 1) || world.canLightningStrikeAt(i1, j1, k1 + 1)) {
									continue;
								}
								int newMeta = meta + random.nextInt(5) / 4;
								if (newMeta > 15) {
									newMeta = 15;
								}
								world.setBlock(i1, j1, k1, this, newMeta, 3);
							}
						}
					}
				}
			}
		}
	}

	@Override
	public int tickRate(World world) {
		return 2;
	}

	public void tryCatchFire(World world, int i, int j, int k, int chance, Random random, int meta, ForgeDirection face) {
		if (isBannered(world, i, j, k)) {
			return;
		}
		int flamm = world.getBlock(i, j, k).getFlammability(world, i, j, k, face);
		if (random.nextInt(chance) < flamm) {
			boolean isTNT;
			isTNT = world.getBlock(i, j, k) == Blocks.tnt;
			if (random.nextInt(meta + 10) < 5 && !world.canLightningStrikeAt(i, j, k)) {
				int newMeta = meta + random.nextInt(5) / 4;
				if (newMeta > 15) {
					newMeta = 15;
				}
				world.setBlock(i, j, k, this, newMeta, 3);
			} else {
				world.setBlockToAir(i, j, k);
			}
			if (isTNT) {
				Blocks.tnt.onBlockDestroyedByPlayer(world, i, j, k, 1);
			}
		}
	}

	@Override
	public void updateTick(World world, int i, int j, int k, Random random) {
		if (LOTRMod.doFireTick(world)) {
			if (isBannered(world, i, j, k)) {
				world.setBlockToAir(i, j, k);
			} else {
				boolean canBurnStone;
				Map<Block, Pair> infos = new HashMap<>();
				canBurnStone = random.nextFloat() < 0.9f;
				if (canBurnStone) {
					for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
						Block block = world.getBlock(i + dir.offsetX, j + dir.offsetY, k + dir.offsetZ);
						Material material = block.getMaterial();
						if (material != Material.rock && material != Material.clay && !(block instanceof LOTRBlockGate) || block.getExplosionResistance(null) >= 100.0f) {
							continue;
						}
						int enco = getEncouragement(block);
						int flam = getFlammability(block);
						infos.put(block, Pair.of((Object) enco, (Object) flam));
						Blocks.fire.setFireInfo(block, 30, 30);
					}
				}
				if (random.nextInt(12) == 0) {
					world.setBlockToAir(i, j, k);
				} else {
					runBaseFireUpdate(world, i, j, k, random);
				}
				if (!infos.isEmpty()) {
					for (Map.Entry e : infos.entrySet()) {
						Blocks.fire.setFireInfo((Block) e.getKey(), (Integer) ((Pair<?, ?>) e.getValue()).getLeft(), (Integer) ((Pair<?, ?>) e.getValue()).getRight());
					}
				}
			}
		}
	}
}
