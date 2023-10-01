package lotr.common.block;

import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.fac.LOTRFaction;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class LOTRBlockHithlainRope extends LOTRBlockRope {
	public LOTRBlockHithlainRope() {
		super(true);
		setLightLevel(0.375f);
	}

	@Override
	public void onEntityCollidedWithBlock(World world, int i, int j, int k, Entity entity) {
		if (entity instanceof EntityLivingBase && ((EntityLivingBase) entity).isOnLadder()) {
			LOTRFaction ropeFaction = LOTRFaction.LOTHLORIEN;
			boolean harm;
			harm = entity instanceof EntityPlayer ? LOTRLevelData.getData((EntityPlayer) entity).getAlignment(ropeFaction) < 0.0f : LOTRMod.getNPCFaction(entity).isBadRelation(ropeFaction);
			if (harm) {
				entity.attackEntityFrom(DamageSource.magic, 1.0f);
			}
		}
	}
}
