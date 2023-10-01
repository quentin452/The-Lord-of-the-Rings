package lotr.common.util;

import cpw.mods.fml.common.FMLLog;
import lotr.common.LOTRReflection;
import net.minecraft.server.MinecraftServer;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;

public class LOTRLog {
	public static Logger logger;

	public static void findLogger() {
		try {
			for (Field f : MinecraftServer.class.getDeclaredFields()) {
				LOTRReflection.unlockFinalField(f);
				Object obj = f.get(null);
				if (!(obj instanceof Logger)) {
					continue;
				}
				logger = (Logger) obj;
				logger.info("LOTR: Found logger");
				break;
			}
		} catch (Exception e) {
			FMLLog.warning("LOTR: Failed to find logger!");
			e.printStackTrace();
		}
	}
}
