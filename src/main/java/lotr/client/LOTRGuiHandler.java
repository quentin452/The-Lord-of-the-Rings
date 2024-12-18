package lotr.client;

import com.google.common.collect.Lists;
import cpw.mods.fml.client.GuiModList;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import lotr.client.gui.*;
import lotr.common.*;
import lotr.common.entity.npc.LOTREntityNPCRideable;
import lotr.common.inventory.LOTRContainerCoinExchange;
import lotr.common.item.LOTRItemCoin;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.network.LOTRPacketMountOpenInv;
import lotr.common.network.LOTRPacketRestockPouches;
import lotr.common.world.LOTRWorldProvider;
import lotr.compatibility.LOTRModChecker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.InventoryEffectRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.event.HoverEvent;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.world.WorldProvider;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.common.MinecraftForge;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.util.*;

public class LOTRGuiHandler {
	public static RenderItem itemRenderer = new RenderItem();
	public static boolean coinCountLeftSide = false;
	public static Set<Class<? extends Container>> coinCount_excludedContainers = new HashSet<>();
	public static Set<Class<? extends GuiContainer>> coinCount_excludedGUIs = new HashSet<>();
	public static Set<Class<? extends IInventory>> coinCount_excludedInvTypes = new HashSet<>();
	public static Set<String> coinCount_excludedContainers_clsNames = new HashSet<>();
	public static Set<String> coinCount_excludedGUIs_clsNames = new HashSet<>();
	public static Set<String> coinCount_excludedInvTypes_clsNames = new HashSet<>();
	public static Set<Class<? extends GuiContainer>> pouchRestock_leftPositionGUIs = new HashSet<>();
	public static Set<Class<? extends GuiContainer>> pouchRestock_sidePositionGUIs = new HashSet<>();

	static {
		coinCount_excludedInvTypes.add(LOTRContainerCoinExchange.InventoryCoinExchangeSlot.class);
		coinCount_excludedInvTypes.add(InventoryCraftResult.class);
		coinCount_excludedInvTypes_clsNames.add("thaumcraft.common.entities.InventoryMob");
		pouchRestock_leftPositionGUIs.add(LOTRGuiAnvil.class);
		pouchRestock_leftPositionGUIs.add(GuiRepair.class);
		pouchRestock_sidePositionGUIs.add(LOTRGuiBarrel.class);
	}

	public int descScrollIndex = -1;

	public LOTRGuiHandler() {
		FMLCommonHandler.instance().bus().register(this);
		MinecraftForge.EVENT_BUS.register(this);
	}

	public void addPouchRestockButton(GuiScreen gui, Collection buttons) {
		if (gui instanceof GuiContainer && !(gui instanceof LOTRGuiPouch) && !(gui instanceof LOTRGuiChestWithPouch)) {
			GuiContainer guiContainer = (GuiContainer) gui;
			EntityClientPlayerMP thePlayer = guiContainer.mc.thePlayer;
			InventoryPlayer playerInv = thePlayer.inventory;
			boolean containsPlayer = false;
			Slot topRightPlayerSlot = null;
			Slot topLeftPlayerSlot = null;
			Container container = guiContainer.inventorySlots;
			for (Object obj : container.inventorySlots) {
				boolean acceptableSlotIndex;
				Slot slot = (Slot) obj;
				acceptableSlotIndex = slot.getSlotIndex() < playerInv.mainInventory.length;
				if (gui instanceof GuiContainerCreative) {
					acceptableSlotIndex = slot.getSlotIndex() >= 9;
				}
				if (slot.inventory != playerInv || !acceptableSlotIndex) {
					continue;
				}
				containsPlayer = true;
				boolean isTopRight = topRightPlayerSlot == null || slot.yDisplayPosition < topRightPlayerSlot.yDisplayPosition || slot.yDisplayPosition == topRightPlayerSlot.yDisplayPosition && slot.xDisplayPosition > topRightPlayerSlot.xDisplayPosition;
				if (isTopRight) {
					topRightPlayerSlot = slot;
				}
				boolean isTopLeft = topLeftPlayerSlot == null || slot.yDisplayPosition < topLeftPlayerSlot.yDisplayPosition || slot.yDisplayPosition == topLeftPlayerSlot.yDisplayPosition && slot.xDisplayPosition < topLeftPlayerSlot.xDisplayPosition;
				if (!isTopLeft) {
					continue;
				}
				topLeftPlayerSlot = slot;
			}
			if (containsPlayer) {
				int guiLeft = LOTRReflectionClient.getGuiLeft(guiContainer);
				int guiTop = LOTRReflectionClient.getGuiTop(guiContainer);
				LOTRReflectionClient.getGuiXSize(guiContainer);
				int buttonX = topRightPlayerSlot.xDisplayPosition + 7;
				int buttonY = topRightPlayerSlot.yDisplayPosition - 14;
				if (pouchRestock_leftPositionGUIs.contains(gui.getClass())) {
					buttonX = topLeftPlayerSlot.xDisplayPosition - 1;
					buttonY = topLeftPlayerSlot.yDisplayPosition - 14;
				} else if (pouchRestock_sidePositionGUIs.contains(gui.getClass())) {
					buttonX = topRightPlayerSlot.xDisplayPosition + 21;
					buttonY = topRightPlayerSlot.yDisplayPosition - 1;
				}
				if (LOTRModChecker.hasNEI() && guiContainer instanceof InventoryEffectRenderer && LOTRReflectionClient.hasGuiPotionEffects((InventoryEffectRenderer) guiContainer)) {
					buttonX -= 60;
				}
				buttons.add(new LOTRGuiButtonRestockPouch(guiContainer, 2000, guiLeft + buttonX, guiTop + buttonY));
			}
		}
	}

	public GuiButton getDifficultyButton(GuiOptions gui, Iterable buttons) {
		for (Object obj : buttons) {
			GuiOptionButton button;
			if (!(obj instanceof GuiOptionButton) || (button = (GuiOptionButton) obj).returnEnumOptions() != GameSettings.Options.DIFFICULTY) {
				continue;
			}
			return button;
		}
		return null;
	}

	@SubscribeEvent
	public void onGuiOpen(GuiOpenEvent event) {
		Object gui = event.gui;
		if (LOTRConfig.customMainMenu && gui != null && gui.getClass() == GuiMainMenu.class) {
			event.gui = (GuiScreen) (gui = new LOTRGuiMainMenu());
		}
		if (gui != null && gui.getClass() == GuiDownloadTerrain.class) {
			Minecraft mc = Minecraft.getMinecraft();
			WorldProvider provider = mc.theWorld.provider;
			if (provider instanceof LOTRWorldProvider) {
				event.gui = new LOTRGuiDownloadTerrain(mc.thePlayer.sendQueue);
			}
		}
	}

	@SubscribeEvent
	public void postActionPerformed(GuiScreenEvent.ActionPerformedEvent.Post event) {
		Minecraft mc = Minecraft.getMinecraft();
		GuiScreen gui = event.gui;
		List buttons = event.buttonList;
		GuiButton button = event.button;
		if (gui instanceof GuiOptions && button instanceof LOTRGuiButtonLock && button.enabled && mc.isSingleplayer()) {
			LOTRLevelData.setSavedDifficulty(mc.gameSettings.difficulty);
			LOTRLevelData.setDifficultyLocked(true);
			button.enabled = false;
			GuiButton buttonDifficulty = getDifficultyButton((GuiOptions) gui, buttons);
			if (buttonDifficulty != null) {
				buttonDifficulty.enabled = false;
			}
		}
		if (button instanceof LOTRGuiButtonRestockPouch && button.enabled) {
			IMessage packet = new LOTRPacketRestockPouches();
			LOTRPacketHandler.networkWrapper.sendToServer(packet);
		}
	}

	@SubscribeEvent
	public void postDrawScreen(GuiScreenEvent.DrawScreenEvent.Post event) {
		HoverEvent hoverevent;
		IChatComponent component;
		Minecraft mc = Minecraft.getMinecraft();
		EntityClientPlayerMP entityplayer = mc.thePlayer;
		GuiScreen gui = event.gui;
		int mouseX = event.mouseX;
		int mouseY = event.mouseY;
		if (gui instanceof GuiChat && (component = mc.ingameGUI.getChatGUI().func_146236_a(Mouse.getX(), Mouse.getY())) != null && component.getChatStyle().getChatHoverEvent() != null && (hoverevent = component.getChatStyle().getChatHoverEvent()).getAction() == LOTRChatEvents.SHOW_LOTR_ACHIEVEMENT) {
			LOTRGuiAchievementHoverEvent proxyGui = new LOTRGuiAchievementHoverEvent();
			proxyGui.setWorldAndResolution(mc, gui.width, gui.height);
			try {
				String unformattedText = hoverevent.getValue().getUnformattedText();
				int splitIndex = unformattedText.indexOf('$');
				String categoryName = unformattedText.substring(0, splitIndex);
				LOTRAchievement.Category category = LOTRAchievement.categoryForName(categoryName);
				int achievementID = Integer.parseInt(unformattedText.substring(splitIndex + 1));
				LOTRAchievement achievement = LOTRAchievement.achievementForCategoryAndID(category, achievementID);
				IChatComponent name = new ChatComponentTranslation("lotr.gui.achievements.hover.name", achievement.getAchievementChatComponent(entityplayer));
				IChatComponent subtitle = new ChatComponentTranslation("lotr.gui.achievements.hover.subtitle", achievement.getDimension().getDimensionName(), category.getDisplayName());
				subtitle.getChatStyle().setItalic(true);
				String desc = achievement.getDescription(entityplayer);
				List list = Lists.newArrayList((Object[]) new String[]{name.getFormattedText(), subtitle.getFormattedText()});
				list.addAll(mc.fontRenderer.listFormattedStringToWidth(desc, 150));
				proxyGui.func_146283_a(list, mouseX, mouseY);
			} catch (Exception e) {
				proxyGui.drawCreativeTabHoveringText(EnumChatFormatting.RED + "Invalid LOTRAchievement!", mouseX, mouseY);
			}
		}
	}

	@SubscribeEvent
	public void postInitGui(GuiScreenEvent.InitGuiEvent.Post event) {
		GuiButton buttonDifficulty;
		GuiScreen gui = event.gui;
		List buttons = event.buttonList;
		if (gui instanceof GuiOptions && (buttonDifficulty = getDifficultyButton((GuiOptions) gui, buttons)) != null) {
			LOTRGuiButtonLock lock = new LOTRGuiButtonLock(1000000, buttonDifficulty.xPosition + buttonDifficulty.width + 4, buttonDifficulty.yPosition);
			lock.enabled = !LOTRLevelData.isDifficultyLocked();
			buttons.add(lock);
			buttonDifficulty.enabled = !LOTRLevelData.isDifficultyLocked();
		}
		addPouchRestockButton(gui, buttons);
	}

	@SubscribeEvent
	public void preDrawScreen(GuiScreenEvent.DrawScreenEvent.Pre event) {
		Minecraft mc = Minecraft.getMinecraft();
		GuiScreen gui = event.gui;
		if (gui instanceof GuiModList) {
			ModContainer mod = LOTRMod.getModContainer();
			ModMetadata meta = mod.getMetadata();
			if (descScrollIndex == -1) {
				meta.description = LOTRModInfo.concatenateDescription(0);
				descScrollIndex = 0;
			}
			while (Mouse.next()) {
				int dwheel = Mouse.getEventDWheel();
				if (dwheel == 0) {
					continue;
				}
				int scroll = Integer.signum(dwheel);
				descScrollIndex -= scroll;
				descScrollIndex = MathHelper.clamp_int(descScrollIndex, 0, LOTRModInfo.description.length - 1);
				meta.description = LOTRModInfo.concatenateDescription(descScrollIndex);
			}
		}
		if (gui instanceof GuiContainer && LOTRConfig.displayCoinCounts) {
			boolean excludeGui;
			mc.theWorld.theProfiler.startSection("invCoinCount");
			GuiContainer guiContainer = (GuiContainer) gui;
			Container container = guiContainer.inventorySlots;
			Class<?> containerCls = container.getClass();
			Class<?> guiCls = guiContainer.getClass();
			boolean excludeContainer = coinCount_excludedContainers.contains(containerCls) || coinCount_excludedContainers_clsNames.contains(containerCls.getName());
			excludeGui = coinCount_excludedGUIs.contains(guiCls) || coinCount_excludedGUIs_clsNames.contains(guiCls.getName());
			if (guiContainer instanceof GuiContainerCreative && LOTRReflectionClient.getCreativeTabIndex((GuiContainerCreative) guiContainer) != CreativeTabs.tabInventory.getTabIndex()) {
				excludeGui = true;
			}
			if (!excludeContainer && !excludeGui) {
				int guiLeft = -1;
				int guiTop = -1;
				int guiXSize = -1;
				Collection<IInventory> differentInvs = new ArrayList<>();
				HashMap<IInventory, Integer> invHighestY = new HashMap<>();
				for (int i = 0; i < container.inventorySlots.size(); ++i) {
					boolean excludeInv;
					Slot slot = container.getSlot(i);
					IInventory inv = slot.inventory;
					if (inv == null) {
						continue;
					}
					Class<?> invClass = inv.getClass();
					excludeInv = coinCount_excludedInvTypes.contains(invClass) || coinCount_excludedInvTypes_clsNames.contains(invClass.getName());
					if (excludeInv) {
						continue;
					}
					if (!differentInvs.contains(inv)) {
						differentInvs.add(inv);
					}
					int slotY = slot.yDisplayPosition;
					if (!invHighestY.containsKey(inv)) {
						invHighestY.put(inv, slotY);
						continue;
					}
					int highestY = invHighestY.get(inv);
					if (slotY >= highestY) {
						continue;
					}
					invHighestY.put(inv, slotY);
				}
				for (IInventory inv : differentInvs) {
					int coins = LOTRItemCoin.getContainerValue(inv, true);
					if (coins <= 0) {
						continue;
					}
					String sCoins = String.valueOf(coins);
					int sCoinsW = mc.fontRenderer.getStringWidth(sCoins);
					int border = 2;
					int rectWidth = 18 + sCoinsW + 1;
					if (guiLeft == -1) {
						guiTop = LOTRReflectionClient.getGuiTop(guiContainer);
						guiXSize = LOTRReflectionClient.getGuiXSize(guiContainer);
						guiLeft = gui.width / 2 - guiXSize / 2;
						if (guiContainer instanceof InventoryEffectRenderer && LOTRReflectionClient.hasGuiPotionEffects((InventoryEffectRenderer) gui) && !LOTRModChecker.hasNEI()) {
							guiLeft += 60;
						}
					}
					int guiGap = 8;
					int x = guiLeft + guiXSize + guiGap;
					if (coinCountLeftSide) {
						x = guiLeft - guiGap;
						x -= rectWidth;
					}
					int y = invHighestY.get(inv) + guiTop;
					int rectX0 = x - border;
					int rectX1 = x + rectWidth + border;
					int rectY0 = y - border;
					int rectY1 = y + 16 + border;
					float a0 = 1.0f;
					float a1 = 0.1f;
					GL11.glDisable(3553);
					GL11.glDisable(3008);
					GL11.glShadeModel(7425);
					GL11.glPushMatrix();
					GL11.glTranslatef(0.0f, 0.0f, -500.0f);
					Tessellator tessellator = Tessellator.instance;
					tessellator.startDrawingQuads();
					tessellator.setColorRGBA_F(0.0f, 0.0f, 0.0f, a1);
					tessellator.addVertex(rectX1, rectY0, 0.0);
					tessellator.setColorRGBA_F(0.0f, 0.0f, 0.0f, a0);
					tessellator.addVertex(rectX0, rectY0, 0.0);
					tessellator.addVertex(rectX0, rectY1, 0.0);
					tessellator.setColorRGBA_F(0.0f, 0.0f, 0.0f, a1);
					tessellator.addVertex(rectX1, rectY1, 0.0);
					tessellator.draw();
					GL11.glPopMatrix();
					GL11.glShadeModel(7424);
					GL11.glEnable(3008);
					GL11.glEnable(3553);
					GL11.glPushMatrix();
					GL11.glTranslatef(0.0f, 0.0f, 500.0f);
					GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
					itemRenderer.renderItemIntoGUI(mc.fontRenderer, mc.getTextureManager(), new ItemStack(LOTRMod.silverCoin), x, y);
					GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
					GL11.glDisable(2896);
					mc.fontRenderer.drawString(sCoins, x + 16 + 2, y + (16 - mc.fontRenderer.FONT_HEIGHT + 2) / 2, 16777215);
					GL11.glPopMatrix();
					GL11.glDisable(2896);
					GL11.glEnable(3008);
					GL11.glEnable(3042);
					GL11.glDisable(2884);
				}
				mc.theWorld.theProfiler.endSection();
			}
		}
	}

	@SubscribeEvent
	public void preInitGui(GuiScreenEvent.InitGuiEvent.Pre event) {
		GuiScreen gui = event.gui;
		Minecraft mc = Minecraft.getMinecraft();
		EntityClientPlayerMP entityplayer = mc.thePlayer;
		WorldClient world = mc.theWorld;
		if ((gui instanceof GuiInventory || gui instanceof GuiContainerCreative) && entityplayer != null && world != null && entityplayer.ridingEntity instanceof LOTREntityNPCRideable && ((LOTREntityNPCRideable) entityplayer.ridingEntity).getMountInventory() != null) {
			entityplayer.closeScreen();
			IMessage packet = new LOTRPacketMountOpenInv();
			LOTRPacketHandler.networkWrapper.sendToServer(packet);
			event.setCanceled(true);
		}
	}
}
