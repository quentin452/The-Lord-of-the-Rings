package lotr.client.gui;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import org.lwjgl.opengl.GL11;

import lotr.common.inventory.LOTRContainerBarrel;
import lotr.common.network.*;
import lotr.common.tileentity.LOTRTileEntityBarrel;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;

public class LOTRGuiBarrel extends GuiContainer {
	public static ResourceLocation guiTexture = new ResourceLocation("lotr:gui/barrel/barrel.png");
	public static ResourceLocation brewingTexture = new ResourceLocation("lotr:gui/barrel/brewing.png");
	public LOTRTileEntityBarrel theBarrel;
	public GuiButton brewingButton;
	public float brewAnim = -1.0f;

	public LOTRGuiBarrel(InventoryPlayer inv, LOTRTileEntityBarrel barrel) {
		super(new LOTRContainerBarrel(inv, barrel));
		theBarrel = barrel;
		xSize = 210;
		ySize = 221;
	}

	@Override
	public void actionPerformed(GuiButton button) {
		if (button.enabled && button.id == 0) {
			IMessage packet = new LOTRPacketBrewingButton();
			LOTRPacketHandler.networkWrapper.sendToServer(packet);
		}
	}

	@Override
	public void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		mc.getTextureManager().bindTexture(guiTexture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		int brewMode = theBarrel.barrelMode;
		int fullAmount = theBarrel.getBarrelFullAmountScaled(96);
		if (brewMode == 1) {
			fullAmount = theBarrel.getBrewProgressScaled(96);
		}
		float prevBrewAnim = brewAnim;
		brewAnim = theBarrel.getBrewAnimationProgressScaledF(97, f);
		float brewAnimF = prevBrewAnim + (brewAnim - prevBrewAnim) * f;
		float brewAnimPc = theBarrel.getBrewAnimationProgressScaledF(1, f);
		if (brewMode == 1 || brewMode == 2) {
			IIcon liquidIcon;
			int x0 = guiLeft + 148;
			int x1 = guiLeft + 196;
			int y0 = guiTop + 34;
			int y1 = guiTop + 130;
			int yFull = y1 - fullAmount;
			float yAnim = y1 - brewAnimF;
			ItemStack itemstack = theBarrel.getStackInSlot(9);
			if (itemstack != null && (liquidIcon = itemstack.getItem().getIconFromDamage(-1)) != null) {
				mc.getTextureManager().bindTexture(TextureMap.locationItemsTexture);
				float minU = liquidIcon.getInterpolatedU(7.0);
				float maxU = liquidIcon.getInterpolatedU(8.0);
				float minV = liquidIcon.getInterpolatedV(7.0);
				float maxV = liquidIcon.getInterpolatedV(8.0);
				Tessellator tessellator = Tessellator.instance;
				tessellator.startDrawingQuads();
				tessellator.addVertexWithUV(x0, y1, zLevel, minU, maxV);
				tessellator.addVertexWithUV(x1, y1, zLevel, maxU, maxV);
				tessellator.addVertexWithUV(x1, yFull, zLevel, maxU, minV);
				tessellator.addVertexWithUV(x0, yFull, zLevel, minU, minV);
				tessellator.draw();
				int fullColor = 2167561;
				drawGradientRect(x0, yFull, x1, y1, 0, 0xFF000000 | fullColor);
			}
			if (brewMode == 1) {
				mc.getTextureManager().bindTexture(brewingTexture);
				GL11.glEnable(3042);
				OpenGlHelper.glBlendFunc(770, 771, 1, 0);
				GL11.glDisable(3008);
				GL11.glColor4f(1.0f, 1.0f, 1.0f, brewAnimPc);
				LOTRGuiScreenBase.drawTexturedModalRectFloat(x0, yAnim, 51.0, 0.0, x1 - x0, y1 - yAnim, 256, zLevel);
				GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
				GL11.glEnable(3008);
				GL11.glDisable(3042);
			}
			mc.getTextureManager().bindTexture(brewingTexture);
			drawTexturedModalRect(x0, y0, 1, 0, x1 - x0, y1 - y0);
		}
	}

	@Override
	public void drawGuiContainerForegroundLayer(int i, int j) {
		String s = theBarrel.getInventoryName();
		String s1 = theBarrel.getInvSubtitle();
		fontRendererObj.drawString(s, xSize / 2 - fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
		fontRendererObj.drawString(s1, xSize / 2 - fontRendererObj.getStringWidth(s1) / 2, 17, 4210752);
		fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 25, 127, 4210752);
	}

	@Override
	public void drawScreen(int i, int j, float f) {
		if (theBarrel.barrelMode == 0) {
			brewingButton.enabled = theBarrel.getStackInSlot(9) != null;
			brewingButton.displayString = StatCollector.translateToLocal("container.lotr.barrel.startBrewing");
		}
		if (theBarrel.barrelMode == 1) {
			brewingButton.enabled = theBarrel.getStackInSlot(9) != null && theBarrel.getStackInSlot(9).getItemDamage() > 0;
			brewingButton.displayString = StatCollector.translateToLocal("container.lotr.barrel.stopBrewing");
		}
		if (theBarrel.barrelMode == 2) {
			brewingButton.enabled = false;
			brewingButton.displayString = StatCollector.translateToLocal("container.lotr.barrel.startBrewing");
		}
		super.drawScreen(i, j, f);
	}

	@Override
	public void initGui() {
		super.initGui();
		brewingButton = new GuiButton(0, guiLeft + 25, guiTop + 97, 100, 20, StatCollector.translateToLocal("container.lotr.barrel.startBrewing"));
		buttonList.add(brewingButton);
	}
}
