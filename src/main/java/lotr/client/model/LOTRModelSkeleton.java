package lotr.client.model;

import net.minecraft.client.model.*;

public class LOTRModelSkeleton extends ModelZombie {
	public LOTRModelSkeleton() {
		this(0.0f);
	}

	public LOTRModelSkeleton(float f) {
		super(f, 0.0f, 64, 32);
		if (f == 0.0f) {
			bipedRightArm = new ModelRenderer(this, 40, 16);
			bipedRightArm.addBox(-1.0f, -2.0f, -1.0f, 2, 12, 2, 0.0f);
			bipedRightArm.setRotationPoint(-5.0f, 2.0f, 0.0f);
			bipedLeftArm = new ModelRenderer(this, 40, 16);
			bipedLeftArm.mirror = true;
			bipedLeftArm.addBox(-1.0f, -2.0f, -1.0f, 2, 12, 2, 0.0f);
			bipedLeftArm.setRotationPoint(5.0f, 2.0f, 0.0f);
			bipedRightLeg = new ModelRenderer(this, 0, 16);
			bipedRightLeg.addBox(-1.0f, 0.0f, -1.0f, 2, 12, 2, 0.0f);
			bipedRightLeg.setRotationPoint(-2.0f, 12.0f, 0.0f);
			bipedLeftLeg = new ModelRenderer(this, 0, 16);
			bipedLeftLeg.mirror = true;
			bipedLeftLeg.addBox(-1.0f, 0.0f, -1.0f, 2, 12, 2, 0.0f);
			bipedLeftLeg.setRotationPoint(2.0f, 12.0f, 0.0f);
		}
	}
}
