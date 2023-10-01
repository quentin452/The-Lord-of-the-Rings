package lotr.common.inventory;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.entity.animal.LOTREntityHorse;
import net.minecraft.inventory.ContainerHorseInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public class LOTRContainerMountInventory extends ContainerHorseInventory {
	public LOTRContainerMountInventory(IInventory playerInv, IInventory horseInv, LOTREntityHorse horse) {
		super(playerInv, horseInv, horse);
		ArrayList slots = new ArrayList(inventorySlots);
		inventorySlots.clear();
		inventoryItemStacks.clear();
		addSlotToContainer((Slot) slots.get(0));
		Slot armorSlot = (Slot) slots.get(1);
		addSlotToContainer(new Slot(armorSlot.inventory, armorSlot.slotNumber, armorSlot.xDisplayPosition, armorSlot.yDisplayPosition) {

			@SideOnly(Side.CLIENT)
			@Override
			public boolean func_111238_b() {
				return horse.func_110259_cr();
			}

			@Override
			public boolean isItemValid(ItemStack itemstack) {
				return super.isItemValid(itemstack) && horse.func_110259_cr() && horse.isMountArmorValid(itemstack);
			}
		});
		for (int i = 2; i < slots.size(); ++i) {
			addSlotToContainer((Slot) slots.get(i));
		}
	}

}
