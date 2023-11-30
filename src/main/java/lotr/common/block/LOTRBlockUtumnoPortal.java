package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.LOTRDimension;
import lotr.common.tileentity.LOTRTileEntityUtumnoPortal;
import lotr.common.world.LOTRTeleporter;
import lotr.common.world.LOTRUtumnoLevel;
import lotr.common.world.LOTRWorldProviderUtumno;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class LOTRBlockUtumnoPortal extends BlockContainer {
	public LOTRBlockUtumnoPortal() {
		super(Material.portal);
		setHardness(-1.0f);
		setResistance(Float.MAX_VALUE);
		setStepSound(Block.soundTypeStone);
	}

	@Override
	public void addCollisionBoxesToList(World world, int i, int j, int k, AxisAlignedBB aabb, List list, Entity entity) {
	}

	@Override
	public TileEntity createNewTileEntity(World world, int i) {
		return new LOTRTileEntityUtumnoPortal();
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		return Blocks.portal.getIcon(i, j);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public Item getItem(World world, int i, int j, int k) {
		return Item.getItemById(0);
	}

	@Override
	public int getRenderType() {
		return -1;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public void onBlockAdded(World world, int i, int j, int k) {
		if (world.provider.dimensionId != LOTRDimension.MIDDLE_EARTH.dimensionID) {
			world.setBlockToAir(i, j, k);
		}
	}

    @Override
    public void onEntityCollidedWithBlock(World world, int i, int j, int k, Entity entity) {
        entity.setInWeb();
        TileEntity te = world.getTileEntity(i, j, k);
        if (te instanceof LOTRTileEntityUtumnoPortal) {
            ((LOTRTileEntityUtumnoPortal) te).transferEntity(entity);
        }
        if (entity instanceof EntityPlayerMP && entity.ridingEntity == null && entity.riddenByEntity == null) {
            EntityPlayerMP thePlayer = (EntityPlayerMP) entity;
            MinecraftServer server = MinecraftServer.getServer();
            if (thePlayer.dimension == LOTRDimension.MIDDLE_EARTH.dimensionID) {
                if (thePlayer.timeUntilPortal > 0) {
                    thePlayer.timeUntilPortal = 10;
                } else {
                    thePlayer.timeUntilPortal = 10;
                    thePlayer.mcServer.getConfigurationManager()
                        .transferPlayerToDimension(thePlayer,
                            LOTRDimension.UTUMNO.dimensionID,
                            new LOTRTeleporter(
                                server.worldServerForDimension(
                                    LOTRDimension.UTUMNO.dimensionID), true));
                }
            } else if (thePlayer.dimension == LOTRDimension.UTUMNO.dimensionID) {
                i = MathHelper.floor_double(thePlayer.posX);
                j = MathHelper.floor_double(thePlayer.boundingBox.minY);
                k = MathHelper.floor_double(thePlayer.posZ);
                int range = 32;
                for (int l = 0; l < 60; ++l) {
                    int i1 = i + world.rand.nextInt(range) - world.rand.nextInt(range);
                    int j1 = j + world.rand.nextInt(range) - world.rand.nextInt(range);
                    int k1 = k + world.rand.nextInt(range) - world.rand.nextInt(range);
                    if (LOTRUtumnoLevel.forY(j1) == LOTRUtumnoLevel.ICE) {
                        Block block = world.getBlock(i1, j1, k1);
                        int meta = world.getBlockMetadata(i1, j1, k1);
                        if (block.getMaterial() == Material.water && meta == 0) {
                            world.setBlock(i1, j1, k1, Blocks.ice, 0, 3);
                        }
                    }
                    if (LOTRUtumnoLevel.forY(j1) != LOTRUtumnoLevel.FIRE) {
                        continue;
                    }
                    Block block = world.getBlock(i1, j1, k1);
                    int meta = world.getBlockMetadata(i1, j1, k1);
                    if (block.getMaterial() != Material.water || meta != 0) {
                        continue;
                    }
                    world.setBlock(i1, j1, k1, Blocks.air, 0, 3);
                    LOTRWorldProviderUtumno.doEvaporateFX(world, i1, j1, k1);
                }
            }
        }
    }

    @Override
	public int quantityDropped(Random par1Random) {
		return 0;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconregister) {
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
}
