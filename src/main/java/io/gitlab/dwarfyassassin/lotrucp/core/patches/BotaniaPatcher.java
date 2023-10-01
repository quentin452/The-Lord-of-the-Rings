package io.gitlab.dwarfyassassin.lotrucp.core.patches;

import io.gitlab.dwarfyassassin.lotrucp.core.UCPCoreMod;
import io.gitlab.dwarfyassassin.lotrucp.core.patches.base.ModPatcher;
import io.gitlab.dwarfyassassin.lotrucp.core.utils.ASMUtils;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TypeInsnNode;

public class BotaniaPatcher extends ModPatcher {
	public BotaniaPatcher() {
		super("Botania", "Botania");
		classes.put("vazkii.botania.common.block.subtile.generating.SubTileKekimurus", this::patchKekimurus);
	}

	public void patchKekimurus(ClassNode classNode) {
		MethodNode method = ASMUtils.findMethod(classNode, "onUpdate", "()V");
		if (method == null) {
			return;
		}
		for (AbstractInsnNode node : method.instructions.toArray()) {
			if (!(node instanceof TypeInsnNode)) {
				continue;
			}
			TypeInsnNode typeNode = (TypeInsnNode) node;
			if (!"net/minecraft/block/BlockCake".equals(typeNode.desc)) {
				continue;
			}
			typeNode.desc = "lotr/common/block/LOTRBlockPlaceableFood";
			break;
		}
		UCPCoreMod.log.info("Patched the Kekimurus to eat all LOTR cakes.");
	}

}
