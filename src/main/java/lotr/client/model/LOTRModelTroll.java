package lotr.client.model;

import lotr.common.entity.item.LOTREntityStoneTroll;
import lotr.common.entity.npc.LOTREntityMountainTroll;
import lotr.common.entity.npc.LOTREntitySnowTroll;
import lotr.common.entity.npc.LOTREntityTroll;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;

public class LOTRModelTroll extends ModelBase {
	public ModelRenderer head;
	public ModelRenderer headHurt;
	public ModelRenderer body;
	public ModelRenderer rightArm;
	public ModelRenderer leftArm;
	public ModelRenderer rightLeg;
	public ModelRenderer leftLeg;
	public ModelRenderer woodenClub;
	public ModelRenderer woodenClubSpikes;
	public ModelRenderer warhammer;
	public ModelRenderer battleaxe;
	public boolean isOutiftModel;

	public LOTRModelTroll() {
		this(0.0f);
	}

	public LOTRModelTroll(float f) {
		textureWidth = 128;
		textureHeight = 128;
		head = new ModelRenderer(this, 0, 0);
		head.addBox(-6.0f, -6.0f, -12.0f, 12, 12, 12, f);
		head.setRotationPoint(0.0f, -27.0f, -6.0f);
		head.setTextureOffset(40, 0).addBox(6.0f, -2.0f, -8.0f, 1, 4, 3, f);
		head.mirror = true;
		head.setTextureOffset(40, 0).addBox(-7.0f, -2.0f, -8.0f, 1, 4, 3, f);
		head.mirror = false;
		head.setTextureOffset(0, 0).addBox(-1.0f, -1.0f, -14.0f, 2, 3, 2, f);
		headHurt = new ModelRenderer(this, 48, 44);
		headHurt.addBox(-6.0f, -6.0f, -12.0f, 12, 12, 12, f);
		headHurt.setRotationPoint(0.0f, -27.0f, -6.0f);
		headHurt.setTextureOffset(40, 0).addBox(6.0f, -2.0f, -8.0f, 1, 4, 3, f);
		headHurt.mirror = true;
		headHurt.setTextureOffset(40, 0).addBox(-7.0f, -2.0f, -8.0f, 1, 4, 3, f);
		headHurt.mirror = false;
		headHurt.setTextureOffset(0, 0).addBox(-1.0f, -1.0f, -14.0f, 2, 3, 2, f);
		body = new ModelRenderer(this, 48, 0);
		body.addBox(-12.0f, -28.0f, -8.0f, 24, 28, 16, f);
		body.setRotationPoint(0.0f, 0.0f, 0.0f);
		rightArm = new ModelRenderer(this, 0, 24);
		rightArm.mirror = true;
		rightArm.addBox(-12.0f, -3.0f, -6.0f, 12, 12, 12, f);
		rightArm.setRotationPoint(-12.0f, -23.0f, 0.0f);
		rightArm.setTextureOffset(0, 48).addBox(-11.0f, 9.0f, -5.0f, 10, 20, 10, f);
		leftArm = new ModelRenderer(this, 0, 24);
		leftArm.addBox(0.0f, -3.0f, -6.0f, 12, 12, 12, f);
		leftArm.setRotationPoint(12.0f, -23.0f, 0.0f);
		leftArm.setTextureOffset(0, 48).addBox(1.0f, 9.0f, -5.0f, 10, 20, 10, f);
		rightLeg = new ModelRenderer(this, 0, 78);
		rightLeg.mirror = true;
		rightLeg.addBox(-6.0f, 0.0f, -6.0f, 11, 12, 12, f);
		rightLeg.setRotationPoint(-6.0f, 0.0f, 0.0f);
		rightLeg.setTextureOffset(0, 102).addBox(-5.5f, 12.0f, -5.0f, 10, 12, 10);
		leftLeg = new ModelRenderer(this, 0, 78);
		leftLeg.addBox(-5.0f, 0.0f, -6.0f, 11, 12, 12, f);
		leftLeg.setRotationPoint(6.0f, 0.0f, 0.0f);
		leftLeg.setTextureOffset(0, 102).addBox(-4.5f, 12.0f, -5.0f, 10, 12, 10);
		woodenClub = new ModelRenderer(this, 0, 0);
		woodenClub.addBox(-9.0f, 5.0f, 21.0f, 6, 24, 6, f);
		woodenClub.setRotationPoint(-12.0f, -23.0f, 0.0f);
		woodenClubSpikes = new ModelRenderer(this, 24, 0);
		woodenClubSpikes.addBox(-12.0f, 25.0f, 23.5f, 12, 1, 1, f);
		woodenClubSpikes.addBox(-12.0f, 20.0f, 23.5f, 12, 1, 1, f);
		woodenClubSpikes.addBox(-12.0f, 15.0f, 23.5f, 12, 1, 1, f);
		woodenClubSpikes.setTextureOffset(24, 2);
		woodenClubSpikes.addBox(-6.5f, 25.0f, 18.0f, 1, 1, 12, f);
		woodenClubSpikes.addBox(-6.5f, 20.0f, 18.0f, 1, 1, 12, f);
		woodenClubSpikes.addBox(-6.5f, 15.0f, 18.0f, 1, 1, 12, f);
		woodenClubSpikes.setRotationPoint(-12.0f, -23.0f, 0.0f);
		warhammer = new ModelRenderer(this, 52, 29);
		warhammer.setRotationPoint(-12.0f, -23.0f, 0.0f);
		warhammer.addBox(-7.5f, 5.0f, 22.5f, 3, 20, 3, f);
		warhammer.setTextureOffset(0, 32).addBox(-12.0f, 25.0f, 14.0f, 12, 12, 20, f);
		battleaxe = new ModelRenderer(this, 64, 0);
		battleaxe.setRotationPoint(-12.0f, -23.0f, 0.0f);
		battleaxe.addBox(-7.0f, -40.0f, 22.5f, 2, 80, 2, f);
		battleaxe.setTextureOffset(72, 0);
		battleaxe.addBox(-6.0f, 20.0f, 24.0f, 0, 24, 16, f);
	}

	public LOTRModelTroll(float f, int i) {
		this(f);
		isOutiftModel = true;
		switch (i) {
			case 0:
				head.showModel = true;
				body.showModel = true;
				rightArm.showModel = true;
				leftArm.showModel = true;
				rightLeg.showModel = false;
				leftLeg.showModel = false;
				break;
			case 1:
				head.showModel = false;
				body.showModel = false;
				rightArm.showModel = false;
				leftArm.showModel = false;
				rightLeg.showModel = true;
				leftLeg.showModel = true;
				break;
			case 2:
				head.showModel = true;
				body.showModel = false;
				rightArm.showModel = false;
				leftArm.showModel = false;
				rightLeg.showModel = false;
				leftLeg.showModel = false;
				break;
			case 3:
				head.showModel = false;
				body.showModel = true;
				rightArm.showModel = true;
				leftArm.showModel = true;
				rightLeg.showModel = false;
				leftLeg.showModel = false;
				break;
			default:
				break;
		}
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		boolean isHurt = false;
		boolean hasTwoHeads = false;
		if (entity instanceof LOTREntityTroll) {
			LOTREntityTroll troll = (LOTREntityTroll) entity;
			isHurt = !isOutiftModel && troll.shouldRenderHeadHurt();
			hasTwoHeads = troll.hasTwoHeads();
		} else if (entity instanceof LOTREntityStoneTroll) {
			LOTREntityStoneTroll troll = (LOTREntityStoneTroll) entity;
			isHurt = false;
			hasTwoHeads = troll.hasTwoHeads();
		}
		if (hasTwoHeads) {
			GL11.glPushMatrix();
			GL11.glRotatef(-15.0f, 0.0f, 0.0f, 1.0f);
			GL11.glRotatef(10.0f, 0.0f, 1.0f, 0.0f);
			if (isHurt) {
				headHurt.render(f5);
			} else {
				head.render(f5);
			}
			GL11.glPopMatrix();
			GL11.glPushMatrix();
			GL11.glRotatef(15.0f, 0.0f, 0.0f, 1.0f);
			GL11.glRotatef(-10.0f, 0.0f, 1.0f, 0.0f);
			if (isHurt) {
				headHurt.render(f5);
			} else {
				head.render(f5);
			}
			GL11.glPopMatrix();
		} else if (isHurt) {
			headHurt.render(f5);
		} else {
			head.render(f5);
		}
		body.render(f5);
		rightArm.render(f5);
		leftArm.render(f5);
		rightLeg.render(f5);
		leftLeg.render(f5);
	}

	public void renderBattleaxe(float f) {
		battleaxe.rotationPointX = rightArm.rotationPointX;
		battleaxe.rotationPointY = rightArm.rotationPointY;
		battleaxe.rotationPointZ = rightArm.rotationPointZ;
		battleaxe.rotateAngleX = rightArm.rotateAngleX - 1.5707964f;
		battleaxe.rotateAngleY = rightArm.rotateAngleY;
		battleaxe.rotateAngleZ = rightArm.rotateAngleZ;
		battleaxe.render(f);
	}

	public void renderWarhammer(float f) {
		warhammer.rotationPointX = rightArm.rotationPointX;
		warhammer.rotationPointY = rightArm.rotationPointY;
		warhammer.rotationPointZ = rightArm.rotationPointZ;
		warhammer.rotateAngleX = rightArm.rotateAngleX - 1.5707964f;
		warhammer.rotateAngleY = rightArm.rotateAngleY;
		warhammer.rotateAngleZ = rightArm.rotateAngleZ;
		warhammer.render(f);
	}

	public void renderWoodenClub(float f) {
		woodenClub.rotationPointX = rightArm.rotationPointX;
		woodenClub.rotationPointY = rightArm.rotationPointY;
		woodenClub.rotationPointZ = rightArm.rotationPointZ;
		woodenClub.rotateAngleX = rightArm.rotateAngleX - 1.5707964f;
		woodenClub.rotateAngleY = rightArm.rotateAngleY;
		woodenClub.rotateAngleZ = rightArm.rotateAngleZ;
		woodenClub.render(f);
	}

	public void renderWoodenClubWithSpikes(float f) {
		renderWoodenClub(f);
		woodenClubSpikes.rotationPointX = woodenClub.rotationPointX;
		woodenClubSpikes.rotationPointY = woodenClub.rotationPointY;
		woodenClubSpikes.rotationPointZ = woodenClub.rotationPointZ;
		woodenClubSpikes.rotateAngleX = woodenClub.rotateAngleX;
		woodenClubSpikes.rotateAngleY = woodenClub.rotateAngleY;
		woodenClubSpikes.rotateAngleZ = woodenClub.rotateAngleZ;
		woodenClubSpikes.render(f);
	}

	@Override
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
		float f6;
		head.rotationPointX = 0.0f;
		head.rotationPointY = -27.0f;
		head.rotateAngleZ = 0.0f;
		body.rotationPointX = 0.0f;
		body.rotationPointY = 0.0f;
		body.rotateAngleZ = 0.0f;
		rightArm.rotationPointX = -12.0f;
		rightArm.rotationPointY = -23.0f;
		leftArm.rotationPointX = 12.0f;
		leftArm.rotationPointY = -23.0f;
		head.rotateAngleY = f3 / 57.295776f;
		head.rotateAngleX = f4 / 57.295776f;
		if (entity instanceof LOTREntityTroll && ((LOTREntityTroll) entity).sniffTime > 0) {
			f6 = (((LOTREntityTroll) entity).sniffTime - (f2 - entity.ticksExisted)) / 8.0f;
			head.rotateAngleY = MathHelper.sin(f6 * 6.2831855f) * 0.5f;
		}
		rightArm.rotateAngleX = MathHelper.cos(f * 0.6662f + 3.1415927f) * 2.0f * f1 * 0.5f;
		leftArm.rotateAngleX = MathHelper.cos(f * 0.6662f) * 2.0f * f1 * 0.5f;
		if (entity instanceof LOTREntityTroll) {
			rightArm.rotateAngleX = rightArm.rotateAngleX * 0.5f - 0.31415927f;
		}
		rightArm.rotateAngleZ = 0.0f;
		leftArm.rotateAngleZ = 0.0f;
		if (onGround > -9990.0f) {
			f6 = onGround;
			body.rotateAngleY = MathHelper.sin(MathHelper.sqrt_float(f6) * 3.1415927f * 2.0f) * 0.2f;
			rightArm.rotationPointZ = MathHelper.sin(body.rotateAngleY) * 5.0f;
			rightArm.rotationPointX = -MathHelper.cos(body.rotateAngleY) * 12.0f;
			leftArm.rotationPointZ = -MathHelper.sin(body.rotateAngleY) * 5.0f;
			leftArm.rotationPointX = MathHelper.cos(body.rotateAngleY) * 12.0f;
			leftArm.rotateAngleX += body.rotateAngleY;
			f6 = 1.0f - onGround;
			f6 *= f6;
			f6 *= f6;
			f6 = 1.0f - f6;
			float f7 = MathHelper.sin(f6 * 3.1415927f);
			float f8 = MathHelper.sin(onGround * 3.1415927f) * -(head.rotateAngleX - 0.7f) * 0.75f;
			rightArm.rotateAngleX = (float) (rightArm.rotateAngleX - (f7 * 1.2 + f8));
			rightArm.rotateAngleZ = MathHelper.sin(onGround * 3.1415927f) * -0.4f;
		}
		rightLeg.rotateAngleX = MathHelper.cos(f * 0.6662f) * 1.4f * f1;
		leftLeg.rotateAngleX = MathHelper.cos(f * 0.6662f + 3.1415927f) * 1.4f * f1;
		rightLeg.rotateAngleY = 0.0f;
		leftLeg.rotateAngleY = 0.0f;
		rightArm.rotateAngleY = 0.0f;
		leftArm.rotateAngleY = 0.0f;
		rightArm.rotateAngleZ += MathHelper.cos(f2 * 0.09f) * 0.05f + 0.05f;
		leftArm.rotateAngleZ -= MathHelper.cos(f2 * 0.09f) * 0.05f + 0.05f;
		rightArm.rotateAngleX += MathHelper.sin(f2 * 0.067f) * 0.05f;
		leftArm.rotateAngleX -= MathHelper.sin(f2 * 0.067f) * 0.05f;
		boolean throwing = entity instanceof LOTREntityMountainTroll && ((LOTREntityMountainTroll) entity).isThrowingRocks();
		if (entity instanceof LOTREntitySnowTroll && ((LOTREntitySnowTroll) entity).isThrowingSnow()) {
			throwing = true;
		}
		if (throwing) {
			rightArm.rotateAngleX -= 0.5f;
			rightArm.rotateAngleZ -= 0.4f;
			leftArm.rotateAngleX = rightArm.rotateAngleX;
			leftArm.rotateAngleY = -rightArm.rotateAngleY;
			leftArm.rotateAngleZ = -rightArm.rotateAngleZ;
		}
		if (entity instanceof EntityLivingBase) {
			float f62 = MathHelper.sin(f * 0.2f) * 0.3f * f1;
			head.rotationPointX += MathHelper.sin(f62) * 27.0f;
			head.rotationPointY += 27.0f - MathHelper.cos(f62) * 27.0f;
			head.rotateAngleZ += f62;
			body.rotateAngleZ += f62;
			float armRotationOffsetX = MathHelper.sin(f62) * 23.0f + MathHelper.cos(f62) * 12.0f - 12.0f;
			float armRotationOffsetY = MathHelper.cos(f62) * -23.0f + MathHelper.sin(f62) * 12.0f + 23.0f;
			rightArm.rotationPointX += armRotationOffsetX;
			rightArm.rotationPointY -= armRotationOffsetY;
			rightArm.rotateAngleZ += f62;
			leftArm.rotationPointX += armRotationOffsetX;
			leftArm.rotationPointY += armRotationOffsetY;
			leftArm.rotateAngleZ += f62;
		}
		headHurt.rotationPointX = head.rotationPointX;
		headHurt.rotationPointY = head.rotationPointY;
		headHurt.rotationPointZ = head.rotationPointZ;
		headHurt.rotateAngleX = head.rotateAngleX;
		headHurt.rotateAngleY = head.rotateAngleY;
		headHurt.rotateAngleZ = head.rotateAngleZ;
	}
}
