package lotr.common.world.mapgen.tpyr;

import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureStart;

import java.util.Random;

public class LOTRStructureTPyrStart extends StructureStart {
	public LOTRStructureTPyrStart() {
	}

	public LOTRStructureTPyrStart(World world, Random random, int i, int j) {
		LOTRComponentTauredainPyramid startComponent = new LOTRComponentTauredainPyramid(world, 0, random, (i << 4) + 8, (j << 4) + 8);
		components.add(startComponent);
		startComponent.buildComponent(startComponent, components, random);
		updateBoundingBox();
	}
}
