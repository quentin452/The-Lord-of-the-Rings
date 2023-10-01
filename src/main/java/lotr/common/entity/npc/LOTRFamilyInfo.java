package lotr.common.entity.npc;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRLevelData;
import lotr.common.network.LOTRPacketFamilyInfo;
import lotr.common.network.LOTRPacketHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.management.PlayerManager;
import net.minecraft.util.MathHelper;
import net.minecraft.world.WorldServer;

import java.util.List;
import java.util.UUID;

public class LOTRFamilyInfo {
	public LOTREntityNPC theEntity;
	public Class marriageEntityClass;
	public Item marriageRing;
	public float marriageAlignmentRequired;
	public LOTRAchievement marriageAchievement;
	public int potentialMaxChildren;
	public int timeToMature;
	public int breedingDelay;
	public UUID spouseUniqueID;
	public int children;
	public int maxChildren;
	public UUID maleParentID;
	public UUID femaleParentID;
	public UUID ringGivingPlayer;
	public boolean doneFirstUpdate;
	public boolean resendData = true;
	public int age;
	public boolean male;
	public String name;
	public int drunkTime;
	public int timeUntilDrunkSpeech;

	public LOTRFamilyInfo(LOTREntityNPC npc) {
		theEntity = npc;
	}

	public boolean canMarryNPC(LOTREntityNPC npc) {
		if (npc.getClass() != theEntity.getClass() || npc.familyInfo.spouseUniqueID != null || npc.familyInfo.age != 0 || npc.getEquipmentInSlot(4) != null) {
			return false;
		}
		if (npc == theEntity || npc.familyInfo.male == male || maleParentID != null && maleParentID == npc.familyInfo.maleParentID || femaleParentID != null && femaleParentID == npc.familyInfo.femaleParentID) {
			return false;
		}
		ItemStack heldItem = npc.getEquipmentInSlot(0);
		return heldItem != null && heldItem.getItem() == marriageRing;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int i) {
		age = i;
		markDirty();
	}

	public String getName() {
		return name;
	}

	public void setName(String s) {
		name = s;
		markDirty();
	}

	public LOTREntityNPC getParentToFollow() {
		UUID parentToFollowID = male ? maleParentID : femaleParentID;
		List list = theEntity.worldObj.getEntitiesWithinAABB(theEntity.getClass(), theEntity.boundingBox.expand(16.0, 8.0, 16.0));
		for (Object element : list) {
			Entity entity = (Entity) element;
			if (!(entity instanceof LOTREntityNPC) || entity == theEntity || parentToFollowID == null || !entity.getUniqueID().equals(parentToFollowID)) {
				continue;
			}
			return (LOTREntityNPC) entity;
		}
		return null;
	}

	public int getRandomMaxChildren() {
		return 1 + theEntity.getRNG().nextInt(potentialMaxChildren);
	}

	public EntityPlayer getRingGivingPlayer() {
		if (ringGivingPlayer != null) {
			for (Object obj : theEntity.worldObj.playerEntities) {
				EntityPlayer entityplayer = (EntityPlayer) obj;
				if (!entityplayer.getUniqueID().equals(ringGivingPlayer)) {
					continue;
				}
				return entityplayer;
			}
		}
		return null;
	}

	public LOTREntityNPC getSpouse() {
		if (spouseUniqueID == null) {
			return null;
		}
		List list = theEntity.worldObj.getEntitiesWithinAABB(theEntity.getClass(), theEntity.boundingBox.expand(16.0, 8.0, 16.0));
		for (Object element : list) {
			Entity entity = (Entity) element;
			if (!(entity instanceof LOTREntityNPC) || entity == theEntity || !entity.getUniqueID().equals(spouseUniqueID)) {
				continue;
			}
			LOTREntityNPC npc = (LOTREntityNPC) entity;
			if (npc.familyInfo.spouseUniqueID == null || !theEntity.getUniqueID().equals(npc.familyInfo.spouseUniqueID)) {
				continue;
			}
			return npc;
		}
		return null;
	}

	public boolean interact(EntityPlayer entityplayer) {
		if (theEntity.hiredNPCInfo.isActive) {
			return false;
		}
		ItemStack itemstack = entityplayer.inventory.getCurrentItem();
		if (itemstack != null && itemstack.getItem() == marriageRing && LOTRLevelData.getData(entityplayer).getAlignment(theEntity.getFaction()) >= marriageAlignmentRequired && theEntity.getClass() == marriageEntityClass && age == 0 && theEntity.getEquipmentInSlot(0) == null && theEntity.getEquipmentInSlot(4) == null && spouseUniqueID == null) {
			if (!entityplayer.capabilities.isCreativeMode) {
				--itemstack.stackSize;
				if (itemstack.stackSize <= 0) {
					entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
				}
			}
			if (!theEntity.worldObj.isRemote) {
				theEntity.setCurrentItemOrArmor(0, new ItemStack(marriageRing));
				ringGivingPlayer = entityplayer.getUniqueID();
			}
			theEntity.isNPCPersistent = true;
			return true;
		}
		return false;
	}

	public boolean isDrunk() {
		return drunkTime > 0;
	}

	public boolean isMale() {
		return male;
	}

	public void setMale(boolean flag) {
		male = flag;
		markDirty();
	}

	public void markDirty() {
		if (!theEntity.worldObj.isRemote) {
			if (theEntity.ticksExisted > 0) {
				resendData = true;
			} else {
				sendDataToAllWatchers();
			}
		}
	}

	public void onUpdate() {
		if (!theEntity.worldObj.isRemote) {
			if (!doneFirstUpdate) {
				doneFirstUpdate = true;
			}
			if (resendData) {
				sendDataToAllWatchers();
				resendData = false;
			}
			if (age < 0) {
				setAge(age + 1);
			} else if (age > 0) {
				setAge(age - 1);
			}
			if (drunkTime > 0) {
				setDrunkTime(drunkTime - 1);
			}
			if (isDrunk()) {
				theEntity.addPotionEffect(new PotionEffect(Potion.confusion.id, 20));
				if (timeUntilDrunkSpeech > 0) {
					--timeUntilDrunkSpeech;
				}
				if (theEntity.isEntityAlive() && theEntity.getAttackTarget() == null && timeUntilDrunkSpeech == 0) {
					double range = 12.0;
					List players = theEntity.worldObj.getEntitiesWithinAABB(EntityPlayer.class, theEntity.boundingBox.expand(range, range, range));
					for (Object obj : players) {
						String speechBank;
						EntityPlayer entityplayer = (EntityPlayer) obj;
						if (!entityplayer.isEntityAlive() || entityplayer.capabilities.isCreativeMode || (speechBank = theEntity.getSpeechBank(entityplayer)) == null || theEntity.getRNG().nextInt(3) != 0) {
							continue;
						}
						theEntity.sendSpeechBank(entityplayer, speechBank);
					}
					timeUntilDrunkSpeech = 20 * MathHelper.getRandomIntegerInRange(theEntity.getRNG(), 5, 20);
				}
			}
		}
	}

	public void readFromNBT(NBTTagCompound nbt) {
		setAge(nbt.getInteger("NPCAge"));
		if (nbt.hasKey("NPCMale")) {
			setMale(nbt.getBoolean("NPCMale"));
		}
		if (nbt.hasKey("NPCName")) {
			setName(nbt.getString("NPCName"));
		}
		setDrunkTime(nbt.getInteger("NPCDrunkTime"));
		if (nbt.hasKey("SpouseUUIDMost") && nbt.hasKey("SpouseUUIDLeast")) {
			spouseUniqueID = new UUID(nbt.getLong("SpouseUUIDMost"), nbt.getLong("SpouseUUIDLeast"));
		}
		children = nbt.getInteger("Children");
		maxChildren = nbt.getInteger("MaxChildren");
		if (nbt.hasKey("MaleParentUUIDMost") && nbt.hasKey("MaleParentUUIDLeast")) {
			maleParentID = new UUID(nbt.getLong("MaleParentUUIDMost"), nbt.getLong("MaleParentUUIDLeast"));
		}
		if (nbt.hasKey("FemaleParentUUIDMost") && nbt.hasKey("FemaleParentUUIDLeast")) {
			femaleParentID = new UUID(nbt.getLong("FemaleParentUUIDMost"), nbt.getLong("FemaleParentUUIDLeast"));
		}
		if (nbt.hasKey("RingGivingPlayer")) {
			ringGivingPlayer = new UUID(nbt.getLong("RingGivingPlayerUUIDMost"), nbt.getLong("RingGivingPlayerUUIDLeast"));
		}
	}

	public void receiveData(LOTRPacketFamilyInfo packet) {
		setAge(packet.age);
		setMale(packet.isMale);
		setName(packet.name);
		if (packet.isDrunk) {
			setDrunkTime(100000);
		} else {
			setDrunkTime(0);
		}
	}

	public void sendData(EntityPlayerMP entityplayer) {
		IMessage packet = new LOTRPacketFamilyInfo(theEntity.getEntityId(), age, male, name, isDrunk());
		LOTRPacketHandler.networkWrapper.sendTo(packet, entityplayer);
	}

	public void sendDataToAllWatchers() {
		int x = MathHelper.floor_double(theEntity.posX) >> 4;
		int z = MathHelper.floor_double(theEntity.posZ) >> 4;
		PlayerManager playermanager = ((WorldServer) theEntity.worldObj).getPlayerManager();
		List players = theEntity.worldObj.playerEntities;
		for (Object obj : players) {
			EntityPlayerMP entityplayer = (EntityPlayerMP) obj;
			if (!playermanager.isPlayerWatchingChunk(entityplayer, x, z)) {
				continue;
			}
			sendData(entityplayer);
		}
	}

	public void setChild() {
		setAge(-timeToMature);
	}

	public void setDrunkTime(int i) {
		boolean prevDrunk = isDrunk();
		drunkTime = i;
		if (isDrunk() != prevDrunk) {
			markDirty();
		}
	}

	public void setMaxBreedingDelay() {
		float f = breedingDelay;
		setAge((int) (f * (0.5f + theEntity.getRNG().nextFloat() * 0.5f)));
	}

	public void writeToNBT(NBTTagCompound nbt) {
		nbt.setInteger("NPCAge", age);
		nbt.setBoolean("NPCMale", male);
		if (name != null) {
			nbt.setString("NPCName", name);
		}
		nbt.setInteger("NPCDrunkTime", drunkTime);
		if (spouseUniqueID != null) {
			nbt.setLong("SpouseUUIDMost", spouseUniqueID.getMostSignificantBits());
			nbt.setLong("SpouseUUIDLeast", spouseUniqueID.getLeastSignificantBits());
		}
		nbt.setInteger("Children", children);
		nbt.setInteger("MaxChildren", maxChildren);
		if (maleParentID != null) {
			nbt.setLong("MaleParentUUIDMost", maleParentID.getMostSignificantBits());
			nbt.setLong("MaleParentUUIDLeast", maleParentID.getLeastSignificantBits());
		}
		if (femaleParentID != null) {
			nbt.setLong("FemaleParentUUIDMost", femaleParentID.getMostSignificantBits());
			nbt.setLong("FemaleParentUUIDLeast", femaleParentID.getLeastSignificantBits());
		}
		if (ringGivingPlayer != null) {
			nbt.setLong("RingGivingPlayerUUIDMost", ringGivingPlayer.getMostSignificantBits());
			nbt.setLong("RingGivingPlayerUUIDLeast", ringGivingPlayer.getLeastSignificantBits());
		}
	}
}
