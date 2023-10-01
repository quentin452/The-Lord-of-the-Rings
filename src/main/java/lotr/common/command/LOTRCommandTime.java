package lotr.common.command;

import lotr.common.LOTRTime;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;

import java.util.List;

public class LOTRCommandTime extends CommandBase {
	@Override
	public List addTabCompletionOptions(ICommandSender sender, String[] args) {
		if (args.length == 1) {
			return CommandBase.getListOfStringsMatchingLastWord(args, "set", "add");
		}
		if (args.length == 2 && "set".equals(args[0])) {
			return CommandBase.getListOfStringsMatchingLastWord(args, "day", "night");
		}
		return null;
	}

	@Override
	public String getCommandName() {
		return "lotr_time";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "commands.lotr.time.usage";
	}

	@Override
	public int getRequiredPermissionLevel() {
		return 2;
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		if (args.length >= 2) {
			if ("set".equals(args[0])) {
				long time;
				time = "day".equals(args[1]) ? Math.round(LOTRTime.DAY_LENGTH * 0.03) : "night".equals(args[1]) ? Math.round(LOTRTime.DAY_LENGTH * 0.6) : CommandBase.parseIntWithMin(sender, args[1], 0);
				LOTRTime.setWorldTime(time);
				CommandBase.func_152373_a(sender, this, "commands.lotr.time.set", time);
				return;
			}
			if ("add".equals(args[0])) {
				int time = CommandBase.parseIntWithMin(sender, args[1], 0);
				LOTRTime.addWorldTime(time);
				CommandBase.func_152373_a(sender, this, "commands.lotr.time.add", time);
				return;
			}
		}
		throw new WrongUsageException(getCommandUsage(sender));
	}
}
