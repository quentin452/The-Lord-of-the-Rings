package lotr.common.entity.npc;

import cpw.mods.fml.relauncher.*;
import lotr.common.*;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import lotr.common.fac.LOTRFaction;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class LOTREntityUtumnoTroll extends LOTREntityTroll {
	public LOTREntityUtumnoTroll(World world) {
		super(world);
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(32.0);
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(60.0);
		getEntityAttribute(npcAttackDamage).setBaseValue(7.0);
	}

	@Override
	public boolean canTrollBeTickled(EntityPlayer entityplayer) {
		return false;
	}

	@Override
	public int getExperiencePoints(EntityPlayer entityplayer) {
		return 5 + rand.nextInt(6);
	}

	@Override
	public LOTRFaction getFaction() {
		return LOTRFaction.UTUMNO;
	}

	@Override
	public LOTRAchievement getKillAchievement() {
		return LOTRAchievement.killUtumnoTroll;
	}

	@Override
	public String getSpeechBank(EntityPlayer entityplayer) {
		return null;
	}

	@Override
	public EntityAIBase getTrollAttackAI() {
		return new LOTREntityAIAttackOnCollide(this, 2.0, false);
	}

	@Override
	public float getTrollScale() {
		return 1.5f;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void handleHealthUpdate(byte b) {
		if (b == 15) {
			super.handleHealthUpdate(b);
			for (int l = 0; l < 64; ++l) {
				LOTRMod.proxy.spawnParticle("largeStone", posX + rand.nextGaussian() * width * 0.5, posY + rand.nextDouble() * height, posZ + rand.nextGaussian() * width * 0.5, 0.0, 0.0, 0.0);
			}
		} else {
			super.handleHealthUpdate(b);
		}
	}

	@Override
	public boolean hasTrollName() {
		return false;
	}

	@Override
	public void onTrollDeathBySun() {
		worldObj.playSoundAtEntity(this, "lotr:troll.transform", getSoundVolume(), getSoundPitch());
		worldObj.setEntityState(this, (byte) 15);
		setDead();
	}
}
