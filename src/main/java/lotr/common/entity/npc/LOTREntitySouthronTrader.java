package lotr.common.entity.npc;

import java.awt.Color;
import java.util.Random;

import lotr.common.*;
import lotr.common.item.*;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public abstract class LOTREntitySouthronTrader extends LOTREntityNearHaradrim implements LOTRTradeable {
	protected LOTREntitySouthronTrader(World world) {
		super(world);
		addTargetTasks(false);
	}

	@Override
	public boolean canTradeWith(EntityPlayer entityplayer) {
		return LOTRLevelData.getData(entityplayer).getAlignment(getFaction()) >= 0.0f && isFriendly(entityplayer);
	}

	@Override
	public float getAlignmentBonus() {
		return 2.0f;
	}

	@Override
	public String getSpeechBank(EntityPlayer entityplayer) {
		if (isFriendly(entityplayer)) {
			return "nearHarad/coast/bazaarTrader/friendly";
		}
		return "nearHarad/coast/bazaarTrader/hostile";
	}

	@Override
	public void onPlayerTrade(EntityPlayer entityplayer, LOTRTradeEntries.TradeType type, ItemStack itemstack) {
		LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.tradeBazaarTrader);
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		setCurrentItemOrArmor(4, createTraderTurban(rand));
		return data;
	}

	@Override
	public boolean shouldTraderRespawn() {
		return true;
	}

	public static ItemStack createTraderTurban(Random random) {
		ItemStack turban = new ItemStack(LOTRMod.helmetHaradRobes);
		if (random.nextInt(3) == 0) {
			LOTRItemHaradTurban.setHasOrnament(turban, true);
		}
		float h = random.nextFloat() * 360.0f;
		float s = MathHelper.randomFloatClamp(random, 0.6f, 0.8f);
		float b = MathHelper.randomFloatClamp(random, 0.5f, 0.75f);
		int turbanColor = Color.HSBtoRGB(h, s, b) & 0xFFFFFF;
		LOTRItemHaradRobes.setRobesColor(turban, turbanColor);
		return turban;
	}
}
