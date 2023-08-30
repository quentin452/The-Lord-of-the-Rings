package io.gitlab.dwarfyassassin.lotrucp.core.patches.base;

import java.util.*;

import org.objectweb.asm.tree.ClassNode;

public abstract class Patcher {
	public Map<String, ConsumerImplBecauseNoLambdas<ClassNode>> classes = new HashMap<>();
	public String patcherName;

	protected Patcher(String name) {
		patcherName = name;
	}

	public boolean canRun(String className) {
		return classes.containsKey(className);
	}

	public LoadingPhase getLoadPhase() {
		return LoadingPhase.CORE_MOD_LOADING;
	}

	public String getName() {
		return patcherName;
	}

	public boolean isDone() {
		return classes.isEmpty();
	}

	public void run(String className, ClassNode classNode) {
		classes.get(className).accept(classNode);
		classes.remove(className);
	}

	public boolean shouldInit() {
		return true;
	}

	public interface ConsumerImplBecauseNoLambdas<T> {
		void accept(T var1);
	}

	public enum LoadingPhase {
		CORE_MOD_LOADING, FORGE_MOD_LOADING

	}

}
