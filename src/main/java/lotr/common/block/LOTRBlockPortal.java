package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.LOTRConfig;
import lotr.common.LOTRDimension;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.fac.LOTRFaction;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.Teleporter;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;

import java.util.List;
import java.util.Random;

public abstract class LOTRBlockPortal extends BlockContainer {
	public LOTRFaction[] portalFactions;
	public Class teleporterClass;

	protected LOTRBlockPortal(LOTRFaction[] factions, Class c) {
		super(Material.portal);
		portalFactions = factions;
		teleporterClass = c;
	}

	@Override
	public void addCollisionBoxesToList(World world, int i, int j, int k, AxisAlignedBB aabb, List list, Entity entity) {
	}

	public boolean canUsePortal(Entity entity) {
		if (entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entity;
			if (player.capabilities.isCreativeMode) {
				return true;
			}
			for (LOTRFaction faction : portalFactions) {
				if (LOTRLevelData.getData(player).getAlignment(faction) < 1.0f) {
					continue;
				}
				return true;
			}
		} else {
			for (LOTRFaction faction : portalFactions) {
				if (LOTRMod.getNPCFaction(entity).isBadRelation(faction) || entity.ridingEntity != null || entity.riddenByEntity != null || entity.timeUntilPortal != 0) {
					continue;
				}
				return true;
			}
		}
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int i, int j) {
		return Blocks.portal.getIcon(i, j);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Item getItem(World world, int i, int j, int k) {
		return Item.getItemById(0);
	}

	public Teleporter getPortalTeleporter(WorldServer world) {
		for (Teleporter obj : world.customTeleporters) {
			if (!teleporterClass.isInstance(obj)) {
				continue;
			}
			return obj;
		}
		Teleporter teleporter = null;
		try {
			teleporter = (Teleporter) teleporterClass.getConstructor(WorldServer.class).newInstance(world);
		} catch (Exception e) {
			e.printStackTrace();
		}
		world.customTeleporters.add(teleporter);
		return teleporter;
	}

	@Override
	public int getRenderType() {
		return -1;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	public abstract boolean isValidPortalLocation(World var1, int var2, int var3, int var4, boolean var5);

	@Override
	public void onBlockAdded(World world, int i, int j, int k) {
		if (world.provider.dimensionId != 0 && world.provider.dimensionId != LOTRDimension.MIDDLE_EARTH.dimensionID) {
			world.setBlockToAir(i, j, k);
		}
	}

	@Override
	public void onEntityCollidedWithBlock(World world, int i, int j, int k, Entity entity) {
		if (!LOTRConfig.enablePortals) {
			return;
		}
		if (canUsePortal(entity)) {
			if (entity.ridingEntity == null && entity.riddenByEntity == null) {
				if (entity instanceof EntityPlayer) {
					setPlayerInPortal((EntityPlayer) entity);
				} else {
					transferEntity(entity, world);
				}
			}
		} else if (!world.isRemote) {
			entity.setFire(4);
			entity.attackEntityFrom(DamageSource.inFire, 2.0f);
			world.playSoundAtEntity(entity, "random.fizz", 0.5f, 1.5f + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.5f);
		}
	}

	@Override
	public int quantityDropped(Random par1Random) {
		return 0;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int i, int j, int k, Random random) {
		if (random.nextInt(100) == 0) {
			world.playSound(i + 0.5, j + 0.5, k + 0.5, "portal.portal", 0.5f, random.nextFloat() * 0.4f + 0.8f, false);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconregister) {
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int i, int j, int k) {
		float f = 0.0625f;
		setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, f, 1.0f);
	}

	public abstract void setPlayerInPortal(EntityPlayer var1);

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess world, int i, int j, int k, int side) {
		return side == 0 && super.shouldSideBeRendered(world, i, j, k, side);
	}

	public void transferEntity(Entity entity, World world) {
		if (!world.isRemote) {
			int dimension = 0;
			if (entity.dimension == 0) {
				dimension = LOTRDimension.MIDDLE_EARTH.dimensionID;
			} else if (entity.dimension == LOTRDimension.MIDDLE_EARTH.dimensionID) {
				dimension = 0;
			}
			LOTRMod.transferEntityToDimension(entity, dimension, getPortalTeleporter(DimensionManager.getWorld(dimension)));
		}
	}
}
