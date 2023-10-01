package lotr.client.model;

import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.item.LOTRItemHaradRobes;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

public class LOTRModelHaradRobes extends LOTRModelHuman {
	public ItemStack robeItem;

	public LOTRModelHaradRobes() {
		this(0.0f);
	}

	public LOTRModelHaradRobes(float f) {
		super(f, true);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		int robeColor = LOTRItemHaradRobes.getRobesColor(robeItem);
		float r = (robeColor >> 16 & 0xFF) / 255.0f;
		float g = (robeColor >> 8 & 0xFF) / 255.0f;
		float b = (robeColor & 0xFF) / 255.0f;
		GL11.glColor3f(r, g, b);
		bipedChest.showModel = entity instanceof LOTREntityNPC && ((LOTREntityNPC) entity).shouldRenderNPCChest();
		bipedHead.render(f5);
		bipedHeadwear.render(f5);
		bipedBody.render(f5);
		bipedRightArm.render(f5);
		bipedLeftArm.render(f5);
		bipedRightLeg.render(f5);
		bipedLeftLeg.render(f5);
		GL11.glColor3f(1.0f, 1.0f, 1.0f);
	}

	public void setRobeItem(ItemStack itemstack) {
		robeItem = itemstack;
	}
}
