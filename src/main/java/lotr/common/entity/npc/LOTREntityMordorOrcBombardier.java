package lotr.common.entity.npc;

import lotr.common.LOTRMod;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import lotr.common.entity.ai.LOTREntityAIOrcPlaceBomb;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityMordorOrcBombardier extends LOTREntityMordorOrc {
	public LOTREntityMordorOrcBombardier(World world) {
		super(world);
	}

	@Override
	public EntityAIBase createOrcAttackAI() {
		tasks.addTask(4, new LOTREntityAIOrcPlaceBomb(this, 1.4));
		return new LOTREntityAIAttackOnCollide(this, 1.4, false);
	}

	@Override
	public boolean isOrcBombardier() {
		return true;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		npcItemsInv.setBombingItem(new ItemStack(LOTRMod.orcTorchItem));
		npcItemsInv.setBomb(new ItemStack(LOTRMod.orcBomb, 1, 0));
		setCurrentItemOrArmor(4, new ItemStack(LOTRMod.helmetOrc));
		return data;
	}
}
