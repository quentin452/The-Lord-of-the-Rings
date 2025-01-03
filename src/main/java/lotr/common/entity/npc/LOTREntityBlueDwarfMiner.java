package lotr.common.entity.npc;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityBlueDwarfMiner extends LOTREntityBlueDwarf implements LOTRTradeable {
	public LOTREntityBlueDwarfMiner(World world) {
		super(world);
	}

	@Override
	public boolean canDwarfSpawnAboveGround() {
		return false;
	}

	@Override
	public boolean canTradeWith(EntityPlayer entityplayer) {
		return LOTRLevelData.getData(entityplayer).getAlignment(getFaction()) >= 100.0f && isFriendly(entityplayer);
	}

	@Override
	public void dropFewItems(boolean flag, int i) {
		super.dropFewItems(flag, i);
		if (flag) {
			if (rand.nextInt(4) == 0) {
				dropChestContents(LOTRChestContents.DWARVEN_MINE_CORRIDOR, 1, 2 + i);
			}
			if (rand.nextInt(15) == 0) {
				entityDropItem(new ItemStack(LOTRMod.mithrilNugget), 0.0f);
			}
		}
	}

	@Override
	public float getAlignmentBonus() {
		return 2.0f;
	}

	@Override
	public LOTRTradeEntries getBuyPool() {
		return LOTRTradeEntries.BLUE_DWARF_MINER_BUY;
	}

	@Override
	public LOTRTradeEntries getSellPool() {
		return LOTRTradeEntries.BLUE_DWARF_MINER_SELL;
	}

	@Override
	public String getSpeechBank(EntityPlayer entityplayer) {
		if (isFriendly(entityplayer)) {
			if (canTradeWith(entityplayer)) {
				return "blueDwarf/miner/friendly";
			}
			return "blueDwarf/miner/neutral";
		}
		return "blueDwarf/dwarf/hostile";
	}

	@Override
	public void onPlayerTrade(EntityPlayer entityplayer, LOTRTradeEntries.TradeType type, ItemStack itemstack) {
		LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.tradeBlueDwarfMiner);
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.pickaxeBlueDwarven));
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		return data;
	}

	@Override
	public boolean shouldTraderRespawn() {
		return false;
	}
}
