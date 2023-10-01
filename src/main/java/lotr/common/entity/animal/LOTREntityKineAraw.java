package lotr.common.entity.animal;

import lotr.common.LOTRMod;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.world.World;

public class LOTREntityKineAraw extends LOTREntityAurochs {
	public static float KINE_SCALE = 1.15f;

	public LOTREntityKineAraw(World world) {
		super(world);
		setSize(aurochsWidth * KINE_SCALE, aurochsHeight * KINE_SCALE);
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(40.0);
		getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(5.0);
	}

	@Override
	public EntityAIBase createAurochsAttackAI() {
		return new LOTREntityAIAttackOnCollide(this, 1.9, true);
	}

	@Override
	public EntityCow createChild(EntityAgeable entity) {
		return new LOTREntityKineAraw(worldObj);
	}

	@Override
	public void dropHornItem(boolean flag, int i) {
		dropItem(LOTRMod.kineArawHorn, 1);
	}
}
