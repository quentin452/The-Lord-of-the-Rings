package lotr.common.block;

import cpw.mods.fml.relauncher.*;
import lotr.common.*;
import lotr.common.tileentity.LOTRTileEntityCommandTable;
import lotr.common.world.map.LOTRConquestGrid;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.*;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class LOTRBlockCommandTable extends BlockContainer {
	@SideOnly(Side.CLIENT)
	public IIcon topIcon;
	@SideOnly(Side.CLIENT)
	public IIcon sideIcon;

	public LOTRBlockCommandTable() {
		super(Material.iron);
		setCreativeTab(LOTRCreativeTabs.tabUtil);
		setHardness(2.5f);
		setStepSound(Block.soundTypeMetal);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int i) {
		return new LOTRTileEntityCommandTable();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int i, int j) {
		if (i == 1 || i == 0) {
			return topIcon;
		}
		return sideIcon;
	}

	@Override
	public int getRenderType() {
		return LOTRMod.proxy.getCommandTableRenderID();
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int side, float f, float f1, float f2) {
		LOTRTileEntityCommandTable table;
		if (entityplayer.isSneaking() && (table = (LOTRTileEntityCommandTable) world.getTileEntity(i, j, k)) != null) {
			if (!world.isRemote) {
				table.toggleZoomExp();
			}
			return true;
		}
		ItemStack itemstack = entityplayer.getCurrentEquippedItem();
		if (itemstack != null && itemstack.getItem() instanceof LOTRSquadrons.SquadronItem) {
			if (!world.isRemote) {
				LOTRCommonProxy.sendClientsideGUI((EntityPlayerMP) entityplayer, 33, 0, 0, 0);
				world.playSoundEffect(i + 0.5, j + 0.5, k + 0.5, stepSound.getBreakSound(), (stepSound.getVolume() + 1.0f) / 2.0f, stepSound.getPitch() * 0.5f);
			}
			return true;
		}
		if (LOTRConquestGrid.conquestEnabled(world)) {
			if (!world.isRemote) {
				LOTRCommonProxy.sendClientsideGUI((EntityPlayerMP) entityplayer, 60, 0, 0, 0);
				world.playSoundEffect(i + 0.5, j + 0.5, k + 0.5, stepSound.getBreakSound(), (stepSound.getVolume() + 1.0f) / 2.0f, stepSound.getPitch() * 0.5f);
			}
			return true;
		}
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconregister) {
		sideIcon = iconregister.registerIcon(getTextureName() + "_side");
		topIcon = iconregister.registerIcon(getTextureName() + "_top");
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
}
