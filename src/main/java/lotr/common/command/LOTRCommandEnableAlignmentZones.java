package lotr.common.command;

import lotr.common.LOTRLevelData;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;

import java.util.List;

public class LOTRCommandEnableAlignmentZones extends CommandBase {
	@Override
	public List addTabCompletionOptions(ICommandSender sender, String[] args) {
		if (args.length == 1) {
			return CommandBase.getListOfStringsMatchingLastWord(args, "enable", "disable");
		}
		return null;
	}

	@Override
	public String getCommandName() {
		return "alignmentZones";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "commands.lotr.alignmentZones.usage";
	}

	@Override
	public int getRequiredPermissionLevel() {
		return 2;
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		if (args.length >= 1) {
			String flag = args[0];
			if ("enable".equals(flag)) {
				LOTRLevelData.setEnableAlignmentZones(true);
				CommandBase.func_152373_a(sender, this, "commands.lotr.alignmentZones.enable");
				return;
			}
			if ("disable".equals(flag)) {
				LOTRLevelData.setEnableAlignmentZones(false);
				CommandBase.func_152373_a(sender, this, "commands.lotr.alignmentZones.disable");
				return;
			}
		}
		throw new WrongUsageException(getCommandUsage(sender));
	}
}
