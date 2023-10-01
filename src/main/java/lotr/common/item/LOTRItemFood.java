package lotr.common.item;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRCreativeTabs;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTRItemFood extends ItemFood {
	public LOTRItemFood(int healAmount, float saturation, boolean canWolfEat) {
		super(healAmount, saturation, canWolfEat);
		setCreativeTab(LOTRCreativeTabs.tabFood);
	}

	@Override
	public ItemStack onEaten(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		if (!world.isRemote && this == LOTRMod.maggotyBread) {
			LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.eatMaggotyBread);
		}
		return super.onEaten(itemstack, world, entityplayer);
	}
}
