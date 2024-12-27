package lotr.common.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class LOTRItemBerry extends LOTRItemFood {
	public static List<Item> allBerries = new ArrayList<>();
	public boolean isPoisonous;

	public LOTRItemBerry() {
		super(2, 0.2f, false);
		allBerries.add(this);
	}

	public static void registerAllBerries(String name) {
		for (Item berry : allBerries) {
			OreDictionary.registerOre(name, berry);
		}
	}

	@Override
	public ItemStack onEaten(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		ItemStack ret = super.onEaten(itemstack, world, entityplayer);
		if (isPoisonous && !world.isRemote) {
			int duration = 3 + world.rand.nextInt(4);
			PotionEffect poison = new PotionEffect(Potion.poison.id, duration * 20);
			entityplayer.addPotionEffect(poison);
		}
		return ret;
	}

	public LOTRItemBerry setPoisonous() {
		isPoisonous = true;
		return this;
	}
}
