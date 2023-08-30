package lotr.client.render.entity;

import org.lwjgl.opengl.GL11;

import com.mojang.authlib.GameProfile;

import lotr.client.model.*;
import lotr.client.render.LOTRRenderShield;
import lotr.common.LOTRShields;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.tileentity.TileEntitySkullRenderer;
import net.minecraft.entity.*;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraftforge.client.*;

public abstract class LOTRRenderBiped extends RenderBiped {
	public static float PLAYER_SCALE = 0.9375f;
	public ModelBiped capeModel = new LOTRModelBiped();
	public ModelBiped npcRenderPassModel;

	protected LOTRRenderBiped(ModelBiped model, float f) {
		super(model, f);
	}

	@Override
	public void doRender(EntityLiving entity, double d, double d1, double d2, float f, float f1) {
		super.doRender(entity, d, d1, d2, f, f1);
		if (Minecraft.isGuiEnabled() && entity instanceof LOTREntityNPC) {
			LOTREntityNPC npc = (LOTREntityNPC) entity;
			if (npc.hiredNPCInfo.getHiringPlayer() == renderManager.livingPlayer) {
				LOTRNPCRendering.renderHiredIcon(npc, d, d1 + 0.5, d2);
				LOTRNPCRendering.renderNPCHealthBar(npc, d, d1 + 0.5, d2);
			}
			LOTRNPCRendering.renderQuestBook(npc, d, d1, d2);
			LOTRNPCRendering.renderQuestOffer(npc, d, d1, d2);
		}
	}

	@Override
	public void func_82408_c(EntityLiving entity, int pass, float f) {
		super.func_82408_c(entity, pass, f);
	}

	@Override
	public void func_82420_a(EntityLiving entity, ItemStack itemstack) {
		LOTRArmorModels.INSTANCE.setupModelForRender(modelBipedMain, modelBipedMain, entity);
		LOTRArmorModels.INSTANCE.setupModelForRender(field_82425_h, modelBipedMain, entity);
		LOTRArmorModels.INSTANCE.setupModelForRender(field_82423_g, modelBipedMain, entity);
		if (npcRenderPassModel != null) {
			LOTRArmorModels.INSTANCE.setupModelForRender(npcRenderPassModel, modelBipedMain, entity);
		}
	}

	@Override
	public void func_82421_b() {
		field_82423_g = new LOTRModelBiped(1.0f);
		field_82425_h = new LOTRModelBiped(0.5f);
	}

	public ResourceLocation getCapeToRender(LOTREntityNPC entity) {
		return entity.npcCape;
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		return super.getEntityTexture(entity);
	}

	public float getHeldItemYTranslation() {
		return 0.1875f;
	}

	@Override
	public void preRenderCallback(EntityLivingBase entity, float f) {
		float f1 = 0.9375f;
		GL11.glScalef(f1, f1, f1);
	}

	@Override
	public void renderEquippedItems(EntityLivingBase entity, float f) {
		ItemStack heldItem;
		ItemStack heldItemLeft;
		float f1;
		GL11.glColor3f(1.0f, 1.0f, 1.0f);
		ItemStack headItem = entity.getEquipmentInSlot(4);
		if (headItem != null) {
			boolean is3D;
			GL11.glPushMatrix();
			modelBipedMain.bipedHead.postRender(0.0625f);
			IItemRenderer customRenderer = MinecraftForgeClient.getItemRenderer(headItem, IItemRenderer.ItemRenderType.EQUIPPED);
			is3D = customRenderer != null && customRenderer.shouldUseRenderHelper(IItemRenderer.ItemRenderType.EQUIPPED, headItem, IItemRenderer.ItemRendererHelper.BLOCK_3D);
			if (headItem.getItem() instanceof ItemBlock) {
				if (is3D || RenderBlocks.renderItemIn3d(Block.getBlockFromItem(headItem.getItem()).getRenderType())) {
					f1 = 0.625f;
					GL11.glTranslatef(0.0f, -0.25f, 0.0f);
					GL11.glRotatef(90.0f, 0.0f, 1.0f, 0.0f);
					GL11.glScalef(f1, -f1, -f1);
				}
				renderManager.itemRenderer.renderItem(entity, headItem, 0);
			} else if (headItem.getItem() == Items.skull) {
				f1 = 1.0625f;
				GL11.glScalef(f1, -f1, -f1);
				GameProfile gameprofile = null;
				if (headItem.hasTagCompound()) {
					NBTTagCompound nbttagcompound = headItem.getTagCompound();
					if (nbttagcompound.hasKey("SkullOwner", new NBTTagCompound().getId())) {
						gameprofile = NBTUtil.func_152459_a(nbttagcompound.getCompoundTag("SkullOwner"));
					} else if (nbttagcompound.hasKey("SkullOwner", new NBTTagString().getId()) && !StringUtils.isNullOrEmpty(nbttagcompound.getString("SkullOwner"))) {
						gameprofile = new GameProfile(null, nbttagcompound.getString("SkullOwner"));
					}
				}
				TileEntitySkullRenderer.field_147536_b.func_152674_a(-0.5f, 0.0f, -0.5f, 1, 180.0f, headItem.getItemDamage(), gameprofile);
			}
			GL11.glPopMatrix();
		}
		heldItem = entity.getHeldItem();
		if (heldItem != null) {
			float f12;
			boolean is3D;
			GL11.glPushMatrix();
			if (mainModel.isChild) {
				float f13 = 0.5f;
				GL11.glTranslatef(0.0f, 0.625f, 0.0f);
				GL11.glRotatef(-20.0f, -1.0f, 0.0f, 0.0f);
				GL11.glScalef(f13, f13, f13);
			}
			modelBipedMain.bipedRightArm.postRender(0.0625f);
			GL11.glTranslatef(-0.0625f, 0.4375f, 0.0625f);
			IItemRenderer customRenderer = MinecraftForgeClient.getItemRenderer(heldItem, IItemRenderer.ItemRenderType.EQUIPPED);
			is3D = customRenderer != null && customRenderer.shouldUseRenderHelper(IItemRenderer.ItemRenderType.EQUIPPED, heldItem, IItemRenderer.ItemRendererHelper.BLOCK_3D);
			if (heldItem.getItem() instanceof ItemBlock && (is3D || RenderBlocks.renderItemIn3d(Block.getBlockFromItem(heldItem.getItem()).getRenderType()))) {
				f12 = 0.5f;
				GL11.glTranslatef(0.0f, getHeldItemYTranslation(), -0.3125f);
				GL11.glRotatef(20.0f, 1.0f, 0.0f, 0.0f);
				GL11.glRotatef(45.0f, 0.0f, 1.0f, 0.0f);
				GL11.glScalef(-(f12 *= 0.75f), -f12, f12);
			} else if (heldItem.getItem() == Items.bow) {
				f12 = 0.625f;
				GL11.glTranslatef(0.0f, getHeldItemYTranslation(), 0.3125f);
				GL11.glRotatef(-20.0f, 0.0f, 1.0f, 0.0f);
				GL11.glScalef(f12, -f12, f12);
				GL11.glRotatef(-100.0f, 1.0f, 0.0f, 0.0f);
				GL11.glRotatef(45.0f, 0.0f, 1.0f, 0.0f);
			} else if (heldItem.getItem().isFull3D()) {
				f12 = 0.625f;
				if (heldItem.getItem().shouldRotateAroundWhenRendering()) {
					GL11.glRotatef(180.0f, 0.0f, 0.0f, 1.0f);
					GL11.glTranslatef(0.0f, -getHeldItemYTranslation(), 0.0f);
				}
				GL11.glTranslatef(0.0f, getHeldItemYTranslation(), 0.0f);
				GL11.glScalef(f12, -f12, f12);
				GL11.glRotatef(-100.0f, 1.0f, 0.0f, 0.0f);
				GL11.glRotatef(45.0f, 0.0f, 1.0f, 0.0f);
			} else {
				f12 = 0.375f;
				GL11.glTranslatef(0.25f, getHeldItemYTranslation(), -0.1875f);
				GL11.glScalef(f12, f12, f12);
				GL11.glRotatef(60.0f, 0.0f, 0.0f, 1.0f);
				GL11.glRotatef(-90.0f, 1.0f, 0.0f, 0.0f);
				GL11.glRotatef(20.0f, 0.0f, 0.0f, 1.0f);
			}
			renderManager.itemRenderer.renderItem(entity, heldItem, 0);
			if (heldItem.getItem().requiresMultipleRenderPasses()) {
				for (int x = 1; x < heldItem.getItem().getRenderPasses(heldItem.getItemDamage()); ++x) {
					renderManager.itemRenderer.renderItem(entity, heldItem, x);
				}
			}
			GL11.glPopMatrix();
		}
		heldItemLeft = ((LOTREntityNPC) entity).getHeldItemLeft();
		if (heldItemLeft != null) {
			float f14;
			boolean is3D;
			GL11.glPushMatrix();
			if (mainModel.isChild) {
				f1 = 0.5f;
				GL11.glTranslatef(0.0f, 0.625f, 0.0f);
				GL11.glRotatef(-20.0f, -1.0f, 0.0f, 0.0f);
				GL11.glScalef(f1, f1, f1);
			}
			modelBipedMain.bipedLeftArm.postRender(0.0625f);
			GL11.glTranslatef(0.0625f, 0.4375f, 0.0625f);
			IItemRenderer customRenderer = MinecraftForgeClient.getItemRenderer(heldItemLeft, IItemRenderer.ItemRenderType.EQUIPPED);
			is3D = customRenderer != null && customRenderer.shouldUseRenderHelper(IItemRenderer.ItemRenderType.EQUIPPED, heldItemLeft, IItemRenderer.ItemRendererHelper.BLOCK_3D);
			if (heldItemLeft.getItem() instanceof ItemBlock && (is3D || RenderBlocks.renderItemIn3d(Block.getBlockFromItem(heldItemLeft.getItem()).getRenderType()))) {
				f14 = 0.5f;
				GL11.glTranslatef(0.0f, getHeldItemYTranslation(), -0.3125f);
				GL11.glRotatef(20.0f, 1.0f, 0.0f, 0.0f);
				GL11.glRotatef(45.0f, 0.0f, 1.0f, 0.0f);
				GL11.glScalef(-(f14 *= 0.75f), -f14, f14);
			} else {
				if (heldItemLeft.getItem().isFull3D()) {
					f14 = 0.625f;
					if (heldItemLeft.getItem().shouldRotateAroundWhenRendering()) {
						GL11.glRotatef(180.0f, 0.0f, 0.0f, 1.0f);
						GL11.glTranslatef(0.0f, -getHeldItemYTranslation(), 0.0f);
					}
				} else {
					f14 = 0.3175f;
				}
				GL11.glTranslatef(0.0f, getHeldItemYTranslation(), 0.0f);
				GL11.glScalef(f14, -f14, f14);
				GL11.glRotatef(-100.0f, 1.0f, 0.0f, 0.0f);
				GL11.glRotatef(45.0f, 0.0f, 1.0f, 0.0f);
			}
			renderManager.itemRenderer.renderItem(entity, heldItemLeft, 0);
			if (heldItemLeft.getItem().requiresMultipleRenderPasses()) {
				for (int i = 1; i < heldItemLeft.getItem().getRenderPasses(heldItemLeft.getItemDamage()); ++i) {
					renderManager.itemRenderer.renderItem(entity, heldItemLeft, i);
				}
			}
			GL11.glPopMatrix();
		}
		renderNPCCape((LOTREntityNPC) entity);
		renderNPCShield((LOTREntityNPC) entity);
	}

	public void renderNPCCape(LOTREntityNPC entity) {
		ResourceLocation capeTexture = getCapeToRender(entity);
		if (capeTexture != null) {
			GL11.glPushMatrix();
			GL11.glTranslatef(0.0f, 0.0f, 0.125f);
			GL11.glRotatef(180.0f, 0.0f, 1.0f, 0.0f);
			GL11.glRotatef(-10.0f, 1.0f, 0.0f, 0.0f);
			bindTexture(capeTexture);
			capeModel.renderCloak(0.0625f);
			GL11.glPopMatrix();
		}
	}

	public void renderNPCShield(LOTREntityNPC entity) {
		LOTRShields shield = entity.npcShield;
		if (shield != null) {
			LOTRRenderShield.renderShield(shield, entity, modelBipedMain);
		}
	}

	@Override
	public void setRenderPassModel(ModelBase model) {
		super.setRenderPassModel(model);
		if (model instanceof ModelBiped) {
			npcRenderPassModel = (ModelBiped) model;
		}
	}

	@Override
	public int shouldRenderPass(EntityLiving entity, int pass, float f) {
		ItemStack armor = entity.getEquipmentInSlot(3 - pass + 1);
		int specialArmorResult = LOTRArmorModels.INSTANCE.getEntityArmorModel(this, modelBipedMain, entity, armor, pass);
		if (specialArmorResult > 0) {
			return specialArmorResult;
		}
		return super.shouldRenderPass(entity, pass, f);
	}
}
