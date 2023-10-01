package lotr.common.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.LOTRCreativeTabs;
import lotr.common.entity.item.LOTREntityBossTrophy;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LOTRItemBossTrophy extends Item {
	@SideOnly(Side.CLIENT)
	public IIcon[] trophyIcons;

	public LOTRItemBossTrophy() {
		setCreativeTab(LOTRCreativeTabs.tabDeco);
		setMaxStackSize(1);
		setMaxDamage(0);
		setHasSubtypes(true);
	}

	public static TrophyType getTrophyType(int i) {
		return TrophyType.forID(i);
	}

	public static TrophyType getTrophyType(ItemStack itemstack) {
		if (itemstack.getItem() instanceof LOTRItemBossTrophy) {
			return getTrophyType(itemstack.getItemDamage());
		}
		return null;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIconFromDamage(int i) {
		if (i >= trophyIcons.length) {
			i = 0;
		}
		return trophyIcons[i];
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		for (TrophyType type : TrophyType.trophyTypes) {
			list.add(new ItemStack(item, 1, type.trophyID));
		}
	}

	@Override
	public String getUnlocalizedName(ItemStack itemstack) {
		return getUnlocalizedName() + "." + getTrophyType(itemstack).trophyName;
	}

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int side, float f, float f1, float f2) {
		TrophyType trophyType = getTrophyType(itemstack);
		Block.SoundType blockSound = Blocks.stone.stepSound;
		if (world.getBlock(i, j, k).isReplaceable(world, i, j, k)) {
			side = 1;
		} else if (side == 1) {
			++j;
		}
		if (side == 0) {
			return false;
		}
		if (side == 1) {
			if (!entityplayer.canPlayerEdit(i, j, k, 1, itemstack)) {
				return false;
			}
			Block block = world.getBlock(i, j - 1, k);
			world.getBlockMetadata(i, j - 1, k);
			if (block.isSideSolid(world, i, j - 1, k, ForgeDirection.UP) && !world.isRemote) {
				LOTREntityBossTrophy trophy = new LOTREntityBossTrophy(world);
				trophy.setLocationAndAngles(i + 0.5f, j, k + 0.5f, 180.0f - entityplayer.rotationYaw % 360.0f, 0.0f);
				trophy.setTrophyHanging(false);
				if (world.checkNoEntityCollision(trophy.boundingBox) && world.getCollidingBoundingBoxes(trophy, trophy.boundingBox).isEmpty() && !world.isAnyLiquid(trophy.boundingBox)) {
					trophy.setTrophyType(trophyType);
					world.spawnEntityInWorld(trophy);
					world.playSoundAtEntity(trophy, blockSound.func_150496_b(), (blockSound.getVolume() + 1.0f) / 2.0f, blockSound.getPitch() * 0.8f);
					--itemstack.stackSize;
					return true;
				}
				trophy.setDead();
			}
		} else {
			if (!entityplayer.canPlayerEdit(i, j, k, side, itemstack)) {
				return false;
			}
			if (!world.isRemote) {
				int direction = Direction.facingToDirection[side];
				LOTREntityBossTrophy trophy = new LOTREntityBossTrophy(world);
				trophy.setLocationAndAngles(i + Direction.offsetX[direction] + 0.5f, j, k + Direction.offsetZ[direction] + 0.5f, direction * 90.0f, 0.0f);
				trophy.setTrophyHanging(true);
				trophy.setTrophyFacing(direction);
				if (world.checkNoEntityCollision(trophy.boundingBox) && !world.isAnyLiquid(trophy.boundingBox) && trophy.hangingOnValidSurface()) {
					trophy.setTrophyType(trophyType);
					world.spawnEntityInWorld(trophy);
					world.playSoundAtEntity(trophy, blockSound.func_150496_b(), (blockSound.getVolume() + 1.0f) / 2.0f, blockSound.getPitch() * 0.8f);
					--itemstack.stackSize;
					return true;
				}
				trophy.setDead();
			}
		}
		return false;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IIconRegister iconregister) {
		trophyIcons = new IIcon[TrophyType.trophyTypes.size()];
		for (int i = 0; i < trophyIcons.length; ++i) {
			trophyIcons[i] = iconregister.registerIcon(getIconString() + "_" + TrophyType.trophyTypes.get(i).trophyName);
		}
	}

	public enum TrophyType {
		MOUNTAIN_TROLL_CHIEFTAIN(0, "mtc"), MALLORN_ENT(1, "mallornEnt");

		public static List<TrophyType> trophyTypes = new ArrayList<>();
		public static Map<Integer, TrophyType> trophyForID = new HashMap<>();

		static {
			for (TrophyType t : values()) {
				trophyTypes.add(t);
				trophyForID.put(t.trophyID, t);
			}
		}

		public int trophyID;

		public String trophyName;

		TrophyType(int i, String s) {
			trophyID = i;
			trophyName = s;
		}

		public static TrophyType forID(int ID) {
			return trophyForID.get(ID);
		}
	}

}
