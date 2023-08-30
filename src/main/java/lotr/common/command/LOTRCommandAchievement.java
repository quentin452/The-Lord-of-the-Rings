package lotr.common.command;

import java.util.*;

import lotr.common.*;
import net.minecraft.command.*;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

public class LOTRCommandAchievement extends CommandBase {
	@Override
	public List addTabCompletionOptions(ICommandSender sender, String[] args) {
		switch (args.length) {
			case 1:
				return CommandBase.getListOfStringsMatchingLastWord(args, "give", "remove");
			case 2: {
				List<LOTRAchievement> achievements = LOTRAchievement.getAllAchievements();
				ArrayList<String> names = new ArrayList<>();
				for (LOTRAchievement a : achievements) {
					names.add(a.getCodeName());
				}
				if ("remove".equals(args[0])) {
					names.add("all");
				}
				return CommandBase.getListOfStringsMatchingLastWord(args, names.toArray(new String[0]));
			}
			case 3:
				return CommandBase.getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames());
			default:
				break;
		}
		return null;
	}

	public LOTRAchievement findAchievementByName(String name) {
		LOTRAchievement ach = LOTRAchievement.findByName(name);
		if (ach == null) {
			throw new CommandException("commands.lotr.lotrAchievement.unknown", name);
		}
		return ach;
	}

	@Override
	public String getCommandName() {
		return "lotrAchievement";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "commands.lotr.lotrAchievement.usage";
	}

	@Override
	public int getRequiredPermissionLevel() {
		return 2;
	}

	@Override
	public boolean isUsernameIndex(String[] args, int i) {
		return i == 2;
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		if (args.length >= 2) {
			String achName = args[1];
			EntityPlayerMP entityplayer = args.length >= 3 ? CommandBase.getPlayer(sender, args[2]) : CommandBase.getCommandSenderAsPlayer(sender);
			LOTRPlayerData playerData = LOTRLevelData.getData(entityplayer);
			if ("give".equalsIgnoreCase(args[0])) {
				LOTRAchievement ach = findAchievementByName(achName);
				if (playerData.hasAchievement(ach)) {
					throw new WrongUsageException("commands.lotr.lotrAchievement.give.fail", entityplayer.getCommandSenderName(), ach.getTitle(entityplayer));
				}
				playerData.addAchievement(ach);
				CommandBase.func_152373_a(sender, this, "commands.lotr.lotrAchievement.give", entityplayer.getCommandSenderName(), ach.getTitle(entityplayer));
				return;
			}
			if ("remove".equalsIgnoreCase(args[0])) {
				if ("all".equalsIgnoreCase(achName)) {
					Iterable<LOTRAchievement> allAchievements = new ArrayList<>(playerData.getAchievements());
					for (LOTRAchievement ach : allAchievements) {
						playerData.removeAchievement(ach);
					}
					CommandBase.func_152373_a(sender, this, "commands.lotr.lotrAchievement.removeAll", entityplayer.getCommandSenderName());
					return;
				}
				LOTRAchievement ach = findAchievementByName(achName);
				if (!playerData.hasAchievement(ach)) {
					throw new WrongUsageException("commands.lotr.lotrAchievement.remove.fail", entityplayer.getCommandSenderName(), ach.getTitle(entityplayer));
				}
				playerData.removeAchievement(ach);
				CommandBase.func_152373_a(sender, this, "commands.lotr.lotrAchievement.remove", entityplayer.getCommandSenderName(), ach.getTitle(entityplayer));
				return;
			}
		}
		throw new WrongUsageException(getCommandUsage(sender));
	}
}
