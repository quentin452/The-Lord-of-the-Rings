package lotr.client.fx;

import lotr.common.fac.LOTRAlignmentBonusMap;
import lotr.common.fac.LOTRFaction;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class LOTREntityAlignmentBonus extends Entity {
	public int particleAge;
	public int particleMaxAge;
	public String name;
	public LOTRFaction mainFaction;
	public float prevMainAlignment;
	public LOTRAlignmentBonusMap factionBonusMap;
	public boolean isKill;
	public boolean isHiredKill;
	public float conquestBonus;

	public LOTREntityAlignmentBonus(World world, double d, double d1, double d2, String s, LOTRFaction f, float pre, LOTRAlignmentBonusMap fMap, boolean kill, boolean hiredKill, float conqBonus) {
		super(world);
		setSize(0.5f, 0.5f);
		yOffset = height / 2.0f;
		setPosition(d, d1, d2);
		particleAge = 0;
		name = s;
		mainFaction = f;
		prevMainAlignment = pre;
		factionBonusMap = fMap;
		isKill = kill;
		isHiredKill = hiredKill;
		conquestBonus = conqBonus;
		calcMaxAge();
	}

	public void calcMaxAge() {
		float highestBonus = 0.0f;
		for (LOTRFaction fac : factionBonusMap.getChangedFactions()) {
			float bonus = Math.abs(factionBonusMap.get(fac));
			if (bonus <= highestBonus) {
				continue;
			}
			highestBonus = bonus;
		}
		float conq = Math.abs(conquestBonus);
		if (conq > highestBonus) {
			highestBonus = conq;
		}
		particleMaxAge = 80;
		int extra = (int) (Math.min(1.0f, highestBonus / 50.0f) * 220.0f);
		particleMaxAge += extra;
	}

	@Override
	public boolean canTriggerWalking() {
		return false;
	}

	@Override
	public void entityInit() {
	}

	@Override
	public boolean isEntityInvulnerable() {
		return true;
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		++particleAge;
		if (particleAge >= particleMaxAge) {
			setDead();
		}
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
	}
}
