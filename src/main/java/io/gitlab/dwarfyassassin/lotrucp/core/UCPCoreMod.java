package io.gitlab.dwarfyassassin.lotrucp.core;

import java.util.*;

import org.apache.logging.log4j.Logger;

import io.gitlab.dwarfyassassin.lotrucp.core.patches.base.Patcher;

public class UCPCoreMod {
	public static Logger log;
	public static List<Patcher> activePatches = new ArrayList<>();
	public static List<Patcher> modPatches = new ArrayList<>();

	static {
		System.out.println("LOTR-UCP: Found core mod.");
	}

	public String getAccessTransformerClass() {
		return null;
	}

	public String[] getASMTransformerClass() {
		return new String[]{UCPClassTransformer.class.getName()};
	}

	public String getModContainerClass() {
		return null;
	}

	public String getSetupClass() {
		return UCPCoreSetup.class.getName();
	}

	@SuppressWarnings("all")
	public void injectData(Map<String, Object> data) {
	}

	public static void loadModPatches() {
		int i = 0;
		for (Patcher patcher : modPatches) {
			if (!patcher.shouldInit()) {
				continue;
			}
			activePatches.add(patcher);
			++i;
		}
		log.info("Loaded " + i + " mod patches.");
		modPatches.clear();
	}

	public static void registerPatcher(Patcher patcher) {
		if (patcher.getLoadPhase() == Patcher.LoadingPhase.CORE_MOD_LOADING && patcher.shouldInit()) {
			activePatches.add(patcher);
		} else if (patcher.getLoadPhase() == Patcher.LoadingPhase.FORGE_MOD_LOADING) {
			modPatches.add(patcher);
		}
	}
}
