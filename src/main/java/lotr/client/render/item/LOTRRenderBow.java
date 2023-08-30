package lotr.client.render.item;

import java.util.*;

import org.lwjgl.opengl.GL11;

import lotr.client.LOTRClientProxy;
import lotr.common.item.LOTRItemBow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.IItemRenderer;

public class LOTRRenderBow implements IItemRenderer {
	public static boolean renderingWeaponRack;
	public LOTRRenderLargeItem largeItemRenderer;
	public Map<LOTRItemBow.BowState, LOTRRenderLargeItem.ExtraLargeIconToken> tokensPullStates;

	public LOTRRenderBow(LOTRRenderLargeItem large) {
		largeItemRenderer = large;
		if (largeItemRenderer != null) {
			tokensPullStates = new EnumMap<>(LOTRItemBow.BowState.class);
			for (LOTRItemBow.BowState state : LOTRItemBow.BowState.values()) {
				if (state == LOTRItemBow.BowState.HELD) {
					continue;
				}
				LOTRRenderLargeItem.ExtraLargeIconToken token = largeItemRenderer.extraIcon(state.iconName);
				tokensPullStates.put(state, token);
			}
		}
	}

	@Override
	public boolean handleRenderType(ItemStack itemstack, IItemRenderer.ItemRenderType type) {
		return type == IItemRenderer.ItemRenderType.EQUIPPED || type == IItemRenderer.ItemRenderType.EQUIPPED_FIRST_PERSON;
	}

	public boolean isLargeBow() {
		return largeItemRenderer != null;
	}

	@Override
	public void renderItem(IItemRenderer.ItemRenderType type, ItemStack itemstack, Object... data) {
		GL11.glPushMatrix();
		EntityLivingBase entity = (EntityLivingBase) data[1];
		if (!renderingWeaponRack && (Minecraft.getMinecraft().gameSettings.thirdPersonView != 0 || entity != Minecraft.getMinecraft().thePlayer)) {
			GL11.glTranslatef(0.9375f, 0.0625f, 0.0f);
			GL11.glRotatef(-335.0f, 0.0f, 0.0f, 1.0f);
			GL11.glRotatef(-50.0f, 0.0f, 1.0f, 0.0f);
			GL11.glScalef(0.6666667f, 0.6666667f, 0.6666667f);
			GL11.glTranslatef(0.0f, 0.3f, 0.0f);
			GL11.glRotatef(-20.0f, 0.0f, 0.0f, 1.0f);
			GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
			GL11.glRotatef(-60.0f, 0.0f, 0.0f, 1.0f);
			GL11.glScalef(2.6666667f, 2.6666667f, 2.6666667f);
			GL11.glTranslatef(-0.25f, -0.1875f, 0.1875f);
			GL11.glTranslatef(0.0f, 0.125f, 0.3125f);
			GL11.glRotatef(-20.0f, 0.0f, 1.0f, 0.0f);
			GL11.glScalef(0.625f, -0.625f, 0.625f);
			GL11.glRotatef(-100.0f, 1.0f, 0.0f, 0.0f);
			GL11.glRotatef(45.0f, 0.0f, 1.0f, 0.0f);
			GL11.glTranslatef(0.0f, -0.3f, 0.0f);
			GL11.glScalef(1.5f, 1.5f, 1.5f);
			GL11.glRotatef(50.0f, 0.0f, 1.0f, 0.0f);
			GL11.glRotatef(335.0f, 0.0f, 0.0f, 1.0f);
			GL11.glTranslatef(-0.9375f, -0.0625f, 0.0f);
		}
		if (largeItemRenderer != null) {
			Item item = itemstack.getItem();
			if (!(item instanceof LOTRItemBow)) {
				throw new RuntimeException("Attempting to render a large bow which is not a bow");
			}
			LOTRItemBow bow = (LOTRItemBow) item;
			LOTRItemBow.BowState bowState;
			if (entity instanceof EntityPlayer) {
				EntityPlayer entityplayer = (EntityPlayer) entity;
				ItemStack usingItem = entityplayer.getItemInUse();
				int useCount = entityplayer.getItemInUseCount();
				bowState = bow.getBowState(entityplayer, usingItem, useCount);
			} else {
				bowState = bow.getBowState(entity, itemstack, 0);
			}
			if (bowState == LOTRItemBow.BowState.HELD) {
				largeItemRenderer.renderLargeItem();
			} else {
				largeItemRenderer.renderLargeItem(tokensPullStates.get(bowState));
			}
		} else {
			IIcon icon = ((EntityLivingBase) data[1]).getItemIcon(itemstack, 0);
			icon = RenderBlocks.getInstance().getIconSafe(icon);
			float minU = icon.getMinU();
			float maxU = icon.getMaxU();
			float minV = icon.getMinV();
			float maxV = icon.getMaxV();
			int width = icon.getIconWidth();
			int height = icon.getIconWidth();
			Tessellator tessellator = Tessellator.instance;
			ItemRenderer.renderItemIn2D(tessellator, maxU, minV, minU, maxV, width, height, 0.0625f);
		}
		if (itemstack != null && itemstack.hasEffect(0)) {
			LOTRClientProxy.renderEnchantmentEffect();
		}
		GL11.glDisable(32826);
		GL11.glPopMatrix();
	}

	@Override
	public boolean shouldUseRenderHelper(IItemRenderer.ItemRenderType type, ItemStack itemstack, IItemRenderer.ItemRendererHelper helper) {
		return false;
	}
}
