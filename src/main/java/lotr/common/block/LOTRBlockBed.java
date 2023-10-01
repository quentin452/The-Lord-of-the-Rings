package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.BlockDirectional;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.util.Direction;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class LOTRBlockBed extends BlockBed {
	public Item bedItem;
	public Block bedBottomBlock;
	public int bedBottomMetadata;
	@SideOnly(Side.CLIENT)
	public IIcon[] bedIconsEnd;
	@SideOnly(Side.CLIENT)
	public IIcon[] bedIconsSide;
	@SideOnly(Side.CLIENT)
	public IIcon[] bedIconsTop;

	public LOTRBlockBed(Block block, int k) {
		bedBottomBlock = block;
		bedBottomMetadata = k;
		setHardness(0.2f);
		setStepSound(Block.soundTypeWood);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		int i1;
		if (i == 0) {
			return bedBottomBlock.getIcon(0, bedBottomMetadata);
		}
		int k = BlockDirectional.getDirection(j);
		int l = Direction.bedDirection[k][i];
		i1 = BlockBed.isBlockHeadOfBed(j) ? 1 : 0;
		return (i1 != 1 || l != 2) && (i1 != 0 || l != 3) ? l != 5 && l != 4 ? bedIconsTop[i1] : bedIconsSide[i1] : bedIconsEnd[i1];
	}

	@SideOnly(Side.CLIENT)
	@Override
	public Item getItem(World world, int i, int j, int k) {
		return bedItem;
	}

	@Override
	public Item getItemDropped(int i, Random random, int j) {
		return BlockBed.isBlockHeadOfBed(i) ? null : bedItem;
	}

	@Override
	public boolean isBed(IBlockAccess world, int i, int j, int k, EntityLivingBase entity) {
		return true;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconregister) {
		bedIconsTop = new IIcon[]{iconregister.registerIcon(getTextureName() + "_feet_top"), iconregister.registerIcon(getTextureName() + "_head_top")};
		bedIconsEnd = new IIcon[]{iconregister.registerIcon(getTextureName() + "_feet_end"), iconregister.registerIcon(getTextureName() + "_head_end")};
		bedIconsSide = new IIcon[]{iconregister.registerIcon(getTextureName() + "_feet_side"), iconregister.registerIcon(getTextureName() + "_head_side")};
	}
}
