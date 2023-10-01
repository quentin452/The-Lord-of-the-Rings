package lotr.common.entity.npc;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRMod;
import lotr.common.entity.animal.LOTREntityHorse;
import lotr.common.quest.LOTRMiniQuest;
import lotr.common.quest.LOTRMiniQuestFactory;
import lotr.common.world.biome.LOTRBiomeGenLindon;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class LOTREntityHighElf extends LOTREntityHighElfBase {
	public LOTREntityHighElf(World world) {
		super(world);
	}

	@Override
	public LOTRMiniQuest createMiniQuest() {
		return LOTRMiniQuestFactory.HIGH_ELF.createQuest(this);
	}

	@Override
	public LOTRNPCMount createMountToRide() {
		LOTREntityHorse horse = (LOTREntityHorse) super.createMountToRide();
		horse.setMountArmor(new ItemStack(LOTRMod.horseArmorHighElven));
		return horse;
	}

	@Override
	public void dropElfItems(boolean flag, int i) {
		super.dropElfItems(flag, i);
		if (rand.nextInt(6) == 0) {
			dropChestContents(LOTRChestContents.HIGH_ELVEN_HALL, 1, 1 + i);
		}
	}

	@Override
	public float getBlockPathWeight(int i, int j, int k) {
		float f = 0.0f;
		BiomeGenBase biome = worldObj.getBiomeGenForCoords(i, k);
		if (biome instanceof LOTRBiomeGenLindon) {
			f += 20.0f;
		}
		return f;
	}

	@Override
	public LOTRMiniQuestFactory getBountyHelpSpeechDir() {
		return LOTRMiniQuestFactory.HIGH_ELF;
	}

	@Override
	public LOTRAchievement getKillAchievement() {
		return LOTRAchievement.killHighElf;
	}

	@Override
	public String getSpeechBank(EntityPlayer entityplayer) {
		if (isFriendly(entityplayer)) {
			if (hiredNPCInfo.getHiringPlayer() == entityplayer) {
				return "highElf/elf/hired";
			}
			return "highElf/elf/friendly";
		}
		return "highElf/elf/hostile";
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerHighElven));
		npcItemsInv.setRangedWeapon(new ItemStack(LOTRMod.highElvenBow));
		npcItemsInv.setIdleItem(null);
		return data;
	}
}
