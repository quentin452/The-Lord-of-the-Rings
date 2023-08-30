package lotr.common.coremod;

import lotr.compatibility.LOTRModChecker;
import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.*;

import java.io.DataInputStream;
import java.util.Iterator;

public class LOTRClassTransformer implements IClassTransformer {
	public static String cls_Block = "net/minecraft/block/Block";
	public static String cls_Block_obf = "aji";
	public static String cls_BlockPistonBase = "net/minecraft/block/BlockPistonBase";
	public static String cls_BlockPistonBase_obf = "app";
	public static String cls_Blocks = "net/minecraft/init/Blocks";
	public static String cls_Blocks_obf = "ajn";
	public static String cls_EntityLivingBase_obf = "sv";
	public static String cls_EntityPlayer_obf = "yz";
	public static String cls_ItemArmor = "net/minecraft/item/ItemArmor";
	public static String cls_ItemArmor_obf = "abb";
	public static String cls_ItemStack_obf = "add";
	public static String cls_RenderBlocks = "net/minecraft/client/renderer/RenderBlocks";
	public static String cls_RenderBlocks_obf = "blm";
	public static String cls_World = "net/minecraft/world/World";
	public static String cls_World_obf = "ahb";

	public byte[] patchArmorProperties(String name, byte[] bytes) {
		String targetMethodName;
		String targetMethodSign;
		boolean isCauldron = LOTRModChecker.isCauldronServer();
		String targetMethodNameObf = targetMethodName = "ApplyArmor";
		String targetMethodSignObf = targetMethodSign = "(Lnet/minecraft/entity/EntityLivingBase;[Lnet/minecraft/item/ItemStack;Lnet/minecraft/util/DamageSource;D)F";
		if (isCauldron) {
			targetMethodName = "ApplyArmor";
			targetMethodSign = "(Lnet/minecraft/entity/EntityLivingBase;[Lnet/minecraft/item/ItemStack;Lnet/minecraft/util/DamageSource;DZ)F";
		}
		ClassNode classNode = new ClassNode();
		ClassReader classReader = new ClassReader(bytes);
		classReader.accept(classNode, 0);
		for (MethodNode method : classNode.methods) {
			AbstractInsnNode nodePrev;
			if (!method.name.equals(targetMethodName) || !method.desc.equals(targetMethodSign)) {
				continue;
			}
			FieldInsnNode nodeFound = null;
			block1:
			for (boolean armorObf : new boolean[]{false, true}) {
				for (int dmgObf = 0; dmgObf < 3; ++dmgObf) {
					String _armor = armorObf ? cls_ItemArmor_obf : cls_ItemArmor;
					String _dmg = new String[]{"field_77879_b", "damageReduceAmount", "c"}[dmgObf];
					FieldInsnNode nodeDmg = new FieldInsnNode(180, _armor, _dmg, "I");
					nodeFound = findNodeInMethod(method, nodeDmg);
					if (nodeFound != null) {
						break block1;
					}
				}
			}
			if (!((nodePrev = nodeFound.getPrevious()) instanceof VarInsnNode) || nodePrev.getOpcode() != 25 || ((VarInsnNode) nodePrev).var != 9) {
				System.out.println("WARNING! Expected ALOAD 9! Instead got " + nodePrev);
				System.out.println("WARNING! Things may break!");
			}
			method.instructions.remove(nodePrev);
			InsnList newIns = new InsnList();
			if (isCauldron) {
				newIns.add(new VarInsnNode(25, 8));
			} else {
				newIns.add(new VarInsnNode(25, 7));
			}
			newIns.add(new MethodInsnNode(184, "lotr/common/coremod/LOTRReplacedMethods$Enchants", "getDamageReduceAmount", "(Lnet/minecraft/item/ItemStack;)I", false));
			method.instructions.insert(nodeFound, newIns);
			method.instructions.remove(nodeFound);
			if (!isCauldron) {
				System.out.println("LOTRCore: Patched method " + method.name);
				continue;
			}
			System.out.println("LOTRCore: Patched method " + method.name + " for Cauldron");
		}
		ClassWriter writer = new ClassWriter(1);
		classNode.accept(writer);
		return writer.toByteArray();
	}

	public byte[] patchBlockAnvil(String name, byte[] bytes) {
		String targetMethodDesc;
		boolean isObf = !name.startsWith("net.minecraft");
		String targetMethodName = "getCollisionBoundingBoxFromPool";
		String targetMethodNameObf = "func_149668_a";
		String targetMethodDescObf = targetMethodDesc = "(Lnet/minecraft/world/World;III)Lnet/minecraft/util/AxisAlignedBB;";
		ClassNode classNode = new ClassNode();
		ClassReader classReader = new ClassReader(bytes);
		classReader.accept(classNode, 0);
		MethodNode newMethod = isObf ? new MethodNode(1, targetMethodNameObf, targetMethodDescObf, null, null) : new MethodNode(1, targetMethodName, targetMethodDesc, null, null);
		newMethod.instructions.add(new VarInsnNode(25, 0));
		newMethod.instructions.add(new VarInsnNode(25, 1));
		newMethod.instructions.add(new VarInsnNode(21, 2));
		newMethod.instructions.add(new VarInsnNode(21, 3));
		newMethod.instructions.add(new VarInsnNode(21, 4));
		newMethod.instructions.add(new MethodInsnNode(184, "lotr/common/coremod/LOTRReplacedMethods$Anvil", "getCollisionBoundingBoxFromPool", "(Lnet/minecraft/block/Block;Lnet/minecraft/world/World;III)Lnet/minecraft/util/AxisAlignedBB;", false));
		newMethod.instructions.add(new InsnNode(176));
		classNode.methods.add(newMethod);
		System.out.println("LOTRCore: Added method " + newMethod.name);
		ClassWriter writer = new ClassWriter(1);
		classNode.accept(writer);
		return writer.toByteArray();
	}

	public byte[] patchBlockCauldron(String name, byte[] bytes) {
		String targetMethodName = "getRenderType";
		String targetMethodNameObf = "func_149645_b";
		String targetMethodSign = "()I";
		ClassNode classNode = new ClassNode();
		ClassReader classReader = new ClassReader(bytes);
		classReader.accept(classNode, 0);
		for (MethodNode method : classNode.methods) {
			if (!method.name.equals(targetMethodName) && !method.name.equals(targetMethodNameObf) || !method.desc.equals(targetMethodSign)) {
				continue;
			}
			method.instructions.clear();
			InsnList newIns = new InsnList();
			newIns.add(new MethodInsnNode(184, "lotr/common/coremod/LOTRReplacedMethods$Cauldron", "getRenderType", "()I", false));
			newIns.add(new InsnNode(172));
			method.instructions.insert(newIns);
			System.out.println("LOTRCore: Patched method " + method.name);
		}
		ClassWriter writer = new ClassWriter(1);
		classNode.accept(writer);
		return writer.toByteArray();
	}

	public byte[] patchBlockDirt(String name, byte[] bytes) {
		String targetMethodName = "damageDropped";
		String targetMethodNameObf = "func_149692_a";
		String targetMethodSign = "(I)I";
		String targetMethodName2 = "createStackedBlock";
		String targetMethodNameObf2 = "func_149644_j";
		String targetMethodSign2 = "(I)Lnet/minecraft/item/ItemStack;";
		String targetMethodSignObf2 = "(I)Ladd;";
		String targetMethodName3 = "getSubBlocks";
		String targetMethodNameObf3 = "func_149666_a";
		String targetMethodSign3 = "(Lnet/minecraft/item/Item;Lnet/minecraft/creativetab/CreativeTabs;Ljava/util/List;)V";
		String targetMethodSignObf3 = "(Ladb;Labt;Ljava/util/List;)V";
		String targetMethodName4 = "getDamageValue";
		String targetMethodNameObf4 = "func_149643_k";
		String targetMethodSign4 = "(Lnet/minecraft/world/World;III)I";
		String targetMethodSignObf4 = "(Lahb;III)I";
		ClassNode classNode = new ClassNode();
		ClassReader classReader = new ClassReader(bytes);
		classReader.accept(classNode, 0);
		for (MethodNode method : classNode.methods) {
			InsnList newIns;
			if ("<clinit>".equals(method.name)) {
				LdcInsnNode nodeNameIndex1 = findNodeInMethod(method, new LdcInsnNode("default"), 1);
				method.instructions.set(nodeNameIndex1, new LdcInsnNode(LOTRReplacedMethods.Dirt.nameIndex1));
				System.out.println("LOTRCore: Patched method " + method.name);
			}
			if ((method.name.equals(targetMethodName) || method.name.equals(targetMethodNameObf)) && method.desc.equals(targetMethodSign)) {
				method.instructions.clear();
				newIns = new InsnList();
				newIns.add(new VarInsnNode(21, 1));
				newIns.add(new MethodInsnNode(184, "lotr/common/coremod/LOTRReplacedMethods$Dirt", "damageDropped", "(I)I", false));
				newIns.add(new InsnNode(172));
				method.instructions.insert(newIns);
				System.out.println("LOTRCore: Patched method " + method.name);
			}
			if ((method.name.equals(targetMethodName2) || method.name.equals(targetMethodNameObf2)) && (method.desc.equals(targetMethodSign2) || method.desc.equals(targetMethodSignObf2))) {
				method.instructions.clear();
				newIns = new InsnList();
				newIns.add(new VarInsnNode(25, 0));
				newIns.add(new VarInsnNode(21, 1));
				newIns.add(new MethodInsnNode(184, "lotr/common/coremod/LOTRReplacedMethods$Dirt", "createStackedBlock", "(Lnet/minecraft/block/Block;I)Lnet/minecraft/item/ItemStack;", false));
				newIns.add(new InsnNode(176));
				method.instructions.insert(newIns);
				System.out.println("LOTRCore: Patched method " + method.name);
			}
			if ((method.name.equals(targetMethodName3) || method.name.equals(targetMethodNameObf3)) && (method.desc.equals(targetMethodSign3) || method.desc.equals(targetMethodSignObf3))) {
				method.instructions.clear();
				newIns = new InsnList();
				newIns.add(new VarInsnNode(25, 0));
				newIns.add(new VarInsnNode(25, 1));
				newIns.add(new VarInsnNode(25, 2));
				newIns.add(new VarInsnNode(25, 3));
				newIns.add(new MethodInsnNode(184, "lotr/common/coremod/LOTRReplacedMethods$Dirt", "getSubBlocks", "(Lnet/minecraft/block/Block;Lnet/minecraft/item/Item;Lnet/minecraft/creativetab/CreativeTabs;Ljava/util/List;)V", false));
				newIns.add(new InsnNode(177));
				method.instructions.insert(newIns);
				System.out.println("LOTRCore: Patched method " + method.name);
			}
			if (!method.name.equals(targetMethodName4) && !method.name.equals(targetMethodNameObf4) || !method.desc.equals(targetMethodSign4) && !method.desc.equals(targetMethodSignObf4)) {
				continue;
			}
			method.instructions.clear();
			newIns = new InsnList();
			newIns.add(new VarInsnNode(25, 1));
			newIns.add(new VarInsnNode(21, 2));
			newIns.add(new VarInsnNode(21, 3));
			newIns.add(new VarInsnNode(21, 4));
			newIns.add(new MethodInsnNode(184, "lotr/common/coremod/LOTRReplacedMethods$Dirt", "getDamageValue", "(Lnet/minecraft/world/World;III)I", false));
			newIns.add(new InsnNode(172));
			method.instructions.insert(newIns);
			System.out.println("LOTRCore: Patched method " + method.name);
		}
		ClassWriter writer = new ClassWriter(1);
		classNode.accept(writer);
		return writer.toByteArray();
	}

	public byte[] patchBlockFence(String name, byte[] bytes) {
		String targetMethodName2;
		String targetMethodName = "canConnectFenceTo";
		String targetMethodNameObf = "func_149826_e";
		String targetMethodSign = "(Lnet/minecraft/world/IBlockAccess;III)Z";
		String targetMethodSignObf = "(Lahl;III)Z";
		String targetMethodNameObf2 = targetMethodName2 = "func_149825_a";
		String targetMethodSign2 = "(Lnet/minecraft/block/Block;)Z";
		String targetMethodSignObf2 = "(Laji;)Z";
		ClassNode classNode = new ClassNode();
		ClassReader classReader = new ClassReader(bytes);
		classReader.accept(classNode, 0);
		for (MethodNode method : classNode.methods) {
			InsnList newIns;
			if ((method.name.equals(targetMethodName) || method.name.equals(targetMethodNameObf)) && (method.desc.equals(targetMethodSign) || method.desc.equals(targetMethodSignObf))) {
				method.instructions.clear();
				newIns = new InsnList();
				newIns.add(new VarInsnNode(25, 1));
				newIns.add(new VarInsnNode(21, 2));
				newIns.add(new VarInsnNode(21, 3));
				newIns.add(new VarInsnNode(21, 4));
				newIns.add(new MethodInsnNode(184, "lotr/common/coremod/LOTRReplacedMethods$Fence", "canConnectFenceTo", "(Lnet/minecraft/world/IBlockAccess;III)Z", false));
				newIns.add(new InsnNode(172));
				method.instructions.insert(newIns);
				System.out.println("LOTRCore: Patched method " + method.name);
			}
			if (!method.name.equals(targetMethodName2) || !method.desc.equals(targetMethodSign2) && !method.desc.equals(targetMethodSignObf2)) {
				continue;
			}
			method.instructions.clear();
			newIns = new InsnList();
			newIns.add(new VarInsnNode(25, 0));
			newIns.add(new MethodInsnNode(184, "lotr/common/coremod/LOTRReplacedMethods$Fence", "canPlacePressurePlate", "(Lnet/minecraft/block/Block;)Z", false));
			newIns.add(new InsnNode(172));
			method.instructions.insert(newIns);
			System.out.println("LOTRCore: Patched method " + method.name);
		}
		ClassWriter writer = new ClassWriter(1);
		classNode.accept(writer);
		return writer.toByteArray();
	}

	public byte[] patchBlockFire(String name, byte[] bytes) {
		String targetMethodName2;
		String targetMethodName = "updateTick";
		String targetMethodNameObf = "func_149674_a";
		String targetMethodSign = "(Lnet/minecraft/world/World;IIILjava/util/Random;)V";
		String targetMethodSignObf = "(Lahb;IIILjava/util/Random;)V";
		String targetMethodNameObf2 = targetMethodName2 = "tryCatchFire";
		String targetMethodSign2 = "(Lnet/minecraft/world/World;IIIILjava/util/Random;ILnet/minecraftforge/common/util/ForgeDirection;)V";
		String targetMethodSignObf2 = "(Lahb;IIIILjava/util/Random;ILnet/minecraftforge/common/util/ForgeDirection;)V";
		ClassNode classNode = new ClassNode();
		ClassReader classReader = new ClassReader(bytes);
		classReader.accept(classNode, 0);
		for (MethodNode method : classNode.methods) {
			MethodInsnNode mNod;
			InsnList newIns;
			if ((method.name.equals(targetMethodName) || method.name.equals(targetMethodNameObf)) && (method.desc.equals(targetMethodSign) || method.desc.equals(targetMethodSignObf))) {
				MethodInsnNode lastTryCatchFire = null;
				for (AbstractInsnNode nod : method.instructions.toArray()) {
					if (!(nod instanceof MethodInsnNode) || nod.getOpcode() != 183) {
						continue;
					}
					mNod = (MethodInsnNode) nod;
					if (!"tryCatchFire".equals(mNod.name)) {
						continue;
					}
					lastTryCatchFire = (MethodInsnNode) nod;
				}
				JumpInsnNode jumpToReturn = null;
				for (AbstractInsnNode nod : method.instructions.toArray()) {
					if (!(nod instanceof MethodInsnNode) || nod.getOpcode() != 182) {
						continue;
					}
					MethodInsnNode mNod2 = (MethodInsnNode) nod;
					if (!"getGameRuleBooleanValue".equals(mNod2.name) && !"func_82766_b".equals(mNod2.name)) {
						continue;
					}
					jumpToReturn = (JumpInsnNode) mNod2.getNext();
				}
				LabelNode labelReturn = jumpToReturn.label;
				if (jumpToReturn.getOpcode() != 153) {
					System.out.println("WARNING! WARNING! THIS OPCODE SHOULD HAVE BEEN IFEQ!");
					System.out.println("WARNING! INSTEAD IT WAS " + jumpToReturn.getOpcode());
					System.out.println("WARNING! NOT SURE WHAT TO DO HERE! THINGS MIGHT BREAK!");
				}
				newIns = new InsnList();
				newIns.add(new MethodInsnNode(184, "lotr/common/coremod/LOTRReplacedMethods$Fire", "isFireSpreadDisabled", "()Z", false));
				newIns.add(new JumpInsnNode(154, labelReturn));
				method.instructions.insert(lastTryCatchFire, newIns);
				System.out.println("LOTRCore: Patched method " + method.name);
			}
			if (!method.name.equals(targetMethodName2) || !method.desc.equals(targetMethodSign2) && !method.desc.equals(targetMethodSignObf2)) {
				continue;
			}
			MethodInsnNode canLightning = null;
			for (AbstractInsnNode nod : method.instructions.toArray()) {
				if (!(nod instanceof MethodInsnNode) || nod.getOpcode() != 182) {
					continue;
				}
				mNod = (MethodInsnNode) nod;
				if (!"canLightningStrikeAt".equals(mNod.name) && !"func_72951_B".equals(mNod.name)) {
					continue;
				}
				canLightning = mNod;
				break;
			}
			JumpInsnNode jumpToSetAir = (JumpInsnNode) canLightning.getNext();
			LabelNode labelSetAir = jumpToSetAir.label;
			if (jumpToSetAir.getOpcode() != 154) {
				System.out.println("WARNING! WARNING! THIS OPCODE SHOULD HAVE BEEN IFNE!");
				System.out.println("WARNING! INSTEAD IT WAS " + jumpToSetAir.getOpcode());
				System.out.println("WARNING! NOT SURE WHAT TO DO HERE! THINGS MIGHT BREAK!");
			}
			newIns = new InsnList();
			newIns.add(new MethodInsnNode(184, "lotr/common/coremod/LOTRReplacedMethods$Fire", "isFireSpreadDisabled", "()Z", false));
			newIns.add(new JumpInsnNode(154, labelSetAir));
			method.instructions.insert(jumpToSetAir, newIns);
			method.instructions.resetLabels();
			System.out.println("LOTRCore: Patched method " + method.name);
		}
		ClassWriter writer = new ClassWriter(3);
		classNode.accept(writer);
		return writer.toByteArray();
	}

	public byte[] patchBlockGrass(String name, byte[] bytes) {
		String targetMethodName = "updateTick";
		String targetMethodNameObf = "func_149674_a";
		String targetMethodSign = "(Lnet/minecraft/world/World;IIILjava/util/Random;)V";
		String targetMethodSignObf = "(Lahb;IIILjava/util/Random;)V";
		ClassNode classNode = new ClassNode();
		ClassReader classReader = new ClassReader(bytes);
		classReader.accept(classNode, 0);
		for (MethodNode method : classNode.methods) {
			if (!method.name.equals(targetMethodName) && !method.name.equals(targetMethodNameObf) || !method.desc.equals(targetMethodSign) && !method.desc.equals(targetMethodSignObf)) {
				continue;
			}
			method.instructions.clear();
			InsnList newIns = new InsnList();
			newIns.add(new VarInsnNode(25, 1));
			newIns.add(new VarInsnNode(21, 2));
			newIns.add(new VarInsnNode(21, 3));
			newIns.add(new VarInsnNode(21, 4));
			newIns.add(new VarInsnNode(25, 5));
			newIns.add(new MethodInsnNode(184, "lotr/common/coremod/LOTRReplacedMethods$Grass", "updateTick_optimised", "(Lnet/minecraft/world/World;IIILjava/util/Random;)V", false));
			newIns.add(new InsnNode(177));
			method.instructions.insert(newIns);
			System.out.println("LOTRCore: Patched method " + method.name);
		}
		ClassWriter writer = new ClassWriter(1);
		classNode.accept(writer);
		return writer.toByteArray();
	}

	public byte[] patchBlockPistonBase(String name, byte[] bytes) {
		ClassNode classNode = new ClassNode();
		ClassReader classReader = new ClassReader(bytes);
		classReader.accept(classNode, 0);
		for (MethodNode method : classNode.methods) {
			int skip = 0;
			do {
				MethodInsnNode nodeFound = null;
				block2:
				for (boolean pistonObf : new boolean[]{false, true}) {
					for (boolean canPushObf : new boolean[]{false, true}) {
						for (boolean blockObf : new boolean[]{false, true}) {
							for (boolean worldObf : new boolean[]{false, true}) {
								String _piston = pistonObf ? cls_BlockPistonBase_obf : cls_BlockPistonBase;
								String _canPush = canPushObf ? "func_150080_a" : "canPushBlock";
								String _block = blockObf ? cls_Block_obf : cls_Block;
								String _world = worldObf ? cls_World_obf : cls_World;
								MethodInsnNode nodeInvokeCanPush = new MethodInsnNode(184, _piston, _canPush, "(L" + _block + ";L" + _world + ";IIIZ)Z", false);
								nodeFound = findNodeInMethod(method, nodeInvokeCanPush, skip);
								if (nodeFound != null) {
									break block2;
								}
							}
						}
					}
				}
				if (nodeFound == null) {
					break;
				}
				nodeFound.setOpcode(184);
				nodeFound.owner = "lotr/common/coremod/LOTRReplacedMethods$Piston";
				nodeFound.name = "canPushBlock";
				nodeFound.desc = "(Lnet/minecraft/block/Block;Lnet/minecraft/world/World;IIIZ)Z";
				nodeFound.itf = false;
				++skip;
			} while (true);
			if (skip <= 0) {
				continue;
			}
			System.out.println("LOTRCore: Patched method " + method.name + " " + skip + " times");
		}
		ClassWriter writer = new ClassWriter(1);
		classNode.accept(writer);
		return writer.toByteArray();
	}

	public byte[] patchBlockStaticLiquid(String name, byte[] bytes) {
		String targetMethodName = "updateTick";
		String targetMethodNameObf = "func_149674_a";
		String targetMethodSign = "(Lnet/minecraft/world/World;IIILjava/util/Random;)V";
		String targetMethodSignObf = "(Lahb;IIILjava/util/Random;)V";
		ClassNode classNode = new ClassNode();
		ClassReader classReader = new ClassReader(bytes);
		classReader.accept(classNode, 0);
		for (MethodNode method : classNode.methods) {
			if (!method.name.equals(targetMethodName) && !method.name.equals(targetMethodNameObf) || !method.desc.equals(targetMethodSign) && !method.desc.equals(targetMethodSignObf)) {
				continue;
			}
			method.instructions.clear();
			InsnList newIns = new InsnList();
			newIns.add(new VarInsnNode(25, 0));
			newIns.add(new VarInsnNode(25, 1));
			newIns.add(new VarInsnNode(21, 2));
			newIns.add(new VarInsnNode(21, 3));
			newIns.add(new VarInsnNode(21, 4));
			newIns.add(new VarInsnNode(25, 5));
			newIns.add(new MethodInsnNode(184, "lotr/common/coremod/LOTRReplacedMethods$StaticLiquid", "updateTick_optimised", "(Lnet/minecraft/block/Block;Lnet/minecraft/world/World;IIILjava/util/Random;)V", false));
			newIns.add(new InsnNode(177));
			method.instructions.insert(newIns);
			System.out.println("LOTRCore: Patched method " + method.name);
		}
		ClassWriter writer = new ClassWriter(1);
		classNode.accept(writer);
		return writer.toByteArray();
	}

	public byte[] patchBlockStone(String name, byte[] bytes) {
		String targetMethodDesc;
		boolean isObf = !name.startsWith("net.minecraft");
		ClassNode classNode = new ClassNode();
		ClassReader classReader = new ClassReader(bytes);
		classReader.accept(classNode, 0);
		String targetMethodName = "getIcon";
		String targetMethodNameObf = "func_149673_e";
		String targetMethodDescObf = targetMethodDesc = "(Lnet/minecraft/world/IBlockAccess;IIII)Lnet/minecraft/util/IIcon;";
		MethodNode newMethod = isObf ? new MethodNode(1, targetMethodNameObf, targetMethodDescObf, null, null) : new MethodNode(1, targetMethodName, targetMethodDesc, null, null);
		newMethod.instructions.add(new VarInsnNode(25, 0));
		newMethod.instructions.add(new VarInsnNode(25, 1));
		newMethod.instructions.add(new VarInsnNode(21, 2));
		newMethod.instructions.add(new VarInsnNode(21, 3));
		newMethod.instructions.add(new VarInsnNode(21, 4));
		newMethod.instructions.add(new VarInsnNode(21, 5));
		newMethod.instructions.add(new MethodInsnNode(184, "lotr/common/coremod/LOTRReplacedMethods$Stone", "getIconWorld", "(Lnet/minecraft/block/Block;Lnet/minecraft/world/IBlockAccess;IIII)Lnet/minecraft/util/IIcon;", false));
		newMethod.instructions.add(new InsnNode(176));
		classNode.methods.add(newMethod);
		System.out.println("LOTRCore: Added method " + newMethod.name);
		targetMethodName = "getIcon";
		targetMethodNameObf = "func_149691_a";
		targetMethodDescObf = targetMethodDesc = "(II)Lnet/minecraft/util/IIcon;";
		newMethod = isObf ? new MethodNode(1, targetMethodNameObf, targetMethodDescObf, null, null) : new MethodNode(1, targetMethodName, targetMethodDesc, null, null);
		newMethod.instructions.add(new VarInsnNode(25, 0));
		newMethod.instructions.add(new FieldInsnNode(180, cls_Block, isObf ? "field_149761_L" : "blockIcon", "Lnet/minecraft/util/IIcon;"));
		newMethod.instructions.add(new VarInsnNode(58, 3));
		newMethod.instructions.add(new VarInsnNode(25, 0));
		newMethod.instructions.add(new VarInsnNode(25, 3));
		newMethod.instructions.add(new VarInsnNode(21, 1));
		newMethod.instructions.add(new VarInsnNode(21, 2));
		newMethod.instructions.add(new MethodInsnNode(184, "lotr/common/coremod/LOTRReplacedMethods$Stone", "getIconSideMeta", "(Lnet/minecraft/block/Block;Lnet/minecraft/util/IIcon;II)Lnet/minecraft/util/IIcon;", false));
		newMethod.instructions.add(new InsnNode(176));
		classNode.methods.add(newMethod);
		System.out.println("LOTRCore: Added method " + newMethod.name);
		ClassWriter writer = new ClassWriter(1);
		classNode.accept(writer);
		return writer.toByteArray();
	}

	public byte[] patchBlockTrapdoor(String name, byte[] bytes) {
		String targetMethodName2;
		String targetMethodSign3;
		String targetMethodName = "canPlaceBlockOnSide";
		String targetMethodNameObf = "func_149707_d";
		String targetMethodSign = "(Lnet/minecraft/world/World;IIII)Z";
		String targetMethodSignObf = "(Lahb;IIII)Z";
		String targetMethodNameObf2 = targetMethodName2 = "func_150119_a";
		String targetMethodSign2 = "(Lnet/minecraft/block/Block;)Z";
		String targetMethodSignObf2 = "(Laji;)Z";
		String targetMethodName3 = "getRenderType";
		String targetMethodNameObf3 = "func_149645_b";
		String targetMethodSignObf3 = targetMethodSign3 = "()I";
		ClassNode classNode = new ClassNode();
		ClassReader classReader = new ClassReader(bytes);
		classReader.accept(classNode, 0);
		for (MethodNode method : classNode.methods) {
			InsnList newIns;
			if ((method.name.equals(targetMethodName) || method.name.equals(targetMethodNameObf)) && (method.desc.equals(targetMethodSign) || method.desc.equals(targetMethodSignObf))) {
				method.instructions.clear();
				newIns = new InsnList();
				newIns.add(new VarInsnNode(25, 1));
				newIns.add(new VarInsnNode(21, 2));
				newIns.add(new VarInsnNode(21, 3));
				newIns.add(new VarInsnNode(21, 4));
				newIns.add(new VarInsnNode(21, 5));
				newIns.add(new MethodInsnNode(184, "lotr/common/coremod/LOTRReplacedMethods$Trapdoor", "canPlaceBlockOnSide", "(Lnet/minecraft/world/World;IIII)Z", false));
				newIns.add(new InsnNode(172));
				method.instructions.insert(newIns);
				System.out.println("LOTRCore: Patched method " + method.name);
			}
			if (method.name.equals(targetMethodName2) && (method.desc.equals(targetMethodSign2) || method.desc.equals(targetMethodSignObf2))) {
				method.instructions.clear();
				newIns = new InsnList();
				newIns.add(new VarInsnNode(25, 0));
				newIns.add(new MethodInsnNode(184, "lotr/common/coremod/LOTRReplacedMethods$Trapdoor", "isValidSupportBlock", "(Lnet/minecraft/block/Block;)Z", false));
				newIns.add(new InsnNode(172));
				method.instructions.insert(newIns);
				System.out.println("LOTRCore: Patched method " + method.name);
			}
			if (!method.name.equals(targetMethodName3) && !method.name.equals(targetMethodNameObf3) || !method.desc.equals(targetMethodSign3)) {
				continue;
			}
			method.instructions.clear();
			newIns = new InsnList();
			newIns.add(new VarInsnNode(25, 0));
			newIns.add(new MethodInsnNode(184, "lotr/common/coremod/LOTRReplacedMethods$Trapdoor", "getRenderType", "(Lnet/minecraft/block/Block;)I", false));
			newIns.add(new InsnNode(172));
			method.instructions.insert(newIns);
			System.out.println("LOTRCore: Patched method " + method.name);
		}
		ClassWriter writer = new ClassWriter(1);
		classNode.accept(writer);
		return writer.toByteArray();
	}

	public byte[] patchBlockWall(String name, byte[] bytes) {
		String targetMethodName = "canConnectWallTo";
		String targetMethodNameObf = "func_150091_e";
		String targetMethodSign = "(Lnet/minecraft/world/IBlockAccess;III)Z";
		String targetMethodSignObf = "(Lahl;III)Z";
		ClassNode classNode = new ClassNode();
		ClassReader classReader = new ClassReader(bytes);
		classReader.accept(classNode, 0);
		for (MethodNode method : classNode.methods) {
			if (!method.name.equals(targetMethodName) && !method.name.equals(targetMethodNameObf) || !method.desc.equals(targetMethodSign) && !method.desc.equals(targetMethodSignObf)) {
				continue;
			}
			method.instructions.clear();
			InsnList newIns = new InsnList();
			newIns.add(new VarInsnNode(25, 1));
			newIns.add(new VarInsnNode(21, 2));
			newIns.add(new VarInsnNode(21, 3));
			newIns.add(new VarInsnNode(21, 4));
			newIns.add(new MethodInsnNode(184, "lotr/common/coremod/LOTRReplacedMethods$Wall", "canConnectWallTo", "(Lnet/minecraft/world/IBlockAccess;III)Z", false));
			newIns.add(new InsnNode(172));
			method.instructions.insert(newIns);
			System.out.println("LOTRCore: Patched method " + method.name);
		}
		ClassWriter writer = new ClassWriter(1);
		classNode.accept(writer);
		return writer.toByteArray();
	}

	public byte[] patchDoorInteract(String name, byte[] bytes) {
		String targetMethodName;
		String targetMethodNameObf = targetMethodName = "func_151503_a";
		String targetMethodSign = "(III)Lnet/minecraft/block/BlockDoor;";
		String targetMethodSignObf = "(III)Lakn;";
		ClassNode classNode = new ClassNode();
		ClassReader classReader = new ClassReader(bytes);
		classReader.accept(classNode, 0);
		for (MethodNode method : classNode.methods) {
			if (!method.name.equals(targetMethodName) || !method.desc.equals(targetMethodSign) && !method.desc.equals(targetMethodSignObf)) {
				continue;
			}
			FieldInsnNode nodeFound = null;
			block1:
			for (boolean blocksObf : new boolean[]{false, true}) {
				for (boolean doorObf : new boolean[]{false, true}) {
					String _blocks = blocksObf ? cls_Blocks_obf : cls_Blocks;
					String _door = doorObf ? "field_150466_ao" : "wooden_door";
					FieldInsnNode nodeGetDoor = new FieldInsnNode(178, _blocks, _door, "Lnet/minecraft/block/Block;");
					nodeFound = findNodeInMethod(method, nodeGetDoor);
					if (nodeFound != null) {
						break block1;
					}
				}
			}
			MethodInsnNode nodeCheckDoor = new MethodInsnNode(184, "lotr/common/coremod/LOTRReplacedMethods$PathFinder", "isWoodenDoor", "(Lnet/minecraft/block/Block;)Z", false);
			method.instructions.set(nodeFound, nodeCheckDoor);
			JumpInsnNode nodeIf = (JumpInsnNode) nodeCheckDoor.getNext();
			if (nodeIf.getOpcode() != 165) {
				System.out.println("WARNING! WARNING! THIS OPCODE SHOULD HAVE BEEN IF_ACMPEQ!");
				System.out.println("WARNING! INSTEAD IT WAS " + nodeIf.getOpcode());
				System.out.println("WARNING! Setting it to IF_NE anyway");
			}
			nodeIf.setOpcode(154);
			System.out.println("LOTRCore: Patched method " + method.name);
		}
		ClassWriter writer = new ClassWriter(1);
		classNode.accept(writer);
		return writer.toByteArray();
	}

	public byte[] patchEnchantmentHelper(String name, byte[] bytes) {
		String targetMethodName2;
		String targetMethodName = "getEnchantmentModifierLiving";
		String targetMethodNameObf = "func_77512_a";
		String targetMethodSign = "(Lnet/minecraft/entity/EntityLivingBase;Lnet/minecraft/entity/EntityLivingBase;)F";
		String targetMethodSignObf = "(Lsv;Lsv;)F";
		String targetMethodNameObf2 = targetMethodName2 = "func_152377_a";
		String targetMethodSign2 = "(Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/EnumCreatureAttribute;)F";
		String targetMethodSignObf2 = "(Ladd;Lsz;)F";
		String targetMethodName3 = "getSilkTouchModifier";
		String targetMethodNameObf3 = "func_77502_d";
		String targetMethodSign3 = "(Lnet/minecraft/entity/EntityLivingBase;)Z";
		String targetMethodSignObf3 = "(Lsv;)Z";
		String targetMethodName4 = "getKnockbackModifier";
		String targetMethodNameObf4 = "func_77507_b";
		String targetMethodSign4 = "(Lnet/minecraft/entity/EntityLivingBase;Lnet/minecraft/entity/EntityLivingBase;)I";
		String targetMethodSignObf4 = "(Lsv;Lsv;)I";
		String targetMethodName5 = "getFortuneModifier";
		String targetMethodNameObf5 = "func_77517_e";
		String targetMethodSign5 = "(Lnet/minecraft/entity/EntityLivingBase;)I";
		String targetMethodSignObf5 = "(Lsv;)I";
		String targetMethodName6 = "getLootingModifier";
		String targetMethodNameObf6 = "func_77519_f";
		String targetMethodSign6 = "(Lnet/minecraft/entity/EntityLivingBase;)I";
		String targetMethodSignObf6 = "(Lsv;)I";
		String targetMethodName7 = "getEnchantmentModifierDamage";
		String targetMethodNameObf7 = "func_77508_a";
		String targetMethodSign7 = "([Lnet/minecraft/item/ItemStack;Lnet/minecraft/util/DamageSource;)I";
		String targetMethodSignObf7 = "([Ladd;Lro;)I";
		String targetMethodName8 = "getFireAspectModifier";
		String targetMethodNameObf8 = "func_90036_a";
		String targetMethodSign8 = "(Lnet/minecraft/entity/EntityLivingBase;)I";
		String targetMethodSignObf8 = "(Lsv;)I";
		ClassNode classNode = new ClassNode();
		ClassReader classReader = new ClassReader(bytes);
		classReader.accept(classNode, 0);
		for (MethodNode method : classNode.methods) {
			InsnNode nodeReturn;
			InsnList extraIns;
			if ((method.name.equals(targetMethodName) || method.name.equals(targetMethodNameObf)) && (method.desc.equals(targetMethodSign) || method.desc.equals(targetMethodSignObf))) {
				nodeReturn = findNodeInMethod(method, new InsnNode(174));
				extraIns = new InsnList();
				extraIns.add(new VarInsnNode(25, 0));
				extraIns.add(new VarInsnNode(25, 1));
				extraIns.add(new MethodInsnNode(184, "lotr/common/coremod/LOTRReplacedMethods$Enchants", "getEnchantmentModifierLiving", "(FLnet/minecraft/entity/EntityLivingBase;Lnet/minecraft/entity/EntityLivingBase;)F", false));
				method.instructions.insertBefore(nodeReturn, extraIns);
				System.out.println("LOTRCore: Patched method " + method.name);
			}
			if (method.name.equals(targetMethodName2) && (method.desc.equals(targetMethodSign2) || method.desc.equals(targetMethodSignObf2))) {
				nodeReturn = findNodeInMethod(method, new InsnNode(174));
				extraIns = new InsnList();
				extraIns.add(new VarInsnNode(25, 0));
				extraIns.add(new VarInsnNode(25, 1));
				extraIns.add(new MethodInsnNode(184, "lotr/common/coremod/LOTRReplacedMethods$Enchants", "func_152377_a", "(FLnet/minecraft/item/ItemStack;Lnet/minecraft/entity/EnumCreatureAttribute;)F", false));
				method.instructions.insertBefore(nodeReturn, extraIns);
				System.out.println("LOTRCore: Patched method " + method.name);
			}
			if ((method.name.equals(targetMethodName3) || method.name.equals(targetMethodNameObf3)) && (method.desc.equals(targetMethodSign3) || method.desc.equals(targetMethodSignObf3))) {
				nodeReturn = findNodeInMethod(method, new InsnNode(172));
				extraIns = new InsnList();
				extraIns.add(new VarInsnNode(25, 0));
				extraIns.add(new MethodInsnNode(184, "lotr/common/coremod/LOTRReplacedMethods$Enchants", "getSilkTouchModifier", "(ZLnet/minecraft/entity/EntityLivingBase;)Z", false));
				method.instructions.insertBefore(nodeReturn, extraIns);
				System.out.println("LOTRCore: Patched method " + method.name);
			}
			if ((method.name.equals(targetMethodName4) || method.name.equals(targetMethodNameObf4)) && (method.desc.equals(targetMethodSign4) || method.desc.equals(targetMethodSignObf4))) {
				nodeReturn = findNodeInMethod(method, new InsnNode(172));
				extraIns = new InsnList();
				extraIns.add(new VarInsnNode(25, 0));
				extraIns.add(new VarInsnNode(25, 1));
				extraIns.add(new MethodInsnNode(184, "lotr/common/coremod/LOTRReplacedMethods$Enchants", "getKnockbackModifier", "(ILnet/minecraft/entity/EntityLivingBase;Lnet/minecraft/entity/EntityLivingBase;)I", false));
				method.instructions.insertBefore(nodeReturn, extraIns);
				System.out.println("LOTRCore: Patched method " + method.name);
			}
			if ((method.name.equals(targetMethodName5) || method.name.equals(targetMethodNameObf5)) && (method.desc.equals(targetMethodSign5) || method.desc.equals(targetMethodSignObf5))) {
				nodeReturn = findNodeInMethod(method, new InsnNode(172));
				extraIns = new InsnList();
				extraIns.add(new VarInsnNode(25, 0));
				extraIns.add(new MethodInsnNode(184, "lotr/common/coremod/LOTRReplacedMethods$Enchants", "getFortuneModifier", "(ILnet/minecraft/entity/EntityLivingBase;)I", false));
				method.instructions.insertBefore(nodeReturn, extraIns);
				System.out.println("LOTRCore: Patched method " + method.name);
			}
			if ((method.name.equals(targetMethodName6) || method.name.equals(targetMethodNameObf6)) && (method.desc.equals(targetMethodSign6) || method.desc.equals(targetMethodSignObf6))) {
				nodeReturn = findNodeInMethod(method, new InsnNode(172));
				extraIns = new InsnList();
				extraIns.add(new VarInsnNode(25, 0));
				extraIns.add(new MethodInsnNode(184, "lotr/common/coremod/LOTRReplacedMethods$Enchants", "getLootingModifier", "(ILnet/minecraft/entity/EntityLivingBase;)I", false));
				method.instructions.insertBefore(nodeReturn, extraIns);
				System.out.println("LOTRCore: Patched method " + method.name);
			}
			if ((method.name.equals(targetMethodName7) || method.name.equals(targetMethodNameObf7)) && (method.desc.equals(targetMethodSign7) || method.desc.equals(targetMethodSignObf7))) {
				nodeReturn = findNodeInMethod(method, new InsnNode(172));
				extraIns = new InsnList();
				extraIns.add(new VarInsnNode(25, 0));
				extraIns.add(new VarInsnNode(25, 1));
				extraIns.add(new MethodInsnNode(184, "lotr/common/coremod/LOTRReplacedMethods$Enchants", "getSpecialArmorProtection", "(I[Lnet/minecraft/item/ItemStack;Lnet/minecraft/util/DamageSource;)I", false));
				method.instructions.insertBefore(nodeReturn, extraIns);
				System.out.println("LOTRCore: Patched method " + method.name);
			}
			if (!method.name.equals(targetMethodName8) && !method.name.equals(targetMethodNameObf8) || !method.desc.equals(targetMethodSign8) && !method.desc.equals(targetMethodSignObf8)) {
				continue;
			}
			nodeReturn = findNodeInMethod(method, new InsnNode(172));
			extraIns = new InsnList();
			extraIns.add(new VarInsnNode(25, 0));
			extraIns.add(new MethodInsnNode(184, "lotr/common/coremod/LOTRReplacedMethods$Enchants", "getFireAspectModifier", "(ILnet/minecraft/entity/EntityLivingBase;)I", false));
			method.instructions.insertBefore(nodeReturn, extraIns);
			System.out.println("LOTRCore: Patched method " + method.name);
		}
		ClassWriter writer = new ClassWriter(1);
		classNode.accept(writer);
		return writer.toByteArray();
	}

	public byte[] patchEnchantmentProtection(String name, byte[] bytes) {
		String targetMethodName = "getFireTimeForEntity";
		String targetMethodNameObf = "func_92093_a";
		String targetMethodSign = "(Lnet/minecraft/entity/Entity;I)I";
		String targetMethodSignObf = "(Lsa;I)I";
		ClassNode classNode = new ClassNode();
		ClassReader classReader = new ClassReader(bytes);
		classReader.accept(classNode, 0);
		for (MethodNode method : classNode.methods) {
			if (!method.name.equals(targetMethodName) && !method.name.equals(targetMethodNameObf) || !method.desc.equals(targetMethodSign) && !method.desc.equals(targetMethodSignObf)) {
				continue;
			}
			VarInsnNode nodeIStore = findNodeInMethod(method, new VarInsnNode(54, 2));
			InsnList newIns = new InsnList();
			newIns.add(new VarInsnNode(25, 0));
			newIns.add(new MethodInsnNode(184, "lotr/common/coremod/LOTRReplacedMethods$Enchants", "getMaxFireProtectionLevel", "(ILnet/minecraft/entity/Entity;)I", false));
			method.instructions.insertBefore(nodeIStore, newIns);
			System.out.println("LOTRCore: Patched method " + method.name);
		}
		ClassWriter writer = new ClassWriter(1);
		classNode.accept(writer);
		return writer.toByteArray();
	}

	public byte[] patchEntityClientPlayerMP(String name, byte[] bytes) {
		String targetMethodName;
		String targetMethodNameObf = targetMethodName = "func_110318_g";
		String targetMethodSign = "()V";
		ClassNode classNode = new ClassNode();
		ClassReader classReader = new ClassReader(bytes);
		classReader.accept(classNode, 0);
		for (MethodNode method : classNode.methods) {
			if (!method.name.equals(targetMethodName) || !method.desc.equals(targetMethodSign)) {
				continue;
			}
			method.instructions.clear();
			InsnList newIns = new InsnList();
			newIns.add(new VarInsnNode(25, 0));
			newIns.add(new MethodInsnNode(184, "lotr/common/coremod/LOTRReplacedMethods$ClientPlayer", "horseJump", "(Lnet/minecraft/client/entity/EntityClientPlayerMP;)V", false));
			newIns.add(new InsnNode(177));
			method.instructions.insert(newIns);
			System.out.println("LOTRCore: Patched method " + method.name);
		}
		ClassWriter writer = new ClassWriter(1);
		classNode.accept(writer);
		return writer.toByteArray();
	}

	public byte[] patchEntityHorse(String name, byte[] bytes) {
		String targetMethodName = "moveEntityWithHeading";
		String targetMethodNameObf = "func_70612_e";
		String targetMethodSign = "(FF)V";
		ClassNode classNode = new ClassNode();
		ClassReader classReader = new ClassReader(bytes);
		classReader.accept(classNode, 0);
		for (MethodNode method : classNode.methods) {
			if (!method.name.equals(targetMethodName) && !method.name.equals(targetMethodNameObf) || !method.desc.equals(targetMethodSign)) {
				continue;
			}
			FieldInsnNode nodeIsRemote = null;
			block1:
			for (boolean worldObf : new boolean[]{false, true}) {
				boolean[] arrbl = {false, true};
				int n = arrbl.length;
				for (boolean b : arrbl) {
					String _world = worldObf ? cls_World_obf : cls_World;
					nodeIsRemote = findNodeInMethod(method, new FieldInsnNode(180, _world, b ? "field_72995_K" : "isRemote", "Z"));
					if (nodeIsRemote != null) {
						break block1;
					}
				}
			}
			VarInsnNode nodeLoadThisEntity = (VarInsnNode) nodeIsRemote.getPrevious().getPrevious();
			for (int l = 0; l < 2; ++l) {
				method.instructions.remove(nodeLoadThisEntity.getNext());
			}
			JumpInsnNode nodeIfTest = (JumpInsnNode) nodeLoadThisEntity.getNext();
			if (nodeIfTest.getOpcode() == 154) {
				nodeIfTest.setOpcode(153);
			} else {
				System.out.println("WARNING! Expected IFNE! Instead got " + nodeIfTest.getOpcode());
				System.out.println("WARNING! Things may break!");
			}
			InsnList newIns = new InsnList();
			newIns.add(new MethodInsnNode(184, "lotr/common/entity/LOTRMountFunctions", "canRiderControl_elseNoMotion", "(Lnet/minecraft/entity/EntityLiving;)Z", false));
			method.instructions.insert(nodeLoadThisEntity, newIns);
			System.out.println("LOTRCore: Patched method " + method.name);
		}
		ClassWriter writer = new ClassWriter(1);
		classNode.accept(writer);
		return writer.toByteArray();
	}

	public byte[] patchEntityLightningBolt(String name, byte[] bytes) {
		ClassNode classNode = new ClassNode();
		ClassReader classReader = new ClassReader(bytes);
		classReader.accept(classNode, 0);
		for (MethodNode method : classNode.methods) {
			if ("<init>".equals(method.name)) {
				AbstractInsnNode nodeSuperConstructor = null;
				Iterator<AbstractInsnNode> it = method.instructions.iterator();
				while (it.hasNext()) {
					AbstractInsnNode node = it.next();
					if (node instanceof MethodInsnNode && node.getOpcode() == 183) {
						nodeSuperConstructor = node;
						break;
					}
				}
				InsnList newIns = new InsnList();
				newIns.add(new VarInsnNode(25, 0));
				newIns.add(new MethodInsnNode(184, "lotr/common/coremod/LOTRReplacedMethods$Lightning", "init", "(Lnet/minecraft/entity/effect/EntityLightningBolt;)V", false));
				method.instructions.insert(nodeSuperConstructor, newIns);
				System.out.println("LOTRCore: Patched method " + method.name);
			}
		}
		for (MethodNode method : classNode.methods) {
			int count = 0;
			do {
				MethodInsnNode nodeSetBlock = null;
				block4:
				for (boolean worldObf : new boolean[]{false, true}) {
					for (boolean setBlockObf : new boolean[]{false, true}) {
						for (boolean blockObf : new boolean[]{false, true}) {
							String _world = worldObf ? cls_World_obf : cls_World;
							String _setBlock = setBlockObf ? "func_147449_b" : "setBlock";
							String _block = blockObf ? cls_Block_obf : cls_Block;
							nodeSetBlock = findNodeInMethod(method, new MethodInsnNode(182, _world, _setBlock, "(IIIL" + _block + ";)Z", false));
							if (nodeSetBlock != null) {
								break block4;
							}
						}
					}
				}
				if (nodeSetBlock == null) {
					break;
				}
				AbstractInsnNode nextNode = nodeSetBlock.getNext();
				if (nextNode.getOpcode() == 87) {
					method.instructions.remove(nodeSetBlock.getNext());
				}
				method.instructions.set(nodeSetBlock, new MethodInsnNode(184, "lotr/common/coremod/LOTRReplacedMethods$Lightning", "doSetBlock", "(Lnet/minecraft/world/World;IIILnet/minecraft/block/Block;)V", false));
				++count;
			} while (true);
			if (count <= 0) {
				continue;
			}
			System.out.println("LOTRCore: Patched method " + method.name + ": " + count + " instances of World.setBlock()");
		}
		ClassWriter writer = new ClassWriter(1);
		classNode.accept(writer);
		return writer.toByteArray();
	}

	public byte[] patchEntityLivingBase(String name, byte[] bytes) {
		String targetMethodName = "getTotalArmorValue";
		String targetMethodNameObf = "func_70658_aO";
		String targetMethodSign = "()I";
		String targetMethodName2 = "onDeath";
		String targetMethodNameObf2 = "func_70645_a";
		String targetMethodSign2 = "(Lnet/minecraft/util/DamageSource;)V";
		String targetMethodSignObf2 = "(Lro;)V";
		ClassNode classNode = new ClassNode();
		ClassReader classReader = new ClassReader(bytes);
		classReader.accept(classNode, 0);
		for (MethodNode method : classNode.methods) {
			if ((method.name.equals(targetMethodName) || method.name.equals(targetMethodNameObf)) && method.desc.equals(targetMethodSign)) {
				VarInsnNode nodeStore = findNodeInMethod(method, new VarInsnNode(54, 6));
				for (int l = 0; l < 3; l++) {
					method.instructions.remove(nodeStore.getPrevious());
				}
				AbstractInsnNode newPrev = nodeStore.getPrevious();
				if (!(newPrev instanceof VarInsnNode) || newPrev.getOpcode() != 25 || ((VarInsnNode) newPrev).var != 5) {
					System.out.println("WARNING! Expected ALOAD 5! Instead got " + newPrev);
					System.out.println("WARNING! Things may break!");
				}
				InsnList newIns = new InsnList();
				newIns.add(new MethodInsnNode(184, "lotr/common/coremod/LOTRReplacedMethods$Enchants", "getDamageReduceAmount", "(Lnet/minecraft/item/ItemStack;)I", false));
				method.instructions.insertBefore(nodeStore, newIns);
				System.out.println("LOTRCore: Patched method " + method.name);
			}
			if ((method.name.equals(targetMethodName2) || method.name.equals(targetMethodNameObf2)) && (method.desc.equals(targetMethodSign2) || method.desc.equals(targetMethodSignObf2))) {
				TypeInsnNode nodeIsInstance = null;
				for (boolean playerObf : new boolean[]{false, true}) {
					nodeIsInstance = findNodeInMethod(method, new TypeInsnNode(193, playerObf ? "yz" : "net/minecraft/entity/player/EntityPlayer"));
					if (nodeIsInstance != null) {
						break;
					}
				}
				VarInsnNode nodeLoadEntity = (VarInsnNode) nodeIsInstance.getPrevious();
				method.instructions.remove(nodeIsInstance);
				InsnList newIns = new InsnList();
				newIns.add(new VarInsnNode(25, 1));
				newIns.add(new MethodInsnNode(184, "lotr/common/coremod/LOTRReplacedMethods$Enchants", "isPlayerMeleeKill", "(Lnet/minecraft/entity/Entity;Lnet/minecraft/util/DamageSource;)Z", false));
				method.instructions.insert(nodeLoadEntity, newIns);
				System.out.println("LOTRCore: Patched method " + method.name);
			}
		}
		ClassWriter writer = new ClassWriter(1);
		classNode.accept(writer);
		return writer.toByteArray();
	}

	public byte[] patchEntityMinecart(String name, byte[] bytes) {
		String targetMethodName;
		String targetMethodNameObf = targetMethodName = "func_145821_a";
		String targetMethodSign = "(IIIDDLnet/minecraft/block/Block;I)V";
		String targetMethodSignObf = "(IIIDDLaji;I)V";
		ClassNode classNode = new ClassNode();
		ClassReader classReader = new ClassReader(bytes);
		classReader.accept(classNode, 0);
		for (MethodNode method : classNode.methods) {
			if (!method.name.equals(targetMethodName) || !method.desc.equals(targetMethodSign) && !method.desc.equals(targetMethodSignObf)) {
				continue;
			}
			VarInsnNode nodeLoadRailBlockForPoweredCheck = findNodeInMethod(method, new VarInsnNode(25, 8), 1);
			AbstractInsnNode nextNode = nodeLoadRailBlockForPoweredCheck.getNext();
			if (!(nextNode instanceof TypeInsnNode) || nextNode.getOpcode() != 192) {
				System.out.println("WARNING! Expected CHECKCAST! Instead got " + nextNode);
				System.out.println("WARNING! Things may break!");
			}
			InsnList newIns = new InsnList();
			newIns.add(new VarInsnNode(25, 0));
			newIns.add(new VarInsnNode(21, 1));
			newIns.add(new VarInsnNode(21, 2));
			newIns.add(new VarInsnNode(21, 3));
			newIns.add(new VarInsnNode(25, 8));
			newIns.add(new VarInsnNode(21, 11));
			newIns.add(new MethodInsnNode(184, "lotr/common/coremod/LOTRReplacedMethods$Minecart", "checkForPoweredRail", "(Lnet/minecraft/entity/item/EntityMinecart;IIILnet/minecraft/block/Block;Z)Z", false));
			newIns.add(new VarInsnNode(54, 11));
			newIns.add(new VarInsnNode(25, 0));
			newIns.add(new VarInsnNode(21, 1));
			newIns.add(new VarInsnNode(21, 2));
			newIns.add(new VarInsnNode(21, 3));
			newIns.add(new VarInsnNode(25, 8));
			newIns.add(new VarInsnNode(21, 12));
			newIns.add(new MethodInsnNode(184, "lotr/common/coremod/LOTRReplacedMethods$Minecart", "checkForDepoweredRail", "(Lnet/minecraft/entity/item/EntityMinecart;IIILnet/minecraft/block/Block;Z)Z", false));
			newIns.add(new VarInsnNode(54, 12));
			method.instructions.insertBefore(nodeLoadRailBlockForPoweredCheck, newIns);
			System.out.println("LOTRCore: Patched method " + method.name);
		}
		ClassWriter writer = new ClassWriter(1);
		classNode.accept(writer);
		return writer.toByteArray();
	}

	public byte[] patchEntityPlayer(String name, byte[] bytes) {
		String targetMethodName = "canEat";
		String targetMethodNameObf = "func_71043_e";
		String targetMethodSign = "(Z)Z";
		ClassNode classNode = new ClassNode();
		ClassReader classReader = new ClassReader(bytes);
		classReader.accept(classNode, 0);
		for (MethodNode method : classNode.methods) {
			if (!method.name.equals(targetMethodName) && !method.name.equals(targetMethodNameObf) || !method.desc.equals(targetMethodSign)) {
				continue;
			}
			method.instructions.clear();
			InsnList newIns = new InsnList();
			newIns.add(new VarInsnNode(25, 0));
			newIns.add(new VarInsnNode(21, 1));
			newIns.add(new MethodInsnNode(184, "lotr/common/coremod/LOTRReplacedMethods$Player", "canEat", "(Lnet/minecraft/entity/player/EntityPlayer;Z)Z", false));
			newIns.add(new InsnNode(172));
			method.instructions.insert(newIns);
			System.out.println("LOTRCore: Patched method " + method.name);
		}
		ClassWriter writer = new ClassWriter(1);
		classNode.accept(writer);
		return writer.toByteArray();
	}

	public byte[] patchFMLNetworkHandler(String name, byte[] bytes) {
		String targetMethodName;
		String targetMethodNameObf = targetMethodName = "getEntitySpawningPacket";
		String targetMethodSign = "(Lnet/minecraft/entity/Entity;)Lnet/minecraft/network/Packet;";
		String targetMethodSignObf = "(Lsa;)Lft;";
		ClassNode classNode = new ClassNode();
		ClassReader classReader = new ClassReader(bytes);
		classReader.accept(classNode, 0);
		for (MethodNode method : classNode.methods) {
			if (!method.name.equals(targetMethodName) || !method.desc.equals(targetMethodSign) && !method.desc.equals(targetMethodSignObf)) {
				continue;
			}
			method.instructions.clear();
			InsnList newIns = new InsnList();
			newIns.add(new VarInsnNode(25, 0));
			newIns.add(new MethodInsnNode(184, "lotr/common/coremod/LOTRReplacedMethods$EntityPackets", "getMobSpawnPacket", "(Lnet/minecraft/entity/Entity;)Lnet/minecraft/network/Packet;", false));
			newIns.add(new InsnNode(176));
			method.instructions.insert(newIns);
			System.out.println("LOTRCore: Patched method " + method.name);
		}
		ClassWriter writer = new ClassWriter(1);
		classNode.accept(writer);
		return writer.toByteArray();
	}

	public byte[] patchFoodStats(String name, byte[] bytes) {
		String targetMethodName = "addExhaustion";
		String targetMethodNameObf = "func_75113_a";
		String targetMethodSign = "(F)V";
		ClassNode classNode = new ClassNode();
		ClassReader classReader = new ClassReader(bytes);
		classReader.accept(classNode, 0);
		for (MethodNode method : classNode.methods) {
			if (!method.name.equals(targetMethodName) && !method.name.equals(targetMethodNameObf) || !method.desc.equals(targetMethodSign)) {
				continue;
			}
			InsnList newIns = new InsnList();
			newIns.add(new VarInsnNode(23, 1));
			newIns.add(new MethodInsnNode(184, "lotr/common/coremod/LOTRReplacedMethods$Food", "getExhaustionFactor", "()F", false));
			newIns.add(new InsnNode(106));
			newIns.add(new VarInsnNode(56, 1));
			VarInsnNode nodeAfter = findNodeInMethod(method, new VarInsnNode(25, 0));
			method.instructions.insertBefore(nodeAfter, newIns);
			System.out.println("LOTRCore: Patched method " + method.name);
		}
		ClassWriter writer = new ClassWriter(1);
		classNode.accept(writer);
		return writer.toByteArray();
	}

	public byte[] patchItemStack(String name, byte[] bytes) {
		boolean isCauldron = LOTRModChecker.isCauldronServer();
		String targetMethodName = "attemptDamageItem";
		String targetMethodNameObf = "func_96631_a";
		String targetMethodSign = "(ILjava/util/Random;)Z";
		String targetMethodSignObf = targetMethodSign;
		if (isCauldron) {
			targetMethodName = targetMethodNameObf = "isDamaged";
			targetMethodSign = "(ILjava/util/Random;Lnet/minecraft/entity/EntityLivingBase;)Z";
			targetMethodSignObf = "(ILjava/util/Random;Lsv;)Z";
		}
		ClassNode classNode = new ClassNode();
		ClassReader classReader = new ClassReader(bytes);
		classReader.accept(classNode, 0);
		for (MethodNode method : classNode.methods) {
			if ((method.name.equals(targetMethodName) || method.name.equals(targetMethodNameObf)) && (method.desc.equals(targetMethodSign) || method.desc.equals(targetMethodSignObf))) {
				if (!isCauldron) {
					method.instructions.clear();
					InsnList newIns = new InsnList();
					newIns.add(new VarInsnNode(25, 0));
					newIns.add(new VarInsnNode(21, 1));
					newIns.add(new VarInsnNode(25, 2));
					newIns.add(new MethodInsnNode(184, "lotr/common/coremod/LOTRReplacedMethods$Enchants", "attemptDamageItem", "(Lnet/minecraft/item/ItemStack;ILjava/util/Random;)Z", false));
					newIns.add(new InsnNode(172));
					method.instructions.insert(newIns);
					System.out.println("LOTRCore: Patched method " + method.name);
					continue;
				}
				for (AbstractInsnNode n : method.instructions.toArray()) {
					if (n.getOpcode() == 100) {
						InsnList insns = new InsnList();
						insns.add(new VarInsnNode(25, 0));
						insns.add(new VarInsnNode(21, 1));
						insns.add(new VarInsnNode(25, 2));
						insns.add(new VarInsnNode(25, 3));
						insns.add(new MethodInsnNode(184, "lotr/common/coremod/LOTRReplacedMethods$Enchants", "c_attemptDamageItem", "(ILnet/minecraft/item/ItemStack;ILjava/util/Random;Lnet/minecraft/entity/EntityLivingBase;)I", false));
						method.instructions.insert(n, insns);
						System.out.println("LOTRCore: Patched method " + method.name + " for Cauldron");
					}
				}
			}
		}
		ClassWriter writer = new ClassWriter(1);
		classNode.accept(writer);
		return writer.toByteArray();
	}

	public byte[] patchNetHandlerClient(String name, byte[] bytes) {
		String targetMethodName = "handleEntityTeleport";
		String targetMethodNameObf = "func_147275_a";
		String targetMethodSign = "(Lnet/minecraft/network/play/server/S18PacketEntityTeleport;)V";
		String targetMethodSignObf = "(Lik;)V";
		String targetMethodName2 = "handleEntityMovement";
		String targetMethodNameObf2 = "func_147259_a";
		String targetMethodSign2 = "(Lnet/minecraft/network/play/server/S14PacketEntity;)V";
		String targetMethodSignObf2 = "(Lhf;)V";
		ClassNode classNode = new ClassNode();
		ClassReader classReader = new ClassReader(bytes);
		classReader.accept(classNode, 0);
		for (MethodNode method : classNode.methods) {
			InsnList newIns;
			if ((method.name.equals(targetMethodName) || method.name.equals(targetMethodNameObf)) && (method.desc.equals(targetMethodSign) || method.desc.equals(targetMethodSignObf))) {
				method.instructions.clear();
				newIns = new InsnList();
				newIns.add(new VarInsnNode(25, 0));
				newIns.add(new VarInsnNode(25, 1));
				newIns.add(new MethodInsnNode(184, "lotr/common/coremod/LOTRReplacedMethods$NetHandlerClient", "handleEntityTeleport", "(Lnet/minecraft/client/network/NetHandlerPlayClient;Lnet/minecraft/network/play/server/S18PacketEntityTeleport;)V", false));
				newIns.add(new InsnNode(177));
				method.instructions.insert(newIns);
				System.out.println("LOTRCore: Patched method " + method.name);
			}
			if (!method.name.equals(targetMethodName2) && !method.name.equals(targetMethodNameObf2) || !method.desc.equals(targetMethodSign2) && !method.desc.equals(targetMethodSignObf2)) {
				continue;
			}
			method.instructions.clear();
			newIns = new InsnList();
			newIns.add(new VarInsnNode(25, 0));
			newIns.add(new VarInsnNode(25, 1));
			newIns.add(new MethodInsnNode(184, "lotr/common/coremod/LOTRReplacedMethods$NetHandlerClient", "handleEntityMovement", "(Lnet/minecraft/client/network/NetHandlerPlayClient;Lnet/minecraft/network/play/server/S14PacketEntity;)V", false));
			newIns.add(new InsnNode(177));
			method.instructions.insert(newIns);
			System.out.println("LOTRCore: Patched method " + method.name);
		}
		ClassWriter writer = new ClassWriter(1);
		classNode.accept(writer);
		return writer.toByteArray();
	}

	public byte[] patchPathFinder(String name, byte[] bytes) {
		String targetMethodName = "func_82565_a";
		String targetMethodSign = "(Lnet/minecraft/entity/Entity;IIILnet/minecraft/pathfinding/PathPoint;ZZZ)I";
		String targetMethodSignObf = "(Lsa;IIILaye;ZZZ)I";
		ClassNode classNode = new ClassNode();
		ClassReader classReader = new ClassReader(bytes);
		classReader.accept(classNode, 0);
		for (MethodNode method : classNode.methods) {
			if ((method.name.equals(targetMethodName)) && (method.desc.equals(targetMethodSign) || method.desc.equals(targetMethodSignObf))) {
				FieldInsnNode nodeFound1 = null;
				FieldInsnNode nodeFound2 = null;
				for (int pass = 0; pass <= 1; pass++) {
					label97:
					for (boolean blocksObf : new boolean[]{false, true}) {
						for (boolean doorObf : new boolean[]{false, true}) {
							String _blocks = blocksObf ? "ajn" : "net/minecraft/init/Blocks";
							String _door = doorObf ? "field_150466_ao" : "wooden_door";
							FieldInsnNode nodeGetDoor = new FieldInsnNode(178, _blocks, _door, "Lnet/minecraft/block/Block;");
							if (pass == 0) {
								nodeFound1 = findNodeInMethod(method, nodeGetDoor, 0);
								if (nodeFound1 != null) {
									break label97;
								}
							} else {
								nodeFound2 = findNodeInMethod(method, nodeGetDoor, 1);
								if (nodeFound2 != null) {
									break label97;
								}
							}
						}
					}
				}
				MethodInsnNode nodeCheckDoor1 = new MethodInsnNode(184, "lotr/common/coremod/LOTRReplacedMethods$PathFinder", "isWoodenDoor", "(Lnet/minecraft/block/Block;)Z", false);
				method.instructions.set(nodeFound1, nodeCheckDoor1);
				JumpInsnNode nodeIf1 = (JumpInsnNode) nodeCheckDoor1.getNext();
				nodeIf1.setOpcode(153);
				MethodInsnNode nodeCheckDoor2 = new MethodInsnNode(184, "lotr/common/coremod/LOTRReplacedMethods$PathFinder", "isWoodenDoor", "(Lnet/minecraft/block/Block;)Z", false);
				method.instructions.set(nodeFound2, nodeCheckDoor2);
				JumpInsnNode nodeIf2 = (JumpInsnNode) nodeCheckDoor2.getNext();
				if (nodeIf2.getOpcode() == 165) {
					nodeIf2.setOpcode(154);
				} else {
					System.out.println("WARNING! WARNING! THIS OPCODE SHOULD HAVE BEEN IF_ACMPEQ!");
					System.out.println("WARNING! INSTEAD IT WAS " + nodeIf2.getOpcode());
					if (nodeIf2.getOpcode() == 166) {
						System.out.println("WARNING! Opcode is IF_ACMPNE instead of expected IF_ACMPEQ, so setting it to IFEQ instead of IFNE");
						System.out.println("WARNING! Hopefully this works...");
						nodeIf2.setOpcode(153);
					} else {
						System.out.println("WARNING! NOT SURE WHAT TO DO HERE! THINGS MIGHT BREAK!");
					}
				}
				FieldInsnNode nodeFoundGate = null;
				label95:
				for (boolean blocksObf : new boolean[]{false, true}) {
					for (boolean gateObf : new boolean[]{false, true}) {
						String _blocks = blocksObf ? "ajn" : "net/minecraft/init/Blocks";
						String _gate = gateObf ? "field_150396_be" : "fence_gate";
						FieldInsnNode nodeGetGate = new FieldInsnNode(178, _blocks, _gate, "Lnet/minecraft/block/Block;");
						nodeFoundGate = findNodeInMethod(method, nodeGetGate, 0);
						if (nodeFoundGate != null) {
							break label95;
						}
					}
				}
				MethodInsnNode nodeCheckGate = new MethodInsnNode(184, "lotr/common/coremod/LOTRReplacedMethods$PathFinder", "isFenceGate", "(Lnet/minecraft/block/Block;)Z", false);
				method.instructions.set(nodeFoundGate, nodeCheckGate);
				JumpInsnNode nodeIfGate = (JumpInsnNode) nodeCheckGate.getNext();
				if (nodeIfGate.getOpcode() == 165) {
					nodeIfGate.setOpcode(154);
				} else {
					System.out.println("WARNING! WARNING! THIS OPCODE SHOULD HAVE BEEN IF_ACMPEQ!");
					System.out.println("WARNING! INSTEAD IT WAS " + nodeIfGate.getOpcode());
					System.out.println("WARNING! NOT SURE WHAT TO DO HERE! THINGS MIGHT BREAK!");
				}
				System.out.println("LOTRCore: Patched method " + method.name);
			}
		}
		ClassWriter writer = new ClassWriter(1);
		classNode.accept(writer);
		return writer.toByteArray();
	}

	public byte[] patchPotionDamage(String name, byte[] bytes) {
		String targetMethodName;
		String targetMethodNameObf = targetMethodName = "func_111183_a";
		String targetMethodSign = "(ILnet/minecraft/entity/ai/attributes/AttributeModifier;)D";
		String targetMethodSignObf = "(ILtj;)D";
		ClassNode classNode = new ClassNode();
		ClassReader classReader = new ClassReader(bytes);
		classReader.accept(classNode, 0);
		for (MethodNode method : classNode.methods) {
			if (!method.name.equals(targetMethodName) || !method.desc.equals(targetMethodSign) && !method.desc.equals(targetMethodSignObf)) {
				continue;
			}
			method.instructions.clear();
			InsnList newIns = new InsnList();
			newIns.add(new VarInsnNode(25, 0));
			newIns.add(new VarInsnNode(21, 1));
			newIns.add(new VarInsnNode(25, 2));
			newIns.add(new MethodInsnNode(184, "lotr/common/coremod/LOTRReplacedMethods$Potions", "getStrengthModifier", "(Lnet/minecraft/potion/Potion;ILnet/minecraft/entity/ai/attributes/AttributeModifier;)D", false));
			newIns.add(new InsnNode(175));
			method.instructions.insert(newIns);
			System.out.println("LOTRCore: Patched method " + method.name);
		}
		ClassWriter writer = new ClassWriter(1);
		classNode.accept(writer);
		return writer.toByteArray();
	}

	public byte[] patchRenderBlocks(String name, byte[] bytes) {
		try {
			String s = getClass().getPackage().getName() + ".RandomTexturePatchCheck";
			Class<?> enableClass = Class.forName(s);
		} catch (ClassNotFoundException e) {
			return bytes;
		}
		String targetMethodName = "renderBlockByRenderType";
		String targetMethodNameObf = "func_147805_b";
		String targetMethodSign = "(Lnet/minecraft/block/Block;III)Z";
		String targetMethodSignObf = "(Laji;III)Z";
		ClassNode classNode = new ClassNode();
		ClassReader classReader = new ClassReader(bytes);
		classReader.accept(classNode, 0);
		for (MethodNode method : classNode.methods) {
			if (!method.name.equals(targetMethodName) && !method.name.equals(targetMethodNameObf) || !method.desc.equals(targetMethodSign) && !method.desc.equals(targetMethodSignObf)) {
				continue;
			}
			MethodInsnNode nodeFound = null;
			block3:
			for (boolean rbObf : new boolean[]{false, true}) {
				for (boolean renderObf : new boolean[]{false, true}) {
					for (boolean blockObf : new boolean[]{false, true}) {
						String _rb = rbObf ? cls_RenderBlocks_obf : cls_RenderBlocks;
						String _render = renderObf ? "func_147784_q" : "renderStandardBlock";
						String _block = blockObf ? cls_Block_obf : cls_Block;
						MethodInsnNode nodeRender = new MethodInsnNode(182, _rb, _render, "(L" + _block + ";III)Z", false);
						nodeFound = findNodeInMethod(method, nodeRender);
						if (nodeFound != null) {
							break block3;
						}
					}
				}
			}
			MethodInsnNode nodeRSB = new MethodInsnNode(184, "lotr/common/coremod/LOTRReplacedMethods$BlockRendering", "renderStandardBlock", "(Lnet/minecraft/client/renderer/RenderBlocks;Lnet/minecraft/block/Block;III)Z", false);
			method.instructions.set(nodeFound, nodeRSB);
			System.out.println("LOTRCore: Patched method " + method.name);
		}
		ClassWriter writer = new ClassWriter(1);
		classNode.accept(writer);
		return writer.toByteArray();
	}

	public byte[] patchSpawnerAnimals(String name, byte[] bytes) {
		String targetMethodName = "findChunksForSpawning";
		String targetMethodNameObf = "func_77192_a";
		String targetMethodSign = "(Lnet/minecraft/world/WorldServer;ZZZ)I";
		String targetMethodSignObf = "(Lmt;ZZZ)I";
		ClassNode classNode = new ClassNode();
		ClassReader classReader = new ClassReader(bytes);
		classReader.accept(classNode, 0);
		for (MethodNode method : classNode.methods) {
			if (!method.name.equals(targetMethodName) && !method.name.equals(targetMethodNameObf) || !method.desc.equals(targetMethodSign) && !method.desc.equals(targetMethodSignObf)) {
				continue;
			}
			method.instructions.clear();
			method.tryCatchBlocks.clear();
			method.localVariables.clear();
			InsnList newIns = new InsnList();
			newIns.add(new VarInsnNode(25, 1));
			newIns.add(new VarInsnNode(21, 2));
			newIns.add(new VarInsnNode(21, 3));
			newIns.add(new VarInsnNode(21, 4));
			newIns.add(new MethodInsnNode(184, "lotr/common/coremod/LOTRReplacedMethods$Spawner", "performSpawning_optimised", "(Lnet/minecraft/world/WorldServer;ZZZ)I", false));
			newIns.add(new InsnNode(172));
			method.instructions.insert(newIns);
			System.out.println("LOTRCore: Patched method " + method.name);
		}
		ClassWriter writer = new ClassWriter(1);
		classNode.accept(writer);
		return writer.toByteArray();
	}

	@Override
	public byte[] transform(String name, String transformedName, byte[] basicClass) {
		if ("anv".equals(name) || "net.minecraft.block.BlockStone".equals(name)) {
			return patchBlockStone(name, basicClass);
		}
		if ("alh".equals(name) || "net.minecraft.block.BlockGrass".equals(name)) {
			return patchBlockGrass(name, basicClass);
		}
		if ("akl".equals(name) || "net.minecraft.block.BlockDirt".equals(name)) {
			return patchBlockDirt(name, basicClass);
		}
		if ("ant".equals(name) || "net.minecraft.block.BlockStaticLiquid".equals(name)) {
			return patchBlockStaticLiquid(name, basicClass);
		}
		if ("alb".equals(name) || "net.minecraft.block.BlockFire".equals(name)) {
			return patchBlockFire(name, basicClass);
		}
		if ("akz".equals(name) || "net.minecraft.block.BlockFence".equals(name)) {
			return patchBlockFence(name, basicClass);
		}
		// empty if block
		if ("aoe".equals(name) || "net.minecraft.block.BlockTrapDoor".equals(name)) {
			return patchBlockTrapdoor(name, basicClass);
		}
		if ("aoi".equals(name) || "net.minecraft.block.BlockWall".equals(name)) {
			return patchBlockWall(name, basicClass);
		}
		if (cls_BlockPistonBase_obf.equals(name) || "net.minecraft.block.BlockPistonBase".equals(name)) {
			return patchBlockPistonBase(name, basicClass);
		}
		if ("ajw".equals(name) || "net.minecraft.block.BlockCauldron".equals(name)) {
			return patchBlockCauldron(name, basicClass);
		}
		if ("ajb".equals(name) || "net.minecraft.block.BlockAnvil".equals(name)) {
			return patchBlockAnvil(name, basicClass);
		}
		if (cls_EntityPlayer_obf.equals(name) || "net.minecraft.entity.player.EntityPlayer".equals(name)) {
			return patchEntityPlayer(name, basicClass);
		}
		if (cls_EntityLivingBase_obf.equals(name) || "net.minecraft.entity.EntityLivingBase".equals(name)) {
			return patchEntityLivingBase(name, basicClass);
		}
		if ("wi".equals(name) || "net.minecraft.entity.passive.EntityHorse".equals(name)) {
			return patchEntityHorse(name, basicClass);
		}
		if ("xh".equals(name) || "net.minecraft.entity.effect.EntityLightningBolt".equals(name)) {
			return patchEntityLightningBolt(name, basicClass);
		}
		if ("xl".equals(name) || "net.minecraft.entity.item.EntityMinecart".equals(name)) {
			return patchEntityMinecart(name, basicClass);
		}
		if ("net.minecraftforge.common.ISpecialArmor$ArmorProperties".equals(name)) {
			return patchArmorProperties(name, basicClass);
		}
		if ("zr".equals(name) || "net.minecraft.util.FoodStats".equals(name)) {
			return patchFoodStats(name, basicClass);
		}
		if ("aho".equals(name) || "net.minecraft.world.SpawnerAnimals".equals(name)) {
			return patchSpawnerAnimals(name, basicClass);
		}
		if ("ayg".equals(name) || "net.minecraft.pathfinding.PathFinder".equals(name)) {
			return patchPathFinder(name, basicClass);
		}
		if ("uc".equals(name) || "net.minecraft.entity.ai.EntityAIDoorInteract".equals(name)) {
			return patchDoorInteract(name, basicClass);
		}
		if ("afv".equals(name) || "net.minecraft.enchantment.EnchantmentHelper".equals(name)) {
			return patchEnchantmentHelper(name, basicClass);
		}
		if (cls_ItemStack_obf.equals(name) || "net.minecraft.item.ItemStack".equals(name)) {
			return patchItemStack(name, basicClass);
		}
		if ("agi".equals(name) || "net.minecraft.enchantment.EnchantmentProtection".equals(name)) {
			return patchEnchantmentProtection(name, basicClass);
		}
		if ("rs".equals(name) || "net.minecraft.potion.PotionAttackDamage".equals(name)) {
			return patchPotionDamage(name, basicClass);
		}
		if (cls_RenderBlocks_obf.equals(name) || "net.minecraft.client.renderer.RenderBlocks".equals(name)) {
			return patchRenderBlocks(name, basicClass);
		}
		if ("bjk".equals(name) || "net.minecraft.client.entity.EntityClientPlayerMP".equals(name)) {
			return patchEntityClientPlayerMP(name, basicClass);
		}
		if ("bjb".equals(name) || "net.minecraft.client.network.NetHandlerPlayClient".equals(name)) {
			return patchNetHandlerClient(name, basicClass);
		}
		if ("cpw.mods.fml.common.network.internal.FMLNetworkHandler".equals(name)) {
			return patchFMLNetworkHandler(name, basicClass);
		}
		return basicClass;
	}

	public static NBTTagCompound doDebug(DataInputStream stream, int i, int k) {
		try {
			return CompressedStreamTools.read(stream);
		} catch (Exception e) {
			System.out.println("Error loading chunk: " + i + ", " + k);
			e.printStackTrace();
			return new NBTTagCompound();
		}
	}

	public static <N extends AbstractInsnNode> N findNodeInMethod(MethodNode method, N target) {
		return findNodeInMethod(method, target, 0);
	}

	public static <N extends AbstractInsnNode> N findNodeInMethod(MethodNode method, N targetAbstract, int skip) {
		int skipped = 0;
		Iterator<AbstractInsnNode> it = method.instructions.iterator();
		while (it.hasNext()) {
			AbstractInsnNode nextAbstract = it.next();
			boolean matched = false;
			if (nextAbstract.getClass() == targetAbstract.getClass()) {
				if (targetAbstract.getClass() == InsnNode.class) {
					InsnNode next = (InsnNode) nextAbstract;
					InsnNode target = (InsnNode) targetAbstract;
					if (next.getOpcode() == target.getOpcode()) {
						matched = true;
					}
				} else if (targetAbstract.getClass() == VarInsnNode.class) {
					VarInsnNode next = (VarInsnNode) nextAbstract;
					VarInsnNode target = (VarInsnNode) targetAbstract;
					if (next.getOpcode() == target.getOpcode() && next.var == target.var) {
						matched = true;
					}
				} else if (targetAbstract.getClass() == LdcInsnNode.class) {
					LdcInsnNode next = (LdcInsnNode) nextAbstract;
					LdcInsnNode target = (LdcInsnNode) targetAbstract;
					if (next.cst.equals(target.cst)) {
						matched = true;
					}
				} else if (targetAbstract.getClass() == TypeInsnNode.class) {
					TypeInsnNode next = (TypeInsnNode) nextAbstract;
					TypeInsnNode target = (TypeInsnNode) targetAbstract;
					if (next.getOpcode() == target.getOpcode() && next.desc.equals(target.desc)) {
						matched = true;
					}
				} else if (targetAbstract.getClass() == FieldInsnNode.class) {
					FieldInsnNode next = (FieldInsnNode) nextAbstract;
					FieldInsnNode target = (FieldInsnNode) targetAbstract;
					if (next.getOpcode() == target.getOpcode() && next.owner.equals(target.owner) && next.name.equals(target.name) && next.desc.equals(target.desc)) {
						matched = true;
					}
				} else if (targetAbstract.getClass() == MethodInsnNode.class) {
					MethodInsnNode next = (MethodInsnNode) nextAbstract;
					MethodInsnNode target = (MethodInsnNode) targetAbstract;
					if (next.getOpcode() == target.getOpcode() && next.owner.equals(target.owner) && next.name.equals(target.name) && next.desc.equals(target.desc) && next.itf == target.itf) {
						matched = true;
					}
				}
			}
			if (matched) {
				if (skipped >= skip) {
					return (N) nextAbstract;
				}
				skipped++;
			}
		}
		return null;
	}
}
