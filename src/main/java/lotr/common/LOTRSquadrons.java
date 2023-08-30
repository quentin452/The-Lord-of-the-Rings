package lotr.common;

import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StringUtils;

public class LOTRSquadrons {
	public static int SQUADRON_LENGTH_MAX = 200;

	public static boolean areSquadronsCompatible(LOTREntityNPC npc, ItemStack itemstack) {
		String npcSquadron = npc.hiredNPCInfo.getSquadron();
		String itemSquadron = getSquadron(itemstack);
		if (StringUtils.isNullOrEmpty(npcSquadron)) {
			return StringUtils.isNullOrEmpty(itemSquadron);
		}
		return npcSquadron.equalsIgnoreCase(itemSquadron);
	}

	public static String checkAcceptableLength(String squadron) {
		if (squadron != null && squadron.length() > SQUADRON_LENGTH_MAX) {
			squadron = squadron.substring(0, SQUADRON_LENGTH_MAX);
		}
		return squadron;
	}

	public static String getSquadron(ItemStack itemstack) {
		if (itemstack.getItem() instanceof SquadronItem && itemstack.getTagCompound() != null && itemstack.getTagCompound().hasKey("LOTRSquadron")) {
			return itemstack.getTagCompound().getString("LOTRSquadron");
		}
		return null;
	}

	public static void setSquadron(ItemStack itemstack, String squadron) {
		if (itemstack.getItem() instanceof SquadronItem) {
			if (itemstack.getTagCompound() == null) {
				itemstack.setTagCompound(new NBTTagCompound());
			}
			itemstack.getTagCompound().setString("LOTRSquadron", squadron);
		}
	}

	public interface SquadronItem {
	}

}
