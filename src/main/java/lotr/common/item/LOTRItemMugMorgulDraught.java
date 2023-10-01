package lotr.common.item;

import lotr.common.LOTRLevelData;
import lotr.common.fac.LOTRFaction;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class LOTRItemMugMorgulDraught extends LOTRItemMug {
	public LOTRItemMugMorgulDraught() {
		super(0.0f);
	}

	@Override
	public ItemStack onEaten(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		if (!shouldApplyPotionEffects(itemstack, entityplayer)) {
			ItemStack result = super.onEaten(itemstack, world, entityplayer);
			if (!world.isRemote) {
				entityplayer.addPotionEffect(new PotionEffect(Potion.poison.id, 100));
			}
			return result;
		}
		return super.onEaten(itemstack, world, entityplayer);
	}

	@Override
	public boolean shouldApplyPotionEffects(ItemStack itemstack, EntityPlayer entityplayer) {
		return LOTRLevelData.getData(entityplayer).getAlignment(LOTRFaction.MORDOR) > 0.0f;
	}
}
