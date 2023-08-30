package lotr.client.gui;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.MathHelper;

public class LOTRGuiSlider extends GuiButton {
	public String baseDisplayString;
	public String overrideStateString;
	public boolean isTime;
	public boolean isFloat;
	public boolean valueOnly;
	public int numberDigits;
	public int minValue;
	public int maxValue;
	public float minValueF;
	public float maxValueF;
	public float sliderValue = 1.0f;
	public boolean dragging;

	public LOTRGuiSlider(int id, int x, int y, int width, int height, String s) {
		super(id, x, y, width, height, s);
		baseDisplayString = s;
	}

	@Override
	public void drawButton(Minecraft mc, int i, int j) {
		if (overrideStateString != null) {
			displayString = overrideStateString;
		} else if (isTime) {
			int value = getSliderValue();
			int seconds = value % 60;
			int minutes = value / 60;
			displayString = String.format("%d:%02d", minutes, seconds);
		} else if (isFloat) {
			displayString = String.format("%.2f", getSliderValue_F());
		} else {
			int value = getSliderValue();
			displayString = String.valueOf(value);
			if (numberDigits > 0) {
				displayString = String.format("%0" + numberDigits + "d", value);
			}
		}
		if (!valueOnly) {
			displayString = baseDisplayString + ": " + displayString;
		}
		super.drawButton(mc, i, j);
	}

	@Override
	public int getHoverState(boolean flag) {
		return 0;
	}

	public int getSliderValue() {
		return minValue + Math.round(sliderValue * (maxValue - minValue));
	}

	public float getSliderValue_F() {
		return minValueF + sliderValue * (maxValueF - minValueF);
	}

	@Override
	public void mouseDragged(Minecraft mc, int i, int j) {
		if (visible && enabled) {
			if (dragging) {
				sliderValue = (float) (i - (xPosition + 4)) / (width - 8);
				if (sliderValue < 0.0f) {
					sliderValue = 0.0f;
				}
				if (sliderValue > 1.0f) {
					sliderValue = 1.0f;
				}
			}
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			drawTexturedModalRect(xPosition + (int) (sliderValue * (width - 8)), yPosition, 0, 66, 4, 20);
			drawTexturedModalRect(xPosition + (int) (sliderValue * (width - 8)) + 4, yPosition, 196, 66, 4, 20);
		}
	}

	@Override
	public boolean mousePressed(Minecraft mc, int i, int j) {
		if (super.mousePressed(mc, i, j)) {
			sliderValue = (float) (i - (xPosition + 4)) / (width - 8);
			if (sliderValue < 0.0f) {
				sliderValue = 0.0f;
			}
			if (sliderValue > 1.0f) {
				sliderValue = 1.0f;
			}
			dragging = true;
			return true;
		}
		return false;
	}

	@Override
	public void mouseReleased(int i, int j) {
		dragging = false;
	}

	public void setFloat() {
		isFloat = true;
	}

	public void setMinMaxValues(int min, int max) {
		minValue = min;
		maxValue = max;
	}

	public void setMinMaxValues_F(float min, float max) {
		minValueF = min;
		maxValueF = max;
	}

	public void setMinutesSecondsTime() {
		isTime = true;
	}

	public void setNumberDigits(int i) {
		numberDigits = i;
	}

	public void setOverrideStateString(String s) {
		overrideStateString = s;
	}

	public void setSliderValue(int value) {
		value = MathHelper.clamp_int(value, minValue, maxValue);
		sliderValue = (float) (value - minValue) / (maxValue - minValue);
	}

	public void setSliderValue_F(float value) {
		value = MathHelper.clamp_float(value, minValueF, maxValueF);
		sliderValue = (value - minValueF) / (maxValueF - minValueF);
	}

	public void setValueOnly() {
		valueOnly = true;
	}
}
