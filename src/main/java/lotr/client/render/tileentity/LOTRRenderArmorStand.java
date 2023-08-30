package lotr.client.render.tileentity;

import org.lwjgl.opengl.GL11;

import io.gitlab.dwarfyassassin.lotrucp.client.util.FakeArmorStandEntity;
import lotr.client.LOTRClientProxy;
import lotr.client.model.*;
import lotr.common.item.LOTRItemPlate;
import lotr.common.tileentity.LOTRTileEntityArmorStand;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.*;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.ForgeHooksClient;

public class LOTRRenderArmorStand extends TileEntitySpecialRenderer {
	public static ResourceLocation standTexture = new ResourceLocation("lotr:item/armorStand.png");
	public static ModelBase standModel = new LOTRModelArmorStand();
	public static ModelBiped modelBipedMain = new ModelBiped(0.0f);
	public static ModelBiped modelBiped1 = new ModelBiped(1.0f);
	public static ModelBiped modelBiped2 = new ModelBiped(0.5f);
	public static float BIPED_ARM_ROTATION = -7.07353f;
	public static float BIPED_TICKS_EXISTED = 46.88954f;

	@Override
	public void renderTileEntityAt(TileEntity tileentity, double d, double d1, double d2, float f) {
		LOTRTileEntityArmorStand armorStand = (LOTRTileEntityArmorStand) tileentity;
		FakeArmorStandEntity fakeArmorEntity = FakeArmorStandEntity.INSTANCE;
		GL11.glPushMatrix();
		GL11.glDisable(2884);
		GL11.glEnable(32826);
		GL11.glEnable(3008);
		GL11.glTranslatef((float) d + 0.5f, (float) d1 + 1.5f, (float) d2 + 0.5f);
		switch (armorStand.getBlockMetadata() & 3) {
			case 0: {
				GL11.glRotatef(0.0f, 0.0f, 1.0f, 0.0f);
				break;
			}
			case 1: {
				GL11.glRotatef(270.0f, 0.0f, 1.0f, 0.0f);
				break;
			}
			case 2: {
				GL11.glRotatef(180.0f, 0.0f, 1.0f, 0.0f);
				break;
			}
			case 3: {
				GL11.glRotatef(90.0f, 0.0f, 1.0f, 0.0f);
			}
		}
		GL11.glScalef(-1.0f, -1.0f, 1.0f);
		float scale = 0.0625f;
		bindTexture(standTexture);
		standModel.render(fakeArmorEntity, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, scale);
		LOTRArmorModels.INSTANCE.setupModelForRender(modelBipedMain, null, fakeArmorEntity);
		GL11.glTranslatef(0.0f, -0.1875f, 0.0f);
		for (int slot = 0; slot < 4; ++slot) {
			float f4;
			ItemStack itemstack = armorStand.getStackInSlot(slot);
			if (itemstack == null || !(itemstack.getItem() instanceof ItemArmor) && !(itemstack.getItem() instanceof LOTRItemPlate)) {
				continue;
			}
			boolean isArmor = itemstack.getItem() instanceof ItemArmor;
			if (isArmor) {
				bindTexture(RenderBiped.getArmorResource(fakeArmorEntity, itemstack, slot, null));
			}
			ModelBiped armorModel = slot == 2 ? modelBiped2 : modelBiped1;
			LOTRArmorModels.INSTANCE.setupArmorForSlot(armorModel, slot);
			armorModel = ForgeHooksClient.getArmorModel(fakeArmorEntity, itemstack, slot, armorModel);
			ModelBiped specialModel = LOTRArmorModels.INSTANCE.getSpecialArmorModel(itemstack, slot, fakeArmorEntity, modelBipedMain);
			if (specialModel != null) {
				armorModel = specialModel;
			}
			LOTRArmorModels.INSTANCE.setupModelForRender(armorModel, null, fakeArmorEntity);
			float f1 = 1.0f;
			boolean isColoredArmor = false;
			if (isArmor) {
				int j = ((ItemArmor) itemstack.getItem()).getColor(itemstack);
				if (j == -1) {
					GL11.glColor3f(f1, f1, f1);
				} else {
					float f2 = (j >> 16 & 0xFF) / 255.0f;
					float f3 = (j >> 8 & 0xFF) / 255.0f;
					f4 = (j & 0xFF) / 255.0f;
					GL11.glColor3f(f1 * f2, f1 * f3, f1 * f4);
					isColoredArmor = true;
				}
			} else {
				GL11.glColor3f(f1, f1, f1);
			}
			armorModel.render(fakeArmorEntity, BIPED_ARM_ROTATION, 0.0f, BIPED_TICKS_EXISTED, 0.0f, 0.0f, scale);
			if (isColoredArmor) {
				bindTexture(RenderBiped.getArmorResource(null, itemstack, slot, "overlay"));
				f1 = 1.0f;
				GL11.glColor3f(f1, f1, f1);
				armorModel.render(fakeArmorEntity, BIPED_ARM_ROTATION, 0.0f, BIPED_TICKS_EXISTED, 0.0f, 0.0f, scale);
			}
			if (!itemstack.isItemEnchanted()) {
				continue;
			}
			float f2 = armorStand.ticksExisted + f;
			bindTexture(LOTRClientProxy.enchantmentTexture);
			GL11.glEnable(3042);
			float f3 = 0.5f;
			GL11.glColor4f(f3, f3, f3, 1.0f);
			GL11.glDepthFunc(514);
			GL11.glDepthMask(false);
			for (int k = 0; k < 2; ++k) {
				GL11.glDisable(2896);
				f4 = 0.76f;
				GL11.glColor4f(0.5f * f4, 0.25f * f4, 0.8f * f4, 1.0f);
				GL11.glBlendFunc(768, 1);
				GL11.glMatrixMode(5890);
				GL11.glLoadIdentity();
				float f5 = 0.33333334f;
				GL11.glScalef(f5, f5, f5);
				GL11.glRotatef(30.0f - k * 60.0f, 0.0f, 0.0f, 1.0f);
				float f6 = f2 * (0.001f + k * 0.003f) * 20.0f;
				GL11.glTranslatef(0.0f, f6, 0.0f);
				GL11.glMatrixMode(5888);
				armorModel.render(fakeArmorEntity, BIPED_ARM_ROTATION, 0.0f, BIPED_TICKS_EXISTED, 0.0f, 0.0f, scale);
			}
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			GL11.glMatrixMode(5890);
			GL11.glDepthMask(true);
			GL11.glLoadIdentity();
			GL11.glMatrixMode(5888);
			GL11.glEnable(2896);
			GL11.glDisable(3042);
			GL11.glDepthFunc(515);
		}
		GL11.glEnable(2884);
		GL11.glDisable(32826);
		GL11.glPopMatrix();
	}
}
