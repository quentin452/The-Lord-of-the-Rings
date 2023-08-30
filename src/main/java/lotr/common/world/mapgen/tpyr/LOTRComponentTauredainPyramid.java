package lotr.common.world.mapgen.tpyr;

import lotr.common.world.structure2.LOTRWorldGenTauredainPyramid;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;

import java.util.Random;

public class LOTRComponentTauredainPyramid extends StructureComponent {
	public static LOTRWorldGenTauredainPyramid pyramidGen = new LOTRWorldGenTauredainPyramid(false);
	public static Random pyramidRand = new Random();

	static {
		pyramidGen.restrictions = false;
	}

	public int posX;
	public int posY = -1;
	public int posZ;
	public int direction;

	public long pyramidSeed = -1L;

	public LOTRComponentTauredainPyramid() {
	}

	public LOTRComponentTauredainPyramid(World world, int l, Random random, int i, int k) {
		super(l);
		int r = LOTRWorldGenTauredainPyramid.RADIUS + 5;
		boundingBox = new StructureBoundingBox(i - r, 0, k - r, i + r, 255, k + r);
		posX = i;
		posZ = k;
		direction = random.nextInt(4);
	}

	@Override
	public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBoundingBox) {
		if (posY == -1) {
			posY = world.getTopSolidOrLiquidBlock(structureBoundingBox.getCenterX(), structureBoundingBox.getCenterZ());
		}
		if (pyramidSeed == -1L) {
			pyramidSeed = random.nextLong();
		}
		pyramidGen.setStructureBB(structureBoundingBox);
		pyramidRand.setSeed(pyramidSeed);
		pyramidGen.generateWithSetRotation(world, pyramidRand, posX, posY, posZ, direction);
		return true;
	}

	@Override
	public void func_143011_b(NBTTagCompound nbt) {
		posX = nbt.getInteger("PyrX");
		posY = nbt.getInteger("PyrY");
		posZ = nbt.getInteger("PyrZ");
		direction = nbt.getInteger("Direction");
		pyramidSeed = nbt.getLong("Seed");
	}

	@Override
	public void func_143012_a(NBTTagCompound nbt) {
		nbt.setInteger("PyrX", posX);
		nbt.setInteger("PyrY", posY);
		nbt.setInteger("PyrZ", posZ);
		nbt.setInteger("Direction", direction);
		nbt.setLong("Seed", pyramidSeed);
	}
}
