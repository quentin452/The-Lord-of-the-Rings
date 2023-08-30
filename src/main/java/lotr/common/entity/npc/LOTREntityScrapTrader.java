package lotr.common.entity.npc;

import java.awt.Color;

import lotr.common.*;
import lotr.common.entity.ai.*;
import lotr.common.item.LOTRItemLeatherHat;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class LOTREntityScrapTrader extends LOTREntityMan implements LOTRTravellingTrader, LOTRTradeable.Smith {
	public static int maxFadeoutTick = 60;
	public int timeUntilFadeOut;

	public LOTREntityScrapTrader(World world) {
		super(world);
		setSize(0.6f, 1.8f);
		getNavigator().setAvoidsWater(true);
		getNavigator().setBreakDoors(true);
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(1, new LOTREntityAIAttackOnCollide(this, 1.3, true));
		tasks.addTask(2, new EntityAIOpenDoor(this, true));
		tasks.addTask(3, new EntityAIWander(this, 1.0));
		tasks.addTask(4, new LOTREntityAIEat(this, LOTRFoods.DUNLENDING, 8000));
		tasks.addTask(4, new LOTREntityAIDrink(this, LOTRFoods.DUNLENDING_DRINK, 8000));
		tasks.addTask(5, new EntityAIWatchClosest2(this, EntityPlayer.class, 10.0f, 0.1f));
		tasks.addTask(5, new EntityAIWatchClosest2(this, LOTREntityNPC.class, 5.0f, 0.05f));
		tasks.addTask(6, new EntityAIWatchClosest(this, EntityLiving.class, 8.0f, 0.02f));
		tasks.addTask(7, new EntityAILookIdle(this));
		addTargetTasks(false);
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.2);
	}

	@Override
	public boolean attackEntityFrom(DamageSource damagesource, float f) {
		if (damagesource.getEntity() != null && LOTRDimension.getCurrentDimension(worldObj) == LOTRDimension.UTUMNO) {
			if (!worldObj.isRemote && getFadeoutTick() < 0) {
				setFadeoutTick(60);
			}
			return false;
		}
		return super.attackEntityFrom(damagesource, f);
	}

	@Override
	public boolean canBeFreelyTargetedBy(EntityLiving attacker) {
		if (LOTRDimension.getCurrentDimension(worldObj) == LOTRDimension.UTUMNO) {
			return false;
		}
		return super.canBeFreelyTargetedBy(attacker);
	}

	@Override
	public boolean canTradeWith(EntityPlayer entityplayer) {
		return isFriendly(entityplayer) && LOTRDimension.getCurrentDimension(worldObj) != LOTRDimension.UTUMNO;
	}

	@Override
	public LOTREntityNPC createTravellingEscort() {
		return null;
	}

	@Override
	public void dropFewItems(boolean flag, int i) {
		super.dropFewItems(flag, i);
		int bones = rand.nextInt(2) + rand.nextInt(i + 1);
		for (int l = 0; l < bones; ++l) {
			dropItem(Items.bone, 1);
		}
	}

	@Override
	public void entityInit() {
		super.entityInit();
		dataWatcher.addObject(20, -1);
	}

	@Override
	public LOTRTradeEntries getBuyPool() {
		return LOTRTradeEntries.SCRAP_TRADER_BUY;
	}

	@Override
	public String getDepartureSpeech() {
		return "misc/scrapTrader/departure";
	}

	public float getFadeoutProgress(float f) {
		int i = getFadeoutTick();
		if (i >= 0) {
			return (60 - i + f) / 60.0f;
		}
		return 0.0f;
	}

	public int getFadeoutTick() {
		return dataWatcher.getWatchableObjectInt(20);
	}

	@Override
	public String getNPCName() {
		return familyInfo.getName();
	}

	@Override
	public LOTRTradeEntries getSellPool() {
		return LOTRTradeEntries.SCRAP_TRADER_SELL;
	}

	public String getSmithSpeechBank() {
		return "misc/scrapTrader/smith";
	}

	@Override
	public String getSpeechBank(EntityPlayer entityplayer) {
		if (isFriendly(entityplayer)) {
			if (LOTRDimension.getCurrentDimension(worldObj) == LOTRDimension.UTUMNO) {
				return "misc/scrapTrader/utumno";
			}
			return "misc/scrapTrader/friendly";
		}
		return "misc/scrapTrader/hostile";
	}

	@Override
	public int getTotalArmorValue() {
		return 5;
	}

	@Override
	public boolean interact(EntityPlayer entityplayer) {
		boolean flag = super.interact(entityplayer);
		if (flag && !worldObj.isRemote && LOTRDimension.getCurrentDimension(worldObj) == LOTRDimension.UTUMNO && timeUntilFadeOut <= 0) {
			timeUntilFadeOut = 100;
		}
		return flag;
	}

	@Override
	public void onAttackModeChange(LOTREntityNPC.AttackMode mode, boolean mounted) {
		if (mode == LOTREntityNPC.AttackMode.IDLE) {
			setCurrentItemOrArmor(0, npcItemsInv.getIdleItem());
		} else {
			setCurrentItemOrArmor(0, npcItemsInv.getMeleeWeapon());
		}
	}

	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		if (!worldObj.isRemote && LOTRDimension.getCurrentDimension(worldObj) == LOTRDimension.UTUMNO) {
			if (timeUntilFadeOut > 0) {
				--timeUntilFadeOut;
				if (timeUntilFadeOut <= 0) {
					setFadeoutTick(60);
				}
			}
			if (getFadeoutTick() > 0) {
				setFadeoutTick(getFadeoutTick() - 1);
				if (getFadeoutTick() <= 0) {
					setDead();
				}
			}
		}
	}

	@Override
	public void onPlayerTrade(EntityPlayer entityplayer, LOTRTradeEntries.TradeType type, ItemStack itemstack) {
		LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.tradeScrapTrader);
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		int weapon = rand.nextInt(2);
		if (weapon == 0) {
			npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerIron));
		} else {
			npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerBronze));
		}
		npcItemsInv.setIdleItem(new ItemStack(LOTRMod.shireHeather));
		ItemStack hat = new ItemStack(LOTRMod.leatherHat);
		float h = 0.06111111f;
		float s = MathHelper.randomFloatClamp(rand, 0.0f, 0.5f);
		float b = MathHelper.randomFloatClamp(rand, 0.0f, 0.5f);
		int hatColor = Color.HSBtoRGB(h, s, b) & 0xFFFFFF;
		LOTRItemLeatherHat.setHatColor(hat, hatColor);
		if (rand.nextInt(3) == 0) {
			h = rand.nextFloat();
			s = MathHelper.randomFloatClamp(rand, 0.7f, 0.9f);
			b = MathHelper.randomFloatClamp(rand, 0.8f, 1.0f);
		} else {
			h = 0.0f;
			s = 0.0f;
			b = rand.nextFloat();
		}
		int featherColor = Color.HSBtoRGB(h, s, b) & 0xFFFFFF;
		LOTRItemLeatherHat.setFeatherColor(hat, featherColor);
		setCurrentItemOrArmor(4, hat);
		return data;
	}

	public void setFadeoutTick(int i) {
		dataWatcher.updateObject(20, i);
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}

	@Override
	public void setupNPCName() {
		int i = rand.nextInt(3);
		switch (i) {
			case 0:
				familyInfo.setName(LOTRNames.getDunlendingName(rand, familyInfo.isMale()));
				break;
			case 1:
				familyInfo.setName(LOTRNames.getRohirricName(rand, familyInfo.isMale()));
				break;
			case 2:
				familyInfo.setName(LOTRNames.getGondorName(rand, familyInfo.isMale()));
				break;
			default:
				break;
		}
	}

	@Override
	public boolean shouldTraderRespawn() {
		return false;
	}
}
