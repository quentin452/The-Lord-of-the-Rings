package lotr.common.item;

import java.util.List;

import cpw.mods.fml.relauncher.*;
import lotr.common.LOTRCreativeTabs;
import lotr.common.dispenser.LOTRDispenseSpawnEgg;
import lotr.common.entity.LOTREntities;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.block.*;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class LOTRItemSpawnEgg extends Item {
	public LOTRItemSpawnEgg() {
		setHasSubtypes(true);
		setCreativeTab(LOTRCreativeTabs.tabSpawn);
		BlockDispenser.dispenseBehaviorRegistry.putObject(this, new LOTRDispenseSpawnEgg());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack itemstack, EntityPlayer entityplayer, List list, boolean flag) {
		String entityName = LOTREntities.getStringFromID(itemstack.getItemDamage());
		if (entityName != null) {
			list.add(entityName);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack itemstack, int i) {
		LOTREntities.SpawnEggInfo info = LOTREntities.spawnEggs.get(itemstack.getItemDamage());
		return info != null ? i == 0 ? info.primaryColor : info.secondaryColor : 16777215;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamageForRenderPass(int i, int j) {
		return Items.spawn_egg.getIconFromDamageForRenderPass(i, j);
	}

	@Override
	public String getItemStackDisplayName(ItemStack itemstack) {
		StringBuilder itemName = new StringBuilder().append((StatCollector.translateToLocal(getUnlocalizedName() + ".name")).trim());
		String entityName = LOTREntities.getStringFromID(itemstack.getItemDamage());
		if (entityName != null) {
			itemName.append(" ").append(StatCollector.translateToLocal("entity." + entityName + ".name"));
		}
		return itemName.toString();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		for (LOTREntities.SpawnEggInfo info : LOTREntities.spawnEggs.values()) {
			list.add(new ItemStack(item, 1, info.spawnedID));
		}
	}

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l, float f, float f1, float f2) {
		Entity entity;
		if (world.isRemote) {
			return true;
		}
		Block block = world.getBlock(i, j, k);
		i += Facing.offsetsXForSide[l];
		j += Facing.offsetsYForSide[l];
		k += Facing.offsetsZForSide[l];
		double d = 0.0;
		if (l == 1 && block != null && block.getRenderType() == 11) {
			d = 0.5;
		}
		entity = spawnCreature(world, itemstack.getItemDamage(), i + 0.5, j + d, k + 0.5);
		if (entity != null) {
			if (entity instanceof EntityLiving && itemstack.hasDisplayName()) {
				((EntityLiving) entity).setCustomNameTag(itemstack.getDisplayName());
			}
			if (entity instanceof LOTREntityNPC) {
				((LOTREntityNPC) entity).setPersistentAndTraderShouldRespawn();
				((LOTREntityNPC) entity).onArtificalSpawn();
			}
			if (!entityplayer.capabilities.isCreativeMode) {
				--itemstack.stackSize;
			}
		}
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iconregister) {
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses() {
		return true;
	}

	public static Entity spawnCreature(World world, int id, double d, double d1, double d2) {
		if (!LOTREntities.spawnEggs.containsKey(id)) {
			return null;
		}
		String entityName = LOTREntities.getStringFromID(id);
		Entity entity = EntityList.createEntityByName(entityName, world);
		if (entity instanceof EntityLiving) {
			EntityLiving entityliving = (EntityLiving) entity;
			entityliving.setLocationAndAngles(d, d1, d2, MathHelper.wrapAngleTo180_float(world.rand.nextFloat() * 360.0f), 0.0f);
			entityliving.rotationYawHead = entityliving.rotationYaw;
			entityliving.renderYawOffset = entityliving.rotationYaw;
			entityliving.onSpawnWithEgg(null);
			world.spawnEntityInWorld(entityliving);
			entityliving.playLivingSound();
		}
		return entity;
	}
}
