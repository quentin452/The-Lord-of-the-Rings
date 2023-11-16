package lotr.client;

import com.google.common.math.IntMath;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import lotr.common.*;
import lotr.common.fac.LOTRFaction;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class LOTRKeyHandler {
	public static KeyBinding keyBindingMenu = new KeyBinding("Menu", 38, "LOTR");
	public static KeyBinding keyBindingMapTeleport = new KeyBinding("Map Teleport", 50, "LOTR");
	public static KeyBinding keyBindingFastTravel = new KeyBinding("Fast Travel", 33, "LOTR");
	public static KeyBinding keyBindingAlignmentCycleLeft = new KeyBinding("Alignment Cycle Left", 203, "LOTR");
	public static KeyBinding keyBindingAlignmentCycleRight = new KeyBinding("Alignment Cycle Right", 205, "LOTR");
	public static KeyBinding keyBindingAlignmentGroupPrev = new KeyBinding("Alignment Group Prev", 200, "LOTR");
	public static KeyBinding keyBindingAlignmentGroupNext = new KeyBinding("Alignment Group Next", 208, "LOTR");
	public static Minecraft mc = Minecraft.getMinecraft();
	public static int alignmentChangeTick;

	public LOTRKeyHandler() {
		FMLCommonHandler.instance().bus().register(this);
		ClientRegistry.registerKeyBinding(keyBindingMenu);
		ClientRegistry.registerKeyBinding(keyBindingMapTeleport);
		ClientRegistry.registerKeyBinding(keyBindingFastTravel);
		ClientRegistry.registerKeyBinding(keyBindingAlignmentCycleLeft);
		ClientRegistry.registerKeyBinding(keyBindingAlignmentCycleRight);
		ClientRegistry.registerKeyBinding(keyBindingAlignmentGroupPrev);
		ClientRegistry.registerKeyBinding(keyBindingAlignmentGroupNext);
	}

	public static void update() {
		if (alignmentChangeTick > 0) {
			--alignmentChangeTick;
		}
	}

	@SubscribeEvent
	public void KeyInputEvent(InputEvent.KeyInputEvent event) {
        if (LOTRConfig.enableAttackCooldown){
		LOTRAttackTiming.doAttackTiming();
        }
		if (keyBindingMenu.getIsKeyPressed() && mc.currentScreen == null) {
			mc.thePlayer.openGui(LOTRMod.instance, 11, mc.theWorld, 0, 0, 0);
		}
		LOTRPlayerData pd = LOTRLevelData.getData(mc.thePlayer);
		boolean usedAlignmentKeys = false;
		Map<LOTRDimension.DimensionRegion, LOTRFaction> lastViewedRegions = new EnumMap<>(LOTRDimension.DimensionRegion.class);
		LOTRDimension currentDimension = LOTRDimension.getCurrentDimensionWithFallback(mc.theWorld);
		LOTRFaction currentFaction = pd.getViewingFaction();
		LOTRDimension.DimensionRegion currentRegion = currentFaction.factionRegion;
		List<LOTRDimension.DimensionRegion> regionList = currentDimension.dimensionRegions;
		List<LOTRFaction> factionList = currentRegion.factionList;
		if (mc.currentScreen == null && alignmentChangeTick <= 0) {
			int i;
			if (keyBindingAlignmentCycleLeft.getIsKeyPressed()) {
				i = factionList.indexOf(currentFaction);
				--i;
				i = IntMath.mod(i, factionList.size());
				currentFaction = factionList.get(i);
				usedAlignmentKeys = true;
			}
			if (keyBindingAlignmentCycleRight.getIsKeyPressed()) {
				i = factionList.indexOf(currentFaction);
				++i;
				i = IntMath.mod(i, factionList.size());
				currentFaction = factionList.get(i);
				usedAlignmentKeys = true;
			}
			if (regionList != null) {
				if (keyBindingAlignmentGroupPrev.getIsKeyPressed()) {
					pd.setRegionLastViewedFaction(currentRegion, currentFaction);
					lastViewedRegions.put(currentRegion, currentFaction);
					i = regionList.indexOf(currentRegion);
					--i;
					i = IntMath.mod(i, regionList.size());
					currentRegion = regionList.get(i);
					currentFaction = pd.getRegionLastViewedFaction(currentRegion);
					usedAlignmentKeys = true;
				}
				if (keyBindingAlignmentGroupNext.getIsKeyPressed()) {
					pd.setRegionLastViewedFaction(currentRegion, currentFaction);
					lastViewedRegions.put(currentRegion, currentFaction);
					i = regionList.indexOf(currentRegion);
					++i;
					i = IntMath.mod(i, regionList.size());
					currentRegion = regionList.get(i);
					currentFaction = pd.getRegionLastViewedFaction(currentRegion);
					usedAlignmentKeys = true;
				}
			}
		}
		if (usedAlignmentKeys) {
			LOTRClientProxy.sendClientInfoPacket(currentFaction, lastViewedRegions);
			alignmentChangeTick = 2;
		}
	}

	@SubscribeEvent
	public void MouseInputEvent(InputEvent.MouseInputEvent event) {
        if (LOTRConfig.enableAttackCooldown){
		LOTRAttackTiming.doAttackTiming();
        }
	}
}
