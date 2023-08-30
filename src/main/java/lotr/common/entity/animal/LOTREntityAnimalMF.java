package lotr.common.entity.animal;

import lotr.common.LOTRMod;
import lotr.common.entity.LOTREntities;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public abstract class LOTREntityAnimalMF extends EntityAnimal {
	protected LOTREntityAnimalMF(World world) {
		super(world);
	}

	@Override
	public boolean canMateWith(EntityAnimal mate) {
		LOTREntityAnimalMF mfMate = (LOTREntityAnimalMF) mate;
		if (mate == this) {
			return false;
		}
		if (getAnimalMFBaseClass().equals(mfMate.getAnimalMFBaseClass()) && isInLove() && mate.isInLove()) {
			boolean thisMale = isMale();
			return thisMale != mfMate.isMale();
		}
		return false;
	}

	public abstract Class getAnimalMFBaseClass();

	@Override
	public ItemStack getPickedResult(MovingObjectPosition target) {
		return new ItemStack(LOTRMod.spawnEgg, 1, LOTREntities.getEntityID(this));
	}

	public abstract boolean isMale();
}
