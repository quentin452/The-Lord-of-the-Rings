package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.fac.LOTRFaction;
import lotr.common.world.biome.LOTRBiomeGenMordor;
import lotr.common.world.biome.LOTRBiomeGenMorgulVale;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class LOTRBlockMorgulFlower extends LOTRBlockFlower {
	public LOTRBlockMorgulFlower() {
		float f = 0.125f;
		setFlowerBounds(f, 0.0f, f, 1.0f - f, 0.8f, 1.0f - f);
		setTickRandomly(true);
	}

	@Override
	public boolean canBlockStay(World world, int i, int j, int k) {
		return super.canBlockStay(world, i, j, k) || LOTRBiomeGenMordor.isSurfaceMordorBlock(world, i, j - 1, k);
	}

	public boolean isEntityVulnerable(EntityLivingBase entity) {
		if (LOTRMod.getNPCFaction(entity) == LOTRFaction.MORDOR) {
			return false;
		}
		if (entity instanceof EntityPlayer) {
			float max;
			EntityPlayer entityplayer = (EntityPlayer) entity;
			if (entityplayer.capabilities.isCreativeMode) {
				return false;
			}
			float alignment = LOTRLevelData.getData(entityplayer).getAlignment(LOTRFaction.MORDOR);
			max = 250.0f;
			if (alignment >= max) {
				return false;
			}
			if (alignment > 0.0f) {
				float f = alignment / max;
				f = 1.0f - f;
				return entity.getRNG().nextFloat() < f;
			}
		}
		return true;
	}

	@Override
	public void onEntityCollidedWithBlock(World world, int i, int j, int k, Entity entity) {
		EntityLivingBase living;
		super.onEntityCollidedWithBlock(world, i, j, k, entity);
		if (!world.isRemote && entity instanceof EntityLivingBase && isEntityVulnerable(living = (EntityLivingBase) entity)) {
			int dur = 100;
			living.addPotionEffect(new PotionEffect(Potion.poison.id, dur));
			living.addPotionEffect(new PotionEffect(Potion.blindness.id, dur * 2));
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void randomDisplayTick(World world, int i, int j, int k, Random random) {
		if (random.nextInt(4) == 0) {
			double d = i + MathHelper.randomFloatClamp(random, 0.1f, 0.9f);
			double d1 = j + MathHelper.randomFloatClamp(random, 0.5f, 0.75f);
			double d2 = k + MathHelper.randomFloatClamp(random, 0.1f, 0.9f);
			if (random.nextBoolean()) {
				LOTRMod.proxy.spawnParticle("morgulWater", d, d1, d2, 0.0, 0.0, 0.0);
			} else {
				LOTRMod.proxy.spawnParticle("whiteSmoke", d, d1, d2, 0.0, 0.0, 0.0);
			}
		}
	}

	@Override
	public void updateTick(World world, int i, int j, int k, Random random) {
		super.updateTick(world, i, j, k, random);
		if (!world.isRemote && world.getBiomeGenForCoords(i, k) instanceof LOTRBiomeGenMorgulVale) {
			double range = 5.0;
			AxisAlignedBB aabb = AxisAlignedBB.getBoundingBox(i, j, k, i + 1, j + 1, k + 1).expand(range, range, range);
			List entities = world.getEntitiesWithinAABB(EntityLivingBase.class, aabb);
			for (Object obj : entities) {
				EntityLivingBase entity = (EntityLivingBase) obj;
				if (!isEntityVulnerable(entity)) {
					continue;
				}
				int dur = 200;
				entity.addPotionEffect(new PotionEffect(Potion.confusion.id, dur));
			}
		}
	}
}
