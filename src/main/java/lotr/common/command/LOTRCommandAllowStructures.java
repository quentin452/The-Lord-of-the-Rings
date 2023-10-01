package lotr.common.command;

import lotr.common.LOTRLevelData;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentTranslation;

import java.util.ArrayList;
import java.util.List;

public class LOTRCommandAllowStructures extends CommandBase {
	@Override
	public List addTabCompletionOptions(ICommandSender sender, String[] args) {
		if (args.length == 1) {
			ArrayList<String> bannedNames = new ArrayList<>(LOTRLevelData.getBannedStructurePlayersUsernames());
			return CommandBase.getListOfStringsMatchingLastWord(args, bannedNames.toArray(new String[0]));
		}
		return null;
	}

	@Override
	public String getCommandName() {
		return "allowStructures";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "commands.lotr.allowStructures.usage";
	}

	@Override
	public int getRequiredPermissionLevel() {
		return 3;
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		if (args.length == 0) {
			if (!LOTRLevelData.structuresBanned()) {
				throw new WrongUsageException("commands.lotr.allowStructures.alreadyAllowed");
			}
			LOTRLevelData.setStructuresBanned(false);
			CommandBase.func_152373_a(sender, this, "commands.lotr.allowStructures.allow");
		} else {
			LOTRLevelData.setPlayerBannedForStructures(args[0], false);
			CommandBase.func_152373_a(sender, this, "commands.lotr.allowStructures.allowPlayer", args[0]);
			EntityPlayerMP entityplayer = CommandBase.getPlayer(sender, args[0]);
			if (entityplayer != null) {
				entityplayer.addChatMessage(new ChatComponentTranslation("chat.lotr.allowStructures"));
			}
		}
	}
}
