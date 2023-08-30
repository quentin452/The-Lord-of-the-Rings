package lotr.common.world.structure2;

import java.util.Random;

import com.google.common.math.IntMath;

import lotr.common.LOTRMod;
import lotr.common.entity.npc.*;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public abstract class LOTRWorldGenEasterlingMarketStall extends LOTRWorldGenEasterlingStructure {
	public static Class[] allStallTypes = {Blacksmith.class, Lumber.class, Mason.class, Butcher.class, Brewer.class, Fish.class, Baker.class, Hunter.class, Farmer.class, Gold.class};

	protected LOTRWorldGenEasterlingMarketStall(boolean flag) {
		super(flag);
	}

	public abstract LOTREntityEasterling createTrader(World var1);

	public abstract void generateRoof(World var1, Random var2, int var3, int var4, int var5);

	@Override
	public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
		int j1;
		int i1;
		setOriginAndRotation(world, i, j, k, rotation, 3);
		setupRandomBlocks(random);
		if (restrictions) {
			int minHeight = 0;
			int maxHeight = 0;
			for (int i12 = -2; i12 <= 2; ++i12) {
				for (int k1 = -2; k1 <= 2; ++k1) {
					j1 = getTopBlock(world, i12, k1) - 1;
					if (!isSurface(world, i12, j1, k1)) {
						return false;
					}
					if (j1 < minHeight) {
						minHeight = j1;
					}
					if (j1 > maxHeight) {
						maxHeight = j1;
					}
					if (maxHeight - minHeight <= 5) {
						continue;
					}
					return false;
				}
			}
		}
		for (i1 = -2; i1 <= 2; ++i1) {
			for (int k1 = -2; k1 <= 2; ++k1) {
				int i2 = Math.abs(i1);
				int k2 = Math.abs(k1);
				for (j1 = 0; (j1 >= 0 || !isOpaque(world, i1, j1, k1)) && getY(j1) >= 0; --j1) {
					setBlockAndMetadata(world, i1, j1, k1, brickBlock, brickMeta);
					setGrassToDirt(world, i1, j1 - 1, k1);
				}
				for (j1 = 1; j1 <= 4; ++j1) {
					setAir(world, i1, j1, k1);
				}
				if (i2 == 2 && k2 == 2) {
					for (j1 = 1; j1 <= 3; ++j1) {
						setBlockAndMetadata(world, i1, j1, k1, woodBeamBlock, woodBeamMeta);
					}
				} else if (i2 == 2 || k2 == 2) {
					setBlockAndMetadata(world, i1, 3, k1, LOTRMod.reedBars, 0);
				}
				generateRoof(world, random, i1, 4, k1);
			}
		}
		for (i1 = -1; i1 <= 1; ++i1) {
			setBlockAndMetadata(world, i1, 1, -2, plankStairBlock, 6);
			setBlockAndMetadata(world, i1, 1, 2, plankStairBlock, 7);
		}
		for (int k1 = -1; k1 <= 1; ++k1) {
			setBlockAndMetadata(world, -2, 1, k1, plankStairBlock, 5);
			setBlockAndMetadata(world, 2, 1, k1, plankStairBlock, 4);
		}
		setBlockAndMetadata(world, -2, 1, 0, fenceGateBlock, 1);
		setBlockAndMetadata(world, -1, 1, 1, Blocks.crafting_table, 0);
		setBlockAndMetadata(world, 1, 1, 1, Blocks.chest, 2);
		LOTREntityEasterling trader = createTrader(world);
		spawnNPCAndSetHome(trader, world, 0, 1, 0, 4);
		return true;
	}

	public static LOTRWorldGenStructureBase2 getRandomStall(Random random, boolean flag) {
		try {
			Class cls = allStallTypes[random.nextInt(allStallTypes.length)];
			return (LOTRWorldGenStructureBase2) cls.getConstructor(Boolean.TYPE).newInstance(flag);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static class Baker extends LOTRWorldGenEasterlingMarketStall {
		public Baker(boolean flag) {
			super(flag);
		}

		@Override
		public LOTREntityEasterling createTrader(World world) {
			return new LOTREntityEasterlingBaker(world);
		}

		@Override
		public void generateRoof(World world, Random random, int i1, int j1, int k1) {
			int k2 = Math.abs(k1);
			if (k2 % 2 == 0) {
				setBlockAndMetadata(world, i1, j1, k1, Blocks.wool, 1);
			} else {
				setBlockAndMetadata(world, i1, j1, k1, Blocks.wool, 12);
			}
		}
	}

	public static class Blacksmith extends LOTRWorldGenEasterlingMarketStall {
		public Blacksmith(boolean flag) {
			super(flag);
		}

		@Override
		public LOTREntityEasterling createTrader(World world) {
			return new LOTREntityEasterlingBlacksmith(world);
		}

		@Override
		public void generateRoof(World world, Random random, int i1, int j1, int k1) {
			int i2 = Math.abs(i1);
			if (i2 == Math.abs(k1)) {
				setBlockAndMetadata(world, i1, j1, k1, Blocks.wool, 15);
			} else {
				setBlockAndMetadata(world, i1, j1, k1, Blocks.wool, 7);
			}
		}
	}

	public static class Brewer extends LOTRWorldGenEasterlingMarketStall {
		public Brewer(boolean flag) {
			super(flag);
		}

		@Override
		public LOTREntityEasterling createTrader(World world) {
			return new LOTREntityEasterlingBrewer(world);
		}

		@Override
		public void generateRoof(World world, Random random, int i1, int j1, int k1) {
			int i2 = Math.abs(i1);
			if (i2 % 2 == 0) {
				setBlockAndMetadata(world, i1, j1, k1, Blocks.wool, 12);
			} else {
				setBlockAndMetadata(world, i1, j1, k1, Blocks.wool, 0);
			}
		}
	}

	public static class Butcher extends LOTRWorldGenEasterlingMarketStall {
		public Butcher(boolean flag) {
			super(flag);
		}

		@Override
		public LOTREntityEasterling createTrader(World world) {
			return new LOTREntityEasterlingButcher(world);
		}

		@Override
		public void generateRoof(World world, Random random, int i1, int j1, int k1) {
			int i2 = Math.abs(i1);
			int k2 = Math.abs(k1);
			if (i2 == 2 || k2 == 2) {
				setBlockAndMetadata(world, i1, j1, k1, Blocks.wool, 6);
			} else if (i2 == 1 || k2 == 1) {
				setBlockAndMetadata(world, i1, j1, k1, Blocks.wool, 14);
			} else {
				setBlockAndMetadata(world, i1, j1, k1, Blocks.wool, 0);
			}
		}
	}

	public static class Farmer extends LOTRWorldGenEasterlingMarketStall {
		public Farmer(boolean flag) {
			super(flag);
		}

		@Override
		public LOTREntityEasterling createTrader(World world) {
			return new LOTREntityEasterlingFarmer(world);
		}

		@Override
		public void generateRoof(World world, Random random, int i1, int j1, int k1) {
			int k2;
			int i2 = Math.abs(i1);
			if (IntMath.mod(i2 + (k2 = Math.abs(k1)), 2) == 0) {
				if (Integer.signum(i1) != -Integer.signum(k1) && i2 + k2 == 2) {
					setBlockAndMetadata(world, i1, j1, k1, Blocks.wool, 4);
				} else {
					setBlockAndMetadata(world, i1, j1, k1, Blocks.wool, 13);
				}
			} else {
				setBlockAndMetadata(world, i1, j1, k1, Blocks.wool, 12);
			}
		}
	}

	public static class Fish extends LOTRWorldGenEasterlingMarketStall {
		public Fish(boolean flag) {
			super(flag);
		}

		@Override
		public LOTREntityEasterling createTrader(World world) {
			return new LOTREntityEasterlingFishmonger(world);
		}

		@Override
		public void generateRoof(World world, Random random, int i1, int j1, int k1) {
			int i2 = Math.abs(i1);
			int k2 = Math.abs(k1);
			if (i2 % 2 == 0) {
				if (k2 == 2) {
					setBlockAndMetadata(world, i1, j1, k1, Blocks.wool, 0);
				} else {
					setBlockAndMetadata(world, i1, j1, k1, Blocks.wool, 3);
				}
			} else {
				setBlockAndMetadata(world, i1, j1, k1, Blocks.wool, 11);
			}
		}
	}

	public static class Gold extends LOTRWorldGenEasterlingMarketStall {
		public Gold(boolean flag) {
			super(flag);
		}

		@Override
		public LOTREntityEasterling createTrader(World world) {
			return new LOTREntityEasterlingGoldsmith(world);
		}

		@Override
		public void generateRoof(World world, Random random, int i1, int j1, int k1) {
			setBlockAndMetadata(world, i1, j1, k1, Blocks.wool, 4);
		}
	}

	public static class Hunter extends LOTRWorldGenEasterlingMarketStall {
		public Hunter(boolean flag) {
			super(flag);
		}

		@Override
		public LOTREntityEasterling createTrader(World world) {
			return new LOTREntityEasterlingHunter(world);
		}

		@Override
		public void generateRoof(World world, Random random, int i1, int j1, int k1) {
			if (IntMath.mod(i1, 2) == 0 && IntMath.mod(k1, 2) == 0) {
				setBlockAndMetadata(world, i1, j1, k1, Blocks.wool, 15);
			} else {
				setBlockAndMetadata(world, i1, j1, k1, Blocks.wool, 12);
			}
		}
	}

	public static class Lumber extends LOTRWorldGenEasterlingMarketStall {
		public Lumber(boolean flag) {
			super(flag);
		}

		@Override
		public LOTREntityEasterling createTrader(World world) {
			return new LOTREntityEasterlingLumberman(world);
		}

		@Override
		public void generateRoof(World world, Random random, int i1, int j1, int k1) {
			int i2 = Math.abs(i1);
			int k2 = Math.abs(k1);
			if ((i2 == 2 || k2 == 2) && IntMath.mod(i2 + k2, 2) == 0) {
				setBlockAndMetadata(world, i1, j1, k1, Blocks.wool, 13);
			} else {
				setBlockAndMetadata(world, i1, j1, k1, Blocks.wool, 12);
			}
		}
	}

	public static class Mason extends LOTRWorldGenEasterlingMarketStall {
		public Mason(boolean flag) {
			super(flag);
		}

		@Override
		public LOTREntityEasterling createTrader(World world) {
			return new LOTREntityEasterlingMason(world);
		}

		@Override
		public void generateRoof(World world, Random random, int i1, int j1, int k1) {
			int i2 = Math.abs(i1);
			int k2 = Math.abs(k1);
			if (i2 == 2 || k2 == 2 || i2 != 1 && k2 != 1) {
				setBlockAndMetadata(world, i1, j1, k1, Blocks.wool, 7);
			} else {
				setBlockAndMetadata(world, i1, j1, k1, Blocks.wool, 14);
			}
		}
	}

}
