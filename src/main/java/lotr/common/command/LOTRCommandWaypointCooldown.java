package lotr.common.command;

import lotr.common.LOTRLevelData;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;

import java.util.List;

public class LOTRCommandWaypointCooldown extends CommandBase {
	public static int MAX_COOLDOWN = 86400;

	@Override
	public List addTabCompletionOptions(ICommandSender sender, String[] args) {
		if (args.length == 1) {
			return CommandBase.getListOfStringsMatchingLastWord(args, "max", "min");
		}
		return null;
	}

	@Override
	public String getCommandName() {
		return "wpCooldown";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "commands.lotr.wpCooldown.usage";
	}

	@Override
	public int getRequiredPermissionLevel() {
		return 2;
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		String function = null;
		int cooldown = -1;
		if (args.length == 1) {
			function = "max";
			cooldown = CommandBase.parseIntBounded(sender, args[0], 0, MAX_COOLDOWN);
		} else if (args.length >= 2) {
			function = args[0];
			cooldown = CommandBase.parseIntBounded(sender, args[1], 0, MAX_COOLDOWN);
		}
		if (function != null && cooldown >= 0) {
			int max = LOTRLevelData.getWaypointCooldownMax();
			int min = LOTRLevelData.getWaypointCooldownMin();
			if ("max".equals(function)) {
				boolean updatedMin = false;
				max = cooldown;
				if (max < min) {
					min = max;
					updatedMin = true;
				}
				LOTRLevelData.setWaypointCooldown(max, min);
				CommandBase.func_152373_a(sender, this, "commands.lotr.wpCooldown.setMax", max, LOTRLevelData.getHMSTime_Seconds(max));
				if (updatedMin) {
					CommandBase.func_152373_a(sender, this, "commands.lotr.wpCooldown.updateMin", min);
				}
				return;
			}
			if ("min".equals(function)) {
				boolean updatedMax = false;
				min = cooldown;
				if (min > max) {
					max = min;
					updatedMax = true;
				}
				LOTRLevelData.setWaypointCooldown(max, min);
				CommandBase.func_152373_a(sender, this, "commands.lotr.wpCooldown.setMin", min, LOTRLevelData.getHMSTime_Seconds(min));
				if (updatedMax) {
					CommandBase.func_152373_a(sender, this, "commands.lotr.wpCooldown.updateMax", max);
				}
				return;
			}
		}
		throw new WrongUsageException(getCommandUsage(sender));
	}
}
