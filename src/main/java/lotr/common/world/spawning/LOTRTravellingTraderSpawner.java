package lotr.common.world.spawning;

import java.util.Random;

import cpw.mods.fml.common.eventhandler.Event;
import lotr.common.LOTRLevelData;
import lotr.common.entity.LOTREntities;
import lotr.common.entity.npc.*;
import lotr.common.world.biome.LOTRBiome;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.event.ForgeEventFactory;

public class LOTRTravellingTraderSpawner {
	public static Random rand = new Random();
	public Class theEntityClass;
	public String entityClassName;
	public int timeUntilTrader;

	public LOTRTravellingTraderSpawner(Class<? extends LOTREntityNPC> entityClass) {
		theEntityClass = entityClass;
		entityClassName = LOTREntities.getStringFromClass(theEntityClass);
	}

	public void performSpawning(World world) {
		if (timeUntilTrader > 0) {
			--timeUntilTrader;
		} else if (world.rand.nextInt(1000) == 0) {
			boolean spawned = false;
			LOTREntityNPC entityTrader = (LOTREntityNPC) EntityList.createEntityByName(LOTREntities.getStringFromClass(theEntityClass), world);
			LOTRTravellingTrader trader = (LOTRTravellingTrader) entityTrader;
			block0:
			for (int players = 0; players < world.playerEntities.size(); ++players) {
				EntityPlayer entityplayer = (EntityPlayer) world.playerEntities.get(players);
				if (LOTRLevelData.getData(entityplayer).getAlignment(entityTrader.getFaction()) < 0.0f) {
					continue;
				}
				for (int attempts = 0; attempts < 16; ++attempts) {
					int k;
					float angle = world.rand.nextFloat() * 360.0f;
					int i = MathHelper.floor_double(entityplayer.posX) + MathHelper.floor_double(MathHelper.sin(angle) * (48 + world.rand.nextInt(33)));
					BiomeGenBase biome = world.getBiomeGenForCoords(i, k = MathHelper.floor_double(entityplayer.posZ) + MathHelper.floor_double(MathHelper.cos(angle) * (48 + world.rand.nextInt(33))));
					if (!(biome instanceof LOTRBiome) || !((LOTRBiome) biome).canSpawnTravellingTrader(theEntityClass)) {
						continue;
					}
					int j = world.getHeightValue(i, k);
					Block block = world.getBlock(i, j - 1, k);
					if (j <= 62 || block != biome.topBlock && block != biome.fillerBlock || world.getBlock(i, j, k).isNormalCube() || world.getBlock(i, j + 1, k).isNormalCube()) {
						continue;
					}
					entityTrader.setLocationAndAngles(i + 0.5, j, k + 0.5, world.rand.nextFloat() * 360.0f, 0.0f);
					entityTrader.liftSpawnRestrictions = true;
					Event.Result canSpawn = ForgeEventFactory.canEntitySpawn(entityTrader, world, (float) entityTrader.posX, (float) entityTrader.posY, (float) entityTrader.posZ);
					if (canSpawn != Event.Result.ALLOW && (canSpawn != Event.Result.DEFAULT || !entityTrader.getCanSpawnHere())) {
						continue;
					}
					entityTrader.liftSpawnRestrictions = false;
					entityTrader.spawnRidingHorse = false;
					world.spawnEntityInWorld(entityTrader);
					entityTrader.onSpawnWithEgg(null);
					entityTrader.isNPCPersistent = true;
					entityTrader.setShouldTraderRespawn(false);
					trader.startTraderVisiting(entityplayer);
					spawned = true;
					timeUntilTrader = getRandomTraderTime();
					LOTRLevelData.markDirty();
					break block0;
				}
			}
			if (!spawned) {
				timeUntilTrader = 200 + world.rand.nextInt(400);
			}
		}
	}

	public void readFromNBT(NBTTagCompound nbt) {
		timeUntilTrader = nbt.hasKey("TraderTime") ? nbt.getInteger("TraderTime") : getRandomTraderTime();
	}

	public void writeToNBT(NBTTagCompound nbt) {
		nbt.setInteger("TraderTime", timeUntilTrader);
	}

	public static int getRandomTraderTime() {
		float minHours = 0.8f;
		float maxHours = 10.0f;
		return MathHelper.getRandomIntegerInRange(rand, (int) (minHours * 3600.0f) * 20, (int) (maxHours * 3600.0f) * 20);
	}
}
