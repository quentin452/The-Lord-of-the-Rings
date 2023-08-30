package lotr.common.entity.animal;

import net.minecraft.entity.*;
import net.minecraft.world.World;

public class LOTREntityGorcrow extends LOTREntityBird {
	public static float GORCROW_SCALE = 1.4f;

	public LOTREntityGorcrow(World world) {
		super(world);
		setSize(width * GORCROW_SCALE, height * GORCROW_SCALE);
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(8.0);
	}

	@Override
	public String getBirdTextureDir() {
		return "gorcrow";
	}

	@Override
	public float getSoundPitch() {
		return super.getSoundPitch() * 0.75f;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		setBirdType(LOTREntityBird.BirdType.CROW);
		return data;
	}
}
