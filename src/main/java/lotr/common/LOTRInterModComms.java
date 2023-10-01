package lotr.common;

import com.google.common.collect.ImmutableList;
import cpw.mods.fml.common.event.FMLInterModComms;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

public class LOTRInterModComms {
	public static void update() {
		ImmutableList<FMLInterModComms.IMCMessage> messages = FMLInterModComms.fetchRuntimeMessages(LOTRMod.instance);
		if (!messages.isEmpty()) {
			for (FMLInterModComms.IMCMessage message : messages) {
				if (!"SIEGE_ACTIVE".equals(message.key)) {
					continue;
				}
				String playerName = message.getStringValue();
				EntityPlayerMP entityplayer = MinecraftServer.getServer().getConfigurationManager().func_152612_a(playerName);
				if (entityplayer == null) {
					continue;
				}
				int duration = 20;
				LOTRLevelData.getData(entityplayer).setSiegeActive(duration);
			}
		}
	}
}
