package lotr.common.entity.npc;

import lotr.common.*;
import lotr.common.entity.animal.LOTREntityHorse;
import lotr.common.fac.LOTRFaction;
import lotr.common.item.LOTRItemMug;
import lotr.common.quest.*;
import lotr.common.world.biome.LOTRBiomeGenLothlorien;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class LOTREntityGaladhrimElf extends LOTREntityElf {
	public LOTREntityGaladhrimElf(World world) {
		super(world);
	}

	@Override
	public boolean canElfSpawnHere() {
		int i = MathHelper.floor_double(posX);
		int j = MathHelper.floor_double(boundingBox.minY);
		int k = MathHelper.floor_double(posZ);
		BiomeGenBase biome = worldObj.getBiomeGenForCoords(i, k);
		return j > 62 && worldObj.getBlock(i, j - 1, k) == biome.topBlock;
	}

	@Override
	public LOTRMiniQuest createMiniQuest() {
		return LOTRMiniQuestFactory.GALADHRIM.createQuest(this);
	}

	@Override
	public LOTRNPCMount createMountToRide() {
		LOTREntityHorse horse = (LOTREntityHorse) super.createMountToRide();
		horse.setMountArmor(new ItemStack(LOTRMod.horseArmorGaladhrim));
		return horse;
	}

	@Override
	public void dropElfItems(boolean flag, int i) {
		super.dropElfItems(flag, i);
		if (flag) {
			int dropChance = 20 - i * 4;
			if (rand.nextInt(Math.max(dropChance, 1)) == 0) {
				ItemStack elfDrink = new ItemStack(LOTRMod.mugMiruvor);
				elfDrink.setItemDamage(1 + rand.nextInt(3));
				LOTRItemMug.setVessel(elfDrink, LOTRFoods.ELF_DRINK.getRandomVessel(rand), true);
				entityDropItem(elfDrink, 0.0f);
			}
		}
		if (rand.nextInt(6) == 0) {
			dropChestContents(LOTRChestContents.ELF_HOUSE, 1, 1 + i);
		}
	}

	@Override
	public float getAlignmentBonus() {
		return 1.0f;
	}

	@Override
	public float getBlockPathWeight(int i, int j, int k) {
		float f = 0.0f;
		BiomeGenBase biome = worldObj.getBiomeGenForCoords(i, k);
		if (biome instanceof LOTRBiomeGenLothlorien) {
			f += 20.0f;
		}
		return f;
	}

	@Override
	public LOTRMiniQuestFactory getBountyHelpSpeechDir() {
		return LOTRMiniQuestFactory.GALADHRIM;
	}

	@Override
	public LOTRFaction getFaction() {
		return LOTRFaction.LOTHLORIEN;
	}

	@Override
	public LOTRAchievement getKillAchievement() {
		return LOTRAchievement.killElf;
	}

	@Override
	public String getSpeechBank(EntityPlayer entityplayer) {
		if (isFriendly(entityplayer)) {
			if (hiredNPCInfo.getHiringPlayer() == entityplayer) {
				return "galadhrim/elf/hired";
			}
			return "galadhrim/elf/friendly";
		}
		return "galadhrim/elf/hostile";
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerElven));
		npcItemsInv.setRangedWeapon(new ItemStack(LOTRMod.mallornBow));
		npcItemsInv.setIdleItem(null);
		return data;
	}

}
