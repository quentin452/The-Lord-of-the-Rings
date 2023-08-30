package lotr.common.world.structure2;

import java.util.*;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import lotr.common.LOTRConfig;
import lotr.common.world.structure.LOTRWorldGenStructureBase;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.WorldEvent;

public class LOTRStructureTimelapse {
	public static List<ThreadTimelapse> allThreads = Collections.synchronizedList(new ArrayList<>());

	public LOTRStructureTimelapse() {
		FMLCommonHandler.instance().bus().register(this);
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	public void onWorldUnload(WorldEvent.Unload event) {
		for (ThreadTimelapse thr : allThreads) {
			thr.interrupt();
		}
		allThreads.clear();
	}

	public static void start(WorldGenerator gen, World world, int i, int j, int k) {
		new ThreadTimelapse(gen, world, i, j, k).start();
	}

	public static class ThreadTimelapse extends Thread {
		public WorldGenerator structureGen;
		public World theWorld;
		public int posX;
		public int posY;
		public int posZ;

		public ThreadTimelapse(WorldGenerator gen, World world, int i, int j, int k) {
			structureGen = gen;
			theWorld = world;
			posX = i;
			posY = j;
			posZ = k;
		}

		public void onBlockSet() {
			if (LOTRConfig.strTimelapse) {
				try {
					Thread.sleep(LOTRConfig.strTimelapseInterval);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		@Override
		public void run() {
			if (structureGen instanceof LOTRWorldGenStructureBase2) {
				LOTRWorldGenStructureBase2 str2 = (LOTRWorldGenStructureBase2) structureGen;
				str2.threadTimelapse = this;
				str2.generateWithSetRotation(theWorld, theWorld.rand, posX, posY, posZ, str2.usingPlayerRotation());
			} else if (structureGen instanceof LOTRWorldGenStructureBase) {
				LOTRWorldGenStructureBase str = (LOTRWorldGenStructureBase) structureGen;
				str.threadTimelapse = this;
				str.generate(theWorld, theWorld.rand, posX, posY, posZ);
			}
			allThreads.remove(this);
		}

		@Override
		public void start() {
			setDaemon(true);
			super.start();
			allThreads.add(this);
		}
	}

}
