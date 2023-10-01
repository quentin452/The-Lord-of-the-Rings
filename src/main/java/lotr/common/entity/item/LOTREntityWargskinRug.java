package lotr.common.entity.item;

import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityWarg;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class LOTREntityWargskinRug extends LOTREntityRugBase {
	public LOTREntityWargskinRug(World world) {
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
		return new ItemStack(LOTRMod.wargskinRug, 1, getRugType().wargID);
	}

	@Override
	public String getRugNoise() {
		return "lotr:warg.say";
	}

	public LOTREntityWarg.WargType getRugType() {
		byte i = dataWatcher.getWatchableObjectByte(18);
		return LOTREntityWarg.WargType.forID(i);
	}

	public void setRugType(LOTREntityWarg.WargType w) {
		dataWatcher.updateObject(18, (byte) w.wargID);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		setRugType(LOTREntityWarg.WargType.forID(nbt.getByte("RugType")));
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		nbt.setByte("RugType", (byte) getRugType().wargID);
	}
}
