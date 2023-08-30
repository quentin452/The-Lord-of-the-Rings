package lotr.common.entity.npc;

import lotr.common.entity.ai.LOTREntityAIWargBombardierAttack;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public abstract class LOTREntityWargBombardier extends LOTREntityWarg {
	protected LOTREntityWargBombardier(World world) {
		super(world);
	}

	@Override
	public boolean canWargBeRidden() {
		return false;
	}

	@Override
	public LOTREntityNPC createWargRider() {
		return null;
	}

	@Override
	public void entityInit() {
		super.entityInit();
		dataWatcher.addObject(21, (byte) 35);
		dataWatcher.addObject(22, (byte) 1);
	}

	public int getBombFuse() {
		return dataWatcher.getWatchableObjectByte(21);
	}

	public int getBombStrengthLevel() {
		return dataWatcher.getWatchableObjectByte(22);
	}

	@Override
	public EntityAIBase getWargAttackAI() {
		return new LOTREntityAIWargBombardierAttack(this, 1.7);
	}

	@Override
	public boolean isMountSaddled() {
		return false;
	}

	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		if (getBombFuse() < 35) {
			worldObj.spawnParticle("smoke", posX, posY + 2.2, posZ, 0.0, 0.0, 0.0);
		}
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		setBombFuse(nbt.getByte("BombFuse"));
		setBombStrengthLevel(nbt.getByte("BombStrengthLevel"));
	}

	@Override
	public void setAttackTarget(EntityLivingBase target, boolean speak) {
		super.setAttackTarget(target, speak);
		if (target != null) {
			worldObj.playSoundAtEntity(this, "game.tnt.primed", 1.0f, 1.0f);
		}
	}

	public void setBombFuse(int i) {
		dataWatcher.updateObject(21, (byte) i);
	}

	public void setBombStrengthLevel(int i) {
		dataWatcher.updateObject(22, (byte) i);
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		nbt.setByte("BombFuse", (byte) getBombFuse());
		nbt.setByte("BombStrengthLevel", (byte) getBombStrengthLevel());
	}
}
