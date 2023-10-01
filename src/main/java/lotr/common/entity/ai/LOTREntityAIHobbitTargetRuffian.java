package lotr.common.entity.ai;

import lotr.common.world.biome.LOTRBiomeGenShire;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;

public class LOTREntityAIHobbitTargetRuffian extends LOTREntityAINearestAttackableTargetBasic {
	public LOTREntityAIHobbitTargetRuffian(EntityCreature entity, Class targetClass, int chance, boolean flag) {
		super(entity, targetClass, chance, flag);
	}

	public LOTREntityAIHobbitTargetRuffian(EntityCreature entity, Class targetClass, int chance, boolean flag, IEntitySelector selector) {
		super(entity, targetClass, chance, flag, selector);
	}

	@Override
	public boolean isSuitableTarget(EntityLivingBase entity, boolean flag) {
		return super.isSuitableTarget(entity, flag) && taskOwner.worldObj.getBiomeGenForCoords(MathHelper.floor_double(taskOwner.posX), MathHelper.floor_double(taskOwner.posZ)) instanceof LOTRBiomeGenShire;
	}
}
