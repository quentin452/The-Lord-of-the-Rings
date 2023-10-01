package lotr.client.gui;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import lotr.common.entity.npc.*;
import lotr.common.inventory.LOTRContainerTrade;
import lotr.common.inventory.LOTRSlotTrade;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.network.LOTRPacketSell;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public class LOTRGuiTrade extends GuiContainer {
	public static ResourceLocation guiTexture = new ResourceLocation("lotr:gui/npc/trade.png");
	public static int lockedTradeColor = -1610612736;
	public LOTREntityNPC theEntity;
	public LOTRContainerTrade containerTrade;
	public GuiButton buttonSell;

	public LOTRGuiTrade(InventoryPlayer inv, LOTRTradeable trader, World world) {
		super(new LOTRContainerTrade(inv, trader, world));
		containerTrade = (LOTRContainerTrade) inventorySlots;
		theEntity = (LOTREntityNPC) trader;
		ySize = 270;
	}

	@Override
	public void actionPerformed(GuiButton button) {
		if (button.enabled && button == buttonSell) {
			IMessage packet = new LOTRPacketSell();
			LOTRPacketHandler.networkWrapper.sendToServer(packet);
		}
	}

	public void drawCenteredString(String s, int i, int j, int k) {
		fontRendererObj.drawString(s, i - fontRendererObj.getStringWidth(s) / 2, j, k);
	}

	@Override
	public void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		mc.getTextureManager().bindTexture(guiTexture);
		Gui.func_146110_a(guiLeft, guiTop, 0.0f, 0.0f, xSize, ySize, 512.0f, 512.0f);
	}

	@Override
	public void drawGuiContainerForegroundLayer(int i, int j) {
		int l;
		drawCenteredString(theEntity.getNPCName(), 89, 11, 4210752);
		fontRendererObj.drawString(StatCollector.translateToLocal("container.lotr.trade.buy"), 8, 28, 4210752);
		fontRendererObj.drawString(StatCollector.translateToLocal("container.lotr.trade.sell"), 8, 79, 4210752);
		fontRendererObj.drawString(StatCollector.translateToLocal("container.lotr.trade.sellOffer"), 8, 129, 4210752);
		fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, 176, 4210752);
		for (l = 0; l < containerTrade.tradeInvBuy.getSizeInventory(); ++l) {
			LOTRSlotTrade slotBuy = (LOTRSlotTrade) containerTrade.getSlotFromInventory(containerTrade.tradeInvBuy, l);
			renderTradeSlot(slotBuy);
		}
		for (l = 0; l < containerTrade.tradeInvSell.getSizeInventory(); ++l) {
			LOTRSlotTrade slotSell = (LOTRSlotTrade) containerTrade.getSlotFromInventory(containerTrade.tradeInvSell, l);
			renderTradeSlot(slotSell);
		}
		int totalSellPrice = 0;
		for (int l2 = 0; l2 < containerTrade.tradeInvSellOffer.getSizeInventory(); ++l2) {
			LOTRTradeSellResult sellResult;
			Slot slotSell = containerTrade.getSlotFromInventory(containerTrade.tradeInvSellOffer, l2);
			ItemStack item = slotSell.getStack();
			if (item == null || (sellResult = LOTRTradeEntries.getItemSellResult(item, theEntity)) == null) {
				continue;
			}
			totalSellPrice += sellResult.totalSellValue;
		}
		if (totalSellPrice > 0) {
			fontRendererObj.drawString(StatCollector.translateToLocalFormatted("container.lotr.trade.sellPrice", totalSellPrice), 100, 169, 4210752);
		}
		buttonSell.enabled = totalSellPrice > 0;
	}

	@Override
	public void initGui() {
		super.initGui();
		buttonSell = new LOTRGuiTradeButton(0, guiLeft + 79, guiTop + 164);
		buttonSell.enabled = false;
		buttonList.add(buttonSell);
	}

	public void renderCost(String s, int x, int y) {
		boolean halfSize;
		int l = fontRendererObj.getStringWidth(s);
		halfSize = l > 15;
		if (halfSize) {
			GL11.glPushMatrix();
			GL11.glScalef(0.5f, 0.5f, 1.0f);
			x *= 2;
			y *= 2;
			y += fontRendererObj.FONT_HEIGHT / 2;
		}
		drawCenteredString(s, x, y, 4210752);
		if (halfSize) {
			GL11.glPopMatrix();
		}
	}

	public void renderTradeSlot(LOTRSlotTrade slot) {
		LOTRTradeEntry trade = slot.getTrade();
		if (trade != null) {
			int lockedPixels;
			boolean inFront;
			if (trade.isAvailable()) {
				lockedPixels = trade.getLockedProgressForSlot();
				inFront = false;
			} else {
				lockedPixels = 16;
				inFront = true;
			}
			if (lockedPixels > 0) {
				GL11.glPushMatrix();
				if (inFront) {
					GL11.glTranslatef(0.0f, 0.0f, 200.0f);
				}
				int x = slot.xDisplayPosition;
				int y = slot.yDisplayPosition;
				Gui.drawRect(x, y, x + lockedPixels, y + 16, lockedTradeColor);
				GL11.glPopMatrix();
			}
			if (trade.isAvailable()) {
				int cost = slot.cost();
				if (cost > 0) {
					renderCost(Integer.toString(cost), slot.xDisplayPosition + 8, slot.yDisplayPosition + 22);
				}
			} else {
				drawCenteredString(StatCollector.translateToLocal("container.lotr.trade.locked"), slot.xDisplayPosition + 8, slot.yDisplayPosition + 22, 4210752);
			}
		}
	}
}
