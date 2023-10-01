package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.LOTRCreativeTabs;
import lotr.common.LOTRDate;
import lotr.common.LOTRMod;
import lotr.common.world.LOTRWorldProvider;
import net.minecraft.block.BlockLeaves;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class LOTRBlockLeavesBase extends BlockLeaves {
	public static Collection allLeafBlocks = new ArrayList();
	@SideOnly(Side.CLIENT)
	public IIcon[][] leafIcons;
	public String[] leafNames;
	public String vanillaTextureName;

	public LOTRBlockLeavesBase() {
		this(false, null);
	}

	public LOTRBlockLeavesBase(boolean vanilla, String vname) {
		if (vanilla) {
			setCreativeTab(CreativeTabs.tabDecorations);
			vanillaTextureName = vname;
		} else {
			setCreativeTab(LOTRCreativeTabs.tabDeco);
		}
		allLeafBlocks.add(this);
	}

	public static int getBiomeLeafColor(IBlockAccess world, int i, int j, int k) {
		int totalR = 0;
		int totalG = 0;
		int totalB = 0;
		int count = 0;
		int range = 1;
		for (int i1 = -range; i1 <= range; ++i1) {
			for (int k1 = -range; k1 <= range; ++k1) {
				int biomeColor = world.getBiomeGenForCoords(i + i1, k + k1).getBiomeFoliageColor(i + i1, j, k + k1);
				totalR += (biomeColor & 0xFF0000) >> 16;
				totalG += (biomeColor & 0xFF00) >> 8;
				totalB += biomeColor & 0xFF;
				++count;
			}
		}
		int avgR = totalR / count & 0xFF;
		int avgG = totalG / count & 0xFF;
		int avgB = totalB / count & 0xFF;
		return avgR << 16 | avgG << 8 | avgB;
	}

	public static void setAllGraphicsLevels(boolean flag) {
		for (Object leafBlock : allLeafBlocks) {
			((BlockLeaves) leafBlock).setGraphicsLevel(flag);
		}
	}

	public void addSpecialLeafDrops(List drops, World world, int i, int j, int k, int meta, int fortune) {
	}

	public int calcFortuneModifiedDropChance(int baseChance, int fortune) {
		int chance = baseChance;
		if (fortune > 0) {
			chance -= 2 << fortune;
			chance = Math.max(chance, baseChance / 2);
			chance = Math.max(chance, 1);
		}
		return chance;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int colorMultiplier(IBlockAccess world, int i, int j, int k) {
		return 16777215;
	}

	@Override
	public String[] func_150125_e() {
		return leafNames;
	}

	public String[] getAllLeafNames() {
		return leafNames;
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int i, int j, int k, int meta, int fortune) {
		ArrayList<ItemStack> drops = new ArrayList<>();
		int saplingChanceBase = getSaplingChance(meta & 3);
		int saplingChance = calcFortuneModifiedDropChance(saplingChanceBase, fortune);
		if (world.rand.nextInt(saplingChance) == 0) {
			drops.add(new ItemStack(getItemDropped(meta, world.rand, fortune), 1, damageDropped(meta)));
		}
		addSpecialLeafDrops(drops, world, i, j, k, meta, fortune);
		return drops;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int i, int j) {
		int meta = j & 3;
		if (meta >= leafNames.length) {
			meta = 0;
		}
		return leafIcons[meta][field_150121_P ? 0 : 1];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderColor(int i) {
		return 16777215;
	}

	public int getSaplingChance(int meta) {
		return 20;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		for (int j = 0; j < leafNames.length; ++j) {
			list.add(new ItemStack(item, 1, j));
		}
	}

	@Override
	public String getTextureName() {
		if (vanillaTextureName != null) {
			return vanillaTextureName;
		}
		return super.getTextureName();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconregister) {
		leafIcons = new IIcon[leafNames.length][2];
		for (int i = 0; i < leafNames.length; ++i) {
			IIcon fancy = iconregister.registerIcon(getTextureName() + "_" + leafNames[i] + "_fancy");
			IIcon fast = iconregister.registerIcon(getTextureName() + "_" + leafNames[i] + "_fast");
			leafIcons[i][0] = fancy;
			leafIcons[i][1] = fast;
		}
	}

	public void setLeafNames(String... s) {
		leafNames = s;
		setSeasonal(new boolean[s.length]);
	}

	public void setSeasonal(boolean... b) {
		if (b.length != leafNames.length) {
			throw new IllegalArgumentException("Leaf seasons length must match number of types");
		}
	}

	public boolean shouldOakUseBiomeColor() {
		LOTRDate.Season season = LOTRDate.ShireReckoning.getSeason();
		return season == LOTRDate.Season.SPRING || season == LOTRDate.Season.SUMMER || !(LOTRMod.proxy.getClientWorld().provider instanceof LOTRWorldProvider);
	}
}
