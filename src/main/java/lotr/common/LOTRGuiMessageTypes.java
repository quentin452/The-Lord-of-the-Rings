package lotr.common;

import net.minecraft.util.StatCollector;

public enum LOTRGuiMessageTypes {
	FRIENDLY_FIRE("friendlyFire"), UTUMNO_WARN("utumnoWarn"), ENCHANTING("enchanting"), ALIGN_DRAIN("alignDrain");

	public String messageName;

	LOTRGuiMessageTypes(String s) {
		messageName = s;
	}

	public String getMessage() {
		return StatCollector.translateToLocal("lotr.gui.message." + messageName);
	}

	public String getSaveName() {
		return messageName;
	}

	public static LOTRGuiMessageTypes forSaveName(String name) {
		for (LOTRGuiMessageTypes message : values()) {
			if (!message.messageName.equals(name)) {
				continue;
			}
			return message;
		}
		return null;
	}
}
