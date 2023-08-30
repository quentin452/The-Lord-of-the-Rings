package lotr.common.command;

import java.util.List;

import lotr.common.LOTRLevelData;
import net.minecraft.command.*;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

public class LOTRCommandFastTravelClock extends CommandBase {
	@Override
	public List addTabCompletionOptions(ICommandSender sender, String[] args) {
		if (args.length == 1) {
			return CommandBase.getListOfStringsMatchingLastWord(args, "0", "max");
		}
		if (args.length == 2) {
			return CommandBase.getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames());
		}
		return null;
	}

	@Override
	public String getCommandName() {
		return "fastTravelClock";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "commands.lotr.fastTravelClock.usage";
	}

	@Override
	public int getRequiredPermissionLevel() {
		return 2;
	}

	@Override
	public boolean isUsernameIndex(String[] args, int i) {
		return i == 1;
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		if (args.length >= 1) {
			EntityPlayerMP entityplayer;
			String argSeconds = args[0];
			int seconds = "max".equals(argSeconds) ? 1000000 : CommandBase.parseIntWithMin(sender, args[0], 0);
			if (args.length >= 2) {
				entityplayer = CommandBase.getPlayer(sender, args[1]);
			} else {
				entityplayer = CommandBase.getCommandSenderAsPlayer(sender);
			}
			int ticks = seconds * 20;
			LOTRLevelData.getData(entityplayer).setTimeSinceFTWithUpdate(ticks);
			CommandBase.func_152373_a(sender, this, "commands.lotr.fastTravelClock.set", entityplayer.getCommandSenderName(), seconds, LOTRLevelData.getHMSTime_Seconds(seconds));
			return;
		}
		throw new WrongUsageException(getCommandUsage(sender));
	}
}
