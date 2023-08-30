package lotr.common;

public class LOTRModInfo {
	public static String modID = "lotr";
	public static String modName = "The Lord of the Rings Mod";
	public static String version = "Update v36.15 for Minecraft 1.7.10";
	public static String[] description = {"Powered by Hummel009"};

	public static String concatenateDescription(int startIndex) {
		StringBuilder s = new StringBuilder();
		for (int i = Math.min(startIndex, description.length - 1); i < description.length; ++i) {
			s.append(description[i]).append("\n\n");
		}
		return s.toString();
	}
}
