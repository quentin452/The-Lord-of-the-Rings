package lotr.common.command;

import java.util.*;

import lotr.common.*;
import lotr.common.world.map.LOTRWaypoint;
import net.minecraft.command.*;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

public class LOTRCommandWaypoints extends CommandBase {
	@Override
	public List addTabCompletionOptions(ICommandSender sender, String[] args) {
		switch (args.length) {
			case 1:
				return CommandBase.getListOfStringsMatchingLastWord(args, "unlock", "lock");
			case 2: {
				ArrayList<String> names = new ArrayList<>();
				for (LOTRWaypoint.Region r : LOTRWaypoint.Region.values()) {
					names.add(r.name());
				}
				names.add("all");
				return CommandBase.getListOfStringsMatchingLastWord(args, names.toArray(new String[0]));
			}
			case 3:
				return CommandBase.getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames());
			default:
				break;
		}
		return null;
	}

	public LOTRWaypoint.Region findRegionByName(String name) {
		LOTRWaypoint.Region region = LOTRWaypoint.regionForName(name);
		if (region == null) {
			throw new CommandException("commands.lotr.lotrWaypoints.unknown", name);
		}
		return region;
	}

	@Override
	public String getCommandName() {
		return "lotrWaypoints";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "commands.lotr.lotrWaypoints.usage";
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
			String regionName = args[1];
			EntityPlayerMP entityplayer = args.length >= 3 ? CommandBase.getPlayer(sender, args[2]) : CommandBase.getCommandSenderAsPlayer(sender);
			LOTRPlayerData playerData = LOTRLevelData.getData(entityplayer);
			if ("unlock".equalsIgnoreCase(args[0])) {
				if ("all".equalsIgnoreCase(regionName)) {
					for (LOTRWaypoint.Region region : LOTRWaypoint.Region.values()) {
						playerData.unlockFTRegion(region);
					}
					CommandBase.func_152373_a(sender, this, "commands.lotr.lotrWaypoints.unlockAll", entityplayer.getCommandSenderName());
					return;
				}
				LOTRWaypoint.Region region = findRegionByName(regionName);
				if (playerData.isFTRegionUnlocked(region)) {
					throw new WrongUsageException("commands.lotr.lotrWaypoints.unlock.fail", entityplayer.getCommandSenderName(), region.name());
				}
				playerData.unlockFTRegion(region);
				CommandBase.func_152373_a(sender, this, "commands.lotr.lotrWaypoints.unlock", entityplayer.getCommandSenderName(), region.name());
				return;
			}
			if ("lock".equalsIgnoreCase(args[0])) {
				if ("all".equalsIgnoreCase(regionName)) {
					for (LOTRWaypoint.Region region : LOTRWaypoint.Region.values()) {
						playerData.lockFTRegion(region);
					}
					CommandBase.func_152373_a(sender, this, "commands.lotr.lotrWaypoints.lockAll", entityplayer.getCommandSenderName());
					return;
				}
				LOTRWaypoint.Region region = findRegionByName(regionName);
				if (!playerData.isFTRegionUnlocked(region)) {
					throw new WrongUsageException("commands.lotr.lotrWaypoints.lock.fail", entityplayer.getCommandSenderName(), region.name());
				}
				playerData.lockFTRegion(region);
				CommandBase.func_152373_a(sender, this, "commands.lotr.lotrWaypoints.lock", entityplayer.getCommandSenderName(), region.name());
				return;
			}
		}
		throw new WrongUsageException(getCommandUsage(sender));
	}
}
