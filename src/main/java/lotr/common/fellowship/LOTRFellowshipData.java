package lotr.common.fellowship;

import java.io.File;
import java.util.*;

import cpw.mods.fml.common.FMLLog;
import lotr.common.LOTRLevelData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;

public class LOTRFellowshipData {
	public static Map<UUID, LOTRFellowship> fellowshipMap = new HashMap<>();
	public static boolean needsLoad = true;
	public static boolean doFullClearing;

	public static void addFellowship(LOTRFellowship fs) {
		if (!fellowshipMap.containsKey(fs.getFellowshipID())) {
			fellowshipMap.put(fs.getFellowshipID(), fs);
		}
	}

	public static boolean anyDataNeedsSave() {
		for (LOTRFellowship fs : fellowshipMap.values()) {
			if (!fs.needsSave()) {
				continue;
			}
			return true;
		}
		return false;
	}

	public static void destroyAllFellowshipData() {
		fellowshipMap.clear();
	}

	public static LOTRFellowship getActiveFellowship(UUID fsID) {
		LOTRFellowship fs = getFellowship(fsID);
		if (fs != null && fs.isDisbanded()) {
			return null;
		}
		return fs;
	}

	public static LOTRFellowship getFellowship(UUID fsID) {
		LOTRFellowship fs = fellowshipMap.get(fsID);
		if (fs == null && (fs = loadFellowship(fsID)) != null) {
			fellowshipMap.put(fsID, fs);
		}
		return fs;
	}

	public static File getFellowshipDat(UUID fsID) {
		return new File(getFellowshipDir(), fsID.toString() + ".dat");
	}

	@SuppressWarnings("ResultOfMethodCallIgnored")
	public static File getFellowshipDir() {
		File fsDir = new File(LOTRLevelData.getOrCreateLOTRDir(), "fellowships");
		if (!fsDir.exists()) {
			fsDir.mkdirs();
		}
		return fsDir;
	}

	public static void loadAll() {
		try {
			destroyAllFellowshipData();
			needsLoad = false;
			saveAll();
		} catch (Exception e) {
			FMLLog.severe("Error loading LOTR fellowship data");
			e.printStackTrace();
		}
	}

	public static LOTRFellowship loadFellowship(UUID fsID) {
		File fsDat = getFellowshipDat(fsID);
		try {
			NBTTagCompound nbt = LOTRLevelData.loadNBTFromFile(fsDat);
			if (nbt.hasNoTags()) {
				return null;
			}
			LOTRFellowship fs = new LOTRFellowship(fsID);
			fs.load(nbt);
			return fs;
		} catch (Exception e) {
			FMLLog.severe("Error loading LOTR fellowship data for %s", fsDat.getName());
			e.printStackTrace();
			return null;
		}
	}

	public static void saveAll() {
		try {
			for (LOTRFellowship fs : fellowshipMap.values()) {
				if (!fs.needsSave()) {
					continue;
				}
				saveFellowship(fs);
			}
		} catch (Exception e) {
			FMLLog.severe("Error saving LOTR fellowship data");
			e.printStackTrace();
		}
	}

	public static void saveAndClearFellowship(LOTRFellowship fs) {
		if (fellowshipMap.containsValue(fs)) {
			saveFellowship(fs);
			fellowshipMap.remove(fs.getFellowshipID());
		} else {
			FMLLog.severe("Attempted to clear LOTR fellowship data for %s; no data found", fs.getFellowshipID());
		}
	}

	public static void saveAndClearUnusedFellowships() {
		if (doFullClearing) {
			Collection<LOTRFellowship> clearing = new ArrayList<>();
			for (LOTRFellowship fs : fellowshipMap.values()) {
				boolean foundMember = false;
				for (Object player : MinecraftServer.getServer().getConfigurationManager().playerEntityList) {
					EntityPlayer entityplayer = (EntityPlayer) player;
					if (!fs.containsPlayer(entityplayer.getUniqueID())) {
						continue;
					}
					foundMember = true;
					break;
				}
				if (foundMember) {
					continue;
				}
				clearing.add(fs);
			}
			for (LOTRFellowship fs : clearing) {
				saveAndClearFellowship(fs);
			}
		}
	}

	public static void saveFellowship(LOTRFellowship fs) {
		try {
			NBTTagCompound nbt = new NBTTagCompound();
			fs.save(nbt);
			LOTRLevelData.saveNBTToFile(getFellowshipDat(fs.getFellowshipID()), nbt);
		} catch (Exception e) {
			FMLLog.severe("Error saving LOTR fellowship data for %s", fs.getFellowshipID());
			e.printStackTrace();
		}
	}
}
