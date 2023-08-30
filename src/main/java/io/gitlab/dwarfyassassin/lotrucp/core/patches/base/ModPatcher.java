package io.gitlab.dwarfyassassin.lotrucp.core.patches.base;

import cpw.mods.fml.common.Loader;

public abstract class ModPatcher extends Patcher {
	public String modID;

	protected ModPatcher(String name, String modID) {
		super(name);
		this.modID = modID;
	}

	@Override
	public Patcher.LoadingPhase getLoadPhase() {
		return Patcher.LoadingPhase.FORGE_MOD_LOADING;
	}

	@Override
	public boolean shouldInit() {
		return Loader.isModLoaded(modID);
	}
}
