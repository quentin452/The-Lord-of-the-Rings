package lotr.common;

import java.io.*;
import java.nio.file.Files;
import java.util.*;
import java.util.regex.*;
import java.util.zip.*;

import org.apache.commons.io.input.BOMInputStream;
import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Charsets;

import cpw.mods.fml.common.ModContainer;
import lotr.common.entity.npc.LOTRNames;
import lotr.common.util.LOTRLog;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.*;
import net.minecraft.util.MathHelper;

public class LOTRLore {
	public static String newline = "\n";
	public static String codeMetadata = "#";
	public static String codeTitle = "title:";
	public static String codeAuthor = "author:";
	public static String codeCategory = "types:";
	public static String codeCategorySeparator = ",";
	public static String codeReward = "reward";
	public String loreName;
	public String loreTitle;
	public String loreAuthor;
	public String loreText;
	public List<LoreCategory> loreCategories;
	public boolean isRewardable;

	public LOTRLore(String name, String title, String auth, String text, List<LoreCategory> categories, boolean reward) {
		loreName = name;
		loreTitle = title;
		loreAuthor = auth;
		loreText = text;
		loreCategories = categories;
		isRewardable = reward;
	}

	public ItemStack createLoreBook(Random random) {
		ItemStack itemstack = new ItemStack(Items.written_book);
		NBTTagCompound data = new NBTTagCompound();
		itemstack.setTagCompound(data);
		String title = formatRandom(loreTitle, random);
		String author = formatRandom(loreAuthor, random);
		String text = formatRandom(loreText, random);
		List<String> textPages = organisePages(text);
		data.setString("title", title);
		data.setString("author", author);
		NBTTagList pages = new NBTTagList();
		for (String pageText : textPages) {
			pages.appendTag(new NBTTagString(pageText));
		}
		data.setTag("pages", pages);
		return itemstack;
	}

	public String formatRandom(String text, Random random) {
		int lastIndexStart = -1;
		do {
			String formatted;
			String unformatted;
			block16:
			{
				String s1;
				int indexStart = text.indexOf('{', lastIndexStart + 1);
				int indexEnd = text.indexOf('}');
				lastIndexStart = indexStart;
				if (indexStart < 0 || indexEnd <= indexStart) {
					break;
				}
				unformatted = text.substring(indexStart, indexEnd + 1);
				formatted = unformatted.substring(1, unformatted.length() - 1);
				if (formatted.startsWith("num:")) {
					try {
						s1 = formatted.substring("num:".length());
						int i1 = s1.indexOf(codeCategorySeparator);
						String s2 = s1.substring(0, i1);
						String s3 = s1.substring(i1 + codeCategorySeparator.length());
						int min = Integer.parseInt(s2);
						int max = Integer.parseInt(s3);
						int number = MathHelper.getRandomIntegerInRange(random, min, max);
						formatted = String.valueOf(number);
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else if (formatted.startsWith("name:")) {
					try {
						String namebank = formatted.substring("name:".length());
						if (!LOTRNames.nameBankExists(namebank)) {
							break block16;
						}
						formatted = LOTRNames.getRandomName(namebank, random);
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else if (formatted.startsWith("choose:")) {
					try {
						String remaining = formatted.substring("choose:".length());
						ArrayList<String> words = new ArrayList<>();
						while (!remaining.isEmpty()) {
							String word;
							int indexOf = remaining.indexOf('/');
							if (indexOf >= 0) {
								word = remaining.substring(0, indexOf);
								remaining = remaining.substring(indexOf + "/".length());
							} else {
								word = remaining;
								remaining = "";
							}
							words.add(word);
						}
						formatted = words.get(random.nextInt(words.size()));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			text = Pattern.compile(unformatted, Pattern.LITERAL).matcher(text).replaceFirst(Matcher.quoteReplacement(formatted));
		} while (true);
		return text;
	}

	public static LOTRLore getMultiRandomLore(Iterable<LoreCategory> categories, Random random, boolean rewardsOnly) {
		ArrayList<LOTRLore> allLore = new ArrayList<>();
		for (LoreCategory c : categories) {
			for (LOTRLore lore : c.loreList) {
				if (allLore.contains(lore) || rewardsOnly && !lore.isRewardable) {
					continue;
				}
				allLore.add(lore);
			}
		}
		if (!allLore.isEmpty()) {
			return allLore.get(random.nextInt(allLore.size()));
		}
		return null;
	}

	public static void loadAllLore() {
		Map<String, BufferedReader> loreReaders = new HashMap<>();
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
					if (!s.startsWith(path = "assets/lotr/lore/") || !s.endsWith(".txt")) {
						continue;
					}
					s = s.substring(path.length());
					int i = s.indexOf(".txt");
					try {
						s = s.substring(0, i);
						BufferedReader reader = new BufferedReader(new InputStreamReader(new BOMInputStream(zip.getInputStream(entry)), Charsets.UTF_8));
						loreReaders.put(s, reader);
					} catch (Exception e) {
						LOTRLog.logger.error("Failed to load LOTR lore " + s + "from zip file");
						e.printStackTrace();
					}
				}
			} else {
				File nameBankDir = new File(LOTRMod.class.getResource("/assets/lotr/lore").toURI());
				for (File file : nameBankDir.listFiles()) {
					String s = file.getName();
					int i = s.indexOf(".txt");
					if (i < 0) {
						LOTRLog.logger.error("Failed to load LOTR lore " + s + " from MCP folder; name bank files must be in .txt format");
						continue;
					}
					try {
						s = s.substring(0, i);
						BufferedReader reader = new BufferedReader(new InputStreamReader(new BOMInputStream(Files.newInputStream(file.toPath())), Charsets.UTF_8));
						loreReaders.put(s, reader);
					} catch (Exception e) {
						LOTRLog.logger.error("Failed to load LOTR lore " + s + " from MCP folder");
						e.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			LOTRLog.logger.error("Failed to load LOTR lore");
			e.printStackTrace();
		}
		for (Map.Entry<String, BufferedReader> entry : loreReaders.entrySet()) {
			String loreName = entry.getKey();
			BufferedReader reader = entry.getValue();
			try {
				String categoryString;
				String line;
				String title = "";
				String author = "";
				ArrayList<LoreCategory> categories = new ArrayList<>();
				StringBuilder text = new StringBuilder();
				boolean reward = false;
				while ((line = reader.readLine()) != null) {
					if (line.startsWith(codeMetadata)) {
						String metadata = line.substring(codeMetadata.length());
						if (metadata.startsWith(codeTitle)) {
							title = metadata.substring(codeTitle.length());
							continue;
						}
						if (metadata.startsWith(codeAuthor)) {
							author = metadata.substring(codeAuthor.length());
							continue;
						}
						if (metadata.startsWith(codeCategory)) {
							categoryString = metadata.substring(codeCategory.length());
							while (!categoryString.isEmpty()) {
								String categoryName;
								int indexOf = categoryString.indexOf(codeCategorySeparator);
								if (indexOf >= 0) {
									categoryName = categoryString.substring(0, indexOf);
									categoryString = categoryString.substring(indexOf + 1);
								} else {
									categoryName = categoryString;
									categoryString = "";
								}
								if ("all".equals(categoryName)) {
									for (LoreCategory category : LoreCategory.values()) {
										if (categories.contains(category)) {
											continue;
										}
										categories.add(category);
									}
									continue;
								}
								LoreCategory category = LoreCategory.forName(categoryName);
								if (category != null) {
									if (categories.contains(category)) {
										continue;
									}
									categories.add(category);
								}
							}
							continue;
						}
						if (!metadata.startsWith(codeReward)) {
							continue;
						}
						reward = true;
						continue;
					}
					text.append(line);
					text.append(newline);
				}
				reader.close();
				LOTRLore lore = new LOTRLore(loreName, title, author, text.toString(), categories, reward);
				for (LoreCategory category : categories) {
					category.addLore(lore);
				}
			} catch (Exception e) {
				LOTRLog.logger.error("Failed to load LOTR lore: " + loreName);
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

	public static List<String> organisePages(String loreText) {
		List<String> loreTextPages = new ArrayList<>();
		String remainingText = loreText;
		ArrayList<String> splitTxtWords = new ArrayList<>();
		while (!remainingText.isEmpty()) {
			String part;
			if (remainingText.startsWith(newline)) {
				part = newline;
				if (!splitTxtWords.isEmpty()) {
					splitTxtWords.add(part);
				}
				remainingText = remainingText.substring(part.length());
				continue;
			}
			int indexOf = remainingText.indexOf(newline);
			part = indexOf >= 0 ? remainingText.substring(0, indexOf) : remainingText;
			Collections.addAll(splitTxtWords, StringUtils.split(part, " "));
			remainingText = remainingText.substring(part.length());
		}
		while (!splitTxtWords.isEmpty()) {
			int i;
			StringBuilder pageText = new StringBuilder();
			int numLines = 0;
			StringBuilder currentLine = new StringBuilder();
			int usedWords = 0;
			for (i = 0; i < splitTxtWords.size(); ++i) {
				String word = splitTxtWords.get(i);
				if (pageText.length() + word.length() > 256) {
					break;
				}
				if (newline.equals(word)) {
					if (currentLine.length() > 0) {
						pageText.append(currentLine);
						currentLine = new StringBuilder();
						numLines++;
						if (numLines >= 13) {
							break;
						}
					}
					++usedWords;
					if (pageText.length() == 0) {
						continue;
					}
					pageText.append(word);
					numLines++;
					if (numLines < 13) {
						continue;
					}
					break;
				}
				currentLine.append(word);
				++usedWords;
				if (i < splitTxtWords.size() - 1) {
					currentLine.append(" ");
				}
				if (currentLine.length() < 17) {
					continue;
				}
				pageText.append(currentLine);
				currentLine = new StringBuilder();
				numLines++;
				if (numLines >= 13) {
					break;
				}
			}
			if (currentLine.length() > 0) {
				pageText.append(currentLine);
				++numLines;
			}
			for (i = 0; i < usedWords; ++i) {
				splitTxtWords.remove(0);
			}
			loreTextPages.add(pageText.toString());
		}
		return loreTextPages;
	}

	public enum LoreCategory {
		RUINS("ruins"), SHIRE("shire"), BREE("bree"), BLUE_MOUNTAINS("blue_mountains"), LINDON("lindon"), ERIADOR("eriador"), RIVENDELL("rivendell"), EREGION("eregion"), DUNLAND("dunland"), GUNDABAD("gundabad"), ANGMAR("angmar"), WOODLAND_REALM("woodland_realm"), DOL_GULDUR("dol_guldur"), DALE("dale"), DURIN("durins_folk"), LOTHLORIEN("lothlorien"), ROHAN("rohan"), ISENGARD("isengard"), GONDOR("gondor"), MORDOR("mordor"), DORWINION("dorwinion"), RHUN("rhun"), HARNENNOR("harnennor"), SOUTHRON("southron"), UMBAR("umbar"), NOMAD("nomad"), GULF("gulf"), FAR_HARAD("far_harad"), FAR_HARAD_JUNGLE("far_harad_jungle"), HALF_TROLL("half_troll");

		public static String allCode = "all";
		public String categoryName;
		public Collection<LOTRLore> loreList = new ArrayList<>();

		LoreCategory(String s) {
			categoryName = s;
		}

		public void addLore(LOTRLore lore) {
			loreList.add(lore);
		}

		public static LoreCategory forName(String s) {
			for (LoreCategory r : values()) {
				if (!s.equalsIgnoreCase(r.categoryName)) {
					continue;
				}
				return r;
			}
			return null;
		}
	}

}