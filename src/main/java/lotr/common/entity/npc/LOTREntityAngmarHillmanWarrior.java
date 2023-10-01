package lotr.common.entity.npc;

import lotr.common.LOTRMod;
import lotr.common.LOTRShields;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityAngmarHillmanWarrior extends LOTREntityAngmarHillman {
	public static ItemStack[] weapons = {new ItemStack(LOTRMod.swordAngmar), new ItemStack(LOTRMod.battleaxeAngmar), new ItemStack(LOTRMod.hammerAngmar), new ItemStack(LOTRMod.daggerAngmar), new ItemStack(LOTRMod.polearmAngmar), new ItemStack(LOTRMod.spearAngmar)};
	public static ItemStack[] helmets = {new ItemStack(LOTRMod.helmetBone), new ItemStack(LOTRMod.helmetFur)};
	public static ItemStack[] bodies = {new ItemStack(LOTRMod.bodyAngmar), new ItemStack(LOTRMod.bodyAngmar), new ItemStack(LOTRMod.bodyBone), new ItemStack(LOTRMod.bodyFur)};
	public static ItemStack[] legs = {new ItemStack(LOTRMod.legsAngmar), new ItemStack(LOTRMod.legsAngmar), new ItemStack(LOTRMod.legsBone), new ItemStack(LOTRMod.legsFur)};
	public static ItemStack[] boots = {new ItemStack(LOTRMod.bootsAngmar), new ItemStack(LOTRMod.bootsAngmar), new ItemStack(LOTRMod.bootsBone), new ItemStack(LOTRMod.bootsFur)};

	public LOTREntityAngmarHillmanWarrior(World world) {
		super(world);
		npcShield = LOTRShields.ALIGNMENT_ANGMAR;
	}

	@Override
	public void dropHillmanItems(boolean flag, int i) {
	}

	@Override
	public float getAlignmentBonus() {
		return 2.0f;
	}

	@Override
	public EntityAIBase getHillmanAttackAI() {
		return new LOTREntityAIAttackOnCollide(this, 1.6, false);
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		int i = rand.nextInt(weapons.length);
		npcItemsInv.setMeleeWeapon(weapons[i].copy());
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		i = rand.nextInt(boots.length);
		setCurrentItemOrArmor(1, boots[i].copy());
		i = rand.nextInt(legs.length);
		setCurrentItemOrArmor(2, legs[i].copy());
		i = rand.nextInt(bodies.length);
		setCurrentItemOrArmor(3, bodies[i].copy());
		if (rand.nextInt(5) != 0) {
			i = rand.nextInt(helmets.length);
			setCurrentItemOrArmor(4, helmets[i].copy());
		}
		return data;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}
}
