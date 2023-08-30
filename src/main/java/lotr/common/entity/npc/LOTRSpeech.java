package lotr.common.entity.npc;

import java.io.*;
import java.nio.file.Files;
import java.util.*;
import java.util.zip.*;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import net.minecraft.command.ICommandSender;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.input.BOMInputStream;

import com.google.common.base.Charsets;

import cpw.mods.fml.common.*;
import lotr.common.*;
import lotr.common.network.*;
import net.minecraft.entity.player.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class LOTRSpeech {
	public static Map<String, SpeechBank> allSpeechBanks = new HashMap<>();
	public static Random rand = new Random();

	public static String formatSpeech(String speech, ICommandSender entityplayer, CharSequence location, CharSequence objective) {
		if (entityplayer != null) {
			speech = speech.replace("#", entityplayer.getCommandSenderName());
		}
		if (location != null) {
			speech = speech.replace("@", location);
		}
		if (objective != null) {
			speech = speech.replace("$", objective);
		}
		return speech;
	}

	public static String getRandomSpeech(String bankName) {
		return getSpeechBank(bankName).getRandomSpeech(rand);
	}

	public static String getRandomSpeechForPlayer(LOTREntityNPC entity, String speechBankName, ICommandSender entityplayer) {
		return getRandomSpeechForPlayer(entity, speechBankName, entityplayer, null, null);
	}

	public static String getRandomSpeechForPlayer(LOTREntityNPC entity, String speechBankName, ICommandSender entityplayer, CharSequence location, CharSequence objective) {
		String s = getRandomSpeech(speechBankName);
		s = formatSpeech(s, entityplayer, location, objective);
		if (entity.isDrunkard()) {
			float f = entity.getDrunkenSpeechFactor();
			s = LOTRDrunkenSpeech.getDrunkenSpeech(s, f);
		}
		return s;
	}

	public static String getSpeechAtLine(String bankName, int i) {
		return getSpeechBank(bankName).getSpeechAtLine(i);
	}

	public static SpeechBank getSpeechBank(String name) {
		SpeechBank bank = allSpeechBanks.get(name);
		if (bank != null) {
			return bank;
		}
		return new SpeechBank("dummy_" + name, true, Collections.singletonList("Speech bank " + name + " could not be found!"));
	}

	public static String getSpeechLineForPlayer(LOTREntityNPC entity, String speechBankName, int i, ICommandSender entityplayer) {
		return getSpeechLineForPlayer(entity, speechBankName, i, entityplayer, null, null);
	}

	public static String getSpeechLineForPlayer(LOTREntityNPC entity, String speechBankName, int i, ICommandSender entityplayer, CharSequence location, CharSequence objective) {
		String s = getSpeechAtLine(speechBankName, i);
		s = formatSpeech(s, entityplayer, location, objective);
		if (entity.isDrunkard()) {
			float f = entity.getDrunkenSpeechFactor();
			s = LOTRDrunkenSpeech.getDrunkenSpeech(s, f);
		}
		return s;
	}

	public static void loadAllSpeechBanks() {
		Map<String, BufferedReader> speechBankNamesAndReaders = new HashMap<>();
		ZipFile zip = null;
		try {
			ModContainer mc = LOTRMod.getModContainer();
			if (mc.getSource().isFile()) {
				zip = new ZipFile(mc.getSource());
				Enumeration<? extends ZipEntry> entries = zip.entries();
				while (entries.hasMoreElements()) {
					String path;
					ZipEntry entry = entries.nextElement();
					String s = entry.getName();
					if (!s.startsWith(path = "assets/lotr/speech/") || !s.endsWith(".txt")) {
						continue;
					}
					s = s.substring(path.length());
					int i = s.indexOf(".txt");
					try {
						s = s.substring(0, i);
						BufferedReader reader = new BufferedReader(new InputStreamReader(new BOMInputStream(zip.getInputStream(entry)), Charsets.UTF_8));
						speechBankNamesAndReaders.put(s, reader);
					} catch (Exception e) {
						FMLLog.severe("Failed to load LOTR speech bank " + s + "from zip file");
						e.printStackTrace();
					}
				}
			} else {
				File speechBankDir = new File(LOTRMod.class.getResource("/assets/lotr/speech").toURI());
				Collection<File> subfiles = FileUtils.listFiles(speechBankDir, null, true);
				for (File subfile : subfiles) {
					String s = subfile.getPath();
					s = s.substring(speechBankDir.getPath().length() + 1);
					int i = (s = s.replace(File.separator, "/")).indexOf(".txt");
					if (i < 0) {
						FMLLog.severe("Failed to load LOTR speech bank " + s + " from MCP folder; speech bank files must be in .txt format");
						continue;
					}
					try {
						s = s.substring(0, i);
						BufferedReader reader = new BufferedReader(new InputStreamReader(new BOMInputStream(Files.newInputStream(subfile.toPath())), Charsets.UTF_8));
						speechBankNamesAndReaders.put(s, reader);
					} catch (Exception e) {
						FMLLog.severe("Failed to load LOTR speech bank " + s + " from MCP folder");
						e.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			FMLLog.severe("Failed to load LOTR speech banks");
			e.printStackTrace();
		}
		for (Map.Entry<String, BufferedReader> entry : speechBankNamesAndReaders.entrySet()) {
			String speechBankName = entry.getKey();
			BufferedReader reader = entry.getValue();
			try {
				String line;
				ArrayList<String> speeches = new ArrayList<>();
				ArrayList<String> allLines = new ArrayList<>();
				boolean random = true;
				while ((line = reader.readLine()) != null) {
					if ("!RANDOM".equals(line)) {
						random = false;
					} else {
						speeches.add(line);
					}
					allLines.add(line);
				}
				reader.close();
				if (speeches.isEmpty()) {
					FMLLog.severe("LOTR speech bank " + speechBankName + " is empty!");
					continue;
				}
				SpeechBank bank = random ? new SpeechBank(speechBankName, true, speeches) : new SpeechBank(speechBankName, false, allLines);
				allSpeechBanks.put(speechBankName, bank);
			} catch (Exception e) {
				FMLLog.severe("Failed to load LOTR speech bank " + speechBankName);
				e.printStackTrace();
			}
		}
		if (zip != null) {
			try {
				zip.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void messageAllPlayers(IChatComponent message) {
		if (MinecraftServer.getServer() == null) {
			return;
		}
		for (Object player : MinecraftServer.getServer().getConfigurationManager().playerEntityList) {
			((ICommandSender) player).addChatMessage(message);
		}
	}

	public static void messageAllPlayersInWorld(World world, IChatComponent message) {
		for (Object player : world.playerEntities) {
			((ICommandSender) player).addChatMessage(message);
		}
	}

	public static void sendSpeech(EntityPlayer entityplayer, LOTREntityNPC entity, String speech) {
		sendSpeech(entityplayer, entity, speech, false);
	}

	public static void sendSpeech(EntityPlayer entityplayer, LOTREntityNPC entity, String speech, boolean forceChatMsg) {
		IMessage packet = new LOTRPacketNPCSpeech(entity.getEntityId(), speech, forceChatMsg);
		LOTRPacketHandler.networkWrapper.sendTo(packet, (EntityPlayerMP) entityplayer);
	}

	public static void sendSpeechAndChatMessage(EntityPlayer entityplayer, LOTREntityNPC entity, String speechBankName) {
		String name = entity.getCommandSenderName();
		String speech = getRandomSpeechForPlayer(entity, speechBankName, entityplayer, null, null);
		String message = EnumChatFormatting.YELLOW + "<" + name + ">" + EnumChatFormatting.WHITE + " " + speech;
		IChatComponent component = new ChatComponentText(message);
		entityplayer.addChatMessage(component);
		sendSpeech(entityplayer, entity, speech);
	}

	public static void sendSpeechBankWithChatMsg(EntityPlayer entityplayer, LOTREntityNPC entity, String speechBankName) {
		String speech = getRandomSpeechForPlayer(entity, speechBankName, entityplayer, null, null);
		sendSpeech(entityplayer, entity, speech, true);
	}

	public static class SpeechBank {
		public String name;
		public boolean isRandom;
		public List<String> speeches;

		public SpeechBank(String s, boolean r, List<String> spc) {
			name = s;
			isRandom = r;
			speeches = spc;
		}

		public String getRandomSpeech(Random random) {
			if (!isRandom) {
				return "ERROR: Tried to retrieve random speech from non-random speech bank " + name;
			}
			String s = speeches.get(rand.nextInt(speeches.size()));
			return internalFormatSpeech(s);
		}

		public String getSpeechAtLine(int line) {
			if (isRandom) {
				return "ERROR: Tried to retrieve indexed speech from random speech bank " + name;
			}
			int index = line - 1;
			if (index >= 0 && index < speeches.size()) {
				String s = speeches.get(index);
				return internalFormatSpeech(s);
			}
			return "ERROR: Speech line " + line + " is out of range!";
		}

		public String internalFormatSpeech(String s) {
			if (LOTRMod.isAprilFools() || rand.nextInt(2000) == 0) {
				s = "Tbh, " + s.substring(0, 1).toLowerCase(Locale.ROOT) + s.substring(1, s.length() - 1) + ", tbh.";
			}
			return s;
		}
	}

}
