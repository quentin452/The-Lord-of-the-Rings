package lotr.common.entity.npc;

import lotr.common.*;
import lotr.common.fac.LOTRFaction;
import lotr.common.quest.*;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LOTREntityWickedDwarf extends LOTREntityDwarf implements LOTRTradeable.Smith {
	public static ItemStack[] wickedWeapons = {new ItemStack(LOTRMod.swordDwarven), new ItemStack(LOTRMod.battleaxeDwarven), new ItemStack(LOTRMod.hammerDwarven)};

	public LOTREntityWickedDwarf(World world) {
		super(world);
		addTargetTasks(true);
	}

	@Override
	public boolean canDwarfSpawnHere() {
		int i = MathHelper.floor_double(posX);
		int j = MathHelper.floor_double(boundingBox.minY);
		int k = MathHelper.floor_double(posZ);
		return j > 62 && worldObj.getBlock(i, j - 1, k) == worldObj.getBiomeGenForCoords(i, k).topBlock;
	}

	@Override
	public boolean canTradeWith(EntityPlayer entityplayer) {
		boolean hasSuitableAlignment = false;
		for (LOTRFaction f : getTradeFactions()) {
			if (LOTRLevelData.getData(entityplayer).getAlignment(f) < 100.0f) {
				continue;
			}
			hasSuitableAlignment = true;
			break;
		}
		return hasSuitableAlignment && isFriendly(entityplayer);
	}

	@Override
	public LOTRMiniQuest createMiniQuest() {
		return null;
	}

	@Override
	public float getAlignmentBonus() {
		return 2.0f;
	}

	@Override
	public LOTRMiniQuestFactory getBountyHelpSpeechDir() {
		return null;
	}

	@Override
	public LOTRTradeEntries getBuyPool() {
		return LOTRTradeEntries.WICKED_DWARF_BUY;
	}

	@Override
	public LOTRFaction getFaction() {
		return LOTRFaction.MORDOR;
	}

	@Override
	public LOTRAchievement getKillAchievement() {
		return LOTRAchievement.killWickedDwarf;
	}

	@Override
	public LOTRTradeEntries getSellPool() {
		return LOTRTradeEntries.WICKED_DWARF_SELL;
	}

	@Override
	public String getSpeechBank(EntityPlayer entityplayer) {
		if (isFriendlyAndAligned(entityplayer)) {
			if (canTradeWith(entityplayer)) {
				return "dwarf/wicked/friendly";
			}
			return "dwarf/wicked/neutral";
		}
		return "dwarf/wicked/hostile";
	}

	@Override
	public void onPlayerTrade(EntityPlayer entityplayer, LOTRTradeEntries.TradeType type, ItemStack itemstack) {
		LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.tradeWickedDwarf);
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		int i = rand.nextInt(wickedWeapons.length);
		npcItemsInv.setMeleeWeapon(wickedWeapons[i].copy());
		npcItemsInv.setIdleItem(new ItemStack(LOTRMod.pickaxeDwarven));
		if (rand.nextInt(4) == 0) {
			setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsDwarven));
			setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsDwarven));
			setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyDwarven));
		} else {
			setCurrentItemOrArmor(1, null);
			setCurrentItemOrArmor(2, null);
			setCurrentItemOrArmor(3, null);
		}
		setCurrentItemOrArmor(4, null);
		return data;
	}

	public static LOTRFaction[] getTradeFactions() {
		return new LOTRFaction[]{LOTRFaction.MORDOR, LOTRFaction.ANGMAR, LOTRFaction.RHUDEL};
	}
}
