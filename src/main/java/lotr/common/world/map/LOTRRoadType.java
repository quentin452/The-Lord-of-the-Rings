package lotr.common.world.map;

import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.BiomeGenBase;

import java.util.Random;

public abstract class LOTRRoadType {
	public static LOTRRoadType PATH = new LOTRRoadType() {

		@Override
		public RoadBlock getBlock(Random rand, BiomeGenBase biome, boolean top, boolean slab) {
			if (slab) {
				float f = rand.nextFloat();
				if (f < 0.5f) {
					return new RoadBlock(LOTRMod.slabSingleDirt, 1);
				}
				if (f < 0.8f) {
					return new RoadBlock(LOTRMod.slabSingleDirt, 0);
				}
				return new RoadBlock(LOTRMod.slabSingleGravel, 0);
			}
			if (top) {
				float f = rand.nextFloat();
				if (f < 0.5f) {
					return new RoadBlock(LOTRMod.dirtPath, 0);
				}
				if (f < 0.8f) {
					return new RoadBlock(Blocks.dirt, 1);
				}
				return new RoadBlock(Blocks.gravel, 0);
			}
			return new RoadBlock(LOTRMod.dirtPath, 0);
		}
	};
	public static LOTRRoadType PAVED_PATH = new LOTRRoadType() {

		@Override
		public RoadBlock getBlock(Random rand, BiomeGenBase biome, boolean top, boolean slab) {
			if (slab) {
				float f = rand.nextFloat();
				if (f < 0.5f) {
					return new RoadBlock(LOTRMod.slabSingleDirt, 1);
				}
				if (f < 0.8f) {
					return new RoadBlock(Blocks.stone_slab, 3);
				}
				return new RoadBlock(LOTRMod.slabSingleGravel, 0);
			}
			if (top) {
				float f = rand.nextFloat();
				if (f < 0.5f) {
					return new RoadBlock(LOTRMod.dirtPath, 0);
				}
				if (f < 0.8f) {
					return new RoadBlock(Blocks.cobblestone, 0);
				}
				return new RoadBlock(Blocks.gravel, 0);
			}
			return new RoadBlock(LOTRMod.dirtPath, 0);
		}
	};
	public static LOTRRoadType COBBLESTONE = new LOTRRoadType() {

		@Override
		public RoadBlock getBlock(Random rand, BiomeGenBase biome, boolean top, boolean slab) {
			if (slab) {
				return new RoadBlock(Blocks.stone_slab, 3);
			}
			return new RoadBlock(Blocks.cobblestone, 0);
		}
	};
	public static LOTRRoadType DIRT = new LOTRRoadType() {

		@Override
		public RoadBlock getBlock(Random rand, BiomeGenBase biome, boolean top, boolean slab) {
			if (slab) {
				return new RoadBlock(LOTRMod.slabSingleDirt, 0);
			}
			return new RoadBlock(Blocks.dirt, 1);
		}
	};
	public static LOTRRoadType GALADHRIM = new LOTRRoadType() {

		@Override
		public RoadBlock getBlock(Random rand, BiomeGenBase biome, boolean top, boolean slab) {
			if (slab) {
				return new RoadBlock(LOTRMod.slabSingle2, 3);
			}
			return new RoadBlock(LOTRMod.brick, 11);
		}
	};
	public static LOTRRoadType GALADHRIM_RUINED = new LOTRRoadType() {

		@Override
		public RoadBlock getBlock(Random rand, BiomeGenBase biome, boolean top, boolean slab) {
			if (slab) {
				if (rand.nextInt(4) == 0) {
					return rand.nextBoolean() ? new RoadBlock(LOTRMod.slabSingle2, 4) : new RoadBlock(LOTRMod.slabSingle2, 5);
				}
				return new RoadBlock(LOTRMod.slabSingle2, 3);
			}
			if (rand.nextInt(4) == 0) {
				return rand.nextBoolean() ? new RoadBlock(LOTRMod.brick, 12) : new RoadBlock(LOTRMod.brick, 13);
			}
			return new RoadBlock(LOTRMod.brick, 11);
		}
	};
	public static LOTRRoadType HIGH_ELVEN = new LOTRRoadType() {

		@Override
		public RoadBlock getBlock(Random rand, BiomeGenBase biome, boolean top, boolean slab) {
			if (slab) {
				return new RoadBlock(LOTRMod.slabSingle5, 5);
			}
			return new RoadBlock(LOTRMod.brick3, 2);
		}
	};
	public static LOTRRoadType HIGH_ELVEN_RUINED = new LOTRRoadType() {

		@Override
		public RoadBlock getBlock(Random rand, BiomeGenBase biome, boolean top, boolean slab) {
			if (slab) {
				if (rand.nextInt(4) == 0) {
					return rand.nextBoolean() ? new RoadBlock(LOTRMod.slabSingle5, 6) : new RoadBlock(LOTRMod.slabSingle5, 7);
				}
				return new RoadBlock(LOTRMod.slabSingle5, 5);
			}
			if (rand.nextInt(4) == 0) {
				return rand.nextBoolean() ? new RoadBlock(LOTRMod.brick3, 3) : new RoadBlock(LOTRMod.brick3, 4);
			}
			return new RoadBlock(LOTRMod.brick3, 2);
		}
	};
	public static LOTRRoadType WOOD_ELVEN = new LOTRRoadType() {

		@Override
		public RoadBlock getBlock(Random rand, BiomeGenBase biome, boolean top, boolean slab) {
			if (slab) {
				return new RoadBlock(LOTRMod.slabSingle6, 2);
			}
			return new RoadBlock(LOTRMod.brick3, 5);
		}
	};
	public static LOTRRoadType WOOD_ELVEN_RUINED = new LOTRRoadType() {

		@Override
		public RoadBlock getBlock(Random rand, BiomeGenBase biome, boolean top, boolean slab) {
			if (slab) {
				if (rand.nextInt(4) == 0) {
					return rand.nextBoolean() ? new RoadBlock(LOTRMod.slabSingle6, 3) : new RoadBlock(LOTRMod.slabSingle6, 4);
				}
				return new RoadBlock(LOTRMod.slabSingle6, 2);
			}
			if (rand.nextInt(4) == 0) {
				return rand.nextBoolean() ? new RoadBlock(LOTRMod.brick3, 6) : new RoadBlock(LOTRMod.brick3, 7);
			}
			return new RoadBlock(LOTRMod.brick3, 5);
		}
	};
	public static LOTRRoadType ARNOR = new LOTRRoadType() {

		@Override
		public RoadBlock getBlock(Random rand, BiomeGenBase biome, boolean top, boolean slab) {
			if (slab) {
				if (rand.nextInt(4) == 0) {
					return rand.nextBoolean() ? new RoadBlock(LOTRMod.slabSingle4, 2) : new RoadBlock(LOTRMod.slabSingle4, 3);
				}
				return new RoadBlock(LOTRMod.slabSingle4, 1);
			}
			if (rand.nextInt(4) == 0) {
				return rand.nextBoolean() ? new RoadBlock(LOTRMod.brick2, 4) : new RoadBlock(LOTRMod.brick2, 5);
			}
			return new RoadBlock(LOTRMod.brick2, 3);
		}
	};
	public static LOTRRoadType GONDOR = new LOTRRoadType() {

		@Override
		public RoadBlock getBlock(Random rand, BiomeGenBase biome, boolean top, boolean slab) {
			if (slab) {
				return new RoadBlock(LOTRMod.slabSingle, 3);
			}
			return new RoadBlock(LOTRMod.brick, 1);
		}
	};
	public static LOTRRoadType GONDOR_MIX = new LOTRRoadType() {

		@Override
		public RoadBlock getBlock(Random rand, BiomeGenBase biome, boolean top, boolean slab) {
			if (slab) {
				if (rand.nextInt(8) == 0) {
					return new RoadBlock(LOTRMod.slabSingle, 2);
				}
				if (rand.nextInt(8) == 0) {
					return rand.nextBoolean() ? new RoadBlock(LOTRMod.slabSingle, 4) : new RoadBlock(LOTRMod.slabSingle, 5);
				}
				return new RoadBlock(LOTRMod.slabSingle, 3);
			}
			if (rand.nextInt(8) == 0) {
				return new RoadBlock(LOTRMod.slabDouble, 2);
			}
			if (rand.nextInt(8) == 0) {
				return rand.nextBoolean() ? new RoadBlock(LOTRMod.brick, 2) : new RoadBlock(LOTRMod.brick, 3);
			}
			return new RoadBlock(LOTRMod.brick, 1);
		}
	};
	public static LOTRRoadType DOL_AMROTH = new LOTRRoadType() {

		@Override
		public RoadBlock getBlock(Random rand, BiomeGenBase biome, boolean top, boolean slab) {
			if (slab) {
				return new RoadBlock(LOTRMod.slabSingle6, 7);
			}
			return new RoadBlock(LOTRMod.brick3, 9);
		}
	};
	public static LOTRRoadType ROHAN = new LOTRRoadType() {

		@Override
		public RoadBlock getBlock(Random rand, BiomeGenBase biome, boolean top, boolean slab) {
			if (slab) {
				return new RoadBlock(LOTRMod.slabSingle, 6);
			}
			return new RoadBlock(LOTRMod.brick, 4);
		}
	};
	public static LOTRRoadType ROHAN_MIX = new LOTRRoadType() {

		@Override
		public RoadBlock getBlock(Random rand, BiomeGenBase biome, boolean top, boolean slab) {
			if (slab) {
				if (rand.nextInt(3) == 0) {
					return rand.nextBoolean() ? new RoadBlock(LOTRMod.slabSingleDirt, 0) : new RoadBlock(LOTRMod.slabSingleDirt, 1);
				}
				return rand.nextBoolean() ? new RoadBlock(LOTRMod.slabSingle, 6) : new RoadBlock(LOTRMod.slabSingle11, 4);
			}
			if (rand.nextInt(3) == 0) {
				return rand.nextBoolean() ? new RoadBlock(Blocks.dirt, 1) : new RoadBlock(LOTRMod.dirtPath, 0);
			}
			return rand.nextBoolean() ? new RoadBlock(LOTRMod.brick, 4) : new RoadBlock(LOTRMod.rock, 2);
		}
	};
	public static LOTRRoadType DWARVEN = new LOTRRoadType() {

		@Override
		public RoadBlock getBlock(Random rand, BiomeGenBase biome, boolean top, boolean slab) {
			if (slab) {
				return new RoadBlock(LOTRMod.slabSingle, 7);
			}
			return new RoadBlock(LOTRMod.brick, 6);
		}
	};
	public static LOTRRoadType DALE = new LOTRRoadType() {

		@Override
		public RoadBlock getBlock(Random rand, BiomeGenBase biome, boolean top, boolean slab) {
			if (slab) {
				return new RoadBlock(LOTRMod.slabSingle9, 6);
			}
			return new RoadBlock(LOTRMod.brick5, 1);
		}
	};
	public static LOTRRoadType HARAD = new LOTRRoadType() {

		@Override
		public RoadBlock getBlock(Random rand, BiomeGenBase biome, boolean top, boolean slab) {
			if (slab) {
				return new RoadBlock(LOTRMod.slabSingle4, 0);
			}
			return new RoadBlock(LOTRMod.brick, 15);
		}
	};
	public static LOTRRoadType HARAD_PATH = new LOTRRoadType() {

		@Override
		public RoadBlock getBlock(Random rand, BiomeGenBase biome, boolean top, boolean slab) {
			if (slab) {
				float f = rand.nextFloat();
				if (f < 0.33f) {
					if (rand.nextInt(4) == 0) {
						return new RoadBlock(LOTRMod.slabSingle7, 1);
					}
					return new RoadBlock(LOTRMod.slabSingle4, 0);
				}
				if (f < 0.67f) {
					return new RoadBlock(LOTRMod.slabSingleSand, 0);
				}
				return new RoadBlock(LOTRMod.slabSingleDirt, 1);
			}
			float f = rand.nextFloat();
			if (f < 0.33f) {
				if (rand.nextInt(4) == 0) {
					return new RoadBlock(LOTRMod.brick3, 11);
				}
				return new RoadBlock(LOTRMod.brick, 15);
			}
			if (f < 0.67f) {
				return top ? new RoadBlock(Blocks.sand, 0) : new RoadBlock(Blocks.sandstone, 0);
			}
			return new RoadBlock(LOTRMod.dirtPath, 0);
		}
	};
	public static LOTRRoadType HARAD_TOWN = new LOTRRoadType() {

		@Override
		public RoadBlock getBlock(Random rand, BiomeGenBase biome, boolean top, boolean slab) {
			if (slab) {
				float f = rand.nextFloat();
				if (f < 0.17f) {
					return new RoadBlock(LOTRMod.slabSingleDirt, 0);
				}
				if (f < 0.33f) {
					return new RoadBlock(LOTRMod.slabSingleDirt, 1);
				}
				if (f < 0.5f) {
					return new RoadBlock(LOTRMod.slabSingleSand, 0);
				}
				if (f < 0.67f) {
					return new RoadBlock(LOTRMod.slabSingle4, 0);
				}
				if (f < 0.83f) {
					return new RoadBlock(LOTRMod.slabSingle7, 1);
				}
				return new RoadBlock(LOTRMod.slabSingle4, 7);
			}
			float f = rand.nextFloat();
			if (f < 0.17f) {
				return new RoadBlock(Blocks.dirt, 1);
			}
			if (f < 0.33f) {
				return new RoadBlock(LOTRMod.dirtPath, 0);
			}
			if (f < 0.5f) {
				return top ? new RoadBlock(Blocks.sand, 0) : new RoadBlock(Blocks.sandstone, 0);
			}
			if (f < 0.67f) {
				return new RoadBlock(LOTRMod.brick, 15);
			}
			if (f < 0.83f) {
				return new RoadBlock(LOTRMod.brick3, 11);
			}
			return new RoadBlock(LOTRMod.pillar, 5);
		}
	};
	public static LOTRRoadType UMBAR = new LOTRRoadType() {

		@Override
		public RoadBlock getBlock(Random rand, BiomeGenBase biome, boolean top, boolean slab) {
			if (slab) {
				return new RoadBlock(LOTRMod.slabSingle13, 2);
			}
			return new RoadBlock(LOTRMod.brick6, 6);
		}
	};
	public static LOTRRoadType GULF_HARAD = new LOTRRoadType() {

		@Override
		public RoadBlock getBlock(Random rand, BiomeGenBase biome, boolean top, boolean slab) {
			if (slab) {
				float f = rand.nextFloat();
				if (f < 0.25f) {
					return new RoadBlock(LOTRMod.slabSingleDirt, 0);
				}
				if (f < 0.5f) {
					return new RoadBlock(LOTRMod.slabSingleSand, 1);
				}
				if (f < 0.75f) {
					return new RoadBlock(LOTRMod.slabSingle7, 2);
				}
				return new RoadBlock(LOTRMod.slabSingle7, 3);
			}
			float f = rand.nextFloat();
			if (f < 0.25f) {
				return new RoadBlock(Blocks.dirt, 1);
			}
			if (f < 0.5f) {
				return top ? new RoadBlock(Blocks.sand, 1) : new RoadBlock(LOTRMod.redSandstone, 0);
			}
			if (f < 0.75f) {
				return new RoadBlock(LOTRMod.brick3, 13);
			}
			return new RoadBlock(LOTRMod.brick3, 14);
		}
	};
	public static LOTRRoadType TAUREDAIN = new LOTRRoadType() {

		@Override
		public RoadBlock getBlock(Random rand, BiomeGenBase biome, boolean top, boolean slab) {
			if (slab) {
				if (rand.nextInt(4) == 0) {
					if (rand.nextBoolean()) {
						return new RoadBlock(LOTRMod.slabSingle8, 1);
					}
					return new RoadBlock(LOTRMod.slabSingle8, 2);
				}
				return new RoadBlock(LOTRMod.slabSingle8, 0);
			}
			if (rand.nextInt(4) == 0) {
				if (rand.nextBoolean()) {
					return new RoadBlock(LOTRMod.brick4, 1);
				}
				return new RoadBlock(LOTRMod.brick4, 2);
			}
			return new RoadBlock(LOTRMod.brick4, 0);
		}
	};
	public static LOTRRoadType MORDOR = new LOTRRoadType() {

		@Override
		public RoadBlock getBlock(Random rand, BiomeGenBase biome, boolean top, boolean slab) {
			if (slab) {
				return new RoadBlock(LOTRMod.slabSingleDirt, 3);
			}
			return new RoadBlock(LOTRMod.mordorDirt, 0);
		}
	};
	public static LOTRRoadType DORWINION = new LOTRRoadType() {

		@Override
		public RoadBlock getBlock(Random rand, BiomeGenBase biome, boolean top, boolean slab) {
			if (slab) {
				return new RoadBlock(LOTRMod.slabSingle9, 7);
			}
			return new RoadBlock(LOTRMod.brick5, 2);
		}
	};
	public static LOTRRoadType RHUN = new LOTRRoadType() {

		@Override
		public RoadBlock getBlock(Random rand, BiomeGenBase biome, boolean top, boolean slab) {
			if (slab) {
				if (rand.nextInt(8) == 0) {
					return rand.nextBoolean() ? new RoadBlock(LOTRMod.slabSingle12, 1) : new RoadBlock(LOTRMod.slabSingle12, 2);
				}
				return new RoadBlock(LOTRMod.slabSingle12, 0);
			}
			if (rand.nextInt(8) == 0) {
				return rand.nextBoolean() ? new RoadBlock(LOTRMod.brick5, 13) : new RoadBlock(LOTRMod.brick5, 14);
			}
			return new RoadBlock(LOTRMod.brick5, 11);
		}
	};

	public abstract RoadBlock getBlock(Random var1, BiomeGenBase var2, boolean var3, boolean var4);

	public float getRepair() {
		return 1.0f;
	}

	public LOTRRoadType setRepair(float f) {
		LOTRRoadType baseRoad = this;
		return new LOTRRoadType() {

			@Override
			public RoadBlock getBlock(Random rand, BiomeGenBase biome, boolean top, boolean slab) {
				return baseRoad.getBlock(rand, biome, top, slab);
			}

			@Override
			public float getRepair() {
				return f;
			}

			@Override
			public boolean hasFlowers() {
				return baseRoad.hasFlowers();
			}
		};
	}

	public boolean hasFlowers() {
		return false;
	}

	public LOTRRoadType setHasFlowers(boolean flag) {
		LOTRRoadType baseRoad = this;
		return new LOTRRoadType() {

			@Override
			public RoadBlock getBlock(Random rand, BiomeGenBase biome, boolean top, boolean slab) {
				return baseRoad.getBlock(rand, biome, top, slab);
			}

			@Override
			public float getRepair() {
				return baseRoad.getRepair();
			}

			@Override
			public boolean hasFlowers() {
				return flag;
			}
		};
	}

	public abstract static class BridgeType {
		public static BridgeType DEFAULT = new BridgeType() {

			@Override
			public RoadBlock getBlock(Random rand, boolean slab) {
				if (slab) {
					return new RoadBlock(Blocks.wooden_slab, 0);
				}
				return new RoadBlock(Blocks.planks, 0);
			}

			@Override
			public RoadBlock getEdge(Random rand) {
				return new RoadBlock(LOTRMod.woodBeamV1, 0);
			}

			@Override
			public RoadBlock getFence(Random rand) {
				return new RoadBlock(Blocks.fence, 0);
			}
		};
		public static BridgeType MIRKWOOD = new BridgeType() {

			@Override
			public RoadBlock getBlock(Random rand, boolean slab) {
				if (slab) {
					return new RoadBlock(LOTRMod.woodSlabSingle, 2);
				}
				return new RoadBlock(LOTRMod.planks, 2);
			}

			@Override
			public RoadBlock getEdge(Random rand) {
				return new RoadBlock(LOTRMod.woodBeam1, 2);
			}

			@Override
			public RoadBlock getFence(Random rand) {
				return new RoadBlock(LOTRMod.fence, 2);
			}
		};
		public static BridgeType CHARRED = new BridgeType() {

			@Override
			public RoadBlock getBlock(Random rand, boolean slab) {
				if (slab) {
					return new RoadBlock(LOTRMod.woodSlabSingle, 3);
				}
				return new RoadBlock(LOTRMod.planks, 3);
			}

			@Override
			public RoadBlock getEdge(Random rand) {
				return new RoadBlock(LOTRMod.woodBeam1, 3);
			}

			@Override
			public RoadBlock getFence(Random rand) {
				return new RoadBlock(LOTRMod.fence, 3);
			}
		};

		public abstract RoadBlock getBlock(Random var1, boolean var2);

		public abstract RoadBlock getEdge(Random var1);

		public abstract RoadBlock getFence(Random var1);

	}

	public static class RoadBlock {
		public Block block;
		public int meta;

		public RoadBlock(Block b, int i) {
			block = b;
			meta = i;
		}
	}

}
