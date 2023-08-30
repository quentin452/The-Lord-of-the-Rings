package lotr.common.entity.npc;

import lotr.common.inventory.LOTRInventoryNPC;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IChatComponent;

public interface IBandit {
	boolean canTargetPlayerForTheft(EntityPlayer var1);

	LOTREntityNPC getBanditAsNPC();

	LOTRInventoryNPC getBanditInventory();

	int getMaxThefts();

	IChatComponent getTheftChatMsg(EntityPlayer var1);

	String getTheftSpeechBank(EntityPlayer var1);

	class Helper {
		public static boolean canStealFromPlayerInv(IBandit bandit, EntityPlayer entityplayer) {
			for (int slot = 0; slot < entityplayer.inventory.mainInventory.length; ++slot) {
				if (slot == entityplayer.inventory.currentItem || entityplayer.inventory.getStackInSlot(slot) == null) {
					continue;
				}
				return true;
			}
			return false;
		}

		public static LOTRInventoryNPC createInv(IBandit bandit) {
			return new LOTRInventoryNPC("BanditInventory", bandit.getBanditAsNPC(), bandit.getMaxThefts());
		}
	}

}
