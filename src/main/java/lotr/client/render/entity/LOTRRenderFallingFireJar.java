package lotr.client.render.entity;

import org.lwjgl.opengl.GL11;

import lotr.common.block.LOTRBlockRhunFireJar;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.RenderFallingBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LOTRRenderFallingFireJar extends RenderFallingBlock {
	public static RenderBlocks renderBlocks = new RenderBlocks();

	@Override
	public void doRender(Entity entity, double d, double d1, double d2, float f, float f1) {
		EntityFallingBlock falling = (EntityFallingBlock) entity;
		World world = falling.func_145807_e();
		Block block = falling.func_145805_f();
		int i = MathHelper.floor_double(falling.posX);
		int j = MathHelper.floor_double(falling.posY);
		int k = MathHelper.floor_double(falling.posZ);
		if (block instanceof LOTRBlockRhunFireJar) {
			if (block != world.getBlock(i, j, k)) {
				GL11.glPushMatrix();
				GL11.glTranslatef((float) d, (float) d1, (float) d2);
				bindEntityTexture(entity);
				GL11.glDisable(2896);
				renderBlocks.blockAccess = world;
				Tessellator tessellator = Tessellator.instance;
				tessellator.startDrawingQuads();
				tessellator.setTranslation((-i) - 0.5f, (-j) - 0.5f, (-k) - 0.5f);
				renderBlocks.renderBlockByRenderType(block, i, j, k);
				tessellator.setTranslation(0.0, 0.0, 0.0);
				tessellator.draw();
				GL11.glEnable(2896);
				GL11.glPopMatrix();
			}
		} else {
			super.doRender(entity, d, d1, d2, f, f1);
		}
	}
}
