package lotr.common.entity.item;

import lotr.common.LOTRMod;
import lotr.common.entity.animal.LOTREntityBear;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class LOTREntityBearRug extends LOTREntityRugBase {
	public LOTREntityBearRug(World world) {
		super(world);
		setSize(1.8f, 0.3f);
	}

	@Override
	public void entityInit() {
		super.entityInit();
		dataWatcher.addObject(18, (byte) 0);
	}

	@Override
	public ItemStack getRugItem() {
		return new ItemStack(LOTRMod.bearRug, 1, getRugType().bearID);
	}

	@Override
	public String getRugNoise() {
		return "lotr:bear.say";
	}

	public LOTREntityBear.BearType getRugType() {
		byte i = dataWatcher.getWatchableObjectByte(18);
		return LOTREntityBear.BearType.forID(i);
	}

	public void setRugType(LOTREntityBear.BearType t) {
		dataWatcher.updateObject(18, (byte) t.bearID);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		setRugType(LOTREntityBear.BearType.forID(nbt.getByte("RugType")));
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		nbt.setByte("RugType", (byte) getRugType().bearID);
	}
}
