package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockOldLeaf;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.IBlockAccess;

public class LOTRBlockLeavesVanilla1 extends LOTRBlockLeavesBase {
	public LOTRBlockLeavesVanilla1() {
		super(true, "lotr:leavesV1");
		setLeafNames("oak", "spruce", "birch", "jungle");
		setSeasonal(true, false, true, false);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public int colorMultiplier(IBlockAccess world, int i, int j, int k) {
		int meta = world.getBlockMetadata(i, j, k) & 3;
		if (meta == 0) {
			return LOTRBlockLeavesBase.getBiomeLeafColor(world, i, j, k);
		}
		return super.colorMultiplier(world, i, j, k);
	}

	@Override
	public String[] func_150125_e() {
		return BlockOldLeaf.field_150131_O;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public int getRenderColor(int i) {
		int meta = i & 3;
		if (meta == 0) {
			return ColorizerFoliage.getFoliageColorBasic();
		}
		return super.getRenderColor(i);
	}

	@Override
	public int getSaplingChance(int meta) {
		if (meta == 3) {
			return 30;
		}
		return super.getSaplingChance(meta);
	}
}
