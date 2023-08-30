package lotr.client.render.entity;

import java.util.*;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import lotr.client.LOTRAttackTiming;
import lotr.common.item.LOTRWeaponStats;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.client.event.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent;

public class LOTRSwingHandler {
	public static Map<EntityLivingBase, SwingTime> entitySwings = new HashMap<>();
	public static float swingFactor = 0.8f;

	public LOTRSwingHandler() {
		FMLCommonHandler.instance().bus().register(this);
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	public void onClientTick(TickEvent.ClientTickEvent event) {
		if (event.phase == TickEvent.Phase.START) {
			Minecraft mc = Minecraft.getMinecraft();
			if (mc.theWorld == null) {
				entitySwings.clear();
			} else if (!mc.isGamePaused()) {
				Collection<EntityLivingBase> removes = new ArrayList<>();
				for (Map.Entry<EntityLivingBase, SwingTime> e : entitySwings.entrySet()) {
					EntityLivingBase entity = e.getKey();
					SwingTime swt = e.getValue();
					swt.swingPrev = swt.swing++;
					if (swt.swing <= swt.swingMax) {
						continue;
					}
					removes.add(entity);
				}
				for (EntityLivingBase entity : removes) {
					entitySwings.remove(entity);
				}
			}
		}
	}

	@SubscribeEvent
	public void onEntityUpdate(LivingEvent.LivingUpdateEvent event) {
		ItemStack item;
		SwingTime swt;
		EntityLivingBase entity = event.entityLiving;
		World world = entity.worldObj;
		if (world.isRemote && entitySwings.get(entity) == null && entity.isSwingInProgress && entity.swingProgressInt == 0 && LOTRWeaponStats.isMeleeWeapon(item = entity.getHeldItem())) {
			int time;
			time = LOTRWeaponStats.getAttackTimePlayer(item);
			time = Math.round(time * swingFactor);
			swt = new SwingTime();
			swt.swing = 1;
			swt.swingPrev = 0;
			swt.swingMax = time;
			entitySwings.put(entity, swt);
		}
	}

	@SubscribeEvent
	public void onRenderTick(TickEvent.RenderTickEvent event) {
		EntityClientPlayerMP entityplayer;
		if (event.phase == TickEvent.Phase.START && (entityplayer = Minecraft.getMinecraft().thePlayer) != null) {
			tryUpdateSwing(entityplayer);
		}
	}

	@SubscribeEvent
	public void preRenderLiving(RenderLivingEvent.Pre event) {
		tryUpdateSwing(event.entity);
	}

	@SubscribeEvent
	public void preRenderPlayer(RenderPlayerEvent.Pre event) {
		tryUpdateSwing(event.entityPlayer);
	}

	public void tryUpdateSwing(EntityLivingBase entity) {
		if (entity == Minecraft.getMinecraft().thePlayer) {
			if (LOTRAttackTiming.fullAttackTime > 0) {
				float max = LOTRAttackTiming.fullAttackTime;
				float swing = (max - LOTRAttackTiming.attackTime) / max;
				float pre = (max - LOTRAttackTiming.prevAttackTime) / max;
				swing /= swingFactor;
				pre /= swingFactor;
				if (swing <= 1.0f) {
					entity.swingProgress = swing;
					entity.prevSwingProgress = pre;
				}
			}
		} else {
			SwingTime swt = entitySwings.get(entity);
			if (swt != null) {
				entity.swingProgress = (float) swt.swing / swt.swingMax;
				entity.prevSwingProgress = (float) swt.swingPrev / swt.swingMax;
			}
		}
	}

	public static class SwingTime {
		public int swingPrev;
		public int swing;
		public int swingMax;

	}

}
