package lotr.common.world.mapgen.dwarvenmine;

import java.util.*;

import net.minecraft.world.gen.structure.*;

public class LOTRStructureDwarvenMinePieces {
	public static void getNextComponent(StructureComponent component, List list, Random random, int i, int j, int k, int direction, int iteration, boolean ruined) {
		getNextMineComponent(component, list, random, i, j, k, direction, iteration, ruined);
	}

	public static void getNextMineComponent(StructureComponent component, List list, Random random, int i, int j, int k, int direction, int iteration, boolean ruined) {
		if (iteration > 12) {
			return;
		}
		if (Math.abs(i - component.getBoundingBox().minX) <= 80 && Math.abs(k - component.getBoundingBox().minZ) <= 80) {
			StructureComponent structurecomponent1 = getRandomComponent(list, random, i, j, k, direction, iteration + 1, ruined);
			if (structurecomponent1 != null) {
				list.add(structurecomponent1);
				structurecomponent1.buildComponent(component, list, random);
			}
		}
	}

	public static StructureComponent getRandomComponent(List list, Random random, int i, int j, int k, int direction, int iteration, boolean ruined) {
		int l = random.nextInt(100);
		if (l >= 80) {
			StructureBoundingBox structureboundingbox = LOTRComponentDwarvenMineCrossing.findValidPlacement(list, random, i, j, k, direction);
			if (structureboundingbox != null) {
				return new LOTRComponentDwarvenMineCrossing(iteration, random, structureboundingbox, direction, ruined);
			}
		} else if (l >= 70) {
			StructureBoundingBox structureboundingbox = LOTRComponentDwarvenMineStairs.findValidPlacement(list, random, i, j, k, direction);
			if (structureboundingbox != null) {
				return new LOTRComponentDwarvenMineStairs(iteration, random, structureboundingbox, direction, ruined);
			}
		} else {
			StructureBoundingBox structureboundingbox = LOTRComponentDwarvenMineCorridor.findValidPlacement(list, random, i, j, k, direction);
			if (structureboundingbox != null) {
				return new LOTRComponentDwarvenMineCorridor(iteration, random, structureboundingbox, direction, ruined);
			}
		}
		return null;
	}
}
