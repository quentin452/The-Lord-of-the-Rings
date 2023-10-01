package lotr.common.entity.npc;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRMod;
import lotr.common.fac.LOTRFaction;
import net.minecraft.world.World;

public class LOTREntityMirkwoodSpider extends LOTREntitySpiderBase {
	public LOTREntityMirkwoodSpider(World world) {
		super(world);
	}

	@Override
	public void dropFewItems(boolean flag, int i) {
		super.dropFewItems(flag, i);
		if (flag && rand.nextInt(4) == 0) {
			dropItem(LOTRMod.mysteryWeb, 1);
		}
	}

	@Override
	public float getAlignmentBonus() {
		return 1.0f;
	}

	@Override
	public LOTRFaction getFaction() {
		return LOTRFaction.DOL_GULDUR;
	}

	@Override
	public LOTRAchievement getKillAchievement() {
		return LOTRAchievement.killMirkwoodSpider;
	}

	@Override
	public int getRandomSpiderScale() {
		return rand.nextInt(3);
	}

	@Override
	public int getRandomSpiderType() {
		return rand.nextBoolean() ? 0 : 1 + rand.nextInt(2);
	}
}
