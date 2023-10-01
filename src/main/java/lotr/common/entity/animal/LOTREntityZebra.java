package lotr.common.entity.animal;

import lotr.common.LOTRMod;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class LOTREntityZebra extends LOTREntityHorse {
	public LOTREntityZebra(World world) {
		super(world);
	}

	@Override
	public EntityAgeable createChild(EntityAgeable entityageable) {
		return super.createChild(entityageable);
	}

	@Override
	public void dropFewItems(boolean flag, int i) {
		int j = rand.nextInt(2) + rand.nextInt(1 + i);
		for (int k = 0; k < j; ++k) {
			dropItem(Items.leather, 1);
		}
		j = rand.nextInt(2) + 1 + rand.nextInt(1 + i);
		for (int l = 0; l < j; ++l) {
			if (isBurning()) {
				dropItem(LOTRMod.zebraCooked, 1);
				continue;
			}
			dropItem(LOTRMod.zebraRaw, 1);
		}
	}

	@Override
	public boolean func_110259_cr() {
		return false;
	}

	@Override
	public String getAngrySoundName() {
		super.getAngrySoundName();
		return "lotr:zebra.hurt";
	}

	@Override
	public String getCommandSenderName() {
		if (hasCustomNameTag()) {
			return getCustomNameTag();
		}
		String s = EntityList.getEntityString(this);
		return StatCollector.translateToLocal("entity." + s + ".name");
	}

	@Override
	public String getDeathSound() {
		super.getDeathSound();
		return "lotr:zebra.death";
	}

	@Override
	public int getHorseType() {
		return 0;
	}

	@Override
	public String getHurtSound() {
		super.getHurtSound();
		return "lotr:zebra.hurt";
	}

	@Override
	public String getLivingSound() {
		super.getLivingSound();
		return "lotr:zebra.say";
	}

	@Override
	public boolean interact(EntityPlayer entityplayer) {
		ItemStack itemstack = entityplayer.inventory.getCurrentItem();
		if (itemstack != null && itemstack.getItem() == Items.bucket && !entityplayer.capabilities.isCreativeMode) {
			--itemstack.stackSize;
			if (itemstack.stackSize <= 0) {
				entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, new ItemStack(Items.milk_bucket));
			} else if (!entityplayer.inventory.addItemStackToInventory(new ItemStack(Items.milk_bucket))) {
				entityplayer.dropPlayerItemWithRandomChoice(new ItemStack(Items.milk_bucket, 1, 0), false);
			}
			return true;
		}
		return super.interact(entityplayer);
	}
}
