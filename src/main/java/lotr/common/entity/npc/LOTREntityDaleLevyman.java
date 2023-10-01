package lotr.common.entity.npc;

import lotr.common.LOTRMod;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityDaleLevyman extends LOTREntityDaleMan {
	public static ItemStack[] militiaWeapons = {new ItemStack(LOTRMod.swordDale), new ItemStack(LOTRMod.battleaxeDale), new ItemStack(LOTRMod.pikeDale), new ItemStack(Items.iron_sword), new ItemStack(Items.iron_axe), new ItemStack(LOTRMod.battleaxeIron), new ItemStack(LOTRMod.pikeIron), new ItemStack(LOTRMod.swordBronze), new ItemStack(LOTRMod.axeBronze), new ItemStack(LOTRMod.battleaxeBronze)};
	public static int[] leatherDyes = {7034184, 5650986, 7039851, 5331051, 2305612, 2698291, 1973790};

	public LOTREntityDaleLevyman(World world) {
		super(world);
		addTargetTasks(true);
	}

	@Override
	public EntityAIBase createDaleAttackAI() {
		return new LOTREntityAIAttackOnCollide(this, 1.4, true);
	}

	public ItemStack dyeLeather(ItemStack itemstack) {
		int i = rand.nextInt(leatherDyes.length);
		int color = leatherDyes[i];
		((ItemArmor) itemstack.getItem()).func_82813_b(itemstack, color);
		return itemstack;
	}

	@Override
	public float getAlignmentBonus() {
		return 2.0f;
	}

	@Override
	public String getSpeechBank(EntityPlayer entityplayer) {
		if (isFriendly(entityplayer)) {
			if (hiredNPCInfo.getHiringPlayer() == entityplayer) {
				return "dale/soldier/hired";
			}
			return "dale/soldier/friendly";
		}
		return "dale/soldier/hostile";
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		int i = rand.nextInt(militiaWeapons.length);
		npcItemsInv.setMeleeWeapon(militiaWeapons[i].copy());
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		setCurrentItemOrArmor(1, dyeLeather(new ItemStack(Items.leather_boots)));
		setCurrentItemOrArmor(2, dyeLeather(new ItemStack(Items.leather_leggings)));
		setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyDaleGambeson));
		if (rand.nextInt(3) == 0) {
			i = rand.nextInt(3);
			switch (i) {
				case 0:
					setCurrentItemOrArmor(4, new ItemStack(LOTRMod.helmetDale));
					break;
				case 1:
					setCurrentItemOrArmor(4, new ItemStack(Items.iron_helmet));
					break;
				case 2:
					setCurrentItemOrArmor(4, dyeLeather(new ItemStack(Items.leather_helmet)));
					break;
				default:
					break;
			}
		} else {
			setCurrentItemOrArmor(4, null);
		}
		return data;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}
}
