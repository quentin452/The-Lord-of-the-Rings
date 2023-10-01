package lotr.common.entity.npc;

import lotr.common.LOTRMod;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityUrukHaiBerserker extends LOTREntityUrukHai {
	public static float BERSERKER_SCALE = 1.15f;

	public LOTREntityUrukHaiBerserker(World world) {
		super(world);
		setSize(npcWidth * BERSERKER_SCALE, npcHeight * BERSERKER_SCALE);
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(30.0);
		getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(24.0);
		getEntityAttribute(npcAttackDamageExtra).setBaseValue(2.0);
	}

	@Override
	public EntityAIBase createOrcAttackAI() {
		return new LOTREntityAIAttackOnCollide(this, 1.6, false);
	}

	@Override
	public float getSoundPitch() {
		return super.getSoundPitch() * 0.8f;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.scimitarUrukBerserker));
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		setCurrentItemOrArmor(4, new ItemStack(LOTRMod.helmetUrukBerserker));
		setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyFur));
		setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsFur));
		setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsFur));
		return data;
	}
}
