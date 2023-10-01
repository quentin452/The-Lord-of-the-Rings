package lotr.common.command;

import lotr.common.world.structure2.scan.LOTRScanAlias;
import lotr.common.world.structure2.scan.LOTRStructureScan;
import net.minecraft.block.Block;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.init.Blocks;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

public class LOTRCommandStrScan extends CommandBase {
	public boolean scanning;
	public int originX;
	public int originY;
	public int originZ;
	public int minX;
	public int minY;
	public int minZ;
	public int maxX;
	public int maxY;
	public int maxZ;
	public Collection<String> aliasOrder = new ArrayList<>();
	public Map<Block, String> blockAliases = new HashMap<>();
	public Map<Pair<Block, Integer>, String> blockMetaAliases = new HashMap<>();
	public Collection<String> aliasesToInclude = new HashSet<>();

	@Override
	public String getCommandName() {
		return "strscan";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "development command";
	}

	@Override
	public int getRequiredPermissionLevel() {
		return 2;
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		if (args.length >= 1) {
			String option = args[0];
			if ("begin".equals(option)) {
				if (!scanning) {
					scanning = true;
					aliasOrder.clear();
					blockAliases.clear();
					blockMetaAliases.clear();
					CommandBase.func_152373_a(sender, this, "Begun scanning");
					return;
				}
				throw new WrongUsageException("Already begun scanning");
			}
			if ("assoc".equals(option) && args.length >= 3 && scanning) {
				String blockID = args[1];
				Block block = Block.getBlockFromName(blockID);
				if (block == null) {
					int intID = Integer.parseInt(blockID);
					block = Block.getBlockById(intID);
				}
				if (block != null) {
					String alias = args[2];
					if (!blockAliases.containsValue(alias)) {
						blockAliases.put(block, alias);
						aliasOrder.add(alias);
						CommandBase.func_152373_a(sender, this, "Associated block %s to alias %s", blockID, alias);
						return;
					}
					throw new WrongUsageException("Alias %s already used", alias);
				}
				throw new WrongUsageException("Block %s does not exist", blockID);
			}
			if ("assoc_meta".equals(option) && args.length >= 4 && scanning) {
				String blockID = args[1];
				Block block = Block.getBlockFromName(blockID);
				if (block == null) {
					int intID = Integer.parseInt(blockID);
					block = Block.getBlockById(intID);
				}
				if (block != null) {
					int meta = CommandBase.parseInt(sender, args[2]);
					if (meta >= 0 && meta <= 15) {
						String alias = args[3];
						if (!blockMetaAliases.containsValue(alias)) {
							blockMetaAliases.put(Pair.of(block, meta), alias);
							aliasOrder.add(alias);
							CommandBase.func_152373_a(sender, this, "Associated block %s and metadata %s to alias %s", blockID, meta, alias);
							return;
						}
						throw new WrongUsageException("Alias %s already used", alias);
					}
					throw new WrongUsageException("Invalid metadata value %s", meta);
				}
				throw new WrongUsageException("Block %s does not exist", blockID);
			}
			if ("origin".equals(option) && args.length >= 4 && scanning) {
				ChunkCoordinates coords = sender.getPlayerCoordinates();
				int i = coords.posX;
				int j = coords.posY;
				int k = coords.posZ;
				i = MathHelper.floor_double(CommandBase.func_110666_a(sender, i, args[1]));
				j = MathHelper.floor_double(CommandBase.func_110666_a(sender, j, args[2]));
				k = MathHelper.floor_double(CommandBase.func_110666_a(sender, k, args[3]));
				maxX = originX = i;
				minX = originX;
				maxY = originY = j;
				minY = originY;
				maxZ = originZ = k;
				minZ = originZ;
				CommandBase.func_152373_a(sender, this, "Set scan origin to %s %s %s", originX, originY, originZ);
				return;
			}
			if ("expand".equals(option) && args.length >= 4 && scanning) {
				ChunkCoordinates coords = sender.getPlayerCoordinates();
				int i = coords.posX;
				int j = coords.posY;
				int k = coords.posZ;
				i = MathHelper.floor_double(CommandBase.func_110666_a(sender, i, args[1]));
				j = MathHelper.floor_double(CommandBase.func_110666_a(sender, j, args[2]));
				k = MathHelper.floor_double(CommandBase.func_110666_a(sender, k, args[3]));
				minX = Math.min(i, minX);
				minY = Math.min(j, minY);
				minZ = Math.min(k, minZ);
				maxX = Math.max(i, maxX);
				maxY = Math.max(j, maxY);
				maxZ = Math.max(k, maxZ);
				CommandBase.func_152373_a(sender, this, "Expanded scan region to include %s %s %s", i, j, k);
				return;
			}
			if ("scan".equals(option) && args.length >= 2 && scanning) {
				String scanName = args[1];
				LOTRStructureScan scan = new LOTRStructureScan(scanName);
				Block fillBelowKey = Blocks.bedrock;
				Block findLowestKey = Blocks.end_stone;
				World world = sender.getEntityWorld();
				for (int j = minY; j <= maxY; ++j) {
					for (int k = minZ; k <= maxZ; ++k) {
						for (int i = minX; i <= maxX; ++i) {
							String alias;
							int i1 = i - originX;
							int j1 = j - originY;
							int k1 = k - originZ;
							Block block = world.getBlock(i, j, k);
							int meta = world.getBlockMetadata(i, j, k);
							boolean fillBelow = false;
							boolean findLowest = false;
							if (block == Blocks.air || block == fillBelowKey || block == findLowestKey) {
								continue;
							}
							if (world.getBlock(i, j - 1, k) == fillBelowKey) {
								fillBelow = true;
							} else if (world.getBlock(i, j - 1, k) == findLowestKey) {
								findLowest = true;
							}
							LOTRStructureScan.ScanStepBase step;
							if (blockMetaAliases.containsKey(Pair.of((Object) block, (Object) meta))) {
								alias = blockMetaAliases.get(Pair.of((Object) block, (Object) meta));
								step = new LOTRStructureScan.ScanStepBlockMetaAlias(i1, j1, k1, alias);
								aliasesToInclude.add(alias);
							} else if (blockAliases.containsKey(block)) {
								alias = blockAliases.get(block);
								step = new LOTRStructureScan.ScanStepBlockAlias(i1, j1, k1, alias, meta);
								aliasesToInclude.add(alias);
							} else {
								step = new LOTRStructureScan.ScanStep(i1, j1, k1, block, meta);
							}
							step.fillDown = fillBelow;
							step.findLowest = findLowest;
							scan.addScanStep(step);
						}
					}
				}
				for (String alias : aliasOrder) {
					if (!aliasesToInclude.contains(alias)) {
						continue;
					}
					if (blockMetaAliases.containsValue(alias)) {
						scan.includeAlias(alias, LOTRScanAlias.Type.BLOCK_META);
						continue;
					}
					if (!blockAliases.containsValue(alias)) {
						continue;
					}
					scan.includeAlias(alias, LOTRScanAlias.Type.BLOCK);
				}
				if (LOTRStructureScan.writeScanToFile(scan)) {
					scanning = false;
					CommandBase.func_152373_a(sender, this, "Scanned structure as %s", scanName);
					return;
				}
				throw new WrongUsageException("Error scanning structure as %s", scanName);
			}
		}
		throw new WrongUsageException(getCommandUsage(sender));
	}
}