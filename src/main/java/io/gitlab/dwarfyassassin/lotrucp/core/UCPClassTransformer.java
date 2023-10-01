package io.gitlab.dwarfyassassin.lotrucp.core;

import cpw.mods.fml.relauncher.FMLLaunchHandler;
import cpw.mods.fml.relauncher.ReflectionHelper;
import io.gitlab.dwarfyassassin.lotrucp.core.patches.base.Patcher;
import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;

import java.util.Collection;
import java.util.HashSet;

public class UCPClassTransformer implements IClassTransformer {
	static {
		FMLLaunchHandler launchHandler = ReflectionHelper.getPrivateValue(FMLLaunchHandler.class, null, "INSTANCE");
		ReflectionHelper.getPrivateValue(FMLLaunchHandler.class, launchHandler, "classLoader");
	}

	@Override
	public byte[] transform(String name, String transformedName, byte[] classBytes) {
		boolean ran = false;
		for (Patcher patcher : UCPCoreMod.activePatches) {
			if (!patcher.canRun(name)) {
				continue;
			}
			ran = true;
			ClassNode classNode = new ClassNode();
			ClassReader classReader = new ClassReader(classBytes);
			classReader.accept(classNode, 0);
			UCPCoreMod.log.info("Running patcher " + patcher.getName() + " for " + name);
			patcher.run(name, classNode);
			ClassWriter writer = new ClassWriter(1);
			classNode.accept(writer);
			classBytes = writer.toByteArray();
		}
		if (ran) {
			Collection<Patcher> removes = new HashSet<>();
			for (Patcher patcher : UCPCoreMod.activePatches) {
				if (!patcher.isDone()) {
					continue;
				}
				removes.add(patcher);
			}
			UCPCoreMod.activePatches.removeAll(removes);
			if (UCPCoreMod.activePatches.isEmpty()) {
				UCPCoreMod.log.info("Ran all active patches.");
			}
		}
		return classBytes;
	}
}
