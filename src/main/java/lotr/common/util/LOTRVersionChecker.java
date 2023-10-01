package lotr.common.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class LOTRVersionChecker {
	public static String versionURL = "https://dl.dropboxusercontent.com/s/sidxw1dicl2nsev/version.txt";
	public static boolean checkedUpdate;

	public static void checkForUpdates() {
		if (!checkedUpdate) {
			Thread checkThread = new Thread("LOTR Update Checker") {

				@Override
				public void run() {
					try {
						String line;
						URL url = new URL(versionURL);
						BufferedReader updateReader = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8));
						StringBuilder updateVersion = new StringBuilder();
						while ((line = updateReader.readLine()) != null) {
							updateVersion.append(line);
						}
						updateReader.close();
						updateVersion = new StringBuilder(updateVersion.toString().trim());
						String currentVersion = "Update v36.14 for Minecraft 1.7.10";
						if (!updateVersion.toString().equals(currentVersion)) {
							ChatComponentText component = new ChatComponentText("The Lord of the Rings Mod:");
							component.getChatStyle().setColor(EnumChatFormatting.YELLOW);
							EntityClientPlayerMP entityplayer = Minecraft.getMinecraft().thePlayer;
							if (entityplayer != null) {
								entityplayer.addChatMessage(new ChatComponentTranslation("chat.lotr.update", component, updateVersion.toString()));
							}
						}
					} catch (Exception e) {
						LOTRLog.logger.warn("LOTR: Version check failed");
						e.printStackTrace();
					}
				}
			};
			checkedUpdate = true;
			checkThread.setDaemon(true);
			checkThread.start();
		}
	}

}
