package lotr.common.command;

import lotr.common.LOTRLevelData;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentTranslation;

import java.util.List;

public class LOTRCommandBanStructures extends CommandBase {
	@Override
	public List addTabCompletionOptions(ICommandSender sender, String[] args) {
		if (args.length == 1) {
			return CommandBase.getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames());
		}
		return null;
	}

	@Override
	public String getCommandName() {
		return "banStructures";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "commands.lotr.banStructures.usage";
	}

	@Override
	public int getRequiredPermissionLevel() {
		return 3;
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		if (args.length == 0) {
			if (LOTRLevelData.structuresBanned()) {
				throw new WrongUsageException("commands.lotr.banStructures.alreadyBanned");
			}
			LOTRLevelData.setStructuresBanned(true);
			CommandBase.func_152373_a(sender, this, "commands.lotr.banStructures.ban");
		} else {
			LOTRLevelData.setPlayerBannedForStructures(args[0], true);
			CommandBase.func_152373_a(sender, this, "commands.lotr.banStructures.banPlayer", args[0]);
			EntityPlayerMP entityplayer = CommandBase.getPlayer(sender, args[0]);
			if (entityplayer != null) {
				entityplayer.addChatMessage(new ChatComponentTranslation("chat.lotr.banStructures"));
			}
		}
	}
}
