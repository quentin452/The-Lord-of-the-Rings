package lotr.common.entity.npc;

import lotr.common.LOTRMod;
import lotr.common.LOTRShields;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import lotr.common.util.LOTRColorUtil;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityBreeGuard extends LOTREntityBreeMan {
	public static ItemStack[] guardWeapons = {new ItemStack(Items.iron_sword), new ItemStack(Items.iron_sword), new ItemStack(LOTRMod.pikeIron)};
	public static int[] leatherDyes = {11373426, 7823440, 5983041, 9535090};

	public LOTREntityBreeGuard(World world) {
		super(world);
		addTargetTasks(true);
		npcShield = LOTRShields.ALIGNMENT_BREE;
	}

	@Override
	public int addBreeAttackAI(int prio) {
		tasks.addTask(prio, new LOTREntityAIAttackOnCollide(this, 1.45, false));
		return prio;
	}

	@Override
	public void addBreeAvoidAI(int prio) {
	}

	@Override
	public float getAlignmentBonus() {
		return 2.0f;
	}

	@Override
	public String getSpeechBank(EntityPlayer entityplayer) {
		if (isFriendlyAndAligned(entityplayer)) {
			if (hiredNPCInfo.getHiringPlayer() == entityplayer) {
				return "bree/guard/hired";
			}
			return "bree/guard/friendly";
		}
		return "bree/guard/hostile";
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		int i = rand.nextInt(guardWeapons.length);
		npcItemsInv.setMeleeWeapon(guardWeapons[i].copy());
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		if (rand.nextInt(3) == 0) {
			setCurrentItemOrArmor(1, new ItemStack(Items.chainmail_boots));
		} else {
			setCurrentItemOrArmor(1, LOTRColorUtil.dyeLeather(new ItemStack(Items.leather_boots), 3354152));
		}
		if (rand.nextInt(3) == 0) {
			setCurrentItemOrArmor(2, new ItemStack(Items.chainmail_leggings));
		} else {
			setCurrentItemOrArmor(2, LOTRColorUtil.dyeLeather(new ItemStack(Items.leather_leggings), leatherDyes, rand));
		}
		if (rand.nextInt(3) == 0) {
			setCurrentItemOrArmor(3, new ItemStack(Items.chainmail_chestplate));
		} else {
			setCurrentItemOrArmor(3, LOTRColorUtil.dyeLeather(new ItemStack(Items.leather_chestplate), leatherDyes, rand));
		}
		setCurrentItemOrArmor(4, new ItemStack(Items.iron_helmet));
		return data;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}
}
