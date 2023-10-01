package lotr.common.item;

import lotr.common.LOTRCreativeTabs;
import lotr.common.dispenser.LOTRDispenseDart;
import lotr.common.entity.projectile.LOTREntityDart;
import net.minecraft.block.BlockDispenser;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTRItemDart extends Item {
	public boolean isPoisoned;

	public LOTRItemDart() {
		setCreativeTab(LOTRCreativeTabs.tabCombat);
		BlockDispenser.dispenseBehaviorRegistry.putObject(this, new LOTRDispenseDart(this));
	}

	public LOTREntityDart createDart(World world, EntityLivingBase entity, EntityLivingBase target, ItemStack itemstack, float charge, float inaccuracy) {
		return new LOTREntityDart(world, entity, target, itemstack, charge, inaccuracy);
	}

	public LOTREntityDart createDart(World world, EntityLivingBase entity, ItemStack itemstack, float charge) {
		return new LOTREntityDart(world, entity, itemstack, charge);
	}

	public LOTREntityDart createDart(World world, ItemStack itemstack, double d, double d1, double d2) {
		return new LOTREntityDart(world, itemstack, d, d1, d2);
	}

	public LOTRItemDart setPoisoned() {
		isPoisoned = true;
		return this;
	}
}
