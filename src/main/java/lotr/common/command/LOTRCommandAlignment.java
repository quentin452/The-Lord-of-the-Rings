package lotr.common.command;

import lotr.common.LOTRLevelData;
import lotr.common.fac.LOTRFaction;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

import java.util.ArrayList;
import java.util.List;

public class LOTRCommandAlignment extends CommandBase {
	@Override
	public List addTabCompletionOptions(ICommandSender sender, String[] args) {
		switch (args.length) {
			case 1:
				return CommandBase.getListOfStringsMatchingLastWord(args, "set", "add");
			case 2: {
				List<String> list = LOTRFaction.getPlayableAlignmentFactionNames();
				list.add("all");
				return CommandBase.getListOfStringsMatchingLastWord(args, list.toArray(new String[0]));
			}
			case 4:
				return CommandBase.getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames());
			default:
				break;
		}
		return null;
	}

	@Override
	public String getCommandName() {
		return "alignment";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "commands.lotr.alignment.usage";
	}

	@Override
	public int getRequiredPermissionLevel() {
		return 2;
	}

	@Override
	public boolean isUsernameIndex(String[] args, int i) {
		return i == 3;
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		if (args.length >= 2) {
			List<LOTRFaction> factions = new ArrayList<>();
			if ("all".equalsIgnoreCase(args[1])) {
				factions = LOTRFaction.getPlayableAlignmentFactions();
			} else {
				LOTRFaction faction = LOTRFaction.forName(args[1]);
				if (faction == null) {
					throw new WrongUsageException("commands.lotr.alignment.noFaction", args[1]);
				}
				factions.add(faction);
			}
			if ("set".equals(args[0])) {
				EntityPlayerMP entityplayer;
				float alignment = (float) CommandBase.parseDoubleBounded(sender, args[2], -10000.0, 10000.0);
				if (args.length >= 4) {
					entityplayer = CommandBase.getPlayer(sender, args[3]);
				} else {
					entityplayer = CommandBase.getCommandSenderAsPlayer(sender);
				}
				for (LOTRFaction f : factions) {
					LOTRLevelData.getData(entityplayer).setAlignmentFromCommand(f, alignment);
					CommandBase.func_152373_a(sender, this, "commands.lotr.alignment.set", entityplayer.getCommandSenderName(), f.factionName(), alignment);
				}
				return;
			}
			if ("add".equals(args[0])) {
				EntityPlayerMP entityplayer;
				float newAlignment;
				float alignment = (float) CommandBase.parseDouble(sender, args[2]);
				if (args.length >= 4) {
					entityplayer = CommandBase.getPlayer(sender, args[3]);
				} else {
					entityplayer = CommandBase.getCommandSenderAsPlayer(sender);
				}
				for (LOTRFaction f : factions) {
					newAlignment = LOTRLevelData.getData(entityplayer).getAlignment(f) + alignment;
					if (newAlignment < -10000.0f) {
						throw new WrongUsageException("commands.lotr.alignment.tooLow", -10000.0f);
					}
					if (newAlignment > 10000.0f) {
						throw new WrongUsageException("commands.lotr.alignment.tooHigh", 10000.0f);
					}
				}
				for (LOTRFaction f : factions) {
					LOTRLevelData.getData(entityplayer).addAlignmentFromCommand(f, alignment);
					CommandBase.func_152373_a(sender, this, "commands.lotr.alignment.add", alignment, entityplayer.getCommandSenderName(), f.factionName());
				}
				return;
			}
		}
		throw new WrongUsageException(getCommandUsage(sender));
	}
}
