package lotr.common.inventory;

public class LOTRSlotStackSize implements Comparable {
	public int slot;
	public int stackSize;

	public LOTRSlotStackSize(int i, int j) {
		slot = i;
		stackSize = j;
	}

	@Override
	public int compareTo(Object obj) {
		if (obj instanceof LOTRSlotStackSize) {
			LOTRSlotStackSize obj1 = (LOTRSlotStackSize) obj;
			if (obj1.stackSize < stackSize) {
				return 1;
			}
			if (obj1.stackSize > stackSize) {
				return -1;
			}
			if (obj1.slot < slot) {
				return 1;
			}
			if (obj1.slot > slot) {
				return -1;
			}
		}
		return 0;
	}
}
