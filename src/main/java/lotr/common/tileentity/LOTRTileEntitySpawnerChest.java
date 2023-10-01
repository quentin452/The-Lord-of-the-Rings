package lotr.common.tileentity;

import lotr.common.entity.LOTREntities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityChest;

public class LOTRTileEntitySpawnerChest extends TileEntityChest {
	public String entityClassName = "";

	public Entity createMob() {
		return EntityList.createEntityByName(entityClassName, worldObj);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		entityClassName = nbt.getString("MobID");
	}

	public void setMobID(Class entityClass) {
		entityClassName = LOTREntities.getStringFromClass(entityClass);
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setString("MobID", entityClassName);
	}
}
