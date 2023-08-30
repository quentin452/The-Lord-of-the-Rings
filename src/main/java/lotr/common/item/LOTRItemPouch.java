package lotr.common.item;

import java.util.*;

import cpw.mods.fml.relauncher.*;
import lotr.common.*;
import lotr.common.block.*;
import lotr.common.inventory.*;
import net.minecraft.block.*;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class LOTRItemPouch extends Item {
	public static String[] pouchTypes = {"small", "medium", "large"};
	@SideOnly(Side.CLIENT)
	public IIcon[] pouchIcons;
	@SideOnly(Side.CLIENT)
	public IIcon[] pouchIconsOpen;
	@SideOnly(Side.CLIENT)
	public IIcon[] overlayIcons;
	@SideOnly(Side.CLIENT)
	public IIcon[] overlayIconsOpen;

	public LOTRItemPouch() {
		setHasSubtypes(true);
		setMaxDamage(0);
		setMaxStackSize(1);
		setCreativeTab(LOTRCreativeTabs.tabMisc);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack itemstack, EntityPlayer entityplayer, List list, boolean flag) {
		int slots = getCapacity(itemstack);
		int slotsFull = 0;
		IInventory pouchInv = new LOTRInventoryPouch(itemstack);
		for (int i = 0; i < pouchInv.getSizeInventory(); ++i) {
			ItemStack slotItem = pouchInv.getStackInSlot(i);
			if (slotItem == null) {
				continue;
			}
			++slotsFull;
		}
		list.add(StatCollector.translateToLocalFormatted("item.lotr.pouch.slots", slotsFull, slots));
		if (isPouchDyed(itemstack)) {
			list.add(StatCollector.translateToLocal("item.lotr.pouch.dyed"));
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack itemstack, int pass) {
		if (pass == 0) {
			return getPouchColor(itemstack);
		}
		return 16777215;
	}

	@Override
	public IIcon getIcon(ItemStack itemstack, int pass) {
		Container container;
		int meta;
		boolean open = false;
		EntityPlayer entityplayer = LOTRMod.proxy.getClientPlayer();
		if (entityplayer != null && ((container = entityplayer.openContainer) instanceof LOTRContainerPouch || container instanceof LOTRContainerChestWithPouch) && itemstack == entityplayer.getHeldItem()) {
			open = true;
		}
		meta = itemstack.getItemDamage();
		if (meta >= pouchIcons.length) {
			meta = 0;
		}
		if (open) {
			return pass > 0 ? overlayIconsOpen[meta] : pouchIconsOpen[meta];
		}
		return pass > 0 ? overlayIcons[meta] : pouchIcons[meta];
	}

	@Override
	public int getRenderPasses(int meta) {
		return 2;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		for (int i = 0; i < pouchTypes.length; ++i) {
			list.add(new ItemStack(item, 1, i));
		}
	}

	@Override
	public String getUnlocalizedName(ItemStack itemstack) {
		return getUnlocalizedName() + "." + itemstack.getItemDamage();
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		if (!world.isRemote) {
			entityplayer.openGui(LOTRMod.instance, 15, world, entityplayer.inventory.currentItem, 0, 0);
		}
		return itemstack;
	}

	@Override
	public boolean onItemUseFirst(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int side, float hitX, float hitY, float hitZ) {
		IInventory chest = getChestInvAt(entityplayer, world, i, j, k);
		if (chest != null) {
			LOTRMod.proxy.usePouchOnChest(entityplayer, world, i, j, k, side, itemstack, entityplayer.inventory.currentItem);
			return true;
		}
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iconregister) {
		pouchIcons = new IIcon[pouchTypes.length];
		pouchIconsOpen = new IIcon[pouchTypes.length];
		overlayIcons = new IIcon[pouchTypes.length];
		overlayIconsOpen = new IIcon[pouchTypes.length];
		for (int i = 0; i < pouchTypes.length; ++i) {
			pouchIcons[i] = iconregister.registerIcon(getIconString() + "_" + pouchTypes[i]);
			pouchIconsOpen[i] = iconregister.registerIcon(getIconString() + "_" + pouchTypes[i] + "_open");
			overlayIcons[i] = iconregister.registerIcon(getIconString() + "_" + pouchTypes[i] + "_overlay");
			overlayIconsOpen[i] = iconregister.registerIcon(getIconString() + "_" + pouchTypes[i] + "_open_overlay");
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses() {
		return true;
	}

	public static int getCapacity(ItemStack itemstack) {
		return getCapacityForMeta(itemstack.getItemDamage());
	}

	public static int getCapacityForMeta(int i) {
		return (i + 1) * 9;
	}

	public static IInventory getChestInvAt(EntityPlayer entityplayer, World world, int i, int j, int k) {
		InventoryEnderChest enderInv;
		Block block = world.getBlock(i, j, k);
		if (block instanceof LOTRBlockSpawnerChest) {
			return null;
		}
		TileEntity te = world.getTileEntity(i, j, k);
		if (block instanceof BlockChest) {
			return ((BlockChest) block).func_149951_m(world, i, j, k);
		}
		if (block instanceof LOTRBlockChest) {
			return ((LOTRBlockChest) block).getModChestAt(world, i, j, k);
		}
		if (block instanceof BlockEnderChest && !world.getBlock(i, j + 1, k).isNormalCube() && (enderInv = entityplayer.getInventoryEnderChest()) != null && te instanceof TileEntityEnderChest) {
			TileEntityEnderChest enderChest = (TileEntityEnderChest) te;
			if (!world.isRemote) {
				enderInv.func_146031_a(enderChest);
			}
			return enderInv;
		}
		return null;
	}

	public static int getMaxPouchCapacity() {
		return getCapacityForMeta(pouchTypes.length - 1);
	}

	public static int getPouchColor(ItemStack itemstack) {
		int dye = getSavedDyeColor(itemstack);
		if (dye != -1) {
			return dye;
		}
		return 10841676;
	}

	public static int getRandomPouchSize(Random random) {
		float f = random.nextFloat();
		if (f < 0.6f) {
			return 0;
		}
		if (f < 0.9f) {
			return 1;
		}
		return 2;
	}

	public static int getSavedDyeColor(ItemStack itemstack) {
		if (itemstack.getTagCompound() != null && itemstack.getTagCompound().hasKey("PouchColor")) {
			return itemstack.getTagCompound().getInteger("PouchColor");
		}
		return -1;
	}

	public static boolean isHoldingPouch(EntityPlayer entityplayer, int slot) {
		return entityplayer.inventory.getStackInSlot(slot) != null && entityplayer.inventory.getStackInSlot(slot).getItem() instanceof LOTRItemPouch;
	}

	public static boolean isPouchDyed(ItemStack itemstack) {
		return getSavedDyeColor(itemstack) != -1;
	}

	public static void removePouchDye(ItemStack itemstack) {
		if (itemstack.getTagCompound() != null) {
			itemstack.getTagCompound().removeTag("PouchColor");
		}
	}

	public static boolean restockPouches(EntityPlayer player) {
		InventoryPlayer inv = player.inventory;
		Collection<Integer> pouchSlots = new ArrayList<>();
		Collection<Integer> itemSlots = new ArrayList<>();
		for (int i = 0; i < inv.mainInventory.length; i++) {
			ItemStack itemstack = inv.getStackInSlot(i);
			if (itemstack != null) {
				if (itemstack.getItem() instanceof LOTRItemPouch) {
					pouchSlots.add(i);
				} else {
					itemSlots.add(i);
				}
			}
		}
		boolean movedAny = false;
		for (Integer integer : itemSlots) {
			int j = integer;
			ItemStack itemstack = inv.getStackInSlot(j);
			for (Integer integer2 : pouchSlots) {
				int p = integer2;
				ItemStack pouch = inv.getStackInSlot(p);
				int stackSizeBefore = itemstack.stackSize;
				tryAddItemToPouch(pouch, itemstack, true);
				if (itemstack.stackSize != stackSizeBefore) {
					movedAny = true;
				}
				if (itemstack.stackSize <= 0) {
					inv.setInventorySlotContents(j, null);
				}
			}
		}
		return movedAny;
	}

	public static void setPouchColor(ItemStack itemstack, int i) {
		if (itemstack.getTagCompound() == null) {
			itemstack.setTagCompound(new NBTTagCompound());
		}
		itemstack.getTagCompound().setInteger("PouchColor", i);
	}

	public static boolean tryAddItemToPouch(ItemStack pouch, ItemStack itemstack, boolean requireMatchInPouch) {
		if (itemstack != null && itemstack.stackSize > 0) {
			IInventory pouchInv = new LOTRInventoryPouch(pouch);
			for (int i = 0; i < pouchInv.getSizeInventory() && itemstack.stackSize > 0; ++i) {
				int difference;
				ItemStack itemInSlot = pouchInv.getStackInSlot(i);
				if (itemInSlot != null ? itemInSlot.stackSize >= itemInSlot.getMaxStackSize() || itemInSlot.getItem() != itemstack.getItem() || !itemInSlot.isStackable() || itemInSlot.getHasSubtypes() && itemInSlot.getItemDamage() != itemstack.getItemDamage() || !ItemStack.areItemStackTagsEqual(itemInSlot, itemstack) : requireMatchInPouch) {
					continue;
				}
				if (itemInSlot == null) {
					pouchInv.setInventorySlotContents(i, itemstack);
					return true;
				}
				int maxStackSize = itemInSlot.getMaxStackSize();
				if (pouchInv.getInventoryStackLimit() < maxStackSize) {
					maxStackSize = pouchInv.getInventoryStackLimit();
				}
				difference = maxStackSize - itemInSlot.stackSize;
				if (difference > itemstack.stackSize) {
					difference = itemstack.stackSize;
				}
				itemstack.stackSize -= difference;
				itemInSlot.stackSize += difference;
				pouchInv.setInventorySlotContents(i, itemInSlot);
				if (itemstack.stackSize > 0) {
					continue;
				}
				return true;
			}
		}
		return false;
	}
}
