package lotr.common.item;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import lotr.common.LOTRCreativeTabs;
import lotr.common.entity.projectile.LOTREntityGandalfFireball;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.network.LOTRPacketWeaponFX;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTRItemGandalfStaffWhite extends LOTRItemSword implements LOTRStoryItem {
	public LOTRItemGandalfStaffWhite() {
		super(LOTRMaterial.HIGH_ELVEN);
		setMaxDamage(1500);
		setCreativeTab(LOTRCreativeTabs.tabStory);
		lotrWeaponDamage = 8.0f;
	}

	@Override
	public EnumAction getItemUseAction(ItemStack itemstack) {
		return EnumAction.bow;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack itemstack) {
		return 40;
	}

	@Override
	public ItemStack onEaten(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		entityplayer.swingItem();
		itemstack.damageItem(2, entityplayer);
		world.playSoundAtEntity(entityplayer, "mob.ghast.fireball", 2.0f, (itemRand.nextFloat() - itemRand.nextFloat()) * 0.2f + 1.0f);
		if (!world.isRemote) {
			world.spawnEntityInWorld(new LOTREntityGandalfFireball(world, entityplayer));
			IMessage packet = new LOTRPacketWeaponFX(LOTRPacketWeaponFX.Type.STAFF_GANDALF_WHITE, entityplayer);
			LOTRPacketHandler.networkWrapper.sendToAllAround(packet, LOTRPacketHandler.nearEntity(entityplayer, 64.0));
		}
		return itemstack;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		entityplayer.setItemInUse(itemstack, getMaxItemUseDuration(itemstack));
		return itemstack;
	}
}
