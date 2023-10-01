package lotr.common.command;

import lotr.common.LOTRConfig;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;

import java.util.List;

public class LOTRCommandStructureTimelapse extends CommandBase {
	@Override
	public List addTabCompletionOptions(ICommandSender sender, String[] args) {
		if (args.length == 1) {
			return CommandBase.getListOfStringsMatchingLastWord(args, "on", "off");
		}
		return null;
	}

	@Override
	public String getCommandName() {
		return "strTimelapse";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "commands.lotr.strTimelapse.usage";
	}

	@Override
	public int getRequiredPermissionLevel() {
		return 2;
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		if (args.length == 1) {
			if ("on".equals(args[0])) {
				LOTRConfig.setStructureTimelapse(true);
				CommandBase.func_152373_a(sender, this, "commands.lotr.strTimelapse.on");
				CommandBase.func_152373_a(sender, this, "commands.lotr.strTimelapse.warn");
				return;
			}
			if ("off".equals(args[0])) {
				LOTRConfig.setStructureTimelapse(false);
				CommandBase.func_152373_a(sender, this, "commands.lotr.strTimelapse.off");
				return;
			}
			int interval = CommandBase.parseIntWithMin(sender, args[0], 0);
			LOTRConfig.setStructureTimelapseInterval(interval);
			CommandBase.func_152373_a(sender, this, "commands.lotr.strTimelapse.interval", interval);
			return;
		}
		throw new WrongUsageException(getCommandUsage(sender));
	}
}
