package lotr.client.gui;

import org.lwjgl.opengl.GL11;

import lotr.common.LOTRMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.multiplayer.WorldClient;

public class LOTRGuiButtonMenu extends GuiButton {
	public Class<? extends LOTRGuiMenuBase> menuScreenClass;
	public int menuKeyCode;

	public LOTRGuiButtonMenu(LOTRGuiMenu gui, int i, int x, int y, Class<? extends LOTRGuiMenuBase> cls, String s, int key) {
		super(i, x, y, 32, 32, s);
		menuScreenClass = cls;
		menuKeyCode = key;
	}

	public boolean canDisplayMenu() {
		if (menuScreenClass == LOTRGuiMap.class) {
			WorldClient world = Minecraft.getMinecraft().theWorld;
			return world != null && world.getWorldInfo().getTerrainType() != LOTRMod.worldTypeMiddleEarthClassic;
		}
		return true;
	}

	@Override
	public void drawButton(Minecraft mc, int i, int j) {
		if (visible) {
			mc.getTextureManager().bindTexture(LOTRGuiMenu.menuIconsTexture);
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			field_146123_n = i >= xPosition && j >= yPosition && i < xPosition + width && j < yPosition + height;
			drawTexturedModalRect(xPosition, yPosition, (enabled ? 0 : width * 2) + (field_146123_n ? width : 0), id * height, width, height);
			mouseDragged(mc, i, j);
		}
	}

	public LOTRGuiMenuBase openMenu() {
		try {
			return menuScreenClass.getConstructor().newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
