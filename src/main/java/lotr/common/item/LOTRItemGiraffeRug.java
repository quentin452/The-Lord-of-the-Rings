package lotr.common.item;

import lotr.common.entity.item.LOTREntityGiraffeRug;
import lotr.common.entity.item.LOTREntityRugBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTRItemGiraffeRug extends LOTRItemRugBase {
	public LOTRItemGiraffeRug() {
		super("giraffe");
	}

	@Override
	public LOTREntityRugBase createRug(World world, ItemStack itemstack) {
		return new LOTREntityGiraffeRug(world);
	}
}
