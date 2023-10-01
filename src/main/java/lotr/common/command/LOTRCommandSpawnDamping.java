package lotr.common.command;

import lotr.common.LOTRSpawnDamping;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class LOTRCommandSpawnDamping extends CommandBase {
	@Override
	public List addTabCompletionOptions(ICommandSender sender, String[] args) {
		if (args.length == 1) {
			return CommandBase.getListOfStringsMatchingLastWord(args, "set", "calc", "reset");
		}
		if (args.length == 2 && ("set".equals(args[0]) || "calc".equals(args[0]))) {
			ArrayList<String> types = new ArrayList<>();
			for (EnumCreatureType type : EnumCreatureType.values()) {
				types.add(type.name());
			}
			types.add(LOTRSpawnDamping.TYPE_NPC);
			return CommandBase.getListOfStringsMatchingLastWord(args, types.toArray(new String[0]));
		}
		return null;
	}

	@Override
	public String getCommandName() {
		return "spawnDamping";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "commands.lotr.spawnDamping.usage";
	}

	@Override
	public int getRequiredPermissionLevel() {
		return 2;
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		if (args.length >= 1) {
			String option = args[0];
			if ("reset".equals(option)) {
				LOTRSpawnDamping.resetAll();
				CommandBase.func_152373_a(sender, this, "commands.lotr.spawnDamping.reset");
				return;
			}
			if (args.length >= 2) {
				String type = args[1];
				if (!type.equals(LOTRSpawnDamping.TYPE_NPC)) {
					EnumCreatureType.valueOf(type);
				}
				if ("set".equals(option) && args.length >= 3) {
					float damping = (float) CommandBase.parseDoubleBounded(sender, args[2], 0.0, 1.0);
					LOTRSpawnDamping.setSpawnDamping(type, damping);
					CommandBase.func_152373_a(sender, this, "commands.lotr.spawnDamping.set", type, damping);
					return;
				}
				if ("calc".equals(option)) {
					World world = sender.getEntityWorld();
					int dim = world.provider.dimensionId;
					String dimName = world.provider.getDimensionName();
					float damping = LOTRSpawnDamping.getSpawnDamping(type);
					int players = world.playerEntities.size();
					int expectedChunks = 196;
					int baseCap = LOTRSpawnDamping.getBaseSpawnCapForInfo(type, world);
					int cap = LOTRSpawnDamping.getSpawnCap(type, baseCap, players);
					int capXPlayers = cap * players;
					IChatComponent msg = new ChatComponentTranslation("commands.lotr.spawnDamping.calc", dim, dimName, type, damping, players, expectedChunks, cap, baseCap, capXPlayers);
					msg.getChatStyle().setColor(EnumChatFormatting.GREEN);
					sender.addChatMessage(msg);
					return;
				}
			}
		}
		throw new WrongUsageException(getCommandUsage(sender));
	}
}
