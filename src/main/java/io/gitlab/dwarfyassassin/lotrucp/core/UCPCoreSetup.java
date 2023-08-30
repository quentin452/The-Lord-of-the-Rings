package io.gitlab.dwarfyassassin.lotrucp.core;

import java.util.Map;

import org.apache.logging.log4j.LogManager;

import cpw.mods.fml.relauncher.IFMLCallHook;
import io.gitlab.dwarfyassassin.lotrucp.core.patches.*;

public class UCPCoreSetup implements IFMLCallHook {
	@Override
	public Void call() {
		UCPCoreMod.log = LogManager.getLogger("LOTR-UCP");
		UCPCoreMod.registerPatcher(new FMLPatcher());
		UCPCoreMod.registerPatcher(new BotaniaPatcher());
		UCPCoreMod.registerPatcher(new ScreenshotEnhancedPatcher());
		UCPCoreMod.registerPatcher(new ThaumcraftPatcher());
		return null;
	}

	@Override
	public void injectData(Map<String, Object> data) {
	}
}
