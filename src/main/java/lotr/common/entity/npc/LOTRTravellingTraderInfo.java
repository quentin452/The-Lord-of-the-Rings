package lotr.common.entity.npc;

import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

public class LOTRTravellingTraderInfo {
	public LOTREntityNPC theEntity;
	public LOTRTravellingTrader theTrader;
	public int timeUntilDespawn = -1;
	public Collection escortUUIDs = new ArrayList();

	public LOTRTravellingTraderInfo(LOTRTravellingTrader entity) {
		theEntity = (LOTREntityNPC) entity;
		theTrader = entity;
	}

	public void onDeath() {
		if (!theEntity.worldObj.isRemote && timeUntilDespawn >= 0) {
			LOTRSpeech.messageAllPlayers(theEntity.func_110142_aN().func_151521_b());
			removeEscorts();
		}
	}

	public void onUpdate() {
		if (!theEntity.worldObj.isRemote) {
			if (timeUntilDespawn > 0) {
				--timeUntilDespawn;
			}
			if (timeUntilDespawn == 2400) {
				for (Object player : theEntity.worldObj.playerEntities) {
					LOTRSpeech.sendSpeechAndChatMessage((EntityPlayer) player, theEntity, theTrader.getDepartureSpeech());
				}
			}
			if (timeUntilDespawn == 0) {
				ChatComponentText componentName = new ChatComponentText(theEntity.getCommandSenderName());
				componentName.getChatStyle().setColor(EnumChatFormatting.YELLOW);
				LOTRSpeech.messageAllPlayersInWorld(theEntity.worldObj, new ChatComponentTranslation("lotr.travellingTrader.depart", componentName));
				theEntity.setDead();
				removeEscorts();
			}
		}
	}

	public void readFromNBT(NBTTagCompound nbt) {
		timeUntilDespawn = nbt.getInteger("DespawnTime");
		escortUUIDs.clear();
		NBTTagList tags = nbt.getTagList("Escorts", 10);
		if (tags != null) {
			for (int i = 0; i < tags.tagCount(); ++i) {
				NBTTagCompound escortData = tags.getCompoundTagAt(i);
				escortUUIDs.add(new UUID(escortData.getLong("UUIDMost"), escortData.getLong("UUIDLeast")));
			}
		}
	}

	public void removeEscorts() {
		for (Object obj : theEntity.worldObj.loadedEntityList) {
			Entity entity = (Entity) obj;
			UUID entityUUID = entity.getUniqueID();
			for (Object uuid : escortUUIDs) {
				if (!entityUUID.equals(uuid)) {
					continue;
				}
				entity.setDead();
			}
		}
	}

	public void startVisiting(ICommandSender entityplayer) {
		ChatComponentText componentName;
		timeUntilDespawn = 24000;
		if (theEntity.worldObj.playerEntities.size() <= 1) {
			componentName = new ChatComponentText(theEntity.getCommandSenderName());
			componentName.getChatStyle().setColor(EnumChatFormatting.YELLOW);
			LOTRSpeech.messageAllPlayers(new ChatComponentTranslation("lotr.travellingTrader.arrive", componentName));
		} else {
			componentName = new ChatComponentText(theEntity.getCommandSenderName());
			componentName.getChatStyle().setColor(EnumChatFormatting.YELLOW);
			LOTRSpeech.messageAllPlayersInWorld(theEntity.worldObj, new ChatComponentTranslation("lotr.travellingTrader.arriveMP", componentName, entityplayer.getCommandSenderName()));
		}
		int i = MathHelper.floor_double(theEntity.posX);
		int j = MathHelper.floor_double(theEntity.boundingBox.minY);
		int k = MathHelper.floor_double(theEntity.posZ);
		theEntity.setHomeArea(i, j, k, 16);
		int escorts = 2 + theEntity.worldObj.rand.nextInt(3);
		for (int l = 0; l < escorts; ++l) {
			LOTREntityNPC escort = theTrader.createTravellingEscort();
			if (escort == null) {
				continue;
			}
			escort.setLocationAndAngles(theEntity.posX, theEntity.posY, theEntity.posZ, theEntity.rotationYaw, theEntity.rotationPitch);
			escort.isNPCPersistent = true;
			escort.spawnRidingHorse = false;
			escort.onSpawnWithEgg(null);
			theEntity.worldObj.spawnEntityInWorld(escort);
			escort.setHomeArea(i, j, k, 16);
			escort.isTraderEscort = true;
			escortUUIDs.add(escort.getUniqueID());
		}
	}

	public void writeToNBT(NBTTagCompound nbt) {
		nbt.setInteger("DespawnTime", timeUntilDespawn);
		NBTTagList escortTags = new NBTTagList();
		for (Object obj : escortUUIDs) {
			if (!(obj instanceof UUID)) {
				continue;
			}
			NBTTagCompound escortData = new NBTTagCompound();
			escortData.setLong("UUIDMost", ((UUID) obj).getMostSignificantBits());
			escortData.setLong("UUIDLeast", ((UUID) obj).getLeastSignificantBits());
			escortTags.appendTag(escortData);
		}
		nbt.setTag("Escorts", escortTags);
	}
}
