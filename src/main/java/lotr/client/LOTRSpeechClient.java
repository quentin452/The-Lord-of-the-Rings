package lotr.client;

import lotr.common.entity.npc.LOTREntityNPC;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class LOTRSpeechClient {
	public static Map<UUID, TimedSpeech> npcSpeeches = new HashMap<>();
	public static int DISPLAY_TIME = 200;

	public static void clearAll() {
		npcSpeeches.clear();
	}

	public static TimedSpeech getSpeechFor(LOTREntityNPC npc) {
		UUID key = npc.getUniqueID();
		if (npcSpeeches.containsKey(key)) {
			return npcSpeeches.get(key);
		}
		return null;
	}

	public static boolean hasSpeech(LOTREntityNPC npc) {
		return getSpeechFor(npc) != null;
	}

	public static void receiveSpeech(LOTREntityNPC npc, String speech) {
		npcSpeeches.put(npc.getUniqueID(), new TimedSpeech(speech, DISPLAY_TIME));
	}

	public static void removeSpeech(LOTREntityNPC npc) {
		npcSpeeches.remove(npc.getUniqueID());
	}

	public static void update() {
		Map<UUID, TimedSpeech> newMap = new HashMap<>();
		for (Map.Entry<UUID, TimedSpeech> e : npcSpeeches.entrySet()) {
			UUID key = e.getKey();
			TimedSpeech speech = e.getValue();
			speech.time--;
			if (speech.time <= 0) {
				continue;
			}
			newMap.put(key, speech);
		}
		npcSpeeches = newMap;
	}

	public static class TimedSpeech {
		public String speech;
		public int time;

		public TimedSpeech(String s, int i) {
			speech = s;
			time = i;
		}

		public float getAge() {
			return (float) time / DISPLAY_TIME;
		}

		public String getSpeech() {
			return speech;
		}
	}

}
