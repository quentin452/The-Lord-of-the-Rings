package lotr.common.item;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRCreativeTabs;
import lotr.common.LOTRLevelData;
import lotr.common.fac.LOTRFaction;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

import java.util.List;

public class LOTRItemManFlesh extends ItemFood {
	public LOTRItemManFlesh(int i, float f, boolean flag) {
		super(i, f, flag);
		setCreativeTab(LOTRCreativeTabs.tabFood);
	}

	public static List<LOTRFaction> getManFleshFactions() {
		return LOTRFaction.getAllOfType(LOTRFaction.FactionType.TYPE_ORC, LOTRFaction.FactionType.TYPE_TROLL);
	}

	@Override
	public ItemStack onEaten(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		--itemstack.stackSize;
		boolean orcAligned = false;
		for (LOTRFaction faction : getManFleshFactions()) {
			float alignment = LOTRLevelData.getData(entityplayer).getAlignment(faction);
			if (alignment <= 0.0f) {
				continue;
			}
			orcAligned = true;
			break;
		}
		if (orcAligned) {
			entityplayer.getFoodStats().func_151686_a(this, itemstack);
			if (!world.isRemote) {
				LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.eatManFlesh);
			}
		} else if (!world.isRemote) {
			int dur = 30;
			entityplayer.addPotionEffect(new PotionEffect(Potion.hunger.id, dur * 20));
		}
		world.playSoundAtEntity(entityplayer, "random.burp", 0.5f, world.rand.nextFloat() * 0.1f + 0.9f);
		onFoodEaten(itemstack, world, entityplayer);
		return itemstack;
	}
}
