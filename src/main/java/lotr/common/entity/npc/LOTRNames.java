package lotr.common.entity.npc;

import java.io.*;
import java.nio.file.Files;
import java.util.*;
import java.util.zip.*;

import org.apache.commons.io.input.BOMInputStream;

import com.google.common.base.Charsets;

import cpw.mods.fml.common.*;
import lotr.common.*;

public class LOTRNames {
	public static Map<String, String[]> allNameBanks = new HashMap<>();

	public static void changeHobbitSurnameForMarriage(LOTREntityHobbit maleHobbit, LOTREntityHobbit femaleHobbit) {
		String surname = maleHobbit.getNPCName().substring(maleHobbit.getNPCName().indexOf(' ') + 1);
		String femaleFirstName = femaleHobbit.getNPCName().substring(0, femaleHobbit.getNPCName().indexOf(' '));
		femaleHobbit.familyInfo.setName(femaleFirstName + " " + surname);
	}

	public static String[] getBreeCoupleAndHomeNames(Random rand) {
		String[] names = new String[4];
		String surname = getRandomName("bree_surname", rand);
		String maleName = getRandomName("bree_male", rand);
		String femaleName = getRandomName("bree_female", rand);
		names[0] = maleName + " " + surname;
		names[1] = femaleName + " " + surname;
		names[2] = surname;
		names[3] = "House";
		return names;
	}

	public static String getBreeHobbitChildNameForParent(Random rand, boolean male, LOTREntityHobbit parent) {
		String name = getBreeHobbitForename(rand, male);
		String surname = parent.getNPCName().substring(parent.getNPCName().indexOf(' ') + 1);
		return name + " " + surname;
	}

	public static String[] getBreeHobbitCoupleAndHomeNames(Random rand) {
		String[] names = new String[4];
		String surname = getBreeHobbitSurname(rand);
		String maleName = getBreeHobbitForename(rand, true);
		String femaleName = getBreeHobbitForename(rand, false);
		String homeName = getRandomName("hobbit_home", rand);
		names[0] = maleName + " " + surname;
		names[1] = femaleName + " " + surname;
		names[2] = surname;
		names[3] = homeName;
		return names;
	}

	public static String getBreeHobbitForename(Random rand, boolean male) {
		boolean shirelike;
		shirelike = rand.nextInt(3) == 0;
		return getRandomName(shirelike ? male ? "hobbit_male" : "hobbit_female" : male ? "bree_male" : "bree_female", rand);
	}

	public static String getBreeHobbitName(Random rand, boolean male) {
		String name = getBreeHobbitForename(rand, male);
		String surname = getBreeHobbitSurname(rand);
		return name + " " + surname;
	}

	public static String getBreeHobbitSurname(Random rand) {
		boolean shirelike = rand.nextInt(3) == 0;
		return getRandomName(shirelike ? "hobbit_surname" : "bree_surname", rand);
	}

	public static String[] getBreeInnName(Random rand) {
		String prefix = getRandomName("breeInn_prefix", rand);
		String suffix = getRandomName("breeInn_suffix", rand);
		return new String[]{prefix, suffix};
	}

	public static String getBreeName(Random rand, boolean male) {
		String name = getRandomName(male ? "bree_male" : "bree_female", rand);
		String surname = getRandomName("bree_surname", rand);
		return name + " " + surname;
	}

	public static String[] getBreeRuffianSign(Random rand) {
		String[] sign = new String[4];
		Arrays.fill(sign, "");
		String text = getRandomName("bree_ruffian_sign", rand);
		String[] split = text.split("#");
		sign[1] = split[0];
		sign[2] = split.length < 2 ? "" : split[1];
		return sign;
	}

	public static String[] getDaleBakeryName(Random rand, String name) {
		String title = getRandomName("dale_bakery", rand);
		return new String[]{name + "'s", title};
	}

	public static String getDalishName(Random rand, boolean male) {
		return getRandomName(male ? "dale_male" : "dale_female", rand);
	}

	public static String getDorwinionName(Random rand, boolean male) {
		return getRandomName(male ? "dorwinion_male" : "dorwinion_female", rand);
	}

	public static String getDunlendingName(Random rand, boolean male) {
		return getRandomName(male ? "dunlending_male" : "dunlending_female", rand);
	}

	public static String[] getDunlendingTavernName(Random rand) {
		String prefix = getRandomName("dunlendingTavern_prefix", rand);
		String suffix = getRandomName("dunlendingTavern_suffix", rand);
		return new String[]{prefix, suffix};
	}

	public static String getDwarfChildNameForParent(Random rand, boolean male, LOTREntityDwarf parent) {
		String name = getRandomName(male ? "dwarf_male" : "dwarf_female", rand);
		String parentName = parent.getNPCName();
		parentName = parentName.substring(0, parentName.indexOf(' '));
		return name + (male ? " son of " : " daughter of ") + parentName;
	}

	public static String getDwarfName(Random rand, boolean male) {
		String name = getRandomName(male ? "dwarf_male" : "dwarf_female", rand);
		String parentName = getRandomName("dwarf_male", rand);
		return name + (male ? " son of " : " daughter of ") + parentName;
	}

	public static String getEntName(Random rand) {
		String prefix = getRandomName("ent_prefix", rand);
		String suffix = getRandomName("ent_suffix", rand);
		return prefix + suffix;
	}

	public static String getGondorName(Random rand, boolean male) {
		return getRandomName(male ? "gondor_male" : "gondor_female", rand);
	}

	public static String[] getGondorTavernName(Random rand) {
		String prefix = getRandomName("gondorTavern_prefix", rand);
		String suffix = getRandomName("gondorTavern_suffix", rand);
		return new String[]{prefix, suffix};
	}

	public static String[] getGondorVillageName(Random rand) {
		String suffix;
		String welcome = "Welcome to";
		String prefix = getRandomName("gondorVillage_prefix", rand);
		if (prefix.endsWith((suffix = getRandomName("gondorVillage_suffix", rand)).substring(0, 1))) {
			suffix = suffix.substring(1);
		}
		String name = prefix + suffix;
		String date = getRandomVillageDate(rand, 50, 5000, 1500);
		String est = "est. " + date;
		return new String[]{welcome, name, "", est};
	}

	public static String getGulfHaradName(Random rand, boolean male) {
		return getRandomName(male ? "gulf_male" : "gulf_female", rand);
	}

	public static String[] getHaradTavernName(Random rand) {
		String prefix = getRandomName("haradTavern_prefix", rand);
		String suffix = getRandomName("haradTavern_suffix", rand);
		return new String[]{prefix, suffix};
	}

	public static String[] getHaradVillageName(Random rand) {
		String suffix;
		String welcome = "Welcome to";
		String prefix = getRandomName("haradVillage_prefix", rand);
		if (prefix.endsWith((suffix = getRandomName("haradVillage_suffix", rand)).substring(0, 1))) {
			suffix = suffix.substring(1);
		}
		String name = prefix + suffix;
		String date = getRandomVillageDate(rand, 50, 4000, 1000);
		String est = "est. " + date;
		return new String[]{welcome, name, "", est};
	}

	public static String getHarnennorName(Random rand, boolean male) {
		return getRandomName(male ? "nearHaradrim_male" : "nearHaradrim_female", rand);
	}

	public static String getHobbitChildNameForParent(Random rand, boolean male, LOTREntityHobbit parent) {
		String name = getHobbitForename(rand, male);
		String surname = parent.getNPCName().substring(parent.getNPCName().indexOf(' ') + 1);
		return name + " " + surname;
	}

	public static String[] getHobbitCoupleAndHomeNames(Random rand) {
		String[] names = new String[4];
		String surname = getHobbitSurname(rand);
		String maleName = getHobbitForename(rand, true);
		String femaleName = getHobbitForename(rand, false);
		String homeName = getRandomName("hobbit_home", rand);
		names[0] = maleName + " " + surname;
		names[1] = femaleName + " " + surname;
		names[2] = surname;
		names[3] = homeName;
		return names;
	}

	public static String getHobbitForename(Random rand, boolean male) {
		return getRandomName(male ? "hobbit_male" : "hobbit_female", rand);
	}

	public static String getHobbitName(Random rand, boolean male) {
		String name = getHobbitForename(rand, male);
		String surname = getHobbitSurname(rand);
		return name + " " + surname;
	}

	public static String[] getHobbitSign(Random rand) {
		String[] sign = new String[4];
		Arrays.fill(sign, "");
		String text = getRandomName("hobbit_sign", rand);
		String[] split = text.split("#");
		sign[1] = split[0];
		sign[2] = split.length < 2 ? "" : split[1];
		if (rand.nextInt(1000) == 0) {
			sign[1] = "Vote";
			sign[2] = "UKIP";
		}
		return sign;
	}

	public static String getHobbitSurname(Random rand) {
		return getRandomName("hobbit_surname", rand);
	}

	public static String[] getHobbitTavernName(Random rand) {
		String prefix = getRandomName("hobbitTavern_prefix", rand);
		String suffix = getRandomName("hobbitTavern_suffix", rand);
		return new String[]{prefix, suffix};
	}

	public static String[] getHobbitTavernQuote(Random rand) {
		String[] sign = new String[4];
		Arrays.fill(sign, "");
		String text = getRandomName("hobbitTavern_quote", rand);
		String[] split = text.split("#");
		for (int l = 0; l < sign.length && l < split.length; ++l) {
			sign[l] = split[l];
		}
		return sign;
	}

	public static String getMoredainName(Random rand, boolean male) {
		return getRandomName(male ? "moredain_male" : "moredain_female", rand);
	}

	public static String[] getNameBank(String nameBankName) {
		return allNameBanks.get(nameBankName);
	}

	public static String getNomadName(Random rand, boolean male) {
		return getRandomName(male ? "nomad_male" : "nomad_female", rand);
	}

	public static String getOrcName(Random rand) {
		String prefix = getRandomName("orc_prefix", rand);
		String suffix = getRandomName("orc_suffix", rand);
		return prefix + suffix;
	}

	public static String getQuenyaName(Random rand, boolean male) {
		StringBuilder name = new StringBuilder().append(getRandomName(male ? "quenya_male" : "quenya_female", rand));
		if (rand.nextInt(5) == 0) {
			name.append(" ").append(getRandomName("quenya_title", rand));
		}
		return name.toString();
	}

	public static String getRandomName(String nameBankName, Random rand) {
		if (allNameBanks.containsKey(nameBankName)) {
			String[] nameBank = getNameBank(nameBankName);
			return nameBank[rand.nextInt(nameBank.length)];
		}
		return "Unnamed";
	}

	public static String getRandomVillageDate(Random rand, int min, int max, int std) {
		double d = rand.nextGaussian();
		d = Math.abs(d);
		int ago = min + (int) Math.round(d * std);
		int date = LOTRDate.THIRD_AGE_CURRENT - Math.min(ago, max);
		if (date >= 1) {
			return "T.A. " + date;
		}
		return "S.A. " + date + LOTRDate.SECOND_AGE_LENGTH;
	}

	public static String getRhudaurName(Random rand, boolean male) {
		return getRandomName(male ? "rhudaur_male" : "rhudaur_female", rand);
	}

	public static String getRhunicName(Random rand, boolean male) {
		return getRandomName(male ? "rhun_male" : "rhun_female", rand);
	}

	public static String[] getRhunTavernName(Random rand) {
		String prefix = getRandomName("rhunTavern_prefix", rand);
		String suffix = getRandomName("rhunTavern_suffix", rand);
		return new String[]{prefix, suffix};
	}

	public static String[] getRhunVillageName(Random rand) {
		String suffix;
		String welcome = "Welcome to";
		String prefix = getRandomName("rhunVillage_prefix", rand);
		if (prefix.endsWith((suffix = getRandomName("rhunVillage_suffix", rand)).substring(0, 1))) {
			suffix = suffix.substring(1);
		}
		String name = prefix + suffix;
		String date = getRandomVillageDate(rand, 50, 2000, 300);
		String est = "est. " + date;
		return new String[]{welcome, name, "", est};
	}

	public static String[] getRohanMeadHallName(Random rand) {
		String prefix = getRandomName("rohanMeadHall_prefix", rand);
		String suffix = getRandomName("rohanMeadHall_suffix", rand);
		return new String[]{prefix, suffix};
	}

	public static String[] getRohanVillageName(Random rand) {
		String suffix;
		String welcome = "Welcome to";
		String prefix = getRandomName("rohanVillage_prefix", rand);
		if (prefix.endsWith((suffix = getRandomName("rohanVillage_suffix", rand)).substring(0, 1))) {
			suffix = suffix.substring(1);
		}
		String name = prefix + suffix;
		String date = getRandomVillageDate(rand, 50, 500, 100);
		String est = "est. " + date;
		return new String[]{welcome, name, "", est};
	}

	public static String getRohirricName(Random rand, boolean male) {
		return getRandomName(male ? "rohan_male" : "rohan_female", rand);
	}

	public static String getSindarinName(Random rand, boolean male) {
		return getRandomName(male ? "sindarin_male" : "sindarin_female", rand);
	}

	public static String getSindarinOrQuenyaName(Random rand, boolean male) {
		if (male) {
			String[] sNames = getNameBank("sindarin_male");
			int i = sNames.length + getNameBank("quenya_male").length;
			if (rand.nextInt(i) < sNames.length) {
				return getSindarinName(rand, true);
			}
			return getQuenyaName(rand, true);
		}
		String[] sNames = getNameBank("sindarin_female");
		int i = sNames.length + getNameBank("quenya_female").length;
		if (rand.nextInt(i) < sNames.length) {
			return getSindarinName(rand, false);
		}
		return getQuenyaName(rand, false);
	}

	public static String getSouthronCoastName(Random rand, boolean male) {
		if (rand.nextInt(3) == 0) {
			return getUmbarName(rand, male);
		}
		return getHarnennorName(rand, male);
	}

	public static String getTauredainName(Random rand, boolean male) {
		return getRandomName(male ? "tauredain_male" : "tauredain_female", rand);
	}

	public static String getTrollName(Random rand) {
		return getRandomName("troll", rand);
	}

	public static String getUmbarName(Random rand, boolean male) {
		return getRandomName(male ? "umbar_male" : "umbar_female", rand);
	}

	public static void loadAllNameBanks() {
		Map<String, BufferedReader> nameBankNamesAndReaders = new HashMap<>();
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
					if (!s.startsWith(path = "assets/lotr/names/") || !s.endsWith(".txt")) {
						continue;
					}
					s = s.substring(path.length());
					int i = s.indexOf(".txt");
					try {
						s = s.substring(0, i);
						BufferedReader reader = new BufferedReader(new InputStreamReader(new BOMInputStream(zip.getInputStream(entry)), Charsets.UTF_8));
						nameBankNamesAndReaders.put(s, reader);
					} catch (Exception e) {
						FMLLog.severe("Failed to load LOTR name bank " + s + "from zip file");
						e.printStackTrace();
					}
				}
			} else {
				File nameBankDir = new File(LOTRMod.class.getResource("/assets/lotr/names").toURI());
				for (File file : nameBankDir.listFiles()) {
					String s = file.getName();
					int i = s.indexOf(".txt");
					if (i < 0) {
						FMLLog.severe("Failed to load LOTR name bank " + s + " from MCP folder; name bank files must be in .txt format");
						continue;
					}
					try {
						s = s.substring(0, i);
						BufferedReader reader = new BufferedReader(new InputStreamReader(new BOMInputStream(Files.newInputStream(file.toPath())), Charsets.UTF_8));
						nameBankNamesAndReaders.put(s, reader);
					} catch (Exception e) {
						FMLLog.severe("Failed to load LOTR name bank " + s + " from MCP folder");
						e.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			FMLLog.severe("Failed to load LOTR name banks");
			e.printStackTrace();
		}
		for (Map.Entry entry : nameBankNamesAndReaders.entrySet()) {
			String nameBankName = (String) entry.getKey();
			BufferedReader reader = (BufferedReader) entry.getValue();
			try {
				String line;
				ArrayList<String> nameList = new ArrayList<>();
				while ((line = reader.readLine()) != null) {
					nameList.add(line);
				}
				reader.close();
				if (nameList.isEmpty()) {
					FMLLog.severe("LOTR name bank " + nameBankName + " is empty!");
					continue;
				}
				String[] nameBank = nameList.toArray(new String[0]);
				allNameBanks.put(nameBankName, nameBank);
			} catch (Exception e) {
				FMLLog.severe("Failed to load LOTR name bank " + nameBankName);
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

	public static boolean nameBankExists(String nameBankName) {
		return getNameBank(nameBankName) != null;
	}
}
