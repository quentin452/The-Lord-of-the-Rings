package lotr.common.entity.npc;

import java.util.UUID;

import lotr.common.LOTRMod;
import lotr.common.entity.LOTRMountFunctions;
import lotr.common.item.LOTRItemMountArmor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public abstract class LOTREntityNPCRideable extends LOTREntityNPC implements LOTRNPCMount {
	public UUID tamingPlayer;
	public int npcTemper;

	protected LOTREntityNPCRideable(World world) {
		super(world);
	}

	public void angerNPC() {
		playSound(getHurtSound(), getSoundVolume(), getSoundPitch() * 1.5f);
	}

	@Override
	public boolean canDespawn() {
		return super.canDespawn() && !isNPCTamed();
	}

	@Override
	public boolean canRenameNPC() {
		return isNPCTamed() || super.canRenameNPC();
	}

	@Override
	public void entityInit() {
		super.entityInit();
		dataWatcher.addObject(17, (byte) 0);
	}

	public double getBaseMountedYOffset() {
		return height * 0.5;
	}

	public int getMaxNPCTemper() {
		return 100;
	}

	@Override
	public double getMountedYOffset() {
		double d = getBaseMountedYOffset();
		if (riddenByEntity != null) {
			d += riddenByEntity.yOffset - riddenByEntity.getYOffset();
		}
		return d;
	}

	public IInventory getMountInventory() {
		return null;
	}

	public int getNPCTemper() {
		return npcTemper;
	}

	@Override
	public float getStepHeightWhileRiddenByPlayer() {
		return 1.0f;
	}

	public EntityPlayer getTamingPlayer() {
		return worldObj.func_152378_a(tamingPlayer);
	}

	public void increaseNPCTemper(int i) {
		npcTemper = MathHelper.clamp_int(npcTemper + i, 0, getMaxNPCTemper());
	}

	@Override
	public boolean isMountArmorValid(ItemStack itemstack) {
		if (itemstack != null && itemstack.getItem() instanceof LOTRItemMountArmor) {
			LOTRItemMountArmor armor = (LOTRItemMountArmor) itemstack.getItem();
			return armor.isValid(this);
		}
		return false;
	}

	public boolean isNPCTamed() {
		return dataWatcher.getWatchableObjectByte(17) == 1;
	}

	@Override
	public void moveEntityWithHeading(float strafe, float forward) {
		LOTRMountFunctions.move(this, strafe, forward);
	}

	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		LOTRMountFunctions.update(this);
	}

	public void openGUI(EntityPlayer entityplayer) {
		IInventory inv = getMountInventory();
		if (inv != null && !worldObj.isRemote && (riddenByEntity == null || riddenByEntity == entityplayer) && isNPCTamed()) {
			entityplayer.openGui(LOTRMod.instance, 29, worldObj, getEntityId(), inv.getSizeInventory(), 0);
		}
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		setNPCTamed(nbt.getBoolean("NPCTamed"));
		if (nbt.hasKey("NPCTamer")) {
			tamingPlayer = UUID.fromString(nbt.getString("NPCTamer"));
		}
		npcTemper = nbt.getInteger("NPCTemper");
	}

	public void setNPCTamed(boolean flag) {
		dataWatcher.updateObject(17, (byte) (flag ? 1 : 0));
	}

	public void setNPCTemper(int i) {
		npcTemper = i;
	}

	@Override
	public void super_moveEntityWithHeading(float strafe, float forward) {
		super.moveEntityWithHeading(strafe, forward);
	}

	public void tameNPC(EntityPlayer entityplayer) {
		setNPCTamed(true);
		tamingPlayer = entityplayer.getUniqueID();
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		nbt.setBoolean("NPCTamed", isNPCTamed());
		if (tamingPlayer != null) {
			nbt.setString("NPCTamer", tamingPlayer.toString());
		}
		nbt.setInteger("NPCTemper", npcTemper);
	}
}
