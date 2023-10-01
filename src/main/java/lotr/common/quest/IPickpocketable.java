package lotr.common.quest;

import lotr.common.util.LOTRLog;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.UUID;

public interface IPickpocketable {
	boolean canPickpocket();

	ItemStack createPickpocketItem();

	class Helper {
		public static String getOwner(ItemStack itemstack) {
			if (itemstack.hasTagCompound()) {
				return itemstack.getTagCompound().getCompoundTag("LOTRPickpocket").getString("Owner");
			}
			return null;
		}

		public static String getWanter(ItemStack itemstack) {
			if (itemstack.hasTagCompound()) {
				return itemstack.getTagCompound().getCompoundTag("LOTRPickpocket").getString("Wanter");
			}
			return null;
		}

		public static UUID getWanterID(ItemStack itemstack) {
			if (itemstack.hasTagCompound()) {
				String id = itemstack.getTagCompound().getCompoundTag("LOTRPickpocket").getString("WanterID");
				try {
					return UUID.fromString(id);
				} catch (IllegalArgumentException e) {
					LOTRLog.logger.warn("Item %s has invalid pickpocketed wanter ID %s", itemstack.getDisplayName(), id);
				}
			}
			return null;
		}

		public static boolean isPickpocketed(ItemStack itemstack) {
			return itemstack.getTagCompound() != null && itemstack.getTagCompound().hasKey("LOTRPickpocket");
		}

		public static void setPickpocketData(ItemStack itemstack, String ownerName, String wanterName, UUID wantedID) {
			NBTTagCompound data = new NBTTagCompound();
			data.setString("Owner", ownerName);
			data.setString("Wanter", wanterName);
			data.setString("WanterID", wantedID.toString());
			itemstack.setTagInfo("LOTRPickpocket", data);
		}
	}

}
