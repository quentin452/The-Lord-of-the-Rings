package lotr.common;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.WorldServer;

public class LOTRPlayerQuestData {
	public LOTRPlayerData playerData;
	public boolean givenFirstPouches;

	public LOTRPlayerQuestData(LOTRPlayerData pd) {
		playerData = pd;
	}

	public boolean getGivenFirstPouches() {
		return givenFirstPouches;
	}

	public void load(NBTTagCompound questData) {
		givenFirstPouches = questData.getBoolean("Pouches");
	}

	public void markDirty() {
		playerData.markDirty();
	}

	@SuppressWarnings("all")
	public void onUpdate(EntityPlayerMP entityplayer, WorldServer world) {
	}

	public void save(NBTTagCompound questData) {
		questData.setBoolean("Pouches", givenFirstPouches);
	}

	public void setGivenFirstPouches(boolean flag) {
		givenFirstPouches = flag;
		markDirty();
	}
}
