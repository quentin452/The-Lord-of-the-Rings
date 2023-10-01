package lotr.common.entity.npc;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRCapes;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.world.spawning.LOTRInvasions;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityBlackrootCaptain extends LOTREntityBlackrootArcher implements LOTRUnitTradeable {
	public LOTREntityBlackrootCaptain(World world) {
		super(world);
		addTargetTasks(false);
		npcCape = LOTRCapes.BLACKROOT;
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(25.0);
	}

	@Override
	public boolean canTradeWith(EntityPlayer entityplayer) {
		return LOTRLevelData.getData(entityplayer).getAlignment(getFaction()) >= 150.0f && isFriendly(entityplayer);
	}

	@Override
	public float getAlignmentBonus() {
		return 5.0f;
	}

	@Override
	public String getSpeechBank(EntityPlayer entityplayer) {
		if (isFriendly(entityplayer)) {
			if (canTradeWith(entityplayer)) {
				return "gondor/blackrootCaptain/friendly";
			}
			return "gondor/blackrootCaptain/neutral";
		}
		return "gondor/soldier/hostile";
	}

	@Override
	public LOTRUnitTradeEntries getUnits() {
		return LOTRUnitTradeEntries.BLACKROOT_CAPTAIN;
	}

	@Override
	public LOTRInvasions getWarhorn() {
		return LOTRInvasions.GONDOR_BLACKROOT;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		npcItemsInv.setRangedWeapon(new ItemStack(LOTRMod.blackrootBow));
		npcItemsInv.setIdleItem(npcItemsInv.getRangedWeapon());
		setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsBlackroot));
		setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsBlackroot));
		setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyBlackroot));
		setCurrentItemOrArmor(4, null);
		return data;
	}

	@Override
	public void onUnitTrade(EntityPlayer entityplayer) {
		LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.tradeBlackrootCaptain);
	}

	@Override
	public boolean shouldTraderRespawn() {
		return true;
	}
}
