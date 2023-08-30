package lotr.common.item;

import java.util.List;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import lotr.common.*;
import lotr.common.fac.LOTRFaction;
import lotr.common.network.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class LOTRItemSauronMace extends LOTRItemHammer implements LOTRStoryItem {
	public LOTRItemSauronMace() {
		super(LOTRMaterial.MORDOR);
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
		itemstack.damageItem(2, entityplayer);
		return useSauronMace(itemstack, world, entityplayer);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		entityplayer.setItemInUse(itemstack, getMaxItemUseDuration(itemstack));
		return itemstack;
	}

	public static ItemStack useSauronMace(ItemStack itemstack, World world, EntityLivingBase user) {
		user.swingItem();
		world.playSoundAtEntity(user, "lotr:item.maceSauron", 2.0f, (itemRand.nextFloat() - itemRand.nextFloat()) * 0.2f + 1.0f);
		if (!world.isRemote) {
			List entities = world.getEntitiesWithinAABB(EntityLivingBase.class, user.boundingBox.expand(12.0, 8.0, 12.0));
			if (!entities.isEmpty()) {
				for (Object entitie : entities) {
					EntityLivingBase entity = (EntityLivingBase) entitie;
					if (entity == user || entity instanceof EntityLiving && LOTRFaction.MORDOR.isGoodRelation(LOTRMod.getNPCFaction(entity)) || entity instanceof EntityPlayer && (!(user instanceof EntityPlayer) ? user instanceof EntityLiving && ((EntityLiving) user).getAttackTarget() != entity && LOTRLevelData.getData((EntityPlayer) entity).getAlignment(LOTRFaction.MORDOR) > 0.0f : !MinecraftServer.getServer().isPVPEnabled() || LOTRLevelData.getData((EntityPlayer) entity).getAlignment(LOTRFaction.MORDOR) > 0.0f)) {
						continue;
					}
					float strength = 6.0f - user.getDistanceToEntity(entity) * 0.75f;
					if (strength < 1.0f) {
						strength = 1.0f;
					}
					if (user instanceof EntityPlayer) {
						entity.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer) user), 6.0f * strength);
					} else {
						entity.attackEntityFrom(DamageSource.causeMobDamage(user), 6.0f * strength);
					}
					float knockback = strength;
					if (knockback > 4.0f) {
						knockback = 4.0f;
					}
					entity.addVelocity(-MathHelper.sin(user.rotationYaw * 3.1415927f / 180.0f) * 0.7f * knockback, 0.2 + 0.12 * knockback, MathHelper.cos(user.rotationYaw * 3.1415927f / 180.0f) * 0.7f * knockback);
				}
			}
			IMessage packet = new LOTRPacketWeaponFX(LOTRPacketWeaponFX.Type.MACE_SAURON, user);
			LOTRPacketHandler.networkWrapper.sendToAllAround(packet, LOTRPacketHandler.nearEntity(user, 64.0));
		}
		return itemstack;
	}
}
