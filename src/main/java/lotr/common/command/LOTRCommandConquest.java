package lotr.common.command;

import lotr.common.LOTRLevelData;
import lotr.common.fac.LOTRFaction;
import lotr.common.world.map.LOTRConquestGrid;
import lotr.common.world.map.LOTRConquestZone;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.World;

import java.util.List;

public class LOTRCommandConquest extends CommandBase {
	@Override
	public List addTabCompletionOptions(ICommandSender sender, String[] args) {
		if (args.length == 1) {
			return CommandBase.getListOfStringsMatchingLastWord(args, "set", "add", "radial", "clear", "rate");
		}
		if (args.length == 2 && ("set".equals(args[0]) || "add".equals(args[0]) || "radial".equals(args[0]))) {
			List<String> list = LOTRFaction.getPlayableAlignmentFactionNames();
			return CommandBase.getListOfStringsMatchingLastWord(args, list.toArray(new String[0]));
		}
		return null;
	}

	@Override
	public String getCommandName() {
		return "conquest";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "commands.lotr.conquest.usage";
	}

	@Override
	public int getRequiredPermissionLevel() {
		return 2;
	}

	public Object[] parseCoordsAndZone(ICommandSender sender, String[] args, int specifyIndex) {
		int posX = sender.getPlayerCoordinates().posX;
		int posZ = sender.getPlayerCoordinates().posZ;
		if (args.length >= specifyIndex + 2) {
			posX = CommandBase.parseInt(sender, args[specifyIndex]);
			posZ = CommandBase.parseInt(sender, args[specifyIndex + 1]);
		}
		LOTRConquestZone zone = LOTRConquestGrid.getZoneByWorldCoords(posX, posZ);
		if (zone.isDummyZone) {
			throw new WrongUsageException("commands.lotr.conquest.outOfBounds", posX, posZ);
		}
		return new Object[]{posX, posZ, zone};
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		World world = sender.getEntityWorld();
		if (!LOTRConquestGrid.conquestEnabled(world)) {
			throw new WrongUsageException("commands.lotr.conquest.notEnabled");
		}
		if (args.length >= 1) {
			String function = args[0];
			if ("clear".equals(function)) {
				Object[] obj = parseCoordsAndZone(sender, args, 1);
				int posX = (Integer) obj[0];
				int posZ = (Integer) obj[1];
				LOTRConquestZone zone = (LOTRConquestZone) obj[2];
				zone.clearAllFactions(world);
				CommandBase.func_152373_a(sender, this, "commands.lotr.conquest.clear", posX, posZ);
				return;
			}
			if ("rate".equals(function)) {
				if (args.length >= 2) {
					double rate = CommandBase.parseDoubleBounded(sender, args[1], 0.0, 100.0);
					LOTRLevelData.setConquestRate((float) rate);
					CommandBase.func_152373_a(sender, this, "commands.lotr.conquest.rateSet", rate);
					return;
				}
				float currentRate = LOTRLevelData.getConquestRate();
				sender.addChatMessage(new ChatComponentTranslation("commands.lotr.conquest.rateGet", currentRate));
				return;
			}
			if (args.length >= 3 && ("set".equals(function) || "add".equals(function) || "radial".equals(function))) {
				LOTRFaction fac = LOTRFaction.forName(args[1]);
				if (fac == null) {
					throw new WrongUsageException("commands.lotr.conquest.noFaction", args[1]);
				}
				float amount = (float) CommandBase.parseDouble(sender, args[2]);
				Object[] obj = parseCoordsAndZone(sender, args, 3);
				int posX = (Integer) obj[0];
				int posZ = (Integer) obj[1];
				LOTRConquestZone zone = (LOTRConquestZone) obj[2];
				if ("set".equals(function)) {
					if (amount < 0.0f) {
						throw new WrongUsageException("commands.lotr.conquest.tooLow", 0.0f);
					}
					if (amount > 100000.0f) {
						throw new WrongUsageException("commands.lotr.conquest.tooHigh", 100000.0f);
					}
					zone.setConquestStrength(fac, amount, world);
					CommandBase.func_152373_a(sender, this, "commands.lotr.conquest.set", fac.factionName(), amount, posX, posZ);
					return;
				}
				if ("add".equals(function)) {
					float currentStr = zone.getConquestStrength(fac, world);
					float newStr = currentStr + amount;
					if (newStr < 0.0f) {
						throw new WrongUsageException("commands.lotr.conquest.tooLow", 0.0f);
					}
					if (newStr > 100000.0f) {
						throw new WrongUsageException("commands.lotr.conquest.tooHigh", 100000.0f);
					}
					zone.addConquestStrength(fac, amount, world);
					CommandBase.func_152373_a(sender, this, "commands.lotr.conquest.add", fac.factionName(), amount, posX, posZ);
					return;
				}
				EntityPlayerMP senderIfPlayer;
				float centralStr = zone.getConquestStrength(fac, world);
				if (centralStr + amount > 100000.0f) {
					throw new WrongUsageException("commands.lotr.conquest.tooHigh", 100000.0f);
				}
				senderIfPlayer = sender instanceof EntityPlayerMP ? (EntityPlayerMP) sender : null;
				if (amount < 0.0f) {
					LOTRConquestGrid.doRadialConquest(world, zone, senderIfPlayer, null, fac, -amount, -amount);
				} else {
					LOTRConquestGrid.doRadialConquest(world, zone, senderIfPlayer, fac, null, amount, amount);
				}
				CommandBase.func_152373_a(sender, this, "commands.lotr.conquest.radial", fac.factionName(), amount, posX, posZ);
				return;
			}
		}
		throw new WrongUsageException(getCommandUsage(sender));
	}
}
