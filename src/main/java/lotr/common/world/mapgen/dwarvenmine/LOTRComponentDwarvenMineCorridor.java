package lotr.common.world.mapgen.dwarvenmine;

import java.util.*;

import lotr.common.LOTRMod;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.*;

public class LOTRComponentDwarvenMineCorridor extends StructureComponent {
	public int sectionCount;
	public boolean ruined;

	public LOTRComponentDwarvenMineCorridor() {
	}

	public LOTRComponentDwarvenMineCorridor(int i, Random random, StructureBoundingBox structureBoundingBox, int j, boolean r) {
		super(i);
		coordBaseMode = j;
		boundingBox = structureBoundingBox;
		sectionCount = coordBaseMode != 2 && coordBaseMode != 0 ? boundingBox.getXSize() / 4 : boundingBox.getZSize() / 4;
		ruined = r;
	}

	@Override
	public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBoundingBox) {
		if (isLiquidInStructureBoundingBox(world, structureBoundingBox)) {
			return false;
		}
		int length = sectionCount * 4 - 1;
		fillWithBlocks(world, structureBoundingBox, 0, 0, 0, 2, 2, length, Blocks.air, Blocks.air, false);
		for (int l = 0; l < sectionCount; ++l) {
			int k = 2 + l * 4;
			for (int i : new int[]{0, 2}) {
				int wallHeight = ruined ? random.nextInt(3) : 2;
				for (int j = 0; j <= wallHeight; ++j) {
					placeBlockAtCurrentPosition(world, LOTRMod.wall, 7, i, j, k, structureBoundingBox);
				}
			}
			fillWithBlocks(world, structureBoundingBox, -1, 0, k, -1, 2, k, LOTRMod.pillar, Blocks.air, false);
			fillWithBlocks(world, structureBoundingBox, 3, 0, k, 3, 2, k, LOTRMod.pillar, Blocks.air, false);
			fillWithBlocks(world, structureBoundingBox, 1, -1, k - 2, 1, -1, k + 2, LOTRMod.pillar, Blocks.air, false);
			if (getBlockAtCurrentPosition(world, 1, -1, k - 3, structureBoundingBox) != Blocks.air) {
				placeBlockAtCurrentPosition(world, LOTRMod.pillar, 0, 1, -1, k - 3, structureBoundingBox);
			}
			if (getBlockAtCurrentPosition(world, 1, -1, k + 3, structureBoundingBox) != Blocks.air) {
				placeBlockAtCurrentPosition(world, LOTRMod.pillar, 0, 1, -1, k + 3, structureBoundingBox);
			}
			if (!ruined) {
				placeBlockAtCurrentPosition(world, LOTRMod.brick3, 12, 1, -1, k, structureBoundingBox);
				if (random.nextInt(80) == 0) {
					placeBlockAtCurrentPosition(world, Blocks.crafting_table, 0, 2, 0, k - 1, structureBoundingBox);
				}
				if (random.nextInt(80) == 0) {
					placeBlockAtCurrentPosition(world, Blocks.crafting_table, 0, 0, 0, k + 1, structureBoundingBox);
				}
			}
			if (random.nextInt(120) == 0) {
				generateStructureChestContents(world, structureBoundingBox, random, 2, 0, k - 1, LOTRChestContents.DWARVEN_MINE_CORRIDOR.items, LOTRChestContents.getRandomItemAmount(LOTRChestContents.DWARVEN_MINE_CORRIDOR, random));
			}
			if (random.nextInt(120) != 0) {
				continue;
			}
			generateStructureChestContents(world, structureBoundingBox, random, 0, 0, k + 1, LOTRChestContents.DWARVEN_MINE_CORRIDOR.items, LOTRChestContents.getRandomItemAmount(LOTRChestContents.DWARVEN_MINE_CORRIDOR, random));
		}
		for (int k = 0; k <= length; ++k) {
			Block block;
			for (int i = -1; i <= 3; ++i) {
				int j;
				block = getBlockAtCurrentPosition(world, i, -1, k, structureBoundingBox);
				if (block.getMaterial().isReplaceable() || block.getMaterial() == Material.sand) {
					placeBlockAtCurrentPosition(world, Blocks.stone, 0, i, -1, k, structureBoundingBox);
				}
				if (!(block = getBlockAtCurrentPosition(world, i, j = 3, k, structureBoundingBox)).getMaterial().isReplaceable() && block.getMaterial() != Material.sand) {
					continue;
				}
				placeBlockAtCurrentPosition(world, Blocks.stone, 0, i, j, k, structureBoundingBox);
			}
			for (int j = 0; j <= 2; ++j) {
				block = getBlockAtCurrentPosition(world, -1, j, k, structureBoundingBox);
				if (block.getMaterial().isReplaceable() || block.getMaterial() == Material.sand) {
					placeBlockAtCurrentPosition(world, Blocks.stone, 0, -1, j, k, structureBoundingBox);
				}
				if (!(block = getBlockAtCurrentPosition(world, 3, j, k, structureBoundingBox)).getMaterial().isReplaceable() && block.getMaterial() != Material.sand) {
					continue;
				}
				placeBlockAtCurrentPosition(world, Blocks.stone, 0, 3, j, k, structureBoundingBox);
			}
		}
		return true;
	}

	@Override
	public void buildComponent(StructureComponent component, List list, Random random) {
		block24:
		{
			int i = getComponentType();
			int j = random.nextInt(4);
			switch (coordBaseMode) {
				case 0: {
					if (j <= 1) {
						LOTRStructureDwarvenMinePieces.getNextComponent(component, list, random, boundingBox.minX, boundingBox.minY - 1 + random.nextInt(3), boundingBox.maxZ + 1, coordBaseMode, i, ruined);
						break;
					}
					if (j == 2) {
						LOTRStructureDwarvenMinePieces.getNextComponent(component, list, random, boundingBox.minX - 1, boundingBox.minY - 1 + random.nextInt(3), boundingBox.maxZ - 3, 1, i, ruined);
						break;
					}
					LOTRStructureDwarvenMinePieces.getNextComponent(component, list, random, boundingBox.maxX + 1, boundingBox.minY - 1 + random.nextInt(3), boundingBox.maxZ - 3, 3, i, ruined);
					break;
				}
				case 1: {
					if (j <= 1) {
						LOTRStructureDwarvenMinePieces.getNextComponent(component, list, random, boundingBox.minX - 1, boundingBox.minY - 1 + random.nextInt(3), boundingBox.minZ, coordBaseMode, i, ruined);
						break;
					}
					if (j == 2) {
						LOTRStructureDwarvenMinePieces.getNextComponent(component, list, random, boundingBox.minX, boundingBox.minY - 1 + random.nextInt(3), boundingBox.minZ - 1, 2, i, ruined);
						break;
					}
					LOTRStructureDwarvenMinePieces.getNextComponent(component, list, random, boundingBox.minX, boundingBox.minY - 1 + random.nextInt(3), boundingBox.maxZ + 1, 0, i, ruined);
					break;
				}
				case 2: {
					if (j <= 1) {
						LOTRStructureDwarvenMinePieces.getNextComponent(component, list, random, boundingBox.minX, boundingBox.minY - 1 + random.nextInt(3), boundingBox.minZ - 1, coordBaseMode, i, ruined);
						break;
					}
					if (j == 2) {
						LOTRStructureDwarvenMinePieces.getNextComponent(component, list, random, boundingBox.minX - 1, boundingBox.minY - 1 + random.nextInt(3), boundingBox.minZ, 1, i, ruined);
						break;
					}
					LOTRStructureDwarvenMinePieces.getNextComponent(component, list, random, boundingBox.maxX + 1, boundingBox.minY - 1 + random.nextInt(3), boundingBox.minZ, 3, i, ruined);
					break;
				}
				case 3: {
					if (j <= 1) {
						LOTRStructureDwarvenMinePieces.getNextComponent(component, list, random, boundingBox.maxX + 1, boundingBox.minY - 1 + random.nextInt(3), boundingBox.minZ, coordBaseMode, i, ruined);
						break;
					}
					if (j == 2) {
						LOTRStructureDwarvenMinePieces.getNextComponent(component, list, random, boundingBox.maxX - 3, boundingBox.minY - 1 + random.nextInt(3), boundingBox.minZ - 1, 2, i, ruined);
						break;
					}
					LOTRStructureDwarvenMinePieces.getNextComponent(component, list, random, boundingBox.maxX - 3, boundingBox.minY - 1 + random.nextInt(3), boundingBox.maxZ + 1, 0, i, ruined);
				}
			}
			if (i >= 12) {
				break block24;
			}
			if (coordBaseMode != 2 && coordBaseMode != 0) {
				int k = boundingBox.minX + 3;
				while (k + 3 <= boundingBox.maxX) {
					int l = random.nextInt(5);
					if (l == 0) {
						LOTRStructureDwarvenMinePieces.getNextComponent(component, list, random, k, boundingBox.minY, boundingBox.minZ - 1, 2, i + 1, ruined);
					} else if (l == 1) {
						LOTRStructureDwarvenMinePieces.getNextComponent(component, list, random, k, boundingBox.minY, boundingBox.maxZ + 1, 0, i + 1, ruined);
					}
					k += 4;
				}
			} else {
				int k = boundingBox.minZ + 3;
				while (k + 3 <= boundingBox.maxZ) {
					int l = random.nextInt(5);
					if (l == 0) {
						LOTRStructureDwarvenMinePieces.getNextComponent(component, list, random, boundingBox.minX - 1, boundingBox.minY, k, 1, i + 1, ruined);
					} else if (l == 1) {
						LOTRStructureDwarvenMinePieces.getNextComponent(component, list, random, boundingBox.maxX + 1, boundingBox.minY, k, 3, i + 1, ruined);
					}
					k += 4;
				}
			}
		}
	}

	@Override
	public void func_143011_b(NBTTagCompound nbt) {
		sectionCount = nbt.getInteger("Sections");
		ruined = nbt.getBoolean("Ruined");
	}

	@Override
	public void func_143012_a(NBTTagCompound nbt) {
		nbt.setInteger("Sections", sectionCount);
		nbt.setBoolean("Ruined", ruined);
	}

	public static StructureBoundingBox findValidPlacement(List list, Random random, int i, int j, int k, int l) {
		int i1;
		StructureBoundingBox structureboundingbox = new StructureBoundingBox(i, j, k, i, j + 3, k);
		for (i1 = random.nextInt(3) + 2; i1 > 0; --i1) {
			int j1 = i1 * 4;
			switch (l) {
				case 0: {
					structureboundingbox.maxX = i + 2;
					structureboundingbox.maxZ = k + j1 - 1;
					break;
				}
				case 1: {
					structureboundingbox.minX = i - (j1 - 1);
					structureboundingbox.maxZ = k + 2;
					break;
				}
				case 2: {
					structureboundingbox.maxX = i + 2;
					structureboundingbox.minZ = k - (j1 - 1);
					break;
				}
				case 3: {
					structureboundingbox.maxX = i + j1 - 1;
					structureboundingbox.maxZ = k + 2;
				}
			}
			if (StructureComponent.findIntersecting(list, structureboundingbox) == null) {
				break;
			}
		}
		return i1 > 0 ? structureboundingbox : null;
	}
}
