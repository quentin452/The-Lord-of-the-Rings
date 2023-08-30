package lotr.common.item;

import java.util.*;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.*;

public class LOTRItemOwnership {
	public static void addPreviousOwner(ItemStack itemstack, String name) {
		if (!itemstack.hasTagCompound()) {
			itemstack.setTagCompound(new NBTTagCompound());
		}
		List<String> previousOwners = getPreviousOwners(itemstack);
		NBTTagCompound nbt = itemstack.getTagCompound();
		if (nbt.hasKey("LOTROwner", 8)) {
			nbt.removeTag("LOTROwner");
		}
		List<String> lastPreviousOwners = previousOwners;
		previousOwners = new ArrayList<>();
		previousOwners.add(name);
		previousOwners.addAll(lastPreviousOwners);
		while (previousOwners.size() > 3) {
			previousOwners.remove(previousOwners.size() - 1);
		}
		NBTTagList tagList = new NBTTagList();
		for (String owner : previousOwners) {
			tagList.appendTag(new NBTTagString(owner));
		}
		nbt.setTag("LOTRPrevOwnerList", tagList);
	}

	public static String getCurrentOwner(ItemStack itemstack) {
		NBTTagCompound nbt;
		if (itemstack.getTagCompound() != null && (nbt = itemstack.getTagCompound()).hasKey("LOTRCurrentOwner", 8)) {
			return nbt.getString("LOTRCurrentOwner");
		}
		return null;
	}

	public static List<String> getPreviousOwners(ItemStack itemstack) {
		List<String> owners = new ArrayList<>();
		if (itemstack.getTagCompound() != null) {
			NBTTagCompound nbt = itemstack.getTagCompound();
			if (nbt.hasKey("LOTROwner", 8)) {
				String outdatedOwner = nbt.getString("LOTROwner");
				owners.add(outdatedOwner);
			}
			if (nbt.hasKey("LOTRPrevOwnerList", 9)) {
				NBTTagList tagList = nbt.getTagList("LOTRPrevOwnerList", 8);
				for (int i = 0; i < tagList.tagCount(); ++i) {
					String owner = tagList.getStringTagAt(i);
					owners.add(owner);
				}
			}
		}
		return owners;
	}

	public static void setCurrentOwner(ItemStack itemstack, String name) {
		String previousCurrentOwner;
		if (!itemstack.hasTagCompound()) {
			itemstack.setTagCompound(new NBTTagCompound());
		}
		previousCurrentOwner = getCurrentOwner(itemstack);
		if (previousCurrentOwner != null) {
			addPreviousOwner(itemstack, previousCurrentOwner);
		}
		NBTTagCompound nbt = itemstack.getTagCompound();
		nbt.setString("LOTRCurrentOwner", name);
	}
}
