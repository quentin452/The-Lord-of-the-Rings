package io.gitlab.dwarfyassassin.lotrucp.core.patches;

import io.gitlab.dwarfyassassin.lotrucp.core.UCPCoreMod;
import io.gitlab.dwarfyassassin.lotrucp.core.patches.base.ModPatcher;
import io.gitlab.dwarfyassassin.lotrucp.core.utils.ASMUtils;
import net.minecraftforge.classloading.FMLForgePlugin;
import org.objectweb.asm.tree.*;

public class ScreenshotEnhancedPatcher extends ModPatcher {
	public ScreenshotEnhancedPatcher() {
		super("Screenshots Enhanced", "screenshots");
		classes.put("lotr.client.render.entity.LOTRRenderScrapTrader", this::patchScrapTraderRender);
	}

	public void patchScrapTraderRender(ClassNode classNode) {
		MethodNode method = ASMUtils.findMethod(classNode, "doRender", "func_76986_a", "(Lnet/minecraft/entity/EntityLiving;DDDFF)V");
		if (method == null) {
			return;
		}
		for (AbstractInsnNode node : method.instructions.toArray()) {
			if (node.getOpcode() != 182) {
				continue;
			}
			MethodInsnNode methodNode = (MethodInsnNode) node;
			if (!methodNode.name.equals(FMLForgePlugin.RUNTIME_DEOBF ? "func_151463_i" : "getKeyCode") || !"()I".equals(methodNode.desc)) {
				continue;
			}
			ASMUtils.removePreviousNodes(method.instructions, methodNode, 3);
			FieldInsnNode keyCodeField = new FieldInsnNode(178, "net/undoredo/screenshots/KeyScreenshotListener", "screenshotKeyBinding", "Lnet/minecraft/client/settings/KeyBinding;");
			method.instructions.insertBefore(methodNode, keyCodeField);
			break;
		}
		UCPCoreMod.log.info("Patched the Oddment Collector render to be compatible with Screenshots Enhanced.");
	}

}
