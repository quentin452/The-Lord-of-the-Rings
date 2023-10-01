package lotr.common.inventory;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRLevelData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class LOTRSlotUnsmeltResult extends LOTRSlotProtected {
	public LOTRSlotUnsmeltResult(IInventory inv, int i, int j, int k) {
		super(inv, i, j, k);
	}

	@Override
	public void onPickupFromSlot(EntityPlayer entityplayer, ItemStack itemstack) {
		super.onPickupFromSlot(entityplayer, itemstack);
		if (!entityplayer.worldObj.isRemote) {
			LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.unsmelt);
		}
	}
}
