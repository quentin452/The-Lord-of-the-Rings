package lotr.common.command;

import com.mojang.authlib.GameProfile;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRPlayerData;
import lotr.common.fellowship.LOTRFellowship;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.PlayerNotFoundException;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

public class LOTRCommandFellowship extends CommandBase {
	public static String[] fixArgsForFellowship(String[] args, int startIndex, boolean autocompleting) {
		if (!args[startIndex].isEmpty() && args[startIndex].charAt(0) == '\"') {
			int endIndex = startIndex;
			boolean foundEnd = false;
			while (!foundEnd) {
				if (!args[endIndex].isEmpty() && args[endIndex].charAt(args[endIndex].length() - 1) == '\"') {
					foundEnd = true;
					continue;
				}
				if (endIndex >= args.length - 1) {
					if (autocompleting) {
						break;
					}
					throw new WrongUsageException("commands.lotr.fellowship.edit.nameError");
				}
				++endIndex;
			}
			StringBuilder fsName = new StringBuilder();
			for (int i = startIndex; i <= endIndex; ++i) {
				if (i > startIndex) {
					fsName.append(" ");
				}
				fsName.append(args[i]);
			}
			if (!autocompleting || foundEnd) {
				fsName = new StringBuilder(fsName.toString().replace("\"", ""));
			}
			int diff = endIndex - startIndex;
			String[] argsNew = new String[args.length - diff];
			for (int i = 0; i < argsNew.length; ++i) {
				argsNew[i] = i < startIndex ? args[i] : i == startIndex ? fsName.toString() : args[i + diff];
			}
			return argsNew;
		}
		if (!autocompleting) {
			throw new WrongUsageException("commands.lotr.fellowship.edit.nameError");
		}
		return args;
	}

	public static List<String> listFellowshipsMatchingLastWord(String[] argsFixed, String[] argsOriginal, int fsNameIndex, LOTRPlayerData playerData, boolean leadingOnly) {
		String fsName = argsFixed[fsNameIndex];
		List<String> allFellowshipNames = leadingOnly ? playerData.listAllLeadingFellowshipNames() : playerData.listAllFellowshipNames();
		ArrayList<String> autocompletes = new ArrayList<>();
		for (String nextFsName : allFellowshipNames) {
			String autocompFsName = "\"" + nextFsName + "\"";
			if (!autocompFsName.toLowerCase(Locale.ROOT).startsWith(fsName.toLowerCase(Locale.ROOT))) {
				continue;
			}
			if (argsOriginal.length > argsFixed.length) {
				int diff = argsOriginal.length - argsFixed.length;
				for (int j = 0; j < diff; ++j) {
					autocompFsName = autocompFsName.substring(autocompFsName.indexOf(' ') + 1);
				}
			}
			if (autocompFsName.contains(" ")) {
				autocompFsName = autocompFsName.substring(0, autocompFsName.indexOf(' '));
			}
			autocompletes.add(autocompFsName);
		}
		return CommandBase.getListOfStringsMatchingLastWord(argsOriginal, autocompletes.toArray(new String[0]));
	}

	@Override
	public List addTabCompletionOptions(ICommandSender sender, String[] args) {
		if (!sender.canCommandSenderUseCommand(getRequiredPermissionLevel(), getCommandName())) {
			return null;
		}
		if (args.length == 1) {
			return CommandBase.getListOfStringsMatchingLastWord(args, "create", "option");
		}
		if (args.length == 2) {
			return CommandBase.getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames());
		}
		if (args.length > 2) {
			String function = args[0];
			if ("create".equals(function)) {
				return null;
			}
			if ("option".equals(function)) {
				String[] argsOriginal = Arrays.copyOf(args, args.length);
				String ownerName = (args = fixArgsForFellowship(args, 2, true))[1];
				UUID ownerID = getPlayerIDByName(sender, ownerName);
				if (ownerID != null) {
					LOTRFellowship fellowship;
					LOTRPlayerData playerData = LOTRLevelData.getData(ownerID);
					String fsName = args[2];
					if (args.length == 3) {
						return listFellowshipsMatchingLastWord(args, argsOriginal, 2, playerData, true);
					}
					if (fsName != null && (fellowship = playerData.getFellowshipByName(fsName)) != null) {
						if (args.length == 4) {
							return CommandBase.getListOfStringsMatchingLastWord(args, "invite", "add", "remove", "transfer", "op", "deop", "disband", "rename", "icon", "pvp", "hired-ff", "map-show");
						}
						String option = args[3];
						if ("invite".equals(option) || "add".equals(option)) {
							ArrayList<String> notInFellowshipNames = new ArrayList<>();
							for (GameProfile playerProfile : MinecraftServer.getServer().getConfigurationManager().func_152600_g()) {
								UUID playerID = playerProfile.getId();
								if (fellowship.containsPlayer(playerID)) {
									continue;
								}
								notInFellowshipNames.add(playerProfile.getName());
							}
							return CommandBase.getListOfStringsMatchingLastWord(args, notInFellowshipNames.toArray(new String[0]));
						}
						if ("remove".equals(option) || "transfer".equals(option)) {
							ArrayList<String> memberNames = new ArrayList<>();
							for (UUID playerID : fellowship.getMemberUUIDs()) {
								GameProfile playerProfile = MinecraftServer.getServer().func_152358_ax().func_152652_a(playerID);
								if (playerProfile == null || playerProfile.getName() == null) {
									continue;
								}
								memberNames.add(playerProfile.getName());
							}
							return CommandBase.getListOfStringsMatchingLastWord(args, memberNames.toArray(new String[0]));
						}
						if ("op".equals(option)) {
							ArrayList<String> notAdminNames = new ArrayList<>();
							for (UUID playerID : fellowship.getMemberUUIDs()) {
								GameProfile playerProfile;
								if (fellowship.isAdmin(playerID) || (playerProfile = MinecraftServer.getServer().func_152358_ax().func_152652_a(playerID)) == null || playerProfile.getName() == null) {
									continue;
								}
								notAdminNames.add(playerProfile.getName());
							}
							return CommandBase.getListOfStringsMatchingLastWord(args, notAdminNames.toArray(new String[0]));
						}
						if ("deop".equals(option)) {
							ArrayList<String> adminNames = new ArrayList<>();
							for (UUID playerID : fellowship.getMemberUUIDs()) {
								GameProfile playerProfile;
								if (!fellowship.isAdmin(playerID) || (playerProfile = MinecraftServer.getServer().func_152358_ax().func_152652_a(playerID)) == null || playerProfile.getName() == null) {
									continue;
								}
								adminNames.add(playerProfile.getName());
							}
							return CommandBase.getListOfStringsMatchingLastWord(args, adminNames.toArray(new String[0]));
						}
						if ("pvp".equals(option) || "hired-ff".equals(option)) {
							return CommandBase.getListOfStringsMatchingLastWord(args, "prevent", "allow");
						}
						if ("map-show".equals(option)) {
							return CommandBase.getListOfStringsMatchingLastWord(args, "on", "off");
						}
					}
				}
			}
		}
		return null;
	}

	@Override
	public String getCommandName() {
		return "fellowship";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "commands.lotr.fellowship.usage";
	}

	public UUID getPlayerIDByName(ICommandSender sender, String username) {
		try {
			EntityPlayerMP entityplayer = CommandBase.getPlayer(sender, username);
			if (entityplayer != null) {
				return entityplayer.getUniqueID();
			}
		} catch (PlayerNotFoundException entityplayer) {
			// empty catch block
		}
		GameProfile profile = MinecraftServer.getServer().func_152358_ax().func_152655_a(username);
		if (profile != null) {
			return profile.getId();
		}
		return null;
	}

	@Override
	public int getRequiredPermissionLevel() {
		return 3;
	}

	@Override
	public boolean isUsernameIndex(String[] args, int i) {
		String option;
		if (args.length >= 5 && "option".equals(args[0]) && ("invite".equals(option = args[3]) || "add".equals(option) || "remove".equals(option) || "transfer".equals(option))) {
			return i == 4;
		}
		return false;
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		if (args.length >= 3 && "create".equals(args[0])) {
			args = fixArgsForFellowship(args, 2, false);
			String playerName = args[1];
			String fsName = args[2];
			if (fsName == null) {
				throw new WrongUsageException("commands.lotr.fellowship.edit.notFound", playerName, null);
			}
			UUID playerID = getPlayerIDByName(sender, playerName);
			if (playerID == null) {
				throw new PlayerNotFoundException();
			}
			LOTRPlayerData playerData = LOTRLevelData.getData(playerID);
			LOTRFellowship fellowship = playerData.getFellowshipByName(fsName);
			if (fellowship != null) {
				throw new WrongUsageException("commands.lotr.fellowship.create.exists", playerName, fsName);
			}
			playerData.createFellowship(fsName, false);
			CommandBase.func_152373_a(sender, this, "commands.lotr.fellowship.create", playerName, fsName);
			return;
		}
		if (!"option".equals(args[0])) {
			throw new WrongUsageException(getCommandUsage(sender));
		}
		if ((args = fixArgsForFellowship(args, 2, false)).length < 4) {
			throw new PlayerNotFoundException();
		}
		String ownerName = args[1];
		String fsName = args[2];
		if (fsName == null) {
			throw new WrongUsageException("commands.lotr.fellowship.edit.notFound", ownerName, null);
		}
		String option = args[3];
		UUID ownerID = getPlayerIDByName(sender, ownerName);
		if (ownerID == null) {
			throw new WrongUsageException(getCommandUsage(sender));
		}
		LOTRPlayerData ownerData = LOTRLevelData.getData(ownerID);
		LOTRFellowship fellowship = ownerData.getFellowshipByName(fsName);
		if (fellowship == null || !fellowship.isOwner(ownerID)) {
			throw new WrongUsageException("commands.lotr.fellowship.edit.notFound", ownerName, fsName);
		}
		if ("disband".equals(option)) {
			ownerData.disbandFellowship(fellowship, ownerName);
			CommandBase.func_152373_a(sender, this, "commands.lotr.fellowship.disband", ownerName, fsName);
			return;
		}
		if ("rename".equals(option)) {
			StringBuilder newName = new StringBuilder();
			int startIndex = 4;
			if (!args[startIndex].isEmpty() && args[startIndex].charAt(0) == '\"') {
				int endIndex = startIndex;
				while (!(!args[endIndex].isEmpty() && args[endIndex].charAt(args[endIndex].length() - 1) == '\"')) {
					endIndex++;
					if (endIndex < args.length) {
						continue;
					}
					throw new WrongUsageException("commands.lotr.fellowship.rename.error");
				}
				for (int i = startIndex; i <= endIndex; ++i) {
					if (i > startIndex) {
						newName.append(" ");
					}
					newName.append(args[i]);
				}
				newName = new StringBuilder(newName.toString().replace("\"", ""));
			}
			if (StringUtils.isBlank(newName.toString())) {
				throw new WrongUsageException("commands.lotr.fellowship.rename.error");
			}
			ownerData.renameFellowship(fellowship, newName.toString());
			CommandBase.func_152373_a(sender, this, "commands.lotr.fellowship.rename", ownerName, fsName, newName.toString());
			return;
		}
		if ("icon".equals(option)) {
			String iconData = CommandBase.func_147178_a(sender, args, 4).getUnformattedText();
			if ("clear".equals(iconData)) {
				ownerData.setFellowshipIcon(fellowship, null);
				CommandBase.func_152373_a(sender, this, "commands.lotr.fellowship.icon", ownerName, fsName, "[none]");
				return;
			}
			ItemStack itemstack;
			try {
				NBTBase nbt = JsonToNBT.func_150315_a(iconData);
				if (!(nbt instanceof NBTTagCompound)) {
					CommandBase.func_152373_a(sender, this, "commands.lotr.fellowship.icon.tagError", "Not a valid tag");
					return;
				}
				NBTTagCompound compound = (NBTTagCompound) nbt;
				itemstack = ItemStack.loadItemStackFromNBT(compound);
			} catch (NBTException nbtexception) {
				CommandBase.func_152373_a(sender, this, "commands.lotr.fellowship.icon.tagError", nbtexception.getMessage());
				return;
			}
			if (itemstack != null) {
				ownerData.setFellowshipIcon(fellowship, itemstack);
				CommandBase.func_152373_a(sender, this, "commands.lotr.fellowship.icon", ownerName, fsName, itemstack.getDisplayName());
				return;
			}
			CommandBase.func_152373_a(sender, this, "commands.lotr.fellowship.icon.tagError", "No item");
			return;
		}
		if ("pvp".equals(option) || "hired-ff".equals(option)) {
			boolean prevent;
			String setting = args[4];
			if ("prevent".equals(setting)) {
				prevent = true;
			} else {
				if (!"allow".equals(setting)) {
					throw new WrongUsageException(getCommandUsage(sender));
				}
				prevent = false;
			}
			if ("pvp".equals(option)) {
				ownerData.setFellowshipPreventPVP(fellowship, prevent);
				if (prevent) {
					CommandBase.func_152373_a(sender, this, "commands.lotr.fellowship.pvp.prevent", ownerName, fsName);
					return;
				}
				CommandBase.func_152373_a(sender, this, "commands.lotr.fellowship.pvp.allow", ownerName, fsName);
				return;
			}
			ownerData.setFellowshipPreventHiredFF(fellowship, prevent);
			if (prevent) {
				CommandBase.func_152373_a(sender, this, "commands.lotr.fellowship.hiredFF.prevent", ownerName, fsName);
				return;
			}
			CommandBase.func_152373_a(sender, this, "commands.lotr.fellowship.hiredFF.allow", ownerName, fsName);
			return;
		}
		if ("map-show".equals(option)) {
			boolean show;
			String setting = args[4];
			if ("on".equals(setting)) {
				show = true;
			} else {
				if (!"off".equals(setting)) {
					throw new WrongUsageException(getCommandUsage(sender));
				}
				show = false;
			}
			ownerData.setFellowshipShowMapLocations(fellowship, show);
			if (show) {
				CommandBase.func_152373_a(sender, this, "commands.lotr.fellowship.mapShow.on", ownerName, fsName);
				return;
			}
			CommandBase.func_152373_a(sender, this, "commands.lotr.fellowship.mapShow.off", ownerName, fsName);
			return;
		}
		String playerName = args[4];
		UUID playerID = getPlayerIDByName(sender, playerName);
		if (playerID == null) {
			throw new PlayerNotFoundException();
		}
		LOTRPlayerData playerData = LOTRLevelData.getData(playerID);
		if ("invite".equals(option)) {
			if (fellowship.containsPlayer(playerID)) {
				throw new WrongUsageException("commands.lotr.fellowship.edit.alreadyIn", ownerName, fsName, playerName);
			}
			ownerData.invitePlayerToFellowship(fellowship, playerID, ownerName);
			CommandBase.func_152373_a(sender, this, "commands.lotr.fellowship.invite", ownerName, fsName, playerName);
			return;
		}
		if ("add".equals(option)) {
			if (fellowship.containsPlayer(playerID)) {
				throw new WrongUsageException("commands.lotr.fellowship.edit.alreadyIn", ownerName, fsName, playerName);
			}
			ownerData.invitePlayerToFellowship(fellowship, playerID, ownerName);
			playerData.acceptFellowshipInvite(fellowship, false);
			CommandBase.func_152373_a(sender, this, "commands.lotr.fellowship.add", ownerName, fsName, playerName);
			return;
		}
		if ("remove".equals(option)) {
			if (!fellowship.hasMember(playerID)) {
				throw new WrongUsageException("commands.lotr.fellowship.edit.notMember", ownerName, fsName, playerName);
			}
			ownerData.removePlayerFromFellowship(fellowship, playerID, ownerName);
			CommandBase.func_152373_a(sender, this, "commands.lotr.fellowship.remove", ownerName, fsName, playerName);
			return;
		}
		if ("transfer".equals(option)) {
			if (!fellowship.hasMember(playerID)) {
				throw new WrongUsageException("commands.lotr.fellowship.edit.notMember", ownerName, fsName, playerName);
			}
			ownerData.transferFellowship(fellowship, playerID, ownerName);
			CommandBase.func_152373_a(sender, this, "commands.lotr.fellowship.transfer", ownerName, fsName, playerName);
			return;
		}
		if ("op".equals(option)) {
			if (!fellowship.hasMember(playerID)) {
				throw new WrongUsageException("commands.lotr.fellowship.edit.notMember", ownerName, fsName, playerName);
			}
			if (fellowship.isAdmin(playerID)) {
				throw new WrongUsageException("commands.lotr.fellowship.edit.alreadyOp", ownerName, fsName, playerName);
			}
			ownerData.setFellowshipAdmin(fellowship, playerID, true, ownerName);
			CommandBase.func_152373_a(sender, this, "commands.lotr.fellowship.op", ownerName, fsName, playerName);
			return;
		}
		if (!"deop".equals(option)) {
			throw new WrongUsageException(getCommandUsage(sender));
		}
		if (!fellowship.hasMember(playerID)) {
			throw new WrongUsageException("commands.lotr.fellowship.edit.notMember", ownerName, fsName, playerName);
		}
		if (!fellowship.isAdmin(playerID)) {
			throw new WrongUsageException("commands.lotr.fellowship.edit.notOp", ownerName, fsName, playerName);
		}
		ownerData.setFellowshipAdmin(fellowship, playerID, false, ownerName);
		CommandBase.func_152373_a(sender, this, "commands.lotr.fellowship.deop", ownerName, fsName, playerName);
	}
}
