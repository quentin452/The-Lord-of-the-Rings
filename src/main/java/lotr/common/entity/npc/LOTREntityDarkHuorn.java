package lotr.common.entity.npc;

import lotr.common.LOTRAchievement;
import lotr.common.entity.ai.LOTREntityAINearestAttackableTargetHuorn;
import lotr.common.fac.LOTRFaction;
import lotr.common.world.biome.LOTRBiomeGenOldForest;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class LOTREntityDarkHuorn extends LOTREntityHuornBase {
	public LOTREntityDarkHuorn(World world) {
		super(world);
		addTargetTasks(true, LOTREntityAINearestAttackableTargetHuorn.class);
	}

	@Override
	public void entityInit() {
		super.entityInit();
		setTreeType(0);
	}

	@Override
	public float getAlignmentBonus() {
		return 1.0f;
	}

	@Override
	public LOTRFaction getFaction() {
		return LOTRFaction.DARK_HUORN;
	}

	@Override
	public LOTRAchievement getKillAchievement() {
		return LOTRAchievement.killDarkHuorn;
	}

	@Override
	public boolean isTreeHomeBiome(BiomeGenBase biome) {
		return biome instanceof LOTRBiomeGenOldForest;
	}
}
