package lotr.common.command;

import lotr.common.LOTRLevelData;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentTranslation;

public class LOTRCommandAdminHideMap extends CommandBase {

	public static void notifyUnhidden(ICommandSender entityplayer) {
		entityplayer.addChatMessage(new ChatComponentTranslation("commands.lotr.opHideMap.unhide"));
	}

	@Override
	public String getCommandName() {
		return "opHideMap";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "commands.lotr.opHideMap.usage";
	}

	@Override
	public int getRequiredPermissionLevel() {
		return 2;
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		if (sender instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) sender;
			if (MinecraftServer.getServer().getConfigurationManager().func_152596_g(player.getGameProfile())) {
				if (player.capabilities.isCreativeMode) {
					LOTRLevelData.getData(player).setAdminHideMap(true);
					CommandBase.func_152373_a(sender, this, "commands.lotr.opHideMap.hiding");
					return;
				}
				throw new WrongUsageException("commands.lotr.opHideMap.notCreative");
			}
		}
		throw new WrongUsageException("commands.lotr.opHideMap.notOp");
	}
}
