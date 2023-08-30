package lotr.common.block;

import java.util.Random;

import cpw.mods.fml.relauncher.*;
import lotr.common.*;
import lotr.common.fac.LOTRFaction;
import lotr.common.tileentity.LOTRTileEntityMorgulPortal;
import lotr.common.world.LOTRTeleporterMorgulPortal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class LOTRBlockMorgulPortal extends LOTRBlockPortal {
	public LOTRBlockMorgulPortal() {
		super(new LOTRFaction[]{LOTRFaction.MORDOR, LOTRFaction.ANGMAR, LOTRFaction.DOL_GULDUR}, LOTRTeleporterMorgulPortal.class);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int i) {
		return new LOTRTileEntityMorgulPortal();
	}

	@Override
	public boolean isValidPortalLocation(World world, int i, int j, int k, boolean portalAlreadyMade) {
		for (int i1 = i - 2; i1 <= i + 2; ++i1) {
			for (int k1 = k - 2; k1 <= k + 2; ++k1) {
				if (Math.abs(i1 - i) == 2 && Math.abs(k1 - k) == 2) {
					for (int j1 = j + 1; j1 <= j + 3; ++j1) {
						if (world.getBlock(i1, j1, k1) == LOTRMod.guldurilBrick) {
							continue;
						}
						return false;
					}
					continue;
				}
				if (Math.abs(i1 - i) == 2 || Math.abs(k1 - k) == 2 ? LOTRMod.isOpaque(world, i1, j, k1) : world.getBlock(i1, j, k1) == (portalAlreadyMade ? LOTRMod.morgulPortal : Blocks.lava) && LOTRMod.isOpaque(world, i1, j - 1, k1)) {
					continue;
				}
				return false;
			}
		}
		return true;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void randomDisplayTick(World world, int i, int j, int k, Random random) {
		double d = i + random.nextFloat();
		double d1 = j + 0.8f;
		double d2 = k + random.nextFloat();
		double d3 = -0.05 + random.nextFloat() * 0.1;
		double d4 = 0.1 + random.nextFloat() * 0.1;
		double d5 = -0.05 + random.nextFloat() * 0.1;
		LOTRMod.proxy.spawnParticle("morgulPortal", d, d1, d2, d3, d4, d5);
	}

	@Override
	public void setPlayerInPortal(EntityPlayer entityplayer) {
		LOTRMod.proxy.setInMorgulPortal(entityplayer);
		if (!entityplayer.worldObj.isRemote) {
			LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.useMorgulPortal);
		}
	}
}
