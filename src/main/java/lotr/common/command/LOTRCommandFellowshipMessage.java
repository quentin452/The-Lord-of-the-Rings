package lotr.common.command;

import lotr.common.LOTRLevelData;
import lotr.common.LOTRPlayerData;
import lotr.common.fellowship.LOTRFellowship;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class LOTRCommandFellowshipMessage extends CommandBase {
	@Override
	public List addTabCompletionOptions(ICommandSender sender, String[] args) {
		LOTRPlayerData playerData = LOTRLevelData.getData(CommandBase.getCommandSenderAsPlayer(sender));
		String[] argsOriginal = Arrays.copyOf(args, args.length);
		if (args.length >= 2 && "bind".equals(args[0])) {
			args = LOTRCommandFellowship.fixArgsForFellowship(args, 1, true);
			return LOTRCommandFellowship.listFellowshipsMatchingLastWord(args, argsOriginal, 1, playerData, false);
		}
		if (args.length >= 1) {
			args = LOTRCommandFellowship.fixArgsForFellowship(args, 0, true);
			List<String> matches = new ArrayList<>();
			if (args.length == 1 && !(!argsOriginal[0].isEmpty() && argsOriginal[0].charAt(0) == '\"')) {
				matches.addAll(CommandBase.getListOfStringsMatchingLastWord(args, "bind", "unbind"));
			}
			matches.addAll(LOTRCommandFellowship.listFellowshipsMatchingLastWord(args, argsOriginal, 0, playerData, false));
			return matches;
		}
		return null;
	}

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender sender) {
		if (sender instanceof EntityPlayer) {
			return true;
		}
		return super.canCommandSenderUseCommand(sender);
	}

	@Override
	public List getCommandAliases() {
		return Collections.singletonList("fchat");
	}

	@Override
	public String getCommandName() {
		return "fmsg";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "commands.lotr.fmsg.usage";
	}

	@Override
	public int getRequiredPermissionLevel() {
		return 0;
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		EntityPlayerMP entityplayer = CommandBase.getCommandSenderAsPlayer(sender);
		LOTRPlayerData playerData = LOTRLevelData.getData(entityplayer);
		if (args.length >= 1) {
			if ("bind".equals(args[0]) && args.length >= 2) {
				String fsName = LOTRCommandFellowship.fixArgsForFellowship(args, 1, false)[1];
				LOTRFellowship fellowship = playerData.getFellowshipByName(fsName);
				if (fellowship != null && !fellowship.isDisbanded() && fellowship.containsPlayer(entityplayer.getUniqueID())) {
					playerData.setChatBoundFellowship(fellowship);
					IChatComponent notif = new ChatComponentTranslation("commands.lotr.fmsg.bind", fellowship.getName());
					notif.getChatStyle().setColor(EnumChatFormatting.GRAY);
					notif.getChatStyle().setItalic(true);
					sender.addChatMessage(notif);
					return;
				}
				throw new WrongUsageException("commands.lotr.fmsg.notFound", fsName);
			}
			if ("unbind".equals(args[0])) {
				LOTRFellowship preBoundFellowship = playerData.getChatBoundFellowship();
				playerData.setChatBoundFellowshipID(null);
				IChatComponent notif = new ChatComponentTranslation("commands.lotr.fmsg.unbind", preBoundFellowship.getName());
				notif.getChatStyle().setColor(EnumChatFormatting.GRAY);
				notif.getChatStyle().setItalic(true);
				sender.addChatMessage(notif);
				return;
			}
			LOTRFellowship fellowship = null;
			int msgStartIndex = 0;
			if (!args[0].isEmpty() && args[0].charAt(0) == '\"') {
				String fsName = (args = LOTRCommandFellowship.fixArgsForFellowship(args, 0, false))[0];
				fellowship = playerData.getFellowshipByName(fsName);
				if (fellowship == null) {
					throw new WrongUsageException("commands.lotr.fmsg.notFound", fsName);
				}
				msgStartIndex = 1;
			}
			if (fellowship == null) {
				fellowship = playerData.getChatBoundFellowship();
				if (fellowship == null) {
					throw new WrongUsageException("commands.lotr.fmsg.boundNone");
				}
				if (fellowship.isDisbanded() || !fellowship.containsPlayer(entityplayer.getUniqueID())) {
					throw new WrongUsageException("commands.lotr.fmsg.boundNotMember", fellowship.getName());
				}
			}
			IChatComponent message = CommandBase.func_147176_a(sender, args, msgStartIndex, false);
			fellowship.sendFellowshipMessage(entityplayer, message.getUnformattedText());
			return;
		}
		throw new WrongUsageException(getCommandUsage(sender));
	}
}
