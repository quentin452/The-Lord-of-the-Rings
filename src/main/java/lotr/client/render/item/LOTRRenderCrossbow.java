package lotr.client.render.item;

import lotr.client.LOTRClientProxy;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.item.LOTRItemCrossbow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

public class LOTRRenderCrossbow implements IItemRenderer {
	@Override
	public boolean handleRenderType(ItemStack itemstack, IItemRenderer.ItemRenderType type) {
		return type == IItemRenderer.ItemRenderType.EQUIPPED || type == IItemRenderer.ItemRenderType.EQUIPPED_FIRST_PERSON;
	}

	@Override
	public void renderItem(IItemRenderer.ItemRenderType type, ItemStack itemstack, Object... data) {
		RotationMode rotationMode;
		EntityLivingBase holder = (EntityLivingBase) data[1];
		boolean loaded = LOTRItemCrossbow.isLoaded(itemstack);
		boolean using = false;
		if (holder instanceof EntityPlayer) {
			using = ((EntityPlayer) holder).getItemInUse() == itemstack;
		} else if (holder instanceof EntityLiving) {
			using = holder.getHeldItem() == itemstack;
			if (using && holder instanceof LOTREntityNPC) {
				using = ((LOTREntityNPC) holder).clientCombatStance;
			}
		}
		if (LOTRRenderBow.renderingWeaponRack) {
			rotationMode = RotationMode.FIRST_PERSON_HOLDING;
		} else if (holder == Minecraft.getMinecraft().renderViewEntity && Minecraft.getMinecraft().gameSettings.thirdPersonView == 0) {
			rotationMode = using || loaded ? RotationMode.FIRST_PERSON_LOADED : RotationMode.FIRST_PERSON_HOLDING;
		} else {
			rotationMode = using || loaded ? RotationMode.ENTITY_LOADED : RotationMode.ENTITY_HOLDING;
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
		}
		if (rotationMode == RotationMode.FIRST_PERSON_LOADED) {
			GL11.glRotatef(-100.0f, 1.0f, 0.0f, 0.0f);
			GL11.glRotatef(-60.0f, 0.0f, 1.0f, 0.0f);
			GL11.glRotatef(-25.0f, 0.0f, 0.0f, 1.0f);
			GL11.glTranslatef(0.0f, 0.0f, -0.5f);
		} else if (rotationMode == RotationMode.ENTITY_HOLDING) {
			GL11.glTranslatef(0.0f, 0.125f, 0.3125f);
			GL11.glRotatef(-20.0f, 0.0f, 1.0f, 0.0f);
			GL11.glScalef(0.625f, -0.625f, 0.625f);
			GL11.glRotatef(-100.0f, 1.0f, 0.0f, 0.0f);
			GL11.glRotatef(45.0f, 0.0f, 1.0f, 0.0f);
			GL11.glTranslatef(0.0f, -0.3f, 0.0f);
			GL11.glScalef(1.625f, 1.625f, 1.625f);
			GL11.glRotatef(50.0f, 0.0f, 1.0f, 0.0f);
			GL11.glRotatef(335.0f, 0.0f, 0.0f, 1.0f);
			GL11.glTranslatef(-0.9375f, -0.0625f, 0.0f);
		} else if (rotationMode == RotationMode.ENTITY_LOADED) {
			GL11.glRotatef(50.0f, 0.0f, 0.0f, 1.0f);
			GL11.glTranslatef(0.0f, 0.0f, -0.15f);
			GL11.glTranslatef(0.0f, -0.5f, 0.0f);
		}
		IIcon icon = ((EntityLivingBase) data[1]).getItemIcon(itemstack, 0);
		if (icon == null) {
			GL11.glPopMatrix();
			return;
		}
		Tessellator tessellator = Tessellator.instance;
		float f = icon.getMinU();
		float f1 = icon.getMaxU();
		float f2 = icon.getMinV();
		float f3 = icon.getMaxV();
		ItemRenderer.renderItemIn2D(tessellator, f1, f2, f, f3, icon.getIconWidth(), icon.getIconHeight(), 0.0625f);
		if (itemstack != null && itemstack.hasEffect(0)) {
			LOTRClientProxy.renderEnchantmentEffect();
		}
		GL11.glDisable(32826);
	}

	@Override
	public boolean shouldUseRenderHelper(IItemRenderer.ItemRenderType type, ItemStack itemstack, IItemRenderer.ItemRendererHelper helper) {
		return false;
	}

	public enum RotationMode {
		FIRST_PERSON_HOLDING, FIRST_PERSON_LOADED, ENTITY_HOLDING, ENTITY_LOADED

	}

}
