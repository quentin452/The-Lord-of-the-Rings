package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.client.render.LOTRConnectedTextures;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRLevelData;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class LOTRBlockGateDwarven extends LOTRBlockGate {
	public LOTRBlockGateDwarven() {
		super(Material.rock, false);
		setHardness(4.0f);
		setResistance(10.0f);
		setStepSound(Block.soundTypeStone);
		setFullBlock();
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(IBlockAccess world, int i, int j, int k, int side) {
		boolean open = LOTRBlockGate.isGateOpen(world, i, j, k);
		if (open) {
			return LOTRConnectedTextures.getConnectedIconBlock(this, world, i, j, k, side, false);
		}
		return Blocks.stone.getIcon(side, 0);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		return Blocks.stone.getIcon(i, 0);
	}

	@Override
	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int side, float f, float f1, float f2) {
		boolean flag = super.onBlockActivated(world, i, j, k, entityplayer, side, f, f1, f2);
		if (flag && !world.isRemote) {
			LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.useDwarvenDoor);
		}
		return flag;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconregister) {
		LOTRConnectedTextures.registerNonConnectedGateIcons(iconregister, this, 0, Blocks.stone.getIcon(0, 0).getIconName());
	}
}
