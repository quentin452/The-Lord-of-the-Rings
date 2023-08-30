package lotr.common.entity.ai;

import lotr.common.block.LOTRBlockOrcBomb;
import lotr.common.entity.item.LOTREntityOrcBomb;
import lotr.common.entity.npc.LOTREntityOrc;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.world.World;

public class LOTREntityAIOrcPlaceBomb extends EntityAIBase {
	public World worldObj;
	public LOTREntityOrc attacker;
	public EntityLivingBase entityTarget;
	public double moveSpeed;
	public PathEntity entityPathEntity;
	public int rePathDelay;

	public LOTREntityAIOrcPlaceBomb(LOTREntityOrc entity, double speed) {
		attacker = entity;
		worldObj = entity.worldObj;
		moveSpeed = speed;
		setMutexBits(3);
	}

	@Override
	public boolean continueExecuting() {
		if (attacker.npcItemsInv.getBomb() == null) {
			return false;
		}
		EntityLivingBase entity = attacker.getAttackTarget();
		return entity != null && entityTarget.isEntityAlive() && !attacker.getNavigator().noPath();
	}

	@Override
	public void resetTask() {
		entityTarget = null;
		attacker.getNavigator().clearPathEntity();
	}

	@Override
	public boolean shouldExecute() {
		EntityLivingBase entity = attacker.getAttackTarget();
		if (entity == null || attacker.npcItemsInv.getBomb() == null) {
			return false;
		}
		entityTarget = entity;
		entityPathEntity = attacker.getNavigator().getPathToEntityLiving(entityTarget);
		return entityPathEntity != null;
	}

	@Override
	public void startExecuting() {
		attacker.getNavigator().setPath(entityPathEntity, moveSpeed);
		rePathDelay = 0;
	}

	@Override
	public void updateTask() {
		attacker.getLookHelper().setLookPositionWithEntity(entityTarget, 30.0f, 30.0f);
		if (attacker.getEntitySenses().canSee(entityTarget) && --rePathDelay <= 0) {
			rePathDelay = 4 + attacker.getRNG().nextInt(7);
			attacker.getNavigator().tryMoveToEntityLiving(entityTarget, moveSpeed);
		}
		if (attacker.getDistanceToEntity(entityTarget) < 3.0) {
			LOTREntityOrcBomb bomb = new LOTREntityOrcBomb(worldObj, attacker.posX, attacker.posY + 1.0, attacker.posZ, attacker);
			int meta = 0;
			ItemStack bombItem = attacker.npcItemsInv.getBomb();
			if (bombItem != null && Block.getBlockFromItem(bombItem.getItem()) instanceof LOTRBlockOrcBomb) {
				meta = bombItem.getItemDamage();
			}
			bomb.setBombStrengthLevel(meta);
			bomb.droppedByHiredUnit = attacker.hiredNPCInfo.isActive;
			bomb.droppedTargetingPlayer = entityTarget instanceof EntityPlayer;
			worldObj.spawnEntityInWorld(bomb);
			worldObj.playSoundAtEntity(attacker, "game.tnt.primed", 1.0f, 1.0f);
			worldObj.playSoundAtEntity(attacker, "lotr:orc.fire", 1.0f, (worldObj.rand.nextFloat() - worldObj.rand.nextFloat()) * 0.2f + 1.0f);
			attacker.npcItemsInv.setBomb(null);
			int bombSlot = 5;
			if (attacker.hiredReplacedInv.hasReplacedEquipment(bombSlot)) {
				attacker.hiredReplacedInv.onEquipmentChanged(bombSlot, null);
			}
			attacker.refreshCurrentAttackMode();
		}
	}
}
