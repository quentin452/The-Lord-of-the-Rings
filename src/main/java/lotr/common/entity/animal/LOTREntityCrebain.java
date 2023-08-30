package lotr.common.entity.animal;

import net.minecraft.entity.*;
import net.minecraft.world.World;

public class LOTREntityCrebain extends LOTREntityBird {
	public static float CREBAIN_SCALE = 1.8f;

	public LOTREntityCrebain(World world) {
		super(world);
		setSize(width * CREBAIN_SCALE, height * CREBAIN_SCALE);
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(10.0);
	}

	@Override
	public String getBirdTextureDir() {
		return "crebain";
	}

	@Override
	public float getSoundPitch() {
		return super.getSoundPitch() * 0.85f;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		setBirdType(LOTREntityBird.BirdType.CROW);
		return data;
	}
}
