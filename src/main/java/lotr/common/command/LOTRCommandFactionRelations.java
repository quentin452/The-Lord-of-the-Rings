package lotr.common.command;

import lotr.common.fac.LOTRFaction;
import lotr.common.fac.LOTRFactionRelations;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;

import java.util.List;

public class LOTRCommandFactionRelations extends CommandBase {
	@Override
	public List addTabCompletionOptions(ICommandSender sender, String[] args) {
		switch (args.length) {
			case 1:
				return CommandBase.getListOfStringsMatchingLastWord(args, "set", "reset");
			case 2:
			case 3: {
				List<String> list = LOTRFaction.getPlayableAlignmentFactionNames();
				return CommandBase.getListOfStringsMatchingLastWord(args, list.toArray(new String[0]));
			}
			case 4: {
				List<String> list = LOTRFactionRelations.Relation.listRelationNames();
				return CommandBase.getListOfStringsMatchingLastWord(args, list.toArray(new String[0]));
			}
			default:
				break;
		}
		return null;
	}

	@Override
	public String getCommandName() {
		return "facRelations";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "commands.lotr.facRelations.usage";
	}

	@Override
	public int getRequiredPermissionLevel() {
		return 2;
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		if (args.length >= 1) {
			String function = args[0];
			if ("set".equals(function)) {
				if (args.length >= 4) {
					LOTRFaction fac1 = LOTRFaction.forName(args[1]);
					if (fac1 == null) {
						throw new WrongUsageException("commands.lotr.alignment.noFaction", args[1]);
					}
					LOTRFaction fac2 = LOTRFaction.forName(args[2]);
					if (fac2 == null) {
						throw new WrongUsageException("commands.lotr.alignment.noFaction", args[2]);
					}
					LOTRFactionRelations.Relation relation = LOTRFactionRelations.Relation.forName(args[3]);
					if (relation == null) {
						throw new WrongUsageException("commands.lotr.facRelations.noRelation", args[3]);
					}
					try {
						LOTRFactionRelations.overrideRelations(fac1, fac2, relation);
						CommandBase.func_152373_a(sender, this, "commands.lotr.facRelations.set", fac1.factionName(), fac2.factionName(), relation.getDisplayName());
						return;
					} catch (IllegalArgumentException e) {
						throw new WrongUsageException("commands.lotr.facRelations.error", e.getMessage());
					}
				}
			} else if ("reset".equals(function)) {
				LOTRFactionRelations.resetAllRelations();
				CommandBase.func_152373_a(sender, this, "commands.lotr.facRelations.reset");
				return;
			}
		}
		throw new WrongUsageException(getCommandUsage(sender));
	}
}
