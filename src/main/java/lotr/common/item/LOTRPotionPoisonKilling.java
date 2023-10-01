package lotr.common.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.LOTRDamage;
import lotr.common.LOTRMod;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class LOTRPotionPoisonKilling extends Potion {
	public LOTRPotionPoisonKilling() {
		super(30, true, Potion.poison.getLiquidColor());
		setPotionName("potion.lotr.drinkPoison");
		setEffectiveness(Potion.poison.getEffectiveness());
		setIconIndex(0, 0);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public boolean hasStatusIcon() {
		return false;
	}

	@Override
	public boolean isReady(int tick, int level) {
		int freq = 5 >> level;
		return freq == 0 || tick % freq == 0;
	}

	@Override
	public void performEffect(EntityLivingBase entity, int level) {
		entity.attackEntityFrom(LOTRDamage.poisonDrink, 1.0f);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void renderInventoryEffect(int x, int y, PotionEffect effect, Minecraft mc) {
		LOTRMod.proxy.renderCustomPotionEffect(x, y, effect, mc);
	}
}
