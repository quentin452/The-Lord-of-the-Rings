package lotr.common.entity.npc;

import lotr.common.LOTRMod;
import lotr.common.entity.animal.LOTREntityHorse;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityDolAmrothSoldier extends LOTREntityGondorLevyman {
	public static ItemStack[] manAtArmsWeapons = {new ItemStack(LOTRMod.swordDolAmroth), new ItemStack(LOTRMod.swordDolAmroth), new ItemStack(LOTRMod.swordGondor), new ItemStack(Items.iron_sword)};

	public LOTREntityDolAmrothSoldier(World world) {
		super(world);
		spawnRidingHorse = rand.nextInt(6) == 0;
	}

	@Override
	public LOTRNPCMount createMountToRide() {
		LOTREntityHorse horse = (LOTREntityHorse) super.createMountToRide();
		horse.setMountArmor(new ItemStack(LOTRMod.horseArmorDolAmroth));
		return horse;
	}

	@Override
	public String getSpeechBank(EntityPlayer entityplayer) {
		if (isFriendly(entityplayer)) {
			if (hiredNPCInfo.getHiringPlayer() == entityplayer) {
				return "gondor/swanKnight/hired";
			}
			return "gondor/swanKnight/friendly";
		}
		return "gondor/swanKnight/hostile";
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		int i = rand.nextInt(manAtArmsWeapons.length);
		npcItemsInv.setMeleeWeapon(manAtArmsWeapons[i].copy());
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		setCurrentItemOrArmor(1, new ItemStack(Items.leather_boots));
		setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsDolAmrothGambeson));
		setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyDolAmrothGambeson));
		if (rand.nextInt(3) == 0) {
			setCurrentItemOrArmor(4, null);
		} else {
			i = rand.nextInt(3);
			switch (i) {
				case 0:
					setCurrentItemOrArmor(4, new ItemStack(LOTRMod.helmetDolAmroth));
					break;
				case 1:
					setCurrentItemOrArmor(4, new ItemStack(Items.iron_helmet));
					break;
				case 2:
					setCurrentItemOrArmor(4, new ItemStack(Items.leather_helmet));
					break;
				default:
					break;
			}
		}
		return data;
	}
}
