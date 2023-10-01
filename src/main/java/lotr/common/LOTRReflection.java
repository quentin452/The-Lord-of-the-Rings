package lotr.common;

import cpw.mods.fml.common.ObfuscationReflectionHelper;
import cpw.mods.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper;
import cpw.mods.fml.relauncher.ReflectionHelper;
import lotr.common.util.LOTRLog;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.BlockStem;
import net.minecraft.command.CommandHandler;
import net.minecraft.command.ICommand;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.event.HoverEvent;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.AnimalChest;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldInfo;
import org.apache.logging.log4j.Level;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

public class LOTRReflection {
	public static boolean canPistonPushBlock(Block block, World world, int i, int j, int k, boolean flag) {
		try {
			Method method = getPrivateMethod(BlockPistonBase.class, null, new Class[]{Block.class, World.class, Integer.TYPE, Integer.TYPE, Integer.TYPE, Boolean.TYPE}, "canPushBlock", "func_150080_a");
			return (Boolean) method.invoke(null, block, world, i, j, k, flag);
		} catch (Exception e) {
			logFailure(e);
			return false;
		}
	}

	public static Item getCropItem(BlockCrops block) {
		try {
			Method method = getPrivateMethod(BlockCrops.class, block, new Class[0], "func_149865_P");
			return (Item) method.invoke(block);
		} catch (Exception e) {
			logFailure(e);
			return null;
		}
	}

	public static int getFishHookBobTime(EntityFishHook fishHook) {
		try {
			return ObfuscationReflectionHelper.getPrivateValue(EntityFishHook.class, fishHook, "field_146045_ax");
		} catch (Exception e) {
			logFailure(e);
			return 0;
		}
	}

	public static String[] getHorseArmorTextures() {
		try {
			return ObfuscationReflectionHelper.getPrivateValue(EntityHorse.class, null, "horseArmorTextures", "field_110270_bw");
		} catch (Exception e) {
			logFailure(e);
			return null;
		}
	}

	public static AnimalChest getHorseInv(EntityHorse horse) {
		try {
			return ObfuscationReflectionHelper.getPrivateValue(EntityHorse.class, horse, "horseChest", "field_110296_bG");
		} catch (Exception e) {
			logFailure(e);
			return null;
		}
	}

	public static IAttribute getHorseJumpStrength() {
		try {
			return ObfuscationReflectionHelper.getPrivateValue(EntityHorse.class, null, "horseJumpStrength", "field_110271_bv");
		} catch (Exception e) {
			logFailure(e);
			return null;
		}
	}

	public static Map getHoverEventMappings() {
		try {
			return ObfuscationReflectionHelper.getPrivateValue(HoverEvent.Action.class, null, "nameMapping", "field_150690_d");
		} catch (Exception e) {
			logFailure(e);
			return null;
		}
	}

	public static <E> Method getPrivateMethod(Class<? super E> classToAccess, E instance, Class[] methodClasses, String... methodNames) {
		try {
			return ReflectionHelper.findMethod(classToAccess, instance, remapMethodNames(classToAccess.getName(), methodNames), methodClasses);
		} catch (ReflectionHelper.UnableToFindFieldException e) {
			LOTRLog.logger.log(Level.ERROR, "Unable to locate any method %s on type %s", Arrays.toString(methodNames), classToAccess.getName());
			throw e;
		} catch (ReflectionHelper.UnableToAccessFieldException e) {
			LOTRLog.logger.log(Level.ERROR, "Unable to access any method %s on type %s", Arrays.toString(methodNames), classToAccess.getName());
			throw e;
		}
	}

	public static Block getStemFruitBlock(BlockStem block) {
		try {
			return ObfuscationReflectionHelper.getPrivateValue(BlockStem.class, block, "field_149877_a");
		} catch (Exception e) {
			logFailure(e);
			return null;
		}
	}

	public static boolean isBadEffect(Potion potion) {
		try {
			return ObfuscationReflectionHelper.getPrivateValue(Potion.class, potion, "isBadEffect", "field_76418_K");
		} catch (Exception e) {
			logFailure(e);
			return false;
		}
	}

	public static boolean isFishHookInGround(EntityFishHook fishHook) {
		try {
			return ObfuscationReflectionHelper.getPrivateValue(EntityFishHook.class, fishHook, "field_146051_au");
		} catch (Exception e) {
			logFailure(e);
			return false;
		}
	}

	public static void logFailure(Exception e) {
		LOTRLog.logger.log(Level.ERROR, "LOTRReflection failed");
		throw new RuntimeException(e);
	}

	public static String[] remapMethodNames(String className, String... methodNames) {
		String internalClassName = FMLDeobfuscatingRemapper.INSTANCE.unmap(className.replace('.', '/'));
		String[] mappedNames = new String[methodNames.length];
		int i = 0;
		for (String mName : methodNames) {
			mappedNames[i] = FMLDeobfuscatingRemapper.INSTANCE.mapMethodName(internalClassName, mName, null);
			i++;
		}
		return mappedNames;
	}

	public static void removeCommand(Class commandClass) {
		try {
			CommandHandler handler = (CommandHandler) MinecraftServer.getServer().getCommandManager();
			Map commandMap = handler.getCommands();
			Collection commandSet = ObfuscationReflectionHelper.getPrivateValue(CommandHandler.class, handler, "commandSet", "field_71561_b");
			Collection<ICommand> mapremoves = new ArrayList<>();
			for (Object obj : commandMap.values()) {
				ICommand command = (ICommand) obj;
				if (command.getClass() != commandClass) {
					continue;
				}
				mapremoves.add(command);
			}
			commandMap.values().removeAll(mapremoves);
			Collection setremoves = new ArrayList();
			for (Object obj : commandSet) {
				if (obj.getClass() != commandClass) {
					continue;
				}
				setremoves.add(obj);
			}
			commandSet.removeAll(setremoves);
		} catch (Exception e) {
			logFailure(e);
		}
	}

	public static <T, E> void setFinalField(Class<? super T> classToAccess, T instance, E value, Field f) throws Exception {
		try {
			unlockFinalField(f);
			f.set(instance, value);
		} catch (Exception e) {
			LOTRLog.logger.log(Level.ERROR, "Unable to access static field");
			throw e;
		}
	}

	public static <T, E> void setFinalField(Class<? super T> classToAccess, T instance, E value, String... fieldNames) throws Exception {
		try {
			fieldNames = ObfuscationReflectionHelper.remapFieldNames(classToAccess.getName(), fieldNames);
			Field f = ReflectionHelper.findField(classToAccess, fieldNames);
			setFinalField(classToAccess, instance, value, f);
		} catch (Exception e) {
			LOTRLog.logger.log(Level.ERROR, "Unable to access static field");
			throw e;
		}
	}

	public static void setupHorseInv(EntityHorse horse) {
		try {
			Method method = getPrivateMethod(EntityHorse.class, horse, new Class[0], "func_110226_cD");
			method.invoke(horse);
		} catch (Exception e) {
			logFailure(e);
		}
	}

	public static void setWorldInfo(World world, WorldInfo newWorldInfo) {
		try {
			ObfuscationReflectionHelper.setPrivateValue(World.class, world, newWorldInfo, "worldInfo", "field_72986_A");
		} catch (Exception e) {
			logFailure(e);
		}
	}

	public static void testAll(World world) {
		getHorseJumpStrength();
		getHorseArmorTextures();
		getHorseInv(new EntityHorse(world));
		setupHorseInv(new EntityHorse(world));
		getStemFruitBlock((BlockStem) Blocks.melon_stem);
		getCropItem((BlockCrops) Blocks.potatoes);
		isBadEffect(Potion.poison);
		getHoverEventMappings();
		isFishHookInGround(new EntityFishHook(world));
		getFishHookBobTime(new EntityFishHook(world));
		canPistonPushBlock(Blocks.glass, world, 0, 0, 0, false);
	}

	public static void unlockFinalField(Field f) throws Exception {
		f.setAccessible(true);
		Field modifiersField = Field.class.getDeclaredField("modifiers");
		modifiersField.setAccessible(true);
		modifiersField.setInt(f, f.getModifiers() & 0xFFFFFFEF);
	}
}
