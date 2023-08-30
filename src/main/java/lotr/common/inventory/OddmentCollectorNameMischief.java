package lotr.common.inventory;

import java.util.Random;

public class OddmentCollectorNameMischief {
	public static String VOWELS = "aeiou";
	public static String CONSONANTS = "bcdfghjklmnopqrstvwxyz";

	public static String garbleName(String name, Random rand) {
		int deletes = rand.nextInt(3);
		for (int l = 0; l < deletes; ++l) {
			int x;
			if (name.length() <= 3 || isFormattingCharacter(name, x = rand.nextInt(name.length()))) {
				continue;
			}
			name = name.substring(0, x) + name.substring(x + 1);
		}
		int replaces = rand.nextInt(3);
		for (int l = 0; l < replaces; ++l) {
			char cNew;
			int x = rand.nextInt(name.length());
			if (isFormattingCharacter(name, x)) {
				continue;
			}
			char c = name.charAt(x);
			if (VOWELS.indexOf(Character.toLowerCase(c)) >= 0) {
				cNew = VOWELS.charAt(rand.nextInt(VOWELS.length()));
				if (Character.isUpperCase(c)) {
					cNew = Character.toUpperCase(cNew);
				}
				c = cNew;
			} else if (CONSONANTS.indexOf(Character.toLowerCase(c)) >= 0) {
				cNew = CONSONANTS.charAt(rand.nextInt(CONSONANTS.length()));
				if (Character.isUpperCase(c)) {
					cNew = Character.toUpperCase(cNew);
				}
				c = cNew;
			}
			name = name.substring(0, x) + c + name.substring(x + 1);
		}
		int dupes = rand.nextInt(2);
		for (int l = 0; l < dupes; ++l) {
			char c;
			int x = rand.nextInt(name.length());
			if (isFormattingCharacter(name, x) || !Character.isAlphabetic(c = name.charAt(x))) {
				continue;
			}
			name = name.substring(0, x) + c + c + name.substring(x + 1);
		}
		return name;
	}

	public static boolean isFormattingCharacter(CharSequence s, int index) {
		char charAt = s.charAt(index);
		if (charAt == '\u00a7') {
			return true;
		}
		return index >= 1 && s.charAt(index - 1) == '\u00a7';
	}
}
