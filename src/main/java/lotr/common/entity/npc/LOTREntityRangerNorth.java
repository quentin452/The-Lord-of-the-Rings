package lotr.common.entity.npc;

import lotr.common.*;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityRangerNorth extends LOTREntityRanger {
	public LOTREntityRangerNorth(World world) {
		super(world);
		spawnRidingHorse = rand.nextInt(20) == 0;
	}

	@Override
	public float getAlignmentBonus() {
		return 2.0f;
	}

	@Override
	public LOTRAchievement getKillAchievement() {
		return LOTRAchievement.killRangerNorth;
	}

	@Override
	public String getSpeechBank(EntityPlayer entityplayer) {
		if (isFriendly(entityplayer)) {
			if (hiredNPCInfo.getHiringPlayer() == entityplayer) {
				return "rangerNorth/ranger/hired";
			}
			return "rangerNorth/ranger/friendly";
		}
		return "rangerNorth/ranger/hostile";
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		int i = rand.nextInt(5);
		switch (i) {
			case 0:
			case 1:
				npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerIron));
				break;
			case 2:
				npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerBronze));
				break;
			case 3:
				npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerBarrow));
				break;
			default:
				break;
		}
		int r = rand.nextInt(2);
		if (r == 0) {
			npcItemsInv.setRangedWeapon(new ItemStack(Items.bow));
		} else {
			npcItemsInv.setRangedWeapon(new ItemStack(LOTRMod.rangerBow));
		}
		return data;
	}
}
