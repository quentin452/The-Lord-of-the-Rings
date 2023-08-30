package io.gitlab.dwarfyassassin.lotrucp.core.utils;

import org.objectweb.asm.tree.*;

import io.gitlab.dwarfyassassin.lotrucp.core.UCPCoreMod;
import net.minecraftforge.classloading.FMLForgePlugin;

public class ASMUtils {
	public static MethodNode findMethod(ClassNode classNode, String targetMethodName, String targetMethodDesc) {
		for (MethodNode method : classNode.methods) {
			if (!method.name.equals(targetMethodName) || !method.desc.equals(targetMethodDesc)) {
				continue;
			}
			return method;
		}
		UCPCoreMod.log.error("Couldn't find method " + targetMethodName + " with desc " + targetMethodDesc + " in " + classNode.name);
		return null;
	}

	public static MethodNode findMethod(ClassNode classNode, String targetMethodName, String obfTargetMethodName, String targetMethodDesc) {
		return findMethod(classNode, FMLForgePlugin.RUNTIME_DEOBF ? obfTargetMethodName : targetMethodName, targetMethodDesc);
	}

	public static void removePreviousNodes(InsnList list, AbstractInsnNode start, int amount) {
		for (int i = 0; i < amount; ++i) {
			AbstractInsnNode prevNode = start.getPrevious();
			list.remove(prevNode);
		}
	}
}
