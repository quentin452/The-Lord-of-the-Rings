package lotr.common.entity.npc;

import lotr.common.LOTRDamage;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class LOTREntityUtumnoIceWarg extends LOTREntityUtumnoWarg {
	public LOTREntityUtumnoIceWarg(World world) {
		super(world);
		isChilly = true;
	}

	@Override
	public boolean attackEntityAsMob(Entity entity) {
		if (super.attackEntityAsMob(entity)) {
			int difficulty;
			int duration;
			if (entity instanceof EntityPlayerMP) {
				LOTRDamage.doFrostDamage((EntityPlayerMP) entity);
			}
			if (entity instanceof EntityLivingBase && (duration = (difficulty = worldObj.difficultySetting.getDifficultyId()) * (difficulty + 5) / 2) > 0) {
				((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, duration * 20, 0));
			}
			return true;
		}
		return false;
	}

	@Override
	public void entityInit() {
		super.entityInit();
		setWargType(LOTREntityWarg.WargType.ICE);
	}
}
