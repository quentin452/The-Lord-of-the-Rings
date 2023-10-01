package lotr.common.item;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRLevelData;
import lotr.common.fac.LOTRFaction;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class LOTRItemMorgulShroom extends ItemBlock {
	public LOTRItemMorgulShroom(Block block) {
		super(block);
	}

	@Override
	public EnumAction getItemUseAction(ItemStack itemstack) {
		return EnumAction.eat;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack itemstack) {
		return 32;
	}

	@Override
	public ItemStack onEaten(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		--itemstack.stackSize;
		if (LOTRLevelData.getData(entityplayer).getAlignment(LOTRFaction.MORDOR) > 0.0f) {
			entityplayer.getFoodStats().addStats(4, 0.4f);
		} else if (!world.isRemote) {
			entityplayer.addPotionEffect(new PotionEffect(Potion.poison.id, 80));
		}
		if (!world.isRemote) {
			LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.eatMorgulShroom);
		}
		world.playSoundAtEntity(entityplayer, "random.burp", 0.5f, world.rand.nextFloat() * 0.1f + 0.9f);
		return itemstack;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		if (entityplayer.canEat(false)) {
			entityplayer.setItemInUse(itemstack, getMaxItemUseDuration(itemstack));
		}
		return itemstack;
	}
}
