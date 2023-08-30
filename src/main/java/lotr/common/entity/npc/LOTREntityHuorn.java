package lotr.common.entity.npc;

import lotr.common.LOTRAchievement;
import lotr.common.entity.ai.LOTREntityAINearestAttackableTargetHuorn;
import lotr.common.fac.LOTRFaction;
import net.minecraft.world.World;

public class LOTREntityHuorn extends LOTREntityHuornBase {
	public LOTREntityHuorn(World world) {
		super(world);
		addTargetTasks(true, LOTREntityAINearestAttackableTargetHuorn.class);
	}

	@Override
	public float getAlignmentBonus() {
		return 2.0f;
	}

	@Override
	public LOTRFaction getFaction() {
		return LOTRFaction.FANGORN;
	}

	@Override
	public LOTRAchievement getKillAchievement() {
		return LOTRAchievement.killHuorn;
	}
}
