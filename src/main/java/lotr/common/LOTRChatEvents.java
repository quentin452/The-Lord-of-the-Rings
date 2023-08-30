package lotr.common;

import net.minecraft.event.HoverEvent;
import net.minecraftforge.common.util.EnumHelper;

public class LOTRChatEvents {
	public static Class[][] hoverParams = {{HoverEvent.Action.class, String.class, Boolean.TYPE}};
	public static HoverEvent.Action SHOW_LOTR_ACHIEVEMENT;

	public static void createEvents() {
		SHOW_LOTR_ACHIEVEMENT = EnumHelper.addEnum(hoverParams, HoverEvent.Action.class, "SHOW_LOTR_ACHIEVEMENT", "show_lotr_achievement", true);
		LOTRReflection.getHoverEventMappings().put(SHOW_LOTR_ACHIEVEMENT.getCanonicalName(), SHOW_LOTR_ACHIEVEMENT);
	}
}
