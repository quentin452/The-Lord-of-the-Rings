package lotr.common.item;

import lotr.common.entity.animal.LOTREntityBear;
import lotr.common.entity.item.LOTREntityBearRug;
import lotr.common.entity.item.LOTREntityRugBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTRItemBearRug extends LOTRItemRugBase {
	public LOTRItemBearRug() {
		super(LOTREntityBear.BearType.bearTypeNames());
	}

	@Override
	public LOTREntityRugBase createRug(World world, ItemStack itemstack) {
		LOTREntityBearRug rug = new LOTREntityBearRug(world);
		rug.setRugType(LOTREntityBear.BearType.forID(itemstack.getItemDamage()));
		return rug;
	}
}
