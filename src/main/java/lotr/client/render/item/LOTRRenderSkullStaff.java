package lotr.client.render.item;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

public class LOTRRenderSkullStaff implements IItemRenderer {
	public static ModelBase staffModel = new ModelBase() {
		public ModelRenderer staff = new ModelRenderer(this, 0, 0);

		{
			staff.addBox(-0.5f, 8.0f, -6.0f, 1, 1, 28, 0.0f);
			staff.addBox(-2.5f, 6.0f, -11.0f, 5, 5, 5, 0.0f);
			staff.rotateAngleY = 1.5707963267948966f;
			staff.rotateAngleZ = -0.3490658503988659f;
		}

		@Override
		public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
			staff.render(f5);
		}
	};
	public static ResourceLocation staffTexture = new ResourceLocation("lotr:item/skullStaff.png");

	@Override
	public boolean handleRenderType(ItemStack itemstack, IItemRenderer.ItemRenderType type) {
		return type == IItemRenderer.ItemRenderType.EQUIPPED || type == IItemRenderer.ItemRenderType.EQUIPPED_FIRST_PERSON;
	}

	@Override
	public void renderItem(IItemRenderer.ItemRenderType type, ItemStack itemstack, Object... data) {
		Minecraft.getMinecraft().getTextureManager().bindTexture(staffTexture);
		if (type == IItemRenderer.ItemRenderType.EQUIPPED_FIRST_PERSON) {
			GL11.glRotatef(-70.0f, 0.0f, 0.0f, 1.0f);
			GL11.glTranslatef(-0.5f, 0.0f, -0.5f);
		}
		staffModel.render(null, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0625f);
	}

	@Override
	public boolean shouldUseRenderHelper(IItemRenderer.ItemRenderType type, ItemStack itemstack, IItemRenderer.ItemRendererHelper helper) {
		return false;
	}

}
