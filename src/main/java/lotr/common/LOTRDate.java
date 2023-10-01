package lotr.common;

import com.google.common.math.IntMath;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import lotr.common.network.LOTRPacketDate;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.world.LOTRWorldInfo;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class LOTRDate {
	public static int ticksInDay = LOTRTime.DAY_LENGTH;
	public static long prevWorldTime = -1L;
	public static int SECOND_AGE_LENGTH = 3441;
	public static int THIRD_AGE_LENGTH = 3021;
	public static int SR_TO_TA = 1600;
	public static int THIRD_AGE_CURRENT = ShireReckoning.startDate.year + SR_TO_TA;

	public static void loadDates(NBTTagCompound levelData) {
		if (levelData.hasKey("Dates")) {
			NBTTagCompound nbt = levelData.getCompoundTag("Dates");
			ShireReckoning.currentDay = nbt.getInteger("ShireDate");
		} else {
			ShireReckoning.currentDay = 0;
		}
	}

	public static void resetWorldTimeInMenu() {
		prevWorldTime = -1L;
	}

	public static void saveDates(NBTTagCompound levelData) {
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setInteger("ShireDate", ShireReckoning.currentDay);
		levelData.setTag("Dates", nbt);
	}

	public static void sendUpdatePacket(EntityPlayerMP entityplayer, boolean update) {
		NBTTagCompound nbt = new NBTTagCompound();
		saveDates(nbt);
		IMessage packet = new LOTRPacketDate(nbt, update);
		LOTRPacketHandler.networkWrapper.sendTo(packet, entityplayer);
	}

	public static void setDate(int date) {
		ShireReckoning.currentDay = date;
		LOTRLevelData.markDirty();
		for (Object obj : MinecraftServer.getServer().getConfigurationManager().playerEntityList) {
			EntityPlayerMP entityplayer = (EntityPlayerMP) obj;
			sendUpdatePacket(entityplayer, true);
		}
	}

	public static void update(World world) {
		if (!(world.getWorldInfo() instanceof LOTRWorldInfo)) {
			return;
		}
		long worldTime = world.getWorldTime();
		if (prevWorldTime == -1L) {
			prevWorldTime = worldTime;
		}
		if (worldTime / ticksInDay != prevWorldTime / ticksInDay) {
			setDate(ShireReckoning.currentDay + 1);
		}
		prevWorldTime = worldTime;
	}

	public enum Season {
		SPRING("spring", 0, new float[]{1.0f, 1.0f, 1.0f}), SUMMER("summer", 1, new float[]{1.15f, 1.15f, 0.9f}), AUTUMN("autumn", 2, new float[]{1.2f, 1.0f, 0.7f}), WINTER("winter", 3, new float[]{1.0f, 0.8f, 0.8f});

		public static Season[] allSeasons = new Season[]{SPRING, SUMMER, AUTUMN, WINTER};

		public String name;
		public int seasonID;

		public float[] grassRGB;

		Season(String s, int i, float[] f) {
			name = s;
			seasonID = i;
			grassRGB = f;
		}

		public String codeName() {
			return name;
		}

		public int transformColor(int color) {
			float[] rgb = new Color(color).getRGBColorComponents(null);
			float r = rgb[0];
			float g = rgb[1];
			float b = rgb[2];
			r = Math.min(r * grassRGB[0], 1.0f);
			g = Math.min(g * grassRGB[1], 1.0f);
			b = Math.min(b * grassRGB[2], 1.0f);
			return new Color(r, g, b).getRGB();
		}
	}

	public static class ShireReckoning {
		public static Date startDate = new Date(1401, Month.HALIMATH, 22);
		public static int currentDay;
		public static Map<Integer, Date> cachedDates = new HashMap<>();

		public static Season getSeason() {
			return getShireDate().month.season;
		}

		public static Date getShireDate() {
			return getShireDate(currentDay);
		}

		public static Date getShireDate(int day) {
			Date date = cachedDates.get(day);
			if (date == null) {
				date = startDate.copy();
				if (day < 0) {
					for (int i = 0; i < -day; ++i) {
						date = date.decrement();
					}
				} else {
					for (int i = 0; i < day; ++i) {
						date = date.increment();
					}
				}
				cachedDates.put(day, date);
			}
			return date;
		}

		public static boolean isLeapYear(int year) {
			return year % 4 == 0 && year % 100 != 0;
		}

		public enum Day {
			STERDAY("sterday"), SUNDAY("sunday"), MONDAY("monday"), TREWSDAY("trewsday"), HEVENSDAY("hevensday"), MERSDAY("mersday"), HIGHDAY("highday");

			public String name;

			Day(String s) {
				name = s;
			}

			public String getDayName() {
				return StatCollector.translateToLocal("lotr.date.shire.day." + name);
			}
		}

		public enum Month {
			YULE_2("yule2", 1, Season.WINTER), AFTERYULE("afteryule", 30, Season.WINTER), SOLMATH("solmath", 30, Season.WINTER), RETHE("rethe", 30, Season.WINTER), ASTRON("astron", 30, Season.SPRING), THRIMIDGE("thrimidge", 30, Season.SPRING), FORELITHE("forelithe", 30, Season.SPRING), LITHE_1("lithe1", 1, Season.SPRING), MIDYEARSDAY("midyearsday", 1, Season.SUMMER, false, false), OVERLITHE("overlithe", 1, Season.SUMMER, false, true), LITHE_2("lithe2", 1, Season.SUMMER), AFTERLITHE("afterlithe", 30, Season.SUMMER), WEDMATH("wedmath", 30, Season.SUMMER), HALIMATH("halimath", 30, Season.SUMMER), WINTERFILTH("winterfilth", 30, Season.AUTUMN), BLOTMATH("blotmath", 30, Season.AUTUMN), FOREYULE("foreyule", 30, Season.AUTUMN), YULE_1("yule1", 1, Season.AUTUMN);

			public String name;
			public int days;
			public boolean hasWeekdayName;
			public boolean isLeapYear;
			public Season season;

			Month(String s, int i, Season se) {
				this(s, i, se, true, false);
			}

			Month(String s, int i, Season se, boolean flag, boolean flag1) {
				name = s;
				days = i;
				hasWeekdayName = flag;
				isLeapYear = flag1;
				season = se;
			}

			public String getMonthName() {
				return StatCollector.translateToLocal("lotr.date.shire.month." + name);
			}

			public boolean isSingleDay() {
				return days == 1;
			}
		}

		public static class Date {
			public int year;
			public Month month;
			public int monthDate;
			public Day day;

			public Date(int y, Month m, int d) {
				year = y;
				month = m;
				monthDate = d;
			}

			public Date copy() {
				return new Date(year, month, monthDate);
			}

			public Date decrement() {
				int newYear = year;
				Month newMonth = month;
				int newDate = monthDate;
				if (--newDate < 0) {
					int monthID = newMonth.ordinal();
					if (--monthID < 0) {
						monthID = Month.values().length - 1;
						--newYear;
					}
					newMonth = Month.values()[monthID];
					if (newMonth.isLeapYear && !isLeapYear(newYear)) {
						monthID--;
						newMonth = Month.values()[monthID];
					}
					newDate = newMonth.days;
				}
				return new Date(newYear, newMonth, newDate);
			}

			public String getDateName(boolean longName) {
				String[] dayYear = getDayAndYearNames(longName);
				return dayYear[0] + ", " + dayYear[1];
			}

			public Day getDay() {
				if (!month.hasWeekdayName) {
					return null;
				}
				if (day == null) {
					int yearDay = 0;
					int monthID = month.ordinal();
					for (int i = 0; i < monthID; ++i) {
						Month m = Month.values()[i];
						if (!m.hasWeekdayName) {
							continue;
						}
						yearDay += m.days;
					}
					int dayID = IntMath.mod(yearDay + monthDate - 1, Day.values().length);
					day = Day.values()[dayID];
				}
				return day;
			}

			public String[] getDayAndYearNames(boolean longName) {
				StringBuilder builder = new StringBuilder();
				if (month.hasWeekdayName) {
					builder.append(getDay().getDayName());
				}
				builder.append(" ");
				if (!month.isSingleDay()) {
					builder.append(monthDate);
					builder.append(" ");
				}
				builder.append(month.getMonthName());
				String dateName = builder.toString();
				builder = new StringBuilder();
				if (longName) {
					builder.append(StatCollector.translateToLocal("lotr.date.shire.long"));
				} else {
					builder.append(StatCollector.translateToLocal("lotr.date.shire"));
				}
				builder.append(" ");
				builder.append(year);
				String yearName = builder.toString();
				return new String[]{dateName, yearName};
			}

			public Date increment() {
				int newYear = year;
				Month newMonth = month;
				int newDate = monthDate;
				if (++newDate > newMonth.days) {
					newDate = 1;
					int monthID = newMonth.ordinal();
					if (++monthID >= Month.values().length) {
						monthID = 0;
						++newYear;
					}
					newMonth = Month.values()[monthID];
					if (newMonth.isLeapYear && !isLeapYear(newYear)) {
						monthID++;
						newMonth = Month.values()[monthID];
					}
				}
				return new Date(newYear, newMonth, newDate);
			}
		}

	}

}
