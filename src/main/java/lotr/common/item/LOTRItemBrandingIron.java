package lotr.common.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.LOTRCreativeTabs;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.tileentity.LOTRTileEntityForgeBase;
import lotr.common.tileentity.LOTRTileEntityHobbitOven;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

public class LOTRItemBrandingIron extends Item {
	@SideOnly(Side.CLIENT)
	public IIcon iconCool;
	@SideOnly(Side.CLIENT)
	public IIcon iconHot;

	public LOTRItemBrandingIron() {
		setCreativeTab(LOTRCreativeTabs.tabTools);
		setMaxStackSize(1);
		setMaxDamage(100);
		setFull3D();
	}

	public static UUID getBrandingPlayer(Entity entity) {
		NBTTagCompound nbt = entity.getEntityData();
		if (nbt.hasKey("LOTRBrander")) {
			String s = nbt.getString("LOTRBrander");
			return UUID.fromString(s);
		}
		return null;
	}

	public static String getBrandName(ItemStack itemstack) {
		String s;
		if (itemstack.hasTagCompound() && !StringUtils.isBlank(s = itemstack.getTagCompound().getString("BrandName"))) {
			return s;
		}
		return null;
	}

	public static boolean hasBrandName(ItemStack itemstack) {
		return getBrandName(itemstack) != null;
	}

	public static boolean isHeated(ItemStack itemstack) {
		if (itemstack.hasTagCompound()) {
			return itemstack.getTagCompound().getBoolean("HotIron");
		}
		return false;
	}

	public static void setBrandingPlayer(Entity entity, UUID player) {
		String s = player.toString();
		entity.getEntityData().setString("LOTRBrander", s);
	}

	public static void setBrandName(ItemStack itemstack, String s) {
		if (!itemstack.hasTagCompound()) {
			itemstack.setTagCompound(new NBTTagCompound());
		}
		itemstack.getTagCompound().setString("BrandName", s);
	}

	public static void setHeated(ItemStack itemstack, boolean flag) {
		if (!itemstack.hasTagCompound()) {
			itemstack.setTagCompound(new NBTTagCompound());
		}
		itemstack.getTagCompound().setBoolean("HotIron", flag);
	}

	public static String trimAcceptableBrandName(String s) {
		s = StringUtils.trim(s);
		int maxLength = 64;
		if (s.length() > maxLength) {
			s = s.substring(0, maxLength);
		}
		return s;
	}

	@Override
	public IIcon getIcon(ItemStack itemstack, int pass) {
		if (isHeated(itemstack)) {
			return iconHot;
		}
		return iconCool;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIconIndex(ItemStack itemstack) {
		return getIcon(itemstack, 0);
	}

	@Override
	public boolean getIsRepairable(ItemStack itemstack, ItemStack repairItem) {
		return repairItem.getItem() == Items.iron_ingot;
	}

	@Override
	public String getItemStackDisplayName(ItemStack itemstack) {
		String name = super.getItemStackDisplayName(itemstack);
		if (hasBrandName(itemstack)) {
			String brandName = getBrandName(itemstack);
			name = StatCollector.translateToLocalFormatted("item.lotr.brandingIron.named", name, brandName);
		} else {
			name = StatCollector.translateToLocalFormatted("item.lotr.brandingIron.unnamed", name);
		}
		return name;
	}

	@Override
	public boolean itemInteractionForEntity(ItemStack itemstack, EntityPlayer entityplayer, EntityLivingBase entity) {
		if (isHeated(itemstack) && hasBrandName(itemstack)) {
			String brandName = getBrandName(itemstack);
			if (entity instanceof EntityLiving) {
				EntityLiving entityliving = (EntityLiving) entity;
				boolean acceptableEntity = entityliving instanceof EntityAnimal || entityliving instanceof LOTREntityNPC && ((LOTREntityNPC) entityliving).canRenameNPC();
				if (acceptableEntity && !entityliving.getCustomNameTag().equals(brandName)) {
					entityliving.setCustomNameTag(brandName);
					entityliving.func_110163_bv();
					entityliving.playLivingSound();
					entityliving.getJumpHelper().setJumping();
					World world = entityliving.worldObj;
					world.playSoundAtEntity(entityliving, "random.fizz", 0.5f, 2.6f + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.8f);
					entityplayer.swingItem();
					int preDamage = itemstack.getItemDamage();
					itemstack.damageItem(1, entityplayer);
					int newDamage = itemstack.getItemDamage();
					if (preDamage / 5 != newDamage / 5) {
						setHeated(itemstack, false);
					}
					if (!world.isRemote) {
						setBrandingPlayer(entityliving, entityplayer.getUniqueID());
					}
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		if (!hasBrandName(itemstack)) {
			entityplayer.openGui(LOTRMod.instance, 61, world, 0, 0, 0);
		}
		return itemstack;
	}

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int side, float f, float f1, float f2) {
		if (hasBrandName(itemstack) && !isHeated(itemstack)) {
			boolean isHotBlock = false;
			TileEntity te = world.getTileEntity(i, j, k);
			if (te instanceof TileEntityFurnace && ((TileEntityFurnace) te).isBurning() || te instanceof LOTRTileEntityForgeBase && ((LOTRTileEntityForgeBase) te).isSmelting()) {
				isHotBlock = true;
			} else if (te instanceof LOTRTileEntityHobbitOven && ((LOTRTileEntityHobbitOven) te).isCooking()) {
				isHotBlock = true;
			}
			if (!isHotBlock) {
				ForgeDirection dir = ForgeDirection.getOrientation(side);
				Block block = world.getBlock(i + dir.offsetX, j + dir.offsetY, k + dir.offsetZ);
				if (block.getMaterial() == Material.fire) {
					isHotBlock = true;
				}
			}
			if (isHotBlock) {
				setHeated(itemstack, true);
				return true;
			}
		}
		return false;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IIconRegister iconregister) {
		iconCool = iconregister.registerIcon(getIconString());
		iconHot = iconregister.registerIcon(getIconString() + "_hot");
	}
}
