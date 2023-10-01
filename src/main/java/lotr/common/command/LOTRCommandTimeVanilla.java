package lotr.common.command;

import lotr.common.world.LOTRWorldProvider;
import net.minecraft.command.CommandTime;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldServer;

public class LOTRCommandTimeVanilla extends CommandTime {
	@Override
	public void addTime(ICommandSender sender, int time) {
		for (WorldServer world : MinecraftServer.getServer().worldServers) {
			if (world.provider instanceof LOTRWorldProvider) {
				continue;
			}
			world.setWorldTime(world.getWorldTime() + time);
		}
	}

	@Override
	public void setTime(ICommandSender sender, int time) {
		for (WorldServer world : MinecraftServer.getServer().worldServers) {
			if (world.provider instanceof LOTRWorldProvider) {
				continue;
			}
			world.setWorldTime(time);
		}
	}
}
