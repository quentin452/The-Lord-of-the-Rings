package lotr.common.world.map;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import com.google.common.io.Files;

import cpw.mods.fml.common.FMLLog;
import lotr.common.LOTRConfig;
import lotr.common.fellowship.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.DimensionManager;

public class LOTRCustomWaypointLogger {
	public static Charset CHARSET = StandardCharsets.UTF_8;
	public static DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("MM-dd", Locale.ROOT);
	public static DateTimeFormatter MONTH_DATE_FORMAT = DateTimeFormatter.ofPattern("HH:mm:ss", Locale.ROOT);
	public static DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.ROOT);

	@SuppressWarnings("ResultOfMethodCallIgnored")
	public static void log(String function, EntityPlayer entityplayer, LOTRCustomWaypoint cwp) {
		if (!LOTRConfig.cwpLog) {
			return;
		}
		try {
			File logFile;
			File dupeLogDir;
			LocalDateTime now = LocalDateTime.now();
			StringBuilder logLine = new StringBuilder()
					.append(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s",
							now.format(DATE_FORMAT),
							now.format(MONTH_DATE_FORMAT),
							function,
							entityplayer.getCommandSenderName(),
							entityplayer.getPersistentID(),
							cwp.getCodeName(),
							cwp.getXCoord(),
							cwp.getYCoordSaved(),
							cwp.getZCoord(),
							cwp.isShared(),
							cwp.isShared() ? cwp.getSharingPlayerName() : "N/A",
							cwp.isShared() ? cwp.getSharingPlayerID() : "N/A"));
			if (cwp.isShared()) {
				List<UUID> fsIDs = cwp.getSharedFellowshipIDs();
				for (UUID id : fsIDs) {
					LOTRFellowship fellowship = LOTRFellowshipData.getActiveFellowship(id);
					if (fellowship == null || !fellowship.containsPlayer(entityplayer.getUniqueID())) {
						continue;
					}
					logLine.append(",");
					logLine.append(fellowship.getName());
				}
			}
			if (!(dupeLogDir = new File(DimensionManager.getCurrentSaveRootDirectory(), "lotr_cwp_logs")).exists()) {
				dupeLogDir.mkdirs();
			}
			if (!(logFile = new File(dupeLogDir, now.format(DATE_FORMAT) + ".csv")).exists()) {
				Files.append("date,time,function,username,UUID,wp_name,x,y,z,shared,sharer_name,sharer_UUID,common_fellowships" + System.lineSeparator(), logFile, CHARSET);
			}
			Files.append(logLine.append(System.lineSeparator()).toString(), logFile, CHARSET);
		} catch (IOException e) {
			FMLLog.warning("Error logging custom waypoint activities");
			e.printStackTrace();
		}
	}

	public static void logCreate(EntityPlayer entityplayer, LOTRCustomWaypoint cwp) {
		log("CREATE", entityplayer, cwp);
	}

	public static void logDelete(EntityPlayer entityplayer, LOTRCustomWaypoint cwp) {
		log("DELETE", entityplayer, cwp);
	}

	public static void logRename(EntityPlayer entityplayer, LOTRCustomWaypoint cwp) {
		log("RENAME", entityplayer, cwp);
	}

	public static void logTravel(EntityPlayer entityplayer, LOTRCustomWaypoint cwp) {
		log("TRAVEL", entityplayer, cwp);
	}
}
