package lotr.common;

import cpw.mods.fml.common.FMLLog;
import lotr.common.world.LOTRWorldInfo;
import lotr.common.world.LOTRWorldProvider;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldServer;

import java.io.File;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class LOTRTime {
	public static int DAY_LENGTH = 48000;
	public static long totalTime;
	public static long worldTime;
	public static boolean needsLoad = true;

	public static void addWorldTime(long time) {
		worldTime += time;
	}

	public static void advanceToMorning() {
		long l = worldTime + DAY_LENGTH;
		worldTime = l - l % DAY_LENGTH;
	}

	public static File getTimeDat() {
		return new File(LOTRLevelData.getOrCreateLOTRDir(), "LOTRTime.dat");
	}

	public static void load() {
		try {
			NBTTagCompound timeData = LOTRLevelData.loadNBTFromFile(getTimeDat());
			totalTime = timeData.getLong("LOTRTotalTime");
			worldTime = timeData.getLong("LOTRWorldTime");
			needsLoad = false;
			save();
		} catch (Exception e) {
			FMLLog.severe("Error loading LOTR time data");
			e.printStackTrace();
		}
	}

	public static void save() {
		try {
			File time_dat = getTimeDat();
			if (!time_dat.exists()) {
				CompressedStreamTools.writeCompressed(new NBTTagCompound(), Files.newOutputStream(time_dat.toPath()));
			}
			NBTTagCompound timeData = new NBTTagCompound();
			timeData.setLong("LOTRTotalTime", totalTime);
			timeData.setLong("LOTRWorldTime", worldTime);
			LOTRLevelData.saveNBTToFile(time_dat, timeData);
		} catch (Exception e) {
			FMLLog.severe("Error saving LOTR time data");
			e.printStackTrace();
		}
	}

	public static void setWorldTime(long time) {
		worldTime = time;
	}


    private static final Map<Integer, LOTRWorldInfo> dimensionInfoMap = new HashMap<>();

    public static void update() {
        MinecraftServer server = MinecraftServer.getServer();
        WorldServer overworld = server.worldServerForDimension(0);
        if (LOTRMod.doDayCycle(overworld)) {
            ++worldTime;
        }
        ++totalTime;

        for (WorldServer world : server.worldServers) {
            if (world.provider instanceof LOTRWorldProvider) {
                int dimensionId = world.provider.dimensionId;
                LOTRWorldInfo worldInfo = dimensionInfoMap.getOrDefault(dimensionId, new LOTRWorldInfo(world.getWorldInfo()));
                worldInfo.lotr_setTotalTime(totalTime);
                worldInfo.lotr_setWorldTime(worldTime);
                dimensionInfoMap.put(dimensionId, worldInfo);
            }
        }
    }
}
