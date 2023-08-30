package lotr.client.gui;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.ObfuscationReflectionHelper;
import lotr.common.world.map.LOTRWaypoint;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiLabel;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.client.ForgeHooksClient;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LOTRGuiMainMenu extends GuiMainMenu {
	public static ResourceLocation titleTexture = new ResourceLocation("textures/gui/title/minecraft.png");
	public static ResourceLocation menuOverlay = new ResourceLocation("lotr:gui/menu_overlay.png");
	public static LOTRGuiRendererMap mapRenderer;
	public static int tickCounter;
	public static Random rand = new Random();
	public static boolean isFirstMenu = true;
	public static List<LOTRWaypoint> waypointRoute = new ArrayList<>();
	public static int currentWPIndex;
	public static boolean randomWPStart;
	public static float mapSpeed;
	public static float mapVelX;
	public static float mapVelY;

	public LOTRGuiMap mapGui;
	public boolean fadeIn = isFirstMenu;
	public long firstRenderTime;

	public LOTRGuiMainMenu() {
		isFirstMenu = false;
		mapGui = new LOTRGuiMap();
		mapRenderer = new LOTRGuiRendererMap();
		mapRenderer.setSepia(false);
		if (waypointRoute.isEmpty()) {
			setupWaypoints();
			currentWPIndex = randomWPStart ? rand.nextInt(waypointRoute.size()) : 0;
		}
		LOTRWaypoint wp = waypointRoute.get(currentWPIndex);
		mapRenderer.prevMapX = mapRenderer.mapX = wp.getX();
		mapRenderer.prevMapY = mapRenderer.mapY = wp.getY();
	}

	@Override
	public void drawScreen(int i, int j, float f) {
		GL11.glEnable(3008);
		GL11.glEnable(3042);
		OpenGlHelper.glBlendFunc(770, 771, 1, 0);
		if (firstRenderTime == 0L && fadeIn) {
			firstRenderTime = System.currentTimeMillis();
		}
		float fade = fadeIn ? (System.currentTimeMillis() - firstRenderTime) / 1000.0f : 1.0f;
		float fadeAlpha = fadeIn ? MathHelper.clamp_float(fade - 1.0f, 0.0f, 1.0f) : 1.0f;
		mapRenderer.zoomExp = -0.1f + MathHelper.cos((tickCounter + f) * 0.003f) * 0.8f;
		if (fadeIn) {
			float slowerFade = fade * 0.5f;
			float fadeInZoom = MathHelper.clamp_float(1.0f - slowerFade, 0.0f, 1.0f) * -1.5f;
			mapRenderer.zoomExp += fadeInZoom;
		}
		mapRenderer.zoomStable = (float) Math.pow(2.0, -0.10000000149011612);
		mapRenderer.renderMap(this, mapGui, f);
		mapRenderer.renderVignettes(this, zLevel, 2);
		GL11.glEnable(3042);
		OpenGlHelper.glBlendFunc(770, 771, 1, 0);
		GL11.glColor4f(1.0f, 1.0f, 1.0f, fadeIn ? MathHelper.clamp_float(1.0f - fade, 0.0f, 1.0f) : 0.0f);
		mc.getTextureManager().bindTexture(menuOverlay);
		Gui.func_152125_a(0, 0, 0.0f, 0.0f, 16, 128, width, height, 16.0f, 128.0f);
		int fadeAlphaI = MathHelper.ceiling_float_int(fadeAlpha * 255.0f) << 24;
		if ((fadeAlphaI & 0xFC000000) != 0) {
			int short1 = 274;
			int k = width / 2 - short1 / 2;
			int b0 = 30;
			mc.getTextureManager().bindTexture(titleTexture);
			GL11.glColor4f(1.0f, 1.0f, 1.0f, fadeAlpha);
			drawTexturedModalRect(k, b0, 0, 0, 155, 44);
			drawTexturedModalRect(k + 155, b0, 0, 45, 155, 44);
			String modSubtitle = StatCollector.translateToLocal("lotr.menu.title");
			drawString(fontRendererObj, modSubtitle, width / 2 - fontRendererObj.getStringWidth(modSubtitle) / 2, 80, -1);
			List brandings = Lists.reverse((List) FMLCommonHandler.instance().getBrandings(true));
			for (int l = 0; l < brandings.size(); ++l) {
				String brd = (String) brandings.get(l);
				if (Strings.isNullOrEmpty(brd)) {
					continue;
				}
				drawString(fontRendererObj, brd, 2, height - (10 + l * (fontRendererObj.FONT_HEIGHT + 1)), -1);
			}
			ForgeHooksClient.renderMainMenu(this, fontRendererObj, width, height);
			String copyright = "Powered by Hummel009";
			drawString(fontRendererObj, copyright, width - fontRendererObj.getStringWidth(copyright) - 2, height - 10, -1);
			String field_92025_p = ObfuscationReflectionHelper.getPrivateValue(GuiMainMenu.class, this, "field_92025_p");
			String field_146972_A = ObfuscationReflectionHelper.getPrivateValue(GuiMainMenu.class, this, "field_146972_A");
			int field_92024_r = ObfuscationReflectionHelper.getPrivateValue(GuiMainMenu.class, this, "field_92024_r");
			int field_92022_t = ObfuscationReflectionHelper.getPrivateValue(GuiMainMenu.class, this, "field_92022_t");
			int field_92021_u = ObfuscationReflectionHelper.getPrivateValue(GuiMainMenu.class, this, "field_92021_u");
			int field_92020_v = ObfuscationReflectionHelper.getPrivateValue(GuiMainMenu.class, this, "field_92020_v");
			int field_92019_w = ObfuscationReflectionHelper.getPrivateValue(GuiMainMenu.class, this, "field_92019_w");
			if (field_92025_p != null && !field_92025_p.isEmpty()) {
				Gui.drawRect(field_92022_t - 2, field_92021_u - 2, field_92020_v + 2, field_92019_w - 1, 1428160512);
				drawString(fontRendererObj, field_92025_p, field_92022_t, field_92021_u, -1);
				drawString(fontRendererObj, field_146972_A, (width - field_92024_r) / 2, ((List<GuiButton>) buttonList).get(0).yPosition - 12, -1);
			}
			for (Object button : buttonList) {
				((GuiButton) button).drawButton(mc, i, j);
			}
			for (Object label : labelList) {
				((GuiLabel) label).func_146159_a(mc, i, j);
			}
		}
	}

	@Override
	public void initGui() {
		super.initGui();
		int lowerButtonMaxY = 0;
		for (Object obj : buttonList) {
			GuiButton button = (GuiButton) obj;
			int buttonMaxY = button.yPosition + button.height;
			if (buttonMaxY <= lowerButtonMaxY) {
				continue;
			}
			lowerButtonMaxY = buttonMaxY;
		}
		int idealMoveDown = 50;
		int lowestSuitableHeight = height - 25;
		int moveDown = Math.min(idealMoveDown, lowestSuitableHeight - lowerButtonMaxY);
		moveDown = Math.max(moveDown, 0);
		for (int i = 0; i < buttonList.size(); ++i) {
			GuiButton button = ((List<GuiButton>) buttonList).get(i);
			button.yPosition += moveDown;
			if (button.getClass() != GuiButton.class) {
				continue;
			}
			LOTRGuiButtonRedBook newButton = new LOTRGuiButtonRedBook(button.id, button.xPosition, button.yPosition, button.width, button.height, button.displayString);
			buttonList.set(i, newButton);
		}
	}

	@Override
	public void setWorldAndResolution(Minecraft mc, int i, int j) {
		super.setWorldAndResolution(mc, i, j);
		mapGui.setWorldAndResolution(mc, i, j);
	}

	@Override
	public void updateScreen() {
		super.updateScreen();
		++tickCounter;
		mapRenderer.updateTick();
		LOTRWaypoint wp = waypointRoute.get(currentWPIndex);
		double dx = wp.getX() - mapRenderer.mapX;
		double dy = wp.getY() - mapRenderer.mapY;
		double distSq = dx * dx + dy * dy;
		double dist = Math.sqrt(distSq);
		if (dist <= 12.0) {
			if (++currentWPIndex >= waypointRoute.size()) {
				currentWPIndex = 0;
			}
		} else {
			mapSpeed += 0.01f;
			mapSpeed = Math.min(mapSpeed, 0.8f);
			double vXNew = dx / dist * mapSpeed;
			double vYNew = dy / dist * mapSpeed;
			float a = 0.02f;
			mapVelX = (float) (mapVelX + (vXNew - mapVelX) * a);
			mapVelY = (float) (mapVelY + (vYNew - mapVelY) * a);
		}
		mapRenderer.mapX += mapVelX;
		mapRenderer.mapY += mapVelY;
	}

	public static void setupWaypoints() {
		waypointRoute.clear();
		waypointRoute.add(LOTRWaypoint.HOBBITON);
		waypointRoute.add(LOTRWaypoint.BRANDYWINE_BRIDGE);
		waypointRoute.add(LOTRWaypoint.BUCKLEBURY);
		waypointRoute.add(LOTRWaypoint.WITHYWINDLE_VALLEY);
		waypointRoute.add(LOTRWaypoint.BREE);
		waypointRoute.add(LOTRWaypoint.WEATHERTOP);
		waypointRoute.add(LOTRWaypoint.RIVENDELL);
		waypointRoute.add(LOTRWaypoint.WEST_GATE);
		waypointRoute.add(LOTRWaypoint.DIMRILL_DALE);
		waypointRoute.add(LOTRWaypoint.CERIN_AMROTH);
		waypointRoute.add(LOTRWaypoint.CARAS_GALADHON);
		waypointRoute.add(LOTRWaypoint.NORTH_UNDEEP);
		waypointRoute.add(LOTRWaypoint.SOUTH_UNDEEP);
		waypointRoute.add(LOTRWaypoint.ARGONATH);
		waypointRoute.add(LOTRWaypoint.RAUROS);
		waypointRoute.add(LOTRWaypoint.EDORAS);
		waypointRoute.add(LOTRWaypoint.HELMS_DEEP);
		waypointRoute.add(LOTRWaypoint.ISENGARD);
		waypointRoute.add(LOTRWaypoint.DUNHARROW);
		waypointRoute.add(LOTRWaypoint.ERECH);
		waypointRoute.add(LOTRWaypoint.MINAS_TIRITH);
		waypointRoute.add(LOTRWaypoint.MINAS_MORGUL);
		waypointRoute.add(LOTRWaypoint.MOUNT_DOOM);
		waypointRoute.add(LOTRWaypoint.MORANNON);
		waypointRoute.add(LOTRWaypoint.EAST_RHOVANION_ROAD);
		waypointRoute.add(LOTRWaypoint.OLD_RHOVANION);
		waypointRoute.add(LOTRWaypoint.RUNNING_FORD);
		waypointRoute.add(LOTRWaypoint.DALE_CITY);
		waypointRoute.add(LOTRWaypoint.THRANDUIL_HALLS);
		waypointRoute.add(LOTRWaypoint.ENCHANTED_RIVER);
		waypointRoute.add(LOTRWaypoint.FOREST_GATE);
		waypointRoute.add(LOTRWaypoint.BEORN);
		waypointRoute.add(LOTRWaypoint.EAGLES_EYRIE);
		waypointRoute.add(LOTRWaypoint.GOBLIN_TOWN);
		waypointRoute.add(LOTRWaypoint.MOUNT_GRAM);
		waypointRoute.add(LOTRWaypoint.FORNOST);
		waypointRoute.add(LOTRWaypoint.ANNUMINAS);
		waypointRoute.add(LOTRWaypoint.MITHLOND_NORTH);
		waypointRoute.add(LOTRWaypoint.TOWER_HILLS);
	}
}
