package lotr.common.world.mapgen.dwarvenmine;

import lotr.common.world.structure2.LOTRWorldGenDwarvenMineEntrance;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;

import java.util.List;
import java.util.Random;

public class LOTRComponentDwarvenMineEntrance extends StructureComponent {
	public static LOTRWorldGenDwarvenMineEntrance entranceGen = new LOTRWorldGenDwarvenMineEntrance(false);

	static {
		entranceGen.restrictions = false;
	}

	public int posX;
	public int posY = -1;
	public int posZ;
	public int direction;

	public boolean ruined;

	public LOTRComponentDwarvenMineEntrance() {
	}

	public LOTRComponentDwarvenMineEntrance(World world, int l, Random random, int i, int k, boolean r) {
		super(l);
		boundingBox = new StructureBoundingBox(i - 4, 40, k - 4, i + 4, 256, k + 4);
		posX = i;
		posZ = k;
		ruined = r;
	}

	@Override
	public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBoundingBox) {
		if (posY == -1) {
			posY = world.getTopSolidOrLiquidBlock(posX, posZ);
		}
		if (world.getBlock(posX, posY - 1, posZ) != Blocks.grass) {
			return false;
		}
		entranceGen.isRuined = ruined;
		entranceGen.generateWithSetRotation(world, random, posX, posY, posZ, direction);
		return true;
	}

	@Override
	public void buildComponent(StructureComponent component, List list, Random random) {
		StructureBoundingBox structureBoundingBox = null;
		direction = random.nextInt(4);
		switch (direction) {
			case 0: {
				structureBoundingBox = new StructureBoundingBox(posX - 1, boundingBox.minY + 1, posZ + 4, posX + 1, boundingBox.minY + 4, posZ + 15);
				break;
			}
			case 1: {
				structureBoundingBox = new StructureBoundingBox(posX - 15, boundingBox.minY + 1, posZ - 1, posX - 4, boundingBox.minY + 4, posZ + 1);
				break;
			}
			case 2: {
				structureBoundingBox = new StructureBoundingBox(posX - 1, boundingBox.minY + 1, posZ - 15, posX + 1, boundingBox.minY + 4, posZ - 4);
				break;
			}
			case 3: {
				structureBoundingBox = new StructureBoundingBox(posX + 4, boundingBox.minY + 1, posZ - 1, posX + 15, boundingBox.minY + 4, posZ + 1);
			}
		}
		LOTRComponentDwarvenMineCorridor corridor = new LOTRComponentDwarvenMineCorridor(0, random, structureBoundingBox, direction, ruined);
		list.add(corridor);
		corridor.buildComponent(component, list, random);
	}

	@Override
	public void func_143011_b(NBTTagCompound nbt) {
		posX = nbt.getInteger("EntranceX");
		posY = nbt.getInteger("EntranceY");
		posZ = nbt.getInteger("EntranceZ");
		direction = nbt.getInteger("Direction");
		ruined = nbt.getBoolean("Ruined");
	}

	@Override
	public void func_143012_a(NBTTagCompound nbt) {
		nbt.setInteger("EntranceX", posX);
		nbt.setInteger("EntranceY", posY);
		nbt.setInteger("EntranceZ", posZ);
		nbt.setInteger("Direction", direction);
		nbt.setBoolean("Ruined", ruined);
	}
}
